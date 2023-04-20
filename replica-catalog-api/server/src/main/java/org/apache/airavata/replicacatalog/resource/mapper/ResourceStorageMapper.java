package org.apache.airavata.replicacatalog.resource.mapper;

import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaLocation;
import org.apache.airavata.replicacatalog.catalog.stubs.StorageType;
import org.apache.airavata.replicacatalog.catalogapi.model.DataReplicaLocationEntity;
import org.apache.airavata.replicacatalog.resource.model.GenericResourceEntity;
import org.apache.airavata.replicacatalog.resource.model.StorageSecretEntity;
import org.apache.airavata.replicacatalog.resource.repository.GenericResourceRepository;
import org.apache.airavata.replicacatalog.resource.stubs.common.GenericResource;
import org.apache.airavata.replicacatalog.resource.stubs.common.SecretForStorage;
import org.apache.airavata.replicacatalog.resource.stubs.common.StorageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Map to/from
 * {@link DataReplicaLocationEntity}
 * <-> {@link DataReplicaLocation}
 */
@Component
public class ResourceStorageMapper {

    @Autowired
    GenericResourceRepository genericResourceRepository;

    public void mapGenericStorageModelToEntity(GenericResource storage, GenericResourceEntity resourceEntity) throws Exception {

        resourceEntity.setReplicaId(storage.getReplicaId());
        resourceEntity.setStorageId(storage.getResourceId());
        resourceEntity.setStorageType(resolveStorage(storage.getStorage()));
        resourceEntity.setResourcePath(storage.getFile().getResourcePath());
        resourceEntity.setResourceType(GenericResourceEntity.ResourceType.FILE);
        // TODO
    }

    public void mapGenericStorageEntityToModel(GenericResourceEntity resourceEntity, StorageWrapper wrapper, GenericResource.Builder builder) throws Exception {

        builder.setResourceId(resourceEntity.getResourceId());
        builder.setReplicaId(resourceEntity.getReplicaId());
        if (wrapper != null) {
            builder.setStorage(wrapper);
        }
    }

    public void mapStorageSecretModelToEntity(SecretForStorage storage, StorageSecretEntity resourceEntity) {
        resourceEntity.setStorageId(storage.getStorageId());
        resourceEntity.setSecretId(storage.getSecretId());
        resourceEntity.setType(storage.getStorageType().name());
        // TODO
    }

    public void mapStorageSecretEntityToModel(StorageSecretEntity resourceEntity, SecretForStorage.Builder dataProductBuilder) {

        dataProductBuilder.setStorageType(StorageType.valueOf(resourceEntity.getType())).setSecretId(resourceEntity.getSecretId())
                .setStorageId(resourceEntity.getStorageId());

    }


    protected GenericResourceEntity.StorageType resolveStorage (StorageWrapper wrapper){
        if(wrapper.hasS3Storage()){
            return GenericResourceEntity.StorageType.S3;
        } else if (wrapper.hasGcsStorage()){
            return GenericResourceEntity.StorageType.GCS;
        } else if (wrapper.hasFtpStorage()) {
            return GenericResourceEntity.StorageType.FTP;
        }

        return null;
    }

    public StorageType resolveStorage(String type){
        GenericResourceEntity.StorageType storageType = GenericResourceEntity.StorageType.valueOf(type);
        switch (storageType) {
            case S3:
                return StorageType.S3;
            case GCS:
                return StorageType.GCS;
            case FTP:
                return StorageType.FTP;
            default:
                throw new RuntimeException(
                        "Unexpected storage type: " + type);
        }
    }

}
