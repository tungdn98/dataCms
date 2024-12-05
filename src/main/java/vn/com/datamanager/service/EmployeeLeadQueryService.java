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
import vn.com.datamanager.domain.EmployeeLead;
import vn.com.datamanager.repository.EmployeeLeadRepository;
import vn.com.datamanager.service.criteria.EmployeeLeadCriteria;

/**
 * Service for executing complex queries for {@link EmployeeLead} entities in the database.
 * The main input is a {@link EmployeeLeadCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeLead} or a {@link Page} of {@link EmployeeLead} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeLeadQueryService extends QueryService<EmployeeLead> {

    private final Logger log = LoggerFactory.getLogger(EmployeeLeadQueryService.class);

    private final EmployeeLeadRepository employeeLeadRepository;

    public EmployeeLeadQueryService(EmployeeLeadRepository employeeLeadRepository) {
        this.employeeLeadRepository = employeeLeadRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeLead} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeLead> findByCriteria(EmployeeLeadCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeLead> specification = createSpecification(criteria);
        return employeeLeadRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeLead} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeLead> findByCriteria(EmployeeLeadCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeLead> specification = createSpecification(criteria);
        return employeeLeadRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeLeadCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeLead> specification = createSpecification(criteria);
        return employeeLeadRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeLeadCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeLead> createSpecification(EmployeeLeadCriteria criteria) {
        Specification<EmployeeLead> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeLead_.id));
            }
            if (criteria.getLeadId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLeadId(), EmployeeLead_.leadId));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeId(), EmployeeLead_.employeeId));
            }
            if (criteria.getLeadCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLeadCode(), EmployeeLead_.leadCode));
            }
            if (criteria.getLeadName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLeadName(), EmployeeLead_.leadName));
            }
            if (criteria.getLeadPotentialLevelId() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLeadPotentialLevelId(), EmployeeLead_.leadPotentialLevelId));
            }
            if (criteria.getLeadSourceId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLeadSourceId(), EmployeeLead_.leadSourceId));
            }
            if (criteria.getLeadPotentialLevelName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLeadPotentialLevelName(), EmployeeLead_.leadPotentialLevelName));
            }
            if (criteria.getLeadSourceName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLeadSourceName(), EmployeeLead_.leadSourceName));
            }
        }
        return specification;
    }
}
