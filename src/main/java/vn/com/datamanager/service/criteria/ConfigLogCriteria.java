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
 * Criteria class for the {@link vn.com.datamanager.domain.ConfigLog} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.ConfigLogResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /config-logs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ConfigLogCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter configName;

    private StringFilter valueBefore;

    private StringFilter valueAfter;

    private InstantFilter modifiedDate;

    private StringFilter modifiedUsername;

    private StringFilter modifiedFullname;

    private Boolean distinct;

    public ConfigLogCriteria() {}

    public ConfigLogCriteria(ConfigLogCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.configName = other.configName == null ? null : other.configName.copy();
        this.valueBefore = other.valueBefore == null ? null : other.valueBefore.copy();
        this.valueAfter = other.valueAfter == null ? null : other.valueAfter.copy();
        this.modifiedDate = other.modifiedDate == null ? null : other.modifiedDate.copy();
        this.modifiedUsername = other.modifiedUsername == null ? null : other.modifiedUsername.copy();
        this.modifiedFullname = other.modifiedFullname == null ? null : other.modifiedFullname.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ConfigLogCriteria copy() {
        return new ConfigLogCriteria(this);
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

    public StringFilter getValueBefore() {
        return valueBefore;
    }

    public StringFilter valueBefore() {
        if (valueBefore == null) {
            valueBefore = new StringFilter();
        }
        return valueBefore;
    }

    public void setValueBefore(StringFilter valueBefore) {
        this.valueBefore = valueBefore;
    }

    public StringFilter getValueAfter() {
        return valueAfter;
    }

    public StringFilter valueAfter() {
        if (valueAfter == null) {
            valueAfter = new StringFilter();
        }
        return valueAfter;
    }

    public void setValueAfter(StringFilter valueAfter) {
        this.valueAfter = valueAfter;
    }

    public InstantFilter getModifiedDate() {
        return modifiedDate;
    }

    public InstantFilter modifiedDate() {
        if (modifiedDate == null) {
            modifiedDate = new InstantFilter();
        }
        return modifiedDate;
    }

    public void setModifiedDate(InstantFilter modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public StringFilter getModifiedUsername() {
        return modifiedUsername;
    }

    public StringFilter modifiedUsername() {
        if (modifiedUsername == null) {
            modifiedUsername = new StringFilter();
        }
        return modifiedUsername;
    }

    public void setModifiedUsername(StringFilter modifiedUsername) {
        this.modifiedUsername = modifiedUsername;
    }

    public StringFilter getModifiedFullname() {
        return modifiedFullname;
    }

    public StringFilter modifiedFullname() {
        if (modifiedFullname == null) {
            modifiedFullname = new StringFilter();
        }
        return modifiedFullname;
    }

    public void setModifiedFullname(StringFilter modifiedFullname) {
        this.modifiedFullname = modifiedFullname;
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
        final ConfigLogCriteria that = (ConfigLogCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(configName, that.configName) &&
            Objects.equals(valueBefore, that.valueBefore) &&
            Objects.equals(valueAfter, that.valueAfter) &&
            Objects.equals(modifiedDate, that.modifiedDate) &&
            Objects.equals(modifiedUsername, that.modifiedUsername) &&
            Objects.equals(modifiedFullname, that.modifiedFullname) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, configName, valueBefore, valueAfter, modifiedDate, modifiedUsername, modifiedFullname, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfigLogCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (configName != null ? "configName=" + configName + ", " : "") +
            (valueBefore != null ? "valueBefore=" + valueBefore + ", " : "") +
            (valueAfter != null ? "valueAfter=" + valueAfter + ", " : "") +
            (modifiedDate != null ? "modifiedDate=" + modifiedDate + ", " : "") +
            (modifiedUsername != null ? "modifiedUsername=" + modifiedUsername + ", " : "") +
            (modifiedFullname != null ? "modifiedFullname=" + modifiedFullname + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
