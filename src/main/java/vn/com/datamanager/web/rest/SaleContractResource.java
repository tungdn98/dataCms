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
import vn.com.datamanager.domain.SaleContract;
import vn.com.datamanager.repository.SaleContractRepository;
import vn.com.datamanager.service.SaleContractQueryService;
import vn.com.datamanager.service.SaleContractService;
import vn.com.datamanager.service.criteria.SaleContractCriteria;
import vn.com.datamanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.com.datamanager.domain.SaleContract}.
 */
@RestController
@RequestMapping("/api")
public class SaleContractResource {

    private final Logger log = LoggerFactory.getLogger(SaleContractResource.class);

    private static final String ENTITY_NAME = "saleContract";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaleContractService saleContractService;

    private final SaleContractRepository saleContractRepository;

    private final SaleContractQueryService saleContractQueryService;

    public SaleContractResource(
        SaleContractService saleContractService,
        SaleContractRepository saleContractRepository,
        SaleContractQueryService saleContractQueryService
    ) {
        this.saleContractService = saleContractService;
        this.saleContractRepository = saleContractRepository;
        this.saleContractQueryService = saleContractQueryService;
    }

    /**
     * {@code POST  /sale-contracts} : Create a new saleContract.
     *
     * @param saleContract the saleContract to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new saleContract, or with status {@code 400 (Bad Request)} if the saleContract has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sale-contracts")
    public ResponseEntity<SaleContract> createSaleContract(@RequestBody SaleContract saleContract) throws URISyntaxException {
        log.debug("REST request to save SaleContract : {}", saleContract);
        if (saleContract.getId() != null) {
            throw new BadRequestAlertException("A new saleContract cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SaleContract result = saleContractService.save(saleContract);
        return ResponseEntity
            .created(new URI("/api/sale-contracts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sale-contracts/:id} : Updates an existing saleContract.
     *
     * @param id the id of the saleContract to save.
     * @param saleContract the saleContract to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saleContract,
     * or with status {@code 400 (Bad Request)} if the saleContract is not valid,
     * or with status {@code 500 (Internal Server Error)} if the saleContract couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sale-contracts/{id}")
    public ResponseEntity<SaleContract> updateSaleContract(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SaleContract saleContract
    ) throws URISyntaxException {
        log.debug("REST request to update SaleContract : {}, {}", id, saleContract);
        if (saleContract.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saleContract.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleContractRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SaleContract result = saleContractService.update(saleContract);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, saleContract.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sale-contracts/:id} : Partial updates given fields of an existing saleContract, field will ignore if it is null
     *
     * @param id the id of the saleContract to save.
     * @param saleContract the saleContract to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saleContract,
     * or with status {@code 400 (Bad Request)} if the saleContract is not valid,
     * or with status {@code 404 (Not Found)} if the saleContract is not found,
     * or with status {@code 500 (Internal Server Error)} if the saleContract couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sale-contracts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SaleContract> partialUpdateSaleContract(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SaleContract saleContract
    ) throws URISyntaxException {
        log.debug("REST request to partial update SaleContract partially : {}, {}", id, saleContract);
        if (saleContract.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saleContract.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleContractRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SaleContract> result = saleContractService.partialUpdate(saleContract);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, saleContract.getId().toString())
        );
    }

    /**
     * {@code GET  /sale-contracts} : get all the saleContracts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saleContracts in body.
     */
    @GetMapping("/sale-contracts")
    public ResponseEntity<List<SaleContract>> getAllSaleContracts(
        SaleContractCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SaleContracts by criteria: {}", criteria);
        Page<SaleContract> page = saleContractQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sale-contracts/count} : count all the saleContracts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sale-contracts/count")
    public ResponseEntity<Long> countSaleContracts(SaleContractCriteria criteria) {
        log.debug("REST request to count SaleContracts by criteria: {}", criteria);
        return ResponseEntity.ok().body(saleContractQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sale-contracts/:id} : get the "id" saleContract.
     *
     * @param id the id of the saleContract to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the saleContract, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sale-contracts/{id}")
    public ResponseEntity<SaleContract> getSaleContract(@PathVariable Long id) {
        log.debug("REST request to get SaleContract : {}", id);
        Optional<SaleContract> saleContract = saleContractService.findOne(id);
        return ResponseUtil.wrapOrNotFound(saleContract);
    }

    /**
     * {@code DELETE  /sale-contracts/:id} : delete the "id" saleContract.
     *
     * @param id the id of the saleContract to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sale-contracts/{id}")
    public ResponseEntity<Void> deleteSaleContract(@PathVariable Long id) {
        log.debug("REST request to delete SaleContract : {}", id);
        saleContractService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
