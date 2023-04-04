package org.apache.airavata.replicacatalog.catalogapi.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.airavata.replicacatalog.catalog.stubs.DataProduct;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaLocation;
import org.apache.airavata.replicacatalog.catalogapi.mapper.DataProductMapper;
import org.apache.airavata.replicacatalog.catalogapi.mapper.DataReplicaMapper;
import org.apache.airavata.replicacatalog.catalogapi.model.DataProductEntity;
import org.apache.airavata.replicacatalog.catalogapi.model.DataReplicaLocationEntity;
import org.apache.airavata.replicacatalog.catalogapi.repository.DataProductRepository;
import org.apache.airavata.replicacatalog.catalogapi.repository.DataReplicaLocationRepository;
import org.apache.airavata.replicacatalog.catalogapi.service.IReplicaCatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class ReplicaCatalogServiceImp implements IReplicaCatalogService {
    private final static Logger logger = LoggerFactory.getLogger(ReplicaCatalogServiceImp.class);

    @Autowired
    DataProductRepository dataProductRepository;

    @Autowired
    DataReplicaLocationRepository dataReplicaLocationRepository;

    @Autowired
    DataProductMapper dataProductMapper = new DataProductMapper();

    @Autowired
    DataReplicaMapper replicaMapper = new DataReplicaMapper();



    @Override
    public DataProduct createDataProduct(DataProduct dataProduct) {
        DataProductEntity dataProductEntity = new DataProductEntity();

        if (dataProduct != null && dataProduct.getProductUri() != null) {
            dataProductEntity.setProductUri(dataProduct.getProductUri());
        }
        dataProductMapper.mapModelToEntity(dataProduct, dataProductEntity);

        if (dataProductEntity.getProductUri() == null) {
            dataProductEntity.setProductUri(UUID.randomUUID().toString());
        }
        DataProductEntity savedDataProductEntity = dataProductRepository.save(dataProductEntity);
        return toDataProduct(savedDataProductEntity);
    }

    @Override
    public DataProduct updateDataProduct(DataProduct dataProduct) {

        DataProductEntity dataProductEntity = dataProductRepository.findByProductUri(dataProduct.getProductUri());
        if (dataProductEntity == null) {
            logger.warn("Data product not exists");
            throw new EntityNotFoundException("Could not find a data product with the ID: " + dataProduct.getProductUri());
        }
        dataProductMapper.mapModelToEntity(dataProduct, dataProductEntity);
        DataProductEntity savedDataProductEntity = dataProductRepository.save(dataProductEntity);

        return toDataProduct(savedDataProductEntity);
    }

    @Override
    public DataProduct getDataProduct(String productUri) {
        DataProductEntity dataProductEntity = dataProductRepository.findByProductUri(productUri);
        return toDataProduct(dataProductEntity);
    }

    @Override
    public void deleteDataProduct(String productUri) {
        dataProductRepository.deleteByProductUri(productUri);
    }



    @Override
    public DataReplicaLocation createDataReplica(DataReplicaLocation replicaLocation) {

        DataReplicaLocationEntity dataReplicaLocationEntity = new DataReplicaLocationEntity();

        if (replicaLocation != null && replicaLocation.getDataReplicaId() != null) {
            dataReplicaLocationEntity.setReplicaId(replicaLocation.getDataReplicaId());
        }
        replicaMapper.mapModelToEntity(replicaLocation, dataReplicaLocationEntity);
        if (dataReplicaLocationEntity.getReplicaId() == null || dataReplicaLocationEntity.getReplicaId().isEmpty()) {
            dataReplicaLocationEntity.setReplicaId(UUID.randomUUID().toString());
        }
        DataReplicaLocationEntity savedDataReplicaLocationEntity = dataReplicaLocationRepository.save(dataReplicaLocationEntity);
        return toDataReplicaLocation(savedDataReplicaLocationEntity);

    }

    @Override
    public DataReplicaLocation updateDataReplica(DataReplicaLocation dataReplicaLocation) {

        DataReplicaLocationEntity dataReplicaLocationEntity = dataReplicaLocationRepository.findByReplicaId(dataReplicaLocation.getDataReplicaId());
        if (dataReplicaLocationEntity == null) {
            logger.debug("Data Replica Location not exists");
        }
        replicaMapper.mapModelToEntity(dataReplicaLocation, dataReplicaLocationEntity);
        DataReplicaLocationEntity savedDataReplicaLocationEntity = dataReplicaLocationRepository.save(dataReplicaLocationEntity);
        return toDataReplicaLocation(savedDataReplicaLocationEntity);
    }

    @Override
    public DataReplicaLocation getDataReplica(String replicaId) {

        DataReplicaLocationEntity dataReplicaLocationEntity = dataReplicaLocationRepository.findByReplicaId(replicaId);
        if (dataReplicaLocationEntity == null) {
            logger.debug("Data Replica Location not exists");
        }
        return toDataReplicaLocation(dataReplicaLocationEntity);
    }

    @Override
    public void deleteDataReplica(String replicaId) {
        dataReplicaLocationRepository.deleteByReplicaId(replicaId);
    }


    private DataReplicaLocation toDataReplicaLocation(DataReplicaLocationEntity savedDataLocationEntity) {
        DataReplicaLocation.Builder builder = DataReplicaLocation.newBuilder();
        replicaMapper.mapEntityToModel(savedDataLocationEntity, builder);
        return builder.build();
    }

    private DataProduct toDataProduct(DataProductEntity savedDataProductEntity) {
        DataProduct.Builder builder = DataProduct.newBuilder();
        dataProductMapper.mapEntityToModel(savedDataProductEntity, builder);
        return builder.build();
    }

}
