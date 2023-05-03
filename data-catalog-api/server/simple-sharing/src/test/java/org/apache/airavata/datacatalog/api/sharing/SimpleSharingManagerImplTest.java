package org.apache.airavata.datacatalog.api.sharing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.apache.airavata.datacatalog.api.DataProduct;
import org.apache.airavata.datacatalog.api.GroupInfo;
import org.apache.airavata.datacatalog.api.Permission;
import org.apache.airavata.datacatalog.api.UserInfo;
import org.apache.airavata.datacatalog.api.model.DataProductEntity;
import org.apache.airavata.datacatalog.api.model.TenantEntity;
import org.apache.airavata.datacatalog.api.model.UserEntity;
import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleGroupEntity;
import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleTenantEntity;
import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleUserEntity;
import org.apache.airavata.datacatalog.api.repository.DataProductRepository;
import org.apache.airavata.datacatalog.api.repository.TenantRepository;
import org.apache.airavata.datacatalog.api.repository.UserRepository;
import org.apache.airavata.datacatalog.api.repository.sharing.simple.SimpleGroupRepository;
import org.apache.airavata.datacatalog.api.repository.sharing.simple.SimpleTenantRepository;
import org.apache.airavata.datacatalog.api.repository.sharing.simple.SimpleUserRepository;
import org.apache.airavata.datacatalog.api.sharing.SimpleSharingManagerImplTest.MyConfiguration;
import org.apache.airavata.datacatalog.api.sharing.exception.SharingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(classes = MyConfiguration.class)
public class SimpleSharingManagerImplTest {

    @Configuration
    @EnableJpaRepositories("org.apache.airavata.datacatalog.api.repository")
    @EntityScan("org.apache.airavata.datacatalog.api.model")
    public static class MyConfiguration {
        @Bean
        public SharingManager getSharingManager() {
            return new SimpleSharingManagerImpl();
        }
    }

    @Autowired
    SimpleSharingManagerImpl simpleSharingManagerImpl;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TenantRepository tenantRepository;
    @Autowired
    SimpleUserRepository simpleUserRepository;
    @Autowired
    SimpleGroupRepository simpleGroupRepository;
    @Autowired
    SimpleTenantRepository simpleTenantRepository;
    @Autowired
    DataProductRepository dataProductRepository;

    @Test
    void testResolveUserCreatesUserAndTenantIfMissing() throws SharingException {

        Optional<UserEntity> maybeUserEntity = userRepository.findByExternalIdAndTenant_ExternalId("userId",
                "tenantId");
        assertFalse(maybeUserEntity.isPresent());
        Optional<TenantEntity> maybeTenantEntity = tenantRepository.findByExternalId("tenantId");
        assertFalse(maybeTenantEntity.isPresent());

        UserInfo userInfo = UserInfo.newBuilder().setUserId("userId").setTenantId("tenantId").build();
        UserEntity userEntity = this.simpleSharingManagerImpl.resolveUser(userInfo);

        assertEquals(userEntity.getExternalId(), "userId");
        assertEquals(userEntity.getName(), "userId");
        assertEquals(userEntity.getTenant().getExternalId(), "tenantId");

        maybeUserEntity = userRepository.findByExternalIdAndTenant_ExternalId("userId", "tenantId");
        assertTrue(maybeUserEntity.isPresent());

        maybeTenantEntity = tenantRepository.findByExternalId("tenantId");
        assertTrue(maybeTenantEntity.isPresent());
    }

    @Test
    void testResolveUserFindsExistingUserAndTenant() throws SharingException {

        String userId = "userId";
        String tenantId = "tenantId";

        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setExternalId(tenantId);
        tenantEntity.setName("tenant name");
        tenantRepository.save(tenantEntity);
        SimpleTenantEntity simpleTenantEntity = new SimpleTenantEntity();
        simpleTenantEntity.setExternalId(tenantId);
        simpleTenantEntity.setName(tenantId);
        simpleTenantEntity.setTenant(tenantEntity);
        simpleTenantRepository.save(simpleTenantEntity);

        UserEntity testUserEntity = new UserEntity();
        testUserEntity.setExternalId(userId);
        testUserEntity.setName("user name");
        testUserEntity.setTenant(tenantEntity);
        UserEntity savedUserEntity = userRepository.save(testUserEntity);
        SimpleUserEntity simpleUserEntity = new SimpleUserEntity();
        simpleUserEntity.setExternalId(userId);
        simpleUserEntity.setName(userId);
        simpleUserEntity.setUser(testUserEntity);
        simpleUserEntity.setSimpleTenant(simpleTenantEntity);
        simpleUserRepository.save(simpleUserEntity);

        UserInfo userInfo = UserInfo.newBuilder().setUserId(userId).setTenantId(tenantId).build();
        UserEntity userEntity = this.simpleSharingManagerImpl.resolveUser(userInfo);

        assertEquals(userEntity.getExternalId(), userId);
        assertEquals(userEntity.getName(), "user name");
        assertEquals(userEntity.getTenant().getExternalId(), tenantId);
        // Double check same database record
        assertEquals(userEntity.getUserId(), savedUserEntity.getUserId());
    }

