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
import vn.com.datamanager.domain.ActivityType;
import vn.com.datamanager.repository.ActivityTypeRepository;
import vn.com.datamanager.service.criteria.ActivityTypeCriteria;

/**
 * Service for executing complex queries for {@link ActivityType} entities in the database.
 * The main input is a {@link ActivityTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ActivityType} or a {@link Page} of {@link ActivityType} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ActivityTypeQueryService extends QueryService<ActivityType> {

    private final Logger log = LoggerFactory.getLogger(ActivityTypeQueryService.class);

    private final ActivityTypeRepository activityTypeRepository;

    public ActivityTypeQueryService(ActivityTypeRepository activityTypeRepository) {
        this.activityTypeRepository = activityTypeRepository;
    }

    /**
     * Return a {@link List} of {@link ActivityType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ActivityType> findByCriteria(ActivityTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ActivityType> specification = createSpecification(criteria);
        return activityTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ActivityType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ActivityType> findByCriteria(ActivityTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ActivityType> specification = createSpecification(criteria);
        return activityTypeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ActivityTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ActivityType> specification = createSpecification(criteria);
        return activityTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link ActivityTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ActivityType> createSpecification(ActivityTypeCriteria criteria) {
        Specification<ActivityType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ActivityType_.id));
            }
            if (criteria.getActivityTypeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActivityTypeId(), ActivityType_.activityTypeId));
            }
            if (criteria.getActivityType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivityType(), ActivityType_.activityType));
            }
            if (criteria.getTextStr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextStr(), ActivityType_.textStr));
            }
        }
        return specification;
    }
}
