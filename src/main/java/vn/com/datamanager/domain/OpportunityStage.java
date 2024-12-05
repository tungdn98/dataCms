package vn.com.datamanager.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OpportunityStage.
 */
@Entity
@Table(name = "opportunity_stage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OpportunityStage extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "opportunity_stage_id")
    private String opportunityStageId;

    @Column(name = "opportunity_stage_name")
    private String opportunityStageName;

    @Column(name = "opportunity_stage_code")
    private String opportunityStageCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OpportunityStage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpportunityStageId() {
        return this.opportunityStageId;
    }

    public OpportunityStage opportunityStageId(String opportunityStageId) {
        this.setOpportunityStageId(opportunityStageId);
        return this;
    }

    public void setOpportunityStageId(String opportunityStageId) {
        this.opportunityStageId = opportunityStageId;
    }

    public String getOpportunityStageName() {
        return this.opportunityStageName;
    }

    public OpportunityStage opportunityStageName(String opportunityStageName) {
        this.setOpportunityStageName(opportunityStageName);
        return this;
    }

    public void setOpportunityStageName(String opportunityStageName) {
        this.opportunityStageName = opportunityStageName;
    }

    public String getOpportunityStageCode() {
        return this.opportunityStageCode;
    }

    public OpportunityStage opportunityStageCode(String opportunityStageCode) {
        this.setOpportunityStageCode(opportunityStageCode);
        return this;
    }

    public void setOpportunityStageCode(String opportunityStageCode) {
        this.opportunityStageCode = opportunityStageCode;
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
        if (!(o instanceof OpportunityStage)) {
            return false;
        }
        return id != null && id.equals(((OpportunityStage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OpportunityStage{" +
            "id=" + getId() +
            ", opportunityStageId='" + getOpportunityStageId() + "'" +
            ", opportunityStageName='" + getOpportunityStageName() + "'" +
            ", opportunityStageCode='" + getOpportunityStageCode() + "'" +
            "}";
    }
}
