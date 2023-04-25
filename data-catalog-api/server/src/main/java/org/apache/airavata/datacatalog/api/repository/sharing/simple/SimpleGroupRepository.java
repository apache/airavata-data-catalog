package org.apache.airavata.datacatalog.api.repository.sharing.simple;

import java.util.Optional;

import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleGroupEntity;
import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleTenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleGroupRepository extends JpaRepository<SimpleGroupEntity, Long> {

    Optional<SimpleGroupEntity> findByExternalIdAndSimpleTenant(String externalId, SimpleTenantEntity simpleTenant);
}
