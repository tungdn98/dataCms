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
 * Criteria class for the {@link vn.com.datamanager.domain.Finalcial} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.FinalcialResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /finalcials?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class FinalcialCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter customerName;

    private StringFilter customerShortName;

    private StringFilter customerType;

    private Boolean distinct;

    public FinalcialCriteria() {}

    public FinalcialCriteria(FinalcialCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.customerName = other.customerName == null ? null : other.customerName.copy();
        this.customerShortName = other.customerShortName == null ? null : other.customerShortName.copy();
        this.customerType = other.customerType == null ? null : other.customerType.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FinalcialCriteria copy() {
        return new FinalcialCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getCustomerName() {
        return customerName;
    }

    public StringFilter customerName() {
        if (customerName == null) {
            customerName = new StringFilter();
        }
        return customerName;
    }

    public void setCustomerName(StringFilter customerName) {
        this.customerName = customerName;
    }

    public StringFilter getCustomerShortName() {
        return customerShortName;
    }

    public StringFilter customerShortName() {
        if (customerShortName == null) {
            customerShortName = new StringFilter();
        }
        return customerShortName;
    }

    public void setCustomerShortName(StringFilter customerShortName) {
        this.customerShortName = customerShortName;
    }

    public StringFilter getCustomerType() {
        return customerType;
    }

    public StringFilter customerType() {
        if (customerType == null) {
            customerType = new StringFilter();
        }
        return customerType;
    }

    public void setCustomerType(StringFilter customerType) {
        this.customerType = customerType;
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
        final FinalcialCriteria that = (FinalcialCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(customerName, that.customerName) &&
            Objects.equals(customerShortName, that.customerShortName) &&
            Objects.equals(customerType, that.customerType) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, customerName, customerShortName, customerType, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FinalcialCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (customerName != null ? "customerName=" + customerName + ", " : "") +
            (customerShortName != null ? "customerShortName=" + customerShortName + ", " : "") +
            (customerType != null ? "customerType=" + customerType + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
