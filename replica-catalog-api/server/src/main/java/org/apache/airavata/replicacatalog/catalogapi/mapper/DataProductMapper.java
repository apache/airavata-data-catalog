package org.apache.airavata.replicacatalog.catalogapi.mapper;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.airavata.replicacatalog.catalog.stubs.DataProduct;
import org.apache.airavata.replicacatalog.catalogapi.model.DataProductEntity;
import org.apache.airavata.replicacatalog.catalogapi.model.DataReplicaLocationEntity;
import org.apache.airavata.replicacatalog.catalogapi.repository.DataProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Map to/from
 * {@link DataReplicaLocationEntity}
 * <-> {@link org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaLocation}
 */
@Component
public class DataProductMapper {

    @Autowired
    DataProductRepository dataProductRepository;

    public void mapModelToEntity(DataProduct dataProduct, DataProductEntity dataProductEntity) {

        // For new data product entities
        if (dataProductEntity.getProductUri() == null) {
            dataProductEntity.setProductUri(dataProduct.getProductUri());
        }
        dataProductEntity.setProductName(dataProduct.getProductName());
        dataProductEntity.setProductDescription(dataProduct.getProductDescription());
        dataProductEntity.setDataProductType(dataProduct.getDataProductType());
        dataProductEntity.setParentProductUri(dataProduct.getParentProductUri());
        dataProductEntity.setLastModifiedTime(new Timestamp(System.currentTimeMillis()));

        if (dataProduct.getCreationTime() < 1) {
            dataProductEntity.setCreationTime(new Timestamp(System.currentTimeMillis()));
        } else {
            dataProductEntity.setCreationTime(new Timestamp(dataProduct.getCreationTime()));
        }

        //TODO Map metadata
        mapMetaData(dataProduct, dataProductEntity);
    }

    public void mapEntityToModel(DataProductEntity dataProductEntity, DataProduct.Builder dataProductBuilder) {

        dataProductBuilder
                .setProductUri(dataProductEntity.getProductUri())
                .setProductName(dataProductEntity.getProductName())
                .setDataProductType(dataProductEntity.getDataProductType())
                .setParentProductUri(dataProductEntity.getParentProductUri())
                .setCreationTime(dataProductEntity.getCreationTime().getTime()).build();

    }

    private void mapMetaData(DataProduct dataProduct, DataProductEntity dataProductEntity) {
        Map<String, String> newMetaData = new HashMap<>();
        dataProduct.getMetadataMap().keySet().forEach(m -> {
            newMetaData.put(m, dataProduct.getMetadataMap().get(m));
        });
        dataProductEntity.setProductMetadata(newMetaData);
    }


}
