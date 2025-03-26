package org.apache.airavata.datacatalog.api.service;

import java.util.List;

import org.apache.airavata.datacatalog.api.DataProduct;
import org.apache.airavata.datacatalog.api.MetadataSchema;
import org.apache.airavata.datacatalog.api.MetadataSchemaField;
import org.apache.airavata.datacatalog.api.UserInfo;
import org.apache.airavata.datacatalog.api.exception.MetadataSchemaSqlParseException;
import org.apache.airavata.datacatalog.api.exception.MetadataSchemaSqlValidateException;
import org.apache.airavata.datacatalog.api.query.MetadataSchemaQueryResult;
import org.apache.airavata.datacatalog.api.sharing.exception.SharingException;

/**
 * Transactional service layer for CRUD operations on data catalog database.
 */
public interface DataCatalogService {

    DataProduct createDataProduct(DataProduct dataProduct) throws SharingException;

    DataProduct updateDataProduct(DataProduct dataProduct);

    DataProduct getDataProduct(String dataProductId);

    void deleteDataProduct(String dataProductId);

    DataProduct addDataProductToMetadataSchema(String dataProductId, String schemaName);

    MetadataSchema getMetadataSchema(String schemaName);

    List<MetadataSchema> getMetadataSchemas();

    MetadataSchema createMetadataSchema(MetadataSchema metadataSchema);

    MetadataSchemaField getMetadataSchemaField(String schemaName, String fieldName);

    MetadataSchemaField createMetadataSchemaField(MetadataSchemaField metadataSchemaField);

    void deleteMetadataSchema(MetadataSchema metadataSchema);

    void deleteMetadataSchemaField(MetadataSchemaField metadataSchemaField);

    List<MetadataSchemaField> getMetadataSchemaFields(String schemaName);

    DataProduct removeDataProductFromMetadataSchema(String dataProductId, String schemaName);

    MetadataSchemaField updateMetadataSchemaField(MetadataSchemaField metadataSchemaField);

    MetadataSchemaQueryResult searchDataProducts(UserInfo userInfo, String sql, int page, int pageSize)
            throws MetadataSchemaSqlParseException, MetadataSchemaSqlValidateException;
}
