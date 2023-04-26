package org.apache.airavata.datacatalog.api.repository;

import java.util.Optional;

import org.apache.airavata.datacatalog.api.model.TenantEntity;
import org.apache.airavata.datacatalog.api.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByExternalIdAndTenant(String externalId, TenantEntity tenantEntity);

    Optional<UserEntity> findByExternalIdAndTenant_ExternalId(String externalId, String tenantExternalId);
}
