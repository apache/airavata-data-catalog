package org.apache.airavata.datacatalog.api.model;

import org.apache.airavata.datacatalog.api.FieldValueType;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "metadata_schema_field", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "field_name", "metadata_schema_id" }) })
public class MetadataSchemaFieldEntity {

    @Id
    @SequenceGenerator(name = "metadata_schema_field_metadata_schema_field_id", sequenceName = "metadata_schema_field_metadata_schema_field_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metadata_schema_field_metadata_schema_field_id")
    @Column(name = "metadata_schema_field_id")
    private Long metadataSchemaFieldId;

    @Basic
    @Column(name = "field_name", nullable = false)
    private String fieldName;

    @Basic
    @Column(name = "json_path", nullable = false)
    private String jsonPath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FieldValueType fieldValueType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "metadata_schema_id", nullable = false, updatable = false)
    private MetadataSchemaEntity metadataSchema;

    public Long getMetadataSchemaFieldId() {
        return metadataSchemaFieldId;
    }

    public void setMetadataSchemaFieldId(Long metadataSchemaFieldId) {
        this.metadataSchemaFieldId = metadataSchemaFieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public FieldValueType getFieldValueType() {
        return fieldValueType;
    }

    public void setFieldValueType(FieldValueType fieldValueType) {
        this.fieldValueType = fieldValueType;
    }

    public MetadataSchemaEntity getMetadataSchema() {
        return metadataSchema;
    }

    public void setMetadataSchema(MetadataSchemaEntity metadataSchema) {
        this.metadataSchema = metadataSchema;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((metadataSchemaFieldId == null) ? 0 : metadataSchemaFieldId.hashCode());
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
        MetadataSchemaFieldEntity other = (MetadataSchemaFieldEntity) obj;
        if (metadataSchemaFieldId == null) {
            if (other.metadataSchemaFieldId != null)
                return false;
        } else if (!metadataSchemaFieldId.equals(other.metadataSchemaFieldId))
            return false;
        return true;
    }

}
