package org.apache.airavata.replicacatalog.catalogapi.mapper;

import java.sql.Timestamp;

import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaLocation;
import org.apache.airavata.replicacatalog.catalogapi.model.DataReplicaLocationEntity;
import org.apache.airavata.replicacatalog.catalogapi.repository.DataReplicaLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Map to/from
 * {@link DataReplicaLocationEntity}
 * <-> {@link DataReplicaLocation}
 */
@Component
public class DataReplicaMapper {

    @Autowired
    DataReplicaLocationRepository replicaLocationRepository;

    @Autowired


    public void mapModelToEntity(DataReplicaLocation model, DataReplicaLocationEntity entity) {

        entity.setReplicaName(model.getReplicaName());
        entity.setReplicaId(model.getDataReplicaId());
        entity.setProductUri(model.getDataProductId());
        entity.setReplicaDescription(model.getReplicaDescription());
        entity.setCreationTime(new Timestamp(System.currentTimeMillis()));
        // TODO: handle parent data product not found
//        entity.setDataProduct(parentDataEntity);

    }

    public void mapEntityToModel(DataReplicaLocationEntity dataReplicaLocationEntity, DataReplicaLocation.Builder dataProductBuilder) {

        dataProductBuilder
                .setDataReplicaId( dataReplicaLocationEntity.getReplicaId() )
                .setReplicaName( dataReplicaLocationEntity.getReplicaName() )
                .setDataProductId( dataReplicaLocationEntity.getProductUri() )
                .setCreationTime( dataReplicaLocationEntity.getCreationTime().getTime() );

//        if ( dataReplicaLocationEntity.getDataProduct() != null )
//        {
//            dataProductBuilder.setDataProductId( dataReplicaLocationEntity.getDataProduct().getProductUri() );
//        }
    }
}
