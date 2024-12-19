package vn.com.datamanager.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SaleContract.
 */
@Entity
@Table(name = "sale_contract")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SaleContract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "contract_id")
    private String contractId;

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "contact_signed_date")
    private LocalDate contactSignedDate;

    @Column(name = "contact_signed_title")
    private String contactSignedTitle;

    @Column(name = "contract_end_date")
    private LocalDate contractEndDate;

    @Column(name = "contract_number")
    private String contractNumber;

    @Column(name = "contract_number_input")
    private String contractNumberInput;

    @Column(name = "contract_stage_id")
    private String contractStageId;

    @Column(name = "contract_start_date")
    private LocalDate contractStartDate;

    @Column(name = "owner_employee_id")
    private String ownerEmployeeId;

    @Column(name = "payment_method_id")
    private String paymentMethodId;

    @Column(name = "contract_name")
    private String contractName;

    @Column(name = "contract_type_id")
    private Long contractTypeId;

    @Column(name = "currency_id")
    private String currencyId;

    @Column(name = "grand_total", precision = 21, scale = 2)
    private BigDecimal grandTotal;

    @Column(name = "payment_term_id")
    private String paymentTermId;

    @Column(name = "quote_id")
    private String quoteId;

    @Column(name = "currency_exchange_rate_id")
    private String currencyExchangeRateId;

    @Column(name = "contract_stage_name")
    private String contractStageName;

    @Column(name = "payment_status_id")
    private Long paymentStatusId;

    @Column(name = "period")
    private Integer period;

    @Column(name = "payment")
    private String payment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SaleContract id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractId() {
        return this.contractId;
    }

    public SaleContract contractId(String contractId) {
        this.setContractId(contractId);
        return this;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public SaleContract companyId(String companyId) {
        this.setCompanyId(companyId);
        return this;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public SaleContract accountId(String accountId) {
        this.setAccountId(accountId);
        return this;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public LocalDate getContactSignedDate() {
        return this.contactSignedDate;
    }

    public SaleContract contactSignedDate(LocalDate contactSignedDate) {
        this.setContactSignedDate(contactSignedDate);
        return this;
    }

    public void setContactSignedDate(LocalDate contactSignedDate) {
        this.contactSignedDate = contactSignedDate;
    }

    public String getContactSignedTitle() {
        return this.contactSignedTitle;
    }

    public SaleContract contactSignedTitle(String contactSignedTitle) {
        this.setContactSignedTitle(contactSignedTitle);
        return this;
    }

    public void setContactSignedTitle(String contactSignedTitle) {
        this.contactSignedTitle = contactSignedTitle;
    }

    public LocalDate getContractEndDate() {
        return this.contractEndDate;
    }

    public SaleContract contractEndDate(LocalDate contractEndDate) {
        this.setContractEndDate(contractEndDate);
        return this;
    }

    public void setContractEndDate(LocalDate contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public String getContractNumber() {
        return this.contractNumber;
    }

    public SaleContract contractNumber(String contractNumber) {
        this.setContractNumber(contractNumber);
        return this;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractNumberInput() {
        return this.contractNumberInput;
    }

    public SaleContract contractNumberInput(String contractNumberInput) {
        this.setContractNumberInput(contractNumberInput);
        return this;
    }

    public void setContractNumberInput(String contractNumberInput) {
        this.contractNumberInput = contractNumberInput;
    }

    public String getContractStageId() {
        return this.contractStageId;
    }

    public SaleContract contractStageId(String contractStageId) {
        this.setContractStageId(contractStageId);
        return this;
    }

    public void setContractStageId(String contractStageId) {
        this.contractStageId = contractStageId;
    }

    public LocalDate getContractStartDate() {
        return this.contractStartDate;
    }

    public SaleContract contractStartDate(LocalDate contractStartDate) {
        this.setContractStartDate(contractStartDate);
        return this;
    }

    public void setContractStartDate(LocalDate contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public String getOwnerEmployeeId() {
        return this.ownerEmployeeId;
    }

    public SaleContract ownerEmployeeId(String ownerEmployeeId) {
        this.setOwnerEmployeeId(ownerEmployeeId);
        return this;
    }

    public void setOwnerEmployeeId(String ownerEmployeeId) {
        this.ownerEmployeeId = ownerEmployeeId;
    }

    public String getPaymentMethodId() {
        return this.paymentMethodId;
    }

    public SaleContract paymentMethodId(String paymentMethodId) {
        this.setPaymentMethodId(paymentMethodId);
        return this;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getContractName() {
        return this.contractName;
    }

    public SaleContract contractName(String contractName) {
        this.setContractName(contractName);
        return this;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Long getContractTypeId() {
        return this.contractTypeId;
    }

    public SaleContract contractTypeId(Long contractTypeId) {
        this.setContractTypeId(contractTypeId);
        return this;
    }

    public void setContractTypeId(Long contractTypeId) {
        this.contractTypeId = contractTypeId;
    }

    public String getCurrencyId() {
        return this.currencyId;
    }

    public SaleContract currencyId(String currencyId) {
        this.setCurrencyId(currencyId);
        return this;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public BigDecimal getGrandTotal() {
        return this.grandTotal;
    }

    public SaleContract grandTotal(BigDecimal grandTotal) {
        this.setGrandTotal(grandTotal);
        return this;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getPaymentTermId() {
        return this.paymentTermId;
    }

    public SaleContract paymentTermId(String paymentTermId) {
        this.setPaymentTermId(paymentTermId);
        return this;
    }

    public void setPaymentTermId(String paymentTermId) {
        this.paymentTermId = paymentTermId;
    }

    public String getQuoteId() {
        return this.quoteId;
    }

    public SaleContract quoteId(String quoteId) {
        this.setQuoteId(quoteId);
        return this;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getCurrencyExchangeRateId() {
        return this.currencyExchangeRateId;
    }

    public SaleContract currencyExchangeRateId(String currencyExchangeRateId) {
        this.setCurrencyExchangeRateId(currencyExchangeRateId);
        return this;
    }

    public void setCurrencyExchangeRateId(String currencyExchangeRateId) {
        this.currencyExchangeRateId = currencyExchangeRateId;
    }

    public String getContractStageName() {
        return this.contractStageName;
    }

    public SaleContract contractStageName(String contractStageName) {
        this.setContractStageName(contractStageName);
        return this;
    }

    public void setContractStageName(String contractStageName) {
        this.contractStageName = contractStageName;
    }

    public Long getPaymentStatusId() {
        return this.paymentStatusId;
    }

    public SaleContract paymentStatusId(Long paymentStatusId) {
        this.setPaymentStatusId(paymentStatusId);
        return this;
    }

    public void setPaymentStatusId(Long paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    public Integer getPeriod() {
        return this.period;
    }

    public SaleContract period(Integer period) {
        this.setPeriod(period);
        return this;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getPayment() {
        return this.payment;
    }

    public SaleContract payment(String payment) {
        this.setPayment(payment);
        return this;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaleContract)) {
            return false;
        }
        return id != null && id.equals(((SaleContract) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaleContract{" +
            "id=" + getId() +
            ", contractId='" + getContractId() + "'" +
            ", companyId='" + getCompanyId() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", contactSignedDate='" + getContactSignedDate() + "'" +
            ", contactSignedTitle='" + getContactSignedTitle() + "'" +
            ", contractEndDate='" + getContractEndDate() + "'" +
            ", contractNumber='" + getContractNumber() + "'" +
            ", contractNumberInput='" + getContractNumberInput() + "'" +
            ", contractStageId='" + getContractStageId() + "'" +
            ", contractStartDate='" + getContractStartDate() + "'" +
            ", ownerEmployeeId='" + getOwnerEmployeeId() + "'" +
            ", paymentMethodId='" + getPaymentMethodId() + "'" +
            ", contractName='" + getContractName() + "'" +
            ", contractTypeId=" + getContractTypeId() +
            ", currencyId='" + getCurrencyId() + "'" +
            ", grandTotal=" + getGrandTotal() +
            ", paymentTermId='" + getPaymentTermId() + "'" +
            ", quoteId='" + getQuoteId() + "'" +
            ", currencyExchangeRateId='" + getCurrencyExchangeRateId() + "'" +
            ", contractStageName='" + getContractStageName() + "'" +
            ", paymentStatusId=" + getPaymentStatusId() +
            ", period=" + getPeriod() +
            ", payment='" + getPayment() + "'" +
            "}";
    }
}
