package org.apache.airavata.replicacatalog.resource.service;

import io.grpc.stub.StreamObserver;
import org.apache.airavata.replicacatalog.catalog.stubs.DataProductCreateResponse;
import org.apache.airavata.replicacatalog.catalogapi.service.IReplicaCatalogService;
import org.apache.airavata.replicacatalog.resource.mapper.ResourceStorageMapper;
import org.apache.airavata.replicacatalog.resource.model.GenericResourceEntity;
import org.apache.airavata.replicacatalog.resource.model.StorageSecretEntity;
import org.apache.airavata.replicacatalog.resource.repository.GenericResourceRepository;
import org.apache.airavata.replicacatalog.resource.repository.StorageSecretRepository;
import org.apache.airavata.replicacatalog.resource.stubs.common.GenericResource;
import org.apache.airavata.replicacatalog.resource.stubs.common.GenericResourceCreateRequest;
import org.apache.airavata.replicacatalog.resource.stubs.common.GenericResourceGetRequest;
import org.apache.airavata.replicacatalog.resource.stubs.common.SecretForStorage;
import org.apache.airavata.replicacatalog.resource.stubs.common.SecretForStorageCreateRequest;
import org.apache.airavata.replicacatalog.resource.stubs.common.SecretForStorageGetRequest;
import org.apache.airavata.replicacatalog.resource.stubs.common.StorageCommonServiceGrpc;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@GRpcService
public class ResourceAPIService extends StorageCommonServiceGrpc.StorageCommonServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(ResourceAPIService.class);


    @Autowired
    IResourceService resourceService;

    @Override
    public void createGenericResource(GenericResourceCreateRequest request, StreamObserver<GenericResource> responseObserver) {
        logger.info("Creating Storage {}", request.getStorageId());
        GenericResource resource = null;
        try {
            resource = resourceService.createGenericResource(request);
        } catch (Exception e) {
            logger.error("Error {0}", e);
        }

        responseObserver.onNext(resource);
        responseObserver.onCompleted();
    }

    @Override
    public void getGenericResource(GenericResourceGetRequest request, StreamObserver<GenericResource> responseObserver) {
        GenericResource genericResource = null;
        try {
            genericResource = resourceService.getGenericResource(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        responseObserver.onNext(genericResource);
        responseObserver.onCompleted();
    }

    @Override
    public void registerSecretForStorage(SecretForStorageCreateRequest request, StreamObserver<SecretForStorage> responseObserver) {
        SecretForStorage secretForStorage = null;
        try {
            secretForStorage = resourceService.registerSecretForStorage(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        responseObserver.onNext(secretForStorage);
        responseObserver.onCompleted();
    }

    @Override
    public void getSecretForStorage(SecretForStorageGetRequest request, StreamObserver<SecretForStorage> responseObserver) {
        SecretForStorage secretForStorage = null;
        try {
            secretForStorage = resourceService.getSecretForStorage(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        responseObserver.onNext(secretForStorage);
        responseObserver.onCompleted();
    }
}
