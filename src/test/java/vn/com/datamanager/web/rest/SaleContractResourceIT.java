package vn.com.datamanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.com.datamanager.web.rest.TestUtil.sameNumber;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
import vn.com.datamanager.domain.SaleContract;
import vn.com.datamanager.repository.SaleContractRepository;
import vn.com.datamanager.service.criteria.SaleContractCriteria;

/**
 * Integration tests for the {@link SaleContractResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaleContractResourceIT {

    private static final String DEFAULT_CONTRACT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_ID = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CONTACT_SIGNED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONTACT_SIGNED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CONTACT_SIGNED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CONTACT_SIGNED_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_SIGNED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CONTRACT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONTRACT_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CONTRACT_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CONTRACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_NUMBER_INPUT = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_NUMBER_INPUT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_STAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_STAGE_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CONTRACT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONTRACT_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CONTRACT_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_OWNER_EMPLOYEE_ID = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_EMPLOYEE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_METHOD_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTRACT_TYPE_ID = 1L;
    private static final Long UPDATED_CONTRACT_TYPE_ID = 2L;
    private static final Long SMALLER_CONTRACT_TYPE_ID = 1L - 1L;

    private static final String DEFAULT_CURRENCY_ID = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_ID = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_GRAND_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_GRAND_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_GRAND_TOTAL = new BigDecimal(1 - 1);

    private static final String DEFAULT_PAYMENT_TERM_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TERM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_QUOTE_ID = "AAAAAAAAAA";
    private static final String UPDATED_QUOTE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY_EXCHANGE_RATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_EXCHANGE_RATE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_STAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_STAGE_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PAYMENT_STATUS_ID = 1L;
    private static final Long UPDATED_PAYMENT_STATUS_ID = 2L;
    private static final Long SMALLER_PAYMENT_STATUS_ID = 1L - 1L;

    private static final Integer DEFAULT_PERIOD = 1;
    private static final Integer UPDATED_PERIOD = 2;
    private static final Integer SMALLER_PERIOD = 1 - 1;

    private static final String DEFAULT_PAYMENT = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sale-contracts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SaleContractRepository saleContractRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaleContractMockMvc;

    private SaleContract saleContract;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaleContract createEntity(EntityManager em) {
        SaleContract saleContract = new SaleContract()
            .contractId(DEFAULT_CONTRACT_ID)
            .companyId(DEFAULT_COMPANY_ID)
            .accountId(DEFAULT_ACCOUNT_ID)
            .contactSignedDate(DEFAULT_CONTACT_SIGNED_DATE)
            .contactSignedTitle(DEFAULT_CONTACT_SIGNED_TITLE)
            .contractEndDate(DEFAULT_CONTRACT_END_DATE)
            .contractNumber(DEFAULT_CONTRACT_NUMBER)
            .contractNumberInput(DEFAULT_CONTRACT_NUMBER_INPUT)
            .contractStageId(DEFAULT_CONTRACT_STAGE_ID)
            .contractStartDate(DEFAULT_CONTRACT_START_DATE)
            .ownerEmployeeId(DEFAULT_OWNER_EMPLOYEE_ID)
            .paymentMethodId(DEFAULT_PAYMENT_METHOD_ID)
            .contractName(DEFAULT_CONTRACT_NAME)
            .contractTypeId(DEFAULT_CONTRACT_TYPE_ID)
            .currencyId(DEFAULT_CURRENCY_ID)
            .grandTotal(DEFAULT_GRAND_TOTAL)
            .paymentTermId(DEFAULT_PAYMENT_TERM_ID)
            .quoteId(DEFAULT_QUOTE_ID)
            .currencyExchangeRateId(DEFAULT_CURRENCY_EXCHANGE_RATE_ID)
            .contractStageName(DEFAULT_CONTRACT_STAGE_NAME)
            .paymentStatusId(DEFAULT_PAYMENT_STATUS_ID)
            .period(DEFAULT_PERIOD)
            .payment(DEFAULT_PAYMENT);
        return saleContract;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaleContract createUpdatedEntity(EntityManager em) {
        SaleContract saleContract = new SaleContract()
            .contractId(UPDATED_CONTRACT_ID)
            .companyId(UPDATED_COMPANY_ID)
            .accountId(UPDATED_ACCOUNT_ID)
            .contactSignedDate(UPDATED_CONTACT_SIGNED_DATE)
            .contactSignedTitle(UPDATED_CONTACT_SIGNED_TITLE)
            .contractEndDate(UPDATED_CONTRACT_END_DATE)
            .contractNumber(UPDATED_CONTRACT_NUMBER)
            .contractNumberInput(UPDATED_CONTRACT_NUMBER_INPUT)
            .contractStageId(UPDATED_CONTRACT_STAGE_ID)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .ownerEmployeeId(UPDATED_OWNER_EMPLOYEE_ID)
            .paymentMethodId(UPDATED_PAYMENT_METHOD_ID)
            .contractName(UPDATED_CONTRACT_NAME)
            .contractTypeId(UPDATED_CONTRACT_TYPE_ID)
            .currencyId(UPDATED_CURRENCY_ID)
            .grandTotal(UPDATED_GRAND_TOTAL)
            .paymentTermId(UPDATED_PAYMENT_TERM_ID)
            .quoteId(UPDATED_QUOTE_ID)
            .currencyExchangeRateId(UPDATED_CURRENCY_EXCHANGE_RATE_ID)
            .contractStageName(UPDATED_CONTRACT_STAGE_NAME)
            .paymentStatusId(UPDATED_PAYMENT_STATUS_ID)
            .period(UPDATED_PERIOD)
            .payment(UPDATED_PAYMENT);
        return saleContract;
    }

    @BeforeEach
    public void initTest() {
        saleContract = createEntity(em);
    }

    @Test
    @Transactional
    void createSaleContract() throws Exception {
        int databaseSizeBeforeCreate = saleContractRepository.findAll().size();
        // Create the SaleContract
        restSaleContractMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleContract)))
            .andExpect(status().isCreated());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeCreate + 1);
        SaleContract testSaleContract = saleContractList.get(saleContractList.size() - 1);
        assertThat(testSaleContract.getContractId()).isEqualTo(DEFAULT_CONTRACT_ID);
        assertThat(testSaleContract.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testSaleContract.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testSaleContract.getContactSignedDate()).isEqualTo(DEFAULT_CONTACT_SIGNED_DATE);
        assertThat(testSaleContract.getContactSignedTitle()).isEqualTo(DEFAULT_CONTACT_SIGNED_TITLE);
        assertThat(testSaleContract.getContractEndDate()).isEqualTo(DEFAULT_CONTRACT_END_DATE);
        assertThat(testSaleContract.getContractNumber()).isEqualTo(DEFAULT_CONTRACT_NUMBER);
        assertThat(testSaleContract.getContractNumberInput()).isEqualTo(DEFAULT_CONTRACT_NUMBER_INPUT);
        assertThat(testSaleContract.getContractStageId()).isEqualTo(DEFAULT_CONTRACT_STAGE_ID);
        assertThat(testSaleContract.getContractStartDate()).isEqualTo(DEFAULT_CONTRACT_START_DATE);
        assertThat(testSaleContract.getOwnerEmployeeId()).isEqualTo(DEFAULT_OWNER_EMPLOYEE_ID);
        assertThat(testSaleContract.getPaymentMethodId()).isEqualTo(DEFAULT_PAYMENT_METHOD_ID);
        assertThat(testSaleContract.getContractName()).isEqualTo(DEFAULT_CONTRACT_NAME);
        assertThat(testSaleContract.getContractTypeId()).isEqualTo(DEFAULT_CONTRACT_TYPE_ID);
        assertThat(testSaleContract.getCurrencyId()).isEqualTo(DEFAULT_CURRENCY_ID);
        assertThat(testSaleContract.getGrandTotal()).isEqualByComparingTo(DEFAULT_GRAND_TOTAL);
        assertThat(testSaleContract.getPaymentTermId()).isEqualTo(DEFAULT_PAYMENT_TERM_ID);
        assertThat(testSaleContract.getQuoteId()).isEqualTo(DEFAULT_QUOTE_ID);
        assertThat(testSaleContract.getCurrencyExchangeRateId()).isEqualTo(DEFAULT_CURRENCY_EXCHANGE_RATE_ID);
        assertThat(testSaleContract.getContractStageName()).isEqualTo(DEFAULT_CONTRACT_STAGE_NAME);
        assertThat(testSaleContract.getPaymentStatusId()).isEqualTo(DEFAULT_PAYMENT_STATUS_ID);
        assertThat(testSaleContract.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testSaleContract.getPayment()).isEqualTo(DEFAULT_PAYMENT);
    }

    @Test
    @Transactional
    void createSaleContractWithExistingId() throws Exception {
        // Create the SaleContract with an existing ID
        saleContract.setId(1L);

        int databaseSizeBeforeCreate = saleContractRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleContractMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleContract)))
            .andExpect(status().isBadRequest());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSaleContracts() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList
        restSaleContractMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleContract.getId().intValue())))
            .andExpect(jsonPath("$.[*].contractId").value(hasItem(DEFAULT_CONTRACT_ID)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID)))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].contactSignedDate").value(hasItem(DEFAULT_CONTACT_SIGNED_DATE.toString())))
            .andExpect(jsonPath("$.[*].contactSignedTitle").value(hasItem(DEFAULT_CONTACT_SIGNED_TITLE)))
            .andExpect(jsonPath("$.[*].contractEndDate").value(hasItem(DEFAULT_CONTRACT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].contractNumber").value(hasItem(DEFAULT_CONTRACT_NUMBER)))
            .andExpect(jsonPath("$.[*].contractNumberInput").value(hasItem(DEFAULT_CONTRACT_NUMBER_INPUT)))
            .andExpect(jsonPath("$.[*].contractStageId").value(hasItem(DEFAULT_CONTRACT_STAGE_ID)))
            .andExpect(jsonPath("$.[*].contractStartDate").value(hasItem(DEFAULT_CONTRACT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].ownerEmployeeId").value(hasItem(DEFAULT_OWNER_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].paymentMethodId").value(hasItem(DEFAULT_PAYMENT_METHOD_ID)))
            .andExpect(jsonPath("$.[*].contractName").value(hasItem(DEFAULT_CONTRACT_NAME)))
            .andExpect(jsonPath("$.[*].contractTypeId").value(hasItem(DEFAULT_CONTRACT_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].currencyId").value(hasItem(DEFAULT_CURRENCY_ID)))
            .andExpect(jsonPath("$.[*].grandTotal").value(hasItem(sameNumber(DEFAULT_GRAND_TOTAL))))
            .andExpect(jsonPath("$.[*].paymentTermId").value(hasItem(DEFAULT_PAYMENT_TERM_ID)))
            .andExpect(jsonPath("$.[*].quoteId").value(hasItem(DEFAULT_QUOTE_ID)))
            .andExpect(jsonPath("$.[*].currencyExchangeRateId").value(hasItem(DEFAULT_CURRENCY_EXCHANGE_RATE_ID)))
            .andExpect(jsonPath("$.[*].contractStageName").value(hasItem(DEFAULT_CONTRACT_STAGE_NAME)))
            .andExpect(jsonPath("$.[*].paymentStatusId").value(hasItem(DEFAULT_PAYMENT_STATUS_ID.intValue())))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD)))
            .andExpect(jsonPath("$.[*].payment").value(hasItem(DEFAULT_PAYMENT)));
    }

    @Test
    @Transactional
    void getSaleContract() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get the saleContract
        restSaleContractMockMvc
            .perform(get(ENTITY_API_URL_ID, saleContract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(saleContract.getId().intValue()))
            .andExpect(jsonPath("$.contractId").value(DEFAULT_CONTRACT_ID))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.contactSignedDate").value(DEFAULT_CONTACT_SIGNED_DATE.toString()))
            .andExpect(jsonPath("$.contactSignedTitle").value(DEFAULT_CONTACT_SIGNED_TITLE))
            .andExpect(jsonPath("$.contractEndDate").value(DEFAULT_CONTRACT_END_DATE.toString()))
            .andExpect(jsonPath("$.contractNumber").value(DEFAULT_CONTRACT_NUMBER))
            .andExpect(jsonPath("$.contractNumberInput").value(DEFAULT_CONTRACT_NUMBER_INPUT))
            .andExpect(jsonPath("$.contractStageId").value(DEFAULT_CONTRACT_STAGE_ID))
            .andExpect(jsonPath("$.contractStartDate").value(DEFAULT_CONTRACT_START_DATE.toString()))
            .andExpect(jsonPath("$.ownerEmployeeId").value(DEFAULT_OWNER_EMPLOYEE_ID))
            .andExpect(jsonPath("$.paymentMethodId").value(DEFAULT_PAYMENT_METHOD_ID))
            .andExpect(jsonPath("$.contractName").value(DEFAULT_CONTRACT_NAME))
            .andExpect(jsonPath("$.contractTypeId").value(DEFAULT_CONTRACT_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.currencyId").value(DEFAULT_CURRENCY_ID))
            .andExpect(jsonPath("$.grandTotal").value(sameNumber(DEFAULT_GRAND_TOTAL)))
            .andExpect(jsonPath("$.paymentTermId").value(DEFAULT_PAYMENT_TERM_ID))
            .andExpect(jsonPath("$.quoteId").value(DEFAULT_QUOTE_ID))
            .andExpect(jsonPath("$.currencyExchangeRateId").value(DEFAULT_CURRENCY_EXCHANGE_RATE_ID))
            .andExpect(jsonPath("$.contractStageName").value(DEFAULT_CONTRACT_STAGE_NAME))
            .andExpect(jsonPath("$.paymentStatusId").value(DEFAULT_PAYMENT_STATUS_ID.intValue()))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD))
            .andExpect(jsonPath("$.payment").value(DEFAULT_PAYMENT));
    }

    @Test
    @Transactional
    void getSaleContractsByIdFiltering() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        Long id = saleContract.getId();

        defaultSaleContractShouldBeFound("id.equals=" + id);
        defaultSaleContractShouldNotBeFound("id.notEquals=" + id);

        defaultSaleContractShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSaleContractShouldNotBeFound("id.greaterThan=" + id);

        defaultSaleContractShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSaleContractShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractId equals to DEFAULT_CONTRACT_ID
        defaultSaleContractShouldBeFound("contractId.equals=" + DEFAULT_CONTRACT_ID);

        // Get all the saleContractList where contractId equals to UPDATED_CONTRACT_ID
        defaultSaleContractShouldNotBeFound("contractId.equals=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractId not equals to DEFAULT_CONTRACT_ID
        defaultSaleContractShouldNotBeFound("contractId.notEquals=" + DEFAULT_CONTRACT_ID);

        // Get all the saleContractList where contractId not equals to UPDATED_CONTRACT_ID
        defaultSaleContractShouldBeFound("contractId.notEquals=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractId in DEFAULT_CONTRACT_ID or UPDATED_CONTRACT_ID
        defaultSaleContractShouldBeFound("contractId.in=" + DEFAULT_CONTRACT_ID + "," + UPDATED_CONTRACT_ID);

        // Get all the saleContractList where contractId equals to UPDATED_CONTRACT_ID
        defaultSaleContractShouldNotBeFound("contractId.in=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractId is not null
        defaultSaleContractShouldBeFound("contractId.specified=true");

        // Get all the saleContractList where contractId is null
        defaultSaleContractShouldNotBeFound("contractId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractIdContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractId contains DEFAULT_CONTRACT_ID
        defaultSaleContractShouldBeFound("contractId.contains=" + DEFAULT_CONTRACT_ID);

        // Get all the saleContractList where contractId contains UPDATED_CONTRACT_ID
        defaultSaleContractShouldNotBeFound("contractId.contains=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractIdNotContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractId does not contain DEFAULT_CONTRACT_ID
        defaultSaleContractShouldNotBeFound("contractId.doesNotContain=" + DEFAULT_CONTRACT_ID);

        // Get all the saleContractList where contractId does not contain UPDATED_CONTRACT_ID
        defaultSaleContractShouldBeFound("contractId.doesNotContain=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where companyId equals to DEFAULT_COMPANY_ID
        defaultSaleContractShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the saleContractList where companyId equals to UPDATED_COMPANY_ID
        defaultSaleContractShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByCompanyIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where companyId not equals to DEFAULT_COMPANY_ID
        defaultSaleContractShouldNotBeFound("companyId.notEquals=" + DEFAULT_COMPANY_ID);

        // Get all the saleContractList where companyId not equals to UPDATED_COMPANY_ID
        defaultSaleContractShouldBeFound("companyId.notEquals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultSaleContractShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the saleContractList where companyId equals to UPDATED_COMPANY_ID
        defaultSaleContractShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where companyId is not null
        defaultSaleContractShouldBeFound("companyId.specified=true");

        // Get all the saleContractList where companyId is null
        defaultSaleContractShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByCompanyIdContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where companyId contains DEFAULT_COMPANY_ID
        defaultSaleContractShouldBeFound("companyId.contains=" + DEFAULT_COMPANY_ID);

        // Get all the saleContractList where companyId contains UPDATED_COMPANY_ID
        defaultSaleContractShouldNotBeFound("companyId.contains=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByCompanyIdNotContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where companyId does not contain DEFAULT_COMPANY_ID
        defaultSaleContractShouldNotBeFound("companyId.doesNotContain=" + DEFAULT_COMPANY_ID);

        // Get all the saleContractList where companyId does not contain UPDATED_COMPANY_ID
        defaultSaleContractShouldBeFound("companyId.doesNotContain=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByAccountIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where accountId equals to DEFAULT_ACCOUNT_ID
        defaultSaleContractShouldBeFound("accountId.equals=" + DEFAULT_ACCOUNT_ID);

        // Get all the saleContractList where accountId equals to UPDATED_ACCOUNT_ID
        defaultSaleContractShouldNotBeFound("accountId.equals=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByAccountIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where accountId not equals to DEFAULT_ACCOUNT_ID
        defaultSaleContractShouldNotBeFound("accountId.notEquals=" + DEFAULT_ACCOUNT_ID);

        // Get all the saleContractList where accountId not equals to UPDATED_ACCOUNT_ID
        defaultSaleContractShouldBeFound("accountId.notEquals=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByAccountIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where accountId in DEFAULT_ACCOUNT_ID or UPDATED_ACCOUNT_ID
        defaultSaleContractShouldBeFound("accountId.in=" + DEFAULT_ACCOUNT_ID + "," + UPDATED_ACCOUNT_ID);

        // Get all the saleContractList where accountId equals to UPDATED_ACCOUNT_ID
        defaultSaleContractShouldNotBeFound("accountId.in=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByAccountIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where accountId is not null
        defaultSaleContractShouldBeFound("accountId.specified=true");

        // Get all the saleContractList where accountId is null
        defaultSaleContractShouldNotBeFound("accountId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByAccountIdContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where accountId contains DEFAULT_ACCOUNT_ID
        defaultSaleContractShouldBeFound("accountId.contains=" + DEFAULT_ACCOUNT_ID);

        // Get all the saleContractList where accountId contains UPDATED_ACCOUNT_ID
        defaultSaleContractShouldNotBeFound("accountId.contains=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByAccountIdNotContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where accountId does not contain DEFAULT_ACCOUNT_ID
        defaultSaleContractShouldNotBeFound("accountId.doesNotContain=" + DEFAULT_ACCOUNT_ID);

        // Get all the saleContractList where accountId does not contain UPDATED_ACCOUNT_ID
        defaultSaleContractShouldBeFound("accountId.doesNotContain=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContactSignedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contactSignedDate equals to DEFAULT_CONTACT_SIGNED_DATE
        defaultSaleContractShouldBeFound("contactSignedDate.equals=" + DEFAULT_CONTACT_SIGNED_DATE);

        // Get all the saleContractList where contactSignedDate equals to UPDATED_CONTACT_SIGNED_DATE
        defaultSaleContractShouldNotBeFound("contactSignedDate.equals=" + UPDATED_CONTACT_SIGNED_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContactSignedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contactSignedDate not equals to DEFAULT_CONTACT_SIGNED_DATE
        defaultSaleContractShouldNotBeFound("contactSignedDate.notEquals=" + DEFAULT_CONTACT_SIGNED_DATE);

        // Get all the saleContractList where contactSignedDate not equals to UPDATED_CONTACT_SIGNED_DATE
        defaultSaleContractShouldBeFound("contactSignedDate.notEquals=" + UPDATED_CONTACT_SIGNED_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContactSignedDateIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contactSignedDate in DEFAULT_CONTACT_SIGNED_DATE or UPDATED_CONTACT_SIGNED_DATE
        defaultSaleContractShouldBeFound("contactSignedDate.in=" + DEFAULT_CONTACT_SIGNED_DATE + "," + UPDATED_CONTACT_SIGNED_DATE);

        // Get all the saleContractList where contactSignedDate equals to UPDATED_CONTACT_SIGNED_DATE
        defaultSaleContractShouldNotBeFound("contactSignedDate.in=" + UPDATED_CONTACT_SIGNED_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContactSignedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contactSignedDate is not null
        defaultSaleContractShouldBeFound("contactSignedDate.specified=true");

        // Get all the saleContractList where contactSignedDate is null
        defaultSaleContractShouldNotBeFound("contactSignedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByContactSignedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contactSignedDate is greater than or equal to DEFAULT_CONTACT_SIGNED_DATE
        defaultSaleContractShouldBeFound("contactSignedDate.greaterThanOrEqual=" + DEFAULT_CONTACT_SIGNED_DATE);

        // Get all the saleContractList where contactSignedDate is greater than or equal to UPDATED_CONTACT_SIGNED_DATE
        defaultSaleContractShouldNotBeFound("contactSignedDate.greaterThanOrEqual=" + UPDATED_CONTACT_SIGNED_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContactSignedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contactSignedDate is less than or equal to DEFAULT_CONTACT_SIGNED_DATE
        defaultSaleContractShouldBeFound("contactSignedDate.lessThanOrEqual=" + DEFAULT_CONTACT_SIGNED_DATE);

        // Get all the saleContractList where contactSignedDate is less than or equal to SMALLER_CONTACT_SIGNED_DATE
        defaultSaleContractShouldNotBeFound("contactSignedDate.lessThanOrEqual=" + SMALLER_CONTACT_SIGNED_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContactSignedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contactSignedDate is less than DEFAULT_CONTACT_SIGNED_DATE
        defaultSaleContractShouldNotBeFound("contactSignedDate.lessThan=" + DEFAULT_CONTACT_SIGNED_DATE);

        // Get all the saleContractList where contactSignedDate is less than UPDATED_CONTACT_SIGNED_DATE
        defaultSaleContractShouldBeFound("contactSignedDate.lessThan=" + UPDATED_CONTACT_SIGNED_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContactSignedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contactSignedDate is greater than DEFAULT_CONTACT_SIGNED_DATE
        defaultSaleContractShouldNotBeFound("contactSignedDate.greaterThan=" + DEFAULT_CONTACT_SIGNED_DATE);

        // Get all the saleContractList where contactSignedDate is greater than SMALLER_CONTACT_SIGNED_DATE
        defaultSaleContractShouldBeFound("contactSignedDate.greaterThan=" + SMALLER_CONTACT_SIGNED_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContactSignedTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contactSignedTitle equals to DEFAULT_CONTACT_SIGNED_TITLE
        defaultSaleContractShouldBeFound("contactSignedTitle.equals=" + DEFAULT_CONTACT_SIGNED_TITLE);

        // Get all the saleContractList where contactSignedTitle equals to UPDATED_CONTACT_SIGNED_TITLE
        defaultSaleContractShouldNotBeFound("contactSignedTitle.equals=" + UPDATED_CONTACT_SIGNED_TITLE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContactSignedTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contactSignedTitle not equals to DEFAULT_CONTACT_SIGNED_TITLE
        defaultSaleContractShouldNotBeFound("contactSignedTitle.notEquals=" + DEFAULT_CONTACT_SIGNED_TITLE);

        // Get all the saleContractList where contactSignedTitle not equals to UPDATED_CONTACT_SIGNED_TITLE
        defaultSaleContractShouldBeFound("contactSignedTitle.notEquals=" + UPDATED_CONTACT_SIGNED_TITLE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContactSignedTitleIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contactSignedTitle in DEFAULT_CONTACT_SIGNED_TITLE or UPDATED_CONTACT_SIGNED_TITLE
        defaultSaleContractShouldBeFound("contactSignedTitle.in=" + DEFAULT_CONTACT_SIGNED_TITLE + "," + UPDATED_CONTACT_SIGNED_TITLE);

        // Get all the saleContractList where contactSignedTitle equals to UPDATED_CONTACT_SIGNED_TITLE
        defaultSaleContractShouldNotBeFound("contactSignedTitle.in=" + UPDATED_CONTACT_SIGNED_TITLE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContactSignedTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contactSignedTitle is not null
        defaultSaleContractShouldBeFound("contactSignedTitle.specified=true");

        // Get all the saleContractList where contactSignedTitle is null
        defaultSaleContractShouldNotBeFound("contactSignedTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByContactSignedTitleContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contactSignedTitle contains DEFAULT_CONTACT_SIGNED_TITLE
        defaultSaleContractShouldBeFound("contactSignedTitle.contains=" + DEFAULT_CONTACT_SIGNED_TITLE);

        // Get all the saleContractList where contactSignedTitle contains UPDATED_CONTACT_SIGNED_TITLE
        defaultSaleContractShouldNotBeFound("contactSignedTitle.contains=" + UPDATED_CONTACT_SIGNED_TITLE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContactSignedTitleNotContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contactSignedTitle does not contain DEFAULT_CONTACT_SIGNED_TITLE
        defaultSaleContractShouldNotBeFound("contactSignedTitle.doesNotContain=" + DEFAULT_CONTACT_SIGNED_TITLE);

        // Get all the saleContractList where contactSignedTitle does not contain UPDATED_CONTACT_SIGNED_TITLE
        defaultSaleContractShouldBeFound("contactSignedTitle.doesNotContain=" + UPDATED_CONTACT_SIGNED_TITLE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractEndDate equals to DEFAULT_CONTRACT_END_DATE
        defaultSaleContractShouldBeFound("contractEndDate.equals=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the saleContractList where contractEndDate equals to UPDATED_CONTRACT_END_DATE
        defaultSaleContractShouldNotBeFound("contractEndDate.equals=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractEndDate not equals to DEFAULT_CONTRACT_END_DATE
        defaultSaleContractShouldNotBeFound("contractEndDate.notEquals=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the saleContractList where contractEndDate not equals to UPDATED_CONTRACT_END_DATE
        defaultSaleContractShouldBeFound("contractEndDate.notEquals=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractEndDate in DEFAULT_CONTRACT_END_DATE or UPDATED_CONTRACT_END_DATE
        defaultSaleContractShouldBeFound("contractEndDate.in=" + DEFAULT_CONTRACT_END_DATE + "," + UPDATED_CONTRACT_END_DATE);

        // Get all the saleContractList where contractEndDate equals to UPDATED_CONTRACT_END_DATE
        defaultSaleContractShouldNotBeFound("contractEndDate.in=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractEndDate is not null
        defaultSaleContractShouldBeFound("contractEndDate.specified=true");

        // Get all the saleContractList where contractEndDate is null
        defaultSaleContractShouldNotBeFound("contractEndDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractEndDate is greater than or equal to DEFAULT_CONTRACT_END_DATE
        defaultSaleContractShouldBeFound("contractEndDate.greaterThanOrEqual=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the saleContractList where contractEndDate is greater than or equal to UPDATED_CONTRACT_END_DATE
        defaultSaleContractShouldNotBeFound("contractEndDate.greaterThanOrEqual=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractEndDate is less than or equal to DEFAULT_CONTRACT_END_DATE
        defaultSaleContractShouldBeFound("contractEndDate.lessThanOrEqual=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the saleContractList where contractEndDate is less than or equal to SMALLER_CONTRACT_END_DATE
        defaultSaleContractShouldNotBeFound("contractEndDate.lessThanOrEqual=" + SMALLER_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractEndDate is less than DEFAULT_CONTRACT_END_DATE
        defaultSaleContractShouldNotBeFound("contractEndDate.lessThan=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the saleContractList where contractEndDate is less than UPDATED_CONTRACT_END_DATE
        defaultSaleContractShouldBeFound("contractEndDate.lessThan=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractEndDate is greater than DEFAULT_CONTRACT_END_DATE
        defaultSaleContractShouldNotBeFound("contractEndDate.greaterThan=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the saleContractList where contractEndDate is greater than SMALLER_CONTRACT_END_DATE
        defaultSaleContractShouldBeFound("contractEndDate.greaterThan=" + SMALLER_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractNumber equals to DEFAULT_CONTRACT_NUMBER
        defaultSaleContractShouldBeFound("contractNumber.equals=" + DEFAULT_CONTRACT_NUMBER);

        // Get all the saleContractList where contractNumber equals to UPDATED_CONTRACT_NUMBER
        defaultSaleContractShouldNotBeFound("contractNumber.equals=" + UPDATED_CONTRACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractNumber not equals to DEFAULT_CONTRACT_NUMBER
        defaultSaleContractShouldNotBeFound("contractNumber.notEquals=" + DEFAULT_CONTRACT_NUMBER);

        // Get all the saleContractList where contractNumber not equals to UPDATED_CONTRACT_NUMBER
        defaultSaleContractShouldBeFound("contractNumber.notEquals=" + UPDATED_CONTRACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNumberIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractNumber in DEFAULT_CONTRACT_NUMBER or UPDATED_CONTRACT_NUMBER
        defaultSaleContractShouldBeFound("contractNumber.in=" + DEFAULT_CONTRACT_NUMBER + "," + UPDATED_CONTRACT_NUMBER);

        // Get all the saleContractList where contractNumber equals to UPDATED_CONTRACT_NUMBER
        defaultSaleContractShouldNotBeFound("contractNumber.in=" + UPDATED_CONTRACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractNumber is not null
        defaultSaleContractShouldBeFound("contractNumber.specified=true");

        // Get all the saleContractList where contractNumber is null
        defaultSaleContractShouldNotBeFound("contractNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNumberContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractNumber contains DEFAULT_CONTRACT_NUMBER
        defaultSaleContractShouldBeFound("contractNumber.contains=" + DEFAULT_CONTRACT_NUMBER);

        // Get all the saleContractList where contractNumber contains UPDATED_CONTRACT_NUMBER
        defaultSaleContractShouldNotBeFound("contractNumber.contains=" + UPDATED_CONTRACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNumberNotContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractNumber does not contain DEFAULT_CONTRACT_NUMBER
        defaultSaleContractShouldNotBeFound("contractNumber.doesNotContain=" + DEFAULT_CONTRACT_NUMBER);

        // Get all the saleContractList where contractNumber does not contain UPDATED_CONTRACT_NUMBER
        defaultSaleContractShouldBeFound("contractNumber.doesNotContain=" + UPDATED_CONTRACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNumberInputIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractNumberInput equals to DEFAULT_CONTRACT_NUMBER_INPUT
        defaultSaleContractShouldBeFound("contractNumberInput.equals=" + DEFAULT_CONTRACT_NUMBER_INPUT);

        // Get all the saleContractList where contractNumberInput equals to UPDATED_CONTRACT_NUMBER_INPUT
        defaultSaleContractShouldNotBeFound("contractNumberInput.equals=" + UPDATED_CONTRACT_NUMBER_INPUT);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNumberInputIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractNumberInput not equals to DEFAULT_CONTRACT_NUMBER_INPUT
        defaultSaleContractShouldNotBeFound("contractNumberInput.notEquals=" + DEFAULT_CONTRACT_NUMBER_INPUT);

        // Get all the saleContractList where contractNumberInput not equals to UPDATED_CONTRACT_NUMBER_INPUT
        defaultSaleContractShouldBeFound("contractNumberInput.notEquals=" + UPDATED_CONTRACT_NUMBER_INPUT);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNumberInputIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractNumberInput in DEFAULT_CONTRACT_NUMBER_INPUT or UPDATED_CONTRACT_NUMBER_INPUT
        defaultSaleContractShouldBeFound("contractNumberInput.in=" + DEFAULT_CONTRACT_NUMBER_INPUT + "," + UPDATED_CONTRACT_NUMBER_INPUT);

        // Get all the saleContractList where contractNumberInput equals to UPDATED_CONTRACT_NUMBER_INPUT
        defaultSaleContractShouldNotBeFound("contractNumberInput.in=" + UPDATED_CONTRACT_NUMBER_INPUT);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNumberInputIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractNumberInput is not null
        defaultSaleContractShouldBeFound("contractNumberInput.specified=true");

        // Get all the saleContractList where contractNumberInput is null
        defaultSaleContractShouldNotBeFound("contractNumberInput.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNumberInputContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractNumberInput contains DEFAULT_CONTRACT_NUMBER_INPUT
        defaultSaleContractShouldBeFound("contractNumberInput.contains=" + DEFAULT_CONTRACT_NUMBER_INPUT);

        // Get all the saleContractList where contractNumberInput contains UPDATED_CONTRACT_NUMBER_INPUT
        defaultSaleContractShouldNotBeFound("contractNumberInput.contains=" + UPDATED_CONTRACT_NUMBER_INPUT);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNumberInputNotContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractNumberInput does not contain DEFAULT_CONTRACT_NUMBER_INPUT
        defaultSaleContractShouldNotBeFound("contractNumberInput.doesNotContain=" + DEFAULT_CONTRACT_NUMBER_INPUT);

        // Get all the saleContractList where contractNumberInput does not contain UPDATED_CONTRACT_NUMBER_INPUT
        defaultSaleContractShouldBeFound("contractNumberInput.doesNotContain=" + UPDATED_CONTRACT_NUMBER_INPUT);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStageIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStageId equals to DEFAULT_CONTRACT_STAGE_ID
        defaultSaleContractShouldBeFound("contractStageId.equals=" + DEFAULT_CONTRACT_STAGE_ID);

        // Get all the saleContractList where contractStageId equals to UPDATED_CONTRACT_STAGE_ID
        defaultSaleContractShouldNotBeFound("contractStageId.equals=" + UPDATED_CONTRACT_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStageIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStageId not equals to DEFAULT_CONTRACT_STAGE_ID
        defaultSaleContractShouldNotBeFound("contractStageId.notEquals=" + DEFAULT_CONTRACT_STAGE_ID);

        // Get all the saleContractList where contractStageId not equals to UPDATED_CONTRACT_STAGE_ID
        defaultSaleContractShouldBeFound("contractStageId.notEquals=" + UPDATED_CONTRACT_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStageIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStageId in DEFAULT_CONTRACT_STAGE_ID or UPDATED_CONTRACT_STAGE_ID
        defaultSaleContractShouldBeFound("contractStageId.in=" + DEFAULT_CONTRACT_STAGE_ID + "," + UPDATED_CONTRACT_STAGE_ID);

        // Get all the saleContractList where contractStageId equals to UPDATED_CONTRACT_STAGE_ID
        defaultSaleContractShouldNotBeFound("contractStageId.in=" + UPDATED_CONTRACT_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStageIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStageId is not null
        defaultSaleContractShouldBeFound("contractStageId.specified=true");

        // Get all the saleContractList where contractStageId is null
        defaultSaleContractShouldNotBeFound("contractStageId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStageIdContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStageId contains DEFAULT_CONTRACT_STAGE_ID
        defaultSaleContractShouldBeFound("contractStageId.contains=" + DEFAULT_CONTRACT_STAGE_ID);

        // Get all the saleContractList where contractStageId contains UPDATED_CONTRACT_STAGE_ID
        defaultSaleContractShouldNotBeFound("contractStageId.contains=" + UPDATED_CONTRACT_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStageIdNotContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStageId does not contain DEFAULT_CONTRACT_STAGE_ID
        defaultSaleContractShouldNotBeFound("contractStageId.doesNotContain=" + DEFAULT_CONTRACT_STAGE_ID);

        // Get all the saleContractList where contractStageId does not contain UPDATED_CONTRACT_STAGE_ID
        defaultSaleContractShouldBeFound("contractStageId.doesNotContain=" + UPDATED_CONTRACT_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStartDate equals to DEFAULT_CONTRACT_START_DATE
        defaultSaleContractShouldBeFound("contractStartDate.equals=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the saleContractList where contractStartDate equals to UPDATED_CONTRACT_START_DATE
        defaultSaleContractShouldNotBeFound("contractStartDate.equals=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStartDate not equals to DEFAULT_CONTRACT_START_DATE
        defaultSaleContractShouldNotBeFound("contractStartDate.notEquals=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the saleContractList where contractStartDate not equals to UPDATED_CONTRACT_START_DATE
        defaultSaleContractShouldBeFound("contractStartDate.notEquals=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStartDate in DEFAULT_CONTRACT_START_DATE or UPDATED_CONTRACT_START_DATE
        defaultSaleContractShouldBeFound("contractStartDate.in=" + DEFAULT_CONTRACT_START_DATE + "," + UPDATED_CONTRACT_START_DATE);

        // Get all the saleContractList where contractStartDate equals to UPDATED_CONTRACT_START_DATE
        defaultSaleContractShouldNotBeFound("contractStartDate.in=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStartDate is not null
        defaultSaleContractShouldBeFound("contractStartDate.specified=true");

        // Get all the saleContractList where contractStartDate is null
        defaultSaleContractShouldNotBeFound("contractStartDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStartDate is greater than or equal to DEFAULT_CONTRACT_START_DATE
        defaultSaleContractShouldBeFound("contractStartDate.greaterThanOrEqual=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the saleContractList where contractStartDate is greater than or equal to UPDATED_CONTRACT_START_DATE
        defaultSaleContractShouldNotBeFound("contractStartDate.greaterThanOrEqual=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStartDate is less than or equal to DEFAULT_CONTRACT_START_DATE
        defaultSaleContractShouldBeFound("contractStartDate.lessThanOrEqual=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the saleContractList where contractStartDate is less than or equal to SMALLER_CONTRACT_START_DATE
        defaultSaleContractShouldNotBeFound("contractStartDate.lessThanOrEqual=" + SMALLER_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStartDate is less than DEFAULT_CONTRACT_START_DATE
        defaultSaleContractShouldNotBeFound("contractStartDate.lessThan=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the saleContractList where contractStartDate is less than UPDATED_CONTRACT_START_DATE
        defaultSaleContractShouldBeFound("contractStartDate.lessThan=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStartDate is greater than DEFAULT_CONTRACT_START_DATE
        defaultSaleContractShouldNotBeFound("contractStartDate.greaterThan=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the saleContractList where contractStartDate is greater than SMALLER_CONTRACT_START_DATE
        defaultSaleContractShouldBeFound("contractStartDate.greaterThan=" + SMALLER_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    void getAllSaleContractsByOwnerEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where ownerEmployeeId equals to DEFAULT_OWNER_EMPLOYEE_ID
        defaultSaleContractShouldBeFound("ownerEmployeeId.equals=" + DEFAULT_OWNER_EMPLOYEE_ID);

        // Get all the saleContractList where ownerEmployeeId equals to UPDATED_OWNER_EMPLOYEE_ID
        defaultSaleContractShouldNotBeFound("ownerEmployeeId.equals=" + UPDATED_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByOwnerEmployeeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where ownerEmployeeId not equals to DEFAULT_OWNER_EMPLOYEE_ID
        defaultSaleContractShouldNotBeFound("ownerEmployeeId.notEquals=" + DEFAULT_OWNER_EMPLOYEE_ID);

        // Get all the saleContractList where ownerEmployeeId not equals to UPDATED_OWNER_EMPLOYEE_ID
        defaultSaleContractShouldBeFound("ownerEmployeeId.notEquals=" + UPDATED_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByOwnerEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where ownerEmployeeId in DEFAULT_OWNER_EMPLOYEE_ID or UPDATED_OWNER_EMPLOYEE_ID
        defaultSaleContractShouldBeFound("ownerEmployeeId.in=" + DEFAULT_OWNER_EMPLOYEE_ID + "," + UPDATED_OWNER_EMPLOYEE_ID);

        // Get all the saleContractList where ownerEmployeeId equals to UPDATED_OWNER_EMPLOYEE_ID
        defaultSaleContractShouldNotBeFound("ownerEmployeeId.in=" + UPDATED_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByOwnerEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where ownerEmployeeId is not null
        defaultSaleContractShouldBeFound("ownerEmployeeId.specified=true");

        // Get all the saleContractList where ownerEmployeeId is null
        defaultSaleContractShouldNotBeFound("ownerEmployeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByOwnerEmployeeIdContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where ownerEmployeeId contains DEFAULT_OWNER_EMPLOYEE_ID
        defaultSaleContractShouldBeFound("ownerEmployeeId.contains=" + DEFAULT_OWNER_EMPLOYEE_ID);

        // Get all the saleContractList where ownerEmployeeId contains UPDATED_OWNER_EMPLOYEE_ID
        defaultSaleContractShouldNotBeFound("ownerEmployeeId.contains=" + UPDATED_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByOwnerEmployeeIdNotContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where ownerEmployeeId does not contain DEFAULT_OWNER_EMPLOYEE_ID
        defaultSaleContractShouldNotBeFound("ownerEmployeeId.doesNotContain=" + DEFAULT_OWNER_EMPLOYEE_ID);

        // Get all the saleContractList where ownerEmployeeId does not contain UPDATED_OWNER_EMPLOYEE_ID
        defaultSaleContractShouldBeFound("ownerEmployeeId.doesNotContain=" + UPDATED_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentMethodIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentMethodId equals to DEFAULT_PAYMENT_METHOD_ID
        defaultSaleContractShouldBeFound("paymentMethodId.equals=" + DEFAULT_PAYMENT_METHOD_ID);

        // Get all the saleContractList where paymentMethodId equals to UPDATED_PAYMENT_METHOD_ID
        defaultSaleContractShouldNotBeFound("paymentMethodId.equals=" + UPDATED_PAYMENT_METHOD_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentMethodIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentMethodId not equals to DEFAULT_PAYMENT_METHOD_ID
        defaultSaleContractShouldNotBeFound("paymentMethodId.notEquals=" + DEFAULT_PAYMENT_METHOD_ID);

        // Get all the saleContractList where paymentMethodId not equals to UPDATED_PAYMENT_METHOD_ID
        defaultSaleContractShouldBeFound("paymentMethodId.notEquals=" + UPDATED_PAYMENT_METHOD_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentMethodIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentMethodId in DEFAULT_PAYMENT_METHOD_ID or UPDATED_PAYMENT_METHOD_ID
        defaultSaleContractShouldBeFound("paymentMethodId.in=" + DEFAULT_PAYMENT_METHOD_ID + "," + UPDATED_PAYMENT_METHOD_ID);

        // Get all the saleContractList where paymentMethodId equals to UPDATED_PAYMENT_METHOD_ID
        defaultSaleContractShouldNotBeFound("paymentMethodId.in=" + UPDATED_PAYMENT_METHOD_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentMethodIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentMethodId is not null
        defaultSaleContractShouldBeFound("paymentMethodId.specified=true");

        // Get all the saleContractList where paymentMethodId is null
        defaultSaleContractShouldNotBeFound("paymentMethodId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentMethodIdContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentMethodId contains DEFAULT_PAYMENT_METHOD_ID
        defaultSaleContractShouldBeFound("paymentMethodId.contains=" + DEFAULT_PAYMENT_METHOD_ID);

        // Get all the saleContractList where paymentMethodId contains UPDATED_PAYMENT_METHOD_ID
        defaultSaleContractShouldNotBeFound("paymentMethodId.contains=" + UPDATED_PAYMENT_METHOD_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentMethodIdNotContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentMethodId does not contain DEFAULT_PAYMENT_METHOD_ID
        defaultSaleContractShouldNotBeFound("paymentMethodId.doesNotContain=" + DEFAULT_PAYMENT_METHOD_ID);

        // Get all the saleContractList where paymentMethodId does not contain UPDATED_PAYMENT_METHOD_ID
        defaultSaleContractShouldBeFound("paymentMethodId.doesNotContain=" + UPDATED_PAYMENT_METHOD_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNameIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractName equals to DEFAULT_CONTRACT_NAME
        defaultSaleContractShouldBeFound("contractName.equals=" + DEFAULT_CONTRACT_NAME);

        // Get all the saleContractList where contractName equals to UPDATED_CONTRACT_NAME
        defaultSaleContractShouldNotBeFound("contractName.equals=" + UPDATED_CONTRACT_NAME);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractName not equals to DEFAULT_CONTRACT_NAME
        defaultSaleContractShouldNotBeFound("contractName.notEquals=" + DEFAULT_CONTRACT_NAME);

        // Get all the saleContractList where contractName not equals to UPDATED_CONTRACT_NAME
        defaultSaleContractShouldBeFound("contractName.notEquals=" + UPDATED_CONTRACT_NAME);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNameIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractName in DEFAULT_CONTRACT_NAME or UPDATED_CONTRACT_NAME
        defaultSaleContractShouldBeFound("contractName.in=" + DEFAULT_CONTRACT_NAME + "," + UPDATED_CONTRACT_NAME);

        // Get all the saleContractList where contractName equals to UPDATED_CONTRACT_NAME
        defaultSaleContractShouldNotBeFound("contractName.in=" + UPDATED_CONTRACT_NAME);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractName is not null
        defaultSaleContractShouldBeFound("contractName.specified=true");

        // Get all the saleContractList where contractName is null
        defaultSaleContractShouldNotBeFound("contractName.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNameContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractName contains DEFAULT_CONTRACT_NAME
        defaultSaleContractShouldBeFound("contractName.contains=" + DEFAULT_CONTRACT_NAME);

        // Get all the saleContractList where contractName contains UPDATED_CONTRACT_NAME
        defaultSaleContractShouldNotBeFound("contractName.contains=" + UPDATED_CONTRACT_NAME);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractNameNotContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractName does not contain DEFAULT_CONTRACT_NAME
        defaultSaleContractShouldNotBeFound("contractName.doesNotContain=" + DEFAULT_CONTRACT_NAME);

        // Get all the saleContractList where contractName does not contain UPDATED_CONTRACT_NAME
        defaultSaleContractShouldBeFound("contractName.doesNotContain=" + UPDATED_CONTRACT_NAME);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractTypeId equals to DEFAULT_CONTRACT_TYPE_ID
        defaultSaleContractShouldBeFound("contractTypeId.equals=" + DEFAULT_CONTRACT_TYPE_ID);

        // Get all the saleContractList where contractTypeId equals to UPDATED_CONTRACT_TYPE_ID
        defaultSaleContractShouldNotBeFound("contractTypeId.equals=" + UPDATED_CONTRACT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractTypeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractTypeId not equals to DEFAULT_CONTRACT_TYPE_ID
        defaultSaleContractShouldNotBeFound("contractTypeId.notEquals=" + DEFAULT_CONTRACT_TYPE_ID);

        // Get all the saleContractList where contractTypeId not equals to UPDATED_CONTRACT_TYPE_ID
        defaultSaleContractShouldBeFound("contractTypeId.notEquals=" + UPDATED_CONTRACT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractTypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractTypeId in DEFAULT_CONTRACT_TYPE_ID or UPDATED_CONTRACT_TYPE_ID
        defaultSaleContractShouldBeFound("contractTypeId.in=" + DEFAULT_CONTRACT_TYPE_ID + "," + UPDATED_CONTRACT_TYPE_ID);

        // Get all the saleContractList where contractTypeId equals to UPDATED_CONTRACT_TYPE_ID
        defaultSaleContractShouldNotBeFound("contractTypeId.in=" + UPDATED_CONTRACT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractTypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractTypeId is not null
        defaultSaleContractShouldBeFound("contractTypeId.specified=true");

        // Get all the saleContractList where contractTypeId is null
        defaultSaleContractShouldNotBeFound("contractTypeId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractTypeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractTypeId is greater than or equal to DEFAULT_CONTRACT_TYPE_ID
        defaultSaleContractShouldBeFound("contractTypeId.greaterThanOrEqual=" + DEFAULT_CONTRACT_TYPE_ID);

        // Get all the saleContractList where contractTypeId is greater than or equal to UPDATED_CONTRACT_TYPE_ID
        defaultSaleContractShouldNotBeFound("contractTypeId.greaterThanOrEqual=" + UPDATED_CONTRACT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractTypeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractTypeId is less than or equal to DEFAULT_CONTRACT_TYPE_ID
        defaultSaleContractShouldBeFound("contractTypeId.lessThanOrEqual=" + DEFAULT_CONTRACT_TYPE_ID);

        // Get all the saleContractList where contractTypeId is less than or equal to SMALLER_CONTRACT_TYPE_ID
        defaultSaleContractShouldNotBeFound("contractTypeId.lessThanOrEqual=" + SMALLER_CONTRACT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractTypeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractTypeId is less than DEFAULT_CONTRACT_TYPE_ID
        defaultSaleContractShouldNotBeFound("contractTypeId.lessThan=" + DEFAULT_CONTRACT_TYPE_ID);

        // Get all the saleContractList where contractTypeId is less than UPDATED_CONTRACT_TYPE_ID
        defaultSaleContractShouldBeFound("contractTypeId.lessThan=" + UPDATED_CONTRACT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractTypeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractTypeId is greater than DEFAULT_CONTRACT_TYPE_ID
        defaultSaleContractShouldNotBeFound("contractTypeId.greaterThan=" + DEFAULT_CONTRACT_TYPE_ID);

        // Get all the saleContractList where contractTypeId is greater than SMALLER_CONTRACT_TYPE_ID
        defaultSaleContractShouldBeFound("contractTypeId.greaterThan=" + SMALLER_CONTRACT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByCurrencyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where currencyId equals to DEFAULT_CURRENCY_ID
        defaultSaleContractShouldBeFound("currencyId.equals=" + DEFAULT_CURRENCY_ID);

        // Get all the saleContractList where currencyId equals to UPDATED_CURRENCY_ID
        defaultSaleContractShouldNotBeFound("currencyId.equals=" + UPDATED_CURRENCY_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByCurrencyIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where currencyId not equals to DEFAULT_CURRENCY_ID
        defaultSaleContractShouldNotBeFound("currencyId.notEquals=" + DEFAULT_CURRENCY_ID);

        // Get all the saleContractList where currencyId not equals to UPDATED_CURRENCY_ID
        defaultSaleContractShouldBeFound("currencyId.notEquals=" + UPDATED_CURRENCY_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByCurrencyIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where currencyId in DEFAULT_CURRENCY_ID or UPDATED_CURRENCY_ID
        defaultSaleContractShouldBeFound("currencyId.in=" + DEFAULT_CURRENCY_ID + "," + UPDATED_CURRENCY_ID);

        // Get all the saleContractList where currencyId equals to UPDATED_CURRENCY_ID
        defaultSaleContractShouldNotBeFound("currencyId.in=" + UPDATED_CURRENCY_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByCurrencyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where currencyId is not null
        defaultSaleContractShouldBeFound("currencyId.specified=true");

        // Get all the saleContractList where currencyId is null
        defaultSaleContractShouldNotBeFound("currencyId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByCurrencyIdContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where currencyId contains DEFAULT_CURRENCY_ID
        defaultSaleContractShouldBeFound("currencyId.contains=" + DEFAULT_CURRENCY_ID);

        // Get all the saleContractList where currencyId contains UPDATED_CURRENCY_ID
        defaultSaleContractShouldNotBeFound("currencyId.contains=" + UPDATED_CURRENCY_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByCurrencyIdNotContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where currencyId does not contain DEFAULT_CURRENCY_ID
        defaultSaleContractShouldNotBeFound("currencyId.doesNotContain=" + DEFAULT_CURRENCY_ID);

        // Get all the saleContractList where currencyId does not contain UPDATED_CURRENCY_ID
        defaultSaleContractShouldBeFound("currencyId.doesNotContain=" + UPDATED_CURRENCY_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByGrandTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where grandTotal equals to DEFAULT_GRAND_TOTAL
        defaultSaleContractShouldBeFound("grandTotal.equals=" + DEFAULT_GRAND_TOTAL);

        // Get all the saleContractList where grandTotal equals to UPDATED_GRAND_TOTAL
        defaultSaleContractShouldNotBeFound("grandTotal.equals=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    void getAllSaleContractsByGrandTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where grandTotal not equals to DEFAULT_GRAND_TOTAL
        defaultSaleContractShouldNotBeFound("grandTotal.notEquals=" + DEFAULT_GRAND_TOTAL);

        // Get all the saleContractList where grandTotal not equals to UPDATED_GRAND_TOTAL
        defaultSaleContractShouldBeFound("grandTotal.notEquals=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    void getAllSaleContractsByGrandTotalIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where grandTotal in DEFAULT_GRAND_TOTAL or UPDATED_GRAND_TOTAL
        defaultSaleContractShouldBeFound("grandTotal.in=" + DEFAULT_GRAND_TOTAL + "," + UPDATED_GRAND_TOTAL);

        // Get all the saleContractList where grandTotal equals to UPDATED_GRAND_TOTAL
        defaultSaleContractShouldNotBeFound("grandTotal.in=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    void getAllSaleContractsByGrandTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where grandTotal is not null
        defaultSaleContractShouldBeFound("grandTotal.specified=true");

        // Get all the saleContractList where grandTotal is null
        defaultSaleContractShouldNotBeFound("grandTotal.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByGrandTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where grandTotal is greater than or equal to DEFAULT_GRAND_TOTAL
        defaultSaleContractShouldBeFound("grandTotal.greaterThanOrEqual=" + DEFAULT_GRAND_TOTAL);

        // Get all the saleContractList where grandTotal is greater than or equal to UPDATED_GRAND_TOTAL
        defaultSaleContractShouldNotBeFound("grandTotal.greaterThanOrEqual=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    void getAllSaleContractsByGrandTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where grandTotal is less than or equal to DEFAULT_GRAND_TOTAL
        defaultSaleContractShouldBeFound("grandTotal.lessThanOrEqual=" + DEFAULT_GRAND_TOTAL);

        // Get all the saleContractList where grandTotal is less than or equal to SMALLER_GRAND_TOTAL
        defaultSaleContractShouldNotBeFound("grandTotal.lessThanOrEqual=" + SMALLER_GRAND_TOTAL);
    }

    @Test
    @Transactional
    void getAllSaleContractsByGrandTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where grandTotal is less than DEFAULT_GRAND_TOTAL
        defaultSaleContractShouldNotBeFound("grandTotal.lessThan=" + DEFAULT_GRAND_TOTAL);

        // Get all the saleContractList where grandTotal is less than UPDATED_GRAND_TOTAL
        defaultSaleContractShouldBeFound("grandTotal.lessThan=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    void getAllSaleContractsByGrandTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where grandTotal is greater than DEFAULT_GRAND_TOTAL
        defaultSaleContractShouldNotBeFound("grandTotal.greaterThan=" + DEFAULT_GRAND_TOTAL);

        // Get all the saleContractList where grandTotal is greater than SMALLER_GRAND_TOTAL
        defaultSaleContractShouldBeFound("grandTotal.greaterThan=" + SMALLER_GRAND_TOTAL);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentTermIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentTermId equals to DEFAULT_PAYMENT_TERM_ID
        defaultSaleContractShouldBeFound("paymentTermId.equals=" + DEFAULT_PAYMENT_TERM_ID);

        // Get all the saleContractList where paymentTermId equals to UPDATED_PAYMENT_TERM_ID
        defaultSaleContractShouldNotBeFound("paymentTermId.equals=" + UPDATED_PAYMENT_TERM_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentTermIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentTermId not equals to DEFAULT_PAYMENT_TERM_ID
        defaultSaleContractShouldNotBeFound("paymentTermId.notEquals=" + DEFAULT_PAYMENT_TERM_ID);

        // Get all the saleContractList where paymentTermId not equals to UPDATED_PAYMENT_TERM_ID
        defaultSaleContractShouldBeFound("paymentTermId.notEquals=" + UPDATED_PAYMENT_TERM_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentTermIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentTermId in DEFAULT_PAYMENT_TERM_ID or UPDATED_PAYMENT_TERM_ID
        defaultSaleContractShouldBeFound("paymentTermId.in=" + DEFAULT_PAYMENT_TERM_ID + "," + UPDATED_PAYMENT_TERM_ID);

        // Get all the saleContractList where paymentTermId equals to UPDATED_PAYMENT_TERM_ID
        defaultSaleContractShouldNotBeFound("paymentTermId.in=" + UPDATED_PAYMENT_TERM_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentTermIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentTermId is not null
        defaultSaleContractShouldBeFound("paymentTermId.specified=true");

        // Get all the saleContractList where paymentTermId is null
        defaultSaleContractShouldNotBeFound("paymentTermId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentTermIdContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentTermId contains DEFAULT_PAYMENT_TERM_ID
        defaultSaleContractShouldBeFound("paymentTermId.contains=" + DEFAULT_PAYMENT_TERM_ID);

        // Get all the saleContractList where paymentTermId contains UPDATED_PAYMENT_TERM_ID
        defaultSaleContractShouldNotBeFound("paymentTermId.contains=" + UPDATED_PAYMENT_TERM_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentTermIdNotContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentTermId does not contain DEFAULT_PAYMENT_TERM_ID
        defaultSaleContractShouldNotBeFound("paymentTermId.doesNotContain=" + DEFAULT_PAYMENT_TERM_ID);

        // Get all the saleContractList where paymentTermId does not contain UPDATED_PAYMENT_TERM_ID
        defaultSaleContractShouldBeFound("paymentTermId.doesNotContain=" + UPDATED_PAYMENT_TERM_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByQuoteIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where quoteId equals to DEFAULT_QUOTE_ID
        defaultSaleContractShouldBeFound("quoteId.equals=" + DEFAULT_QUOTE_ID);

        // Get all the saleContractList where quoteId equals to UPDATED_QUOTE_ID
        defaultSaleContractShouldNotBeFound("quoteId.equals=" + UPDATED_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByQuoteIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where quoteId not equals to DEFAULT_QUOTE_ID
        defaultSaleContractShouldNotBeFound("quoteId.notEquals=" + DEFAULT_QUOTE_ID);

        // Get all the saleContractList where quoteId not equals to UPDATED_QUOTE_ID
        defaultSaleContractShouldBeFound("quoteId.notEquals=" + UPDATED_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByQuoteIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where quoteId in DEFAULT_QUOTE_ID or UPDATED_QUOTE_ID
        defaultSaleContractShouldBeFound("quoteId.in=" + DEFAULT_QUOTE_ID + "," + UPDATED_QUOTE_ID);

        // Get all the saleContractList where quoteId equals to UPDATED_QUOTE_ID
        defaultSaleContractShouldNotBeFound("quoteId.in=" + UPDATED_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByQuoteIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where quoteId is not null
        defaultSaleContractShouldBeFound("quoteId.specified=true");

        // Get all the saleContractList where quoteId is null
        defaultSaleContractShouldNotBeFound("quoteId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByQuoteIdContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where quoteId contains DEFAULT_QUOTE_ID
        defaultSaleContractShouldBeFound("quoteId.contains=" + DEFAULT_QUOTE_ID);

        // Get all the saleContractList where quoteId contains UPDATED_QUOTE_ID
        defaultSaleContractShouldNotBeFound("quoteId.contains=" + UPDATED_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByQuoteIdNotContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where quoteId does not contain DEFAULT_QUOTE_ID
        defaultSaleContractShouldNotBeFound("quoteId.doesNotContain=" + DEFAULT_QUOTE_ID);

        // Get all the saleContractList where quoteId does not contain UPDATED_QUOTE_ID
        defaultSaleContractShouldBeFound("quoteId.doesNotContain=" + UPDATED_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByCurrencyExchangeRateIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where currencyExchangeRateId equals to DEFAULT_CURRENCY_EXCHANGE_RATE_ID
        defaultSaleContractShouldBeFound("currencyExchangeRateId.equals=" + DEFAULT_CURRENCY_EXCHANGE_RATE_ID);

        // Get all the saleContractList where currencyExchangeRateId equals to UPDATED_CURRENCY_EXCHANGE_RATE_ID
        defaultSaleContractShouldNotBeFound("currencyExchangeRateId.equals=" + UPDATED_CURRENCY_EXCHANGE_RATE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByCurrencyExchangeRateIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where currencyExchangeRateId not equals to DEFAULT_CURRENCY_EXCHANGE_RATE_ID
        defaultSaleContractShouldNotBeFound("currencyExchangeRateId.notEquals=" + DEFAULT_CURRENCY_EXCHANGE_RATE_ID);

        // Get all the saleContractList where currencyExchangeRateId not equals to UPDATED_CURRENCY_EXCHANGE_RATE_ID
        defaultSaleContractShouldBeFound("currencyExchangeRateId.notEquals=" + UPDATED_CURRENCY_EXCHANGE_RATE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByCurrencyExchangeRateIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where currencyExchangeRateId in DEFAULT_CURRENCY_EXCHANGE_RATE_ID or UPDATED_CURRENCY_EXCHANGE_RATE_ID
        defaultSaleContractShouldBeFound(
            "currencyExchangeRateId.in=" + DEFAULT_CURRENCY_EXCHANGE_RATE_ID + "," + UPDATED_CURRENCY_EXCHANGE_RATE_ID
        );

        // Get all the saleContractList where currencyExchangeRateId equals to UPDATED_CURRENCY_EXCHANGE_RATE_ID
        defaultSaleContractShouldNotBeFound("currencyExchangeRateId.in=" + UPDATED_CURRENCY_EXCHANGE_RATE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByCurrencyExchangeRateIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where currencyExchangeRateId is not null
        defaultSaleContractShouldBeFound("currencyExchangeRateId.specified=true");

        // Get all the saleContractList where currencyExchangeRateId is null
        defaultSaleContractShouldNotBeFound("currencyExchangeRateId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByCurrencyExchangeRateIdContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where currencyExchangeRateId contains DEFAULT_CURRENCY_EXCHANGE_RATE_ID
        defaultSaleContractShouldBeFound("currencyExchangeRateId.contains=" + DEFAULT_CURRENCY_EXCHANGE_RATE_ID);

        // Get all the saleContractList where currencyExchangeRateId contains UPDATED_CURRENCY_EXCHANGE_RATE_ID
        defaultSaleContractShouldNotBeFound("currencyExchangeRateId.contains=" + UPDATED_CURRENCY_EXCHANGE_RATE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByCurrencyExchangeRateIdNotContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where currencyExchangeRateId does not contain DEFAULT_CURRENCY_EXCHANGE_RATE_ID
        defaultSaleContractShouldNotBeFound("currencyExchangeRateId.doesNotContain=" + DEFAULT_CURRENCY_EXCHANGE_RATE_ID);

        // Get all the saleContractList where currencyExchangeRateId does not contain UPDATED_CURRENCY_EXCHANGE_RATE_ID
        defaultSaleContractShouldBeFound("currencyExchangeRateId.doesNotContain=" + UPDATED_CURRENCY_EXCHANGE_RATE_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStageNameIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStageName equals to DEFAULT_CONTRACT_STAGE_NAME
        defaultSaleContractShouldBeFound("contractStageName.equals=" + DEFAULT_CONTRACT_STAGE_NAME);

        // Get all the saleContractList where contractStageName equals to UPDATED_CONTRACT_STAGE_NAME
        defaultSaleContractShouldNotBeFound("contractStageName.equals=" + UPDATED_CONTRACT_STAGE_NAME);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStageNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStageName not equals to DEFAULT_CONTRACT_STAGE_NAME
        defaultSaleContractShouldNotBeFound("contractStageName.notEquals=" + DEFAULT_CONTRACT_STAGE_NAME);

        // Get all the saleContractList where contractStageName not equals to UPDATED_CONTRACT_STAGE_NAME
        defaultSaleContractShouldBeFound("contractStageName.notEquals=" + UPDATED_CONTRACT_STAGE_NAME);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStageNameIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStageName in DEFAULT_CONTRACT_STAGE_NAME or UPDATED_CONTRACT_STAGE_NAME
        defaultSaleContractShouldBeFound("contractStageName.in=" + DEFAULT_CONTRACT_STAGE_NAME + "," + UPDATED_CONTRACT_STAGE_NAME);

        // Get all the saleContractList where contractStageName equals to UPDATED_CONTRACT_STAGE_NAME
        defaultSaleContractShouldNotBeFound("contractStageName.in=" + UPDATED_CONTRACT_STAGE_NAME);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStageNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStageName is not null
        defaultSaleContractShouldBeFound("contractStageName.specified=true");

        // Get all the saleContractList where contractStageName is null
        defaultSaleContractShouldNotBeFound("contractStageName.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStageNameContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStageName contains DEFAULT_CONTRACT_STAGE_NAME
        defaultSaleContractShouldBeFound("contractStageName.contains=" + DEFAULT_CONTRACT_STAGE_NAME);

        // Get all the saleContractList where contractStageName contains UPDATED_CONTRACT_STAGE_NAME
        defaultSaleContractShouldNotBeFound("contractStageName.contains=" + UPDATED_CONTRACT_STAGE_NAME);
    }

    @Test
    @Transactional
    void getAllSaleContractsByContractStageNameNotContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where contractStageName does not contain DEFAULT_CONTRACT_STAGE_NAME
        defaultSaleContractShouldNotBeFound("contractStageName.doesNotContain=" + DEFAULT_CONTRACT_STAGE_NAME);

        // Get all the saleContractList where contractStageName does not contain UPDATED_CONTRACT_STAGE_NAME
        defaultSaleContractShouldBeFound("contractStageName.doesNotContain=" + UPDATED_CONTRACT_STAGE_NAME);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentStatusIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentStatusId equals to DEFAULT_PAYMENT_STATUS_ID
        defaultSaleContractShouldBeFound("paymentStatusId.equals=" + DEFAULT_PAYMENT_STATUS_ID);

        // Get all the saleContractList where paymentStatusId equals to UPDATED_PAYMENT_STATUS_ID
        defaultSaleContractShouldNotBeFound("paymentStatusId.equals=" + UPDATED_PAYMENT_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentStatusIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentStatusId not equals to DEFAULT_PAYMENT_STATUS_ID
        defaultSaleContractShouldNotBeFound("paymentStatusId.notEquals=" + DEFAULT_PAYMENT_STATUS_ID);

        // Get all the saleContractList where paymentStatusId not equals to UPDATED_PAYMENT_STATUS_ID
        defaultSaleContractShouldBeFound("paymentStatusId.notEquals=" + UPDATED_PAYMENT_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentStatusIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentStatusId in DEFAULT_PAYMENT_STATUS_ID or UPDATED_PAYMENT_STATUS_ID
        defaultSaleContractShouldBeFound("paymentStatusId.in=" + DEFAULT_PAYMENT_STATUS_ID + "," + UPDATED_PAYMENT_STATUS_ID);

        // Get all the saleContractList where paymentStatusId equals to UPDATED_PAYMENT_STATUS_ID
        defaultSaleContractShouldNotBeFound("paymentStatusId.in=" + UPDATED_PAYMENT_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentStatusIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentStatusId is not null
        defaultSaleContractShouldBeFound("paymentStatusId.specified=true");

        // Get all the saleContractList where paymentStatusId is null
        defaultSaleContractShouldNotBeFound("paymentStatusId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentStatusIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentStatusId is greater than or equal to DEFAULT_PAYMENT_STATUS_ID
        defaultSaleContractShouldBeFound("paymentStatusId.greaterThanOrEqual=" + DEFAULT_PAYMENT_STATUS_ID);

        // Get all the saleContractList where paymentStatusId is greater than or equal to UPDATED_PAYMENT_STATUS_ID
        defaultSaleContractShouldNotBeFound("paymentStatusId.greaterThanOrEqual=" + UPDATED_PAYMENT_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentStatusIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentStatusId is less than or equal to DEFAULT_PAYMENT_STATUS_ID
        defaultSaleContractShouldBeFound("paymentStatusId.lessThanOrEqual=" + DEFAULT_PAYMENT_STATUS_ID);

        // Get all the saleContractList where paymentStatusId is less than or equal to SMALLER_PAYMENT_STATUS_ID
        defaultSaleContractShouldNotBeFound("paymentStatusId.lessThanOrEqual=" + SMALLER_PAYMENT_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentStatusIdIsLessThanSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentStatusId is less than DEFAULT_PAYMENT_STATUS_ID
        defaultSaleContractShouldNotBeFound("paymentStatusId.lessThan=" + DEFAULT_PAYMENT_STATUS_ID);

        // Get all the saleContractList where paymentStatusId is less than UPDATED_PAYMENT_STATUS_ID
        defaultSaleContractShouldBeFound("paymentStatusId.lessThan=" + UPDATED_PAYMENT_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentStatusIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where paymentStatusId is greater than DEFAULT_PAYMENT_STATUS_ID
        defaultSaleContractShouldNotBeFound("paymentStatusId.greaterThan=" + DEFAULT_PAYMENT_STATUS_ID);

        // Get all the saleContractList where paymentStatusId is greater than SMALLER_PAYMENT_STATUS_ID
        defaultSaleContractShouldBeFound("paymentStatusId.greaterThan=" + SMALLER_PAYMENT_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where period equals to DEFAULT_PERIOD
        defaultSaleContractShouldBeFound("period.equals=" + DEFAULT_PERIOD);

        // Get all the saleContractList where period equals to UPDATED_PERIOD
        defaultSaleContractShouldNotBeFound("period.equals=" + UPDATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPeriodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where period not equals to DEFAULT_PERIOD
        defaultSaleContractShouldNotBeFound("period.notEquals=" + DEFAULT_PERIOD);

        // Get all the saleContractList where period not equals to UPDATED_PERIOD
        defaultSaleContractShouldBeFound("period.notEquals=" + UPDATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where period in DEFAULT_PERIOD or UPDATED_PERIOD
        defaultSaleContractShouldBeFound("period.in=" + DEFAULT_PERIOD + "," + UPDATED_PERIOD);

        // Get all the saleContractList where period equals to UPDATED_PERIOD
        defaultSaleContractShouldNotBeFound("period.in=" + UPDATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where period is not null
        defaultSaleContractShouldBeFound("period.specified=true");

        // Get all the saleContractList where period is null
        defaultSaleContractShouldNotBeFound("period.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByPeriodIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where period is greater than or equal to DEFAULT_PERIOD
        defaultSaleContractShouldBeFound("period.greaterThanOrEqual=" + DEFAULT_PERIOD);

        // Get all the saleContractList where period is greater than or equal to UPDATED_PERIOD
        defaultSaleContractShouldNotBeFound("period.greaterThanOrEqual=" + UPDATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPeriodIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where period is less than or equal to DEFAULT_PERIOD
        defaultSaleContractShouldBeFound("period.lessThanOrEqual=" + DEFAULT_PERIOD);

        // Get all the saleContractList where period is less than or equal to SMALLER_PERIOD
        defaultSaleContractShouldNotBeFound("period.lessThanOrEqual=" + SMALLER_PERIOD);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPeriodIsLessThanSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where period is less than DEFAULT_PERIOD
        defaultSaleContractShouldNotBeFound("period.lessThan=" + DEFAULT_PERIOD);

        // Get all the saleContractList where period is less than UPDATED_PERIOD
        defaultSaleContractShouldBeFound("period.lessThan=" + UPDATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPeriodIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where period is greater than DEFAULT_PERIOD
        defaultSaleContractShouldNotBeFound("period.greaterThan=" + DEFAULT_PERIOD);

        // Get all the saleContractList where period is greater than SMALLER_PERIOD
        defaultSaleContractShouldBeFound("period.greaterThan=" + SMALLER_PERIOD);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where payment equals to DEFAULT_PAYMENT
        defaultSaleContractShouldBeFound("payment.equals=" + DEFAULT_PAYMENT);

        // Get all the saleContractList where payment equals to UPDATED_PAYMENT
        defaultSaleContractShouldNotBeFound("payment.equals=" + UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where payment not equals to DEFAULT_PAYMENT
        defaultSaleContractShouldNotBeFound("payment.notEquals=" + DEFAULT_PAYMENT);

        // Get all the saleContractList where payment not equals to UPDATED_PAYMENT
        defaultSaleContractShouldBeFound("payment.notEquals=" + UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentIsInShouldWork() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where payment in DEFAULT_PAYMENT or UPDATED_PAYMENT
        defaultSaleContractShouldBeFound("payment.in=" + DEFAULT_PAYMENT + "," + UPDATED_PAYMENT);

        // Get all the saleContractList where payment equals to UPDATED_PAYMENT
        defaultSaleContractShouldNotBeFound("payment.in=" + UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where payment is not null
        defaultSaleContractShouldBeFound("payment.specified=true");

        // Get all the saleContractList where payment is null
        defaultSaleContractShouldNotBeFound("payment.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where payment contains DEFAULT_PAYMENT
        defaultSaleContractShouldBeFound("payment.contains=" + DEFAULT_PAYMENT);

        // Get all the saleContractList where payment contains UPDATED_PAYMENT
        defaultSaleContractShouldNotBeFound("payment.contains=" + UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    void getAllSaleContractsByPaymentNotContainsSomething() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        // Get all the saleContractList where payment does not contain DEFAULT_PAYMENT
        defaultSaleContractShouldNotBeFound("payment.doesNotContain=" + DEFAULT_PAYMENT);

        // Get all the saleContractList where payment does not contain UPDATED_PAYMENT
        defaultSaleContractShouldBeFound("payment.doesNotContain=" + UPDATED_PAYMENT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSaleContractShouldBeFound(String filter) throws Exception {
        restSaleContractMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleContract.getId().intValue())))
            .andExpect(jsonPath("$.[*].contractId").value(hasItem(DEFAULT_CONTRACT_ID)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID)))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].contactSignedDate").value(hasItem(DEFAULT_CONTACT_SIGNED_DATE.toString())))
            .andExpect(jsonPath("$.[*].contactSignedTitle").value(hasItem(DEFAULT_CONTACT_SIGNED_TITLE)))
            .andExpect(jsonPath("$.[*].contractEndDate").value(hasItem(DEFAULT_CONTRACT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].contractNumber").value(hasItem(DEFAULT_CONTRACT_NUMBER)))
            .andExpect(jsonPath("$.[*].contractNumberInput").value(hasItem(DEFAULT_CONTRACT_NUMBER_INPUT)))
            .andExpect(jsonPath("$.[*].contractStageId").value(hasItem(DEFAULT_CONTRACT_STAGE_ID)))
            .andExpect(jsonPath("$.[*].contractStartDate").value(hasItem(DEFAULT_CONTRACT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].ownerEmployeeId").value(hasItem(DEFAULT_OWNER_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].paymentMethodId").value(hasItem(DEFAULT_PAYMENT_METHOD_ID)))
            .andExpect(jsonPath("$.[*].contractName").value(hasItem(DEFAULT_CONTRACT_NAME)))
            .andExpect(jsonPath("$.[*].contractTypeId").value(hasItem(DEFAULT_CONTRACT_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].currencyId").value(hasItem(DEFAULT_CURRENCY_ID)))
            .andExpect(jsonPath("$.[*].grandTotal").value(hasItem(sameNumber(DEFAULT_GRAND_TOTAL))))
            .andExpect(jsonPath("$.[*].paymentTermId").value(hasItem(DEFAULT_PAYMENT_TERM_ID)))
            .andExpect(jsonPath("$.[*].quoteId").value(hasItem(DEFAULT_QUOTE_ID)))
            .andExpect(jsonPath("$.[*].currencyExchangeRateId").value(hasItem(DEFAULT_CURRENCY_EXCHANGE_RATE_ID)))
            .andExpect(jsonPath("$.[*].contractStageName").value(hasItem(DEFAULT_CONTRACT_STAGE_NAME)))
            .andExpect(jsonPath("$.[*].paymentStatusId").value(hasItem(DEFAULT_PAYMENT_STATUS_ID.intValue())))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD)))
            .andExpect(jsonPath("$.[*].payment").value(hasItem(DEFAULT_PAYMENT)));

        // Check, that the count call also returns 1
        restSaleContractMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSaleContractShouldNotBeFound(String filter) throws Exception {
        restSaleContractMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSaleContractMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSaleContract() throws Exception {
        // Get the saleContract
        restSaleContractMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSaleContract() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();

        // Update the saleContract
        SaleContract updatedSaleContract = saleContractRepository.findById(saleContract.getId()).get();
        // Disconnect from session so that the updates on updatedSaleContract are not directly saved in db
        em.detach(updatedSaleContract);
        updatedSaleContract
            .contractId(UPDATED_CONTRACT_ID)
            .companyId(UPDATED_COMPANY_ID)
            .accountId(UPDATED_ACCOUNT_ID)
            .contactSignedDate(UPDATED_CONTACT_SIGNED_DATE)
            .contactSignedTitle(UPDATED_CONTACT_SIGNED_TITLE)
            .contractEndDate(UPDATED_CONTRACT_END_DATE)
            .contractNumber(UPDATED_CONTRACT_NUMBER)
            .contractNumberInput(UPDATED_CONTRACT_NUMBER_INPUT)
            .contractStageId(UPDATED_CONTRACT_STAGE_ID)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .ownerEmployeeId(UPDATED_OWNER_EMPLOYEE_ID)
            .paymentMethodId(UPDATED_PAYMENT_METHOD_ID)
            .contractName(UPDATED_CONTRACT_NAME)
            .contractTypeId(UPDATED_CONTRACT_TYPE_ID)
            .currencyId(UPDATED_CURRENCY_ID)
            .grandTotal(UPDATED_GRAND_TOTAL)
            .paymentTermId(UPDATED_PAYMENT_TERM_ID)
            .quoteId(UPDATED_QUOTE_ID)
            .currencyExchangeRateId(UPDATED_CURRENCY_EXCHANGE_RATE_ID)
            .contractStageName(UPDATED_CONTRACT_STAGE_NAME)
            .paymentStatusId(UPDATED_PAYMENT_STATUS_ID)
            .period(UPDATED_PERIOD)
            .payment(UPDATED_PAYMENT);

        restSaleContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSaleContract.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSaleContract))
            )
            .andExpect(status().isOk());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
        SaleContract testSaleContract = saleContractList.get(saleContractList.size() - 1);
        assertThat(testSaleContract.getContractId()).isEqualTo(UPDATED_CONTRACT_ID);
        assertThat(testSaleContract.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testSaleContract.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testSaleContract.getContactSignedDate()).isEqualTo(UPDATED_CONTACT_SIGNED_DATE);
        assertThat(testSaleContract.getContactSignedTitle()).isEqualTo(UPDATED_CONTACT_SIGNED_TITLE);
        assertThat(testSaleContract.getContractEndDate()).isEqualTo(UPDATED_CONTRACT_END_DATE);
        assertThat(testSaleContract.getContractNumber()).isEqualTo(UPDATED_CONTRACT_NUMBER);
        assertThat(testSaleContract.getContractNumberInput()).isEqualTo(UPDATED_CONTRACT_NUMBER_INPUT);
        assertThat(testSaleContract.getContractStageId()).isEqualTo(UPDATED_CONTRACT_STAGE_ID);
        assertThat(testSaleContract.getContractStartDate()).isEqualTo(UPDATED_CONTRACT_START_DATE);
        assertThat(testSaleContract.getOwnerEmployeeId()).isEqualTo(UPDATED_OWNER_EMPLOYEE_ID);
        assertThat(testSaleContract.getPaymentMethodId()).isEqualTo(UPDATED_PAYMENT_METHOD_ID);
        assertThat(testSaleContract.getContractName()).isEqualTo(UPDATED_CONTRACT_NAME);
        assertThat(testSaleContract.getContractTypeId()).isEqualTo(UPDATED_CONTRACT_TYPE_ID);
        assertThat(testSaleContract.getCurrencyId()).isEqualTo(UPDATED_CURRENCY_ID);
        assertThat(testSaleContract.getGrandTotal()).isEqualByComparingTo(UPDATED_GRAND_TOTAL);
        assertThat(testSaleContract.getPaymentTermId()).isEqualTo(UPDATED_PAYMENT_TERM_ID);
        assertThat(testSaleContract.getQuoteId()).isEqualTo(UPDATED_QUOTE_ID);
        assertThat(testSaleContract.getCurrencyExchangeRateId()).isEqualTo(UPDATED_CURRENCY_EXCHANGE_RATE_ID);
        assertThat(testSaleContract.getContractStageName()).isEqualTo(UPDATED_CONTRACT_STAGE_NAME);
        assertThat(testSaleContract.getPaymentStatusId()).isEqualTo(UPDATED_PAYMENT_STATUS_ID);
        assertThat(testSaleContract.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testSaleContract.getPayment()).isEqualTo(UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    void putNonExistingSaleContract() throws Exception {
        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();
        saleContract.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, saleContract.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saleContract))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSaleContract() throws Exception {
        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();
        saleContract.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saleContract))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSaleContract() throws Exception {
        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();
        saleContract.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleContractMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleContract)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSaleContractWithPatch() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();

        // Update the saleContract using partial update
        SaleContract partialUpdatedSaleContract = new SaleContract();
        partialUpdatedSaleContract.setId(saleContract.getId());

        partialUpdatedSaleContract
            .contactSignedTitle(UPDATED_CONTACT_SIGNED_TITLE)
            .contractNumberInput(UPDATED_CONTRACT_NUMBER_INPUT)
            .ownerEmployeeId(UPDATED_OWNER_EMPLOYEE_ID)
            .paymentMethodId(UPDATED_PAYMENT_METHOD_ID)
            .contractName(UPDATED_CONTRACT_NAME)
            .contractTypeId(UPDATED_CONTRACT_TYPE_ID)
            .paymentTermId(UPDATED_PAYMENT_TERM_ID)
            .contractStageName(UPDATED_CONTRACT_STAGE_NAME)
            .paymentStatusId(UPDATED_PAYMENT_STATUS_ID);

        restSaleContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaleContract.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSaleContract))
            )
            .andExpect(status().isOk());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
        SaleContract testSaleContract = saleContractList.get(saleContractList.size() - 1);
        assertThat(testSaleContract.getContractId()).isEqualTo(DEFAULT_CONTRACT_ID);
        assertThat(testSaleContract.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testSaleContract.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testSaleContract.getContactSignedDate()).isEqualTo(DEFAULT_CONTACT_SIGNED_DATE);
        assertThat(testSaleContract.getContactSignedTitle()).isEqualTo(UPDATED_CONTACT_SIGNED_TITLE);
        assertThat(testSaleContract.getContractEndDate()).isEqualTo(DEFAULT_CONTRACT_END_DATE);
        assertThat(testSaleContract.getContractNumber()).isEqualTo(DEFAULT_CONTRACT_NUMBER);
        assertThat(testSaleContract.getContractNumberInput()).isEqualTo(UPDATED_CONTRACT_NUMBER_INPUT);
        assertThat(testSaleContract.getContractStageId()).isEqualTo(DEFAULT_CONTRACT_STAGE_ID);
        assertThat(testSaleContract.getContractStartDate()).isEqualTo(DEFAULT_CONTRACT_START_DATE);
        assertThat(testSaleContract.getOwnerEmployeeId()).isEqualTo(UPDATED_OWNER_EMPLOYEE_ID);
        assertThat(testSaleContract.getPaymentMethodId()).isEqualTo(UPDATED_PAYMENT_METHOD_ID);
        assertThat(testSaleContract.getContractName()).isEqualTo(UPDATED_CONTRACT_NAME);
        assertThat(testSaleContract.getContractTypeId()).isEqualTo(UPDATED_CONTRACT_TYPE_ID);
        assertThat(testSaleContract.getCurrencyId()).isEqualTo(DEFAULT_CURRENCY_ID);
        assertThat(testSaleContract.getGrandTotal()).isEqualByComparingTo(DEFAULT_GRAND_TOTAL);
        assertThat(testSaleContract.getPaymentTermId()).isEqualTo(UPDATED_PAYMENT_TERM_ID);
        assertThat(testSaleContract.getQuoteId()).isEqualTo(DEFAULT_QUOTE_ID);
        assertThat(testSaleContract.getCurrencyExchangeRateId()).isEqualTo(DEFAULT_CURRENCY_EXCHANGE_RATE_ID);
        assertThat(testSaleContract.getContractStageName()).isEqualTo(UPDATED_CONTRACT_STAGE_NAME);
        assertThat(testSaleContract.getPaymentStatusId()).isEqualTo(UPDATED_PAYMENT_STATUS_ID);
        assertThat(testSaleContract.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testSaleContract.getPayment()).isEqualTo(DEFAULT_PAYMENT);
    }

    @Test
    @Transactional
    void fullUpdateSaleContractWithPatch() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();

        // Update the saleContract using partial update
        SaleContract partialUpdatedSaleContract = new SaleContract();
        partialUpdatedSaleContract.setId(saleContract.getId());

        partialUpdatedSaleContract
            .contractId(UPDATED_CONTRACT_ID)
            .companyId(UPDATED_COMPANY_ID)
            .accountId(UPDATED_ACCOUNT_ID)
            .contactSignedDate(UPDATED_CONTACT_SIGNED_DATE)
            .contactSignedTitle(UPDATED_CONTACT_SIGNED_TITLE)
            .contractEndDate(UPDATED_CONTRACT_END_DATE)
            .contractNumber(UPDATED_CONTRACT_NUMBER)
            .contractNumberInput(UPDATED_CONTRACT_NUMBER_INPUT)
            .contractStageId(UPDATED_CONTRACT_STAGE_ID)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .ownerEmployeeId(UPDATED_OWNER_EMPLOYEE_ID)
            .paymentMethodId(UPDATED_PAYMENT_METHOD_ID)
            .contractName(UPDATED_CONTRACT_NAME)
            .contractTypeId(UPDATED_CONTRACT_TYPE_ID)
            .currencyId(UPDATED_CURRENCY_ID)
            .grandTotal(UPDATED_GRAND_TOTAL)
            .paymentTermId(UPDATED_PAYMENT_TERM_ID)
            .quoteId(UPDATED_QUOTE_ID)
            .currencyExchangeRateId(UPDATED_CURRENCY_EXCHANGE_RATE_ID)
            .contractStageName(UPDATED_CONTRACT_STAGE_NAME)
            .paymentStatusId(UPDATED_PAYMENT_STATUS_ID)
            .period(UPDATED_PERIOD)
            .payment(UPDATED_PAYMENT);

        restSaleContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaleContract.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSaleContract))
            )
            .andExpect(status().isOk());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
        SaleContract testSaleContract = saleContractList.get(saleContractList.size() - 1);
        assertThat(testSaleContract.getContractId()).isEqualTo(UPDATED_CONTRACT_ID);
        assertThat(testSaleContract.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testSaleContract.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testSaleContract.getContactSignedDate()).isEqualTo(UPDATED_CONTACT_SIGNED_DATE);
        assertThat(testSaleContract.getContactSignedTitle()).isEqualTo(UPDATED_CONTACT_SIGNED_TITLE);
        assertThat(testSaleContract.getContractEndDate()).isEqualTo(UPDATED_CONTRACT_END_DATE);
        assertThat(testSaleContract.getContractNumber()).isEqualTo(UPDATED_CONTRACT_NUMBER);
        assertThat(testSaleContract.getContractNumberInput()).isEqualTo(UPDATED_CONTRACT_NUMBER_INPUT);
        assertThat(testSaleContract.getContractStageId()).isEqualTo(UPDATED_CONTRACT_STAGE_ID);
        assertThat(testSaleContract.getContractStartDate()).isEqualTo(UPDATED_CONTRACT_START_DATE);
        assertThat(testSaleContract.getOwnerEmployeeId()).isEqualTo(UPDATED_OWNER_EMPLOYEE_ID);
        assertThat(testSaleContract.getPaymentMethodId()).isEqualTo(UPDATED_PAYMENT_METHOD_ID);
        assertThat(testSaleContract.getContractName()).isEqualTo(UPDATED_CONTRACT_NAME);
        assertThat(testSaleContract.getContractTypeId()).isEqualTo(UPDATED_CONTRACT_TYPE_ID);
        assertThat(testSaleContract.getCurrencyId()).isEqualTo(UPDATED_CURRENCY_ID);
        assertThat(testSaleContract.getGrandTotal()).isEqualByComparingTo(UPDATED_GRAND_TOTAL);
        assertThat(testSaleContract.getPaymentTermId()).isEqualTo(UPDATED_PAYMENT_TERM_ID);
        assertThat(testSaleContract.getQuoteId()).isEqualTo(UPDATED_QUOTE_ID);
        assertThat(testSaleContract.getCurrencyExchangeRateId()).isEqualTo(UPDATED_CURRENCY_EXCHANGE_RATE_ID);
        assertThat(testSaleContract.getContractStageName()).isEqualTo(UPDATED_CONTRACT_STAGE_NAME);
        assertThat(testSaleContract.getPaymentStatusId()).isEqualTo(UPDATED_PAYMENT_STATUS_ID);
        assertThat(testSaleContract.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testSaleContract.getPayment()).isEqualTo(UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    void patchNonExistingSaleContract() throws Exception {
        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();
        saleContract.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saleContract.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saleContract))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSaleContract() throws Exception {
        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();
        saleContract.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saleContract))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSaleContract() throws Exception {
        int databaseSizeBeforeUpdate = saleContractRepository.findAll().size();
        saleContract.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleContractMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(saleContract))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaleContract in the database
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSaleContract() throws Exception {
        // Initialize the database
        saleContractRepository.saveAndFlush(saleContract);

        int databaseSizeBeforeDelete = saleContractRepository.findAll().size();

        // Delete the saleContract
        restSaleContractMockMvc
            .perform(delete(ENTITY_API_URL_ID, saleContract.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SaleContract> saleContractList = saleContractRepository.findAll();
        assertThat(saleContractList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
