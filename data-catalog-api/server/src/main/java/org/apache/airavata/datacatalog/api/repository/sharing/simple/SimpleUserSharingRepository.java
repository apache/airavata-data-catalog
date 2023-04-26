package org.apache.airavata.datacatalog.api.repository.sharing.simple;

import java.util.Optional;

import org.apache.airavata.datacatalog.api.Permission;
import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleUserSharingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleUserSharingRepository extends JpaRepository<SimpleUserSharingEntity, Long> {

    Optional<SimpleUserSharingEntity> findBySimpleUser_SimpleUserIdAndDataProduct_DataProductIdAndPermission(
            long userId, long dataProductId, Permission permission);
}