    @Test
    public void testUserHasAccess() throws SharingException {

        UserInfo userA = UserInfo.newBuilder().setTenantId("tenantId").setUserId("userA").build();
        UserEntity testUserA = simpleSharingManagerImpl.resolveUser(userA);
        UserInfo userB = UserInfo.newBuilder().setTenantId("tenantId").setUserId("userB").build();
        // UserEntity testUserB = simpleSharingManagerImpl
        // .resolveUser(userB);

        DataProductEntity dataProductEntity = new DataProductEntity();
        dataProductEntity.setExternalId(UUID.randomUUID().toString());
        dataProductEntity.setOwner(testUserA);
        dataProductEntity.setName("test data product");
        dataProductRepository.save(dataProductEntity);

        // Check that userB doesn't have READ access to the data product
        DataProduct dataProduct = DataProduct.newBuilder()
                .setDataProductId(dataProductEntity.getExternalId()) // only need the data product id
                .build();
        boolean hasAccess = simpleSharingManagerImpl.userHasAccess(userB, dataProduct, Permission.READ);
        assertFalse(hasAccess);

        // Grant READ access to userB for the data product
        simpleSharingManagerImpl.grantPermissionToUser(userB, dataProduct, Permission.READ, userA);

        // Check that userB does now have READ access to the data product
        hasAccess = simpleSharingManagerImpl.userHasAccess(userB, dataProduct, Permission.READ);
        assertTrue(hasAccess);
    }

    @Test
    public void testRevokePermissionFromUser() throws SharingException {

        UserInfo userA = UserInfo.newBuilder().setTenantId("tenantId").setUserId("userA").build();
        UserEntity testUserA = simpleSharingManagerImpl.resolveUser(userA);
        UserInfo userB = UserInfo.newBuilder().setTenantId("tenantId").setUserId("userB").build();

        DataProductEntity dataProductEntity = new DataProductEntity();
        dataProductEntity.setExternalId(UUID.randomUUID().toString());
        dataProductEntity.setOwner(testUserA);
        dataProductEntity.setName("test data product");
        dataProductRepository.save(dataProductEntity);

        DataProduct dataProduct = DataProduct.newBuilder()
                .setDataProductId(dataProductEntity.getExternalId()) // only need the data product id
                .build();

        // Grant READ access to userB for the data product
        simpleSharingManagerImpl.grantPermissionToUser(userB, dataProduct, Permission.READ, userA);

        // Check that userB does have READ access to the data product
        assertTrue(simpleSharingManagerImpl.userHasAccess(userB, dataProduct, Permission.READ));

        // Revoke READ access from userB
        simpleSharingManagerImpl.revokePermissionFromUser(userB, dataProduct, Permission.READ);

        // Check that userB does not now have READ access
        assertFalse(simpleSharingManagerImpl.userHasAccess(userB, dataProduct, Permission.READ));
    }

