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
import vn.com.datamanager.domain.ActivityType;
import vn.com.datamanager.repository.ActivityTypeRepository;
import vn.com.datamanager.service.criteria.ActivityTypeCriteria;

/**
 * Integration tests for the {@link ActivityTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActivityTypeResourceIT {

    private static final Long DEFAULT_ACTIVITY_TYPE_ID = 1L;
    private static final Long UPDATED_ACTIVITY_TYPE_ID = 2L;
    private static final Long SMALLER_ACTIVITY_TYPE_ID = 1L - 1L;

    private static final String DEFAULT_ACTIVITY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_STR = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_STR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/activity-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivityTypeMockMvc;

    private ActivityType activityType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityType createEntity(EntityManager em) {
        ActivityType activityType = new ActivityType()
            .activityTypeId(DEFAULT_ACTIVITY_TYPE_ID)
            .activityType(DEFAULT_ACTIVITY_TYPE)
            .textStr(DEFAULT_TEXT_STR);
        return activityType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityType createUpdatedEntity(EntityManager em) {
        ActivityType activityType = new ActivityType()
            .activityTypeId(UPDATED_ACTIVITY_TYPE_ID)
            .activityType(UPDATED_ACTIVITY_TYPE)
            .textStr(UPDATED_TEXT_STR);
        return activityType;
    }

    @BeforeEach
    public void initTest() {
        activityType = createEntity(em);
    }

    @Test
    @Transactional
    void createActivityType() throws Exception {
        int databaseSizeBeforeCreate = activityTypeRepository.findAll().size();
        // Create the ActivityType
        restActivityTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityType)))
            .andExpect(status().isCreated());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ActivityType testActivityType = activityTypeList.get(activityTypeList.size() - 1);
        assertThat(testActivityType.getActivityTypeId()).isEqualTo(DEFAULT_ACTIVITY_TYPE_ID);
        assertThat(testActivityType.getActivityType()).isEqualTo(DEFAULT_ACTIVITY_TYPE);
        assertThat(testActivityType.getTextStr()).isEqualTo(DEFAULT_TEXT_STR);
    }

    @Test
    @Transactional
    void createActivityTypeWithExistingId() throws Exception {
        // Create the ActivityType with an existing ID
        activityType.setId(1L);

        int databaseSizeBeforeCreate = activityTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityType)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActivityTypes() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList
        restActivityTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityType.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityTypeId").value(hasItem(DEFAULT_ACTIVITY_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].activityType").value(hasItem(DEFAULT_ACTIVITY_TYPE)))
            .andExpect(jsonPath("$.[*].textStr").value(hasItem(DEFAULT_TEXT_STR)));
    }

    @Test
    @Transactional
    void getActivityType() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get the activityType
        restActivityTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, activityType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activityType.getId().intValue()))
            .andExpect(jsonPath("$.activityTypeId").value(DEFAULT_ACTIVITY_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.activityType").value(DEFAULT_ACTIVITY_TYPE))
            .andExpect(jsonPath("$.textStr").value(DEFAULT_TEXT_STR));
    }

    @Test
    @Transactional
    void getActivityTypesByIdFiltering() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        Long id = activityType.getId();

        defaultActivityTypeShouldBeFound("id.equals=" + id);
        defaultActivityTypeShouldNotBeFound("id.notEquals=" + id);

        defaultActivityTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultActivityTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultActivityTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultActivityTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityTypeId equals to DEFAULT_ACTIVITY_TYPE_ID
        defaultActivityTypeShouldBeFound("activityTypeId.equals=" + DEFAULT_ACTIVITY_TYPE_ID);

        // Get all the activityTypeList where activityTypeId equals to UPDATED_ACTIVITY_TYPE_ID
        defaultActivityTypeShouldNotBeFound("activityTypeId.equals=" + UPDATED_ACTIVITY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityTypeId not equals to DEFAULT_ACTIVITY_TYPE_ID
        defaultActivityTypeShouldNotBeFound("activityTypeId.notEquals=" + DEFAULT_ACTIVITY_TYPE_ID);

        // Get all the activityTypeList where activityTypeId not equals to UPDATED_ACTIVITY_TYPE_ID
        defaultActivityTypeShouldBeFound("activityTypeId.notEquals=" + UPDATED_ACTIVITY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityTypeId in DEFAULT_ACTIVITY_TYPE_ID or UPDATED_ACTIVITY_TYPE_ID
        defaultActivityTypeShouldBeFound("activityTypeId.in=" + DEFAULT_ACTIVITY_TYPE_ID + "," + UPDATED_ACTIVITY_TYPE_ID);

        // Get all the activityTypeList where activityTypeId equals to UPDATED_ACTIVITY_TYPE_ID
        defaultActivityTypeShouldNotBeFound("activityTypeId.in=" + UPDATED_ACTIVITY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityTypeId is not null
        defaultActivityTypeShouldBeFound("activityTypeId.specified=true");

        // Get all the activityTypeList where activityTypeId is null
        defaultActivityTypeShouldNotBeFound("activityTypeId.specified=false");
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityTypeId is greater than or equal to DEFAULT_ACTIVITY_TYPE_ID
        defaultActivityTypeShouldBeFound("activityTypeId.greaterThanOrEqual=" + DEFAULT_ACTIVITY_TYPE_ID);

        // Get all the activityTypeList where activityTypeId is greater than or equal to UPDATED_ACTIVITY_TYPE_ID
        defaultActivityTypeShouldNotBeFound("activityTypeId.greaterThanOrEqual=" + UPDATED_ACTIVITY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityTypeId is less than or equal to DEFAULT_ACTIVITY_TYPE_ID
        defaultActivityTypeShouldBeFound("activityTypeId.lessThanOrEqual=" + DEFAULT_ACTIVITY_TYPE_ID);

        // Get all the activityTypeList where activityTypeId is less than or equal to SMALLER_ACTIVITY_TYPE_ID
        defaultActivityTypeShouldNotBeFound("activityTypeId.lessThanOrEqual=" + SMALLER_ACTIVITY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityTypeId is less than DEFAULT_ACTIVITY_TYPE_ID
        defaultActivityTypeShouldNotBeFound("activityTypeId.lessThan=" + DEFAULT_ACTIVITY_TYPE_ID);

        // Get all the activityTypeList where activityTypeId is less than UPDATED_ACTIVITY_TYPE_ID
        defaultActivityTypeShouldBeFound("activityTypeId.lessThan=" + UPDATED_ACTIVITY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityTypeId is greater than DEFAULT_ACTIVITY_TYPE_ID
        defaultActivityTypeShouldNotBeFound("activityTypeId.greaterThan=" + DEFAULT_ACTIVITY_TYPE_ID);

        // Get all the activityTypeList where activityTypeId is greater than SMALLER_ACTIVITY_TYPE_ID
        defaultActivityTypeShouldBeFound("activityTypeId.greaterThan=" + SMALLER_ACTIVITY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityType equals to DEFAULT_ACTIVITY_TYPE
        defaultActivityTypeShouldBeFound("activityType.equals=" + DEFAULT_ACTIVITY_TYPE);

        // Get all the activityTypeList where activityType equals to UPDATED_ACTIVITY_TYPE
        defaultActivityTypeShouldNotBeFound("activityType.equals=" + UPDATED_ACTIVITY_TYPE);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityType not equals to DEFAULT_ACTIVITY_TYPE
        defaultActivityTypeShouldNotBeFound("activityType.notEquals=" + DEFAULT_ACTIVITY_TYPE);

        // Get all the activityTypeList where activityType not equals to UPDATED_ACTIVITY_TYPE
        defaultActivityTypeShouldBeFound("activityType.notEquals=" + UPDATED_ACTIVITY_TYPE);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeIsInShouldWork() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityType in DEFAULT_ACTIVITY_TYPE or UPDATED_ACTIVITY_TYPE
        defaultActivityTypeShouldBeFound("activityType.in=" + DEFAULT_ACTIVITY_TYPE + "," + UPDATED_ACTIVITY_TYPE);

        // Get all the activityTypeList where activityType equals to UPDATED_ACTIVITY_TYPE
        defaultActivityTypeShouldNotBeFound("activityType.in=" + UPDATED_ACTIVITY_TYPE);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityType is not null
        defaultActivityTypeShouldBeFound("activityType.specified=true");

        // Get all the activityTypeList where activityType is null
        defaultActivityTypeShouldNotBeFound("activityType.specified=false");
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeContainsSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityType contains DEFAULT_ACTIVITY_TYPE
        defaultActivityTypeShouldBeFound("activityType.contains=" + DEFAULT_ACTIVITY_TYPE);

        // Get all the activityTypeList where activityType contains UPDATED_ACTIVITY_TYPE
        defaultActivityTypeShouldNotBeFound("activityType.contains=" + UPDATED_ACTIVITY_TYPE);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeNotContainsSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityType does not contain DEFAULT_ACTIVITY_TYPE
        defaultActivityTypeShouldNotBeFound("activityType.doesNotContain=" + DEFAULT_ACTIVITY_TYPE);

        // Get all the activityTypeList where activityType does not contain UPDATED_ACTIVITY_TYPE
        defaultActivityTypeShouldBeFound("activityType.doesNotContain=" + UPDATED_ACTIVITY_TYPE);
    }

    @Test
    @Transactional
    void getAllActivityTypesByTextStrIsEqualToSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where textStr equals to DEFAULT_TEXT_STR
        defaultActivityTypeShouldBeFound("textStr.equals=" + DEFAULT_TEXT_STR);

        // Get all the activityTypeList where textStr equals to UPDATED_TEXT_STR
        defaultActivityTypeShouldNotBeFound("textStr.equals=" + UPDATED_TEXT_STR);
    }

    @Test
    @Transactional
    void getAllActivityTypesByTextStrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where textStr not equals to DEFAULT_TEXT_STR
        defaultActivityTypeShouldNotBeFound("textStr.notEquals=" + DEFAULT_TEXT_STR);

        // Get all the activityTypeList where textStr not equals to UPDATED_TEXT_STR
        defaultActivityTypeShouldBeFound("textStr.notEquals=" + UPDATED_TEXT_STR);
    }

    @Test
    @Transactional
    void getAllActivityTypesByTextStrIsInShouldWork() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where textStr in DEFAULT_TEXT_STR or UPDATED_TEXT_STR
        defaultActivityTypeShouldBeFound("textStr.in=" + DEFAULT_TEXT_STR + "," + UPDATED_TEXT_STR);

        // Get all the activityTypeList where textStr equals to UPDATED_TEXT_STR
        defaultActivityTypeShouldNotBeFound("textStr.in=" + UPDATED_TEXT_STR);
    }

    @Test
    @Transactional
    void getAllActivityTypesByTextStrIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where textStr is not null
        defaultActivityTypeShouldBeFound("textStr.specified=true");

        // Get all the activityTypeList where textStr is null
        defaultActivityTypeShouldNotBeFound("textStr.specified=false");
    }

    @Test
    @Transactional
    void getAllActivityTypesByTextStrContainsSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where textStr contains DEFAULT_TEXT_STR
        defaultActivityTypeShouldBeFound("textStr.contains=" + DEFAULT_TEXT_STR);

        // Get all the activityTypeList where textStr contains UPDATED_TEXT_STR
        defaultActivityTypeShouldNotBeFound("textStr.contains=" + UPDATED_TEXT_STR);
    }

    @Test
    @Transactional
    void getAllActivityTypesByTextStrNotContainsSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where textStr does not contain DEFAULT_TEXT_STR
        defaultActivityTypeShouldNotBeFound("textStr.doesNotContain=" + DEFAULT_TEXT_STR);

        // Get all the activityTypeList where textStr does not contain UPDATED_TEXT_STR
        defaultActivityTypeShouldBeFound("textStr.doesNotContain=" + UPDATED_TEXT_STR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultActivityTypeShouldBeFound(String filter) throws Exception {
        restActivityTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityType.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityTypeId").value(hasItem(DEFAULT_ACTIVITY_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].activityType").value(hasItem(DEFAULT_ACTIVITY_TYPE)))
            .andExpect(jsonPath("$.[*].textStr").value(hasItem(DEFAULT_TEXT_STR)));

        // Check, that the count call also returns 1
        restActivityTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultActivityTypeShouldNotBeFound(String filter) throws Exception {
        restActivityTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restActivityTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingActivityType() throws Exception {
        // Get the activityType
        restActivityTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewActivityType() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();

        // Update the activityType
        ActivityType updatedActivityType = activityTypeRepository.findById(activityType.getId()).get();
        // Disconnect from session so that the updates on updatedActivityType are not directly saved in db
        em.detach(updatedActivityType);
        updatedActivityType.activityTypeId(UPDATED_ACTIVITY_TYPE_ID).activityType(UPDATED_ACTIVITY_TYPE).textStr(UPDATED_TEXT_STR);

        restActivityTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActivityType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedActivityType))
            )
            .andExpect(status().isOk());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
        ActivityType testActivityType = activityTypeList.get(activityTypeList.size() - 1);
        assertThat(testActivityType.getActivityTypeId()).isEqualTo(UPDATED_ACTIVITY_TYPE_ID);
        assertThat(testActivityType.getActivityType()).isEqualTo(UPDATED_ACTIVITY_TYPE);
        assertThat(testActivityType.getTextStr()).isEqualTo(UPDATED_TEXT_STR);
    }

    @Test
    @Transactional
    void putNonExistingActivityType() throws Exception {
        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();
        activityType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activityType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activityType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActivityType() throws Exception {
        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();
        activityType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activityType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActivityType() throws Exception {
        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();
        activityType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActivityTypeWithPatch() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();

        // Update the activityType using partial update
        ActivityType partialUpdatedActivityType = new ActivityType();
        partialUpdatedActivityType.setId(activityType.getId());

        restActivityTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivityType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivityType))
            )
            .andExpect(status().isOk());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
        ActivityType testActivityType = activityTypeList.get(activityTypeList.size() - 1);
        assertThat(testActivityType.getActivityTypeId()).isEqualTo(DEFAULT_ACTIVITY_TYPE_ID);
        assertThat(testActivityType.getActivityType()).isEqualTo(DEFAULT_ACTIVITY_TYPE);
        assertThat(testActivityType.getTextStr()).isEqualTo(DEFAULT_TEXT_STR);
    }

    @Test
    @Transactional
    void fullUpdateActivityTypeWithPatch() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();

        // Update the activityType using partial update
        ActivityType partialUpdatedActivityType = new ActivityType();
        partialUpdatedActivityType.setId(activityType.getId());

        partialUpdatedActivityType.activityTypeId(UPDATED_ACTIVITY_TYPE_ID).activityType(UPDATED_ACTIVITY_TYPE).textStr(UPDATED_TEXT_STR);

        restActivityTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivityType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivityType))
            )
            .andExpect(status().isOk());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
        ActivityType testActivityType = activityTypeList.get(activityTypeList.size() - 1);
        assertThat(testActivityType.getActivityTypeId()).isEqualTo(UPDATED_ACTIVITY_TYPE_ID);
        assertThat(testActivityType.getActivityType()).isEqualTo(UPDATED_ACTIVITY_TYPE);
        assertThat(testActivityType.getTextStr()).isEqualTo(UPDATED_TEXT_STR);
    }

    @Test
    @Transactional
    void patchNonExistingActivityType() throws Exception {
        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();
        activityType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activityType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activityType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActivityType() throws Exception {
        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();
        activityType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activityType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActivityType() throws Exception {
        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();
        activityType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(activityType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActivityType() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        int databaseSizeBeforeDelete = activityTypeRepository.findAll().size();

        // Delete the activityType
        restActivityTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, activityType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
