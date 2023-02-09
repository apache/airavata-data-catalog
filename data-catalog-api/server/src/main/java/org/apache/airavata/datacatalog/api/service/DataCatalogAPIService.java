package org.apache.airavata.datacatalog.api.service;

import java.util.UUID;

import org.apache.airavata.datacatalog.api.DataCatalogAPIServiceGrpc;
import org.apache.airavata.datacatalog.api.DataProduct;
import org.apache.airavata.datacatalog.api.DataProductCreateRequest;
import org.apache.airavata.datacatalog.api.DataProductCreateResponse;
import org.apache.airavata.datacatalog.api.DataProductDeleteRequest;
import org.apache.airavata.datacatalog.api.DataProductDeleteResponse;
import org.apache.airavata.datacatalog.api.DataProductGetRequest;
import org.apache.airavata.datacatalog.api.DataProductGetResponse;
import org.apache.airavata.datacatalog.api.DataProductUpdateRequest;
import org.apache.airavata.datacatalog.api.DataProductUpdateResponse;
import org.apache.airavata.datacatalog.api.mapper.DataProductMapper;
import org.apache.airavata.datacatalog.api.model.DataProductEntity;
import org.apache.airavata.datacatalog.api.repository.DataProductRepository;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.stub.StreamObserver;

@GRpcService
public class DataCatalogAPIService extends DataCatalogAPIServiceGrpc.DataCatalogAPIServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(DataCatalogAPIService.class);

    @Autowired
    DataProductRepository dataProductRepository;

    @Autowired
    DataProductMapper dataProductMapper = new DataProductMapper();

    @Override
    public void createDataProduct(DataProductCreateRequest request,
            StreamObserver<DataProductCreateResponse> responseObserver) {

        // TODO: SharingManager.resolveUser
        logger.info("Creating data product {}", request.getDataProduct());
        DataProductEntity dataProductEntity = new DataProductEntity();
        dataProductEntity.setExternalId(UUID.randomUUID().toString());
        dataProductMapper.mapModelToEntity(request.getDataProduct(), dataProductEntity);
        DataProductEntity savedDataProductEntity = dataProductRepository.save(dataProductEntity);

        // TODO: SharingManager.grantPermissionToUser(userInfo, dataProduct,
        // Permission.OWNER)

        DataProductCreateResponse.Builder responseBuilder = DataProductCreateResponse.newBuilder();
        dataProductMapper.mapEntityToModel(savedDataProductEntity, responseBuilder.getDataProductBuilder());
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateDataProduct(DataProductUpdateRequest request,
            StreamObserver<DataProductUpdateResponse> responseObserver) {

        // TODO: check that user has access to update data product record
        // TODO: handle data product does not exist
        DataProduct dataProduct = request.getDataProduct();
        DataProductEntity dataProductEntity = dataProductRepository
                .findByExternalId(dataProduct.getDataProductId());
        dataProductMapper.mapModelToEntity(dataProduct, dataProductEntity);

        DataProductEntity savedDataProductEntity = dataProductRepository.save(dataProductEntity);

        DataProductUpdateResponse.Builder responseBuilder = DataProductUpdateResponse.newBuilder();
        dataProductMapper.mapEntityToModel(savedDataProductEntity, responseBuilder.getDataProductBuilder());
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getDataProduct(DataProductGetRequest request, StreamObserver<DataProductGetResponse> responseObserver) {
        // TODO: check that user has READ access on data product record
        // TODO: handle data product does not exist
        DataProductEntity dataProductEntity = dataProductRepository
                .findByExternalId(request.getDataProductId());
        DataProductGetResponse.Builder responseBuilder = DataProductGetResponse.newBuilder();
        dataProductMapper.mapEntityToModel(dataProductEntity, responseBuilder.getDataProductBuilder());
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteDataProduct(DataProductDeleteRequest request,
            StreamObserver<DataProductDeleteResponse> responseObserver) {
        // TODO: check that user has WRITE access on data product record
        dataProductRepository.deleteByExternalId(request.getDataProductId());
        DataProductDeleteResponse.Builder responseBuilder = DataProductDeleteResponse.newBuilder();
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
