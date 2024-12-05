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
 * Criteria class for the {@link vn.com.datamanager.domain.ContractType} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.ContractTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contract-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ContractTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter contractTypeId;

    private StringFilter contractTypeName;

    private StringFilter contractTypeCode;

    private Boolean distinct;

    public ContractTypeCriteria() {}

    public ContractTypeCriteria(ContractTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.contractTypeId = other.contractTypeId == null ? null : other.contractTypeId.copy();
        this.contractTypeName = other.contractTypeName == null ? null : other.contractTypeName.copy();
        this.contractTypeCode = other.contractTypeCode == null ? null : other.contractTypeCode.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ContractTypeCriteria copy() {
        return new ContractTypeCriteria(this);
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

    public StringFilter getContractTypeId() {
        return contractTypeId;
    }

    public StringFilter contractTypeId() {
        if (contractTypeId == null) {
            contractTypeId = new StringFilter();
        }
        return contractTypeId;
    }

    public void setContractTypeId(StringFilter contractTypeId) {
        this.contractTypeId = contractTypeId;
    }

    public StringFilter getContractTypeName() {
        return contractTypeName;
    }

    public StringFilter contractTypeName() {
        if (contractTypeName == null) {
            contractTypeName = new StringFilter();
        }
        return contractTypeName;
    }

    public void setContractTypeName(StringFilter contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    public StringFilter getContractTypeCode() {
        return contractTypeCode;
    }

    public StringFilter contractTypeCode() {
        if (contractTypeCode == null) {
            contractTypeCode = new StringFilter();
        }
        return contractTypeCode;
    }

    public void setContractTypeCode(StringFilter contractTypeCode) {
        this.contractTypeCode = contractTypeCode;
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
        final ContractTypeCriteria that = (ContractTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(contractTypeId, that.contractTypeId) &&
            Objects.equals(contractTypeName, that.contractTypeName) &&
            Objects.equals(contractTypeCode, that.contractTypeCode) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contractTypeId, contractTypeName, contractTypeCode, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContractTypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (contractTypeId != null ? "contractTypeId=" + contractTypeId + ", " : "") +
            (contractTypeName != null ? "contractTypeName=" + contractTypeName + ", " : "") +
            (contractTypeCode != null ? "contractTypeCode=" + contractTypeCode + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
