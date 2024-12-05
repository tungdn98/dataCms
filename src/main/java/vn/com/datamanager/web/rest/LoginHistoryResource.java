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
import vn.com.datamanager.domain.LoginHistory;
import vn.com.datamanager.repository.LoginHistoryRepository;
import vn.com.datamanager.service.LoginHistoryQueryService;
import vn.com.datamanager.service.LoginHistoryService;
import vn.com.datamanager.service.criteria.LoginHistoryCriteria;
import vn.com.datamanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.com.datamanager.domain.LoginHistory}.
 */
@RestController
@RequestMapping("/api")
public class LoginHistoryResource {

    private final Logger log = LoggerFactory.getLogger(LoginHistoryResource.class);

    private static final String ENTITY_NAME = "loginHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoginHistoryService loginHistoryService;

    private final LoginHistoryRepository loginHistoryRepository;

    private final LoginHistoryQueryService loginHistoryQueryService;

    public LoginHistoryResource(
        LoginHistoryService loginHistoryService,
        LoginHistoryRepository loginHistoryRepository,
        LoginHistoryQueryService loginHistoryQueryService
    ) {
        this.loginHistoryService = loginHistoryService;
        this.loginHistoryRepository = loginHistoryRepository;
        this.loginHistoryQueryService = loginHistoryQueryService;
    }

    /**
     * {@code POST  /login-histories} : Create a new loginHistory.
     *
     * @param loginHistory the loginHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loginHistory, or with status {@code 400 (Bad Request)} if the loginHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/login-histories")
    public ResponseEntity<LoginHistory> createLoginHistory(@RequestBody LoginHistory loginHistory) throws URISyntaxException {
        log.debug("REST request to save LoginHistory : {}", loginHistory);
        if (loginHistory.getId() != null) {
            throw new BadRequestAlertException("A new loginHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoginHistory result = loginHistoryService.save(loginHistory);
        return ResponseEntity
            .created(new URI("/api/login-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /login-histories/:id} : Updates an existing loginHistory.
     *
     * @param id the id of the loginHistory to save.
     * @param loginHistory the loginHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loginHistory,
     * or with status {@code 400 (Bad Request)} if the loginHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loginHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/login-histories/{id}")
    public ResponseEntity<LoginHistory> updateLoginHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoginHistory loginHistory
    ) throws URISyntaxException {
        log.debug("REST request to update LoginHistory : {}, {}", id, loginHistory);
        if (loginHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loginHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loginHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoginHistory result = loginHistoryService.update(loginHistory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, loginHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /login-histories/:id} : Partial updates given fields of an existing loginHistory, field will ignore if it is null
     *
     * @param id the id of the loginHistory to save.
     * @param loginHistory the loginHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loginHistory,
     * or with status {@code 400 (Bad Request)} if the loginHistory is not valid,
     * or with status {@code 404 (Not Found)} if the loginHistory is not found,
     * or with status {@code 500 (Internal Server Error)} if the loginHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/login-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoginHistory> partialUpdateLoginHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoginHistory loginHistory
    ) throws URISyntaxException {
        log.debug("REST request to partial update LoginHistory partially : {}, {}", id, loginHistory);
        if (loginHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loginHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loginHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoginHistory> result = loginHistoryService.partialUpdate(loginHistory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, loginHistory.getId().toString())
        );
    }

    /**
     * {@code GET  /login-histories} : get all the loginHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loginHistories in body.
     */
    @GetMapping("/login-histories")
    public ResponseEntity<List<LoginHistory>> getAllLoginHistories(
        LoginHistoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LoginHistories by criteria: {}", criteria);
        Page<LoginHistory> page = loginHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /login-histories/count} : count all the loginHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/login-histories/count")
    public ResponseEntity<Long> countLoginHistories(LoginHistoryCriteria criteria) {
        log.debug("REST request to count LoginHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(loginHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /login-histories/:id} : get the "id" loginHistory.
     *
     * @param id the id of the loginHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loginHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/login-histories/{id}")
    public ResponseEntity<LoginHistory> getLoginHistory(@PathVariable Long id) {
        log.debug("REST request to get LoginHistory : {}", id);
        Optional<LoginHistory> loginHistory = loginHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loginHistory);
    }

    /**
     * {@code DELETE  /login-histories/:id} : delete the "id" loginHistory.
     *
     * @param id the id of the loginHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/login-histories/{id}")
    public ResponseEntity<Void> deleteLoginHistory(@PathVariable Long id) {
        log.debug("REST request to delete LoginHistory : {}", id);
        loginHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
