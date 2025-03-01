/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.airavata.replicacatalog.resource.service;



import io.grpc.stub.StreamObserver;
import org.apache.airavata.replicacatalog.resource.stubs.common.*;
import org.apache.airavata.replicacatalog.resource.stubs.s3.*;

import java.util.Optional;

public interface IResourceService {

    public void init();
    public void destroy();
    public SecretForStorage getSecretForStorage(SecretForStorageGetRequest request) throws Exception;

    public SecretForStorage registerSecretForStorage(SecretForStorageCreateRequest request) throws Exception;

    public boolean deleteSecretForStorage(SecretForStorageDeleteRequest request) throws Exception;

    public StorageListResponse searchStorages(StorageSearchRequest request) throws Exception;

    public StorageListResponse listStorage(StorageListRequest request) throws Exception;

    public GenericResource createGenericResource(GenericResourceCreateRequest request) throws Exception;

    public GenericResource getGenericResource(GenericResourceGetRequest request) throws Exception;

    public GenericResource updateGenericResource(GenericResourceUpdateRequest request) throws Exception;

    public GenericResource deleteGenericResource(GenericResourceDeleteRequest request) throws Exception;

//    public S3StorageListResponse listS3Storage(S3StorageListRequest request) throws Exception;
//    public Optional<S3Storage> getS3Storage(S3StorageGetRequest request) throws Exception;
//    public S3Storage createS3Storage(S3StorageCreateRequest request) throws Exception;
//    public boolean updateS3Storage(S3StorageUpdateRequest request) throws Exception;
//    public boolean deleteS3Storage(S3StorageDeleteRequest request) throws Exception;
//

    StorageTypeResolveResponse resolveStorageType(StorageTypeResolveRequest request) throws Exception;
}
