package org.apache.airavata.replicacatalog.sceret.mapper;

import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaLocation;
import org.apache.airavata.replicacatalog.catalogapi.model.DataReplicaLocationEntity;
import org.apache.airavata.replicacatalog.resource.model.GenericResourceEntity;
import org.apache.airavata.replicacatalog.resource.repository.GenericResourceRepository;
import org.apache.airavata.replicacatalog.resource.stubs.common.SecretForStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Map to/from
 * {@link DataReplicaLocationEntity}
 * <-> {@link DataReplicaLocation}
 */
@Component
public class ResourceSecretMapper
{

    @Autowired
    GenericResourceRepository genericResourceRepository;

    public void mapModelToEntity(SecretForStorage storage, GenericResourceEntity resourceEntity) {

        resourceEntity.setResourceId(storage.getStorageId());
            // TODO
    }

    public void mapEntityToModel(GenericResourceEntity resourceEntity, SecretForStorage.Builder dataProductBuilder) {

       //TODO
    }
}
