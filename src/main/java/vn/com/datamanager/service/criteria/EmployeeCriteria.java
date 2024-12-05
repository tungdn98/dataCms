package vn.com.datamanager.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link vn.com.datamanager.domain.Employee} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class EmployeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter employeeCode;

    private StringFilter employeeName;

    private StringFilter username;

    private StringFilter password;

    private IntegerFilter active;

    private StringFilter companyCode;

    private StringFilter companyName;

    private StringFilter organizationId;

    private StringFilter employeeLastName;

    private StringFilter employeeMiddleName;

    private StringFilter employeeTitleId;

    private StringFilter employeeTitleName;

    private StringFilter employeeFullName;

    private InstantFilter createdDate;

    private StringFilter createdBy;

    private InstantFilter lastModifiedDate;

    private StringFilter lastModifiedBy;

    private LongFilter empGroupId;

    private Boolean distinct;

    public EmployeeCriteria() {}

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.employeeCode = other.employeeCode == null ? null : other.employeeCode.copy();
        this.employeeName = other.employeeName == null ? null : other.employeeName.copy();
        this.username = other.username == null ? null : other.username.copy();
        this.password = other.password == null ? null : other.password.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.companyCode = other.companyCode == null ? null : other.companyCode.copy();
        this.companyName = other.companyName == null ? null : other.companyName.copy();
        this.organizationId = other.organizationId == null ? null : other.organizationId.copy();
        this.employeeLastName = other.employeeLastName == null ? null : other.employeeLastName.copy();
        this.employeeMiddleName = other.employeeMiddleName == null ? null : other.employeeMiddleName.copy();
        this.employeeTitleId = other.employeeTitleId == null ? null : other.employeeTitleId.copy();
        this.employeeTitleName = other.employeeTitleName == null ? null : other.employeeTitleName.copy();
        this.employeeFullName = other.employeeFullName == null ? null : other.employeeFullName.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.empGroupId = other.empGroupId == null ? null : other.empGroupId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEmployeeCode() {
        return employeeCode;
    }

    public StringFilter employeeCode() {
        if (employeeCode == null) {
            employeeCode = new StringFilter();
        }
        return employeeCode;
    }

    public void setEmployeeCode(StringFilter employeeCode) {
        this.employeeCode = employeeCode;
    }

    public StringFilter getEmployeeName() {
        return employeeName;
    }

    public StringFilter employeeName() {
        if (employeeName == null) {
            employeeName = new StringFilter();
        }
        return employeeName;
    }

    public void setEmployeeName(StringFilter employeeName) {
        this.employeeName = employeeName;
    }

    public StringFilter getUsername() {
        return username;
    }

    public StringFilter username() {
        if (username == null) {
            username = new StringFilter();
        }
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public StringFilter getPassword() {
        return password;
    }

    public StringFilter password() {
        if (password == null) {
            password = new StringFilter();
        }
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public IntegerFilter getActive() {
        return active;
    }

    public IntegerFilter active() {
        if (active == null) {
            active = new IntegerFilter();
        }
        return active;
    }

    public void setActive(IntegerFilter active) {
        this.active = active;
    }

    public StringFilter getCompanyCode() {
        return companyCode;
    }

    public StringFilter companyCode() {
        if (companyCode == null) {
            companyCode = new StringFilter();
        }
        return companyCode;
    }

    public void setCompanyCode(StringFilter companyCode) {
        this.companyCode = companyCode;
    }

    public StringFilter getCompanyName() {
        return companyName;
    }

    public StringFilter companyName() {
        if (companyName == null) {
            companyName = new StringFilter();
        }
        return companyName;
    }

    public void setCompanyName(StringFilter companyName) {
        this.companyName = companyName;
    }

    public StringFilter getOrganizationId() {
        return organizationId;
    }

    public StringFilter organizationId() {
        if (organizationId == null) {
            organizationId = new StringFilter();
        }
        return organizationId;
    }

    public void setOrganizationId(StringFilter organizationId) {
        this.organizationId = organizationId;
    }

    public StringFilter getEmployeeLastName() {
        return employeeLastName;
    }

    public StringFilter employeeLastName() {
        if (employeeLastName == null) {
            employeeLastName = new StringFilter();
        }
        return employeeLastName;
    }

    public void setEmployeeLastName(StringFilter employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public StringFilter getEmployeeMiddleName() {
        return employeeMiddleName;
    }

    public StringFilter employeeMiddleName() {
        if (employeeMiddleName == null) {
            employeeMiddleName = new StringFilter();
        }
        return employeeMiddleName;
    }

    public void setEmployeeMiddleName(StringFilter employeeMiddleName) {
        this.employeeMiddleName = employeeMiddleName;
    }

    public StringFilter getEmployeeTitleId() {
        return employeeTitleId;
    }

    public StringFilter employeeTitleId() {
        if (employeeTitleId == null) {
            employeeTitleId = new StringFilter();
        }
        return employeeTitleId;
    }

    public void setEmployeeTitleId(StringFilter employeeTitleId) {
        this.employeeTitleId = employeeTitleId;
    }

    public StringFilter getEmployeeTitleName() {
        return employeeTitleName;
    }

    public StringFilter employeeTitleName() {
        if (employeeTitleName == null) {
            employeeTitleName = new StringFilter();
        }
        return employeeTitleName;
    }

    public void setEmployeeTitleName(StringFilter employeeTitleName) {
        this.employeeTitleName = employeeTitleName;
    }

    public StringFilter getEmployeeFullName() {
        return employeeFullName;
    }

    public StringFilter employeeFullName() {
        if (employeeFullName == null) {
            employeeFullName = new StringFilter();
        }
        return employeeFullName;
    }

    public void setEmployeeFullName(StringFilter employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            createdDate = new InstantFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public InstantFilter lastModifiedDate() {
        if (lastModifiedDate == null) {
            lastModifiedDate = new InstantFilter();
        }
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LongFilter getEmpGroupId() {
        return empGroupId;
    }

    public LongFilter empGroupId() {
        if (empGroupId == null) {
            empGroupId = new LongFilter();
        }
        return empGroupId;
    }

    public void setEmpGroupId(LongFilter empGroupId) {
        this.empGroupId = empGroupId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(employeeCode, that.employeeCode) &&
            Objects.equals(employeeName, that.employeeName) &&
            Objects.equals(username, that.username) &&
            Objects.equals(password, that.password) &&
            Objects.equals(active, that.active) &&
            Objects.equals(companyCode, that.companyCode) &&
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(organizationId, that.organizationId) &&
            Objects.equals(employeeLastName, that.employeeLastName) &&
            Objects.equals(employeeMiddleName, that.employeeMiddleName) &&
            Objects.equals(employeeTitleId, that.employeeTitleId) &&
            Objects.equals(employeeTitleName, that.employeeTitleName) &&
            Objects.equals(employeeFullName, that.employeeFullName) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(empGroupId, that.empGroupId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            employeeCode,
            employeeName,
            username,
            password,
            active,
            companyCode,
            companyName,
            organizationId,
            employeeLastName,
            employeeMiddleName,
            employeeTitleId,
            employeeTitleName,
            employeeFullName,
            createdDate,
            createdBy,
            lastModifiedDate,
            lastModifiedBy,
            empGroupId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (employeeCode != null ? "employeeCode=" + employeeCode + ", " : "") +
            (employeeName != null ? "employeeName=" + employeeName + ", " : "") +
            (username != null ? "username=" + username + ", " : "") +
            (password != null ? "password=" + password + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (companyCode != null ? "companyCode=" + companyCode + ", " : "") +
            (companyName != null ? "companyName=" + companyName + ", " : "") +
            (organizationId != null ? "organizationId=" + organizationId + ", " : "") +
            (employeeLastName != null ? "employeeLastName=" + employeeLastName + ", " : "") +
            (employeeMiddleName != null ? "employeeMiddleName=" + employeeMiddleName + ", " : "") +
            (employeeTitleId != null ? "employeeTitleId=" + employeeTitleId + ", " : "") +
            (employeeTitleName != null ? "employeeTitleName=" + employeeTitleName + ", " : "") +
            (employeeFullName != null ? "employeeFullName=" + employeeFullName + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (empGroupId != null ? "empGroupId=" + empGroupId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
