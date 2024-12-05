package vn.com.datamanager.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SaleOpportunity.
 */
@Entity
@Table(name = "sale_opportunity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SaleOpportunity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "opportunity_id")
    private Long opportunityId;

    @NotNull
    @Column(name = "opportunity_code", nullable = false)
    private String opportunityCode;

    @NotNull
    @Column(name = "opportunity_name", nullable = false)
    private String opportunityName;

    @Column(name = "opportunity_type_name")
    private String opportunityTypeName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "close_date")
    private LocalDate closeDate;

    @Column(name = "stage_id")
    private Long stageId;

    @Column(name = "stage_reason_id")
    private Long stageReasonId;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "lead_id")
    private Long leadId;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "sales_price_prd", precision = 21, scale = 2)
    private BigDecimal salesPricePrd;

    @Column(name = "value", precision = 21, scale = 2)
    private BigDecimal value;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SaleOpportunity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOpportunityId() {
        return this.opportunityId;
    }

    public SaleOpportunity opportunityId(Long opportunityId) {
        this.setOpportunityId(opportunityId);
        return this;
    }

    public void setOpportunityId(Long opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getOpportunityCode() {
        return this.opportunityCode;
    }

    public SaleOpportunity opportunityCode(String opportunityCode) {
        this.setOpportunityCode(opportunityCode);
        return this;
    }

    public void setOpportunityCode(String opportunityCode) {
        this.opportunityCode = opportunityCode;
    }

    public String getOpportunityName() {
        return this.opportunityName;
    }

    public SaleOpportunity opportunityName(String opportunityName) {
        this.setOpportunityName(opportunityName);
        return this;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public String getOpportunityTypeName() {
        return this.opportunityTypeName;
    }

    public SaleOpportunity opportunityTypeName(String opportunityTypeName) {
        this.setOpportunityTypeName(opportunityTypeName);
        return this;
    }

    public void setOpportunityTypeName(String opportunityTypeName) {
        this.opportunityTypeName = opportunityTypeName;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public SaleOpportunity startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getCloseDate() {
        return this.closeDate;
    }

    public SaleOpportunity closeDate(LocalDate closeDate) {
        this.setCloseDate(closeDate);
        return this;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public Long getStageId() {
        return this.stageId;
    }

    public SaleOpportunity stageId(Long stageId) {
        this.setStageId(stageId);
        return this;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public Long getStageReasonId() {
        return this.stageReasonId;
    }

    public SaleOpportunity stageReasonId(Long stageReasonId) {
        this.setStageReasonId(stageReasonId);
        return this;
    }

    public void setStageReasonId(Long stageReasonId) {
        this.stageReasonId = stageReasonId;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public SaleOpportunity employeeId(Long employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getLeadId() {
        return this.leadId;
    }

    public SaleOpportunity leadId(Long leadId) {
        this.setLeadId(leadId);
        return this;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public SaleOpportunity currencyCode(String currencyCode) {
        this.setCurrencyCode(currencyCode);
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public SaleOpportunity accountId(Long accountId) {
        this.setAccountId(accountId);
        return this;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getProductId() {
        return this.productId;
    }

    public SaleOpportunity productId(Long productId) {
        this.setProductId(productId);
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getSalesPricePrd() {
        return this.salesPricePrd;
    }

    public SaleOpportunity salesPricePrd(BigDecimal salesPricePrd) {
        this.setSalesPricePrd(salesPricePrd);
        return this;
    }

    public void setSalesPricePrd(BigDecimal salesPricePrd) {
        this.salesPricePrd = salesPricePrd;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public SaleOpportunity value(BigDecimal value) {
        this.setValue(value);
        return this;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaleOpportunity)) {
            return false;
        }
        return id != null && id.equals(((SaleOpportunity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaleOpportunity{" +
            "id=" + getId() +
            ", opportunityId=" + getOpportunityId() +
            ", opportunityCode='" + getOpportunityCode() + "'" +
            ", opportunityName='" + getOpportunityName() + "'" +
            ", opportunityTypeName='" + getOpportunityTypeName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", closeDate='" + getCloseDate() + "'" +
            ", stageId=" + getStageId() +
            ", stageReasonId=" + getStageReasonId() +
            ", employeeId=" + getEmployeeId() +
            ", leadId=" + getLeadId() +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", accountId=" + getAccountId() +
            ", productId=" + getProductId() +
            ", salesPricePrd=" + getSalesPricePrd() +
            ", value=" + getValue() +
            "}";
    }
}
