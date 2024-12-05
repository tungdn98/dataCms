package vn.com.datamanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Nhóm quyền
 */
@Schema(description = "Nhóm quyền")
@Entity
@Table(name = "role_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleGroup extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(min = 1, max = 100)
    @Column(name = "group_name", length = 100, unique = true)
    private String groupName;

    @OneToMany(mappedBy = "roleGroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "roleGroup", "empGroups" }, allowSetters = true)
    private Set<Roles> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RoleGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public RoleGroup groupName(String groupName) {
        this.setGroupName(groupName);
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<Roles> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Roles> roles) {
        if (this.roles != null) {
            this.roles.forEach(i -> i.setRoleGroup(null));
        }
        if (roles != null) {
            roles.forEach(i -> i.setRoleGroup(this));
        }
        this.roles = roles;
    }

    public RoleGroup roles(Set<Roles> roles) {
        this.setRoles(roles);
        return this;
    }

    public RoleGroup addRole(Roles roles) {
        this.roles.add(roles);
        roles.setRoleGroup(this);
        return this;
    }

    public RoleGroup removeRole(Roles roles) {
        this.roles.remove(roles);
        roles.setRoleGroup(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public String getCreatedBy() {
        return super.getCreatedBy();
    }

    @Override
    public void setCreatedBy(String createdBy) {
        super.setCreatedBy(createdBy);
    }

    @Override
    public Instant getCreatedDate() {
        return super.getCreatedDate();
    }

    @Override
    public void setCreatedDate(Instant createdDate) {
        super.setCreatedDate(createdDate);
    }

    @Override
    public String getLastModifiedBy() {
        return super.getLastModifiedBy();
    }

    @Override
    public void setLastModifiedBy(String lastModifiedBy) {
        super.setLastModifiedBy(lastModifiedBy);
    }

    @Override
    public Instant getLastModifiedDate() {
        return super.getLastModifiedDate();
    }

    @Override
    public void setLastModifiedDate(Instant lastModifiedDate) {
        super.setLastModifiedDate(lastModifiedDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleGroup)) {
            return false;
        }
        return id != null && id.equals(((RoleGroup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleGroup{" +
            "id=" + getId() +
            ", groupName='" + getGroupName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
