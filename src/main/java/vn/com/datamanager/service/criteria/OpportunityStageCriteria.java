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
 * Criteria class for the {@link vn.com.datamanager.domain.OpportunityStage} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.OpportunityStageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /opportunity-stages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class OpportunityStageCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter opportunityStageId;

    private StringFilter opportunityStageName;

    private StringFilter opportunityStageCode;

    private Boolean distinct;

    public OpportunityStageCriteria() {}

    public OpportunityStageCriteria(OpportunityStageCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.opportunityStageId = other.opportunityStageId == null ? null : other.opportunityStageId.copy();
        this.opportunityStageName = other.opportunityStageName == null ? null : other.opportunityStageName.copy();
        this.opportunityStageCode = other.opportunityStageCode == null ? null : other.opportunityStageCode.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OpportunityStageCriteria copy() {
        return new OpportunityStageCriteria(this);
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

    public StringFilter getOpportunityStageName() {
        return opportunityStageName;
    }

    public StringFilter opportunityStageName() {
        if (opportunityStageName == null) {
            opportunityStageName = new StringFilter();
        }
        return opportunityStageName;
    }

    public void setOpportunityStageName(StringFilter opportunityStageName) {
        this.opportunityStageName = opportunityStageName;
    }

    public StringFilter getOpportunityStageCode() {
        return opportunityStageCode;
    }

    public StringFilter opportunityStageCode() {
        if (opportunityStageCode == null) {
            opportunityStageCode = new StringFilter();
        }
        return opportunityStageCode;
    }

    public void setOpportunityStageCode(StringFilter opportunityStageCode) {
        this.opportunityStageCode = opportunityStageCode;
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
        final OpportunityStageCriteria that = (OpportunityStageCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(opportunityStageId, that.opportunityStageId) &&
            Objects.equals(opportunityStageName, that.opportunityStageName) &&
            Objects.equals(opportunityStageCode, that.opportunityStageCode) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, opportunityStageId, opportunityStageName, opportunityStageCode, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OpportunityStageCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (opportunityStageId != null ? "opportunityStageId=" + opportunityStageId + ", " : "") +
            (opportunityStageName != null ? "opportunityStageName=" + opportunityStageName + ", " : "") +
            (opportunityStageCode != null ? "opportunityStageCode=" + opportunityStageCode + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
