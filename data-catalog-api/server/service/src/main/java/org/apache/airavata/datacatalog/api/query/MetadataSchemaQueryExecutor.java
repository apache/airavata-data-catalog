package org.apache.airavata.datacatalog.api.query;

import org.apache.airavata.datacatalog.api.exception.MetadataSchemaSqlParseException;
import org.apache.airavata.datacatalog.api.exception.MetadataSchemaSqlValidateException;
import org.apache.airavata.datacatalog.api.model.UserEntity;

public interface MetadataSchemaQueryExecutor {

    MetadataSchemaQueryResult execute(UserEntity userEntity, String sql)
            throws MetadataSchemaSqlParseException, MetadataSchemaSqlValidateException;
}
