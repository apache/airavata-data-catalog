package org.apache.airavata.datacatalog.api.repository;

import java.util.Optional;

import org.apache.airavata.datacatalog.api.model.GroupEntity;
import org.apache.airavata.datacatalog.api.model.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// TODO: not needed?
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    Optional<GroupEntity> findByExternalIdAndTenant(String externalId, TenantEntity tenantEntity);
}
