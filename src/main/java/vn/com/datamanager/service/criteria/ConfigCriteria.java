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
 * Criteria class for the {@link vn.com.datamanager.domain.Config} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.ConfigResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /configs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ConfigCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter configName;

    private StringFilter configValue;

    private StringFilter configDesc;

    private InstantFilter createdDate;

    private StringFilter createdBy;

    private InstantFilter lastModifiedDate;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public ConfigCriteria() {}

    public ConfigCriteria(ConfigCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.configName = other.configName == null ? null : other.configName.copy();
        this.configValue = other.configValue == null ? null : other.configValue.copy();
        this.configDesc = other.configDesc == null ? null : other.configDesc.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ConfigCriteria copy() {
        return new ConfigCriteria(this);
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

    public StringFilter getConfigName() {
        return configName;
    }

    public StringFilter configName() {
        if (configName == null) {
            configName = new StringFilter();
        }
        return configName;
    }

    public void setConfigName(StringFilter configName) {
        this.configName = configName;
    }

    public StringFilter getConfigValue() {
        return configValue;
    }

    public StringFilter configValue() {
        if (configValue == null) {
            configValue = new StringFilter();
        }
        return configValue;
    }

    public void setConfigValue(StringFilter configValue) {
        this.configValue = configValue;
    }

    public StringFilter getConfigDesc() {
        return configDesc;
    }

    public StringFilter configDesc() {
        if (configDesc == null) {
            configDesc = new StringFilter();
        }
        return configDesc;
    }

    public void setConfigDesc(StringFilter configDesc) {
        this.configDesc = configDesc;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            createdDate = new InstantFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public InstantFilter lastModifiedDate() {
        if (lastModifiedDate == null) {
            lastModifiedDate = new InstantFilter();
        }
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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
        final ConfigCriteria that = (ConfigCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(configName, that.configName) &&
            Objects.equals(configValue, that.configValue) &&
            Objects.equals(configDesc, that.configDesc) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, configName, configValue, configDesc, createdDate, createdBy, lastModifiedDate, lastModifiedBy, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfigCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (configName != null ? "configName=" + configName + ", " : "") +
            (configValue != null ? "configValue=" + configValue + ", " : "") +
            (configDesc != null ? "configDesc=" + configDesc + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
