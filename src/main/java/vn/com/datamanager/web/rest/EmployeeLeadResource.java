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
import vn.com.datamanager.domain.EmployeeLead;
import vn.com.datamanager.domain.Product;
import vn.com.datamanager.repository.EmployeeLeadRepository;
import vn.com.datamanager.service.EmployeeLeadQueryService;
import vn.com.datamanager.service.EmployeeLeadService;
import vn.com.datamanager.service.criteria.EmployeeLeadCriteria;
import vn.com.datamanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.com.datamanager.domain.EmployeeLead}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeLeadResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeLeadResource.class);

    private static final String ENTITY_NAME = "employeeLead";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeLeadService employeeLeadService;

    private final EmployeeLeadRepository employeeLeadRepository;

    private final EmployeeLeadQueryService employeeLeadQueryService;

    public EmployeeLeadResource(
        EmployeeLeadService employeeLeadService,
        EmployeeLeadRepository employeeLeadRepository,
        EmployeeLeadQueryService employeeLeadQueryService
    ) {
        this.employeeLeadService = employeeLeadService;
        this.employeeLeadRepository = employeeLeadRepository;
        this.employeeLeadQueryService = employeeLeadQueryService;
    }

    /**
     * {@code POST  /employee-leads} : Create a new employeeLead.
     *
     * @param employeeLead the employeeLead to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeLead, or with status {@code 400 (Bad Request)} if the employeeLead has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-leads")
    public ResponseEntity<EmployeeLead> createEmployeeLead(@Valid @RequestBody EmployeeLead employeeLead) throws URISyntaxException {
        log.debug("REST request to save EmployeeLead : {}", employeeLead);
        if (employeeLead.getId() != null) {
            throw new BadRequestAlertException("A new employeeLead cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeLead result = employeeLeadService.save(employeeLead);
        return ResponseEntity
            .created(new URI("/api/employee-leads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-leads/:id} : Updates an existing employeeLead.
     *
     * @param id the id of the employeeLead to save.
     * @param employeeLead the employeeLead to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeLead,
     * or with status {@code 400 (Bad Request)} if the employeeLead is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeLead couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-leads/{id}")
    public ResponseEntity<EmployeeLead> updateEmployeeLead(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeLead employeeLead
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeLead : {}, {}", id, employeeLead);
        if (employeeLead.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeLead.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeLeadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeLead result = employeeLeadService.update(employeeLead);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeLead.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-leads/:id} : Partial updates given fields of an existing employeeLead, field will ignore if it is null
     *
     * @param id the id of the employeeLead to save.
     * @param employeeLead the employeeLead to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeLead,
     * or with status {@code 400 (Bad Request)} if the employeeLead is not valid,
     * or with status {@code 404 (Not Found)} if the employeeLead is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeLead couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-leads/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeLead> partialUpdateEmployeeLead(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeLead employeeLead
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeLead partially : {}, {}", id, employeeLead);
        if (employeeLead.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeLead.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeLeadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeLead> result = employeeLeadService.partialUpdate(employeeLead);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeLead.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-leads} : get all the employeeLeads.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeLeads in body.
     */
    @GetMapping("/employee-leads")
    public ResponseEntity<List<EmployeeLead>> getAllEmployeeLeads(
        EmployeeLeadCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeLeads by criteria: {}", criteria);
        Page<EmployeeLead> page = employeeLeadQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-leads/count} : count all the employeeLeads.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-leads/count")
    public ResponseEntity<Long> countEmployeeLeads(EmployeeLeadCriteria criteria) {
        log.debug("REST request to count EmployeeLeads by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeLeadQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-leads/:id} : get the "id" employeeLead.
     *
     * @param id the id of the employeeLead to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeLead, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-leads/{id}")
    public ResponseEntity<EmployeeLead> getEmployeeLead(@PathVariable Long id) {
        log.debug("REST request to get EmployeeLead : {}", id);
        Optional<EmployeeLead> employeeLead = employeeLeadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeLead);
    }

    /**
     * {@code DELETE  /employee-leads/:id} : delete the "id" employeeLead.
     *
     * @param id the id of the employeeLead to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-leads/{id}")
    public ResponseEntity<Void> deleteEmployeeLead(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeLead : {}", id);
        employeeLeadService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/employee-leads/batch")
    public ResponseEntity<Integer> saveBatchEmployeeLead(@RequestBody List<EmployeeLead> employeeLeads) {
        log.debug("REST request to save a list of employee-leads : {}", employeeLeads);
        for (EmployeeLead employeeLead : employeeLeads) {
            if (employeeLead.getId() != null) {
                throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
            }
        }
        Integer savedCount = employeeLeadRepository.saveAll(employeeLeads).size();
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, savedCount.toString()))
            .body(savedCount);
    }
}
