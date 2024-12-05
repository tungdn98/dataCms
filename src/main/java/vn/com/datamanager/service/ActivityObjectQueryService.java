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
import vn.com.datamanager.domain.ActivityObject;
import vn.com.datamanager.repository.ActivityObjectRepository;
import vn.com.datamanager.service.criteria.ActivityObjectCriteria;

/**
 * Service for executing complex queries for {@link ActivityObject} entities in the database.
 * The main input is a {@link ActivityObjectCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ActivityObject} or a {@link Page} of {@link ActivityObject} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ActivityObjectQueryService extends QueryService<ActivityObject> {

    private final Logger log = LoggerFactory.getLogger(ActivityObjectQueryService.class);

    private final ActivityObjectRepository activityObjectRepository;

    public ActivityObjectQueryService(ActivityObjectRepository activityObjectRepository) {
        this.activityObjectRepository = activityObjectRepository;
    }

    /**
     * Return a {@link List} of {@link ActivityObject} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ActivityObject> findByCriteria(ActivityObjectCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ActivityObject> specification = createSpecification(criteria);
        return activityObjectRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ActivityObject} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ActivityObject> findByCriteria(ActivityObjectCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ActivityObject> specification = createSpecification(criteria);
        return activityObjectRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ActivityObjectCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ActivityObject> specification = createSpecification(criteria);
        return activityObjectRepository.count(specification);
    }

    /**
     * Function to convert {@link ActivityObjectCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ActivityObject> createSpecification(ActivityObjectCriteria criteria) {
        Specification<ActivityObject> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ActivityObject_.id));
            }
            if (criteria.getUnitCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnitCode(), ActivityObject_.unitCode));
            }
            if (criteria.getUnitName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnitName(), ActivityObject_.unitName));
            }
        }
        return specification;
    }
}
