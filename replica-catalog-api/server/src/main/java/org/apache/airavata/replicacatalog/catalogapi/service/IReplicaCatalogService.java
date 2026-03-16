package org.apache.airavata.replicacatalog.catalogapi.service;

import java.util.List;

import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaLocation;
import org.apache.airavata.replicacatalog.catalog.stubs.ReplicaGroupEntry;

public interface IReplicaCatalogService {

    DataReplicaLocation createDataReplica(DataReplicaLocation dataReplica) throws Exception;

    DataReplicaLocation updateDataReplica(DataReplicaLocation dataReplica) throws Exception;

    DataReplicaLocation getDataReplica(String replicaId) throws Exception;

    void deleteDataReplica(String replicaId) throws Exception;

    List<ReplicaGroupEntry> getDataReplicas(String productUri) throws Exception;

}
