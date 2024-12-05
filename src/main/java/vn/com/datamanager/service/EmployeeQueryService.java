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
import vn.com.datamanager.domain.Employee;
import vn.com.datamanager.repository.EmployeeRepository;
import vn.com.datamanager.service.criteria.EmployeeCriteria;

/**
 * Service for executing complex queries for {@link Employee} entities in the database.
 * The main input is a {@link EmployeeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Employee} or a {@link Page} of {@link Employee} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeQueryService extends QueryService<Employee> {

    private final Logger log = LoggerFactory.getLogger(EmployeeQueryService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeQueryService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Return a {@link List} of {@link Employee} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Employee> findByCriteria(EmployeeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Employee} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Employee> findByCriteria(EmployeeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Employee> createSpecification(EmployeeCriteria criteria) {
        Specification<Employee> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Employee_.id));
            }
            if (criteria.getEmployeeCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeCode(), Employee_.employeeCode));
            }
            if (criteria.getEmployeeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeName(), Employee_.employeeName));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), Employee_.username));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), Employee_.password));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActive(), Employee_.active));
            }
            if (criteria.getCompanyCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyCode(), Employee_.companyCode));
            }
            if (criteria.getCompanyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyName(), Employee_.companyName));
            }
            if (criteria.getOrganizationId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganizationId(), Employee_.organizationId));
            }
            if (criteria.getEmployeeLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeLastName(), Employee_.employeeLastName));
            }
            if (criteria.getEmployeeMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeMiddleName(), Employee_.employeeMiddleName));
            }
            if (criteria.getEmployeeTitleId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeTitleId(), Employee_.employeeTitleId));
            }
            if (criteria.getEmployeeTitleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeTitleName(), Employee_.employeeTitleName));
            }
            if (criteria.getEmployeeFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeFullName(), Employee_.employeeFullName));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Employee_.createdDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Employee_.createdBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Employee_.lastModifiedDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Employee_.lastModifiedBy));
            }
            if (criteria.getEmpGroupId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEmpGroupId(), root -> root.join(Employee_.empGroup, JoinType.LEFT).get(EmpGroup_.id))
                    );
            }
        }
        return specification;
    }
}
