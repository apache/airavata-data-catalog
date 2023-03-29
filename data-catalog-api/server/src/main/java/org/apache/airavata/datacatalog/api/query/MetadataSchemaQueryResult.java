package org.apache.airavata.datacatalog.api.query;

import java.util.List;

import org.apache.airavata.datacatalog.api.DataProduct;

public record MetadataSchemaQueryResult(List<DataProduct> dataProducts) {

}
