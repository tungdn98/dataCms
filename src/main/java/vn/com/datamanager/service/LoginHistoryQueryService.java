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
import vn.com.datamanager.domain.LoginHistory;
import vn.com.datamanager.repository.LoginHistoryRepository;
import vn.com.datamanager.service.criteria.LoginHistoryCriteria;

/**
 * Service for executing complex queries for {@link LoginHistory} entities in the database.
 * The main input is a {@link LoginHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LoginHistory} or a {@link Page} of {@link LoginHistory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LoginHistoryQueryService extends QueryService<LoginHistory> {

    private final Logger log = LoggerFactory.getLogger(LoginHistoryQueryService.class);

    private final LoginHistoryRepository loginHistoryRepository;

    public LoginHistoryQueryService(LoginHistoryRepository loginHistoryRepository) {
        this.loginHistoryRepository = loginHistoryRepository;
    }

    /**
     * Return a {@link List} of {@link LoginHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LoginHistory> findByCriteria(LoginHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LoginHistory> specification = createSpecification(criteria);
        return loginHistoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LoginHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LoginHistory> findByCriteria(LoginHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LoginHistory> specification = createSpecification(criteria);
        return loginHistoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LoginHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LoginHistory> specification = createSpecification(criteria);
        return loginHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link LoginHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LoginHistory> createSpecification(LoginHistoryCriteria criteria) {
        Specification<LoginHistory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LoginHistory_.id));
            }
            if (criteria.getEmpCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpCode(), LoginHistory_.empCode));
            }
            if (criteria.getEmpUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpUsername(), LoginHistory_.empUsername));
            }
            if (criteria.getEmpFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpFullName(), LoginHistory_.empFullName));
            }
            if (criteria.getLoginIp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoginIp(), LoginHistory_.loginIp));
            }
            if (criteria.getLoginTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoginTime(), LoginHistory_.loginTime));
            }
        }
        return specification;
    }
}
