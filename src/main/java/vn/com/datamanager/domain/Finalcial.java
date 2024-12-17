package vn.com.datamanager.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Finalcial.
 */
@Entity
@Table(name = "finalcial")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Finalcial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_short_name")
    private String customerShortName;

    @Column(name = "customer_type")
    private String customerType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Finalcial id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Finalcial code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public Finalcial customerName(String customerName) {
        this.setCustomerName(customerName);
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerShortName() {
        return this.customerShortName;
    }

    public Finalcial customerShortName(String customerShortName) {
        this.setCustomerShortName(customerShortName);
        return this;
    }

    public void setCustomerShortName(String customerShortName) {
        this.customerShortName = customerShortName;
    }

    public String getCustomerType() {
        return this.customerType;
    }

    public Finalcial customerType(String customerType) {
        this.setCustomerType(customerType);
        return this;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Finalcial)) {
            return false;
        }
        return id != null && id.equals(((Finalcial) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Finalcial{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            ", customerShortName='" + getCustomerShortName() + "'" +
            ", customerType='" + getCustomerType() + "'" +
            "}";
    }
}
