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
import vn.com.datamanager.domain.SaleOrder;
import vn.com.datamanager.repository.SaleOrderRepository;
import vn.com.datamanager.service.SaleOrderQueryService;
import vn.com.datamanager.service.SaleOrderService;
import vn.com.datamanager.service.criteria.SaleOrderCriteria;
import vn.com.datamanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.com.datamanager.domain.SaleOrder}.
 */
@RestController
@RequestMapping("/api")
public class SaleOrderResource {

    private final Logger log = LoggerFactory.getLogger(SaleOrderResource.class);

    private static final String ENTITY_NAME = "saleOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaleOrderService saleOrderService;

    private final SaleOrderRepository saleOrderRepository;

    private final SaleOrderQueryService saleOrderQueryService;

    public SaleOrderResource(
        SaleOrderService saleOrderService,
        SaleOrderRepository saleOrderRepository,
        SaleOrderQueryService saleOrderQueryService
    ) {
        this.saleOrderService = saleOrderService;
        this.saleOrderRepository = saleOrderRepository;
        this.saleOrderQueryService = saleOrderQueryService;
    }

    /**
     * {@code POST  /sale-orders} : Create a new saleOrder.
     *
     * @param saleOrder the saleOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new saleOrder, or with status {@code 400 (Bad Request)} if the saleOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sale-orders")
    public ResponseEntity<SaleOrder> createSaleOrder(@RequestBody SaleOrder saleOrder) throws URISyntaxException {
        log.debug("REST request to save SaleOrder : {}", saleOrder);
        if (saleOrder.getId() != null) {
            throw new BadRequestAlertException("A new saleOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SaleOrder result = saleOrderService.save(saleOrder);
        return ResponseEntity
            .created(new URI("/api/sale-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sale-orders/:id} : Updates an existing saleOrder.
     *
     * @param id the id of the saleOrder to save.
     * @param saleOrder the saleOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saleOrder,
     * or with status {@code 400 (Bad Request)} if the saleOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the saleOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sale-orders/{id}")
    public ResponseEntity<SaleOrder> updateSaleOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SaleOrder saleOrder
    ) throws URISyntaxException {
        log.debug("REST request to update SaleOrder : {}, {}", id, saleOrder);
        if (saleOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saleOrder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SaleOrder result = saleOrderService.update(saleOrder);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, saleOrder.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sale-orders/:id} : Partial updates given fields of an existing saleOrder, field will ignore if it is null
     *
     * @param id the id of the saleOrder to save.
     * @param saleOrder the saleOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saleOrder,
     * or with status {@code 400 (Bad Request)} if the saleOrder is not valid,
     * or with status {@code 404 (Not Found)} if the saleOrder is not found,
     * or with status {@code 500 (Internal Server Error)} if the saleOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sale-orders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SaleOrder> partialUpdateSaleOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SaleOrder saleOrder
    ) throws URISyntaxException {
        log.debug("REST request to partial update SaleOrder partially : {}, {}", id, saleOrder);
        if (saleOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saleOrder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SaleOrder> result = saleOrderService.partialUpdate(saleOrder);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, saleOrder.getId().toString())
        );
    }

    /**
     * {@code GET  /sale-orders} : get all the saleOrders.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saleOrders in body.
     */
    @GetMapping("/sale-orders")
    public ResponseEntity<List<SaleOrder>> getAllSaleOrders(
        SaleOrderCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SaleOrders by criteria: {}", criteria);
        Page<SaleOrder> page = saleOrderQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sale-orders/count} : count all the saleOrders.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sale-orders/count")
    public ResponseEntity<Long> countSaleOrders(SaleOrderCriteria criteria) {
        log.debug("REST request to count SaleOrders by criteria: {}", criteria);
        return ResponseEntity.ok().body(saleOrderQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sale-orders/:id} : get the "id" saleOrder.
     *
     * @param id the id of the saleOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the saleOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sale-orders/{id}")
    public ResponseEntity<SaleOrder> getSaleOrder(@PathVariable Long id) {
        log.debug("REST request to get SaleOrder : {}", id);
        Optional<SaleOrder> saleOrder = saleOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(saleOrder);
    }

    /**
     * {@code DELETE  /sale-orders/:id} : delete the "id" saleOrder.
     *
     * @param id the id of the saleOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sale-orders/{id}")
    public ResponseEntity<Void> deleteSaleOrder(@PathVariable Long id) {
        log.debug("REST request to delete SaleOrder : {}", id);
        saleOrderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
