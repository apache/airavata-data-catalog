package org.apache.airavata.datacatalog.api.repository;

import java.util.List;

import org.apache.airavata.datacatalog.api.model.MetadataSchemaFieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetadataSchemaFieldRepository extends JpaRepository<MetadataSchemaFieldEntity, Long> {

    List<MetadataSchemaFieldEntity> findByMetadataSchema_SchemaName(String schemaName);

    MetadataSchemaFieldEntity findByFieldNameAndMetadataSchema_SchemaName(String fieldName, String schemaName);
}
