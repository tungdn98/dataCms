package vn.com.datamanager.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OpportunityStageReason.
 */
@Entity
@Table(name = "opportunity_stage_reason")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OpportunityStageReason extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "opportunity_stage_reason_id")
    private String opportunityStageReasonId;

    @Column(name = "opportunity_stage_id")
    private String opportunityStageId;

    @Column(name = "opportunity_stage_reason_name")
    private String opportunityStageReasonName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OpportunityStageReason id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpportunityStageReasonId() {
        return this.opportunityStageReasonId;
    }

    public OpportunityStageReason opportunityStageReasonId(String opportunityStageReasonId) {
        this.setOpportunityStageReasonId(opportunityStageReasonId);
        return this;
    }

    public void setOpportunityStageReasonId(String opportunityStageReasonId) {
        this.opportunityStageReasonId = opportunityStageReasonId;
    }

    public String getOpportunityStageId() {
        return this.opportunityStageId;
    }

    public OpportunityStageReason opportunityStageId(String opportunityStageId) {
        this.setOpportunityStageId(opportunityStageId);
        return this;
    }

    public void setOpportunityStageId(String opportunityStageId) {
        this.opportunityStageId = opportunityStageId;
    }

    public String getOpportunityStageReasonName() {
        return this.opportunityStageReasonName;
    }

    public OpportunityStageReason opportunityStageReasonName(String opportunityStageReasonName) {
        this.setOpportunityStageReasonName(opportunityStageReasonName);
        return this;
    }

    public void setOpportunityStageReasonName(String opportunityStageReasonName) {
        this.opportunityStageReasonName = opportunityStageReasonName;
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
        if (!(o instanceof OpportunityStageReason)) {
            return false;
        }
        return id != null && id.equals(((OpportunityStageReason) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OpportunityStageReason{" +
            "id=" + getId() +
            ", opportunityStageReasonId='" + getOpportunityStageReasonId() + "'" +
            ", opportunityStageId='" + getOpportunityStageId() + "'" +
            ", opportunityStageReasonName='" + getOpportunityStageReasonName() + "'" +
            "}";
    }
}
