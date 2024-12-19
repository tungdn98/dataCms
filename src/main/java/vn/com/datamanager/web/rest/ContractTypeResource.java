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
import vn.com.datamanager.domain.ContractType;
import vn.com.datamanager.repository.ContractTypeRepository;
import vn.com.datamanager.service.ContractTypeQueryService;
import vn.com.datamanager.service.ContractTypeService;
import vn.com.datamanager.service.criteria.ContractTypeCriteria;
import vn.com.datamanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.com.datamanager.domain.ContractType}.
 */
@RestController
@RequestMapping("/api")
public class ContractTypeResource {

    private final Logger log = LoggerFactory.getLogger(ContractTypeResource.class);

    private static final String ENTITY_NAME = "contractType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContractTypeService contractTypeService;

    private final ContractTypeRepository contractTypeRepository;

    private final ContractTypeQueryService contractTypeQueryService;

    public ContractTypeResource(
        ContractTypeService contractTypeService,
        ContractTypeRepository contractTypeRepository,
        ContractTypeQueryService contractTypeQueryService
    ) {
        this.contractTypeService = contractTypeService;
        this.contractTypeRepository = contractTypeRepository;
        this.contractTypeQueryService = contractTypeQueryService;
    }

    /**
     * {@code POST  /contract-types} : Create a new contractType.
     *
     * @param contractType the contractType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contractType, or with status {@code 400 (Bad Request)} if the contractType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contract-types")
    public ResponseEntity<ContractType> createContractType(@RequestBody ContractType contractType) throws URISyntaxException {
        log.debug("REST request to save ContractType : {}", contractType);
        if (contractType.getId() != null) {
            throw new BadRequestAlertException("A new contractType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContractType result = contractTypeService.save(contractType);
        return ResponseEntity
            .created(new URI("/api/contract-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contract-types/:id} : Updates an existing contractType.
     *
     * @param id the id of the contractType to save.
     * @param contractType the contractType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contractType,
     * or with status {@code 400 (Bad Request)} if the contractType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contractType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contract-types/{id}")
    public ResponseEntity<ContractType> updateContractType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContractType contractType
    ) throws URISyntaxException {
        log.debug("REST request to update ContractType : {}, {}", id, contractType);
        if (contractType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contractType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contractTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContractType result = contractTypeService.update(contractType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contractType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contract-types/:id} : Partial updates given fields of an existing contractType, field will ignore if it is null
     *
     * @param id the id of the contractType to save.
     * @param contractType the contractType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contractType,
     * or with status {@code 400 (Bad Request)} if the contractType is not valid,
     * or with status {@code 404 (Not Found)} if the contractType is not found,
     * or with status {@code 500 (Internal Server Error)} if the contractType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contract-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContractType> partialUpdateContractType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContractType contractType
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContractType partially : {}, {}", id, contractType);
        if (contractType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contractType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contractTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContractType> result = contractTypeService.partialUpdate(contractType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contractType.getId().toString())
        );
    }

    /**
     * {@code GET  /contract-types} : get all the contractTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contractTypes in body.
     */
    @GetMapping("/contract-types")
    public ResponseEntity<List<ContractType>> getAllContractTypes(
        ContractTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ContractTypes by criteria: {}", criteria);
        Page<ContractType> page = contractTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contract-types/count} : count all the contractTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/contract-types/count")
    public ResponseEntity<Long> countContractTypes(ContractTypeCriteria criteria) {
        log.debug("REST request to count ContractTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(contractTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /contract-types/:id} : get the "id" contractType.
     *
     * @param id the id of the contractType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contractType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contract-types/{id}")
    public ResponseEntity<ContractType> getContractType(@PathVariable Long id) {
        log.debug("REST request to get ContractType : {}", id);
        Optional<ContractType> contractType = contractTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contractType);
    }

    /**
     * {@code DELETE  /contract-types/:id} : delete the "id" contractType.
     *
     * @param id the id of the contractType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contract-types/{id}")
    public ResponseEntity<Void> deleteContractType(@PathVariable Long id) {
        log.debug("REST request to delete ContractType : {}", id);
        contractTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
