package org.apache.airavata.datacatalog.api.repository.sharing.simple;

import java.util.Optional;

import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleTenantEntity;
import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleUserRepository extends JpaRepository<SimpleUserEntity, Long> {

    Optional<SimpleUserEntity> findByExternalIdAndSimpleTenant(String externalId, SimpleTenantEntity simpleTenant);

    Optional<SimpleUserEntity> findByExternalIdAndSimpleTenant_ExternalId(String externalId,
            String tenantId);
}
