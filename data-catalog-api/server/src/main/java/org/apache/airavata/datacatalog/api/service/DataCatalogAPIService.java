package org.apache.airavata.datacatalog.api.service;

import java.util.List;

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
import org.apache.airavata.datacatalog.api.DataProductSearchRequest;
import org.apache.airavata.datacatalog.api.DataProductSearchResponse;
import org.apache.airavata.datacatalog.api.DataProductUpdateRequest;
import org.apache.airavata.datacatalog.api.DataProductUpdateResponse;
import org.apache.airavata.datacatalog.api.MetadataSchema;
import org.apache.airavata.datacatalog.api.MetadataSchemaCreateRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaCreateResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaDeleteRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaDeleteResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaField;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldCreateRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldCreateResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldDeleteRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldDeleteResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldGetRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldGetResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldListRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldUpdateRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaFieldUpdateResponse;
import org.apache.airavata.datacatalog.api.MetadataSchemaGetRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaGetResponse;
import org.apache.airavata.datacatalog.api.exception.EntityNotFoundException;
import org.apache.airavata.datacatalog.api.exception.MetadataSchemaSqlParseException;
import org.apache.airavata.datacatalog.api.exception.MetadataSchemaSqlValidateException;
import org.apache.airavata.datacatalog.api.query.MetadataSchemaQueryResult;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

