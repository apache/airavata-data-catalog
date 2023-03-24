package org.apache.airavata.replicacatalog.catalogapi.service;

import org.apache.airavata.replicacatalog.catalog.stubs.DataProduct;
import org.apache.airavata.replicacatalog.catalog.stubs.DataReplicaLocation;

import java.util.List;

public interface IReplicaCatalogService {

    DataReplicaLocation createDataReplica(DataReplicaLocation dataReplica);

    DataReplicaLocation updateDataReplica(DataReplicaLocation dataReplica);

    DataReplicaLocation getDataReplica(String replicaId);

    void deleteDataReplica(String replicaId);


    DataProduct createDataProduct(DataProduct dataProduct);

    DataProduct updateDataProduct(DataProduct dataProduct);

    DataProduct getDataProduct(String productUri);

    void deleteDataProduct(String productUri);


}
