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
import vn.com.datamanager.domain.RoleGroup;
import vn.com.datamanager.repository.RoleGroupRepository;
import vn.com.datamanager.service.RoleGroupQueryService;
import vn.com.datamanager.service.RoleGroupService;
import vn.com.datamanager.service.criteria.RoleGroupCriteria;
import vn.com.datamanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.com.datamanager.domain.RoleGroup}.
 */
@RestController
@RequestMapping("/api")
public class RoleGroupResource {

    private final Logger log = LoggerFactory.getLogger(RoleGroupResource.class);

    private static final String ENTITY_NAME = "roleGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleGroupService roleGroupService;

    private final RoleGroupRepository roleGroupRepository;

    private final RoleGroupQueryService roleGroupQueryService;

    public RoleGroupResource(
        RoleGroupService roleGroupService,
        RoleGroupRepository roleGroupRepository,
        RoleGroupQueryService roleGroupQueryService
    ) {
        this.roleGroupService = roleGroupService;
        this.roleGroupRepository = roleGroupRepository;
        this.roleGroupQueryService = roleGroupQueryService;
    }

    /**
     * {@code POST  /role-groups} : Create a new roleGroup.
     *
     * @param roleGroup the roleGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleGroup, or with status {@code 400 (Bad Request)} if the roleGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/role-groups")
    public ResponseEntity<RoleGroup> createRoleGroup(@Valid @RequestBody RoleGroup roleGroup) throws URISyntaxException {
        log.debug("REST request to save RoleGroup : {}", roleGroup);
        if (roleGroup.getId() != null) {
            throw new BadRequestAlertException("A new roleGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleGroup result = roleGroupService.save(roleGroup);
        return ResponseEntity
            .created(new URI("/api/role-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /role-groups/:id} : Updates an existing roleGroup.
     *
     * @param id the id of the roleGroup to save.
     * @param roleGroup the roleGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleGroup,
     * or with status {@code 400 (Bad Request)} if the roleGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/role-groups/{id}")
    public ResponseEntity<RoleGroup> updateRoleGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RoleGroup roleGroup
    ) throws URISyntaxException {
        log.debug("REST request to update RoleGroup : {}, {}", id, roleGroup);
        if (roleGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoleGroup result = roleGroupService.update(roleGroup);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, roleGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /role-groups/:id} : Partial updates given fields of an existing roleGroup, field will ignore if it is null
     *
     * @param id the id of the roleGroup to save.
     * @param roleGroup the roleGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleGroup,
     * or with status {@code 400 (Bad Request)} if the roleGroup is not valid,
     * or with status {@code 404 (Not Found)} if the roleGroup is not found,
     * or with status {@code 500 (Internal Server Error)} if the roleGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/role-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RoleGroup> partialUpdateRoleGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RoleGroup roleGroup
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoleGroup partially : {}, {}", id, roleGroup);
        if (roleGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoleGroup> result = roleGroupService.partialUpdate(roleGroup);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, roleGroup.getId().toString())
        );
    }

    /**
     * {@code GET  /role-groups} : get all the roleGroups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roleGroups in body.
     */
    @GetMapping("/role-groups")
    public ResponseEntity<List<RoleGroup>> getAllRoleGroups(
        RoleGroupCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get RoleGroups by criteria: {}", criteria);
        Page<RoleGroup> page = roleGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /role-groups/count} : count all the roleGroups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/role-groups/count")
    public ResponseEntity<Long> countRoleGroups(RoleGroupCriteria criteria) {
        log.debug("REST request to count RoleGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(roleGroupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /role-groups/:id} : get the "id" roleGroup.
     *
     * @param id the id of the roleGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/role-groups/{id}")
    public ResponseEntity<RoleGroup> getRoleGroup(@PathVariable Long id) {
        log.debug("REST request to get RoleGroup : {}", id);
        Optional<RoleGroup> roleGroup = roleGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roleGroup);
    }

    /**
     * {@code DELETE  /role-groups/:id} : delete the "id" roleGroup.
     *
     * @param id the id of the roleGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/role-groups/{id}")
    public ResponseEntity<Void> deleteRoleGroup(@PathVariable Long id) {
        log.debug("REST request to delete RoleGroup : {}", id);
        roleGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
