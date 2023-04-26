package org.apache.airavata.datacatalog.api.model.sharing.simple;

import org.apache.airavata.datacatalog.api.Permission;
import org.apache.airavata.datacatalog.api.model.DataProductEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "simple_group_sharing", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "simple_group_id", "data_product_id", "permission_id" }) })
public class SimpleGroupSharingEntity {

    @Id
    @SequenceGenerator(name = "simple_group_sharing_sharing_id", sequenceName = "simple_group_sharing_sharing_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "simple_group_sharing_sharing_id")
    @Column(name = "sharing_id")
    private Long sharingId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "simple_group_id", referencedColumnName = "simple_group_id", nullable = false, updatable = false)
    private SimpleGroupEntity simpleGroup;

    @ManyToOne(optional = false)
    @JoinColumn(name = "data_product_id", referencedColumnName = "data_product_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_simple_group_sharing_data_product_id"))
    private DataProductEntity dataProduct;

    @Column(name = "permission_id")
    @Enumerated(EnumType.STRING)
    private Permission permission;

    @ManyToOne
    @JoinColumn(name = "shared_by_user_id", referencedColumnName = "user_id")
    private SimpleUserEntity sharedByUser;

    public Long getSharingId() {
        return sharingId;
    }

    public void setSharingId(Long sharingId) {
        this.sharingId = sharingId;
    }

    public SimpleGroupEntity getSimpleGroup() {
        return simpleGroup;
    }

    public void setSimpleGroup(SimpleGroupEntity simpleGroup) {
        this.simpleGroup = simpleGroup;
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
        SimpleGroupSharingEntity other = (SimpleGroupSharingEntity) obj;
        if (sharingId == null) {
            if (other.sharingId != null)
                return false;
        } else if (!sharingId.equals(other.sharingId))
            return false;
        return true;
    }

}
