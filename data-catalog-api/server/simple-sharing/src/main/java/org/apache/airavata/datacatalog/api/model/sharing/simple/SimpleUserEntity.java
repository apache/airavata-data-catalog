package org.apache.airavata.datacatalog.api.model.sharing.simple;

import org.apache.airavata.datacatalog.api.model.UserEntity;

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
@Table(name = "simple_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "simple_tenant_id", "external_id" }) })
public class SimpleUserEntity {

    @Id
    @SequenceGenerator(name = "simple_user_simple_user_id_seq", sequenceName = "simple_user_simple_user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "simple_user_simple_user_id_seq")
    @Column(name = "simple_user_id")
    private Long simpleUserId;

    @Basic
    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "simple_tenant_id", referencedColumnName = "simple_tenant_id", nullable = false, updatable = false)
    private SimpleTenantEntity simpleTenant;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UserEntity user;

    public Long getSimpleUserId() {
        return simpleUserId;
    }

    public void setSimpleUserId(Long simpleUserId) {
        this.simpleUserId = simpleUserId;
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

    public void setSimpleTenant(SimpleTenantEntity tenant) {
        this.simpleTenant = tenant;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((simpleUserId == null) ? 0 : simpleUserId.hashCode());
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
        SimpleUserEntity other = (SimpleUserEntity) obj;
        if (simpleUserId == null) {
            if (other.simpleUserId != null)
                return false;
        } else if (!simpleUserId.equals(other.simpleUserId))
            return false;
        return true;
    }

}
