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
import vn.com.datamanager.domain.Customer;
import vn.com.datamanager.repository.CustomerRepository;
import vn.com.datamanager.service.criteria.CustomerCriteria;

/**
 * Service for executing complex queries for {@link Customer} entities in the database.
 * The main input is a {@link CustomerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Customer} or a {@link Page} of {@link Customer} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomerQueryService extends QueryService<Customer> {

    private final Logger log = LoggerFactory.getLogger(CustomerQueryService.class);

    private final CustomerRepository customerRepository;

    public CustomerQueryService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Return a {@link List} of {@link Customer} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Customer> findByCriteria(CustomerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Customer> specification = createSpecification(criteria);
        return customerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Customer} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Customer> findByCriteria(CustomerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Customer> specification = createSpecification(criteria);
        return customerRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Customer> specification = createSpecification(criteria);
        return customerRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Customer> createSpecification(CustomerCriteria criteria) {
        Specification<Customer> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Customer_.id));
            }
            if (criteria.getAccountId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountId(), Customer_.accountId));
            }
            if (criteria.getAccountCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountCode(), Customer_.accountCode));
            }
            if (criteria.getAccountName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountName(), Customer_.accountName));
            }
            if (criteria.getMappingAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMappingAccount(), Customer_.mappingAccount));
            }
            if (criteria.getAccountEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountEmail(), Customer_.accountEmail));
            }
            if (criteria.getAccountPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountPhone(), Customer_.accountPhone));
            }
            if (criteria.getAccountTypeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountTypeName(), Customer_.accountTypeName));
            }
            if (criteria.getGenderName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGenderName(), Customer_.genderName));
            }
            if (criteria.getIndustryName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIndustryName(), Customer_.industryName));
            }
            if (criteria.getOwnerEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOwnerEmployeeId(), Customer_.ownerEmployeeId));
            }
        }
        return specification;
    }
}
