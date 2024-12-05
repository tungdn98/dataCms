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
import vn.com.datamanager.domain.Unit;
import vn.com.datamanager.repository.UnitRepository;
import vn.com.datamanager.service.criteria.UnitCriteria;

/**
 * Integration tests for the {@link UnitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UnitResourceIT {

    private static final String DEFAULT_UNIT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnitMockMvc;

    private Unit unit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unit createEntity(EntityManager em) {
        Unit unit = new Unit().unitCode(DEFAULT_UNIT_CODE).unitName(DEFAULT_UNIT_NAME);
        return unit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unit createUpdatedEntity(EntityManager em) {
        Unit unit = new Unit().unitCode(UPDATED_UNIT_CODE).unitName(UPDATED_UNIT_NAME);
        return unit;
    }

    @BeforeEach
    public void initTest() {
        unit = createEntity(em);
    }

    @Test
    @Transactional
    void createUnit() throws Exception {
        int databaseSizeBeforeCreate = unitRepository.findAll().size();
        // Create the Unit
        restUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isCreated());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeCreate + 1);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getUnitCode()).isEqualTo(DEFAULT_UNIT_CODE);
        assertThat(testUnit.getUnitName()).isEqualTo(DEFAULT_UNIT_NAME);
    }

    @Test
    @Transactional
    void createUnitWithExistingId() throws Exception {
        // Create the Unit with an existing ID
        unit.setId(1L);

        int databaseSizeBeforeCreate = unitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUnits() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList
        restUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unit.getId().intValue())))
            .andExpect(jsonPath("$.[*].unitCode").value(hasItem(DEFAULT_UNIT_CODE)))
            .andExpect(jsonPath("$.[*].unitName").value(hasItem(DEFAULT_UNIT_NAME)));
    }

    @Test
    @Transactional
    void getUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get the unit
        restUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, unit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unit.getId().intValue()))
            .andExpect(jsonPath("$.unitCode").value(DEFAULT_UNIT_CODE))
            .andExpect(jsonPath("$.unitName").value(DEFAULT_UNIT_NAME));
    }

    @Test
    @Transactional
    void getUnitsByIdFiltering() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        Long id = unit.getId();

        defaultUnitShouldBeFound("id.equals=" + id);
        defaultUnitShouldNotBeFound("id.notEquals=" + id);

        defaultUnitShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUnitShouldNotBeFound("id.greaterThan=" + id);

        defaultUnitShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUnitShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUnitsByUnitCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where unitCode equals to DEFAULT_UNIT_CODE
        defaultUnitShouldBeFound("unitCode.equals=" + DEFAULT_UNIT_CODE);

        // Get all the unitList where unitCode equals to UPDATED_UNIT_CODE
        defaultUnitShouldNotBeFound("unitCode.equals=" + UPDATED_UNIT_CODE);
    }

    @Test
    @Transactional
    void getAllUnitsByUnitCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where unitCode not equals to DEFAULT_UNIT_CODE
        defaultUnitShouldNotBeFound("unitCode.notEquals=" + DEFAULT_UNIT_CODE);

        // Get all the unitList where unitCode not equals to UPDATED_UNIT_CODE
        defaultUnitShouldBeFound("unitCode.notEquals=" + UPDATED_UNIT_CODE);
    }

    @Test
    @Transactional
    void getAllUnitsByUnitCodeIsInShouldWork() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where unitCode in DEFAULT_UNIT_CODE or UPDATED_UNIT_CODE
        defaultUnitShouldBeFound("unitCode.in=" + DEFAULT_UNIT_CODE + "," + UPDATED_UNIT_CODE);

        // Get all the unitList where unitCode equals to UPDATED_UNIT_CODE
        defaultUnitShouldNotBeFound("unitCode.in=" + UPDATED_UNIT_CODE);
    }

    @Test
    @Transactional
    void getAllUnitsByUnitCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where unitCode is not null
        defaultUnitShouldBeFound("unitCode.specified=true");

        // Get all the unitList where unitCode is null
        defaultUnitShouldNotBeFound("unitCode.specified=false");
    }

    @Test
    @Transactional
    void getAllUnitsByUnitCodeContainsSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where unitCode contains DEFAULT_UNIT_CODE
        defaultUnitShouldBeFound("unitCode.contains=" + DEFAULT_UNIT_CODE);

        // Get all the unitList where unitCode contains UPDATED_UNIT_CODE
        defaultUnitShouldNotBeFound("unitCode.contains=" + UPDATED_UNIT_CODE);
    }

    @Test
    @Transactional
    void getAllUnitsByUnitCodeNotContainsSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where unitCode does not contain DEFAULT_UNIT_CODE
        defaultUnitShouldNotBeFound("unitCode.doesNotContain=" + DEFAULT_UNIT_CODE);

        // Get all the unitList where unitCode does not contain UPDATED_UNIT_CODE
        defaultUnitShouldBeFound("unitCode.doesNotContain=" + UPDATED_UNIT_CODE);
    }

    @Test
    @Transactional
    void getAllUnitsByUnitNameIsEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where unitName equals to DEFAULT_UNIT_NAME
        defaultUnitShouldBeFound("unitName.equals=" + DEFAULT_UNIT_NAME);

        // Get all the unitList where unitName equals to UPDATED_UNIT_NAME
        defaultUnitShouldNotBeFound("unitName.equals=" + UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    void getAllUnitsByUnitNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where unitName not equals to DEFAULT_UNIT_NAME
        defaultUnitShouldNotBeFound("unitName.notEquals=" + DEFAULT_UNIT_NAME);

        // Get all the unitList where unitName not equals to UPDATED_UNIT_NAME
        defaultUnitShouldBeFound("unitName.notEquals=" + UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    void getAllUnitsByUnitNameIsInShouldWork() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where unitName in DEFAULT_UNIT_NAME or UPDATED_UNIT_NAME
        defaultUnitShouldBeFound("unitName.in=" + DEFAULT_UNIT_NAME + "," + UPDATED_UNIT_NAME);

        // Get all the unitList where unitName equals to UPDATED_UNIT_NAME
        defaultUnitShouldNotBeFound("unitName.in=" + UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    void getAllUnitsByUnitNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where unitName is not null
        defaultUnitShouldBeFound("unitName.specified=true");

        // Get all the unitList where unitName is null
        defaultUnitShouldNotBeFound("unitName.specified=false");
    }

    @Test
    @Transactional
    void getAllUnitsByUnitNameContainsSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where unitName contains DEFAULT_UNIT_NAME
        defaultUnitShouldBeFound("unitName.contains=" + DEFAULT_UNIT_NAME);

        // Get all the unitList where unitName contains UPDATED_UNIT_NAME
        defaultUnitShouldNotBeFound("unitName.contains=" + UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    void getAllUnitsByUnitNameNotContainsSomething() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList where unitName does not contain DEFAULT_UNIT_NAME
        defaultUnitShouldNotBeFound("unitName.doesNotContain=" + DEFAULT_UNIT_NAME);

        // Get all the unitList where unitName does not contain UPDATED_UNIT_NAME
        defaultUnitShouldBeFound("unitName.doesNotContain=" + UPDATED_UNIT_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUnitShouldBeFound(String filter) throws Exception {
        restUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unit.getId().intValue())))
            .andExpect(jsonPath("$.[*].unitCode").value(hasItem(DEFAULT_UNIT_CODE)))
            .andExpect(jsonPath("$.[*].unitName").value(hasItem(DEFAULT_UNIT_NAME)));

        // Check, that the count call also returns 1
        restUnitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUnitShouldNotBeFound(String filter) throws Exception {
        restUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUnitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUnit() throws Exception {
        // Get the unit
        restUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        int databaseSizeBeforeUpdate = unitRepository.findAll().size();

        // Update the unit
        Unit updatedUnit = unitRepository.findById(unit.getId()).get();
        // Disconnect from session so that the updates on updatedUnit are not directly saved in db
        em.detach(updatedUnit);
        updatedUnit.unitCode(UPDATED_UNIT_CODE).unitName(UPDATED_UNIT_NAME);

        restUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUnit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUnit))
            )
            .andExpect(status().isOk());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getUnitCode()).isEqualTo(UPDATED_UNIT_CODE);
        assertThat(testUnit.getUnitName()).isEqualTo(UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    void putNonExistingUnit() throws Exception {
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();
        unit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUnit() throws Exception {
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();
        unit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUnit() throws Exception {
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();
        unit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUnitWithPatch() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        int databaseSizeBeforeUpdate = unitRepository.findAll().size();

        // Update the unit using partial update
        Unit partialUpdatedUnit = new Unit();
        partialUpdatedUnit.setId(unit.getId());

        partialUpdatedUnit.unitCode(UPDATED_UNIT_CODE).unitName(UPDATED_UNIT_NAME);

        restUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnit))
            )
            .andExpect(status().isOk());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getUnitCode()).isEqualTo(UPDATED_UNIT_CODE);
        assertThat(testUnit.getUnitName()).isEqualTo(UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateUnitWithPatch() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        int databaseSizeBeforeUpdate = unitRepository.findAll().size();

        // Update the unit using partial update
        Unit partialUpdatedUnit = new Unit();
        partialUpdatedUnit.setId(unit.getId());

        partialUpdatedUnit.unitCode(UPDATED_UNIT_CODE).unitName(UPDATED_UNIT_NAME);

        restUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnit))
            )
            .andExpect(status().isOk());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getUnitCode()).isEqualTo(UPDATED_UNIT_CODE);
        assertThat(testUnit.getUnitName()).isEqualTo(UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingUnit() throws Exception {
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();
        unit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, unit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUnit() throws Exception {
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();
        unit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUnit() throws Exception {
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();
        unit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        int databaseSizeBeforeDelete = unitRepository.findAll().size();

        // Delete the unit
        restUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, unit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
