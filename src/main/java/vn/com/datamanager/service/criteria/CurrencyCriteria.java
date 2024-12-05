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
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link vn.com.datamanager.domain.Currency} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.CurrencyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /currencies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class CurrencyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter currencyId;

    private StringFilter currencyNum;

    private StringFilter currencyCode;

    private StringFilter currencyName;

    private LongFilter currencyExchangeRateId;

    private BigDecimalFilter conversionRate;

    private Boolean distinct;

    public CurrencyCriteria() {}

    public CurrencyCriteria(CurrencyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
        this.currencyNum = other.currencyNum == null ? null : other.currencyNum.copy();
        this.currencyCode = other.currencyCode == null ? null : other.currencyCode.copy();
        this.currencyName = other.currencyName == null ? null : other.currencyName.copy();
        this.currencyExchangeRateId = other.currencyExchangeRateId == null ? null : other.currencyExchangeRateId.copy();
        this.conversionRate = other.conversionRate == null ? null : other.conversionRate.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CurrencyCriteria copy() {
        return new CurrencyCriteria(this);
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

    public StringFilter getCurrencyNum() {
        return currencyNum;
    }

    public StringFilter currencyNum() {
        if (currencyNum == null) {
            currencyNum = new StringFilter();
        }
        return currencyNum;
    }

    public void setCurrencyNum(StringFilter currencyNum) {
        this.currencyNum = currencyNum;
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

    public StringFilter getCurrencyName() {
        return currencyName;
    }

    public StringFilter currencyName() {
        if (currencyName == null) {
            currencyName = new StringFilter();
        }
        return currencyName;
    }

    public void setCurrencyName(StringFilter currencyName) {
        this.currencyName = currencyName;
    }

    public LongFilter getCurrencyExchangeRateId() {
        return currencyExchangeRateId;
    }

    public LongFilter currencyExchangeRateId() {
        if (currencyExchangeRateId == null) {
            currencyExchangeRateId = new LongFilter();
        }
        return currencyExchangeRateId;
    }

    public void setCurrencyExchangeRateId(LongFilter currencyExchangeRateId) {
        this.currencyExchangeRateId = currencyExchangeRateId;
    }

    public BigDecimalFilter getConversionRate() {
        return conversionRate;
    }

    public BigDecimalFilter conversionRate() {
        if (conversionRate == null) {
            conversionRate = new BigDecimalFilter();
        }
        return conversionRate;
    }

    public void setConversionRate(BigDecimalFilter conversionRate) {
        this.conversionRate = conversionRate;
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
        final CurrencyCriteria that = (CurrencyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(currencyId, that.currencyId) &&
            Objects.equals(currencyNum, that.currencyNum) &&
            Objects.equals(currencyCode, that.currencyCode) &&
            Objects.equals(currencyName, that.currencyName) &&
            Objects.equals(currencyExchangeRateId, that.currencyExchangeRateId) &&
            Objects.equals(conversionRate, that.conversionRate) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currencyId, currencyNum, currencyCode, currencyName, currencyExchangeRateId, conversionRate, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrencyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
            (currencyNum != null ? "currencyNum=" + currencyNum + ", " : "") +
            (currencyCode != null ? "currencyCode=" + currencyCode + ", " : "") +
            (currencyName != null ? "currencyName=" + currencyName + ", " : "") +
            (currencyExchangeRateId != null ? "currencyExchangeRateId=" + currencyExchangeRateId + ", " : "") +
            (conversionRate != null ? "conversionRate=" + conversionRate + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
