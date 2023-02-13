package org.apache.airavata.datacatalog.api.mapper;

import org.apache.airavata.datacatalog.api.MetadataSchema;
import org.apache.airavata.datacatalog.api.model.MetadataSchemaEntity;
import org.springframework.stereotype.Component;

@Component
public class MetadataSchemaMapper {

    public void mapModelToEntity(MetadataSchema metadataSchema, MetadataSchemaEntity metadataSchemaEntity) {

        metadataSchemaEntity.setSchemaName(metadataSchema.getSchemaName());
    }

    public void mapEntityToModel(MetadataSchemaEntity metadataSchemaEntity,
            MetadataSchema.Builder metadataSchemaBuilder) {
        metadataSchemaBuilder.setSchemaName(metadataSchemaEntity.getSchemaName());
    }
}
