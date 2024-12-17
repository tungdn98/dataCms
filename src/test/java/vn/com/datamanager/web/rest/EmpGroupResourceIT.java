package vn.com.datamanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.IntegrationTest;
import vn.com.datamanager.domain.EmpGroup;
import vn.com.datamanager.domain.Employee;
import vn.com.datamanager.domain.Roles;
import vn.com.datamanager.repository.EmpGroupRepository;
import vn.com.datamanager.service.EmpGroupService;
import vn.com.datamanager.service.criteria.EmpGroupCriteria;

/**
 * Integration tests for the {@link EmpGroupResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EmpGroupResourceIT {

    private static final String DEFAULT_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/emp-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmpGroupRepository empGroupRepository;

    @Mock
    private EmpGroupRepository empGroupRepositoryMock;

    @Mock
    private EmpGroupService empGroupServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpGroupMockMvc;

    private EmpGroup empGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpGroup createEntity(EntityManager em) {
        EmpGroup empGroup = new EmpGroup()
            .groupName(DEFAULT_GROUP_NAME)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return empGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpGroup createUpdatedEntity(EntityManager em) {
        EmpGroup empGroup = new EmpGroup()
            .groupName(UPDATED_GROUP_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return empGroup;
    }

    @BeforeEach
    public void initTest() {
        empGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createEmpGroup() throws Exception {
        int databaseSizeBeforeCreate = empGroupRepository.findAll().size();
        // Create the EmpGroup
        restEmpGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empGroup)))
            .andExpect(status().isCreated());

        // Validate the EmpGroup in the database
        List<EmpGroup> empGroupList = empGroupRepository.findAll();
        assertThat(empGroupList).hasSize(databaseSizeBeforeCreate + 1);
        EmpGroup testEmpGroup = empGroupList.get(empGroupList.size() - 1);
        assertThat(testEmpGroup.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
        assertThat(testEmpGroup.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEmpGroup.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEmpGroup.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testEmpGroup.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createEmpGroupWithExistingId() throws Exception {
        // Create the EmpGroup with an existing ID
        empGroup.setId(1L);

        int databaseSizeBeforeCreate = empGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empGroup)))
            .andExpect(status().isBadRequest());

        // Validate the EmpGroup in the database
        List<EmpGroup> empGroupList = empGroupRepository.findAll();
        assertThat(empGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGroupNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = empGroupRepository.findAll().size();
        // set the field null
        empGroup.setGroupName(null);

        // Create the EmpGroup, which fails.

        restEmpGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empGroup)))
            .andExpect(status().isBadRequest());

        List<EmpGroup> empGroupList = empGroupRepository.findAll();
        assertThat(empGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmpGroups() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList
        restEmpGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmpGroupsWithEagerRelationshipsIsEnabled() throws Exception {
        when(empGroupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmpGroupMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(empGroupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmpGroupsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(empGroupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmpGroupMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(empGroupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getEmpGroup() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get the empGroup
        restEmpGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, empGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(empGroup.getId().intValue()))
            .andExpect(jsonPath("$.groupName").value(DEFAULT_GROUP_NAME))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getEmpGroupsByIdFiltering() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        Long id = empGroup.getId();

        defaultEmpGroupShouldBeFound("id.equals=" + id);
        defaultEmpGroupShouldNotBeFound("id.notEquals=" + id);

        defaultEmpGroupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmpGroupShouldNotBeFound("id.greaterThan=" + id);

        defaultEmpGroupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmpGroupShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByGroupNameIsEqualToSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where groupName equals to DEFAULT_GROUP_NAME
        defaultEmpGroupShouldBeFound("groupName.equals=" + DEFAULT_GROUP_NAME);

        // Get all the empGroupList where groupName equals to UPDATED_GROUP_NAME
        defaultEmpGroupShouldNotBeFound("groupName.equals=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByGroupNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where groupName not equals to DEFAULT_GROUP_NAME
        defaultEmpGroupShouldNotBeFound("groupName.notEquals=" + DEFAULT_GROUP_NAME);

        // Get all the empGroupList where groupName not equals to UPDATED_GROUP_NAME
        defaultEmpGroupShouldBeFound("groupName.notEquals=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByGroupNameIsInShouldWork() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where groupName in DEFAULT_GROUP_NAME or UPDATED_GROUP_NAME
        defaultEmpGroupShouldBeFound("groupName.in=" + DEFAULT_GROUP_NAME + "," + UPDATED_GROUP_NAME);

        // Get all the empGroupList where groupName equals to UPDATED_GROUP_NAME
        defaultEmpGroupShouldNotBeFound("groupName.in=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByGroupNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where groupName is not null
        defaultEmpGroupShouldBeFound("groupName.specified=true");

        // Get all the empGroupList where groupName is null
        defaultEmpGroupShouldNotBeFound("groupName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpGroupsByGroupNameContainsSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where groupName contains DEFAULT_GROUP_NAME
        defaultEmpGroupShouldBeFound("groupName.contains=" + DEFAULT_GROUP_NAME);

        // Get all the empGroupList where groupName contains UPDATED_GROUP_NAME
        defaultEmpGroupShouldNotBeFound("groupName.contains=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByGroupNameNotContainsSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where groupName does not contain DEFAULT_GROUP_NAME
        defaultEmpGroupShouldNotBeFound("groupName.doesNotContain=" + DEFAULT_GROUP_NAME);

        // Get all the empGroupList where groupName does not contain UPDATED_GROUP_NAME
        defaultEmpGroupShouldBeFound("groupName.doesNotContain=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where createdDate equals to DEFAULT_CREATED_DATE
        defaultEmpGroupShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the empGroupList where createdDate equals to UPDATED_CREATED_DATE
        defaultEmpGroupShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultEmpGroupShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the empGroupList where createdDate not equals to UPDATED_CREATED_DATE
        defaultEmpGroupShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultEmpGroupShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the empGroupList where createdDate equals to UPDATED_CREATED_DATE
        defaultEmpGroupShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where createdDate is not null
        defaultEmpGroupShouldBeFound("createdDate.specified=true");

        // Get all the empGroupList where createdDate is null
        defaultEmpGroupShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpGroupsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where createdBy equals to DEFAULT_CREATED_BY
        defaultEmpGroupShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the empGroupList where createdBy equals to UPDATED_CREATED_BY
        defaultEmpGroupShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where createdBy not equals to DEFAULT_CREATED_BY
        defaultEmpGroupShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the empGroupList where createdBy not equals to UPDATED_CREATED_BY
        defaultEmpGroupShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultEmpGroupShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the empGroupList where createdBy equals to UPDATED_CREATED_BY
        defaultEmpGroupShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where createdBy is not null
        defaultEmpGroupShouldBeFound("createdBy.specified=true");

        // Get all the empGroupList where createdBy is null
        defaultEmpGroupShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpGroupsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where createdBy contains DEFAULT_CREATED_BY
        defaultEmpGroupShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the empGroupList where createdBy contains UPDATED_CREATED_BY
        defaultEmpGroupShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where createdBy does not contain DEFAULT_CREATED_BY
        defaultEmpGroupShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the empGroupList where createdBy does not contain UPDATED_CREATED_BY
        defaultEmpGroupShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultEmpGroupShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the empGroupList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultEmpGroupShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultEmpGroupShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the empGroupList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultEmpGroupShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultEmpGroupShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the empGroupList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultEmpGroupShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where lastModifiedDate is not null
        defaultEmpGroupShouldBeFound("lastModifiedDate.specified=true");

        // Get all the empGroupList where lastModifiedDate is null
        defaultEmpGroupShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpGroupsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultEmpGroupShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the empGroupList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEmpGroupShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultEmpGroupShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the empGroupList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultEmpGroupShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultEmpGroupShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the empGroupList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEmpGroupShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where lastModifiedBy is not null
        defaultEmpGroupShouldBeFound("lastModifiedBy.specified=true");

        // Get all the empGroupList where lastModifiedBy is null
        defaultEmpGroupShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpGroupsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultEmpGroupShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the empGroupList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultEmpGroupShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        // Get all the empGroupList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultEmpGroupShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the empGroupList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultEmpGroupShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmpGroupsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        empGroup.addEmployee(employee);
        empGroupRepository.saveAndFlush(empGroup);
        Long employeeId = employee.getId();

        // Get all the empGroupList where employee equals to employeeId
        defaultEmpGroupShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the empGroupList where employee equals to (employeeId + 1)
        defaultEmpGroupShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmpGroupsByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);
        Roles role;
        if (TestUtil.findAll(em, Roles.class).isEmpty()) {
            role = RolesResourceIT.createEntity(em);
            em.persist(role);
            em.flush();
        } else {
            role = TestUtil.findAll(em, Roles.class).get(0);
        }
        em.persist(role);
        em.flush();
        empGroup.addRole(role);
        empGroupRepository.saveAndFlush(empGroup);
        Long roleId = role.getId();

        // Get all the empGroupList where role equals to roleId
        defaultEmpGroupShouldBeFound("roleId.equals=" + roleId);

        // Get all the empGroupList where role equals to (roleId + 1)
        defaultEmpGroupShouldNotBeFound("roleId.equals=" + (roleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmpGroupShouldBeFound(String filter) throws Exception {
        restEmpGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restEmpGroupMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmpGroupShouldNotBeFound(String filter) throws Exception {
        restEmpGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmpGroupMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmpGroup() throws Exception {
        // Get the empGroup
        restEmpGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmpGroup() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        int databaseSizeBeforeUpdate = empGroupRepository.findAll().size();

        // Update the empGroup
        EmpGroup updatedEmpGroup = empGroupRepository.findById(empGroup.getId()).get();
        // Disconnect from session so that the updates on updatedEmpGroup are not directly saved in db
        em.detach(updatedEmpGroup);
        updatedEmpGroup
            .groupName(UPDATED_GROUP_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restEmpGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmpGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmpGroup))
            )
            .andExpect(status().isOk());

        // Validate the EmpGroup in the database
        List<EmpGroup> empGroupList = empGroupRepository.findAll();
        assertThat(empGroupList).hasSize(databaseSizeBeforeUpdate);
        EmpGroup testEmpGroup = empGroupList.get(empGroupList.size() - 1);
        assertThat(testEmpGroup.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testEmpGroup.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmpGroup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmpGroup.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testEmpGroup.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingEmpGroup() throws Exception {
        int databaseSizeBeforeUpdate = empGroupRepository.findAll().size();
        empGroup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpGroup in the database
        List<EmpGroup> empGroupList = empGroupRepository.findAll();
        assertThat(empGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmpGroup() throws Exception {
        int databaseSizeBeforeUpdate = empGroupRepository.findAll().size();
        empGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpGroup in the database
        List<EmpGroup> empGroupList = empGroupRepository.findAll();
        assertThat(empGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmpGroup() throws Exception {
        int databaseSizeBeforeUpdate = empGroupRepository.findAll().size();
        empGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpGroup in the database
        List<EmpGroup> empGroupList = empGroupRepository.findAll();
        assertThat(empGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpGroupWithPatch() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        int databaseSizeBeforeUpdate = empGroupRepository.findAll().size();

        // Update the empGroup using partial update
        EmpGroup partialUpdatedEmpGroup = new EmpGroup();
        partialUpdatedEmpGroup.setId(empGroup.getId());

        partialUpdatedEmpGroup.groupName(UPDATED_GROUP_NAME).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restEmpGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpGroup))
            )
            .andExpect(status().isOk());

        // Validate the EmpGroup in the database
        List<EmpGroup> empGroupList = empGroupRepository.findAll();
        assertThat(empGroupList).hasSize(databaseSizeBeforeUpdate);
        EmpGroup testEmpGroup = empGroupList.get(empGroupList.size() - 1);
        assertThat(testEmpGroup.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testEmpGroup.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEmpGroup.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEmpGroup.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testEmpGroup.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateEmpGroupWithPatch() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        int databaseSizeBeforeUpdate = empGroupRepository.findAll().size();

        // Update the empGroup using partial update
        EmpGroup partialUpdatedEmpGroup = new EmpGroup();
        partialUpdatedEmpGroup.setId(empGroup.getId());

        partialUpdatedEmpGroup
            .groupName(UPDATED_GROUP_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restEmpGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpGroup))
            )
            .andExpect(status().isOk());

        // Validate the EmpGroup in the database
        List<EmpGroup> empGroupList = empGroupRepository.findAll();
        assertThat(empGroupList).hasSize(databaseSizeBeforeUpdate);
        EmpGroup testEmpGroup = empGroupList.get(empGroupList.size() - 1);
        assertThat(testEmpGroup.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testEmpGroup.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmpGroup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmpGroup.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testEmpGroup.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingEmpGroup() throws Exception {
        int databaseSizeBeforeUpdate = empGroupRepository.findAll().size();
        empGroup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, empGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpGroup in the database
        List<EmpGroup> empGroupList = empGroupRepository.findAll();
        assertThat(empGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmpGroup() throws Exception {
        int databaseSizeBeforeUpdate = empGroupRepository.findAll().size();
        empGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpGroup in the database
        List<EmpGroup> empGroupList = empGroupRepository.findAll();
        assertThat(empGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmpGroup() throws Exception {
        int databaseSizeBeforeUpdate = empGroupRepository.findAll().size();
        empGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpGroupMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(empGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpGroup in the database
        List<EmpGroup> empGroupList = empGroupRepository.findAll();
        assertThat(empGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmpGroup() throws Exception {
        // Initialize the database
        empGroupRepository.saveAndFlush(empGroup);

        int databaseSizeBeforeDelete = empGroupRepository.findAll().size();

        // Delete the empGroup
        restEmpGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, empGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmpGroup> empGroupList = empGroupRepository.findAll();
        assertThat(empGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
