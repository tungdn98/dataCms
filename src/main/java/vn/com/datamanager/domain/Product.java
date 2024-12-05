package vn.com.datamanager.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_family_id")
    private String productFamilyId;

    @Column(name = "product_price_id")
    private String productPriceId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_family_code")
    private String productFamilyCode;

    @Column(name = "product_family_name")
    private String productFamilyName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

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

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return this.productId;
    }

    public Product productId(String productId) {
        this.setProductId(productId);
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return this.productCode;
    }

    public Product productCode(String productCode) {
        this.setProductCode(productCode);
        return this;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductFamilyId() {
        return this.productFamilyId;
    }

    public Product productFamilyId(String productFamilyId) {
        this.setProductFamilyId(productFamilyId);
        return this;
    }

    public void setProductFamilyId(String productFamilyId) {
        this.productFamilyId = productFamilyId;
    }

    public String getProductPriceId() {
        return this.productPriceId;
    }

    public Product productPriceId(String productPriceId) {
        this.setProductPriceId(productPriceId);
        return this;
    }

    public void setProductPriceId(String productPriceId) {
        this.productPriceId = productPriceId;
    }

    public String getProductName() {
        return this.productName;
    }

    public Product productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductFamilyCode() {
        return this.productFamilyCode;
    }

    public Product productFamilyCode(String productFamilyCode) {
        this.setProductFamilyCode(productFamilyCode);
        return this;
    }

    public void setProductFamilyCode(String productFamilyCode) {
        this.productFamilyCode = productFamilyCode;
    }

    public String getProductFamilyName() {
        return this.productFamilyName;
    }

    public Product productFamilyName(String productFamilyName) {
        this.setProductFamilyName(productFamilyName);
        return this;
    }

    public void setProductFamilyName(String productFamilyName) {
        this.productFamilyName = productFamilyName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productId='" + getProductId() + "'" +
            ", productCode='" + getProductCode() + "'" +
            ", productFamilyId='" + getProductFamilyId() + "'" +
            ", productPriceId='" + getProductPriceId() + "'" +
            ", productName='" + getProductName() + "'" +
            ", productFamilyCode='" + getProductFamilyCode() + "'" +
            ", productFamilyName='" + getProductFamilyName() + "'" +
            "}";
    }
}
