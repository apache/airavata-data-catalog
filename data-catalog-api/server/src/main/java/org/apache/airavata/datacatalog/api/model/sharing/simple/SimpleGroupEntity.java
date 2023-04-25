package org.apache.airavata.datacatalog.api.model.sharing.simple;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

// TODO: need a unique constraint on (tenant, externalId)
@Entity
@Table(name = "simple_group")
public class SimpleGroupEntity {

    @Id
    @SequenceGenerator(name = "simple_group_simple_group_id", sequenceName = "simple_group_simple_group_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "simple_group_simple_group_id")
    @Column(name = "simple_group_id")
    private Long simpleGroupId;

    @Basic
    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "simple_tenant_id", referencedColumnName = "simple_tenant_id", nullable = false, updatable = false)
    private SimpleTenantEntity simpleTenant;

    @ManyToMany
    @JoinTable(name = "simple_group_membership", joinColumns = @JoinColumn(name = "simple_group_id"), inverseJoinColumns = @JoinColumn(name = "simple_user_id"))
    private Set<SimpleUserEntity> memberUsers = new HashSet<>();

    public Long getSimpleGroupId() {
        return simpleGroupId;
    }

    public void setSimpleGroupId(Long simpleGroupId) {
        this.simpleGroupId = simpleGroupId;
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

    public SimpleTenantEntity getSimpleTenant() {
        return simpleTenant;
    }

    public void setSimpleTenant(SimpleTenantEntity simpleTenant) {
        this.simpleTenant = simpleTenant;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((simpleGroupId == null) ? 0 : simpleGroupId.hashCode());
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
        SimpleGroupEntity other = (SimpleGroupEntity) obj;
        if (simpleGroupId == null) {
            if (other.simpleGroupId != null)
                return false;
        } else if (!simpleGroupId.equals(other.simpleGroupId))
            return false;
        return true;
    }

}
