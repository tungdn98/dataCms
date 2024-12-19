package vn.com.datamanager.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
import vn.com.datamanager.domain.SaleOpportunity;
import vn.com.datamanager.repository.SaleOpportunityRepository;
import vn.com.datamanager.service.SaleOpportunityQueryService;
import vn.com.datamanager.service.SaleOpportunityService;
import vn.com.datamanager.service.criteria.SaleOpportunityCriteria;
import vn.com.datamanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.com.datamanager.domain.SaleOpportunity}.
 */
@RestController
@RequestMapping("/api")
public class SaleOpportunityResource {

    private final Logger log = LoggerFactory.getLogger(SaleOpportunityResource.class);

    private static final String ENTITY_NAME = "saleOpportunity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaleOpportunityService saleOpportunityService;

    private final SaleOpportunityRepository saleOpportunityRepository;

    private final SaleOpportunityQueryService saleOpportunityQueryService;

    public SaleOpportunityResource(
        SaleOpportunityService saleOpportunityService,
        SaleOpportunityRepository saleOpportunityRepository,
        SaleOpportunityQueryService saleOpportunityQueryService
    ) {
        this.saleOpportunityService = saleOpportunityService;
        this.saleOpportunityRepository = saleOpportunityRepository;
        this.saleOpportunityQueryService = saleOpportunityQueryService;
    }

    /**
     * {@code POST  /sale-opportunities} : Create a new saleOpportunity.
     *
     * @param saleOpportunity the saleOpportunity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new saleOpportunity, or with status {@code 400 (Bad Request)} if the saleOpportunity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sale-opportunities")
    public ResponseEntity<SaleOpportunity> createSaleOpportunity(@Valid @RequestBody SaleOpportunity saleOpportunity)
        throws URISyntaxException {
        log.debug("REST request to save SaleOpportunity : {}", saleOpportunity);
        if (saleOpportunity.getId() != null) {
            throw new BadRequestAlertException("A new saleOpportunity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SaleOpportunity result = saleOpportunityService.save(saleOpportunity);
        return ResponseEntity
            .created(new URI("/api/sale-opportunities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sale-opportunities/:id} : Updates an existing saleOpportunity.
     *
     * @param id the id of the saleOpportunity to save.
     * @param saleOpportunity the saleOpportunity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saleOpportunity,
     * or with status {@code 400 (Bad Request)} if the saleOpportunity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the saleOpportunity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sale-opportunities/{id}")
    public ResponseEntity<SaleOpportunity> updateSaleOpportunity(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SaleOpportunity saleOpportunity
    ) throws URISyntaxException {
        log.debug("REST request to update SaleOpportunity : {}, {}", id, saleOpportunity);
        if (saleOpportunity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saleOpportunity.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleOpportunityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SaleOpportunity result = saleOpportunityService.update(saleOpportunity);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, saleOpportunity.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sale-opportunities/:id} : Partial updates given fields of an existing saleOpportunity, field will ignore if it is null
     *
     * @param id the id of the saleOpportunity to save.
     * @param saleOpportunity the saleOpportunity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saleOpportunity,
     * or with status {@code 400 (Bad Request)} if the saleOpportunity is not valid,
     * or with status {@code 404 (Not Found)} if the saleOpportunity is not found,
     * or with status {@code 500 (Internal Server Error)} if the saleOpportunity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sale-opportunities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SaleOpportunity> partialUpdateSaleOpportunity(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SaleOpportunity saleOpportunity
    ) throws URISyntaxException {
        log.debug("REST request to partial update SaleOpportunity partially : {}, {}", id, saleOpportunity);
        if (saleOpportunity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saleOpportunity.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleOpportunityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SaleOpportunity> result = saleOpportunityService.partialUpdate(saleOpportunity);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, saleOpportunity.getId().toString())
        );
    }

    /**
     * {@code GET  /sale-opportunities} : get all the saleOpportunities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saleOpportunities in body.
     */
    @GetMapping("/sale-opportunities")
    public ResponseEntity<List<SaleOpportunity>> getAllSaleOpportunities(
        SaleOpportunityCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SaleOpportunities by criteria: {}", criteria);
        Page<SaleOpportunity> page = saleOpportunityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sale-opportunities/count} : count all the saleOpportunities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sale-opportunities/count")
    public ResponseEntity<Long> countSaleOpportunities(SaleOpportunityCriteria criteria) {
        log.debug("REST request to count SaleOpportunities by criteria: {}", criteria);
        return ResponseEntity.ok().body(saleOpportunityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sale-opportunities/:id} : get the "id" saleOpportunity.
     *
     * @param id the id of the saleOpportunity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the saleOpportunity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sale-opportunities/{id}")
    public ResponseEntity<SaleOpportunity> getSaleOpportunity(@PathVariable Long id) {
        log.debug("REST request to get SaleOpportunity : {}", id);
        Optional<SaleOpportunity> saleOpportunity = saleOpportunityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(saleOpportunity);
    }

    /**
     * {@code DELETE  /sale-opportunities/:id} : delete the "id" saleOpportunity.
     *
     * @param id the id of the saleOpportunity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sale-opportunities/{id}")
    public ResponseEntity<Void> deleteSaleOpportunity(@PathVariable Long id) {
        log.debug("REST request to delete SaleOpportunity : {}", id);
        saleOpportunityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
