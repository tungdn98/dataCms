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
import vn.com.datamanager.domain.ConfigLog;
import vn.com.datamanager.repository.ConfigLogRepository;
import vn.com.datamanager.service.criteria.ConfigLogCriteria;

/**
 * Integration tests for the {@link ConfigLogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConfigLogResourceIT {

    private static final String DEFAULT_CONFIG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_BEFORE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_BEFORE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_AFTER = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_AFTER = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MODIFIED_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIED_FULLNAME = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_FULLNAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/config-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConfigLogRepository configLogRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConfigLogMockMvc;

    private ConfigLog configLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConfigLog createEntity(EntityManager em) {
        ConfigLog configLog = new ConfigLog()
            .configName(DEFAULT_CONFIG_NAME)
            .valueBefore(DEFAULT_VALUE_BEFORE)
            .valueAfter(DEFAULT_VALUE_AFTER)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .modifiedUsername(DEFAULT_MODIFIED_USERNAME)
            .modifiedFullname(DEFAULT_MODIFIED_FULLNAME);
        return configLog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConfigLog createUpdatedEntity(EntityManager em) {
        ConfigLog configLog = new ConfigLog()
            .configName(UPDATED_CONFIG_NAME)
            .valueBefore(UPDATED_VALUE_BEFORE)
            .valueAfter(UPDATED_VALUE_AFTER)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .modifiedUsername(UPDATED_MODIFIED_USERNAME)
            .modifiedFullname(UPDATED_MODIFIED_FULLNAME);
        return configLog;
    }

    @BeforeEach
    public void initTest() {
        configLog = createEntity(em);
    }

    @Test
    @Transactional
    void createConfigLog() throws Exception {
        int databaseSizeBeforeCreate = configLogRepository.findAll().size();
        // Create the ConfigLog
        restConfigLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(configLog)))
            .andExpect(status().isCreated());

        // Validate the ConfigLog in the database
        List<ConfigLog> configLogList = configLogRepository.findAll();
        assertThat(configLogList).hasSize(databaseSizeBeforeCreate + 1);
        ConfigLog testConfigLog = configLogList.get(configLogList.size() - 1);
        assertThat(testConfigLog.getConfigName()).isEqualTo(DEFAULT_CONFIG_NAME);
        assertThat(testConfigLog.getValueBefore()).isEqualTo(DEFAULT_VALUE_BEFORE);
        assertThat(testConfigLog.getValueAfter()).isEqualTo(DEFAULT_VALUE_AFTER);
        assertThat(testConfigLog.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testConfigLog.getModifiedUsername()).isEqualTo(DEFAULT_MODIFIED_USERNAME);
        assertThat(testConfigLog.getModifiedFullname()).isEqualTo(DEFAULT_MODIFIED_FULLNAME);
    }

    @Test
    @Transactional
    void createConfigLogWithExistingId() throws Exception {
        // Create the ConfigLog with an existing ID
        configLog.setId(1L);

        int databaseSizeBeforeCreate = configLogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfigLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(configLog)))
            .andExpect(status().isBadRequest());

        // Validate the ConfigLog in the database
        List<ConfigLog> configLogList = configLogRepository.findAll();
        assertThat(configLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConfigLogs() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList
        restConfigLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].configName").value(hasItem(DEFAULT_CONFIG_NAME)))
            .andExpect(jsonPath("$.[*].valueBefore").value(hasItem(DEFAULT_VALUE_BEFORE)))
            .andExpect(jsonPath("$.[*].valueAfter").value(hasItem(DEFAULT_VALUE_AFTER)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedUsername").value(hasItem(DEFAULT_MODIFIED_USERNAME)))
            .andExpect(jsonPath("$.[*].modifiedFullname").value(hasItem(DEFAULT_MODIFIED_FULLNAME)));
    }

    @Test
    @Transactional
    void getConfigLog() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get the configLog
        restConfigLogMockMvc
            .perform(get(ENTITY_API_URL_ID, configLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(configLog.getId().intValue()))
            .andExpect(jsonPath("$.configName").value(DEFAULT_CONFIG_NAME))
            .andExpect(jsonPath("$.valueBefore").value(DEFAULT_VALUE_BEFORE))
            .andExpect(jsonPath("$.valueAfter").value(DEFAULT_VALUE_AFTER))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedUsername").value(DEFAULT_MODIFIED_USERNAME))
            .andExpect(jsonPath("$.modifiedFullname").value(DEFAULT_MODIFIED_FULLNAME));
    }

    @Test
    @Transactional
    void getConfigLogsByIdFiltering() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        Long id = configLog.getId();

        defaultConfigLogShouldBeFound("id.equals=" + id);
        defaultConfigLogShouldNotBeFound("id.notEquals=" + id);

        defaultConfigLogShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConfigLogShouldNotBeFound("id.greaterThan=" + id);

        defaultConfigLogShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConfigLogShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllConfigLogsByConfigNameIsEqualToSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where configName equals to DEFAULT_CONFIG_NAME
        defaultConfigLogShouldBeFound("configName.equals=" + DEFAULT_CONFIG_NAME);

        // Get all the configLogList where configName equals to UPDATED_CONFIG_NAME
        defaultConfigLogShouldNotBeFound("configName.equals=" + UPDATED_CONFIG_NAME);
    }

    @Test
    @Transactional
    void getAllConfigLogsByConfigNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where configName not equals to DEFAULT_CONFIG_NAME
        defaultConfigLogShouldNotBeFound("configName.notEquals=" + DEFAULT_CONFIG_NAME);

        // Get all the configLogList where configName not equals to UPDATED_CONFIG_NAME
        defaultConfigLogShouldBeFound("configName.notEquals=" + UPDATED_CONFIG_NAME);
    }

    @Test
    @Transactional
    void getAllConfigLogsByConfigNameIsInShouldWork() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where configName in DEFAULT_CONFIG_NAME or UPDATED_CONFIG_NAME
        defaultConfigLogShouldBeFound("configName.in=" + DEFAULT_CONFIG_NAME + "," + UPDATED_CONFIG_NAME);

        // Get all the configLogList where configName equals to UPDATED_CONFIG_NAME
        defaultConfigLogShouldNotBeFound("configName.in=" + UPDATED_CONFIG_NAME);
    }

    @Test
    @Transactional
    void getAllConfigLogsByConfigNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where configName is not null
        defaultConfigLogShouldBeFound("configName.specified=true");

        // Get all the configLogList where configName is null
        defaultConfigLogShouldNotBeFound("configName.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigLogsByConfigNameContainsSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where configName contains DEFAULT_CONFIG_NAME
        defaultConfigLogShouldBeFound("configName.contains=" + DEFAULT_CONFIG_NAME);

        // Get all the configLogList where configName contains UPDATED_CONFIG_NAME
        defaultConfigLogShouldNotBeFound("configName.contains=" + UPDATED_CONFIG_NAME);
    }

    @Test
    @Transactional
    void getAllConfigLogsByConfigNameNotContainsSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where configName does not contain DEFAULT_CONFIG_NAME
        defaultConfigLogShouldNotBeFound("configName.doesNotContain=" + DEFAULT_CONFIG_NAME);

        // Get all the configLogList where configName does not contain UPDATED_CONFIG_NAME
        defaultConfigLogShouldBeFound("configName.doesNotContain=" + UPDATED_CONFIG_NAME);
    }

    @Test
    @Transactional
    void getAllConfigLogsByValueBeforeIsEqualToSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where valueBefore equals to DEFAULT_VALUE_BEFORE
        defaultConfigLogShouldBeFound("valueBefore.equals=" + DEFAULT_VALUE_BEFORE);

        // Get all the configLogList where valueBefore equals to UPDATED_VALUE_BEFORE
        defaultConfigLogShouldNotBeFound("valueBefore.equals=" + UPDATED_VALUE_BEFORE);
    }

    @Test
    @Transactional
    void getAllConfigLogsByValueBeforeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where valueBefore not equals to DEFAULT_VALUE_BEFORE
        defaultConfigLogShouldNotBeFound("valueBefore.notEquals=" + DEFAULT_VALUE_BEFORE);

        // Get all the configLogList where valueBefore not equals to UPDATED_VALUE_BEFORE
        defaultConfigLogShouldBeFound("valueBefore.notEquals=" + UPDATED_VALUE_BEFORE);
    }

    @Test
    @Transactional
    void getAllConfigLogsByValueBeforeIsInShouldWork() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where valueBefore in DEFAULT_VALUE_BEFORE or UPDATED_VALUE_BEFORE
        defaultConfigLogShouldBeFound("valueBefore.in=" + DEFAULT_VALUE_BEFORE + "," + UPDATED_VALUE_BEFORE);

        // Get all the configLogList where valueBefore equals to UPDATED_VALUE_BEFORE
        defaultConfigLogShouldNotBeFound("valueBefore.in=" + UPDATED_VALUE_BEFORE);
    }

    @Test
    @Transactional
    void getAllConfigLogsByValueBeforeIsNullOrNotNull() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where valueBefore is not null
        defaultConfigLogShouldBeFound("valueBefore.specified=true");

        // Get all the configLogList where valueBefore is null
        defaultConfigLogShouldNotBeFound("valueBefore.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigLogsByValueBeforeContainsSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where valueBefore contains DEFAULT_VALUE_BEFORE
        defaultConfigLogShouldBeFound("valueBefore.contains=" + DEFAULT_VALUE_BEFORE);

        // Get all the configLogList where valueBefore contains UPDATED_VALUE_BEFORE
        defaultConfigLogShouldNotBeFound("valueBefore.contains=" + UPDATED_VALUE_BEFORE);
    }

    @Test
    @Transactional
    void getAllConfigLogsByValueBeforeNotContainsSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where valueBefore does not contain DEFAULT_VALUE_BEFORE
        defaultConfigLogShouldNotBeFound("valueBefore.doesNotContain=" + DEFAULT_VALUE_BEFORE);

        // Get all the configLogList where valueBefore does not contain UPDATED_VALUE_BEFORE
        defaultConfigLogShouldBeFound("valueBefore.doesNotContain=" + UPDATED_VALUE_BEFORE);
    }

    @Test
    @Transactional
    void getAllConfigLogsByValueAfterIsEqualToSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where valueAfter equals to DEFAULT_VALUE_AFTER
        defaultConfigLogShouldBeFound("valueAfter.equals=" + DEFAULT_VALUE_AFTER);

        // Get all the configLogList where valueAfter equals to UPDATED_VALUE_AFTER
        defaultConfigLogShouldNotBeFound("valueAfter.equals=" + UPDATED_VALUE_AFTER);
    }

    @Test
    @Transactional
    void getAllConfigLogsByValueAfterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where valueAfter not equals to DEFAULT_VALUE_AFTER
        defaultConfigLogShouldNotBeFound("valueAfter.notEquals=" + DEFAULT_VALUE_AFTER);

        // Get all the configLogList where valueAfter not equals to UPDATED_VALUE_AFTER
        defaultConfigLogShouldBeFound("valueAfter.notEquals=" + UPDATED_VALUE_AFTER);
    }

    @Test
    @Transactional
    void getAllConfigLogsByValueAfterIsInShouldWork() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where valueAfter in DEFAULT_VALUE_AFTER or UPDATED_VALUE_AFTER
        defaultConfigLogShouldBeFound("valueAfter.in=" + DEFAULT_VALUE_AFTER + "," + UPDATED_VALUE_AFTER);

        // Get all the configLogList where valueAfter equals to UPDATED_VALUE_AFTER
        defaultConfigLogShouldNotBeFound("valueAfter.in=" + UPDATED_VALUE_AFTER);
    }

    @Test
    @Transactional
    void getAllConfigLogsByValueAfterIsNullOrNotNull() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where valueAfter is not null
        defaultConfigLogShouldBeFound("valueAfter.specified=true");

        // Get all the configLogList where valueAfter is null
        defaultConfigLogShouldNotBeFound("valueAfter.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigLogsByValueAfterContainsSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where valueAfter contains DEFAULT_VALUE_AFTER
        defaultConfigLogShouldBeFound("valueAfter.contains=" + DEFAULT_VALUE_AFTER);

        // Get all the configLogList where valueAfter contains UPDATED_VALUE_AFTER
        defaultConfigLogShouldNotBeFound("valueAfter.contains=" + UPDATED_VALUE_AFTER);
    }

    @Test
    @Transactional
    void getAllConfigLogsByValueAfterNotContainsSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where valueAfter does not contain DEFAULT_VALUE_AFTER
        defaultConfigLogShouldNotBeFound("valueAfter.doesNotContain=" + DEFAULT_VALUE_AFTER);

        // Get all the configLogList where valueAfter does not contain UPDATED_VALUE_AFTER
        defaultConfigLogShouldBeFound("valueAfter.doesNotContain=" + UPDATED_VALUE_AFTER);
    }

    @Test
    @Transactional
    void getAllConfigLogsByModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where modifiedDate equals to DEFAULT_MODIFIED_DATE
        defaultConfigLogShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);

        // Get all the configLogList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultConfigLogShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllConfigLogsByModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where modifiedDate not equals to DEFAULT_MODIFIED_DATE
        defaultConfigLogShouldNotBeFound("modifiedDate.notEquals=" + DEFAULT_MODIFIED_DATE);

        // Get all the configLogList where modifiedDate not equals to UPDATED_MODIFIED_DATE
        defaultConfigLogShouldBeFound("modifiedDate.notEquals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllConfigLogsByModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
        defaultConfigLogShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);

        // Get all the configLogList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultConfigLogShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllConfigLogsByModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where modifiedDate is not null
        defaultConfigLogShouldBeFound("modifiedDate.specified=true");

        // Get all the configLogList where modifiedDate is null
        defaultConfigLogShouldNotBeFound("modifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigLogsByModifiedUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where modifiedUsername equals to DEFAULT_MODIFIED_USERNAME
        defaultConfigLogShouldBeFound("modifiedUsername.equals=" + DEFAULT_MODIFIED_USERNAME);

        // Get all the configLogList where modifiedUsername equals to UPDATED_MODIFIED_USERNAME
        defaultConfigLogShouldNotBeFound("modifiedUsername.equals=" + UPDATED_MODIFIED_USERNAME);
    }

    @Test
    @Transactional
    void getAllConfigLogsByModifiedUsernameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where modifiedUsername not equals to DEFAULT_MODIFIED_USERNAME
        defaultConfigLogShouldNotBeFound("modifiedUsername.notEquals=" + DEFAULT_MODIFIED_USERNAME);

        // Get all the configLogList where modifiedUsername not equals to UPDATED_MODIFIED_USERNAME
        defaultConfigLogShouldBeFound("modifiedUsername.notEquals=" + UPDATED_MODIFIED_USERNAME);
    }

    @Test
    @Transactional
    void getAllConfigLogsByModifiedUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where modifiedUsername in DEFAULT_MODIFIED_USERNAME or UPDATED_MODIFIED_USERNAME
        defaultConfigLogShouldBeFound("modifiedUsername.in=" + DEFAULT_MODIFIED_USERNAME + "," + UPDATED_MODIFIED_USERNAME);

        // Get all the configLogList where modifiedUsername equals to UPDATED_MODIFIED_USERNAME
        defaultConfigLogShouldNotBeFound("modifiedUsername.in=" + UPDATED_MODIFIED_USERNAME);
    }

    @Test
    @Transactional
    void getAllConfigLogsByModifiedUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where modifiedUsername is not null
        defaultConfigLogShouldBeFound("modifiedUsername.specified=true");

        // Get all the configLogList where modifiedUsername is null
        defaultConfigLogShouldNotBeFound("modifiedUsername.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigLogsByModifiedUsernameContainsSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where modifiedUsername contains DEFAULT_MODIFIED_USERNAME
        defaultConfigLogShouldBeFound("modifiedUsername.contains=" + DEFAULT_MODIFIED_USERNAME);

        // Get all the configLogList where modifiedUsername contains UPDATED_MODIFIED_USERNAME
        defaultConfigLogShouldNotBeFound("modifiedUsername.contains=" + UPDATED_MODIFIED_USERNAME);
    }

    @Test
    @Transactional
    void getAllConfigLogsByModifiedUsernameNotContainsSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where modifiedUsername does not contain DEFAULT_MODIFIED_USERNAME
        defaultConfigLogShouldNotBeFound("modifiedUsername.doesNotContain=" + DEFAULT_MODIFIED_USERNAME);

        // Get all the configLogList where modifiedUsername does not contain UPDATED_MODIFIED_USERNAME
        defaultConfigLogShouldBeFound("modifiedUsername.doesNotContain=" + UPDATED_MODIFIED_USERNAME);
    }

    @Test
    @Transactional
    void getAllConfigLogsByModifiedFullnameIsEqualToSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where modifiedFullname equals to DEFAULT_MODIFIED_FULLNAME
        defaultConfigLogShouldBeFound("modifiedFullname.equals=" + DEFAULT_MODIFIED_FULLNAME);

        // Get all the configLogList where modifiedFullname equals to UPDATED_MODIFIED_FULLNAME
        defaultConfigLogShouldNotBeFound("modifiedFullname.equals=" + UPDATED_MODIFIED_FULLNAME);
    }

    @Test
    @Transactional
    void getAllConfigLogsByModifiedFullnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where modifiedFullname not equals to DEFAULT_MODIFIED_FULLNAME
        defaultConfigLogShouldNotBeFound("modifiedFullname.notEquals=" + DEFAULT_MODIFIED_FULLNAME);

        // Get all the configLogList where modifiedFullname not equals to UPDATED_MODIFIED_FULLNAME
        defaultConfigLogShouldBeFound("modifiedFullname.notEquals=" + UPDATED_MODIFIED_FULLNAME);
    }

    @Test
    @Transactional
    void getAllConfigLogsByModifiedFullnameIsInShouldWork() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where modifiedFullname in DEFAULT_MODIFIED_FULLNAME or UPDATED_MODIFIED_FULLNAME
        defaultConfigLogShouldBeFound("modifiedFullname.in=" + DEFAULT_MODIFIED_FULLNAME + "," + UPDATED_MODIFIED_FULLNAME);

        // Get all the configLogList where modifiedFullname equals to UPDATED_MODIFIED_FULLNAME
        defaultConfigLogShouldNotBeFound("modifiedFullname.in=" + UPDATED_MODIFIED_FULLNAME);
    }

    @Test
    @Transactional
    void getAllConfigLogsByModifiedFullnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where modifiedFullname is not null
        defaultConfigLogShouldBeFound("modifiedFullname.specified=true");

        // Get all the configLogList where modifiedFullname is null
        defaultConfigLogShouldNotBeFound("modifiedFullname.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigLogsByModifiedFullnameContainsSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where modifiedFullname contains DEFAULT_MODIFIED_FULLNAME
        defaultConfigLogShouldBeFound("modifiedFullname.contains=" + DEFAULT_MODIFIED_FULLNAME);

        // Get all the configLogList where modifiedFullname contains UPDATED_MODIFIED_FULLNAME
        defaultConfigLogShouldNotBeFound("modifiedFullname.contains=" + UPDATED_MODIFIED_FULLNAME);
    }

    @Test
    @Transactional
    void getAllConfigLogsByModifiedFullnameNotContainsSomething() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        // Get all the configLogList where modifiedFullname does not contain DEFAULT_MODIFIED_FULLNAME
        defaultConfigLogShouldNotBeFound("modifiedFullname.doesNotContain=" + DEFAULT_MODIFIED_FULLNAME);

        // Get all the configLogList where modifiedFullname does not contain UPDATED_MODIFIED_FULLNAME
        defaultConfigLogShouldBeFound("modifiedFullname.doesNotContain=" + UPDATED_MODIFIED_FULLNAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConfigLogShouldBeFound(String filter) throws Exception {
        restConfigLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].configName").value(hasItem(DEFAULT_CONFIG_NAME)))
            .andExpect(jsonPath("$.[*].valueBefore").value(hasItem(DEFAULT_VALUE_BEFORE)))
            .andExpect(jsonPath("$.[*].valueAfter").value(hasItem(DEFAULT_VALUE_AFTER)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedUsername").value(hasItem(DEFAULT_MODIFIED_USERNAME)))
            .andExpect(jsonPath("$.[*].modifiedFullname").value(hasItem(DEFAULT_MODIFIED_FULLNAME)));

        // Check, that the count call also returns 1
        restConfigLogMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConfigLogShouldNotBeFound(String filter) throws Exception {
        restConfigLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConfigLogMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConfigLog() throws Exception {
        // Get the configLog
        restConfigLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConfigLog() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        int databaseSizeBeforeUpdate = configLogRepository.findAll().size();

        // Update the configLog
        ConfigLog updatedConfigLog = configLogRepository.findById(configLog.getId()).get();
        // Disconnect from session so that the updates on updatedConfigLog are not directly saved in db
        em.detach(updatedConfigLog);
        updatedConfigLog
            .configName(UPDATED_CONFIG_NAME)
            .valueBefore(UPDATED_VALUE_BEFORE)
            .valueAfter(UPDATED_VALUE_AFTER)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .modifiedUsername(UPDATED_MODIFIED_USERNAME)
            .modifiedFullname(UPDATED_MODIFIED_FULLNAME);

        restConfigLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConfigLog.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConfigLog))
            )
            .andExpect(status().isOk());

        // Validate the ConfigLog in the database
        List<ConfigLog> configLogList = configLogRepository.findAll();
        assertThat(configLogList).hasSize(databaseSizeBeforeUpdate);
        ConfigLog testConfigLog = configLogList.get(configLogList.size() - 1);
        assertThat(testConfigLog.getConfigName()).isEqualTo(UPDATED_CONFIG_NAME);
        assertThat(testConfigLog.getValueBefore()).isEqualTo(UPDATED_VALUE_BEFORE);
        assertThat(testConfigLog.getValueAfter()).isEqualTo(UPDATED_VALUE_AFTER);
        assertThat(testConfigLog.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testConfigLog.getModifiedUsername()).isEqualTo(UPDATED_MODIFIED_USERNAME);
        assertThat(testConfigLog.getModifiedFullname()).isEqualTo(UPDATED_MODIFIED_FULLNAME);
    }

    @Test
    @Transactional
    void putNonExistingConfigLog() throws Exception {
        int databaseSizeBeforeUpdate = configLogRepository.findAll().size();
        configLog.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, configLog.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigLog in the database
        List<ConfigLog> configLogList = configLogRepository.findAll();
        assertThat(configLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConfigLog() throws Exception {
        int databaseSizeBeforeUpdate = configLogRepository.findAll().size();
        configLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigLog in the database
        List<ConfigLog> configLogList = configLogRepository.findAll();
        assertThat(configLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConfigLog() throws Exception {
        int databaseSizeBeforeUpdate = configLogRepository.findAll().size();
        configLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigLogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(configLog)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConfigLog in the database
        List<ConfigLog> configLogList = configLogRepository.findAll();
        assertThat(configLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConfigLogWithPatch() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        int databaseSizeBeforeUpdate = configLogRepository.findAll().size();

        // Update the configLog using partial update
        ConfigLog partialUpdatedConfigLog = new ConfigLog();
        partialUpdatedConfigLog.setId(configLog.getId());

        partialUpdatedConfigLog
            .configName(UPDATED_CONFIG_NAME)
            .valueBefore(UPDATED_VALUE_BEFORE)
            .valueAfter(UPDATED_VALUE_AFTER)
            .modifiedUsername(UPDATED_MODIFIED_USERNAME)
            .modifiedFullname(UPDATED_MODIFIED_FULLNAME);

        restConfigLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfigLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfigLog))
            )
            .andExpect(status().isOk());

        // Validate the ConfigLog in the database
        List<ConfigLog> configLogList = configLogRepository.findAll();
        assertThat(configLogList).hasSize(databaseSizeBeforeUpdate);
        ConfigLog testConfigLog = configLogList.get(configLogList.size() - 1);
        assertThat(testConfigLog.getConfigName()).isEqualTo(UPDATED_CONFIG_NAME);
        assertThat(testConfigLog.getValueBefore()).isEqualTo(UPDATED_VALUE_BEFORE);
        assertThat(testConfigLog.getValueAfter()).isEqualTo(UPDATED_VALUE_AFTER);
        assertThat(testConfigLog.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testConfigLog.getModifiedUsername()).isEqualTo(UPDATED_MODIFIED_USERNAME);
        assertThat(testConfigLog.getModifiedFullname()).isEqualTo(UPDATED_MODIFIED_FULLNAME);
    }

    @Test
    @Transactional
    void fullUpdateConfigLogWithPatch() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        int databaseSizeBeforeUpdate = configLogRepository.findAll().size();

        // Update the configLog using partial update
        ConfigLog partialUpdatedConfigLog = new ConfigLog();
        partialUpdatedConfigLog.setId(configLog.getId());

        partialUpdatedConfigLog
            .configName(UPDATED_CONFIG_NAME)
            .valueBefore(UPDATED_VALUE_BEFORE)
            .valueAfter(UPDATED_VALUE_AFTER)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .modifiedUsername(UPDATED_MODIFIED_USERNAME)
            .modifiedFullname(UPDATED_MODIFIED_FULLNAME);

        restConfigLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfigLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfigLog))
            )
            .andExpect(status().isOk());

        // Validate the ConfigLog in the database
        List<ConfigLog> configLogList = configLogRepository.findAll();
        assertThat(configLogList).hasSize(databaseSizeBeforeUpdate);
        ConfigLog testConfigLog = configLogList.get(configLogList.size() - 1);
        assertThat(testConfigLog.getConfigName()).isEqualTo(UPDATED_CONFIG_NAME);
        assertThat(testConfigLog.getValueBefore()).isEqualTo(UPDATED_VALUE_BEFORE);
        assertThat(testConfigLog.getValueAfter()).isEqualTo(UPDATED_VALUE_AFTER);
        assertThat(testConfigLog.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testConfigLog.getModifiedUsername()).isEqualTo(UPDATED_MODIFIED_USERNAME);
        assertThat(testConfigLog.getModifiedFullname()).isEqualTo(UPDATED_MODIFIED_FULLNAME);
    }

    @Test
    @Transactional
    void patchNonExistingConfigLog() throws Exception {
        int databaseSizeBeforeUpdate = configLogRepository.findAll().size();
        configLog.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, configLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigLog in the database
        List<ConfigLog> configLogList = configLogRepository.findAll();
        assertThat(configLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConfigLog() throws Exception {
        int databaseSizeBeforeUpdate = configLogRepository.findAll().size();
        configLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigLog in the database
        List<ConfigLog> configLogList = configLogRepository.findAll();
        assertThat(configLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConfigLog() throws Exception {
        int databaseSizeBeforeUpdate = configLogRepository.findAll().size();
        configLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigLogMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(configLog))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConfigLog in the database
        List<ConfigLog> configLogList = configLogRepository.findAll();
        assertThat(configLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConfigLog() throws Exception {
        // Initialize the database
        configLogRepository.saveAndFlush(configLog);

        int databaseSizeBeforeDelete = configLogRepository.findAll().size();

        // Delete the configLog
        restConfigLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, configLog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConfigLog> configLogList = configLogRepository.findAll();
        assertThat(configLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
