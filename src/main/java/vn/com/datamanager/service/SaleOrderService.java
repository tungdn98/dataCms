package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.SaleOrder;
import vn.com.datamanager.repository.SaleOrderRepository;

/**
 * Service Implementation for managing {@link SaleOrder}.
 */
@Service
@Transactional
public class SaleOrderService {

    private final Logger log = LoggerFactory.getLogger(SaleOrderService.class);

    private final SaleOrderRepository saleOrderRepository;

    public SaleOrderService(SaleOrderRepository saleOrderRepository) {
        this.saleOrderRepository = saleOrderRepository;
    }

    /**
     * Save a saleOrder.
     *
     * @param saleOrder the entity to save.
     * @return the persisted entity.
     */
    public SaleOrder save(SaleOrder saleOrder) {
        log.debug("Request to save SaleOrder : {}", saleOrder);
        return saleOrderRepository.save(saleOrder);
    }

    /**
     * Update a saleOrder.
     *
     * @param saleOrder the entity to save.
     * @return the persisted entity.
     */
    public SaleOrder update(SaleOrder saleOrder) {
        log.debug("Request to save SaleOrder : {}", saleOrder);
        return saleOrderRepository.save(saleOrder);
    }

    /**
     * Partially update a saleOrder.
     *
     * @param saleOrder the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SaleOrder> partialUpdate(SaleOrder saleOrder) {
        log.debug("Request to partially update SaleOrder : {}", saleOrder);

        return saleOrderRepository
            .findById(saleOrder.getId())
            .map(existingSaleOrder -> {
                if (saleOrder.getOrderId() != null) {
                    existingSaleOrder.setOrderId(saleOrder.getOrderId());
                }
                if (saleOrder.getContractId() != null) {
                    existingSaleOrder.setContractId(saleOrder.getContractId());
                }
                if (saleOrder.getOwnerEmployeeId() != null) {
                    existingSaleOrder.setOwnerEmployeeId(saleOrder.getOwnerEmployeeId());
                }
                if (saleOrder.getProductId() != null) {
                    existingSaleOrder.setProductId(saleOrder.getProductId());
                }
                if (saleOrder.getTotalValue() != null) {
                    existingSaleOrder.setTotalValue(saleOrder.getTotalValue());
                }
                if (saleOrder.getOrderStageId() != null) {
                    existingSaleOrder.setOrderStageId(saleOrder.getOrderStageId());
                }
                if (saleOrder.getOrderStageName() != null) {
                    existingSaleOrder.setOrderStageName(saleOrder.getOrderStageName());
                }
                if (saleOrder.getCreatedDate() != null) {
                    existingSaleOrder.setCreatedDate(saleOrder.getCreatedDate());
                }
                if (saleOrder.getCreatedBy() != null) {
                    existingSaleOrder.setCreatedBy(saleOrder.getCreatedBy());
                }
                if (saleOrder.getLastModifiedDate() != null) {
                    existingSaleOrder.setLastModifiedDate(saleOrder.getLastModifiedDate());
                }
                if (saleOrder.getLastModifiedBy() != null) {
                    existingSaleOrder.setLastModifiedBy(saleOrder.getLastModifiedBy());
                }

                return existingSaleOrder;
            })
            .map(saleOrderRepository::save);
    }

    /**
     * Get all the saleOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SaleOrder> findAll(Pageable pageable) {
        log.debug("Request to get all SaleOrders");
        return saleOrderRepository.findAll(pageable);
    }

    /**
     * Get one saleOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SaleOrder> findOne(Long id) {
        log.debug("Request to get SaleOrder : {}", id);
        return saleOrderRepository.findById(id);
    }

    /**
     * Delete the saleOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SaleOrder : {}", id);
        saleOrderRepository.deleteById(id);
    }
}
