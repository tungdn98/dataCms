package vn.com.datamanager.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EmployeeLead.
 */
@Entity
@Table(name = "employee_lead")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeLead extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "lead_id")
    private String leadId;

    @Column(name = "employee_id")
    private String employeeId;

    @NotNull
    @Column(name = "lead_code", nullable = false)
    private String leadCode;

    @Column(name = "lead_name")
    private String leadName;

    @Column(name = "lead_potential_level_id")
    private String leadPotentialLevelId;

    @Column(name = "lead_source_id")
    private String leadSourceId;

    @Column(name = "lead_potential_level_name")
    private String leadPotentialLevelName;

    @Column(name = "lead_source_name")
    private String leadSourceName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmployeeLead id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeadId() {
        return this.leadId;
    }

    public EmployeeLead leadId(String leadId) {
        this.setLeadId(leadId);
        return this;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public EmployeeLead employeeId(String employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getLeadCode() {
        return this.leadCode;
    }

    public EmployeeLead leadCode(String leadCode) {
        this.setLeadCode(leadCode);
        return this;
    }

    public void setLeadCode(String leadCode) {
        this.leadCode = leadCode;
    }

    public String getLeadName() {
        return this.leadName;
    }

    public EmployeeLead leadName(String leadName) {
        this.setLeadName(leadName);
        return this;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public String getLeadPotentialLevelId() {
        return this.leadPotentialLevelId;
    }

    public EmployeeLead leadPotentialLevelId(String leadPotentialLevelId) {
        this.setLeadPotentialLevelId(leadPotentialLevelId);
        return this;
    }

    public void setLeadPotentialLevelId(String leadPotentialLevelId) {
        this.leadPotentialLevelId = leadPotentialLevelId;
    }

    public String getLeadSourceId() {
        return this.leadSourceId;
    }

    public EmployeeLead leadSourceId(String leadSourceId) {
        this.setLeadSourceId(leadSourceId);
        return this;
    }

    public void setLeadSourceId(String leadSourceId) {
        this.leadSourceId = leadSourceId;
    }

    public String getLeadPotentialLevelName() {
        return this.leadPotentialLevelName;
    }

    public EmployeeLead leadPotentialLevelName(String leadPotentialLevelName) {
        this.setLeadPotentialLevelName(leadPotentialLevelName);
        return this;
    }

    public void setLeadPotentialLevelName(String leadPotentialLevelName) {
        this.leadPotentialLevelName = leadPotentialLevelName;
    }

    public String getLeadSourceName() {
        return this.leadSourceName;
    }

    public EmployeeLead leadSourceName(String leadSourceName) {
        this.setLeadSourceName(leadSourceName);
        return this;
    }

    public void setLeadSourceName(String leadSourceName) {
        this.leadSourceName = leadSourceName;
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
        if (!(o instanceof EmployeeLead)) {
            return false;
        }
        return id != null && id.equals(((EmployeeLead) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeLead{" +
            "id=" + getId() +
            ", leadId='" + getLeadId() + "'" +
            ", employeeId='" + getEmployeeId() + "'" +
            ", leadCode='" + getLeadCode() + "'" +
            ", leadName='" + getLeadName() + "'" +
            ", leadPotentialLevelId='" + getLeadPotentialLevelId() + "'" +
            ", leadSourceId='" + getLeadSourceId() + "'" +
            ", leadPotentialLevelName='" + getLeadPotentialLevelName() + "'" +
            ", leadSourceName='" + getLeadSourceName() + "'" +
            "}";
    }
}
