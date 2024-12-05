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
import vn.com.datamanager.domain.RoleGroup;
import vn.com.datamanager.domain.Roles;
import vn.com.datamanager.repository.RolesRepository;
import vn.com.datamanager.service.criteria.RolesCriteria;

/**
 * Integration tests for the {@link RolesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RolesResourceIT {

    private static final String DEFAULT_RESOURCE_URL = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_RESOURCE_DESC = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_DESC = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRolesMockMvc;

    private Roles roles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Roles createEntity(EntityManager em) {
        Roles roles = new Roles()
            .resourceUrl(DEFAULT_RESOURCE_URL)
            .resourceDesc(DEFAULT_RESOURCE_DESC)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return roles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Roles createUpdatedEntity(EntityManager em) {
        Roles roles = new Roles()
            .resourceUrl(UPDATED_RESOURCE_URL)
            .resourceDesc(UPDATED_RESOURCE_DESC)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return roles;
    }

    @BeforeEach
    public void initTest() {
        roles = createEntity(em);
    }

    @Test
    @Transactional
    void createRoles() throws Exception {
        int databaseSizeBeforeCreate = rolesRepository.findAll().size();
        // Create the Roles
        restRolesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roles)))
            .andExpect(status().isCreated());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeCreate + 1);
        Roles testRoles = rolesList.get(rolesList.size() - 1);
        assertThat(testRoles.getResourceUrl()).isEqualTo(DEFAULT_RESOURCE_URL);
        assertThat(testRoles.getResourceDesc()).isEqualTo(DEFAULT_RESOURCE_DESC);
        assertThat(testRoles.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testRoles.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRoles.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testRoles.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createRolesWithExistingId() throws Exception {
        // Create the Roles with an existing ID
        roles.setId(1L);

        int databaseSizeBeforeCreate = rolesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRolesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roles)))
            .andExpect(status().isBadRequest());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkResourceUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = rolesRepository.findAll().size();
        // set the field null
        roles.setResourceUrl(null);

        // Create the Roles, which fails.

        restRolesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roles)))
            .andExpect(status().isBadRequest());

        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRoles() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList
        restRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roles.getId().intValue())))
            .andExpect(jsonPath("$.[*].resourceUrl").value(hasItem(DEFAULT_RESOURCE_URL)))
            .andExpect(jsonPath("$.[*].resourceDesc").value(hasItem(DEFAULT_RESOURCE_DESC)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getRoles() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get the roles
        restRolesMockMvc
            .perform(get(ENTITY_API_URL_ID, roles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roles.getId().intValue()))
            .andExpect(jsonPath("$.resourceUrl").value(DEFAULT_RESOURCE_URL))
            .andExpect(jsonPath("$.resourceDesc").value(DEFAULT_RESOURCE_DESC))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getRolesByIdFiltering() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        Long id = roles.getId();

        defaultRolesShouldBeFound("id.equals=" + id);
        defaultRolesShouldNotBeFound("id.notEquals=" + id);

        defaultRolesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRolesShouldNotBeFound("id.greaterThan=" + id);

        defaultRolesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRolesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRolesByResourceUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where resourceUrl equals to DEFAULT_RESOURCE_URL
        defaultRolesShouldBeFound("resourceUrl.equals=" + DEFAULT_RESOURCE_URL);

        // Get all the rolesList where resourceUrl equals to UPDATED_RESOURCE_URL
        defaultRolesShouldNotBeFound("resourceUrl.equals=" + UPDATED_RESOURCE_URL);
    }

    @Test
    @Transactional
    void getAllRolesByResourceUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where resourceUrl not equals to DEFAULT_RESOURCE_URL
        defaultRolesShouldNotBeFound("resourceUrl.notEquals=" + DEFAULT_RESOURCE_URL);

        // Get all the rolesList where resourceUrl not equals to UPDATED_RESOURCE_URL
        defaultRolesShouldBeFound("resourceUrl.notEquals=" + UPDATED_RESOURCE_URL);
    }

    @Test
    @Transactional
    void getAllRolesByResourceUrlIsInShouldWork() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where resourceUrl in DEFAULT_RESOURCE_URL or UPDATED_RESOURCE_URL
        defaultRolesShouldBeFound("resourceUrl.in=" + DEFAULT_RESOURCE_URL + "," + UPDATED_RESOURCE_URL);

        // Get all the rolesList where resourceUrl equals to UPDATED_RESOURCE_URL
        defaultRolesShouldNotBeFound("resourceUrl.in=" + UPDATED_RESOURCE_URL);
    }

    @Test
    @Transactional
    void getAllRolesByResourceUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where resourceUrl is not null
        defaultRolesShouldBeFound("resourceUrl.specified=true");

        // Get all the rolesList where resourceUrl is null
        defaultRolesShouldNotBeFound("resourceUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllRolesByResourceUrlContainsSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where resourceUrl contains DEFAULT_RESOURCE_URL
        defaultRolesShouldBeFound("resourceUrl.contains=" + DEFAULT_RESOURCE_URL);

        // Get all the rolesList where resourceUrl contains UPDATED_RESOURCE_URL
        defaultRolesShouldNotBeFound("resourceUrl.contains=" + UPDATED_RESOURCE_URL);
    }

    @Test
    @Transactional
    void getAllRolesByResourceUrlNotContainsSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where resourceUrl does not contain DEFAULT_RESOURCE_URL
        defaultRolesShouldNotBeFound("resourceUrl.doesNotContain=" + DEFAULT_RESOURCE_URL);

        // Get all the rolesList where resourceUrl does not contain UPDATED_RESOURCE_URL
        defaultRolesShouldBeFound("resourceUrl.doesNotContain=" + UPDATED_RESOURCE_URL);
    }

    @Test
    @Transactional
    void getAllRolesByResourceDescIsEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where resourceDesc equals to DEFAULT_RESOURCE_DESC
        defaultRolesShouldBeFound("resourceDesc.equals=" + DEFAULT_RESOURCE_DESC);

        // Get all the rolesList where resourceDesc equals to UPDATED_RESOURCE_DESC
        defaultRolesShouldNotBeFound("resourceDesc.equals=" + UPDATED_RESOURCE_DESC);
    }

    @Test
    @Transactional
    void getAllRolesByResourceDescIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where resourceDesc not equals to DEFAULT_RESOURCE_DESC
        defaultRolesShouldNotBeFound("resourceDesc.notEquals=" + DEFAULT_RESOURCE_DESC);

        // Get all the rolesList where resourceDesc not equals to UPDATED_RESOURCE_DESC
        defaultRolesShouldBeFound("resourceDesc.notEquals=" + UPDATED_RESOURCE_DESC);
    }

    @Test
    @Transactional
    void getAllRolesByResourceDescIsInShouldWork() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where resourceDesc in DEFAULT_RESOURCE_DESC or UPDATED_RESOURCE_DESC
        defaultRolesShouldBeFound("resourceDesc.in=" + DEFAULT_RESOURCE_DESC + "," + UPDATED_RESOURCE_DESC);

        // Get all the rolesList where resourceDesc equals to UPDATED_RESOURCE_DESC
        defaultRolesShouldNotBeFound("resourceDesc.in=" + UPDATED_RESOURCE_DESC);
    }

    @Test
    @Transactional
    void getAllRolesByResourceDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where resourceDesc is not null
        defaultRolesShouldBeFound("resourceDesc.specified=true");

        // Get all the rolesList where resourceDesc is null
        defaultRolesShouldNotBeFound("resourceDesc.specified=false");
    }

    @Test
    @Transactional
    void getAllRolesByResourceDescContainsSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where resourceDesc contains DEFAULT_RESOURCE_DESC
        defaultRolesShouldBeFound("resourceDesc.contains=" + DEFAULT_RESOURCE_DESC);

        // Get all the rolesList where resourceDesc contains UPDATED_RESOURCE_DESC
        defaultRolesShouldNotBeFound("resourceDesc.contains=" + UPDATED_RESOURCE_DESC);
    }

    @Test
    @Transactional
    void getAllRolesByResourceDescNotContainsSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where resourceDesc does not contain DEFAULT_RESOURCE_DESC
        defaultRolesShouldNotBeFound("resourceDesc.doesNotContain=" + DEFAULT_RESOURCE_DESC);

        // Get all the rolesList where resourceDesc does not contain UPDATED_RESOURCE_DESC
        defaultRolesShouldBeFound("resourceDesc.doesNotContain=" + UPDATED_RESOURCE_DESC);
    }

    @Test
    @Transactional
    void getAllRolesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where createdDate equals to DEFAULT_CREATED_DATE
        defaultRolesShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the rolesList where createdDate equals to UPDATED_CREATED_DATE
        defaultRolesShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllRolesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultRolesShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the rolesList where createdDate not equals to UPDATED_CREATED_DATE
        defaultRolesShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllRolesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultRolesShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the rolesList where createdDate equals to UPDATED_CREATED_DATE
        defaultRolesShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllRolesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where createdDate is not null
        defaultRolesShouldBeFound("createdDate.specified=true");

        // Get all the rolesList where createdDate is null
        defaultRolesShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRolesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where createdBy equals to DEFAULT_CREATED_BY
        defaultRolesShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the rolesList where createdBy equals to UPDATED_CREATED_BY
        defaultRolesShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRolesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where createdBy not equals to DEFAULT_CREATED_BY
        defaultRolesShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the rolesList where createdBy not equals to UPDATED_CREATED_BY
        defaultRolesShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRolesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultRolesShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the rolesList where createdBy equals to UPDATED_CREATED_BY
        defaultRolesShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRolesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where createdBy is not null
        defaultRolesShouldBeFound("createdBy.specified=true");

        // Get all the rolesList where createdBy is null
        defaultRolesShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRolesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where createdBy contains DEFAULT_CREATED_BY
        defaultRolesShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the rolesList where createdBy contains UPDATED_CREATED_BY
        defaultRolesShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRolesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where createdBy does not contain DEFAULT_CREATED_BY
        defaultRolesShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the rolesList where createdBy does not contain UPDATED_CREATED_BY
        defaultRolesShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRolesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultRolesShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the rolesList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultRolesShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllRolesByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultRolesShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the rolesList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultRolesShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllRolesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultRolesShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the rolesList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultRolesShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllRolesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where lastModifiedDate is not null
        defaultRolesShouldBeFound("lastModifiedDate.specified=true");

        // Get all the rolesList where lastModifiedDate is null
        defaultRolesShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRolesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultRolesShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the rolesList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRolesShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRolesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultRolesShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the rolesList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultRolesShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRolesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultRolesShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the rolesList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRolesShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRolesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where lastModifiedBy is not null
        defaultRolesShouldBeFound("lastModifiedBy.specified=true");

        // Get all the rolesList where lastModifiedBy is null
        defaultRolesShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRolesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultRolesShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the rolesList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultRolesShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRolesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultRolesShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the rolesList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultRolesShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRolesByRoleGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);
        RoleGroup roleGroup;
        if (TestUtil.findAll(em, RoleGroup.class).isEmpty()) {
            roleGroup = RoleGroupResourceIT.createEntity(em);
            em.persist(roleGroup);
            em.flush();
        } else {
            roleGroup = TestUtil.findAll(em, RoleGroup.class).get(0);
        }
        em.persist(roleGroup);
        em.flush();
        roles.setRoleGroup(roleGroup);
        rolesRepository.saveAndFlush(roles);
        Long roleGroupId = roleGroup.getId();

        // Get all the rolesList where roleGroup equals to roleGroupId
        defaultRolesShouldBeFound("roleGroupId.equals=" + roleGroupId);

        // Get all the rolesList where roleGroup equals to (roleGroupId + 1)
        defaultRolesShouldNotBeFound("roleGroupId.equals=" + (roleGroupId + 1));
    }

    @Test
    @Transactional
    void getAllRolesByEmpGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);
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
        roles.addEmpGroup(empGroup);
        rolesRepository.saveAndFlush(roles);
        Long empGroupId = empGroup.getId();

        // Get all the rolesList where empGroup equals to empGroupId
        defaultRolesShouldBeFound("empGroupId.equals=" + empGroupId);

        // Get all the rolesList where empGroup equals to (empGroupId + 1)
        defaultRolesShouldNotBeFound("empGroupId.equals=" + (empGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRolesShouldBeFound(String filter) throws Exception {
        restRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roles.getId().intValue())))
            .andExpect(jsonPath("$.[*].resourceUrl").value(hasItem(DEFAULT_RESOURCE_URL)))
            .andExpect(jsonPath("$.[*].resourceDesc").value(hasItem(DEFAULT_RESOURCE_DESC)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restRolesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRolesShouldNotBeFound(String filter) throws Exception {
        restRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRolesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRoles() throws Exception {
        // Get the roles
        restRolesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoles() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();

        // Update the roles
        Roles updatedRoles = rolesRepository.findById(roles.getId()).get();
        // Disconnect from session so that the updates on updatedRoles are not directly saved in db
        em.detach(updatedRoles);
        updatedRoles
            .resourceUrl(UPDATED_RESOURCE_URL)
            .resourceDesc(UPDATED_RESOURCE_DESC)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoles.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoles))
            )
            .andExpect(status().isOk());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
        Roles testRoles = rolesList.get(rolesList.size() - 1);
        assertThat(testRoles.getResourceUrl()).isEqualTo(UPDATED_RESOURCE_URL);
        assertThat(testRoles.getResourceDesc()).isEqualTo(UPDATED_RESOURCE_DESC);
        assertThat(testRoles.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRoles.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRoles.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testRoles.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roles.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roles))
            )
            .andExpect(status().isBadRequest());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roles))
            )
            .andExpect(status().isBadRequest());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roles)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRolesWithPatch() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();

        // Update the roles using partial update
        Roles partialUpdatedRoles = new Roles();
        partialUpdatedRoles.setId(roles.getId());

        partialUpdatedRoles.createdBy(UPDATED_CREATED_BY);

        restRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoles))
            )
            .andExpect(status().isOk());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
        Roles testRoles = rolesList.get(rolesList.size() - 1);
        assertThat(testRoles.getResourceUrl()).isEqualTo(DEFAULT_RESOURCE_URL);
        assertThat(testRoles.getResourceDesc()).isEqualTo(DEFAULT_RESOURCE_DESC);
        assertThat(testRoles.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testRoles.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRoles.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testRoles.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateRolesWithPatch() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();

        // Update the roles using partial update
        Roles partialUpdatedRoles = new Roles();
        partialUpdatedRoles.setId(roles.getId());

        partialUpdatedRoles
            .resourceUrl(UPDATED_RESOURCE_URL)
            .resourceDesc(UPDATED_RESOURCE_DESC)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoles))
            )
            .andExpect(status().isOk());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
        Roles testRoles = rolesList.get(rolesList.size() - 1);
        assertThat(testRoles.getResourceUrl()).isEqualTo(UPDATED_RESOURCE_URL);
        assertThat(testRoles.getResourceDesc()).isEqualTo(UPDATED_RESOURCE_DESC);
        assertThat(testRoles.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRoles.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRoles.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testRoles.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roles))
            )
            .andExpect(status().isBadRequest());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roles))
            )
            .andExpect(status().isBadRequest());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(roles)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoles() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        int databaseSizeBeforeDelete = rolesRepository.findAll().size();

        // Delete the roles
        restRolesMockMvc
            .perform(delete(ENTITY_API_URL_ID, roles.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
