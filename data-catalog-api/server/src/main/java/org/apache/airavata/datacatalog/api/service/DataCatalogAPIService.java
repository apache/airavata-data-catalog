package org.apache.airavata.datacatalog.api.service;

import java.util.List;
import java.util.UUID;

import org.apache.airavata.datacatalog.api.DataCatalogAPIServiceGrpc;
import org.apache.airavata.datacatalog.api.DataProduct;
import org.apache.airavata.datacatalog.api.DataProductAddToMetadataSchemaRequest;
import org.apache.airavata.datacatalog.api.DataProductAddToMetadataSchemaResponse;
import org.apache.airavata.datacatalog.api.DataProductCreateRequest;
import org.apache.airavata.datacatalog.api.DataProductCreateResponse;
import org.apache.airavata.datacatalog.api.DataProductDeleteRequest;
import org.apache.airavata.datacatalog.api.DataProductDeleteResponse;
import org.apache.airavata.datacatalog.api.DataProductGetRequest;
import org.apache.airavata.datacatalog.api.DataProductGetResponse;
import org.apache.airavata.datacatalog.api.DataProductRemoveFromMetadataSchemaRequest;
import org.apache.airavata.datacatalog.api.DataProductRemoveFromMetadataSchemaResponse;
import org.apache.airavata.datacatalog.api.DataProductUpdateRequest;
import org.apache.airavata.datacatalog.api.DataProductUpdateResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaCreateRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaCreateResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaDeleteRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaDeleteResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaField;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldCreateRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldCreateResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldDeleteRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldDeleteResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldListRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldUpdateRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldUpdateResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaGetRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaGetResponse;
import org.apache.airavata.datacatalog.api.mapper.DataProductMapper;
import org.apache.airavata.datacatalog.api.mapper.MetadataSchemaFieldMapper;
import org.apache.airavata.datacatalog.api.mapper.MetadataSchemaMapper;
import org.apache.airavata.datacatalog.api.model.DataProductEntity;
import org.apache.airavata.datacatalog.api.model.MetadataSchemaEntity;
import org.apache.airavata.datacatalog.api.model.MetadataSchemaFieldEntity;
import org.apache.airavata.datacatalog.api.repository.DataProductRepository;
import org.apache.airavata.datacatalog.api.repository.MetadataSchemaFieldRepository;
import org.apache.airavata.datacatalog.api.repository.MetadataSchemaRepository;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

