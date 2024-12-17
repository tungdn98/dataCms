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
import vn.com.datamanager.domain.PaymentStatus;
import vn.com.datamanager.repository.PaymentStatusRepository;
import vn.com.datamanager.service.criteria.PaymentStatusCriteria;

/**
 * Integration tests for the {@link PaymentStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentStatusResourceIT {

    private static final String DEFAULT_PAYMENT_STATUS_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_STATUS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_STATUS_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payment-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentStatusMockMvc;

    private PaymentStatus paymentStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentStatus createEntity(EntityManager em) {
        PaymentStatus paymentStatus = new PaymentStatus()
            .paymentStatusId(DEFAULT_PAYMENT_STATUS_ID)
            .paymentStatusName(DEFAULT_PAYMENT_STATUS_NAME);
        return paymentStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentStatus createUpdatedEntity(EntityManager em) {
        PaymentStatus paymentStatus = new PaymentStatus()
            .paymentStatusId(UPDATED_PAYMENT_STATUS_ID)
            .paymentStatusName(UPDATED_PAYMENT_STATUS_NAME);
        return paymentStatus;
    }

    @BeforeEach
    public void initTest() {
        paymentStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentStatus() throws Exception {
        int databaseSizeBeforeCreate = paymentStatusRepository.findAll().size();
        // Create the PaymentStatus
        restPaymentStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentStatus)))
            .andExpect(status().isCreated());

        // Validate the PaymentStatus in the database
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();
        assertThat(paymentStatusList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentStatus testPaymentStatus = paymentStatusList.get(paymentStatusList.size() - 1);
        assertThat(testPaymentStatus.getPaymentStatusId()).isEqualTo(DEFAULT_PAYMENT_STATUS_ID);
        assertThat(testPaymentStatus.getPaymentStatusName()).isEqualTo(DEFAULT_PAYMENT_STATUS_NAME);
    }

    @Test
    @Transactional
    void createPaymentStatusWithExistingId() throws Exception {
        // Create the PaymentStatus with an existing ID
        paymentStatus.setId(1L);

        int databaseSizeBeforeCreate = paymentStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentStatus)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentStatus in the database
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();
        assertThat(paymentStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaymentStatuses() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        // Get all the paymentStatusList
        restPaymentStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentStatusId").value(hasItem(DEFAULT_PAYMENT_STATUS_ID)))
            .andExpect(jsonPath("$.[*].paymentStatusName").value(hasItem(DEFAULT_PAYMENT_STATUS_NAME)));
    }

    @Test
    @Transactional
    void getPaymentStatus() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        // Get the paymentStatus
        restPaymentStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentStatus.getId().intValue()))
            .andExpect(jsonPath("$.paymentStatusId").value(DEFAULT_PAYMENT_STATUS_ID))
            .andExpect(jsonPath("$.paymentStatusName").value(DEFAULT_PAYMENT_STATUS_NAME));
    }

    @Test
    @Transactional
    void getPaymentStatusesByIdFiltering() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        Long id = paymentStatus.getId();

        defaultPaymentStatusShouldBeFound("id.equals=" + id);
        defaultPaymentStatusShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentStatusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentStatusShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentStatusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentStatusShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPaymentStatusesByPaymentStatusIdIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        // Get all the paymentStatusList where paymentStatusId equals to DEFAULT_PAYMENT_STATUS_ID
        defaultPaymentStatusShouldBeFound("paymentStatusId.equals=" + DEFAULT_PAYMENT_STATUS_ID);

        // Get all the paymentStatusList where paymentStatusId equals to UPDATED_PAYMENT_STATUS_ID
        defaultPaymentStatusShouldNotBeFound("paymentStatusId.equals=" + UPDATED_PAYMENT_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllPaymentStatusesByPaymentStatusIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        // Get all the paymentStatusList where paymentStatusId not equals to DEFAULT_PAYMENT_STATUS_ID
        defaultPaymentStatusShouldNotBeFound("paymentStatusId.notEquals=" + DEFAULT_PAYMENT_STATUS_ID);

        // Get all the paymentStatusList where paymentStatusId not equals to UPDATED_PAYMENT_STATUS_ID
        defaultPaymentStatusShouldBeFound("paymentStatusId.notEquals=" + UPDATED_PAYMENT_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllPaymentStatusesByPaymentStatusIdIsInShouldWork() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        // Get all the paymentStatusList where paymentStatusId in DEFAULT_PAYMENT_STATUS_ID or UPDATED_PAYMENT_STATUS_ID
        defaultPaymentStatusShouldBeFound("paymentStatusId.in=" + DEFAULT_PAYMENT_STATUS_ID + "," + UPDATED_PAYMENT_STATUS_ID);

        // Get all the paymentStatusList where paymentStatusId equals to UPDATED_PAYMENT_STATUS_ID
        defaultPaymentStatusShouldNotBeFound("paymentStatusId.in=" + UPDATED_PAYMENT_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllPaymentStatusesByPaymentStatusIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        // Get all the paymentStatusList where paymentStatusId is not null
        defaultPaymentStatusShouldBeFound("paymentStatusId.specified=true");

        // Get all the paymentStatusList where paymentStatusId is null
        defaultPaymentStatusShouldNotBeFound("paymentStatusId.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentStatusesByPaymentStatusIdContainsSomething() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        // Get all the paymentStatusList where paymentStatusId contains DEFAULT_PAYMENT_STATUS_ID
        defaultPaymentStatusShouldBeFound("paymentStatusId.contains=" + DEFAULT_PAYMENT_STATUS_ID);

        // Get all the paymentStatusList where paymentStatusId contains UPDATED_PAYMENT_STATUS_ID
        defaultPaymentStatusShouldNotBeFound("paymentStatusId.contains=" + UPDATED_PAYMENT_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllPaymentStatusesByPaymentStatusIdNotContainsSomething() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        // Get all the paymentStatusList where paymentStatusId does not contain DEFAULT_PAYMENT_STATUS_ID
        defaultPaymentStatusShouldNotBeFound("paymentStatusId.doesNotContain=" + DEFAULT_PAYMENT_STATUS_ID);

        // Get all the paymentStatusList where paymentStatusId does not contain UPDATED_PAYMENT_STATUS_ID
        defaultPaymentStatusShouldBeFound("paymentStatusId.doesNotContain=" + UPDATED_PAYMENT_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllPaymentStatusesByPaymentStatusNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        // Get all the paymentStatusList where paymentStatusName equals to DEFAULT_PAYMENT_STATUS_NAME
        defaultPaymentStatusShouldBeFound("paymentStatusName.equals=" + DEFAULT_PAYMENT_STATUS_NAME);

        // Get all the paymentStatusList where paymentStatusName equals to UPDATED_PAYMENT_STATUS_NAME
        defaultPaymentStatusShouldNotBeFound("paymentStatusName.equals=" + UPDATED_PAYMENT_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentStatusesByPaymentStatusNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        // Get all the paymentStatusList where paymentStatusName not equals to DEFAULT_PAYMENT_STATUS_NAME
        defaultPaymentStatusShouldNotBeFound("paymentStatusName.notEquals=" + DEFAULT_PAYMENT_STATUS_NAME);

        // Get all the paymentStatusList where paymentStatusName not equals to UPDATED_PAYMENT_STATUS_NAME
        defaultPaymentStatusShouldBeFound("paymentStatusName.notEquals=" + UPDATED_PAYMENT_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentStatusesByPaymentStatusNameIsInShouldWork() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        // Get all the paymentStatusList where paymentStatusName in DEFAULT_PAYMENT_STATUS_NAME or UPDATED_PAYMENT_STATUS_NAME
        defaultPaymentStatusShouldBeFound("paymentStatusName.in=" + DEFAULT_PAYMENT_STATUS_NAME + "," + UPDATED_PAYMENT_STATUS_NAME);

        // Get all the paymentStatusList where paymentStatusName equals to UPDATED_PAYMENT_STATUS_NAME
        defaultPaymentStatusShouldNotBeFound("paymentStatusName.in=" + UPDATED_PAYMENT_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentStatusesByPaymentStatusNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        // Get all the paymentStatusList where paymentStatusName is not null
        defaultPaymentStatusShouldBeFound("paymentStatusName.specified=true");

        // Get all the paymentStatusList where paymentStatusName is null
        defaultPaymentStatusShouldNotBeFound("paymentStatusName.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentStatusesByPaymentStatusNameContainsSomething() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        // Get all the paymentStatusList where paymentStatusName contains DEFAULT_PAYMENT_STATUS_NAME
        defaultPaymentStatusShouldBeFound("paymentStatusName.contains=" + DEFAULT_PAYMENT_STATUS_NAME);

        // Get all the paymentStatusList where paymentStatusName contains UPDATED_PAYMENT_STATUS_NAME
        defaultPaymentStatusShouldNotBeFound("paymentStatusName.contains=" + UPDATED_PAYMENT_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentStatusesByPaymentStatusNameNotContainsSomething() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        // Get all the paymentStatusList where paymentStatusName does not contain DEFAULT_PAYMENT_STATUS_NAME
        defaultPaymentStatusShouldNotBeFound("paymentStatusName.doesNotContain=" + DEFAULT_PAYMENT_STATUS_NAME);

        // Get all the paymentStatusList where paymentStatusName does not contain UPDATED_PAYMENT_STATUS_NAME
        defaultPaymentStatusShouldBeFound("paymentStatusName.doesNotContain=" + UPDATED_PAYMENT_STATUS_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentStatusShouldBeFound(String filter) throws Exception {
        restPaymentStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentStatusId").value(hasItem(DEFAULT_PAYMENT_STATUS_ID)))
            .andExpect(jsonPath("$.[*].paymentStatusName").value(hasItem(DEFAULT_PAYMENT_STATUS_NAME)));

        // Check, that the count call also returns 1
        restPaymentStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentStatusShouldNotBeFound(String filter) throws Exception {
        restPaymentStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPaymentStatus() throws Exception {
        // Get the paymentStatus
        restPaymentStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentStatus() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        int databaseSizeBeforeUpdate = paymentStatusRepository.findAll().size();

        // Update the paymentStatus
        PaymentStatus updatedPaymentStatus = paymentStatusRepository.findById(paymentStatus.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentStatus are not directly saved in db
        em.detach(updatedPaymentStatus);
        updatedPaymentStatus.paymentStatusId(UPDATED_PAYMENT_STATUS_ID).paymentStatusName(UPDATED_PAYMENT_STATUS_NAME);

        restPaymentStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaymentStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaymentStatus))
            )
            .andExpect(status().isOk());

        // Validate the PaymentStatus in the database
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();
        assertThat(paymentStatusList).hasSize(databaseSizeBeforeUpdate);
        PaymentStatus testPaymentStatus = paymentStatusList.get(paymentStatusList.size() - 1);
        assertThat(testPaymentStatus.getPaymentStatusId()).isEqualTo(UPDATED_PAYMENT_STATUS_ID);
        assertThat(testPaymentStatus.getPaymentStatusName()).isEqualTo(UPDATED_PAYMENT_STATUS_NAME);
    }

    @Test
    @Transactional
    void putNonExistingPaymentStatus() throws Exception {
        int databaseSizeBeforeUpdate = paymentStatusRepository.findAll().size();
        paymentStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentStatus in the database
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();
        assertThat(paymentStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentStatus() throws Exception {
        int databaseSizeBeforeUpdate = paymentStatusRepository.findAll().size();
        paymentStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentStatus in the database
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();
        assertThat(paymentStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentStatus() throws Exception {
        int databaseSizeBeforeUpdate = paymentStatusRepository.findAll().size();
        paymentStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentStatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentStatus in the database
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();
        assertThat(paymentStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentStatusWithPatch() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        int databaseSizeBeforeUpdate = paymentStatusRepository.findAll().size();

        // Update the paymentStatus using partial update
        PaymentStatus partialUpdatedPaymentStatus = new PaymentStatus();
        partialUpdatedPaymentStatus.setId(paymentStatus.getId());

        partialUpdatedPaymentStatus.paymentStatusId(UPDATED_PAYMENT_STATUS_ID).paymentStatusName(UPDATED_PAYMENT_STATUS_NAME);

        restPaymentStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentStatus))
            )
            .andExpect(status().isOk());

        // Validate the PaymentStatus in the database
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();
        assertThat(paymentStatusList).hasSize(databaseSizeBeforeUpdate);
        PaymentStatus testPaymentStatus = paymentStatusList.get(paymentStatusList.size() - 1);
        assertThat(testPaymentStatus.getPaymentStatusId()).isEqualTo(UPDATED_PAYMENT_STATUS_ID);
        assertThat(testPaymentStatus.getPaymentStatusName()).isEqualTo(UPDATED_PAYMENT_STATUS_NAME);
    }

    @Test
    @Transactional
    void fullUpdatePaymentStatusWithPatch() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        int databaseSizeBeforeUpdate = paymentStatusRepository.findAll().size();

        // Update the paymentStatus using partial update
        PaymentStatus partialUpdatedPaymentStatus = new PaymentStatus();
        partialUpdatedPaymentStatus.setId(paymentStatus.getId());

        partialUpdatedPaymentStatus.paymentStatusId(UPDATED_PAYMENT_STATUS_ID).paymentStatusName(UPDATED_PAYMENT_STATUS_NAME);

        restPaymentStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentStatus))
            )
            .andExpect(status().isOk());

        // Validate the PaymentStatus in the database
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();
        assertThat(paymentStatusList).hasSize(databaseSizeBeforeUpdate);
        PaymentStatus testPaymentStatus = paymentStatusList.get(paymentStatusList.size() - 1);
        assertThat(testPaymentStatus.getPaymentStatusId()).isEqualTo(UPDATED_PAYMENT_STATUS_ID);
        assertThat(testPaymentStatus.getPaymentStatusName()).isEqualTo(UPDATED_PAYMENT_STATUS_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentStatus() throws Exception {
        int databaseSizeBeforeUpdate = paymentStatusRepository.findAll().size();
        paymentStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentStatus in the database
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();
        assertThat(paymentStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentStatus() throws Exception {
        int databaseSizeBeforeUpdate = paymentStatusRepository.findAll().size();
        paymentStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentStatus in the database
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();
        assertThat(paymentStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentStatus() throws Exception {
        int databaseSizeBeforeUpdate = paymentStatusRepository.findAll().size();
        paymentStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentStatusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paymentStatus))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentStatus in the database
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();
        assertThat(paymentStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentStatus() throws Exception {
        // Initialize the database
        paymentStatusRepository.saveAndFlush(paymentStatus);

        int databaseSizeBeforeDelete = paymentStatusRepository.findAll().size();

        // Delete the paymentStatus
        restPaymentStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();
        assertThat(paymentStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
