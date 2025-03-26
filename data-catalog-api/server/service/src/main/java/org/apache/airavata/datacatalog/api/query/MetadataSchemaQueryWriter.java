package org.apache.airavata.datacatalog.api.query;

import java.util.Collection;
import java.util.Map;

import org.apache.airavata.datacatalog.api.model.MetadataSchemaEntity;
import org.apache.airavata.datacatalog.api.model.UserEntity;
import org.apache.calcite.sql.SqlNode;

public interface MetadataSchemaQueryWriter {

    /**
     * Rewrite the query as needed to filter against metadata schema fields.
     * 
     * @param userEntity
     *
     * @param sqlNode
     * @param metadataSchemas
     * @param tableAliases
     * @return
     */
    String rewriteQuery(UserEntity userEntity, SqlNode sqlNode, Collection<MetadataSchemaEntity> metadataSchemas,
            Map<String, String> tableAliases);
}
