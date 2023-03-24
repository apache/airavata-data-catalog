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

package org.apache.airavata.replicacatalog.sceret.service;

import org.apache.airavata.replicacatalog.secret.stubs.common.*;

import java.util.Optional;

public interface ISecretService {

    public void init();
    public void destroy();

    public StorageSecret getSecretForStorage(SecretGetRequest request) throws Exception;
    public StorageSecret registerSecretForStorage(StorageSecret request) throws Exception;
    public boolean deleteSecretForStorage(SecretDeleteRequest request) throws Exception;
    public SecretListResponse searchStorages(SecretSearchRequest request) throws Exception;
    public SecretListResponse listStorage(SecretListRequest request) throws Exception;

//    public Optional<org.apache.airavata.replicacatalog.secret.stubs.s3.S3Secret> getS3Secret(org.apache.airavata.replicacatalog.secret.stubs.s3.S3SecretGetRequest request) throws Exception;
//    public org.apache.airavata.replicacatalog.secret.stubs.s3.S3Secret createS3Secret(org.apache.airavata.replicacatalog.secret.stubs.s3.S3SecretCreateRequest request) throws Exception;
//    public boolean updateS3Secret(org.apache.airavata.replicacatalog.secret.stubs.s3.S3SecretUpdateRequest request) throws Exception;
//    public boolean deleteS3Secret(org.apache.airavata.replicacatalog.secret.stubs.s3.S3SecretDeleteRequest request) throws Exception;
//

}
