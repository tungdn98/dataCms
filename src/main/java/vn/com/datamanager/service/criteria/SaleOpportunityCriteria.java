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
 * Criteria class for the {@link vn.com.datamanager.domain.SaleOpportunity} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.SaleOpportunityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sale-opportunities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class SaleOpportunityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter opportunityId;

    private StringFilter opportunityCode;

    private StringFilter opportunityName;

    private StringFilter opportunityTypeName;

    private LocalDateFilter startDate;

    private LocalDateFilter closeDate;

    private LongFilter stageId;

    private LongFilter stageReasonId;

    private LongFilter employeeId;

    private LongFilter leadId;

    private StringFilter currencyCode;

    private LongFilter accountId;

    private LongFilter productId;

    private BigDecimalFilter salesPricePrd;

    private BigDecimalFilter value;

    private Boolean distinct;

    public SaleOpportunityCriteria() {}

    public SaleOpportunityCriteria(SaleOpportunityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.opportunityId = other.opportunityId == null ? null : other.opportunityId.copy();
        this.opportunityCode = other.opportunityCode == null ? null : other.opportunityCode.copy();
        this.opportunityName = other.opportunityName == null ? null : other.opportunityName.copy();
        this.opportunityTypeName = other.opportunityTypeName == null ? null : other.opportunityTypeName.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.closeDate = other.closeDate == null ? null : other.closeDate.copy();
        this.stageId = other.stageId == null ? null : other.stageId.copy();
        this.stageReasonId = other.stageReasonId == null ? null : other.stageReasonId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.leadId = other.leadId == null ? null : other.leadId.copy();
        this.currencyCode = other.currencyCode == null ? null : other.currencyCode.copy();
        this.accountId = other.accountId == null ? null : other.accountId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.salesPricePrd = other.salesPricePrd == null ? null : other.salesPricePrd.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SaleOpportunityCriteria copy() {
        return new SaleOpportunityCriteria(this);
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

    public LongFilter getOpportunityId() {
        return opportunityId;
    }

    public LongFilter opportunityId() {
        if (opportunityId == null) {
            opportunityId = new LongFilter();
        }
        return opportunityId;
    }

    public void setOpportunityId(LongFilter opportunityId) {
        this.opportunityId = opportunityId;
    }

    public StringFilter getOpportunityCode() {
        return opportunityCode;
    }

    public StringFilter opportunityCode() {
        if (opportunityCode == null) {
            opportunityCode = new StringFilter();
        }
        return opportunityCode;
    }

    public void setOpportunityCode(StringFilter opportunityCode) {
        this.opportunityCode = opportunityCode;
    }

    public StringFilter getOpportunityName() {
        return opportunityName;
    }

    public StringFilter opportunityName() {
        if (opportunityName == null) {
            opportunityName = new StringFilter();
        }
        return opportunityName;
    }

    public void setOpportunityName(StringFilter opportunityName) {
        this.opportunityName = opportunityName;
    }

    public StringFilter getOpportunityTypeName() {
        return opportunityTypeName;
    }

    public StringFilter opportunityTypeName() {
        if (opportunityTypeName == null) {
            opportunityTypeName = new StringFilter();
        }
        return opportunityTypeName;
    }

    public void setOpportunityTypeName(StringFilter opportunityTypeName) {
        this.opportunityTypeName = opportunityTypeName;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public LocalDateFilter startDate() {
        if (startDate == null) {
            startDate = new LocalDateFilter();
        }
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getCloseDate() {
        return closeDate;
    }

    public LocalDateFilter closeDate() {
        if (closeDate == null) {
            closeDate = new LocalDateFilter();
        }
        return closeDate;
    }

    public void setCloseDate(LocalDateFilter closeDate) {
        this.closeDate = closeDate;
    }

    public LongFilter getStageId() {
        return stageId;
    }

    public LongFilter stageId() {
        if (stageId == null) {
            stageId = new LongFilter();
        }
        return stageId;
    }

    public void setStageId(LongFilter stageId) {
        this.stageId = stageId;
    }

    public LongFilter getStageReasonId() {
        return stageReasonId;
    }

    public LongFilter stageReasonId() {
        if (stageReasonId == null) {
            stageReasonId = new LongFilter();
        }
        return stageReasonId;
    }

    public void setStageReasonId(LongFilter stageReasonId) {
        this.stageReasonId = stageReasonId;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getLeadId() {
        return leadId;
    }

    public LongFilter leadId() {
        if (leadId == null) {
            leadId = new LongFilter();
        }
        return leadId;
    }

    public void setLeadId(LongFilter leadId) {
        this.leadId = leadId;
    }

    public StringFilter getCurrencyCode() {
        return currencyCode;
    }

    public StringFilter currencyCode() {
        if (currencyCode == null) {
            currencyCode = new StringFilter();
        }
        return currencyCode;
    }

    public void setCurrencyCode(StringFilter currencyCode) {
        this.currencyCode = currencyCode;
    }

    public LongFilter getAccountId() {
        return accountId;
    }

    public LongFilter accountId() {
        if (accountId == null) {
            accountId = new LongFilter();
        }
        return accountId;
    }

    public void setAccountId(LongFilter accountId) {
        this.accountId = accountId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public LongFilter productId() {
        if (productId == null) {
            productId = new LongFilter();
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public BigDecimalFilter getSalesPricePrd() {
        return salesPricePrd;
    }

    public BigDecimalFilter salesPricePrd() {
        if (salesPricePrd == null) {
            salesPricePrd = new BigDecimalFilter();
        }
        return salesPricePrd;
    }

    public void setSalesPricePrd(BigDecimalFilter salesPricePrd) {
        this.salesPricePrd = salesPricePrd;
    }

    public BigDecimalFilter getValue() {
        return value;
    }

    public BigDecimalFilter value() {
        if (value == null) {
            value = new BigDecimalFilter();
        }
        return value;
    }

    public void setValue(BigDecimalFilter value) {
        this.value = value;
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
        final SaleOpportunityCriteria that = (SaleOpportunityCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(opportunityId, that.opportunityId) &&
            Objects.equals(opportunityCode, that.opportunityCode) &&
            Objects.equals(opportunityName, that.opportunityName) &&
            Objects.equals(opportunityTypeName, that.opportunityTypeName) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(closeDate, that.closeDate) &&
            Objects.equals(stageId, that.stageId) &&
            Objects.equals(stageReasonId, that.stageReasonId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(leadId, that.leadId) &&
            Objects.equals(currencyCode, that.currencyCode) &&
            Objects.equals(accountId, that.accountId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(salesPricePrd, that.salesPricePrd) &&
            Objects.equals(value, that.value) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            opportunityId,
            opportunityCode,
            opportunityName,
            opportunityTypeName,
            startDate,
            closeDate,
            stageId,
            stageReasonId,
            employeeId,
            leadId,
            currencyCode,
            accountId,
            productId,
            salesPricePrd,
            value,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaleOpportunityCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (opportunityId != null ? "opportunityId=" + opportunityId + ", " : "") +
            (opportunityCode != null ? "opportunityCode=" + opportunityCode + ", " : "") +
            (opportunityName != null ? "opportunityName=" + opportunityName + ", " : "") +
            (opportunityTypeName != null ? "opportunityTypeName=" + opportunityTypeName + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (closeDate != null ? "closeDate=" + closeDate + ", " : "") +
            (stageId != null ? "stageId=" + stageId + ", " : "") +
            (stageReasonId != null ? "stageReasonId=" + stageReasonId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (leadId != null ? "leadId=" + leadId + ", " : "") +
            (currencyCode != null ? "currencyCode=" + currencyCode + ", " : "") +
            (accountId != null ? "accountId=" + accountId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (salesPricePrd != null ? "salesPricePrd=" + salesPricePrd + ", " : "") +
            (value != null ? "value=" + value + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
