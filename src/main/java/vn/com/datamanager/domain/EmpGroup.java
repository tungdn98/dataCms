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
 * Nhóm người dùng
 */
@Schema(description = "Nhóm người dùng")
@Entity
@Table(name = "emp_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmpGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "group_name", length = 100, nullable = false)
    private String groupName;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @OneToMany(mappedBy = "empGroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empGroup" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_emp_group__role",
        joinColumns = @JoinColumn(name = "emp_group_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "roleGroup", "empGroups" }, allowSetters = true)
    private Set<Roles> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmpGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public EmpGroup groupName(String groupName) {
        this.setGroupName(groupName);
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public EmpGroup createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public EmpGroup createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public EmpGroup lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public EmpGroup lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setEmpGroup(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setEmpGroup(this));
        }
        this.employees = employees;
    }

    public EmpGroup employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public EmpGroup addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setEmpGroup(this);
        return this;
    }

    public EmpGroup removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setEmpGroup(null);
        return this;
    }

    public Set<Roles> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public EmpGroup roles(Set<Roles> roles) {
        this.setRoles(roles);
        return this;
    }

    public EmpGroup addRole(Roles roles) {
        this.roles.add(roles);
        roles.getEmpGroups().add(this);
        return this;
    }

    public EmpGroup removeRole(Roles roles) {
        this.roles.remove(roles);
        roles.getEmpGroups().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmpGroup)) {
            return false;
        }
        return id != null && id.equals(((EmpGroup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpGroup{" +
            "id=" + getId() +
            ", groupName='" + getGroupName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
