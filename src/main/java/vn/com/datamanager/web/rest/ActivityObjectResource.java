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
import vn.com.datamanager.domain.ActivityObject;
import vn.com.datamanager.repository.ActivityObjectRepository;
import vn.com.datamanager.service.ActivityObjectQueryService;
import vn.com.datamanager.service.ActivityObjectService;
import vn.com.datamanager.service.criteria.ActivityObjectCriteria;
import vn.com.datamanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.com.datamanager.domain.ActivityObject}.
 */
@RestController
@RequestMapping("/api")
public class ActivityObjectResource {

    private final Logger log = LoggerFactory.getLogger(ActivityObjectResource.class);

    private static final String ENTITY_NAME = "activityObject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivityObjectService activityObjectService;

    private final ActivityObjectRepository activityObjectRepository;

    private final ActivityObjectQueryService activityObjectQueryService;

    public ActivityObjectResource(
        ActivityObjectService activityObjectService,
        ActivityObjectRepository activityObjectRepository,
        ActivityObjectQueryService activityObjectQueryService
    ) {
        this.activityObjectService = activityObjectService;
        this.activityObjectRepository = activityObjectRepository;
        this.activityObjectQueryService = activityObjectQueryService;
    }

    /**
     * {@code POST  /activity-objects} : Create a new activityObject.
     *
     * @param activityObject the activityObject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activityObject, or with status {@code 400 (Bad Request)} if the activityObject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/activity-objects")
    public ResponseEntity<ActivityObject> createActivityObject(@RequestBody ActivityObject activityObject) throws URISyntaxException {
        log.debug("REST request to save ActivityObject : {}", activityObject);
        if (activityObject.getId() != null) {
            throw new BadRequestAlertException("A new activityObject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivityObject result = activityObjectService.save(activityObject);
        return ResponseEntity
            .created(new URI("/api/activity-objects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /activity-objects/:id} : Updates an existing activityObject.
     *
     * @param id the id of the activityObject to save.
     * @param activityObject the activityObject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityObject,
     * or with status {@code 400 (Bad Request)} if the activityObject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activityObject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/activity-objects/{id}")
    public ResponseEntity<ActivityObject> updateActivityObject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ActivityObject activityObject
    ) throws URISyntaxException {
        log.debug("REST request to update ActivityObject : {}, {}", id, activityObject);
        if (activityObject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activityObject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activityObjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ActivityObject result = activityObjectService.update(activityObject);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, activityObject.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /activity-objects/:id} : Partial updates given fields of an existing activityObject, field will ignore if it is null
     *
     * @param id the id of the activityObject to save.
     * @param activityObject the activityObject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityObject,
     * or with status {@code 400 (Bad Request)} if the activityObject is not valid,
     * or with status {@code 404 (Not Found)} if the activityObject is not found,
     * or with status {@code 500 (Internal Server Error)} if the activityObject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/activity-objects/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ActivityObject> partialUpdateActivityObject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ActivityObject activityObject
    ) throws URISyntaxException {
        log.debug("REST request to partial update ActivityObject partially : {}, {}", id, activityObject);
        if (activityObject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activityObject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activityObjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ActivityObject> result = activityObjectService.partialUpdate(activityObject);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, activityObject.getId().toString())
        );
    }

    /**
     * {@code GET  /activity-objects} : get all the activityObjects.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activityObjects in body.
     */
    @GetMapping("/activity-objects")
    public ResponseEntity<List<ActivityObject>> getAllActivityObjects(
        ActivityObjectCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ActivityObjects by criteria: {}", criteria);
        Page<ActivityObject> page = activityObjectQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /activity-objects/count} : count all the activityObjects.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/activity-objects/count")
    public ResponseEntity<Long> countActivityObjects(ActivityObjectCriteria criteria) {
        log.debug("REST request to count ActivityObjects by criteria: {}", criteria);
        return ResponseEntity.ok().body(activityObjectQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /activity-objects/:id} : get the "id" activityObject.
     *
     * @param id the id of the activityObject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activityObject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/activity-objects/{id}")
    public ResponseEntity<ActivityObject> getActivityObject(@PathVariable Long id) {
        log.debug("REST request to get ActivityObject : {}", id);
        Optional<ActivityObject> activityObject = activityObjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activityObject);
    }

    /**
     * {@code DELETE  /activity-objects/:id} : delete the "id" activityObject.
     *
     * @param id the id of the activityObject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/activity-objects/{id}")
    public ResponseEntity<Void> deleteActivityObject(@PathVariable Long id) {
        log.debug("REST request to delete ActivityObject : {}", id);
        activityObjectService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/activity_objects/batch")
    public ResponseEntity<Integer> saveBatchSaleContract(@RequestBody List<ActivityObject> activityObjects) {
        log.debug("REST request to save a list of activity object : {}", activityObjects);
        for (ActivityObject activityObject : activityObjects) {
            if (activityObject.getId() != null) {
                throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
            }
        }
        Integer savedCount = activityObjectRepository.saveAll(activityObjects).size();
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, savedCount.toString()))
            .body(savedCount);
    }
}
