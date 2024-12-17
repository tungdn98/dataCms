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
import vn.com.datamanager.domain.ActivityObject;
import vn.com.datamanager.repository.ActivityObjectRepository;
import vn.com.datamanager.service.criteria.ActivityObjectCriteria;

/**
 * Integration tests for the {@link ActivityObjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActivityObjectResourceIT {

    private static final String DEFAULT_UNIT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/activity-objects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ActivityObjectRepository activityObjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivityObjectMockMvc;

    private ActivityObject activityObject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityObject createEntity(EntityManager em) {
        ActivityObject activityObject = new ActivityObject().unitCode(DEFAULT_UNIT_CODE).unitName(DEFAULT_UNIT_NAME);
        return activityObject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityObject createUpdatedEntity(EntityManager em) {
        ActivityObject activityObject = new ActivityObject().unitCode(UPDATED_UNIT_CODE).unitName(UPDATED_UNIT_NAME);
        return activityObject;
    }

    @BeforeEach
    public void initTest() {
        activityObject = createEntity(em);
    }

    @Test
    @Transactional
    void createActivityObject() throws Exception {
        int databaseSizeBeforeCreate = activityObjectRepository.findAll().size();
        // Create the ActivityObject
        restActivityObjectMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityObject))
            )
            .andExpect(status().isCreated());

        // Validate the ActivityObject in the database
        List<ActivityObject> activityObjectList = activityObjectRepository.findAll();
        assertThat(activityObjectList).hasSize(databaseSizeBeforeCreate + 1);
        ActivityObject testActivityObject = activityObjectList.get(activityObjectList.size() - 1);
        assertThat(testActivityObject.getUnitCode()).isEqualTo(DEFAULT_UNIT_CODE);
        assertThat(testActivityObject.getUnitName()).isEqualTo(DEFAULT_UNIT_NAME);
    }

    @Test
    @Transactional
    void createActivityObjectWithExistingId() throws Exception {
        // Create the ActivityObject with an existing ID
        activityObject.setId(1L);

        int databaseSizeBeforeCreate = activityObjectRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityObjectMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityObject))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityObject in the database
        List<ActivityObject> activityObjectList = activityObjectRepository.findAll();
        assertThat(activityObjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActivityObjects() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        // Get all the activityObjectList
        restActivityObjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityObject.getId().intValue())))
            .andExpect(jsonPath("$.[*].unitCode").value(hasItem(DEFAULT_UNIT_CODE)))
            .andExpect(jsonPath("$.[*].unitName").value(hasItem(DEFAULT_UNIT_NAME)));
    }

    @Test
    @Transactional
    void getActivityObject() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        // Get the activityObject
        restActivityObjectMockMvc
            .perform(get(ENTITY_API_URL_ID, activityObject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activityObject.getId().intValue()))
            .andExpect(jsonPath("$.unitCode").value(DEFAULT_UNIT_CODE))
            .andExpect(jsonPath("$.unitName").value(DEFAULT_UNIT_NAME));
    }

    @Test
    @Transactional
    void getActivityObjectsByIdFiltering() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        Long id = activityObject.getId();

        defaultActivityObjectShouldBeFound("id.equals=" + id);
        defaultActivityObjectShouldNotBeFound("id.notEquals=" + id);

        defaultActivityObjectShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultActivityObjectShouldNotBeFound("id.greaterThan=" + id);

        defaultActivityObjectShouldBeFound("id.lessThanOrEqual=" + id);
        defaultActivityObjectShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllActivityObjectsByUnitCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        // Get all the activityObjectList where unitCode equals to DEFAULT_UNIT_CODE
        defaultActivityObjectShouldBeFound("unitCode.equals=" + DEFAULT_UNIT_CODE);

        // Get all the activityObjectList where unitCode equals to UPDATED_UNIT_CODE
        defaultActivityObjectShouldNotBeFound("unitCode.equals=" + UPDATED_UNIT_CODE);
    }

    @Test
    @Transactional
    void getAllActivityObjectsByUnitCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        // Get all the activityObjectList where unitCode not equals to DEFAULT_UNIT_CODE
        defaultActivityObjectShouldNotBeFound("unitCode.notEquals=" + DEFAULT_UNIT_CODE);

        // Get all the activityObjectList where unitCode not equals to UPDATED_UNIT_CODE
        defaultActivityObjectShouldBeFound("unitCode.notEquals=" + UPDATED_UNIT_CODE);
    }

    @Test
    @Transactional
    void getAllActivityObjectsByUnitCodeIsInShouldWork() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        // Get all the activityObjectList where unitCode in DEFAULT_UNIT_CODE or UPDATED_UNIT_CODE
        defaultActivityObjectShouldBeFound("unitCode.in=" + DEFAULT_UNIT_CODE + "," + UPDATED_UNIT_CODE);

        // Get all the activityObjectList where unitCode equals to UPDATED_UNIT_CODE
        defaultActivityObjectShouldNotBeFound("unitCode.in=" + UPDATED_UNIT_CODE);
    }

    @Test
    @Transactional
    void getAllActivityObjectsByUnitCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        // Get all the activityObjectList where unitCode is not null
        defaultActivityObjectShouldBeFound("unitCode.specified=true");

        // Get all the activityObjectList where unitCode is null
        defaultActivityObjectShouldNotBeFound("unitCode.specified=false");
    }

    @Test
    @Transactional
    void getAllActivityObjectsByUnitCodeContainsSomething() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        // Get all the activityObjectList where unitCode contains DEFAULT_UNIT_CODE
        defaultActivityObjectShouldBeFound("unitCode.contains=" + DEFAULT_UNIT_CODE);

        // Get all the activityObjectList where unitCode contains UPDATED_UNIT_CODE
        defaultActivityObjectShouldNotBeFound("unitCode.contains=" + UPDATED_UNIT_CODE);
    }

    @Test
    @Transactional
    void getAllActivityObjectsByUnitCodeNotContainsSomething() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        // Get all the activityObjectList where unitCode does not contain DEFAULT_UNIT_CODE
        defaultActivityObjectShouldNotBeFound("unitCode.doesNotContain=" + DEFAULT_UNIT_CODE);

        // Get all the activityObjectList where unitCode does not contain UPDATED_UNIT_CODE
        defaultActivityObjectShouldBeFound("unitCode.doesNotContain=" + UPDATED_UNIT_CODE);
    }

    @Test
    @Transactional
    void getAllActivityObjectsByUnitNameIsEqualToSomething() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        // Get all the activityObjectList where unitName equals to DEFAULT_UNIT_NAME
        defaultActivityObjectShouldBeFound("unitName.equals=" + DEFAULT_UNIT_NAME);

        // Get all the activityObjectList where unitName equals to UPDATED_UNIT_NAME
        defaultActivityObjectShouldNotBeFound("unitName.equals=" + UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    void getAllActivityObjectsByUnitNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        // Get all the activityObjectList where unitName not equals to DEFAULT_UNIT_NAME
        defaultActivityObjectShouldNotBeFound("unitName.notEquals=" + DEFAULT_UNIT_NAME);

        // Get all the activityObjectList where unitName not equals to UPDATED_UNIT_NAME
        defaultActivityObjectShouldBeFound("unitName.notEquals=" + UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    void getAllActivityObjectsByUnitNameIsInShouldWork() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        // Get all the activityObjectList where unitName in DEFAULT_UNIT_NAME or UPDATED_UNIT_NAME
        defaultActivityObjectShouldBeFound("unitName.in=" + DEFAULT_UNIT_NAME + "," + UPDATED_UNIT_NAME);

        // Get all the activityObjectList where unitName equals to UPDATED_UNIT_NAME
        defaultActivityObjectShouldNotBeFound("unitName.in=" + UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    void getAllActivityObjectsByUnitNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        // Get all the activityObjectList where unitName is not null
        defaultActivityObjectShouldBeFound("unitName.specified=true");

        // Get all the activityObjectList where unitName is null
        defaultActivityObjectShouldNotBeFound("unitName.specified=false");
    }

    @Test
    @Transactional
    void getAllActivityObjectsByUnitNameContainsSomething() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        // Get all the activityObjectList where unitName contains DEFAULT_UNIT_NAME
        defaultActivityObjectShouldBeFound("unitName.contains=" + DEFAULT_UNIT_NAME);

        // Get all the activityObjectList where unitName contains UPDATED_UNIT_NAME
        defaultActivityObjectShouldNotBeFound("unitName.contains=" + UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    void getAllActivityObjectsByUnitNameNotContainsSomething() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        // Get all the activityObjectList where unitName does not contain DEFAULT_UNIT_NAME
        defaultActivityObjectShouldNotBeFound("unitName.doesNotContain=" + DEFAULT_UNIT_NAME);

        // Get all the activityObjectList where unitName does not contain UPDATED_UNIT_NAME
        defaultActivityObjectShouldBeFound("unitName.doesNotContain=" + UPDATED_UNIT_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultActivityObjectShouldBeFound(String filter) throws Exception {
        restActivityObjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityObject.getId().intValue())))
            .andExpect(jsonPath("$.[*].unitCode").value(hasItem(DEFAULT_UNIT_CODE)))
            .andExpect(jsonPath("$.[*].unitName").value(hasItem(DEFAULT_UNIT_NAME)));

        // Check, that the count call also returns 1
        restActivityObjectMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultActivityObjectShouldNotBeFound(String filter) throws Exception {
        restActivityObjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restActivityObjectMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingActivityObject() throws Exception {
        // Get the activityObject
        restActivityObjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewActivityObject() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        int databaseSizeBeforeUpdate = activityObjectRepository.findAll().size();

        // Update the activityObject
        ActivityObject updatedActivityObject = activityObjectRepository.findById(activityObject.getId()).get();
        // Disconnect from session so that the updates on updatedActivityObject are not directly saved in db
        em.detach(updatedActivityObject);
        updatedActivityObject.unitCode(UPDATED_UNIT_CODE).unitName(UPDATED_UNIT_NAME);

        restActivityObjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActivityObject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedActivityObject))
            )
            .andExpect(status().isOk());

        // Validate the ActivityObject in the database
        List<ActivityObject> activityObjectList = activityObjectRepository.findAll();
        assertThat(activityObjectList).hasSize(databaseSizeBeforeUpdate);
        ActivityObject testActivityObject = activityObjectList.get(activityObjectList.size() - 1);
        assertThat(testActivityObject.getUnitCode()).isEqualTo(UPDATED_UNIT_CODE);
        assertThat(testActivityObject.getUnitName()).isEqualTo(UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    void putNonExistingActivityObject() throws Exception {
        int databaseSizeBeforeUpdate = activityObjectRepository.findAll().size();
        activityObject.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityObjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activityObject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activityObject))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityObject in the database
        List<ActivityObject> activityObjectList = activityObjectRepository.findAll();
        assertThat(activityObjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActivityObject() throws Exception {
        int databaseSizeBeforeUpdate = activityObjectRepository.findAll().size();
        activityObject.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityObjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activityObject))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityObject in the database
        List<ActivityObject> activityObjectList = activityObjectRepository.findAll();
        assertThat(activityObjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActivityObject() throws Exception {
        int databaseSizeBeforeUpdate = activityObjectRepository.findAll().size();
        activityObject.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityObjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityObject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivityObject in the database
        List<ActivityObject> activityObjectList = activityObjectRepository.findAll();
        assertThat(activityObjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActivityObjectWithPatch() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        int databaseSizeBeforeUpdate = activityObjectRepository.findAll().size();

        // Update the activityObject using partial update
        ActivityObject partialUpdatedActivityObject = new ActivityObject();
        partialUpdatedActivityObject.setId(activityObject.getId());

        partialUpdatedActivityObject.unitCode(UPDATED_UNIT_CODE);

        restActivityObjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivityObject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivityObject))
            )
            .andExpect(status().isOk());

        // Validate the ActivityObject in the database
        List<ActivityObject> activityObjectList = activityObjectRepository.findAll();
        assertThat(activityObjectList).hasSize(databaseSizeBeforeUpdate);
        ActivityObject testActivityObject = activityObjectList.get(activityObjectList.size() - 1);
        assertThat(testActivityObject.getUnitCode()).isEqualTo(UPDATED_UNIT_CODE);
        assertThat(testActivityObject.getUnitName()).isEqualTo(DEFAULT_UNIT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateActivityObjectWithPatch() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        int databaseSizeBeforeUpdate = activityObjectRepository.findAll().size();

        // Update the activityObject using partial update
        ActivityObject partialUpdatedActivityObject = new ActivityObject();
        partialUpdatedActivityObject.setId(activityObject.getId());

        partialUpdatedActivityObject.unitCode(UPDATED_UNIT_CODE).unitName(UPDATED_UNIT_NAME);

        restActivityObjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivityObject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivityObject))
            )
            .andExpect(status().isOk());

        // Validate the ActivityObject in the database
        List<ActivityObject> activityObjectList = activityObjectRepository.findAll();
        assertThat(activityObjectList).hasSize(databaseSizeBeforeUpdate);
        ActivityObject testActivityObject = activityObjectList.get(activityObjectList.size() - 1);
        assertThat(testActivityObject.getUnitCode()).isEqualTo(UPDATED_UNIT_CODE);
        assertThat(testActivityObject.getUnitName()).isEqualTo(UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingActivityObject() throws Exception {
        int databaseSizeBeforeUpdate = activityObjectRepository.findAll().size();
        activityObject.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityObjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activityObject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activityObject))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityObject in the database
        List<ActivityObject> activityObjectList = activityObjectRepository.findAll();
        assertThat(activityObjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActivityObject() throws Exception {
        int databaseSizeBeforeUpdate = activityObjectRepository.findAll().size();
        activityObject.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityObjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activityObject))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityObject in the database
        List<ActivityObject> activityObjectList = activityObjectRepository.findAll();
        assertThat(activityObjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActivityObject() throws Exception {
        int databaseSizeBeforeUpdate = activityObjectRepository.findAll().size();
        activityObject.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityObjectMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(activityObject))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivityObject in the database
        List<ActivityObject> activityObjectList = activityObjectRepository.findAll();
        assertThat(activityObjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActivityObject() throws Exception {
        // Initialize the database
        activityObjectRepository.saveAndFlush(activityObject);

        int databaseSizeBeforeDelete = activityObjectRepository.findAll().size();

        // Delete the activityObject
        restActivityObjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, activityObject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActivityObject> activityObjectList = activityObjectRepository.findAll();
        assertThat(activityObjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
