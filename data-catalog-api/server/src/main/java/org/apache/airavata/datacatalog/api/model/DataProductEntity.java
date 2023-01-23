package org.apache.airavata.datacatalog.api.model;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.databind.JsonNode;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "data_product", uniqueConstraints = { @UniqueConstraint(columnNames = { "external_id" }) })
public class DataProductEntity {

    @Id
    @SequenceGenerator(name="data_product_data_product_id_seq", sequenceName = "data_product_data_product_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "data_product_data_product_id_seq")
    @Column(name="data_product_id")
    private Long dataProductId;

    @ManyToOne(optional = true)
    @JoinColumn(name="parent_data_product_id", referencedColumnName = "data_product_id", nullable = true)
    private DataProductEntity parentDataProductEntity;

    @Basic
    @Column(name="external_id", nullable = false)
    private String externalId;

    @Basic
    @Column(name="name", nullable = false)
    private String name;

    @Type(JsonType.class)
    @Column(name = "metadata", columnDefinition = "jsonb")
    private JsonNode metadata;

    public Long getDataProductId() {
        return dataProductId;
    }

    public void setDataProductId(Long dataProductId) {
        this.dataProductId = dataProductId;
    }

    public DataProductEntity getParentDataProductEntity() {
        return parentDataProductEntity;
    }

    public void setParentDataProductEntity(DataProductEntity parentDataProductEntity) {
        this.parentDataProductEntity = parentDataProductEntity;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonNode getMetadata() {
        return metadata;
    }

    public void setMetadata(JsonNode metadata) {
        this.metadata = metadata;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataProductId == null) ? 0 : dataProductId.hashCode());
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
        DataProductEntity other = (DataProductEntity) obj;
        if (dataProductId == null) {
            if (other.dataProductId != null)
                return false;
        } else if (!dataProductId.equals(other.dataProductId))
            return false;
        return true;
    }
}