    @Test
    public void testUserHasAccessViaGroupMembership() throws SharingException {

        UserInfo userA = UserInfo.newBuilder().setTenantId("tenantId").setUserId("userA").build();
        UserEntity testUserA = simpleSharingManagerImpl.resolveUser(userA);
        UserInfo userB = UserInfo.newBuilder().setTenantId("tenantId").setUserId("userB").build();
        UserInfo userC = UserInfo.newBuilder().setTenantId("tenantId").setUserId("userC").build();

        GroupInfo testGroup = GroupInfo.newBuilder().setGroupId("groupId").setTenantId("tenantId").build();

        // Create a data product
        DataProductEntity dataProductEntity = new DataProductEntity();
        dataProductEntity.setExternalId(UUID.randomUUID().toString());
        dataProductEntity.setOwner(testUserA);
        dataProductEntity.setName("test data product");
        dataProductRepository.save(dataProductEntity);

        // Add users B and C to the testGroup
        simpleSharingManagerImpl.resolveUser(userB);
        simpleSharingManagerImpl.resolveUser(userC);
        Optional<SimpleUserEntity> userBEntity = simpleUserRepository
                .findByExternalIdAndSimpleTenant_ExternalId(userB.getUserId(), userB.getTenantId());
        assertTrue(userBEntity.isPresent());
        Optional<SimpleUserEntity> userCEntity = simpleUserRepository
                .findByExternalIdAndSimpleTenant_ExternalId(userC.getUserId(), userC.getTenantId());
        assertTrue(userCEntity.isPresent());
        SimpleGroupEntity testGroupEntity = new SimpleGroupEntity();
        testGroupEntity.setName(testGroup.getGroupId());
        testGroupEntity.setExternalId(testGroup.getGroupId());
        testGroupEntity.getMemberUsers().addAll(Arrays.asList(userBEntity.get(), userCEntity.get()));
        testGroupEntity.setSimpleTenant(userBEntity.get().getSimpleTenant());
        simpleGroupRepository.save(testGroupEntity);

        // Check that users B and C doesn't have READ access to the data product
        DataProduct dataProduct = DataProduct.newBuilder()
                .setDataProductId(dataProductEntity.getExternalId()) // only need the data product id
                .build();
        assertFalse(simpleSharingManagerImpl.userHasAccess(userB, dataProduct, Permission.READ));
        assertFalse(simpleSharingManagerImpl.userHasAccess(userC, dataProduct, Permission.READ));

        // Grant READ access to testGroup for the data product
        simpleSharingManagerImpl.grantPermissionToGroup(testGroup, dataProduct, Permission.READ, userA);

        // Check that users B and C now have READ access to the data product
        assertTrue(simpleSharingManagerImpl.userHasAccess(userB, dataProduct, Permission.READ));
        assertTrue(simpleSharingManagerImpl.userHasAccess(userC, dataProduct, Permission.READ));
    }

    @Test
    public void testRevokePermissionFromGroup() throws SharingException {

        UserInfo userA = UserInfo.newBuilder().setTenantId("tenantId").setUserId("userA").build();
        UserEntity testUserA = simpleSharingManagerImpl.resolveUser(userA);
        UserInfo userB = UserInfo.newBuilder().setTenantId("tenantId").setUserId("userB").build();
        UserInfo userC = UserInfo.newBuilder().setTenantId("tenantId").setUserId("userC").build();

        GroupInfo testGroup = GroupInfo.newBuilder().setGroupId("groupId").setTenantId("tenantId").build();

        // Create a data product
        DataProductEntity dataProductEntity = new DataProductEntity();
        dataProductEntity.setExternalId(UUID.randomUUID().toString());
        dataProductEntity.setOwner(testUserA);
        dataProductEntity.setName("test data product");
        dataProductRepository.save(dataProductEntity);

        // Add users B and C to the testGroup
        simpleSharingManagerImpl.resolveUser(userB);
        simpleSharingManagerImpl.resolveUser(userC);
        Optional<SimpleUserEntity> userBEntity = simpleUserRepository
                .findByExternalIdAndSimpleTenant_ExternalId(userB.getUserId(), userB.getTenantId());
        assertTrue(userBEntity.isPresent());
        Optional<SimpleUserEntity> userCEntity = simpleUserRepository
                .findByExternalIdAndSimpleTenant_ExternalId(userC.getUserId(), userC.getTenantId());
        assertTrue(userCEntity.isPresent());
        SimpleGroupEntity testGroupEntity = new SimpleGroupEntity();
        testGroupEntity.setName(testGroup.getGroupId());
        testGroupEntity.setExternalId(testGroup.getGroupId());
        testGroupEntity.getMemberUsers().addAll(Arrays.asList(userBEntity.get(), userCEntity.get()));
        testGroupEntity.setSimpleTenant(userBEntity.get().getSimpleTenant());
        simpleGroupRepository.save(testGroupEntity);

        DataProduct dataProduct = DataProduct.newBuilder()
                .setDataProductId(dataProductEntity.getExternalId()) // only need the data product id
                .build();

        // Grant READ access to testGroup for the data product
        simpleSharingManagerImpl.grantPermissionToGroup(testGroup, dataProduct, Permission.READ, userA);

        // Check that users B and C now have READ access to the data product
        assertTrue(simpleSharingManagerImpl.userHasAccess(userB, dataProduct, Permission.READ));
        assertTrue(simpleSharingManagerImpl.userHasAccess(userC, dataProduct, Permission.READ));

        // Revoke READ access from testGroup
        simpleSharingManagerImpl.revokePermissionFromGroup(testGroup, dataProduct, Permission.READ);

        // Check that users B and C don't have READ access to the data product
        assertFalse(simpleSharingManagerImpl.userHasAccess(userB, dataProduct, Permission.READ));
        assertFalse(simpleSharingManagerImpl.userHasAccess(userC, dataProduct, Permission.READ));
    }

