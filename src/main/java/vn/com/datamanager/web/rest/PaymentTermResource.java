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
import vn.com.datamanager.domain.PaymentTerm;
import vn.com.datamanager.repository.PaymentTermRepository;
import vn.com.datamanager.service.PaymentTermQueryService;
import vn.com.datamanager.service.PaymentTermService;
import vn.com.datamanager.service.criteria.PaymentTermCriteria;
import vn.com.datamanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.com.datamanager.domain.PaymentTerm}.
 */
@RestController
@RequestMapping("/api")
public class PaymentTermResource {

    private final Logger log = LoggerFactory.getLogger(PaymentTermResource.class);

    private static final String ENTITY_NAME = "paymentTerm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentTermService paymentTermService;

    private final PaymentTermRepository paymentTermRepository;

    private final PaymentTermQueryService paymentTermQueryService;

    public PaymentTermResource(
        PaymentTermService paymentTermService,
        PaymentTermRepository paymentTermRepository,
        PaymentTermQueryService paymentTermQueryService
    ) {
        this.paymentTermService = paymentTermService;
        this.paymentTermRepository = paymentTermRepository;
        this.paymentTermQueryService = paymentTermQueryService;
    }

    /**
     * {@code POST  /payment-terms} : Create a new paymentTerm.
     *
     * @param paymentTerm the paymentTerm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentTerm, or with status {@code 400 (Bad Request)} if the paymentTerm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-terms")
    public ResponseEntity<PaymentTerm> createPaymentTerm(@RequestBody PaymentTerm paymentTerm) throws URISyntaxException {
        log.debug("REST request to save PaymentTerm : {}", paymentTerm);
        if (paymentTerm.getId() != null) {
            throw new BadRequestAlertException("A new paymentTerm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentTerm result = paymentTermService.save(paymentTerm);
        return ResponseEntity
            .created(new URI("/api/payment-terms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-terms/:id} : Updates an existing paymentTerm.
     *
     * @param id the id of the paymentTerm to save.
     * @param paymentTerm the paymentTerm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentTerm,
     * or with status {@code 400 (Bad Request)} if the paymentTerm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentTerm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-terms/{id}")
    public ResponseEntity<PaymentTerm> updatePaymentTerm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentTerm paymentTerm
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentTerm : {}, {}", id, paymentTerm);
        if (paymentTerm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentTerm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentTermRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentTerm result = paymentTermService.update(paymentTerm);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentTerm.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-terms/:id} : Partial updates given fields of an existing paymentTerm, field will ignore if it is null
     *
     * @param id the id of the paymentTerm to save.
     * @param paymentTerm the paymentTerm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentTerm,
     * or with status {@code 400 (Bad Request)} if the paymentTerm is not valid,
     * or with status {@code 404 (Not Found)} if the paymentTerm is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentTerm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-terms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentTerm> partialUpdatePaymentTerm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentTerm paymentTerm
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentTerm partially : {}, {}", id, paymentTerm);
        if (paymentTerm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentTerm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentTermRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentTerm> result = paymentTermService.partialUpdate(paymentTerm);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentTerm.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-terms} : get all the paymentTerms.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentTerms in body.
     */
    @GetMapping("/payment-terms")
    public ResponseEntity<List<PaymentTerm>> getAllPaymentTerms(
        PaymentTermCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PaymentTerms by criteria: {}", criteria);
        Page<PaymentTerm> page = paymentTermQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payment-terms/count} : count all the paymentTerms.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/payment-terms/count")
    public ResponseEntity<Long> countPaymentTerms(PaymentTermCriteria criteria) {
        log.debug("REST request to count PaymentTerms by criteria: {}", criteria);
        return ResponseEntity.ok().body(paymentTermQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payment-terms/:id} : get the "id" paymentTerm.
     *
     * @param id the id of the paymentTerm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentTerm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-terms/{id}")
    public ResponseEntity<PaymentTerm> getPaymentTerm(@PathVariable Long id) {
        log.debug("REST request to get PaymentTerm : {}", id);
        Optional<PaymentTerm> paymentTerm = paymentTermService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentTerm);
    }

    /**
     * {@code DELETE  /payment-terms/:id} : delete the "id" paymentTerm.
     *
     * @param id the id of the paymentTerm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-terms/{id}")
    public ResponseEntity<Void> deletePaymentTerm(@PathVariable Long id) {
        log.debug("REST request to delete PaymentTerm : {}", id);
        paymentTermService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
