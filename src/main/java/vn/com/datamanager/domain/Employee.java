package vn.com.datamanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Người dùng
 */
@Schema(description = "Người dùng")
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(min = 1, max = 100)
    @Column(name = "employee_code", length = 100, unique = true)
    private String employeeCode;

    @NotNull
    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @NotNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private Integer active;

    @Column(name = "company_code")
    private String companyCode;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "organization_id")
    private String organizationId;

    @Column(name = "employee_last_name")
    private String employeeLastName;

    @Column(name = "employee_middle_name")
    private String employeeMiddleName;

    @Column(name = "employee_title_id")
    private String employeeTitleId;

    @Column(name = "employee_title_name")
    private String employeeTitleName;

    @Column(name = "employee_full_name")
    private String employeeFullName;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employees", "roles" }, allowSetters = true)
    private EmpGroup empGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeCode() {
        return this.employeeCode;
    }

    public Employee employeeCode(String employeeCode) {
        this.setEmployeeCode(employeeCode);
        return this;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public Employee employeeName(String employeeName) {
        this.setEmployeeName(employeeName);
        return this;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getUsername() {
        return this.username;
    }

    public Employee username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public Employee password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getActive() {
        return this.active;
    }

    public Employee active(Integer active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getCompanyCode() {
        return this.companyCode;
    }

    public Employee companyCode(String companyCode) {
        this.setCompanyCode(companyCode);
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public Employee companyName(String companyName) {
        this.setCompanyName(companyName);
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrganizationId() {
        return this.organizationId;
    }

    public Employee organizationId(String organizationId) {
        this.setOrganizationId(organizationId);
        return this;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getEmployeeLastName() {
        return this.employeeLastName;
    }

    public Employee employeeLastName(String employeeLastName) {
        this.setEmployeeLastName(employeeLastName);
        return this;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getEmployeeMiddleName() {
        return this.employeeMiddleName;
    }

    public Employee employeeMiddleName(String employeeMiddleName) {
        this.setEmployeeMiddleName(employeeMiddleName);
        return this;
    }

    public void setEmployeeMiddleName(String employeeMiddleName) {
        this.employeeMiddleName = employeeMiddleName;
    }

    public String getEmployeeTitleId() {
        return this.employeeTitleId;
    }

    public Employee employeeTitleId(String employeeTitleId) {
        this.setEmployeeTitleId(employeeTitleId);
        return this;
    }

    public void setEmployeeTitleId(String employeeTitleId) {
        this.employeeTitleId = employeeTitleId;
    }

    public String getEmployeeTitleName() {
        return this.employeeTitleName;
    }

    public Employee employeeTitleName(String employeeTitleName) {
        this.setEmployeeTitleName(employeeTitleName);
        return this;
    }

    public void setEmployeeTitleName(String employeeTitleName) {
        this.employeeTitleName = employeeTitleName;
    }

    public String getEmployeeFullName() {
        return this.employeeFullName;
    }

    public Employee employeeFullName(String employeeFullName) {
        this.setEmployeeFullName(employeeFullName);
        return this;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    public EmpGroup getEmpGroup() {
        return this.empGroup;
    }

    public void setEmpGroup(EmpGroup empGroup) {
        this.empGroup = empGroup;
    }

    public Employee empGroup(EmpGroup empGroup) {
        this.setEmpGroup(empGroup);
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
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", employeeCode='" + getEmployeeCode() + "'" +
            ", employeeName='" + getEmployeeName() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", active=" + getActive() +
            ", companyCode='" + getCompanyCode() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", organizationId='" + getOrganizationId() + "'" +
            ", employeeLastName='" + getEmployeeLastName() + "'" +
            ", employeeMiddleName='" + getEmployeeMiddleName() + "'" +
            ", employeeTitleId='" + getEmployeeTitleId() + "'" +
            ", employeeTitleName='" + getEmployeeTitleName() + "'" +
            ", employeeFullName='" + getEmployeeFullName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
