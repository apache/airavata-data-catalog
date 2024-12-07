package org.apache.airavata.datacatalog.api.sharing;

import io.grpc.stub.StreamObserver;
import org.apache.airavata.datacatalog.api.*;
import org.apache.airavata.datacatalog.api.sharing.exception.SharingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SharingServiceImpl extends SharingServiceGrpc.SharingServiceImplBase {

    private final SimpleSharingManagerImpl sharingManager;

    @Autowired
    public SharingServiceImpl(SimpleSharingManagerImpl sharingManager) {
        this.sharingManager = sharingManager;
    }

    @Override
    public void grantPermissionToUser(GrantPermissionRequest request, StreamObserver<GrantPermissionResponse> responseObserver) {
        try {
            sharingManager.grantPermissionToUser(
                    request.getUserInfo(),
                    request.getDataProduct(),
                    request.getPermission(),
                    request.hasSharedByUser() ? request.getSharedByUser() : null
            );
            responseObserver.onNext(GrantPermissionResponse.newBuilder().build());
            responseObserver.onCompleted();
        } catch (SharingException e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void revokePermissionFromUser(RevokePermissionRequest request, StreamObserver<RevokePermissionResponse> responseObserver) {
        try {
            sharingManager.revokePermissionFromUser(
                    request.getUserInfo(),
                    request.getDataProduct(),
                    request.getPermission()
            );
            responseObserver.onNext(RevokePermissionResponse.newBuilder().build());
            responseObserver.onCompleted();
        } catch (SharingException e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void grantPermissionToGroup(GrantPermissionToGroupRequest request, StreamObserver<GrantPermissionResponse> responseObserver) {
        try {
            sharingManager.grantPermissionToGroup(
                    request.getGroupInfo(),
                    request.getDataProduct(),
                    request.getPermission(),
                    request.hasSharedByUser() ? request.getSharedByUser() : null
            );
            responseObserver.onNext(GrantPermissionResponse.newBuilder().build());
            responseObserver.onCompleted();
        } catch (SharingException e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void revokePermissionFromGroup(RevokePermissionFromGroupRequest request, StreamObserver<RevokePermissionResponse> responseObserver) {
        try {
            sharingManager.revokePermissionFromGroup(
                    request.getGroupInfo(),
                    request.getDataProduct(),
                    request.getPermission()
            );
            responseObserver.onNext(RevokePermissionResponse.newBuilder().build());
            responseObserver.onCompleted();
        } catch (SharingException e) {
            responseObserver.onError(e);
        }
    }
}
