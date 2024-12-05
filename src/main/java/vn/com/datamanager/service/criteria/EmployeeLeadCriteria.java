package vn.com.datamanager.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link vn.com.datamanager.domain.EmployeeLead} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.EmployeeLeadResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-leads?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class EmployeeLeadCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter leadId;

    private StringFilter employeeId;

    private StringFilter leadCode;

    private StringFilter leadName;

    private StringFilter leadPotentialLevelId;

    private StringFilter leadSourceId;

    private StringFilter leadPotentialLevelName;

    private StringFilter leadSourceName;

    private Boolean distinct;

    public EmployeeLeadCriteria() {}

    public EmployeeLeadCriteria(EmployeeLeadCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.leadId = other.leadId == null ? null : other.leadId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.leadCode = other.leadCode == null ? null : other.leadCode.copy();
        this.leadName = other.leadName == null ? null : other.leadName.copy();
        this.leadPotentialLevelId = other.leadPotentialLevelId == null ? null : other.leadPotentialLevelId.copy();
        this.leadSourceId = other.leadSourceId == null ? null : other.leadSourceId.copy();
        this.leadPotentialLevelName = other.leadPotentialLevelName == null ? null : other.leadPotentialLevelName.copy();
        this.leadSourceName = other.leadSourceName == null ? null : other.leadSourceName.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeLeadCriteria copy() {
        return new EmployeeLeadCriteria(this);
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

    public StringFilter getLeadId() {
        return leadId;
    }

    public StringFilter leadId() {
        if (leadId == null) {
            leadId = new StringFilter();
        }
        return leadId;
    }

    public void setLeadId(StringFilter leadId) {
        this.leadId = leadId;
    }

    public StringFilter getEmployeeId() {
        return employeeId;
    }

    public StringFilter employeeId() {
        if (employeeId == null) {
            employeeId = new StringFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(StringFilter employeeId) {
        this.employeeId = employeeId;
    }

    public StringFilter getLeadCode() {
        return leadCode;
    }

    public StringFilter leadCode() {
        if (leadCode == null) {
            leadCode = new StringFilter();
        }
        return leadCode;
    }

    public void setLeadCode(StringFilter leadCode) {
        this.leadCode = leadCode;
    }

    public StringFilter getLeadName() {
        return leadName;
    }

    public StringFilter leadName() {
        if (leadName == null) {
            leadName = new StringFilter();
        }
        return leadName;
    }

    public void setLeadName(StringFilter leadName) {
        this.leadName = leadName;
    }

    public StringFilter getLeadPotentialLevelId() {
        return leadPotentialLevelId;
    }

    public StringFilter leadPotentialLevelId() {
        if (leadPotentialLevelId == null) {
            leadPotentialLevelId = new StringFilter();
        }
        return leadPotentialLevelId;
    }

    public void setLeadPotentialLevelId(StringFilter leadPotentialLevelId) {
        this.leadPotentialLevelId = leadPotentialLevelId;
    }

    public StringFilter getLeadSourceId() {
        return leadSourceId;
    }

    public StringFilter leadSourceId() {
        if (leadSourceId == null) {
            leadSourceId = new StringFilter();
        }
        return leadSourceId;
    }

    public void setLeadSourceId(StringFilter leadSourceId) {
        this.leadSourceId = leadSourceId;
    }

    public StringFilter getLeadPotentialLevelName() {
        return leadPotentialLevelName;
    }

    public StringFilter leadPotentialLevelName() {
        if (leadPotentialLevelName == null) {
            leadPotentialLevelName = new StringFilter();
        }
        return leadPotentialLevelName;
    }

    public void setLeadPotentialLevelName(StringFilter leadPotentialLevelName) {
        this.leadPotentialLevelName = leadPotentialLevelName;
    }

    public StringFilter getLeadSourceName() {
        return leadSourceName;
    }

    public StringFilter leadSourceName() {
        if (leadSourceName == null) {
            leadSourceName = new StringFilter();
        }
        return leadSourceName;
    }

    public void setLeadSourceName(StringFilter leadSourceName) {
        this.leadSourceName = leadSourceName;
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
        final EmployeeLeadCriteria that = (EmployeeLeadCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(leadId, that.leadId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(leadCode, that.leadCode) &&
            Objects.equals(leadName, that.leadName) &&
            Objects.equals(leadPotentialLevelId, that.leadPotentialLevelId) &&
            Objects.equals(leadSourceId, that.leadSourceId) &&
            Objects.equals(leadPotentialLevelName, that.leadPotentialLevelName) &&
            Objects.equals(leadSourceName, that.leadSourceName) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            leadId,
            employeeId,
            leadCode,
            leadName,
            leadPotentialLevelId,
            leadSourceId,
            leadPotentialLevelName,
            leadSourceName,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeLeadCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (leadId != null ? "leadId=" + leadId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (leadCode != null ? "leadCode=" + leadCode + ", " : "") +
            (leadName != null ? "leadName=" + leadName + ", " : "") +
            (leadPotentialLevelId != null ? "leadPotentialLevelId=" + leadPotentialLevelId + ", " : "") +
            (leadSourceId != null ? "leadSourceId=" + leadSourceId + ", " : "") +
            (leadPotentialLevelName != null ? "leadPotentialLevelName=" + leadPotentialLevelName + ", " : "") +
            (leadSourceName != null ? "leadSourceName=" + leadSourceName + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
