package org.apache.airavata;


import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.airavata.replicacatalog.catalog.stubs.AllDataReplicaGetRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.AllDataReplicaGetResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaCreateRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaCreateResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaGetRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaGetResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaLocation;
import org.apache.airavata.replicacatalog.catalog.stubs.StorageType;
import org.apache.airavata.replicacatalog.resource.stubs.common.FileResource;
import org.apache.airavata.replicacatalog.resource.stubs.common.GenericResource;
import org.apache.airavata.replicacatalog.resource.stubs.common.GenericResourceCreateRequest;
import org.apache.airavata.replicacatalog.resource.stubs.common.GenericResourceGetRequest;
import org.apache.airavata.replicacatalog.resource.stubs.common.SecretForStorage;
import org.apache.airavata.replicacatalog.resource.stubs.common.SecretForStorageCreateRequest;
import org.apache.airavata.replicacatalog.resource.stubs.common.SecretForStorageGetRequest;
import org.apache.airavata.replicacatalog.resource.stubs.common.StorageWrapper;
import org.apache.airavata.replicacatalog.resource.stubs.gcs.GCSStorage;
import org.apache.airavata.replicacatalog.resource.stubs.s3.S3Storage;
import org.apache.airavata.replicacatalog.secret.stubs.common.SecretCreateRequest;
import org.apache.airavata.replicacatalog.secret.stubs.common.SecretGetRequest;
import org.apache.airavata.replicacatalog.secret.stubs.common.SecretWrapper;
import org.apache.airavata.replicacatalog.secret.stubs.common.StorageSecret;
import org.apache.airavata.replicacatalog.secret.stubs.gcs.GCSSecret;
import org.apache.airavata.replicacatalog.secret.stubs.s3.S3Secret;
import org.junit.jupiter.api.Test;

class ReplicaCatalogAPIClientTest {

