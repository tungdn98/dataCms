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
import vn.com.datamanager.domain.Activity;
import vn.com.datamanager.repository.ActivityRepository;
import vn.com.datamanager.service.criteria.ActivityCriteria;

/**
 * Service for executing complex queries for {@link Activity} entities in the database.
 * The main input is a {@link ActivityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Activity} or a {@link Page} of {@link Activity} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ActivityQueryService extends QueryService<Activity> {

    private final Logger log = LoggerFactory.getLogger(ActivityQueryService.class);

    private final ActivityRepository activityRepository;

    public ActivityQueryService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * Return a {@link List} of {@link Activity} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Activity> findByCriteria(ActivityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Activity> specification = createSpecification(criteria);
        return activityRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Activity} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Activity> findByCriteria(ActivityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Activity> specification = createSpecification(criteria);
        return activityRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ActivityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Activity> specification = createSpecification(criteria);
        return activityRepository.count(specification);
    }

    /**
     * Function to convert {@link ActivityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Activity> createSpecification(ActivityCriteria criteria) {
        Specification<Activity> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Activity_.id));
            }
            if (criteria.getActivityId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivityId(), Activity_.activityId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyId(), Activity_.companyId));
            }
            if (criteria.getCreateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateDate(), Activity_.createDate));
            }
            if (criteria.getDeadline() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeadline(), Activity_.deadline));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Activity_.name));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), Activity_.state));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Activity_.type));
            }
            if (criteria.getAccountId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountId(), Activity_.accountId));
            }
            if (criteria.getActivityTypeId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivityTypeId(), Activity_.activityTypeId));
            }
            if (criteria.getObjectTypeId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObjectTypeId(), Activity_.objectTypeId));
            }
            if (criteria.getPriorityId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPriorityId(), Activity_.priorityId));
            }
            if (criteria.getOpportunityId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOpportunityId(), Activity_.opportunityId));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrderId(), Activity_.orderId));
            }
            if (criteria.getContractId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContractId(), Activity_.contractId));
            }
            if (criteria.getPriorityName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPriorityName(), Activity_.priorityName));
            }
            if (criteria.getResponsibleId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResponsibleId(), Activity_.responsibleId));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), Activity_.startDate));
            }
            if (criteria.getClosedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClosedOn(), Activity_.closedOn));
            }
            if (criteria.getDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuration(), Activity_.duration));
            }
            if (criteria.getDurationUnitId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDurationUnitId(), Activity_.durationUnitId));
            }
            if (criteria.getConversion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConversion(), Activity_.conversion));
            }
            if (criteria.getTextStr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextStr(), Activity_.textStr));
            }
        }
        return specification;
    }
}
