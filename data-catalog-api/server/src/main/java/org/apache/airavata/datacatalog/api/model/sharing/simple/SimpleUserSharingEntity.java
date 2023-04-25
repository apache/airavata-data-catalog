package org.apache.airavata.datacatalog.api.model.sharing.simple;

import org.apache.airavata.datacatalog.api.Permission;
import org.apache.airavata.datacatalog.api.model.DataProductEntity;

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
@Table(name = "simple_user_sharing", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "simple_user_id", "data_product_id", "permission_id" }) })
public class SimpleUserSharingEntity {

    @Id
    @SequenceGenerator(name = "simple_user_sharing_sharing_id", sequenceName = "simple_user_sharing_sharing_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "simple_user_sharing_sharing_id")
    @Column(name = "sharing_id")
    private Long sharingId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "simple_user_id", nullable = false, updatable = false)
    private SimpleUserEntity simpleUser;

    @ManyToOne(optional = false)
    @JoinColumn(name = "data_product_id", nullable = false, updatable = false)
    private DataProductEntity dataProduct;

    @Column(name = "permission_id")
    @Enumerated(EnumType.STRING)
    private Permission permission;

    @ManyToOne
    @JoinColumn(name = "shared_by_user_id")
    private SimpleUserEntity sharedByUser;

    public Long getSharingId() {
        return sharingId;
    }

    public void setSharingId(Long sharingId) {
        this.sharingId = sharingId;
    }

    public SimpleUserEntity getSimpleUser() {
        return simpleUser;
    }

    public void setSimpleUser(SimpleUserEntity simpleUser) {
        this.simpleUser = simpleUser;
    }

    public DataProductEntity getDataProduct() {
        return dataProduct;
    }

    public void setDataProduct(DataProductEntity dataProduct) {
        this.dataProduct = dataProduct;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public SimpleUserEntity getSharedByUser() {
        return sharedByUser;
    }

    public void setSharedByUser(SimpleUserEntity sharedByUser) {
        this.sharedByUser = sharedByUser;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sharingId == null) ? 0 : sharingId.hashCode());
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
        SimpleUserSharingEntity other = (SimpleUserSharingEntity) obj;
        if (sharingId == null) {
            if (other.sharingId != null)
                return false;
        } else if (!sharingId.equals(other.sharingId))
            return false;
        return true;
    }

}
