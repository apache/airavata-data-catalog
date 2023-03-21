package org.apache.airavata.datacatalog.api.query.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.airavata.datacatalog.api.DataProduct;
import org.apache.airavata.datacatalog.api.FieldValueType;
import org.apache.airavata.datacatalog.api.exception.MetadataSchemaSqlParseException;
import org.apache.airavata.datacatalog.api.exception.MetadataSchemaSqlValidateException;
import org.apache.airavata.datacatalog.api.mapper.DataProductMapper;
import org.apache.airavata.datacatalog.api.model.DataProductEntity;
import org.apache.airavata.datacatalog.api.model.MetadataSchemaEntity;
import org.apache.airavata.datacatalog.api.model.MetadataSchemaFieldEntity;
import org.apache.airavata.datacatalog.api.query.MetadataSchemaQueryExecutor;
import org.apache.airavata.datacatalog.api.query.MetadataSchemaQueryResult;
import org.apache.airavata.datacatalog.api.query.MetadataSchemaQueryWriter;
import org.apache.airavata.datacatalog.api.repository.MetadataSchemaRepository;
import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.config.CalciteConnectionConfig;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.prepare.CalciteCatalogReader;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rel.type.RelDataTypeFactory.Builder;
import org.apache.calcite.runtime.CalciteContextException;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.SqlCall;
import org.apache.calcite.sql.SqlIdentifier;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.calcite.sql.util.SqlShuttle;
import org.apache.calcite.sql.validate.SqlValidator;
import org.apache.calcite.sql.validate.SqlValidatorUtil;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.Planner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;

@Component
public class MetadataSchemaQueryExecutorImpl implements MetadataSchemaQueryExecutor {

    private static final Logger logger = LoggerFactory.getLogger(MetadataSchemaQueryExecutorImpl.class);

    @Autowired
    MetadataSchemaRepository metadataSchemaRepository;

    @Autowired
    MetadataSchemaQueryWriter metadataSchemaQueryWriter;

    @Autowired
    EntityManager entityManager;

    @Autowired
    DataProductMapper dataProductMapper;

