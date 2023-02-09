package org.apache.airavata.datacatalog.api.mapper;

import org.apache.airavata.datacatalog.api.DataProduct;
import org.apache.airavata.datacatalog.api.model.DataProductEntity;
import org.apache.airavata.datacatalog.api.repository.DataProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Map to/from
 * {@link org.apache.airavata.datacatalog.api.model.DataProductEntity}
 * <-> {@link org.apache.airavata.datacatalog.api.DataProduct}
 */
@Component
public class DataProductMapper {

    @Autowired
    DataProductRepository dataProductRepository;

    public void mapModelToEntity(DataProduct dataProduct, DataProductEntity dataProductEntity) {

        dataProductEntity.setName(dataProduct.getName());

        if (dataProduct.hasParentDataProductId()) {
            // TODO: handle parent data product not found
            DataProductEntity parentDataProductEntity = dataProductRepository
                    .findByExternalId(dataProduct.getParentDataProductId());
            dataProductEntity.setParentDataProductEntity(parentDataProductEntity);
        }
        if (dataProduct.hasMetadata()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode metadata = mapper.readTree(dataProduct.getMetadata());
                dataProductEntity.setMetadata(metadata);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void mapEntityToModel(DataProductEntity dataProductEntity, DataProduct.Builder dataProductBuilder) {

        dataProductBuilder
                .setDataProductId(dataProductEntity.getExternalId())
                .setName(dataProductEntity.getName());
        if (dataProductEntity.getParentDataProductEntity() != null) {
            dataProductBuilder.setParentDataProductId(dataProductEntity.getParentDataProductEntity().getExternalId());
        }
    }
}
