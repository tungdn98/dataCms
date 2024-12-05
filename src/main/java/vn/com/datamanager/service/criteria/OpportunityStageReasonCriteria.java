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
 * Criteria class for the {@link vn.com.datamanager.domain.OpportunityStageReason} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.OpportunityStageReasonResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /opportunity-stage-reasons?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class OpportunityStageReasonCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter opportunityStageReasonId;

    private StringFilter opportunityStageId;

    private StringFilter opportunityStageReasonName;

    private Boolean distinct;

    public OpportunityStageReasonCriteria() {}

    public OpportunityStageReasonCriteria(OpportunityStageReasonCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.opportunityStageReasonId = other.opportunityStageReasonId == null ? null : other.opportunityStageReasonId.copy();
        this.opportunityStageId = other.opportunityStageId == null ? null : other.opportunityStageId.copy();
        this.opportunityStageReasonName = other.opportunityStageReasonName == null ? null : other.opportunityStageReasonName.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OpportunityStageReasonCriteria copy() {
        return new OpportunityStageReasonCriteria(this);
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

    public StringFilter getOpportunityStageReasonId() {
        return opportunityStageReasonId;
    }

    public StringFilter opportunityStageReasonId() {
        if (opportunityStageReasonId == null) {
            opportunityStageReasonId = new StringFilter();
        }
        return opportunityStageReasonId;
    }

    public void setOpportunityStageReasonId(StringFilter opportunityStageReasonId) {
        this.opportunityStageReasonId = opportunityStageReasonId;
    }

    public StringFilter getOpportunityStageId() {
        return opportunityStageId;
    }

    public StringFilter opportunityStageId() {
        if (opportunityStageId == null) {
            opportunityStageId = new StringFilter();
        }
        return opportunityStageId;
    }

    public void setOpportunityStageId(StringFilter opportunityStageId) {
        this.opportunityStageId = opportunityStageId;
    }

    public StringFilter getOpportunityStageReasonName() {
        return opportunityStageReasonName;
    }

    public StringFilter opportunityStageReasonName() {
        if (opportunityStageReasonName == null) {
            opportunityStageReasonName = new StringFilter();
        }
        return opportunityStageReasonName;
    }

    public void setOpportunityStageReasonName(StringFilter opportunityStageReasonName) {
        this.opportunityStageReasonName = opportunityStageReasonName;
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
        final OpportunityStageReasonCriteria that = (OpportunityStageReasonCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(opportunityStageReasonId, that.opportunityStageReasonId) &&
            Objects.equals(opportunityStageId, that.opportunityStageId) &&
            Objects.equals(opportunityStageReasonName, that.opportunityStageReasonName) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, opportunityStageReasonId, opportunityStageId, opportunityStageReasonName, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OpportunityStageReasonCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (opportunityStageReasonId != null ? "opportunityStageReasonId=" + opportunityStageReasonId + ", " : "") +
            (opportunityStageId != null ? "opportunityStageId=" + opportunityStageId + ", " : "") +
            (opportunityStageReasonName != null ? "opportunityStageReasonName=" + opportunityStageReasonName + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
