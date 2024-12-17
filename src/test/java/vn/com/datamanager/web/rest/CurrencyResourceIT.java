package vn.com.datamanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.com.datamanager.web.rest.TestUtil.sameNumber;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.IntegrationTest;
import vn.com.datamanager.domain.Currency;
import vn.com.datamanager.repository.CurrencyRepository;
import vn.com.datamanager.service.criteria.CurrencyCriteria;

/**
 * Integration tests for the {@link CurrencyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CurrencyResourceIT {

    private static final String DEFAULT_CURRENCY_ID = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY_NUM = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CURRENCY_EXCHANGE_RATE_ID = 1L;
    private static final Long UPDATED_CURRENCY_EXCHANGE_RATE_ID = 2L;
    private static final Long SMALLER_CURRENCY_EXCHANGE_RATE_ID = 1L - 1L;

    private static final BigDecimal DEFAULT_CONVERSION_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONVERSION_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_CONVERSION_RATE = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/currencies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCurrencyMockMvc;

    private Currency currency;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Currency createEntity(EntityManager em) {
        Currency currency = new Currency()
            .currencyId(DEFAULT_CURRENCY_ID)
            .currencyNum(DEFAULT_CURRENCY_NUM)
            .currencyCode(DEFAULT_CURRENCY_CODE)
            .currencyName(DEFAULT_CURRENCY_NAME)
            .currencyExchangeRateId(DEFAULT_CURRENCY_EXCHANGE_RATE_ID)
            .conversionRate(DEFAULT_CONVERSION_RATE);
        return currency;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Currency createUpdatedEntity(EntityManager em) {
        Currency currency = new Currency()
            .currencyId(UPDATED_CURRENCY_ID)
            .currencyNum(UPDATED_CURRENCY_NUM)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .currencyName(UPDATED_CURRENCY_NAME)
            .currencyExchangeRateId(UPDATED_CURRENCY_EXCHANGE_RATE_ID)
            .conversionRate(UPDATED_CONVERSION_RATE);
        return currency;
    }

    @BeforeEach
    public void initTest() {
        currency = createEntity(em);
    }

    @Test
    @Transactional
    void createCurrency() throws Exception {
        int databaseSizeBeforeCreate = currencyRepository.findAll().size();
        // Create the Currency
        restCurrencyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isCreated());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeCreate + 1);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getCurrencyId()).isEqualTo(DEFAULT_CURRENCY_ID);
        assertThat(testCurrency.getCurrencyNum()).isEqualTo(DEFAULT_CURRENCY_NUM);
        assertThat(testCurrency.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testCurrency.getCurrencyName()).isEqualTo(DEFAULT_CURRENCY_NAME);
        assertThat(testCurrency.getCurrencyExchangeRateId()).isEqualTo(DEFAULT_CURRENCY_EXCHANGE_RATE_ID);
        assertThat(testCurrency.getConversionRate()).isEqualByComparingTo(DEFAULT_CONVERSION_RATE);
    }

    @Test
    @Transactional
    void createCurrencyWithExistingId() throws Exception {
        // Create the Currency with an existing ID
        currency.setId(1L);

        int databaseSizeBeforeCreate = currencyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrencyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCurrencies() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList
        restCurrencyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyId").value(hasItem(DEFAULT_CURRENCY_ID)))
            .andExpect(jsonPath("$.[*].currencyNum").value(hasItem(DEFAULT_CURRENCY_NUM)))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].currencyName").value(hasItem(DEFAULT_CURRENCY_NAME)))
            .andExpect(jsonPath("$.[*].currencyExchangeRateId").value(hasItem(DEFAULT_CURRENCY_EXCHANGE_RATE_ID.intValue())))
            .andExpect(jsonPath("$.[*].conversionRate").value(hasItem(sameNumber(DEFAULT_CONVERSION_RATE))));
    }

    @Test
    @Transactional
    void getCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get the currency
        restCurrencyMockMvc
            .perform(get(ENTITY_API_URL_ID, currency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(currency.getId().intValue()))
            .andExpect(jsonPath("$.currencyId").value(DEFAULT_CURRENCY_ID))
            .andExpect(jsonPath("$.currencyNum").value(DEFAULT_CURRENCY_NUM))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE))
            .andExpect(jsonPath("$.currencyName").value(DEFAULT_CURRENCY_NAME))
            .andExpect(jsonPath("$.currencyExchangeRateId").value(DEFAULT_CURRENCY_EXCHANGE_RATE_ID.intValue()))
            .andExpect(jsonPath("$.conversionRate").value(sameNumber(DEFAULT_CONVERSION_RATE)));
    }

    @Test
    @Transactional
    void getCurrenciesByIdFiltering() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        Long id = currency.getId();

        defaultCurrencyShouldBeFound("id.equals=" + id);
        defaultCurrencyShouldNotBeFound("id.notEquals=" + id);

        defaultCurrencyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCurrencyShouldNotBeFound("id.greaterThan=" + id);

        defaultCurrencyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCurrencyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyId equals to DEFAULT_CURRENCY_ID
        defaultCurrencyShouldBeFound("currencyId.equals=" + DEFAULT_CURRENCY_ID);

        // Get all the currencyList where currencyId equals to UPDATED_CURRENCY_ID
        defaultCurrencyShouldNotBeFound("currencyId.equals=" + UPDATED_CURRENCY_ID);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyId not equals to DEFAULT_CURRENCY_ID
        defaultCurrencyShouldNotBeFound("currencyId.notEquals=" + DEFAULT_CURRENCY_ID);

        // Get all the currencyList where currencyId not equals to UPDATED_CURRENCY_ID
        defaultCurrencyShouldBeFound("currencyId.notEquals=" + UPDATED_CURRENCY_ID);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyIdIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyId in DEFAULT_CURRENCY_ID or UPDATED_CURRENCY_ID
        defaultCurrencyShouldBeFound("currencyId.in=" + DEFAULT_CURRENCY_ID + "," + UPDATED_CURRENCY_ID);

        // Get all the currencyList where currencyId equals to UPDATED_CURRENCY_ID
        defaultCurrencyShouldNotBeFound("currencyId.in=" + UPDATED_CURRENCY_ID);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyId is not null
        defaultCurrencyShouldBeFound("currencyId.specified=true");

        // Get all the currencyList where currencyId is null
        defaultCurrencyShouldNotBeFound("currencyId.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyIdContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyId contains DEFAULT_CURRENCY_ID
        defaultCurrencyShouldBeFound("currencyId.contains=" + DEFAULT_CURRENCY_ID);

        // Get all the currencyList where currencyId contains UPDATED_CURRENCY_ID
        defaultCurrencyShouldNotBeFound("currencyId.contains=" + UPDATED_CURRENCY_ID);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyIdNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyId does not contain DEFAULT_CURRENCY_ID
        defaultCurrencyShouldNotBeFound("currencyId.doesNotContain=" + DEFAULT_CURRENCY_ID);

        // Get all the currencyList where currencyId does not contain UPDATED_CURRENCY_ID
        defaultCurrencyShouldBeFound("currencyId.doesNotContain=" + UPDATED_CURRENCY_ID);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyNumIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyNum equals to DEFAULT_CURRENCY_NUM
        defaultCurrencyShouldBeFound("currencyNum.equals=" + DEFAULT_CURRENCY_NUM);

        // Get all the currencyList where currencyNum equals to UPDATED_CURRENCY_NUM
        defaultCurrencyShouldNotBeFound("currencyNum.equals=" + UPDATED_CURRENCY_NUM);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyNumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyNum not equals to DEFAULT_CURRENCY_NUM
        defaultCurrencyShouldNotBeFound("currencyNum.notEquals=" + DEFAULT_CURRENCY_NUM);

        // Get all the currencyList where currencyNum not equals to UPDATED_CURRENCY_NUM
        defaultCurrencyShouldBeFound("currencyNum.notEquals=" + UPDATED_CURRENCY_NUM);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyNumIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyNum in DEFAULT_CURRENCY_NUM or UPDATED_CURRENCY_NUM
        defaultCurrencyShouldBeFound("currencyNum.in=" + DEFAULT_CURRENCY_NUM + "," + UPDATED_CURRENCY_NUM);

        // Get all the currencyList where currencyNum equals to UPDATED_CURRENCY_NUM
        defaultCurrencyShouldNotBeFound("currencyNum.in=" + UPDATED_CURRENCY_NUM);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyNum is not null
        defaultCurrencyShouldBeFound("currencyNum.specified=true");

        // Get all the currencyList where currencyNum is null
        defaultCurrencyShouldNotBeFound("currencyNum.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyNumContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyNum contains DEFAULT_CURRENCY_NUM
        defaultCurrencyShouldBeFound("currencyNum.contains=" + DEFAULT_CURRENCY_NUM);

        // Get all the currencyList where currencyNum contains UPDATED_CURRENCY_NUM
        defaultCurrencyShouldNotBeFound("currencyNum.contains=" + UPDATED_CURRENCY_NUM);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyNumNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyNum does not contain DEFAULT_CURRENCY_NUM
        defaultCurrencyShouldNotBeFound("currencyNum.doesNotContain=" + DEFAULT_CURRENCY_NUM);

        // Get all the currencyList where currencyNum does not contain UPDATED_CURRENCY_NUM
        defaultCurrencyShouldBeFound("currencyNum.doesNotContain=" + UPDATED_CURRENCY_NUM);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyCode equals to DEFAULT_CURRENCY_CODE
        defaultCurrencyShouldBeFound("currencyCode.equals=" + DEFAULT_CURRENCY_CODE);

        // Get all the currencyList where currencyCode equals to UPDATED_CURRENCY_CODE
        defaultCurrencyShouldNotBeFound("currencyCode.equals=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyCode not equals to DEFAULT_CURRENCY_CODE
        defaultCurrencyShouldNotBeFound("currencyCode.notEquals=" + DEFAULT_CURRENCY_CODE);

        // Get all the currencyList where currencyCode not equals to UPDATED_CURRENCY_CODE
        defaultCurrencyShouldBeFound("currencyCode.notEquals=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyCodeIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyCode in DEFAULT_CURRENCY_CODE or UPDATED_CURRENCY_CODE
        defaultCurrencyShouldBeFound("currencyCode.in=" + DEFAULT_CURRENCY_CODE + "," + UPDATED_CURRENCY_CODE);

        // Get all the currencyList where currencyCode equals to UPDATED_CURRENCY_CODE
        defaultCurrencyShouldNotBeFound("currencyCode.in=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyCode is not null
        defaultCurrencyShouldBeFound("currencyCode.specified=true");

        // Get all the currencyList where currencyCode is null
        defaultCurrencyShouldNotBeFound("currencyCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyCodeContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyCode contains DEFAULT_CURRENCY_CODE
        defaultCurrencyShouldBeFound("currencyCode.contains=" + DEFAULT_CURRENCY_CODE);

        // Get all the currencyList where currencyCode contains UPDATED_CURRENCY_CODE
        defaultCurrencyShouldNotBeFound("currencyCode.contains=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyCodeNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyCode does not contain DEFAULT_CURRENCY_CODE
        defaultCurrencyShouldNotBeFound("currencyCode.doesNotContain=" + DEFAULT_CURRENCY_CODE);

        // Get all the currencyList where currencyCode does not contain UPDATED_CURRENCY_CODE
        defaultCurrencyShouldBeFound("currencyCode.doesNotContain=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName equals to DEFAULT_CURRENCY_NAME
        defaultCurrencyShouldBeFound("currencyName.equals=" + DEFAULT_CURRENCY_NAME);

        // Get all the currencyList where currencyName equals to UPDATED_CURRENCY_NAME
        defaultCurrencyShouldNotBeFound("currencyName.equals=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName not equals to DEFAULT_CURRENCY_NAME
        defaultCurrencyShouldNotBeFound("currencyName.notEquals=" + DEFAULT_CURRENCY_NAME);

        // Get all the currencyList where currencyName not equals to UPDATED_CURRENCY_NAME
        defaultCurrencyShouldBeFound("currencyName.notEquals=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyNameIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName in DEFAULT_CURRENCY_NAME or UPDATED_CURRENCY_NAME
        defaultCurrencyShouldBeFound("currencyName.in=" + DEFAULT_CURRENCY_NAME + "," + UPDATED_CURRENCY_NAME);

        // Get all the currencyList where currencyName equals to UPDATED_CURRENCY_NAME
        defaultCurrencyShouldNotBeFound("currencyName.in=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName is not null
        defaultCurrencyShouldBeFound("currencyName.specified=true");

        // Get all the currencyList where currencyName is null
        defaultCurrencyShouldNotBeFound("currencyName.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyNameContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName contains DEFAULT_CURRENCY_NAME
        defaultCurrencyShouldBeFound("currencyName.contains=" + DEFAULT_CURRENCY_NAME);

        // Get all the currencyList where currencyName contains UPDATED_CURRENCY_NAME
        defaultCurrencyShouldNotBeFound("currencyName.contains=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyNameNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName does not contain DEFAULT_CURRENCY_NAME
        defaultCurrencyShouldNotBeFound("currencyName.doesNotContain=" + DEFAULT_CURRENCY_NAME);

        // Get all the currencyList where currencyName does not contain UPDATED_CURRENCY_NAME
        defaultCurrencyShouldBeFound("currencyName.doesNotContain=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyExchangeRateIdIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyExchangeRateId equals to DEFAULT_CURRENCY_EXCHANGE_RATE_ID
        defaultCurrencyShouldBeFound("currencyExchangeRateId.equals=" + DEFAULT_CURRENCY_EXCHANGE_RATE_ID);

        // Get all the currencyList where currencyExchangeRateId equals to UPDATED_CURRENCY_EXCHANGE_RATE_ID
        defaultCurrencyShouldNotBeFound("currencyExchangeRateId.equals=" + UPDATED_CURRENCY_EXCHANGE_RATE_ID);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyExchangeRateIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyExchangeRateId not equals to DEFAULT_CURRENCY_EXCHANGE_RATE_ID
        defaultCurrencyShouldNotBeFound("currencyExchangeRateId.notEquals=" + DEFAULT_CURRENCY_EXCHANGE_RATE_ID);

        // Get all the currencyList where currencyExchangeRateId not equals to UPDATED_CURRENCY_EXCHANGE_RATE_ID
        defaultCurrencyShouldBeFound("currencyExchangeRateId.notEquals=" + UPDATED_CURRENCY_EXCHANGE_RATE_ID);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyExchangeRateIdIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyExchangeRateId in DEFAULT_CURRENCY_EXCHANGE_RATE_ID or UPDATED_CURRENCY_EXCHANGE_RATE_ID
        defaultCurrencyShouldBeFound(
            "currencyExchangeRateId.in=" + DEFAULT_CURRENCY_EXCHANGE_RATE_ID + "," + UPDATED_CURRENCY_EXCHANGE_RATE_ID
        );

        // Get all the currencyList where currencyExchangeRateId equals to UPDATED_CURRENCY_EXCHANGE_RATE_ID
        defaultCurrencyShouldNotBeFound("currencyExchangeRateId.in=" + UPDATED_CURRENCY_EXCHANGE_RATE_ID);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyExchangeRateIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyExchangeRateId is not null
        defaultCurrencyShouldBeFound("currencyExchangeRateId.specified=true");

        // Get all the currencyList where currencyExchangeRateId is null
        defaultCurrencyShouldNotBeFound("currencyExchangeRateId.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyExchangeRateIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyExchangeRateId is greater than or equal to DEFAULT_CURRENCY_EXCHANGE_RATE_ID
        defaultCurrencyShouldBeFound("currencyExchangeRateId.greaterThanOrEqual=" + DEFAULT_CURRENCY_EXCHANGE_RATE_ID);

        // Get all the currencyList where currencyExchangeRateId is greater than or equal to UPDATED_CURRENCY_EXCHANGE_RATE_ID
        defaultCurrencyShouldNotBeFound("currencyExchangeRateId.greaterThanOrEqual=" + UPDATED_CURRENCY_EXCHANGE_RATE_ID);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyExchangeRateIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyExchangeRateId is less than or equal to DEFAULT_CURRENCY_EXCHANGE_RATE_ID
        defaultCurrencyShouldBeFound("currencyExchangeRateId.lessThanOrEqual=" + DEFAULT_CURRENCY_EXCHANGE_RATE_ID);

        // Get all the currencyList where currencyExchangeRateId is less than or equal to SMALLER_CURRENCY_EXCHANGE_RATE_ID
        defaultCurrencyShouldNotBeFound("currencyExchangeRateId.lessThanOrEqual=" + SMALLER_CURRENCY_EXCHANGE_RATE_ID);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyExchangeRateIdIsLessThanSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyExchangeRateId is less than DEFAULT_CURRENCY_EXCHANGE_RATE_ID
        defaultCurrencyShouldNotBeFound("currencyExchangeRateId.lessThan=" + DEFAULT_CURRENCY_EXCHANGE_RATE_ID);

        // Get all the currencyList where currencyExchangeRateId is less than UPDATED_CURRENCY_EXCHANGE_RATE_ID
        defaultCurrencyShouldBeFound("currencyExchangeRateId.lessThan=" + UPDATED_CURRENCY_EXCHANGE_RATE_ID);
    }

    @Test
    @Transactional
    void getAllCurrenciesByCurrencyExchangeRateIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyExchangeRateId is greater than DEFAULT_CURRENCY_EXCHANGE_RATE_ID
        defaultCurrencyShouldNotBeFound("currencyExchangeRateId.greaterThan=" + DEFAULT_CURRENCY_EXCHANGE_RATE_ID);

        // Get all the currencyList where currencyExchangeRateId is greater than SMALLER_CURRENCY_EXCHANGE_RATE_ID
        defaultCurrencyShouldBeFound("currencyExchangeRateId.greaterThan=" + SMALLER_CURRENCY_EXCHANGE_RATE_ID);
    }

    @Test
    @Transactional
    void getAllCurrenciesByConversionRateIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where conversionRate equals to DEFAULT_CONVERSION_RATE
        defaultCurrencyShouldBeFound("conversionRate.equals=" + DEFAULT_CONVERSION_RATE);

        // Get all the currencyList where conversionRate equals to UPDATED_CONVERSION_RATE
        defaultCurrencyShouldNotBeFound("conversionRate.equals=" + UPDATED_CONVERSION_RATE);
    }

    @Test
    @Transactional
    void getAllCurrenciesByConversionRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where conversionRate not equals to DEFAULT_CONVERSION_RATE
        defaultCurrencyShouldNotBeFound("conversionRate.notEquals=" + DEFAULT_CONVERSION_RATE);

        // Get all the currencyList where conversionRate not equals to UPDATED_CONVERSION_RATE
        defaultCurrencyShouldBeFound("conversionRate.notEquals=" + UPDATED_CONVERSION_RATE);
    }

    @Test
    @Transactional
    void getAllCurrenciesByConversionRateIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where conversionRate in DEFAULT_CONVERSION_RATE or UPDATED_CONVERSION_RATE
        defaultCurrencyShouldBeFound("conversionRate.in=" + DEFAULT_CONVERSION_RATE + "," + UPDATED_CONVERSION_RATE);

        // Get all the currencyList where conversionRate equals to UPDATED_CONVERSION_RATE
        defaultCurrencyShouldNotBeFound("conversionRate.in=" + UPDATED_CONVERSION_RATE);
    }

    @Test
    @Transactional
    void getAllCurrenciesByConversionRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where conversionRate is not null
        defaultCurrencyShouldBeFound("conversionRate.specified=true");

        // Get all the currencyList where conversionRate is null
        defaultCurrencyShouldNotBeFound("conversionRate.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrenciesByConversionRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where conversionRate is greater than or equal to DEFAULT_CONVERSION_RATE
        defaultCurrencyShouldBeFound("conversionRate.greaterThanOrEqual=" + DEFAULT_CONVERSION_RATE);

        // Get all the currencyList where conversionRate is greater than or equal to UPDATED_CONVERSION_RATE
        defaultCurrencyShouldNotBeFound("conversionRate.greaterThanOrEqual=" + UPDATED_CONVERSION_RATE);
    }

    @Test
    @Transactional
    void getAllCurrenciesByConversionRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where conversionRate is less than or equal to DEFAULT_CONVERSION_RATE
        defaultCurrencyShouldBeFound("conversionRate.lessThanOrEqual=" + DEFAULT_CONVERSION_RATE);

        // Get all the currencyList where conversionRate is less than or equal to SMALLER_CONVERSION_RATE
        defaultCurrencyShouldNotBeFound("conversionRate.lessThanOrEqual=" + SMALLER_CONVERSION_RATE);
    }

    @Test
    @Transactional
    void getAllCurrenciesByConversionRateIsLessThanSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where conversionRate is less than DEFAULT_CONVERSION_RATE
        defaultCurrencyShouldNotBeFound("conversionRate.lessThan=" + DEFAULT_CONVERSION_RATE);

        // Get all the currencyList where conversionRate is less than UPDATED_CONVERSION_RATE
        defaultCurrencyShouldBeFound("conversionRate.lessThan=" + UPDATED_CONVERSION_RATE);
    }

    @Test
    @Transactional
    void getAllCurrenciesByConversionRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where conversionRate is greater than DEFAULT_CONVERSION_RATE
        defaultCurrencyShouldNotBeFound("conversionRate.greaterThan=" + DEFAULT_CONVERSION_RATE);

        // Get all the currencyList where conversionRate is greater than SMALLER_CONVERSION_RATE
        defaultCurrencyShouldBeFound("conversionRate.greaterThan=" + SMALLER_CONVERSION_RATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCurrencyShouldBeFound(String filter) throws Exception {
        restCurrencyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyId").value(hasItem(DEFAULT_CURRENCY_ID)))
            .andExpect(jsonPath("$.[*].currencyNum").value(hasItem(DEFAULT_CURRENCY_NUM)))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].currencyName").value(hasItem(DEFAULT_CURRENCY_NAME)))
            .andExpect(jsonPath("$.[*].currencyExchangeRateId").value(hasItem(DEFAULT_CURRENCY_EXCHANGE_RATE_ID.intValue())))
            .andExpect(jsonPath("$.[*].conversionRate").value(hasItem(sameNumber(DEFAULT_CONVERSION_RATE))));

        // Check, that the count call also returns 1
        restCurrencyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCurrencyShouldNotBeFound(String filter) throws Exception {
        restCurrencyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCurrencyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCurrency() throws Exception {
        // Get the currency
        restCurrencyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // Update the currency
        Currency updatedCurrency = currencyRepository.findById(currency.getId()).get();
        // Disconnect from session so that the updates on updatedCurrency are not directly saved in db
        em.detach(updatedCurrency);
        updatedCurrency
            .currencyId(UPDATED_CURRENCY_ID)
            .currencyNum(UPDATED_CURRENCY_NUM)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .currencyName(UPDATED_CURRENCY_NAME)
            .currencyExchangeRateId(UPDATED_CURRENCY_EXCHANGE_RATE_ID)
            .conversionRate(UPDATED_CONVERSION_RATE);

        restCurrencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCurrency.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCurrency))
            )
            .andExpect(status().isOk());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getCurrencyId()).isEqualTo(UPDATED_CURRENCY_ID);
        assertThat(testCurrency.getCurrencyNum()).isEqualTo(UPDATED_CURRENCY_NUM);
        assertThat(testCurrency.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testCurrency.getCurrencyName()).isEqualTo(UPDATED_CURRENCY_NAME);
        assertThat(testCurrency.getCurrencyExchangeRateId()).isEqualTo(UPDATED_CURRENCY_EXCHANGE_RATE_ID);
        assertThat(testCurrency.getConversionRate()).isEqualByComparingTo(UPDATED_CONVERSION_RATE);
    }

    @Test
    @Transactional
    void putNonExistingCurrency() throws Exception {
        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();
        currency.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, currency.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currency))
            )
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCurrency() throws Exception {
        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();
        currency.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currency))
            )
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCurrency() throws Exception {
        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();
        currency.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrencyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCurrencyWithPatch() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // Update the currency using partial update
        Currency partialUpdatedCurrency = new Currency();
        partialUpdatedCurrency.setId(currency.getId());

        partialUpdatedCurrency
            .currencyNum(UPDATED_CURRENCY_NUM)
            .currencyName(UPDATED_CURRENCY_NAME)
            .currencyExchangeRateId(UPDATED_CURRENCY_EXCHANGE_RATE_ID);

        restCurrencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrency.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurrency))
            )
            .andExpect(status().isOk());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getCurrencyId()).isEqualTo(DEFAULT_CURRENCY_ID);
        assertThat(testCurrency.getCurrencyNum()).isEqualTo(UPDATED_CURRENCY_NUM);
        assertThat(testCurrency.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testCurrency.getCurrencyName()).isEqualTo(UPDATED_CURRENCY_NAME);
        assertThat(testCurrency.getCurrencyExchangeRateId()).isEqualTo(UPDATED_CURRENCY_EXCHANGE_RATE_ID);
        assertThat(testCurrency.getConversionRate()).isEqualByComparingTo(DEFAULT_CONVERSION_RATE);
    }

    @Test
    @Transactional
    void fullUpdateCurrencyWithPatch() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // Update the currency using partial update
        Currency partialUpdatedCurrency = new Currency();
        partialUpdatedCurrency.setId(currency.getId());

        partialUpdatedCurrency
            .currencyId(UPDATED_CURRENCY_ID)
            .currencyNum(UPDATED_CURRENCY_NUM)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .currencyName(UPDATED_CURRENCY_NAME)
            .currencyExchangeRateId(UPDATED_CURRENCY_EXCHANGE_RATE_ID)
            .conversionRate(UPDATED_CONVERSION_RATE);

        restCurrencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrency.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurrency))
            )
            .andExpect(status().isOk());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getCurrencyId()).isEqualTo(UPDATED_CURRENCY_ID);
        assertThat(testCurrency.getCurrencyNum()).isEqualTo(UPDATED_CURRENCY_NUM);
        assertThat(testCurrency.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testCurrency.getCurrencyName()).isEqualTo(UPDATED_CURRENCY_NAME);
        assertThat(testCurrency.getCurrencyExchangeRateId()).isEqualTo(UPDATED_CURRENCY_EXCHANGE_RATE_ID);
        assertThat(testCurrency.getConversionRate()).isEqualByComparingTo(UPDATED_CONVERSION_RATE);
    }

    @Test
    @Transactional
    void patchNonExistingCurrency() throws Exception {
        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();
        currency.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, currency.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currency))
            )
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCurrency() throws Exception {
        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();
        currency.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currency))
            )
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCurrency() throws Exception {
        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();
        currency.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrencyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        int databaseSizeBeforeDelete = currencyRepository.findAll().size();

        // Delete the currency
        restCurrencyMockMvc
            .perform(delete(ENTITY_API_URL_ID, currency.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
