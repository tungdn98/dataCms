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
import vn.com.datamanager.domain.PaymentStatus;
import vn.com.datamanager.repository.PaymentStatusRepository;
import vn.com.datamanager.service.PaymentStatusQueryService;
import vn.com.datamanager.service.PaymentStatusService;
import vn.com.datamanager.service.criteria.PaymentStatusCriteria;
import vn.com.datamanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.com.datamanager.domain.PaymentStatus}.
 */
@RestController
@RequestMapping("/api")
public class PaymentStatusResource {

    private final Logger log = LoggerFactory.getLogger(PaymentStatusResource.class);

    private static final String ENTITY_NAME = "paymentStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentStatusService paymentStatusService;

    private final PaymentStatusRepository paymentStatusRepository;

    private final PaymentStatusQueryService paymentStatusQueryService;

    public PaymentStatusResource(
        PaymentStatusService paymentStatusService,
        PaymentStatusRepository paymentStatusRepository,
        PaymentStatusQueryService paymentStatusQueryService
    ) {
        this.paymentStatusService = paymentStatusService;
        this.paymentStatusRepository = paymentStatusRepository;
        this.paymentStatusQueryService = paymentStatusQueryService;
    }

    /**
     * {@code POST  /payment-statuses} : Create a new paymentStatus.
     *
     * @param paymentStatus the paymentStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentStatus, or with status {@code 400 (Bad Request)} if the paymentStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-statuses")
    public ResponseEntity<PaymentStatus> createPaymentStatus(@RequestBody PaymentStatus paymentStatus) throws URISyntaxException {
        log.debug("REST request to save PaymentStatus : {}", paymentStatus);
        if (paymentStatus.getId() != null) {
            throw new BadRequestAlertException("A new paymentStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentStatus result = paymentStatusService.save(paymentStatus);
        return ResponseEntity
            .created(new URI("/api/payment-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-statuses/:id} : Updates an existing paymentStatus.
     *
     * @param id the id of the paymentStatus to save.
     * @param paymentStatus the paymentStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentStatus,
     * or with status {@code 400 (Bad Request)} if the paymentStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-statuses/{id}")
    public ResponseEntity<PaymentStatus> updatePaymentStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentStatus paymentStatus
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentStatus : {}, {}", id, paymentStatus);
        if (paymentStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentStatus result = paymentStatusService.update(paymentStatus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-statuses/:id} : Partial updates given fields of an existing paymentStatus, field will ignore if it is null
     *
     * @param id the id of the paymentStatus to save.
     * @param paymentStatus the paymentStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentStatus,
     * or with status {@code 400 (Bad Request)} if the paymentStatus is not valid,
     * or with status {@code 404 (Not Found)} if the paymentStatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentStatus> partialUpdatePaymentStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentStatus paymentStatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentStatus partially : {}, {}", id, paymentStatus);
        if (paymentStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentStatus> result = paymentStatusService.partialUpdate(paymentStatus);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentStatus.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-statuses} : get all the paymentStatuses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentStatuses in body.
     */
    @GetMapping("/payment-statuses")
    public ResponseEntity<List<PaymentStatus>> getAllPaymentStatuses(
        PaymentStatusCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PaymentStatuses by criteria: {}", criteria);
        Page<PaymentStatus> page = paymentStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payment-statuses/count} : count all the paymentStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/payment-statuses/count")
    public ResponseEntity<Long> countPaymentStatuses(PaymentStatusCriteria criteria) {
        log.debug("REST request to count PaymentStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(paymentStatusQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payment-statuses/:id} : get the "id" paymentStatus.
     *
     * @param id the id of the paymentStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-statuses/{id}")
    public ResponseEntity<PaymentStatus> getPaymentStatus(@PathVariable Long id) {
        log.debug("REST request to get PaymentStatus : {}", id);
        Optional<PaymentStatus> paymentStatus = paymentStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentStatus);
    }

    /**
     * {@code DELETE  /payment-statuses/:id} : delete the "id" paymentStatus.
     *
     * @param id the id of the paymentStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-statuses/{id}")
    public ResponseEntity<Void> deletePaymentStatus(@PathVariable Long id) {
        log.debug("REST request to delete PaymentStatus : {}", id);
        paymentStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
    @PostMapping("/payment_status/batch")
    public ResponseEntity<Integer> saveBatchSaleContract(@RequestBody List<PaymentStatus> paymentStatuses) {
        log.debug("REST request to save a list of payment status : {}", paymentStatuses);
        for (PaymentStatus paymentStatus : paymentStatuses) {
            if (paymentStatus.getId() != null) {
                throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
            }
        }
        Integer savedCount = paymentStatusRepository.saveAll(paymentStatuses).size();
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, savedCount.toString()))
            .body(savedCount);
    }

}
