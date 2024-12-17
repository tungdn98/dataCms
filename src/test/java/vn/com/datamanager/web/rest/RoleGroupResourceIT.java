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
import vn.com.datamanager.domain.RoleGroup;
import vn.com.datamanager.domain.Roles;
import vn.com.datamanager.repository.RoleGroupRepository;
import vn.com.datamanager.service.criteria.RoleGroupCriteria;

/**
 * Integration tests for the {@link RoleGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoleGroupResourceIT {

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

    private static final String ENTITY_API_URL = "/api/role-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoleGroupRepository roleGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoleGroupMockMvc;

    private RoleGroup roleGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleGroup createEntity(EntityManager em) {
        RoleGroup roleGroup = new RoleGroup()
            .groupName(DEFAULT_GROUP_NAME)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return roleGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleGroup createUpdatedEntity(EntityManager em) {
        RoleGroup roleGroup = new RoleGroup()
            .groupName(UPDATED_GROUP_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return roleGroup;
    }

    @BeforeEach
    public void initTest() {
        roleGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createRoleGroup() throws Exception {
        int databaseSizeBeforeCreate = roleGroupRepository.findAll().size();
        // Create the RoleGroup
        restRoleGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleGroup)))
            .andExpect(status().isCreated());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeCreate + 1);
        RoleGroup testRoleGroup = roleGroupList.get(roleGroupList.size() - 1);
        assertThat(testRoleGroup.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
        assertThat(testRoleGroup.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testRoleGroup.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRoleGroup.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testRoleGroup.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createRoleGroupWithExistingId() throws Exception {
        // Create the RoleGroup with an existing ID
        roleGroup.setId(1L);

        int databaseSizeBeforeCreate = roleGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleGroup)))
            .andExpect(status().isBadRequest());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRoleGroups() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList
        restRoleGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getRoleGroup() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get the roleGroup
        restRoleGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, roleGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roleGroup.getId().intValue()))
            .andExpect(jsonPath("$.groupName").value(DEFAULT_GROUP_NAME))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getRoleGroupsByIdFiltering() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        Long id = roleGroup.getId();

        defaultRoleGroupShouldBeFound("id.equals=" + id);
        defaultRoleGroupShouldNotBeFound("id.notEquals=" + id);

        defaultRoleGroupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRoleGroupShouldNotBeFound("id.greaterThan=" + id);

        defaultRoleGroupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRoleGroupShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByGroupNameIsEqualToSomething() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where groupName equals to DEFAULT_GROUP_NAME
        defaultRoleGroupShouldBeFound("groupName.equals=" + DEFAULT_GROUP_NAME);

        // Get all the roleGroupList where groupName equals to UPDATED_GROUP_NAME
        defaultRoleGroupShouldNotBeFound("groupName.equals=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByGroupNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where groupName not equals to DEFAULT_GROUP_NAME
        defaultRoleGroupShouldNotBeFound("groupName.notEquals=" + DEFAULT_GROUP_NAME);

        // Get all the roleGroupList where groupName not equals to UPDATED_GROUP_NAME
        defaultRoleGroupShouldBeFound("groupName.notEquals=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByGroupNameIsInShouldWork() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where groupName in DEFAULT_GROUP_NAME or UPDATED_GROUP_NAME
        defaultRoleGroupShouldBeFound("groupName.in=" + DEFAULT_GROUP_NAME + "," + UPDATED_GROUP_NAME);

        // Get all the roleGroupList where groupName equals to UPDATED_GROUP_NAME
        defaultRoleGroupShouldNotBeFound("groupName.in=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByGroupNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where groupName is not null
        defaultRoleGroupShouldBeFound("groupName.specified=true");

        // Get all the roleGroupList where groupName is null
        defaultRoleGroupShouldNotBeFound("groupName.specified=false");
    }

    @Test
    @Transactional
    void getAllRoleGroupsByGroupNameContainsSomething() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where groupName contains DEFAULT_GROUP_NAME
        defaultRoleGroupShouldBeFound("groupName.contains=" + DEFAULT_GROUP_NAME);

        // Get all the roleGroupList where groupName contains UPDATED_GROUP_NAME
        defaultRoleGroupShouldNotBeFound("groupName.contains=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByGroupNameNotContainsSomething() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where groupName does not contain DEFAULT_GROUP_NAME
        defaultRoleGroupShouldNotBeFound("groupName.doesNotContain=" + DEFAULT_GROUP_NAME);

        // Get all the roleGroupList where groupName does not contain UPDATED_GROUP_NAME
        defaultRoleGroupShouldBeFound("groupName.doesNotContain=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where createdDate equals to DEFAULT_CREATED_DATE
        defaultRoleGroupShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the roleGroupList where createdDate equals to UPDATED_CREATED_DATE
        defaultRoleGroupShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultRoleGroupShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the roleGroupList where createdDate not equals to UPDATED_CREATED_DATE
        defaultRoleGroupShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultRoleGroupShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the roleGroupList where createdDate equals to UPDATED_CREATED_DATE
        defaultRoleGroupShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where createdDate is not null
        defaultRoleGroupShouldBeFound("createdDate.specified=true");

        // Get all the roleGroupList where createdDate is null
        defaultRoleGroupShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRoleGroupsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where createdBy equals to DEFAULT_CREATED_BY
        defaultRoleGroupShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the roleGroupList where createdBy equals to UPDATED_CREATED_BY
        defaultRoleGroupShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where createdBy not equals to DEFAULT_CREATED_BY
        defaultRoleGroupShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the roleGroupList where createdBy not equals to UPDATED_CREATED_BY
        defaultRoleGroupShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultRoleGroupShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the roleGroupList where createdBy equals to UPDATED_CREATED_BY
        defaultRoleGroupShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where createdBy is not null
        defaultRoleGroupShouldBeFound("createdBy.specified=true");

        // Get all the roleGroupList where createdBy is null
        defaultRoleGroupShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRoleGroupsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where createdBy contains DEFAULT_CREATED_BY
        defaultRoleGroupShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the roleGroupList where createdBy contains UPDATED_CREATED_BY
        defaultRoleGroupShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where createdBy does not contain DEFAULT_CREATED_BY
        defaultRoleGroupShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the roleGroupList where createdBy does not contain UPDATED_CREATED_BY
        defaultRoleGroupShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultRoleGroupShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the roleGroupList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultRoleGroupShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultRoleGroupShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the roleGroupList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultRoleGroupShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultRoleGroupShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the roleGroupList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultRoleGroupShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where lastModifiedDate is not null
        defaultRoleGroupShouldBeFound("lastModifiedDate.specified=true");

        // Get all the roleGroupList where lastModifiedDate is null
        defaultRoleGroupShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRoleGroupsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultRoleGroupShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the roleGroupList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRoleGroupShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultRoleGroupShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the roleGroupList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultRoleGroupShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultRoleGroupShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the roleGroupList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRoleGroupShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where lastModifiedBy is not null
        defaultRoleGroupShouldBeFound("lastModifiedBy.specified=true");

        // Get all the roleGroupList where lastModifiedBy is null
        defaultRoleGroupShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRoleGroupsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultRoleGroupShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the roleGroupList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultRoleGroupShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultRoleGroupShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the roleGroupList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultRoleGroupShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRoleGroupsByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);
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
        roleGroup.addRole(role);
        roleGroupRepository.saveAndFlush(roleGroup);
        Long roleId = role.getId();

        // Get all the roleGroupList where role equals to roleId
        defaultRoleGroupShouldBeFound("roleId.equals=" + roleId);

        // Get all the roleGroupList where role equals to (roleId + 1)
        defaultRoleGroupShouldNotBeFound("roleId.equals=" + (roleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRoleGroupShouldBeFound(String filter) throws Exception {
        restRoleGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restRoleGroupMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRoleGroupShouldNotBeFound(String filter) throws Exception {
        restRoleGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRoleGroupMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRoleGroup() throws Exception {
        // Get the roleGroup
        restRoleGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoleGroup() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();

        // Update the roleGroup
        RoleGroup updatedRoleGroup = roleGroupRepository.findById(roleGroup.getId()).get();
        // Disconnect from session so that the updates on updatedRoleGroup are not directly saved in db
        em.detach(updatedRoleGroup);
        updatedRoleGroup
            .groupName(UPDATED_GROUP_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restRoleGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoleGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoleGroup))
            )
            .andExpect(status().isOk());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
        RoleGroup testRoleGroup = roleGroupList.get(roleGroupList.size() - 1);
        assertThat(testRoleGroup.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testRoleGroup.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRoleGroup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRoleGroup.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testRoleGroup.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingRoleGroup() throws Exception {
        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();
        roleGroup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roleGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoleGroup() throws Exception {
        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();
        roleGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoleGroup() throws Exception {
        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();
        roleGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoleGroupWithPatch() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();

        // Update the roleGroup using partial update
        RoleGroup partialUpdatedRoleGroup = new RoleGroup();
        partialUpdatedRoleGroup.setId(roleGroup.getId());

        partialUpdatedRoleGroup.groupName(UPDATED_GROUP_NAME).createdDate(UPDATED_CREATED_DATE);

        restRoleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoleGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoleGroup))
            )
            .andExpect(status().isOk());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
        RoleGroup testRoleGroup = roleGroupList.get(roleGroupList.size() - 1);
        assertThat(testRoleGroup.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testRoleGroup.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRoleGroup.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRoleGroup.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testRoleGroup.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateRoleGroupWithPatch() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();

        // Update the roleGroup using partial update
        RoleGroup partialUpdatedRoleGroup = new RoleGroup();
        partialUpdatedRoleGroup.setId(roleGroup.getId());

        partialUpdatedRoleGroup
            .groupName(UPDATED_GROUP_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restRoleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoleGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoleGroup))
            )
            .andExpect(status().isOk());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
        RoleGroup testRoleGroup = roleGroupList.get(roleGroupList.size() - 1);
        assertThat(testRoleGroup.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testRoleGroup.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRoleGroup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRoleGroup.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testRoleGroup.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingRoleGroup() throws Exception {
        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();
        roleGroup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roleGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoleGroup() throws Exception {
        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();
        roleGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoleGroup() throws Exception {
        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();
        roleGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(roleGroup))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoleGroup() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        int databaseSizeBeforeDelete = roleGroupRepository.findAll().size();

        // Delete the roleGroup
        restRoleGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, roleGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
