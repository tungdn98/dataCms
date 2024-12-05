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
import vn.com.datamanager.domain.SaleOpportunity;
import vn.com.datamanager.repository.SaleOpportunityRepository;
import vn.com.datamanager.service.criteria.SaleOpportunityCriteria;

/**
 * Service for executing complex queries for {@link SaleOpportunity} entities in the database.
 * The main input is a {@link SaleOpportunityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SaleOpportunity} or a {@link Page} of {@link SaleOpportunity} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SaleOpportunityQueryService extends QueryService<SaleOpportunity> {

    private final Logger log = LoggerFactory.getLogger(SaleOpportunityQueryService.class);

    private final SaleOpportunityRepository saleOpportunityRepository;

    public SaleOpportunityQueryService(SaleOpportunityRepository saleOpportunityRepository) {
        this.saleOpportunityRepository = saleOpportunityRepository;
    }

    /**
     * Return a {@link List} of {@link SaleOpportunity} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SaleOpportunity> findByCriteria(SaleOpportunityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SaleOpportunity> specification = createSpecification(criteria);
        return saleOpportunityRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SaleOpportunity} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SaleOpportunity> findByCriteria(SaleOpportunityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SaleOpportunity> specification = createSpecification(criteria);
        return saleOpportunityRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SaleOpportunityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SaleOpportunity> specification = createSpecification(criteria);
        return saleOpportunityRepository.count(specification);
    }

    /**
     * Function to convert {@link SaleOpportunityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SaleOpportunity> createSpecification(SaleOpportunityCriteria criteria) {
        Specification<SaleOpportunity> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SaleOpportunity_.id));
            }
            if (criteria.getOpportunityId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOpportunityId(), SaleOpportunity_.opportunityId));
            }
            if (criteria.getOpportunityCode() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOpportunityCode(), SaleOpportunity_.opportunityCode));
            }
            if (criteria.getOpportunityName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOpportunityName(), SaleOpportunity_.opportunityName));
            }
            if (criteria.getOpportunityTypeName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOpportunityTypeName(), SaleOpportunity_.opportunityTypeName));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), SaleOpportunity_.startDate));
            }
            if (criteria.getCloseDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCloseDate(), SaleOpportunity_.closeDate));
            }
            if (criteria.getStageId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStageId(), SaleOpportunity_.stageId));
            }
            if (criteria.getStageReasonId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStageReasonId(), SaleOpportunity_.stageReasonId));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), SaleOpportunity_.employeeId));
            }
            if (criteria.getLeadId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLeadId(), SaleOpportunity_.leadId));
            }
            if (criteria.getCurrencyCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrencyCode(), SaleOpportunity_.currencyCode));
            }
            if (criteria.getAccountId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountId(), SaleOpportunity_.accountId));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductId(), SaleOpportunity_.productId));
            }
            if (criteria.getSalesPricePrd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalesPricePrd(), SaleOpportunity_.salesPricePrd));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), SaleOpportunity_.value));
            }
        }
        return specification;
    }
}
