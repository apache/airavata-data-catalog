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


import org.apache.airavata.replicacatalog.catalog.stubs.StorageType;
import org.apache.airavata.replicacatalog.exception.InvalidDataException;
import org.apache.airavata.replicacatalog.exception.StorageNotSupportException;
import org.apache.airavata.replicacatalog.resource.mapper.ResourceStorageMapper;
import org.apache.airavata.replicacatalog.resource.model.GenericResourceEntity;
import org.apache.airavata.replicacatalog.resource.model.ResolveStorageEntity;
import org.apache.airavata.replicacatalog.resource.model.storage.GCSStorageEntity;
import org.apache.airavata.replicacatalog.resource.model.storage.S3StorageEntity;
import org.apache.airavata.replicacatalog.resource.model.StorageSecretEntity;
import org.apache.airavata.replicacatalog.resource.repository.GenericResourceRepository;
import org.apache.airavata.replicacatalog.resource.repository.ResolveStorageRepository;
import org.apache.airavata.replicacatalog.resource.repository.storage.GCSStorageRepository;
import org.apache.airavata.replicacatalog.resource.repository.storage.LocalStorageRepository;
import org.apache.airavata.replicacatalog.resource.repository.storage.S3StorageRepository;
import org.apache.airavata.replicacatalog.resource.repository.StorageSecretRepository;

import org.apache.airavata.replicacatalog.resource.stubs.common.*;
import org.apache.airavata.replicacatalog.resource.stubs.common.Error;
import org.apache.airavata.replicacatalog.resource.stubs.gcs.GCSStorage;
import org.apache.airavata.replicacatalog.resource.stubs.s3.*;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component("SQLResourceBackend")
public class SQLResourceService implements IResourceService {

    private static final Logger logger = LoggerFactory.getLogger(SQLResourceService.class);
    private DozerBeanMapper mapper = new DozerBeanMapper();
    @Autowired
    private GenericResourceRepository resourceRepository;

    @Autowired
    private S3StorageRepository s3StorageRepository;

    @Autowired
    private GCSStorageRepository gcsStorageRepository;

    @Autowired
    private LocalStorageRepository localStorageRepository;

    @Autowired
    private StorageSecretRepository resourceSecretRepository;

    @Autowired
    private ResolveStorageRepository resolveStorageRepository;

    @Autowired
    ResourceStorageMapper resourceStorageMapper = new ResourceStorageMapper();

    @Override
    public void init() {
        logger.info("Initializing database resource backend");
    }

    @Override
    public void destroy() {
        logger.info("Destroying database resource backend");
    }

    @Override
    public GenericResource createGenericResource(GenericResourceCreateRequest request) throws Exception {

        StorageType storageType = null;
        switch (request.getResource().getStorage().getStorageCase().getNumber()) {
            case StorageWrapper.S3STORAGE_FIELD_NUMBER:
                storageType = StorageType.S3;
                break;
            case StorageWrapper.LOCALSTORAGE_FIELD_NUMBER:
                storageType = StorageType.LOCAL;
                break;
            case StorageWrapper.GCSSTORAGE_FIELD_NUMBER:
                storageType = StorageType.GCS;
                break;
            case StorageWrapper.FTPSTORAGE_FIELD_NUMBER:
                storageType = StorageType.FTP;
                break;
            default:
                break;
        }


        GenericResourceEntity resourceEntity = new GenericResourceEntity();
        String storageId = "";
        StorageWrapper wrapper = null;
        if (storageType == StorageType.S3) {
            S3Storage storage = createS3Storage(request.getResource().getStorage().getS3Storage());
            if (storage != null) {
                storageId = storage.getStorageId();
                wrapper = StorageWrapper.newBuilder().setS3Storage(storage).build();
            }

        } else if (storageType == StorageType.GCS) {
            GCSStorage storage = createGCSStorage(request.getResource().getStorage().getGcsStorage());
            if (storage != null) {
                storageId = storage.getStorageId();
                wrapper = StorageWrapper.newBuilder().setGcsStorage(storage).build();
            }

        } else {
            throw new StorageNotSupportException( "Storage type not supported/implemented");
        }

        if (wrapper == null) {
            throw new InvalidDataException("Storage not created.");
        }

        resourceEntity.setResourceId(UUID.randomUUID().toString());
        resourceStorageMapper.mapGenericStorageModelToEntity(request.getResource(), resourceEntity);
        resourceEntity.setStorageId(storageId);
        GenericResourceEntity savedDataProductEntity = resourceRepository.save(resourceEntity);

        GenericResource.Builder responseBuilder = GenericResource.newBuilder();
        resourceStorageMapper.mapGenericStorageEntityToModel(savedDataProductEntity, wrapper, responseBuilder);
        return responseBuilder.build();
    }

