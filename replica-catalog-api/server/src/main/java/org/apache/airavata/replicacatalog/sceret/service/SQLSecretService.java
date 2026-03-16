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

import org.apache.airavata.replicacatalog.catalog.stubs.StorageType;
import org.apache.airavata.replicacatalog.sceret.mapper.ResourceSecretMapper;
import org.apache.airavata.replicacatalog.sceret.model.GCSSecretEntity;
import org.apache.airavata.replicacatalog.sceret.model.S3SecretEntity;
import org.apache.airavata.replicacatalog.sceret.repository.GCSSecretRepository;
import org.apache.airavata.replicacatalog.sceret.repository.S3SecretRepository;
import org.apache.airavata.replicacatalog.secret.stubs.common.*;
import org.apache.airavata.replicacatalog.secret.stubs.gcs.GCSSecret;
import org.apache.airavata.replicacatalog.secret.stubs.gcs.GCSSecretGetRequest;
import org.apache.airavata.replicacatalog.secret.stubs.s3.*;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("SQLSecretBackend")
public class SQLSecretService implements ISecretService {

    private static final Logger logger = LoggerFactory.getLogger(SQLSecretService.class);


    @Autowired
    ResourceSecretMapper secretMapper = new ResourceSecretMapper();

    @Autowired
    private S3SecretRepository s3SecretRepository;

    @Autowired
    private GCSSecretRepository gcsSecretRepository;

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
    public StorageSecret getSecret(SecretGetRequest request) throws Exception {
        String id = request.getSecretId();
        StorageSecret.Builder storageSecret = StorageSecret.newBuilder();
        if (StorageType.S3.name().equals(request.getStorageType().name())) {
            S3SecretGetRequest getRequest = S3SecretGetRequest.newBuilder().setSecretId(id).build();
            Optional<S3Secret> secret = getS3Secret(getRequest);
            return storageSecret.setSecret(SecretWrapper.newBuilder().setS3Secret(secret.get())).setSecretId(id).build();
        } else if (StorageType.GCS.name().equals(request.getStorageType().name())) {
            GCSSecretGetRequest getRequest = GCSSecretGetRequest.newBuilder().setSecretId(id).build();
            Optional<GCSSecret> secret = getGCSSecret(getRequest);
            return storageSecret.setSecret(SecretWrapper.newBuilder().setGcsSecret(secret.get())).setSecretId(id).build();
        }
        return null;

    }

    @Override
    public StorageSecret registerSecretForStorage(SecretCreateRequest request) throws Exception {
        StorageSecret stoReq = request.getSecret();
        StorageSecret.Builder storageSecret = StorageSecret.newBuilder();
        if (StorageType.S3.name().equals(stoReq.getStorageType().name())) {
            S3Secret secret = createS3Secret(stoReq.getSecret().getS3Secret());
            return storageSecret.setSecret(SecretWrapper.newBuilder().setS3Secret(secret)).setSecretId(secret.getSecretId()).build();
        } else if (StorageType.GCS.name().equals(stoReq.getStorageType().name())) {
            GCSSecret secret = createGCSSecret(stoReq.getSecret().getGcsSecret());
            return storageSecret.setSecret(SecretWrapper.newBuilder().setGcsSecret(secret)).setSecretId(secret.getSecretId()).build();
        }
        return null;
    }

    @Override
    public boolean deleteSecret(SecretDeleteRequest request) throws Exception {
        return false;
    }

    @Override
    public SecretListResponse searchSecrets(SecretSearchRequest request) throws Exception {
        return null;
    }

    @Override
    public SecretListResponse listSecrets(SecretListRequest request) throws Exception {
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


    public GCSSecret createGCSSecret(GCSSecret request) throws Exception {
        GCSSecretEntity savedEntity = gcsSecretRepository.save(mapper.map(request, GCSSecretEntity.class));
        return mapper.map(savedEntity, GCSSecret.newBuilder().getClass()).build();
    }

    public Optional<GCSSecret> getGCSSecret(GCSSecretGetRequest request) throws Exception {
        Optional<GCSSecretEntity> secretEty = gcsSecretRepository.findBySecretId(request.getSecretId());
        return secretEty.map(entity -> mapper.map(entity, GCSSecret.newBuilder().getClass()).build());
    }
}
