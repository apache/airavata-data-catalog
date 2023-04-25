package org.apache.airavata.datacatalog.api.sharing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.UUID;

import org.apache.airavata.datacatalog.api.DataProduct;
import org.apache.airavata.datacatalog.api.Permission;
import org.apache.airavata.datacatalog.api.UserInfo;
import org.apache.airavata.datacatalog.api.exception.SharingException;
import org.apache.airavata.datacatalog.api.model.DataProductEntity;
import org.apache.airavata.datacatalog.api.model.TenantEntity;
import org.apache.airavata.datacatalog.api.model.UserEntity;
import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleTenantEntity;
import org.apache.airavata.datacatalog.api.model.sharing.simple.SimpleUserEntity;
import org.apache.airavata.datacatalog.api.repository.DataProductRepository;
import org.apache.airavata.datacatalog.api.repository.TenantRepository;
import org.apache.airavata.datacatalog.api.repository.UserRepository;
import org.apache.airavata.datacatalog.api.repository.sharing.simple.SimpleTenantRepository;
import org.apache.airavata.datacatalog.api.repository.sharing.simple.SimpleUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SimpleSharingManagerImplTest {
    @Autowired
    SimpleSharingManagerImpl simpleSharingManagerImpl;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TenantRepository tenantRepository;
    @Autowired
    SimpleUserRepository simpleUserRepository;
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

}
