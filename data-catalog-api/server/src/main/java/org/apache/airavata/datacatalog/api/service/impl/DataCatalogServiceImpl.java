package org.apache.airavata.datacatalog.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.airavata.datacatalog.api.DataProduct;
import org.apache.airavata.datacatalog.api.MetadataSchema;
import org.apache.airavata.datacatalog.api.MetadataSchemaField;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse;
import org.apache.airavata.datacatalog.api.exception.EntityNotFoundException;
import org.apache.airavata.datacatalog.api.mapper.DataProductMapper;
import org.apache.airavata.datacatalog.api.mapper.MetadataSchemaFieldMapper;
import org.apache.airavata.datacatalog.api.mapper.MetadataSchemaMapper;
import org.apache.airavata.datacatalog.api.model.DataProductEntity;
import org.apache.airavata.datacatalog.api.model.MetadataSchemaEntity;
import org.apache.airavata.datacatalog.api.model.MetadataSchemaFieldEntity;
import org.apache.airavata.datacatalog.api.repository.DataProductRepository;
import org.apache.airavata.datacatalog.api.repository.MetadataSchemaFieldRepository;
import org.apache.airavata.datacatalog.api.repository.MetadataSchemaRepository;
import org.apache.airavata.datacatalog.api.service.DataCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DataCatalogServiceImpl implements DataCatalogService {

    // Repositories
    @Autowired
    DataProductRepository dataProductRepository;

    @Autowired
    MetadataSchemaRepository metadataSchemaRepository;

    @Autowired
    MetadataSchemaFieldRepository metadataSchemaFieldRepository;

    // Mappers
    @Autowired
    DataProductMapper dataProductMapper;

    @Autowired
    MetadataSchemaMapper metadataSchemaMapper;

    @Autowired
    MetadataSchemaFieldMapper metadataSchemaFieldMapper;

    @Override
    public DataProduct createDataProduct(DataProduct dataProduct) {

        // TODO: SharingManager.resolveUser
        DataProductEntity dataProductEntity = new DataProductEntity();
        dataProductEntity.setExternalId(UUID.randomUUID().toString());
        dataProductMapper.mapModelToEntity(dataProduct, dataProductEntity);
        DataProductEntity savedDataProductEntity = dataProductRepository.save(dataProductEntity);

        // TODO: SharingManager.grantPermissionToUser(userInfo, dataProduct,
        // Permission.OWNER)

        return toDataProduct(savedDataProductEntity);
    }

    @Override
    public DataProduct updateDataProduct(DataProduct dataProduct) {

        DataProductEntity dataProductEntity = findDataProductEntity(dataProduct.getDataProductId());
        dataProductMapper.mapModelToEntity(dataProduct, dataProductEntity);

        DataProductEntity savedDataProductEntity = dataProductRepository.save(dataProductEntity);

        return toDataProduct(savedDataProductEntity);
    }

    @Override
    public DataProduct getDataProduct(String dataProductId) {
        DataProductEntity dataProductEntity = findDataProductEntity(dataProductId);
        return toDataProduct(dataProductEntity);
    }

    @Override
    public void deleteDataProduct(String dataProductId) {
        dataProductRepository.deleteByExternalId(dataProductId);
    }

    @Override
    public DataProduct addDataProductToMetadataSchema(String dataProductId, String schemaName) {
        DataProductEntity dataProduct = findDataProductEntity(dataProductId);
        // TODO: handle metadata schema not found
        MetadataSchemaEntity metadataSchemaEntity = metadataSchemaRepository.findBySchemaName(schemaName);
        dataProduct.addMetadataSchema(metadataSchemaEntity);
        DataProductEntity savedDataProductEntity = dataProductRepository.save(dataProduct);
        return toDataProduct(savedDataProductEntity);
    }

    @Override
    public MetadataSchema getMetadataSchema(String schemaName) {
        MetadataSchemaEntity metadataSchemaEntity = metadataSchemaRepository.findBySchemaName(schemaName);
        if (metadataSchemaEntity == null) {
            throw new EntityNotFoundException("No schema found with name " + schemaName);
        }
        return toMetadataSchema(metadataSchemaEntity);
    }

    @Override
    public MetadataSchema createMetadataSchema(MetadataSchema metadataSchema) {

        MetadataSchemaEntity metadataSchemaEntity = new MetadataSchemaEntity();
        metadataSchemaMapper.mapModelToEntity(metadataSchema, metadataSchemaEntity);
        MetadataSchemaEntity savedMetadataSchemaEntity = metadataSchemaRepository.save(metadataSchemaEntity);
        return toMetadataSchema(savedMetadataSchemaEntity);
    }

    @Override
    public MetadataSchemaField getMetadataSchemaField(String schemaName, String fieldName) {
        MetadataSchemaFieldEntity metadataSchemaFieldEntity = metadataSchemaFieldRepository
                .findByFieldNameAndMetadataSchema_SchemaName(fieldName, schemaName);
        if (metadataSchemaFieldEntity == null) {
            throw new EntityNotFoundException("No field found in schema " + schemaName + " with name " + fieldName);
        }
        return toMetadataSchemaField(metadataSchemaFieldEntity);
    }

    @Override
    public MetadataSchemaField createMetadataSchemaField(MetadataSchemaField metadataSchemaField) {

        MetadataSchemaFieldEntity metadataSchemaFieldEntity = new MetadataSchemaFieldEntity();
        metadataSchemaFieldMapper.mapModelToEntity(metadataSchemaField, metadataSchemaFieldEntity);
        MetadataSchemaFieldEntity savedMetadataSchemaFieldEntity = metadataSchemaFieldRepository
                .save(metadataSchemaFieldEntity);
        return toMetadataSchemaField(savedMetadataSchemaFieldEntity);
    }

    @Override
    public void deleteMetadataSchema(MetadataSchema metadataSchema) {
        // TODO: check that user has write access on metadata schema
        // TODO: handle metadata schema not found
        MetadataSchemaEntity metadataSchemaEntity = metadataSchemaRepository
                .findBySchemaName(metadataSchema.getSchemaName());
        metadataSchemaFieldRepository.deleteAll(metadataSchemaEntity.getMetadataSchemaFields());
        metadataSchemaRepository.delete(metadataSchemaEntity);
    }

    @Override
    public void deleteMetadataSchemaField(MetadataSchemaField metadataSchemaField) {
        // TODO: handle metadata schema field not found
        MetadataSchemaFieldEntity metadataSchemaFieldEntity = metadataSchemaFieldRepository
                .findByFieldNameAndMetadataSchema_SchemaName(metadataSchemaField.getFieldName(),
                        metadataSchemaField.getSchemaName());
        metadataSchemaFieldRepository.delete(metadataSchemaFieldEntity);
    }

    @Override
    public List<MetadataSchemaField> getMetadataSchemaFields(String schemaName) {

        // TODO: handle case where schema doesn't exist
        List<MetadataSchemaFieldEntity> metadataSchemaFieldEntities = metadataSchemaFieldRepository
                .findByMetadataSchema_SchemaName(schemaName);

        List<MetadataSchemaField> fields = new ArrayList<>();
        MetadataSchemaFieldListResponse.Builder responseBuilder = MetadataSchemaFieldListResponse.newBuilder();
        for (MetadataSchemaFieldEntity metadataSchemaFieldEntity : metadataSchemaFieldEntities) {
            fields.add(toMetadataSchemaField(metadataSchemaFieldEntity));
        }
        return fields;
    }

    @Override
    public DataProduct removeDataProductFromMetadataSchema(String dataProductId, String schemaName) {

        DataProductEntity dataProduct = findDataProductEntity(dataProductId);
        // TODO: handle metadata schema not found
        MetadataSchemaEntity metadataSchemaEntity = metadataSchemaRepository.findBySchemaName(schemaName);
        dataProduct.removeMetadataSchema(metadataSchemaEntity);
        DataProductEntity savedDataProductEntity = dataProductRepository.save(dataProduct);
        return toDataProduct(savedDataProductEntity);
    }

    @Override
    public MetadataSchemaField updateMetadataSchemaField(MetadataSchemaField metadataSchemaField) {

        // TODO: handle metadata schema field not found
        MetadataSchemaFieldEntity metadataSchemaFieldEntity = metadataSchemaFieldRepository
                .findByFieldNameAndMetadataSchema_SchemaName(metadataSchemaField.getFieldName(),
                        metadataSchemaField.getSchemaName());
        metadataSchemaFieldMapper.mapModelToEntity(metadataSchemaField, metadataSchemaFieldEntity);
        MetadataSchemaFieldEntity savedMetadataSchemaFieldEntity = metadataSchemaFieldRepository
                .save(metadataSchemaFieldEntity);
        return toMetadataSchemaField(savedMetadataSchemaFieldEntity);
    }

    private DataProduct toDataProduct(DataProductEntity savedDataProductEntity) {
        DataProduct.Builder builder = DataProduct.newBuilder();
        dataProductMapper.mapEntityToModel(savedDataProductEntity, builder);
        return builder.build();
    }

    private MetadataSchema toMetadataSchema(MetadataSchemaEntity metadataSchemaEntity) {
        MetadataSchema.Builder builder = MetadataSchema.newBuilder();
        metadataSchemaMapper.mapEntityToModel(metadataSchemaEntity, builder);
        return builder.build();
    }

    private MetadataSchemaField toMetadataSchemaField(MetadataSchemaFieldEntity metadataSchemaFieldEntity) {
        MetadataSchemaField.Builder builder = MetadataSchemaField.newBuilder();
        metadataSchemaFieldMapper.mapEntityToModel(metadataSchemaFieldEntity, builder);
        return builder.build();
    }

    private DataProductEntity findDataProductEntity(String dataProductId) {
        return dataProductRepository
                .findByExternalId(dataProductId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find a data product with the ID: " + dataProductId));
    }

}
