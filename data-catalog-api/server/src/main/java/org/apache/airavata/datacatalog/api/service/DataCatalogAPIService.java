package org.apache.airavata.datacatalog.api.service;

import java.util.UUID;

import org.apache.airavata.datacatalog.api.DataCatalogAPIServiceGrpc;
import org.apache.airavata.datacatalog.api.DataProductCreateRequest;
import org.apache.airavata.datacatalog.api.DataProductCreateResponse;
import org.apache.airavata.datacatalog.api.model.DataProductEntity;
import org.apache.airavata.datacatalog.api.repository.DataProductRepository;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.grpc.stub.StreamObserver;

@GRpcService
public class DataCatalogAPIService extends DataCatalogAPIServiceGrpc.DataCatalogAPIServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(DataCatalogAPIService.class);

    @Autowired
    DataProductRepository dataProductRepository;

    @Override
    public void createDataProduct(DataProductCreateRequest request,
            StreamObserver<DataProductCreateResponse> responseObserver) {

        logger.info("Creating data product {}", request.getDataProduct());
        DataProductEntity dataProductEntity = new DataProductEntity();
        dataProductEntity.setExternalId(UUID.randomUUID().toString());
        dataProductEntity.setName(request.getDataProduct().getName());
        if (request.getDataProduct().hasParentDataProductId()) {
            // TODO: handle parent data product not found
            DataProductEntity parentDataProductEntity = dataProductRepository
                    .findByExternalId(request.getDataProduct().getParentDataProductId());
            dataProductEntity.setParentDataProductEntity(parentDataProductEntity);
        }
        if (request.getDataProduct().hasMetadata()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode metadata = mapper.readTree(request.getDataProduct().getMetadata());
                dataProductEntity.setMetadata(metadata);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        DataProductEntity savedDataProductEntity = dataProductRepository.save(dataProductEntity);

        DataProductCreateResponse.Builder responseBuilder = DataProductCreateResponse.newBuilder();
        responseBuilder.getDataProductBuilder()
                .setDataProductId(savedDataProductEntity.getExternalId())
                .setName(savedDataProductEntity.getName()).build();
        if (savedDataProductEntity.getParentDataProductEntity() != null) {
            responseBuilder.getDataProductBuilder()
                    .setParentDataProductId(savedDataProductEntity.getParentDataProductEntity().getExternalId());
        }
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