    @Override
    public MetadataSchemaQueryResult execute(String sql)
            throws MetadataSchemaSqlParseException, MetadataSchemaSqlValidateException {

        // Create a schema that contains the data_product table and all of the metadata
        // schemas
        SchemaPlus schema = Frameworks.createRootSchema(true);
        schema.add("data_product", new AbstractTable() {
            @Override
            public RelDataType getRowType(RelDataTypeFactory typeFactory) {
                Builder builder = (Builder) typeFactory.builder();
                return builder
                        .add("data_product_id", SqlTypeName.INTEGER)
                        .add("parent_data_product_id", SqlTypeName.INTEGER)
                        .add("external_id", SqlTypeName.VARCHAR)
                        .add("metadata", SqlTypeName.OTHER)
                        .build();
            }
        });

        // TODO: limit by tenant id
        List<MetadataSchemaEntity> metadataSchemas = metadataSchemaRepository.findAll();
        for (MetadataSchemaEntity metadataSchema : metadataSchemas) {

            schema.add(metadataSchema.getSchemaName(), new AbstractTable() {
                @Override
                public RelDataType getRowType(RelDataTypeFactory typeFactory) {
                    Builder builder = (Builder) typeFactory.builder();

                    // Add all of the common fields
                    builder.add("data_product_id", SqlTypeName.INTEGER)
                            .add("parent_data_product_id", SqlTypeName.INTEGER)
                            .add("external_id", SqlTypeName.VARCHAR)
                            .add("metadata", SqlTypeName.OTHER);

                    // Add all of the schema specific metadata fields
                    for (MetadataSchemaFieldEntity metadataSchemaField : metadataSchema.getMetadataSchemaFields()) {
                        builder.add(metadataSchemaField.getFieldName(),
                                getSqlTypeName(metadataSchemaField.getFieldValueType()));
                    }

                    return builder.build();
                }
            });
        }

        FrameworkConfig config = Frameworks.newConfigBuilder()
                .defaultSchema(schema)
                .parserConfig(SqlParser.Config.DEFAULT.withUnquotedCasing(Casing.TO_LOWER))
                .build();
        Planner planner = Frameworks.getPlanner(config);

        SqlNode sqlNode = parse(planner, sql);

        SqlValidator validator = getValidator(schema, config, planner);

        // Validate the query
        SqlNode validatedSqlNode = validate(validator, sqlNode);

        // create a mapping of table aliases to actual tables (metadata schemas)
        // For example, if query is of the form "select * from smilesdb as sm", then
        // create a mapping from sm -> smilesdb

        // TODO: may not be a SqlSelect, might be an OrderBy for example
        SqlSelect selectNode = (SqlSelect) validatedSqlNode;
        Map<String, String> tableAliases = new HashMap<>();
        selectNode.getFrom().accept(new SqlShuttle() {

            @Override
            public SqlNode visit(SqlCall call) {
                if (call.isA(Collections.singleton(SqlKind.AS))) {

                    SqlIdentifier first = call.operand(0);
                    SqlIdentifier second = call.operand(1);
                    tableAliases.put(second.getSimple(), first.getSimple());
                }
                return super.visit(call);
            }

        });

        String finalSql = metadataSchemaQueryWriter.rewriteQuery(validatedSqlNode, metadataSchemas, tableAliases);
        logger.debug("Metadata schema query final sql: {}", finalSql);

        List<DataProductEntity> dataProductEntities = entityManager.createNativeQuery(finalSql, DataProductEntity.class)
                .getResultList();

        List<DataProduct> dataProducts = new ArrayList<>();
        for (DataProductEntity dataProductEntity : dataProductEntities) {

            org.apache.airavata.datacatalog.api.DataProduct.Builder dpBuilder = DataProduct.newBuilder();
            dataProductMapper.mapEntityToModel(dataProductEntity, dpBuilder);
            dataProducts.add(dpBuilder.build());
        }

        return new MetadataSchemaQueryResult(dataProducts);
    }

    private SqlValidator getValidator(SchemaPlus schema, FrameworkConfig config, Planner planner) {
        CalciteConnectionConfig connectionConfig = CalciteConnectionConfig.DEFAULT;
        CalciteCatalogReader catalogReader = new CalciteCatalogReader(CalciteSchema.from(schema),
                CalciteSchema.from(schema).path(null),
                planner.getTypeFactory(), connectionConfig);

        SqlValidator validator = SqlValidatorUtil.newValidator(SqlStdOperatorTable.instance(),
                catalogReader, planner.getTypeFactory(),
                config.getSqlValidatorConfig().withIdentifierExpansion(false));
        return validator;
    }

    SqlNode parse(Planner planner, String sql) throws MetadataSchemaSqlParseException {
        try {
            return planner.parse(sql);
        } catch (SqlParseException e) {
            throw new MetadataSchemaSqlParseException(e);
        }
    }

    SqlNode validate(SqlValidator validator, SqlNode sqlNode) throws MetadataSchemaSqlValidateException {
        try {
            return validator.validate(sqlNode);
        } catch (CalciteContextException e) {
            throw new MetadataSchemaSqlValidateException(e);
        }
    }

    private SqlTypeName getSqlTypeName(FieldValueType fieldValueType) {

        switch (fieldValueType) {
            case BOOLEAN:
                return SqlTypeName.BOOLEAN;
            case FLOAT:
                return SqlTypeName.FLOAT;
            case INTEGER:
                return SqlTypeName.INTEGER;
            case STRING:
                return SqlTypeName.VARCHAR;
            case DATESTRING:
                return SqlTypeName.TIMESTAMP;
            default:
                throw new RuntimeException(
                        "Unexpected fieldValueType, unable to convert to SqlTypeName: " + fieldValueType);
        }
    }

}
