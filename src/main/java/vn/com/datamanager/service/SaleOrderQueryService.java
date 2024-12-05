package vn.com.datamanager.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import vn.com.datamanager.domain.*; // for static metamodels
import vn.com.datamanager.domain.SaleOrder;
import vn.com.datamanager.repository.SaleOrderRepository;
import vn.com.datamanager.service.criteria.SaleOrderCriteria;

/**
 * Service for executing complex queries for {@link SaleOrder} entities in the database.
 * The main input is a {@link SaleOrderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SaleOrder} or a {@link Page} of {@link SaleOrder} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SaleOrderQueryService extends QueryService<SaleOrder> {

    private final Logger log = LoggerFactory.getLogger(SaleOrderQueryService.class);

    private final SaleOrderRepository saleOrderRepository;

    public SaleOrderQueryService(SaleOrderRepository saleOrderRepository) {
        this.saleOrderRepository = saleOrderRepository;
    }

    /**
     * Return a {@link List} of {@link SaleOrder} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SaleOrder> findByCriteria(SaleOrderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SaleOrder> specification = createSpecification(criteria);
        return saleOrderRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SaleOrder} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SaleOrder> findByCriteria(SaleOrderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SaleOrder> specification = createSpecification(criteria);
        return saleOrderRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SaleOrderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SaleOrder> specification = createSpecification(criteria);
        return saleOrderRepository.count(specification);
    }

    /**
     * Function to convert {@link SaleOrderCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SaleOrder> createSpecification(SaleOrderCriteria criteria) {
        Specification<SaleOrder> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SaleOrder_.id));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrderId(), SaleOrder_.orderId));
            }
            if (criteria.getContractId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContractId(), SaleOrder_.contractId));
            }
            if (criteria.getOwnerEmployeeId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOwnerEmployeeId(), SaleOrder_.ownerEmployeeId));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductId(), SaleOrder_.productId));
            }
            if (criteria.getTotalValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalValue(), SaleOrder_.totalValue));
            }
            if (criteria.getOrderStageId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrderStageId(), SaleOrder_.orderStageId));
            }
            if (criteria.getOrderStageName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrderStageName(), SaleOrder_.orderStageName));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), SaleOrder_.createdDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SaleOrder_.createdBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), SaleOrder_.lastModifiedDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SaleOrder_.lastModifiedBy));
            }
        }
        return specification;
    }
}