    @Override
    public GenericResource getGenericResource(GenericResourceGetRequest request) throws Exception {

        Optional<GenericResourceEntity> savedGenericResourceEntityList = Optional.empty();
        if (!request.getReplicaId().isBlank()) {
            savedGenericResourceEntityList = resourceRepository.findByReplicaId(request.getReplicaId());
        } else if (!request.getResourceId().isBlank()) {
            savedGenericResourceEntityList = resourceRepository.findByResourceId(request.getResourceId());
        }
        if (savedGenericResourceEntityList.isEmpty()) {
            return null;
        }
        GenericResourceEntity resourceEntity = savedGenericResourceEntityList.get();
        StorageWrapper wrapper = null;
        if (StorageType.S3.name().equals(resourceEntity.getStorageType().name())) {
            Optional<S3StorageEntity> storage = Optional.empty();
            storage = s3StorageRepository.findById(resourceEntity.getStorageId());
            if (storage.isPresent()) {
                S3Storage s3 = mapper.map(storage.get(), S3Storage.newBuilder().getClass()).build();
                wrapper = StorageWrapper.newBuilder().setS3Storage(s3).build();
            }
        } else if (StorageType.GCS.name().equals(resourceEntity.getStorageType().name())) {
            Optional<GCSStorageEntity> storage = Optional.empty();
            storage = gcsStorageRepository.findById(resourceEntity.getStorageId());
            if (storage.isPresent()) {
                GCSStorage gcs = mapper.map(storage.get(), GCSStorage.newBuilder().getClass()).build();
                wrapper = StorageWrapper.newBuilder().setGcsStorage(gcs).build();
            }
        }

        GenericResource.Builder responseBuilder = GenericResource.newBuilder();
        resourceStorageMapper.mapGenericStorageEntityToModel(resourceEntity, wrapper, responseBuilder);
        return responseBuilder.build();
    }

    @Override
    public GenericResource updateGenericResource(GenericResourceUpdateRequest request) throws Exception {

        Optional<GenericResourceEntity> savedGenericResourceEntityList = Optional.empty();
        if (!request.getResourceId().isBlank()) {

            savedGenericResourceEntityList = resourceRepository.findByResourceId(request.getResourceId());

        } else if (request.getGenericResource().hasStorage()) {

            savedGenericResourceEntityList = resourceRepository.findByResourceId(request.getGenericResource().getResourceId());

        }
        if (savedGenericResourceEntityList.isEmpty()) {
            return null;
        }
        GenericResourceEntity resourceEntity = savedGenericResourceEntityList.get();
        StorageWrapper wrapper = null;
        if (StorageType.S3.name().equals(resourceEntity.getStorageType().name() )) {
            Optional<S3StorageEntity> storage = null;
            storage = s3StorageRepository.findById(resourceEntity.getStorageId());
            if (storage.isPresent()) {
                boolean updated = updateS3Storage(storage.get(), request.getGenericResource().getStorage().getS3Storage());
                if (updated) {
                    S3Storage s3 = mapper.map(storage.get(), S3Storage.newBuilder().getClass()).build();
                    wrapper = StorageWrapper.newBuilder().setS3Storage(s3).build();
                }
            }
        } else if (StorageType.GCS.name().equals(resourceEntity.getStorageType().name())) {
            Optional<GCSStorageEntity> storage = null;
            storage = gcsStorageRepository.findById(resourceEntity.getStorageId());
            if (storage.isPresent()) {
                boolean updated = updateGCSStorage(storage.get(), request.getGenericResource().getStorage().getGcsStorage());
                if (updated) {
                    GCSStorage gcs = mapper.map(storage.get(), GCSStorage.newBuilder().getClass()).build();
                    wrapper = StorageWrapper.newBuilder().setGcsStorage(gcs).build();
                }
            }
        }
        resourceStorageMapper.mapGenericStorageModelToEntity(request.getGenericResource(), resourceEntity);

        GenericResourceEntity savedDataProductEntity = resourceRepository.save(resourceEntity);

        GenericResource.Builder responseBuilder = GenericResource.newBuilder();
        resourceStorageMapper.mapGenericStorageEntityToModel(savedDataProductEntity, wrapper, responseBuilder);
        return responseBuilder.build();
    }

