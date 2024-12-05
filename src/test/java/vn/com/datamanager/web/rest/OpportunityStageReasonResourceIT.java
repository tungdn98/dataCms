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
import vn.com.datamanager.domain.OpportunityStageReason;
import vn.com.datamanager.repository.OpportunityStageReasonRepository;
import vn.com.datamanager.service.criteria.OpportunityStageReasonCriteria;

/**
 * Integration tests for the {@link OpportunityStageReasonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OpportunityStageReasonResourceIT {

    private static final String DEFAULT_OPPORTUNITY_STAGE_REASON_ID = "AAAAAAAAAA";
    private static final String UPDATED_OPPORTUNITY_STAGE_REASON_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OPPORTUNITY_STAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_OPPORTUNITY_STAGE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OPPORTUNITY_STAGE_REASON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OPPORTUNITY_STAGE_REASON_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/opportunity-stage-reasons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OpportunityStageReasonRepository opportunityStageReasonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpportunityStageReasonMockMvc;

    private OpportunityStageReason opportunityStageReason;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpportunityStageReason createEntity(EntityManager em) {
        OpportunityStageReason opportunityStageReason = new OpportunityStageReason()
            .opportunityStageReasonId(DEFAULT_OPPORTUNITY_STAGE_REASON_ID)
            .opportunityStageId(DEFAULT_OPPORTUNITY_STAGE_ID)
            .opportunityStageReasonName(DEFAULT_OPPORTUNITY_STAGE_REASON_NAME);
        return opportunityStageReason;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpportunityStageReason createUpdatedEntity(EntityManager em) {
        OpportunityStageReason opportunityStageReason = new OpportunityStageReason()
            .opportunityStageReasonId(UPDATED_OPPORTUNITY_STAGE_REASON_ID)
            .opportunityStageId(UPDATED_OPPORTUNITY_STAGE_ID)
            .opportunityStageReasonName(UPDATED_OPPORTUNITY_STAGE_REASON_NAME);
        return opportunityStageReason;
    }

    @BeforeEach
    public void initTest() {
        opportunityStageReason = createEntity(em);
    }

    @Test
    @Transactional
    void createOpportunityStageReason() throws Exception {
        int databaseSizeBeforeCreate = opportunityStageReasonRepository.findAll().size();
        // Create the OpportunityStageReason
        restOpportunityStageReasonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opportunityStageReason))
            )
            .andExpect(status().isCreated());

        // Validate the OpportunityStageReason in the database
        List<OpportunityStageReason> opportunityStageReasonList = opportunityStageReasonRepository.findAll();
        assertThat(opportunityStageReasonList).hasSize(databaseSizeBeforeCreate + 1);
        OpportunityStageReason testOpportunityStageReason = opportunityStageReasonList.get(opportunityStageReasonList.size() - 1);
        assertThat(testOpportunityStageReason.getOpportunityStageReasonId()).isEqualTo(DEFAULT_OPPORTUNITY_STAGE_REASON_ID);
        assertThat(testOpportunityStageReason.getOpportunityStageId()).isEqualTo(DEFAULT_OPPORTUNITY_STAGE_ID);
        assertThat(testOpportunityStageReason.getOpportunityStageReasonName()).isEqualTo(DEFAULT_OPPORTUNITY_STAGE_REASON_NAME);
    }

    @Test
    @Transactional
    void createOpportunityStageReasonWithExistingId() throws Exception {
        // Create the OpportunityStageReason with an existing ID
        opportunityStageReason.setId(1L);

        int databaseSizeBeforeCreate = opportunityStageReasonRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpportunityStageReasonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opportunityStageReason))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpportunityStageReason in the database
        List<OpportunityStageReason> opportunityStageReasonList = opportunityStageReasonRepository.findAll();
        assertThat(opportunityStageReasonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasons() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList
        restOpportunityStageReasonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opportunityStageReason.getId().intValue())))
            .andExpect(jsonPath("$.[*].opportunityStageReasonId").value(hasItem(DEFAULT_OPPORTUNITY_STAGE_REASON_ID)))
            .andExpect(jsonPath("$.[*].opportunityStageId").value(hasItem(DEFAULT_OPPORTUNITY_STAGE_ID)))
            .andExpect(jsonPath("$.[*].opportunityStageReasonName").value(hasItem(DEFAULT_OPPORTUNITY_STAGE_REASON_NAME)));
    }

    @Test
    @Transactional
    void getOpportunityStageReason() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get the opportunityStageReason
        restOpportunityStageReasonMockMvc
            .perform(get(ENTITY_API_URL_ID, opportunityStageReason.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(opportunityStageReason.getId().intValue()))
            .andExpect(jsonPath("$.opportunityStageReasonId").value(DEFAULT_OPPORTUNITY_STAGE_REASON_ID))
            .andExpect(jsonPath("$.opportunityStageId").value(DEFAULT_OPPORTUNITY_STAGE_ID))
            .andExpect(jsonPath("$.opportunityStageReasonName").value(DEFAULT_OPPORTUNITY_STAGE_REASON_NAME));
    }

    @Test
    @Transactional
    void getOpportunityStageReasonsByIdFiltering() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        Long id = opportunityStageReason.getId();

        defaultOpportunityStageReasonShouldBeFound("id.equals=" + id);
        defaultOpportunityStageReasonShouldNotBeFound("id.notEquals=" + id);

        defaultOpportunityStageReasonShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOpportunityStageReasonShouldNotBeFound("id.greaterThan=" + id);

        defaultOpportunityStageReasonShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOpportunityStageReasonShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageReasonIdIsEqualToSomething() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageReasonId equals to DEFAULT_OPPORTUNITY_STAGE_REASON_ID
        defaultOpportunityStageReasonShouldBeFound("opportunityStageReasonId.equals=" + DEFAULT_OPPORTUNITY_STAGE_REASON_ID);

        // Get all the opportunityStageReasonList where opportunityStageReasonId equals to UPDATED_OPPORTUNITY_STAGE_REASON_ID
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageReasonId.equals=" + UPDATED_OPPORTUNITY_STAGE_REASON_ID);
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageReasonIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageReasonId not equals to DEFAULT_OPPORTUNITY_STAGE_REASON_ID
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageReasonId.notEquals=" + DEFAULT_OPPORTUNITY_STAGE_REASON_ID);

        // Get all the opportunityStageReasonList where opportunityStageReasonId not equals to UPDATED_OPPORTUNITY_STAGE_REASON_ID
        defaultOpportunityStageReasonShouldBeFound("opportunityStageReasonId.notEquals=" + UPDATED_OPPORTUNITY_STAGE_REASON_ID);
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageReasonIdIsInShouldWork() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageReasonId in DEFAULT_OPPORTUNITY_STAGE_REASON_ID or UPDATED_OPPORTUNITY_STAGE_REASON_ID
        defaultOpportunityStageReasonShouldBeFound(
            "opportunityStageReasonId.in=" + DEFAULT_OPPORTUNITY_STAGE_REASON_ID + "," + UPDATED_OPPORTUNITY_STAGE_REASON_ID
        );

        // Get all the opportunityStageReasonList where opportunityStageReasonId equals to UPDATED_OPPORTUNITY_STAGE_REASON_ID
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageReasonId.in=" + UPDATED_OPPORTUNITY_STAGE_REASON_ID);
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageReasonIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageReasonId is not null
        defaultOpportunityStageReasonShouldBeFound("opportunityStageReasonId.specified=true");

        // Get all the opportunityStageReasonList where opportunityStageReasonId is null
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageReasonId.specified=false");
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageReasonIdContainsSomething() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageReasonId contains DEFAULT_OPPORTUNITY_STAGE_REASON_ID
        defaultOpportunityStageReasonShouldBeFound("opportunityStageReasonId.contains=" + DEFAULT_OPPORTUNITY_STAGE_REASON_ID);

        // Get all the opportunityStageReasonList where opportunityStageReasonId contains UPDATED_OPPORTUNITY_STAGE_REASON_ID
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageReasonId.contains=" + UPDATED_OPPORTUNITY_STAGE_REASON_ID);
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageReasonIdNotContainsSomething() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageReasonId does not contain DEFAULT_OPPORTUNITY_STAGE_REASON_ID
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageReasonId.doesNotContain=" + DEFAULT_OPPORTUNITY_STAGE_REASON_ID);

        // Get all the opportunityStageReasonList where opportunityStageReasonId does not contain UPDATED_OPPORTUNITY_STAGE_REASON_ID
        defaultOpportunityStageReasonShouldBeFound("opportunityStageReasonId.doesNotContain=" + UPDATED_OPPORTUNITY_STAGE_REASON_ID);
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageIdIsEqualToSomething() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageId equals to DEFAULT_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageReasonShouldBeFound("opportunityStageId.equals=" + DEFAULT_OPPORTUNITY_STAGE_ID);

        // Get all the opportunityStageReasonList where opportunityStageId equals to UPDATED_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageId.equals=" + UPDATED_OPPORTUNITY_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageId not equals to DEFAULT_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageId.notEquals=" + DEFAULT_OPPORTUNITY_STAGE_ID);

        // Get all the opportunityStageReasonList where opportunityStageId not equals to UPDATED_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageReasonShouldBeFound("opportunityStageId.notEquals=" + UPDATED_OPPORTUNITY_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageIdIsInShouldWork() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageId in DEFAULT_OPPORTUNITY_STAGE_ID or UPDATED_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageReasonShouldBeFound(
            "opportunityStageId.in=" + DEFAULT_OPPORTUNITY_STAGE_ID + "," + UPDATED_OPPORTUNITY_STAGE_ID
        );

        // Get all the opportunityStageReasonList where opportunityStageId equals to UPDATED_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageId.in=" + UPDATED_OPPORTUNITY_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageId is not null
        defaultOpportunityStageReasonShouldBeFound("opportunityStageId.specified=true");

        // Get all the opportunityStageReasonList where opportunityStageId is null
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageId.specified=false");
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageIdContainsSomething() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageId contains DEFAULT_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageReasonShouldBeFound("opportunityStageId.contains=" + DEFAULT_OPPORTUNITY_STAGE_ID);

        // Get all the opportunityStageReasonList where opportunityStageId contains UPDATED_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageId.contains=" + UPDATED_OPPORTUNITY_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageIdNotContainsSomething() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageId does not contain DEFAULT_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageId.doesNotContain=" + DEFAULT_OPPORTUNITY_STAGE_ID);

        // Get all the opportunityStageReasonList where opportunityStageId does not contain UPDATED_OPPORTUNITY_STAGE_ID
        defaultOpportunityStageReasonShouldBeFound("opportunityStageId.doesNotContain=" + UPDATED_OPPORTUNITY_STAGE_ID);
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageReasonNameIsEqualToSomething() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageReasonName equals to DEFAULT_OPPORTUNITY_STAGE_REASON_NAME
        defaultOpportunityStageReasonShouldBeFound("opportunityStageReasonName.equals=" + DEFAULT_OPPORTUNITY_STAGE_REASON_NAME);

        // Get all the opportunityStageReasonList where opportunityStageReasonName equals to UPDATED_OPPORTUNITY_STAGE_REASON_NAME
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageReasonName.equals=" + UPDATED_OPPORTUNITY_STAGE_REASON_NAME);
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageReasonNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageReasonName not equals to DEFAULT_OPPORTUNITY_STAGE_REASON_NAME
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageReasonName.notEquals=" + DEFAULT_OPPORTUNITY_STAGE_REASON_NAME);

        // Get all the opportunityStageReasonList where opportunityStageReasonName not equals to UPDATED_OPPORTUNITY_STAGE_REASON_NAME
        defaultOpportunityStageReasonShouldBeFound("opportunityStageReasonName.notEquals=" + UPDATED_OPPORTUNITY_STAGE_REASON_NAME);
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageReasonNameIsInShouldWork() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageReasonName in DEFAULT_OPPORTUNITY_STAGE_REASON_NAME or UPDATED_OPPORTUNITY_STAGE_REASON_NAME
        defaultOpportunityStageReasonShouldBeFound(
            "opportunityStageReasonName.in=" + DEFAULT_OPPORTUNITY_STAGE_REASON_NAME + "," + UPDATED_OPPORTUNITY_STAGE_REASON_NAME
        );

        // Get all the opportunityStageReasonList where opportunityStageReasonName equals to UPDATED_OPPORTUNITY_STAGE_REASON_NAME
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageReasonName.in=" + UPDATED_OPPORTUNITY_STAGE_REASON_NAME);
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageReasonNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageReasonName is not null
        defaultOpportunityStageReasonShouldBeFound("opportunityStageReasonName.specified=true");

        // Get all the opportunityStageReasonList where opportunityStageReasonName is null
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageReasonName.specified=false");
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageReasonNameContainsSomething() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageReasonName contains DEFAULT_OPPORTUNITY_STAGE_REASON_NAME
        defaultOpportunityStageReasonShouldBeFound("opportunityStageReasonName.contains=" + DEFAULT_OPPORTUNITY_STAGE_REASON_NAME);

        // Get all the opportunityStageReasonList where opportunityStageReasonName contains UPDATED_OPPORTUNITY_STAGE_REASON_NAME
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageReasonName.contains=" + UPDATED_OPPORTUNITY_STAGE_REASON_NAME);
    }

    @Test
    @Transactional
    void getAllOpportunityStageReasonsByOpportunityStageReasonNameNotContainsSomething() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        // Get all the opportunityStageReasonList where opportunityStageReasonName does not contain DEFAULT_OPPORTUNITY_STAGE_REASON_NAME
        defaultOpportunityStageReasonShouldNotBeFound("opportunityStageReasonName.doesNotContain=" + DEFAULT_OPPORTUNITY_STAGE_REASON_NAME);

        // Get all the opportunityStageReasonList where opportunityStageReasonName does not contain UPDATED_OPPORTUNITY_STAGE_REASON_NAME
        defaultOpportunityStageReasonShouldBeFound("opportunityStageReasonName.doesNotContain=" + UPDATED_OPPORTUNITY_STAGE_REASON_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOpportunityStageReasonShouldBeFound(String filter) throws Exception {
        restOpportunityStageReasonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opportunityStageReason.getId().intValue())))
            .andExpect(jsonPath("$.[*].opportunityStageReasonId").value(hasItem(DEFAULT_OPPORTUNITY_STAGE_REASON_ID)))
            .andExpect(jsonPath("$.[*].opportunityStageId").value(hasItem(DEFAULT_OPPORTUNITY_STAGE_ID)))
            .andExpect(jsonPath("$.[*].opportunityStageReasonName").value(hasItem(DEFAULT_OPPORTUNITY_STAGE_REASON_NAME)));

        // Check, that the count call also returns 1
        restOpportunityStageReasonMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOpportunityStageReasonShouldNotBeFound(String filter) throws Exception {
        restOpportunityStageReasonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOpportunityStageReasonMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOpportunityStageReason() throws Exception {
        // Get the opportunityStageReason
        restOpportunityStageReasonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOpportunityStageReason() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        int databaseSizeBeforeUpdate = opportunityStageReasonRepository.findAll().size();

        // Update the opportunityStageReason
        OpportunityStageReason updatedOpportunityStageReason = opportunityStageReasonRepository
            .findById(opportunityStageReason.getId())
            .get();
        // Disconnect from session so that the updates on updatedOpportunityStageReason are not directly saved in db
        em.detach(updatedOpportunityStageReason);
        updatedOpportunityStageReason
            .opportunityStageReasonId(UPDATED_OPPORTUNITY_STAGE_REASON_ID)
            .opportunityStageId(UPDATED_OPPORTUNITY_STAGE_ID)
            .opportunityStageReasonName(UPDATED_OPPORTUNITY_STAGE_REASON_NAME);

        restOpportunityStageReasonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOpportunityStageReason.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOpportunityStageReason))
            )
            .andExpect(status().isOk());

        // Validate the OpportunityStageReason in the database
        List<OpportunityStageReason> opportunityStageReasonList = opportunityStageReasonRepository.findAll();
        assertThat(opportunityStageReasonList).hasSize(databaseSizeBeforeUpdate);
        OpportunityStageReason testOpportunityStageReason = opportunityStageReasonList.get(opportunityStageReasonList.size() - 1);
        assertThat(testOpportunityStageReason.getOpportunityStageReasonId()).isEqualTo(UPDATED_OPPORTUNITY_STAGE_REASON_ID);
        assertThat(testOpportunityStageReason.getOpportunityStageId()).isEqualTo(UPDATED_OPPORTUNITY_STAGE_ID);
        assertThat(testOpportunityStageReason.getOpportunityStageReasonName()).isEqualTo(UPDATED_OPPORTUNITY_STAGE_REASON_NAME);
    }

    @Test
    @Transactional
    void putNonExistingOpportunityStageReason() throws Exception {
        int databaseSizeBeforeUpdate = opportunityStageReasonRepository.findAll().size();
        opportunityStageReason.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpportunityStageReasonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opportunityStageReason.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opportunityStageReason))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpportunityStageReason in the database
        List<OpportunityStageReason> opportunityStageReasonList = opportunityStageReasonRepository.findAll();
        assertThat(opportunityStageReasonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOpportunityStageReason() throws Exception {
        int databaseSizeBeforeUpdate = opportunityStageReasonRepository.findAll().size();
        opportunityStageReason.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpportunityStageReasonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opportunityStageReason))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpportunityStageReason in the database
        List<OpportunityStageReason> opportunityStageReasonList = opportunityStageReasonRepository.findAll();
        assertThat(opportunityStageReasonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOpportunityStageReason() throws Exception {
        int databaseSizeBeforeUpdate = opportunityStageReasonRepository.findAll().size();
        opportunityStageReason.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpportunityStageReasonMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opportunityStageReason))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OpportunityStageReason in the database
        List<OpportunityStageReason> opportunityStageReasonList = opportunityStageReasonRepository.findAll();
        assertThat(opportunityStageReasonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOpportunityStageReasonWithPatch() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        int databaseSizeBeforeUpdate = opportunityStageReasonRepository.findAll().size();

        // Update the opportunityStageReason using partial update
        OpportunityStageReason partialUpdatedOpportunityStageReason = new OpportunityStageReason();
        partialUpdatedOpportunityStageReason.setId(opportunityStageReason.getId());

        restOpportunityStageReasonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpportunityStageReason.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOpportunityStageReason))
            )
            .andExpect(status().isOk());

        // Validate the OpportunityStageReason in the database
        List<OpportunityStageReason> opportunityStageReasonList = opportunityStageReasonRepository.findAll();
        assertThat(opportunityStageReasonList).hasSize(databaseSizeBeforeUpdate);
        OpportunityStageReason testOpportunityStageReason = opportunityStageReasonList.get(opportunityStageReasonList.size() - 1);
        assertThat(testOpportunityStageReason.getOpportunityStageReasonId()).isEqualTo(DEFAULT_OPPORTUNITY_STAGE_REASON_ID);
        assertThat(testOpportunityStageReason.getOpportunityStageId()).isEqualTo(DEFAULT_OPPORTUNITY_STAGE_ID);
        assertThat(testOpportunityStageReason.getOpportunityStageReasonName()).isEqualTo(DEFAULT_OPPORTUNITY_STAGE_REASON_NAME);
    }

    @Test
    @Transactional
    void fullUpdateOpportunityStageReasonWithPatch() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        int databaseSizeBeforeUpdate = opportunityStageReasonRepository.findAll().size();

        // Update the opportunityStageReason using partial update
        OpportunityStageReason partialUpdatedOpportunityStageReason = new OpportunityStageReason();
        partialUpdatedOpportunityStageReason.setId(opportunityStageReason.getId());

        partialUpdatedOpportunityStageReason
            .opportunityStageReasonId(UPDATED_OPPORTUNITY_STAGE_REASON_ID)
            .opportunityStageId(UPDATED_OPPORTUNITY_STAGE_ID)
            .opportunityStageReasonName(UPDATED_OPPORTUNITY_STAGE_REASON_NAME);

        restOpportunityStageReasonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpportunityStageReason.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOpportunityStageReason))
            )
            .andExpect(status().isOk());

        // Validate the OpportunityStageReason in the database
        List<OpportunityStageReason> opportunityStageReasonList = opportunityStageReasonRepository.findAll();
        assertThat(opportunityStageReasonList).hasSize(databaseSizeBeforeUpdate);
        OpportunityStageReason testOpportunityStageReason = opportunityStageReasonList.get(opportunityStageReasonList.size() - 1);
        assertThat(testOpportunityStageReason.getOpportunityStageReasonId()).isEqualTo(UPDATED_OPPORTUNITY_STAGE_REASON_ID);
        assertThat(testOpportunityStageReason.getOpportunityStageId()).isEqualTo(UPDATED_OPPORTUNITY_STAGE_ID);
        assertThat(testOpportunityStageReason.getOpportunityStageReasonName()).isEqualTo(UPDATED_OPPORTUNITY_STAGE_REASON_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingOpportunityStageReason() throws Exception {
        int databaseSizeBeforeUpdate = opportunityStageReasonRepository.findAll().size();
        opportunityStageReason.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpportunityStageReasonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, opportunityStageReason.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opportunityStageReason))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpportunityStageReason in the database
        List<OpportunityStageReason> opportunityStageReasonList = opportunityStageReasonRepository.findAll();
        assertThat(opportunityStageReasonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOpportunityStageReason() throws Exception {
        int databaseSizeBeforeUpdate = opportunityStageReasonRepository.findAll().size();
        opportunityStageReason.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpportunityStageReasonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opportunityStageReason))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpportunityStageReason in the database
        List<OpportunityStageReason> opportunityStageReasonList = opportunityStageReasonRepository.findAll();
        assertThat(opportunityStageReasonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOpportunityStageReason() throws Exception {
        int databaseSizeBeforeUpdate = opportunityStageReasonRepository.findAll().size();
        opportunityStageReason.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpportunityStageReasonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opportunityStageReason))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OpportunityStageReason in the database
        List<OpportunityStageReason> opportunityStageReasonList = opportunityStageReasonRepository.findAll();
        assertThat(opportunityStageReasonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOpportunityStageReason() throws Exception {
        // Initialize the database
        opportunityStageReasonRepository.saveAndFlush(opportunityStageReason);

        int databaseSizeBeforeDelete = opportunityStageReasonRepository.findAll().size();

        // Delete the opportunityStageReason
        restOpportunityStageReasonMockMvc
            .perform(delete(ENTITY_API_URL_ID, opportunityStageReason.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OpportunityStageReason> opportunityStageReasonList = opportunityStageReasonRepository.findAll();
        assertThat(opportunityStageReasonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
