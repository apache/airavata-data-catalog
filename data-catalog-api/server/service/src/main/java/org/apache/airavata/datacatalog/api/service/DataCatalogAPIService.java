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
import org.apache.airavata.datacatalog.api.MetadataSchemaListRequest;
import org.apache.airavata.datacatalog.api.MetadataSchemaListResponse;
import org.apache.airavata.datacatalog.api.Permission;
import org.apache.airavata.datacatalog.api.UserInfo;
import org.apache.airavata.datacatalog.api.exception.EntityNotFoundException;
import org.apache.airavata.datacatalog.api.exception.MetadataSchemaSqlParseException;
import org.apache.airavata.datacatalog.api.exception.MetadataSchemaSqlValidateException;
import org.apache.airavata.datacatalog.api.query.MetadataSchemaQueryResult;
import org.apache.airavata.datacatalog.api.sharing.SharingManager;
import org.apache.airavata.datacatalog.api.sharing.exception.SharingException;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import org.apache.airavata.datacatalog.api.GrantPermissionToUserRequest;
import org.apache.airavata.datacatalog.api.GrantPermissionToUserResponse;
import org.apache.airavata.datacatalog.api.GrantPermissionToGroupRequest;
import org.apache.airavata.datacatalog.api.GrantPermissionToGroupResponse;

