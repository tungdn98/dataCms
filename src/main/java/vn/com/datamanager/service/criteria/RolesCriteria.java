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
 * Criteria class for the {@link vn.com.datamanager.domain.Roles} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.RolesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /roles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class RolesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter resourceUrl;

    private StringFilter resourceDesc;

    private InstantFilter createdDate;

    private StringFilter createdBy;

    private InstantFilter lastModifiedDate;

    private StringFilter lastModifiedBy;

    private LongFilter roleGroupId;

    private LongFilter empGroupId;

    private Boolean distinct;

    public RolesCriteria() {}

    public RolesCriteria(RolesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.resourceUrl = other.resourceUrl == null ? null : other.resourceUrl.copy();
        this.resourceDesc = other.resourceDesc == null ? null : other.resourceDesc.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.roleGroupId = other.roleGroupId == null ? null : other.roleGroupId.copy();
        this.empGroupId = other.empGroupId == null ? null : other.empGroupId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RolesCriteria copy() {
        return new RolesCriteria(this);
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

    public StringFilter getResourceUrl() {
        return resourceUrl;
    }

    public StringFilter resourceUrl() {
        if (resourceUrl == null) {
            resourceUrl = new StringFilter();
        }
        return resourceUrl;
    }

    public void setResourceUrl(StringFilter resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public StringFilter getResourceDesc() {
        return resourceDesc;
    }

    public StringFilter resourceDesc() {
        if (resourceDesc == null) {
            resourceDesc = new StringFilter();
        }
        return resourceDesc;
    }

    public void setResourceDesc(StringFilter resourceDesc) {
        this.resourceDesc = resourceDesc;
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

    public LongFilter getRoleGroupId() {
        return roleGroupId;
    }

    public LongFilter roleGroupId() {
        if (roleGroupId == null) {
            roleGroupId = new LongFilter();
        }
        return roleGroupId;
    }

    public void setRoleGroupId(LongFilter roleGroupId) {
        this.roleGroupId = roleGroupId;
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
        final RolesCriteria that = (RolesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(resourceUrl, that.resourceUrl) &&
            Objects.equals(resourceDesc, that.resourceDesc) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(roleGroupId, that.roleGroupId) &&
            Objects.equals(empGroupId, that.empGroupId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            resourceUrl,
            resourceDesc,
            createdDate,
            createdBy,
            lastModifiedDate,
            lastModifiedBy,
            roleGroupId,
            empGroupId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RolesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (resourceUrl != null ? "resourceUrl=" + resourceUrl + ", " : "") +
            (resourceDesc != null ? "resourceDesc=" + resourceDesc + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (roleGroupId != null ? "roleGroupId=" + roleGroupId + ", " : "") +
            (empGroupId != null ? "empGroupId=" + empGroupId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
