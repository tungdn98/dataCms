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
import vn.com.datamanager.domain.LoginHistory;
import vn.com.datamanager.repository.LoginHistoryRepository;
import vn.com.datamanager.service.criteria.LoginHistoryCriteria;

/**
 * Integration tests for the {@link LoginHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoginHistoryResourceIT {

    private static final String DEFAULT_EMP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EMP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_EMP_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_EMP_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMP_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMP_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN_IP = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN_IP = "BBBBBBBBBB";

    private static final Instant DEFAULT_LOGIN_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOGIN_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/login-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoginHistoryMockMvc;

    private LoginHistory loginHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoginHistory createEntity(EntityManager em) {
        LoginHistory loginHistory = new LoginHistory()
            .empCode(DEFAULT_EMP_CODE)
            .empUsername(DEFAULT_EMP_USERNAME)
            .empFullName(DEFAULT_EMP_FULL_NAME)
            .loginIp(DEFAULT_LOGIN_IP)
            .loginTime(DEFAULT_LOGIN_TIME);
        return loginHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoginHistory createUpdatedEntity(EntityManager em) {
        LoginHistory loginHistory = new LoginHistory()
            .empCode(UPDATED_EMP_CODE)
            .empUsername(UPDATED_EMP_USERNAME)
            .empFullName(UPDATED_EMP_FULL_NAME)
            .loginIp(UPDATED_LOGIN_IP)
            .loginTime(UPDATED_LOGIN_TIME);
        return loginHistory;
    }

    @BeforeEach
    public void initTest() {
        loginHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createLoginHistory() throws Exception {
        int databaseSizeBeforeCreate = loginHistoryRepository.findAll().size();
        // Create the LoginHistory
        restLoginHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loginHistory)))
            .andExpect(status().isCreated());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        LoginHistory testLoginHistory = loginHistoryList.get(loginHistoryList.size() - 1);
        assertThat(testLoginHistory.getEmpCode()).isEqualTo(DEFAULT_EMP_CODE);
        assertThat(testLoginHistory.getEmpUsername()).isEqualTo(DEFAULT_EMP_USERNAME);
        assertThat(testLoginHistory.getEmpFullName()).isEqualTo(DEFAULT_EMP_FULL_NAME);
        assertThat(testLoginHistory.getLoginIp()).isEqualTo(DEFAULT_LOGIN_IP);
        assertThat(testLoginHistory.getLoginTime()).isEqualTo(DEFAULT_LOGIN_TIME);
    }

    @Test
    @Transactional
    void createLoginHistoryWithExistingId() throws Exception {
        // Create the LoginHistory with an existing ID
        loginHistory.setId(1L);

        int databaseSizeBeforeCreate = loginHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoginHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loginHistory)))
            .andExpect(status().isBadRequest());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoginHistories() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList
        restLoginHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loginHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].empCode").value(hasItem(DEFAULT_EMP_CODE)))
            .andExpect(jsonPath("$.[*].empUsername").value(hasItem(DEFAULT_EMP_USERNAME)))
            .andExpect(jsonPath("$.[*].empFullName").value(hasItem(DEFAULT_EMP_FULL_NAME)))
            .andExpect(jsonPath("$.[*].loginIp").value(hasItem(DEFAULT_LOGIN_IP)))
            .andExpect(jsonPath("$.[*].loginTime").value(hasItem(DEFAULT_LOGIN_TIME.toString())));
    }

    @Test
    @Transactional
    void getLoginHistory() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get the loginHistory
        restLoginHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, loginHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loginHistory.getId().intValue()))
            .andExpect(jsonPath("$.empCode").value(DEFAULT_EMP_CODE))
            .andExpect(jsonPath("$.empUsername").value(DEFAULT_EMP_USERNAME))
            .andExpect(jsonPath("$.empFullName").value(DEFAULT_EMP_FULL_NAME))
            .andExpect(jsonPath("$.loginIp").value(DEFAULT_LOGIN_IP))
            .andExpect(jsonPath("$.loginTime").value(DEFAULT_LOGIN_TIME.toString()));
    }

    @Test
    @Transactional
    void getLoginHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        Long id = loginHistory.getId();

        defaultLoginHistoryShouldBeFound("id.equals=" + id);
        defaultLoginHistoryShouldNotBeFound("id.notEquals=" + id);

        defaultLoginHistoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLoginHistoryShouldNotBeFound("id.greaterThan=" + id);

        defaultLoginHistoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLoginHistoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empCode equals to DEFAULT_EMP_CODE
        defaultLoginHistoryShouldBeFound("empCode.equals=" + DEFAULT_EMP_CODE);

        // Get all the loginHistoryList where empCode equals to UPDATED_EMP_CODE
        defaultLoginHistoryShouldNotBeFound("empCode.equals=" + UPDATED_EMP_CODE);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empCode not equals to DEFAULT_EMP_CODE
        defaultLoginHistoryShouldNotBeFound("empCode.notEquals=" + DEFAULT_EMP_CODE);

        // Get all the loginHistoryList where empCode not equals to UPDATED_EMP_CODE
        defaultLoginHistoryShouldBeFound("empCode.notEquals=" + UPDATED_EMP_CODE);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpCodeIsInShouldWork() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empCode in DEFAULT_EMP_CODE or UPDATED_EMP_CODE
        defaultLoginHistoryShouldBeFound("empCode.in=" + DEFAULT_EMP_CODE + "," + UPDATED_EMP_CODE);

        // Get all the loginHistoryList where empCode equals to UPDATED_EMP_CODE
        defaultLoginHistoryShouldNotBeFound("empCode.in=" + UPDATED_EMP_CODE);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empCode is not null
        defaultLoginHistoryShouldBeFound("empCode.specified=true");

        // Get all the loginHistoryList where empCode is null
        defaultLoginHistoryShouldNotBeFound("empCode.specified=false");
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpCodeContainsSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empCode contains DEFAULT_EMP_CODE
        defaultLoginHistoryShouldBeFound("empCode.contains=" + DEFAULT_EMP_CODE);

        // Get all the loginHistoryList where empCode contains UPDATED_EMP_CODE
        defaultLoginHistoryShouldNotBeFound("empCode.contains=" + UPDATED_EMP_CODE);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpCodeNotContainsSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empCode does not contain DEFAULT_EMP_CODE
        defaultLoginHistoryShouldNotBeFound("empCode.doesNotContain=" + DEFAULT_EMP_CODE);

        // Get all the loginHistoryList where empCode does not contain UPDATED_EMP_CODE
        defaultLoginHistoryShouldBeFound("empCode.doesNotContain=" + UPDATED_EMP_CODE);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empUsername equals to DEFAULT_EMP_USERNAME
        defaultLoginHistoryShouldBeFound("empUsername.equals=" + DEFAULT_EMP_USERNAME);

        // Get all the loginHistoryList where empUsername equals to UPDATED_EMP_USERNAME
        defaultLoginHistoryShouldNotBeFound("empUsername.equals=" + UPDATED_EMP_USERNAME);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpUsernameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empUsername not equals to DEFAULT_EMP_USERNAME
        defaultLoginHistoryShouldNotBeFound("empUsername.notEquals=" + DEFAULT_EMP_USERNAME);

        // Get all the loginHistoryList where empUsername not equals to UPDATED_EMP_USERNAME
        defaultLoginHistoryShouldBeFound("empUsername.notEquals=" + UPDATED_EMP_USERNAME);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empUsername in DEFAULT_EMP_USERNAME or UPDATED_EMP_USERNAME
        defaultLoginHistoryShouldBeFound("empUsername.in=" + DEFAULT_EMP_USERNAME + "," + UPDATED_EMP_USERNAME);

        // Get all the loginHistoryList where empUsername equals to UPDATED_EMP_USERNAME
        defaultLoginHistoryShouldNotBeFound("empUsername.in=" + UPDATED_EMP_USERNAME);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empUsername is not null
        defaultLoginHistoryShouldBeFound("empUsername.specified=true");

        // Get all the loginHistoryList where empUsername is null
        defaultLoginHistoryShouldNotBeFound("empUsername.specified=false");
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpUsernameContainsSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empUsername contains DEFAULT_EMP_USERNAME
        defaultLoginHistoryShouldBeFound("empUsername.contains=" + DEFAULT_EMP_USERNAME);

        // Get all the loginHistoryList where empUsername contains UPDATED_EMP_USERNAME
        defaultLoginHistoryShouldNotBeFound("empUsername.contains=" + UPDATED_EMP_USERNAME);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpUsernameNotContainsSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empUsername does not contain DEFAULT_EMP_USERNAME
        defaultLoginHistoryShouldNotBeFound("empUsername.doesNotContain=" + DEFAULT_EMP_USERNAME);

        // Get all the loginHistoryList where empUsername does not contain UPDATED_EMP_USERNAME
        defaultLoginHistoryShouldBeFound("empUsername.doesNotContain=" + UPDATED_EMP_USERNAME);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empFullName equals to DEFAULT_EMP_FULL_NAME
        defaultLoginHistoryShouldBeFound("empFullName.equals=" + DEFAULT_EMP_FULL_NAME);

        // Get all the loginHistoryList where empFullName equals to UPDATED_EMP_FULL_NAME
        defaultLoginHistoryShouldNotBeFound("empFullName.equals=" + UPDATED_EMP_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpFullNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empFullName not equals to DEFAULT_EMP_FULL_NAME
        defaultLoginHistoryShouldNotBeFound("empFullName.notEquals=" + DEFAULT_EMP_FULL_NAME);

        // Get all the loginHistoryList where empFullName not equals to UPDATED_EMP_FULL_NAME
        defaultLoginHistoryShouldBeFound("empFullName.notEquals=" + UPDATED_EMP_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empFullName in DEFAULT_EMP_FULL_NAME or UPDATED_EMP_FULL_NAME
        defaultLoginHistoryShouldBeFound("empFullName.in=" + DEFAULT_EMP_FULL_NAME + "," + UPDATED_EMP_FULL_NAME);

        // Get all the loginHistoryList where empFullName equals to UPDATED_EMP_FULL_NAME
        defaultLoginHistoryShouldNotBeFound("empFullName.in=" + UPDATED_EMP_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empFullName is not null
        defaultLoginHistoryShouldBeFound("empFullName.specified=true");

        // Get all the loginHistoryList where empFullName is null
        defaultLoginHistoryShouldNotBeFound("empFullName.specified=false");
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpFullNameContainsSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empFullName contains DEFAULT_EMP_FULL_NAME
        defaultLoginHistoryShouldBeFound("empFullName.contains=" + DEFAULT_EMP_FULL_NAME);

        // Get all the loginHistoryList where empFullName contains UPDATED_EMP_FULL_NAME
        defaultLoginHistoryShouldNotBeFound("empFullName.contains=" + UPDATED_EMP_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByEmpFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where empFullName does not contain DEFAULT_EMP_FULL_NAME
        defaultLoginHistoryShouldNotBeFound("empFullName.doesNotContain=" + DEFAULT_EMP_FULL_NAME);

        // Get all the loginHistoryList where empFullName does not contain UPDATED_EMP_FULL_NAME
        defaultLoginHistoryShouldBeFound("empFullName.doesNotContain=" + UPDATED_EMP_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByLoginIpIsEqualToSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where loginIp equals to DEFAULT_LOGIN_IP
        defaultLoginHistoryShouldBeFound("loginIp.equals=" + DEFAULT_LOGIN_IP);

        // Get all the loginHistoryList where loginIp equals to UPDATED_LOGIN_IP
        defaultLoginHistoryShouldNotBeFound("loginIp.equals=" + UPDATED_LOGIN_IP);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByLoginIpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where loginIp not equals to DEFAULT_LOGIN_IP
        defaultLoginHistoryShouldNotBeFound("loginIp.notEquals=" + DEFAULT_LOGIN_IP);

        // Get all the loginHistoryList where loginIp not equals to UPDATED_LOGIN_IP
        defaultLoginHistoryShouldBeFound("loginIp.notEquals=" + UPDATED_LOGIN_IP);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByLoginIpIsInShouldWork() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where loginIp in DEFAULT_LOGIN_IP or UPDATED_LOGIN_IP
        defaultLoginHistoryShouldBeFound("loginIp.in=" + DEFAULT_LOGIN_IP + "," + UPDATED_LOGIN_IP);

        // Get all the loginHistoryList where loginIp equals to UPDATED_LOGIN_IP
        defaultLoginHistoryShouldNotBeFound("loginIp.in=" + UPDATED_LOGIN_IP);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByLoginIpIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where loginIp is not null
        defaultLoginHistoryShouldBeFound("loginIp.specified=true");

        // Get all the loginHistoryList where loginIp is null
        defaultLoginHistoryShouldNotBeFound("loginIp.specified=false");
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByLoginIpContainsSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where loginIp contains DEFAULT_LOGIN_IP
        defaultLoginHistoryShouldBeFound("loginIp.contains=" + DEFAULT_LOGIN_IP);

        // Get all the loginHistoryList where loginIp contains UPDATED_LOGIN_IP
        defaultLoginHistoryShouldNotBeFound("loginIp.contains=" + UPDATED_LOGIN_IP);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByLoginIpNotContainsSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where loginIp does not contain DEFAULT_LOGIN_IP
        defaultLoginHistoryShouldNotBeFound("loginIp.doesNotContain=" + DEFAULT_LOGIN_IP);

        // Get all the loginHistoryList where loginIp does not contain UPDATED_LOGIN_IP
        defaultLoginHistoryShouldBeFound("loginIp.doesNotContain=" + UPDATED_LOGIN_IP);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByLoginTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where loginTime equals to DEFAULT_LOGIN_TIME
        defaultLoginHistoryShouldBeFound("loginTime.equals=" + DEFAULT_LOGIN_TIME);

        // Get all the loginHistoryList where loginTime equals to UPDATED_LOGIN_TIME
        defaultLoginHistoryShouldNotBeFound("loginTime.equals=" + UPDATED_LOGIN_TIME);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByLoginTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where loginTime not equals to DEFAULT_LOGIN_TIME
        defaultLoginHistoryShouldNotBeFound("loginTime.notEquals=" + DEFAULT_LOGIN_TIME);

        // Get all the loginHistoryList where loginTime not equals to UPDATED_LOGIN_TIME
        defaultLoginHistoryShouldBeFound("loginTime.notEquals=" + UPDATED_LOGIN_TIME);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByLoginTimeIsInShouldWork() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where loginTime in DEFAULT_LOGIN_TIME or UPDATED_LOGIN_TIME
        defaultLoginHistoryShouldBeFound("loginTime.in=" + DEFAULT_LOGIN_TIME + "," + UPDATED_LOGIN_TIME);

        // Get all the loginHistoryList where loginTime equals to UPDATED_LOGIN_TIME
        defaultLoginHistoryShouldNotBeFound("loginTime.in=" + UPDATED_LOGIN_TIME);
    }

    @Test
    @Transactional
    void getAllLoginHistoriesByLoginTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList where loginTime is not null
        defaultLoginHistoryShouldBeFound("loginTime.specified=true");

        // Get all the loginHistoryList where loginTime is null
        defaultLoginHistoryShouldNotBeFound("loginTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLoginHistoryShouldBeFound(String filter) throws Exception {
        restLoginHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loginHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].empCode").value(hasItem(DEFAULT_EMP_CODE)))
            .andExpect(jsonPath("$.[*].empUsername").value(hasItem(DEFAULT_EMP_USERNAME)))
            .andExpect(jsonPath("$.[*].empFullName").value(hasItem(DEFAULT_EMP_FULL_NAME)))
            .andExpect(jsonPath("$.[*].loginIp").value(hasItem(DEFAULT_LOGIN_IP)))
            .andExpect(jsonPath("$.[*].loginTime").value(hasItem(DEFAULT_LOGIN_TIME.toString())));

        // Check, that the count call also returns 1
        restLoginHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLoginHistoryShouldNotBeFound(String filter) throws Exception {
        restLoginHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLoginHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLoginHistory() throws Exception {
        // Get the loginHistory
        restLoginHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLoginHistory() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();

        // Update the loginHistory
        LoginHistory updatedLoginHistory = loginHistoryRepository.findById(loginHistory.getId()).get();
        // Disconnect from session so that the updates on updatedLoginHistory are not directly saved in db
        em.detach(updatedLoginHistory);
        updatedLoginHistory
            .empCode(UPDATED_EMP_CODE)
            .empUsername(UPDATED_EMP_USERNAME)
            .empFullName(UPDATED_EMP_FULL_NAME)
            .loginIp(UPDATED_LOGIN_IP)
            .loginTime(UPDATED_LOGIN_TIME);

        restLoginHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLoginHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLoginHistory))
            )
            .andExpect(status().isOk());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
        LoginHistory testLoginHistory = loginHistoryList.get(loginHistoryList.size() - 1);
        assertThat(testLoginHistory.getEmpCode()).isEqualTo(UPDATED_EMP_CODE);
        assertThat(testLoginHistory.getEmpUsername()).isEqualTo(UPDATED_EMP_USERNAME);
        assertThat(testLoginHistory.getEmpFullName()).isEqualTo(UPDATED_EMP_FULL_NAME);
        assertThat(testLoginHistory.getLoginIp()).isEqualTo(UPDATED_LOGIN_IP);
        assertThat(testLoginHistory.getLoginTime()).isEqualTo(UPDATED_LOGIN_TIME);
    }

    @Test
    @Transactional
    void putNonExistingLoginHistory() throws Exception {
        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();
        loginHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoginHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loginHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loginHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoginHistory() throws Exception {
        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();
        loginHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loginHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoginHistory() throws Exception {
        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();
        loginHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loginHistory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoginHistoryWithPatch() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();

        // Update the loginHistory using partial update
        LoginHistory partialUpdatedLoginHistory = new LoginHistory();
        partialUpdatedLoginHistory.setId(loginHistory.getId());

        partialUpdatedLoginHistory
            .empCode(UPDATED_EMP_CODE)
            .empUsername(UPDATED_EMP_USERNAME)
            .empFullName(UPDATED_EMP_FULL_NAME)
            .loginIp(UPDATED_LOGIN_IP);

        restLoginHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoginHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoginHistory))
            )
            .andExpect(status().isOk());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
        LoginHistory testLoginHistory = loginHistoryList.get(loginHistoryList.size() - 1);
        assertThat(testLoginHistory.getEmpCode()).isEqualTo(UPDATED_EMP_CODE);
        assertThat(testLoginHistory.getEmpUsername()).isEqualTo(UPDATED_EMP_USERNAME);
        assertThat(testLoginHistory.getEmpFullName()).isEqualTo(UPDATED_EMP_FULL_NAME);
        assertThat(testLoginHistory.getLoginIp()).isEqualTo(UPDATED_LOGIN_IP);
        assertThat(testLoginHistory.getLoginTime()).isEqualTo(DEFAULT_LOGIN_TIME);
    }

    @Test
    @Transactional
    void fullUpdateLoginHistoryWithPatch() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();

        // Update the loginHistory using partial update
        LoginHistory partialUpdatedLoginHistory = new LoginHistory();
        partialUpdatedLoginHistory.setId(loginHistory.getId());

        partialUpdatedLoginHistory
            .empCode(UPDATED_EMP_CODE)
            .empUsername(UPDATED_EMP_USERNAME)
            .empFullName(UPDATED_EMP_FULL_NAME)
            .loginIp(UPDATED_LOGIN_IP)
            .loginTime(UPDATED_LOGIN_TIME);

        restLoginHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoginHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoginHistory))
            )
            .andExpect(status().isOk());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
        LoginHistory testLoginHistory = loginHistoryList.get(loginHistoryList.size() - 1);
        assertThat(testLoginHistory.getEmpCode()).isEqualTo(UPDATED_EMP_CODE);
        assertThat(testLoginHistory.getEmpUsername()).isEqualTo(UPDATED_EMP_USERNAME);
        assertThat(testLoginHistory.getEmpFullName()).isEqualTo(UPDATED_EMP_FULL_NAME);
        assertThat(testLoginHistory.getLoginIp()).isEqualTo(UPDATED_LOGIN_IP);
        assertThat(testLoginHistory.getLoginTime()).isEqualTo(UPDATED_LOGIN_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingLoginHistory() throws Exception {
        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();
        loginHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoginHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loginHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loginHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoginHistory() throws Exception {
        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();
        loginHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loginHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoginHistory() throws Exception {
        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();
        loginHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(loginHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoginHistory() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        int databaseSizeBeforeDelete = loginHistoryRepository.findAll().size();

        // Delete the loginHistory
        restLoginHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, loginHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
