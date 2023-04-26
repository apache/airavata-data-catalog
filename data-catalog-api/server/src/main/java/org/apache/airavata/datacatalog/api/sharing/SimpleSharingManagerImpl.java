package org.apache.airavata.datacatalog.api.sharing;

import java.util.Arrays;
import java.util.Optional;

import org.apache.airavata.datacatalog.api.DataProduct;
import org.apache.airavata.datacatalog.api.GroupInfo;
import org.apache.airavata.datacatalog.api.Permission;
import org.apache.airavata.datacatalog.api.UserInfo;
import org.apache.airavata.datacatalog.api.exception.SharingException;
import org.apache.airavata.datacatalog.api.model.DataProductEntity;
import org.apache.airavata.datacatalog.api.model.TenantEntity;
import org.apache.airavata.datacatalog.api.model.UserEntity;
import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleGroupEntity;
import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleGroupSharingEntity;
import org.apache.airavata.datacatalog.api.model.sharing.simple.SimplePublicSharingEntity;
import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleTenantEntity;
import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleUserEntity;
import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleUserSharingEntity;
import org.apache.airavata.datacatalog.api.repository.DataProductRepository;
import org.apache.airavata.datacatalog.api.repository.TenantRepository;
import org.apache.airavata.datacatalog.api.repository.UserRepository;
import org.apache.airavata.datacatalog.api.repository.sharing.simple.SimpleGroupRepository;
import org.apache.airavata.datacatalog.api.repository.sharing.simple.SimpleGroupSharingRepository;
import org.apache.airavata.datacatalog.api.repository.sharing.simple.SimplePublicSharingRepository;
import org.apache.airavata.datacatalog.api.repository.sharing.simple.SimpleTenantRepository;
import org.apache.airavata.datacatalog.api.repository.sharing.simple.SimpleUserRepository;
import org.apache.airavata.datacatalog.api.repository.sharing.simple.SimpleUserSharingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Component("simpleSharingManager")
public class SimpleSharingManagerImpl implements SharingManager {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataProductRepository dataProductRepository;

    @Autowired
    private SimpleUserSharingRepository simpleUserSharingRepository;

    @Autowired
    private SimpleGroupSharingRepository simpleGroupSharingRepository;

    @Autowired
    private SimplePublicSharingRepository simplePublicSharingRepository;

    @Autowired
    private SimpleTenantRepository simpleTenantRepository;

    @Autowired
    private SimpleUserRepository simpleUserRepository;

    @Autowired
    private SimpleGroupRepository simpleGroupRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(String tenantId) throws SharingException {
        // Nothing to do
    }

    @Override
    public UserEntity resolveUser(UserInfo userInfo) throws SharingException {
        SimpleUserEntity simpleUser = resolveSimpleUser(userInfo);
        return simpleUser.getUser();
    }

    @Override
    public boolean userHasAccess(UserInfo userInfo, DataProduct dataProduct, Permission permission)
            throws SharingException {
        UserEntity user = resolveUser(userInfo);
        DataProductEntity dataProductEntity = resolveDataProduct(dataProduct);
        Query query = entityManager.createNativeQuery("select 1 from " + getDataProductSharingView()
                + " where user_id = :user_id and data_product_id = :data_product_id and permission_id in :permission_id");
        query.setParameter("user_id", user.getUserId());
        query.setParameter("data_product_id", dataProductEntity.getDataProductId());
        query.setParameter("permission_id", Arrays.asList(permission.getNumber(), Permission.OWNER.getNumber()));

        return query.getResultList().size() > 0;
    }

    @Override
    public String getDataProductSharingView() {
        return "simple_data_product_sharing_view";
    }

    @Override
    public void grantPermissionToUser(UserInfo userInfo, DataProduct dataProduct, Permission permission,
            UserInfo sharedByUser) throws SharingException {
        SimpleUserEntity simpleUser = resolveSimpleUser(userInfo);
        DataProductEntity dataProductEntity = resolveDataProduct(dataProduct);
        SimpleUserEntity sharedByUserEntity = sharedByUser != null ? resolveSimpleUser(sharedByUser) : null;

        Optional<SimpleUserSharingEntity> maybeSimpleUserSharingEntity = simpleUserSharingRepository
                .findBySimpleUser_SimpleUserIdAndDataProduct_DataProductIdAndPermission(simpleUser.getSimpleUserId(),
                        dataProductEntity.getDataProductId(), permission);

        if (maybeSimpleUserSharingEntity.isEmpty()) {
            SimpleUserSharingEntity simpleUserSharingEntity = new SimpleUserSharingEntity();
            simpleUserSharingEntity.setDataProduct(dataProductEntity);
            simpleUserSharingEntity.setPermission(permission);
            simpleUserSharingEntity.setSimpleUser(simpleUser);
            simpleUserSharingEntity.setSharedByUser(sharedByUserEntity);
            simpleUserSharingRepository.save(simpleUserSharingEntity);
        }
    }

