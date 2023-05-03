package org.apache.airavata.datacatalog.api.repository.sharing.simple;

import java.util.Optional;

import org.apache.airavata.datacatalog.api.Permission;
import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleGroupSharingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleGroupSharingRepository extends JpaRepository<SimpleGroupSharingEntity, Long> {

    Optional<SimpleGroupSharingEntity> findBySimpleGroup_SimpleGroupIdAndDataProduct_DataProductIdAndPermission(
            long groupId, long dataProductId, Permission permission);
}
