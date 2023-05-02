package org.apache.airavata.datacatalog.api.repository.sharing.simple;

import java.util.Optional;

import org.apache.airavata.datacatalog.api.Permission;
import org.apache.airavata.datacatalog.api.model.sharing.simple.SimplePublicSharingEntity;
import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleTenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimplePublicSharingRepository extends JpaRepository<SimplePublicSharingEntity, Long> {

    Optional<SimplePublicSharingEntity> findBySimpleTenantAndDataProduct_DataProductIdAndPermission(
            SimpleTenantEntity simpleTenant, Long dataProductId, Permission permission);

}
