package vn.com.datamanager.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link vn.com.datamanager.domain.Activity} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.ActivityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /activities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ActivityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter activityId;

    private StringFilter companyId;

    private InstantFilter createDate;

    private LocalDateFilter deadline;

    private StringFilter name;

    private StringFilter state;

    private StringFilter type;

    private StringFilter accountId;

    private StringFilter activityTypeId;

    private StringFilter objectTypeId;

    private StringFilter priorityId;

    private StringFilter opportunityId;

    private StringFilter orderId;

    private StringFilter contractId;

    private StringFilter priorityName;

    private StringFilter responsibleId;

    private InstantFilter startDate;

    private InstantFilter closedOn;

    private IntegerFilter duration;

    private StringFilter durationUnitId;

    private BigDecimalFilter conversion;

    private StringFilter textStr;

    private Boolean distinct;

    public ActivityCriteria() {}

    public ActivityCriteria(ActivityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.activityId = other.activityId == null ? null : other.activityId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.createDate = other.createDate == null ? null : other.createDate.copy();
        this.deadline = other.deadline == null ? null : other.deadline.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.accountId = other.accountId == null ? null : other.accountId.copy();
        this.activityTypeId = other.activityTypeId == null ? null : other.activityTypeId.copy();
        this.objectTypeId = other.objectTypeId == null ? null : other.objectTypeId.copy();
        this.priorityId = other.priorityId == null ? null : other.priorityId.copy();
        this.opportunityId = other.opportunityId == null ? null : other.opportunityId.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
        this.contractId = other.contractId == null ? null : other.contractId.copy();
        this.priorityName = other.priorityName == null ? null : other.priorityName.copy();
        this.responsibleId = other.responsibleId == null ? null : other.responsibleId.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.closedOn = other.closedOn == null ? null : other.closedOn.copy();
        this.duration = other.duration == null ? null : other.duration.copy();
        this.durationUnitId = other.durationUnitId == null ? null : other.durationUnitId.copy();
        this.conversion = other.conversion == null ? null : other.conversion.copy();
        this.textStr = other.textStr == null ? null : other.textStr.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ActivityCriteria copy() {
        return new ActivityCriteria(this);
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

    public StringFilter getActivityId() {
        return activityId;
    }

    public StringFilter activityId() {
        if (activityId == null) {
            activityId = new StringFilter();
        }
        return activityId;
    }

    public void setActivityId(StringFilter activityId) {
        this.activityId = activityId;
    }

    public StringFilter getCompanyId() {
        return companyId;
    }

    public StringFilter companyId() {
        if (companyId == null) {
            companyId = new StringFilter();
        }
        return companyId;
    }

    public void setCompanyId(StringFilter companyId) {
        this.companyId = companyId;
    }

    public InstantFilter getCreateDate() {
        return createDate;
    }

    public InstantFilter createDate() {
        if (createDate == null) {
            createDate = new InstantFilter();
        }
        return createDate;
    }

    public void setCreateDate(InstantFilter createDate) {
        this.createDate = createDate;
    }

    public LocalDateFilter getDeadline() {
        return deadline;
    }

    public LocalDateFilter deadline() {
        if (deadline == null) {
            deadline = new LocalDateFilter();
        }
        return deadline;
    }

    public void setDeadline(LocalDateFilter deadline) {
        this.deadline = deadline;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getState() {
        return state;
    }

    public StringFilter state() {
        if (state == null) {
            state = new StringFilter();
        }
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getAccountId() {
        return accountId;
    }

    public StringFilter accountId() {
        if (accountId == null) {
            accountId = new StringFilter();
        }
        return accountId;
    }

    public void setAccountId(StringFilter accountId) {
        this.accountId = accountId;
    }

    public StringFilter getActivityTypeId() {
        return activityTypeId;
    }

    public StringFilter activityTypeId() {
        if (activityTypeId == null) {
            activityTypeId = new StringFilter();
        }
        return activityTypeId;
    }

    public void setActivityTypeId(StringFilter activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public StringFilter getObjectTypeId() {
        return objectTypeId;
    }

    public StringFilter objectTypeId() {
        if (objectTypeId == null) {
            objectTypeId = new StringFilter();
        }
        return objectTypeId;
    }

    public void setObjectTypeId(StringFilter objectTypeId) {
        this.objectTypeId = objectTypeId;
    }

    public StringFilter getPriorityId() {
        return priorityId;
    }

    public StringFilter priorityId() {
        if (priorityId == null) {
            priorityId = new StringFilter();
        }
        return priorityId;
    }

    public void setPriorityId(StringFilter priorityId) {
        this.priorityId = priorityId;
    }

    public StringFilter getOpportunityId() {
        return opportunityId;
    }

    public StringFilter opportunityId() {
        if (opportunityId == null) {
            opportunityId = new StringFilter();
        }
        return opportunityId;
    }

    public void setOpportunityId(StringFilter opportunityId) {
        this.opportunityId = opportunityId;
    }

    public StringFilter getOrderId() {
        return orderId;
    }

    public StringFilter orderId() {
        if (orderId == null) {
            orderId = new StringFilter();
        }
        return orderId;
    }

    public void setOrderId(StringFilter orderId) {
        this.orderId = orderId;
    }

    public StringFilter getContractId() {
        return contractId;
    }

    public StringFilter contractId() {
        if (contractId == null) {
            contractId = new StringFilter();
        }
        return contractId;
    }

    public void setContractId(StringFilter contractId) {
        this.contractId = contractId;
    }

    public StringFilter getPriorityName() {
        return priorityName;
    }

    public StringFilter priorityName() {
        if (priorityName == null) {
            priorityName = new StringFilter();
        }
        return priorityName;
    }

    public void setPriorityName(StringFilter priorityName) {
        this.priorityName = priorityName;
    }

    public StringFilter getResponsibleId() {
        return responsibleId;
    }

    public StringFilter responsibleId() {
        if (responsibleId == null) {
            responsibleId = new StringFilter();
        }
        return responsibleId;
    }

    public void setResponsibleId(StringFilter responsibleId) {
        this.responsibleId = responsibleId;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public InstantFilter startDate() {
        if (startDate == null) {
            startDate = new InstantFilter();
        }
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public InstantFilter getClosedOn() {
        return closedOn;
    }

    public InstantFilter closedOn() {
        if (closedOn == null) {
            closedOn = new InstantFilter();
        }
        return closedOn;
    }

    public void setClosedOn(InstantFilter closedOn) {
        this.closedOn = closedOn;
    }

    public IntegerFilter getDuration() {
        return duration;
    }

    public IntegerFilter duration() {
        if (duration == null) {
            duration = new IntegerFilter();
        }
        return duration;
    }

    public void setDuration(IntegerFilter duration) {
        this.duration = duration;
    }

    public StringFilter getDurationUnitId() {
        return durationUnitId;
    }

    public StringFilter durationUnitId() {
        if (durationUnitId == null) {
            durationUnitId = new StringFilter();
        }
        return durationUnitId;
    }

    public void setDurationUnitId(StringFilter durationUnitId) {
        this.durationUnitId = durationUnitId;
    }

    public BigDecimalFilter getConversion() {
        return conversion;
    }

    public BigDecimalFilter conversion() {
        if (conversion == null) {
            conversion = new BigDecimalFilter();
        }
        return conversion;
    }

    public void setConversion(BigDecimalFilter conversion) {
        this.conversion = conversion;
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
        final ActivityCriteria that = (ActivityCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(activityId, that.activityId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(createDate, that.createDate) &&
            Objects.equals(deadline, that.deadline) &&
            Objects.equals(name, that.name) &&
            Objects.equals(state, that.state) &&
            Objects.equals(type, that.type) &&
            Objects.equals(accountId, that.accountId) &&
            Objects.equals(activityTypeId, that.activityTypeId) &&
            Objects.equals(objectTypeId, that.objectTypeId) &&
            Objects.equals(priorityId, that.priorityId) &&
            Objects.equals(opportunityId, that.opportunityId) &&
            Objects.equals(orderId, that.orderId) &&
            Objects.equals(contractId, that.contractId) &&
            Objects.equals(priorityName, that.priorityName) &&
            Objects.equals(responsibleId, that.responsibleId) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(closedOn, that.closedOn) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(durationUnitId, that.durationUnitId) &&
            Objects.equals(conversion, that.conversion) &&
            Objects.equals(textStr, that.textStr) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            activityId,
            companyId,
            createDate,
            deadline,
            name,
            state,
            type,
            accountId,
            activityTypeId,
            objectTypeId,
            priorityId,
            opportunityId,
            orderId,
            contractId,
            priorityName,
            responsibleId,
            startDate,
            closedOn,
            duration,
            durationUnitId,
            conversion,
            textStr,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivityCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (activityId != null ? "activityId=" + activityId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (createDate != null ? "createDate=" + createDate + ", " : "") +
            (deadline != null ? "deadline=" + deadline + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (state != null ? "state=" + state + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (accountId != null ? "accountId=" + accountId + ", " : "") +
            (activityTypeId != null ? "activityTypeId=" + activityTypeId + ", " : "") +
            (objectTypeId != null ? "objectTypeId=" + objectTypeId + ", " : "") +
            (priorityId != null ? "priorityId=" + priorityId + ", " : "") +
            (opportunityId != null ? "opportunityId=" + opportunityId + ", " : "") +
            (orderId != null ? "orderId=" + orderId + ", " : "") +
            (contractId != null ? "contractId=" + contractId + ", " : "") +
            (priorityName != null ? "priorityName=" + priorityName + ", " : "") +
            (responsibleId != null ? "responsibleId=" + responsibleId + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (closedOn != null ? "closedOn=" + closedOn + ", " : "") +
            (duration != null ? "duration=" + duration + ", " : "") +
            (durationUnitId != null ? "durationUnitId=" + durationUnitId + ", " : "") +
            (conversion != null ? "conversion=" + conversion + ", " : "") +
            (textStr != null ? "textStr=" + textStr + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
