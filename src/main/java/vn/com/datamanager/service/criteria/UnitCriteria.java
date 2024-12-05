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
 * Criteria class for the {@link vn.com.datamanager.domain.Unit} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.UnitResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /units?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class UnitCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter unitCode;

    private StringFilter unitName;

    private Boolean distinct;

    public UnitCriteria() {}

    public UnitCriteria(UnitCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.unitCode = other.unitCode == null ? null : other.unitCode.copy();
        this.unitName = other.unitName == null ? null : other.unitName.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UnitCriteria copy() {
        return new UnitCriteria(this);
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

    public StringFilter getUnitCode() {
        return unitCode;
    }

    public StringFilter unitCode() {
        if (unitCode == null) {
            unitCode = new StringFilter();
        }
        return unitCode;
    }

    public void setUnitCode(StringFilter unitCode) {
        this.unitCode = unitCode;
    }

    public StringFilter getUnitName() {
        return unitName;
    }

    public StringFilter unitName() {
        if (unitName == null) {
            unitName = new StringFilter();
        }
        return unitName;
    }

    public void setUnitName(StringFilter unitName) {
        this.unitName = unitName;
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
        final UnitCriteria that = (UnitCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(unitCode, that.unitCode) &&
            Objects.equals(unitName, that.unitName) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, unitCode, unitName, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnitCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (unitCode != null ? "unitCode=" + unitCode + ", " : "") +
            (unitName != null ? "unitName=" + unitName + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
