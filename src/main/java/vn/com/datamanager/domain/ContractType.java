package vn.com.datamanager.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContractType.
 */
@Entity
@Table(name = "contract_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContractType extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "contract_type_id")
    private String contractTypeId;

    @Column(name = "contract_type_name")
    private String contractTypeName;

    @Column(name = "contract_type_code")
    private String contractTypeCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ContractType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractTypeId() {
        return this.contractTypeId;
    }

    public ContractType contractTypeId(String contractTypeId) {
        this.setContractTypeId(contractTypeId);
        return this;
    }

    public void setContractTypeId(String contractTypeId) {
        this.contractTypeId = contractTypeId;
    }

    public String getContractTypeName() {
        return this.contractTypeName;
    }

    public ContractType contractTypeName(String contractTypeName) {
        this.setContractTypeName(contractTypeName);
        return this;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    public String getContractTypeCode() {
        return this.contractTypeCode;
    }

    public ContractType contractTypeCode(String contractTypeCode) {
        this.setContractTypeCode(contractTypeCode);
        return this;
    }

    public void setContractTypeCode(String contractTypeCode) {
        this.contractTypeCode = contractTypeCode;
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
        if (!(o instanceof ContractType)) {
            return false;
        }
        return id != null && id.equals(((ContractType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContractType{" +
            "id=" + getId() +
            ", contractTypeId='" + getContractTypeId() + "'" +
            ", contractTypeName='" + getContractTypeName() + "'" +
            ", contractTypeCode='" + getContractTypeCode() + "'" +
            "}";
    }
}
