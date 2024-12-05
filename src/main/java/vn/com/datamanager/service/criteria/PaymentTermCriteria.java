package vn.com.datamanager.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link vn.com.datamanager.domain.PaymentTerm} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.PaymentTermResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payment-terms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PaymentTermCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter paymentTermId;

    private StringFilter paymentTermCode;

    private StringFilter paymentTermName;

    private Boolean distinct;

    public PaymentTermCriteria() {}

    public PaymentTermCriteria(PaymentTermCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.paymentTermId = other.paymentTermId == null ? null : other.paymentTermId.copy();
        this.paymentTermCode = other.paymentTermCode == null ? null : other.paymentTermCode.copy();
        this.paymentTermName = other.paymentTermName == null ? null : other.paymentTermName.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PaymentTermCriteria copy() {
        return new PaymentTermCriteria(this);
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

    public StringFilter getPaymentTermCode() {
        return paymentTermCode;
    }

    public StringFilter paymentTermCode() {
        if (paymentTermCode == null) {
            paymentTermCode = new StringFilter();
        }
        return paymentTermCode;
    }

    public void setPaymentTermCode(StringFilter paymentTermCode) {
        this.paymentTermCode = paymentTermCode;
    }

    public StringFilter getPaymentTermName() {
        return paymentTermName;
    }

    public StringFilter paymentTermName() {
        if (paymentTermName == null) {
            paymentTermName = new StringFilter();
        }
        return paymentTermName;
    }

    public void setPaymentTermName(StringFilter paymentTermName) {
        this.paymentTermName = paymentTermName;
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
        final PaymentTermCriteria that = (PaymentTermCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(paymentTermId, that.paymentTermId) &&
            Objects.equals(paymentTermCode, that.paymentTermCode) &&
            Objects.equals(paymentTermName, that.paymentTermName) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentTermId, paymentTermCode, paymentTermName, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentTermCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (paymentTermId != null ? "paymentTermId=" + paymentTermId + ", " : "") +
            (paymentTermCode != null ? "paymentTermCode=" + paymentTermCode + ", " : "") +
            (paymentTermName != null ? "paymentTermName=" + paymentTermName + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
