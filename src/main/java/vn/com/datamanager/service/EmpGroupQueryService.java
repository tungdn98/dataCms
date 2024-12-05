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
import vn.com.datamanager.domain.EmpGroup;
import vn.com.datamanager.repository.EmpGroupRepository;
import vn.com.datamanager.service.criteria.EmpGroupCriteria;

/**
 * Service for executing complex queries for {@link EmpGroup} entities in the database.
 * The main input is a {@link EmpGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmpGroup} or a {@link Page} of {@link EmpGroup} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmpGroupQueryService extends QueryService<EmpGroup> {

    private final Logger log = LoggerFactory.getLogger(EmpGroupQueryService.class);

    private final EmpGroupRepository empGroupRepository;

    public EmpGroupQueryService(EmpGroupRepository empGroupRepository) {
        this.empGroupRepository = empGroupRepository;
    }

    /**
     * Return a {@link List} of {@link EmpGroup} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmpGroup> findByCriteria(EmpGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmpGroup> specification = createSpecification(criteria);
        return empGroupRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmpGroup} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmpGroup> findByCriteria(EmpGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmpGroup> specification = createSpecification(criteria);
        return empGroupRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmpGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmpGroup> specification = createSpecification(criteria);
        return empGroupRepository.count(specification);
    }

    /**
     * Function to convert {@link EmpGroupCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmpGroup> createSpecification(EmpGroupCriteria criteria) {
        Specification<EmpGroup> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmpGroup_.id));
            }
            if (criteria.getGroupName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupName(), EmpGroup_.groupName));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), EmpGroup_.createdDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), EmpGroup_.createdBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), EmpGroup_.lastModifiedDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), EmpGroup_.lastModifiedBy));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmpGroup_.employees, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
            if (criteria.getRoleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRoleId(), root -> root.join(EmpGroup_.roles, JoinType.LEFT).get(Roles_.id))
                    );
            }
        }
        return specification;
    }
}
