package vn.com.datamanager.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import vn.com.datamanager.domain.Unit;
import vn.com.datamanager.repository.UnitRepository;
import vn.com.datamanager.service.UnitQueryService;
import vn.com.datamanager.service.UnitService;
import vn.com.datamanager.service.criteria.UnitCriteria;
import vn.com.datamanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.com.datamanager.domain.Unit}.
 */
@RestController
@RequestMapping("/api")
public class UnitResource {

    private final Logger log = LoggerFactory.getLogger(UnitResource.class);

    private static final String ENTITY_NAME = "unit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnitService unitService;

    private final UnitRepository unitRepository;

    private final UnitQueryService unitQueryService;

    public UnitResource(UnitService unitService, UnitRepository unitRepository, UnitQueryService unitQueryService) {
        this.unitService = unitService;
        this.unitRepository = unitRepository;
        this.unitQueryService = unitQueryService;
    }

    /**
     * {@code POST  /units} : Create a new unit.
     *
     * @param unit the unit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unit, or with status {@code 400 (Bad Request)} if the unit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/units")
    public ResponseEntity<Unit> createUnit(@RequestBody Unit unit) throws URISyntaxException {
        log.debug("REST request to save Unit : {}", unit);
        if (unit.getId() != null) {
            throw new BadRequestAlertException("A new unit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Unit result = unitService.save(unit);
        return ResponseEntity
            .created(new URI("/api/units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /units/:id} : Updates an existing unit.
     *
     * @param id the id of the unit to save.
     * @param unit the unit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unit,
     * or with status {@code 400 (Bad Request)} if the unit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/units/{id}")
    public ResponseEntity<Unit> updateUnit(@PathVariable(value = "id", required = false) final Long id, @RequestBody Unit unit)
        throws URISyntaxException {
        log.debug("REST request to update Unit : {}, {}", id, unit);
        if (unit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Unit result = unitService.update(unit);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, unit.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /units/:id} : Partial updates given fields of an existing unit, field will ignore if it is null
     *
     * @param id the id of the unit to save.
     * @param unit the unit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unit,
     * or with status {@code 400 (Bad Request)} if the unit is not valid,
     * or with status {@code 404 (Not Found)} if the unit is not found,
     * or with status {@code 500 (Internal Server Error)} if the unit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/units/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Unit> partialUpdateUnit(@PathVariable(value = "id", required = false) final Long id, @RequestBody Unit unit)
        throws URISyntaxException {
        log.debug("REST request to partial update Unit partially : {}, {}", id, unit);
        if (unit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Unit> result = unitService.partialUpdate(unit);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, unit.getId().toString())
        );
    }

    /**
     * {@code GET  /units} : get all the units.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of units in body.
     */
    @GetMapping("/units")
    public ResponseEntity<List<Unit>> getAllUnits(UnitCriteria criteria, @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get Units by criteria: {}", criteria);
        Page<Unit> page = unitQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /units/count} : count all the units.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/units/count")
    public ResponseEntity<Long> countUnits(UnitCriteria criteria) {
        log.debug("REST request to count Units by criteria: {}", criteria);
        return ResponseEntity.ok().body(unitQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /units/:id} : get the "id" unit.
     *
     * @param id the id of the unit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/units/{id}")
    public ResponseEntity<Unit> getUnit(@PathVariable Long id) {
        log.debug("REST request to get Unit : {}", id);
        Optional<Unit> unit = unitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unit);
    }

    /**
     * {@code DELETE  /units/:id} : delete the "id" unit.
     *
     * @param id the id of the unit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/units/{id}")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long id) {
        log.debug("REST request to delete Unit : {}", id);
        unitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
