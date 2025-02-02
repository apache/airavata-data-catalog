package org.apache.airavata.datacatalog.api.service.impl;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.apache.airavata.datacatalog.api.GrantPermissionRequest;
import org.apache.airavata.datacatalog.api.GrantPermissionResponse;
import org.apache.airavata.datacatalog.api.GrantPermissionToGroupRequest;
import org.apache.airavata.datacatalog.api.Permission;
import org.apache.airavata.datacatalog.api.RevokePermissionFromGroupRequest;
import org.apache.airavata.datacatalog.api.RevokePermissionRequest;
import org.apache.airavata.datacatalog.api.RevokePermissionResponse;
import org.apache.airavata.datacatalog.api.SearchGroupsResponse;
import org.apache.airavata.datacatalog.api.SearchRequest;
import org.apache.airavata.datacatalog.api.SearchUsersResponse;
import org.apache.airavata.datacatalog.api.SharingServiceGrpc;
import org.apache.airavata.datacatalog.api.sharing.SharingManager;
import org.apache.airavata.datacatalog.api.sharing.exception.SharingException;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * In the same Spring Boot application as DataCatalogServiceImpl,
 * mark it with @GRpcService to expose all RPC methods of SharingService 
 * (such as SearchUsers, SearchGroups, Grant/Revoke Permission, etc.) externally.
 */
@GRpcService
public class SharingServiceImpl extends SharingServiceGrpc.SharingServiceImplBase {

    @Autowired
    private SharingManager sharingManager;

    @Override
    public void searchUsers(SearchRequest request, StreamObserver<SearchUsersResponse> responseObserver) {
        try {
            // get keyword and tenant ID
            String searchQuery = request.getQuery();
            String tenantId = request.getTenantId();

            
            var users = sharingManager.searchUsers(searchQuery, tenantId);

            
            SearchUsersResponse response = SearchUsersResponse.newBuilder()
                    .addAllUsers(users)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (SharingException e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("searchUsers failed: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void searchGroups(SearchRequest request, StreamObserver<SearchGroupsResponse> responseObserver) {
        try {
            String searchQuery = request.getQuery();
            String tenantId = request.getTenantId();

            var groups = sharingManager.searchGroups(searchQuery, tenantId);

            SearchGroupsResponse response = SearchGroupsResponse.newBuilder()
                    .addAllGroups(groups)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (SharingException e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("searchGroups failed: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void grantPermissionToUser(GrantPermissionRequest request,
                                      StreamObserver<GrantPermissionResponse> responseObserver) {
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
            responseObserver.onError(Status.INTERNAL
                    .withDescription("grantPermissionToUser failed: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void revokePermissionFromUser(RevokePermissionRequest request,
                                         StreamObserver<RevokePermissionResponse> responseObserver) {
        try {
            sharingManager.revokePermissionFromUser(
                    request.getUserInfo(),
                    request.getDataProduct(),
                    request.getPermission()
            );

            responseObserver.onNext(RevokePermissionResponse.newBuilder().build());
            responseObserver.onCompleted();
        } catch (SharingException e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("revokePermissionFromUser failed: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void grantPermissionToGroup(GrantPermissionToGroupRequest request,
                                       StreamObserver<GrantPermissionResponse> responseObserver) {
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
            responseObserver.onError(Status.INTERNAL
                    .withDescription("grantPermissionToGroup failed: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void revokePermissionFromGroup(RevokePermissionFromGroupRequest request,
                                          StreamObserver<RevokePermissionResponse> responseObserver) {
        try {
            sharingManager.revokePermissionFromGroup(
                    request.getGroupInfo(),
                    request.getDataProduct(),
                    request.getPermission()
            );
            responseObserver.onNext(RevokePermissionResponse.newBuilder().build());
            responseObserver.onCompleted();
        } catch (SharingException e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("revokePermissionFromGroup failed: " + e.getMessage())
                    .asRuntimeException());
        }
    }
}
