package vn.com.datamanager.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Unit.
 */
@Entity
@Table(name = "unit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Unit extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "unit_code")
    private String unitCode;

    @Column(name = "unit_name")
    private String unitName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Unit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public Unit unitCode(String unitCode) {
        this.setUnitCode(unitCode);
        return this;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public Unit unitName(String unitName) {
        this.setUnitName(unitName);
        return this;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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
        if (!(o instanceof Unit)) {
            return false;
        }
        return id != null && id.equals(((Unit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Unit{" +
            "id=" + getId() +
            ", unitCode='" + getUnitCode() + "'" +
            ", unitName='" + getUnitName() + "'" +
            "}";
    }
}
