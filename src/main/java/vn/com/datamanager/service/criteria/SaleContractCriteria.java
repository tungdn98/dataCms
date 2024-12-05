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
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link vn.com.datamanager.domain.SaleContract} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.SaleContractResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sale-contracts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class SaleContractCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter contractId;

    private StringFilter companyId;

    private StringFilter accountId;

    private LocalDateFilter contactSignedDate;

    private StringFilter contactSignedTitle;

    private LocalDateFilter contractEndDate;

    private StringFilter contractNumber;

    private StringFilter contractNumberInput;

    private StringFilter contractStageId;

    private LocalDateFilter contractStartDate;

    private StringFilter ownerEmployeeId;

    private StringFilter paymentMethodId;

    private StringFilter contractName;

    private LongFilter contractTypeId;

    private StringFilter currencyId;

    private BigDecimalFilter grandTotal;

    private StringFilter paymentTermId;

    private StringFilter quoteId;

    private StringFilter currencyExchangeRateId;

    private StringFilter contractStageName;

    private LongFilter paymentStatusId;

    private IntegerFilter period;

    private StringFilter payment;

    private Boolean distinct;

    public SaleContractCriteria() {}

    public SaleContractCriteria(SaleContractCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.contractId = other.contractId == null ? null : other.contractId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.accountId = other.accountId == null ? null : other.accountId.copy();
        this.contactSignedDate = other.contactSignedDate == null ? null : other.contactSignedDate.copy();
        this.contactSignedTitle = other.contactSignedTitle == null ? null : other.contactSignedTitle.copy();
        this.contractEndDate = other.contractEndDate == null ? null : other.contractEndDate.copy();
        this.contractNumber = other.contractNumber == null ? null : other.contractNumber.copy();
        this.contractNumberInput = other.contractNumberInput == null ? null : other.contractNumberInput.copy();
        this.contractStageId = other.contractStageId == null ? null : other.contractStageId.copy();
        this.contractStartDate = other.contractStartDate == null ? null : other.contractStartDate.copy();
        this.ownerEmployeeId = other.ownerEmployeeId == null ? null : other.ownerEmployeeId.copy();
        this.paymentMethodId = other.paymentMethodId == null ? null : other.paymentMethodId.copy();
        this.contractName = other.contractName == null ? null : other.contractName.copy();
        this.contractTypeId = other.contractTypeId == null ? null : other.contractTypeId.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
        this.grandTotal = other.grandTotal == null ? null : other.grandTotal.copy();
        this.paymentTermId = other.paymentTermId == null ? null : other.paymentTermId.copy();
        this.quoteId = other.quoteId == null ? null : other.quoteId.copy();
        this.currencyExchangeRateId = other.currencyExchangeRateId == null ? null : other.currencyExchangeRateId.copy();
        this.contractStageName = other.contractStageName == null ? null : other.contractStageName.copy();
        this.paymentStatusId = other.paymentStatusId == null ? null : other.paymentStatusId.copy();
        this.period = other.period == null ? null : other.period.copy();
        this.payment = other.payment == null ? null : other.payment.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SaleContractCriteria copy() {
        return new SaleContractCriteria(this);
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

    public LocalDateFilter getContactSignedDate() {
        return contactSignedDate;
    }

    public LocalDateFilter contactSignedDate() {
        if (contactSignedDate == null) {
            contactSignedDate = new LocalDateFilter();
        }
        return contactSignedDate;
    }

    public void setContactSignedDate(LocalDateFilter contactSignedDate) {
        this.contactSignedDate = contactSignedDate;
    }

    public StringFilter getContactSignedTitle() {
        return contactSignedTitle;
    }

    public StringFilter contactSignedTitle() {
        if (contactSignedTitle == null) {
            contactSignedTitle = new StringFilter();
        }
        return contactSignedTitle;
    }

    public void setContactSignedTitle(StringFilter contactSignedTitle) {
        this.contactSignedTitle = contactSignedTitle;
    }

    public LocalDateFilter getContractEndDate() {
        return contractEndDate;
    }

    public LocalDateFilter contractEndDate() {
        if (contractEndDate == null) {
            contractEndDate = new LocalDateFilter();
        }
        return contractEndDate;
    }

    public void setContractEndDate(LocalDateFilter contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public StringFilter getContractNumber() {
        return contractNumber;
    }

    public StringFilter contractNumber() {
        if (contractNumber == null) {
            contractNumber = new StringFilter();
        }
        return contractNumber;
    }

    public void setContractNumber(StringFilter contractNumber) {
        this.contractNumber = contractNumber;
    }

    public StringFilter getContractNumberInput() {
        return contractNumberInput;
    }

    public StringFilter contractNumberInput() {
        if (contractNumberInput == null) {
            contractNumberInput = new StringFilter();
        }
        return contractNumberInput;
    }

    public void setContractNumberInput(StringFilter contractNumberInput) {
        this.contractNumberInput = contractNumberInput;
    }

    public StringFilter getContractStageId() {
        return contractStageId;
    }

    public StringFilter contractStageId() {
        if (contractStageId == null) {
            contractStageId = new StringFilter();
        }
        return contractStageId;
    }

    public void setContractStageId(StringFilter contractStageId) {
        this.contractStageId = contractStageId;
    }

    public LocalDateFilter getContractStartDate() {
        return contractStartDate;
    }

    public LocalDateFilter contractStartDate() {
        if (contractStartDate == null) {
            contractStartDate = new LocalDateFilter();
        }
        return contractStartDate;
    }

    public void setContractStartDate(LocalDateFilter contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public StringFilter getOwnerEmployeeId() {
        return ownerEmployeeId;
    }

    public StringFilter ownerEmployeeId() {
        if (ownerEmployeeId == null) {
            ownerEmployeeId = new StringFilter();
        }
        return ownerEmployeeId;
    }

    public void setOwnerEmployeeId(StringFilter ownerEmployeeId) {
        this.ownerEmployeeId = ownerEmployeeId;
    }

    public StringFilter getPaymentMethodId() {
        return paymentMethodId;
    }

    public StringFilter paymentMethodId() {
        if (paymentMethodId == null) {
            paymentMethodId = new StringFilter();
        }
        return paymentMethodId;
    }

    public void setPaymentMethodId(StringFilter paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public StringFilter getContractName() {
        return contractName;
    }

    public StringFilter contractName() {
        if (contractName == null) {
            contractName = new StringFilter();
        }
        return contractName;
    }

    public void setContractName(StringFilter contractName) {
        this.contractName = contractName;
    }

    public LongFilter getContractTypeId() {
        return contractTypeId;
    }

    public LongFilter contractTypeId() {
        if (contractTypeId == null) {
            contractTypeId = new LongFilter();
        }
        return contractTypeId;
    }

    public void setContractTypeId(LongFilter contractTypeId) {
        this.contractTypeId = contractTypeId;
    }

    public StringFilter getCurrencyId() {
        return currencyId;
    }

    public StringFilter currencyId() {
        if (currencyId == null) {
            currencyId = new StringFilter();
        }
        return currencyId;
    }

    public void setCurrencyId(StringFilter currencyId) {
        this.currencyId = currencyId;
    }

    public BigDecimalFilter getGrandTotal() {
        return grandTotal;
    }

    public BigDecimalFilter grandTotal() {
        if (grandTotal == null) {
            grandTotal = new BigDecimalFilter();
        }
        return grandTotal;
    }

    public void setGrandTotal(BigDecimalFilter grandTotal) {
        this.grandTotal = grandTotal;
    }

    public StringFilter getPaymentTermId() {
        return paymentTermId;
    }

    public StringFilter paymentTermId() {
        if (paymentTermId == null) {
            paymentTermId = new StringFilter();
        }
        return paymentTermId;
    }

    public void setPaymentTermId(StringFilter paymentTermId) {
        this.paymentTermId = paymentTermId;
    }

    public StringFilter getQuoteId() {
        return quoteId;
    }

    public StringFilter quoteId() {
        if (quoteId == null) {
            quoteId = new StringFilter();
        }
        return quoteId;
    }

    public void setQuoteId(StringFilter quoteId) {
        this.quoteId = quoteId;
    }

    public StringFilter getCurrencyExchangeRateId() {
        return currencyExchangeRateId;
    }

    public StringFilter currencyExchangeRateId() {
        if (currencyExchangeRateId == null) {
            currencyExchangeRateId = new StringFilter();
        }
        return currencyExchangeRateId;
    }

    public void setCurrencyExchangeRateId(StringFilter currencyExchangeRateId) {
        this.currencyExchangeRateId = currencyExchangeRateId;
    }

    public StringFilter getContractStageName() {
        return contractStageName;
    }

    public StringFilter contractStageName() {
        if (contractStageName == null) {
            contractStageName = new StringFilter();
        }
        return contractStageName;
    }

    public void setContractStageName(StringFilter contractStageName) {
        this.contractStageName = contractStageName;
    }

    public LongFilter getPaymentStatusId() {
        return paymentStatusId;
    }

    public LongFilter paymentStatusId() {
        if (paymentStatusId == null) {
            paymentStatusId = new LongFilter();
        }
        return paymentStatusId;
    }

    public void setPaymentStatusId(LongFilter paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    public IntegerFilter getPeriod() {
        return period;
    }

    public IntegerFilter period() {
        if (period == null) {
            period = new IntegerFilter();
        }
        return period;
    }

    public void setPeriod(IntegerFilter period) {
        this.period = period;
    }

    public StringFilter getPayment() {
        return payment;
    }

    public StringFilter payment() {
        if (payment == null) {
            payment = new StringFilter();
        }
        return payment;
    }

    public void setPayment(StringFilter payment) {
        this.payment = payment;
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
        final SaleContractCriteria that = (SaleContractCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(contractId, that.contractId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(accountId, that.accountId) &&
            Objects.equals(contactSignedDate, that.contactSignedDate) &&
            Objects.equals(contactSignedTitle, that.contactSignedTitle) &&
            Objects.equals(contractEndDate, that.contractEndDate) &&
            Objects.equals(contractNumber, that.contractNumber) &&
            Objects.equals(contractNumberInput, that.contractNumberInput) &&
            Objects.equals(contractStageId, that.contractStageId) &&
            Objects.equals(contractStartDate, that.contractStartDate) &&
            Objects.equals(ownerEmployeeId, that.ownerEmployeeId) &&
            Objects.equals(paymentMethodId, that.paymentMethodId) &&
            Objects.equals(contractName, that.contractName) &&
            Objects.equals(contractTypeId, that.contractTypeId) &&
            Objects.equals(currencyId, that.currencyId) &&
            Objects.equals(grandTotal, that.grandTotal) &&
            Objects.equals(paymentTermId, that.paymentTermId) &&
            Objects.equals(quoteId, that.quoteId) &&
            Objects.equals(currencyExchangeRateId, that.currencyExchangeRateId) &&
            Objects.equals(contractStageName, that.contractStageName) &&
            Objects.equals(paymentStatusId, that.paymentStatusId) &&
            Objects.equals(period, that.period) &&
            Objects.equals(payment, that.payment) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            contractId,
            companyId,
            accountId,
            contactSignedDate,
            contactSignedTitle,
            contractEndDate,
            contractNumber,
            contractNumberInput,
            contractStageId,
            contractStartDate,
            ownerEmployeeId,
            paymentMethodId,
            contractName,
            contractTypeId,
            currencyId,
            grandTotal,
            paymentTermId,
            quoteId,
            currencyExchangeRateId,
            contractStageName,
            paymentStatusId,
            period,
            payment,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaleContractCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (contractId != null ? "contractId=" + contractId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (accountId != null ? "accountId=" + accountId + ", " : "") +
            (contactSignedDate != null ? "contactSignedDate=" + contactSignedDate + ", " : "") +
            (contactSignedTitle != null ? "contactSignedTitle=" + contactSignedTitle + ", " : "") +
            (contractEndDate != null ? "contractEndDate=" + contractEndDate + ", " : "") +
            (contractNumber != null ? "contractNumber=" + contractNumber + ", " : "") +
            (contractNumberInput != null ? "contractNumberInput=" + contractNumberInput + ", " : "") +
            (contractStageId != null ? "contractStageId=" + contractStageId + ", " : "") +
            (contractStartDate != null ? "contractStartDate=" + contractStartDate + ", " : "") +
            (ownerEmployeeId != null ? "ownerEmployeeId=" + ownerEmployeeId + ", " : "") +
            (paymentMethodId != null ? "paymentMethodId=" + paymentMethodId + ", " : "") +
            (contractName != null ? "contractName=" + contractName + ", " : "") +
            (contractTypeId != null ? "contractTypeId=" + contractTypeId + ", " : "") +
            (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
            (grandTotal != null ? "grandTotal=" + grandTotal + ", " : "") +
            (paymentTermId != null ? "paymentTermId=" + paymentTermId + ", " : "") +
            (quoteId != null ? "quoteId=" + quoteId + ", " : "") +
            (currencyExchangeRateId != null ? "currencyExchangeRateId=" + currencyExchangeRateId + ", " : "") +
            (contractStageName != null ? "contractStageName=" + contractStageName + ", " : "") +
            (paymentStatusId != null ? "paymentStatusId=" + paymentStatusId + ", " : "") +
            (period != null ? "period=" + period + ", " : "") +
            (payment != null ? "payment=" + payment + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
