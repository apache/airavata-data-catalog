package org.apache.airavata.datacatalog.api.query;

import org.apache.airavata.datacatalog.api.exception.MetadataSchemaSqlParseException;
import org.apache.airavata.datacatalog.api.exception.MetadataSchemaSqlValidateException;

public interface MetadataSchemaQueryExecutor {

    MetadataSchemaQueryResult execute(String sql)
            throws MetadataSchemaSqlParseException, MetadataSchemaSqlValidateException;
}
