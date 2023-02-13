package org.apache.airavata.datacatalog.api.client;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.airavata.datacatalog.api.DataCatalogAPIServiceGrpc;
import org.apache.airavata.datacatalog.api.DataCatalogAPIServiceGrpc.DataCatalogAPIServiceBlockingStub;
import org.apache.airavata.datacatalog.api.DataProduct;
import org.apache.airavata.datacatalog.api.DataProductCreateRequest;
import org.apache.airavata.datacatalog.api.DataProductCreateResponse;
import org.apache.airavata.datacatalog.api.DataProductDeleteRequest;
import org.apache.airavata.datacatalog.api.DataProductGetRequest;
import org.apache.airavata.datacatalog.api.DataProductGetResponse;
import org.apache.airavata.datacatalog.api.DataProductUpdateRequest;
import org.apache.airavata.datacatalog.api.DataProductUpdateResponse;
import org.apache.airavata.datacatalog.api.FieldValueType;
import org.apache.airavata.datacatalog.api.MetadataSchema;
import org.apache.airavata.datacatalog.api.MetadataSchemaCreateRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaCreateResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaField;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldCreateRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldCreateResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldListRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;;

public class DataCatalogAPIClient {

    private final DataCatalogAPIServiceBlockingStub blockingStub;

    public DataCatalogAPIClient(Channel channel) {
        blockingStub = DataCatalogAPIServiceGrpc.newBlockingStub(channel);
    }

    public DataProduct createDataProduct(DataProduct dataProduct) {
        DataProductCreateRequest request = DataProductCreateRequest.newBuilder().setDataProduct(dataProduct).build();
        DataProductCreateResponse response = blockingStub.createDataProduct(request);
        return response.getDataProduct();
    }

    public DataProduct updateDataProduct(DataProduct dataProduct) {
        DataProductUpdateRequest request = DataProductUpdateRequest.newBuilder().setDataProduct(dataProduct).build();
        DataProductUpdateResponse response = blockingStub.updateDataProduct(request);
        return response.getDataProduct();
    }

    public DataProduct getDataProduct(String dataProductId) {
        DataProductGetRequest request = DataProductGetRequest.newBuilder().setDataProductId(dataProductId).build();
        DataProductGetResponse response = blockingStub.getDataProduct(request);
        return response.getDataProduct();
    }

    public void deleteDataProduct(String dataProductId) {
        DataProductDeleteRequest request = DataProductDeleteRequest.newBuilder().setDataProductId(dataProductId)
                .build();
        blockingStub.deleteDataProduct(request);
    }

    public MetadataSchema createMetadataSchema(MetadataSchema metadataSchema) {
        MetadataSchemaCreateRequest request = MetadataSchemaCreateRequest.newBuilder().setMetadataSchema(metadataSchema)
                .build();
        MetadataSchemaCreateResponse response = blockingStub.createMetadataSchema(request);
        return response.getMetadataSchema();
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

            MetadataSchema metadataSchema = MetadataSchema.newBuilder().setSchemaName("my_schema").build();
            metadataSchema = client.createMetadataSchema(metadataSchema);

            System.out.println(
                    MessageFormat.format("Created metadata schema with name [{0}]", metadataSchema.getSchemaName()));

            MetadataSchemaField field1 = MetadataSchemaField.newBuilder().setFieldName("field1")
                    .setJsonPath("$.field1").setValueType(FieldValueType.FLOAT)
                    .setSchemaName(metadataSchema.getSchemaName()).build();
            field1 = client.createMetadataSchemaField(field1);
            System.out.println(MessageFormat.format("Created metadata schema field [{0}] in schema [{1}]",
                    field1.getFieldName(), field1.getSchemaName()));

            MetadataSchemaField field2 = MetadataSchemaField.newBuilder().setFieldName("field2")
                    .setJsonPath("$.field2").setValueType(FieldValueType.FLOAT)
                    .setSchemaName(metadataSchema.getSchemaName()).build();
            field2 = client.createMetadataSchemaField(field2);
            System.out.println(MessageFormat.format("Created metadata schema field [{0}] in schema [{1}]",
                    field2.getFieldName(), field2.getSchemaName()));

            List<MetadataSchemaField> fields = client.getMetadataSchemaFields(metadataSchema.getSchemaName());
            System.out.println(MessageFormat.format("Found {0} fields for schema {1}", fields.size(),
                    metadataSchema.getSchemaName()));
            for (MetadataSchemaField field : fields) {
                System.out.println(MessageFormat.format("-> field {0}", field.getFieldName()));
            }

        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