    @Override
    public GenericResource deleteGenericResource(GenericResourceDeleteRequest request) throws Exception {
        resourceRepository.deleteByResourceId(request.getResourceId());
        return null;
    }

    @Override
    public SecretForStorage getSecretForStorage(SecretForStorageGetRequest request) throws Exception {
        Optional<StorageSecretEntity> resourceSecEtyOp = resourceSecretRepository.findByStorageId(request.getStorageId());
        SecretForStorage.Builder resultBuilder = SecretForStorage.newBuilder();
        if (resourceSecEtyOp.isPresent()) {
            StorageSecretEntity storageSecretEntity = resourceSecEtyOp.get();
            resultBuilder.setSecretId(storageSecretEntity.getSecretId());
            resultBuilder.setStorageId(storageSecretEntity.getStorageId());
            resultBuilder.setStorageType(resourceStorageMapper.resolveStorage( storageSecretEntity.getType()));
        } else {
            resultBuilder.setError(Error.NOT_FOUND);
        }
        return resultBuilder.build();
    }

    @Override
    public SecretForStorage registerSecretForStorage(SecretForStorageCreateRequest request) throws Exception {

        StorageSecretEntity resourceEntity = new StorageSecretEntity();
        resourceStorageMapper.mapStorageSecretModelToEntity(request.getSecretForStorage(), resourceEntity);
        StorageSecretEntity savedDataProductEntity = resourceSecretRepository.save(resourceEntity);
        SecretForStorage.Builder responseBuilder = SecretForStorage.newBuilder();
        resourceStorageMapper.mapStorageSecretEntityToModel(savedDataProductEntity, responseBuilder);

        return responseBuilder.build();
    }

    @Override
    public boolean deleteSecretForStorage(SecretForStorageDeleteRequest request) throws Exception {
        resourceSecretRepository.deleteByStorageId(request.getStorageId());
        return true;
    }

    @Override
    public StorageListResponse searchStorages(StorageSearchRequest request) throws Exception {
        StorageListResponse.Builder resp = StorageListResponse.newBuilder();
        switch (request.getSearchQueryCase()) {
            case STORAGE_ID:
                Optional<ResolveStorageEntity> storageOp = resolveStorageRepository.getByStorageId(request.getStorageId());
                if (storageOp.isPresent()) {
                    StorageListEntry.Builder entry = StorageListEntry.newBuilder();
                    entry.setStorageId(storageOp.get().getStorageId());
                    entry.setStorageName(storageOp.get().getStorageName());
                    entry.setStorageType(StorageType.valueOf(storageOp.get().getStorageType().name()));
                    resp.addStorageList(entry);
                }
                break;
            case STORAGE_NAME:
                List<ResolveStorageEntity> storages = resolveStorageRepository.getByStorageName(request.getStorageName());
                storages.forEach(st -> {
                    StorageListEntry.Builder entry = StorageListEntry.newBuilder();
                    entry.setStorageId(st.getStorageId());
                    entry.setStorageName(st.getStorageName());
                    entry.setStorageType(StorageType.valueOf(st.getStorageType().name()));
                    resp.addStorageList(entry);
                });
                break;
            case STORAGE_TYPE:
                storages = resolveStorageRepository.getByStorageType(ResolveStorageEntity.StorageType.valueOf(request.getStorageType().name()));
                storages.forEach(st -> {
                    StorageListEntry.Builder entry = StorageListEntry.newBuilder();
                    entry.setStorageId(st.getStorageId());
                    entry.setStorageName(st.getStorageName());
                    entry.setStorageType(StorageType.valueOf(st.getStorageType().name()));
                    resp.addStorageList(entry);
                });
                break;
        }
        return resp.build();
    }

