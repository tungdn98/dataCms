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
import vn.com.datamanager.domain.SaleOpportunity;
import vn.com.datamanager.repository.SaleOpportunityRepository;
import vn.com.datamanager.service.criteria.SaleOpportunityCriteria;

/**
 * Integration tests for the {@link SaleOpportunityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaleOpportunityResourceIT {

    private static final Long DEFAULT_OPPORTUNITY_ID = 1L;
    private static final Long UPDATED_OPPORTUNITY_ID = 2L;
    private static final Long SMALLER_OPPORTUNITY_ID = 1L - 1L;

    private static final String DEFAULT_OPPORTUNITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_OPPORTUNITY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_OPPORTUNITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OPPORTUNITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OPPORTUNITY_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OPPORTUNITY_TYPE_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_CLOSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CLOSE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CLOSE_DATE = LocalDate.ofEpochDay(-1L);

    private static final Long DEFAULT_STAGE_ID = 1L;
    private static final Long UPDATED_STAGE_ID = 2L;
    private static final Long SMALLER_STAGE_ID = 1L - 1L;

    private static final Long DEFAULT_STAGE_REASON_ID = 1L;
    private static final Long UPDATED_STAGE_REASON_ID = 2L;
    private static final Long SMALLER_STAGE_REASON_ID = 1L - 1L;

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_EMPLOYEE_ID = 2L;
    private static final Long SMALLER_EMPLOYEE_ID = 1L - 1L;

    private static final Long DEFAULT_LEAD_ID = 1L;
    private static final Long UPDATED_LEAD_ID = 2L;
    private static final Long SMALLER_LEAD_ID = 1L - 1L;

    private static final String DEFAULT_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ACCOUNT_ID = 1L;
    private static final Long UPDATED_ACCOUNT_ID = 2L;
    private static final Long SMALLER_ACCOUNT_ID = 1L - 1L;

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;
    private static final Long SMALLER_PRODUCT_ID = 1L - 1L;

    private static final BigDecimal DEFAULT_SALES_PRICE_PRD = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALES_PRICE_PRD = new BigDecimal(2);
    private static final BigDecimal SMALLER_SALES_PRICE_PRD = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALUE = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/sale-opportunities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SaleOpportunityRepository saleOpportunityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaleOpportunityMockMvc;

    private SaleOpportunity saleOpportunity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaleOpportunity createEntity(EntityManager em) {
        SaleOpportunity saleOpportunity = new SaleOpportunity()
            .opportunityId(DEFAULT_OPPORTUNITY_ID)
            .opportunityCode(DEFAULT_OPPORTUNITY_CODE)
            .opportunityName(DEFAULT_OPPORTUNITY_NAME)
            .opportunityTypeName(DEFAULT_OPPORTUNITY_TYPE_NAME)
            .startDate(DEFAULT_START_DATE)
            .closeDate(DEFAULT_CLOSE_DATE)
            .stageId(DEFAULT_STAGE_ID)
            .stageReasonId(DEFAULT_STAGE_REASON_ID)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .leadId(DEFAULT_LEAD_ID)
            .currencyCode(DEFAULT_CURRENCY_CODE)
            .accountId(DEFAULT_ACCOUNT_ID)
            .productId(DEFAULT_PRODUCT_ID)
            .salesPricePrd(DEFAULT_SALES_PRICE_PRD)
            .value(DEFAULT_VALUE);
        return saleOpportunity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaleOpportunity createUpdatedEntity(EntityManager em) {
        SaleOpportunity saleOpportunity = new SaleOpportunity()
            .opportunityId(UPDATED_OPPORTUNITY_ID)
            .opportunityCode(UPDATED_OPPORTUNITY_CODE)
            .opportunityName(UPDATED_OPPORTUNITY_NAME)
            .opportunityTypeName(UPDATED_OPPORTUNITY_TYPE_NAME)
            .startDate(UPDATED_START_DATE)
            .closeDate(UPDATED_CLOSE_DATE)
            .stageId(UPDATED_STAGE_ID)
            .stageReasonId(UPDATED_STAGE_REASON_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .leadId(UPDATED_LEAD_ID)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .accountId(UPDATED_ACCOUNT_ID)
            .productId(UPDATED_PRODUCT_ID)
            .salesPricePrd(UPDATED_SALES_PRICE_PRD)
            .value(UPDATED_VALUE);
        return saleOpportunity;
    }

    @BeforeEach
    public void initTest() {
        saleOpportunity = createEntity(em);
    }

    @Test
    @Transactional
    void createSaleOpportunity() throws Exception {
        int databaseSizeBeforeCreate = saleOpportunityRepository.findAll().size();
        // Create the SaleOpportunity
        restSaleOpportunityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleOpportunity))
            )
            .andExpect(status().isCreated());

        // Validate the SaleOpportunity in the database
        List<SaleOpportunity> saleOpportunityList = saleOpportunityRepository.findAll();
        assertThat(saleOpportunityList).hasSize(databaseSizeBeforeCreate + 1);
        SaleOpportunity testSaleOpportunity = saleOpportunityList.get(saleOpportunityList.size() - 1);
        assertThat(testSaleOpportunity.getOpportunityId()).isEqualTo(DEFAULT_OPPORTUNITY_ID);
        assertThat(testSaleOpportunity.getOpportunityCode()).isEqualTo(DEFAULT_OPPORTUNITY_CODE);
        assertThat(testSaleOpportunity.getOpportunityName()).isEqualTo(DEFAULT_OPPORTUNITY_NAME);
        assertThat(testSaleOpportunity.getOpportunityTypeName()).isEqualTo(DEFAULT_OPPORTUNITY_TYPE_NAME);
        assertThat(testSaleOpportunity.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSaleOpportunity.getCloseDate()).isEqualTo(DEFAULT_CLOSE_DATE);
        assertThat(testSaleOpportunity.getStageId()).isEqualTo(DEFAULT_STAGE_ID);
        assertThat(testSaleOpportunity.getStageReasonId()).isEqualTo(DEFAULT_STAGE_REASON_ID);
        assertThat(testSaleOpportunity.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testSaleOpportunity.getLeadId()).isEqualTo(DEFAULT_LEAD_ID);
        assertThat(testSaleOpportunity.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testSaleOpportunity.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testSaleOpportunity.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testSaleOpportunity.getSalesPricePrd()).isEqualByComparingTo(DEFAULT_SALES_PRICE_PRD);
        assertThat(testSaleOpportunity.getValue()).isEqualByComparingTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createSaleOpportunityWithExistingId() throws Exception {
        // Create the SaleOpportunity with an existing ID
        saleOpportunity.setId(1L);

        int databaseSizeBeforeCreate = saleOpportunityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleOpportunityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleOpportunity))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleOpportunity in the database
        List<SaleOpportunity> saleOpportunityList = saleOpportunityRepository.findAll();
        assertThat(saleOpportunityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOpportunityCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleOpportunityRepository.findAll().size();
        // set the field null
        saleOpportunity.setOpportunityCode(null);

        // Create the SaleOpportunity, which fails.

        restSaleOpportunityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleOpportunity))
            )
            .andExpect(status().isBadRequest());

        List<SaleOpportunity> saleOpportunityList = saleOpportunityRepository.findAll();
        assertThat(saleOpportunityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOpportunityNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleOpportunityRepository.findAll().size();
        // set the field null
        saleOpportunity.setOpportunityName(null);

        // Create the SaleOpportunity, which fails.

        restSaleOpportunityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleOpportunity))
            )
            .andExpect(status().isBadRequest());

        List<SaleOpportunity> saleOpportunityList = saleOpportunityRepository.findAll();
        assertThat(saleOpportunityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSaleOpportunities() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList
        restSaleOpportunityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleOpportunity.getId().intValue())))
            .andExpect(jsonPath("$.[*].opportunityId").value(hasItem(DEFAULT_OPPORTUNITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].opportunityCode").value(hasItem(DEFAULT_OPPORTUNITY_CODE)))
            .andExpect(jsonPath("$.[*].opportunityName").value(hasItem(DEFAULT_OPPORTUNITY_NAME)))
            .andExpect(jsonPath("$.[*].opportunityTypeName").value(hasItem(DEFAULT_OPPORTUNITY_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].closeDate").value(hasItem(DEFAULT_CLOSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].stageId").value(hasItem(DEFAULT_STAGE_ID.intValue())))
            .andExpect(jsonPath("$.[*].stageReasonId").value(hasItem(DEFAULT_STAGE_REASON_ID.intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].leadId").value(hasItem(DEFAULT_LEAD_ID.intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID.intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].salesPricePrd").value(hasItem(sameNumber(DEFAULT_SALES_PRICE_PRD))))
            .andExpect(jsonPath("$.[*].value").value(hasItem(sameNumber(DEFAULT_VALUE))));
    }

    @Test
    @Transactional
    void getSaleOpportunity() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get the saleOpportunity
        restSaleOpportunityMockMvc
            .perform(get(ENTITY_API_URL_ID, saleOpportunity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(saleOpportunity.getId().intValue()))
            .andExpect(jsonPath("$.opportunityId").value(DEFAULT_OPPORTUNITY_ID.intValue()))
            .andExpect(jsonPath("$.opportunityCode").value(DEFAULT_OPPORTUNITY_CODE))
            .andExpect(jsonPath("$.opportunityName").value(DEFAULT_OPPORTUNITY_NAME))
            .andExpect(jsonPath("$.opportunityTypeName").value(DEFAULT_OPPORTUNITY_TYPE_NAME))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.closeDate").value(DEFAULT_CLOSE_DATE.toString()))
            .andExpect(jsonPath("$.stageId").value(DEFAULT_STAGE_ID.intValue()))
            .andExpect(jsonPath("$.stageReasonId").value(DEFAULT_STAGE_REASON_ID.intValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.leadId").value(DEFAULT_LEAD_ID.intValue()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID.intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.salesPricePrd").value(sameNumber(DEFAULT_SALES_PRICE_PRD)))
            .andExpect(jsonPath("$.value").value(sameNumber(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getSaleOpportunitiesByIdFiltering() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        Long id = saleOpportunity.getId();

        defaultSaleOpportunityShouldBeFound("id.equals=" + id);
        defaultSaleOpportunityShouldNotBeFound("id.notEquals=" + id);

        defaultSaleOpportunityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSaleOpportunityShouldNotBeFound("id.greaterThan=" + id);

        defaultSaleOpportunityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSaleOpportunityShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityId equals to DEFAULT_OPPORTUNITY_ID
        defaultSaleOpportunityShouldBeFound("opportunityId.equals=" + DEFAULT_OPPORTUNITY_ID);

        // Get all the saleOpportunityList where opportunityId equals to UPDATED_OPPORTUNITY_ID
        defaultSaleOpportunityShouldNotBeFound("opportunityId.equals=" + UPDATED_OPPORTUNITY_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityId not equals to DEFAULT_OPPORTUNITY_ID
        defaultSaleOpportunityShouldNotBeFound("opportunityId.notEquals=" + DEFAULT_OPPORTUNITY_ID);

        // Get all the saleOpportunityList where opportunityId not equals to UPDATED_OPPORTUNITY_ID
        defaultSaleOpportunityShouldBeFound("opportunityId.notEquals=" + UPDATED_OPPORTUNITY_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityId in DEFAULT_OPPORTUNITY_ID or UPDATED_OPPORTUNITY_ID
        defaultSaleOpportunityShouldBeFound("opportunityId.in=" + DEFAULT_OPPORTUNITY_ID + "," + UPDATED_OPPORTUNITY_ID);

        // Get all the saleOpportunityList where opportunityId equals to UPDATED_OPPORTUNITY_ID
        defaultSaleOpportunityShouldNotBeFound("opportunityId.in=" + UPDATED_OPPORTUNITY_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityId is not null
        defaultSaleOpportunityShouldBeFound("opportunityId.specified=true");

        // Get all the saleOpportunityList where opportunityId is null
        defaultSaleOpportunityShouldNotBeFound("opportunityId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityId is greater than or equal to DEFAULT_OPPORTUNITY_ID
        defaultSaleOpportunityShouldBeFound("opportunityId.greaterThanOrEqual=" + DEFAULT_OPPORTUNITY_ID);

        // Get all the saleOpportunityList where opportunityId is greater than or equal to UPDATED_OPPORTUNITY_ID
        defaultSaleOpportunityShouldNotBeFound("opportunityId.greaterThanOrEqual=" + UPDATED_OPPORTUNITY_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityId is less than or equal to DEFAULT_OPPORTUNITY_ID
        defaultSaleOpportunityShouldBeFound("opportunityId.lessThanOrEqual=" + DEFAULT_OPPORTUNITY_ID);

        // Get all the saleOpportunityList where opportunityId is less than or equal to SMALLER_OPPORTUNITY_ID
        defaultSaleOpportunityShouldNotBeFound("opportunityId.lessThanOrEqual=" + SMALLER_OPPORTUNITY_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityIdIsLessThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityId is less than DEFAULT_OPPORTUNITY_ID
        defaultSaleOpportunityShouldNotBeFound("opportunityId.lessThan=" + DEFAULT_OPPORTUNITY_ID);

        // Get all the saleOpportunityList where opportunityId is less than UPDATED_OPPORTUNITY_ID
        defaultSaleOpportunityShouldBeFound("opportunityId.lessThan=" + UPDATED_OPPORTUNITY_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityId is greater than DEFAULT_OPPORTUNITY_ID
        defaultSaleOpportunityShouldNotBeFound("opportunityId.greaterThan=" + DEFAULT_OPPORTUNITY_ID);

        // Get all the saleOpportunityList where opportunityId is greater than SMALLER_OPPORTUNITY_ID
        defaultSaleOpportunityShouldBeFound("opportunityId.greaterThan=" + SMALLER_OPPORTUNITY_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityCode equals to DEFAULT_OPPORTUNITY_CODE
        defaultSaleOpportunityShouldBeFound("opportunityCode.equals=" + DEFAULT_OPPORTUNITY_CODE);

        // Get all the saleOpportunityList where opportunityCode equals to UPDATED_OPPORTUNITY_CODE
        defaultSaleOpportunityShouldNotBeFound("opportunityCode.equals=" + UPDATED_OPPORTUNITY_CODE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityCode not equals to DEFAULT_OPPORTUNITY_CODE
        defaultSaleOpportunityShouldNotBeFound("opportunityCode.notEquals=" + DEFAULT_OPPORTUNITY_CODE);

        // Get all the saleOpportunityList where opportunityCode not equals to UPDATED_OPPORTUNITY_CODE
        defaultSaleOpportunityShouldBeFound("opportunityCode.notEquals=" + UPDATED_OPPORTUNITY_CODE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityCodeIsInShouldWork() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityCode in DEFAULT_OPPORTUNITY_CODE or UPDATED_OPPORTUNITY_CODE
        defaultSaleOpportunityShouldBeFound("opportunityCode.in=" + DEFAULT_OPPORTUNITY_CODE + "," + UPDATED_OPPORTUNITY_CODE);

        // Get all the saleOpportunityList where opportunityCode equals to UPDATED_OPPORTUNITY_CODE
        defaultSaleOpportunityShouldNotBeFound("opportunityCode.in=" + UPDATED_OPPORTUNITY_CODE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityCode is not null
        defaultSaleOpportunityShouldBeFound("opportunityCode.specified=true");

        // Get all the saleOpportunityList where opportunityCode is null
        defaultSaleOpportunityShouldNotBeFound("opportunityCode.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityCodeContainsSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityCode contains DEFAULT_OPPORTUNITY_CODE
        defaultSaleOpportunityShouldBeFound("opportunityCode.contains=" + DEFAULT_OPPORTUNITY_CODE);

        // Get all the saleOpportunityList where opportunityCode contains UPDATED_OPPORTUNITY_CODE
        defaultSaleOpportunityShouldNotBeFound("opportunityCode.contains=" + UPDATED_OPPORTUNITY_CODE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityCodeNotContainsSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityCode does not contain DEFAULT_OPPORTUNITY_CODE
        defaultSaleOpportunityShouldNotBeFound("opportunityCode.doesNotContain=" + DEFAULT_OPPORTUNITY_CODE);

        // Get all the saleOpportunityList where opportunityCode does not contain UPDATED_OPPORTUNITY_CODE
        defaultSaleOpportunityShouldBeFound("opportunityCode.doesNotContain=" + UPDATED_OPPORTUNITY_CODE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityName equals to DEFAULT_OPPORTUNITY_NAME
        defaultSaleOpportunityShouldBeFound("opportunityName.equals=" + DEFAULT_OPPORTUNITY_NAME);

        // Get all the saleOpportunityList where opportunityName equals to UPDATED_OPPORTUNITY_NAME
        defaultSaleOpportunityShouldNotBeFound("opportunityName.equals=" + UPDATED_OPPORTUNITY_NAME);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityName not equals to DEFAULT_OPPORTUNITY_NAME
        defaultSaleOpportunityShouldNotBeFound("opportunityName.notEquals=" + DEFAULT_OPPORTUNITY_NAME);

        // Get all the saleOpportunityList where opportunityName not equals to UPDATED_OPPORTUNITY_NAME
        defaultSaleOpportunityShouldBeFound("opportunityName.notEquals=" + UPDATED_OPPORTUNITY_NAME);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityNameIsInShouldWork() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityName in DEFAULT_OPPORTUNITY_NAME or UPDATED_OPPORTUNITY_NAME
        defaultSaleOpportunityShouldBeFound("opportunityName.in=" + DEFAULT_OPPORTUNITY_NAME + "," + UPDATED_OPPORTUNITY_NAME);

        // Get all the saleOpportunityList where opportunityName equals to UPDATED_OPPORTUNITY_NAME
        defaultSaleOpportunityShouldNotBeFound("opportunityName.in=" + UPDATED_OPPORTUNITY_NAME);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityName is not null
        defaultSaleOpportunityShouldBeFound("opportunityName.specified=true");

        // Get all the saleOpportunityList where opportunityName is null
        defaultSaleOpportunityShouldNotBeFound("opportunityName.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityNameContainsSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityName contains DEFAULT_OPPORTUNITY_NAME
        defaultSaleOpportunityShouldBeFound("opportunityName.contains=" + DEFAULT_OPPORTUNITY_NAME);

        // Get all the saleOpportunityList where opportunityName contains UPDATED_OPPORTUNITY_NAME
        defaultSaleOpportunityShouldNotBeFound("opportunityName.contains=" + UPDATED_OPPORTUNITY_NAME);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityNameNotContainsSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityName does not contain DEFAULT_OPPORTUNITY_NAME
        defaultSaleOpportunityShouldNotBeFound("opportunityName.doesNotContain=" + DEFAULT_OPPORTUNITY_NAME);

        // Get all the saleOpportunityList where opportunityName does not contain UPDATED_OPPORTUNITY_NAME
        defaultSaleOpportunityShouldBeFound("opportunityName.doesNotContain=" + UPDATED_OPPORTUNITY_NAME);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityTypeName equals to DEFAULT_OPPORTUNITY_TYPE_NAME
        defaultSaleOpportunityShouldBeFound("opportunityTypeName.equals=" + DEFAULT_OPPORTUNITY_TYPE_NAME);

        // Get all the saleOpportunityList where opportunityTypeName equals to UPDATED_OPPORTUNITY_TYPE_NAME
        defaultSaleOpportunityShouldNotBeFound("opportunityTypeName.equals=" + UPDATED_OPPORTUNITY_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityTypeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityTypeName not equals to DEFAULT_OPPORTUNITY_TYPE_NAME
        defaultSaleOpportunityShouldNotBeFound("opportunityTypeName.notEquals=" + DEFAULT_OPPORTUNITY_TYPE_NAME);

        // Get all the saleOpportunityList where opportunityTypeName not equals to UPDATED_OPPORTUNITY_TYPE_NAME
        defaultSaleOpportunityShouldBeFound("opportunityTypeName.notEquals=" + UPDATED_OPPORTUNITY_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityTypeName in DEFAULT_OPPORTUNITY_TYPE_NAME or UPDATED_OPPORTUNITY_TYPE_NAME
        defaultSaleOpportunityShouldBeFound(
            "opportunityTypeName.in=" + DEFAULT_OPPORTUNITY_TYPE_NAME + "," + UPDATED_OPPORTUNITY_TYPE_NAME
        );

        // Get all the saleOpportunityList where opportunityTypeName equals to UPDATED_OPPORTUNITY_TYPE_NAME
        defaultSaleOpportunityShouldNotBeFound("opportunityTypeName.in=" + UPDATED_OPPORTUNITY_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityTypeName is not null
        defaultSaleOpportunityShouldBeFound("opportunityTypeName.specified=true");

        // Get all the saleOpportunityList where opportunityTypeName is null
        defaultSaleOpportunityShouldNotBeFound("opportunityTypeName.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityTypeNameContainsSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityTypeName contains DEFAULT_OPPORTUNITY_TYPE_NAME
        defaultSaleOpportunityShouldBeFound("opportunityTypeName.contains=" + DEFAULT_OPPORTUNITY_TYPE_NAME);

        // Get all the saleOpportunityList where opportunityTypeName contains UPDATED_OPPORTUNITY_TYPE_NAME
        defaultSaleOpportunityShouldNotBeFound("opportunityTypeName.contains=" + UPDATED_OPPORTUNITY_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByOpportunityTypeNameNotContainsSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where opportunityTypeName does not contain DEFAULT_OPPORTUNITY_TYPE_NAME
        defaultSaleOpportunityShouldNotBeFound("opportunityTypeName.doesNotContain=" + DEFAULT_OPPORTUNITY_TYPE_NAME);

        // Get all the saleOpportunityList where opportunityTypeName does not contain UPDATED_OPPORTUNITY_TYPE_NAME
        defaultSaleOpportunityShouldBeFound("opportunityTypeName.doesNotContain=" + UPDATED_OPPORTUNITY_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where startDate equals to DEFAULT_START_DATE
        defaultSaleOpportunityShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the saleOpportunityList where startDate equals to UPDATED_START_DATE
        defaultSaleOpportunityShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where startDate not equals to DEFAULT_START_DATE
        defaultSaleOpportunityShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the saleOpportunityList where startDate not equals to UPDATED_START_DATE
        defaultSaleOpportunityShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultSaleOpportunityShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the saleOpportunityList where startDate equals to UPDATED_START_DATE
        defaultSaleOpportunityShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where startDate is not null
        defaultSaleOpportunityShouldBeFound("startDate.specified=true");

        // Get all the saleOpportunityList where startDate is null
        defaultSaleOpportunityShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultSaleOpportunityShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the saleOpportunityList where startDate is greater than or equal to UPDATED_START_DATE
        defaultSaleOpportunityShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where startDate is less than or equal to DEFAULT_START_DATE
        defaultSaleOpportunityShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the saleOpportunityList where startDate is less than or equal to SMALLER_START_DATE
        defaultSaleOpportunityShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where startDate is less than DEFAULT_START_DATE
        defaultSaleOpportunityShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the saleOpportunityList where startDate is less than UPDATED_START_DATE
        defaultSaleOpportunityShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where startDate is greater than DEFAULT_START_DATE
        defaultSaleOpportunityShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the saleOpportunityList where startDate is greater than SMALLER_START_DATE
        defaultSaleOpportunityShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByCloseDateIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where closeDate equals to DEFAULT_CLOSE_DATE
        defaultSaleOpportunityShouldBeFound("closeDate.equals=" + DEFAULT_CLOSE_DATE);

        // Get all the saleOpportunityList where closeDate equals to UPDATED_CLOSE_DATE
        defaultSaleOpportunityShouldNotBeFound("closeDate.equals=" + UPDATED_CLOSE_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByCloseDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where closeDate not equals to DEFAULT_CLOSE_DATE
        defaultSaleOpportunityShouldNotBeFound("closeDate.notEquals=" + DEFAULT_CLOSE_DATE);

        // Get all the saleOpportunityList where closeDate not equals to UPDATED_CLOSE_DATE
        defaultSaleOpportunityShouldBeFound("closeDate.notEquals=" + UPDATED_CLOSE_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByCloseDateIsInShouldWork() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where closeDate in DEFAULT_CLOSE_DATE or UPDATED_CLOSE_DATE
        defaultSaleOpportunityShouldBeFound("closeDate.in=" + DEFAULT_CLOSE_DATE + "," + UPDATED_CLOSE_DATE);

        // Get all the saleOpportunityList where closeDate equals to UPDATED_CLOSE_DATE
        defaultSaleOpportunityShouldNotBeFound("closeDate.in=" + UPDATED_CLOSE_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByCloseDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where closeDate is not null
        defaultSaleOpportunityShouldBeFound("closeDate.specified=true");

        // Get all the saleOpportunityList where closeDate is null
        defaultSaleOpportunityShouldNotBeFound("closeDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByCloseDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where closeDate is greater than or equal to DEFAULT_CLOSE_DATE
        defaultSaleOpportunityShouldBeFound("closeDate.greaterThanOrEqual=" + DEFAULT_CLOSE_DATE);

        // Get all the saleOpportunityList where closeDate is greater than or equal to UPDATED_CLOSE_DATE
        defaultSaleOpportunityShouldNotBeFound("closeDate.greaterThanOrEqual=" + UPDATED_CLOSE_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByCloseDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where closeDate is less than or equal to DEFAULT_CLOSE_DATE
        defaultSaleOpportunityShouldBeFound("closeDate.lessThanOrEqual=" + DEFAULT_CLOSE_DATE);

        // Get all the saleOpportunityList where closeDate is less than or equal to SMALLER_CLOSE_DATE
        defaultSaleOpportunityShouldNotBeFound("closeDate.lessThanOrEqual=" + SMALLER_CLOSE_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByCloseDateIsLessThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where closeDate is less than DEFAULT_CLOSE_DATE
        defaultSaleOpportunityShouldNotBeFound("closeDate.lessThan=" + DEFAULT_CLOSE_DATE);

        // Get all the saleOpportunityList where closeDate is less than UPDATED_CLOSE_DATE
        defaultSaleOpportunityShouldBeFound("closeDate.lessThan=" + UPDATED_CLOSE_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByCloseDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where closeDate is greater than DEFAULT_CLOSE_DATE
        defaultSaleOpportunityShouldNotBeFound("closeDate.greaterThan=" + DEFAULT_CLOSE_DATE);

        // Get all the saleOpportunityList where closeDate is greater than SMALLER_CLOSE_DATE
        defaultSaleOpportunityShouldBeFound("closeDate.greaterThan=" + SMALLER_CLOSE_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStageIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where stageId equals to DEFAULT_STAGE_ID
        defaultSaleOpportunityShouldBeFound("stageId.equals=" + DEFAULT_STAGE_ID);

        // Get all the saleOpportunityList where stageId equals to UPDATED_STAGE_ID
        defaultSaleOpportunityShouldNotBeFound("stageId.equals=" + UPDATED_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStageIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where stageId not equals to DEFAULT_STAGE_ID
        defaultSaleOpportunityShouldNotBeFound("stageId.notEquals=" + DEFAULT_STAGE_ID);

        // Get all the saleOpportunityList where stageId not equals to UPDATED_STAGE_ID
        defaultSaleOpportunityShouldBeFound("stageId.notEquals=" + UPDATED_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStageIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where stageId in DEFAULT_STAGE_ID or UPDATED_STAGE_ID
        defaultSaleOpportunityShouldBeFound("stageId.in=" + DEFAULT_STAGE_ID + "," + UPDATED_STAGE_ID);

        // Get all the saleOpportunityList where stageId equals to UPDATED_STAGE_ID
        defaultSaleOpportunityShouldNotBeFound("stageId.in=" + UPDATED_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStageIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where stageId is not null
        defaultSaleOpportunityShouldBeFound("stageId.specified=true");

        // Get all the saleOpportunityList where stageId is null
        defaultSaleOpportunityShouldNotBeFound("stageId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStageIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where stageId is greater than or equal to DEFAULT_STAGE_ID
        defaultSaleOpportunityShouldBeFound("stageId.greaterThanOrEqual=" + DEFAULT_STAGE_ID);

        // Get all the saleOpportunityList where stageId is greater than or equal to UPDATED_STAGE_ID
        defaultSaleOpportunityShouldNotBeFound("stageId.greaterThanOrEqual=" + UPDATED_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStageIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where stageId is less than or equal to DEFAULT_STAGE_ID
        defaultSaleOpportunityShouldBeFound("stageId.lessThanOrEqual=" + DEFAULT_STAGE_ID);

        // Get all the saleOpportunityList where stageId is less than or equal to SMALLER_STAGE_ID
        defaultSaleOpportunityShouldNotBeFound("stageId.lessThanOrEqual=" + SMALLER_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStageIdIsLessThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where stageId is less than DEFAULT_STAGE_ID
        defaultSaleOpportunityShouldNotBeFound("stageId.lessThan=" + DEFAULT_STAGE_ID);

        // Get all the saleOpportunityList where stageId is less than UPDATED_STAGE_ID
        defaultSaleOpportunityShouldBeFound("stageId.lessThan=" + UPDATED_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStageIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where stageId is greater than DEFAULT_STAGE_ID
        defaultSaleOpportunityShouldNotBeFound("stageId.greaterThan=" + DEFAULT_STAGE_ID);

        // Get all the saleOpportunityList where stageId is greater than SMALLER_STAGE_ID
        defaultSaleOpportunityShouldBeFound("stageId.greaterThan=" + SMALLER_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStageReasonIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where stageReasonId equals to DEFAULT_STAGE_REASON_ID
        defaultSaleOpportunityShouldBeFound("stageReasonId.equals=" + DEFAULT_STAGE_REASON_ID);

        // Get all the saleOpportunityList where stageReasonId equals to UPDATED_STAGE_REASON_ID
        defaultSaleOpportunityShouldNotBeFound("stageReasonId.equals=" + UPDATED_STAGE_REASON_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStageReasonIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where stageReasonId not equals to DEFAULT_STAGE_REASON_ID
        defaultSaleOpportunityShouldNotBeFound("stageReasonId.notEquals=" + DEFAULT_STAGE_REASON_ID);

        // Get all the saleOpportunityList where stageReasonId not equals to UPDATED_STAGE_REASON_ID
        defaultSaleOpportunityShouldBeFound("stageReasonId.notEquals=" + UPDATED_STAGE_REASON_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStageReasonIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where stageReasonId in DEFAULT_STAGE_REASON_ID or UPDATED_STAGE_REASON_ID
        defaultSaleOpportunityShouldBeFound("stageReasonId.in=" + DEFAULT_STAGE_REASON_ID + "," + UPDATED_STAGE_REASON_ID);

        // Get all the saleOpportunityList where stageReasonId equals to UPDATED_STAGE_REASON_ID
        defaultSaleOpportunityShouldNotBeFound("stageReasonId.in=" + UPDATED_STAGE_REASON_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStageReasonIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where stageReasonId is not null
        defaultSaleOpportunityShouldBeFound("stageReasonId.specified=true");

        // Get all the saleOpportunityList where stageReasonId is null
        defaultSaleOpportunityShouldNotBeFound("stageReasonId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStageReasonIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where stageReasonId is greater than or equal to DEFAULT_STAGE_REASON_ID
        defaultSaleOpportunityShouldBeFound("stageReasonId.greaterThanOrEqual=" + DEFAULT_STAGE_REASON_ID);

        // Get all the saleOpportunityList where stageReasonId is greater than or equal to UPDATED_STAGE_REASON_ID
        defaultSaleOpportunityShouldNotBeFound("stageReasonId.greaterThanOrEqual=" + UPDATED_STAGE_REASON_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStageReasonIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where stageReasonId is less than or equal to DEFAULT_STAGE_REASON_ID
        defaultSaleOpportunityShouldBeFound("stageReasonId.lessThanOrEqual=" + DEFAULT_STAGE_REASON_ID);

        // Get all the saleOpportunityList where stageReasonId is less than or equal to SMALLER_STAGE_REASON_ID
        defaultSaleOpportunityShouldNotBeFound("stageReasonId.lessThanOrEqual=" + SMALLER_STAGE_REASON_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStageReasonIdIsLessThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where stageReasonId is less than DEFAULT_STAGE_REASON_ID
        defaultSaleOpportunityShouldNotBeFound("stageReasonId.lessThan=" + DEFAULT_STAGE_REASON_ID);

        // Get all the saleOpportunityList where stageReasonId is less than UPDATED_STAGE_REASON_ID
        defaultSaleOpportunityShouldBeFound("stageReasonId.lessThan=" + UPDATED_STAGE_REASON_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByStageReasonIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where stageReasonId is greater than DEFAULT_STAGE_REASON_ID
        defaultSaleOpportunityShouldNotBeFound("stageReasonId.greaterThan=" + DEFAULT_STAGE_REASON_ID);

        // Get all the saleOpportunityList where stageReasonId is greater than SMALLER_STAGE_REASON_ID
        defaultSaleOpportunityShouldBeFound("stageReasonId.greaterThan=" + SMALLER_STAGE_REASON_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultSaleOpportunityShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the saleOpportunityList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultSaleOpportunityShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByEmployeeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where employeeId not equals to DEFAULT_EMPLOYEE_ID
        defaultSaleOpportunityShouldNotBeFound("employeeId.notEquals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the saleOpportunityList where employeeId not equals to UPDATED_EMPLOYEE_ID
        defaultSaleOpportunityShouldBeFound("employeeId.notEquals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultSaleOpportunityShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the saleOpportunityList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultSaleOpportunityShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where employeeId is not null
        defaultSaleOpportunityShouldBeFound("employeeId.specified=true");

        // Get all the saleOpportunityList where employeeId is null
        defaultSaleOpportunityShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultSaleOpportunityShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the saleOpportunityList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultSaleOpportunityShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultSaleOpportunityShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the saleOpportunityList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultSaleOpportunityShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultSaleOpportunityShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the saleOpportunityList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultSaleOpportunityShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultSaleOpportunityShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the saleOpportunityList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultSaleOpportunityShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByLeadIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where leadId equals to DEFAULT_LEAD_ID
        defaultSaleOpportunityShouldBeFound("leadId.equals=" + DEFAULT_LEAD_ID);

        // Get all the saleOpportunityList where leadId equals to UPDATED_LEAD_ID
        defaultSaleOpportunityShouldNotBeFound("leadId.equals=" + UPDATED_LEAD_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByLeadIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where leadId not equals to DEFAULT_LEAD_ID
        defaultSaleOpportunityShouldNotBeFound("leadId.notEquals=" + DEFAULT_LEAD_ID);

        // Get all the saleOpportunityList where leadId not equals to UPDATED_LEAD_ID
        defaultSaleOpportunityShouldBeFound("leadId.notEquals=" + UPDATED_LEAD_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByLeadIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where leadId in DEFAULT_LEAD_ID or UPDATED_LEAD_ID
        defaultSaleOpportunityShouldBeFound("leadId.in=" + DEFAULT_LEAD_ID + "," + UPDATED_LEAD_ID);

        // Get all the saleOpportunityList where leadId equals to UPDATED_LEAD_ID
        defaultSaleOpportunityShouldNotBeFound("leadId.in=" + UPDATED_LEAD_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByLeadIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where leadId is not null
        defaultSaleOpportunityShouldBeFound("leadId.specified=true");

        // Get all the saleOpportunityList where leadId is null
        defaultSaleOpportunityShouldNotBeFound("leadId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByLeadIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where leadId is greater than or equal to DEFAULT_LEAD_ID
        defaultSaleOpportunityShouldBeFound("leadId.greaterThanOrEqual=" + DEFAULT_LEAD_ID);

        // Get all the saleOpportunityList where leadId is greater than or equal to UPDATED_LEAD_ID
        defaultSaleOpportunityShouldNotBeFound("leadId.greaterThanOrEqual=" + UPDATED_LEAD_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByLeadIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where leadId is less than or equal to DEFAULT_LEAD_ID
        defaultSaleOpportunityShouldBeFound("leadId.lessThanOrEqual=" + DEFAULT_LEAD_ID);

        // Get all the saleOpportunityList where leadId is less than or equal to SMALLER_LEAD_ID
        defaultSaleOpportunityShouldNotBeFound("leadId.lessThanOrEqual=" + SMALLER_LEAD_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByLeadIdIsLessThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where leadId is less than DEFAULT_LEAD_ID
        defaultSaleOpportunityShouldNotBeFound("leadId.lessThan=" + DEFAULT_LEAD_ID);

        // Get all the saleOpportunityList where leadId is less than UPDATED_LEAD_ID
        defaultSaleOpportunityShouldBeFound("leadId.lessThan=" + UPDATED_LEAD_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByLeadIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where leadId is greater than DEFAULT_LEAD_ID
        defaultSaleOpportunityShouldNotBeFound("leadId.greaterThan=" + DEFAULT_LEAD_ID);

        // Get all the saleOpportunityList where leadId is greater than SMALLER_LEAD_ID
        defaultSaleOpportunityShouldBeFound("leadId.greaterThan=" + SMALLER_LEAD_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByCurrencyCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where currencyCode equals to DEFAULT_CURRENCY_CODE
        defaultSaleOpportunityShouldBeFound("currencyCode.equals=" + DEFAULT_CURRENCY_CODE);

        // Get all the saleOpportunityList where currencyCode equals to UPDATED_CURRENCY_CODE
        defaultSaleOpportunityShouldNotBeFound("currencyCode.equals=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByCurrencyCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where currencyCode not equals to DEFAULT_CURRENCY_CODE
        defaultSaleOpportunityShouldNotBeFound("currencyCode.notEquals=" + DEFAULT_CURRENCY_CODE);

        // Get all the saleOpportunityList where currencyCode not equals to UPDATED_CURRENCY_CODE
        defaultSaleOpportunityShouldBeFound("currencyCode.notEquals=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByCurrencyCodeIsInShouldWork() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where currencyCode in DEFAULT_CURRENCY_CODE or UPDATED_CURRENCY_CODE
        defaultSaleOpportunityShouldBeFound("currencyCode.in=" + DEFAULT_CURRENCY_CODE + "," + UPDATED_CURRENCY_CODE);

        // Get all the saleOpportunityList where currencyCode equals to UPDATED_CURRENCY_CODE
        defaultSaleOpportunityShouldNotBeFound("currencyCode.in=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByCurrencyCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where currencyCode is not null
        defaultSaleOpportunityShouldBeFound("currencyCode.specified=true");

        // Get all the saleOpportunityList where currencyCode is null
        defaultSaleOpportunityShouldNotBeFound("currencyCode.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByCurrencyCodeContainsSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where currencyCode contains DEFAULT_CURRENCY_CODE
        defaultSaleOpportunityShouldBeFound("currencyCode.contains=" + DEFAULT_CURRENCY_CODE);

        // Get all the saleOpportunityList where currencyCode contains UPDATED_CURRENCY_CODE
        defaultSaleOpportunityShouldNotBeFound("currencyCode.contains=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByCurrencyCodeNotContainsSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where currencyCode does not contain DEFAULT_CURRENCY_CODE
        defaultSaleOpportunityShouldNotBeFound("currencyCode.doesNotContain=" + DEFAULT_CURRENCY_CODE);

        // Get all the saleOpportunityList where currencyCode does not contain UPDATED_CURRENCY_CODE
        defaultSaleOpportunityShouldBeFound("currencyCode.doesNotContain=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByAccountIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where accountId equals to DEFAULT_ACCOUNT_ID
        defaultSaleOpportunityShouldBeFound("accountId.equals=" + DEFAULT_ACCOUNT_ID);

        // Get all the saleOpportunityList where accountId equals to UPDATED_ACCOUNT_ID
        defaultSaleOpportunityShouldNotBeFound("accountId.equals=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByAccountIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where accountId not equals to DEFAULT_ACCOUNT_ID
        defaultSaleOpportunityShouldNotBeFound("accountId.notEquals=" + DEFAULT_ACCOUNT_ID);

        // Get all the saleOpportunityList where accountId not equals to UPDATED_ACCOUNT_ID
        defaultSaleOpportunityShouldBeFound("accountId.notEquals=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByAccountIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where accountId in DEFAULT_ACCOUNT_ID or UPDATED_ACCOUNT_ID
        defaultSaleOpportunityShouldBeFound("accountId.in=" + DEFAULT_ACCOUNT_ID + "," + UPDATED_ACCOUNT_ID);

        // Get all the saleOpportunityList where accountId equals to UPDATED_ACCOUNT_ID
        defaultSaleOpportunityShouldNotBeFound("accountId.in=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByAccountIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where accountId is not null
        defaultSaleOpportunityShouldBeFound("accountId.specified=true");

        // Get all the saleOpportunityList where accountId is null
        defaultSaleOpportunityShouldNotBeFound("accountId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByAccountIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where accountId is greater than or equal to DEFAULT_ACCOUNT_ID
        defaultSaleOpportunityShouldBeFound("accountId.greaterThanOrEqual=" + DEFAULT_ACCOUNT_ID);

        // Get all the saleOpportunityList where accountId is greater than or equal to UPDATED_ACCOUNT_ID
        defaultSaleOpportunityShouldNotBeFound("accountId.greaterThanOrEqual=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByAccountIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where accountId is less than or equal to DEFAULT_ACCOUNT_ID
        defaultSaleOpportunityShouldBeFound("accountId.lessThanOrEqual=" + DEFAULT_ACCOUNT_ID);

        // Get all the saleOpportunityList where accountId is less than or equal to SMALLER_ACCOUNT_ID
        defaultSaleOpportunityShouldNotBeFound("accountId.lessThanOrEqual=" + SMALLER_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByAccountIdIsLessThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where accountId is less than DEFAULT_ACCOUNT_ID
        defaultSaleOpportunityShouldNotBeFound("accountId.lessThan=" + DEFAULT_ACCOUNT_ID);

        // Get all the saleOpportunityList where accountId is less than UPDATED_ACCOUNT_ID
        defaultSaleOpportunityShouldBeFound("accountId.lessThan=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByAccountIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where accountId is greater than DEFAULT_ACCOUNT_ID
        defaultSaleOpportunityShouldNotBeFound("accountId.greaterThan=" + DEFAULT_ACCOUNT_ID);

        // Get all the saleOpportunityList where accountId is greater than SMALLER_ACCOUNT_ID
        defaultSaleOpportunityShouldBeFound("accountId.greaterThan=" + SMALLER_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByProductIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where productId equals to DEFAULT_PRODUCT_ID
        defaultSaleOpportunityShouldBeFound("productId.equals=" + DEFAULT_PRODUCT_ID);

        // Get all the saleOpportunityList where productId equals to UPDATED_PRODUCT_ID
        defaultSaleOpportunityShouldNotBeFound("productId.equals=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByProductIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where productId not equals to DEFAULT_PRODUCT_ID
        defaultSaleOpportunityShouldNotBeFound("productId.notEquals=" + DEFAULT_PRODUCT_ID);

        // Get all the saleOpportunityList where productId not equals to UPDATED_PRODUCT_ID
        defaultSaleOpportunityShouldBeFound("productId.notEquals=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByProductIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where productId in DEFAULT_PRODUCT_ID or UPDATED_PRODUCT_ID
        defaultSaleOpportunityShouldBeFound("productId.in=" + DEFAULT_PRODUCT_ID + "," + UPDATED_PRODUCT_ID);

        // Get all the saleOpportunityList where productId equals to UPDATED_PRODUCT_ID
        defaultSaleOpportunityShouldNotBeFound("productId.in=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByProductIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where productId is not null
        defaultSaleOpportunityShouldBeFound("productId.specified=true");

        // Get all the saleOpportunityList where productId is null
        defaultSaleOpportunityShouldNotBeFound("productId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByProductIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where productId is greater than or equal to DEFAULT_PRODUCT_ID
        defaultSaleOpportunityShouldBeFound("productId.greaterThanOrEqual=" + DEFAULT_PRODUCT_ID);

        // Get all the saleOpportunityList where productId is greater than or equal to UPDATED_PRODUCT_ID
        defaultSaleOpportunityShouldNotBeFound("productId.greaterThanOrEqual=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByProductIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where productId is less than or equal to DEFAULT_PRODUCT_ID
        defaultSaleOpportunityShouldBeFound("productId.lessThanOrEqual=" + DEFAULT_PRODUCT_ID);

        // Get all the saleOpportunityList where productId is less than or equal to SMALLER_PRODUCT_ID
        defaultSaleOpportunityShouldNotBeFound("productId.lessThanOrEqual=" + SMALLER_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByProductIdIsLessThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where productId is less than DEFAULT_PRODUCT_ID
        defaultSaleOpportunityShouldNotBeFound("productId.lessThan=" + DEFAULT_PRODUCT_ID);

        // Get all the saleOpportunityList where productId is less than UPDATED_PRODUCT_ID
        defaultSaleOpportunityShouldBeFound("productId.lessThan=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByProductIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where productId is greater than DEFAULT_PRODUCT_ID
        defaultSaleOpportunityShouldNotBeFound("productId.greaterThan=" + DEFAULT_PRODUCT_ID);

        // Get all the saleOpportunityList where productId is greater than SMALLER_PRODUCT_ID
        defaultSaleOpportunityShouldBeFound("productId.greaterThan=" + SMALLER_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesBySalesPricePrdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where salesPricePrd equals to DEFAULT_SALES_PRICE_PRD
        defaultSaleOpportunityShouldBeFound("salesPricePrd.equals=" + DEFAULT_SALES_PRICE_PRD);

        // Get all the saleOpportunityList where salesPricePrd equals to UPDATED_SALES_PRICE_PRD
        defaultSaleOpportunityShouldNotBeFound("salesPricePrd.equals=" + UPDATED_SALES_PRICE_PRD);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesBySalesPricePrdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where salesPricePrd not equals to DEFAULT_SALES_PRICE_PRD
        defaultSaleOpportunityShouldNotBeFound("salesPricePrd.notEquals=" + DEFAULT_SALES_PRICE_PRD);

        // Get all the saleOpportunityList where salesPricePrd not equals to UPDATED_SALES_PRICE_PRD
        defaultSaleOpportunityShouldBeFound("salesPricePrd.notEquals=" + UPDATED_SALES_PRICE_PRD);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesBySalesPricePrdIsInShouldWork() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where salesPricePrd in DEFAULT_SALES_PRICE_PRD or UPDATED_SALES_PRICE_PRD
        defaultSaleOpportunityShouldBeFound("salesPricePrd.in=" + DEFAULT_SALES_PRICE_PRD + "," + UPDATED_SALES_PRICE_PRD);

        // Get all the saleOpportunityList where salesPricePrd equals to UPDATED_SALES_PRICE_PRD
        defaultSaleOpportunityShouldNotBeFound("salesPricePrd.in=" + UPDATED_SALES_PRICE_PRD);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesBySalesPricePrdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where salesPricePrd is not null
        defaultSaleOpportunityShouldBeFound("salesPricePrd.specified=true");

        // Get all the saleOpportunityList where salesPricePrd is null
        defaultSaleOpportunityShouldNotBeFound("salesPricePrd.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesBySalesPricePrdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where salesPricePrd is greater than or equal to DEFAULT_SALES_PRICE_PRD
        defaultSaleOpportunityShouldBeFound("salesPricePrd.greaterThanOrEqual=" + DEFAULT_SALES_PRICE_PRD);

        // Get all the saleOpportunityList where salesPricePrd is greater than or equal to UPDATED_SALES_PRICE_PRD
        defaultSaleOpportunityShouldNotBeFound("salesPricePrd.greaterThanOrEqual=" + UPDATED_SALES_PRICE_PRD);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesBySalesPricePrdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where salesPricePrd is less than or equal to DEFAULT_SALES_PRICE_PRD
        defaultSaleOpportunityShouldBeFound("salesPricePrd.lessThanOrEqual=" + DEFAULT_SALES_PRICE_PRD);

        // Get all the saleOpportunityList where salesPricePrd is less than or equal to SMALLER_SALES_PRICE_PRD
        defaultSaleOpportunityShouldNotBeFound("salesPricePrd.lessThanOrEqual=" + SMALLER_SALES_PRICE_PRD);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesBySalesPricePrdIsLessThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where salesPricePrd is less than DEFAULT_SALES_PRICE_PRD
        defaultSaleOpportunityShouldNotBeFound("salesPricePrd.lessThan=" + DEFAULT_SALES_PRICE_PRD);

        // Get all the saleOpportunityList where salesPricePrd is less than UPDATED_SALES_PRICE_PRD
        defaultSaleOpportunityShouldBeFound("salesPricePrd.lessThan=" + UPDATED_SALES_PRICE_PRD);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesBySalesPricePrdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where salesPricePrd is greater than DEFAULT_SALES_PRICE_PRD
        defaultSaleOpportunityShouldNotBeFound("salesPricePrd.greaterThan=" + DEFAULT_SALES_PRICE_PRD);

        // Get all the saleOpportunityList where salesPricePrd is greater than SMALLER_SALES_PRICE_PRD
        defaultSaleOpportunityShouldBeFound("salesPricePrd.greaterThan=" + SMALLER_SALES_PRICE_PRD);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where value equals to DEFAULT_VALUE
        defaultSaleOpportunityShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the saleOpportunityList where value equals to UPDATED_VALUE
        defaultSaleOpportunityShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where value not equals to DEFAULT_VALUE
        defaultSaleOpportunityShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the saleOpportunityList where value not equals to UPDATED_VALUE
        defaultSaleOpportunityShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultSaleOpportunityShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the saleOpportunityList where value equals to UPDATED_VALUE
        defaultSaleOpportunityShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where value is not null
        defaultSaleOpportunityShouldBeFound("value.specified=true");

        // Get all the saleOpportunityList where value is null
        defaultSaleOpportunityShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where value is greater than or equal to DEFAULT_VALUE
        defaultSaleOpportunityShouldBeFound("value.greaterThanOrEqual=" + DEFAULT_VALUE);

        // Get all the saleOpportunityList where value is greater than or equal to UPDATED_VALUE
        defaultSaleOpportunityShouldNotBeFound("value.greaterThanOrEqual=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where value is less than or equal to DEFAULT_VALUE
        defaultSaleOpportunityShouldBeFound("value.lessThanOrEqual=" + DEFAULT_VALUE);

        // Get all the saleOpportunityList where value is less than or equal to SMALLER_VALUE
        defaultSaleOpportunityShouldNotBeFound("value.lessThanOrEqual=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where value is less than DEFAULT_VALUE
        defaultSaleOpportunityShouldNotBeFound("value.lessThan=" + DEFAULT_VALUE);

        // Get all the saleOpportunityList where value is less than UPDATED_VALUE
        defaultSaleOpportunityShouldBeFound("value.lessThan=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleOpportunitiesByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        // Get all the saleOpportunityList where value is greater than DEFAULT_VALUE
        defaultSaleOpportunityShouldNotBeFound("value.greaterThan=" + DEFAULT_VALUE);

        // Get all the saleOpportunityList where value is greater than SMALLER_VALUE
        defaultSaleOpportunityShouldBeFound("value.greaterThan=" + SMALLER_VALUE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSaleOpportunityShouldBeFound(String filter) throws Exception {
        restSaleOpportunityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleOpportunity.getId().intValue())))
            .andExpect(jsonPath("$.[*].opportunityId").value(hasItem(DEFAULT_OPPORTUNITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].opportunityCode").value(hasItem(DEFAULT_OPPORTUNITY_CODE)))
            .andExpect(jsonPath("$.[*].opportunityName").value(hasItem(DEFAULT_OPPORTUNITY_NAME)))
            .andExpect(jsonPath("$.[*].opportunityTypeName").value(hasItem(DEFAULT_OPPORTUNITY_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].closeDate").value(hasItem(DEFAULT_CLOSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].stageId").value(hasItem(DEFAULT_STAGE_ID.intValue())))
            .andExpect(jsonPath("$.[*].stageReasonId").value(hasItem(DEFAULT_STAGE_REASON_ID.intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].leadId").value(hasItem(DEFAULT_LEAD_ID.intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID.intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].salesPricePrd").value(hasItem(sameNumber(DEFAULT_SALES_PRICE_PRD))))
            .andExpect(jsonPath("$.[*].value").value(hasItem(sameNumber(DEFAULT_VALUE))));

        // Check, that the count call also returns 1
        restSaleOpportunityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSaleOpportunityShouldNotBeFound(String filter) throws Exception {
        restSaleOpportunityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSaleOpportunityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSaleOpportunity() throws Exception {
        // Get the saleOpportunity
        restSaleOpportunityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSaleOpportunity() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        int databaseSizeBeforeUpdate = saleOpportunityRepository.findAll().size();

        // Update the saleOpportunity
        SaleOpportunity updatedSaleOpportunity = saleOpportunityRepository.findById(saleOpportunity.getId()).get();
        // Disconnect from session so that the updates on updatedSaleOpportunity are not directly saved in db
        em.detach(updatedSaleOpportunity);
        updatedSaleOpportunity
            .opportunityId(UPDATED_OPPORTUNITY_ID)
            .opportunityCode(UPDATED_OPPORTUNITY_CODE)
            .opportunityName(UPDATED_OPPORTUNITY_NAME)
            .opportunityTypeName(UPDATED_OPPORTUNITY_TYPE_NAME)
            .startDate(UPDATED_START_DATE)
            .closeDate(UPDATED_CLOSE_DATE)
            .stageId(UPDATED_STAGE_ID)
            .stageReasonId(UPDATED_STAGE_REASON_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .leadId(UPDATED_LEAD_ID)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .accountId(UPDATED_ACCOUNT_ID)
            .productId(UPDATED_PRODUCT_ID)
            .salesPricePrd(UPDATED_SALES_PRICE_PRD)
            .value(UPDATED_VALUE);

        restSaleOpportunityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSaleOpportunity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSaleOpportunity))
            )
            .andExpect(status().isOk());

        // Validate the SaleOpportunity in the database
        List<SaleOpportunity> saleOpportunityList = saleOpportunityRepository.findAll();
        assertThat(saleOpportunityList).hasSize(databaseSizeBeforeUpdate);
        SaleOpportunity testSaleOpportunity = saleOpportunityList.get(saleOpportunityList.size() - 1);
        assertThat(testSaleOpportunity.getOpportunityId()).isEqualTo(UPDATED_OPPORTUNITY_ID);
        assertThat(testSaleOpportunity.getOpportunityCode()).isEqualTo(UPDATED_OPPORTUNITY_CODE);
        assertThat(testSaleOpportunity.getOpportunityName()).isEqualTo(UPDATED_OPPORTUNITY_NAME);
        assertThat(testSaleOpportunity.getOpportunityTypeName()).isEqualTo(UPDATED_OPPORTUNITY_TYPE_NAME);
        assertThat(testSaleOpportunity.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSaleOpportunity.getCloseDate()).isEqualTo(UPDATED_CLOSE_DATE);
        assertThat(testSaleOpportunity.getStageId()).isEqualTo(UPDATED_STAGE_ID);
        assertThat(testSaleOpportunity.getStageReasonId()).isEqualTo(UPDATED_STAGE_REASON_ID);
        assertThat(testSaleOpportunity.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testSaleOpportunity.getLeadId()).isEqualTo(UPDATED_LEAD_ID);
        assertThat(testSaleOpportunity.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testSaleOpportunity.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testSaleOpportunity.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testSaleOpportunity.getSalesPricePrd()).isEqualByComparingTo(UPDATED_SALES_PRICE_PRD);
        assertThat(testSaleOpportunity.getValue()).isEqualByComparingTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingSaleOpportunity() throws Exception {
        int databaseSizeBeforeUpdate = saleOpportunityRepository.findAll().size();
        saleOpportunity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleOpportunityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, saleOpportunity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saleOpportunity))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleOpportunity in the database
        List<SaleOpportunity> saleOpportunityList = saleOpportunityRepository.findAll();
        assertThat(saleOpportunityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSaleOpportunity() throws Exception {
        int databaseSizeBeforeUpdate = saleOpportunityRepository.findAll().size();
        saleOpportunity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleOpportunityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saleOpportunity))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleOpportunity in the database
        List<SaleOpportunity> saleOpportunityList = saleOpportunityRepository.findAll();
        assertThat(saleOpportunityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSaleOpportunity() throws Exception {
        int databaseSizeBeforeUpdate = saleOpportunityRepository.findAll().size();
        saleOpportunity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleOpportunityMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleOpportunity))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaleOpportunity in the database
        List<SaleOpportunity> saleOpportunityList = saleOpportunityRepository.findAll();
        assertThat(saleOpportunityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSaleOpportunityWithPatch() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        int databaseSizeBeforeUpdate = saleOpportunityRepository.findAll().size();

        // Update the saleOpportunity using partial update
        SaleOpportunity partialUpdatedSaleOpportunity = new SaleOpportunity();
        partialUpdatedSaleOpportunity.setId(saleOpportunity.getId());

        partialUpdatedSaleOpportunity
            .opportunityId(UPDATED_OPPORTUNITY_ID)
            .opportunityCode(UPDATED_OPPORTUNITY_CODE)
            .startDate(UPDATED_START_DATE)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .accountId(UPDATED_ACCOUNT_ID);

        restSaleOpportunityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaleOpportunity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSaleOpportunity))
            )
            .andExpect(status().isOk());

        // Validate the SaleOpportunity in the database
        List<SaleOpportunity> saleOpportunityList = saleOpportunityRepository.findAll();
        assertThat(saleOpportunityList).hasSize(databaseSizeBeforeUpdate);
        SaleOpportunity testSaleOpportunity = saleOpportunityList.get(saleOpportunityList.size() - 1);
        assertThat(testSaleOpportunity.getOpportunityId()).isEqualTo(UPDATED_OPPORTUNITY_ID);
        assertThat(testSaleOpportunity.getOpportunityCode()).isEqualTo(UPDATED_OPPORTUNITY_CODE);
        assertThat(testSaleOpportunity.getOpportunityName()).isEqualTo(DEFAULT_OPPORTUNITY_NAME);
        assertThat(testSaleOpportunity.getOpportunityTypeName()).isEqualTo(DEFAULT_OPPORTUNITY_TYPE_NAME);
        assertThat(testSaleOpportunity.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSaleOpportunity.getCloseDate()).isEqualTo(DEFAULT_CLOSE_DATE);
        assertThat(testSaleOpportunity.getStageId()).isEqualTo(DEFAULT_STAGE_ID);
        assertThat(testSaleOpportunity.getStageReasonId()).isEqualTo(DEFAULT_STAGE_REASON_ID);
        assertThat(testSaleOpportunity.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testSaleOpportunity.getLeadId()).isEqualTo(DEFAULT_LEAD_ID);
        assertThat(testSaleOpportunity.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testSaleOpportunity.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testSaleOpportunity.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testSaleOpportunity.getSalesPricePrd()).isEqualByComparingTo(DEFAULT_SALES_PRICE_PRD);
        assertThat(testSaleOpportunity.getValue()).isEqualByComparingTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateSaleOpportunityWithPatch() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        int databaseSizeBeforeUpdate = saleOpportunityRepository.findAll().size();

        // Update the saleOpportunity using partial update
        SaleOpportunity partialUpdatedSaleOpportunity = new SaleOpportunity();
        partialUpdatedSaleOpportunity.setId(saleOpportunity.getId());

        partialUpdatedSaleOpportunity
            .opportunityId(UPDATED_OPPORTUNITY_ID)
            .opportunityCode(UPDATED_OPPORTUNITY_CODE)
            .opportunityName(UPDATED_OPPORTUNITY_NAME)
            .opportunityTypeName(UPDATED_OPPORTUNITY_TYPE_NAME)
            .startDate(UPDATED_START_DATE)
            .closeDate(UPDATED_CLOSE_DATE)
            .stageId(UPDATED_STAGE_ID)
            .stageReasonId(UPDATED_STAGE_REASON_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .leadId(UPDATED_LEAD_ID)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .accountId(UPDATED_ACCOUNT_ID)
            .productId(UPDATED_PRODUCT_ID)
            .salesPricePrd(UPDATED_SALES_PRICE_PRD)
            .value(UPDATED_VALUE);

        restSaleOpportunityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaleOpportunity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSaleOpportunity))
            )
            .andExpect(status().isOk());

        // Validate the SaleOpportunity in the database
        List<SaleOpportunity> saleOpportunityList = saleOpportunityRepository.findAll();
        assertThat(saleOpportunityList).hasSize(databaseSizeBeforeUpdate);
        SaleOpportunity testSaleOpportunity = saleOpportunityList.get(saleOpportunityList.size() - 1);
        assertThat(testSaleOpportunity.getOpportunityId()).isEqualTo(UPDATED_OPPORTUNITY_ID);
        assertThat(testSaleOpportunity.getOpportunityCode()).isEqualTo(UPDATED_OPPORTUNITY_CODE);
        assertThat(testSaleOpportunity.getOpportunityName()).isEqualTo(UPDATED_OPPORTUNITY_NAME);
        assertThat(testSaleOpportunity.getOpportunityTypeName()).isEqualTo(UPDATED_OPPORTUNITY_TYPE_NAME);
        assertThat(testSaleOpportunity.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSaleOpportunity.getCloseDate()).isEqualTo(UPDATED_CLOSE_DATE);
        assertThat(testSaleOpportunity.getStageId()).isEqualTo(UPDATED_STAGE_ID);
        assertThat(testSaleOpportunity.getStageReasonId()).isEqualTo(UPDATED_STAGE_REASON_ID);
        assertThat(testSaleOpportunity.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testSaleOpportunity.getLeadId()).isEqualTo(UPDATED_LEAD_ID);
        assertThat(testSaleOpportunity.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testSaleOpportunity.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testSaleOpportunity.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testSaleOpportunity.getSalesPricePrd()).isEqualByComparingTo(UPDATED_SALES_PRICE_PRD);
        assertThat(testSaleOpportunity.getValue()).isEqualByComparingTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingSaleOpportunity() throws Exception {
        int databaseSizeBeforeUpdate = saleOpportunityRepository.findAll().size();
        saleOpportunity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleOpportunityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saleOpportunity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saleOpportunity))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleOpportunity in the database
        List<SaleOpportunity> saleOpportunityList = saleOpportunityRepository.findAll();
        assertThat(saleOpportunityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSaleOpportunity() throws Exception {
        int databaseSizeBeforeUpdate = saleOpportunityRepository.findAll().size();
        saleOpportunity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleOpportunityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saleOpportunity))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleOpportunity in the database
        List<SaleOpportunity> saleOpportunityList = saleOpportunityRepository.findAll();
        assertThat(saleOpportunityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSaleOpportunity() throws Exception {
        int databaseSizeBeforeUpdate = saleOpportunityRepository.findAll().size();
        saleOpportunity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleOpportunityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saleOpportunity))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaleOpportunity in the database
        List<SaleOpportunity> saleOpportunityList = saleOpportunityRepository.findAll();
        assertThat(saleOpportunityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSaleOpportunity() throws Exception {
        // Initialize the database
        saleOpportunityRepository.saveAndFlush(saleOpportunity);

        int databaseSizeBeforeDelete = saleOpportunityRepository.findAll().size();

        // Delete the saleOpportunity
        restSaleOpportunityMockMvc
            .perform(delete(ENTITY_API_URL_ID, saleOpportunity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SaleOpportunity> saleOpportunityList = saleOpportunityRepository.findAll();
        assertThat(saleOpportunityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
