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
 * Criteria class for the {@link vn.com.datamanager.domain.Customer} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.CustomerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class CustomerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter accountId;

    private StringFilter accountCode;

    private StringFilter accountName;

    private StringFilter mappingAccount;

    private StringFilter accountEmail;

    private StringFilter accountPhone;

    private StringFilter accountTypeName;

    private StringFilter genderName;

    private StringFilter industryName;

    private LongFilter ownerEmployeeId;

    private Boolean distinct;

    public CustomerCriteria() {}

    public CustomerCriteria(CustomerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.accountId = other.accountId == null ? null : other.accountId.copy();
        this.accountCode = other.accountCode == null ? null : other.accountCode.copy();
        this.accountName = other.accountName == null ? null : other.accountName.copy();
        this.mappingAccount = other.mappingAccount == null ? null : other.mappingAccount.copy();
        this.accountEmail = other.accountEmail == null ? null : other.accountEmail.copy();
        this.accountPhone = other.accountPhone == null ? null : other.accountPhone.copy();
        this.accountTypeName = other.accountTypeName == null ? null : other.accountTypeName.copy();
        this.genderName = other.genderName == null ? null : other.genderName.copy();
        this.industryName = other.industryName == null ? null : other.industryName.copy();
        this.ownerEmployeeId = other.ownerEmployeeId == null ? null : other.ownerEmployeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CustomerCriteria copy() {
        return new CustomerCriteria(this);
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

    public StringFilter getAccountId() {
        return accountId;
    }

    public StringFilter accountId() {
        if (accountId == null) {
            accountId = new StringFilter();
        }
        return accountId;
    }

    public void setAccountId(StringFilter accountId) {
        this.accountId = accountId;
    }

    public StringFilter getAccountCode() {
        return accountCode;
    }

    public StringFilter accountCode() {
        if (accountCode == null) {
            accountCode = new StringFilter();
        }
        return accountCode;
    }

    public void setAccountCode(StringFilter accountCode) {
        this.accountCode = accountCode;
    }

    public StringFilter getAccountName() {
        return accountName;
    }

    public StringFilter accountName() {
        if (accountName == null) {
            accountName = new StringFilter();
        }
        return accountName;
    }

    public void setAccountName(StringFilter accountName) {
        this.accountName = accountName;
    }

    public StringFilter getMappingAccount() {
        return mappingAccount;
    }

    public StringFilter mappingAccount() {
        if (mappingAccount == null) {
            mappingAccount = new StringFilter();
        }
        return mappingAccount;
    }

    public void setMappingAccount(StringFilter mappingAccount) {
        this.mappingAccount = mappingAccount;
    }

    public StringFilter getAccountEmail() {
        return accountEmail;
    }

    public StringFilter accountEmail() {
        if (accountEmail == null) {
            accountEmail = new StringFilter();
        }
        return accountEmail;
    }

    public void setAccountEmail(StringFilter accountEmail) {
        this.accountEmail = accountEmail;
    }

    public StringFilter getAccountPhone() {
        return accountPhone;
    }

    public StringFilter accountPhone() {
        if (accountPhone == null) {
            accountPhone = new StringFilter();
        }
        return accountPhone;
    }

    public void setAccountPhone(StringFilter accountPhone) {
        this.accountPhone = accountPhone;
    }

    public StringFilter getAccountTypeName() {
        return accountTypeName;
    }

    public StringFilter accountTypeName() {
        if (accountTypeName == null) {
            accountTypeName = new StringFilter();
        }
        return accountTypeName;
    }

    public void setAccountTypeName(StringFilter accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public StringFilter getGenderName() {
        return genderName;
    }

    public StringFilter genderName() {
        if (genderName == null) {
            genderName = new StringFilter();
        }
        return genderName;
    }

    public void setGenderName(StringFilter genderName) {
        this.genderName = genderName;
    }

    public StringFilter getIndustryName() {
        return industryName;
    }

    public StringFilter industryName() {
        if (industryName == null) {
            industryName = new StringFilter();
        }
        return industryName;
    }

    public void setIndustryName(StringFilter industryName) {
        this.industryName = industryName;
    }

    public LongFilter getOwnerEmployeeId() {
        return ownerEmployeeId;
    }

    public LongFilter ownerEmployeeId() {
        if (ownerEmployeeId == null) {
            ownerEmployeeId = new LongFilter();
        }
        return ownerEmployeeId;
    }

    public void setOwnerEmployeeId(LongFilter ownerEmployeeId) {
        this.ownerEmployeeId = ownerEmployeeId;
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
        final CustomerCriteria that = (CustomerCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(accountId, that.accountId) &&
            Objects.equals(accountCode, that.accountCode) &&
            Objects.equals(accountName, that.accountName) &&
            Objects.equals(mappingAccount, that.mappingAccount) &&
            Objects.equals(accountEmail, that.accountEmail) &&
            Objects.equals(accountPhone, that.accountPhone) &&
            Objects.equals(accountTypeName, that.accountTypeName) &&
            Objects.equals(genderName, that.genderName) &&
            Objects.equals(industryName, that.industryName) &&
            Objects.equals(ownerEmployeeId, that.ownerEmployeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            accountId,
            accountCode,
            accountName,
            mappingAccount,
            accountEmail,
            accountPhone,
            accountTypeName,
            genderName,
            industryName,
            ownerEmployeeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (accountId != null ? "accountId=" + accountId + ", " : "") +
            (accountCode != null ? "accountCode=" + accountCode + ", " : "") +
            (accountName != null ? "accountName=" + accountName + ", " : "") +
            (mappingAccount != null ? "mappingAccount=" + mappingAccount + ", " : "") +
            (accountEmail != null ? "accountEmail=" + accountEmail + ", " : "") +
            (accountPhone != null ? "accountPhone=" + accountPhone + ", " : "") +
            (accountTypeName != null ? "accountTypeName=" + accountTypeName + ", " : "") +
            (genderName != null ? "genderName=" + genderName + ", " : "") +
            (industryName != null ? "industryName=" + industryName + ", " : "") +
            (ownerEmployeeId != null ? "ownerEmployeeId=" + ownerEmployeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
