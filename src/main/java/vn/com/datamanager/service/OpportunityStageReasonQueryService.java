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
import vn.com.datamanager.domain.OpportunityStageReason;
import vn.com.datamanager.repository.OpportunityStageReasonRepository;
import vn.com.datamanager.service.criteria.OpportunityStageReasonCriteria;

/**
 * Service for executing complex queries for {@link OpportunityStageReason} entities in the database.
 * The main input is a {@link OpportunityStageReasonCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OpportunityStageReason} or a {@link Page} of {@link OpportunityStageReason} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OpportunityStageReasonQueryService extends QueryService<OpportunityStageReason> {

    private final Logger log = LoggerFactory.getLogger(OpportunityStageReasonQueryService.class);

    private final OpportunityStageReasonRepository opportunityStageReasonRepository;

    public OpportunityStageReasonQueryService(OpportunityStageReasonRepository opportunityStageReasonRepository) {
        this.opportunityStageReasonRepository = opportunityStageReasonRepository;
    }

    /**
     * Return a {@link List} of {@link OpportunityStageReason} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OpportunityStageReason> findByCriteria(OpportunityStageReasonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OpportunityStageReason> specification = createSpecification(criteria);
        return opportunityStageReasonRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link OpportunityStageReason} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OpportunityStageReason> findByCriteria(OpportunityStageReasonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OpportunityStageReason> specification = createSpecification(criteria);
        return opportunityStageReasonRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OpportunityStageReasonCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OpportunityStageReason> specification = createSpecification(criteria);
        return opportunityStageReasonRepository.count(specification);
    }

    /**
     * Function to convert {@link OpportunityStageReasonCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OpportunityStageReason> createSpecification(OpportunityStageReasonCriteria criteria) {
        Specification<OpportunityStageReason> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OpportunityStageReason_.id));
            }
            if (criteria.getOpportunityStageReasonId() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getOpportunityStageReasonId(), OpportunityStageReason_.opportunityStageReasonId)
                    );
            }
            if (criteria.getOpportunityStageId() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getOpportunityStageId(), OpportunityStageReason_.opportunityStageId)
                    );
            }
            if (criteria.getOpportunityStageReasonName() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getOpportunityStageReasonName(),
                            OpportunityStageReason_.opportunityStageReasonName
                        )
                    );
            }
        }
        return specification;
    }
}
