package vn.com.datamanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.com.datamanager.web.rest.TestUtil.sameNumber;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import vn.com.datamanager.domain.SaleOrder;
import vn.com.datamanager.repository.SaleOrderRepository;
import vn.com.datamanager.service.criteria.SaleOrderCriteria;

/**
 * Integration tests for the {@link SaleOrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaleOrderResourceIT {

    private static final String DEFAULT_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER_EMPLOYEE_ID = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_EMPLOYEE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TOTAL_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_VALUE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_VALUE = new BigDecimal(1 - 1);

    private static final String DEFAULT_ORDER_STAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_STAGE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_STAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_STAGE_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sale-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SaleOrderRepository saleOrderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaleOrderMockMvc;

    private SaleOrder saleOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaleOrder createEntity(EntityManager em) {
        SaleOrder saleOrder = new SaleOrder()
            .orderId(DEFAULT_ORDER_ID)
            .contractId(DEFAULT_CONTRACT_ID)
            .ownerEmployeeId(DEFAULT_OWNER_EMPLOYEE_ID)
            .productId(DEFAULT_PRODUCT_ID)
            .totalValue(DEFAULT_TOTAL_VALUE)
            .orderStageId(DEFAULT_ORDER_STAGE_ID)
            .orderStageName(DEFAULT_ORDER_STAGE_NAME)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return saleOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaleOrder createUpdatedEntity(EntityManager em) {
        SaleOrder saleOrder = new SaleOrder()
            .orderId(UPDATED_ORDER_ID)
            .contractId(UPDATED_CONTRACT_ID)
            .ownerEmployeeId(UPDATED_OWNER_EMPLOYEE_ID)
            .productId(UPDATED_PRODUCT_ID)
            .totalValue(UPDATED_TOTAL_VALUE)
            .orderStageId(UPDATED_ORDER_STAGE_ID)
            .orderStageName(UPDATED_ORDER_STAGE_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return saleOrder;
    }

    @BeforeEach
    public void initTest() {
        saleOrder = createEntity(em);
    }

    @Test
    @Transactional
    void createSaleOrder() throws Exception {
        int databaseSizeBeforeCreate = saleOrderRepository.findAll().size();
        // Create the SaleOrder
        restSaleOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleOrder)))
            .andExpect(status().isCreated());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeCreate + 1);
        SaleOrder testSaleOrder = saleOrderList.get(saleOrderList.size() - 1);
        assertThat(testSaleOrder.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testSaleOrder.getContractId()).isEqualTo(DEFAULT_CONTRACT_ID);
        assertThat(testSaleOrder.getOwnerEmployeeId()).isEqualTo(DEFAULT_OWNER_EMPLOYEE_ID);
        assertThat(testSaleOrder.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testSaleOrder.getTotalValue()).isEqualByComparingTo(DEFAULT_TOTAL_VALUE);
        assertThat(testSaleOrder.getOrderStageId()).isEqualTo(DEFAULT_ORDER_STAGE_ID);
        assertThat(testSaleOrder.getOrderStageName()).isEqualTo(DEFAULT_ORDER_STAGE_NAME);
        assertThat(testSaleOrder.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSaleOrder.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSaleOrder.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testSaleOrder.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createSaleOrderWithExistingId() throws Exception {
        // Create the SaleOrder with an existing ID
        saleOrder.setId(1L);

        int databaseSizeBeforeCreate = saleOrderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleOrder)))
            .andExpect(status().isBadRequest());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSaleOrders() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList
        restSaleOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].contractId").value(hasItem(DEFAULT_CONTRACT_ID)))
            .andExpect(jsonPath("$.[*].ownerEmployeeId").value(hasItem(DEFAULT_OWNER_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].totalValue").value(hasItem(sameNumber(DEFAULT_TOTAL_VALUE))))
            .andExpect(jsonPath("$.[*].orderStageId").value(hasItem(DEFAULT_ORDER_STAGE_ID)))
            .andExpect(jsonPath("$.[*].orderStageName").value(hasItem(DEFAULT_ORDER_STAGE_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getSaleOrder() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get the saleOrder
        restSaleOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, saleOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(saleOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.contractId").value(DEFAULT_CONTRACT_ID))
            .andExpect(jsonPath("$.ownerEmployeeId").value(DEFAULT_OWNER_EMPLOYEE_ID))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID))
            .andExpect(jsonPath("$.totalValue").value(sameNumber(DEFAULT_TOTAL_VALUE)))
            .andExpect(jsonPath("$.orderStageId").value(DEFAULT_ORDER_STAGE_ID))
            .andExpect(jsonPath("$.orderStageName").value(DEFAULT_ORDER_STAGE_NAME))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getSaleOrdersByIdFiltering() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        Long id = saleOrder.getId();

        defaultSaleOrderShouldBeFound("id.equals=" + id);
        defaultSaleOrderShouldNotBeFound("id.notEquals=" + id);

        defaultSaleOrderShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSaleOrderShouldNotBeFound("id.greaterThan=" + id);

        defaultSaleOrderShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSaleOrderShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderId equals to DEFAULT_ORDER_ID
        defaultSaleOrderShouldBeFound("orderId.equals=" + DEFAULT_ORDER_ID);

        // Get all the saleOrderList where orderId equals to UPDATED_ORDER_ID
        defaultSaleOrderShouldNotBeFound("orderId.equals=" + UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderId not equals to DEFAULT_ORDER_ID
        defaultSaleOrderShouldNotBeFound("orderId.notEquals=" + DEFAULT_ORDER_ID);

        // Get all the saleOrderList where orderId not equals to UPDATED_ORDER_ID
        defaultSaleOrderShouldBeFound("orderId.notEquals=" + UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderId in DEFAULT_ORDER_ID or UPDATED_ORDER_ID
        defaultSaleOrderShouldBeFound("orderId.in=" + DEFAULT_ORDER_ID + "," + UPDATED_ORDER_ID);

        // Get all the saleOrderList where orderId equals to UPDATED_ORDER_ID
        defaultSaleOrderShouldNotBeFound("orderId.in=" + UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderId is not null
        defaultSaleOrderShouldBeFound("orderId.specified=true");

        // Get all the saleOrderList where orderId is null
        defaultSaleOrderShouldNotBeFound("orderId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderIdContainsSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderId contains DEFAULT_ORDER_ID
        defaultSaleOrderShouldBeFound("orderId.contains=" + DEFAULT_ORDER_ID);

        // Get all the saleOrderList where orderId contains UPDATED_ORDER_ID
        defaultSaleOrderShouldNotBeFound("orderId.contains=" + UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderIdNotContainsSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderId does not contain DEFAULT_ORDER_ID
        defaultSaleOrderShouldNotBeFound("orderId.doesNotContain=" + DEFAULT_ORDER_ID);

        // Get all the saleOrderList where orderId does not contain UPDATED_ORDER_ID
        defaultSaleOrderShouldBeFound("orderId.doesNotContain=" + UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByContractIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where contractId equals to DEFAULT_CONTRACT_ID
        defaultSaleOrderShouldBeFound("contractId.equals=" + DEFAULT_CONTRACT_ID);

        // Get all the saleOrderList where contractId equals to UPDATED_CONTRACT_ID
        defaultSaleOrderShouldNotBeFound("contractId.equals=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByContractIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where contractId not equals to DEFAULT_CONTRACT_ID
        defaultSaleOrderShouldNotBeFound("contractId.notEquals=" + DEFAULT_CONTRACT_ID);

        // Get all the saleOrderList where contractId not equals to UPDATED_CONTRACT_ID
        defaultSaleOrderShouldBeFound("contractId.notEquals=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByContractIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where contractId in DEFAULT_CONTRACT_ID or UPDATED_CONTRACT_ID
        defaultSaleOrderShouldBeFound("contractId.in=" + DEFAULT_CONTRACT_ID + "," + UPDATED_CONTRACT_ID);

        // Get all the saleOrderList where contractId equals to UPDATED_CONTRACT_ID
        defaultSaleOrderShouldNotBeFound("contractId.in=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByContractIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where contractId is not null
        defaultSaleOrderShouldBeFound("contractId.specified=true");

        // Get all the saleOrderList where contractId is null
        defaultSaleOrderShouldNotBeFound("contractId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOrdersByContractIdContainsSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where contractId contains DEFAULT_CONTRACT_ID
        defaultSaleOrderShouldBeFound("contractId.contains=" + DEFAULT_CONTRACT_ID);

        // Get all the saleOrderList where contractId contains UPDATED_CONTRACT_ID
        defaultSaleOrderShouldNotBeFound("contractId.contains=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByContractIdNotContainsSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where contractId does not contain DEFAULT_CONTRACT_ID
        defaultSaleOrderShouldNotBeFound("contractId.doesNotContain=" + DEFAULT_CONTRACT_ID);

        // Get all the saleOrderList where contractId does not contain UPDATED_CONTRACT_ID
        defaultSaleOrderShouldBeFound("contractId.doesNotContain=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOwnerEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where ownerEmployeeId equals to DEFAULT_OWNER_EMPLOYEE_ID
        defaultSaleOrderShouldBeFound("ownerEmployeeId.equals=" + DEFAULT_OWNER_EMPLOYEE_ID);

        // Get all the saleOrderList where ownerEmployeeId equals to UPDATED_OWNER_EMPLOYEE_ID
        defaultSaleOrderShouldNotBeFound("ownerEmployeeId.equals=" + UPDATED_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOwnerEmployeeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where ownerEmployeeId not equals to DEFAULT_OWNER_EMPLOYEE_ID
        defaultSaleOrderShouldNotBeFound("ownerEmployeeId.notEquals=" + DEFAULT_OWNER_EMPLOYEE_ID);

        // Get all the saleOrderList where ownerEmployeeId not equals to UPDATED_OWNER_EMPLOYEE_ID
        defaultSaleOrderShouldBeFound("ownerEmployeeId.notEquals=" + UPDATED_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOwnerEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where ownerEmployeeId in DEFAULT_OWNER_EMPLOYEE_ID or UPDATED_OWNER_EMPLOYEE_ID
        defaultSaleOrderShouldBeFound("ownerEmployeeId.in=" + DEFAULT_OWNER_EMPLOYEE_ID + "," + UPDATED_OWNER_EMPLOYEE_ID);

        // Get all the saleOrderList where ownerEmployeeId equals to UPDATED_OWNER_EMPLOYEE_ID
        defaultSaleOrderShouldNotBeFound("ownerEmployeeId.in=" + UPDATED_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOwnerEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where ownerEmployeeId is not null
        defaultSaleOrderShouldBeFound("ownerEmployeeId.specified=true");

        // Get all the saleOrderList where ownerEmployeeId is null
        defaultSaleOrderShouldNotBeFound("ownerEmployeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOwnerEmployeeIdContainsSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where ownerEmployeeId contains DEFAULT_OWNER_EMPLOYEE_ID
        defaultSaleOrderShouldBeFound("ownerEmployeeId.contains=" + DEFAULT_OWNER_EMPLOYEE_ID);

        // Get all the saleOrderList where ownerEmployeeId contains UPDATED_OWNER_EMPLOYEE_ID
        defaultSaleOrderShouldNotBeFound("ownerEmployeeId.contains=" + UPDATED_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOwnerEmployeeIdNotContainsSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where ownerEmployeeId does not contain DEFAULT_OWNER_EMPLOYEE_ID
        defaultSaleOrderShouldNotBeFound("ownerEmployeeId.doesNotContain=" + DEFAULT_OWNER_EMPLOYEE_ID);

        // Get all the saleOrderList where ownerEmployeeId does not contain UPDATED_OWNER_EMPLOYEE_ID
        defaultSaleOrderShouldBeFound("ownerEmployeeId.doesNotContain=" + UPDATED_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByProductIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where productId equals to DEFAULT_PRODUCT_ID
        defaultSaleOrderShouldBeFound("productId.equals=" + DEFAULT_PRODUCT_ID);

        // Get all the saleOrderList where productId equals to UPDATED_PRODUCT_ID
        defaultSaleOrderShouldNotBeFound("productId.equals=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByProductIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where productId not equals to DEFAULT_PRODUCT_ID
        defaultSaleOrderShouldNotBeFound("productId.notEquals=" + DEFAULT_PRODUCT_ID);

        // Get all the saleOrderList where productId not equals to UPDATED_PRODUCT_ID
        defaultSaleOrderShouldBeFound("productId.notEquals=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByProductIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where productId in DEFAULT_PRODUCT_ID or UPDATED_PRODUCT_ID
        defaultSaleOrderShouldBeFound("productId.in=" + DEFAULT_PRODUCT_ID + "," + UPDATED_PRODUCT_ID);

        // Get all the saleOrderList where productId equals to UPDATED_PRODUCT_ID
        defaultSaleOrderShouldNotBeFound("productId.in=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByProductIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where productId is not null
        defaultSaleOrderShouldBeFound("productId.specified=true");

        // Get all the saleOrderList where productId is null
        defaultSaleOrderShouldNotBeFound("productId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOrdersByProductIdContainsSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where productId contains DEFAULT_PRODUCT_ID
        defaultSaleOrderShouldBeFound("productId.contains=" + DEFAULT_PRODUCT_ID);

        // Get all the saleOrderList where productId contains UPDATED_PRODUCT_ID
        defaultSaleOrderShouldNotBeFound("productId.contains=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByProductIdNotContainsSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where productId does not contain DEFAULT_PRODUCT_ID
        defaultSaleOrderShouldNotBeFound("productId.doesNotContain=" + DEFAULT_PRODUCT_ID);

        // Get all the saleOrderList where productId does not contain UPDATED_PRODUCT_ID
        defaultSaleOrderShouldBeFound("productId.doesNotContain=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByTotalValueIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where totalValue equals to DEFAULT_TOTAL_VALUE
        defaultSaleOrderShouldBeFound("totalValue.equals=" + DEFAULT_TOTAL_VALUE);

        // Get all the saleOrderList where totalValue equals to UPDATED_TOTAL_VALUE
        defaultSaleOrderShouldNotBeFound("totalValue.equals=" + UPDATED_TOTAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByTotalValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where totalValue not equals to DEFAULT_TOTAL_VALUE
        defaultSaleOrderShouldNotBeFound("totalValue.notEquals=" + DEFAULT_TOTAL_VALUE);

        // Get all the saleOrderList where totalValue not equals to UPDATED_TOTAL_VALUE
        defaultSaleOrderShouldBeFound("totalValue.notEquals=" + UPDATED_TOTAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByTotalValueIsInShouldWork() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where totalValue in DEFAULT_TOTAL_VALUE or UPDATED_TOTAL_VALUE
        defaultSaleOrderShouldBeFound("totalValue.in=" + DEFAULT_TOTAL_VALUE + "," + UPDATED_TOTAL_VALUE);

        // Get all the saleOrderList where totalValue equals to UPDATED_TOTAL_VALUE
        defaultSaleOrderShouldNotBeFound("totalValue.in=" + UPDATED_TOTAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByTotalValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where totalValue is not null
        defaultSaleOrderShouldBeFound("totalValue.specified=true");

        // Get all the saleOrderList where totalValue is null
        defaultSaleOrderShouldNotBeFound("totalValue.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOrdersByTotalValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where totalValue is greater than or equal to DEFAULT_TOTAL_VALUE
        defaultSaleOrderShouldBeFound("totalValue.greaterThanOrEqual=" + DEFAULT_TOTAL_VALUE);

        // Get all the saleOrderList where totalValue is greater than or equal to UPDATED_TOTAL_VALUE
        defaultSaleOrderShouldNotBeFound("totalValue.greaterThanOrEqual=" + UPDATED_TOTAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByTotalValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where totalValue is less than or equal to DEFAULT_TOTAL_VALUE
        defaultSaleOrderShouldBeFound("totalValue.lessThanOrEqual=" + DEFAULT_TOTAL_VALUE);

        // Get all the saleOrderList where totalValue is less than or equal to SMALLER_TOTAL_VALUE
        defaultSaleOrderShouldNotBeFound("totalValue.lessThanOrEqual=" + SMALLER_TOTAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByTotalValueIsLessThanSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where totalValue is less than DEFAULT_TOTAL_VALUE
        defaultSaleOrderShouldNotBeFound("totalValue.lessThan=" + DEFAULT_TOTAL_VALUE);

        // Get all the saleOrderList where totalValue is less than UPDATED_TOTAL_VALUE
        defaultSaleOrderShouldBeFound("totalValue.lessThan=" + UPDATED_TOTAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByTotalValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where totalValue is greater than DEFAULT_TOTAL_VALUE
        defaultSaleOrderShouldNotBeFound("totalValue.greaterThan=" + DEFAULT_TOTAL_VALUE);

        // Get all the saleOrderList where totalValue is greater than SMALLER_TOTAL_VALUE
        defaultSaleOrderShouldBeFound("totalValue.greaterThan=" + SMALLER_TOTAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderStageIdIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderStageId equals to DEFAULT_ORDER_STAGE_ID
        defaultSaleOrderShouldBeFound("orderStageId.equals=" + DEFAULT_ORDER_STAGE_ID);

        // Get all the saleOrderList where orderStageId equals to UPDATED_ORDER_STAGE_ID
        defaultSaleOrderShouldNotBeFound("orderStageId.equals=" + UPDATED_ORDER_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderStageIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderStageId not equals to DEFAULT_ORDER_STAGE_ID
        defaultSaleOrderShouldNotBeFound("orderStageId.notEquals=" + DEFAULT_ORDER_STAGE_ID);

        // Get all the saleOrderList where orderStageId not equals to UPDATED_ORDER_STAGE_ID
        defaultSaleOrderShouldBeFound("orderStageId.notEquals=" + UPDATED_ORDER_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderStageIdIsInShouldWork() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderStageId in DEFAULT_ORDER_STAGE_ID or UPDATED_ORDER_STAGE_ID
        defaultSaleOrderShouldBeFound("orderStageId.in=" + DEFAULT_ORDER_STAGE_ID + "," + UPDATED_ORDER_STAGE_ID);

        // Get all the saleOrderList where orderStageId equals to UPDATED_ORDER_STAGE_ID
        defaultSaleOrderShouldNotBeFound("orderStageId.in=" + UPDATED_ORDER_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderStageIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderStageId is not null
        defaultSaleOrderShouldBeFound("orderStageId.specified=true");

        // Get all the saleOrderList where orderStageId is null
        defaultSaleOrderShouldNotBeFound("orderStageId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderStageIdContainsSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderStageId contains DEFAULT_ORDER_STAGE_ID
        defaultSaleOrderShouldBeFound("orderStageId.contains=" + DEFAULT_ORDER_STAGE_ID);

        // Get all the saleOrderList where orderStageId contains UPDATED_ORDER_STAGE_ID
        defaultSaleOrderShouldNotBeFound("orderStageId.contains=" + UPDATED_ORDER_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderStageIdNotContainsSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderStageId does not contain DEFAULT_ORDER_STAGE_ID
        defaultSaleOrderShouldNotBeFound("orderStageId.doesNotContain=" + DEFAULT_ORDER_STAGE_ID);

        // Get all the saleOrderList where orderStageId does not contain UPDATED_ORDER_STAGE_ID
        defaultSaleOrderShouldBeFound("orderStageId.doesNotContain=" + UPDATED_ORDER_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderStageNameIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderStageName equals to DEFAULT_ORDER_STAGE_NAME
        defaultSaleOrderShouldBeFound("orderStageName.equals=" + DEFAULT_ORDER_STAGE_NAME);

        // Get all the saleOrderList where orderStageName equals to UPDATED_ORDER_STAGE_NAME
        defaultSaleOrderShouldNotBeFound("orderStageName.equals=" + UPDATED_ORDER_STAGE_NAME);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderStageNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderStageName not equals to DEFAULT_ORDER_STAGE_NAME
        defaultSaleOrderShouldNotBeFound("orderStageName.notEquals=" + DEFAULT_ORDER_STAGE_NAME);

        // Get all the saleOrderList where orderStageName not equals to UPDATED_ORDER_STAGE_NAME
        defaultSaleOrderShouldBeFound("orderStageName.notEquals=" + UPDATED_ORDER_STAGE_NAME);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderStageNameIsInShouldWork() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderStageName in DEFAULT_ORDER_STAGE_NAME or UPDATED_ORDER_STAGE_NAME
        defaultSaleOrderShouldBeFound("orderStageName.in=" + DEFAULT_ORDER_STAGE_NAME + "," + UPDATED_ORDER_STAGE_NAME);

        // Get all the saleOrderList where orderStageName equals to UPDATED_ORDER_STAGE_NAME
        defaultSaleOrderShouldNotBeFound("orderStageName.in=" + UPDATED_ORDER_STAGE_NAME);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderStageNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderStageName is not null
        defaultSaleOrderShouldBeFound("orderStageName.specified=true");

        // Get all the saleOrderList where orderStageName is null
        defaultSaleOrderShouldNotBeFound("orderStageName.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderStageNameContainsSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderStageName contains DEFAULT_ORDER_STAGE_NAME
        defaultSaleOrderShouldBeFound("orderStageName.contains=" + DEFAULT_ORDER_STAGE_NAME);

        // Get all the saleOrderList where orderStageName contains UPDATED_ORDER_STAGE_NAME
        defaultSaleOrderShouldNotBeFound("orderStageName.contains=" + UPDATED_ORDER_STAGE_NAME);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByOrderStageNameNotContainsSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where orderStageName does not contain DEFAULT_ORDER_STAGE_NAME
        defaultSaleOrderShouldNotBeFound("orderStageName.doesNotContain=" + DEFAULT_ORDER_STAGE_NAME);

        // Get all the saleOrderList where orderStageName does not contain UPDATED_ORDER_STAGE_NAME
        defaultSaleOrderShouldBeFound("orderStageName.doesNotContain=" + UPDATED_ORDER_STAGE_NAME);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where createdDate equals to DEFAULT_CREATED_DATE
        defaultSaleOrderShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the saleOrderList where createdDate equals to UPDATED_CREATED_DATE
        defaultSaleOrderShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultSaleOrderShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the saleOrderList where createdDate not equals to UPDATED_CREATED_DATE
        defaultSaleOrderShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultSaleOrderShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the saleOrderList where createdDate equals to UPDATED_CREATED_DATE
        defaultSaleOrderShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where createdDate is not null
        defaultSaleOrderShouldBeFound("createdDate.specified=true");

        // Get all the saleOrderList where createdDate is null
        defaultSaleOrderShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOrdersByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where createdBy equals to DEFAULT_CREATED_BY
        defaultSaleOrderShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the saleOrderList where createdBy equals to UPDATED_CREATED_BY
        defaultSaleOrderShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where createdBy not equals to DEFAULT_CREATED_BY
        defaultSaleOrderShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the saleOrderList where createdBy not equals to UPDATED_CREATED_BY
        defaultSaleOrderShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSaleOrderShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the saleOrderList where createdBy equals to UPDATED_CREATED_BY
        defaultSaleOrderShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where createdBy is not null
        defaultSaleOrderShouldBeFound("createdBy.specified=true");

        // Get all the saleOrderList where createdBy is null
        defaultSaleOrderShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOrdersByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where createdBy contains DEFAULT_CREATED_BY
        defaultSaleOrderShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the saleOrderList where createdBy contains UPDATED_CREATED_BY
        defaultSaleOrderShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where createdBy does not contain DEFAULT_CREATED_BY
        defaultSaleOrderShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the saleOrderList where createdBy does not contain UPDATED_CREATED_BY
        defaultSaleOrderShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultSaleOrderShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the saleOrderList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultSaleOrderShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultSaleOrderShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the saleOrderList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultSaleOrderShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultSaleOrderShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the saleOrderList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultSaleOrderShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where lastModifiedDate is not null
        defaultSaleOrderShouldBeFound("lastModifiedDate.specified=true");

        // Get all the saleOrderList where lastModifiedDate is null
        defaultSaleOrderShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOrdersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSaleOrderShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the saleOrderList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSaleOrderShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultSaleOrderShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the saleOrderList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultSaleOrderShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSaleOrderShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the saleOrderList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSaleOrderShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where lastModifiedBy is not null
        defaultSaleOrderShouldBeFound("lastModifiedBy.specified=true");

        // Get all the saleOrderList where lastModifiedBy is null
        defaultSaleOrderShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleOrdersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSaleOrderShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the saleOrderList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSaleOrderShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSaleOrdersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSaleOrderShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the saleOrderList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSaleOrderShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSaleOrderShouldBeFound(String filter) throws Exception {
        restSaleOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].contractId").value(hasItem(DEFAULT_CONTRACT_ID)))
            .andExpect(jsonPath("$.[*].ownerEmployeeId").value(hasItem(DEFAULT_OWNER_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].totalValue").value(hasItem(sameNumber(DEFAULT_TOTAL_VALUE))))
            .andExpect(jsonPath("$.[*].orderStageId").value(hasItem(DEFAULT_ORDER_STAGE_ID)))
            .andExpect(jsonPath("$.[*].orderStageName").value(hasItem(DEFAULT_ORDER_STAGE_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restSaleOrderMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSaleOrderShouldNotBeFound(String filter) throws Exception {
        restSaleOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSaleOrderMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSaleOrder() throws Exception {
        // Get the saleOrder
        restSaleOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSaleOrder() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        int databaseSizeBeforeUpdate = saleOrderRepository.findAll().size();

        // Update the saleOrder
        SaleOrder updatedSaleOrder = saleOrderRepository.findById(saleOrder.getId()).get();
        // Disconnect from session so that the updates on updatedSaleOrder are not directly saved in db
        em.detach(updatedSaleOrder);
        updatedSaleOrder
            .orderId(UPDATED_ORDER_ID)
            .contractId(UPDATED_CONTRACT_ID)
            .ownerEmployeeId(UPDATED_OWNER_EMPLOYEE_ID)
            .productId(UPDATED_PRODUCT_ID)
            .totalValue(UPDATED_TOTAL_VALUE)
            .orderStageId(UPDATED_ORDER_STAGE_ID)
            .orderStageName(UPDATED_ORDER_STAGE_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restSaleOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSaleOrder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSaleOrder))
            )
            .andExpect(status().isOk());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeUpdate);
        SaleOrder testSaleOrder = saleOrderList.get(saleOrderList.size() - 1);
        assertThat(testSaleOrder.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testSaleOrder.getContractId()).isEqualTo(UPDATED_CONTRACT_ID);
        assertThat(testSaleOrder.getOwnerEmployeeId()).isEqualTo(UPDATED_OWNER_EMPLOYEE_ID);
        assertThat(testSaleOrder.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testSaleOrder.getTotalValue()).isEqualByComparingTo(UPDATED_TOTAL_VALUE);
        assertThat(testSaleOrder.getOrderStageId()).isEqualTo(UPDATED_ORDER_STAGE_ID);
        assertThat(testSaleOrder.getOrderStageName()).isEqualTo(UPDATED_ORDER_STAGE_NAME);
        assertThat(testSaleOrder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSaleOrder.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSaleOrder.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testSaleOrder.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingSaleOrder() throws Exception {
        int databaseSizeBeforeUpdate = saleOrderRepository.findAll().size();
        saleOrder.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, saleOrder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saleOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSaleOrder() throws Exception {
        int databaseSizeBeforeUpdate = saleOrderRepository.findAll().size();
        saleOrder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saleOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSaleOrder() throws Exception {
        int databaseSizeBeforeUpdate = saleOrderRepository.findAll().size();
        saleOrder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleOrderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleOrder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSaleOrderWithPatch() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        int databaseSizeBeforeUpdate = saleOrderRepository.findAll().size();

        // Update the saleOrder using partial update
        SaleOrder partialUpdatedSaleOrder = new SaleOrder();
        partialUpdatedSaleOrder.setId(saleOrder.getId());

        partialUpdatedSaleOrder
            .orderId(UPDATED_ORDER_ID)
            .contractId(UPDATED_CONTRACT_ID)
            .orderStageId(UPDATED_ORDER_STAGE_ID)
            .orderStageName(UPDATED_ORDER_STAGE_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restSaleOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaleOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSaleOrder))
            )
            .andExpect(status().isOk());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeUpdate);
        SaleOrder testSaleOrder = saleOrderList.get(saleOrderList.size() - 1);
        assertThat(testSaleOrder.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testSaleOrder.getContractId()).isEqualTo(UPDATED_CONTRACT_ID);
        assertThat(testSaleOrder.getOwnerEmployeeId()).isEqualTo(DEFAULT_OWNER_EMPLOYEE_ID);
        assertThat(testSaleOrder.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testSaleOrder.getTotalValue()).isEqualByComparingTo(DEFAULT_TOTAL_VALUE);
        assertThat(testSaleOrder.getOrderStageId()).isEqualTo(UPDATED_ORDER_STAGE_ID);
        assertThat(testSaleOrder.getOrderStageName()).isEqualTo(UPDATED_ORDER_STAGE_NAME);
        assertThat(testSaleOrder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSaleOrder.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSaleOrder.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testSaleOrder.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateSaleOrderWithPatch() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        int databaseSizeBeforeUpdate = saleOrderRepository.findAll().size();

        // Update the saleOrder using partial update
        SaleOrder partialUpdatedSaleOrder = new SaleOrder();
        partialUpdatedSaleOrder.setId(saleOrder.getId());

        partialUpdatedSaleOrder
            .orderId(UPDATED_ORDER_ID)
            .contractId(UPDATED_CONTRACT_ID)
            .ownerEmployeeId(UPDATED_OWNER_EMPLOYEE_ID)
            .productId(UPDATED_PRODUCT_ID)
            .totalValue(UPDATED_TOTAL_VALUE)
            .orderStageId(UPDATED_ORDER_STAGE_ID)
            .orderStageName(UPDATED_ORDER_STAGE_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restSaleOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaleOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSaleOrder))
            )
            .andExpect(status().isOk());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeUpdate);
        SaleOrder testSaleOrder = saleOrderList.get(saleOrderList.size() - 1);
        assertThat(testSaleOrder.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testSaleOrder.getContractId()).isEqualTo(UPDATED_CONTRACT_ID);
        assertThat(testSaleOrder.getOwnerEmployeeId()).isEqualTo(UPDATED_OWNER_EMPLOYEE_ID);
        assertThat(testSaleOrder.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testSaleOrder.getTotalValue()).isEqualByComparingTo(UPDATED_TOTAL_VALUE);
        assertThat(testSaleOrder.getOrderStageId()).isEqualTo(UPDATED_ORDER_STAGE_ID);
        assertThat(testSaleOrder.getOrderStageName()).isEqualTo(UPDATED_ORDER_STAGE_NAME);
        assertThat(testSaleOrder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSaleOrder.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSaleOrder.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testSaleOrder.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingSaleOrder() throws Exception {
        int databaseSizeBeforeUpdate = saleOrderRepository.findAll().size();
        saleOrder.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saleOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saleOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSaleOrder() throws Exception {
        int databaseSizeBeforeUpdate = saleOrderRepository.findAll().size();
        saleOrder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saleOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSaleOrder() throws Exception {
        int databaseSizeBeforeUpdate = saleOrderRepository.findAll().size();
        saleOrder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleOrderMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(saleOrder))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSaleOrder() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        int databaseSizeBeforeDelete = saleOrderRepository.findAll().size();

        // Delete the saleOrder
        restSaleOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, saleOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
