package vn.com.datamanager.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Currency.
 */
@Entity
@Table(name = "currency")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "currency_id")
    private String currencyId;

    @Column(name = "currency_num")
    private String currencyNum;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "currency_exchange_rate_id")
    private Long currencyExchangeRateId;

    @Column(name = "conversion_rate", precision = 21, scale = 2)
    private BigDecimal conversionRate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Currency id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyId() {
        return this.currencyId;
    }

    public Currency currencyId(String currencyId) {
        this.setCurrencyId(currencyId);
        return this;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyNum() {
        return this.currencyNum;
    }

    public Currency currencyNum(String currencyNum) {
        this.setCurrencyNum(currencyNum);
        return this;
    }

    public void setCurrencyNum(String currencyNum) {
        this.currencyNum = currencyNum;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public Currency currencyCode(String currencyCode) {
        this.setCurrencyCode(currencyCode);
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return this.currencyName;
    }

    public Currency currencyName(String currencyName) {
        this.setCurrencyName(currencyName);
        return this;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Long getCurrencyExchangeRateId() {
        return this.currencyExchangeRateId;
    }

    public Currency currencyExchangeRateId(Long currencyExchangeRateId) {
        this.setCurrencyExchangeRateId(currencyExchangeRateId);
        return this;
    }

    public void setCurrencyExchangeRateId(Long currencyExchangeRateId) {
        this.currencyExchangeRateId = currencyExchangeRateId;
    }

    public BigDecimal getConversionRate() {
        return this.conversionRate;
    }

    public Currency conversionRate(BigDecimal conversionRate) {
        this.setConversionRate(conversionRate);
        return this;
    }

    public void setConversionRate(BigDecimal conversionRate) {
        this.conversionRate = conversionRate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Currency)) {
            return false;
        }
        return id != null && id.equals(((Currency) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Currency{" +
            "id=" + getId() +
            ", currencyId='" + getCurrencyId() + "'" +
            ", currencyNum='" + getCurrencyNum() + "'" +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", currencyName='" + getCurrencyName() + "'" +
            ", currencyExchangeRateId=" + getCurrencyExchangeRateId() +
            ", conversionRate=" + getConversionRate() +
            "}";
    }
}
