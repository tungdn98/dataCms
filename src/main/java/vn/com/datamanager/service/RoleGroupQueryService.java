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
import vn.com.datamanager.domain.RoleGroup;
import vn.com.datamanager.repository.RoleGroupRepository;
import vn.com.datamanager.service.criteria.RoleGroupCriteria;

/**
 * Service for executing complex queries for {@link RoleGroup} entities in the database.
 * The main input is a {@link RoleGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RoleGroup} or a {@link Page} of {@link RoleGroup} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RoleGroupQueryService extends QueryService<RoleGroup> {

    private final Logger log = LoggerFactory.getLogger(RoleGroupQueryService.class);

    private final RoleGroupRepository roleGroupRepository;

    public RoleGroupQueryService(RoleGroupRepository roleGroupRepository) {
        this.roleGroupRepository = roleGroupRepository;
    }

    /**
     * Return a {@link List} of {@link RoleGroup} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RoleGroup> findByCriteria(RoleGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RoleGroup> specification = createSpecification(criteria);
        return roleGroupRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RoleGroup} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RoleGroup> findByCriteria(RoleGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RoleGroup> specification = createSpecification(criteria);
        return roleGroupRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RoleGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RoleGroup> specification = createSpecification(criteria);
        return roleGroupRepository.count(specification);
    }

    /**
     * Function to convert {@link RoleGroupCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RoleGroup> createSpecification(RoleGroupCriteria criteria) {
        Specification<RoleGroup> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RoleGroup_.id));
            }
            if (criteria.getGroupName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupName(), RoleGroup_.groupName));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), RoleGroup_.createdDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), RoleGroup_.createdBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), RoleGroup_.lastModifiedDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), RoleGroup_.lastModifiedBy));
            }
            if (criteria.getRoleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRoleId(), root -> root.join(RoleGroup_.roles, JoinType.LEFT).get(Roles_.id))
                    );
            }
        }
        return specification;
    }
}
