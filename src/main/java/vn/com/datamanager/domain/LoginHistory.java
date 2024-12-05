package vn.com.datamanager.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Lịch sử đăng nhập hệ thống
 */
@Schema(description = "Lịch sử đăng nhập hệ thống")
@Entity
@Table(name = "login_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LoginHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "emp_code")
    private String empCode;

    @Column(name = "emp_username")
    private String empUsername;

    @Column(name = "emp_full_name")
    private String empFullName;

    @Column(name = "login_ip")
    private String loginIp;

    @Column(name = "login_time")
    private Instant loginTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LoginHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpCode() {
        return this.empCode;
    }

    public LoginHistory empCode(String empCode) {
        this.setEmpCode(empCode);
        return this;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpUsername() {
        return this.empUsername;
    }

    public LoginHistory empUsername(String empUsername) {
        this.setEmpUsername(empUsername);
        return this;
    }

    public void setEmpUsername(String empUsername) {
        this.empUsername = empUsername;
    }

    public String getEmpFullName() {
        return this.empFullName;
    }

    public LoginHistory empFullName(String empFullName) {
        this.setEmpFullName(empFullName);
        return this;
    }

    public void setEmpFullName(String empFullName) {
        this.empFullName = empFullName;
    }

    public String getLoginIp() {
        return this.loginIp;
    }

    public LoginHistory loginIp(String loginIp) {
        this.setLoginIp(loginIp);
        return this;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Instant getLoginTime() {
        return this.loginTime;
    }

    public LoginHistory loginTime(Instant loginTime) {
        this.setLoginTime(loginTime);
        return this;
    }

    public void setLoginTime(Instant loginTime) {
        this.loginTime = loginTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoginHistory)) {
            return false;
        }
        return id != null && id.equals(((LoginHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoginHistory{" +
            "id=" + getId() +
            ", empCode='" + getEmpCode() + "'" +
            ", empUsername='" + getEmpUsername() + "'" +
            ", empFullName='" + getEmpFullName() + "'" +
            ", loginIp='" + getLoginIp() + "'" +
            ", loginTime='" + getLoginTime() + "'" +
            "}";
    }
}