@GRpcService
public class DataCatalogAPIService extends DataCatalogAPIServiceGrpc.DataCatalogAPIServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(DataCatalogAPIService.class);

    @Autowired
    DataCatalogService dataCatalogService;

    @Autowired
    SharingManager sharingManager;

    @Override
    public void createDataProduct(DataProductCreateRequest request,
            StreamObserver<DataProductCreateResponse> responseObserver) {

        logger.info("Creating data product {}", request.getDataProduct());

        // Set the owner as the requesting user
        DataProduct dataProduct = request.getDataProduct().toBuilder().setOwner(request.getUserInfo()).build();

        DataProduct result;
        try {
            result = dataCatalogService.createDataProduct(dataProduct);
            responseObserver.onNext(DataProductCreateResponse.newBuilder().setDataProduct(result).build());
            responseObserver.onCompleted();
        } catch (SharingException e) {
            logger.error("Sharing error when trying to create data product", e);
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
        }

    }

    @Override
    public void updateDataProduct(DataProductUpdateRequest request,
            StreamObserver<DataProductUpdateResponse> responseObserver) {

        // check that user has access to update data product record
        if (!checkHasPermission(request.getUserInfo(), request.getDataProduct(), Permission.WRITE_METADATA,
                responseObserver)) {
            return;
        }

        try {
            DataProduct savedDataProduct = dataCatalogService.updateDataProduct(request.getDataProduct());

            responseObserver.onNext(DataProductUpdateResponse.newBuilder().setDataProduct(savedDataProduct).build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asException());
        }
    }

    @Override
    public void getDataProduct(DataProductGetRequest request, StreamObserver<DataProductGetResponse> responseObserver) {

        try {
            DataProduct dataProduct = dataCatalogService.getDataProduct(request.getDataProductId());
            // check that user has READ_METADATA access on data product record
            if (!checkHasPermission(request.getUserInfo(), dataProduct, Permission.READ_METADATA, responseObserver)) {
                return;
            }

            responseObserver.onNext(DataProductGetResponse.newBuilder().setDataProduct(dataProduct).build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.asException());
        }
    }

    @Override
    public void deleteDataProduct(DataProductDeleteRequest request,
            StreamObserver<DataProductDeleteResponse> responseObserver) {
        try {
            DataProduct dataProduct = dataCatalogService.getDataProduct(request.getDataProductId());
            // check that user has WRITE_METADATA access on data product record
            if (!checkHasPermission(request.getUserInfo(), dataProduct, Permission.WRITE_METADATA, responseObserver)) {
                return;
            }
            dataCatalogService.deleteDataProduct(request.getDataProductId());

            responseObserver.onNext(DataProductDeleteResponse.newBuilder().build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.asException());
        }
    }

    @Override
    public void addDataProductToMetadataSchema(DataProductAddToMetadataSchemaRequest request,
            StreamObserver<DataProductAddToMetadataSchemaResponse> responseObserver) {

        String dataProductId = request.getDataProductId();
        String schemaName = request.getSchemaName();

        try {
            DataProduct checkDataProduct = dataCatalogService.getDataProduct(request.getDataProductId());
            // check that user has WRITE_METADATA access on data product record
            if (!checkHasPermission(request.getUserInfo(), checkDataProduct, Permission.WRITE_METADATA,
                    responseObserver)) {
                return;
            }
            DataProduct dataProduct = dataCatalogService.addDataProductToMetadataSchema(dataProductId, schemaName);

            responseObserver
                    .onNext(DataProductAddToMetadataSchemaResponse.newBuilder().setDataProduct(dataProduct).build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asException());
        }
    }

    @Override
    public void removeDataProductFromMetadataSchema(DataProductRemoveFromMetadataSchemaRequest request,
            StreamObserver<DataProductRemoveFromMetadataSchemaResponse> responseObserver) {

        String dataProductId = request.getDataProductId();
        String schemaName = request.getSchemaName();
        try {
            DataProduct checkDataProduct = dataCatalogService.getDataProduct(request.getDataProductId());
            // check that user has WRITE_METADATA access on data product record
            if (!checkHasPermission(request.getUserInfo(), checkDataProduct, Permission.WRITE_METADATA,
                    responseObserver)) {
                return;
            }
            DataProduct dataProduct = dataCatalogService.removeDataProductFromMetadataSchema(dataProductId, schemaName);

            responseObserver
                    .onNext(DataProductRemoveFromMetadataSchemaResponse.newBuilder().setDataProduct(dataProduct)
                            .build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asException());
        }
    }

    @Override
    public void searchDataProducts(DataProductSearchRequest request,
            StreamObserver<DataProductSearchResponse> responseObserver) {

        try {
            MetadataSchemaQueryResult searchResult = dataCatalogService.searchDataProducts(
                    request.getUserInfo(),
                    request.getSql(),
                    request.getPage(),
                    request.getPageSize()
            );
            List<DataProduct> dataProducts = searchResult.dataProducts();
            int totalCount = searchResult.totalCount();
            responseObserver.onNext(
                    DataProductSearchResponse.newBuilder()
                            .addAllDataProducts(dataProducts)
                            .setTotalCount(totalCount)
                            .build()
            );
            responseObserver.onCompleted();
        } catch (MetadataSchemaSqlParseException e) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Failed to parse SQL query.").asException());
        } catch (MetadataSchemaSqlValidateException e) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Failed to validate SQL query.").asException());
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
        }
    }

    @Override
    public void getMetadataSchemas(MetadataSchemaListRequest request,
                                    StreamObserver<MetadataSchemaListResponse> responseObserver) {
        List<MetadataSchema> fields = dataCatalogService.getMetadataSchemas();
        responseObserver.onNext(MetadataSchemaListResponse.newBuilder().addAllMetadataSchemas(fields).build());
        responseObserver.onCompleted();
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

    private <T> boolean checkHasPermission(UserInfo userInfo, DataProduct dataProduct, Permission permission,
            StreamObserver<T> responseObserver) {
        try {
            boolean userHasAccess = sharingManager.userHasAccess(userInfo, dataProduct,
                    permission);
            if (!userHasAccess) {
                responseObserver.onError(Status.PERMISSION_DENIED
                        .withDescription("user does not have " + permission + " permission")
                        .asException());
                return false;
            } else {
                return true;
            }
        } catch (SharingException e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
        }
        return false;
    }

    @Override
    public void grantPermissionToUser(GrantPermissionToUserRequest request,
                                      StreamObserver<GrantPermissionToUserResponse> responseObserver) {

        try {

            DataProduct dataProduct = dataCatalogService.getDataProduct(request.getDataProductId());

            sharingManager.grantPermissionToUser(
                    request.getTargetUser(),
                    dataProduct,
                    request.getPermission(),
                    request.getUserInfo()
            );
            responseObserver.onNext(GrantPermissionToUserResponse.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage()).asException());
        } catch (Exception e) {
            logger.error("Failed to grant permission to user", e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription(e.getMessage()).asException());
        }
    }

    @Override
    public void grantPermissionToGroup(GrantPermissionToGroupRequest request,
                                       StreamObserver<GrantPermissionToGroupResponse> responseObserver) {

        try {
            DataProduct dataProduct = dataCatalogService.getDataProduct(request.getDataProductId());

            if (!checkHasPermission(request.getUserInfo(), dataProduct, Permission.OWNER, responseObserver)) {
                return;
            }

            sharingManager.grantPermissionToGroup(
                    request.getTargetGroup(),
                    dataProduct,
                    request.getPermission(),
                    request.getUserInfo()
            );

            responseObserver.onNext(GrantPermissionToGroupResponse.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage()).asException());
        } catch (Exception e) {
            logger.error("Failed to grant permission to group", e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription(e.getMessage()).asException());
        }
    }
}