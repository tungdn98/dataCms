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
import vn.com.datamanager.domain.ContractType;
import vn.com.datamanager.repository.ContractTypeRepository;
import vn.com.datamanager.service.criteria.ContractTypeCriteria;

/**
 * Integration tests for the {@link ContractTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContractTypeResourceIT {

    private static final String DEFAULT_CONTRACT_TYPE_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_TYPE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_TYPE_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contract-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContractTypeRepository contractTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContractTypeMockMvc;

    private ContractType contractType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContractType createEntity(EntityManager em) {
        ContractType contractType = new ContractType()
            .contractTypeId(DEFAULT_CONTRACT_TYPE_ID)
            .contractTypeName(DEFAULT_CONTRACT_TYPE_NAME)
            .contractTypeCode(DEFAULT_CONTRACT_TYPE_CODE);
        return contractType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContractType createUpdatedEntity(EntityManager em) {
        ContractType contractType = new ContractType()
            .contractTypeId(UPDATED_CONTRACT_TYPE_ID)
            .contractTypeName(UPDATED_CONTRACT_TYPE_NAME)
            .contractTypeCode(UPDATED_CONTRACT_TYPE_CODE);
        return contractType;
    }

    @BeforeEach
    public void initTest() {
        contractType = createEntity(em);
    }

    @Test
    @Transactional
    void createContractType() throws Exception {
        int databaseSizeBeforeCreate = contractTypeRepository.findAll().size();
        // Create the ContractType
        restContractTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contractType)))
            .andExpect(status().isCreated());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ContractType testContractType = contractTypeList.get(contractTypeList.size() - 1);
        assertThat(testContractType.getContractTypeId()).isEqualTo(DEFAULT_CONTRACT_TYPE_ID);
        assertThat(testContractType.getContractTypeName()).isEqualTo(DEFAULT_CONTRACT_TYPE_NAME);
        assertThat(testContractType.getContractTypeCode()).isEqualTo(DEFAULT_CONTRACT_TYPE_CODE);
    }

    @Test
    @Transactional
    void createContractTypeWithExistingId() throws Exception {
        // Create the ContractType with an existing ID
        contractType.setId(1L);

        int databaseSizeBeforeCreate = contractTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contractType)))
            .andExpect(status().isBadRequest());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContractTypes() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList
        restContractTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractType.getId().intValue())))
            .andExpect(jsonPath("$.[*].contractTypeId").value(hasItem(DEFAULT_CONTRACT_TYPE_ID)))
            .andExpect(jsonPath("$.[*].contractTypeName").value(hasItem(DEFAULT_CONTRACT_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].contractTypeCode").value(hasItem(DEFAULT_CONTRACT_TYPE_CODE)));
    }

    @Test
    @Transactional
    void getContractType() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get the contractType
        restContractTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, contractType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contractType.getId().intValue()))
            .andExpect(jsonPath("$.contractTypeId").value(DEFAULT_CONTRACT_TYPE_ID))
            .andExpect(jsonPath("$.contractTypeName").value(DEFAULT_CONTRACT_TYPE_NAME))
            .andExpect(jsonPath("$.contractTypeCode").value(DEFAULT_CONTRACT_TYPE_CODE));
    }

    @Test
    @Transactional
    void getContractTypesByIdFiltering() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        Long id = contractType.getId();

        defaultContractTypeShouldBeFound("id.equals=" + id);
        defaultContractTypeShouldNotBeFound("id.notEquals=" + id);

        defaultContractTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContractTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultContractTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContractTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeId equals to DEFAULT_CONTRACT_TYPE_ID
        defaultContractTypeShouldBeFound("contractTypeId.equals=" + DEFAULT_CONTRACT_TYPE_ID);

        // Get all the contractTypeList where contractTypeId equals to UPDATED_CONTRACT_TYPE_ID
        defaultContractTypeShouldNotBeFound("contractTypeId.equals=" + UPDATED_CONTRACT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeId not equals to DEFAULT_CONTRACT_TYPE_ID
        defaultContractTypeShouldNotBeFound("contractTypeId.notEquals=" + DEFAULT_CONTRACT_TYPE_ID);

        // Get all the contractTypeList where contractTypeId not equals to UPDATED_CONTRACT_TYPE_ID
        defaultContractTypeShouldBeFound("contractTypeId.notEquals=" + UPDATED_CONTRACT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeId in DEFAULT_CONTRACT_TYPE_ID or UPDATED_CONTRACT_TYPE_ID
        defaultContractTypeShouldBeFound("contractTypeId.in=" + DEFAULT_CONTRACT_TYPE_ID + "," + UPDATED_CONTRACT_TYPE_ID);

        // Get all the contractTypeList where contractTypeId equals to UPDATED_CONTRACT_TYPE_ID
        defaultContractTypeShouldNotBeFound("contractTypeId.in=" + UPDATED_CONTRACT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeId is not null
        defaultContractTypeShouldBeFound("contractTypeId.specified=true");

        // Get all the contractTypeList where contractTypeId is null
        defaultContractTypeShouldNotBeFound("contractTypeId.specified=false");
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeIdContainsSomething() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeId contains DEFAULT_CONTRACT_TYPE_ID
        defaultContractTypeShouldBeFound("contractTypeId.contains=" + DEFAULT_CONTRACT_TYPE_ID);

        // Get all the contractTypeList where contractTypeId contains UPDATED_CONTRACT_TYPE_ID
        defaultContractTypeShouldNotBeFound("contractTypeId.contains=" + UPDATED_CONTRACT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeIdNotContainsSomething() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeId does not contain DEFAULT_CONTRACT_TYPE_ID
        defaultContractTypeShouldNotBeFound("contractTypeId.doesNotContain=" + DEFAULT_CONTRACT_TYPE_ID);

        // Get all the contractTypeList where contractTypeId does not contain UPDATED_CONTRACT_TYPE_ID
        defaultContractTypeShouldBeFound("contractTypeId.doesNotContain=" + UPDATED_CONTRACT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeName equals to DEFAULT_CONTRACT_TYPE_NAME
        defaultContractTypeShouldBeFound("contractTypeName.equals=" + DEFAULT_CONTRACT_TYPE_NAME);

        // Get all the contractTypeList where contractTypeName equals to UPDATED_CONTRACT_TYPE_NAME
        defaultContractTypeShouldNotBeFound("contractTypeName.equals=" + UPDATED_CONTRACT_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeName not equals to DEFAULT_CONTRACT_TYPE_NAME
        defaultContractTypeShouldNotBeFound("contractTypeName.notEquals=" + DEFAULT_CONTRACT_TYPE_NAME);

        // Get all the contractTypeList where contractTypeName not equals to UPDATED_CONTRACT_TYPE_NAME
        defaultContractTypeShouldBeFound("contractTypeName.notEquals=" + UPDATED_CONTRACT_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeName in DEFAULT_CONTRACT_TYPE_NAME or UPDATED_CONTRACT_TYPE_NAME
        defaultContractTypeShouldBeFound("contractTypeName.in=" + DEFAULT_CONTRACT_TYPE_NAME + "," + UPDATED_CONTRACT_TYPE_NAME);

        // Get all the contractTypeList where contractTypeName equals to UPDATED_CONTRACT_TYPE_NAME
        defaultContractTypeShouldNotBeFound("contractTypeName.in=" + UPDATED_CONTRACT_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeName is not null
        defaultContractTypeShouldBeFound("contractTypeName.specified=true");

        // Get all the contractTypeList where contractTypeName is null
        defaultContractTypeShouldNotBeFound("contractTypeName.specified=false");
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeNameContainsSomething() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeName contains DEFAULT_CONTRACT_TYPE_NAME
        defaultContractTypeShouldBeFound("contractTypeName.contains=" + DEFAULT_CONTRACT_TYPE_NAME);

        // Get all the contractTypeList where contractTypeName contains UPDATED_CONTRACT_TYPE_NAME
        defaultContractTypeShouldNotBeFound("contractTypeName.contains=" + UPDATED_CONTRACT_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeNameNotContainsSomething() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeName does not contain DEFAULT_CONTRACT_TYPE_NAME
        defaultContractTypeShouldNotBeFound("contractTypeName.doesNotContain=" + DEFAULT_CONTRACT_TYPE_NAME);

        // Get all the contractTypeList where contractTypeName does not contain UPDATED_CONTRACT_TYPE_NAME
        defaultContractTypeShouldBeFound("contractTypeName.doesNotContain=" + UPDATED_CONTRACT_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeCode equals to DEFAULT_CONTRACT_TYPE_CODE
        defaultContractTypeShouldBeFound("contractTypeCode.equals=" + DEFAULT_CONTRACT_TYPE_CODE);

        // Get all the contractTypeList where contractTypeCode equals to UPDATED_CONTRACT_TYPE_CODE
        defaultContractTypeShouldNotBeFound("contractTypeCode.equals=" + UPDATED_CONTRACT_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeCode not equals to DEFAULT_CONTRACT_TYPE_CODE
        defaultContractTypeShouldNotBeFound("contractTypeCode.notEquals=" + DEFAULT_CONTRACT_TYPE_CODE);

        // Get all the contractTypeList where contractTypeCode not equals to UPDATED_CONTRACT_TYPE_CODE
        defaultContractTypeShouldBeFound("contractTypeCode.notEquals=" + UPDATED_CONTRACT_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeCodeIsInShouldWork() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeCode in DEFAULT_CONTRACT_TYPE_CODE or UPDATED_CONTRACT_TYPE_CODE
        defaultContractTypeShouldBeFound("contractTypeCode.in=" + DEFAULT_CONTRACT_TYPE_CODE + "," + UPDATED_CONTRACT_TYPE_CODE);

        // Get all the contractTypeList where contractTypeCode equals to UPDATED_CONTRACT_TYPE_CODE
        defaultContractTypeShouldNotBeFound("contractTypeCode.in=" + UPDATED_CONTRACT_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeCode is not null
        defaultContractTypeShouldBeFound("contractTypeCode.specified=true");

        // Get all the contractTypeList where contractTypeCode is null
        defaultContractTypeShouldNotBeFound("contractTypeCode.specified=false");
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeCodeContainsSomething() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeCode contains DEFAULT_CONTRACT_TYPE_CODE
        defaultContractTypeShouldBeFound("contractTypeCode.contains=" + DEFAULT_CONTRACT_TYPE_CODE);

        // Get all the contractTypeList where contractTypeCode contains UPDATED_CONTRACT_TYPE_CODE
        defaultContractTypeShouldNotBeFound("contractTypeCode.contains=" + UPDATED_CONTRACT_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllContractTypesByContractTypeCodeNotContainsSomething() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where contractTypeCode does not contain DEFAULT_CONTRACT_TYPE_CODE
        defaultContractTypeShouldNotBeFound("contractTypeCode.doesNotContain=" + DEFAULT_CONTRACT_TYPE_CODE);

        // Get all the contractTypeList where contractTypeCode does not contain UPDATED_CONTRACT_TYPE_CODE
        defaultContractTypeShouldBeFound("contractTypeCode.doesNotContain=" + UPDATED_CONTRACT_TYPE_CODE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContractTypeShouldBeFound(String filter) throws Exception {
        restContractTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractType.getId().intValue())))
            .andExpect(jsonPath("$.[*].contractTypeId").value(hasItem(DEFAULT_CONTRACT_TYPE_ID)))
            .andExpect(jsonPath("$.[*].contractTypeName").value(hasItem(DEFAULT_CONTRACT_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].contractTypeCode").value(hasItem(DEFAULT_CONTRACT_TYPE_CODE)));

        // Check, that the count call also returns 1
        restContractTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContractTypeShouldNotBeFound(String filter) throws Exception {
        restContractTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContractTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingContractType() throws Exception {
        // Get the contractType
        restContractTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContractType() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        int databaseSizeBeforeUpdate = contractTypeRepository.findAll().size();

        // Update the contractType
        ContractType updatedContractType = contractTypeRepository.findById(contractType.getId()).get();
        // Disconnect from session so that the updates on updatedContractType are not directly saved in db
        em.detach(updatedContractType);
        updatedContractType
            .contractTypeId(UPDATED_CONTRACT_TYPE_ID)
            .contractTypeName(UPDATED_CONTRACT_TYPE_NAME)
            .contractTypeCode(UPDATED_CONTRACT_TYPE_CODE);

        restContractTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContractType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedContractType))
            )
            .andExpect(status().isOk());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeUpdate);
        ContractType testContractType = contractTypeList.get(contractTypeList.size() - 1);
        assertThat(testContractType.getContractTypeId()).isEqualTo(UPDATED_CONTRACT_TYPE_ID);
        assertThat(testContractType.getContractTypeName()).isEqualTo(UPDATED_CONTRACT_TYPE_NAME);
        assertThat(testContractType.getContractTypeCode()).isEqualTo(UPDATED_CONTRACT_TYPE_CODE);
    }

    @Test
    @Transactional
    void putNonExistingContractType() throws Exception {
        int databaseSizeBeforeUpdate = contractTypeRepository.findAll().size();
        contractType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contractType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contractType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContractType() throws Exception {
        int databaseSizeBeforeUpdate = contractTypeRepository.findAll().size();
        contractType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contractType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContractType() throws Exception {
        int databaseSizeBeforeUpdate = contractTypeRepository.findAll().size();
        contractType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contractType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContractTypeWithPatch() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        int databaseSizeBeforeUpdate = contractTypeRepository.findAll().size();

        // Update the contractType using partial update
        ContractType partialUpdatedContractType = new ContractType();
        partialUpdatedContractType.setId(contractType.getId());

        partialUpdatedContractType.contractTypeName(UPDATED_CONTRACT_TYPE_NAME).contractTypeCode(UPDATED_CONTRACT_TYPE_CODE);

        restContractTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContractType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContractType))
            )
            .andExpect(status().isOk());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeUpdate);
        ContractType testContractType = contractTypeList.get(contractTypeList.size() - 1);
        assertThat(testContractType.getContractTypeId()).isEqualTo(DEFAULT_CONTRACT_TYPE_ID);
        assertThat(testContractType.getContractTypeName()).isEqualTo(UPDATED_CONTRACT_TYPE_NAME);
        assertThat(testContractType.getContractTypeCode()).isEqualTo(UPDATED_CONTRACT_TYPE_CODE);
    }

    @Test
    @Transactional
    void fullUpdateContractTypeWithPatch() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        int databaseSizeBeforeUpdate = contractTypeRepository.findAll().size();

        // Update the contractType using partial update
        ContractType partialUpdatedContractType = new ContractType();
        partialUpdatedContractType.setId(contractType.getId());

        partialUpdatedContractType
            .contractTypeId(UPDATED_CONTRACT_TYPE_ID)
            .contractTypeName(UPDATED_CONTRACT_TYPE_NAME)
            .contractTypeCode(UPDATED_CONTRACT_TYPE_CODE);

        restContractTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContractType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContractType))
            )
            .andExpect(status().isOk());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeUpdate);
        ContractType testContractType = contractTypeList.get(contractTypeList.size() - 1);
        assertThat(testContractType.getContractTypeId()).isEqualTo(UPDATED_CONTRACT_TYPE_ID);
        assertThat(testContractType.getContractTypeName()).isEqualTo(UPDATED_CONTRACT_TYPE_NAME);
        assertThat(testContractType.getContractTypeCode()).isEqualTo(UPDATED_CONTRACT_TYPE_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingContractType() throws Exception {
        int databaseSizeBeforeUpdate = contractTypeRepository.findAll().size();
        contractType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contractType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contractType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContractType() throws Exception {
        int databaseSizeBeforeUpdate = contractTypeRepository.findAll().size();
        contractType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contractType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContractType() throws Exception {
        int databaseSizeBeforeUpdate = contractTypeRepository.findAll().size();
        contractType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contractType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContractType() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        int databaseSizeBeforeDelete = contractTypeRepository.findAll().size();

        // Delete the contractType
        restContractTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, contractType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
