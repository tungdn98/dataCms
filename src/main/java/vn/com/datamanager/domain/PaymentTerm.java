package vn.com.datamanager.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PaymentTerm.
 */
@Entity
@Table(name = "payment_term")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentTerm extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "payment_term_id")
    private String paymentTermId;

    @Column(name = "payment_term_code")
    private String paymentTermCode;

    @Column(name = "payment_term_name")
    private String paymentTermName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentTerm id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentTermId() {
        return this.paymentTermId;
    }

    public PaymentTerm paymentTermId(String paymentTermId) {
        this.setPaymentTermId(paymentTermId);
        return this;
    }

    public void setPaymentTermId(String paymentTermId) {
        this.paymentTermId = paymentTermId;
    }

    public String getPaymentTermCode() {
        return this.paymentTermCode;
    }

    public PaymentTerm paymentTermCode(String paymentTermCode) {
        this.setPaymentTermCode(paymentTermCode);
        return this;
    }

    public void setPaymentTermCode(String paymentTermCode) {
        this.paymentTermCode = paymentTermCode;
    }

    public String getPaymentTermName() {
        return this.paymentTermName;
    }

    public PaymentTerm paymentTermName(String paymentTermName) {
        this.setPaymentTermName(paymentTermName);
        return this;
    }

    public void setPaymentTermName(String paymentTermName) {
        this.paymentTermName = paymentTermName;
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
        if (!(o instanceof PaymentTerm)) {
            return false;
        }
        return id != null && id.equals(((PaymentTerm) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentTerm{" +
            "id=" + getId() +
            ", paymentTermId='" + getPaymentTermId() + "'" +
            ", paymentTermCode='" + getPaymentTermCode() + "'" +
            ", paymentTermName='" + getPaymentTermName() + "'" +
            "}";
    }
}
