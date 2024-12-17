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
import vn.com.datamanager.domain.PaymentTerm;
import vn.com.datamanager.repository.PaymentTermRepository;
import vn.com.datamanager.service.criteria.PaymentTermCriteria;

/**
 * Integration tests for the {@link PaymentTermResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentTermResourceIT {

    private static final String DEFAULT_PAYMENT_TERM_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TERM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_TERM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TERM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_TERM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TERM_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payment-terms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentTermRepository paymentTermRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentTermMockMvc;

    private PaymentTerm paymentTerm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentTerm createEntity(EntityManager em) {
        PaymentTerm paymentTerm = new PaymentTerm()
            .paymentTermId(DEFAULT_PAYMENT_TERM_ID)
            .paymentTermCode(DEFAULT_PAYMENT_TERM_CODE)
            .paymentTermName(DEFAULT_PAYMENT_TERM_NAME);
        return paymentTerm;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentTerm createUpdatedEntity(EntityManager em) {
        PaymentTerm paymentTerm = new PaymentTerm()
            .paymentTermId(UPDATED_PAYMENT_TERM_ID)
            .paymentTermCode(UPDATED_PAYMENT_TERM_CODE)
            .paymentTermName(UPDATED_PAYMENT_TERM_NAME);
        return paymentTerm;
    }

    @BeforeEach
    public void initTest() {
        paymentTerm = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentTerm() throws Exception {
        int databaseSizeBeforeCreate = paymentTermRepository.findAll().size();
        // Create the PaymentTerm
        restPaymentTermMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentTerm)))
            .andExpect(status().isCreated());

        // Validate the PaymentTerm in the database
        List<PaymentTerm> paymentTermList = paymentTermRepository.findAll();
        assertThat(paymentTermList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentTerm testPaymentTerm = paymentTermList.get(paymentTermList.size() - 1);
        assertThat(testPaymentTerm.getPaymentTermId()).isEqualTo(DEFAULT_PAYMENT_TERM_ID);
        assertThat(testPaymentTerm.getPaymentTermCode()).isEqualTo(DEFAULT_PAYMENT_TERM_CODE);
        assertThat(testPaymentTerm.getPaymentTermName()).isEqualTo(DEFAULT_PAYMENT_TERM_NAME);
    }

    @Test
    @Transactional
    void createPaymentTermWithExistingId() throws Exception {
        // Create the PaymentTerm with an existing ID
        paymentTerm.setId(1L);

        int databaseSizeBeforeCreate = paymentTermRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentTermMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentTerm)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentTerm in the database
        List<PaymentTerm> paymentTermList = paymentTermRepository.findAll();
        assertThat(paymentTermList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaymentTerms() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList
        restPaymentTermMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentTerm.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentTermId").value(hasItem(DEFAULT_PAYMENT_TERM_ID)))
            .andExpect(jsonPath("$.[*].paymentTermCode").value(hasItem(DEFAULT_PAYMENT_TERM_CODE)))
            .andExpect(jsonPath("$.[*].paymentTermName").value(hasItem(DEFAULT_PAYMENT_TERM_NAME)));
    }

    @Test
    @Transactional
    void getPaymentTerm() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get the paymentTerm
        restPaymentTermMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentTerm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentTerm.getId().intValue()))
            .andExpect(jsonPath("$.paymentTermId").value(DEFAULT_PAYMENT_TERM_ID))
            .andExpect(jsonPath("$.paymentTermCode").value(DEFAULT_PAYMENT_TERM_CODE))
            .andExpect(jsonPath("$.paymentTermName").value(DEFAULT_PAYMENT_TERM_NAME));
    }

    @Test
    @Transactional
    void getPaymentTermsByIdFiltering() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        Long id = paymentTerm.getId();

        defaultPaymentTermShouldBeFound("id.equals=" + id);
        defaultPaymentTermShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentTermShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentTermShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentTermShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentTermShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermIdIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermId equals to DEFAULT_PAYMENT_TERM_ID
        defaultPaymentTermShouldBeFound("paymentTermId.equals=" + DEFAULT_PAYMENT_TERM_ID);

        // Get all the paymentTermList where paymentTermId equals to UPDATED_PAYMENT_TERM_ID
        defaultPaymentTermShouldNotBeFound("paymentTermId.equals=" + UPDATED_PAYMENT_TERM_ID);
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermId not equals to DEFAULT_PAYMENT_TERM_ID
        defaultPaymentTermShouldNotBeFound("paymentTermId.notEquals=" + DEFAULT_PAYMENT_TERM_ID);

        // Get all the paymentTermList where paymentTermId not equals to UPDATED_PAYMENT_TERM_ID
        defaultPaymentTermShouldBeFound("paymentTermId.notEquals=" + UPDATED_PAYMENT_TERM_ID);
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermIdIsInShouldWork() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermId in DEFAULT_PAYMENT_TERM_ID or UPDATED_PAYMENT_TERM_ID
        defaultPaymentTermShouldBeFound("paymentTermId.in=" + DEFAULT_PAYMENT_TERM_ID + "," + UPDATED_PAYMENT_TERM_ID);

        // Get all the paymentTermList where paymentTermId equals to UPDATED_PAYMENT_TERM_ID
        defaultPaymentTermShouldNotBeFound("paymentTermId.in=" + UPDATED_PAYMENT_TERM_ID);
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermId is not null
        defaultPaymentTermShouldBeFound("paymentTermId.specified=true");

        // Get all the paymentTermList where paymentTermId is null
        defaultPaymentTermShouldNotBeFound("paymentTermId.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermIdContainsSomething() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermId contains DEFAULT_PAYMENT_TERM_ID
        defaultPaymentTermShouldBeFound("paymentTermId.contains=" + DEFAULT_PAYMENT_TERM_ID);

        // Get all the paymentTermList where paymentTermId contains UPDATED_PAYMENT_TERM_ID
        defaultPaymentTermShouldNotBeFound("paymentTermId.contains=" + UPDATED_PAYMENT_TERM_ID);
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermIdNotContainsSomething() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermId does not contain DEFAULT_PAYMENT_TERM_ID
        defaultPaymentTermShouldNotBeFound("paymentTermId.doesNotContain=" + DEFAULT_PAYMENT_TERM_ID);

        // Get all the paymentTermList where paymentTermId does not contain UPDATED_PAYMENT_TERM_ID
        defaultPaymentTermShouldBeFound("paymentTermId.doesNotContain=" + UPDATED_PAYMENT_TERM_ID);
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermCode equals to DEFAULT_PAYMENT_TERM_CODE
        defaultPaymentTermShouldBeFound("paymentTermCode.equals=" + DEFAULT_PAYMENT_TERM_CODE);

        // Get all the paymentTermList where paymentTermCode equals to UPDATED_PAYMENT_TERM_CODE
        defaultPaymentTermShouldNotBeFound("paymentTermCode.equals=" + UPDATED_PAYMENT_TERM_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermCode not equals to DEFAULT_PAYMENT_TERM_CODE
        defaultPaymentTermShouldNotBeFound("paymentTermCode.notEquals=" + DEFAULT_PAYMENT_TERM_CODE);

        // Get all the paymentTermList where paymentTermCode not equals to UPDATED_PAYMENT_TERM_CODE
        defaultPaymentTermShouldBeFound("paymentTermCode.notEquals=" + UPDATED_PAYMENT_TERM_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermCodeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermCode in DEFAULT_PAYMENT_TERM_CODE or UPDATED_PAYMENT_TERM_CODE
        defaultPaymentTermShouldBeFound("paymentTermCode.in=" + DEFAULT_PAYMENT_TERM_CODE + "," + UPDATED_PAYMENT_TERM_CODE);

        // Get all the paymentTermList where paymentTermCode equals to UPDATED_PAYMENT_TERM_CODE
        defaultPaymentTermShouldNotBeFound("paymentTermCode.in=" + UPDATED_PAYMENT_TERM_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermCode is not null
        defaultPaymentTermShouldBeFound("paymentTermCode.specified=true");

        // Get all the paymentTermList where paymentTermCode is null
        defaultPaymentTermShouldNotBeFound("paymentTermCode.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermCodeContainsSomething() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermCode contains DEFAULT_PAYMENT_TERM_CODE
        defaultPaymentTermShouldBeFound("paymentTermCode.contains=" + DEFAULT_PAYMENT_TERM_CODE);

        // Get all the paymentTermList where paymentTermCode contains UPDATED_PAYMENT_TERM_CODE
        defaultPaymentTermShouldNotBeFound("paymentTermCode.contains=" + UPDATED_PAYMENT_TERM_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermCodeNotContainsSomething() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermCode does not contain DEFAULT_PAYMENT_TERM_CODE
        defaultPaymentTermShouldNotBeFound("paymentTermCode.doesNotContain=" + DEFAULT_PAYMENT_TERM_CODE);

        // Get all the paymentTermList where paymentTermCode does not contain UPDATED_PAYMENT_TERM_CODE
        defaultPaymentTermShouldBeFound("paymentTermCode.doesNotContain=" + UPDATED_PAYMENT_TERM_CODE);
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermName equals to DEFAULT_PAYMENT_TERM_NAME
        defaultPaymentTermShouldBeFound("paymentTermName.equals=" + DEFAULT_PAYMENT_TERM_NAME);

        // Get all the paymentTermList where paymentTermName equals to UPDATED_PAYMENT_TERM_NAME
        defaultPaymentTermShouldNotBeFound("paymentTermName.equals=" + UPDATED_PAYMENT_TERM_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermName not equals to DEFAULT_PAYMENT_TERM_NAME
        defaultPaymentTermShouldNotBeFound("paymentTermName.notEquals=" + DEFAULT_PAYMENT_TERM_NAME);

        // Get all the paymentTermList where paymentTermName not equals to UPDATED_PAYMENT_TERM_NAME
        defaultPaymentTermShouldBeFound("paymentTermName.notEquals=" + UPDATED_PAYMENT_TERM_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermNameIsInShouldWork() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermName in DEFAULT_PAYMENT_TERM_NAME or UPDATED_PAYMENT_TERM_NAME
        defaultPaymentTermShouldBeFound("paymentTermName.in=" + DEFAULT_PAYMENT_TERM_NAME + "," + UPDATED_PAYMENT_TERM_NAME);

        // Get all the paymentTermList where paymentTermName equals to UPDATED_PAYMENT_TERM_NAME
        defaultPaymentTermShouldNotBeFound("paymentTermName.in=" + UPDATED_PAYMENT_TERM_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermName is not null
        defaultPaymentTermShouldBeFound("paymentTermName.specified=true");

        // Get all the paymentTermList where paymentTermName is null
        defaultPaymentTermShouldNotBeFound("paymentTermName.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermNameContainsSomething() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermName contains DEFAULT_PAYMENT_TERM_NAME
        defaultPaymentTermShouldBeFound("paymentTermName.contains=" + DEFAULT_PAYMENT_TERM_NAME);

        // Get all the paymentTermList where paymentTermName contains UPDATED_PAYMENT_TERM_NAME
        defaultPaymentTermShouldNotBeFound("paymentTermName.contains=" + UPDATED_PAYMENT_TERM_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentTermsByPaymentTermNameNotContainsSomething() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        // Get all the paymentTermList where paymentTermName does not contain DEFAULT_PAYMENT_TERM_NAME
        defaultPaymentTermShouldNotBeFound("paymentTermName.doesNotContain=" + DEFAULT_PAYMENT_TERM_NAME);

        // Get all the paymentTermList where paymentTermName does not contain UPDATED_PAYMENT_TERM_NAME
        defaultPaymentTermShouldBeFound("paymentTermName.doesNotContain=" + UPDATED_PAYMENT_TERM_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentTermShouldBeFound(String filter) throws Exception {
        restPaymentTermMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentTerm.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentTermId").value(hasItem(DEFAULT_PAYMENT_TERM_ID)))
            .andExpect(jsonPath("$.[*].paymentTermCode").value(hasItem(DEFAULT_PAYMENT_TERM_CODE)))
            .andExpect(jsonPath("$.[*].paymentTermName").value(hasItem(DEFAULT_PAYMENT_TERM_NAME)));

        // Check, that the count call also returns 1
        restPaymentTermMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentTermShouldNotBeFound(String filter) throws Exception {
        restPaymentTermMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentTermMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPaymentTerm() throws Exception {
        // Get the paymentTerm
        restPaymentTermMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentTerm() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        int databaseSizeBeforeUpdate = paymentTermRepository.findAll().size();

        // Update the paymentTerm
        PaymentTerm updatedPaymentTerm = paymentTermRepository.findById(paymentTerm.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentTerm are not directly saved in db
        em.detach(updatedPaymentTerm);
        updatedPaymentTerm
            .paymentTermId(UPDATED_PAYMENT_TERM_ID)
            .paymentTermCode(UPDATED_PAYMENT_TERM_CODE)
            .paymentTermName(UPDATED_PAYMENT_TERM_NAME);

        restPaymentTermMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaymentTerm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaymentTerm))
            )
            .andExpect(status().isOk());

        // Validate the PaymentTerm in the database
        List<PaymentTerm> paymentTermList = paymentTermRepository.findAll();
        assertThat(paymentTermList).hasSize(databaseSizeBeforeUpdate);
        PaymentTerm testPaymentTerm = paymentTermList.get(paymentTermList.size() - 1);
        assertThat(testPaymentTerm.getPaymentTermId()).isEqualTo(UPDATED_PAYMENT_TERM_ID);
        assertThat(testPaymentTerm.getPaymentTermCode()).isEqualTo(UPDATED_PAYMENT_TERM_CODE);
        assertThat(testPaymentTerm.getPaymentTermName()).isEqualTo(UPDATED_PAYMENT_TERM_NAME);
    }

    @Test
    @Transactional
    void putNonExistingPaymentTerm() throws Exception {
        int databaseSizeBeforeUpdate = paymentTermRepository.findAll().size();
        paymentTerm.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentTermMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentTerm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentTerm))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentTerm in the database
        List<PaymentTerm> paymentTermList = paymentTermRepository.findAll();
        assertThat(paymentTermList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentTerm() throws Exception {
        int databaseSizeBeforeUpdate = paymentTermRepository.findAll().size();
        paymentTerm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentTermMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentTerm))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentTerm in the database
        List<PaymentTerm> paymentTermList = paymentTermRepository.findAll();
        assertThat(paymentTermList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentTerm() throws Exception {
        int databaseSizeBeforeUpdate = paymentTermRepository.findAll().size();
        paymentTerm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentTermMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentTerm)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentTerm in the database
        List<PaymentTerm> paymentTermList = paymentTermRepository.findAll();
        assertThat(paymentTermList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentTermWithPatch() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        int databaseSizeBeforeUpdate = paymentTermRepository.findAll().size();

        // Update the paymentTerm using partial update
        PaymentTerm partialUpdatedPaymentTerm = new PaymentTerm();
        partialUpdatedPaymentTerm.setId(paymentTerm.getId());

        partialUpdatedPaymentTerm.paymentTermCode(UPDATED_PAYMENT_TERM_CODE);

        restPaymentTermMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentTerm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentTerm))
            )
            .andExpect(status().isOk());

        // Validate the PaymentTerm in the database
        List<PaymentTerm> paymentTermList = paymentTermRepository.findAll();
        assertThat(paymentTermList).hasSize(databaseSizeBeforeUpdate);
        PaymentTerm testPaymentTerm = paymentTermList.get(paymentTermList.size() - 1);
        assertThat(testPaymentTerm.getPaymentTermId()).isEqualTo(DEFAULT_PAYMENT_TERM_ID);
        assertThat(testPaymentTerm.getPaymentTermCode()).isEqualTo(UPDATED_PAYMENT_TERM_CODE);
        assertThat(testPaymentTerm.getPaymentTermName()).isEqualTo(DEFAULT_PAYMENT_TERM_NAME);
    }

    @Test
    @Transactional
    void fullUpdatePaymentTermWithPatch() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        int databaseSizeBeforeUpdate = paymentTermRepository.findAll().size();

        // Update the paymentTerm using partial update
        PaymentTerm partialUpdatedPaymentTerm = new PaymentTerm();
        partialUpdatedPaymentTerm.setId(paymentTerm.getId());

        partialUpdatedPaymentTerm
            .paymentTermId(UPDATED_PAYMENT_TERM_ID)
            .paymentTermCode(UPDATED_PAYMENT_TERM_CODE)
            .paymentTermName(UPDATED_PAYMENT_TERM_NAME);

        restPaymentTermMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentTerm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentTerm))
            )
            .andExpect(status().isOk());

        // Validate the PaymentTerm in the database
        List<PaymentTerm> paymentTermList = paymentTermRepository.findAll();
        assertThat(paymentTermList).hasSize(databaseSizeBeforeUpdate);
        PaymentTerm testPaymentTerm = paymentTermList.get(paymentTermList.size() - 1);
        assertThat(testPaymentTerm.getPaymentTermId()).isEqualTo(UPDATED_PAYMENT_TERM_ID);
        assertThat(testPaymentTerm.getPaymentTermCode()).isEqualTo(UPDATED_PAYMENT_TERM_CODE);
        assertThat(testPaymentTerm.getPaymentTermName()).isEqualTo(UPDATED_PAYMENT_TERM_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentTerm() throws Exception {
        int databaseSizeBeforeUpdate = paymentTermRepository.findAll().size();
        paymentTerm.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentTermMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentTerm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentTerm))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentTerm in the database
        List<PaymentTerm> paymentTermList = paymentTermRepository.findAll();
        assertThat(paymentTermList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentTerm() throws Exception {
        int databaseSizeBeforeUpdate = paymentTermRepository.findAll().size();
        paymentTerm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentTermMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentTerm))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentTerm in the database
        List<PaymentTerm> paymentTermList = paymentTermRepository.findAll();
        assertThat(paymentTermList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentTerm() throws Exception {
        int databaseSizeBeforeUpdate = paymentTermRepository.findAll().size();
        paymentTerm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentTermMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paymentTerm))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentTerm in the database
        List<PaymentTerm> paymentTermList = paymentTermRepository.findAll();
        assertThat(paymentTermList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentTerm() throws Exception {
        // Initialize the database
        paymentTermRepository.saveAndFlush(paymentTerm);

        int databaseSizeBeforeDelete = paymentTermRepository.findAll().size();

        // Delete the paymentTerm
        restPaymentTermMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentTerm.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentTerm> paymentTermList = paymentTermRepository.findAll();
        assertThat(paymentTermList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
