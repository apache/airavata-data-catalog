package org.apache.airavata.datacatalog.api.model.sharing.simple;

import org.apache.airavata.datacatalog.api.model.TenantEntity;

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
@Table(name = "simple_tenant", uniqueConstraints = { @UniqueConstraint(columnNames = { "external_id" }) })
public class SimpleTenantEntity {

    @Id
    @SequenceGenerator(name = "simple_tenant_simple_tenant_id_seq", sequenceName = "simple_tenant_simple_tenant_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "simple_tenant_simple_tenant_id_seq")
    @Column(name = "simple_tenant_id")
    private Long simpleTenantId;

    @Basic
    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tenant_id", nullable = false, updatable = false)
    private TenantEntity tenant;

    public Long getSimpleTenantId() {
        return simpleTenantId;
    }

    public void setSimpleTenantId(Long simpleTenantId) {
        this.simpleTenantId = simpleTenantId;
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

    public TenantEntity getTenant() {
        return tenant;
    }

    public void setTenant(TenantEntity tenant) {
        this.tenant = tenant;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((simpleTenantId == null) ? 0 : simpleTenantId.hashCode());
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
        SimpleTenantEntity other = (SimpleTenantEntity) obj;
        if (simpleTenantId == null) {
            if (other.simpleTenantId != null)
                return false;
        } else if (!simpleTenantId.equals(other.simpleTenantId))
            return false;
        return true;
    }

}
