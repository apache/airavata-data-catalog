package org.apache.airavata.replicacatalog.sceret.service;

import java.util.UUID;

import io.grpc.stub.StreamObserver;
import org.apache.airavata.replicacatalog.resource.stubs.common.SecretForStorage;
import org.apache.airavata.replicacatalog.sceret.mapper.ResourceSecretMapper;
import org.apache.airavata.replicacatalog.sceret.repository.S3SecretRepository;
import org.apache.airavata.replicacatalog.secret.stubs.common.SecretCommonServiceGrpc;
import org.apache.airavata.replicacatalog.secret.stubs.common.StorageSecret;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class StorageSecretAPIService extends SecretCommonServiceGrpc.SecretCommonServiceImplBase {


    private static final Logger logger = LoggerFactory.getLogger(StorageSecretAPIService.class);


    @Autowired
    ISecretService secretService;

    @Autowired
    ResourceSecretMapper secretMapper = new ResourceSecretMapper();

    @Override
    public void registerSecret(StorageSecret request, StreamObserver<StorageSecret> responseObserver) {
        StorageSecret secretResult = null;
        try {
            secretResult = secretService.registerSecretForStorage(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        responseObserver.onNext(secretResult);
        responseObserver.onCompleted();
    }
}
