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
 * Criteria class for the {@link vn.com.datamanager.domain.Product} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ProductCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter productId;

    private StringFilter productCode;

    private StringFilter productFamilyId;

    private StringFilter productPriceId;

    private StringFilter productName;

    private StringFilter productFamilyCode;

    private StringFilter productFamilyName;

    private Boolean distinct;

    public ProductCriteria() {}

    public ProductCriteria(ProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.productCode = other.productCode == null ? null : other.productCode.copy();
        this.productFamilyId = other.productFamilyId == null ? null : other.productFamilyId.copy();
        this.productPriceId = other.productPriceId == null ? null : other.productPriceId.copy();
        this.productName = other.productName == null ? null : other.productName.copy();
        this.productFamilyCode = other.productFamilyCode == null ? null : other.productFamilyCode.copy();
        this.productFamilyName = other.productFamilyName == null ? null : other.productFamilyName.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
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

    public StringFilter getProductId() {
        return productId;
    }

    public StringFilter productId() {
        if (productId == null) {
            productId = new StringFilter();
        }
        return productId;
    }

    public void setProductId(StringFilter productId) {
        this.productId = productId;
    }

    public StringFilter getProductCode() {
        return productCode;
    }

    public StringFilter productCode() {
        if (productCode == null) {
            productCode = new StringFilter();
        }
        return productCode;
    }

    public void setProductCode(StringFilter productCode) {
        this.productCode = productCode;
    }

    public StringFilter getProductFamilyId() {
        return productFamilyId;
    }

    public StringFilter productFamilyId() {
        if (productFamilyId == null) {
            productFamilyId = new StringFilter();
        }
        return productFamilyId;
    }

    public void setProductFamilyId(StringFilter productFamilyId) {
        this.productFamilyId = productFamilyId;
    }

    public StringFilter getProductPriceId() {
        return productPriceId;
    }

    public StringFilter productPriceId() {
        if (productPriceId == null) {
            productPriceId = new StringFilter();
        }
        return productPriceId;
    }

    public void setProductPriceId(StringFilter productPriceId) {
        this.productPriceId = productPriceId;
    }

    public StringFilter getProductName() {
        return productName;
    }

    public StringFilter productName() {
        if (productName == null) {
            productName = new StringFilter();
        }
        return productName;
    }

    public void setProductName(StringFilter productName) {
        this.productName = productName;
    }

    public StringFilter getProductFamilyCode() {
        return productFamilyCode;
    }

    public StringFilter productFamilyCode() {
        if (productFamilyCode == null) {
            productFamilyCode = new StringFilter();
        }
        return productFamilyCode;
    }

    public void setProductFamilyCode(StringFilter productFamilyCode) {
        this.productFamilyCode = productFamilyCode;
    }

    public StringFilter getProductFamilyName() {
        return productFamilyName;
    }

    public StringFilter productFamilyName() {
        if (productFamilyName == null) {
            productFamilyName = new StringFilter();
        }
        return productFamilyName;
    }

    public void setProductFamilyName(StringFilter productFamilyName) {
        this.productFamilyName = productFamilyName;
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
        final ProductCriteria that = (ProductCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(productCode, that.productCode) &&
            Objects.equals(productFamilyId, that.productFamilyId) &&
            Objects.equals(productPriceId, that.productPriceId) &&
            Objects.equals(productName, that.productName) &&
            Objects.equals(productFamilyCode, that.productFamilyCode) &&
            Objects.equals(productFamilyName, that.productFamilyName) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            productId,
            productCode,
            productFamilyId,
            productPriceId,
            productName,
            productFamilyCode,
            productFamilyName,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (productCode != null ? "productCode=" + productCode + ", " : "") +
            (productFamilyId != null ? "productFamilyId=" + productFamilyId + ", " : "") +
            (productPriceId != null ? "productPriceId=" + productPriceId + ", " : "") +
            (productName != null ? "productName=" + productName + ", " : "") +
            (productFamilyCode != null ? "productFamilyCode=" + productFamilyCode + ", " : "") +
            (productFamilyName != null ? "productFamilyName=" + productFamilyName + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
