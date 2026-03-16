package org.apache.airavata.replicacatalog.catalogapi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.transaction.Transactional;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaLocation;
import org.apache.airavata.replicacatalog.catalog.stubs.ReplicaGroupEntry;
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
    public DataReplicaLocation createDataReplica(DataReplicaLocation replicaLocation) throws Exception{

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
    public DataReplicaLocation updateDataReplica(DataReplicaLocation dataReplicaLocation) throws Exception{

        DataReplicaLocationEntity dataReplicaLocationEntity = dataReplicaLocationRepository.findByReplicaId(dataReplicaLocation.getDataReplicaId());
        if (dataReplicaLocationEntity == null) {
            logger.debug("Data Replica Location not exists");
        }
        replicaMapper.mapModelToEntity(dataReplicaLocation, dataReplicaLocationEntity);
        DataReplicaLocationEntity savedDataReplicaLocationEntity = dataReplicaLocationRepository.save(dataReplicaLocationEntity);
        return toDataReplicaLocation(savedDataReplicaLocationEntity);
    }

    @Override
    public DataReplicaLocation getDataReplica(String replicaId) throws Exception {

        DataReplicaLocationEntity dataReplicaLocationEntity = dataReplicaLocationRepository.findByReplicaId(replicaId);
        if (dataReplicaLocationEntity == null) {
            logger.debug("Data Replica Location not exists");
        }
        return toDataReplicaLocation(dataReplicaLocationEntity);
    }

    @Override
    public void deleteDataReplica(String replicaId) throws Exception {
        dataReplicaLocationRepository.deleteByReplicaId(replicaId);
    }

    @Override
    public List<ReplicaGroupEntry> getDataReplicas(String productUri) throws Exception{
        Optional<List<DataReplicaLocationEntity>> dataReplicaLocationEntities = dataReplicaLocationRepository.findByProductUri(productUri);
        if (!dataReplicaLocationEntities.isPresent() || dataReplicaLocationEntities.get().isEmpty()) {
            logger.debug("Data Replica Location not exists");
        }
        List<ReplicaGroupEntry> dataReplicaLocations = new ArrayList<>();
        dataReplicaLocationEntities.get().forEach(r -> {
            ReplicaGroupEntry groupEntry = null;
            try {
                groupEntry = ReplicaGroupEntry.newBuilder()
                        .setDataReplicaId(r.getReplicaId())
                        .addFiles(toDataReplicaLocation(r)).build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            dataReplicaLocations.add(groupEntry);
        });
        return dataReplicaLocations;
    }
    private DataReplicaLocation toDataReplicaLocation(DataReplicaLocationEntity savedDataLocationEntity) throws Exception{
        DataReplicaLocation.Builder builder = DataReplicaLocation.newBuilder();
        replicaMapper.mapEntityToModel(savedDataLocationEntity, builder);
        return builder.build();
    }


}
