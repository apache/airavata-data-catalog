package org.apache.airavata.datacatalog.api.repository;

import org.apache.airavata.datacatalog.api.model.DataProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface DataProductRepository extends JpaRepository<DataProductEntity, Long> {

    Optional<DataProductEntity> findByExternalId(String externalId);

    @Transactional
    void deleteByExternalId(String externalId);

}
