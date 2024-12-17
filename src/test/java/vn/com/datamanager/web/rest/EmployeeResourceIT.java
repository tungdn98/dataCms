package vn.com.datamanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import vn.com.datamanager.domain.EmpGroup;
import vn.com.datamanager.domain.Employee;
import vn.com.datamanager.repository.EmployeeRepository;
import vn.com.datamanager.service.criteria.EmployeeCriteria;

/**
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeResourceIT {

    private static final String DEFAULT_EMPLOYEE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACTIVE = 1;
    private static final Integer UPDATED_ACTIVE = 2;
    private static final Integer SMALLER_ACTIVE = 1 - 1;

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_TITLE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_TITLE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_TITLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_TITLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_FULL_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .employeeCode(DEFAULT_EMPLOYEE_CODE)
            .employeeName(DEFAULT_EMPLOYEE_NAME)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .active(DEFAULT_ACTIVE)
            .companyCode(DEFAULT_COMPANY_CODE)
            .companyName(DEFAULT_COMPANY_NAME)
            .organizationId(DEFAULT_ORGANIZATION_ID)
            .employeeLastName(DEFAULT_EMPLOYEE_LAST_NAME)
            .employeeMiddleName(DEFAULT_EMPLOYEE_MIDDLE_NAME)
            .employeeTitleId(DEFAULT_EMPLOYEE_TITLE_ID)
            .employeeTitleName(DEFAULT_EMPLOYEE_TITLE_NAME)
            .employeeFullName(DEFAULT_EMPLOYEE_FULL_NAME)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return employee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee()
            .employeeCode(UPDATED_EMPLOYEE_CODE)
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .active(UPDATED_ACTIVE)
            .companyCode(UPDATED_COMPANY_CODE)
            .companyName(UPDATED_COMPANY_NAME)
            .organizationId(UPDATED_ORGANIZATION_ID)
            .employeeLastName(UPDATED_EMPLOYEE_LAST_NAME)
            .employeeMiddleName(UPDATED_EMPLOYEE_MIDDLE_NAME)
            .employeeTitleId(UPDATED_EMPLOYEE_TITLE_ID)
            .employeeTitleName(UPDATED_EMPLOYEE_TITLE_NAME)
            .employeeFullName(UPDATED_EMPLOYEE_FULL_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();
        // Create the Employee
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getEmployeeCode()).isEqualTo(DEFAULT_EMPLOYEE_CODE);
        assertThat(testEmployee.getEmployeeName()).isEqualTo(DEFAULT_EMPLOYEE_NAME);
        assertThat(testEmployee.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testEmployee.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testEmployee.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testEmployee.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testEmployee.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testEmployee.getOrganizationId()).isEqualTo(DEFAULT_ORGANIZATION_ID);
        assertThat(testEmployee.getEmployeeLastName()).isEqualTo(DEFAULT_EMPLOYEE_LAST_NAME);
        assertThat(testEmployee.getEmployeeMiddleName()).isEqualTo(DEFAULT_EMPLOYEE_MIDDLE_NAME);
        assertThat(testEmployee.getEmployeeTitleId()).isEqualTo(DEFAULT_EMPLOYEE_TITLE_ID);
        assertThat(testEmployee.getEmployeeTitleName()).isEqualTo(DEFAULT_EMPLOYEE_TITLE_NAME);
        assertThat(testEmployee.getEmployeeFullName()).isEqualTo(DEFAULT_EMPLOYEE_FULL_NAME);
        assertThat(testEmployee.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEmployee.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEmployee.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testEmployee.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createEmployeeWithExistingId() throws Exception {
        // Create the Employee with an existing ID
        employee.setId(1L);

        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEmployeeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setEmployeeName(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setUsername(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeCode").value(hasItem(DEFAULT_EMPLOYEE_CODE)))
            .andExpect(jsonPath("$.[*].employeeName").value(hasItem(DEFAULT_EMPLOYEE_NAME)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].organizationId").value(hasItem(DEFAULT_ORGANIZATION_ID)))
            .andExpect(jsonPath("$.[*].employeeLastName").value(hasItem(DEFAULT_EMPLOYEE_LAST_NAME)))
            .andExpect(jsonPath("$.[*].employeeMiddleName").value(hasItem(DEFAULT_EMPLOYEE_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].employeeTitleId").value(hasItem(DEFAULT_EMPLOYEE_TITLE_ID)))
            .andExpect(jsonPath("$.[*].employeeTitleName").value(hasItem(DEFAULT_EMPLOYEE_TITLE_NAME)))
            .andExpect(jsonPath("$.[*].employeeFullName").value(hasItem(DEFAULT_EMPLOYEE_FULL_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL_ID, employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.employeeCode").value(DEFAULT_EMPLOYEE_CODE))
            .andExpect(jsonPath("$.employeeName").value(DEFAULT_EMPLOYEE_NAME))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.organizationId").value(DEFAULT_ORGANIZATION_ID))
            .andExpect(jsonPath("$.employeeLastName").value(DEFAULT_EMPLOYEE_LAST_NAME))
            .andExpect(jsonPath("$.employeeMiddleName").value(DEFAULT_EMPLOYEE_MIDDLE_NAME))
            .andExpect(jsonPath("$.employeeTitleId").value(DEFAULT_EMPLOYEE_TITLE_ID))
            .andExpect(jsonPath("$.employeeTitleName").value(DEFAULT_EMPLOYEE_TITLE_NAME))
            .andExpect(jsonPath("$.employeeFullName").value(DEFAULT_EMPLOYEE_FULL_NAME))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getEmployeesByIdFiltering() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        Long id = employee.getId();

        defaultEmployeeShouldBeFound("id.equals=" + id);
        defaultEmployeeShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeCode equals to DEFAULT_EMPLOYEE_CODE
        defaultEmployeeShouldBeFound("employeeCode.equals=" + DEFAULT_EMPLOYEE_CODE);

        // Get all the employeeList where employeeCode equals to UPDATED_EMPLOYEE_CODE
        defaultEmployeeShouldNotBeFound("employeeCode.equals=" + UPDATED_EMPLOYEE_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeCode not equals to DEFAULT_EMPLOYEE_CODE
        defaultEmployeeShouldNotBeFound("employeeCode.notEquals=" + DEFAULT_EMPLOYEE_CODE);

        // Get all the employeeList where employeeCode not equals to UPDATED_EMPLOYEE_CODE
        defaultEmployeeShouldBeFound("employeeCode.notEquals=" + UPDATED_EMPLOYEE_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeCodeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeCode in DEFAULT_EMPLOYEE_CODE or UPDATED_EMPLOYEE_CODE
        defaultEmployeeShouldBeFound("employeeCode.in=" + DEFAULT_EMPLOYEE_CODE + "," + UPDATED_EMPLOYEE_CODE);

        // Get all the employeeList where employeeCode equals to UPDATED_EMPLOYEE_CODE
        defaultEmployeeShouldNotBeFound("employeeCode.in=" + UPDATED_EMPLOYEE_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeCode is not null
        defaultEmployeeShouldBeFound("employeeCode.specified=true");

        // Get all the employeeList where employeeCode is null
        defaultEmployeeShouldNotBeFound("employeeCode.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeCodeContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeCode contains DEFAULT_EMPLOYEE_CODE
        defaultEmployeeShouldBeFound("employeeCode.contains=" + DEFAULT_EMPLOYEE_CODE);

        // Get all the employeeList where employeeCode contains UPDATED_EMPLOYEE_CODE
        defaultEmployeeShouldNotBeFound("employeeCode.contains=" + UPDATED_EMPLOYEE_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeCodeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeCode does not contain DEFAULT_EMPLOYEE_CODE
        defaultEmployeeShouldNotBeFound("employeeCode.doesNotContain=" + DEFAULT_EMPLOYEE_CODE);

        // Get all the employeeList where employeeCode does not contain UPDATED_EMPLOYEE_CODE
        defaultEmployeeShouldBeFound("employeeCode.doesNotContain=" + UPDATED_EMPLOYEE_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeName equals to DEFAULT_EMPLOYEE_NAME
        defaultEmployeeShouldBeFound("employeeName.equals=" + DEFAULT_EMPLOYEE_NAME);

        // Get all the employeeList where employeeName equals to UPDATED_EMPLOYEE_NAME
        defaultEmployeeShouldNotBeFound("employeeName.equals=" + UPDATED_EMPLOYEE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeName not equals to DEFAULT_EMPLOYEE_NAME
        defaultEmployeeShouldNotBeFound("employeeName.notEquals=" + DEFAULT_EMPLOYEE_NAME);

        // Get all the employeeList where employeeName not equals to UPDATED_EMPLOYEE_NAME
        defaultEmployeeShouldBeFound("employeeName.notEquals=" + UPDATED_EMPLOYEE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeName in DEFAULT_EMPLOYEE_NAME or UPDATED_EMPLOYEE_NAME
        defaultEmployeeShouldBeFound("employeeName.in=" + DEFAULT_EMPLOYEE_NAME + "," + UPDATED_EMPLOYEE_NAME);

        // Get all the employeeList where employeeName equals to UPDATED_EMPLOYEE_NAME
        defaultEmployeeShouldNotBeFound("employeeName.in=" + UPDATED_EMPLOYEE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeName is not null
        defaultEmployeeShouldBeFound("employeeName.specified=true");

        // Get all the employeeList where employeeName is null
        defaultEmployeeShouldNotBeFound("employeeName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeName contains DEFAULT_EMPLOYEE_NAME
        defaultEmployeeShouldBeFound("employeeName.contains=" + DEFAULT_EMPLOYEE_NAME);

        // Get all the employeeList where employeeName contains UPDATED_EMPLOYEE_NAME
        defaultEmployeeShouldNotBeFound("employeeName.contains=" + UPDATED_EMPLOYEE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeName does not contain DEFAULT_EMPLOYEE_NAME
        defaultEmployeeShouldNotBeFound("employeeName.doesNotContain=" + DEFAULT_EMPLOYEE_NAME);

        // Get all the employeeList where employeeName does not contain UPDATED_EMPLOYEE_NAME
        defaultEmployeeShouldBeFound("employeeName.doesNotContain=" + UPDATED_EMPLOYEE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where username equals to DEFAULT_USERNAME
        defaultEmployeeShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the employeeList where username equals to UPDATED_USERNAME
        defaultEmployeeShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByUsernameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where username not equals to DEFAULT_USERNAME
        defaultEmployeeShouldNotBeFound("username.notEquals=" + DEFAULT_USERNAME);

        // Get all the employeeList where username not equals to UPDATED_USERNAME
        defaultEmployeeShouldBeFound("username.notEquals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultEmployeeShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the employeeList where username equals to UPDATED_USERNAME
        defaultEmployeeShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where username is not null
        defaultEmployeeShouldBeFound("username.specified=true");

        // Get all the employeeList where username is null
        defaultEmployeeShouldNotBeFound("username.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByUsernameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where username contains DEFAULT_USERNAME
        defaultEmployeeShouldBeFound("username.contains=" + DEFAULT_USERNAME);

        // Get all the employeeList where username contains UPDATED_USERNAME
        defaultEmployeeShouldNotBeFound("username.contains=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByUsernameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where username does not contain DEFAULT_USERNAME
        defaultEmployeeShouldNotBeFound("username.doesNotContain=" + DEFAULT_USERNAME);

        // Get all the employeeList where username does not contain UPDATED_USERNAME
        defaultEmployeeShouldBeFound("username.doesNotContain=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where password equals to DEFAULT_PASSWORD
        defaultEmployeeShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the employeeList where password equals to UPDATED_PASSWORD
        defaultEmployeeShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllEmployeesByPasswordIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where password not equals to DEFAULT_PASSWORD
        defaultEmployeeShouldNotBeFound("password.notEquals=" + DEFAULT_PASSWORD);

        // Get all the employeeList where password not equals to UPDATED_PASSWORD
        defaultEmployeeShouldBeFound("password.notEquals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllEmployeesByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultEmployeeShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the employeeList where password equals to UPDATED_PASSWORD
        defaultEmployeeShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllEmployeesByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where password is not null
        defaultEmployeeShouldBeFound("password.specified=true");

        // Get all the employeeList where password is null
        defaultEmployeeShouldNotBeFound("password.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByPasswordContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where password contains DEFAULT_PASSWORD
        defaultEmployeeShouldBeFound("password.contains=" + DEFAULT_PASSWORD);

        // Get all the employeeList where password contains UPDATED_PASSWORD
        defaultEmployeeShouldNotBeFound("password.contains=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllEmployeesByPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where password does not contain DEFAULT_PASSWORD
        defaultEmployeeShouldNotBeFound("password.doesNotContain=" + DEFAULT_PASSWORD);

        // Get all the employeeList where password does not contain UPDATED_PASSWORD
        defaultEmployeeShouldBeFound("password.doesNotContain=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllEmployeesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where active equals to DEFAULT_ACTIVE
        defaultEmployeeShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the employeeList where active equals to UPDATED_ACTIVE
        defaultEmployeeShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEmployeesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where active not equals to DEFAULT_ACTIVE
        defaultEmployeeShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the employeeList where active not equals to UPDATED_ACTIVE
        defaultEmployeeShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEmployeesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultEmployeeShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the employeeList where active equals to UPDATED_ACTIVE
        defaultEmployeeShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEmployeesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where active is not null
        defaultEmployeeShouldBeFound("active.specified=true");

        // Get all the employeeList where active is null
        defaultEmployeeShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByActiveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where active is greater than or equal to DEFAULT_ACTIVE
        defaultEmployeeShouldBeFound("active.greaterThanOrEqual=" + DEFAULT_ACTIVE);

        // Get all the employeeList where active is greater than or equal to UPDATED_ACTIVE
        defaultEmployeeShouldNotBeFound("active.greaterThanOrEqual=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEmployeesByActiveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where active is less than or equal to DEFAULT_ACTIVE
        defaultEmployeeShouldBeFound("active.lessThanOrEqual=" + DEFAULT_ACTIVE);

        // Get all the employeeList where active is less than or equal to SMALLER_ACTIVE
        defaultEmployeeShouldNotBeFound("active.lessThanOrEqual=" + SMALLER_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEmployeesByActiveIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where active is less than DEFAULT_ACTIVE
        defaultEmployeeShouldNotBeFound("active.lessThan=" + DEFAULT_ACTIVE);

        // Get all the employeeList where active is less than UPDATED_ACTIVE
        defaultEmployeeShouldBeFound("active.lessThan=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEmployeesByActiveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where active is greater than DEFAULT_ACTIVE
        defaultEmployeeShouldNotBeFound("active.greaterThan=" + DEFAULT_ACTIVE);

        // Get all the employeeList where active is greater than SMALLER_ACTIVE
        defaultEmployeeShouldBeFound("active.greaterThan=" + SMALLER_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCompanyCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyCode equals to DEFAULT_COMPANY_CODE
        defaultEmployeeShouldBeFound("companyCode.equals=" + DEFAULT_COMPANY_CODE);

        // Get all the employeeList where companyCode equals to UPDATED_COMPANY_CODE
        defaultEmployeeShouldNotBeFound("companyCode.equals=" + UPDATED_COMPANY_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCompanyCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyCode not equals to DEFAULT_COMPANY_CODE
        defaultEmployeeShouldNotBeFound("companyCode.notEquals=" + DEFAULT_COMPANY_CODE);

        // Get all the employeeList where companyCode not equals to UPDATED_COMPANY_CODE
        defaultEmployeeShouldBeFound("companyCode.notEquals=" + UPDATED_COMPANY_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCompanyCodeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyCode in DEFAULT_COMPANY_CODE or UPDATED_COMPANY_CODE
        defaultEmployeeShouldBeFound("companyCode.in=" + DEFAULT_COMPANY_CODE + "," + UPDATED_COMPANY_CODE);

        // Get all the employeeList where companyCode equals to UPDATED_COMPANY_CODE
        defaultEmployeeShouldNotBeFound("companyCode.in=" + UPDATED_COMPANY_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCompanyCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyCode is not null
        defaultEmployeeShouldBeFound("companyCode.specified=true");

        // Get all the employeeList where companyCode is null
        defaultEmployeeShouldNotBeFound("companyCode.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByCompanyCodeContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyCode contains DEFAULT_COMPANY_CODE
        defaultEmployeeShouldBeFound("companyCode.contains=" + DEFAULT_COMPANY_CODE);

        // Get all the employeeList where companyCode contains UPDATED_COMPANY_CODE
        defaultEmployeeShouldNotBeFound("companyCode.contains=" + UPDATED_COMPANY_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCompanyCodeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyCode does not contain DEFAULT_COMPANY_CODE
        defaultEmployeeShouldNotBeFound("companyCode.doesNotContain=" + DEFAULT_COMPANY_CODE);

        // Get all the employeeList where companyCode does not contain UPDATED_COMPANY_CODE
        defaultEmployeeShouldBeFound("companyCode.doesNotContain=" + UPDATED_COMPANY_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCompanyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyName equals to DEFAULT_COMPANY_NAME
        defaultEmployeeShouldBeFound("companyName.equals=" + DEFAULT_COMPANY_NAME);

        // Get all the employeeList where companyName equals to UPDATED_COMPANY_NAME
        defaultEmployeeShouldNotBeFound("companyName.equals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByCompanyNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyName not equals to DEFAULT_COMPANY_NAME
        defaultEmployeeShouldNotBeFound("companyName.notEquals=" + DEFAULT_COMPANY_NAME);

        // Get all the employeeList where companyName not equals to UPDATED_COMPANY_NAME
        defaultEmployeeShouldBeFound("companyName.notEquals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByCompanyNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyName in DEFAULT_COMPANY_NAME or UPDATED_COMPANY_NAME
        defaultEmployeeShouldBeFound("companyName.in=" + DEFAULT_COMPANY_NAME + "," + UPDATED_COMPANY_NAME);

        // Get all the employeeList where companyName equals to UPDATED_COMPANY_NAME
        defaultEmployeeShouldNotBeFound("companyName.in=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByCompanyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyName is not null
        defaultEmployeeShouldBeFound("companyName.specified=true");

        // Get all the employeeList where companyName is null
        defaultEmployeeShouldNotBeFound("companyName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByCompanyNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyName contains DEFAULT_COMPANY_NAME
        defaultEmployeeShouldBeFound("companyName.contains=" + DEFAULT_COMPANY_NAME);

        // Get all the employeeList where companyName contains UPDATED_COMPANY_NAME
        defaultEmployeeShouldNotBeFound("companyName.contains=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByCompanyNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyName does not contain DEFAULT_COMPANY_NAME
        defaultEmployeeShouldNotBeFound("companyName.doesNotContain=" + DEFAULT_COMPANY_NAME);

        // Get all the employeeList where companyName does not contain UPDATED_COMPANY_NAME
        defaultEmployeeShouldBeFound("companyName.doesNotContain=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organizationId equals to DEFAULT_ORGANIZATION_ID
        defaultEmployeeShouldBeFound("organizationId.equals=" + DEFAULT_ORGANIZATION_ID);

        // Get all the employeeList where organizationId equals to UPDATED_ORGANIZATION_ID
        defaultEmployeeShouldNotBeFound("organizationId.equals=" + UPDATED_ORGANIZATION_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organizationId not equals to DEFAULT_ORGANIZATION_ID
        defaultEmployeeShouldNotBeFound("organizationId.notEquals=" + DEFAULT_ORGANIZATION_ID);

        // Get all the employeeList where organizationId not equals to UPDATED_ORGANIZATION_ID
        defaultEmployeeShouldBeFound("organizationId.notEquals=" + UPDATED_ORGANIZATION_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organizationId in DEFAULT_ORGANIZATION_ID or UPDATED_ORGANIZATION_ID
        defaultEmployeeShouldBeFound("organizationId.in=" + DEFAULT_ORGANIZATION_ID + "," + UPDATED_ORGANIZATION_ID);

        // Get all the employeeList where organizationId equals to UPDATED_ORGANIZATION_ID
        defaultEmployeeShouldNotBeFound("organizationId.in=" + UPDATED_ORGANIZATION_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organizationId is not null
        defaultEmployeeShouldBeFound("organizationId.specified=true");

        // Get all the employeeList where organizationId is null
        defaultEmployeeShouldNotBeFound("organizationId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationIdContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organizationId contains DEFAULT_ORGANIZATION_ID
        defaultEmployeeShouldBeFound("organizationId.contains=" + DEFAULT_ORGANIZATION_ID);

        // Get all the employeeList where organizationId contains UPDATED_ORGANIZATION_ID
        defaultEmployeeShouldNotBeFound("organizationId.contains=" + UPDATED_ORGANIZATION_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationIdNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organizationId does not contain DEFAULT_ORGANIZATION_ID
        defaultEmployeeShouldNotBeFound("organizationId.doesNotContain=" + DEFAULT_ORGANIZATION_ID);

        // Get all the employeeList where organizationId does not contain UPDATED_ORGANIZATION_ID
        defaultEmployeeShouldBeFound("organizationId.doesNotContain=" + UPDATED_ORGANIZATION_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeLastName equals to DEFAULT_EMPLOYEE_LAST_NAME
        defaultEmployeeShouldBeFound("employeeLastName.equals=" + DEFAULT_EMPLOYEE_LAST_NAME);

        // Get all the employeeList where employeeLastName equals to UPDATED_EMPLOYEE_LAST_NAME
        defaultEmployeeShouldNotBeFound("employeeLastName.equals=" + UPDATED_EMPLOYEE_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeLastName not equals to DEFAULT_EMPLOYEE_LAST_NAME
        defaultEmployeeShouldNotBeFound("employeeLastName.notEquals=" + DEFAULT_EMPLOYEE_LAST_NAME);

        // Get all the employeeList where employeeLastName not equals to UPDATED_EMPLOYEE_LAST_NAME
        defaultEmployeeShouldBeFound("employeeLastName.notEquals=" + UPDATED_EMPLOYEE_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeLastName in DEFAULT_EMPLOYEE_LAST_NAME or UPDATED_EMPLOYEE_LAST_NAME
        defaultEmployeeShouldBeFound("employeeLastName.in=" + DEFAULT_EMPLOYEE_LAST_NAME + "," + UPDATED_EMPLOYEE_LAST_NAME);

        // Get all the employeeList where employeeLastName equals to UPDATED_EMPLOYEE_LAST_NAME
        defaultEmployeeShouldNotBeFound("employeeLastName.in=" + UPDATED_EMPLOYEE_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeLastName is not null
        defaultEmployeeShouldBeFound("employeeLastName.specified=true");

        // Get all the employeeList where employeeLastName is null
        defaultEmployeeShouldNotBeFound("employeeLastName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeLastNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeLastName contains DEFAULT_EMPLOYEE_LAST_NAME
        defaultEmployeeShouldBeFound("employeeLastName.contains=" + DEFAULT_EMPLOYEE_LAST_NAME);

        // Get all the employeeList where employeeLastName contains UPDATED_EMPLOYEE_LAST_NAME
        defaultEmployeeShouldNotBeFound("employeeLastName.contains=" + UPDATED_EMPLOYEE_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeLastName does not contain DEFAULT_EMPLOYEE_LAST_NAME
        defaultEmployeeShouldNotBeFound("employeeLastName.doesNotContain=" + DEFAULT_EMPLOYEE_LAST_NAME);

        // Get all the employeeList where employeeLastName does not contain UPDATED_EMPLOYEE_LAST_NAME
        defaultEmployeeShouldBeFound("employeeLastName.doesNotContain=" + UPDATED_EMPLOYEE_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeMiddleName equals to DEFAULT_EMPLOYEE_MIDDLE_NAME
        defaultEmployeeShouldBeFound("employeeMiddleName.equals=" + DEFAULT_EMPLOYEE_MIDDLE_NAME);

        // Get all the employeeList where employeeMiddleName equals to UPDATED_EMPLOYEE_MIDDLE_NAME
        defaultEmployeeShouldNotBeFound("employeeMiddleName.equals=" + UPDATED_EMPLOYEE_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeMiddleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeMiddleName not equals to DEFAULT_EMPLOYEE_MIDDLE_NAME
        defaultEmployeeShouldNotBeFound("employeeMiddleName.notEquals=" + DEFAULT_EMPLOYEE_MIDDLE_NAME);

        // Get all the employeeList where employeeMiddleName not equals to UPDATED_EMPLOYEE_MIDDLE_NAME
        defaultEmployeeShouldBeFound("employeeMiddleName.notEquals=" + UPDATED_EMPLOYEE_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeMiddleName in DEFAULT_EMPLOYEE_MIDDLE_NAME or UPDATED_EMPLOYEE_MIDDLE_NAME
        defaultEmployeeShouldBeFound("employeeMiddleName.in=" + DEFAULT_EMPLOYEE_MIDDLE_NAME + "," + UPDATED_EMPLOYEE_MIDDLE_NAME);

        // Get all the employeeList where employeeMiddleName equals to UPDATED_EMPLOYEE_MIDDLE_NAME
        defaultEmployeeShouldNotBeFound("employeeMiddleName.in=" + UPDATED_EMPLOYEE_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeMiddleName is not null
        defaultEmployeeShouldBeFound("employeeMiddleName.specified=true");

        // Get all the employeeList where employeeMiddleName is null
        defaultEmployeeShouldNotBeFound("employeeMiddleName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeMiddleName contains DEFAULT_EMPLOYEE_MIDDLE_NAME
        defaultEmployeeShouldBeFound("employeeMiddleName.contains=" + DEFAULT_EMPLOYEE_MIDDLE_NAME);

        // Get all the employeeList where employeeMiddleName contains UPDATED_EMPLOYEE_MIDDLE_NAME
        defaultEmployeeShouldNotBeFound("employeeMiddleName.contains=" + UPDATED_EMPLOYEE_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeMiddleName does not contain DEFAULT_EMPLOYEE_MIDDLE_NAME
        defaultEmployeeShouldNotBeFound("employeeMiddleName.doesNotContain=" + DEFAULT_EMPLOYEE_MIDDLE_NAME);

        // Get all the employeeList where employeeMiddleName does not contain UPDATED_EMPLOYEE_MIDDLE_NAME
        defaultEmployeeShouldBeFound("employeeMiddleName.doesNotContain=" + UPDATED_EMPLOYEE_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeTitleIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeTitleId equals to DEFAULT_EMPLOYEE_TITLE_ID
        defaultEmployeeShouldBeFound("employeeTitleId.equals=" + DEFAULT_EMPLOYEE_TITLE_ID);

        // Get all the employeeList where employeeTitleId equals to UPDATED_EMPLOYEE_TITLE_ID
        defaultEmployeeShouldNotBeFound("employeeTitleId.equals=" + UPDATED_EMPLOYEE_TITLE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeTitleIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeTitleId not equals to DEFAULT_EMPLOYEE_TITLE_ID
        defaultEmployeeShouldNotBeFound("employeeTitleId.notEquals=" + DEFAULT_EMPLOYEE_TITLE_ID);

        // Get all the employeeList where employeeTitleId not equals to UPDATED_EMPLOYEE_TITLE_ID
        defaultEmployeeShouldBeFound("employeeTitleId.notEquals=" + UPDATED_EMPLOYEE_TITLE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeTitleIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeTitleId in DEFAULT_EMPLOYEE_TITLE_ID or UPDATED_EMPLOYEE_TITLE_ID
        defaultEmployeeShouldBeFound("employeeTitleId.in=" + DEFAULT_EMPLOYEE_TITLE_ID + "," + UPDATED_EMPLOYEE_TITLE_ID);

        // Get all the employeeList where employeeTitleId equals to UPDATED_EMPLOYEE_TITLE_ID
        defaultEmployeeShouldNotBeFound("employeeTitleId.in=" + UPDATED_EMPLOYEE_TITLE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeTitleIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeTitleId is not null
        defaultEmployeeShouldBeFound("employeeTitleId.specified=true");

        // Get all the employeeList where employeeTitleId is null
        defaultEmployeeShouldNotBeFound("employeeTitleId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeTitleIdContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeTitleId contains DEFAULT_EMPLOYEE_TITLE_ID
        defaultEmployeeShouldBeFound("employeeTitleId.contains=" + DEFAULT_EMPLOYEE_TITLE_ID);

        // Get all the employeeList where employeeTitleId contains UPDATED_EMPLOYEE_TITLE_ID
        defaultEmployeeShouldNotBeFound("employeeTitleId.contains=" + UPDATED_EMPLOYEE_TITLE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeTitleIdNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeTitleId does not contain DEFAULT_EMPLOYEE_TITLE_ID
        defaultEmployeeShouldNotBeFound("employeeTitleId.doesNotContain=" + DEFAULT_EMPLOYEE_TITLE_ID);

        // Get all the employeeList where employeeTitleId does not contain UPDATED_EMPLOYEE_TITLE_ID
        defaultEmployeeShouldBeFound("employeeTitleId.doesNotContain=" + UPDATED_EMPLOYEE_TITLE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeTitleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeTitleName equals to DEFAULT_EMPLOYEE_TITLE_NAME
        defaultEmployeeShouldBeFound("employeeTitleName.equals=" + DEFAULT_EMPLOYEE_TITLE_NAME);

        // Get all the employeeList where employeeTitleName equals to UPDATED_EMPLOYEE_TITLE_NAME
        defaultEmployeeShouldNotBeFound("employeeTitleName.equals=" + UPDATED_EMPLOYEE_TITLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeTitleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeTitleName not equals to DEFAULT_EMPLOYEE_TITLE_NAME
        defaultEmployeeShouldNotBeFound("employeeTitleName.notEquals=" + DEFAULT_EMPLOYEE_TITLE_NAME);

        // Get all the employeeList where employeeTitleName not equals to UPDATED_EMPLOYEE_TITLE_NAME
        defaultEmployeeShouldBeFound("employeeTitleName.notEquals=" + UPDATED_EMPLOYEE_TITLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeTitleNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeTitleName in DEFAULT_EMPLOYEE_TITLE_NAME or UPDATED_EMPLOYEE_TITLE_NAME
        defaultEmployeeShouldBeFound("employeeTitleName.in=" + DEFAULT_EMPLOYEE_TITLE_NAME + "," + UPDATED_EMPLOYEE_TITLE_NAME);

        // Get all the employeeList where employeeTitleName equals to UPDATED_EMPLOYEE_TITLE_NAME
        defaultEmployeeShouldNotBeFound("employeeTitleName.in=" + UPDATED_EMPLOYEE_TITLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeTitleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeTitleName is not null
        defaultEmployeeShouldBeFound("employeeTitleName.specified=true");

        // Get all the employeeList where employeeTitleName is null
        defaultEmployeeShouldNotBeFound("employeeTitleName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeTitleNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeTitleName contains DEFAULT_EMPLOYEE_TITLE_NAME
        defaultEmployeeShouldBeFound("employeeTitleName.contains=" + DEFAULT_EMPLOYEE_TITLE_NAME);

        // Get all the employeeList where employeeTitleName contains UPDATED_EMPLOYEE_TITLE_NAME
        defaultEmployeeShouldNotBeFound("employeeTitleName.contains=" + UPDATED_EMPLOYEE_TITLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeTitleNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeTitleName does not contain DEFAULT_EMPLOYEE_TITLE_NAME
        defaultEmployeeShouldNotBeFound("employeeTitleName.doesNotContain=" + DEFAULT_EMPLOYEE_TITLE_NAME);

        // Get all the employeeList where employeeTitleName does not contain UPDATED_EMPLOYEE_TITLE_NAME
        defaultEmployeeShouldBeFound("employeeTitleName.doesNotContain=" + UPDATED_EMPLOYEE_TITLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeFullName equals to DEFAULT_EMPLOYEE_FULL_NAME
        defaultEmployeeShouldBeFound("employeeFullName.equals=" + DEFAULT_EMPLOYEE_FULL_NAME);

        // Get all the employeeList where employeeFullName equals to UPDATED_EMPLOYEE_FULL_NAME
        defaultEmployeeShouldNotBeFound("employeeFullName.equals=" + UPDATED_EMPLOYEE_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeFullNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeFullName not equals to DEFAULT_EMPLOYEE_FULL_NAME
        defaultEmployeeShouldNotBeFound("employeeFullName.notEquals=" + DEFAULT_EMPLOYEE_FULL_NAME);

        // Get all the employeeList where employeeFullName not equals to UPDATED_EMPLOYEE_FULL_NAME
        defaultEmployeeShouldBeFound("employeeFullName.notEquals=" + UPDATED_EMPLOYEE_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeFullName in DEFAULT_EMPLOYEE_FULL_NAME or UPDATED_EMPLOYEE_FULL_NAME
        defaultEmployeeShouldBeFound("employeeFullName.in=" + DEFAULT_EMPLOYEE_FULL_NAME + "," + UPDATED_EMPLOYEE_FULL_NAME);

        // Get all the employeeList where employeeFullName equals to UPDATED_EMPLOYEE_FULL_NAME
        defaultEmployeeShouldNotBeFound("employeeFullName.in=" + UPDATED_EMPLOYEE_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeFullName is not null
        defaultEmployeeShouldBeFound("employeeFullName.specified=true");

        // Get all the employeeList where employeeFullName is null
        defaultEmployeeShouldNotBeFound("employeeFullName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeFullNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeFullName contains DEFAULT_EMPLOYEE_FULL_NAME
        defaultEmployeeShouldBeFound("employeeFullName.contains=" + DEFAULT_EMPLOYEE_FULL_NAME);

        // Get all the employeeList where employeeFullName contains UPDATED_EMPLOYEE_FULL_NAME
        defaultEmployeeShouldNotBeFound("employeeFullName.contains=" + UPDATED_EMPLOYEE_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeFullName does not contain DEFAULT_EMPLOYEE_FULL_NAME
        defaultEmployeeShouldNotBeFound("employeeFullName.doesNotContain=" + DEFAULT_EMPLOYEE_FULL_NAME);

        // Get all the employeeList where employeeFullName does not contain UPDATED_EMPLOYEE_FULL_NAME
        defaultEmployeeShouldBeFound("employeeFullName.doesNotContain=" + UPDATED_EMPLOYEE_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdDate equals to DEFAULT_CREATED_DATE
        defaultEmployeeShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the employeeList where createdDate equals to UPDATED_CREATED_DATE
        defaultEmployeeShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultEmployeeShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the employeeList where createdDate not equals to UPDATED_CREATED_DATE
        defaultEmployeeShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultEmployeeShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the employeeList where createdDate equals to UPDATED_CREATED_DATE
        defaultEmployeeShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdDate is not null
        defaultEmployeeShouldBeFound("createdDate.specified=true");

        // Get all the employeeList where createdDate is null
        defaultEmployeeShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdBy equals to DEFAULT_CREATED_BY
        defaultEmployeeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the employeeList where createdBy equals to UPDATED_CREATED_BY
        defaultEmployeeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdBy not equals to DEFAULT_CREATED_BY
        defaultEmployeeShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the employeeList where createdBy not equals to UPDATED_CREATED_BY
        defaultEmployeeShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultEmployeeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the employeeList where createdBy equals to UPDATED_CREATED_BY
        defaultEmployeeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdBy is not null
        defaultEmployeeShouldBeFound("createdBy.specified=true");

        // Get all the employeeList where createdBy is null
        defaultEmployeeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdBy contains DEFAULT_CREATED_BY
        defaultEmployeeShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the employeeList where createdBy contains UPDATED_CREATED_BY
        defaultEmployeeShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdBy does not contain DEFAULT_CREATED_BY
        defaultEmployeeShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the employeeList where createdBy does not contain UPDATED_CREATED_BY
        defaultEmployeeShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultEmployeeShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the employeeList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultEmployeeShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultEmployeeShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the employeeList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultEmployeeShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultEmployeeShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the employeeList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultEmployeeShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastModifiedDate is not null
        defaultEmployeeShouldBeFound("lastModifiedDate.specified=true");

        // Get all the employeeList where lastModifiedDate is null
        defaultEmployeeShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultEmployeeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employeeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEmployeeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultEmployeeShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employeeList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultEmployeeShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultEmployeeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the employeeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEmployeeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastModifiedBy is not null
        defaultEmployeeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the employeeList where lastModifiedBy is null
        defaultEmployeeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultEmployeeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employeeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultEmployeeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultEmployeeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employeeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultEmployeeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmpGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        EmpGroup empGroup;
        if (TestUtil.findAll(em, EmpGroup.class).isEmpty()) {
            empGroup = EmpGroupResourceIT.createEntity(em);
            em.persist(empGroup);
            em.flush();
        } else {
            empGroup = TestUtil.findAll(em, EmpGroup.class).get(0);
        }
        em.persist(empGroup);
        em.flush();
        employee.setEmpGroup(empGroup);
        employeeRepository.saveAndFlush(employee);
        Long empGroupId = empGroup.getId();

        // Get all the employeeList where empGroup equals to empGroupId
        defaultEmployeeShouldBeFound("empGroupId.equals=" + empGroupId);

        // Get all the employeeList where empGroup equals to (empGroupId + 1)
        defaultEmployeeShouldNotBeFound("empGroupId.equals=" + (empGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeShouldBeFound(String filter) throws Exception {
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeCode").value(hasItem(DEFAULT_EMPLOYEE_CODE)))
            .andExpect(jsonPath("$.[*].employeeName").value(hasItem(DEFAULT_EMPLOYEE_NAME)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].organizationId").value(hasItem(DEFAULT_ORGANIZATION_ID)))
            .andExpect(jsonPath("$.[*].employeeLastName").value(hasItem(DEFAULT_EMPLOYEE_LAST_NAME)))
            .andExpect(jsonPath("$.[*].employeeMiddleName").value(hasItem(DEFAULT_EMPLOYEE_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].employeeTitleId").value(hasItem(DEFAULT_EMPLOYEE_TITLE_ID)))
            .andExpect(jsonPath("$.[*].employeeTitleName").value(hasItem(DEFAULT_EMPLOYEE_TITLE_NAME)))
            .andExpect(jsonPath("$.[*].employeeFullName").value(hasItem(DEFAULT_EMPLOYEE_FULL_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeShouldNotBeFound(String filter) throws Exception {
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .employeeCode(UPDATED_EMPLOYEE_CODE)
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .active(UPDATED_ACTIVE)
            .companyCode(UPDATED_COMPANY_CODE)
            .companyName(UPDATED_COMPANY_NAME)
            .organizationId(UPDATED_ORGANIZATION_ID)
            .employeeLastName(UPDATED_EMPLOYEE_LAST_NAME)
            .employeeMiddleName(UPDATED_EMPLOYEE_MIDDLE_NAME)
            .employeeTitleId(UPDATED_EMPLOYEE_TITLE_ID)
            .employeeTitleName(UPDATED_EMPLOYEE_TITLE_NAME)
            .employeeFullName(UPDATED_EMPLOYEE_FULL_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getEmployeeCode()).isEqualTo(UPDATED_EMPLOYEE_CODE);
        assertThat(testEmployee.getEmployeeName()).isEqualTo(UPDATED_EMPLOYEE_NAME);
        assertThat(testEmployee.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testEmployee.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testEmployee.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testEmployee.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testEmployee.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testEmployee.getOrganizationId()).isEqualTo(UPDATED_ORGANIZATION_ID);
        assertThat(testEmployee.getEmployeeLastName()).isEqualTo(UPDATED_EMPLOYEE_LAST_NAME);
        assertThat(testEmployee.getEmployeeMiddleName()).isEqualTo(UPDATED_EMPLOYEE_MIDDLE_NAME);
        assertThat(testEmployee.getEmployeeTitleId()).isEqualTo(UPDATED_EMPLOYEE_TITLE_ID);
        assertThat(testEmployee.getEmployeeTitleName()).isEqualTo(UPDATED_EMPLOYEE_TITLE_NAME);
        assertThat(testEmployee.getEmployeeFullName()).isEqualTo(UPDATED_EMPLOYEE_FULL_NAME);
        assertThat(testEmployee.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmployee.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployee.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testEmployee.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .username(UPDATED_USERNAME)
            .companyName(UPDATED_COMPANY_NAME)
            .organizationId(UPDATED_ORGANIZATION_ID)
            .employeeLastName(UPDATED_EMPLOYEE_LAST_NAME)
            .employeeMiddleName(UPDATED_EMPLOYEE_MIDDLE_NAME)
            .createdDate(UPDATED_CREATED_DATE);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getEmployeeCode()).isEqualTo(DEFAULT_EMPLOYEE_CODE);
        assertThat(testEmployee.getEmployeeName()).isEqualTo(UPDATED_EMPLOYEE_NAME);
        assertThat(testEmployee.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testEmployee.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testEmployee.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testEmployee.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testEmployee.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testEmployee.getOrganizationId()).isEqualTo(UPDATED_ORGANIZATION_ID);
        assertThat(testEmployee.getEmployeeLastName()).isEqualTo(UPDATED_EMPLOYEE_LAST_NAME);
        assertThat(testEmployee.getEmployeeMiddleName()).isEqualTo(UPDATED_EMPLOYEE_MIDDLE_NAME);
        assertThat(testEmployee.getEmployeeTitleId()).isEqualTo(DEFAULT_EMPLOYEE_TITLE_ID);
        assertThat(testEmployee.getEmployeeTitleName()).isEqualTo(DEFAULT_EMPLOYEE_TITLE_NAME);
        assertThat(testEmployee.getEmployeeFullName()).isEqualTo(DEFAULT_EMPLOYEE_FULL_NAME);
        assertThat(testEmployee.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmployee.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEmployee.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testEmployee.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee
            .employeeCode(UPDATED_EMPLOYEE_CODE)
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .active(UPDATED_ACTIVE)
            .companyCode(UPDATED_COMPANY_CODE)
            .companyName(UPDATED_COMPANY_NAME)
            .organizationId(UPDATED_ORGANIZATION_ID)
            .employeeLastName(UPDATED_EMPLOYEE_LAST_NAME)
            .employeeMiddleName(UPDATED_EMPLOYEE_MIDDLE_NAME)
            .employeeTitleId(UPDATED_EMPLOYEE_TITLE_ID)
            .employeeTitleName(UPDATED_EMPLOYEE_TITLE_NAME)
            .employeeFullName(UPDATED_EMPLOYEE_FULL_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getEmployeeCode()).isEqualTo(UPDATED_EMPLOYEE_CODE);
        assertThat(testEmployee.getEmployeeName()).isEqualTo(UPDATED_EMPLOYEE_NAME);
        assertThat(testEmployee.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testEmployee.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testEmployee.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testEmployee.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testEmployee.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testEmployee.getOrganizationId()).isEqualTo(UPDATED_ORGANIZATION_ID);
        assertThat(testEmployee.getEmployeeLastName()).isEqualTo(UPDATED_EMPLOYEE_LAST_NAME);
        assertThat(testEmployee.getEmployeeMiddleName()).isEqualTo(UPDATED_EMPLOYEE_MIDDLE_NAME);
        assertThat(testEmployee.getEmployeeTitleId()).isEqualTo(UPDATED_EMPLOYEE_TITLE_ID);
        assertThat(testEmployee.getEmployeeTitleName()).isEqualTo(UPDATED_EMPLOYEE_TITLE_NAME);
        assertThat(testEmployee.getEmployeeFullName()).isEqualTo(UPDATED_EMPLOYEE_FULL_NAME);
        assertThat(testEmployee.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmployee.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployee.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testEmployee.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, employee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
