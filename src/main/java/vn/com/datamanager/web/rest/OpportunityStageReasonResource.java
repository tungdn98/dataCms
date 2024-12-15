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
import vn.com.datamanager.domain.OpportunityStageReason;
import vn.com.datamanager.domain.Product;
import vn.com.datamanager.repository.OpportunityStageReasonRepository;
import vn.com.datamanager.service.OpportunityStageReasonQueryService;
import vn.com.datamanager.service.OpportunityStageReasonService;
import vn.com.datamanager.service.criteria.OpportunityStageReasonCriteria;
import vn.com.datamanager.web.rest.errors.BadRequestAlertException;

import static vn.com.datamanager.repository.OpportunityStageReasonRepository.*;

/**
 * REST controller for managing {@link vn.com.datamanager.domain.OpportunityStageReason}.
 */
@RestController
@RequestMapping("/api")
public class OpportunityStageReasonResource {

    private final Logger log = LoggerFactory.getLogger(OpportunityStageReasonResource.class);

    private static final String ENTITY_NAME = "opportunityStageReason";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpportunityStageReasonService opportunityStageReasonService;

    private final OpportunityStageReasonRepository opportunityStageReasonRepository;

    private final OpportunityStageReasonQueryService opportunityStageReasonQueryService;

    public OpportunityStageReasonResource(
        OpportunityStageReasonService opportunityStageReasonService,
        OpportunityStageReasonRepository opportunityStageReasonRepository,
        OpportunityStageReasonQueryService opportunityStageReasonQueryService
    ) {
        this.opportunityStageReasonService = opportunityStageReasonService;
        this.opportunityStageReasonRepository = opportunityStageReasonRepository;
        this.opportunityStageReasonQueryService = opportunityStageReasonQueryService;
    }

    /**
     * {@code POST  /opportunity-stage-reasons} : Create a new opportunityStageReason.
     *
     * @param opportunityStageReason the opportunityStageReason to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opportunityStageReason, or with status {@code 400 (Bad Request)} if the opportunityStageReason has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/opportunity-stage-reasons")
    public ResponseEntity<OpportunityStageReason> createOpportunityStageReason(@RequestBody OpportunityStageReason opportunityStageReason)
        throws URISyntaxException {
        log.debug("REST request to save OpportunityStageReason : {}", opportunityStageReason);
        if (opportunityStageReason.getId() != null) {
            throw new BadRequestAlertException("A new opportunityStageReason cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OpportunityStageReason result = opportunityStageReasonService.save(opportunityStageReason);
        return ResponseEntity
            .created(new URI("/api/opportunity-stage-reasons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /opportunity-stage-reasons/:id} : Updates an existing opportunityStageReason.
     *
     * @param id the id of the opportunityStageReason to save.
     * @param opportunityStageReason the opportunityStageReason to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opportunityStageReason,
     * or with status {@code 400 (Bad Request)} if the opportunityStageReason is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opportunityStageReason couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/opportunity-stage-reasons/{id}")
    public ResponseEntity<OpportunityStageReason> updateOpportunityStageReason(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OpportunityStageReason opportunityStageReason
    ) throws URISyntaxException {
        log.debug("REST request to update OpportunityStageReason : {}, {}", id, opportunityStageReason);
        if (opportunityStageReason.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opportunityStageReason.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opportunityStageReasonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OpportunityStageReason result = opportunityStageReasonService.update(opportunityStageReason);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, opportunityStageReason.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /opportunity-stage-reasons/:id} : Partial updates given fields of an existing opportunityStageReason, field will ignore if it is null
     *
     * @param id the id of the opportunityStageReason to save.
     * @param opportunityStageReason the opportunityStageReason to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opportunityStageReason,
     * or with status {@code 400 (Bad Request)} if the opportunityStageReason is not valid,
     * or with status {@code 404 (Not Found)} if the opportunityStageReason is not found,
     * or with status {@code 500 (Internal Server Error)} if the opportunityStageReason couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/opportunity-stage-reasons/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OpportunityStageReason> partialUpdateOpportunityStageReason(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OpportunityStageReason opportunityStageReason
    ) throws URISyntaxException {
        log.debug("REST request to partial update OpportunityStageReason partially : {}, {}", id, opportunityStageReason);
        if (opportunityStageReason.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opportunityStageReason.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opportunityStageReasonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OpportunityStageReason> result = opportunityStageReasonService.partialUpdate(opportunityStageReason);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, opportunityStageReason.getId().toString())
        );
    }

    /**
     * {@code GET  /opportunity-stage-reasons} : get all the opportunityStageReasons.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opportunityStageReasons in body.
     */
    @GetMapping("/opportunity-stage-reasons")
    public ResponseEntity<List<OpportunityStageReason>> getAllOpportunityStageReasons(
        OpportunityStageReasonCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get OpportunityStageReasons by criteria: {}", criteria);
        Page<OpportunityStageReason> page = opportunityStageReasonQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /opportunity-stage-reasons/count} : count all the opportunityStageReasons.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/opportunity-stage-reasons/count")
    public ResponseEntity<Long> countOpportunityStageReasons(OpportunityStageReasonCriteria criteria) {
        log.debug("REST request to count OpportunityStageReasons by criteria: {}", criteria);
        return ResponseEntity.ok().body(opportunityStageReasonQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /opportunity-stage-reasons/:id} : get the "id" opportunityStageReason.
     *
     * @param id the id of the opportunityStageReason to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opportunityStageReason, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/opportunity-stage-reasons/{id}")
    public ResponseEntity<OpportunityStageReason> getOpportunityStageReason(@PathVariable Long id) {
        log.debug("REST request to get OpportunityStageReason : {}", id);
        Optional<OpportunityStageReason> opportunityStageReason = opportunityStageReasonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(opportunityStageReason);
    }

    /**
     * {@code DELETE  /opportunity-stage-reasons/:id} : delete the "id" opportunityStageReason.
     *
     * @param id the id of the opportunityStageReason to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/opportunity-stage-reasons/{id}")
    public ResponseEntity<Void> deleteOpportunityStageReason(@PathVariable Long id) {
        log.debug("REST request to delete OpportunityStageReason : {}", id);
        opportunityStageReasonService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/opportunity-stage-reason/batch")
    public ResponseEntity<Integer> saveBatchSaleContract(@RequestBody List<OpportunityStageReason> oppStageReasonResources) {
        log.debug("REST request to save a list of products : {}", oppStageReasonResources);
        for (OpportunityStageReason opportunityStageReason : oppStageReasonResources) {
            if (opportunityStageReason.getId() != null) {
                throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
            }
        }
        Integer savedCount = opportunityStageReasonRepository.saveAll(oppStageReasonResources).size();
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, savedCount.toString()))
            .body(savedCount);
    }
}
