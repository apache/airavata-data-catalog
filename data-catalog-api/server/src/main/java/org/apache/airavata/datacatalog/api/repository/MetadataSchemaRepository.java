package org.apache.airavata.datacatalog.api.repository;

import org.apache.airavata.datacatalog.api.model.MetadataSchemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetadataSchemaRepository extends JpaRepository<MetadataSchemaEntity, Long> {

    MetadataSchemaEntity findBySchemaName(String schemaName);
}
