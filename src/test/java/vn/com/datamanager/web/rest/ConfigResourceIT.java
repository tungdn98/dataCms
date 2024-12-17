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
import vn.com.datamanager.domain.Config;
import vn.com.datamanager.repository.ConfigRepository;
import vn.com.datamanager.service.criteria.ConfigCriteria;

/**
 * Integration tests for the {@link ConfigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConfigResourceIT {

    private static final String DEFAULT_CONFIG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIG_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIG_DESC = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG_DESC = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/configs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConfigMockMvc;

    private Config config;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Config createEntity(EntityManager em) {
        Config config = new Config()
            .configName(DEFAULT_CONFIG_NAME)
            .configValue(DEFAULT_CONFIG_VALUE)
            .configDesc(DEFAULT_CONFIG_DESC)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return config;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Config createUpdatedEntity(EntityManager em) {
        Config config = new Config()
            .configName(UPDATED_CONFIG_NAME)
            .configValue(UPDATED_CONFIG_VALUE)
            .configDesc(UPDATED_CONFIG_DESC)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return config;
    }

    @BeforeEach
    public void initTest() {
        config = createEntity(em);
    }

    @Test
    @Transactional
    void createConfig() throws Exception {
        int databaseSizeBeforeCreate = configRepository.findAll().size();
        // Create the Config
        restConfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(config)))
            .andExpect(status().isCreated());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeCreate + 1);
        Config testConfig = configList.get(configList.size() - 1);
        assertThat(testConfig.getConfigName()).isEqualTo(DEFAULT_CONFIG_NAME);
        assertThat(testConfig.getConfigValue()).isEqualTo(DEFAULT_CONFIG_VALUE);
        assertThat(testConfig.getConfigDesc()).isEqualTo(DEFAULT_CONFIG_DESC);
        assertThat(testConfig.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testConfig.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testConfig.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testConfig.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createConfigWithExistingId() throws Exception {
        // Create the Config with an existing ID
        config.setId(1L);

        int databaseSizeBeforeCreate = configRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(config)))
            .andExpect(status().isBadRequest());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkConfigValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = configRepository.findAll().size();
        // set the field null
        config.setConfigValue(null);

        // Create the Config, which fails.

        restConfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(config)))
            .andExpect(status().isBadRequest());

        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllConfigs() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList
        restConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(config.getId().intValue())))
            .andExpect(jsonPath("$.[*].configName").value(hasItem(DEFAULT_CONFIG_NAME)))
            .andExpect(jsonPath("$.[*].configValue").value(hasItem(DEFAULT_CONFIG_VALUE)))
            .andExpect(jsonPath("$.[*].configDesc").value(hasItem(DEFAULT_CONFIG_DESC)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getConfig() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get the config
        restConfigMockMvc
            .perform(get(ENTITY_API_URL_ID, config.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(config.getId().intValue()))
            .andExpect(jsonPath("$.configName").value(DEFAULT_CONFIG_NAME))
            .andExpect(jsonPath("$.configValue").value(DEFAULT_CONFIG_VALUE))
            .andExpect(jsonPath("$.configDesc").value(DEFAULT_CONFIG_DESC))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getConfigsByIdFiltering() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        Long id = config.getId();

        defaultConfigShouldBeFound("id.equals=" + id);
        defaultConfigShouldNotBeFound("id.notEquals=" + id);

        defaultConfigShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConfigShouldNotBeFound("id.greaterThan=" + id);

        defaultConfigShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConfigShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllConfigsByConfigNameIsEqualToSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configName equals to DEFAULT_CONFIG_NAME
        defaultConfigShouldBeFound("configName.equals=" + DEFAULT_CONFIG_NAME);

        // Get all the configList where configName equals to UPDATED_CONFIG_NAME
        defaultConfigShouldNotBeFound("configName.equals=" + UPDATED_CONFIG_NAME);
    }

    @Test
    @Transactional
    void getAllConfigsByConfigNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configName not equals to DEFAULT_CONFIG_NAME
        defaultConfigShouldNotBeFound("configName.notEquals=" + DEFAULT_CONFIG_NAME);

        // Get all the configList where configName not equals to UPDATED_CONFIG_NAME
        defaultConfigShouldBeFound("configName.notEquals=" + UPDATED_CONFIG_NAME);
    }

    @Test
    @Transactional
    void getAllConfigsByConfigNameIsInShouldWork() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configName in DEFAULT_CONFIG_NAME or UPDATED_CONFIG_NAME
        defaultConfigShouldBeFound("configName.in=" + DEFAULT_CONFIG_NAME + "," + UPDATED_CONFIG_NAME);

        // Get all the configList where configName equals to UPDATED_CONFIG_NAME
        defaultConfigShouldNotBeFound("configName.in=" + UPDATED_CONFIG_NAME);
    }

    @Test
    @Transactional
    void getAllConfigsByConfigNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configName is not null
        defaultConfigShouldBeFound("configName.specified=true");

        // Get all the configList where configName is null
        defaultConfigShouldNotBeFound("configName.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigsByConfigNameContainsSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configName contains DEFAULT_CONFIG_NAME
        defaultConfigShouldBeFound("configName.contains=" + DEFAULT_CONFIG_NAME);

        // Get all the configList where configName contains UPDATED_CONFIG_NAME
        defaultConfigShouldNotBeFound("configName.contains=" + UPDATED_CONFIG_NAME);
    }

    @Test
    @Transactional
    void getAllConfigsByConfigNameNotContainsSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configName does not contain DEFAULT_CONFIG_NAME
        defaultConfigShouldNotBeFound("configName.doesNotContain=" + DEFAULT_CONFIG_NAME);

        // Get all the configList where configName does not contain UPDATED_CONFIG_NAME
        defaultConfigShouldBeFound("configName.doesNotContain=" + UPDATED_CONFIG_NAME);
    }

    @Test
    @Transactional
    void getAllConfigsByConfigValueIsEqualToSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configValue equals to DEFAULT_CONFIG_VALUE
        defaultConfigShouldBeFound("configValue.equals=" + DEFAULT_CONFIG_VALUE);

        // Get all the configList where configValue equals to UPDATED_CONFIG_VALUE
        defaultConfigShouldNotBeFound("configValue.equals=" + UPDATED_CONFIG_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByConfigValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configValue not equals to DEFAULT_CONFIG_VALUE
        defaultConfigShouldNotBeFound("configValue.notEquals=" + DEFAULT_CONFIG_VALUE);

        // Get all the configList where configValue not equals to UPDATED_CONFIG_VALUE
        defaultConfigShouldBeFound("configValue.notEquals=" + UPDATED_CONFIG_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByConfigValueIsInShouldWork() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configValue in DEFAULT_CONFIG_VALUE or UPDATED_CONFIG_VALUE
        defaultConfigShouldBeFound("configValue.in=" + DEFAULT_CONFIG_VALUE + "," + UPDATED_CONFIG_VALUE);

        // Get all the configList where configValue equals to UPDATED_CONFIG_VALUE
        defaultConfigShouldNotBeFound("configValue.in=" + UPDATED_CONFIG_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByConfigValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configValue is not null
        defaultConfigShouldBeFound("configValue.specified=true");

        // Get all the configList where configValue is null
        defaultConfigShouldNotBeFound("configValue.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigsByConfigValueContainsSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configValue contains DEFAULT_CONFIG_VALUE
        defaultConfigShouldBeFound("configValue.contains=" + DEFAULT_CONFIG_VALUE);

        // Get all the configList where configValue contains UPDATED_CONFIG_VALUE
        defaultConfigShouldNotBeFound("configValue.contains=" + UPDATED_CONFIG_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByConfigValueNotContainsSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configValue does not contain DEFAULT_CONFIG_VALUE
        defaultConfigShouldNotBeFound("configValue.doesNotContain=" + DEFAULT_CONFIG_VALUE);

        // Get all the configList where configValue does not contain UPDATED_CONFIG_VALUE
        defaultConfigShouldBeFound("configValue.doesNotContain=" + UPDATED_CONFIG_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByConfigDescIsEqualToSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configDesc equals to DEFAULT_CONFIG_DESC
        defaultConfigShouldBeFound("configDesc.equals=" + DEFAULT_CONFIG_DESC);

        // Get all the configList where configDesc equals to UPDATED_CONFIG_DESC
        defaultConfigShouldNotBeFound("configDesc.equals=" + UPDATED_CONFIG_DESC);
    }

    @Test
    @Transactional
    void getAllConfigsByConfigDescIsNotEqualToSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configDesc not equals to DEFAULT_CONFIG_DESC
        defaultConfigShouldNotBeFound("configDesc.notEquals=" + DEFAULT_CONFIG_DESC);

        // Get all the configList where configDesc not equals to UPDATED_CONFIG_DESC
        defaultConfigShouldBeFound("configDesc.notEquals=" + UPDATED_CONFIG_DESC);
    }

    @Test
    @Transactional
    void getAllConfigsByConfigDescIsInShouldWork() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configDesc in DEFAULT_CONFIG_DESC or UPDATED_CONFIG_DESC
        defaultConfigShouldBeFound("configDesc.in=" + DEFAULT_CONFIG_DESC + "," + UPDATED_CONFIG_DESC);

        // Get all the configList where configDesc equals to UPDATED_CONFIG_DESC
        defaultConfigShouldNotBeFound("configDesc.in=" + UPDATED_CONFIG_DESC);
    }

    @Test
    @Transactional
    void getAllConfigsByConfigDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configDesc is not null
        defaultConfigShouldBeFound("configDesc.specified=true");

        // Get all the configList where configDesc is null
        defaultConfigShouldNotBeFound("configDesc.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigsByConfigDescContainsSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configDesc contains DEFAULT_CONFIG_DESC
        defaultConfigShouldBeFound("configDesc.contains=" + DEFAULT_CONFIG_DESC);

        // Get all the configList where configDesc contains UPDATED_CONFIG_DESC
        defaultConfigShouldNotBeFound("configDesc.contains=" + UPDATED_CONFIG_DESC);
    }

    @Test
    @Transactional
    void getAllConfigsByConfigDescNotContainsSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where configDesc does not contain DEFAULT_CONFIG_DESC
        defaultConfigShouldNotBeFound("configDesc.doesNotContain=" + DEFAULT_CONFIG_DESC);

        // Get all the configList where configDesc does not contain UPDATED_CONFIG_DESC
        defaultConfigShouldBeFound("configDesc.doesNotContain=" + UPDATED_CONFIG_DESC);
    }

    @Test
    @Transactional
    void getAllConfigsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where createdDate equals to DEFAULT_CREATED_DATE
        defaultConfigShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the configList where createdDate equals to UPDATED_CREATED_DATE
        defaultConfigShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllConfigsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultConfigShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the configList where createdDate not equals to UPDATED_CREATED_DATE
        defaultConfigShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllConfigsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultConfigShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the configList where createdDate equals to UPDATED_CREATED_DATE
        defaultConfigShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllConfigsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where createdDate is not null
        defaultConfigShouldBeFound("createdDate.specified=true");

        // Get all the configList where createdDate is null
        defaultConfigShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where createdBy equals to DEFAULT_CREATED_BY
        defaultConfigShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the configList where createdBy equals to UPDATED_CREATED_BY
        defaultConfigShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllConfigsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where createdBy not equals to DEFAULT_CREATED_BY
        defaultConfigShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the configList where createdBy not equals to UPDATED_CREATED_BY
        defaultConfigShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllConfigsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultConfigShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the configList where createdBy equals to UPDATED_CREATED_BY
        defaultConfigShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllConfigsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where createdBy is not null
        defaultConfigShouldBeFound("createdBy.specified=true");

        // Get all the configList where createdBy is null
        defaultConfigShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where createdBy contains DEFAULT_CREATED_BY
        defaultConfigShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the configList where createdBy contains UPDATED_CREATED_BY
        defaultConfigShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllConfigsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where createdBy does not contain DEFAULT_CREATED_BY
        defaultConfigShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the configList where createdBy does not contain UPDATED_CREATED_BY
        defaultConfigShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllConfigsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultConfigShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the configList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultConfigShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllConfigsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultConfigShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the configList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultConfigShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllConfigsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultConfigShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the configList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultConfigShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllConfigsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where lastModifiedDate is not null
        defaultConfigShouldBeFound("lastModifiedDate.specified=true");

        // Get all the configList where lastModifiedDate is null
        defaultConfigShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultConfigShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the configList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultConfigShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllConfigsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultConfigShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the configList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultConfigShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllConfigsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultConfigShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the configList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultConfigShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllConfigsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where lastModifiedBy is not null
        defaultConfigShouldBeFound("lastModifiedBy.specified=true");

        // Get all the configList where lastModifiedBy is null
        defaultConfigShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultConfigShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the configList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultConfigShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllConfigsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultConfigShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the configList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultConfigShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConfigShouldBeFound(String filter) throws Exception {
        restConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(config.getId().intValue())))
            .andExpect(jsonPath("$.[*].configName").value(hasItem(DEFAULT_CONFIG_NAME)))
            .andExpect(jsonPath("$.[*].configValue").value(hasItem(DEFAULT_CONFIG_VALUE)))
            .andExpect(jsonPath("$.[*].configDesc").value(hasItem(DEFAULT_CONFIG_DESC)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restConfigMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConfigShouldNotBeFound(String filter) throws Exception {
        restConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConfigMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConfig() throws Exception {
        // Get the config
        restConfigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConfig() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        int databaseSizeBeforeUpdate = configRepository.findAll().size();

        // Update the config
        Config updatedConfig = configRepository.findById(config.getId()).get();
        // Disconnect from session so that the updates on updatedConfig are not directly saved in db
        em.detach(updatedConfig);
        updatedConfig
            .configName(UPDATED_CONFIG_NAME)
            .configValue(UPDATED_CONFIG_VALUE)
            .configDesc(UPDATED_CONFIG_DESC)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConfig.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConfig))
            )
            .andExpect(status().isOk());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
        Config testConfig = configList.get(configList.size() - 1);
        assertThat(testConfig.getConfigName()).isEqualTo(UPDATED_CONFIG_NAME);
        assertThat(testConfig.getConfigValue()).isEqualTo(UPDATED_CONFIG_VALUE);
        assertThat(testConfig.getConfigDesc()).isEqualTo(UPDATED_CONFIG_DESC);
        assertThat(testConfig.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testConfig.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testConfig.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testConfig.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingConfig() throws Exception {
        int databaseSizeBeforeUpdate = configRepository.findAll().size();
        config.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, config.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(config))
            )
            .andExpect(status().isBadRequest());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConfig() throws Exception {
        int databaseSizeBeforeUpdate = configRepository.findAll().size();
        config.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(config))
            )
            .andExpect(status().isBadRequest());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConfig() throws Exception {
        int databaseSizeBeforeUpdate = configRepository.findAll().size();
        config.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(config)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConfigWithPatch() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        int databaseSizeBeforeUpdate = configRepository.findAll().size();

        // Update the config using partial update
        Config partialUpdatedConfig = new Config();
        partialUpdatedConfig.setId(config.getId());

        partialUpdatedConfig
            .configName(UPDATED_CONFIG_NAME)
            .configValue(UPDATED_CONFIG_VALUE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfig))
            )
            .andExpect(status().isOk());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
        Config testConfig = configList.get(configList.size() - 1);
        assertThat(testConfig.getConfigName()).isEqualTo(UPDATED_CONFIG_NAME);
        assertThat(testConfig.getConfigValue()).isEqualTo(UPDATED_CONFIG_VALUE);
        assertThat(testConfig.getConfigDesc()).isEqualTo(DEFAULT_CONFIG_DESC);
        assertThat(testConfig.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testConfig.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testConfig.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testConfig.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateConfigWithPatch() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        int databaseSizeBeforeUpdate = configRepository.findAll().size();

        // Update the config using partial update
        Config partialUpdatedConfig = new Config();
        partialUpdatedConfig.setId(config.getId());

        partialUpdatedConfig
            .configName(UPDATED_CONFIG_NAME)
            .configValue(UPDATED_CONFIG_VALUE)
            .configDesc(UPDATED_CONFIG_DESC)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfig))
            )
            .andExpect(status().isOk());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
        Config testConfig = configList.get(configList.size() - 1);
        assertThat(testConfig.getConfigName()).isEqualTo(UPDATED_CONFIG_NAME);
        assertThat(testConfig.getConfigValue()).isEqualTo(UPDATED_CONFIG_VALUE);
        assertThat(testConfig.getConfigDesc()).isEqualTo(UPDATED_CONFIG_DESC);
        assertThat(testConfig.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testConfig.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testConfig.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testConfig.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingConfig() throws Exception {
        int databaseSizeBeforeUpdate = configRepository.findAll().size();
        config.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, config.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(config))
            )
            .andExpect(status().isBadRequest());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConfig() throws Exception {
        int databaseSizeBeforeUpdate = configRepository.findAll().size();
        config.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(config))
            )
            .andExpect(status().isBadRequest());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConfig() throws Exception {
        int databaseSizeBeforeUpdate = configRepository.findAll().size();
        config.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(config)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConfig() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        int databaseSizeBeforeDelete = configRepository.findAll().size();

        // Delete the config
        restConfigMockMvc
            .perform(delete(ENTITY_API_URL_ID, config.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
