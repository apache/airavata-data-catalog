package org.apache.airavata.datacatalog.api.sharing;

import java.util.ArrayList;
import java.util.List;

import org.apache.airavata.datacatalog.api.DataProduct;
import org.apache.airavata.datacatalog.api.GroupInfo;
import org.apache.airavata.datacatalog.api.Permission;
import org.apache.airavata.datacatalog.api.UserInfo;
import org.apache.airavata.datacatalog.api.exception.SharingException;
import org.apache.airavata.datacatalog.api.model.UserEntity;
import org.apache.custos.sharing.core.exceptions.CustosSharingException;
import org.apache.custos.sharing.core.impl.SharingImpl;
import org.apache.custos.sharing.core.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public boolean userHasAccess(UserInfo userInfo, DataProduct dataProduct, Permission permission)
            throws SharingException {
        try {
            return custosSharingImpl.userHasAccess(userInfo.getTenantId(), dataProduct.getDataProductId(),
                    permission.name(),
                    userInfo.getUserId());
        } catch (CustosSharingException e) {
            throw new SharingException(e);
        }
    }

    @Override
    public String getDataProductSharingView() {
        return null;
    }

    @Override
    public void grantPermissionToUser(UserInfo userInfo, DataProduct dataProduct, Permission permission)
            throws SharingException {

        List<String> userIds = new ArrayList<>();
        userIds.add(userInfo.getUserId());
        try {
            custosSharingImpl.shareEntity(userInfo.getTenantId(),
                    dataProduct.getDataProductId(), permission.name(), userIds, true, Constants.USER, null);
        } catch (CustosSharingException e) {
            throw new SharingException(e);
        }
    }

    @Override
    public void revokePermissionFromUser(UserInfo userInfo, DataProduct dataProduct, Permission permission)
            throws SharingException {

        List<String> userIds = new ArrayList<>();
        userIds.add(userInfo.getUserId());
        try {
            custosSharingImpl.revokePermission(userInfo.getTenantId(),
                    dataProduct.getDataProductId(), permission.name(), userIds);
        } catch (CustosSharingException e) {
            throw new SharingException(e);
        }

    }

    @Override
    public void grantPermissionToGroup(GroupInfo groupInfo, DataProduct dataProduct, Permission permission)
            throws SharingException {

        List<String> userIds = new ArrayList<>();
        userIds.add(groupInfo.getGroupId());
        try {
            custosSharingImpl.shareEntity(groupInfo.getTenantId(),
                    dataProduct.getDataProductId(), permission.name(), userIds, true, Constants.GROUP, null);
        } catch (CustosSharingException e) {
            throw new SharingException(e);
        }
    }

    @Override
    public void revokePermissionFromGroup(GroupInfo groupInfo, DataProduct dataProduct, Permission permission)
            throws SharingException {
        List<String> userIds = new ArrayList<>();
        userIds.add(groupInfo.getGroupId());
        try {
            custosSharingImpl.revokePermission(groupInfo.getTenantId(),
                    dataProduct.getDataProductId(), permission.name(), userIds);
        } catch (CustosSharingException e) {
            throw new SharingException(e);
        }
    }

    @Override
    public boolean hasPublicAccess(DataProduct dataProduct, Permission permission) throws SharingException {
        try {
            return custosSharingImpl.userHasAccess(dataProduct.getTenantId(), dataProduct.getDataProductId(),
                    permission.name(),
                    PUBLIC_ACCESS_GROUP);
        } catch (CustosSharingException e) {
            throw new SharingException(e);
        }
    }

    @Override
    public void grantPublicAccess(DataProduct dataProduct, Permission permission) throws SharingException {

        // TODO: create PUBLIC GROUP If not exists
        List<String> userIds = new ArrayList<>();
        userIds.add(PUBLIC_ACCESS_GROUP);
        try {
            custosSharingImpl.shareEntity(dataProduct.getTenantId(),
                    dataProduct.getDataProductId(), permission.name(), userIds, true, Constants.GROUP, null);
        } catch (CustosSharingException e) {
            throw new SharingException(e);
        }

    }

    @Override
    public void revokePublicAccess(DataProduct dataProduct, Permission permission) throws SharingException {
        List<String> userIds = new ArrayList<>();
        userIds.add(PUBLIC_ACCESS_GROUP);
        try {
            custosSharingImpl.revokePermission(dataProduct.getTenantId(),
                    dataProduct.getDataProductId(), permission.name(), userIds);
        } catch (CustosSharingException e) {
            throw new SharingException(e);
        }
    }
}