@GRpcService
@Transactional
public class DataCatalogAPIService extends DataCatalogAPIServiceGrpc.DataCatalogAPIServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(DataCatalogAPIService.class);

    @Autowired
    DataProductRepository dataProductRepository;

    @Autowired
    MetadataSchemaRepository metadataSchemaRepository;

    @Autowired
    MetadataSchemaFieldRepository metadataSchemaFieldRepository;

    @Autowired
    DataProductMapper dataProductMapper;

    @Autowired
    MetadataSchemaMapper metadataSchemaMapper;

    @Autowired
    MetadataSchemaFieldMapper metadataSchemaFieldMapper;

    @Override
    public void createDataProduct(DataProductCreateRequest request,
            StreamObserver<DataProductCreateResponse> responseObserver) {

        // TODO: SharingManager.resolveUser
        logger.info("Creating data product {}", request.getDataProduct());
        DataProductEntity dataProductEntity = new DataProductEntity();
        dataProductEntity.setExternalId(UUID.randomUUID().toString());
        dataProductMapper.mapModelToEntity(request.getDataProduct(), dataProductEntity);
        DataProductEntity savedDataProductEntity = dataProductRepository.saveAndFlush(dataProductEntity);

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

    @Override
    public void addDataProductToMetadataSchema(DataProductAddToMetadataSchemaRequest request,
            StreamObserver<DataProductAddToMetadataSchemaResponse> responseObserver) {
        String dataProductId = request.getDataProductId();
        // TODO: handle data product not found
        DataProductEntity dataProduct = dataProductRepository.findByExternalId(dataProductId);
        String schemaName = request.getSchemaName();
        // TODO: handle metadata schema not found
        MetadataSchemaEntity metadataSchemaEntity = metadataSchemaRepository.findBySchemaName(schemaName);
        dataProduct.addMetadataSchema(metadataSchemaEntity);

        DataProductAddToMetadataSchemaResponse.Builder responseBuilder = DataProductAddToMetadataSchemaResponse
                .newBuilder();
        dataProductMapper.mapEntityToModel(dataProduct, responseBuilder.getDataProductBuilder());
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getMetadataSchema(MetadataSchemaGetRequest request,
            StreamObserver<MetadataSchemaGetResponse> responseObserver) {
        MetadataSchemaEntity metadataSchemaEntity = metadataSchemaRepository.findBySchemaName(request.getSchemaName());
        if (metadataSchemaEntity == null) {
            responseObserver.onError(Status.NOT_FOUND.asException());
            responseObserver.onCompleted();
        }
        MetadataSchemaGetResponse.Builder responseBuilder = MetadataSchemaGetResponse.newBuilder();
        metadataSchemaMapper.mapEntityToModel(metadataSchemaEntity, responseBuilder.getMetadataSchemaBuilder());
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void createMetadataSchema(MetadataSchemaCreateRequest request,
            StreamObserver<MetadataSchemaCreateResponse> responseObserver) {

        MetadataSchemaEntity metadataSchemaEntity = new MetadataSchemaEntity();
        metadataSchemaMapper.mapModelToEntity(request.getMetadataSchema(), metadataSchemaEntity);
        MetadataSchemaEntity savedMetadataSchemaEntity = metadataSchemaRepository.save(metadataSchemaEntity);

        MetadataSchemaCreateResponse.Builder responseBuilder = MetadataSchemaCreateResponse.newBuilder();
        metadataSchemaMapper.mapEntityToModel(savedMetadataSchemaEntity, responseBuilder.getMetadataSchemaBuilder());
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void createMetadataSchemaField(MetadataSchemaFieldCreateRequest request,
            StreamObserver<MetadataSchemaFieldCreateResponse> responseObserver) {

        MetadataSchemaFieldEntity metadataSchemaFieldEntity = new MetadataSchemaFieldEntity();
        metadataSchemaFieldMapper.mapModelToEntity(request.getMetadataSchemaField(), metadataSchemaFieldEntity);
        MetadataSchemaFieldEntity savedMetadataSchemaFieldEntity = metadataSchemaFieldRepository
                .save(metadataSchemaFieldEntity);

        MetadataSchemaFieldCreateResponse.Builder responseBuilder = MetadataSchemaFieldCreateResponse.newBuilder();
        metadataSchemaFieldMapper.mapEntityToModel(savedMetadataSchemaFieldEntity,
                responseBuilder.getMetadataSchemaFieldBuilder());
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteMetadataSchema(MetadataSchemaDeleteRequest request,
            StreamObserver<MetadataSchemaDeleteResponse> responseObserver) {
        // TODO: check that user has write access on metadata schema
        // TODO: handle metadata schema not found
        MetadataSchemaEntity metadataSchemaEntity = metadataSchemaRepository
                .findBySchemaName(request.getMetadataSchema().getSchemaName());
        metadataSchemaRepository.delete(metadataSchemaEntity);
        MetadataSchemaDeleteResponse.Builder responseBuilder = MetadataSchemaDeleteResponse.newBuilder();
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteMetadataSchemaField(MetadataSchemaFieldDeleteRequest request,
            StreamObserver<MetadataSchemaFieldDeleteResponse> responseObserver) {
        // TODO: check that user has write access on metadata schema field
        // TODO: handle metadata schema field not found
        MetadataSchemaFieldEntity metadataSchemaFieldEntity = metadataSchemaFieldRepository
                .findByFieldNameAndSchema_SchemaName(request.getMetadataSchemaField().getFieldName(),
                        request.getMetadataSchemaField().getSchemaName());
        metadataSchemaFieldRepository.delete(metadataSchemaFieldEntity);
        MetadataSchemaFieldDeleteResponse.Builder responseBuilder = MetadataSchemaFieldDeleteResponse.newBuilder();
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getMetadataSchemaFields(MetadataSchemaFieldListRequest request,
            StreamObserver<MetadataSchemaFieldListResponse> responseObserver) {

        // TODO: handle case where schema doesn't exist
        List<MetadataSchemaFieldEntity> metadataSchemaFieldEntities = metadataSchemaFieldRepository
                .findByMetadataSchema_SchemaName(request.getSchemaName());

        MetadataSchemaFieldListResponse.Builder responseBuilder = MetadataSchemaFieldListResponse.newBuilder();
        for (MetadataSchemaFieldEntity metadataSchemaFieldEntity : metadataSchemaFieldEntities) {
            MetadataSchemaField.Builder builder = responseBuilder.addMetadataSchemaFieldsBuilder();
            metadataSchemaFieldMapper.mapEntityToModel(metadataSchemaFieldEntity, builder);
        }
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeDataProductFromMetadataSchema(DataProductRemoveFromMetadataSchemaRequest request,
            StreamObserver<DataProductRemoveFromMetadataSchemaResponse> responseObserver) {

        String dataProductId = request.getDataProductId();
        // TODO: handle data product not found
        DataProductEntity dataProduct = dataProductRepository.findByExternalId(dataProductId);
        String schemaName = request.getSchemaName();
        // TODO: handle metadata schema not found
        MetadataSchemaEntity metadataSchemaEntity = metadataSchemaRepository.findBySchemaName(schemaName);
        dataProduct.removeMetadataSchema(metadataSchemaEntity);

        DataProductRemoveFromMetadataSchemaResponse.Builder responseBuilder = DataProductRemoveFromMetadataSchemaResponse
                .newBuilder();
        dataProductMapper.mapEntityToModel(dataProduct, responseBuilder.getDataProductBuilder());
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateMetadataSchemaField(MetadataSchemaFieldUpdateRequest request,
            StreamObserver<MetadataSchemaFieldUpdateResponse> responseObserver) {

        // TODO: check that user has write access on metadata schema field
        // TODO: handle metadata schema field not found
        MetadataSchemaFieldEntity metadataSchemaFieldEntity = metadataSchemaFieldRepository
                .findByFieldNameAndSchema_SchemaName(request.getMetadataSchemaField().getFieldName(),
                        request.getMetadataSchemaField().getSchemaName());
        metadataSchemaFieldMapper.mapModelToEntity(request.getMetadataSchemaField(), metadataSchemaFieldEntity);
        metadataSchemaFieldRepository.save(metadataSchemaFieldEntity);

        MetadataSchemaFieldUpdateResponse.Builder responseBuilder = MetadataSchemaFieldUpdateResponse.newBuilder();
        metadataSchemaFieldMapper.mapEntityToModel(metadataSchemaFieldEntity,
                responseBuilder.getMetadataSchemaFieldBuilder());
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
