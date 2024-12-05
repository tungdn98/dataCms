package vn.com.datamanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.com.datamanager.web.rest.TestUtil.sameNumber;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
import vn.com.datamanager.domain.Activity;
import vn.com.datamanager.repository.ActivityRepository;
import vn.com.datamanager.service.criteria.ActivityCriteria;

/**
 * Integration tests for the {@link ActivityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActivityResourceIT {

    private static final String DEFAULT_ACTIVITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_ID = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_DEADLINE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEADLINE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DEADLINE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITY_TYPE_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_TYPE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECT_TYPE_ID = "AAAAAAAAAA";
    private static final String UPDATED_OBJECT_TYPE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRIORITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OPPORTUNITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_OPPORTUNITY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRIORITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSIBLE_ID = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSIBLE_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CLOSED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLOSED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;
    private static final Integer SMALLER_DURATION = 1 - 1;

    private static final String DEFAULT_DURATION_UNIT_ID = "AAAAAAAAAA";
    private static final String UPDATED_DURATION_UNIT_ID = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CONVERSION = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONVERSION = new BigDecimal(2);
    private static final BigDecimal SMALLER_CONVERSION = new BigDecimal(1 - 1);

    private static final String DEFAULT_TEXT_STR = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_STR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/activities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivityMockMvc;

    private Activity activity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activity createEntity(EntityManager em) {
        Activity activity = new Activity()
            .activityId(DEFAULT_ACTIVITY_ID)
            .companyId(DEFAULT_COMPANY_ID)
            .createDate(DEFAULT_CREATE_DATE)
            .deadline(DEFAULT_DEADLINE)
            .name(DEFAULT_NAME)
            .state(DEFAULT_STATE)
            .type(DEFAULT_TYPE)
            .accountId(DEFAULT_ACCOUNT_ID)
            .activityTypeId(DEFAULT_ACTIVITY_TYPE_ID)
            .objectTypeId(DEFAULT_OBJECT_TYPE_ID)
            .priorityId(DEFAULT_PRIORITY_ID)
            .opportunityId(DEFAULT_OPPORTUNITY_ID)
            .orderId(DEFAULT_ORDER_ID)
            .contractId(DEFAULT_CONTRACT_ID)
            .priorityName(DEFAULT_PRIORITY_NAME)
            .responsibleId(DEFAULT_RESPONSIBLE_ID)
            .startDate(DEFAULT_START_DATE)
            .closedOn(DEFAULT_CLOSED_ON)
            .duration(DEFAULT_DURATION)
            .durationUnitId(DEFAULT_DURATION_UNIT_ID)
            .conversion(DEFAULT_CONVERSION)
            .textStr(DEFAULT_TEXT_STR);
        return activity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activity createUpdatedEntity(EntityManager em) {
        Activity activity = new Activity()
            .activityId(UPDATED_ACTIVITY_ID)
            .companyId(UPDATED_COMPANY_ID)
            .createDate(UPDATED_CREATE_DATE)
            .deadline(UPDATED_DEADLINE)
            .name(UPDATED_NAME)
            .state(UPDATED_STATE)
            .type(UPDATED_TYPE)
            .accountId(UPDATED_ACCOUNT_ID)
            .activityTypeId(UPDATED_ACTIVITY_TYPE_ID)
            .objectTypeId(UPDATED_OBJECT_TYPE_ID)
            .priorityId(UPDATED_PRIORITY_ID)
            .opportunityId(UPDATED_OPPORTUNITY_ID)
            .orderId(UPDATED_ORDER_ID)
            .contractId(UPDATED_CONTRACT_ID)
            .priorityName(UPDATED_PRIORITY_NAME)
            .responsibleId(UPDATED_RESPONSIBLE_ID)
            .startDate(UPDATED_START_DATE)
            .closedOn(UPDATED_CLOSED_ON)
            .duration(UPDATED_DURATION)
            .durationUnitId(UPDATED_DURATION_UNIT_ID)
            .conversion(UPDATED_CONVERSION)
            .textStr(UPDATED_TEXT_STR);
        return activity;
    }

    @BeforeEach
    public void initTest() {
        activity = createEntity(em);
    }

    @Test
    @Transactional
    void createActivity() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();
        // Create the Activity
        restActivityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate + 1);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getActivityId()).isEqualTo(DEFAULT_ACTIVITY_ID);
        assertThat(testActivity.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testActivity.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testActivity.getDeadline()).isEqualTo(DEFAULT_DEADLINE);
        assertThat(testActivity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testActivity.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testActivity.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testActivity.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testActivity.getActivityTypeId()).isEqualTo(DEFAULT_ACTIVITY_TYPE_ID);
        assertThat(testActivity.getObjectTypeId()).isEqualTo(DEFAULT_OBJECT_TYPE_ID);
        assertThat(testActivity.getPriorityId()).isEqualTo(DEFAULT_PRIORITY_ID);
        assertThat(testActivity.getOpportunityId()).isEqualTo(DEFAULT_OPPORTUNITY_ID);
        assertThat(testActivity.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testActivity.getContractId()).isEqualTo(DEFAULT_CONTRACT_ID);
        assertThat(testActivity.getPriorityName()).isEqualTo(DEFAULT_PRIORITY_NAME);
        assertThat(testActivity.getResponsibleId()).isEqualTo(DEFAULT_RESPONSIBLE_ID);
        assertThat(testActivity.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testActivity.getClosedOn()).isEqualTo(DEFAULT_CLOSED_ON);
        assertThat(testActivity.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testActivity.getDurationUnitId()).isEqualTo(DEFAULT_DURATION_UNIT_ID);
        assertThat(testActivity.getConversion()).isEqualByComparingTo(DEFAULT_CONVERSION);
        assertThat(testActivity.getTextStr()).isEqualTo(DEFAULT_TEXT_STR);
    }

    @Test
    @Transactional
    void createActivityWithExistingId() throws Exception {
        // Create the Activity with an existing ID
        activity.setId(1L);

        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActivities() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList
        restActivityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activity.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityId").value(hasItem(DEFAULT_ACTIVITY_ID)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].deadline").value(hasItem(DEFAULT_DEADLINE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].activityTypeId").value(hasItem(DEFAULT_ACTIVITY_TYPE_ID)))
            .andExpect(jsonPath("$.[*].objectTypeId").value(hasItem(DEFAULT_OBJECT_TYPE_ID)))
            .andExpect(jsonPath("$.[*].priorityId").value(hasItem(DEFAULT_PRIORITY_ID)))
            .andExpect(jsonPath("$.[*].opportunityId").value(hasItem(DEFAULT_OPPORTUNITY_ID)))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].contractId").value(hasItem(DEFAULT_CONTRACT_ID)))
            .andExpect(jsonPath("$.[*].priorityName").value(hasItem(DEFAULT_PRIORITY_NAME)))
            .andExpect(jsonPath("$.[*].responsibleId").value(hasItem(DEFAULT_RESPONSIBLE_ID)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].closedOn").value(hasItem(DEFAULT_CLOSED_ON.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].durationUnitId").value(hasItem(DEFAULT_DURATION_UNIT_ID)))
            .andExpect(jsonPath("$.[*].conversion").value(hasItem(sameNumber(DEFAULT_CONVERSION))))
            .andExpect(jsonPath("$.[*].textStr").value(hasItem(DEFAULT_TEXT_STR)));
    }

    @Test
    @Transactional
    void getActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get the activity
        restActivityMockMvc
            .perform(get(ENTITY_API_URL_ID, activity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activity.getId().intValue()))
            .andExpect(jsonPath("$.activityId").value(DEFAULT_ACTIVITY_ID))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.deadline").value(DEFAULT_DEADLINE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.activityTypeId").value(DEFAULT_ACTIVITY_TYPE_ID))
            .andExpect(jsonPath("$.objectTypeId").value(DEFAULT_OBJECT_TYPE_ID))
            .andExpect(jsonPath("$.priorityId").value(DEFAULT_PRIORITY_ID))
            .andExpect(jsonPath("$.opportunityId").value(DEFAULT_OPPORTUNITY_ID))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.contractId").value(DEFAULT_CONTRACT_ID))
            .andExpect(jsonPath("$.priorityName").value(DEFAULT_PRIORITY_NAME))
            .andExpect(jsonPath("$.responsibleId").value(DEFAULT_RESPONSIBLE_ID))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.closedOn").value(DEFAULT_CLOSED_ON.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.durationUnitId").value(DEFAULT_DURATION_UNIT_ID))
            .andExpect(jsonPath("$.conversion").value(sameNumber(DEFAULT_CONVERSION)))
            .andExpect(jsonPath("$.textStr").value(DEFAULT_TEXT_STR));
    }

    @Test
    @Transactional
    void getActivitiesByIdFiltering() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        Long id = activity.getId();

        defaultActivityShouldBeFound("id.equals=" + id);
        defaultActivityShouldNotBeFound("id.notEquals=" + id);

        defaultActivityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultActivityShouldNotBeFound("id.greaterThan=" + id);

        defaultActivityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultActivityShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllActivitiesByActivityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityId equals to DEFAULT_ACTIVITY_ID
        defaultActivityShouldBeFound("activityId.equals=" + DEFAULT_ACTIVITY_ID);

        // Get all the activityList where activityId equals to UPDATED_ACTIVITY_ID
        defaultActivityShouldNotBeFound("activityId.equals=" + UPDATED_ACTIVITY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByActivityIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityId not equals to DEFAULT_ACTIVITY_ID
        defaultActivityShouldNotBeFound("activityId.notEquals=" + DEFAULT_ACTIVITY_ID);

        // Get all the activityList where activityId not equals to UPDATED_ACTIVITY_ID
        defaultActivityShouldBeFound("activityId.notEquals=" + UPDATED_ACTIVITY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByActivityIdIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityId in DEFAULT_ACTIVITY_ID or UPDATED_ACTIVITY_ID
        defaultActivityShouldBeFound("activityId.in=" + DEFAULT_ACTIVITY_ID + "," + UPDATED_ACTIVITY_ID);

        // Get all the activityList where activityId equals to UPDATED_ACTIVITY_ID
        defaultActivityShouldNotBeFound("activityId.in=" + UPDATED_ACTIVITY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByActivityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityId is not null
        defaultActivityShouldBeFound("activityId.specified=true");

        // Get all the activityList where activityId is null
        defaultActivityShouldNotBeFound("activityId.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByActivityIdContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityId contains DEFAULT_ACTIVITY_ID
        defaultActivityShouldBeFound("activityId.contains=" + DEFAULT_ACTIVITY_ID);

        // Get all the activityList where activityId contains UPDATED_ACTIVITY_ID
        defaultActivityShouldNotBeFound("activityId.contains=" + UPDATED_ACTIVITY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByActivityIdNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityId does not contain DEFAULT_ACTIVITY_ID
        defaultActivityShouldNotBeFound("activityId.doesNotContain=" + DEFAULT_ACTIVITY_ID);

        // Get all the activityList where activityId does not contain UPDATED_ACTIVITY_ID
        defaultActivityShouldBeFound("activityId.doesNotContain=" + UPDATED_ACTIVITY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where companyId equals to DEFAULT_COMPANY_ID
        defaultActivityShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the activityList where companyId equals to UPDATED_COMPANY_ID
        defaultActivityShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByCompanyIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where companyId not equals to DEFAULT_COMPANY_ID
        defaultActivityShouldNotBeFound("companyId.notEquals=" + DEFAULT_COMPANY_ID);

        // Get all the activityList where companyId not equals to UPDATED_COMPANY_ID
        defaultActivityShouldBeFound("companyId.notEquals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultActivityShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the activityList where companyId equals to UPDATED_COMPANY_ID
        defaultActivityShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where companyId is not null
        defaultActivityShouldBeFound("companyId.specified=true");

        // Get all the activityList where companyId is null
        defaultActivityShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByCompanyIdContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where companyId contains DEFAULT_COMPANY_ID
        defaultActivityShouldBeFound("companyId.contains=" + DEFAULT_COMPANY_ID);

        // Get all the activityList where companyId contains UPDATED_COMPANY_ID
        defaultActivityShouldNotBeFound("companyId.contains=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByCompanyIdNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where companyId does not contain DEFAULT_COMPANY_ID
        defaultActivityShouldNotBeFound("companyId.doesNotContain=" + DEFAULT_COMPANY_ID);

        // Get all the activityList where companyId does not contain UPDATED_COMPANY_ID
        defaultActivityShouldBeFound("companyId.doesNotContain=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByCreateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where createDate equals to DEFAULT_CREATE_DATE
        defaultActivityShouldBeFound("createDate.equals=" + DEFAULT_CREATE_DATE);

        // Get all the activityList where createDate equals to UPDATED_CREATE_DATE
        defaultActivityShouldNotBeFound("createDate.equals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    void getAllActivitiesByCreateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where createDate not equals to DEFAULT_CREATE_DATE
        defaultActivityShouldNotBeFound("createDate.notEquals=" + DEFAULT_CREATE_DATE);

        // Get all the activityList where createDate not equals to UPDATED_CREATE_DATE
        defaultActivityShouldBeFound("createDate.notEquals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    void getAllActivitiesByCreateDateIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where createDate in DEFAULT_CREATE_DATE or UPDATED_CREATE_DATE
        defaultActivityShouldBeFound("createDate.in=" + DEFAULT_CREATE_DATE + "," + UPDATED_CREATE_DATE);

        // Get all the activityList where createDate equals to UPDATED_CREATE_DATE
        defaultActivityShouldNotBeFound("createDate.in=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    void getAllActivitiesByCreateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where createDate is not null
        defaultActivityShouldBeFound("createDate.specified=true");

        // Get all the activityList where createDate is null
        defaultActivityShouldNotBeFound("createDate.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByDeadlineIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where deadline equals to DEFAULT_DEADLINE
        defaultActivityShouldBeFound("deadline.equals=" + DEFAULT_DEADLINE);

        // Get all the activityList where deadline equals to UPDATED_DEADLINE
        defaultActivityShouldNotBeFound("deadline.equals=" + UPDATED_DEADLINE);
    }

    @Test
    @Transactional
    void getAllActivitiesByDeadlineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where deadline not equals to DEFAULT_DEADLINE
        defaultActivityShouldNotBeFound("deadline.notEquals=" + DEFAULT_DEADLINE);

        // Get all the activityList where deadline not equals to UPDATED_DEADLINE
        defaultActivityShouldBeFound("deadline.notEquals=" + UPDATED_DEADLINE);
    }

    @Test
    @Transactional
    void getAllActivitiesByDeadlineIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where deadline in DEFAULT_DEADLINE or UPDATED_DEADLINE
        defaultActivityShouldBeFound("deadline.in=" + DEFAULT_DEADLINE + "," + UPDATED_DEADLINE);

        // Get all the activityList where deadline equals to UPDATED_DEADLINE
        defaultActivityShouldNotBeFound("deadline.in=" + UPDATED_DEADLINE);
    }

    @Test
    @Transactional
    void getAllActivitiesByDeadlineIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where deadline is not null
        defaultActivityShouldBeFound("deadline.specified=true");

        // Get all the activityList where deadline is null
        defaultActivityShouldNotBeFound("deadline.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByDeadlineIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where deadline is greater than or equal to DEFAULT_DEADLINE
        defaultActivityShouldBeFound("deadline.greaterThanOrEqual=" + DEFAULT_DEADLINE);

        // Get all the activityList where deadline is greater than or equal to UPDATED_DEADLINE
        defaultActivityShouldNotBeFound("deadline.greaterThanOrEqual=" + UPDATED_DEADLINE);
    }

    @Test
    @Transactional
    void getAllActivitiesByDeadlineIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where deadline is less than or equal to DEFAULT_DEADLINE
        defaultActivityShouldBeFound("deadline.lessThanOrEqual=" + DEFAULT_DEADLINE);

        // Get all the activityList where deadline is less than or equal to SMALLER_DEADLINE
        defaultActivityShouldNotBeFound("deadline.lessThanOrEqual=" + SMALLER_DEADLINE);
    }

    @Test
    @Transactional
    void getAllActivitiesByDeadlineIsLessThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where deadline is less than DEFAULT_DEADLINE
        defaultActivityShouldNotBeFound("deadline.lessThan=" + DEFAULT_DEADLINE);

        // Get all the activityList where deadline is less than UPDATED_DEADLINE
        defaultActivityShouldBeFound("deadline.lessThan=" + UPDATED_DEADLINE);
    }

    @Test
    @Transactional
    void getAllActivitiesByDeadlineIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where deadline is greater than DEFAULT_DEADLINE
        defaultActivityShouldNotBeFound("deadline.greaterThan=" + DEFAULT_DEADLINE);

        // Get all the activityList where deadline is greater than SMALLER_DEADLINE
        defaultActivityShouldBeFound("deadline.greaterThan=" + SMALLER_DEADLINE);
    }

    @Test
    @Transactional
    void getAllActivitiesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where name equals to DEFAULT_NAME
        defaultActivityShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the activityList where name equals to UPDATED_NAME
        defaultActivityShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllActivitiesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where name not equals to DEFAULT_NAME
        defaultActivityShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the activityList where name not equals to UPDATED_NAME
        defaultActivityShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllActivitiesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where name in DEFAULT_NAME or UPDATED_NAME
        defaultActivityShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the activityList where name equals to UPDATED_NAME
        defaultActivityShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllActivitiesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where name is not null
        defaultActivityShouldBeFound("name.specified=true");

        // Get all the activityList where name is null
        defaultActivityShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByNameContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where name contains DEFAULT_NAME
        defaultActivityShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the activityList where name contains UPDATED_NAME
        defaultActivityShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllActivitiesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where name does not contain DEFAULT_NAME
        defaultActivityShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the activityList where name does not contain UPDATED_NAME
        defaultActivityShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllActivitiesByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where state equals to DEFAULT_STATE
        defaultActivityShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the activityList where state equals to UPDATED_STATE
        defaultActivityShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllActivitiesByStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where state not equals to DEFAULT_STATE
        defaultActivityShouldNotBeFound("state.notEquals=" + DEFAULT_STATE);

        // Get all the activityList where state not equals to UPDATED_STATE
        defaultActivityShouldBeFound("state.notEquals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllActivitiesByStateIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where state in DEFAULT_STATE or UPDATED_STATE
        defaultActivityShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the activityList where state equals to UPDATED_STATE
        defaultActivityShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllActivitiesByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where state is not null
        defaultActivityShouldBeFound("state.specified=true");

        // Get all the activityList where state is null
        defaultActivityShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByStateContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where state contains DEFAULT_STATE
        defaultActivityShouldBeFound("state.contains=" + DEFAULT_STATE);

        // Get all the activityList where state contains UPDATED_STATE
        defaultActivityShouldNotBeFound("state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllActivitiesByStateNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where state does not contain DEFAULT_STATE
        defaultActivityShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

        // Get all the activityList where state does not contain UPDATED_STATE
        defaultActivityShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllActivitiesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where type equals to DEFAULT_TYPE
        defaultActivityShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the activityList where type equals to UPDATED_TYPE
        defaultActivityShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllActivitiesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where type not equals to DEFAULT_TYPE
        defaultActivityShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the activityList where type not equals to UPDATED_TYPE
        defaultActivityShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllActivitiesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultActivityShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the activityList where type equals to UPDATED_TYPE
        defaultActivityShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllActivitiesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where type is not null
        defaultActivityShouldBeFound("type.specified=true");

        // Get all the activityList where type is null
        defaultActivityShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByTypeContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where type contains DEFAULT_TYPE
        defaultActivityShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the activityList where type contains UPDATED_TYPE
        defaultActivityShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllActivitiesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where type does not contain DEFAULT_TYPE
        defaultActivityShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the activityList where type does not contain UPDATED_TYPE
        defaultActivityShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllActivitiesByAccountIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where accountId equals to DEFAULT_ACCOUNT_ID
        defaultActivityShouldBeFound("accountId.equals=" + DEFAULT_ACCOUNT_ID);

        // Get all the activityList where accountId equals to UPDATED_ACCOUNT_ID
        defaultActivityShouldNotBeFound("accountId.equals=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByAccountIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where accountId not equals to DEFAULT_ACCOUNT_ID
        defaultActivityShouldNotBeFound("accountId.notEquals=" + DEFAULT_ACCOUNT_ID);

        // Get all the activityList where accountId not equals to UPDATED_ACCOUNT_ID
        defaultActivityShouldBeFound("accountId.notEquals=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByAccountIdIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where accountId in DEFAULT_ACCOUNT_ID or UPDATED_ACCOUNT_ID
        defaultActivityShouldBeFound("accountId.in=" + DEFAULT_ACCOUNT_ID + "," + UPDATED_ACCOUNT_ID);

        // Get all the activityList where accountId equals to UPDATED_ACCOUNT_ID
        defaultActivityShouldNotBeFound("accountId.in=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByAccountIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where accountId is not null
        defaultActivityShouldBeFound("accountId.specified=true");

        // Get all the activityList where accountId is null
        defaultActivityShouldNotBeFound("accountId.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByAccountIdContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where accountId contains DEFAULT_ACCOUNT_ID
        defaultActivityShouldBeFound("accountId.contains=" + DEFAULT_ACCOUNT_ID);

        // Get all the activityList where accountId contains UPDATED_ACCOUNT_ID
        defaultActivityShouldNotBeFound("accountId.contains=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByAccountIdNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where accountId does not contain DEFAULT_ACCOUNT_ID
        defaultActivityShouldNotBeFound("accountId.doesNotContain=" + DEFAULT_ACCOUNT_ID);

        // Get all the activityList where accountId does not contain UPDATED_ACCOUNT_ID
        defaultActivityShouldBeFound("accountId.doesNotContain=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByActivityTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityTypeId equals to DEFAULT_ACTIVITY_TYPE_ID
        defaultActivityShouldBeFound("activityTypeId.equals=" + DEFAULT_ACTIVITY_TYPE_ID);

        // Get all the activityList where activityTypeId equals to UPDATED_ACTIVITY_TYPE_ID
        defaultActivityShouldNotBeFound("activityTypeId.equals=" + UPDATED_ACTIVITY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByActivityTypeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityTypeId not equals to DEFAULT_ACTIVITY_TYPE_ID
        defaultActivityShouldNotBeFound("activityTypeId.notEquals=" + DEFAULT_ACTIVITY_TYPE_ID);

        // Get all the activityList where activityTypeId not equals to UPDATED_ACTIVITY_TYPE_ID
        defaultActivityShouldBeFound("activityTypeId.notEquals=" + UPDATED_ACTIVITY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByActivityTypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityTypeId in DEFAULT_ACTIVITY_TYPE_ID or UPDATED_ACTIVITY_TYPE_ID
        defaultActivityShouldBeFound("activityTypeId.in=" + DEFAULT_ACTIVITY_TYPE_ID + "," + UPDATED_ACTIVITY_TYPE_ID);

        // Get all the activityList where activityTypeId equals to UPDATED_ACTIVITY_TYPE_ID
        defaultActivityShouldNotBeFound("activityTypeId.in=" + UPDATED_ACTIVITY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByActivityTypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityTypeId is not null
        defaultActivityShouldBeFound("activityTypeId.specified=true");

        // Get all the activityList where activityTypeId is null
        defaultActivityShouldNotBeFound("activityTypeId.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByActivityTypeIdContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityTypeId contains DEFAULT_ACTIVITY_TYPE_ID
        defaultActivityShouldBeFound("activityTypeId.contains=" + DEFAULT_ACTIVITY_TYPE_ID);

        // Get all the activityList where activityTypeId contains UPDATED_ACTIVITY_TYPE_ID
        defaultActivityShouldNotBeFound("activityTypeId.contains=" + UPDATED_ACTIVITY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByActivityTypeIdNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityTypeId does not contain DEFAULT_ACTIVITY_TYPE_ID
        defaultActivityShouldNotBeFound("activityTypeId.doesNotContain=" + DEFAULT_ACTIVITY_TYPE_ID);

        // Get all the activityList where activityTypeId does not contain UPDATED_ACTIVITY_TYPE_ID
        defaultActivityShouldBeFound("activityTypeId.doesNotContain=" + UPDATED_ACTIVITY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByObjectTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where objectTypeId equals to DEFAULT_OBJECT_TYPE_ID
        defaultActivityShouldBeFound("objectTypeId.equals=" + DEFAULT_OBJECT_TYPE_ID);

        // Get all the activityList where objectTypeId equals to UPDATED_OBJECT_TYPE_ID
        defaultActivityShouldNotBeFound("objectTypeId.equals=" + UPDATED_OBJECT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByObjectTypeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where objectTypeId not equals to DEFAULT_OBJECT_TYPE_ID
        defaultActivityShouldNotBeFound("objectTypeId.notEquals=" + DEFAULT_OBJECT_TYPE_ID);

        // Get all the activityList where objectTypeId not equals to UPDATED_OBJECT_TYPE_ID
        defaultActivityShouldBeFound("objectTypeId.notEquals=" + UPDATED_OBJECT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByObjectTypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where objectTypeId in DEFAULT_OBJECT_TYPE_ID or UPDATED_OBJECT_TYPE_ID
        defaultActivityShouldBeFound("objectTypeId.in=" + DEFAULT_OBJECT_TYPE_ID + "," + UPDATED_OBJECT_TYPE_ID);

        // Get all the activityList where objectTypeId equals to UPDATED_OBJECT_TYPE_ID
        defaultActivityShouldNotBeFound("objectTypeId.in=" + UPDATED_OBJECT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByObjectTypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where objectTypeId is not null
        defaultActivityShouldBeFound("objectTypeId.specified=true");

        // Get all the activityList where objectTypeId is null
        defaultActivityShouldNotBeFound("objectTypeId.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByObjectTypeIdContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where objectTypeId contains DEFAULT_OBJECT_TYPE_ID
        defaultActivityShouldBeFound("objectTypeId.contains=" + DEFAULT_OBJECT_TYPE_ID);

        // Get all the activityList where objectTypeId contains UPDATED_OBJECT_TYPE_ID
        defaultActivityShouldNotBeFound("objectTypeId.contains=" + UPDATED_OBJECT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByObjectTypeIdNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where objectTypeId does not contain DEFAULT_OBJECT_TYPE_ID
        defaultActivityShouldNotBeFound("objectTypeId.doesNotContain=" + DEFAULT_OBJECT_TYPE_ID);

        // Get all the activityList where objectTypeId does not contain UPDATED_OBJECT_TYPE_ID
        defaultActivityShouldBeFound("objectTypeId.doesNotContain=" + UPDATED_OBJECT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByPriorityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where priorityId equals to DEFAULT_PRIORITY_ID
        defaultActivityShouldBeFound("priorityId.equals=" + DEFAULT_PRIORITY_ID);

        // Get all the activityList where priorityId equals to UPDATED_PRIORITY_ID
        defaultActivityShouldNotBeFound("priorityId.equals=" + UPDATED_PRIORITY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByPriorityIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where priorityId not equals to DEFAULT_PRIORITY_ID
        defaultActivityShouldNotBeFound("priorityId.notEquals=" + DEFAULT_PRIORITY_ID);

        // Get all the activityList where priorityId not equals to UPDATED_PRIORITY_ID
        defaultActivityShouldBeFound("priorityId.notEquals=" + UPDATED_PRIORITY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByPriorityIdIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where priorityId in DEFAULT_PRIORITY_ID or UPDATED_PRIORITY_ID
        defaultActivityShouldBeFound("priorityId.in=" + DEFAULT_PRIORITY_ID + "," + UPDATED_PRIORITY_ID);

        // Get all the activityList where priorityId equals to UPDATED_PRIORITY_ID
        defaultActivityShouldNotBeFound("priorityId.in=" + UPDATED_PRIORITY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByPriorityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where priorityId is not null
        defaultActivityShouldBeFound("priorityId.specified=true");

        // Get all the activityList where priorityId is null
        defaultActivityShouldNotBeFound("priorityId.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByPriorityIdContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where priorityId contains DEFAULT_PRIORITY_ID
        defaultActivityShouldBeFound("priorityId.contains=" + DEFAULT_PRIORITY_ID);

        // Get all the activityList where priorityId contains UPDATED_PRIORITY_ID
        defaultActivityShouldNotBeFound("priorityId.contains=" + UPDATED_PRIORITY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByPriorityIdNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where priorityId does not contain DEFAULT_PRIORITY_ID
        defaultActivityShouldNotBeFound("priorityId.doesNotContain=" + DEFAULT_PRIORITY_ID);

        // Get all the activityList where priorityId does not contain UPDATED_PRIORITY_ID
        defaultActivityShouldBeFound("priorityId.doesNotContain=" + UPDATED_PRIORITY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByOpportunityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where opportunityId equals to DEFAULT_OPPORTUNITY_ID
        defaultActivityShouldBeFound("opportunityId.equals=" + DEFAULT_OPPORTUNITY_ID);

        // Get all the activityList where opportunityId equals to UPDATED_OPPORTUNITY_ID
        defaultActivityShouldNotBeFound("opportunityId.equals=" + UPDATED_OPPORTUNITY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByOpportunityIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where opportunityId not equals to DEFAULT_OPPORTUNITY_ID
        defaultActivityShouldNotBeFound("opportunityId.notEquals=" + DEFAULT_OPPORTUNITY_ID);

        // Get all the activityList where opportunityId not equals to UPDATED_OPPORTUNITY_ID
        defaultActivityShouldBeFound("opportunityId.notEquals=" + UPDATED_OPPORTUNITY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByOpportunityIdIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where opportunityId in DEFAULT_OPPORTUNITY_ID or UPDATED_OPPORTUNITY_ID
        defaultActivityShouldBeFound("opportunityId.in=" + DEFAULT_OPPORTUNITY_ID + "," + UPDATED_OPPORTUNITY_ID);

        // Get all the activityList where opportunityId equals to UPDATED_OPPORTUNITY_ID
        defaultActivityShouldNotBeFound("opportunityId.in=" + UPDATED_OPPORTUNITY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByOpportunityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where opportunityId is not null
        defaultActivityShouldBeFound("opportunityId.specified=true");

        // Get all the activityList where opportunityId is null
        defaultActivityShouldNotBeFound("opportunityId.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByOpportunityIdContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where opportunityId contains DEFAULT_OPPORTUNITY_ID
        defaultActivityShouldBeFound("opportunityId.contains=" + DEFAULT_OPPORTUNITY_ID);

        // Get all the activityList where opportunityId contains UPDATED_OPPORTUNITY_ID
        defaultActivityShouldNotBeFound("opportunityId.contains=" + UPDATED_OPPORTUNITY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByOpportunityIdNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where opportunityId does not contain DEFAULT_OPPORTUNITY_ID
        defaultActivityShouldNotBeFound("opportunityId.doesNotContain=" + DEFAULT_OPPORTUNITY_ID);

        // Get all the activityList where opportunityId does not contain UPDATED_OPPORTUNITY_ID
        defaultActivityShouldBeFound("opportunityId.doesNotContain=" + UPDATED_OPPORTUNITY_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByOrderIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where orderId equals to DEFAULT_ORDER_ID
        defaultActivityShouldBeFound("orderId.equals=" + DEFAULT_ORDER_ID);

        // Get all the activityList where orderId equals to UPDATED_ORDER_ID
        defaultActivityShouldNotBeFound("orderId.equals=" + UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByOrderIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where orderId not equals to DEFAULT_ORDER_ID
        defaultActivityShouldNotBeFound("orderId.notEquals=" + DEFAULT_ORDER_ID);

        // Get all the activityList where orderId not equals to UPDATED_ORDER_ID
        defaultActivityShouldBeFound("orderId.notEquals=" + UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByOrderIdIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where orderId in DEFAULT_ORDER_ID or UPDATED_ORDER_ID
        defaultActivityShouldBeFound("orderId.in=" + DEFAULT_ORDER_ID + "," + UPDATED_ORDER_ID);

        // Get all the activityList where orderId equals to UPDATED_ORDER_ID
        defaultActivityShouldNotBeFound("orderId.in=" + UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByOrderIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where orderId is not null
        defaultActivityShouldBeFound("orderId.specified=true");

        // Get all the activityList where orderId is null
        defaultActivityShouldNotBeFound("orderId.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByOrderIdContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where orderId contains DEFAULT_ORDER_ID
        defaultActivityShouldBeFound("orderId.contains=" + DEFAULT_ORDER_ID);

        // Get all the activityList where orderId contains UPDATED_ORDER_ID
        defaultActivityShouldNotBeFound("orderId.contains=" + UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByOrderIdNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where orderId does not contain DEFAULT_ORDER_ID
        defaultActivityShouldNotBeFound("orderId.doesNotContain=" + DEFAULT_ORDER_ID);

        // Get all the activityList where orderId does not contain UPDATED_ORDER_ID
        defaultActivityShouldBeFound("orderId.doesNotContain=" + UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByContractIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where contractId equals to DEFAULT_CONTRACT_ID
        defaultActivityShouldBeFound("contractId.equals=" + DEFAULT_CONTRACT_ID);

        // Get all the activityList where contractId equals to UPDATED_CONTRACT_ID
        defaultActivityShouldNotBeFound("contractId.equals=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByContractIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where contractId not equals to DEFAULT_CONTRACT_ID
        defaultActivityShouldNotBeFound("contractId.notEquals=" + DEFAULT_CONTRACT_ID);

        // Get all the activityList where contractId not equals to UPDATED_CONTRACT_ID
        defaultActivityShouldBeFound("contractId.notEquals=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByContractIdIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where contractId in DEFAULT_CONTRACT_ID or UPDATED_CONTRACT_ID
        defaultActivityShouldBeFound("contractId.in=" + DEFAULT_CONTRACT_ID + "," + UPDATED_CONTRACT_ID);

        // Get all the activityList where contractId equals to UPDATED_CONTRACT_ID
        defaultActivityShouldNotBeFound("contractId.in=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByContractIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where contractId is not null
        defaultActivityShouldBeFound("contractId.specified=true");

        // Get all the activityList where contractId is null
        defaultActivityShouldNotBeFound("contractId.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByContractIdContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where contractId contains DEFAULT_CONTRACT_ID
        defaultActivityShouldBeFound("contractId.contains=" + DEFAULT_CONTRACT_ID);

        // Get all the activityList where contractId contains UPDATED_CONTRACT_ID
        defaultActivityShouldNotBeFound("contractId.contains=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByContractIdNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where contractId does not contain DEFAULT_CONTRACT_ID
        defaultActivityShouldNotBeFound("contractId.doesNotContain=" + DEFAULT_CONTRACT_ID);

        // Get all the activityList where contractId does not contain UPDATED_CONTRACT_ID
        defaultActivityShouldBeFound("contractId.doesNotContain=" + UPDATED_CONTRACT_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByPriorityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where priorityName equals to DEFAULT_PRIORITY_NAME
        defaultActivityShouldBeFound("priorityName.equals=" + DEFAULT_PRIORITY_NAME);

        // Get all the activityList where priorityName equals to UPDATED_PRIORITY_NAME
        defaultActivityShouldNotBeFound("priorityName.equals=" + UPDATED_PRIORITY_NAME);
    }

    @Test
    @Transactional
    void getAllActivitiesByPriorityNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where priorityName not equals to DEFAULT_PRIORITY_NAME
        defaultActivityShouldNotBeFound("priorityName.notEquals=" + DEFAULT_PRIORITY_NAME);

        // Get all the activityList where priorityName not equals to UPDATED_PRIORITY_NAME
        defaultActivityShouldBeFound("priorityName.notEquals=" + UPDATED_PRIORITY_NAME);
    }

    @Test
    @Transactional
    void getAllActivitiesByPriorityNameIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where priorityName in DEFAULT_PRIORITY_NAME or UPDATED_PRIORITY_NAME
        defaultActivityShouldBeFound("priorityName.in=" + DEFAULT_PRIORITY_NAME + "," + UPDATED_PRIORITY_NAME);

        // Get all the activityList where priorityName equals to UPDATED_PRIORITY_NAME
        defaultActivityShouldNotBeFound("priorityName.in=" + UPDATED_PRIORITY_NAME);
    }

    @Test
    @Transactional
    void getAllActivitiesByPriorityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where priorityName is not null
        defaultActivityShouldBeFound("priorityName.specified=true");

        // Get all the activityList where priorityName is null
        defaultActivityShouldNotBeFound("priorityName.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByPriorityNameContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where priorityName contains DEFAULT_PRIORITY_NAME
        defaultActivityShouldBeFound("priorityName.contains=" + DEFAULT_PRIORITY_NAME);

        // Get all the activityList where priorityName contains UPDATED_PRIORITY_NAME
        defaultActivityShouldNotBeFound("priorityName.contains=" + UPDATED_PRIORITY_NAME);
    }

    @Test
    @Transactional
    void getAllActivitiesByPriorityNameNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where priorityName does not contain DEFAULT_PRIORITY_NAME
        defaultActivityShouldNotBeFound("priorityName.doesNotContain=" + DEFAULT_PRIORITY_NAME);

        // Get all the activityList where priorityName does not contain UPDATED_PRIORITY_NAME
        defaultActivityShouldBeFound("priorityName.doesNotContain=" + UPDATED_PRIORITY_NAME);
    }

    @Test
    @Transactional
    void getAllActivitiesByResponsibleIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where responsibleId equals to DEFAULT_RESPONSIBLE_ID
        defaultActivityShouldBeFound("responsibleId.equals=" + DEFAULT_RESPONSIBLE_ID);

        // Get all the activityList where responsibleId equals to UPDATED_RESPONSIBLE_ID
        defaultActivityShouldNotBeFound("responsibleId.equals=" + UPDATED_RESPONSIBLE_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByResponsibleIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where responsibleId not equals to DEFAULT_RESPONSIBLE_ID
        defaultActivityShouldNotBeFound("responsibleId.notEquals=" + DEFAULT_RESPONSIBLE_ID);

        // Get all the activityList where responsibleId not equals to UPDATED_RESPONSIBLE_ID
        defaultActivityShouldBeFound("responsibleId.notEquals=" + UPDATED_RESPONSIBLE_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByResponsibleIdIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where responsibleId in DEFAULT_RESPONSIBLE_ID or UPDATED_RESPONSIBLE_ID
        defaultActivityShouldBeFound("responsibleId.in=" + DEFAULT_RESPONSIBLE_ID + "," + UPDATED_RESPONSIBLE_ID);

        // Get all the activityList where responsibleId equals to UPDATED_RESPONSIBLE_ID
        defaultActivityShouldNotBeFound("responsibleId.in=" + UPDATED_RESPONSIBLE_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByResponsibleIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where responsibleId is not null
        defaultActivityShouldBeFound("responsibleId.specified=true");

        // Get all the activityList where responsibleId is null
        defaultActivityShouldNotBeFound("responsibleId.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByResponsibleIdContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where responsibleId contains DEFAULT_RESPONSIBLE_ID
        defaultActivityShouldBeFound("responsibleId.contains=" + DEFAULT_RESPONSIBLE_ID);

        // Get all the activityList where responsibleId contains UPDATED_RESPONSIBLE_ID
        defaultActivityShouldNotBeFound("responsibleId.contains=" + UPDATED_RESPONSIBLE_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByResponsibleIdNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where responsibleId does not contain DEFAULT_RESPONSIBLE_ID
        defaultActivityShouldNotBeFound("responsibleId.doesNotContain=" + DEFAULT_RESPONSIBLE_ID);

        // Get all the activityList where responsibleId does not contain UPDATED_RESPONSIBLE_ID
        defaultActivityShouldBeFound("responsibleId.doesNotContain=" + UPDATED_RESPONSIBLE_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where startDate equals to DEFAULT_START_DATE
        defaultActivityShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the activityList where startDate equals to UPDATED_START_DATE
        defaultActivityShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllActivitiesByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where startDate not equals to DEFAULT_START_DATE
        defaultActivityShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the activityList where startDate not equals to UPDATED_START_DATE
        defaultActivityShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllActivitiesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultActivityShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the activityList where startDate equals to UPDATED_START_DATE
        defaultActivityShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllActivitiesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where startDate is not null
        defaultActivityShouldBeFound("startDate.specified=true");

        // Get all the activityList where startDate is null
        defaultActivityShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByClosedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where closedOn equals to DEFAULT_CLOSED_ON
        defaultActivityShouldBeFound("closedOn.equals=" + DEFAULT_CLOSED_ON);

        // Get all the activityList where closedOn equals to UPDATED_CLOSED_ON
        defaultActivityShouldNotBeFound("closedOn.equals=" + UPDATED_CLOSED_ON);
    }

    @Test
    @Transactional
    void getAllActivitiesByClosedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where closedOn not equals to DEFAULT_CLOSED_ON
        defaultActivityShouldNotBeFound("closedOn.notEquals=" + DEFAULT_CLOSED_ON);

        // Get all the activityList where closedOn not equals to UPDATED_CLOSED_ON
        defaultActivityShouldBeFound("closedOn.notEquals=" + UPDATED_CLOSED_ON);
    }

    @Test
    @Transactional
    void getAllActivitiesByClosedOnIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where closedOn in DEFAULT_CLOSED_ON or UPDATED_CLOSED_ON
        defaultActivityShouldBeFound("closedOn.in=" + DEFAULT_CLOSED_ON + "," + UPDATED_CLOSED_ON);

        // Get all the activityList where closedOn equals to UPDATED_CLOSED_ON
        defaultActivityShouldNotBeFound("closedOn.in=" + UPDATED_CLOSED_ON);
    }

    @Test
    @Transactional
    void getAllActivitiesByClosedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where closedOn is not null
        defaultActivityShouldBeFound("closedOn.specified=true");

        // Get all the activityList where closedOn is null
        defaultActivityShouldNotBeFound("closedOn.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where duration equals to DEFAULT_DURATION
        defaultActivityShouldBeFound("duration.equals=" + DEFAULT_DURATION);

        // Get all the activityList where duration equals to UPDATED_DURATION
        defaultActivityShouldNotBeFound("duration.equals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllActivitiesByDurationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where duration not equals to DEFAULT_DURATION
        defaultActivityShouldNotBeFound("duration.notEquals=" + DEFAULT_DURATION);

        // Get all the activityList where duration not equals to UPDATED_DURATION
        defaultActivityShouldBeFound("duration.notEquals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllActivitiesByDurationIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where duration in DEFAULT_DURATION or UPDATED_DURATION
        defaultActivityShouldBeFound("duration.in=" + DEFAULT_DURATION + "," + UPDATED_DURATION);

        // Get all the activityList where duration equals to UPDATED_DURATION
        defaultActivityShouldNotBeFound("duration.in=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllActivitiesByDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where duration is not null
        defaultActivityShouldBeFound("duration.specified=true");

        // Get all the activityList where duration is null
        defaultActivityShouldNotBeFound("duration.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where duration is greater than or equal to DEFAULT_DURATION
        defaultActivityShouldBeFound("duration.greaterThanOrEqual=" + DEFAULT_DURATION);

        // Get all the activityList where duration is greater than or equal to UPDATED_DURATION
        defaultActivityShouldNotBeFound("duration.greaterThanOrEqual=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllActivitiesByDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where duration is less than or equal to DEFAULT_DURATION
        defaultActivityShouldBeFound("duration.lessThanOrEqual=" + DEFAULT_DURATION);

        // Get all the activityList where duration is less than or equal to SMALLER_DURATION
        defaultActivityShouldNotBeFound("duration.lessThanOrEqual=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllActivitiesByDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where duration is less than DEFAULT_DURATION
        defaultActivityShouldNotBeFound("duration.lessThan=" + DEFAULT_DURATION);

        // Get all the activityList where duration is less than UPDATED_DURATION
        defaultActivityShouldBeFound("duration.lessThan=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllActivitiesByDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where duration is greater than DEFAULT_DURATION
        defaultActivityShouldNotBeFound("duration.greaterThan=" + DEFAULT_DURATION);

        // Get all the activityList where duration is greater than SMALLER_DURATION
        defaultActivityShouldBeFound("duration.greaterThan=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllActivitiesByDurationUnitIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where durationUnitId equals to DEFAULT_DURATION_UNIT_ID
        defaultActivityShouldBeFound("durationUnitId.equals=" + DEFAULT_DURATION_UNIT_ID);

        // Get all the activityList where durationUnitId equals to UPDATED_DURATION_UNIT_ID
        defaultActivityShouldNotBeFound("durationUnitId.equals=" + UPDATED_DURATION_UNIT_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByDurationUnitIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where durationUnitId not equals to DEFAULT_DURATION_UNIT_ID
        defaultActivityShouldNotBeFound("durationUnitId.notEquals=" + DEFAULT_DURATION_UNIT_ID);

        // Get all the activityList where durationUnitId not equals to UPDATED_DURATION_UNIT_ID
        defaultActivityShouldBeFound("durationUnitId.notEquals=" + UPDATED_DURATION_UNIT_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByDurationUnitIdIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where durationUnitId in DEFAULT_DURATION_UNIT_ID or UPDATED_DURATION_UNIT_ID
        defaultActivityShouldBeFound("durationUnitId.in=" + DEFAULT_DURATION_UNIT_ID + "," + UPDATED_DURATION_UNIT_ID);

        // Get all the activityList where durationUnitId equals to UPDATED_DURATION_UNIT_ID
        defaultActivityShouldNotBeFound("durationUnitId.in=" + UPDATED_DURATION_UNIT_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByDurationUnitIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where durationUnitId is not null
        defaultActivityShouldBeFound("durationUnitId.specified=true");

        // Get all the activityList where durationUnitId is null
        defaultActivityShouldNotBeFound("durationUnitId.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByDurationUnitIdContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where durationUnitId contains DEFAULT_DURATION_UNIT_ID
        defaultActivityShouldBeFound("durationUnitId.contains=" + DEFAULT_DURATION_UNIT_ID);

        // Get all the activityList where durationUnitId contains UPDATED_DURATION_UNIT_ID
        defaultActivityShouldNotBeFound("durationUnitId.contains=" + UPDATED_DURATION_UNIT_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByDurationUnitIdNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where durationUnitId does not contain DEFAULT_DURATION_UNIT_ID
        defaultActivityShouldNotBeFound("durationUnitId.doesNotContain=" + DEFAULT_DURATION_UNIT_ID);

        // Get all the activityList where durationUnitId does not contain UPDATED_DURATION_UNIT_ID
        defaultActivityShouldBeFound("durationUnitId.doesNotContain=" + UPDATED_DURATION_UNIT_ID);
    }

    @Test
    @Transactional
    void getAllActivitiesByConversionIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where conversion equals to DEFAULT_CONVERSION
        defaultActivityShouldBeFound("conversion.equals=" + DEFAULT_CONVERSION);

        // Get all the activityList where conversion equals to UPDATED_CONVERSION
        defaultActivityShouldNotBeFound("conversion.equals=" + UPDATED_CONVERSION);
    }

    @Test
    @Transactional
    void getAllActivitiesByConversionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where conversion not equals to DEFAULT_CONVERSION
        defaultActivityShouldNotBeFound("conversion.notEquals=" + DEFAULT_CONVERSION);

        // Get all the activityList where conversion not equals to UPDATED_CONVERSION
        defaultActivityShouldBeFound("conversion.notEquals=" + UPDATED_CONVERSION);
    }

    @Test
    @Transactional
    void getAllActivitiesByConversionIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where conversion in DEFAULT_CONVERSION or UPDATED_CONVERSION
        defaultActivityShouldBeFound("conversion.in=" + DEFAULT_CONVERSION + "," + UPDATED_CONVERSION);

        // Get all the activityList where conversion equals to UPDATED_CONVERSION
        defaultActivityShouldNotBeFound("conversion.in=" + UPDATED_CONVERSION);
    }

    @Test
    @Transactional
    void getAllActivitiesByConversionIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where conversion is not null
        defaultActivityShouldBeFound("conversion.specified=true");

        // Get all the activityList where conversion is null
        defaultActivityShouldNotBeFound("conversion.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByConversionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where conversion is greater than or equal to DEFAULT_CONVERSION
        defaultActivityShouldBeFound("conversion.greaterThanOrEqual=" + DEFAULT_CONVERSION);

        // Get all the activityList where conversion is greater than or equal to UPDATED_CONVERSION
        defaultActivityShouldNotBeFound("conversion.greaterThanOrEqual=" + UPDATED_CONVERSION);
    }

    @Test
    @Transactional
    void getAllActivitiesByConversionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where conversion is less than or equal to DEFAULT_CONVERSION
        defaultActivityShouldBeFound("conversion.lessThanOrEqual=" + DEFAULT_CONVERSION);

        // Get all the activityList where conversion is less than or equal to SMALLER_CONVERSION
        defaultActivityShouldNotBeFound("conversion.lessThanOrEqual=" + SMALLER_CONVERSION);
    }

    @Test
    @Transactional
    void getAllActivitiesByConversionIsLessThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where conversion is less than DEFAULT_CONVERSION
        defaultActivityShouldNotBeFound("conversion.lessThan=" + DEFAULT_CONVERSION);

        // Get all the activityList where conversion is less than UPDATED_CONVERSION
        defaultActivityShouldBeFound("conversion.lessThan=" + UPDATED_CONVERSION);
    }

    @Test
    @Transactional
    void getAllActivitiesByConversionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where conversion is greater than DEFAULT_CONVERSION
        defaultActivityShouldNotBeFound("conversion.greaterThan=" + DEFAULT_CONVERSION);

        // Get all the activityList where conversion is greater than SMALLER_CONVERSION
        defaultActivityShouldBeFound("conversion.greaterThan=" + SMALLER_CONVERSION);
    }

    @Test
    @Transactional
    void getAllActivitiesByTextStrIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where textStr equals to DEFAULT_TEXT_STR
        defaultActivityShouldBeFound("textStr.equals=" + DEFAULT_TEXT_STR);

        // Get all the activityList where textStr equals to UPDATED_TEXT_STR
        defaultActivityShouldNotBeFound("textStr.equals=" + UPDATED_TEXT_STR);
    }

    @Test
    @Transactional
    void getAllActivitiesByTextStrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where textStr not equals to DEFAULT_TEXT_STR
        defaultActivityShouldNotBeFound("textStr.notEquals=" + DEFAULT_TEXT_STR);

        // Get all the activityList where textStr not equals to UPDATED_TEXT_STR
        defaultActivityShouldBeFound("textStr.notEquals=" + UPDATED_TEXT_STR);
    }

    @Test
    @Transactional
    void getAllActivitiesByTextStrIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where textStr in DEFAULT_TEXT_STR or UPDATED_TEXT_STR
        defaultActivityShouldBeFound("textStr.in=" + DEFAULT_TEXT_STR + "," + UPDATED_TEXT_STR);

        // Get all the activityList where textStr equals to UPDATED_TEXT_STR
        defaultActivityShouldNotBeFound("textStr.in=" + UPDATED_TEXT_STR);
    }

    @Test
    @Transactional
    void getAllActivitiesByTextStrIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where textStr is not null
        defaultActivityShouldBeFound("textStr.specified=true");

        // Get all the activityList where textStr is null
        defaultActivityShouldNotBeFound("textStr.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitiesByTextStrContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where textStr contains DEFAULT_TEXT_STR
        defaultActivityShouldBeFound("textStr.contains=" + DEFAULT_TEXT_STR);

        // Get all the activityList where textStr contains UPDATED_TEXT_STR
        defaultActivityShouldNotBeFound("textStr.contains=" + UPDATED_TEXT_STR);
    }

    @Test
    @Transactional
    void getAllActivitiesByTextStrNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where textStr does not contain DEFAULT_TEXT_STR
        defaultActivityShouldNotBeFound("textStr.doesNotContain=" + DEFAULT_TEXT_STR);

        // Get all the activityList where textStr does not contain UPDATED_TEXT_STR
        defaultActivityShouldBeFound("textStr.doesNotContain=" + UPDATED_TEXT_STR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultActivityShouldBeFound(String filter) throws Exception {
        restActivityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activity.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityId").value(hasItem(DEFAULT_ACTIVITY_ID)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].deadline").value(hasItem(DEFAULT_DEADLINE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].activityTypeId").value(hasItem(DEFAULT_ACTIVITY_TYPE_ID)))
            .andExpect(jsonPath("$.[*].objectTypeId").value(hasItem(DEFAULT_OBJECT_TYPE_ID)))
            .andExpect(jsonPath("$.[*].priorityId").value(hasItem(DEFAULT_PRIORITY_ID)))
            .andExpect(jsonPath("$.[*].opportunityId").value(hasItem(DEFAULT_OPPORTUNITY_ID)))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].contractId").value(hasItem(DEFAULT_CONTRACT_ID)))
            .andExpect(jsonPath("$.[*].priorityName").value(hasItem(DEFAULT_PRIORITY_NAME)))
            .andExpect(jsonPath("$.[*].responsibleId").value(hasItem(DEFAULT_RESPONSIBLE_ID)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].closedOn").value(hasItem(DEFAULT_CLOSED_ON.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].durationUnitId").value(hasItem(DEFAULT_DURATION_UNIT_ID)))
            .andExpect(jsonPath("$.[*].conversion").value(hasItem(sameNumber(DEFAULT_CONVERSION))))
            .andExpect(jsonPath("$.[*].textStr").value(hasItem(DEFAULT_TEXT_STR)));

        // Check, that the count call also returns 1
        restActivityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultActivityShouldNotBeFound(String filter) throws Exception {
        restActivityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restActivityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingActivity() throws Exception {
        // Get the activity
        restActivityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity
        Activity updatedActivity = activityRepository.findById(activity.getId()).get();
        // Disconnect from session so that the updates on updatedActivity are not directly saved in db
        em.detach(updatedActivity);
        updatedActivity
            .activityId(UPDATED_ACTIVITY_ID)
            .companyId(UPDATED_COMPANY_ID)
            .createDate(UPDATED_CREATE_DATE)
            .deadline(UPDATED_DEADLINE)
            .name(UPDATED_NAME)
            .state(UPDATED_STATE)
            .type(UPDATED_TYPE)
            .accountId(UPDATED_ACCOUNT_ID)
            .activityTypeId(UPDATED_ACTIVITY_TYPE_ID)
            .objectTypeId(UPDATED_OBJECT_TYPE_ID)
            .priorityId(UPDATED_PRIORITY_ID)
            .opportunityId(UPDATED_OPPORTUNITY_ID)
            .orderId(UPDATED_ORDER_ID)
            .contractId(UPDATED_CONTRACT_ID)
            .priorityName(UPDATED_PRIORITY_NAME)
            .responsibleId(UPDATED_RESPONSIBLE_ID)
            .startDate(UPDATED_START_DATE)
            .closedOn(UPDATED_CLOSED_ON)
            .duration(UPDATED_DURATION)
            .durationUnitId(UPDATED_DURATION_UNIT_ID)
            .conversion(UPDATED_CONVERSION)
            .textStr(UPDATED_TEXT_STR);

        restActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActivity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedActivity))
            )
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getActivityId()).isEqualTo(UPDATED_ACTIVITY_ID);
        assertThat(testActivity.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testActivity.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testActivity.getDeadline()).isEqualTo(UPDATED_DEADLINE);
        assertThat(testActivity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testActivity.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testActivity.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testActivity.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testActivity.getActivityTypeId()).isEqualTo(UPDATED_ACTIVITY_TYPE_ID);
        assertThat(testActivity.getObjectTypeId()).isEqualTo(UPDATED_OBJECT_TYPE_ID);
        assertThat(testActivity.getPriorityId()).isEqualTo(UPDATED_PRIORITY_ID);
        assertThat(testActivity.getOpportunityId()).isEqualTo(UPDATED_OPPORTUNITY_ID);
        assertThat(testActivity.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testActivity.getContractId()).isEqualTo(UPDATED_CONTRACT_ID);
        assertThat(testActivity.getPriorityName()).isEqualTo(UPDATED_PRIORITY_NAME);
        assertThat(testActivity.getResponsibleId()).isEqualTo(UPDATED_RESPONSIBLE_ID);
        assertThat(testActivity.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testActivity.getClosedOn()).isEqualTo(UPDATED_CLOSED_ON);
        assertThat(testActivity.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testActivity.getDurationUnitId()).isEqualTo(UPDATED_DURATION_UNIT_ID);
        assertThat(testActivity.getConversion()).isEqualByComparingTo(UPDATED_CONVERSION);
        assertThat(testActivity.getTextStr()).isEqualTo(UPDATED_TEXT_STR);
    }

    @Test
    @Transactional
    void putNonExistingActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActivityWithPatch() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity using partial update
        Activity partialUpdatedActivity = new Activity();
        partialUpdatedActivity.setId(activity.getId());

        partialUpdatedActivity
            .activityId(UPDATED_ACTIVITY_ID)
            .deadline(UPDATED_DEADLINE)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .activityTypeId(UPDATED_ACTIVITY_TYPE_ID)
            .priorityId(UPDATED_PRIORITY_ID)
            .opportunityId(UPDATED_OPPORTUNITY_ID)
            .priorityName(UPDATED_PRIORITY_NAME)
            .startDate(UPDATED_START_DATE)
            .closedOn(UPDATED_CLOSED_ON)
            .durationUnitId(UPDATED_DURATION_UNIT_ID)
            .conversion(UPDATED_CONVERSION);

        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivity))
            )
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getActivityId()).isEqualTo(UPDATED_ACTIVITY_ID);
        assertThat(testActivity.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testActivity.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testActivity.getDeadline()).isEqualTo(UPDATED_DEADLINE);
        assertThat(testActivity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testActivity.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testActivity.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testActivity.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testActivity.getActivityTypeId()).isEqualTo(UPDATED_ACTIVITY_TYPE_ID);
        assertThat(testActivity.getObjectTypeId()).isEqualTo(DEFAULT_OBJECT_TYPE_ID);
        assertThat(testActivity.getPriorityId()).isEqualTo(UPDATED_PRIORITY_ID);
        assertThat(testActivity.getOpportunityId()).isEqualTo(UPDATED_OPPORTUNITY_ID);
        assertThat(testActivity.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testActivity.getContractId()).isEqualTo(DEFAULT_CONTRACT_ID);
        assertThat(testActivity.getPriorityName()).isEqualTo(UPDATED_PRIORITY_NAME);
        assertThat(testActivity.getResponsibleId()).isEqualTo(DEFAULT_RESPONSIBLE_ID);
        assertThat(testActivity.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testActivity.getClosedOn()).isEqualTo(UPDATED_CLOSED_ON);
        assertThat(testActivity.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testActivity.getDurationUnitId()).isEqualTo(UPDATED_DURATION_UNIT_ID);
        assertThat(testActivity.getConversion()).isEqualByComparingTo(UPDATED_CONVERSION);
        assertThat(testActivity.getTextStr()).isEqualTo(DEFAULT_TEXT_STR);
    }

    @Test
    @Transactional
    void fullUpdateActivityWithPatch() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity using partial update
        Activity partialUpdatedActivity = new Activity();
        partialUpdatedActivity.setId(activity.getId());

        partialUpdatedActivity
            .activityId(UPDATED_ACTIVITY_ID)
            .companyId(UPDATED_COMPANY_ID)
            .createDate(UPDATED_CREATE_DATE)
            .deadline(UPDATED_DEADLINE)
            .name(UPDATED_NAME)
            .state(UPDATED_STATE)
            .type(UPDATED_TYPE)
            .accountId(UPDATED_ACCOUNT_ID)
            .activityTypeId(UPDATED_ACTIVITY_TYPE_ID)
            .objectTypeId(UPDATED_OBJECT_TYPE_ID)
            .priorityId(UPDATED_PRIORITY_ID)
            .opportunityId(UPDATED_OPPORTUNITY_ID)
            .orderId(UPDATED_ORDER_ID)
            .contractId(UPDATED_CONTRACT_ID)
            .priorityName(UPDATED_PRIORITY_NAME)
            .responsibleId(UPDATED_RESPONSIBLE_ID)
            .startDate(UPDATED_START_DATE)
            .closedOn(UPDATED_CLOSED_ON)
            .duration(UPDATED_DURATION)
            .durationUnitId(UPDATED_DURATION_UNIT_ID)
            .conversion(UPDATED_CONVERSION)
            .textStr(UPDATED_TEXT_STR);

        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivity))
            )
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getActivityId()).isEqualTo(UPDATED_ACTIVITY_ID);
        assertThat(testActivity.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testActivity.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testActivity.getDeadline()).isEqualTo(UPDATED_DEADLINE);
        assertThat(testActivity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testActivity.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testActivity.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testActivity.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testActivity.getActivityTypeId()).isEqualTo(UPDATED_ACTIVITY_TYPE_ID);
        assertThat(testActivity.getObjectTypeId()).isEqualTo(UPDATED_OBJECT_TYPE_ID);
        assertThat(testActivity.getPriorityId()).isEqualTo(UPDATED_PRIORITY_ID);
        assertThat(testActivity.getOpportunityId()).isEqualTo(UPDATED_OPPORTUNITY_ID);
        assertThat(testActivity.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testActivity.getContractId()).isEqualTo(UPDATED_CONTRACT_ID);
        assertThat(testActivity.getPriorityName()).isEqualTo(UPDATED_PRIORITY_NAME);
        assertThat(testActivity.getResponsibleId()).isEqualTo(UPDATED_RESPONSIBLE_ID);
        assertThat(testActivity.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testActivity.getClosedOn()).isEqualTo(UPDATED_CLOSED_ON);
        assertThat(testActivity.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testActivity.getDurationUnitId()).isEqualTo(UPDATED_DURATION_UNIT_ID);
        assertThat(testActivity.getConversion()).isEqualByComparingTo(UPDATED_CONVERSION);
        assertThat(testActivity.getTextStr()).isEqualTo(UPDATED_TEXT_STR);
    }

    @Test
    @Transactional
    void patchNonExistingActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        int databaseSizeBeforeDelete = activityRepository.findAll().size();

        // Delete the activity
        restActivityMockMvc
            .perform(delete(ENTITY_API_URL_ID, activity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
