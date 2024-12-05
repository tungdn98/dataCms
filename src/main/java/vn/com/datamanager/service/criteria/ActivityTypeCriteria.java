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
 * Criteria class for the {@link vn.com.datamanager.domain.ActivityType} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.ActivityTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /activity-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ActivityTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter activityTypeId;

    private StringFilter activityType;

    private StringFilter textStr;

    private Boolean distinct;

    public ActivityTypeCriteria() {}

    public ActivityTypeCriteria(ActivityTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.activityTypeId = other.activityTypeId == null ? null : other.activityTypeId.copy();
        this.activityType = other.activityType == null ? null : other.activityType.copy();
        this.textStr = other.textStr == null ? null : other.textStr.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ActivityTypeCriteria copy() {
        return new ActivityTypeCriteria(this);
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

    public LongFilter getActivityTypeId() {
        return activityTypeId;
    }

    public LongFilter activityTypeId() {
        if (activityTypeId == null) {
            activityTypeId = new LongFilter();
        }
        return activityTypeId;
    }

    public void setActivityTypeId(LongFilter activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public StringFilter getActivityType() {
        return activityType;
    }

    public StringFilter activityType() {
        if (activityType == null) {
            activityType = new StringFilter();
        }
        return activityType;
    }

    public void setActivityType(StringFilter activityType) {
        this.activityType = activityType;
    }

    public StringFilter getTextStr() {
        return textStr;
    }

    public StringFilter textStr() {
        if (textStr == null) {
            textStr = new StringFilter();
        }
        return textStr;
    }

    public void setTextStr(StringFilter textStr) {
        this.textStr = textStr;
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
        final ActivityTypeCriteria that = (ActivityTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(activityTypeId, that.activityTypeId) &&
            Objects.equals(activityType, that.activityType) &&
            Objects.equals(textStr, that.textStr) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, activityTypeId, activityType, textStr, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivityTypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (activityTypeId != null ? "activityTypeId=" + activityTypeId + ", " : "") +
            (activityType != null ? "activityType=" + activityType + ", " : "") +
            (textStr != null ? "textStr=" + textStr + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
