package org.apache.airavata.replicacatalog.catalogapi.service;

import io.grpc.stub.StreamObserver;

import org.apache.airavata.replicacatalog.catalog.service.ReplicaCatalogAPIServiceGrpc;
import org.apache.airavata.replicacatalog.catalog.stubs.*;
import org.apache.airavata.replicacatalog.catalogapi.mapper.DataProductMapper;
import org.apache.airavata.replicacatalog.catalogapi.mapper.DataReplicaMapper;
import org.apache.airavata.replicacatalog.catalogapi.repository.DataProductRepository;
import org.apache.airavata.replicacatalog.catalogapi.repository.DataReplicaLocationRepository;
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
    IReplicaCatalogService dataCatalogService;


    @Override
    public void registerDataProduct(DataProductCreateRequest request,
                                    StreamObserver<DataProductCreateResponse> responseObserver) {

        DataProduct result = dataCatalogService.createDataProduct(request.getDataProduct());
        DataProductCreateResponse.Builder responseBuilder = DataProductCreateResponse.newBuilder();
        responseBuilder.setDataProduct(result);
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();

    }

    @Override
    public void updateDataProduct(DataProductUpdateRequest request,
                                  StreamObserver<DataProductUpdateResponse> responseObserver) {
        super.updateDataProduct(request, responseObserver);
    }

    @Override
    public void getDataProduct(DataProductGetRequest request, StreamObserver<DataProductGetResponse> responseObserver) {
        super.getDataProduct(request, responseObserver);
    }

    @Override
    public void removeDataProduct(DataProductDeleteRequest request,
                                  StreamObserver<DataProductDeleteResponse> responseObserver) {
        super.removeDataProduct(request, responseObserver);
    }

    @Override
    public void registerReplicaLocation(DataReplicaCreateRequest request,
                                        StreamObserver<DataReplicaCreateResponse> responseObserver) {

        logger.info("Creating Replica for a data product {}", request.getDataReplica());
        if (request.getDataReplica() == null) {
            logger.debug("No Data Replica Location");
        }
        DataReplicaLocation result = dataCatalogService.createDataReplica(request.getDataReplica());

        DataReplicaCreateResponse.Builder responseBuilder = DataReplicaCreateResponse.newBuilder();
        responseBuilder.setDataReplica(result);
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateReplicaLocation(DataReplicaUpdateRequest request,
                                      StreamObserver<DataReplicaUpdateResponse> responseObserver) {

        DataReplicaLocation result = dataCatalogService.updateDataReplica(request.getDataReplica());

        DataReplicaUpdateResponse.Builder responseBuilder = DataReplicaUpdateResponse.newBuilder();
        responseBuilder.setDataReplica(result);
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getReplicaLocation(DataReplicaGetRequest request,
                                   StreamObserver<DataReplicaGetResponse> responseObserver) {
        super.getReplicaLocation(request, responseObserver);
    }

    @Override
    public void removeReplicaLocation(DataReplicaDeleteRequest request,
                                      StreamObserver<DataReplicaDeleteResponse> responseObserver) {
        super.removeReplicaLocation(request, responseObserver);
    }

    @Override
    public void getAllReplicaLocation(AllDataReplicaGetRequest request,
                                      StreamObserver<AllDataReplicaGetResponse> responseObserver) {
        super.getAllReplicaLocation(request, responseObserver);
    }

    @Override
    public void removeAllReplicaLocation(AllDataReplicaDeleteRequest request,
                                         StreamObserver<AllDataReplicaDeleteResponse> responseObserver) {
        super.removeAllReplicaLocation(request, responseObserver);
    }


}