    @Test
    public void testGrantPublicAccess() throws SharingException {

        UserInfo userA = UserInfo.newBuilder().setTenantId("tenantId").setUserId("userA").build();
        UserEntity testUserA = simpleSharingManagerImpl.resolveUser(userA);

        // Create a data product
        DataProductEntity dataProductEntity = new DataProductEntity();
        dataProductEntity.setExternalId(UUID.randomUUID().toString());
        dataProductEntity.setOwner(testUserA);
        dataProductEntity.setName("test data product");
        dataProductRepository.save(dataProductEntity);

        DataProduct dataProduct = DataProduct.newBuilder()
                .setDataProductId(dataProductEntity.getExternalId()) // only need the data product id
                .build();

        assertFalse(simpleSharingManagerImpl.hasPublicAccess(dataProduct, Permission.READ));

        simpleSharingManagerImpl.grantPublicAccess(dataProduct, Permission.READ);

        assertTrue(simpleSharingManagerImpl.hasPublicAccess(dataProduct, Permission.READ));
    }

    @Test
    public void testRevokePublicAccess() throws SharingException {

        UserInfo userA = UserInfo.newBuilder().setTenantId("tenantId").setUserId("userA").build();
        UserEntity testUserA = simpleSharingManagerImpl.resolveUser(userA);

        // Create a data product
        DataProductEntity dataProductEntity = new DataProductEntity();
        dataProductEntity.setExternalId(UUID.randomUUID().toString());
        dataProductEntity.setOwner(testUserA);
        dataProductEntity.setName("test data product");
        dataProductRepository.save(dataProductEntity);

        DataProduct dataProduct = DataProduct.newBuilder()
                .setDataProductId(dataProductEntity.getExternalId()) // only need the data product id
                .build();

        simpleSharingManagerImpl.grantPublicAccess(dataProduct, Permission.READ);

        assertTrue(simpleSharingManagerImpl.hasPublicAccess(dataProduct, Permission.READ));

        simpleSharingManagerImpl.revokePublicAccess(dataProduct, Permission.READ);

        assertFalse(simpleSharingManagerImpl.hasPublicAccess(dataProduct, Permission.READ));
    }

    @Test
    public void testUserHasAccessViaCascade() throws SharingException {

        UserInfo userA = UserInfo.newBuilder().setTenantId("tenantId").setUserId("userA").build();
        UserEntity testUserA = simpleSharingManagerImpl.resolveUser(userA);
        UserInfo userB = UserInfo.newBuilder().setTenantId("tenantId").setUserId("userB").build();

        DataProductEntity dp1 = new DataProductEntity();
        dp1.setExternalId(UUID.randomUUID().toString());
        dp1.setOwner(testUserA);
        dp1.setName("test data product 1");
        dataProductRepository.save(dp1);

        DataProductEntity dp2 = new DataProductEntity();
        dp2.setExternalId(UUID.randomUUID().toString());
        dp2.setOwner(testUserA);
        dp2.setName("test data product 2");
        dp2.setParentDataProductEntity(dp1);
        dataProductRepository.save(dp2);

        DataProductEntity dp3 = new DataProductEntity();
        dp3.setExternalId(UUID.randomUUID().toString());
        dp3.setOwner(testUserA);
        dp3.setName("test data product 3");
        dp3.setParentDataProductEntity(dp2);
        dataProductRepository.save(dp3);

        // Check that userB doesn't have READ access to the data products 1, 2 or 3
        DataProduct dataProduct = DataProduct.newBuilder()
                .setDataProductId(dp1.getExternalId()) // only need the data product id
                .build();
        assertFalse(simpleSharingManagerImpl.userHasAccess(userB, dataProduct, Permission.READ));

        // Grant READ access to userB for the data product
        simpleSharingManagerImpl.grantPermissionToUser(userB, dataProduct, Permission.READ, userA);

        // Check that userB does now have READ access to the data product 1, 2 and 3
        assertTrue(simpleSharingManagerImpl.userHasAccess(userB, dataProduct, Permission.READ));
        assertTrue(simpleSharingManagerImpl.userHasAccess(userB,
                DataProduct.newBuilder().setDataProductId(dp2.getExternalId()).build(), Permission.READ));
        assertTrue(simpleSharingManagerImpl.userHasAccess(userB,
                DataProduct.newBuilder().setDataProductId(dp3.getExternalId()).build(), Permission.READ));
    }

