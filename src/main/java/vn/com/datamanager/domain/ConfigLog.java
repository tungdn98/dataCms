package vn.com.datamanager.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Lịch sử chỉnh sửa tham số hệ thống
 */
@Schema(description = "Lịch sử chỉnh sửa tham số hệ thống")
@Entity
@Table(name = "config_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConfigLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(min = 1, max = 100)
    @Column(name = "config_name", length = 100, unique = true)
    private String configName;

    @Column(name = "value_before")
    private String valueBefore;

    @Column(name = "value_after")
    private String valueAfter;

    @Column(name = "modified_date")
    private Instant modifiedDate;

    @Column(name = "modified_username")
    private String modifiedUsername;

    @Column(name = "modified_fullname")
    private String modifiedFullname;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ConfigLog id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigName() {
        return this.configName;
    }

    public ConfigLog configName(String configName) {
        this.setConfigName(configName);
        return this;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getValueBefore() {
        return this.valueBefore;
    }

    public ConfigLog valueBefore(String valueBefore) {
        this.setValueBefore(valueBefore);
        return this;
    }

    public void setValueBefore(String valueBefore) {
        this.valueBefore = valueBefore;
    }

    public String getValueAfter() {
        return this.valueAfter;
    }

    public ConfigLog valueAfter(String valueAfter) {
        this.setValueAfter(valueAfter);
        return this;
    }

    public void setValueAfter(String valueAfter) {
        this.valueAfter = valueAfter;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public ConfigLog modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedUsername() {
        return this.modifiedUsername;
    }

    public ConfigLog modifiedUsername(String modifiedUsername) {
        this.setModifiedUsername(modifiedUsername);
        return this;
    }

    public void setModifiedUsername(String modifiedUsername) {
        this.modifiedUsername = modifiedUsername;
    }

    public String getModifiedFullname() {
        return this.modifiedFullname;
    }

    public ConfigLog modifiedFullname(String modifiedFullname) {
        this.setModifiedFullname(modifiedFullname);
        return this;
    }

    public void setModifiedFullname(String modifiedFullname) {
        this.modifiedFullname = modifiedFullname;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfigLog)) {
            return false;
        }
        return id != null && id.equals(((ConfigLog) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfigLog{" +
            "id=" + getId() +
            ", configName='" + getConfigName() + "'" +
            ", valueBefore='" + getValueBefore() + "'" +
            ", valueAfter='" + getValueAfter() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", modifiedUsername='" + getModifiedUsername() + "'" +
            ", modifiedFullname='" + getModifiedFullname() + "'" +
            "}";
    }
}
