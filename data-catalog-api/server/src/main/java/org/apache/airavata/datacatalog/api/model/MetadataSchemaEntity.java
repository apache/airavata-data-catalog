package org.apache.airavata.datacatalog.api.model;

import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
// TODO: unique constraint on schema_name, tenant
@Table(name = "metadata_schema")
public class MetadataSchemaEntity {

    @Id
    @SequenceGenerator(name = "metadata_schema_metadata_schema_id_seq", sequenceName = "metadata_schema_metadata_schema_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metadata_schema_metadata_schema_id_seq")
    @Column(name = "metadata_schema_id")
    private Long metadataSchemaId;

    @Basic
    @Column(name = "schema_name", nullable = false)
    private String schemaName;

    @OneToMany(mappedBy = "metadataSchema")
    private Set<MetadataSchemaFieldEntity> metadataSchemaFields;

    public Long getMetadataSchemaId() {
        return metadataSchemaId;
    }

    public void setMetadataSchemaId(Long metadataSchemaId) {
        this.metadataSchemaId = metadataSchemaId;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((metadataSchemaId == null) ? 0 : metadataSchemaId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MetadataSchemaEntity other = (MetadataSchemaEntity) obj;
        if (metadataSchemaId == null) {
            if (other.metadataSchemaId != null)
                return false;
        } else if (!metadataSchemaId.equals(other.metadataSchemaId))
            return false;
        return true;
    }
}
