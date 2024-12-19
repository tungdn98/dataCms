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
import vn.com.datamanager.domain.Finalcial;
import vn.com.datamanager.domain.Product;
import vn.com.datamanager.repository.FinalcialRepository;
import vn.com.datamanager.service.FinalcialQueryService;
import vn.com.datamanager.service.FinalcialService;
import vn.com.datamanager.service.criteria.FinalcialCriteria;
import vn.com.datamanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.com.datamanager.domain.Finalcial}.
 */
@RestController
@RequestMapping("/api")
public class FinalcialResource {

    private final Logger log = LoggerFactory.getLogger(FinalcialResource.class);

    private static final String ENTITY_NAME = "finalcial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FinalcialService finalcialService;

    private final FinalcialRepository finalcialRepository;

    private final FinalcialQueryService finalcialQueryService;

    public FinalcialResource(
        FinalcialService finalcialService,
        FinalcialRepository finalcialRepository,
        FinalcialQueryService finalcialQueryService
    ) {
        this.finalcialService = finalcialService;
        this.finalcialRepository = finalcialRepository;
        this.finalcialQueryService = finalcialQueryService;
    }

    /**
     * {@code POST  /finalcials} : Create a new finalcial.
     *
     * @param finalcial the finalcial to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new finalcial, or with status {@code 400 (Bad Request)} if the finalcial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/finalcials")
    public ResponseEntity<Finalcial> createFinalcial(@RequestBody Finalcial finalcial) throws URISyntaxException {
        log.debug("REST request to save Finalcial : {}", finalcial);
        if (finalcial.getId() != null) {
            throw new BadRequestAlertException("A new finalcial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Finalcial result = finalcialService.save(finalcial);
        return ResponseEntity
            .created(new URI("/api/finalcials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /finalcials/:id} : Updates an existing finalcial.
     *
     * @param id the id of the finalcial to save.
     * @param finalcial the finalcial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated finalcial,
     * or with status {@code 400 (Bad Request)} if the finalcial is not valid,
     * or with status {@code 500 (Internal Server Error)} if the finalcial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/finalcials/{id}")
    public ResponseEntity<Finalcial> updateFinalcial(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Finalcial finalcial
    ) throws URISyntaxException {
        log.debug("REST request to update Finalcial : {}, {}", id, finalcial);
        if (finalcial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, finalcial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!finalcialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Finalcial result = finalcialService.update(finalcial);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, finalcial.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /finalcials/:id} : Partial updates given fields of an existing finalcial, field will ignore if it is null
     *
     * @param id the id of the finalcial to save.
     * @param finalcial the finalcial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated finalcial,
     * or with status {@code 400 (Bad Request)} if the finalcial is not valid,
     * or with status {@code 404 (Not Found)} if the finalcial is not found,
     * or with status {@code 500 (Internal Server Error)} if the finalcial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/finalcials/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Finalcial> partialUpdateFinalcial(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Finalcial finalcial
    ) throws URISyntaxException {
        log.debug("REST request to partial update Finalcial partially : {}, {}", id, finalcial);
        if (finalcial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, finalcial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!finalcialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Finalcial> result = finalcialService.partialUpdate(finalcial);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, finalcial.getId().toString())
        );
    }

    /**
     * {@code GET  /finalcials} : get all the finalcials.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of finalcials in body.
     */
    @GetMapping("/finalcials")
    public ResponseEntity<List<Finalcial>> getAllFinalcials(
        FinalcialCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Finalcials by criteria: {}", criteria);
        Page<Finalcial> page = finalcialQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /finalcials/count} : count all the finalcials.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/finalcials/count")
    public ResponseEntity<Long> countFinalcials(FinalcialCriteria criteria) {
        log.debug("REST request to count Finalcials by criteria: {}", criteria);
        return ResponseEntity.ok().body(finalcialQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /finalcials/:id} : get the "id" finalcial.
     *
     * @param id the id of the finalcial to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the finalcial, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/finalcials/{id}")
    public ResponseEntity<Finalcial> getFinalcial(@PathVariable Long id) {
        log.debug("REST request to get Finalcial : {}", id);
        Optional<Finalcial> finalcial = finalcialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(finalcial);
    }

    /**
     * {@code DELETE  /finalcials/:id} : delete the "id" finalcial.
     *
     * @param id the id of the finalcial to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/finalcials/{id}")
    public ResponseEntity<Void> deleteFinalcial(@PathVariable Long id) {
        log.debug("REST request to delete Finalcial : {}", id);
        finalcialService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/finalcials/batch")
    public ResponseEntity<Integer> saveBatchfinalcials(@RequestBody List<Finalcial> products) {
        log.debug("REST request to save a list of products : {}", products);
        for (Finalcial product : products) {
            if (product.getId() != null) {
                throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
            }
        }
        Integer savedCount = finalcialRepository.saveAll(products).size();
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, savedCount.toString()))
            .body(savedCount);
    }
}
