package org.apache.airavata.replicacatalog.catalogapi.service;

import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaLocation;

public interface IReplicaCatalogService {

    DataReplicaLocation createDataReplica(DataReplicaLocation dataReplica);

    DataReplicaLocation updateDataReplica(DataReplicaLocation dataReplica);

    DataReplicaLocation getDataReplica(String replicaId);

    void deleteDataReplica(String replicaId);

}