@GRpcService
public class DataCatalogAPIService extends DataCatalogAPIServiceGrpc.DataCatalogAPIServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(DataCatalogAPIService.class);

    @Autowired
    DataCatalogService dataCatalogService;

    @Override
    public void createDataProduct(DataProductCreateRequest request,
            StreamObserver<DataProductCreateResponse> responseObserver) {

        logger.info("Creating data product {}", request.getDataProduct());

        DataProduct result = dataCatalogService.createDataProduct(request.getDataProduct());

        responseObserver.onNext(DataProductCreateResponse.newBuilder().setDataProduct(result).build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateDataProduct(DataProductUpdateRequest request,
            StreamObserver<DataProductUpdateResponse> responseObserver) {

        // TODO: check that user has access to update data product record
        try {
            DataProduct savedDataProduct = dataCatalogService.updateDataProduct(request.getDataProduct());

            responseObserver.onNext(DataProductUpdateResponse.newBuilder().setDataProduct(savedDataProduct).build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asException());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getDataProduct(DataProductGetRequest request, StreamObserver<DataProductGetResponse> responseObserver) {
        // TODO: check that user has READ access on data product record
        try {
            DataProduct dataProduct = dataCatalogService.getDataProduct(request.getDataProductId());

            responseObserver.onNext(DataProductGetResponse.newBuilder().setDataProduct(dataProduct).build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.asException());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void deleteDataProduct(DataProductDeleteRequest request,
            StreamObserver<DataProductDeleteResponse> responseObserver) {
        // TODO: check that user has WRITE access on data product record
        dataCatalogService.deleteDataProduct(request.getDataProductId());

        responseObserver.onNext(DataProductDeleteResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void addDataProductToMetadataSchema(DataProductAddToMetadataSchemaRequest request,
            StreamObserver<DataProductAddToMetadataSchemaResponse> responseObserver) {

        String dataProductId = request.getDataProductId();
        String schemaName = request.getSchemaName();
        try {
            DataProduct dataProduct = dataCatalogService.addDataProductToMetadataSchema(dataProductId, schemaName);

            responseObserver
                    .onNext(DataProductAddToMetadataSchemaResponse.newBuilder().setDataProduct(dataProduct).build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asException());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void removeDataProductFromMetadataSchema(DataProductRemoveFromMetadataSchemaRequest request,
            StreamObserver<DataProductRemoveFromMetadataSchemaResponse> responseObserver) {

        String dataProductId = request.getDataProductId();
        String schemaName = request.getSchemaName();
        try {
            DataProduct dataProduct = dataCatalogService.removeDataProductFromMetadataSchema(dataProductId, schemaName);

            responseObserver
                    .onNext(DataProductRemoveFromMetadataSchemaResponse.newBuilder().setDataProduct(dataProduct)
                            .build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asException());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void searchDataProducts(DataProductSearchRequest request,
            StreamObserver<DataProductSearchResponse> responseObserver) {

        try {
            MetadataSchemaQueryResult searchResult = dataCatalogService.searchDataProducts(request.getSql());
            List<DataProduct> dataProducts = searchResult.dataProducts();
            responseObserver.onNext(DataProductSearchResponse.newBuilder().addAllDataProducts(dataProducts).build());
            responseObserver.onCompleted();
        } catch (MetadataSchemaSqlParseException e) {
            responseObserver
                    .onError(Status.INVALID_ARGUMENT.withDescription("Failed to parse SQL query.").asException());
            responseObserver.onCompleted();
        } catch (MetadataSchemaSqlValidateException e) {
            responseObserver
                    .onError(Status.INVALID_ARGUMENT.withDescription("Failed to validate SQL query.").asException());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getMetadataSchema(MetadataSchemaGetRequest request,
            StreamObserver<MetadataSchemaGetResponse> responseObserver) {
        try {
            MetadataSchema metadataSchema = dataCatalogService.getMetadataSchema(request.getSchemaName());

            responseObserver.onNext(MetadataSchemaGetResponse.newBuilder().setMetadataSchema(metadataSchema).build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.asException());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void createMetadataSchema(MetadataSchemaCreateRequest request,
            StreamObserver<MetadataSchemaCreateResponse> responseObserver) {

        MetadataSchema metadataSchema = dataCatalogService.createMetadataSchema(request.getMetadataSchema());

        responseObserver.onNext(MetadataSchemaCreateResponse.newBuilder().setMetadataSchema(metadataSchema).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getMetadataSchemaField(MetadataSchemaFieldGetRequest request,
            StreamObserver<MetadataSchemaFieldGetResponse> responseObserver) {
        try {
            MetadataSchemaField metadataSchemaField = dataCatalogService.getMetadataSchemaField(request.getSchemaName(),
                    request.getFieldName());
            responseObserver.onNext(
                    MetadataSchemaFieldGetResponse.newBuilder().setMetadataSchemaField(metadataSchemaField).build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.asException());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void createMetadataSchemaField(MetadataSchemaFieldCreateRequest request,
            StreamObserver<MetadataSchemaFieldCreateResponse> responseObserver) {

        MetadataSchemaField metadataSchemaField = dataCatalogService
                .createMetadataSchemaField(request.getMetadataSchemaField());

        responseObserver.onNext(
                MetadataSchemaFieldCreateResponse.newBuilder().setMetadataSchemaField(metadataSchemaField).build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteMetadataSchema(MetadataSchemaDeleteRequest request,
            StreamObserver<MetadataSchemaDeleteResponse> responseObserver) {
        // TODO: check that user has write access on metadata schema
        dataCatalogService.deleteMetadataSchema(request.getMetadataSchema());

        responseObserver.onNext(MetadataSchemaDeleteResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteMetadataSchemaField(MetadataSchemaFieldDeleteRequest request,
            StreamObserver<MetadataSchemaFieldDeleteResponse> responseObserver) {
        // TODO: check that user has write access on metadata schema field
        dataCatalogService.deleteMetadataSchemaField(request.getMetadataSchemaField());

        responseObserver.onNext(MetadataSchemaFieldDeleteResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void getMetadataSchemaFields(MetadataSchemaFieldListRequest request,
            StreamObserver<MetadataSchemaFieldListResponse> responseObserver) {

        List<MetadataSchemaField> fields = dataCatalogService.getMetadataSchemaFields(request.getSchemaName());

        responseObserver
                .onNext(MetadataSchemaFieldListResponse.newBuilder().addAllMetadataSchemaFields(fields).build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateMetadataSchemaField(MetadataSchemaFieldUpdateRequest request,
            StreamObserver<MetadataSchemaFieldUpdateResponse> responseObserver) {

        // TODO: check that user has write access on metadata schema field
        MetadataSchemaField metadataSchemaField = dataCatalogService
                .updateMetadataSchemaField(request.getMetadataSchemaField());

        responseObserver.onNext(
                MetadataSchemaFieldUpdateResponse.newBuilder().setMetadataSchemaField(metadataSchemaField).build());
        responseObserver.onCompleted();
    }
}