    @Override
    public void revokePermissionFromUser(UserInfo userInfo, DataProduct dataProduct, Permission permission)
            throws SharingException {

        SimpleUserEntity simpleUser = resolveSimpleUser(userInfo);
        DataProductEntity dataProductEntity = resolveDataProduct(dataProduct);

        Optional<SimpleUserSharingEntity> maybeSimpleUserSharingEntity = simpleUserSharingRepository
                .findBySimpleUser_SimpleUserIdAndDataProduct_DataProductIdAndPermission(simpleUser.getSimpleUserId(),
                        dataProductEntity.getDataProductId(), permission);
        maybeSimpleUserSharingEntity.ifPresent(simpleUserSharingEntity -> {
            simpleUserSharingRepository.delete(simpleUserSharingEntity);
        });
    }

    @Override
    public void grantPermissionToGroup(GroupInfo groupInfo, DataProduct dataProduct, Permission permission,
            UserInfo sharedByUser) throws SharingException {

        SimpleGroupEntity groupEntity = resolveGroup(groupInfo);
        DataProductEntity dataProductEntity = resolveDataProduct(dataProduct);
        SimpleUserEntity sharedByUserEntity = sharedByUser != null
                ? resolveSimpleUser(sharedByUser, groupEntity.getSimpleTenant())
                : null;

        Optional<SimpleGroupSharingEntity> maybeSimpleGroupSharingEntity = simpleGroupSharingRepository
                .findBySimpleGroup_SimpleGroupIdAndDataProduct_DataProductIdAndPermission(
                        groupEntity.getSimpleGroupId(),
                        dataProductEntity.getDataProductId(), permission);

        if (maybeSimpleGroupSharingEntity.isEmpty()) {
            SimpleGroupSharingEntity simpleGroupSharingEntity = new SimpleGroupSharingEntity();
            simpleGroupSharingEntity.setDataProduct(dataProductEntity);
            simpleGroupSharingEntity.setPermission(permission);
            simpleGroupSharingEntity.setSimpleGroup(groupEntity);
            simpleGroupSharingEntity.setSharedByUser(sharedByUserEntity);
            simpleGroupSharingRepository.save(simpleGroupSharingEntity);
        }
    }

    @Override
    public void revokePermissionFromGroup(GroupInfo groupInfo, DataProduct dataProduct, Permission permission)
            throws SharingException {
        SimpleGroupEntity groupEntity = resolveGroup(groupInfo);
        DataProductEntity dataProductEntity = resolveDataProduct(dataProduct);

        Optional<SimpleGroupSharingEntity> maybeSimpleGroupSharingEntity = simpleGroupSharingRepository
                .findBySimpleGroup_SimpleGroupIdAndDataProduct_DataProductIdAndPermission(
                        groupEntity.getSimpleGroupId(), dataProductEntity.getDataProductId(), permission);
        maybeSimpleGroupSharingEntity.ifPresent(simpleUserSharingEntity -> {
            simpleGroupSharingRepository.delete(simpleUserSharingEntity);
        });
    }

    @Override
    public boolean hasPublicAccess(DataProduct dataProduct, Permission permission) throws SharingException {
        DataProductEntity dataProductEntity = resolveDataProduct(dataProduct);
        SimpleUserEntity simpleUser = simpleUserRepository.findByUser(dataProductEntity.getOwner());
        SimpleTenantEntity simpleTenant = simpleUser.getSimpleTenant();

        Optional<SimplePublicSharingEntity> maybeSimplePublicSharingEntity = simplePublicSharingRepository
                .findBySimpleTenantAndDataProduct_DataProductIdAndPermission(simpleTenant,
                        dataProductEntity.getDataProductId(), permission);
        return maybeSimplePublicSharingEntity.isPresent();
    }

    @Override
    public void grantPublicAccess(DataProduct dataProduct, Permission permission) throws SharingException {

        DataProductEntity dataProductEntity = resolveDataProduct(dataProduct);
        SimpleUserEntity simpleUser = simpleUserRepository.findByUser(dataProductEntity.getOwner());
        SimpleTenantEntity simpleTenant = simpleUser.getSimpleTenant();

        Optional<SimplePublicSharingEntity> maybeSimplePublicSharingEntity = simplePublicSharingRepository
                .findBySimpleTenantAndDataProduct_DataProductIdAndPermission(simpleTenant,
                        dataProductEntity.getDataProductId(), permission);

        if (maybeSimplePublicSharingEntity.isEmpty()) {
            SimplePublicSharingEntity simplePublicSharingEntity = new SimplePublicSharingEntity();
            simplePublicSharingEntity.setDataProduct(dataProductEntity);
            simplePublicSharingEntity.setPermission(permission);
            simplePublicSharingEntity.setSimpleTenant(simpleTenant);
            simplePublicSharingRepository.save(simplePublicSharingEntity);
        }
    }

