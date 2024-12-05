package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.Currency;
import vn.com.datamanager.repository.CurrencyRepository;

/**
 * Service Implementation for managing {@link Currency}.
 */
@Service
@Transactional
public class CurrencyService {

    private final Logger log = LoggerFactory.getLogger(CurrencyService.class);

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    /**
     * Save a currency.
     *
     * @param currency the entity to save.
     * @return the persisted entity.
     */
    public Currency save(Currency currency) {
        log.debug("Request to save Currency : {}", currency);
        return currencyRepository.save(currency);
    }

    /**
     * Update a currency.
     *
     * @param currency the entity to save.
     * @return the persisted entity.
     */
    public Currency update(Currency currency) {
        log.debug("Request to save Currency : {}", currency);
        return currencyRepository.save(currency);
    }

    /**
     * Partially update a currency.
     *
     * @param currency the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Currency> partialUpdate(Currency currency) {
        log.debug("Request to partially update Currency : {}", currency);

        return currencyRepository
            .findById(currency.getId())
            .map(existingCurrency -> {
                if (currency.getCurrencyId() != null) {
                    existingCurrency.setCurrencyId(currency.getCurrencyId());
                }
                if (currency.getCurrencyNum() != null) {
                    existingCurrency.setCurrencyNum(currency.getCurrencyNum());
                }
                if (currency.getCurrencyCode() != null) {
                    existingCurrency.setCurrencyCode(currency.getCurrencyCode());
                }
                if (currency.getCurrencyName() != null) {
                    existingCurrency.setCurrencyName(currency.getCurrencyName());
                }
                if (currency.getCurrencyExchangeRateId() != null) {
                    existingCurrency.setCurrencyExchangeRateId(currency.getCurrencyExchangeRateId());
                }
                if (currency.getConversionRate() != null) {
                    existingCurrency.setConversionRate(currency.getConversionRate());
                }

                return existingCurrency;
            })
            .map(currencyRepository::save);
    }

    /**
     * Get all the currencies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Currency> findAll(Pageable pageable) {
        log.debug("Request to get all Currencies");
        return currencyRepository.findAll(pageable);
    }

    /**
     * Get one currency by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Currency> findOne(Long id) {
        log.debug("Request to get Currency : {}", id);
        return currencyRepository.findById(id);
    }

    /**
     * Delete the currency by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Currency : {}", id);
        currencyRepository.deleteById(id);
    }
}
