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

import org.apache.airavata.replicacatalog.resource.stubs.common.GenericResource;
import org.apache.airavata.replicacatalog.resource.stubs.common.StorageType;
import org.apache.airavata.replicacatalog.sceret.mapper.ResourceSecretMapper;
import org.apache.airavata.replicacatalog.sceret.model.S3SecretEntity;
import org.apache.airavata.replicacatalog.sceret.repository.S3SecretRepository;
import org.apache.airavata.replicacatalog.secret.stubs.common.*;
import org.apache.airavata.replicacatalog.secret.stubs.s3.*;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("SQLSecretBackend")
public class SQLISecretService implements ISecretService {

    private static final Logger logger = LoggerFactory.getLogger(SQLISecretService.class);


    @Autowired
    ResourceSecretMapper secretMapper = new ResourceSecretMapper();

    @Autowired
    private S3SecretRepository s3SecretRepository;

    private DozerBeanMapper mapper = new DozerBeanMapper();

    @Override
    public void init() {
        logger.info("Initializing database secret backend");
    }

    @Override
    public void destroy() {
        logger.info("Destroying database secret backend");
    }

    @Override
    public StorageSecret getSecretForStorage(SecretGetRequest request) throws Exception {
        return null;
    }

    @Override
    public StorageSecret registerSecretForStorage(StorageSecret request) throws Exception {
        StorageSecret.Builder storageSecret = StorageSecret.newBuilder();
        if (request.getStorageType().name().equals(StorageType.S3.name())) {
            return storageSecret.setSecret(SecretWrapper.newBuilder().setS3Secret(createS3Secret(request.getSecret().getS3Secret()))).build();
        }
        return null;
    }

    @Override
    public boolean deleteSecretForStorage(SecretDeleteRequest request) throws Exception {
        return false;
    }

    @Override
    public SecretListResponse searchStorages(SecretSearchRequest request) throws Exception {
        return null;
    }

    @Override
    public SecretListResponse listStorage(SecretListRequest request) throws Exception {
        return null;
    }


    public Optional<S3Secret> getS3Secret(S3SecretGetRequest request) throws Exception {
        Optional<S3SecretEntity> secretEty = s3SecretRepository.findBySecretId(request.getSecretId());
        return secretEty.map(s3SecretEntity -> mapper.map(s3SecretEntity, S3Secret.newBuilder().getClass()).build());
    }

    public S3Secret createS3Secret(S3Secret request) throws Exception {
        S3SecretEntity savedEntity = s3SecretRepository.save(mapper.map(request, S3SecretEntity.class));
        return mapper.map(savedEntity, S3Secret.newBuilder().getClass()).build();
    }

    public boolean updateS3Secret(S3SecretUpdateRequest request) throws Exception {
        s3SecretRepository.save(mapper.map(request, S3SecretEntity.class));
        return true;
    }

    public boolean deleteS3Secret(S3SecretDeleteRequest request) throws Exception {
        s3SecretRepository.deleteById(request.getSecretId());
        return true;
    }

}