    @Test
    public void testCase1() throws InterruptedException {
        String target = "localhost:6565";

        /*
            --- Sample scenario 1---
            Airavata MFT copied a 100GB astrological data file to Amazon S3 bucket and another copy to GCS location
            User can access that S3 bucket data file and Google cloud storage file via Replica catalog details

            1) Register Data Product in data catalog
            2) Register Replica Location
            3) Register Location storage details
            4) Register location storage credentials

         */
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        try {
            ReplicaCatalogAPIClient client = new ReplicaCatalogAPIClient(channel);

            String testUri = "TestUri";

            DataReplicaLocation replicaLocation = DataReplicaLocation.newBuilder()
                    .setReplicaName("ASTRO S3")
                    .setReplicaDescription("S3 replica")
                    .setDataProductUri(testUri)
                    .setStorageType(org.apache.airavata.replicacatalog.catalog.stubs.StorageType.S3)
                    .setCreationTime(System.currentTimeMillis()).build();
            DataReplicaLocation replicaResult = createReplicaLocation(client, replicaLocation);

            S3Storage storage = S3Storage.newBuilder()
                    .setName("ASTRO S3")
                    .setBucketName("arn:aws:s3:::mftjayan")
                    .setRegion("us-east-1")
                    .setEndpoint("https://s3.us-east-1.amazonaws.com").build();

            GenericResource resource = GenericResource.newBuilder()
                    .setReplicaId(replicaResult.getDataReplicaId())
                    .setStorage(StorageWrapper.newBuilder().setS3Storage(storage).build())
                    .setFile(FileResource.newBuilder().setResourcePath("/astro.zip").build()).build();
            GenericResource resourceResult = createStorage(client, channel, resource);

            S3Secret secret = S3Secret.newBuilder()
                    .setAccessKey("access")
                    .setSecretKey("secKey")
                    .setSessionToken("token").build();
            StorageSecret storageSecret = StorageSecret.newBuilder()
                    .setSecret(SecretWrapper.newBuilder().setS3Secret(secret).build())
                    .setStorageType(StorageType.S3).build();
            SecretCreateRequest secretCreateRequest = SecretCreateRequest.newBuilder().setSecret(storageSecret).build();
            StorageSecret secretResult = createSecret(client, channel, secretCreateRequest);

            SecretForStorage secretForStorage = SecretForStorage.newBuilder()
                    .setStorageId(resourceResult.getStorage().getS3Storage().getStorageId())
                    .setSecretId(secretResult.getSecret().getS3Secret().getSecretId()).setStorageType(StorageType.S3)
                    .setUserIdentity("userID#12345").build();
            SecretForStorageCreateRequest createRequest = SecretForStorageCreateRequest.newBuilder().setSecretForStorage(secretForStorage).build();
            SecretForStorage secretForStorageResult = createCommonStorage(client, channel, createRequest);

            // GCS replica
            DataReplicaLocation replicaLocation2 = DataReplicaLocation.newBuilder()
                    .setReplicaName("ASTRO GCS")
                    .setReplicaDescription("GCS replica")
                    .setDataProductUri(testUri)
                    .setStorageType(org.apache.airavata.replicacatalog.catalog.stubs.StorageType.GCS)
                    .setCreationTime(System.currentTimeMillis()).build();
            DataReplicaLocation replicaResult2 = createReplicaLocation(client, replicaLocation2);

            GCSStorage gcsStorage = GCSStorage.newBuilder()
                    .setName("ASTRO GCS")
                    .setBucketName("mftjayan").build();

            GenericResource resource2 = GenericResource.newBuilder()
                    .setReplicaId(replicaResult2.getDataReplicaId())
                    .setStorage(StorageWrapper.newBuilder().setGcsStorage(gcsStorage).build())
                    .setFile(FileResource.newBuilder().setResourcePath("/astro.zip").build()).build();
            GenericResource resourceResult2 = createStorage(client, channel, resource2);

            GCSSecret gcsSecret = GCSSecret.newBuilder()
                    .setClientEmail("accessEmail@gmail.com")
                    .setPrivateKey("secKey")
                    .setProjectId("1258").build();
            StorageSecret storageSecret2 = StorageSecret.newBuilder()
                    .setSecret(SecretWrapper.newBuilder().setGcsSecret(gcsSecret).build())
                    .setStorageType(StorageType.GCS).build();
            SecretCreateRequest secretCreateRequest2 = SecretCreateRequest.newBuilder().setSecret(storageSecret2).build();
            StorageSecret secretResult2 = createSecret(client, channel, secretCreateRequest2);

            SecretForStorage secretForStorage2 = SecretForStorage.newBuilder()
                    .setStorageId(resourceResult2.getStorage().getGcsStorage().getStorageId())
                    .setSecretId(secretResult2.getSecret().getGcsSecret().getSecretId())
                    .setStorageType(StorageType.GCS)
                    .setUserIdentity("userID#12345").build();
            SecretForStorageCreateRequest createRequest2 = SecretForStorageCreateRequest.newBuilder().setSecretForStorage(secretForStorage2).build();
            SecretForStorage secretForStorageResult2 = createCommonStorage(client, channel, createRequest2);


            System.out.println(
                    MessageFormat.format("Created data replica with id [{0}], Storage id [{1}], Secret id [{2}] in S3",
                            (Object[]) new String[]{replicaResult.getDataReplicaId(), secretForStorageResult.getStorageId(), secretForStorageResult.getSecretId()}));

            System.out.println(
                    MessageFormat.format("Created data replica with id [{0}], Storage id [{1}], Secret id [{2}] in GCS",
                            (Object[]) new String[]{replicaResult2.getDataReplicaId(), secretForStorageResult2.getStorageId(), secretForStorageResult2.getSecretId()}));


            AllDataReplicaGetRequest replicasGetRequest = AllDataReplicaGetRequest.newBuilder().setDataProductUri(testUri).build();
            AllDataReplicaGetResponse replicas = client.getReplicaServiceAPI().getAllReplicaLocation(replicasGetRequest);
            System.out.println(MessageFormat.format("Load replicas with product url [{0}] ", (Object[]) new String[]{testUri}));

            replicas.getReplicaListList().forEach(r -> {

                DataReplicaGetRequest replicaGetRequest = DataReplicaGetRequest.newBuilder().setDataReplicaId(r.getDataReplicaId()).build();
                DataReplicaGetResponse replicaResponse = client.getReplicaServiceAPI().getReplicaLocation(replicaGetRequest);
                if (replicaResponse != null) {
                    SecretForStorage secretForStorage1 = null;
                    try {
                        secretForStorage1 = getStorageSecretIds(client, channel, replicaResponse.getDataReplica().getDataReplicaId());
                        SecretGetRequest secretGetRequest  = SecretGetRequest.newBuilder().setSecretId(secretForStorage1.getSecretId()).setStorageType(secretForStorage1.getStorageType()).build();
                        StorageSecret secretLoad = client.getBlockingSecretStub(channel).getSecret(secretGetRequest);
                        System.out.println(secretLoad);


                        GenericResourceGetRequest genericResourceGetRequest = GenericResourceGetRequest.newBuilder().setReplicaId(replicaResponse.getDataReplica().getDataReplicaId()).build();
                        GenericResource resource1 = client.getBlockingStorageStub(channel).getGenericResource(genericResourceGetRequest);
                        System.out.println(resource1);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    if (secretForStorage1 != null) {
                        System.out.println(
                                MessageFormat.format("Loaded data replica with id [{0}], Storage id [{1}], Secret id [{2}] Storage Type [{3}]",
                                        (Object[]) new String[]{replicaResponse.getDataReplica().getDataReplicaId(), secretForStorage1.getStorageId(), secretForStorage1.getSecretId(),
                                                secretForStorage1.getStorageType().name()}));

                    }
                }
            }

            );


            /*
            --- Sample scenario 2---
            Airavata MFT copied a 1000GB astrological data Folder which contains 100 data files to Amazon S3 bucket
            User can access that S3 bucket data folder via Replica catalog details

            1) Register Data Product
            2) Register Replica Location
            3) Register Location storage details
            4) Register location storage credentials

         */
        }catch (Exception e){

        } finally {
            channel.shutdownNow().awaitTermination(1800, TimeUnit.SECONDS);
        }
    }

    public DataReplicaLocation createReplicaLocation(ReplicaCatalogAPIClient client, DataReplicaLocation replicaLocation) throws Exception {
        DataReplicaCreateRequest request = DataReplicaCreateRequest.newBuilder().setDataReplica(replicaLocation).build();
        DataReplicaCreateResponse response = client.getReplicaServiceAPI().registerReplicaLocation(request);
        if(response.hasError() && !response.getError().getMessage().isBlank()){
            throw new Exception(response.getError().getMessage());
        }
        return response.getDataReplica();
    }


    public SecretForStorage createCommonStorage(ReplicaCatalogAPIClient client, Channel channel, SecretForStorageCreateRequest createRequest) {

        SecretForStorage response = client.getBlockingStorageStub(channel).registerSecretForStorage(createRequest);
        return response;
    }

    public GenericResource createStorage(ReplicaCatalogAPIClient client, Channel channel, GenericResource resource) {
        GenericResourceCreateRequest request = GenericResourceCreateRequest.newBuilder().setResource(resource).build();
        GenericResource response = client.getBlockingStorageStub(channel).createGenericResource(request);
        return response;
    }

    public StorageSecret createSecret(ReplicaCatalogAPIClient client, Channel channel, SecretCreateRequest createRequest) {
        StorageSecret response = client.getBlockingSecretStub(channel).registerSecret(createRequest);
        return response;
    }

    public SecretForStorage getStorageSecretIds(ReplicaCatalogAPIClient client, Channel channel, String replicaId) throws Exception {
        GenericResourceGetRequest resourceGetRequest = GenericResourceGetRequest.newBuilder().setReplicaId(replicaId).build();
        GenericResource resource = client.getBlockingStorageStub(channel).getGenericResource(resourceGetRequest);
        String storageId = null;
        if (resource.getStorage().hasS3Storage()) {
            storageId = resource.getStorage().getS3Storage().getStorageId();
        } else if (resource.getStorage().hasGcsStorage()) {
            storageId = resource.getStorage().getGcsStorage().getStorageId();
        } else {
            throw new Exception("Not Supported storage type");
        }
        SecretForStorageGetRequest request = SecretForStorageGetRequest.newBuilder().setStorageId(storageId).build();
        SecretForStorage secretForStorage = client.getBlockingStorageStub(channel).getSecretForStorage(request);
        return secretForStorage;
    }


}