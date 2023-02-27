package org.apache.airavata.datacatalog.api.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

import javax.sql.DataSource;

import org.apache.calcite.adapter.jdbc.JdbcSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.impl.ViewTable;
import org.apache.calcite.sql.SqlExplainFormat;
import org.apache.calcite.sql.SqlExplainLevel;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.dialect.PostgresqlSqlDialect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.Planner;
import org.apache.calcite.tools.RelConversionException;
import org.apache.calcite.tools.RelRunner;
import org.apache.calcite.tools.ValidationException;

public class MetadataQueryParser {
    private static final String POSTGRESQL_SCHEMA = "PUBLIC";

    public static void main(String[] args)
            throws SqlParseException, SQLException, ValidationException, RelConversionException {
        // SqlParser parser = SqlParser.create(
        // "SELECT * FROM smilesdb WHERE created_date > '2020-01-01' AND absorb < 300.0
        // ORDER BY created_date desc LIMIT 10");
        // SqlNode query = parser.parseQuery();
        // System.out.println(query.toSqlString(PostgresqlSqlDialect.DEFAULT));

        // Properties props = new Properties();
        // props.setProperty("currentSchema", "data_catalog");
        // props.setProperty("password", "secret");
        // props.setProperty("ssl", "true");
        // Connection connection = DriverManager.getConnection("jdbc:calcite:", props);
        Connection connection = DriverManager.getConnection("jdbc:calcite:");
        // Set the default schema for the connection
        // connection.setSchema("data_catalog");
        // Unwrap our connection using the CalciteConnection
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        // calciteConnection.setSchema("data_catalog");

        // Get a pointer to our root schema for our Calcite Connection
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        // SchemaPlus rootSchema = Frameworks.createRootSchema(false);

        // Instantiate a data source, this can be autowired in using Spring as well
        DataSource postgresDataSource = JdbcSchema.dataSource(
                "jdbc:postgresql://localhost/data_catalog",
                "org.postgresql.Driver", // Change this if you want to use something like MySQL, Oracle,
                                         // etc.
                "postgres", // username
                "example" // password
        );
        // JdbcSchema jdbcSchema = JdbcSchema.create(rootSchema, POSTGRESQL_SCHEMA,
        // postgresDataSource, null, null);
        // System.out.println("tables: " + jdbcSchema.getTableNames());
        // rootSchema.add(POSTGRESQL_SCHEMA,
        // JdbcSchema.create(rootSchema, POSTGRESQL_SCHEMA, postgresDataSource, null,
        // null));
        // rootSchema.add("data_product", jdbcSchema.getTable("data_product"));
        SchemaPlus dcSchema = rootSchema.add("data_catalog",
                JdbcSchema.create(rootSchema, "data_catalog", postgresDataSource, null, null));
        dcSchema.add("smilesdb", ViewTable.viewMacro(dcSchema,
                "select data_product_id, parent_data_product_id, external_id, metadata, name from \"data_catalog\".\"data_product\" where jsonb_path_exists(\"metadata\", '$.foo == \"bar\"')",
                Collections.emptyList(), Arrays.asList("data_catalog", "smilesdb"), false));

        System.out.println("subschemas: " + rootSchema.getSubSchemaNames());

        FrameworkConfig config = Frameworks.newConfigBuilder()
                .defaultSchema(rootSchema)
                .parserConfig(PostgresqlSqlDialect.DEFAULT.configureParser(SqlParser.config()))
                // .parserConfig(SqlParser.Config.DEFAULT.withCaseSensitive(false).withQuoting(Quoting.))
                .build();
        // String query = "SELECT * FROM public.\"data_product\" WHERE
        // \"data_product_id\" = 1";
        // String query = "SELECT * FROM data_catalog.data_product WHERE data_product_id
        // = 1";
        // String query = "SELECT * FROM data_product WHERE data_product_id = 1";
        // "smilesdb" view macro
        String query = "SELECT * FROM data_catalog.smilesdb WHERE data_product_id = 1";
        Planner planner = Frameworks.getPlanner(config);
        SqlNode parse = planner.parse(query);

        // Change unqualified table referenced into qualified
        // final SqlNode transformedNode = parse.accept(
        // new SqlShuttle() {
        // @Override
        // public SqlNode visit(SqlIdentifier id) {
        // System.out.println("id: " + id);
        // System.out.println("id.names: " + id.names);
        // if (id.names.equals(Collections.singletonList("data_product"))) {
        // return new SqlIdentifier(Arrays.asList("data_catalog", "data_product"),
        // id.getParserPosition());
        // }
        // return id;
        // }
        // });
        System.out.println(parse.toSqlString(PostgresqlSqlDialect.DEFAULT));
        // System.out.println(parse.toString());

        SqlNode validate = planner.validate(parse);
        RelRoot relRoot = planner.rel(validate);
        // RelNode rel = relRoot.project();
        RelNode rel = relRoot.rel;
        System.out.println(RelOptUtil.dumpPlan("", rel, SqlExplainFormat.TEXT, SqlExplainLevel.EXPPLAN_ATTRIBUTES));

        final RelRunner runner = connection.unwrap(RelRunner.class);
        PreparedStatement ps = runner.prepareStatement(rel);

        ps.executeQuery();

        ResultSet resultSet = ps.getResultSet();

        final StringBuilder buf = new StringBuilder();

        while (resultSet.next()) {

            int columnCount = resultSet.getMetaData().getColumnCount();

            for (int i = 1; i <= columnCount; i++) {

                buf.append(i > 1 ? "; " : "")
                        .append(resultSet.getMetaData().getColumnLabel(i))
                        .append("=")
                        .append(resultSet.getObject(i));
            }

            System.out.println("Entry: " + buf.toString());

            buf.setLength(0);
        }
    }
}
