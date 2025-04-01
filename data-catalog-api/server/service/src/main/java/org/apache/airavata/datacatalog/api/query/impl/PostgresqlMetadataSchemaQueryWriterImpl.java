package org.apache.airavata.datacatalog.api.query.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.airavata.datacatalog.api.Permission;
import org.apache.airavata.datacatalog.api.model.MetadataSchemaEntity;
import org.apache.airavata.datacatalog.api.model.MetadataSchemaFieldEntity;
import org.apache.airavata.datacatalog.api.model.UserEntity;
import org.apache.airavata.datacatalog.api.query.MetadataSchemaQueryWriter;
import org.apache.airavata.datacatalog.api.sharing.SharingManager;
import org.apache.calcite.sql.SqlBasicCall;
import org.apache.calcite.sql.SqlCall;
import org.apache.calcite.sql.SqlIdentifier;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.dialect.PostgresqlSqlDialect;
import org.apache.calcite.sql.util.SqlShuttle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostgresqlMetadataSchemaQueryWriterImpl implements MetadataSchemaQueryWriter {

    @Autowired
    SharingManager sharingManager;

    private static final class MetadataSchemaFieldFilterRewriter extends SqlShuttle {

        final Collection<MetadataSchemaEntity> metadataSchemas;
        final Map<String, String> tableAliases;
        final StringBuilder sql = new StringBuilder();
        // Maintain queue of binary logical operators so we know when to
        // open/close parentheses and when to add "AND" and "OR" to the query
        Deque<SqlCall> binaryLogicalOperatorNodes = new ArrayDeque<>();

        MetadataSchemaFieldFilterRewriter(Collection<MetadataSchemaEntity> metadataSchemas,
                Map<String, String> tableAliases) {
            this.metadataSchemas = metadataSchemas;
            this.tableAliases = tableAliases;
        }

        MetadataSchemaFieldEntity resolveMetadataSchemaField(SqlIdentifier sqlIdentifier) {

            MetadataSchemaEntity metadataSchema = null;
            String fieldName = null;
            if (sqlIdentifier.names.size() == 2) {
                String tableName = sqlIdentifier.names.get(0);
                metadataSchema = resolveMetadataSchema(tableName);
                fieldName = sqlIdentifier.names.get(1);
            } else if (sqlIdentifier.names.size() == 1) {
                // TODO: just pick the first one, but in general we would need
                // to look through all of the metadata schemas to find the one
                // that this field belongs to
                metadataSchema = this.metadataSchemas.iterator().next();
                fieldName = sqlIdentifier.names.get(0);
            } else {
                throw new RuntimeException("Unexpected sqlIdentifier: " + sqlIdentifier);
            }

            for (MetadataSchemaFieldEntity metadataSchemaField : metadataSchema.getMetadataSchemaFields()) {
                if (metadataSchemaField.getFieldName().equals(fieldName)) {
                    return metadataSchemaField;
                }
            }
            // If none matched, must not be a metadata schema field
            return null;
        }

        MetadataSchemaEntity resolveMetadataSchema(String tableOrAliasName) {
            String tableName = tableOrAliasName;
            if (this.tableAliases.containsKey(tableOrAliasName)) {
                tableName = this.tableAliases.get(tableOrAliasName);
            }
            return findMetadataSchema(tableName);
        }

        MetadataSchemaEntity findMetadataSchema(String schemaName) {
            for (MetadataSchemaEntity metadataSchema : this.metadataSchemas) {
                if (metadataSchema.getSchemaName().equals(schemaName)) {
                    return metadataSchema;
                }
            }
            return null;
        }

        public String finalizeSql() {
            while (!this.binaryLogicalOperatorNodes.isEmpty()) {
                this.binaryLogicalOperatorNodes.pop();
                this.sql.append(" ) ");
            }
            return this.sql.toString();
        }

        @Override
        public SqlNode visit(SqlCall call) {
            SqlCall currentOperator = this.binaryLogicalOperatorNodes.peek();
            while (currentOperator != null
                    && !call.getParserPosition().overlaps(currentOperator.getParserPosition())) {
                this.binaryLogicalOperatorNodes.remove();
                currentOperator = this.binaryLogicalOperatorNodes.peek();
                this.sql.append(" ) ");
                this.sql.append(currentOperator.getOperator().toString());
                this.sql.append(" ");
            }
            if (call.getKind() == SqlKind.NOT) {
                this.sql.append(" NOT ");
            } else if (call.getKind() == SqlKind.AND || call.getKind() == SqlKind.OR) {
                this.binaryLogicalOperatorNodes.push(call);
                this.sql.append("( ");
            } else {
                SqlNode sqlNode = call.getOperandList().get(0);
                // TODO: this assumes that there would only ever be one metadata schema field
                // and that it comes first and the second operand is a literal
                if (sqlNode.isA(Set.of(SqlKind.IDENTIFIER))) {
                    SqlIdentifier sqlIdentifier = (SqlIdentifier) sqlNode;
                    MetadataSchemaFieldEntity metadataSchemaField = resolveMetadataSchemaField(sqlIdentifier);
                    if (metadataSchemaField != null) {
                        // TODO: assuming an alias
                        sql.append(sqlIdentifier.names.get(0));
                        sql.append(".");
                        sql.append("metadata @@ '");
                        sql.append(metadataSchemaField.getJsonPath());
                        sql.append(" ");
                        switch (call.getOperator().kind) {
                            case EQUALS:
                                sql.append(" == ");
                                break;
                            default:
                                sql.append(call.getOperator().kind.sql);
                                break;
                        }
                        sql.append(call.getOperandList().get(1).toSqlString(new PostgresqlSqlDialect(
                                PostgresqlSqlDialect.DEFAULT_CONTEXT.withLiteralQuoteString("\""))));
                        sql.append("'");
                    } else {
                        sql.append(call.toSqlString(PostgresqlSqlDialect.DEFAULT));
                    }
                }

                if (currentOperator != null && !(call.getParserPosition().getEndColumnNum() == currentOperator
                        .getParserPosition().getEndColumnNum()
                        && call.getParserPosition().getEndLineNum() == currentOperator.getParserPosition()
                                .getEndLineNum())) {
                    sql.append(" ");
                    sql.append(currentOperator.getOperator().toString());
                    sql.append(" ");
                }
            }
            return super.visit(call);
        }
    }
    @Override
    public String rewriteQuery(UserEntity userEntity, SqlNode sqlNode, Collection<MetadataSchemaEntity> metadataSchemas,
                               Map<String, String> tableAliases) {
        List<String> groupIds = userEntity.getGroupIds();
        if (groupIds == null) {
            groupIds = java.util.Collections.emptyList();
        }
        return writeCommonTableExpressions(userEntity, metadataSchemas) + buildSelectStatement(sqlNode, metadataSchemas, tableAliases);
    }

    private String buildSelectStatement(SqlNode sqlNode, Collection<MetadataSchemaEntity> metadataSchemas,
                                        Map<String, String> tableAliases) {
        StringBuilder sb = new StringBuilder();
        if (sqlNode instanceof SqlSelect) {
            sb.append(" SELECT * FROM ");
            sb.append(((SqlSelect) sqlNode).getFrom().toSqlString(PostgresqlSqlDialect.DEFAULT));
            if (((SqlSelect) sqlNode).getWhere() != null) {
                sb.append(" WHERE ");
                sb.append(rewriteWhereClauseFilters(sqlNode, metadataSchemas, tableAliases));
            }
        } else if (sqlNode instanceof SqlBasicCall unionNode &&
                ((SqlBasicCall) sqlNode).getOperator().getKind() == SqlKind.UNION) {

            for (int i = 0; i < unionNode.getOperandList().size(); i++) {
                if (i > 0) {
                    sb.append(unionNode.getOperator().getName());
                }
                sb.append(buildSelectStatement(unionNode.getOperandList().get(i), metadataSchemas, tableAliases));
            }
        }
        return sb.toString();
    }

    private String rewriteWhereClauseFilters(SqlNode sqlNode, Collection<MetadataSchemaEntity> metadataSchemas,
            Map<String, String> tableAliases) {
        MetadataSchemaFieldFilterRewriter filterRewriter = new MetadataSchemaFieldFilterRewriter(metadataSchemas,
                tableAliases);
        sqlNode.accept(filterRewriter);
        return filterRewriter.finalizeSql();
    }
String writeCommonTableExpressions(UserEntity userEntity, Collection<MetadataSchemaEntity> metadataSchemas) {
    StringBuilder sb = new StringBuilder();
    List<String> ctes = new ArrayList<>();
    long userDbId = userEntity.getUserId(); // userEntity -> user -> getUserId()
    List<String> groupIds = userEntity.getGroupIds();
    // user_group_union CTE
    // (simple_data_product_sharing_view, integer)
    //union (simple_group_sharing, varchar)
    StringBuilder unionCte = new StringBuilder();
    unionCte.append("user_group_union AS (");

    //user_id = userDbId, permission_id in (0,1) => OWNER=0,READ=1
    unionCte.append(" SELECT data_product_id FROM ")
            .append(sharingManager.getDataProductSharingView()) // "simple_data_product_sharing_view"
            .append(" WHERE user_id = ")
            .append(userDbId)
            .append(" AND permission_id IN (")
            .append(Permission.OWNER.getNumber())
            .append(",")
            .append(Permission.READ.getNumber())
            .append(")");

    if (groupIds != null && !groupIds.isEmpty()) {
        unionCte.append(" UNION SELECT sgs.data_product_id ")
                .append(" FROM simple_group_sharing sgs ")
                .append(" JOIN simple_group g ON g.simple_group_id = sgs.simple_group_id ")
                .append(" WHERE sgs.permission_id IN ('READ') ")
                .append("   AND g.external_id IN (");
        for (int i = 0; i < groupIds.size(); i++) {
            if (i > 0) unionCte.append(",");
            unionCte.append("'").append(groupIds.get(i).replace("'", "''")).append("'");
        }
        unionCte.append(")");
    }
    unionCte.append(")"); // end of user_group_union

    ctes.add(unionCte.toString());
    for (MetadataSchemaEntity metadataSchema : metadataSchemas) {
        String cteForSchema = writeCommonTableExpression(userEntity, metadataSchema);
        ctes.add(cteForSchema);
    }
    sb.append("WITH ");
    sb.append(String.join(", ", ctes));
    return sb.toString();
}

    String writeCommonTableExpression(UserEntity userEntity, MetadataSchemaEntity metadataSchemaEntity) {

        StringBuilder sb = new StringBuilder();
        sb.append(metadataSchemaEntity.getSchemaName());
        sb.append(" AS (");
        sb.append(
                "select dp_.data_product_id, dp_.parent_data_product_id, dp_.external_id, dp_.name, dp_.metadata, dp_.owner_id ");
        // for (MetadataSchemaFieldEntity field :
        // metadataSchemaEntity.getMetadataSchemaFields()) {
        // TODO: include each field as well?
        // }
        sb.append("from data_product dp_ ");
        sb.append("inner join data_product_metadata_schema dpms_ on dpms_.data_product_id = dp_.data_product_id ");
        sb.append("inner join metadata_schema ms_ on ms_.metadata_schema_id = dpms_.metadata_schema_id ");
        //sb.append("inner join ");
        //sb.append(sharingManager.getDataProductSharingView());
        //sb.append(" dpsv_ on dpsv_.data_product_id = dp_.data_product_id ");
        //sb.append("and dpsv_.user_id = ");
        // TODO: change these to be bound parameters
        //sb.append(userEntity.getUserId());
        //sb.append(" and dpsv_.permission_id in (");
       // sb.append(Permission.OWNER.getNumber());
        //sb.append(",");
        //sb.append(Permission.READ.getNumber());
        //sb.append(") ");
        //sb.append("where ms_.metadata_schema_id = " + metadataSchemaEntity.getMetadataSchemaId());
        sb.append("   WHERE dp_.data_product_id IN (SELECT data_product_id FROM user_group_union)");
        sb.append("     AND ms_.metadata_schema_id = ");
        sb.append(metadataSchemaEntity.getMetadataSchemaId());
        sb.append(")");
        return sb.toString();
    }
}
