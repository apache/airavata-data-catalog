package org.apache.airavata.replicacatalog.catalogapi.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.grpc.stub.StreamObserver;
import org.apache.airavata.replicacatalog.catalog.service.ReplicaCatalogAPIServiceGrpc;
import org.apache.airavata.replicacatalog.catalog.stubs.AllDataReplicaDeleteRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.AllDataReplicaDeleteResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.AllDataReplicaGetRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.AllDataReplicaGetResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaCreateRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaCreateResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaDeleteRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaDeleteResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaGetRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaGetResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaLocation;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaUpdateRequest;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaUpdateResponse;
import org.apache.airavata.replicacatalog.catalog.stubs.Error;
import org.apache.airavata.replicacatalog.catalog.stubs.ErrorCode;
import org.apache.airavata.replicacatalog.catalog.stubs.ReplicaGroupEntry;
import org.apache.airavata.replicacatalog.catalogapi.mapper.DataReplicaMapper;
import org.apache.airavata.replicacatalog.exception.InvalidDataException;
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
    DataReplicaMapper replicaMapper = new DataReplicaMapper();

    @Autowired
    IReplicaCatalogService catalogService;

    @Override
    public void registerReplicaLocation(DataReplicaCreateRequest request, StreamObserver<DataReplicaCreateResponse> responseObserver) {

        logger.info("Creating Replica for a data product {}", request.getDataReplica());
        Error.Builder  errorBuilder = Error.newBuilder();
        DataReplicaLocation result = DataReplicaLocation.newBuilder().build();
        if (!request.hasDataReplica()) {
            logger.debug("No Data Replica Location");
            errorBuilder.setMessage("Replica details not available in the DataReplicaCreateRequest")
                    .setCode(ErrorCode.INVALID_DATA).build();
        } else if (request.getDataReplica().getDataProductUri().isBlank()){
            logger.debug("No data product uri for the replica location");
            errorBuilder.setMessage("Data product uri not available in the replica location in the DataReplicaCreateRequest")
                    .setCode(ErrorCode.INVALID_DATA).build();
        }else if (request.getDataReplica().getStorageType().getNumber()==0){
            logger.debug("No storage type for the replica location");
            errorBuilder.setMessage("Storage type not available in the replica location in the DataReplicaCreateRequest")
                    .setCode(ErrorCode.INVALID_DATA).build();
        }
        else {
            try {
                result = catalogService.createDataReplica(request.getDataReplica());
            } catch (Exception e) {
                logger.error("Error in creating Replica location",e);
            }
        }
        DataReplicaCreateResponse.Builder responseBuilder = DataReplicaCreateResponse.newBuilder();
        responseBuilder.setDataReplica(result);
        responseBuilder.setError(errorBuilder.build());
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateReplicaLocation(DataReplicaUpdateRequest request, StreamObserver<DataReplicaUpdateResponse> responseObserver) {

        DataReplicaLocation result = null;
        try {
            result = catalogService.updateDataReplica(request.getDataReplica());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        DataReplicaUpdateResponse.Builder responseBuilder = DataReplicaUpdateResponse.newBuilder();
        responseBuilder.setDataReplica(result);
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getReplicaLocation(DataReplicaGetRequest request, StreamObserver<DataReplicaGetResponse> responseObserver) {

        logger.info("Loading Replica for a Replica ID : {}", request.getDataReplicaId());

        DataReplicaLocation result = null;
        try {
            result = catalogService.getDataReplica(request.getDataReplicaId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        DataReplicaGetResponse.Builder responseBuilder = DataReplicaGetResponse.newBuilder();
        responseBuilder.setDataReplica(result);
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeReplicaLocation(DataReplicaDeleteRequest request, StreamObserver<DataReplicaDeleteResponse> responseObserver) {
        try {
            catalogService.deleteDataReplica(request.getDataReplicaId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        responseObserver.onNext(DataReplicaDeleteResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllReplicaLocation(AllDataReplicaGetRequest request, StreamObserver<AllDataReplicaGetResponse> responseObserver) {
        List<ReplicaGroupEntry> replicaLocations = null;
        try {
            replicaLocations = catalogService.getDataReplicas(request.getDataProductUri());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        AllDataReplicaGetResponse.Builder responseBuilder = AllDataReplicaGetResponse.newBuilder();
        replicaLocations.forEach(entry -> {
            responseBuilder.setDataProductUri(request.getDataProductUri()).addReplicaList( entry);
        });
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();

    }

    @Override
    public void removeAllReplicaLocation(AllDataReplicaDeleteRequest request, StreamObserver<AllDataReplicaDeleteResponse> responseObserver) {
        super.removeAllReplicaLocation(request, responseObserver);
    }


}
