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
import vn.com.datamanager.domain.OpportunityStage;
import vn.com.datamanager.repository.OpportunityStageRepository;
import vn.com.datamanager.service.criteria.OpportunityStageCriteria;

/**
 * Integration tests for the {@link OpportunityStageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OpportunityStageResourceIT {

    private static final String DEFAULT_OPPORTUNITY_STAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_OPPORTUNITY_STAGE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OPPORTUNITY_STAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OPPORTUNITY_STAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OPPORTUNITY_STAGE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_OPPORTUNITY_STAGE_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/opportunity-stages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OpportunityStageRepository opportunityStageRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpportunityStageMockMvc;

    private OpportunityStage opportunityStage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpportunityStage createEntity(EntityManager em) {
        OpportunityStage opportunityStage = new OpportunityStage()
            .opportunityStageId(DEFAULT_OPPORTUNITY_STAGE_ID)
            .opportunityStageName(DEFAULT_OPPORTUNITY_STAGE_NAME)
            .opportunityStageCode(DEFAULT_OPPORTUNITY_STAGE_CODE);
        return opportunityStage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpportunityStage createUpdatedEntity(EntityManager em) {
        OpportunityStage opportunityStage = new OpportunityStage()
            .opportunityStageId(UPDATED_OPPORTUNITY_STAGE_ID)
            .opportunityStageName(UPDATED_OPPORTUNITY_STAGE_NAME)
            .opportunityStageCode(UPDATED_OPPORTUNITY_STAGE_CODE);
        return opportunityStage;
    }

    @BeforeEach
    public void initTest() {
        opportunityStage = createEntity(em);
    }

    @Test
    @Transactional
    void createOpportunityStage() throws Exception {
        int databaseSizeBeforeCreate = opportunityStageRepository.findAll().size();
        // Create the OpportunityStage
        restOpportunityStageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(opportunityStage))
            )
            .andExpect(status().isCreated());

        // Validate the OpportunityStage in the database
        List<OpportunityStage> opportunityStageList = opportunityStageRepository.findAll();
        assertThat(opportunityStageList).hasSize(databaseSizeBeforeCreate + 1);
        OpportunityStage testOpportunityStage = opportunityStageList.get(opportunityStageList.size() - 1);
        assertThat(testOpportunityStage.getOpportunityStageId()).isEqualTo(DEFAULT_OPPORTUNITY_STAGE_ID);
        assertThat(testOpportunityStage.getOpportunityStageName()).isEqualTo(DEFAULT_OPPORTUNITY_STAGE_NAME);
        assertThat(testOpportunityStage.getOpportunityStageCode()).isEqualTo(DEFAULT_OPPORTUNITY_STAGE_CODE);
    }

    @Test
    @Transactional
    void createOpportunityStageWithExistingId() throws Exception {
        // Create the OpportunityStage with an existing ID
        opportunityStage.setId(1L);

        int databaseSizeBeforeCreate = opportunityStageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpportunityStageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(opportunityStage))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpportunityStage in the database
        List<OpportunityStage> opportunityStageList = opportunityStageRepository.findAll();
        assertThat(opportunityStageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOpportunityStages() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList
        restOpportunityStageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opportunityStage.getId().intValue())))
            .andExpect(jsonPath("$.[*].opportunityStageId").value(hasItem(DEFAULT_OPPORTUNITY_STAGE_ID)))
            .andExpect(jsonPath("$.[*].opportunityStageName").value(hasItem(DEFAULT_OPPORTUNITY_STAGE_NAME)))
            .andExpect(jsonPath("$.[*].opportunityStageCode").value(hasItem(DEFAULT_OPPORTUNITY_STAGE_CODE)));
    }

    @Test
    @Transactional
    void getOpportunityStage() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get the opportunityStage
        restOpportunityStageMockMvc
            .perform(get(ENTITY_API_URL_ID, opportunityStage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(opportunityStage.getId().intValue()))
            .andExpect(jsonPath("$.opportunityStageId").value(DEFAULT_OPPORTUNITY_STAGE_ID))
            .andExpect(jsonPath("$.opportunityStageName").value(DEFAULT_OPPORTUNITY_STAGE_NAME))
            .andExpect(jsonPath("$.opportunityStageCode").value(DEFAULT_OPPORTUNITY_STAGE_CODE));
    }

    @Test
    @Transactional
    void getOpportunityStagesByIdFiltering() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        Long id = opportunityStage.getId();

        defaultOpportunityStageShouldBeFound("id.equals=" + id);
        defaultOpportunityStageShouldNotBeFound("id.notEquals=" + id);

        defaultOpportunityStageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOpportunityStageShouldNotBeFound("id.greaterThan=" + id);

        defaultOpportunityStageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOpportunityStageShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageIdIsEqualToSomething() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageId equals to DEFAULT_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageShouldBeFound("opportunityStageId.equals=" + DEFAULT_OPPORTUNITY_STAGE_ID);

        // Get all the opportunityStageList where opportunityStageId equals to UPDATED_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageShouldNotBeFound("opportunityStageId.equals=" + UPDATED_OPPORTUNITY_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageId not equals to DEFAULT_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageShouldNotBeFound("opportunityStageId.notEquals=" + DEFAULT_OPPORTUNITY_STAGE_ID);

        // Get all the opportunityStageList where opportunityStageId not equals to UPDATED_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageShouldBeFound("opportunityStageId.notEquals=" + UPDATED_OPPORTUNITY_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageIdIsInShouldWork() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageId in DEFAULT_OPPORTUNITY_STAGE_ID or UPDATED_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageShouldBeFound("opportunityStageId.in=" + DEFAULT_OPPORTUNITY_STAGE_ID + "," + UPDATED_OPPORTUNITY_STAGE_ID);

        // Get all the opportunityStageList where opportunityStageId equals to UPDATED_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageShouldNotBeFound("opportunityStageId.in=" + UPDATED_OPPORTUNITY_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageId is not null
        defaultOpportunityStageShouldBeFound("opportunityStageId.specified=true");

        // Get all the opportunityStageList where opportunityStageId is null
        defaultOpportunityStageShouldNotBeFound("opportunityStageId.specified=false");
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageIdContainsSomething() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageId contains DEFAULT_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageShouldBeFound("opportunityStageId.contains=" + DEFAULT_OPPORTUNITY_STAGE_ID);

        // Get all the opportunityStageList where opportunityStageId contains UPDATED_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageShouldNotBeFound("opportunityStageId.contains=" + UPDATED_OPPORTUNITY_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageIdNotContainsSomething() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageId does not contain DEFAULT_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageShouldNotBeFound("opportunityStageId.doesNotContain=" + DEFAULT_OPPORTUNITY_STAGE_ID);

        // Get all the opportunityStageList where opportunityStageId does not contain UPDATED_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageShouldBeFound("opportunityStageId.doesNotContain=" + UPDATED_OPPORTUNITY_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageNameIsEqualToSomething() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageName equals to DEFAULT_OPPORTUNITY_STAGE_NAME
        defaultOpportunityStageShouldBeFound("opportunityStageName.equals=" + DEFAULT_OPPORTUNITY_STAGE_NAME);

        // Get all the opportunityStageList where opportunityStageName equals to UPDATED_OPPORTUNITY_STAGE_NAME
        defaultOpportunityStageShouldNotBeFound("opportunityStageName.equals=" + UPDATED_OPPORTUNITY_STAGE_NAME);
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageName not equals to DEFAULT_OPPORTUNITY_STAGE_NAME
        defaultOpportunityStageShouldNotBeFound("opportunityStageName.notEquals=" + DEFAULT_OPPORTUNITY_STAGE_NAME);

        // Get all the opportunityStageList where opportunityStageName not equals to UPDATED_OPPORTUNITY_STAGE_NAME
        defaultOpportunityStageShouldBeFound("opportunityStageName.notEquals=" + UPDATED_OPPORTUNITY_STAGE_NAME);
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageNameIsInShouldWork() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageName in DEFAULT_OPPORTUNITY_STAGE_NAME or UPDATED_OPPORTUNITY_STAGE_NAME
        defaultOpportunityStageShouldBeFound(
            "opportunityStageName.in=" + DEFAULT_OPPORTUNITY_STAGE_NAME + "," + UPDATED_OPPORTUNITY_STAGE_NAME
        );

        // Get all the opportunityStageList where opportunityStageName equals to UPDATED_OPPORTUNITY_STAGE_NAME
        defaultOpportunityStageShouldNotBeFound("opportunityStageName.in=" + UPDATED_OPPORTUNITY_STAGE_NAME);
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageName is not null
        defaultOpportunityStageShouldBeFound("opportunityStageName.specified=true");

        // Get all the opportunityStageList where opportunityStageName is null
        defaultOpportunityStageShouldNotBeFound("opportunityStageName.specified=false");
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageNameContainsSomething() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageName contains DEFAULT_OPPORTUNITY_STAGE_NAME
        defaultOpportunityStageShouldBeFound("opportunityStageName.contains=" + DEFAULT_OPPORTUNITY_STAGE_NAME);

        // Get all the opportunityStageList where opportunityStageName contains UPDATED_OPPORTUNITY_STAGE_NAME
        defaultOpportunityStageShouldNotBeFound("opportunityStageName.contains=" + UPDATED_OPPORTUNITY_STAGE_NAME);
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageNameNotContainsSomething() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageName does not contain DEFAULT_OPPORTUNITY_STAGE_NAME
        defaultOpportunityStageShouldNotBeFound("opportunityStageName.doesNotContain=" + DEFAULT_OPPORTUNITY_STAGE_NAME);

        // Get all the opportunityStageList where opportunityStageName does not contain UPDATED_OPPORTUNITY_STAGE_NAME
        defaultOpportunityStageShouldBeFound("opportunityStageName.doesNotContain=" + UPDATED_OPPORTUNITY_STAGE_NAME);
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageCode equals to DEFAULT_OPPORTUNITY_STAGE_CODE
        defaultOpportunityStageShouldBeFound("opportunityStageCode.equals=" + DEFAULT_OPPORTUNITY_STAGE_CODE);

        // Get all the opportunityStageList where opportunityStageCode equals to UPDATED_OPPORTUNITY_STAGE_CODE
        defaultOpportunityStageShouldNotBeFound("opportunityStageCode.equals=" + UPDATED_OPPORTUNITY_STAGE_CODE);
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageCode not equals to DEFAULT_OPPORTUNITY_STAGE_CODE
        defaultOpportunityStageShouldNotBeFound("opportunityStageCode.notEquals=" + DEFAULT_OPPORTUNITY_STAGE_CODE);

        // Get all the opportunityStageList where opportunityStageCode not equals to UPDATED_OPPORTUNITY_STAGE_CODE
        defaultOpportunityStageShouldBeFound("opportunityStageCode.notEquals=" + UPDATED_OPPORTUNITY_STAGE_CODE);
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageCodeIsInShouldWork() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageCode in DEFAULT_OPPORTUNITY_STAGE_CODE or UPDATED_OPPORTUNITY_STAGE_CODE
        defaultOpportunityStageShouldBeFound(
            "opportunityStageCode.in=" + DEFAULT_OPPORTUNITY_STAGE_CODE + "," + UPDATED_OPPORTUNITY_STAGE_CODE
        );

        // Get all the opportunityStageList where opportunityStageCode equals to UPDATED_OPPORTUNITY_STAGE_CODE
        defaultOpportunityStageShouldNotBeFound("opportunityStageCode.in=" + UPDATED_OPPORTUNITY_STAGE_CODE);
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageCode is not null
        defaultOpportunityStageShouldBeFound("opportunityStageCode.specified=true");

        // Get all the opportunityStageList where opportunityStageCode is null
        defaultOpportunityStageShouldNotBeFound("opportunityStageCode.specified=false");
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageCodeContainsSomething() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageCode contains DEFAULT_OPPORTUNITY_STAGE_CODE
        defaultOpportunityStageShouldBeFound("opportunityStageCode.contains=" + DEFAULT_OPPORTUNITY_STAGE_CODE);

        // Get all the opportunityStageList where opportunityStageCode contains UPDATED_OPPORTUNITY_STAGE_CODE
        defaultOpportunityStageShouldNotBeFound("opportunityStageCode.contains=" + UPDATED_OPPORTUNITY_STAGE_CODE);
    }

    @Test
    @Transactional
    void getAllOpportunityStagesByOpportunityStageCodeNotContainsSomething() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        // Get all the opportunityStageList where opportunityStageCode does not contain DEFAULT_OPPORTUNITY_STAGE_CODE
        defaultOpportunityStageShouldNotBeFound("opportunityStageCode.doesNotContain=" + DEFAULT_OPPORTUNITY_STAGE_CODE);

        // Get all the opportunityStageList where opportunityStageCode does not contain UPDATED_OPPORTUNITY_STAGE_CODE
        defaultOpportunityStageShouldBeFound("opportunityStageCode.doesNotContain=" + UPDATED_OPPORTUNITY_STAGE_CODE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOpportunityStageShouldBeFound(String filter) throws Exception {
        restOpportunityStageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opportunityStage.getId().intValue())))
            .andExpect(jsonPath("$.[*].opportunityStageId").value(hasItem(DEFAULT_OPPORTUNITY_STAGE_ID)))
            .andExpect(jsonPath("$.[*].opportunityStageName").value(hasItem(DEFAULT_OPPORTUNITY_STAGE_NAME)))
            .andExpect(jsonPath("$.[*].opportunityStageCode").value(hasItem(DEFAULT_OPPORTUNITY_STAGE_CODE)));

        // Check, that the count call also returns 1
        restOpportunityStageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOpportunityStageShouldNotBeFound(String filter) throws Exception {
        restOpportunityStageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOpportunityStageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOpportunityStage() throws Exception {
        // Get the opportunityStage
        restOpportunityStageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOpportunityStage() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        int databaseSizeBeforeUpdate = opportunityStageRepository.findAll().size();

        // Update the opportunityStage
        OpportunityStage updatedOpportunityStage = opportunityStageRepository.findById(opportunityStage.getId()).get();
        // Disconnect from session so that the updates on updatedOpportunityStage are not directly saved in db
        em.detach(updatedOpportunityStage);
        updatedOpportunityStage
            .opportunityStageId(UPDATED_OPPORTUNITY_STAGE_ID)
            .opportunityStageName(UPDATED_OPPORTUNITY_STAGE_NAME)
            .opportunityStageCode(UPDATED_OPPORTUNITY_STAGE_CODE);

        restOpportunityStageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOpportunityStage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOpportunityStage))
            )
            .andExpect(status().isOk());

        // Validate the OpportunityStage in the database
        List<OpportunityStage> opportunityStageList = opportunityStageRepository.findAll();
        assertThat(opportunityStageList).hasSize(databaseSizeBeforeUpdate);
        OpportunityStage testOpportunityStage = opportunityStageList.get(opportunityStageList.size() - 1);
        assertThat(testOpportunityStage.getOpportunityStageId()).isEqualTo(UPDATED_OPPORTUNITY_STAGE_ID);
        assertThat(testOpportunityStage.getOpportunityStageName()).isEqualTo(UPDATED_OPPORTUNITY_STAGE_NAME);
        assertThat(testOpportunityStage.getOpportunityStageCode()).isEqualTo(UPDATED_OPPORTUNITY_STAGE_CODE);
    }

    @Test
    @Transactional
    void putNonExistingOpportunityStage() throws Exception {
        int databaseSizeBeforeUpdate = opportunityStageRepository.findAll().size();
        opportunityStage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpportunityStageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opportunityStage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opportunityStage))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpportunityStage in the database
        List<OpportunityStage> opportunityStageList = opportunityStageRepository.findAll();
        assertThat(opportunityStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOpportunityStage() throws Exception {
        int databaseSizeBeforeUpdate = opportunityStageRepository.findAll().size();
        opportunityStage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpportunityStageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opportunityStage))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpportunityStage in the database
        List<OpportunityStage> opportunityStageList = opportunityStageRepository.findAll();
        assertThat(opportunityStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOpportunityStage() throws Exception {
        int databaseSizeBeforeUpdate = opportunityStageRepository.findAll().size();
        opportunityStage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpportunityStageMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(opportunityStage))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OpportunityStage in the database
        List<OpportunityStage> opportunityStageList = opportunityStageRepository.findAll();
        assertThat(opportunityStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOpportunityStageWithPatch() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        int databaseSizeBeforeUpdate = opportunityStageRepository.findAll().size();

        // Update the opportunityStage using partial update
        OpportunityStage partialUpdatedOpportunityStage = new OpportunityStage();
        partialUpdatedOpportunityStage.setId(opportunityStage.getId());

        partialUpdatedOpportunityStage
            .opportunityStageName(UPDATED_OPPORTUNITY_STAGE_NAME)
            .opportunityStageCode(UPDATED_OPPORTUNITY_STAGE_CODE);

        restOpportunityStageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpportunityStage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOpportunityStage))
            )
            .andExpect(status().isOk());

        // Validate the OpportunityStage in the database
        List<OpportunityStage> opportunityStageList = opportunityStageRepository.findAll();
        assertThat(opportunityStageList).hasSize(databaseSizeBeforeUpdate);
        OpportunityStage testOpportunityStage = opportunityStageList.get(opportunityStageList.size() - 1);
        assertThat(testOpportunityStage.getOpportunityStageId()).isEqualTo(DEFAULT_OPPORTUNITY_STAGE_ID);
        assertThat(testOpportunityStage.getOpportunityStageName()).isEqualTo(UPDATED_OPPORTUNITY_STAGE_NAME);
        assertThat(testOpportunityStage.getOpportunityStageCode()).isEqualTo(UPDATED_OPPORTUNITY_STAGE_CODE);
    }

    @Test
    @Transactional
    void fullUpdateOpportunityStageWithPatch() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        int databaseSizeBeforeUpdate = opportunityStageRepository.findAll().size();

        // Update the opportunityStage using partial update
        OpportunityStage partialUpdatedOpportunityStage = new OpportunityStage();
        partialUpdatedOpportunityStage.setId(opportunityStage.getId());

        partialUpdatedOpportunityStage
            .opportunityStageId(UPDATED_OPPORTUNITY_STAGE_ID)
            .opportunityStageName(UPDATED_OPPORTUNITY_STAGE_NAME)
            .opportunityStageCode(UPDATED_OPPORTUNITY_STAGE_CODE);

        restOpportunityStageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpportunityStage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOpportunityStage))
            )
            .andExpect(status().isOk());

        // Validate the OpportunityStage in the database
        List<OpportunityStage> opportunityStageList = opportunityStageRepository.findAll();
        assertThat(opportunityStageList).hasSize(databaseSizeBeforeUpdate);
        OpportunityStage testOpportunityStage = opportunityStageList.get(opportunityStageList.size() - 1);
        assertThat(testOpportunityStage.getOpportunityStageId()).isEqualTo(UPDATED_OPPORTUNITY_STAGE_ID);
        assertThat(testOpportunityStage.getOpportunityStageName()).isEqualTo(UPDATED_OPPORTUNITY_STAGE_NAME);
        assertThat(testOpportunityStage.getOpportunityStageCode()).isEqualTo(UPDATED_OPPORTUNITY_STAGE_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingOpportunityStage() throws Exception {
        int databaseSizeBeforeUpdate = opportunityStageRepository.findAll().size();
        opportunityStage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpportunityStageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, opportunityStage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opportunityStage))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpportunityStage in the database
        List<OpportunityStage> opportunityStageList = opportunityStageRepository.findAll();
        assertThat(opportunityStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOpportunityStage() throws Exception {
        int databaseSizeBeforeUpdate = opportunityStageRepository.findAll().size();
        opportunityStage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpportunityStageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opportunityStage))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpportunityStage in the database
        List<OpportunityStage> opportunityStageList = opportunityStageRepository.findAll();
        assertThat(opportunityStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOpportunityStage() throws Exception {
        int databaseSizeBeforeUpdate = opportunityStageRepository.findAll().size();
        opportunityStage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpportunityStageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opportunityStage))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OpportunityStage in the database
        List<OpportunityStage> opportunityStageList = opportunityStageRepository.findAll();
        assertThat(opportunityStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOpportunityStage() throws Exception {
        // Initialize the database
        opportunityStageRepository.saveAndFlush(opportunityStage);

        int databaseSizeBeforeDelete = opportunityStageRepository.findAll().size();

        // Delete the opportunityStage
        restOpportunityStageMockMvc
            .perform(delete(ENTITY_API_URL_ID, opportunityStage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OpportunityStage> opportunityStageList = opportunityStageRepository.findAll();
        assertThat(opportunityStageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
