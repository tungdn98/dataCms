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
import vn.com.datamanager.domain.OpportunityStage;
import vn.com.datamanager.domain.Product;
import vn.com.datamanager.repository.OpportunityStageRepository;
import vn.com.datamanager.service.OpportunityStageQueryService;
import vn.com.datamanager.service.OpportunityStageService;
import vn.com.datamanager.service.criteria.OpportunityStageCriteria;
import vn.com.datamanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.com.datamanager.domain.OpportunityStage}.
 */
@RestController
@RequestMapping("/api")
public class OpportunityStageResource {

    private final Logger log = LoggerFactory.getLogger(OpportunityStageResource.class);

    private static final String ENTITY_NAME = "opportunityStage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpportunityStageService opportunityStageService;

    private final OpportunityStageRepository opportunityStageRepository;

    private final OpportunityStageQueryService opportunityStageQueryService;

    public OpportunityStageResource(
        OpportunityStageService opportunityStageService,
        OpportunityStageRepository opportunityStageRepository,
        OpportunityStageQueryService opportunityStageQueryService
    ) {
        this.opportunityStageService = opportunityStageService;
        this.opportunityStageRepository = opportunityStageRepository;
        this.opportunityStageQueryService = opportunityStageQueryService;
    }

    /**
     * {@code POST  /opportunity-stages} : Create a new opportunityStage.
     *
     * @param opportunityStage the opportunityStage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opportunityStage, or with status {@code 400 (Bad Request)} if the opportunityStage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/opportunity-stages")
    public ResponseEntity<OpportunityStage> createOpportunityStage(@RequestBody OpportunityStage opportunityStage)
        throws URISyntaxException {
        log.debug("REST request to save OpportunityStage : {}", opportunityStage);
        if (opportunityStage.getId() != null) {
            throw new BadRequestAlertException("A new opportunityStage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OpportunityStage result = opportunityStageService.save(opportunityStage);
        return ResponseEntity
            .created(new URI("/api/opportunity-stages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /opportunity-stages/:id} : Updates an existing opportunityStage.
     *
     * @param id the id of the opportunityStage to save.
     * @param opportunityStage the opportunityStage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opportunityStage,
     * or with status {@code 400 (Bad Request)} if the opportunityStage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opportunityStage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/opportunity-stages/{id}")
    public ResponseEntity<OpportunityStage> updateOpportunityStage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OpportunityStage opportunityStage
    ) throws URISyntaxException {
        log.debug("REST request to update OpportunityStage : {}, {}", id, opportunityStage);
        if (opportunityStage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opportunityStage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opportunityStageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OpportunityStage result = opportunityStageService.update(opportunityStage);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, opportunityStage.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /opportunity-stages/:id} : Partial updates given fields of an existing opportunityStage, field will ignore if it is null
     *
     * @param id the id of the opportunityStage to save.
     * @param opportunityStage the opportunityStage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opportunityStage,
     * or with status {@code 400 (Bad Request)} if the opportunityStage is not valid,
     * or with status {@code 404 (Not Found)} if the opportunityStage is not found,
     * or with status {@code 500 (Internal Server Error)} if the opportunityStage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/opportunity-stages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OpportunityStage> partialUpdateOpportunityStage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OpportunityStage opportunityStage
    ) throws URISyntaxException {
        log.debug("REST request to partial update OpportunityStage partially : {}, {}", id, opportunityStage);
        if (opportunityStage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opportunityStage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opportunityStageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OpportunityStage> result = opportunityStageService.partialUpdate(opportunityStage);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, opportunityStage.getId().toString())
        );
    }

    /**
     * {@code GET  /opportunity-stages} : get all the opportunityStages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opportunityStages in body.
     */
    @GetMapping("/opportunity-stages")
    public ResponseEntity<List<OpportunityStage>> getAllOpportunityStages(
        OpportunityStageCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get OpportunityStages by criteria: {}", criteria);
        Page<OpportunityStage> page = opportunityStageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /opportunity-stages/count} : count all the opportunityStages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/opportunity-stages/count")
    public ResponseEntity<Long> countOpportunityStages(OpportunityStageCriteria criteria) {
        log.debug("REST request to count OpportunityStages by criteria: {}", criteria);
        return ResponseEntity.ok().body(opportunityStageQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /opportunity-stages/:id} : get the "id" opportunityStage.
     *
     * @param id the id of the opportunityStage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opportunityStage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/opportunity-stages/{id}")
    public ResponseEntity<OpportunityStage> getOpportunityStage(@PathVariable Long id) {
        log.debug("REST request to get OpportunityStage : {}", id);
        Optional<OpportunityStage> opportunityStage = opportunityStageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(opportunityStage);
    }

    /**
     * {@code DELETE  /opportunity-stages/:id} : delete the "id" opportunityStage.
     *
     * @param id the id of the opportunityStage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/opportunity-stages/{id}")
    public ResponseEntity<Void> deleteOpportunityStage(@PathVariable Long id) {
        log.debug("REST request to delete OpportunityStage : {}", id);
        opportunityStageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
    @PostMapping("/opportunity-stage/batch")
    public ResponseEntity<Integer> saveBatchSaleContract(@RequestBody List<OpportunityStage> opportunityStages) {
        log.debug("REST request to save a list of opportunityStages : {}", opportunityStages);
        for (OpportunityStage opportunityStage : opportunityStages) {
            if (opportunityStage.getId() != null) {
                throw new BadRequestAlertException("A new opportunityStage cannot already have an ID", ENTITY_NAME, "idexists");
            }
        }
        Integer savedCount = opportunityStageRepository.saveAll(opportunityStages).size();
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, savedCount.toString()))
            .body(savedCount);
    }
}
