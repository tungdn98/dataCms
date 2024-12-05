package vn.com.datamanager.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * CRM
 */
@Schema(description = "CRM")
@Entity
@Table(name = "sale_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SaleOrder extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "contract_id")
    private String contractId;

    @Column(name = "owner_employee_id")
    private String ownerEmployeeId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "total_value", precision = 21, scale = 2)
    private BigDecimal totalValue;

    @Column(name = "order_stage_id")
    private String orderStageId;

    @Column(name = "order_stage_name")
    private String orderStageName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SaleOrder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public SaleOrder orderId(String orderId) {
        this.setOrderId(orderId);
        return this;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getContractId() {
        return this.contractId;
    }

    public SaleOrder contractId(String contractId) {
        this.setContractId(contractId);
        return this;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getOwnerEmployeeId() {
        return this.ownerEmployeeId;
    }

    public SaleOrder ownerEmployeeId(String ownerEmployeeId) {
        this.setOwnerEmployeeId(ownerEmployeeId);
        return this;
    }

    public void setOwnerEmployeeId(String ownerEmployeeId) {
        this.ownerEmployeeId = ownerEmployeeId;
    }

    public String getProductId() {
        return this.productId;
    }

    public SaleOrder productId(String productId) {
        this.setProductId(productId);
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getTotalValue() {
        return this.totalValue;
    }

    public SaleOrder totalValue(BigDecimal totalValue) {
        this.setTotalValue(totalValue);
        return this;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public String getOrderStageId() {
        return this.orderStageId;
    }

    public SaleOrder orderStageId(String orderStageId) {
        this.setOrderStageId(orderStageId);
        return this;
    }

    public void setOrderStageId(String orderStageId) {
        this.orderStageId = orderStageId;
    }

    public String getOrderStageName() {
        return this.orderStageName;
    }

    public SaleOrder orderStageName(String orderStageName) {
        this.setOrderStageName(orderStageName);
        return this;
    }

    public void setOrderStageName(String orderStageName) {
        this.orderStageName = orderStageName;
    }

    public SaleOrder createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public SaleOrder lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public SaleOrder lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public String getCreatedBy() {
        return super.getCreatedBy();
    }

    @Override
    public void setCreatedBy(String createdBy) {
        super.setCreatedBy(createdBy);
    }

    @Override
    public Instant getCreatedDate() {
        return super.getCreatedDate();
    }

    @Override
    public void setCreatedDate(Instant createdDate) {
        super.setCreatedDate(createdDate);
    }

    @Override
    public String getLastModifiedBy() {
        return super.getLastModifiedBy();
    }

    @Override
    public void setLastModifiedBy(String lastModifiedBy) {
        super.setLastModifiedBy(lastModifiedBy);
    }

    @Override
    public Instant getLastModifiedDate() {
        return super.getLastModifiedDate();
    }

    @Override
    public void setLastModifiedDate(Instant lastModifiedDate) {
        super.setLastModifiedDate(lastModifiedDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaleOrder)) {
            return false;
        }
        return id != null && id.equals(((SaleOrder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaleOrder{" +
            "id=" + getId() +
            ", orderId='" + getOrderId() + "'" +
            ", contractId='" + getContractId() + "'" +
            ", ownerEmployeeId='" + getOwnerEmployeeId() + "'" +
            ", productId='" + getProductId() + "'" +
            ", totalValue=" + getTotalValue() +
            ", orderStageId='" + getOrderStageId() + "'" +
            ", orderStageName='" + getOrderStageName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
