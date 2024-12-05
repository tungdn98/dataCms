package vn.com.datamanager.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ActivityType.
 */
@Entity
@Table(name = "activity_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ActivityType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "activity_type_id")
    private Long activityTypeId;

    @Column(name = "activity_type")
    private String activityType;

    @Column(name = "text_str")
    private String textStr;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ActivityType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActivityTypeId() {
        return this.activityTypeId;
    }

    public ActivityType activityTypeId(Long activityTypeId) {
        this.setActivityTypeId(activityTypeId);
        return this;
    }

    public void setActivityTypeId(Long activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getActivityType() {
        return this.activityType;
    }

    public ActivityType activityType(String activityType) {
        this.setActivityType(activityType);
        return this;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getTextStr() {
        return this.textStr;
    }

    public ActivityType textStr(String textStr) {
        this.setTextStr(textStr);
        return this;
    }

    public void setTextStr(String textStr) {
        this.textStr = textStr;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivityType)) {
            return false;
        }
        return id != null && id.equals(((ActivityType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivityType{" +
            "id=" + getId() +
            ", activityTypeId=" + getActivityTypeId() +
            ", activityType='" + getActivityType() + "'" +
            ", textStr='" + getTextStr() + "'" +
            "}";
    }
}
