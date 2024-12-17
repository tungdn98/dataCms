package vn.com.datamanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import vn.com.datamanager.domain.Customer;
import vn.com.datamanager.repository.CustomerRepository;
import vn.com.datamanager.service.criteria.CustomerCriteria;

/**
 * Integration tests for the {@link CustomerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomerResourceIT {

    private static final String DEFAULT_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MAPPING_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_MAPPING_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GENDER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INDUSTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INDUSTRY_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_OWNER_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_OWNER_EMPLOYEE_ID = 2L;
    private static final Long SMALLER_OWNER_EMPLOYEE_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/customers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerMockMvc;

    private Customer customer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .accountId(DEFAULT_ACCOUNT_ID)
            .accountCode(DEFAULT_ACCOUNT_CODE)
            .accountName(DEFAULT_ACCOUNT_NAME)
            .mappingAccount(DEFAULT_MAPPING_ACCOUNT)
            .accountEmail(DEFAULT_ACCOUNT_EMAIL)
            .accountPhone(DEFAULT_ACCOUNT_PHONE)
            .accountTypeName(DEFAULT_ACCOUNT_TYPE_NAME)
            .genderName(DEFAULT_GENDER_NAME)
            .industryName(DEFAULT_INDUSTRY_NAME)
            .ownerEmployeeId(DEFAULT_OWNER_EMPLOYEE_ID);
        return customer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createUpdatedEntity(EntityManager em) {
        Customer customer = new Customer()
            .accountId(UPDATED_ACCOUNT_ID)
            .accountCode(UPDATED_ACCOUNT_CODE)
            .accountName(UPDATED_ACCOUNT_NAME)
            .mappingAccount(UPDATED_MAPPING_ACCOUNT)
            .accountEmail(UPDATED_ACCOUNT_EMAIL)
            .accountPhone(UPDATED_ACCOUNT_PHONE)
            .accountTypeName(UPDATED_ACCOUNT_TYPE_NAME)
            .genderName(UPDATED_GENDER_NAME)
            .industryName(UPDATED_INDUSTRY_NAME)
            .ownerEmployeeId(UPDATED_OWNER_EMPLOYEE_ID);
        return customer;
    }

    @BeforeEach
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();
        // Create the Customer
        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testCustomer.getAccountCode()).isEqualTo(DEFAULT_ACCOUNT_CODE);
        assertThat(testCustomer.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testCustomer.getMappingAccount()).isEqualTo(DEFAULT_MAPPING_ACCOUNT);
        assertThat(testCustomer.getAccountEmail()).isEqualTo(DEFAULT_ACCOUNT_EMAIL);
        assertThat(testCustomer.getAccountPhone()).isEqualTo(DEFAULT_ACCOUNT_PHONE);
        assertThat(testCustomer.getAccountTypeName()).isEqualTo(DEFAULT_ACCOUNT_TYPE_NAME);
        assertThat(testCustomer.getGenderName()).isEqualTo(DEFAULT_GENDER_NAME);
        assertThat(testCustomer.getIndustryName()).isEqualTo(DEFAULT_INDUSTRY_NAME);
        assertThat(testCustomer.getOwnerEmployeeId()).isEqualTo(DEFAULT_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void createCustomerWithExistingId() throws Exception {
        // Create the Customer with an existing ID
        customer.setId(1L);

        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].accountCode").value(hasItem(DEFAULT_ACCOUNT_CODE)))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].mappingAccount").value(hasItem(DEFAULT_MAPPING_ACCOUNT)))
            .andExpect(jsonPath("$.[*].accountEmail").value(hasItem(DEFAULT_ACCOUNT_EMAIL)))
            .andExpect(jsonPath("$.[*].accountPhone").value(hasItem(DEFAULT_ACCOUNT_PHONE)))
            .andExpect(jsonPath("$.[*].accountTypeName").value(hasItem(DEFAULT_ACCOUNT_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].genderName").value(hasItem(DEFAULT_GENDER_NAME)))
            .andExpect(jsonPath("$.[*].industryName").value(hasItem(DEFAULT_INDUSTRY_NAME)))
            .andExpect(jsonPath("$.[*].ownerEmployeeId").value(hasItem(DEFAULT_OWNER_EMPLOYEE_ID.intValue())));
    }

    @Test
    @Transactional
    void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc
            .perform(get(ENTITY_API_URL_ID, customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.accountCode").value(DEFAULT_ACCOUNT_CODE))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME))
            .andExpect(jsonPath("$.mappingAccount").value(DEFAULT_MAPPING_ACCOUNT))
            .andExpect(jsonPath("$.accountEmail").value(DEFAULT_ACCOUNT_EMAIL))
            .andExpect(jsonPath("$.accountPhone").value(DEFAULT_ACCOUNT_PHONE))
            .andExpect(jsonPath("$.accountTypeName").value(DEFAULT_ACCOUNT_TYPE_NAME))
            .andExpect(jsonPath("$.genderName").value(DEFAULT_GENDER_NAME))
            .andExpect(jsonPath("$.industryName").value(DEFAULT_INDUSTRY_NAME))
            .andExpect(jsonPath("$.ownerEmployeeId").value(DEFAULT_OWNER_EMPLOYEE_ID.intValue()));
    }

    @Test
    @Transactional
    void getCustomersByIdFiltering() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        Long id = customer.getId();

        defaultCustomerShouldBeFound("id.equals=" + id);
        defaultCustomerShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountIdIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountId equals to DEFAULT_ACCOUNT_ID
        defaultCustomerShouldBeFound("accountId.equals=" + DEFAULT_ACCOUNT_ID);

        // Get all the customerList where accountId equals to UPDATED_ACCOUNT_ID
        defaultCustomerShouldNotBeFound("accountId.equals=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountId not equals to DEFAULT_ACCOUNT_ID
        defaultCustomerShouldNotBeFound("accountId.notEquals=" + DEFAULT_ACCOUNT_ID);

        // Get all the customerList where accountId not equals to UPDATED_ACCOUNT_ID
        defaultCustomerShouldBeFound("accountId.notEquals=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountIdIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountId in DEFAULT_ACCOUNT_ID or UPDATED_ACCOUNT_ID
        defaultCustomerShouldBeFound("accountId.in=" + DEFAULT_ACCOUNT_ID + "," + UPDATED_ACCOUNT_ID);

        // Get all the customerList where accountId equals to UPDATED_ACCOUNT_ID
        defaultCustomerShouldNotBeFound("accountId.in=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountId is not null
        defaultCustomerShouldBeFound("accountId.specified=true");

        // Get all the customerList where accountId is null
        defaultCustomerShouldNotBeFound("accountId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByAccountIdContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountId contains DEFAULT_ACCOUNT_ID
        defaultCustomerShouldBeFound("accountId.contains=" + DEFAULT_ACCOUNT_ID);

        // Get all the customerList where accountId contains UPDATED_ACCOUNT_ID
        defaultCustomerShouldNotBeFound("accountId.contains=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountIdNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountId does not contain DEFAULT_ACCOUNT_ID
        defaultCustomerShouldNotBeFound("accountId.doesNotContain=" + DEFAULT_ACCOUNT_ID);

        // Get all the customerList where accountId does not contain UPDATED_ACCOUNT_ID
        defaultCustomerShouldBeFound("accountId.doesNotContain=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountCode equals to DEFAULT_ACCOUNT_CODE
        defaultCustomerShouldBeFound("accountCode.equals=" + DEFAULT_ACCOUNT_CODE);

        // Get all the customerList where accountCode equals to UPDATED_ACCOUNT_CODE
        defaultCustomerShouldNotBeFound("accountCode.equals=" + UPDATED_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountCode not equals to DEFAULT_ACCOUNT_CODE
        defaultCustomerShouldNotBeFound("accountCode.notEquals=" + DEFAULT_ACCOUNT_CODE);

        // Get all the customerList where accountCode not equals to UPDATED_ACCOUNT_CODE
        defaultCustomerShouldBeFound("accountCode.notEquals=" + UPDATED_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountCodeIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountCode in DEFAULT_ACCOUNT_CODE or UPDATED_ACCOUNT_CODE
        defaultCustomerShouldBeFound("accountCode.in=" + DEFAULT_ACCOUNT_CODE + "," + UPDATED_ACCOUNT_CODE);

        // Get all the customerList where accountCode equals to UPDATED_ACCOUNT_CODE
        defaultCustomerShouldNotBeFound("accountCode.in=" + UPDATED_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountCode is not null
        defaultCustomerShouldBeFound("accountCode.specified=true");

        // Get all the customerList where accountCode is null
        defaultCustomerShouldNotBeFound("accountCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByAccountCodeContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountCode contains DEFAULT_ACCOUNT_CODE
        defaultCustomerShouldBeFound("accountCode.contains=" + DEFAULT_ACCOUNT_CODE);

        // Get all the customerList where accountCode contains UPDATED_ACCOUNT_CODE
        defaultCustomerShouldNotBeFound("accountCode.contains=" + UPDATED_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountCodeNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountCode does not contain DEFAULT_ACCOUNT_CODE
        defaultCustomerShouldNotBeFound("accountCode.doesNotContain=" + DEFAULT_ACCOUNT_CODE);

        // Get all the customerList where accountCode does not contain UPDATED_ACCOUNT_CODE
        defaultCustomerShouldBeFound("accountCode.doesNotContain=" + UPDATED_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountName equals to DEFAULT_ACCOUNT_NAME
        defaultCustomerShouldBeFound("accountName.equals=" + DEFAULT_ACCOUNT_NAME);

        // Get all the customerList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultCustomerShouldNotBeFound("accountName.equals=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountName not equals to DEFAULT_ACCOUNT_NAME
        defaultCustomerShouldNotBeFound("accountName.notEquals=" + DEFAULT_ACCOUNT_NAME);

        // Get all the customerList where accountName not equals to UPDATED_ACCOUNT_NAME
        defaultCustomerShouldBeFound("accountName.notEquals=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountName in DEFAULT_ACCOUNT_NAME or UPDATED_ACCOUNT_NAME
        defaultCustomerShouldBeFound("accountName.in=" + DEFAULT_ACCOUNT_NAME + "," + UPDATED_ACCOUNT_NAME);

        // Get all the customerList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultCustomerShouldNotBeFound("accountName.in=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountName is not null
        defaultCustomerShouldBeFound("accountName.specified=true");

        // Get all the customerList where accountName is null
        defaultCustomerShouldNotBeFound("accountName.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByAccountNameContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountName contains DEFAULT_ACCOUNT_NAME
        defaultCustomerShouldBeFound("accountName.contains=" + DEFAULT_ACCOUNT_NAME);

        // Get all the customerList where accountName contains UPDATED_ACCOUNT_NAME
        defaultCustomerShouldNotBeFound("accountName.contains=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountName does not contain DEFAULT_ACCOUNT_NAME
        defaultCustomerShouldNotBeFound("accountName.doesNotContain=" + DEFAULT_ACCOUNT_NAME);

        // Get all the customerList where accountName does not contain UPDATED_ACCOUNT_NAME
        defaultCustomerShouldBeFound("accountName.doesNotContain=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByMappingAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where mappingAccount equals to DEFAULT_MAPPING_ACCOUNT
        defaultCustomerShouldBeFound("mappingAccount.equals=" + DEFAULT_MAPPING_ACCOUNT);

        // Get all the customerList where mappingAccount equals to UPDATED_MAPPING_ACCOUNT
        defaultCustomerShouldNotBeFound("mappingAccount.equals=" + UPDATED_MAPPING_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCustomersByMappingAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where mappingAccount not equals to DEFAULT_MAPPING_ACCOUNT
        defaultCustomerShouldNotBeFound("mappingAccount.notEquals=" + DEFAULT_MAPPING_ACCOUNT);

        // Get all the customerList where mappingAccount not equals to UPDATED_MAPPING_ACCOUNT
        defaultCustomerShouldBeFound("mappingAccount.notEquals=" + UPDATED_MAPPING_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCustomersByMappingAccountIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where mappingAccount in DEFAULT_MAPPING_ACCOUNT or UPDATED_MAPPING_ACCOUNT
        defaultCustomerShouldBeFound("mappingAccount.in=" + DEFAULT_MAPPING_ACCOUNT + "," + UPDATED_MAPPING_ACCOUNT);

        // Get all the customerList where mappingAccount equals to UPDATED_MAPPING_ACCOUNT
        defaultCustomerShouldNotBeFound("mappingAccount.in=" + UPDATED_MAPPING_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCustomersByMappingAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where mappingAccount is not null
        defaultCustomerShouldBeFound("mappingAccount.specified=true");

        // Get all the customerList where mappingAccount is null
        defaultCustomerShouldNotBeFound("mappingAccount.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByMappingAccountContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where mappingAccount contains DEFAULT_MAPPING_ACCOUNT
        defaultCustomerShouldBeFound("mappingAccount.contains=" + DEFAULT_MAPPING_ACCOUNT);

        // Get all the customerList where mappingAccount contains UPDATED_MAPPING_ACCOUNT
        defaultCustomerShouldNotBeFound("mappingAccount.contains=" + UPDATED_MAPPING_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCustomersByMappingAccountNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where mappingAccount does not contain DEFAULT_MAPPING_ACCOUNT
        defaultCustomerShouldNotBeFound("mappingAccount.doesNotContain=" + DEFAULT_MAPPING_ACCOUNT);

        // Get all the customerList where mappingAccount does not contain UPDATED_MAPPING_ACCOUNT
        defaultCustomerShouldBeFound("mappingAccount.doesNotContain=" + UPDATED_MAPPING_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountEmail equals to DEFAULT_ACCOUNT_EMAIL
        defaultCustomerShouldBeFound("accountEmail.equals=" + DEFAULT_ACCOUNT_EMAIL);

        // Get all the customerList where accountEmail equals to UPDATED_ACCOUNT_EMAIL
        defaultCustomerShouldNotBeFound("accountEmail.equals=" + UPDATED_ACCOUNT_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountEmail not equals to DEFAULT_ACCOUNT_EMAIL
        defaultCustomerShouldNotBeFound("accountEmail.notEquals=" + DEFAULT_ACCOUNT_EMAIL);

        // Get all the customerList where accountEmail not equals to UPDATED_ACCOUNT_EMAIL
        defaultCustomerShouldBeFound("accountEmail.notEquals=" + UPDATED_ACCOUNT_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountEmailIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountEmail in DEFAULT_ACCOUNT_EMAIL or UPDATED_ACCOUNT_EMAIL
        defaultCustomerShouldBeFound("accountEmail.in=" + DEFAULT_ACCOUNT_EMAIL + "," + UPDATED_ACCOUNT_EMAIL);

        // Get all the customerList where accountEmail equals to UPDATED_ACCOUNT_EMAIL
        defaultCustomerShouldNotBeFound("accountEmail.in=" + UPDATED_ACCOUNT_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountEmail is not null
        defaultCustomerShouldBeFound("accountEmail.specified=true");

        // Get all the customerList where accountEmail is null
        defaultCustomerShouldNotBeFound("accountEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByAccountEmailContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountEmail contains DEFAULT_ACCOUNT_EMAIL
        defaultCustomerShouldBeFound("accountEmail.contains=" + DEFAULT_ACCOUNT_EMAIL);

        // Get all the customerList where accountEmail contains UPDATED_ACCOUNT_EMAIL
        defaultCustomerShouldNotBeFound("accountEmail.contains=" + UPDATED_ACCOUNT_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountEmailNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountEmail does not contain DEFAULT_ACCOUNT_EMAIL
        defaultCustomerShouldNotBeFound("accountEmail.doesNotContain=" + DEFAULT_ACCOUNT_EMAIL);

        // Get all the customerList where accountEmail does not contain UPDATED_ACCOUNT_EMAIL
        defaultCustomerShouldBeFound("accountEmail.doesNotContain=" + UPDATED_ACCOUNT_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountPhone equals to DEFAULT_ACCOUNT_PHONE
        defaultCustomerShouldBeFound("accountPhone.equals=" + DEFAULT_ACCOUNT_PHONE);

        // Get all the customerList where accountPhone equals to UPDATED_ACCOUNT_PHONE
        defaultCustomerShouldNotBeFound("accountPhone.equals=" + UPDATED_ACCOUNT_PHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountPhone not equals to DEFAULT_ACCOUNT_PHONE
        defaultCustomerShouldNotBeFound("accountPhone.notEquals=" + DEFAULT_ACCOUNT_PHONE);

        // Get all the customerList where accountPhone not equals to UPDATED_ACCOUNT_PHONE
        defaultCustomerShouldBeFound("accountPhone.notEquals=" + UPDATED_ACCOUNT_PHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountPhone in DEFAULT_ACCOUNT_PHONE or UPDATED_ACCOUNT_PHONE
        defaultCustomerShouldBeFound("accountPhone.in=" + DEFAULT_ACCOUNT_PHONE + "," + UPDATED_ACCOUNT_PHONE);

        // Get all the customerList where accountPhone equals to UPDATED_ACCOUNT_PHONE
        defaultCustomerShouldNotBeFound("accountPhone.in=" + UPDATED_ACCOUNT_PHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountPhone is not null
        defaultCustomerShouldBeFound("accountPhone.specified=true");

        // Get all the customerList where accountPhone is null
        defaultCustomerShouldNotBeFound("accountPhone.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByAccountPhoneContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountPhone contains DEFAULT_ACCOUNT_PHONE
        defaultCustomerShouldBeFound("accountPhone.contains=" + DEFAULT_ACCOUNT_PHONE);

        // Get all the customerList where accountPhone contains UPDATED_ACCOUNT_PHONE
        defaultCustomerShouldNotBeFound("accountPhone.contains=" + UPDATED_ACCOUNT_PHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountPhone does not contain DEFAULT_ACCOUNT_PHONE
        defaultCustomerShouldNotBeFound("accountPhone.doesNotContain=" + DEFAULT_ACCOUNT_PHONE);

        // Get all the customerList where accountPhone does not contain UPDATED_ACCOUNT_PHONE
        defaultCustomerShouldBeFound("accountPhone.doesNotContain=" + UPDATED_ACCOUNT_PHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountTypeName equals to DEFAULT_ACCOUNT_TYPE_NAME
        defaultCustomerShouldBeFound("accountTypeName.equals=" + DEFAULT_ACCOUNT_TYPE_NAME);

        // Get all the customerList where accountTypeName equals to UPDATED_ACCOUNT_TYPE_NAME
        defaultCustomerShouldNotBeFound("accountTypeName.equals=" + UPDATED_ACCOUNT_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountTypeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountTypeName not equals to DEFAULT_ACCOUNT_TYPE_NAME
        defaultCustomerShouldNotBeFound("accountTypeName.notEquals=" + DEFAULT_ACCOUNT_TYPE_NAME);

        // Get all the customerList where accountTypeName not equals to UPDATED_ACCOUNT_TYPE_NAME
        defaultCustomerShouldBeFound("accountTypeName.notEquals=" + UPDATED_ACCOUNT_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountTypeName in DEFAULT_ACCOUNT_TYPE_NAME or UPDATED_ACCOUNT_TYPE_NAME
        defaultCustomerShouldBeFound("accountTypeName.in=" + DEFAULT_ACCOUNT_TYPE_NAME + "," + UPDATED_ACCOUNT_TYPE_NAME);

        // Get all the customerList where accountTypeName equals to UPDATED_ACCOUNT_TYPE_NAME
        defaultCustomerShouldNotBeFound("accountTypeName.in=" + UPDATED_ACCOUNT_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountTypeName is not null
        defaultCustomerShouldBeFound("accountTypeName.specified=true");

        // Get all the customerList where accountTypeName is null
        defaultCustomerShouldNotBeFound("accountTypeName.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByAccountTypeNameContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountTypeName contains DEFAULT_ACCOUNT_TYPE_NAME
        defaultCustomerShouldBeFound("accountTypeName.contains=" + DEFAULT_ACCOUNT_TYPE_NAME);

        // Get all the customerList where accountTypeName contains UPDATED_ACCOUNT_TYPE_NAME
        defaultCustomerShouldNotBeFound("accountTypeName.contains=" + UPDATED_ACCOUNT_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByAccountTypeNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountTypeName does not contain DEFAULT_ACCOUNT_TYPE_NAME
        defaultCustomerShouldNotBeFound("accountTypeName.doesNotContain=" + DEFAULT_ACCOUNT_TYPE_NAME);

        // Get all the customerList where accountTypeName does not contain UPDATED_ACCOUNT_TYPE_NAME
        defaultCustomerShouldBeFound("accountTypeName.doesNotContain=" + UPDATED_ACCOUNT_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByGenderNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where genderName equals to DEFAULT_GENDER_NAME
        defaultCustomerShouldBeFound("genderName.equals=" + DEFAULT_GENDER_NAME);

        // Get all the customerList where genderName equals to UPDATED_GENDER_NAME
        defaultCustomerShouldNotBeFound("genderName.equals=" + UPDATED_GENDER_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByGenderNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where genderName not equals to DEFAULT_GENDER_NAME
        defaultCustomerShouldNotBeFound("genderName.notEquals=" + DEFAULT_GENDER_NAME);

        // Get all the customerList where genderName not equals to UPDATED_GENDER_NAME
        defaultCustomerShouldBeFound("genderName.notEquals=" + UPDATED_GENDER_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByGenderNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where genderName in DEFAULT_GENDER_NAME or UPDATED_GENDER_NAME
        defaultCustomerShouldBeFound("genderName.in=" + DEFAULT_GENDER_NAME + "," + UPDATED_GENDER_NAME);

        // Get all the customerList where genderName equals to UPDATED_GENDER_NAME
        defaultCustomerShouldNotBeFound("genderName.in=" + UPDATED_GENDER_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByGenderNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where genderName is not null
        defaultCustomerShouldBeFound("genderName.specified=true");

        // Get all the customerList where genderName is null
        defaultCustomerShouldNotBeFound("genderName.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByGenderNameContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where genderName contains DEFAULT_GENDER_NAME
        defaultCustomerShouldBeFound("genderName.contains=" + DEFAULT_GENDER_NAME);

        // Get all the customerList where genderName contains UPDATED_GENDER_NAME
        defaultCustomerShouldNotBeFound("genderName.contains=" + UPDATED_GENDER_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByGenderNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where genderName does not contain DEFAULT_GENDER_NAME
        defaultCustomerShouldNotBeFound("genderName.doesNotContain=" + DEFAULT_GENDER_NAME);

        // Get all the customerList where genderName does not contain UPDATED_GENDER_NAME
        defaultCustomerShouldBeFound("genderName.doesNotContain=" + UPDATED_GENDER_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByIndustryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where industryName equals to DEFAULT_INDUSTRY_NAME
        defaultCustomerShouldBeFound("industryName.equals=" + DEFAULT_INDUSTRY_NAME);

        // Get all the customerList where industryName equals to UPDATED_INDUSTRY_NAME
        defaultCustomerShouldNotBeFound("industryName.equals=" + UPDATED_INDUSTRY_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByIndustryNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where industryName not equals to DEFAULT_INDUSTRY_NAME
        defaultCustomerShouldNotBeFound("industryName.notEquals=" + DEFAULT_INDUSTRY_NAME);

        // Get all the customerList where industryName not equals to UPDATED_INDUSTRY_NAME
        defaultCustomerShouldBeFound("industryName.notEquals=" + UPDATED_INDUSTRY_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByIndustryNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where industryName in DEFAULT_INDUSTRY_NAME or UPDATED_INDUSTRY_NAME
        defaultCustomerShouldBeFound("industryName.in=" + DEFAULT_INDUSTRY_NAME + "," + UPDATED_INDUSTRY_NAME);

        // Get all the customerList where industryName equals to UPDATED_INDUSTRY_NAME
        defaultCustomerShouldNotBeFound("industryName.in=" + UPDATED_INDUSTRY_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByIndustryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where industryName is not null
        defaultCustomerShouldBeFound("industryName.specified=true");

        // Get all the customerList where industryName is null
        defaultCustomerShouldNotBeFound("industryName.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByIndustryNameContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where industryName contains DEFAULT_INDUSTRY_NAME
        defaultCustomerShouldBeFound("industryName.contains=" + DEFAULT_INDUSTRY_NAME);

        // Get all the customerList where industryName contains UPDATED_INDUSTRY_NAME
        defaultCustomerShouldNotBeFound("industryName.contains=" + UPDATED_INDUSTRY_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByIndustryNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where industryName does not contain DEFAULT_INDUSTRY_NAME
        defaultCustomerShouldNotBeFound("industryName.doesNotContain=" + DEFAULT_INDUSTRY_NAME);

        // Get all the customerList where industryName does not contain UPDATED_INDUSTRY_NAME
        defaultCustomerShouldBeFound("industryName.doesNotContain=" + UPDATED_INDUSTRY_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByOwnerEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where ownerEmployeeId equals to DEFAULT_OWNER_EMPLOYEE_ID
        defaultCustomerShouldBeFound("ownerEmployeeId.equals=" + DEFAULT_OWNER_EMPLOYEE_ID);

        // Get all the customerList where ownerEmployeeId equals to UPDATED_OWNER_EMPLOYEE_ID
        defaultCustomerShouldNotBeFound("ownerEmployeeId.equals=" + UPDATED_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByOwnerEmployeeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where ownerEmployeeId not equals to DEFAULT_OWNER_EMPLOYEE_ID
        defaultCustomerShouldNotBeFound("ownerEmployeeId.notEquals=" + DEFAULT_OWNER_EMPLOYEE_ID);

        // Get all the customerList where ownerEmployeeId not equals to UPDATED_OWNER_EMPLOYEE_ID
        defaultCustomerShouldBeFound("ownerEmployeeId.notEquals=" + UPDATED_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByOwnerEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where ownerEmployeeId in DEFAULT_OWNER_EMPLOYEE_ID or UPDATED_OWNER_EMPLOYEE_ID
        defaultCustomerShouldBeFound("ownerEmployeeId.in=" + DEFAULT_OWNER_EMPLOYEE_ID + "," + UPDATED_OWNER_EMPLOYEE_ID);

        // Get all the customerList where ownerEmployeeId equals to UPDATED_OWNER_EMPLOYEE_ID
        defaultCustomerShouldNotBeFound("ownerEmployeeId.in=" + UPDATED_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByOwnerEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where ownerEmployeeId is not null
        defaultCustomerShouldBeFound("ownerEmployeeId.specified=true");

        // Get all the customerList where ownerEmployeeId is null
        defaultCustomerShouldNotBeFound("ownerEmployeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByOwnerEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where ownerEmployeeId is greater than or equal to DEFAULT_OWNER_EMPLOYEE_ID
        defaultCustomerShouldBeFound("ownerEmployeeId.greaterThanOrEqual=" + DEFAULT_OWNER_EMPLOYEE_ID);

        // Get all the customerList where ownerEmployeeId is greater than or equal to UPDATED_OWNER_EMPLOYEE_ID
        defaultCustomerShouldNotBeFound("ownerEmployeeId.greaterThanOrEqual=" + UPDATED_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByOwnerEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where ownerEmployeeId is less than or equal to DEFAULT_OWNER_EMPLOYEE_ID
        defaultCustomerShouldBeFound("ownerEmployeeId.lessThanOrEqual=" + DEFAULT_OWNER_EMPLOYEE_ID);

        // Get all the customerList where ownerEmployeeId is less than or equal to SMALLER_OWNER_EMPLOYEE_ID
        defaultCustomerShouldNotBeFound("ownerEmployeeId.lessThanOrEqual=" + SMALLER_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByOwnerEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where ownerEmployeeId is less than DEFAULT_OWNER_EMPLOYEE_ID
        defaultCustomerShouldNotBeFound("ownerEmployeeId.lessThan=" + DEFAULT_OWNER_EMPLOYEE_ID);

        // Get all the customerList where ownerEmployeeId is less than UPDATED_OWNER_EMPLOYEE_ID
        defaultCustomerShouldBeFound("ownerEmployeeId.lessThan=" + UPDATED_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByOwnerEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where ownerEmployeeId is greater than DEFAULT_OWNER_EMPLOYEE_ID
        defaultCustomerShouldNotBeFound("ownerEmployeeId.greaterThan=" + DEFAULT_OWNER_EMPLOYEE_ID);

        // Get all the customerList where ownerEmployeeId is greater than SMALLER_OWNER_EMPLOYEE_ID
        defaultCustomerShouldBeFound("ownerEmployeeId.greaterThan=" + SMALLER_OWNER_EMPLOYEE_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerShouldBeFound(String filter) throws Exception {
        restCustomerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].accountCode").value(hasItem(DEFAULT_ACCOUNT_CODE)))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].mappingAccount").value(hasItem(DEFAULT_MAPPING_ACCOUNT)))
            .andExpect(jsonPath("$.[*].accountEmail").value(hasItem(DEFAULT_ACCOUNT_EMAIL)))
            .andExpect(jsonPath("$.[*].accountPhone").value(hasItem(DEFAULT_ACCOUNT_PHONE)))
            .andExpect(jsonPath("$.[*].accountTypeName").value(hasItem(DEFAULT_ACCOUNT_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].genderName").value(hasItem(DEFAULT_GENDER_NAME)))
            .andExpect(jsonPath("$.[*].industryName").value(hasItem(DEFAULT_INDUSTRY_NAME)))
            .andExpect(jsonPath("$.[*].ownerEmployeeId").value(hasItem(DEFAULT_OWNER_EMPLOYEE_ID.intValue())));

        // Check, that the count call also returns 1
        restCustomerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerShouldNotBeFound(String filter) throws Exception {
        restCustomerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        // Disconnect from session so that the updates on updatedCustomer are not directly saved in db
        em.detach(updatedCustomer);
        updatedCustomer
            .accountId(UPDATED_ACCOUNT_ID)
            .accountCode(UPDATED_ACCOUNT_CODE)
            .accountName(UPDATED_ACCOUNT_NAME)
            .mappingAccount(UPDATED_MAPPING_ACCOUNT)
            .accountEmail(UPDATED_ACCOUNT_EMAIL)
            .accountPhone(UPDATED_ACCOUNT_PHONE)
            .accountTypeName(UPDATED_ACCOUNT_TYPE_NAME)
            .genderName(UPDATED_GENDER_NAME)
            .industryName(UPDATED_INDUSTRY_NAME)
            .ownerEmployeeId(UPDATED_OWNER_EMPLOYEE_ID);

        restCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustomer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustomer))
            )
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testCustomer.getAccountCode()).isEqualTo(UPDATED_ACCOUNT_CODE);
        assertThat(testCustomer.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testCustomer.getMappingAccount()).isEqualTo(UPDATED_MAPPING_ACCOUNT);
        assertThat(testCustomer.getAccountEmail()).isEqualTo(UPDATED_ACCOUNT_EMAIL);
        assertThat(testCustomer.getAccountPhone()).isEqualTo(UPDATED_ACCOUNT_PHONE);
        assertThat(testCustomer.getAccountTypeName()).isEqualTo(UPDATED_ACCOUNT_TYPE_NAME);
        assertThat(testCustomer.getGenderName()).isEqualTo(UPDATED_GENDER_NAME);
        assertThat(testCustomer.getIndustryName()).isEqualTo(UPDATED_INDUSTRY_NAME);
        assertThat(testCustomer.getOwnerEmployeeId()).isEqualTo(UPDATED_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomerWithPatch() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer using partial update
        Customer partialUpdatedCustomer = new Customer();
        partialUpdatedCustomer.setId(customer.getId());

        partialUpdatedCustomer
            .accountId(UPDATED_ACCOUNT_ID)
            .accountEmail(UPDATED_ACCOUNT_EMAIL)
            .genderName(UPDATED_GENDER_NAME)
            .industryName(UPDATED_INDUSTRY_NAME);

        restCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomer))
            )
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testCustomer.getAccountCode()).isEqualTo(DEFAULT_ACCOUNT_CODE);
        assertThat(testCustomer.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testCustomer.getMappingAccount()).isEqualTo(DEFAULT_MAPPING_ACCOUNT);
        assertThat(testCustomer.getAccountEmail()).isEqualTo(UPDATED_ACCOUNT_EMAIL);
        assertThat(testCustomer.getAccountPhone()).isEqualTo(DEFAULT_ACCOUNT_PHONE);
        assertThat(testCustomer.getAccountTypeName()).isEqualTo(DEFAULT_ACCOUNT_TYPE_NAME);
        assertThat(testCustomer.getGenderName()).isEqualTo(UPDATED_GENDER_NAME);
        assertThat(testCustomer.getIndustryName()).isEqualTo(UPDATED_INDUSTRY_NAME);
        assertThat(testCustomer.getOwnerEmployeeId()).isEqualTo(DEFAULT_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustomerWithPatch() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer using partial update
        Customer partialUpdatedCustomer = new Customer();
        partialUpdatedCustomer.setId(customer.getId());

        partialUpdatedCustomer
            .accountId(UPDATED_ACCOUNT_ID)
            .accountCode(UPDATED_ACCOUNT_CODE)
            .accountName(UPDATED_ACCOUNT_NAME)
            .mappingAccount(UPDATED_MAPPING_ACCOUNT)
            .accountEmail(UPDATED_ACCOUNT_EMAIL)
            .accountPhone(UPDATED_ACCOUNT_PHONE)
            .accountTypeName(UPDATED_ACCOUNT_TYPE_NAME)
            .genderName(UPDATED_GENDER_NAME)
            .industryName(UPDATED_INDUSTRY_NAME)
            .ownerEmployeeId(UPDATED_OWNER_EMPLOYEE_ID);

        restCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomer))
            )
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testCustomer.getAccountCode()).isEqualTo(UPDATED_ACCOUNT_CODE);
        assertThat(testCustomer.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testCustomer.getMappingAccount()).isEqualTo(UPDATED_MAPPING_ACCOUNT);
        assertThat(testCustomer.getAccountEmail()).isEqualTo(UPDATED_ACCOUNT_EMAIL);
        assertThat(testCustomer.getAccountPhone()).isEqualTo(UPDATED_ACCOUNT_PHONE);
        assertThat(testCustomer.getAccountTypeName()).isEqualTo(UPDATED_ACCOUNT_TYPE_NAME);
        assertThat(testCustomer.getGenderName()).isEqualTo(UPDATED_GENDER_NAME);
        assertThat(testCustomer.getIndustryName()).isEqualTo(UPDATED_INDUSTRY_NAME);
        assertThat(testCustomer.getOwnerEmployeeId()).isEqualTo(UPDATED_OWNER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Delete the customer
        restCustomerMockMvc
            .perform(delete(ENTITY_API_URL_ID, customer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
