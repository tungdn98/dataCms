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
 * Criteria class for the {@link vn.com.datamanager.domain.Tungdn} entity. This class is used
 * in {@link vn.com.datamanager.web.rest.TungdnResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tungdns?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class TungdnCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tung;

    private StringFilter tung1;

    private Boolean distinct;

    public TungdnCriteria() {}

    public TungdnCriteria(TungdnCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tung = other.tung == null ? null : other.tung.copy();
        this.tung1 = other.tung1 == null ? null : other.tung1.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TungdnCriteria copy() {
        return new TungdnCriteria(this);
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

    public StringFilter getTung() {
        return tung;
    }

    public StringFilter tung() {
        if (tung == null) {
            tung = new StringFilter();
        }
        return tung;
    }

    public void setTung(StringFilter tung) {
        this.tung = tung;
    }

    public StringFilter getTung1() {
        return tung1;
    }

    public StringFilter tung1() {
        if (tung1 == null) {
            tung1 = new StringFilter();
        }
        return tung1;
    }

    public void setTung1(StringFilter tung1) {
        this.tung1 = tung1;
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
        final TungdnCriteria that = (TungdnCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tung, that.tung) &&
            Objects.equals(tung1, that.tung1) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tung, tung1, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TungdnCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tung != null ? "tung=" + tung + ", " : "") +
            (tung1 != null ? "tung1=" + tung1 + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
