package org.apache.airavata.datacatalog.api.sharing;

import org.apache.airavata.datacatalog.api.DataProduct;
import org.apache.airavata.datacatalog.api.GroupInfo;
import org.apache.airavata.datacatalog.api.Permission;
import org.apache.airavata.datacatalog.api.UserInfo;
import org.apache.airavata.datacatalog.api.model.UserEntity;
import org.apache.custos.sharing.core.exceptions.CustosSharingException;
import org.apache.custos.sharing.core.impl.SharingImpl;
import org.apache.custos.sharing.core.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SharingManagerImpl implements SharingManager {

    @Autowired
    SharingImpl custosSharingImpl;

    private static final String PUBLIC_ACCESS_GROUP = "public_access_group";


    @Override
    public UserEntity resolveUser(UserInfo userInfo) {
        return null;
    }

    @Override
    public boolean userHasAccess(UserInfo userInfo, DataProduct dataProduct, Permission permission) throws CustosSharingException {
        return custosSharingImpl.userHasAccess(userInfo.getTenantId(), dataProduct.getDataProductId(), permission.name(),
                userInfo.getUserId());
    }

    @Override
    public String getDataProductSharingView() {
        return null;
    }

    @Override
    public void grantPermissionToUser(UserInfo userInfo, DataProduct dataProduct, Permission permission) throws CustosSharingException {

        List<String> userIds = new ArrayList<>();
        userIds.add(userInfo.getUserId());
        custosSharingImpl.shareEntity(userInfo.getTenantId(),
                dataProduct.getDataProductId(), permission.name(), userIds, true, Constants.USER, null);
    }

    @Override
    public void revokePermissionFromUser(UserInfo userInfo, DataProduct dataProduct, Permission permission) throws CustosSharingException {

        List<String> userIds = new ArrayList<>();
        userIds.add(userInfo.getUserId());
        custosSharingImpl.revokePermission(userInfo.getTenantId(),
                dataProduct.getDataProductId(), permission.name(), userIds);

    }

    @Override
    public void grantPermissionToGroup(GroupInfo groupInfo, DataProduct dataProduct, Permission permission) throws CustosSharingException {

        List<String> userIds = new ArrayList<>();
        userIds.add(groupInfo.getGroupId());
        custosSharingImpl.shareEntity(groupInfo.getTenantId(),
                dataProduct.getDataProductId(), permission.name(), userIds, true, Constants.GROUP, null);
    }

    @Override
    public void revokePermissionFromGroup(GroupInfo groupInfo, DataProduct dataProduct, Permission permission) throws CustosSharingException {
        List<String> userIds = new ArrayList<>();
        userIds.add(groupInfo.getGroupId());
        custosSharingImpl.revokePermission(groupInfo.getTenantId(),
                dataProduct.getDataProductId(), permission.name(), userIds);
    }

    @Override
    public boolean hasPublicAccess(DataProduct dataProduct, Permission permission) throws CustosSharingException{
        return custosSharingImpl.userHasAccess(dataProduct.getTenantId(), dataProduct.getDataProductId(), permission.name(),
                PUBLIC_ACCESS_GROUP);
    }

    @Override
    public void grantPublicAccess(DataProduct dataProduct, Permission permission) throws CustosSharingException{

        //TODO: create PUBLIC GROUP If not exists
        List<String> userIds = new ArrayList<>();
        userIds.add(PUBLIC_ACCESS_GROUP);
        custosSharingImpl.shareEntity(dataProduct.getTenantId(),
                dataProduct.getDataProductId(), permission.name(), userIds, true, Constants.GROUP, null);

    }

    @Override
    public void revokePublicAccess(DataProduct dataProduct, Permission permission) throws CustosSharingException{
        List<String> userIds = new ArrayList<>();
        userIds.add(PUBLIC_ACCESS_GROUP);
        custosSharingImpl.revokePermission(dataProduct.getTenantId(),
                dataProduct.getDataProductId(), permission.name(), userIds);
    }
}
