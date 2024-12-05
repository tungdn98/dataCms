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
import vn.com.datamanager.domain.EmpGroup;
import vn.com.datamanager.repository.EmpGroupRepository;
import vn.com.datamanager.service.EmpGroupQueryService;
import vn.com.datamanager.service.EmpGroupService;
import vn.com.datamanager.service.criteria.EmpGroupCriteria;
import vn.com.datamanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.com.datamanager.domain.EmpGroup}.
 */
@RestController
@RequestMapping("/api")
public class EmpGroupResource {

    private final Logger log = LoggerFactory.getLogger(EmpGroupResource.class);

    private static final String ENTITY_NAME = "empGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpGroupService empGroupService;

    private final EmpGroupRepository empGroupRepository;

    private final EmpGroupQueryService empGroupQueryService;

    public EmpGroupResource(
        EmpGroupService empGroupService,
        EmpGroupRepository empGroupRepository,
        EmpGroupQueryService empGroupQueryService
    ) {
        this.empGroupService = empGroupService;
        this.empGroupRepository = empGroupRepository;
        this.empGroupQueryService = empGroupQueryService;
    }

    /**
     * {@code POST  /emp-groups} : Create a new empGroup.
     *
     * @param empGroup the empGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empGroup, or with status {@code 400 (Bad Request)} if the empGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emp-groups")
    public ResponseEntity<EmpGroup> createEmpGroup(@Valid @RequestBody EmpGroup empGroup) throws URISyntaxException {
        log.debug("REST request to save EmpGroup : {}", empGroup);
        if (empGroup.getId() != null) {
            throw new BadRequestAlertException("A new empGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmpGroup result = empGroupService.save(empGroup);
        return ResponseEntity
            .created(new URI("/api/emp-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emp-groups/:id} : Updates an existing empGroup.
     *
     * @param id the id of the empGroup to save.
     * @param empGroup the empGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empGroup,
     * or with status {@code 400 (Bad Request)} if the empGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emp-groups/{id}")
    public ResponseEntity<EmpGroup> updateEmpGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmpGroup empGroup
    ) throws URISyntaxException {
        log.debug("REST request to update EmpGroup : {}, {}", id, empGroup);
        if (empGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmpGroup result = empGroupService.update(empGroup);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, empGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /emp-groups/:id} : Partial updates given fields of an existing empGroup, field will ignore if it is null
     *
     * @param id the id of the empGroup to save.
     * @param empGroup the empGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empGroup,
     * or with status {@code 400 (Bad Request)} if the empGroup is not valid,
     * or with status {@code 404 (Not Found)} if the empGroup is not found,
     * or with status {@code 500 (Internal Server Error)} if the empGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/emp-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmpGroup> partialUpdateEmpGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmpGroup empGroup
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmpGroup partially : {}, {}", id, empGroup);
        if (empGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmpGroup> result = empGroupService.partialUpdate(empGroup);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, empGroup.getId().toString())
        );
    }

    /**
     * {@code GET  /emp-groups} : get all the empGroups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empGroups in body.
     */
    @GetMapping("/emp-groups")
    public ResponseEntity<List<EmpGroup>> getAllEmpGroups(
        EmpGroupCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmpGroups by criteria: {}", criteria);
        Page<EmpGroup> page = empGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emp-groups/count} : count all the empGroups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/emp-groups/count")
    public ResponseEntity<Long> countEmpGroups(EmpGroupCriteria criteria) {
        log.debug("REST request to count EmpGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(empGroupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /emp-groups/:id} : get the "id" empGroup.
     *
     * @param id the id of the empGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emp-groups/{id}")
    public ResponseEntity<EmpGroup> getEmpGroup(@PathVariable Long id) {
        log.debug("REST request to get EmpGroup : {}", id);
        Optional<EmpGroup> empGroup = empGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empGroup);
    }

    /**
     * {@code DELETE  /emp-groups/:id} : delete the "id" empGroup.
     *
     * @param id the id of the empGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emp-groups/{id}")
    public ResponseEntity<Void> deleteEmpGroup(@PathVariable Long id) {
        log.debug("REST request to delete EmpGroup : {}", id);
        empGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
