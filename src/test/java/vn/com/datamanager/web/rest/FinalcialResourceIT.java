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
import vn.com.datamanager.domain.Finalcial;
import vn.com.datamanager.repository.FinalcialRepository;
import vn.com.datamanager.service.criteria.FinalcialCriteria;

/**
 * Integration tests for the {@link FinalcialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FinalcialResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/finalcials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FinalcialRepository finalcialRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFinalcialMockMvc;

    private Finalcial finalcial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Finalcial createEntity(EntityManager em) {
        Finalcial finalcial = new Finalcial()
            .code(DEFAULT_CODE)
            .customerName(DEFAULT_CUSTOMER_NAME)
            .customerShortName(DEFAULT_CUSTOMER_SHORT_NAME)
            .customerType(DEFAULT_CUSTOMER_TYPE);
        return finalcial;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Finalcial createUpdatedEntity(EntityManager em) {
        Finalcial finalcial = new Finalcial()
            .code(UPDATED_CODE)
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerShortName(UPDATED_CUSTOMER_SHORT_NAME)
            .customerType(UPDATED_CUSTOMER_TYPE);
        return finalcial;
    }

    @BeforeEach
    public void initTest() {
        finalcial = createEntity(em);
    }

    @Test
    @Transactional
    void createFinalcial() throws Exception {
        int databaseSizeBeforeCreate = finalcialRepository.findAll().size();
        // Create the Finalcial
        restFinalcialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(finalcial)))
            .andExpect(status().isCreated());

        // Validate the Finalcial in the database
        List<Finalcial> finalcialList = finalcialRepository.findAll();
        assertThat(finalcialList).hasSize(databaseSizeBeforeCreate + 1);
        Finalcial testFinalcial = finalcialList.get(finalcialList.size() - 1);
        assertThat(testFinalcial.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFinalcial.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testFinalcial.getCustomerShortName()).isEqualTo(DEFAULT_CUSTOMER_SHORT_NAME);
        assertThat(testFinalcial.getCustomerType()).isEqualTo(DEFAULT_CUSTOMER_TYPE);
    }

    @Test
    @Transactional
    void createFinalcialWithExistingId() throws Exception {
        // Create the Finalcial with an existing ID
        finalcial.setId(1L);

        int databaseSizeBeforeCreate = finalcialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinalcialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(finalcial)))
            .andExpect(status().isBadRequest());

        // Validate the Finalcial in the database
        List<Finalcial> finalcialList = finalcialRepository.findAll();
        assertThat(finalcialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFinalcials() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList
        restFinalcialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(finalcial.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].customerShortName").value(hasItem(DEFAULT_CUSTOMER_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].customerType").value(hasItem(DEFAULT_CUSTOMER_TYPE)));
    }

    @Test
    @Transactional
    void getFinalcial() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get the finalcial
        restFinalcialMockMvc
            .perform(get(ENTITY_API_URL_ID, finalcial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(finalcial.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME))
            .andExpect(jsonPath("$.customerShortName").value(DEFAULT_CUSTOMER_SHORT_NAME))
            .andExpect(jsonPath("$.customerType").value(DEFAULT_CUSTOMER_TYPE));
    }

    @Test
    @Transactional
    void getFinalcialsByIdFiltering() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        Long id = finalcial.getId();

        defaultFinalcialShouldBeFound("id.equals=" + id);
        defaultFinalcialShouldNotBeFound("id.notEquals=" + id);

        defaultFinalcialShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFinalcialShouldNotBeFound("id.greaterThan=" + id);

        defaultFinalcialShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFinalcialShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where code equals to DEFAULT_CODE
        defaultFinalcialShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the finalcialList where code equals to UPDATED_CODE
        defaultFinalcialShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where code not equals to DEFAULT_CODE
        defaultFinalcialShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the finalcialList where code not equals to UPDATED_CODE
        defaultFinalcialShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where code in DEFAULT_CODE or UPDATED_CODE
        defaultFinalcialShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the finalcialList where code equals to UPDATED_CODE
        defaultFinalcialShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where code is not null
        defaultFinalcialShouldBeFound("code.specified=true");

        // Get all the finalcialList where code is null
        defaultFinalcialShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllFinalcialsByCodeContainsSomething() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where code contains DEFAULT_CODE
        defaultFinalcialShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the finalcialList where code contains UPDATED_CODE
        defaultFinalcialShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where code does not contain DEFAULT_CODE
        defaultFinalcialShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the finalcialList where code does not contain UPDATED_CODE
        defaultFinalcialShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerName equals to DEFAULT_CUSTOMER_NAME
        defaultFinalcialShouldBeFound("customerName.equals=" + DEFAULT_CUSTOMER_NAME);

        // Get all the finalcialList where customerName equals to UPDATED_CUSTOMER_NAME
        defaultFinalcialShouldNotBeFound("customerName.equals=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerName not equals to DEFAULT_CUSTOMER_NAME
        defaultFinalcialShouldNotBeFound("customerName.notEquals=" + DEFAULT_CUSTOMER_NAME);

        // Get all the finalcialList where customerName not equals to UPDATED_CUSTOMER_NAME
        defaultFinalcialShouldBeFound("customerName.notEquals=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerNameIsInShouldWork() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerName in DEFAULT_CUSTOMER_NAME or UPDATED_CUSTOMER_NAME
        defaultFinalcialShouldBeFound("customerName.in=" + DEFAULT_CUSTOMER_NAME + "," + UPDATED_CUSTOMER_NAME);

        // Get all the finalcialList where customerName equals to UPDATED_CUSTOMER_NAME
        defaultFinalcialShouldNotBeFound("customerName.in=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerName is not null
        defaultFinalcialShouldBeFound("customerName.specified=true");

        // Get all the finalcialList where customerName is null
        defaultFinalcialShouldNotBeFound("customerName.specified=false");
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerNameContainsSomething() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerName contains DEFAULT_CUSTOMER_NAME
        defaultFinalcialShouldBeFound("customerName.contains=" + DEFAULT_CUSTOMER_NAME);

        // Get all the finalcialList where customerName contains UPDATED_CUSTOMER_NAME
        defaultFinalcialShouldNotBeFound("customerName.contains=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerNameNotContainsSomething() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerName does not contain DEFAULT_CUSTOMER_NAME
        defaultFinalcialShouldNotBeFound("customerName.doesNotContain=" + DEFAULT_CUSTOMER_NAME);

        // Get all the finalcialList where customerName does not contain UPDATED_CUSTOMER_NAME
        defaultFinalcialShouldBeFound("customerName.doesNotContain=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerShortNameIsEqualToSomething() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerShortName equals to DEFAULT_CUSTOMER_SHORT_NAME
        defaultFinalcialShouldBeFound("customerShortName.equals=" + DEFAULT_CUSTOMER_SHORT_NAME);

        // Get all the finalcialList where customerShortName equals to UPDATED_CUSTOMER_SHORT_NAME
        defaultFinalcialShouldNotBeFound("customerShortName.equals=" + UPDATED_CUSTOMER_SHORT_NAME);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerShortNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerShortName not equals to DEFAULT_CUSTOMER_SHORT_NAME
        defaultFinalcialShouldNotBeFound("customerShortName.notEquals=" + DEFAULT_CUSTOMER_SHORT_NAME);

        // Get all the finalcialList where customerShortName not equals to UPDATED_CUSTOMER_SHORT_NAME
        defaultFinalcialShouldBeFound("customerShortName.notEquals=" + UPDATED_CUSTOMER_SHORT_NAME);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerShortNameIsInShouldWork() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerShortName in DEFAULT_CUSTOMER_SHORT_NAME or UPDATED_CUSTOMER_SHORT_NAME
        defaultFinalcialShouldBeFound("customerShortName.in=" + DEFAULT_CUSTOMER_SHORT_NAME + "," + UPDATED_CUSTOMER_SHORT_NAME);

        // Get all the finalcialList where customerShortName equals to UPDATED_CUSTOMER_SHORT_NAME
        defaultFinalcialShouldNotBeFound("customerShortName.in=" + UPDATED_CUSTOMER_SHORT_NAME);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerShortNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerShortName is not null
        defaultFinalcialShouldBeFound("customerShortName.specified=true");

        // Get all the finalcialList where customerShortName is null
        defaultFinalcialShouldNotBeFound("customerShortName.specified=false");
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerShortNameContainsSomething() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerShortName contains DEFAULT_CUSTOMER_SHORT_NAME
        defaultFinalcialShouldBeFound("customerShortName.contains=" + DEFAULT_CUSTOMER_SHORT_NAME);

        // Get all the finalcialList where customerShortName contains UPDATED_CUSTOMER_SHORT_NAME
        defaultFinalcialShouldNotBeFound("customerShortName.contains=" + UPDATED_CUSTOMER_SHORT_NAME);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerShortNameNotContainsSomething() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerShortName does not contain DEFAULT_CUSTOMER_SHORT_NAME
        defaultFinalcialShouldNotBeFound("customerShortName.doesNotContain=" + DEFAULT_CUSTOMER_SHORT_NAME);

        // Get all the finalcialList where customerShortName does not contain UPDATED_CUSTOMER_SHORT_NAME
        defaultFinalcialShouldBeFound("customerShortName.doesNotContain=" + UPDATED_CUSTOMER_SHORT_NAME);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerType equals to DEFAULT_CUSTOMER_TYPE
        defaultFinalcialShouldBeFound("customerType.equals=" + DEFAULT_CUSTOMER_TYPE);

        // Get all the finalcialList where customerType equals to UPDATED_CUSTOMER_TYPE
        defaultFinalcialShouldNotBeFound("customerType.equals=" + UPDATED_CUSTOMER_TYPE);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerType not equals to DEFAULT_CUSTOMER_TYPE
        defaultFinalcialShouldNotBeFound("customerType.notEquals=" + DEFAULT_CUSTOMER_TYPE);

        // Get all the finalcialList where customerType not equals to UPDATED_CUSTOMER_TYPE
        defaultFinalcialShouldBeFound("customerType.notEquals=" + UPDATED_CUSTOMER_TYPE);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerTypeIsInShouldWork() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerType in DEFAULT_CUSTOMER_TYPE or UPDATED_CUSTOMER_TYPE
        defaultFinalcialShouldBeFound("customerType.in=" + DEFAULT_CUSTOMER_TYPE + "," + UPDATED_CUSTOMER_TYPE);

        // Get all the finalcialList where customerType equals to UPDATED_CUSTOMER_TYPE
        defaultFinalcialShouldNotBeFound("customerType.in=" + UPDATED_CUSTOMER_TYPE);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerType is not null
        defaultFinalcialShouldBeFound("customerType.specified=true");

        // Get all the finalcialList where customerType is null
        defaultFinalcialShouldNotBeFound("customerType.specified=false");
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerTypeContainsSomething() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerType contains DEFAULT_CUSTOMER_TYPE
        defaultFinalcialShouldBeFound("customerType.contains=" + DEFAULT_CUSTOMER_TYPE);

        // Get all the finalcialList where customerType contains UPDATED_CUSTOMER_TYPE
        defaultFinalcialShouldNotBeFound("customerType.contains=" + UPDATED_CUSTOMER_TYPE);
    }

    @Test
    @Transactional
    void getAllFinalcialsByCustomerTypeNotContainsSomething() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        // Get all the finalcialList where customerType does not contain DEFAULT_CUSTOMER_TYPE
        defaultFinalcialShouldNotBeFound("customerType.doesNotContain=" + DEFAULT_CUSTOMER_TYPE);

        // Get all the finalcialList where customerType does not contain UPDATED_CUSTOMER_TYPE
        defaultFinalcialShouldBeFound("customerType.doesNotContain=" + UPDATED_CUSTOMER_TYPE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFinalcialShouldBeFound(String filter) throws Exception {
        restFinalcialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(finalcial.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].customerShortName").value(hasItem(DEFAULT_CUSTOMER_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].customerType").value(hasItem(DEFAULT_CUSTOMER_TYPE)));

        // Check, that the count call also returns 1
        restFinalcialMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFinalcialShouldNotBeFound(String filter) throws Exception {
        restFinalcialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFinalcialMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFinalcial() throws Exception {
        // Get the finalcial
        restFinalcialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFinalcial() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        int databaseSizeBeforeUpdate = finalcialRepository.findAll().size();

        // Update the finalcial
        Finalcial updatedFinalcial = finalcialRepository.findById(finalcial.getId()).get();
        // Disconnect from session so that the updates on updatedFinalcial are not directly saved in db
        em.detach(updatedFinalcial);
        updatedFinalcial
            .code(UPDATED_CODE)
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerShortName(UPDATED_CUSTOMER_SHORT_NAME)
            .customerType(UPDATED_CUSTOMER_TYPE);

        restFinalcialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFinalcial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFinalcial))
            )
            .andExpect(status().isOk());

        // Validate the Finalcial in the database
        List<Finalcial> finalcialList = finalcialRepository.findAll();
        assertThat(finalcialList).hasSize(databaseSizeBeforeUpdate);
        Finalcial testFinalcial = finalcialList.get(finalcialList.size() - 1);
        assertThat(testFinalcial.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFinalcial.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testFinalcial.getCustomerShortName()).isEqualTo(UPDATED_CUSTOMER_SHORT_NAME);
        assertThat(testFinalcial.getCustomerType()).isEqualTo(UPDATED_CUSTOMER_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingFinalcial() throws Exception {
        int databaseSizeBeforeUpdate = finalcialRepository.findAll().size();
        finalcial.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinalcialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, finalcial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(finalcial))
            )
            .andExpect(status().isBadRequest());

        // Validate the Finalcial in the database
        List<Finalcial> finalcialList = finalcialRepository.findAll();
        assertThat(finalcialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFinalcial() throws Exception {
        int databaseSizeBeforeUpdate = finalcialRepository.findAll().size();
        finalcial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinalcialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(finalcial))
            )
            .andExpect(status().isBadRequest());

        // Validate the Finalcial in the database
        List<Finalcial> finalcialList = finalcialRepository.findAll();
        assertThat(finalcialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFinalcial() throws Exception {
        int databaseSizeBeforeUpdate = finalcialRepository.findAll().size();
        finalcial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinalcialMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(finalcial)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Finalcial in the database
        List<Finalcial> finalcialList = finalcialRepository.findAll();
        assertThat(finalcialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFinalcialWithPatch() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        int databaseSizeBeforeUpdate = finalcialRepository.findAll().size();

        // Update the finalcial using partial update
        Finalcial partialUpdatedFinalcial = new Finalcial();
        partialUpdatedFinalcial.setId(finalcial.getId());

        partialUpdatedFinalcial.code(UPDATED_CODE).customerShortName(UPDATED_CUSTOMER_SHORT_NAME).customerType(UPDATED_CUSTOMER_TYPE);

        restFinalcialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFinalcial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFinalcial))
            )
            .andExpect(status().isOk());

        // Validate the Finalcial in the database
        List<Finalcial> finalcialList = finalcialRepository.findAll();
        assertThat(finalcialList).hasSize(databaseSizeBeforeUpdate);
        Finalcial testFinalcial = finalcialList.get(finalcialList.size() - 1);
        assertThat(testFinalcial.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFinalcial.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testFinalcial.getCustomerShortName()).isEqualTo(UPDATED_CUSTOMER_SHORT_NAME);
        assertThat(testFinalcial.getCustomerType()).isEqualTo(UPDATED_CUSTOMER_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateFinalcialWithPatch() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        int databaseSizeBeforeUpdate = finalcialRepository.findAll().size();

        // Update the finalcial using partial update
        Finalcial partialUpdatedFinalcial = new Finalcial();
        partialUpdatedFinalcial.setId(finalcial.getId());

        partialUpdatedFinalcial
            .code(UPDATED_CODE)
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerShortName(UPDATED_CUSTOMER_SHORT_NAME)
            .customerType(UPDATED_CUSTOMER_TYPE);

        restFinalcialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFinalcial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFinalcial))
            )
            .andExpect(status().isOk());

        // Validate the Finalcial in the database
        List<Finalcial> finalcialList = finalcialRepository.findAll();
        assertThat(finalcialList).hasSize(databaseSizeBeforeUpdate);
        Finalcial testFinalcial = finalcialList.get(finalcialList.size() - 1);
        assertThat(testFinalcial.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFinalcial.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testFinalcial.getCustomerShortName()).isEqualTo(UPDATED_CUSTOMER_SHORT_NAME);
        assertThat(testFinalcial.getCustomerType()).isEqualTo(UPDATED_CUSTOMER_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingFinalcial() throws Exception {
        int databaseSizeBeforeUpdate = finalcialRepository.findAll().size();
        finalcial.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinalcialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, finalcial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(finalcial))
            )
            .andExpect(status().isBadRequest());

        // Validate the Finalcial in the database
        List<Finalcial> finalcialList = finalcialRepository.findAll();
        assertThat(finalcialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFinalcial() throws Exception {
        int databaseSizeBeforeUpdate = finalcialRepository.findAll().size();
        finalcial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinalcialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(finalcial))
            )
            .andExpect(status().isBadRequest());

        // Validate the Finalcial in the database
        List<Finalcial> finalcialList = finalcialRepository.findAll();
        assertThat(finalcialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFinalcial() throws Exception {
        int databaseSizeBeforeUpdate = finalcialRepository.findAll().size();
        finalcial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinalcialMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(finalcial))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Finalcial in the database
        List<Finalcial> finalcialList = finalcialRepository.findAll();
        assertThat(finalcialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFinalcial() throws Exception {
        // Initialize the database
        finalcialRepository.saveAndFlush(finalcial);

        int databaseSizeBeforeDelete = finalcialRepository.findAll().size();

        // Delete the finalcial
        restFinalcialMockMvc
            .perform(delete(ENTITY_API_URL_ID, finalcial.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Finalcial> finalcialList = finalcialRepository.findAll();
        assertThat(finalcialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
