package org.apache.airavata.datacatalog.api.sharing;

import org.apache.airavata.datacatalog.api.DataProduct;
import org.apache.airavata.datacatalog.api.GroupInfo;
import org.apache.airavata.datacatalog.api.Permission;
import org.apache.airavata.datacatalog.api.UserInfo;
import org.apache.airavata.datacatalog.api.model.UserEntity;
import org.apache.custos.sharing.core.exceptions.CustosSharingException;

public interface SharingManager {

    /**
     * Get or create a {@link UserEntity}.
     *
     * @param userInfo
     * @return
     */
    UserEntity resolveUser(UserInfo userInfo);

    /**
     * Return true if the user has access to the data product with the given
     * permission.
     *
     * @param userInfo
     * @param dataProduct
     * @param permission
     * @return
     */
    boolean userHasAccess(UserInfo userInfo, DataProduct dataProduct, Permission permission) throws Exception;

    /**
     * Return the name of the database view that includes sharing information
     * for each data product. The view should contain the following columns:
     * data_product_id, user_id, and permission_id where the permission_id
     * should be a number as defined in the {@link Permission} enum.
     *
     * @return
     */
    String getDataProductSharingView();

    /**
     * Grant permission to the user for the given data product.
     *
     * @param userInfo
     * @param dataProduct
     * @param permission
     */
    void grantPermissionToUser(UserInfo userInfo, DataProduct dataProduct, Permission permission) throws Exception;

    /**
     * Revoke permission from the user for the given data product.
     *
     * @param userInfo
     * @param dataProduct
     * @param permission
     */
    void revokePermissionFromUser(UserInfo userInfo, DataProduct dataProduct, Permission permission) throws Exception;

    /**
     * Grant permission to the group for the given data product.
     *
     * @param groupInfo
     * @param dataProduct
     * @param permission
     */
    void grantPermissionToGroup(GroupInfo groupInfo, DataProduct dataProduct, Permission permission) throws Exception;

    /**
     * Revoke permission from the group for the given data product.
     *
     * @param groupInfo
     * @param dataProduct
     * @param permission
     */
    void revokePermissionFromGroup(GroupInfo groupInfo, DataProduct dataProduct, Permission permission) throws Exception;

    /**
     * Return true if public access at the given permission is granted for the
     * given data product. Public access means anonymous access; no user information
     * provided in the API request.
     *
     * @param dataProduct
     * @param permission
     * @return
     */
    boolean hasPublicAccess(DataProduct dataProduct, Permission permission) throws Exception;

    /**
     * Grant public access to the given data product.
     *
     * @param dataProduct
     * @param permission
     */
    void grantPublicAccess(DataProduct dataProduct, Permission permission) throws Exception;

    /**
     * Revoke public access from the given data product.
     *
     * @param dataProduct
     * @param permission
     */
    void revokePublicAccess(DataProduct dataProduct, Permission permission) throws Exception;
}
