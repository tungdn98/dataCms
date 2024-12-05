package vn.com.datamanager.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link vn.com.datamanager.domain.LoginHistory} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.LoginHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /login-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class LoginHistoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter empCode;

    private StringFilter empUsername;

    private StringFilter empFullName;

    private StringFilter loginIp;

    private InstantFilter loginTime;

    private Boolean distinct;

    public LoginHistoryCriteria() {}

    public LoginHistoryCriteria(LoginHistoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.empCode = other.empCode == null ? null : other.empCode.copy();
        this.empUsername = other.empUsername == null ? null : other.empUsername.copy();
        this.empFullName = other.empFullName == null ? null : other.empFullName.copy();
        this.loginIp = other.loginIp == null ? null : other.loginIp.copy();
        this.loginTime = other.loginTime == null ? null : other.loginTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LoginHistoryCriteria copy() {
        return new LoginHistoryCriteria(this);
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

    public StringFilter getEmpCode() {
        return empCode;
    }

    public StringFilter empCode() {
        if (empCode == null) {
            empCode = new StringFilter();
        }
        return empCode;
    }

    public void setEmpCode(StringFilter empCode) {
        this.empCode = empCode;
    }

    public StringFilter getEmpUsername() {
        return empUsername;
    }

    public StringFilter empUsername() {
        if (empUsername == null) {
            empUsername = new StringFilter();
        }
        return empUsername;
    }

    public void setEmpUsername(StringFilter empUsername) {
        this.empUsername = empUsername;
    }

    public StringFilter getEmpFullName() {
        return empFullName;
    }

    public StringFilter empFullName() {
        if (empFullName == null) {
            empFullName = new StringFilter();
        }
        return empFullName;
    }

    public void setEmpFullName(StringFilter empFullName) {
        this.empFullName = empFullName;
    }

    public StringFilter getLoginIp() {
        return loginIp;
    }

    public StringFilter loginIp() {
        if (loginIp == null) {
            loginIp = new StringFilter();
        }
        return loginIp;
    }

    public void setLoginIp(StringFilter loginIp) {
        this.loginIp = loginIp;
    }

    public InstantFilter getLoginTime() {
        return loginTime;
    }

    public InstantFilter loginTime() {
        if (loginTime == null) {
            loginTime = new InstantFilter();
        }
        return loginTime;
    }

    public void setLoginTime(InstantFilter loginTime) {
        this.loginTime = loginTime;
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
        final LoginHistoryCriteria that = (LoginHistoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(empCode, that.empCode) &&
            Objects.equals(empUsername, that.empUsername) &&
            Objects.equals(empFullName, that.empFullName) &&
            Objects.equals(loginIp, that.loginIp) &&
            Objects.equals(loginTime, that.loginTime) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, empCode, empUsername, empFullName, loginIp, loginTime, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoginHistoryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (empCode != null ? "empCode=" + empCode + ", " : "") +
            (empUsername != null ? "empUsername=" + empUsername + ", " : "") +
            (empFullName != null ? "empFullName=" + empFullName + ", " : "") +
            (loginIp != null ? "loginIp=" + loginIp + ", " : "") +
            (loginTime != null ? "loginTime=" + loginTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
