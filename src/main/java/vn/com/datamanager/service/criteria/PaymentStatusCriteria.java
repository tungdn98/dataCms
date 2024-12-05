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
 * Criteria class for the {@link vn.com.datamanager.domain.PaymentStatus} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.PaymentStatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payment-statuses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PaymentStatusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter paymentStatusId;

    private StringFilter paymentStatusName;

    private Boolean distinct;

    public PaymentStatusCriteria() {}

    public PaymentStatusCriteria(PaymentStatusCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.paymentStatusId = other.paymentStatusId == null ? null : other.paymentStatusId.copy();
        this.paymentStatusName = other.paymentStatusName == null ? null : other.paymentStatusName.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PaymentStatusCriteria copy() {
        return new PaymentStatusCriteria(this);
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

    public StringFilter getPaymentStatusId() {
        return paymentStatusId;
    }

    public StringFilter paymentStatusId() {
        if (paymentStatusId == null) {
            paymentStatusId = new StringFilter();
        }
        return paymentStatusId;
    }

    public void setPaymentStatusId(StringFilter paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    public StringFilter getPaymentStatusName() {
        return paymentStatusName;
    }

    public StringFilter paymentStatusName() {
        if (paymentStatusName == null) {
            paymentStatusName = new StringFilter();
        }
        return paymentStatusName;
    }

    public void setPaymentStatusName(StringFilter paymentStatusName) {
        this.paymentStatusName = paymentStatusName;
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
        final PaymentStatusCriteria that = (PaymentStatusCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(paymentStatusId, that.paymentStatusId) &&
            Objects.equals(paymentStatusName, that.paymentStatusName) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentStatusId, paymentStatusName, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentStatusCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (paymentStatusId != null ? "paymentStatusId=" + paymentStatusId + ", " : "") +
            (paymentStatusName != null ? "paymentStatusName=" + paymentStatusName + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
