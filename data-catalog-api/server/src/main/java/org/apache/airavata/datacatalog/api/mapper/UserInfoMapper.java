package org.apache.airavata.datacatalog.api.mapper;

import org.apache.airavata.datacatalog.api.UserInfo;
import org.apache.airavata.datacatalog.api.model.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Map from {@link org.apache.airavata.datacatalog.api.model.UserEntity} to
 * {@link org.apache.airavata.datacatalog.api.UserInfo}. For the reverse, see
 * {@link org.apache.airavata.datacatalog.api.sharing.SharingManager#resolveUser(UserInfo)}
 */
@Component
public class UserInfoMapper {

    public void mapEntityToModel(UserEntity userEntity, UserInfo.Builder userInfoBuilder) {

        userInfoBuilder
                .setUserId(userEntity.getExternalId())
                .setTenantId(userEntity.getTenant().getExternalId());

    }
}
