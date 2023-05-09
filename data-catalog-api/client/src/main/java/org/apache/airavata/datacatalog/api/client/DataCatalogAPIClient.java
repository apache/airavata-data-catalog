package org.apache.airavata.datacatalog.api.client;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.airavata.datacatalog.api.DataCatalogAPIServiceGrpc;
import org.apache.airavata.datacatalog.api.DataCatalogAPIServiceGrpc.DataCatalogAPIServiceBlockingStub;
import org.apache.airavata.datacatalog.api.DataProduct;
import org.apache.airavata.datacatalog.api.DataProductAddToMetadataSchemaRequest;
import org.apache.airavata.datacatalog.api.DataProductAddToMetadataSchemaResponse;
import org.apache.airavata.datacatalog.api.DataProductCreateRequest;
import org.apache.airavata.datacatalog.api.DataProductCreateResponse;
import org.apache.airavata.datacatalog.api.DataProductDeleteRequest;
import org.apache.airavata.datacatalog.api.DataProductGetRequest;
import org.apache.airavata.datacatalog.api.DataProductGetResponse;
import org.apache.airavata.datacatalog.api.DataProductRemoveFromMetadataSchemaRequest;
import org.apache.airavata.datacatalog.api.DataProductRemoveFromMetadataSchemaResponse;
import org.apache.airavata.datacatalog.api.DataProductSearchRequest;
import org.apache.airavata.datacatalog.api.DataProductSearchResponse;
import org.apache.airavata.datacatalog.api.DataProductUpdateRequest;
import org.apache.airavata.datacatalog.api.DataProductUpdateResponse;
import org.apache.airavata.datacatalog.api.FieldValueType;
import org.apache.airavata.datacatalog.api.MetadataSchema;
import org.apache.airavata.datacatalog.api.MetadataSchemaCreateRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaCreateResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaField;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldCreateRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldCreateResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldGetRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldGetResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldListRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaGetRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaGetResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaListRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaListResponse;
import org.apache.airavata.datacatalog.api.UserInfo;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;;

public class DataCatalogAPIClient {

    private final DataCatalogAPIServiceBlockingStub blockingStub;
    // String tenantId = "custos-e6vgzgskcr0pewrejma3-10000002";
    String tenantId = "demotenant";
    String userId = "demouser";
    private final UserInfo userInfo = UserInfo.newBuilder()
            .setUserId(userId)
            .setTenantId(tenantId)
            .build();

    public DataCatalogAPIClient(Channel channel) {
        blockingStub = DataCatalogAPIServiceGrpc.newBlockingStub(channel);
    }

    public DataProduct createDataProduct(DataProduct dataProduct) {
        DataProductCreateRequest request = DataProductCreateRequest.newBuilder().setDataProduct(dataProduct)
                .setUserInfo(userInfo).build();
        DataProductCreateResponse response = blockingStub.createDataProduct(request);
        return response.getDataProduct();
    }

    public DataProduct updateDataProduct(DataProduct dataProduct) {
        DataProductUpdateRequest request = DataProductUpdateRequest.newBuilder().setDataProduct(dataProduct)
                .setUserInfo(userInfo).build();
        DataProductUpdateResponse response = blockingStub.updateDataProduct(request);
        return response.getDataProduct();
    }

    public DataProduct getDataProduct(String dataProductId) {
        DataProductGetRequest request = DataProductGetRequest.newBuilder().setDataProductId(dataProductId)
                .setUserInfo(userInfo).build();
        DataProductGetResponse response = blockingStub.getDataProduct(request);
        return response.getDataProduct();
    }

    public void deleteDataProduct(String dataProductId) {
        DataProductDeleteRequest request = DataProductDeleteRequest.newBuilder().setDataProductId(dataProductId)
                .setUserInfo(userInfo).build();
        blockingStub.deleteDataProduct(request);
    }

    public MetadataSchema createMetadataSchema(MetadataSchema metadataSchema) {
        MetadataSchemaCreateRequest request = MetadataSchemaCreateRequest.newBuilder().setMetadataSchema(metadataSchema)
                .build();
        MetadataSchemaCreateResponse response = blockingStub.createMetadataSchema(request);
        return response.getMetadataSchema();
    }

