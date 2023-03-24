package org.apache.airavata.replicacatalog.catalogapi.repository;

import org.apache.airavata.replicacatalog.catalogapi.model.DataProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface DataProductRepository extends JpaRepository<DataProductEntity, String> {

    DataProductEntity findByProductUri(String productUri);

    @Transactional
    void deleteByProductUri(String productUri);

}
