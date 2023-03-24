//package org.apache.airavata;
//
//
//import java.text.MessageFormat;
//import java.util.concurrent.TimeUnit;
//
//import io.grpc.Channel;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import org.apache.airavata.replicacatalog.catalog.stubs.DataProduct;
//import org.apache.airavata.replicacatalog.catalog.stubs.DataProductCreateRequest;
//import org.apache.airavata.replicacatalog.catalog.stubs.DataProductCreateResponse;
//import org.apache.airavata.replicacatalog.catalog.stubs.DataProductType;
//import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaCreateRequest;
//import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaCreateResponse;
//import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaLocation;
//import org.apache.airavata.replicacatalog.resource.stubs.common.FileResource;
//import org.apache.airavata.replicacatalog.resource.stubs.common.GenericResource;
//import org.apache.airavata.replicacatalog.resource.stubs.common.GenericResourceCreateRequest;
//import org.apache.airavata.replicacatalog.resource.stubs.common.SecretForStorage;
//import org.apache.airavata.replicacatalog.resource.stubs.common.StorageCommon;
//import org.apache.airavata.replicacatalog.resource.stubs.common.StorageType;
//import org.apache.airavata.replicacatalog.resource.stubs.s3.S3Storage;
//import org.apache.airavata.replicacatalog.secret.stubs.common.StorageSecret;
//import org.apache.airavata.replicacatalog.secret.stubs.s3.S3Secret;
//
//class ReplicaCatalogAPIClientTest
//{
//    public void testCase1()
//    {
//        String target = "localhost:6565";
//
//    /*
//        --- Sample scenario ---
//        Airavata MFT copied a 1000GB astrological data Folder which contains 100 data files to Amazon S3 bucket
//        User can access that S3 bucket data folder via Replica catalog details
//
//        1) Register Data Product
//        2) Register Replica Location
//        3) Register Location storage details
//        4) Register location storage credentials
//
//     */
//        ManagedChannel channel = ManagedChannelBuilder.forTarget( target ).usePlaintext().build();
//        try
//        {
//            ReplicaCatalogAPIClient client = new ReplicaCatalogAPIClient( channel );
//
//            DataProduct dataProduct = DataProduct.newBuilder().setDataProductType( DataProductType.FILE )
//                    .setProductName( "ASTRO" ).setProductUri( "613" ).setProductDescription( "Astro Data" ).build();
//            DataProduct productResult = client.createDataProduct( dataProduct );
//
//            DataReplicaLocation replicaLocation = DataReplicaLocation.newBuilder().setReplicaName( "ASTRO S3" )
//                    .setReplicaDescription( "S3 replica" ).setDataProductId( productResult.getProductUri() ).build();
//
//            DataReplicaLocation replicaResult = client.createReplicaLocation( replicaLocation );
//
//            GenericResource resource = client.createStorage( channel, null );
//
//            StorageSecret secret = client.createSecret( channel, null );
//
//            client.createCommonStorage( channel, null );
//
//
//            DataReplicaLocation result = client.createReplicaLocation( replicaLocation );
//            System.out.println(
//                    MessageFormat.format( "Created data product with id [{0}]", result.getDataReplicaId() ) );
//
//        }
//        finally
//        {
//            channel.shutdownNow().awaitTermination( 5, TimeUnit.SECONDS );
//        }
//    }
//
//    public DataReplicaLocation createReplicaLocation( DataReplicaLocation replicaLocation )
//    {
//        DataReplicaCreateRequest request =
//                DataReplicaCreateRequest.newBuilder().setDataReplica( replicaLocation ).build();
//        DataReplicaCreateResponse response = blockingApiStub.registerReplicaLocation( request );
//        return response.getDataReplica();
//    }
//
//    public DataProduct createDataProduct( DataProduct dataProduct )
//    {
//        DataProductCreateRequest request = DataProductCreateRequest.newBuilder().setDataProduct( dataProduct ).build();
//        DataProductCreateResponse response = blockingApiStub.registerDataProduct( request );
//        return response.getDataProduct();
//    }
//
//    public SecretForStorage createCommonStorage( Channel channel, StorageCommon storageCommon )
//    {
//        SecretForStorage request = SecretForStorage.newBuilder()
//                .setStorageId( "-1" ).setSecretId( "-1" ).setStorageType( StorageType.S3 ).build();
//        SecretForStorage response = getBlockingStorageStub( channel ).registerSecretForStorage( request );
//        return response;
//    }
//
//    public GenericResource createStorage( Channel channel, GenericResource resource )
//    {
//        S3Storage storage = S3Storage.newBuilder().setStorageId( "-1" ).setBucketName( "bucket" ).setName( "Name" )
//                .setEndpoint( "end" ).setRegion( "us-west" ).build();
//
//        GenericResource resource1 = GenericResource.newBuilder().setResourceId( "-1" ).setS3Storage( storage )
//                .setFile( FileResource.newBuilder().setResourcePath( "path" ).build() ).build();
//
//        GenericResourceCreateRequest request = GenericResourceCreateRequest.newBuilder().setStorageId( "-1" ).
//                setResource( resource1 ).setStorageType( GenericResourceCreateRequest.StorageType.S3 ).build();
//        GenericResource response = getBlockingResourceStub( channel ).createGenericResource( request );
//        return response;
//    }
//
//    public StorageSecret createSecret( Channel channel, GenericResource resource )
//    {
//        S3Secret secret = S3Secret.newBuilder().setSecretId( "-1" ).setAccessKey( "access" ).setSecretKey( "secKey" )
//                .setSessionToken( "token" ).build();
//        StorageSecret
//                storageSecret = StorageSecret.newBuilder().setSecretId( "-1" ).setS3Secret( secret )
//                .setStorageType( org.apache.airavata.replicacatalog.secret.stubs.common.StorageType.S3 ).build();
//
//
//        StorageSecret request = StorageSecret.newBuilder().setSecretId( "-1" ).setS3Secret( secret ).
//                setStorageType( org.apache.airavata.replicacatalog.secret.stubs.common.StorageType.S3 ).build();
//        StorageSecret response = getBlockingSecretStub( channel ).registerSecret( storageSecret );
//        return storageSecret;
//    }
//
//
//}