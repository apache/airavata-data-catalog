package org.apache.airavata.replicacatalog.catalogapi.service.impl;

import java.util.UUID;

import jakarta.transaction.Transactional;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaLocation;
import org.apache.airavata.replicacatalog.catalogapi.mapper.DataReplicaMapper;
import org.apache.airavata.replicacatalog.catalogapi.model.DataReplicaLocationEntity;
import org.apache.airavata.replicacatalog.catalogapi.repository.DataReplicaLocationRepository;
import org.apache.airavata.replicacatalog.catalogapi.service.IReplicaCatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReplicaCatalogServiceImp implements IReplicaCatalogService {
    private final static Logger logger = LoggerFactory.getLogger(ReplicaCatalogServiceImp.class);

    @Autowired
    DataReplicaLocationRepository dataReplicaLocationRepository;

    @Autowired
    DataReplicaMapper replicaMapper = new DataReplicaMapper();



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


}