    @Override
    public void revokePublicAccess(DataProduct dataProduct, Permission permission) throws SharingException {
        DataProductEntity dataProductEntity = resolveDataProduct(dataProduct);
        SimpleUserEntity simpleUser = simpleUserRepository.findByUser(dataProductEntity.getOwner());
        SimpleTenantEntity simpleTenant = simpleUser.getSimpleTenant();

        Optional<SimplePublicSharingEntity> maybeSimplePublicSharingEntity = simplePublicSharingRepository
                .findBySimpleTenantAndDataProduct_DataProductIdAndPermission(simpleTenant,
                        dataProductEntity.getDataProductId(), permission);

        maybeSimplePublicSharingEntity.ifPresent(simplePublicSharingEntity -> {
            simplePublicSharingRepository.delete(simplePublicSharingEntity);
        });
    }

    private SimpleGroupEntity resolveGroup(GroupInfo groupInfo) {

        final String tenantId = groupInfo.hasTenantId() ? groupInfo.getTenantId() : "default";
        SimpleTenantEntity tenant = resolveTenant(tenantId);

        // Create the group if missing
        Optional<SimpleGroupEntity> maybeSimpleGroup = simpleGroupRepository
                .findByExternalIdAndSimpleTenant(groupInfo.getGroupId(), tenant);
        SimpleGroupEntity simpleGroup = maybeSimpleGroup.orElseGet(() -> {
            SimpleGroupEntity newGroup = new SimpleGroupEntity();
            newGroup.setExternalId(groupInfo.getGroupId());
            newGroup.setName(groupInfo.getGroupId());
            newGroup.setSimpleTenant(tenant);
            return simpleGroupRepository.save(newGroup);
        });

        return simpleGroup;
    }

    private SimpleTenantEntity resolveTenant(String tenantId) {

        Optional<SimpleTenantEntity> maybeSimpleTenant = simpleTenantRepository.findByExternalId(tenantId);
        return maybeSimpleTenant.orElseGet(() -> {
            TenantEntity newTenant = new TenantEntity();
            newTenant.setExternalId(tenantId);
            newTenant.setName(tenantId);
            newTenant = tenantRepository.save(newTenant);

            SimpleTenantEntity newSimpleTenant = new SimpleTenantEntity();
            newSimpleTenant.setExternalId(tenantId);
            newSimpleTenant.setName(tenantId);
            newSimpleTenant.setTenant(newTenant);
            return simpleTenantRepository.save(newSimpleTenant);
        });
    }

    private SimpleUserEntity resolveSimpleUser(UserInfo userInfo) {

        final String tenantId = userInfo.hasTenantId() ? userInfo.getTenantId() : "default";
        SimpleTenantEntity tenant = resolveTenant(tenantId);

        return resolveSimpleUser(userInfo, tenant);
    }

    private SimpleUserEntity resolveSimpleUser(UserInfo userInfo, SimpleTenantEntity tenant) {
        Optional<SimpleUserEntity> maybeSimpleUser = simpleUserRepository
                .findByExternalIdAndSimpleTenant(userInfo.getUserId(), tenant);

        SimpleUserEntity simpleUser = maybeSimpleUser.orElseGet(() -> {
            UserEntity newUser = new UserEntity();
            newUser.setExternalId(userInfo.getUserId());
            newUser.setName(userInfo.getUserId());
            newUser.setTenant(tenant.getTenant());
            newUser = userRepository.save(newUser);

            SimpleUserEntity newSimpleUser = new SimpleUserEntity();
            newSimpleUser.setExternalId(userInfo.getUserId());
            newSimpleUser.setName(userInfo.getUserId());
            newSimpleUser.setSimpleTenant(tenant);
            newSimpleUser.setUser(newUser);
            return simpleUserRepository.save(newSimpleUser);
        });
        return simpleUser;
    }

    private DataProductEntity resolveDataProduct(DataProduct dataProduct) throws SharingException {
        Optional<DataProductEntity> maybeDataProduct = dataProductRepository
                .findByExternalId(dataProduct.getDataProductId());
        DataProductEntity dataProductEntity = maybeDataProduct.orElseThrow(() -> {
            return new SharingException("No data product exists with id " + dataProduct.getDataProductId());
        });
        return dataProductEntity;
    }
}