    @Override
    public StorageListResponse listStorage(StorageListRequest request) throws Exception {
        Iterable<ResolveStorageEntity> all = resolveStorageRepository.findAll();
        StorageListResponse.Builder builder = StorageListResponse.newBuilder();
        all.forEach(r -> {
            StorageListEntry.Builder entry = StorageListEntry.newBuilder();
            entry.setStorageId(r.getStorageId());
            entry.setStorageType(StorageType.valueOf(r.getStorageType().name()));
            entry.setStorageName(r.getStorageName());
            builder.addStorageList(entry);
        });
        return builder.build();
    }


    public S3StorageListResponse listS3Storage(S3StorageListRequest request) throws Exception {
        S3StorageListResponse.Builder respBuilder = S3StorageListResponse.newBuilder();
        List<S3StorageEntity> all = s3StorageRepository.findAll(PageRequest.of(request.getOffset(), request.getLimit()));
        all.forEach(ety -> respBuilder.addStorages(mapper.map(ety, S3Storage.newBuilder().getClass())));
        return respBuilder.build();
    }


    public Optional<S3Storage> getS3Storage(S3StorageGetRequest request) throws Exception {
        Optional<S3StorageEntity> entity = s3StorageRepository.findById(request.getStorageId());
        return entity.map(e -> mapper.map(e, S3Storage.newBuilder().getClass()).build());
    }


    public S3Storage createS3Storage(S3Storage request) throws Exception {
        S3StorageEntity savedEntity = s3StorageRepository.save(mapper.map(request, S3StorageEntity.class));

        ResolveStorageEntity storageTypeEty = new ResolveStorageEntity();
        storageTypeEty.setStorageId(savedEntity.getStorageId());
        storageTypeEty.setStorageType(ResolveStorageEntity.StorageType.S3);
        storageTypeEty.setStorageName(savedEntity.getName());
        resolveStorageRepository.save(storageTypeEty);

        return mapper.map(savedEntity, S3Storage.newBuilder().getClass()).build();
    }


    public boolean updateS3Storage(S3StorageEntity entity, S3Storage storage) throws Exception {

        entity.setEndpoint(storage.getEndpoint());
        entity.setName(storage.getName());
        entity.setBucketName(storage.getBucketName());
        entity.setRegion(storage.getRegion());
        entity.setUseTLS(storage.getUseTLS());
        s3StorageRepository.save(entity);
        return true;
    }


    public boolean deleteS3Storage(S3StorageDeleteRequest request) throws Exception {
        s3StorageRepository.deleteById(request.getStorageId());
        resourceRepository.deleteByStorageIdAndStorageType(request.getStorageId(), GenericResourceEntity.StorageType.S3);
        return true;
    }


    public GCSStorage createGCSStorage(GCSStorage request) throws Exception {
        GCSStorageEntity savedEntity = gcsStorageRepository.save(mapper.map(request, GCSStorageEntity.class));

        ResolveStorageEntity storageTypeEty = new ResolveStorageEntity();
        storageTypeEty.setStorageId(savedEntity.getStorageId());
        storageTypeEty.setStorageType(ResolveStorageEntity.StorageType.GCS);
        storageTypeEty.setStorageName(savedEntity.getName());
        resolveStorageRepository.save(storageTypeEty);

        return mapper.map(savedEntity, GCSStorage.newBuilder().getClass()).build();
    }


    public boolean updateGCSStorage(GCSStorageEntity entity, GCSStorage storage) throws Exception {
        entity.setName(storage.getName());
        entity.setBucketName(storage.getBucketName());
        gcsStorageRepository.save(entity);
        return true;
    }

    @Override
    public StorageTypeResolveResponse resolveStorageType(StorageTypeResolveRequest request) throws Exception {
        Optional<ResolveStorageEntity> resolveStorageOp = resolveStorageRepository.getByStorageId(request.getStorageId());
        StorageTypeResolveResponse.Builder responseBuilder = StorageTypeResolveResponse.newBuilder();

        if (resolveStorageOp.isPresent()) {
            ResolveStorageEntity resolveStorageEntity = resolveStorageOp.get();
            responseBuilder.setStorageId(resolveStorageEntity.getStorageId());
            responseBuilder.setStorageType(StorageType.valueOf(resolveStorageEntity.getStorageType().name()));
            responseBuilder.setStorageName(resolveStorageEntity.getStorageName());
        } else {
            responseBuilder.setError(Error.NOT_FOUND);
        }
        return responseBuilder.build();
    }
}
