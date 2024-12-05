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
import vn.com.datamanager.domain.OpportunityStage;
import vn.com.datamanager.repository.OpportunityStageRepository;
import vn.com.datamanager.service.criteria.OpportunityStageCriteria;

/**
 * Service for executing complex queries for {@link OpportunityStage} entities in the database.
 * The main input is a {@link OpportunityStageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OpportunityStage} or a {@link Page} of {@link OpportunityStage} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OpportunityStageQueryService extends QueryService<OpportunityStage> {

    private final Logger log = LoggerFactory.getLogger(OpportunityStageQueryService.class);

    private final OpportunityStageRepository opportunityStageRepository;

    public OpportunityStageQueryService(OpportunityStageRepository opportunityStageRepository) {
        this.opportunityStageRepository = opportunityStageRepository;
    }

    /**
     * Return a {@link List} of {@link OpportunityStage} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OpportunityStage> findByCriteria(OpportunityStageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OpportunityStage> specification = createSpecification(criteria);
        return opportunityStageRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link OpportunityStage} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OpportunityStage> findByCriteria(OpportunityStageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OpportunityStage> specification = createSpecification(criteria);
        return opportunityStageRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OpportunityStageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OpportunityStage> specification = createSpecification(criteria);
        return opportunityStageRepository.count(specification);
    }

    /**
     * Function to convert {@link OpportunityStageCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OpportunityStage> createSpecification(OpportunityStageCriteria criteria) {
        Specification<OpportunityStage> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OpportunityStage_.id));
            }
            if (criteria.getOpportunityStageId() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOpportunityStageId(), OpportunityStage_.opportunityStageId));
            }
            if (criteria.getOpportunityStageName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOpportunityStageName(), OpportunityStage_.opportunityStageName));
            }
            if (criteria.getOpportunityStageCode() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOpportunityStageCode(), OpportunityStage_.opportunityStageCode));
            }
        }
        return specification;
    }
}
