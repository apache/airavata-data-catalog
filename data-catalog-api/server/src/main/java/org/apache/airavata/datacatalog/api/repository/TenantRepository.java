package org.apache.airavata.datacatalog.api.repository;

import org.apache.airavata.datacatalog.api.model.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TenantRepository extends JpaRepository<TenantEntity, Long> {

}
