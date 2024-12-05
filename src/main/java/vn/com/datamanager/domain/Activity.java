package vn.com.datamanager.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "activity_id")
    private String activityId;

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "name")
    private String name;

    @Column(name = "state")
    private String state;

    @Column(name = "type")
    private String type;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "activity_type_id")
    private String activityTypeId;

    @Column(name = "object_type_id")
    private String objectTypeId;

    @Column(name = "priority_id")
    private String priorityId;

    @Column(name = "opportunity_id")
    private String opportunityId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "contract_id")
    private String contractId;

    @Column(name = "priority_name")
    private String priorityName;

    @Column(name = "responsible_id")
    private String responsibleId;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "closed_on")
    private Instant closedOn;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "duration_unit_id")
    private String durationUnitId;

    @Column(name = "conversion", precision = 21, scale = 2)
    private BigDecimal conversion;

    @Column(name = "text_str")
    private String textStr;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Activity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityId() {
        return this.activityId;
    }

    public Activity activityId(String activityId) {
        this.setActivityId(activityId);
        return this;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public Activity companyId(String companyId) {
        this.setCompanyId(companyId);
        return this;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Instant getCreateDate() {
        return this.createDate;
    }

    public Activity createDate(Instant createDate) {
        this.setCreateDate(createDate);
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public LocalDate getDeadline() {
        return this.deadline;
    }

    public Activity deadline(LocalDate deadline) {
        this.setDeadline(deadline);
        return this;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getName() {
        return this.name;
    }

    public Activity name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return this.state;
    }

    public Activity state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return this.type;
    }

    public Activity type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public Activity accountId(String accountId) {
        this.setAccountId(accountId);
        return this;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getActivityTypeId() {
        return this.activityTypeId;
    }

    public Activity activityTypeId(String activityTypeId) {
        this.setActivityTypeId(activityTypeId);
        return this;
    }

    public void setActivityTypeId(String activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getObjectTypeId() {
        return this.objectTypeId;
    }

    public Activity objectTypeId(String objectTypeId) {
        this.setObjectTypeId(objectTypeId);
        return this;
    }

    public void setObjectTypeId(String objectTypeId) {
        this.objectTypeId = objectTypeId;
    }

    public String getPriorityId() {
        return this.priorityId;
    }

    public Activity priorityId(String priorityId) {
        this.setPriorityId(priorityId);
        return this;
    }

    public void setPriorityId(String priorityId) {
        this.priorityId = priorityId;
    }

    public String getOpportunityId() {
        return this.opportunityId;
    }

    public Activity opportunityId(String opportunityId) {
        this.setOpportunityId(opportunityId);
        return this;
    }

    public void setOpportunityId(String opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public Activity orderId(String orderId) {
        this.setOrderId(orderId);
        return this;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getContractId() {
        return this.contractId;
    }

    public Activity contractId(String contractId) {
        this.setContractId(contractId);
        return this;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getPriorityName() {
        return this.priorityName;
    }

    public Activity priorityName(String priorityName) {
        this.setPriorityName(priorityName);
        return this;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public String getResponsibleId() {
        return this.responsibleId;
    }

    public Activity responsibleId(String responsibleId) {
        this.setResponsibleId(responsibleId);
        return this;
    }

    public void setResponsibleId(String responsibleId) {
        this.responsibleId = responsibleId;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Activity startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getClosedOn() {
        return this.closedOn;
    }

    public Activity closedOn(Instant closedOn) {
        this.setClosedOn(closedOn);
        return this;
    }

    public void setClosedOn(Instant closedOn) {
        this.closedOn = closedOn;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Activity duration(Integer duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDurationUnitId() {
        return this.durationUnitId;
    }

    public Activity durationUnitId(String durationUnitId) {
        this.setDurationUnitId(durationUnitId);
        return this;
    }

    public void setDurationUnitId(String durationUnitId) {
        this.durationUnitId = durationUnitId;
    }

    public BigDecimal getConversion() {
        return this.conversion;
    }

    public Activity conversion(BigDecimal conversion) {
        this.setConversion(conversion);
        return this;
    }

    public void setConversion(BigDecimal conversion) {
        this.conversion = conversion;
    }

    public String getTextStr() {
        return this.textStr;
    }

    public Activity textStr(String textStr) {
        this.setTextStr(textStr);
        return this;
    }

    public void setTextStr(String textStr) {
        this.textStr = textStr;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activity)) {
            return false;
        }
        return id != null && id.equals(((Activity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", activityId='" + getActivityId() + "'" +
            ", companyId='" + getCompanyId() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", deadline='" + getDeadline() + "'" +
            ", name='" + getName() + "'" +
            ", state='" + getState() + "'" +
            ", type='" + getType() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", activityTypeId='" + getActivityTypeId() + "'" +
            ", objectTypeId='" + getObjectTypeId() + "'" +
            ", priorityId='" + getPriorityId() + "'" +
            ", opportunityId='" + getOpportunityId() + "'" +
            ", orderId='" + getOrderId() + "'" +
            ", contractId='" + getContractId() + "'" +
            ", priorityName='" + getPriorityName() + "'" +
            ", responsibleId='" + getResponsibleId() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", closedOn='" + getClosedOn() + "'" +
            ", duration=" + getDuration() +
            ", durationUnitId='" + getDurationUnitId() + "'" +
            ", conversion=" + getConversion() +
            ", textStr='" + getTextStr() + "'" +
            "}";
    }
}
