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
import vn.com.datamanager.domain.PaymentMethod;
import vn.com.datamanager.repository.PaymentMethodRepository;
import vn.com.datamanager.service.criteria.PaymentMethodCriteria;

/**
 * Integration tests for the {@link PaymentMethodResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentMethodResourceIT {

    private static final String DEFAULT_PAYMENT_STATUS_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_STATUS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_STATUS_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payment-methods";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentMethodMockMvc;

    private PaymentMethod paymentMethod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentMethod createEntity(EntityManager em) {
        PaymentMethod paymentMethod = new PaymentMethod()
            .paymentStatusId(DEFAULT_PAYMENT_STATUS_ID)
            .paymentStatusName(DEFAULT_PAYMENT_STATUS_NAME);
        return paymentMethod;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentMethod createUpdatedEntity(EntityManager em) {
        PaymentMethod paymentMethod = new PaymentMethod()
            .paymentStatusId(UPDATED_PAYMENT_STATUS_ID)
            .paymentStatusName(UPDATED_PAYMENT_STATUS_NAME);
        return paymentMethod;
    }

    @BeforeEach
    public void initTest() {
        paymentMethod = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentMethod() throws Exception {
        int databaseSizeBeforeCreate = paymentMethodRepository.findAll().size();
        // Create the PaymentMethod
        restPaymentMethodMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentMethod)))
            .andExpect(status().isCreated());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentMethod testPaymentMethod = paymentMethodList.get(paymentMethodList.size() - 1);
        assertThat(testPaymentMethod.getPaymentStatusId()).isEqualTo(DEFAULT_PAYMENT_STATUS_ID);
        assertThat(testPaymentMethod.getPaymentStatusName()).isEqualTo(DEFAULT_PAYMENT_STATUS_NAME);
    }

    @Test
    @Transactional
    void createPaymentMethodWithExistingId() throws Exception {
        // Create the PaymentMethod with an existing ID
        paymentMethod.setId(1L);

        int databaseSizeBeforeCreate = paymentMethodRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMethodMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentMethod)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaymentMethods() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList
        restPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentStatusId").value(hasItem(DEFAULT_PAYMENT_STATUS_ID)))
            .andExpect(jsonPath("$.[*].paymentStatusName").value(hasItem(DEFAULT_PAYMENT_STATUS_NAME)));
    }

    @Test
    @Transactional
    void getPaymentMethod() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get the paymentMethod
        restPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentMethod.getId().intValue()))
            .andExpect(jsonPath("$.paymentStatusId").value(DEFAULT_PAYMENT_STATUS_ID))
            .andExpect(jsonPath("$.paymentStatusName").value(DEFAULT_PAYMENT_STATUS_NAME));
    }

    @Test
    @Transactional
    void getPaymentMethodsByIdFiltering() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        Long id = paymentMethod.getId();

        defaultPaymentMethodShouldBeFound("id.equals=" + id);
        defaultPaymentMethodShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentMethodShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentMethodShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentMethodShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentMethodShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByPaymentStatusIdIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where paymentStatusId equals to DEFAULT_PAYMENT_STATUS_ID
        defaultPaymentMethodShouldBeFound("paymentStatusId.equals=" + DEFAULT_PAYMENT_STATUS_ID);

        // Get all the paymentMethodList where paymentStatusId equals to UPDATED_PAYMENT_STATUS_ID
        defaultPaymentMethodShouldNotBeFound("paymentStatusId.equals=" + UPDATED_PAYMENT_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByPaymentStatusIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where paymentStatusId not equals to DEFAULT_PAYMENT_STATUS_ID
        defaultPaymentMethodShouldNotBeFound("paymentStatusId.notEquals=" + DEFAULT_PAYMENT_STATUS_ID);

        // Get all the paymentMethodList where paymentStatusId not equals to UPDATED_PAYMENT_STATUS_ID
        defaultPaymentMethodShouldBeFound("paymentStatusId.notEquals=" + UPDATED_PAYMENT_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByPaymentStatusIdIsInShouldWork() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where paymentStatusId in DEFAULT_PAYMENT_STATUS_ID or UPDATED_PAYMENT_STATUS_ID
        defaultPaymentMethodShouldBeFound("paymentStatusId.in=" + DEFAULT_PAYMENT_STATUS_ID + "," + UPDATED_PAYMENT_STATUS_ID);

        // Get all the paymentMethodList where paymentStatusId equals to UPDATED_PAYMENT_STATUS_ID
        defaultPaymentMethodShouldNotBeFound("paymentStatusId.in=" + UPDATED_PAYMENT_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByPaymentStatusIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where paymentStatusId is not null
        defaultPaymentMethodShouldBeFound("paymentStatusId.specified=true");

        // Get all the paymentMethodList where paymentStatusId is null
        defaultPaymentMethodShouldNotBeFound("paymentStatusId.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByPaymentStatusIdContainsSomething() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where paymentStatusId contains DEFAULT_PAYMENT_STATUS_ID
        defaultPaymentMethodShouldBeFound("paymentStatusId.contains=" + DEFAULT_PAYMENT_STATUS_ID);

        // Get all the paymentMethodList where paymentStatusId contains UPDATED_PAYMENT_STATUS_ID
        defaultPaymentMethodShouldNotBeFound("paymentStatusId.contains=" + UPDATED_PAYMENT_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByPaymentStatusIdNotContainsSomething() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where paymentStatusId does not contain DEFAULT_PAYMENT_STATUS_ID
        defaultPaymentMethodShouldNotBeFound("paymentStatusId.doesNotContain=" + DEFAULT_PAYMENT_STATUS_ID);

        // Get all the paymentMethodList where paymentStatusId does not contain UPDATED_PAYMENT_STATUS_ID
        defaultPaymentMethodShouldBeFound("paymentStatusId.doesNotContain=" + UPDATED_PAYMENT_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByPaymentStatusNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where paymentStatusName equals to DEFAULT_PAYMENT_STATUS_NAME
        defaultPaymentMethodShouldBeFound("paymentStatusName.equals=" + DEFAULT_PAYMENT_STATUS_NAME);

        // Get all the paymentMethodList where paymentStatusName equals to UPDATED_PAYMENT_STATUS_NAME
        defaultPaymentMethodShouldNotBeFound("paymentStatusName.equals=" + UPDATED_PAYMENT_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByPaymentStatusNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where paymentStatusName not equals to DEFAULT_PAYMENT_STATUS_NAME
        defaultPaymentMethodShouldNotBeFound("paymentStatusName.notEquals=" + DEFAULT_PAYMENT_STATUS_NAME);

        // Get all the paymentMethodList where paymentStatusName not equals to UPDATED_PAYMENT_STATUS_NAME
        defaultPaymentMethodShouldBeFound("paymentStatusName.notEquals=" + UPDATED_PAYMENT_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByPaymentStatusNameIsInShouldWork() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where paymentStatusName in DEFAULT_PAYMENT_STATUS_NAME or UPDATED_PAYMENT_STATUS_NAME
        defaultPaymentMethodShouldBeFound("paymentStatusName.in=" + DEFAULT_PAYMENT_STATUS_NAME + "," + UPDATED_PAYMENT_STATUS_NAME);

        // Get all the paymentMethodList where paymentStatusName equals to UPDATED_PAYMENT_STATUS_NAME
        defaultPaymentMethodShouldNotBeFound("paymentStatusName.in=" + UPDATED_PAYMENT_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByPaymentStatusNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where paymentStatusName is not null
        defaultPaymentMethodShouldBeFound("paymentStatusName.specified=true");

        // Get all the paymentMethodList where paymentStatusName is null
        defaultPaymentMethodShouldNotBeFound("paymentStatusName.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByPaymentStatusNameContainsSomething() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where paymentStatusName contains DEFAULT_PAYMENT_STATUS_NAME
        defaultPaymentMethodShouldBeFound("paymentStatusName.contains=" + DEFAULT_PAYMENT_STATUS_NAME);

        // Get all the paymentMethodList where paymentStatusName contains UPDATED_PAYMENT_STATUS_NAME
        defaultPaymentMethodShouldNotBeFound("paymentStatusName.contains=" + UPDATED_PAYMENT_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByPaymentStatusNameNotContainsSomething() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where paymentStatusName does not contain DEFAULT_PAYMENT_STATUS_NAME
        defaultPaymentMethodShouldNotBeFound("paymentStatusName.doesNotContain=" + DEFAULT_PAYMENT_STATUS_NAME);

        // Get all the paymentMethodList where paymentStatusName does not contain UPDATED_PAYMENT_STATUS_NAME
        defaultPaymentMethodShouldBeFound("paymentStatusName.doesNotContain=" + UPDATED_PAYMENT_STATUS_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentMethodShouldBeFound(String filter) throws Exception {
        restPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentStatusId").value(hasItem(DEFAULT_PAYMENT_STATUS_ID)))
            .andExpect(jsonPath("$.[*].paymentStatusName").value(hasItem(DEFAULT_PAYMENT_STATUS_NAME)));

        // Check, that the count call also returns 1
        restPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentMethodShouldNotBeFound(String filter) throws Exception {
        restPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPaymentMethod() throws Exception {
        // Get the paymentMethod
        restPaymentMethodMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentMethod() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();

        // Update the paymentMethod
        PaymentMethod updatedPaymentMethod = paymentMethodRepository.findById(paymentMethod.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentMethod are not directly saved in db
        em.detach(updatedPaymentMethod);
        updatedPaymentMethod.paymentStatusId(UPDATED_PAYMENT_STATUS_ID).paymentStatusName(UPDATED_PAYMENT_STATUS_NAME);

        restPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaymentMethod.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaymentMethod))
            )
            .andExpect(status().isOk());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
        PaymentMethod testPaymentMethod = paymentMethodList.get(paymentMethodList.size() - 1);
        assertThat(testPaymentMethod.getPaymentStatusId()).isEqualTo(UPDATED_PAYMENT_STATUS_ID);
        assertThat(testPaymentMethod.getPaymentStatusName()).isEqualTo(UPDATED_PAYMENT_STATUS_NAME);
    }

    @Test
    @Transactional
    void putNonExistingPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentMethod.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethod))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethod))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentMethod)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentMethodWithPatch() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();

        // Update the paymentMethod using partial update
        PaymentMethod partialUpdatedPaymentMethod = new PaymentMethod();
        partialUpdatedPaymentMethod.setId(paymentMethod.getId());

        partialUpdatedPaymentMethod.paymentStatusId(UPDATED_PAYMENT_STATUS_ID).paymentStatusName(UPDATED_PAYMENT_STATUS_NAME);

        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentMethod.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentMethod))
            )
            .andExpect(status().isOk());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
        PaymentMethod testPaymentMethod = paymentMethodList.get(paymentMethodList.size() - 1);
        assertThat(testPaymentMethod.getPaymentStatusId()).isEqualTo(UPDATED_PAYMENT_STATUS_ID);
        assertThat(testPaymentMethod.getPaymentStatusName()).isEqualTo(UPDATED_PAYMENT_STATUS_NAME);
    }

    @Test
    @Transactional
    void fullUpdatePaymentMethodWithPatch() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();

        // Update the paymentMethod using partial update
        PaymentMethod partialUpdatedPaymentMethod = new PaymentMethod();
        partialUpdatedPaymentMethod.setId(paymentMethod.getId());

        partialUpdatedPaymentMethod.paymentStatusId(UPDATED_PAYMENT_STATUS_ID).paymentStatusName(UPDATED_PAYMENT_STATUS_NAME);

        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentMethod.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentMethod))
            )
            .andExpect(status().isOk());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
        PaymentMethod testPaymentMethod = paymentMethodList.get(paymentMethodList.size() - 1);
        assertThat(testPaymentMethod.getPaymentStatusId()).isEqualTo(UPDATED_PAYMENT_STATUS_ID);
        assertThat(testPaymentMethod.getPaymentStatusName()).isEqualTo(UPDATED_PAYMENT_STATUS_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentMethod.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethod))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethod))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paymentMethod))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentMethod() throws Exception {
        // Initialize the database
        paymentMethodRepository.saveAndFlush(paymentMethod);

        int databaseSizeBeforeDelete = paymentMethodRepository.findAll().size();

        // Delete the paymentMethod
        restPaymentMethodMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentMethod.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
