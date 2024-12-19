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
 * Quyền
 */
@Schema(description = "Quyền")
@Entity
@Table(name = "roles")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "resource_url", length = 100, nullable = false)
    private String resourceUrl;

    @Column(name = "resource_desc")
    private String resourceDesc;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "roles" }, allowSetters = true)
    private RoleGroup roleGroup;

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "employees", "roles" }, allowSetters = true)
    private Set<EmpGroup> empGroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Roles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceUrl() {
        return this.resourceUrl;
    }

    public Roles resourceUrl(String resourceUrl) {
        this.setResourceUrl(resourceUrl);
        return this;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourceDesc() {
        return this.resourceDesc;
    }

    public Roles resourceDesc(String resourceDesc) {
        this.setResourceDesc(resourceDesc);
        return this;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Roles createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Roles createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Roles lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Roles lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public RoleGroup getRoleGroup() {
        return this.roleGroup;
    }

    public void setRoleGroup(RoleGroup roleGroup) {
        this.roleGroup = roleGroup;
    }

    public Roles roleGroup(RoleGroup roleGroup) {
        this.setRoleGroup(roleGroup);
        return this;
    }

    public Set<EmpGroup> getEmpGroups() {
        return this.empGroups;
    }

    public void setEmpGroups(Set<EmpGroup> empGroups) {
        if (this.empGroups != null) {
            this.empGroups.forEach(i -> i.removeRole(this));
        }
        if (empGroups != null) {
            empGroups.forEach(i -> i.addRole(this));
        }
        this.empGroups = empGroups;
    }

    public Roles empGroups(Set<EmpGroup> empGroups) {
        this.setEmpGroups(empGroups);
        return this;
    }

    public Roles addEmpGroup(EmpGroup empGroup) {
        this.empGroups.add(empGroup);
        empGroup.getRoles().add(this);
        return this;
    }

    public Roles removeEmpGroup(EmpGroup empGroup) {
        this.empGroups.remove(empGroup);
        empGroup.getRoles().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Roles)) {
            return false;
        }
        return id != null && id.equals(((Roles) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Roles{" +
            "id=" + getId() +
            ", resourceUrl='" + getResourceUrl() + "'" +
            ", resourceDesc='" + getResourceDesc() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
