package vn.com.datamanager.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "account_code")
    private String accountCode;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "mapping_account")
    private String mappingAccount;

    @Column(name = "account_email")
    private String accountEmail;

    @Column(name = "account_phone")
    private String accountPhone;

    @Column(name = "account_type_name")
    private String accountTypeName;

    @Column(name = "gender_name")
    private String genderName;

    @Column(name = "industry_name")
    private String industryName;

    @Column(name = "owner_employee_id")
    private Long ownerEmployeeId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Customer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public Customer accountId(String accountId) {
        this.setAccountId(accountId);
        return this;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountCode() {
        return this.accountCode;
    }

    public Customer accountCode(String accountCode) {
        this.setAccountCode(accountCode);
        return this;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public Customer accountName(String accountName) {
        this.setAccountName(accountName);
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getMappingAccount() {
        return this.mappingAccount;
    }

    public Customer mappingAccount(String mappingAccount) {
        this.setMappingAccount(mappingAccount);
        return this;
    }

    public void setMappingAccount(String mappingAccount) {
        this.mappingAccount = mappingAccount;
    }

    public String getAccountEmail() {
        return this.accountEmail;
    }

    public Customer accountEmail(String accountEmail) {
        this.setAccountEmail(accountEmail);
        return this;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getAccountPhone() {
        return this.accountPhone;
    }

    public Customer accountPhone(String accountPhone) {
        this.setAccountPhone(accountPhone);
        return this;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public String getAccountTypeName() {
        return this.accountTypeName;
    }

    public Customer accountTypeName(String accountTypeName) {
        this.setAccountTypeName(accountTypeName);
        return this;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public String getGenderName() {
        return this.genderName;
    }

    public Customer genderName(String genderName) {
        this.setGenderName(genderName);
        return this;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getIndustryName() {
        return this.industryName;
    }

    public Customer industryName(String industryName) {
        this.setIndustryName(industryName);
        return this;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public Long getOwnerEmployeeId() {
        return this.ownerEmployeeId;
    }

    public Customer ownerEmployeeId(Long ownerEmployeeId) {
        this.setOwnerEmployeeId(ownerEmployeeId);
        return this;
    }

    public void setOwnerEmployeeId(Long ownerEmployeeId) {
        this.ownerEmployeeId = ownerEmployeeId;
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
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", accountId='" + getAccountId() + "'" +
            ", accountCode='" + getAccountCode() + "'" +
            ", accountName='" + getAccountName() + "'" +
            ", mappingAccount='" + getMappingAccount() + "'" +
            ", accountEmail='" + getAccountEmail() + "'" +
            ", accountPhone='" + getAccountPhone() + "'" +
            ", accountTypeName='" + getAccountTypeName() + "'" +
            ", genderName='" + getGenderName() + "'" +
            ", industryName='" + getIndustryName() + "'" +
            ", ownerEmployeeId=" + getOwnerEmployeeId() +
            "}";
    }
}
