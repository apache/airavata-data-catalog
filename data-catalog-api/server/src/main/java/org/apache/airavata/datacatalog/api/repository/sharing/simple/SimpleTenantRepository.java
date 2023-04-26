package org.apache.airavata.datacatalog.api.repository.sharing.simple;

import java.util.Optional;

import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleTenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleTenantRepository extends JpaRepository<SimpleTenantEntity, Long> {

    Optional<SimpleTenantEntity> findByExternalId(String externalId);
}