    @Test
    public void testUserHasAccessViaGroupMembershipAndCascade() throws SharingException {

        UserInfo userA = UserInfo.newBuilder().setTenantId("tenantId").setUserId("userA").build();
        UserEntity testUserA = simpleSharingManagerImpl.resolveUser(userA);
        UserInfo userB = UserInfo.newBuilder().setTenantId("tenantId").setUserId("userB").build();

        GroupInfo testGroup = GroupInfo.newBuilder().setGroupId("groupId").setTenantId("tenantId").build();

        // Create data products
        DataProductEntity dp1 = new DataProductEntity();
        dp1.setExternalId(UUID.randomUUID().toString());
        dp1.setOwner(testUserA);
        dp1.setName("test data product 1");
        dataProductRepository.save(dp1);

        DataProductEntity dp2 = new DataProductEntity();
        dp2.setExternalId(UUID.randomUUID().toString());
        dp2.setOwner(testUserA);
        dp2.setName("test data product 2");
        dp2.setParentDataProductEntity(dp1);
        dataProductRepository.save(dp2);

        DataProductEntity dp3 = new DataProductEntity();
        dp3.setExternalId(UUID.randomUUID().toString());
        dp3.setOwner(testUserA);
        dp3.setName("test data product 3");
        dp3.setParentDataProductEntity(dp2);
        dataProductRepository.save(dp3);

        // Add user B to the testGroup
        simpleSharingManagerImpl.resolveUser(userB);
        Optional<SimpleUserEntity> userBEntity = simpleUserRepository
                .findByExternalIdAndSimpleTenant_ExternalId(userB.getUserId(), userB.getTenantId());
        assertTrue(userBEntity.isPresent());
        SimpleGroupEntity testGroupEntity = new SimpleGroupEntity();
        testGroupEntity.setName(testGroup.getGroupId());
        testGroupEntity.setExternalId(testGroup.getGroupId());
        testGroupEntity.getMemberUsers().addAll(Arrays.asList(userBEntity.get()));
        testGroupEntity.setSimpleTenant(userBEntity.get().getSimpleTenant());
        simpleGroupRepository.save(testGroupEntity);

        // Check that user B doesn't have READ access to the data product
        DataProduct dataProduct = DataProduct.newBuilder()
                .setDataProductId(dp1.getExternalId()) // only need the data product id
                .build();
        assertFalse(simpleSharingManagerImpl.userHasAccess(userB, dataProduct, Permission.READ));

        // Grant READ access to testGroup for the data product
        simpleSharingManagerImpl.grantPermissionToGroup(testGroup, dataProduct, Permission.READ, userA);

        // Check that users B now has READ access to the data products 1, 2, and 3
        assertTrue(simpleSharingManagerImpl.userHasAccess(userB, dataProduct, Permission.READ));
        assertTrue(simpleSharingManagerImpl.userHasAccess(userB,
                DataProduct.newBuilder().setDataProductId(dp2.getExternalId()).build(), Permission.READ));
        assertTrue(simpleSharingManagerImpl.userHasAccess(userB,
                DataProduct.newBuilder().setDataProductId(dp3.getExternalId()).build(), Permission.READ));
    }

    @Test
    public void testUserHasAccessOwnerHasAllPermissions() throws SharingException {

        UserInfo userA = UserInfo.newBuilder().setTenantId("tenantId").setUserId("userA").build();
        UserEntity testUserA = simpleSharingManagerImpl.resolveUser(userA);

        // Create a data product
        DataProductEntity dataProductEntity = new DataProductEntity();
        dataProductEntity.setExternalId(UUID.randomUUID().toString());
        dataProductEntity.setOwner(testUserA);
        dataProductEntity.setName("test data product");
        dataProductRepository.save(dataProductEntity);

        DataProduct dataProduct = DataProduct.newBuilder()
                .setDataProductId(dataProductEntity.getExternalId()) // only need the data product id
                .build();

        // Grant OWNER access to userA for the data product
        simpleSharingManagerImpl.grantPermissionToUser(userA, dataProduct, Permission.OWNER, userA);

        // Check that owner has all permissions
        Set<Permission> allPermissions = new HashSet<Permission>(Arrays.asList(Permission.values()));
        allPermissions.remove(Permission.UNRECOGNIZED); // remove the special protobuf specific UNRECOGNIZED permission
        for (Permission permission : allPermissions) {
            assertTrue(simpleSharingManagerImpl.userHasAccess(userA, dataProduct, permission), permission.toString());
        }

    }

}
