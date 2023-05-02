package org.apache.airavata.datacatalog.api.mapper;

import org.apache.airavata.datacatalog.api.MetadataSchemaField;
import org.apache.airavata.datacatalog.api.model.MetadataSchemaEntity;
import org.apache.airavata.datacatalog.api.model.MetadataSchemaFieldEntity;
import org.apache.airavata.datacatalog.api.repository.MetadataSchemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetadataSchemaFieldMapper {

    @Autowired
    MetadataSchemaRepository metadataSchemaRepository;

    public void mapModelToEntity(MetadataSchemaField metadataSchemaField,
            MetadataSchemaFieldEntity metadataSchemaFieldEntity) {

        metadataSchemaFieldEntity.setFieldName(metadataSchemaField.getFieldName());
        metadataSchemaFieldEntity.setFieldValueType(metadataSchemaField.getValueType());
        metadataSchemaFieldEntity.setJsonPath(metadataSchemaField.getJsonPath());

        MetadataSchemaEntity metadataSchemaEntity = metadataSchemaRepository
                .findBySchemaName(metadataSchemaField.getSchemaName());
        metadataSchemaFieldEntity.setMetadataSchema(metadataSchemaEntity);
    }

    public void mapEntityToModel(MetadataSchemaFieldEntity metadataSchemaFieldEntity,
            MetadataSchemaField.Builder metadataSchemaFieldBuilder) {

        metadataSchemaFieldBuilder.setFieldName(metadataSchemaFieldEntity.getFieldName());
        metadataSchemaFieldBuilder.setJsonPath(metadataSchemaFieldEntity.getJsonPath());
        metadataSchemaFieldBuilder.setValueType(metadataSchemaFieldEntity.getFieldValueType());
        metadataSchemaFieldBuilder.setSchemaName(metadataSchemaFieldEntity.getMetadataSchema().getSchemaName());
    }
}
