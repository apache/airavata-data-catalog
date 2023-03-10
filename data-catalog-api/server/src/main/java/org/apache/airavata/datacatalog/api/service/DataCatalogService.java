package org.apache.airavata.datacatalog.api.service;

import java.util.List;

import org.apache.airavata.datacatalog.api.DataProduct;
import org.apache.airavata.datacatalog.api.MetadataSchema;
import org.apache.airavata.datacatalog.api.MetadataSchemaField;

/**
 * Transactional service layer for CRUD operations on data catalog database.
 */
public interface DataCatalogService {

    DataProduct createDataProduct(DataProduct dataProduct);

    DataProduct updateDataProduct(DataProduct dataProduct);

    DataProduct getDataProduct(String dataProductId);

    void deleteDataProduct(String dataProductId);

    DataProduct addDataProductToMetadataSchema(String dataProductId, String schemaName);

    MetadataSchema getMetadataSchema(String schemaName);

    MetadataSchema createMetadataSchema(MetadataSchema metadataSchema);

    MetadataSchemaField getMetadataSchemaField(String schemaName, String fieldName);

    MetadataSchemaField createMetadataSchemaField(MetadataSchemaField metadataSchemaField);

    void deleteMetadataSchema(MetadataSchema metadataSchema);

    void deleteMetadataSchemaField(MetadataSchemaField metadataSchemaField);

    List<MetadataSchemaField> getMetadataSchemaFields(String schemaName);

    DataProduct removeDataProductFromMetadataSchema(String dataProductId, String schemaName);

    MetadataSchemaField updateMetadataSchemaField(MetadataSchemaField metadataSchemaField);
}
