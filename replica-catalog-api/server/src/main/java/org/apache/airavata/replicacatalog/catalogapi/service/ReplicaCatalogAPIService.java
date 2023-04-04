package org.apache.airavata.replicacatalog.catalogapi.service;

import io.grpc.stub.StreamObserver;
import org.apache.airavata.replicacatalog.catalog.service.ReplicaCatalogAPIServiceGrpc;
import org.apache.airavata.replicacatalog.catalog.stubs.AllDataReplicaDeleteRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.AllDataReplicaDeleteResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.AllDataReplicaGetRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.AllDataReplicaGetResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.DataProduct;
import org.apache.airavata.replicacatalog.catalog.stubs.DataProductCreateRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.DataProductCreateResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.DataProductDeleteRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.DataProductDeleteResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.DataProductGetRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.DataProductGetResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.DataProductUpdateRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.DataProductUpdateResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaCreateRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaCreateResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaDeleteRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaDeleteResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaGetRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaGetResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaLocation;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaUpdateRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaUpdateResponse;
import org.apache.airavata.replicacatalog.catalogapi.mapper.DataProductMapper;
import org.apache.airavata.replicacatalog.catalogapi.mapper.DataReplicaMapper;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * API level services
 */
@GRpcService
public class ReplicaCatalogAPIService extends ReplicaCatalogAPIServiceGrpc.ReplicaCatalogAPIServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(ReplicaCatalogAPIService.class);


    @Autowired
    DataProductMapper dataProductMapper = new DataProductMapper();

    @Autowired
    DataReplicaMapper replicaMapper = new DataReplicaMapper();

    @Autowired
    IReplicaCatalogService catalogService;


    @Override
    public void registerDataProduct(DataProductCreateRequest request, StreamObserver<DataProductCreateResponse> responseObserver) {
        DataProduct result = catalogService.createDataProduct(request.getDataProduct());
        DataProductCreateResponse.Builder responseBuilder = DataProductCreateResponse.newBuilder();
        responseBuilder.setDataProduct(result);
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateDataProduct(DataProductUpdateRequest request, StreamObserver<DataProductUpdateResponse> responseObserver) {
        DataProduct result = catalogService.updateDataProduct(request.getDataProduct());
        DataProductUpdateResponse.Builder responseBuilder = DataProductUpdateResponse.newBuilder();
        responseBuilder.setDataProduct(result);
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getDataProduct(DataProductGetRequest request, StreamObserver<DataProductGetResponse> responseObserver) {
        DataProduct result = catalogService.getDataProduct(request.getDataProductUri());
        DataProductGetResponse.Builder responseBuilder = DataProductGetResponse.newBuilder();
        responseBuilder.setDataProduct(result);
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeDataProduct(DataProductDeleteRequest request, StreamObserver<DataProductDeleteResponse> responseObserver) {
        catalogService.deleteDataProduct(request.getDataProductUri());
        responseObserver.onNext(DataProductDeleteResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void registerReplicaLocation(DataReplicaCreateRequest request, StreamObserver<DataReplicaCreateResponse> responseObserver) {

        logger.info("Creating Replica for a data product {}", request.getDataReplica());
        if (request.getDataReplica() == null) {
            logger.debug("No Data Replica Location");
        }
        DataReplicaLocation result = catalogService.createDataReplica(request.getDataReplica());

        DataReplicaCreateResponse.Builder responseBuilder = DataReplicaCreateResponse.newBuilder();
        responseBuilder.setDataReplica(result);
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateReplicaLocation(DataReplicaUpdateRequest request, StreamObserver<DataReplicaUpdateResponse> responseObserver) {

        DataReplicaLocation result = catalogService.updateDataReplica(request.getDataReplica());

        DataReplicaUpdateResponse.Builder responseBuilder = DataReplicaUpdateResponse.newBuilder();
        responseBuilder.setDataReplica(result);
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getReplicaLocation(DataReplicaGetRequest request, StreamObserver<DataReplicaGetResponse> responseObserver) {

        logger.info("Loading Replica for a Replica ID : {}", request.getDataReplicaId());

        DataReplicaLocation result = catalogService.getDataReplica(request.getDataReplicaId());

        DataReplicaGetResponse.Builder responseBuilder = DataReplicaGetResponse.newBuilder();
        responseBuilder.setDataReplica(result);
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeReplicaLocation(DataReplicaDeleteRequest request, StreamObserver<DataReplicaDeleteResponse> responseObserver) {
        catalogService.deleteDataReplica(request.getDataReplicaId());
        responseObserver.onNext(DataReplicaDeleteResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllReplicaLocation(AllDataReplicaGetRequest request, StreamObserver<AllDataReplicaGetResponse> responseObserver) {
        super.getAllReplicaLocation(request, responseObserver);
    }

    @Override
    public void removeAllReplicaLocation(AllDataReplicaDeleteRequest request, StreamObserver<AllDataReplicaDeleteResponse> responseObserver) {
        super.removeAllReplicaLocation(request, responseObserver);
    }


}
