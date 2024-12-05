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
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link vn.com.datamanager.domain.SaleOrder} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.SaleOrderResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sale-orders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class SaleOrderCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter orderId;

    private StringFilter contractId;

    private StringFilter ownerEmployeeId;

    private StringFilter productId;

    private BigDecimalFilter totalValue;

    private StringFilter orderStageId;

    private StringFilter orderStageName;

    private InstantFilter createdDate;

    private StringFilter createdBy;

    private InstantFilter lastModifiedDate;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public SaleOrderCriteria() {}

    public SaleOrderCriteria(SaleOrderCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
        this.contractId = other.contractId == null ? null : other.contractId.copy();
        this.ownerEmployeeId = other.ownerEmployeeId == null ? null : other.ownerEmployeeId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.totalValue = other.totalValue == null ? null : other.totalValue.copy();
        this.orderStageId = other.orderStageId == null ? null : other.orderStageId.copy();
        this.orderStageName = other.orderStageName == null ? null : other.orderStageName.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SaleOrderCriteria copy() {
        return new SaleOrderCriteria(this);
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

    public StringFilter getOrderId() {
        return orderId;
    }

    public StringFilter orderId() {
        if (orderId == null) {
            orderId = new StringFilter();
        }
        return orderId;
    }

    public void setOrderId(StringFilter orderId) {
        this.orderId = orderId;
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

    public BigDecimalFilter getTotalValue() {
        return totalValue;
    }

    public BigDecimalFilter totalValue() {
        if (totalValue == null) {
            totalValue = new BigDecimalFilter();
        }
        return totalValue;
    }

    public void setTotalValue(BigDecimalFilter totalValue) {
        this.totalValue = totalValue;
    }

    public StringFilter getOrderStageId() {
        return orderStageId;
    }

    public StringFilter orderStageId() {
        if (orderStageId == null) {
            orderStageId = new StringFilter();
        }
        return orderStageId;
    }

    public void setOrderStageId(StringFilter orderStageId) {
        this.orderStageId = orderStageId;
    }

    public StringFilter getOrderStageName() {
        return orderStageName;
    }

    public StringFilter orderStageName() {
        if (orderStageName == null) {
            orderStageName = new StringFilter();
        }
        return orderStageName;
    }

    public void setOrderStageName(StringFilter orderStageName) {
        this.orderStageName = orderStageName;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            createdDate = new InstantFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public InstantFilter lastModifiedDate() {
        if (lastModifiedDate == null) {
            lastModifiedDate = new InstantFilter();
        }
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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
        final SaleOrderCriteria that = (SaleOrderCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(orderId, that.orderId) &&
            Objects.equals(contractId, that.contractId) &&
            Objects.equals(ownerEmployeeId, that.ownerEmployeeId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(totalValue, that.totalValue) &&
            Objects.equals(orderStageId, that.orderStageId) &&
            Objects.equals(orderStageName, that.orderStageName) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            orderId,
            contractId,
            ownerEmployeeId,
            productId,
            totalValue,
            orderStageId,
            orderStageName,
            createdDate,
            createdBy,
            lastModifiedDate,
            lastModifiedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaleOrderCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (orderId != null ? "orderId=" + orderId + ", " : "") +
            (contractId != null ? "contractId=" + contractId + ", " : "") +
            (ownerEmployeeId != null ? "ownerEmployeeId=" + ownerEmployeeId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (totalValue != null ? "totalValue=" + totalValue + ", " : "") +
            (orderStageId != null ? "orderStageId=" + orderStageId + ", " : "") +
            (orderStageName != null ? "orderStageName=" + orderStageName + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
