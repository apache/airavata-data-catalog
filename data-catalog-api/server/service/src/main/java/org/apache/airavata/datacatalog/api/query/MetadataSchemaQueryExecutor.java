package org.apache.airavata.datacatalog.api.query;

import org.apache.airavata.datacatalog.api.exception.MetadataSchemaSqlParseException;
import org.apache.airavata.datacatalog.api.exception.MetadataSchemaSqlValidateException;
import org.apache.airavata.datacatalog.api.model.UserEntity;
import java.util.List;
public interface MetadataSchemaQueryExecutor {
    MetadataSchemaQueryResult execute(UserEntity userEntity, String sql, int page, int pageSize)
        throws MetadataSchemaSqlParseException, MetadataSchemaSqlValidateException;
    MetadataSchemaQueryResult execute(UserEntity userEntity, List<String> groupIds,String sql, int page, int pageSize)
            throws MetadataSchemaSqlParseException, MetadataSchemaSqlValidateException;
}