    public MetadataSchema getMetadataSchema(String schemaName) {
        MetadataSchemaGetRequest request = MetadataSchemaGetRequest.newBuilder().setSchemaName(schemaName).build();
        try {
            MetadataSchemaGetResponse response = blockingStub.getMetadataSchema(request);
            return response.getMetadataSchema();
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return null;
            }
            throw e;
        }
    }

    public List<MetadataSchema> getMetadataSchemas() {
        MetadataSchemaListRequest request = MetadataSchemaListRequest.newBuilder().setUserInfo(userInfo).build();
        MetadataSchemaListResponse response = blockingStub.getMetadataSchemas(request);
        return response.getMetadataSchemasList();
    }

    public MetadataSchemaField getMetadataSchemaField(String schemaName, String fieldName) {
        MetadataSchemaFieldGetRequest request = MetadataSchemaFieldGetRequest.newBuilder().setSchemaName(schemaName)
                .setFieldName(fieldName).build();
        try {
            MetadataSchemaFieldGetResponse response = blockingStub.getMetadataSchemaField(request);
            return response.getMetadataSchemaField();
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return null;
            }
            throw e;
        }
    }

    public MetadataSchemaField createMetadataSchemaField(MetadataSchemaField metadataSchemaField) {
        MetadataSchemaFieldCreateRequest request = MetadataSchemaFieldCreateRequest.newBuilder()
                .setMetadataSchemaField(metadataSchemaField).build();
        MetadataSchemaFieldCreateResponse response = blockingStub.createMetadataSchemaField(request);
        return response.getMetadataSchemaField();
    }

    public List<MetadataSchemaField> getMetadataSchemaFields(String schemaName) {
        MetadataSchemaFieldListRequest request = MetadataSchemaFieldListRequest.newBuilder().setSchemaName(schemaName)
                .build();
        MetadataSchemaFieldListResponse response = blockingStub.getMetadataSchemaFields(request);
        return response.getMetadataSchemaFieldsList();
    }

    public DataProduct addDataProductToMetadataSchema(String dataProductId, String schemaName) {
        DataProductAddToMetadataSchemaRequest request = DataProductAddToMetadataSchemaRequest.newBuilder()
                .setDataProductId(dataProductId).setSchemaName(schemaName).setUserInfo(userInfo).build();
        DataProductAddToMetadataSchemaResponse response = blockingStub.addDataProductToMetadataSchema(request);
        return response.getDataProduct();
    }

    public DataProduct removeDataProductFromMetadataSchema(String dataProductId, String schemaName) {
        DataProductRemoveFromMetadataSchemaRequest request = DataProductRemoveFromMetadataSchemaRequest.newBuilder()
                .setDataProductId(dataProductId).setSchemaName(schemaName).setUserInfo(userInfo).build();
        DataProductRemoveFromMetadataSchemaResponse response = blockingStub
                .removeDataProductFromMetadataSchema(request);
        return response.getDataProduct();
    }

    public List<DataProduct> searchDataProducts(String sql) {
        DataProductSearchRequest request = DataProductSearchRequest.newBuilder().setSql(sql).setUserInfo(userInfo)
                .build();
        DataProductSearchResponse response = blockingStub.searchDataProducts(request);
        return response.getDataProductsList();
    }

    public static void main(String[] args) throws InterruptedException {
        String target = "localhost:6565";

        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        try {
            DataCatalogAPIClient client = new DataCatalogAPIClient(channel);
            DataProduct parentDataProduct = DataProduct.newBuilder().setName("parent dp").build();
            DataProduct parentResult = client.createDataProduct(parentDataProduct);

            DataProduct dataProduct = DataProduct.newBuilder().setName("testing").setMetadata("{\"foo\": \"bar\"}")
                    .setParentDataProductId(parentResult.getDataProductId())
                    .build();
            DataProduct result = client.createDataProduct(dataProduct);
            System.out.println(MessageFormat.format("Created data product with id [{0}]", result.getDataProductId()));

            DataProduct updatedDataProduct = result.toBuilder().setName("updated name").build();
            result = client.updateDataProduct(updatedDataProduct);
            System.out.println(MessageFormat.format("Updated data product with id [{0}] to have name [{1}]",
                    result.getDataProductId(), result.getName()));

            DataProduct retrievedDataProduct = client.getDataProduct(result.getDataProductId());
            System.out.println(MessageFormat.format("Retrieved data product with id [{0}] to have name [{1}]",
                    retrievedDataProduct.getDataProductId(), retrievedDataProduct.getName()));

            DataProduct dataProduct2 = DataProduct.newBuilder().setName("testing 2").setMetadata("{\"foo\": \"bar\"}")
                    .build();
            DataProduct result2 = client.createDataProduct(dataProduct2);
            System.out.println(
                    MessageFormat.format("Created second data product [{0}]", result2));

            client.deleteDataProduct(result2.getDataProductId());
            System.out.println(
                    MessageFormat.format("Deleted data product with id [{0}]", result2.getDataProductId()));

            // First check if metadata schema exists
            MetadataSchema metadataSchema = client.getMetadataSchema("my_schema");
            if (metadataSchema == null) {
                metadataSchema = MetadataSchema.newBuilder().setSchemaName("my_schema").build();
                metadataSchema = client.createMetadataSchema(metadataSchema);
                System.out.println(
                        MessageFormat.format("Created metadata schema with name [{0}]",
                                metadataSchema.getSchemaName()));
            } else {
                System.out.println(
                        MessageFormat.format("Found metadata schema with name [{0}]",
                                metadataSchema.getSchemaName()));
            }

            MetadataSchemaField field1 = MetadataSchemaField.newBuilder().setFieldName("field1")
                    .setJsonPath("$.field1").setValueType(FieldValueType.FLOAT)
                    .setSchemaName(metadataSchema.getSchemaName()).build();
            MetadataSchemaField field1Exists = client.getMetadataSchemaField(field1.getSchemaName(),
                    field1.getFieldName());
            if (field1Exists == null) {
                field1 = client.createMetadataSchemaField(field1);
                System.out.println(MessageFormat.format("Created metadata schema field [{0}] in schema [{1}]",
                        field1.getFieldName(), field1.getSchemaName()));
            } else {
                field1 = field1Exists;
                System.out.println(MessageFormat.format("Found metadata schema field [{0}] in schema [{1}]",
                        field1.getFieldName(), field1.getSchemaName()));
            }

            MetadataSchemaField field2 = MetadataSchemaField.newBuilder().setFieldName("field2")
                    .setJsonPath("$.field2").setValueType(FieldValueType.FLOAT)
                    .setSchemaName(metadataSchema.getSchemaName()).build();
            MetadataSchemaField field2Exists = client.getMetadataSchemaField(field2.getSchemaName(),
                    field2.getFieldName());
            if (field2Exists == null) {
                field2 = client.createMetadataSchemaField(field2);
                System.out.println(MessageFormat.format("Created metadata schema field [{0}] in schema [{1}]",
                        field2.getFieldName(), field2.getSchemaName()));
            } else {
                field2 = field2Exists;
                System.out.println(MessageFormat.format("Found metadata schema field [{0}] in schema [{1}]",
                        field2.getFieldName(), field2.getSchemaName()));
            }

            MetadataSchemaField field3 = MetadataSchemaField.newBuilder().setFieldName("field3")
                    .setJsonPath("$.field3").setValueType(FieldValueType.STRING)
                    .setSchemaName(metadataSchema.getSchemaName()).build();
            MetadataSchemaField field3Exists = client.getMetadataSchemaField(field3.getSchemaName(),
                    field3.getFieldName());
            if (field3Exists == null) {
                field3 = client.createMetadataSchemaField(field3);
                System.out.println(MessageFormat.format("Created metadata schema field [{0}] in schema [{1}]",
                        field3.getFieldName(), field3.getSchemaName()));
            } else {
                field3 = field3Exists;
                System.out.println(MessageFormat.format("Found metadata schema field [{0}] in schema [{1}]",
                        field3.getFieldName(), field3.getSchemaName()));
            }

            List<MetadataSchemaField> fields = client.getMetadataSchemaFields(metadataSchema.getSchemaName());
            System.out.println(MessageFormat.format("Found {0} fields for schema {1}", fields.size(),
                    metadataSchema.getSchemaName()));
            for (MetadataSchemaField field : fields) {
                System.out.println(MessageFormat.format("-> field {0}", field.getFieldName()));
            }

            result = client.addDataProductToMetadataSchema(result.getDataProductId(), metadataSchema.getSchemaName());
            System.out.println(MessageFormat.format("Added data product [{0}] to metadata schema [{1}]",
                    result.getDataProductId(), metadataSchema.getSchemaName()));

            result = client.removeDataProductFromMetadataSchema(result.getDataProductId(),
                    metadataSchema.getSchemaName());
            System.out.println(MessageFormat.format("Removed data product [{0}] from metadata schema [{1}]",
                    result.getDataProductId(), metadataSchema.getSchemaName()));

            // Create data product that belongs to my_schema schema
            DataProduct dataProduct3 = DataProduct.newBuilder()
                    .setName("testing 3")
                    .setMetadata("{\"field3\": \"bar\", \"field1\": 10}")
                    .addMetadataSchemas("my_schema")
                    .build();
            DataProduct result3 = client.createDataProduct(dataProduct3);
            System.out.println(
                    MessageFormat.format("Created third data product [{0}], supporting schemas [{1}]",
                            result3.getDataProductId(), result3.getMetadataSchemasList()));

            // Create another data product that belongs to my_schema schema, but with
            // different "field3" and "field1" values
            DataProduct dataProduct4 = DataProduct.newBuilder()
                    .setName("testing 4")
                    .setMetadata("{\"field3\": \"baz\", \"field1\": 2}")
                    .addMetadataSchemas("my_schema")
                    .build();
            client.createDataProduct(dataProduct4);

            List<DataProduct> searchResults = client.searchDataProducts("""
                    select * from my_schema where field3 = 'bar'
                     """);
            System.out.println(searchResults);

            searchResults = client.searchDataProducts("""
                    select * from my_schema where (field1 < 5 or field3 = 'bar') and field1 > 0
                    and external_id = 'fff'
                    """);
            // searchResults = client.searchDataProducts("""
            // select * from my_schema where not (field1 < 5 or field3 = 'bar')
            // """);
            System.out.println("Shouldn't match anything: " + searchResults);

            // MetadataSchemas retrieval
            MetadataSchema exp_schema = client.getMetadataSchema("exp_schema");
            if (exp_schema == null) {
                exp_schema = MetadataSchema.newBuilder().setSchemaName("exp_schema").build();
                exp_schema = client.createMetadataSchema(exp_schema);
                System.out.println(MessageFormat.format("Created metadata schema with name [{0}]",
                        exp_schema.getSchemaName()));
            }
            List<MetadataSchema> metadataSchemas = client.getMetadataSchemas();
            System.out.println("Metadata schema list: " + metadataSchemas);

            // Retrieve data products belonging to different schemas
            // Create data product that belongs to both my_schema and exp_schema
            DataProduct dataProduct5 = DataProduct.newBuilder()
                    .setName("exp-schema testing5")
                    .setMetadata("{\"field3\": \"bar\", \"field1\": 10}")
                    .addMetadataSchemas("my_schema")
                    .addMetadataSchemas("exp_schema")
                    .build();
            client.createDataProduct(dataProduct5);

            // Create data product that belongs to exp_schema
            DataProduct dataProduct6 = DataProduct.newBuilder()
                    .setName("exp-schema testing6")
                    .setMetadata("{\"field3\": \"bar\", \"field1\": 10}")
                    .addMetadataSchemas("exp_schema")
                    .build();
            client.createDataProduct(dataProduct5);

            // Get the *distinct* data products that belong to both 'my_schema' and 'exp_schema'
            List<DataProduct> searchResultsUnion = client.searchDataProducts("""
                    select data_product_id from my_schema union distinct select data_product_id from exp_schema
                     """);
            // Get the data products that belong to both 'my_schema' and 'exp_schema'
            List<DataProduct> searchResultsUnionAll = client.searchDataProducts("""
                    select data_product_id from my_schema union all select data_product_id from exp_schema
                     """);

            System.out.println(MessageFormat.format("UNION search result count: [{0}], UNION ALL search result count: [{1}]. Should be different",
                    searchResultsUnion.size(), searchResultsUnionAll.size()));

        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
