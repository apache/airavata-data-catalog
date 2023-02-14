package org.apache.airavata.datacatalog.api.sharing;

import org.apache.airavata.datacatalog.api.DataProduct;
import org.apache.airavata.datacatalog.api.GroupInfo;
import org.apache.airavata.datacatalog.api.Permission;
import org.apache.airavata.datacatalog.api.UserInfo;
import org.apache.airavata.datacatalog.api.model.UserEntity;

import org.apache.custos.sharing.core.impl.SharingImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SharingManagerImpl  implements SharingManager {

    @Autowired
    SharingImpl custosSharingImpl;


    @Override
    public UserEntity resolveUser(UserInfo userInfo) {
        return null;
    }

    @Override
    public boolean userHasAccess(UserInfo userInfo, DataProduct dataProduct, Permission permission) {
        return false;
    }

    @Override
    public String getDataProductSharingView() {
        return null;
    }

    @Override
    public void grantPermissionToUser(UserInfo userInfo, DataProduct dataProduct, Permission permission) {

    }

    @Override
    public void revokePermissionFromUser(UserInfo userInfo, DataProduct dataProduct, Permission permission) {

    }

    @Override
    public void grantPermissionToGroup(GroupInfo groupInfo, DataProduct dataProduct, Permission permission) {

    }

    @Override
    public void revokePermissionFromGroup(GroupInfo groupInfo, DataProduct dataProduct, Permission permission) {

    }

    @Override
    public boolean hasPublicAccess(DataProduct dataProduct, Permission permission) {
        return false;
    }

    @Override
    public void grantPublicAccess(DataProduct dataProduct, Permission permission) {

    }

    @Override
    public void revokePublicAccess(DataProduct dataProduct, Permission permission) {

    }
}
