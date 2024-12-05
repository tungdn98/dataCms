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
import vn.com.datamanager.domain.EmployeeLead;
import vn.com.datamanager.repository.EmployeeLeadRepository;
import vn.com.datamanager.service.criteria.EmployeeLeadCriteria;

/**
 * Integration tests for the {@link EmployeeLeadResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeLeadResourceIT {

    private static final String DEFAULT_LEAD_ID = "AAAAAAAAAA";
    private static final String UPDATED_LEAD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LEAD_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LEAD_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LEAD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LEAD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LEAD_POTENTIAL_LEVEL_ID = "AAAAAAAAAA";
    private static final String UPDATED_LEAD_POTENTIAL_LEVEL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LEAD_SOURCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_LEAD_SOURCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LEAD_POTENTIAL_LEVEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LEAD_POTENTIAL_LEVEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LEAD_SOURCE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LEAD_SOURCE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employee-leads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeLeadRepository employeeLeadRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeLeadMockMvc;

    private EmployeeLead employeeLead;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeLead createEntity(EntityManager em) {
        EmployeeLead employeeLead = new EmployeeLead()
            .leadId(DEFAULT_LEAD_ID)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .leadCode(DEFAULT_LEAD_CODE)
            .leadName(DEFAULT_LEAD_NAME)
            .leadPotentialLevelId(DEFAULT_LEAD_POTENTIAL_LEVEL_ID)
            .leadSourceId(DEFAULT_LEAD_SOURCE_ID)
            .leadPotentialLevelName(DEFAULT_LEAD_POTENTIAL_LEVEL_NAME)
            .leadSourceName(DEFAULT_LEAD_SOURCE_NAME);
        return employeeLead;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeLead createUpdatedEntity(EntityManager em) {
        EmployeeLead employeeLead = new EmployeeLead()
            .leadId(UPDATED_LEAD_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .leadCode(UPDATED_LEAD_CODE)
            .leadName(UPDATED_LEAD_NAME)
            .leadPotentialLevelId(UPDATED_LEAD_POTENTIAL_LEVEL_ID)
            .leadSourceId(UPDATED_LEAD_SOURCE_ID)
            .leadPotentialLevelName(UPDATED_LEAD_POTENTIAL_LEVEL_NAME)
            .leadSourceName(UPDATED_LEAD_SOURCE_NAME);
        return employeeLead;
    }

    @BeforeEach
    public void initTest() {
        employeeLead = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeLead() throws Exception {
        int databaseSizeBeforeCreate = employeeLeadRepository.findAll().size();
        // Create the EmployeeLead
        restEmployeeLeadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeLead)))
            .andExpect(status().isCreated());

        // Validate the EmployeeLead in the database
        List<EmployeeLead> employeeLeadList = employeeLeadRepository.findAll();
        assertThat(employeeLeadList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeLead testEmployeeLead = employeeLeadList.get(employeeLeadList.size() - 1);
        assertThat(testEmployeeLead.getLeadId()).isEqualTo(DEFAULT_LEAD_ID);
        assertThat(testEmployeeLead.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testEmployeeLead.getLeadCode()).isEqualTo(DEFAULT_LEAD_CODE);
        assertThat(testEmployeeLead.getLeadName()).isEqualTo(DEFAULT_LEAD_NAME);
        assertThat(testEmployeeLead.getLeadPotentialLevelId()).isEqualTo(DEFAULT_LEAD_POTENTIAL_LEVEL_ID);
        assertThat(testEmployeeLead.getLeadSourceId()).isEqualTo(DEFAULT_LEAD_SOURCE_ID);
        assertThat(testEmployeeLead.getLeadPotentialLevelName()).isEqualTo(DEFAULT_LEAD_POTENTIAL_LEVEL_NAME);
        assertThat(testEmployeeLead.getLeadSourceName()).isEqualTo(DEFAULT_LEAD_SOURCE_NAME);
    }

    @Test
    @Transactional
    void createEmployeeLeadWithExistingId() throws Exception {
        // Create the EmployeeLead with an existing ID
        employeeLead.setId(1L);

        int databaseSizeBeforeCreate = employeeLeadRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeLeadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeLead)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeLead in the database
        List<EmployeeLead> employeeLeadList = employeeLeadRepository.findAll();
        assertThat(employeeLeadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLeadCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLeadRepository.findAll().size();
        // set the field null
        employeeLead.setLeadCode(null);

        // Create the EmployeeLead, which fails.

        restEmployeeLeadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeLead)))
            .andExpect(status().isBadRequest());

        List<EmployeeLead> employeeLeadList = employeeLeadRepository.findAll();
        assertThat(employeeLeadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeLeads() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList
        restEmployeeLeadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeLead.getId().intValue())))
            .andExpect(jsonPath("$.[*].leadId").value(hasItem(DEFAULT_LEAD_ID)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].leadCode").value(hasItem(DEFAULT_LEAD_CODE)))
            .andExpect(jsonPath("$.[*].leadName").value(hasItem(DEFAULT_LEAD_NAME)))
            .andExpect(jsonPath("$.[*].leadPotentialLevelId").value(hasItem(DEFAULT_LEAD_POTENTIAL_LEVEL_ID)))
            .andExpect(jsonPath("$.[*].leadSourceId").value(hasItem(DEFAULT_LEAD_SOURCE_ID)))
            .andExpect(jsonPath("$.[*].leadPotentialLevelName").value(hasItem(DEFAULT_LEAD_POTENTIAL_LEVEL_NAME)))
            .andExpect(jsonPath("$.[*].leadSourceName").value(hasItem(DEFAULT_LEAD_SOURCE_NAME)));
    }

    @Test
    @Transactional
    void getEmployeeLead() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get the employeeLead
        restEmployeeLeadMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeLead.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeLead.getId().intValue()))
            .andExpect(jsonPath("$.leadId").value(DEFAULT_LEAD_ID))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID))
            .andExpect(jsonPath("$.leadCode").value(DEFAULT_LEAD_CODE))
            .andExpect(jsonPath("$.leadName").value(DEFAULT_LEAD_NAME))
            .andExpect(jsonPath("$.leadPotentialLevelId").value(DEFAULT_LEAD_POTENTIAL_LEVEL_ID))
            .andExpect(jsonPath("$.leadSourceId").value(DEFAULT_LEAD_SOURCE_ID))
            .andExpect(jsonPath("$.leadPotentialLevelName").value(DEFAULT_LEAD_POTENTIAL_LEVEL_NAME))
            .andExpect(jsonPath("$.leadSourceName").value(DEFAULT_LEAD_SOURCE_NAME));
    }

    @Test
    @Transactional
    void getEmployeeLeadsByIdFiltering() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        Long id = employeeLead.getId();

        defaultEmployeeLeadShouldBeFound("id.equals=" + id);
        defaultEmployeeLeadShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeLeadShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeLeadShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeLeadShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeLeadShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadId equals to DEFAULT_LEAD_ID
        defaultEmployeeLeadShouldBeFound("leadId.equals=" + DEFAULT_LEAD_ID);

        // Get all the employeeLeadList where leadId equals to UPDATED_LEAD_ID
        defaultEmployeeLeadShouldNotBeFound("leadId.equals=" + UPDATED_LEAD_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadId not equals to DEFAULT_LEAD_ID
        defaultEmployeeLeadShouldNotBeFound("leadId.notEquals=" + DEFAULT_LEAD_ID);

        // Get all the employeeLeadList where leadId not equals to UPDATED_LEAD_ID
        defaultEmployeeLeadShouldBeFound("leadId.notEquals=" + UPDATED_LEAD_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadId in DEFAULT_LEAD_ID or UPDATED_LEAD_ID
        defaultEmployeeLeadShouldBeFound("leadId.in=" + DEFAULT_LEAD_ID + "," + UPDATED_LEAD_ID);

        // Get all the employeeLeadList where leadId equals to UPDATED_LEAD_ID
        defaultEmployeeLeadShouldNotBeFound("leadId.in=" + UPDATED_LEAD_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadId is not null
        defaultEmployeeLeadShouldBeFound("leadId.specified=true");

        // Get all the employeeLeadList where leadId is null
        defaultEmployeeLeadShouldNotBeFound("leadId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadIdContainsSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadId contains DEFAULT_LEAD_ID
        defaultEmployeeLeadShouldBeFound("leadId.contains=" + DEFAULT_LEAD_ID);

        // Get all the employeeLeadList where leadId contains UPDATED_LEAD_ID
        defaultEmployeeLeadShouldNotBeFound("leadId.contains=" + UPDATED_LEAD_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadIdNotContainsSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadId does not contain DEFAULT_LEAD_ID
        defaultEmployeeLeadShouldNotBeFound("leadId.doesNotContain=" + DEFAULT_LEAD_ID);

        // Get all the employeeLeadList where leadId does not contain UPDATED_LEAD_ID
        defaultEmployeeLeadShouldBeFound("leadId.doesNotContain=" + UPDATED_LEAD_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultEmployeeLeadShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeLeadList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultEmployeeLeadShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByEmployeeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where employeeId not equals to DEFAULT_EMPLOYEE_ID
        defaultEmployeeLeadShouldNotBeFound("employeeId.notEquals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeLeadList where employeeId not equals to UPDATED_EMPLOYEE_ID
        defaultEmployeeLeadShouldBeFound("employeeId.notEquals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultEmployeeLeadShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the employeeLeadList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultEmployeeLeadShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where employeeId is not null
        defaultEmployeeLeadShouldBeFound("employeeId.specified=true");

        // Get all the employeeLeadList where employeeId is null
        defaultEmployeeLeadShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByEmployeeIdContainsSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where employeeId contains DEFAULT_EMPLOYEE_ID
        defaultEmployeeLeadShouldBeFound("employeeId.contains=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeLeadList where employeeId contains UPDATED_EMPLOYEE_ID
        defaultEmployeeLeadShouldNotBeFound("employeeId.contains=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByEmployeeIdNotContainsSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where employeeId does not contain DEFAULT_EMPLOYEE_ID
        defaultEmployeeLeadShouldNotBeFound("employeeId.doesNotContain=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeLeadList where employeeId does not contain UPDATED_EMPLOYEE_ID
        defaultEmployeeLeadShouldBeFound("employeeId.doesNotContain=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadCode equals to DEFAULT_LEAD_CODE
        defaultEmployeeLeadShouldBeFound("leadCode.equals=" + DEFAULT_LEAD_CODE);

        // Get all the employeeLeadList where leadCode equals to UPDATED_LEAD_CODE
        defaultEmployeeLeadShouldNotBeFound("leadCode.equals=" + UPDATED_LEAD_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadCode not equals to DEFAULT_LEAD_CODE
        defaultEmployeeLeadShouldNotBeFound("leadCode.notEquals=" + DEFAULT_LEAD_CODE);

        // Get all the employeeLeadList where leadCode not equals to UPDATED_LEAD_CODE
        defaultEmployeeLeadShouldBeFound("leadCode.notEquals=" + UPDATED_LEAD_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadCodeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadCode in DEFAULT_LEAD_CODE or UPDATED_LEAD_CODE
        defaultEmployeeLeadShouldBeFound("leadCode.in=" + DEFAULT_LEAD_CODE + "," + UPDATED_LEAD_CODE);

        // Get all the employeeLeadList where leadCode equals to UPDATED_LEAD_CODE
        defaultEmployeeLeadShouldNotBeFound("leadCode.in=" + UPDATED_LEAD_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadCode is not null
        defaultEmployeeLeadShouldBeFound("leadCode.specified=true");

        // Get all the employeeLeadList where leadCode is null
        defaultEmployeeLeadShouldNotBeFound("leadCode.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadCodeContainsSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadCode contains DEFAULT_LEAD_CODE
        defaultEmployeeLeadShouldBeFound("leadCode.contains=" + DEFAULT_LEAD_CODE);

        // Get all the employeeLeadList where leadCode contains UPDATED_LEAD_CODE
        defaultEmployeeLeadShouldNotBeFound("leadCode.contains=" + UPDATED_LEAD_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadCodeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadCode does not contain DEFAULT_LEAD_CODE
        defaultEmployeeLeadShouldNotBeFound("leadCode.doesNotContain=" + DEFAULT_LEAD_CODE);

        // Get all the employeeLeadList where leadCode does not contain UPDATED_LEAD_CODE
        defaultEmployeeLeadShouldBeFound("leadCode.doesNotContain=" + UPDATED_LEAD_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadName equals to DEFAULT_LEAD_NAME
        defaultEmployeeLeadShouldBeFound("leadName.equals=" + DEFAULT_LEAD_NAME);

        // Get all the employeeLeadList where leadName equals to UPDATED_LEAD_NAME
        defaultEmployeeLeadShouldNotBeFound("leadName.equals=" + UPDATED_LEAD_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadName not equals to DEFAULT_LEAD_NAME
        defaultEmployeeLeadShouldNotBeFound("leadName.notEquals=" + DEFAULT_LEAD_NAME);

        // Get all the employeeLeadList where leadName not equals to UPDATED_LEAD_NAME
        defaultEmployeeLeadShouldBeFound("leadName.notEquals=" + UPDATED_LEAD_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadName in DEFAULT_LEAD_NAME or UPDATED_LEAD_NAME
        defaultEmployeeLeadShouldBeFound("leadName.in=" + DEFAULT_LEAD_NAME + "," + UPDATED_LEAD_NAME);

        // Get all the employeeLeadList where leadName equals to UPDATED_LEAD_NAME
        defaultEmployeeLeadShouldNotBeFound("leadName.in=" + UPDATED_LEAD_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadName is not null
        defaultEmployeeLeadShouldBeFound("leadName.specified=true");

        // Get all the employeeLeadList where leadName is null
        defaultEmployeeLeadShouldNotBeFound("leadName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadNameContainsSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadName contains DEFAULT_LEAD_NAME
        defaultEmployeeLeadShouldBeFound("leadName.contains=" + DEFAULT_LEAD_NAME);

        // Get all the employeeLeadList where leadName contains UPDATED_LEAD_NAME
        defaultEmployeeLeadShouldNotBeFound("leadName.contains=" + UPDATED_LEAD_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadName does not contain DEFAULT_LEAD_NAME
        defaultEmployeeLeadShouldNotBeFound("leadName.doesNotContain=" + DEFAULT_LEAD_NAME);

        // Get all the employeeLeadList where leadName does not contain UPDATED_LEAD_NAME
        defaultEmployeeLeadShouldBeFound("leadName.doesNotContain=" + UPDATED_LEAD_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadPotentialLevelIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadPotentialLevelId equals to DEFAULT_LEAD_POTENTIAL_LEVEL_ID
        defaultEmployeeLeadShouldBeFound("leadPotentialLevelId.equals=" + DEFAULT_LEAD_POTENTIAL_LEVEL_ID);

        // Get all the employeeLeadList where leadPotentialLevelId equals to UPDATED_LEAD_POTENTIAL_LEVEL_ID
        defaultEmployeeLeadShouldNotBeFound("leadPotentialLevelId.equals=" + UPDATED_LEAD_POTENTIAL_LEVEL_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadPotentialLevelIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadPotentialLevelId not equals to DEFAULT_LEAD_POTENTIAL_LEVEL_ID
        defaultEmployeeLeadShouldNotBeFound("leadPotentialLevelId.notEquals=" + DEFAULT_LEAD_POTENTIAL_LEVEL_ID);

        // Get all the employeeLeadList where leadPotentialLevelId not equals to UPDATED_LEAD_POTENTIAL_LEVEL_ID
        defaultEmployeeLeadShouldBeFound("leadPotentialLevelId.notEquals=" + UPDATED_LEAD_POTENTIAL_LEVEL_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadPotentialLevelIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadPotentialLevelId in DEFAULT_LEAD_POTENTIAL_LEVEL_ID or UPDATED_LEAD_POTENTIAL_LEVEL_ID
        defaultEmployeeLeadShouldBeFound(
            "leadPotentialLevelId.in=" + DEFAULT_LEAD_POTENTIAL_LEVEL_ID + "," + UPDATED_LEAD_POTENTIAL_LEVEL_ID
        );

        // Get all the employeeLeadList where leadPotentialLevelId equals to UPDATED_LEAD_POTENTIAL_LEVEL_ID
        defaultEmployeeLeadShouldNotBeFound("leadPotentialLevelId.in=" + UPDATED_LEAD_POTENTIAL_LEVEL_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadPotentialLevelIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadPotentialLevelId is not null
        defaultEmployeeLeadShouldBeFound("leadPotentialLevelId.specified=true");

        // Get all the employeeLeadList where leadPotentialLevelId is null
        defaultEmployeeLeadShouldNotBeFound("leadPotentialLevelId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadPotentialLevelIdContainsSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadPotentialLevelId contains DEFAULT_LEAD_POTENTIAL_LEVEL_ID
        defaultEmployeeLeadShouldBeFound("leadPotentialLevelId.contains=" + DEFAULT_LEAD_POTENTIAL_LEVEL_ID);

        // Get all the employeeLeadList where leadPotentialLevelId contains UPDATED_LEAD_POTENTIAL_LEVEL_ID
        defaultEmployeeLeadShouldNotBeFound("leadPotentialLevelId.contains=" + UPDATED_LEAD_POTENTIAL_LEVEL_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadPotentialLevelIdNotContainsSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadPotentialLevelId does not contain DEFAULT_LEAD_POTENTIAL_LEVEL_ID
        defaultEmployeeLeadShouldNotBeFound("leadPotentialLevelId.doesNotContain=" + DEFAULT_LEAD_POTENTIAL_LEVEL_ID);

        // Get all the employeeLeadList where leadPotentialLevelId does not contain UPDATED_LEAD_POTENTIAL_LEVEL_ID
        defaultEmployeeLeadShouldBeFound("leadPotentialLevelId.doesNotContain=" + UPDATED_LEAD_POTENTIAL_LEVEL_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadSourceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadSourceId equals to DEFAULT_LEAD_SOURCE_ID
        defaultEmployeeLeadShouldBeFound("leadSourceId.equals=" + DEFAULT_LEAD_SOURCE_ID);

        // Get all the employeeLeadList where leadSourceId equals to UPDATED_LEAD_SOURCE_ID
        defaultEmployeeLeadShouldNotBeFound("leadSourceId.equals=" + UPDATED_LEAD_SOURCE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadSourceIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadSourceId not equals to DEFAULT_LEAD_SOURCE_ID
        defaultEmployeeLeadShouldNotBeFound("leadSourceId.notEquals=" + DEFAULT_LEAD_SOURCE_ID);

        // Get all the employeeLeadList where leadSourceId not equals to UPDATED_LEAD_SOURCE_ID
        defaultEmployeeLeadShouldBeFound("leadSourceId.notEquals=" + UPDATED_LEAD_SOURCE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadSourceIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadSourceId in DEFAULT_LEAD_SOURCE_ID or UPDATED_LEAD_SOURCE_ID
        defaultEmployeeLeadShouldBeFound("leadSourceId.in=" + DEFAULT_LEAD_SOURCE_ID + "," + UPDATED_LEAD_SOURCE_ID);

        // Get all the employeeLeadList where leadSourceId equals to UPDATED_LEAD_SOURCE_ID
        defaultEmployeeLeadShouldNotBeFound("leadSourceId.in=" + UPDATED_LEAD_SOURCE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadSourceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadSourceId is not null
        defaultEmployeeLeadShouldBeFound("leadSourceId.specified=true");

        // Get all the employeeLeadList where leadSourceId is null
        defaultEmployeeLeadShouldNotBeFound("leadSourceId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadSourceIdContainsSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadSourceId contains DEFAULT_LEAD_SOURCE_ID
        defaultEmployeeLeadShouldBeFound("leadSourceId.contains=" + DEFAULT_LEAD_SOURCE_ID);

        // Get all the employeeLeadList where leadSourceId contains UPDATED_LEAD_SOURCE_ID
        defaultEmployeeLeadShouldNotBeFound("leadSourceId.contains=" + UPDATED_LEAD_SOURCE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadSourceIdNotContainsSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadSourceId does not contain DEFAULT_LEAD_SOURCE_ID
        defaultEmployeeLeadShouldNotBeFound("leadSourceId.doesNotContain=" + DEFAULT_LEAD_SOURCE_ID);

        // Get all the employeeLeadList where leadSourceId does not contain UPDATED_LEAD_SOURCE_ID
        defaultEmployeeLeadShouldBeFound("leadSourceId.doesNotContain=" + UPDATED_LEAD_SOURCE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadPotentialLevelNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadPotentialLevelName equals to DEFAULT_LEAD_POTENTIAL_LEVEL_NAME
        defaultEmployeeLeadShouldBeFound("leadPotentialLevelName.equals=" + DEFAULT_LEAD_POTENTIAL_LEVEL_NAME);

        // Get all the employeeLeadList where leadPotentialLevelName equals to UPDATED_LEAD_POTENTIAL_LEVEL_NAME
        defaultEmployeeLeadShouldNotBeFound("leadPotentialLevelName.equals=" + UPDATED_LEAD_POTENTIAL_LEVEL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadPotentialLevelNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadPotentialLevelName not equals to DEFAULT_LEAD_POTENTIAL_LEVEL_NAME
        defaultEmployeeLeadShouldNotBeFound("leadPotentialLevelName.notEquals=" + DEFAULT_LEAD_POTENTIAL_LEVEL_NAME);

        // Get all the employeeLeadList where leadPotentialLevelName not equals to UPDATED_LEAD_POTENTIAL_LEVEL_NAME
        defaultEmployeeLeadShouldBeFound("leadPotentialLevelName.notEquals=" + UPDATED_LEAD_POTENTIAL_LEVEL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadPotentialLevelNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadPotentialLevelName in DEFAULT_LEAD_POTENTIAL_LEVEL_NAME or UPDATED_LEAD_POTENTIAL_LEVEL_NAME
        defaultEmployeeLeadShouldBeFound(
            "leadPotentialLevelName.in=" + DEFAULT_LEAD_POTENTIAL_LEVEL_NAME + "," + UPDATED_LEAD_POTENTIAL_LEVEL_NAME
        );

        // Get all the employeeLeadList where leadPotentialLevelName equals to UPDATED_LEAD_POTENTIAL_LEVEL_NAME
        defaultEmployeeLeadShouldNotBeFound("leadPotentialLevelName.in=" + UPDATED_LEAD_POTENTIAL_LEVEL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadPotentialLevelNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadPotentialLevelName is not null
        defaultEmployeeLeadShouldBeFound("leadPotentialLevelName.specified=true");

        // Get all the employeeLeadList where leadPotentialLevelName is null
        defaultEmployeeLeadShouldNotBeFound("leadPotentialLevelName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadPotentialLevelNameContainsSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadPotentialLevelName contains DEFAULT_LEAD_POTENTIAL_LEVEL_NAME
        defaultEmployeeLeadShouldBeFound("leadPotentialLevelName.contains=" + DEFAULT_LEAD_POTENTIAL_LEVEL_NAME);

        // Get all the employeeLeadList where leadPotentialLevelName contains UPDATED_LEAD_POTENTIAL_LEVEL_NAME
        defaultEmployeeLeadShouldNotBeFound("leadPotentialLevelName.contains=" + UPDATED_LEAD_POTENTIAL_LEVEL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadPotentialLevelNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadPotentialLevelName does not contain DEFAULT_LEAD_POTENTIAL_LEVEL_NAME
        defaultEmployeeLeadShouldNotBeFound("leadPotentialLevelName.doesNotContain=" + DEFAULT_LEAD_POTENTIAL_LEVEL_NAME);

        // Get all the employeeLeadList where leadPotentialLevelName does not contain UPDATED_LEAD_POTENTIAL_LEVEL_NAME
        defaultEmployeeLeadShouldBeFound("leadPotentialLevelName.doesNotContain=" + UPDATED_LEAD_POTENTIAL_LEVEL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadSourceNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadSourceName equals to DEFAULT_LEAD_SOURCE_NAME
        defaultEmployeeLeadShouldBeFound("leadSourceName.equals=" + DEFAULT_LEAD_SOURCE_NAME);

        // Get all the employeeLeadList where leadSourceName equals to UPDATED_LEAD_SOURCE_NAME
        defaultEmployeeLeadShouldNotBeFound("leadSourceName.equals=" + UPDATED_LEAD_SOURCE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadSourceNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadSourceName not equals to DEFAULT_LEAD_SOURCE_NAME
        defaultEmployeeLeadShouldNotBeFound("leadSourceName.notEquals=" + DEFAULT_LEAD_SOURCE_NAME);

        // Get all the employeeLeadList where leadSourceName not equals to UPDATED_LEAD_SOURCE_NAME
        defaultEmployeeLeadShouldBeFound("leadSourceName.notEquals=" + UPDATED_LEAD_SOURCE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadSourceNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadSourceName in DEFAULT_LEAD_SOURCE_NAME or UPDATED_LEAD_SOURCE_NAME
        defaultEmployeeLeadShouldBeFound("leadSourceName.in=" + DEFAULT_LEAD_SOURCE_NAME + "," + UPDATED_LEAD_SOURCE_NAME);

        // Get all the employeeLeadList where leadSourceName equals to UPDATED_LEAD_SOURCE_NAME
        defaultEmployeeLeadShouldNotBeFound("leadSourceName.in=" + UPDATED_LEAD_SOURCE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadSourceNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadSourceName is not null
        defaultEmployeeLeadShouldBeFound("leadSourceName.specified=true");

        // Get all the employeeLeadList where leadSourceName is null
        defaultEmployeeLeadShouldNotBeFound("leadSourceName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadSourceNameContainsSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadSourceName contains DEFAULT_LEAD_SOURCE_NAME
        defaultEmployeeLeadShouldBeFound("leadSourceName.contains=" + DEFAULT_LEAD_SOURCE_NAME);

        // Get all the employeeLeadList where leadSourceName contains UPDATED_LEAD_SOURCE_NAME
        defaultEmployeeLeadShouldNotBeFound("leadSourceName.contains=" + UPDATED_LEAD_SOURCE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeLeadsByLeadSourceNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        // Get all the employeeLeadList where leadSourceName does not contain DEFAULT_LEAD_SOURCE_NAME
        defaultEmployeeLeadShouldNotBeFound("leadSourceName.doesNotContain=" + DEFAULT_LEAD_SOURCE_NAME);

        // Get all the employeeLeadList where leadSourceName does not contain UPDATED_LEAD_SOURCE_NAME
        defaultEmployeeLeadShouldBeFound("leadSourceName.doesNotContain=" + UPDATED_LEAD_SOURCE_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeLeadShouldBeFound(String filter) throws Exception {
        restEmployeeLeadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeLead.getId().intValue())))
            .andExpect(jsonPath("$.[*].leadId").value(hasItem(DEFAULT_LEAD_ID)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].leadCode").value(hasItem(DEFAULT_LEAD_CODE)))
            .andExpect(jsonPath("$.[*].leadName").value(hasItem(DEFAULT_LEAD_NAME)))
            .andExpect(jsonPath("$.[*].leadPotentialLevelId").value(hasItem(DEFAULT_LEAD_POTENTIAL_LEVEL_ID)))
            .andExpect(jsonPath("$.[*].leadSourceId").value(hasItem(DEFAULT_LEAD_SOURCE_ID)))
            .andExpect(jsonPath("$.[*].leadPotentialLevelName").value(hasItem(DEFAULT_LEAD_POTENTIAL_LEVEL_NAME)))
            .andExpect(jsonPath("$.[*].leadSourceName").value(hasItem(DEFAULT_LEAD_SOURCE_NAME)));

        // Check, that the count call also returns 1
        restEmployeeLeadMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeLeadShouldNotBeFound(String filter) throws Exception {
        restEmployeeLeadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeLeadMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeLead() throws Exception {
        // Get the employeeLead
        restEmployeeLeadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmployeeLead() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        int databaseSizeBeforeUpdate = employeeLeadRepository.findAll().size();

        // Update the employeeLead
        EmployeeLead updatedEmployeeLead = employeeLeadRepository.findById(employeeLead.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeLead are not directly saved in db
        em.detach(updatedEmployeeLead);
        updatedEmployeeLead
            .leadId(UPDATED_LEAD_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .leadCode(UPDATED_LEAD_CODE)
            .leadName(UPDATED_LEAD_NAME)
            .leadPotentialLevelId(UPDATED_LEAD_POTENTIAL_LEVEL_ID)
            .leadSourceId(UPDATED_LEAD_SOURCE_ID)
            .leadPotentialLevelName(UPDATED_LEAD_POTENTIAL_LEVEL_NAME)
            .leadSourceName(UPDATED_LEAD_SOURCE_NAME);

        restEmployeeLeadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeLead.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeLead))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeLead in the database
        List<EmployeeLead> employeeLeadList = employeeLeadRepository.findAll();
        assertThat(employeeLeadList).hasSize(databaseSizeBeforeUpdate);
        EmployeeLead testEmployeeLead = employeeLeadList.get(employeeLeadList.size() - 1);
        assertThat(testEmployeeLead.getLeadId()).isEqualTo(UPDATED_LEAD_ID);
        assertThat(testEmployeeLead.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEmployeeLead.getLeadCode()).isEqualTo(UPDATED_LEAD_CODE);
        assertThat(testEmployeeLead.getLeadName()).isEqualTo(UPDATED_LEAD_NAME);
        assertThat(testEmployeeLead.getLeadPotentialLevelId()).isEqualTo(UPDATED_LEAD_POTENTIAL_LEVEL_ID);
        assertThat(testEmployeeLead.getLeadSourceId()).isEqualTo(UPDATED_LEAD_SOURCE_ID);
        assertThat(testEmployeeLead.getLeadPotentialLevelName()).isEqualTo(UPDATED_LEAD_POTENTIAL_LEVEL_NAME);
        assertThat(testEmployeeLead.getLeadSourceName()).isEqualTo(UPDATED_LEAD_SOURCE_NAME);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeLead() throws Exception {
        int databaseSizeBeforeUpdate = employeeLeadRepository.findAll().size();
        employeeLead.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeLeadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeLead.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeLead))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeLead in the database
        List<EmployeeLead> employeeLeadList = employeeLeadRepository.findAll();
        assertThat(employeeLeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeLead() throws Exception {
        int databaseSizeBeforeUpdate = employeeLeadRepository.findAll().size();
        employeeLead.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeLeadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeLead))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeLead in the database
        List<EmployeeLead> employeeLeadList = employeeLeadRepository.findAll();
        assertThat(employeeLeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeLead() throws Exception {
        int databaseSizeBeforeUpdate = employeeLeadRepository.findAll().size();
        employeeLead.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeLeadMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeLead)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeLead in the database
        List<EmployeeLead> employeeLeadList = employeeLeadRepository.findAll();
        assertThat(employeeLeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeLeadWithPatch() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        int databaseSizeBeforeUpdate = employeeLeadRepository.findAll().size();

        // Update the employeeLead using partial update
        EmployeeLead partialUpdatedEmployeeLead = new EmployeeLead();
        partialUpdatedEmployeeLead.setId(employeeLead.getId());

        partialUpdatedEmployeeLead
            .leadId(UPDATED_LEAD_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .leadCode(UPDATED_LEAD_CODE)
            .leadPotentialLevelId(UPDATED_LEAD_POTENTIAL_LEVEL_ID)
            .leadPotentialLevelName(UPDATED_LEAD_POTENTIAL_LEVEL_NAME);

        restEmployeeLeadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeLead.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeLead))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeLead in the database
        List<EmployeeLead> employeeLeadList = employeeLeadRepository.findAll();
        assertThat(employeeLeadList).hasSize(databaseSizeBeforeUpdate);
        EmployeeLead testEmployeeLead = employeeLeadList.get(employeeLeadList.size() - 1);
        assertThat(testEmployeeLead.getLeadId()).isEqualTo(UPDATED_LEAD_ID);
        assertThat(testEmployeeLead.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEmployeeLead.getLeadCode()).isEqualTo(UPDATED_LEAD_CODE);
        assertThat(testEmployeeLead.getLeadName()).isEqualTo(DEFAULT_LEAD_NAME);
        assertThat(testEmployeeLead.getLeadPotentialLevelId()).isEqualTo(UPDATED_LEAD_POTENTIAL_LEVEL_ID);
        assertThat(testEmployeeLead.getLeadSourceId()).isEqualTo(DEFAULT_LEAD_SOURCE_ID);
        assertThat(testEmployeeLead.getLeadPotentialLevelName()).isEqualTo(UPDATED_LEAD_POTENTIAL_LEVEL_NAME);
        assertThat(testEmployeeLead.getLeadSourceName()).isEqualTo(DEFAULT_LEAD_SOURCE_NAME);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeLeadWithPatch() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        int databaseSizeBeforeUpdate = employeeLeadRepository.findAll().size();

        // Update the employeeLead using partial update
        EmployeeLead partialUpdatedEmployeeLead = new EmployeeLead();
        partialUpdatedEmployeeLead.setId(employeeLead.getId());

        partialUpdatedEmployeeLead
            .leadId(UPDATED_LEAD_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .leadCode(UPDATED_LEAD_CODE)
            .leadName(UPDATED_LEAD_NAME)
            .leadPotentialLevelId(UPDATED_LEAD_POTENTIAL_LEVEL_ID)
            .leadSourceId(UPDATED_LEAD_SOURCE_ID)
            .leadPotentialLevelName(UPDATED_LEAD_POTENTIAL_LEVEL_NAME)
            .leadSourceName(UPDATED_LEAD_SOURCE_NAME);

        restEmployeeLeadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeLead.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeLead))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeLead in the database
        List<EmployeeLead> employeeLeadList = employeeLeadRepository.findAll();
        assertThat(employeeLeadList).hasSize(databaseSizeBeforeUpdate);
        EmployeeLead testEmployeeLead = employeeLeadList.get(employeeLeadList.size() - 1);
        assertThat(testEmployeeLead.getLeadId()).isEqualTo(UPDATED_LEAD_ID);
        assertThat(testEmployeeLead.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEmployeeLead.getLeadCode()).isEqualTo(UPDATED_LEAD_CODE);
        assertThat(testEmployeeLead.getLeadName()).isEqualTo(UPDATED_LEAD_NAME);
        assertThat(testEmployeeLead.getLeadPotentialLevelId()).isEqualTo(UPDATED_LEAD_POTENTIAL_LEVEL_ID);
        assertThat(testEmployeeLead.getLeadSourceId()).isEqualTo(UPDATED_LEAD_SOURCE_ID);
        assertThat(testEmployeeLead.getLeadPotentialLevelName()).isEqualTo(UPDATED_LEAD_POTENTIAL_LEVEL_NAME);
        assertThat(testEmployeeLead.getLeadSourceName()).isEqualTo(UPDATED_LEAD_SOURCE_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeLead() throws Exception {
        int databaseSizeBeforeUpdate = employeeLeadRepository.findAll().size();
        employeeLead.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeLeadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeLead.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeLead))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeLead in the database
        List<EmployeeLead> employeeLeadList = employeeLeadRepository.findAll();
        assertThat(employeeLeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeLead() throws Exception {
        int databaseSizeBeforeUpdate = employeeLeadRepository.findAll().size();
        employeeLead.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeLeadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeLead))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeLead in the database
        List<EmployeeLead> employeeLeadList = employeeLeadRepository.findAll();
        assertThat(employeeLeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeLead() throws Exception {
        int databaseSizeBeforeUpdate = employeeLeadRepository.findAll().size();
        employeeLead.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeLeadMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(employeeLead))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeLead in the database
        List<EmployeeLead> employeeLeadList = employeeLeadRepository.findAll();
        assertThat(employeeLeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeLead() throws Exception {
        // Initialize the database
        employeeLeadRepository.saveAndFlush(employeeLead);

        int databaseSizeBeforeDelete = employeeLeadRepository.findAll().size();

        // Delete the employeeLead
        restEmployeeLeadMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeLead.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeLead> employeeLeadList = employeeLeadRepository.findAll();
        assertThat(employeeLeadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
