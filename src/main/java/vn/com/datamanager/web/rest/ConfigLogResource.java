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
import vn.com.datamanager.domain.ConfigLog;
import vn.com.datamanager.repository.ConfigLogRepository;
import vn.com.datamanager.service.ConfigLogQueryService;
import vn.com.datamanager.service.ConfigLogService;
import vn.com.datamanager.service.criteria.ConfigLogCriteria;
import vn.com.datamanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.com.datamanager.domain.ConfigLog}.
 */
@RestController
@RequestMapping("/api")
public class ConfigLogResource {

    private final Logger log = LoggerFactory.getLogger(ConfigLogResource.class);

    private static final String ENTITY_NAME = "configLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfigLogService configLogService;

    private final ConfigLogRepository configLogRepository;

    private final ConfigLogQueryService configLogQueryService;

    public ConfigLogResource(
        ConfigLogService configLogService,
        ConfigLogRepository configLogRepository,
        ConfigLogQueryService configLogQueryService
    ) {
        this.configLogService = configLogService;
        this.configLogRepository = configLogRepository;
        this.configLogQueryService = configLogQueryService;
    }

    /**
     * {@code POST  /config-logs} : Create a new configLog.
     *
     * @param configLog the configLog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new configLog, or with status {@code 400 (Bad Request)} if the configLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/config-logs")
    public ResponseEntity<ConfigLog> createConfigLog(@Valid @RequestBody ConfigLog configLog) throws URISyntaxException {
        log.debug("REST request to save ConfigLog : {}", configLog);
        if (configLog.getId() != null) {
            throw new BadRequestAlertException("A new configLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfigLog result = configLogService.save(configLog);
        return ResponseEntity
            .created(new URI("/api/config-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /config-logs/:id} : Updates an existing configLog.
     *
     * @param id the id of the configLog to save.
     * @param configLog the configLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configLog,
     * or with status {@code 400 (Bad Request)} if the configLog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the configLog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/config-logs/{id}")
    public ResponseEntity<ConfigLog> updateConfigLog(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ConfigLog configLog
    ) throws URISyntaxException {
        log.debug("REST request to update ConfigLog : {}, {}", id, configLog);
        if (configLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, configLog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!configLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConfigLog result = configLogService.update(configLog);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, configLog.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /config-logs/:id} : Partial updates given fields of an existing configLog, field will ignore if it is null
     *
     * @param id the id of the configLog to save.
     * @param configLog the configLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configLog,
     * or with status {@code 400 (Bad Request)} if the configLog is not valid,
     * or with status {@code 404 (Not Found)} if the configLog is not found,
     * or with status {@code 500 (Internal Server Error)} if the configLog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/config-logs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConfigLog> partialUpdateConfigLog(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ConfigLog configLog
    ) throws URISyntaxException {
        log.debug("REST request to partial update ConfigLog partially : {}, {}", id, configLog);
        if (configLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, configLog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!configLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConfigLog> result = configLogService.partialUpdate(configLog);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, configLog.getId().toString())
        );
    }

    /**
     * {@code GET  /config-logs} : get all the configLogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of configLogs in body.
     */
    @GetMapping("/config-logs")
    public ResponseEntity<List<ConfigLog>> getAllConfigLogs(
        ConfigLogCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ConfigLogs by criteria: {}", criteria);
        Page<ConfigLog> page = configLogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /config-logs/count} : count all the configLogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/config-logs/count")
    public ResponseEntity<Long> countConfigLogs(ConfigLogCriteria criteria) {
        log.debug("REST request to count ConfigLogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(configLogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /config-logs/:id} : get the "id" configLog.
     *
     * @param id the id of the configLog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the configLog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/config-logs/{id}")
    public ResponseEntity<ConfigLog> getConfigLog(@PathVariable Long id) {
        log.debug("REST request to get ConfigLog : {}", id);
        Optional<ConfigLog> configLog = configLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(configLog);
    }

    /**
     * {@code DELETE  /config-logs/:id} : delete the "id" configLog.
     *
     * @param id the id of the configLog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/config-logs/{id}")
    public ResponseEntity<Void> deleteConfigLog(@PathVariable Long id) {
        log.debug("REST request to delete ConfigLog : {}", id);
        configLogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
