package org.apache.airavata.datacatalog.api.repository;

import org.apache.airavata.datacatalog.api.model.DataProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataProductRepository extends JpaRepository<DataProductEntity, Long> {

    DataProductEntity findByExternalId(String parentDataProductId);

}
