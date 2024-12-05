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
import vn.com.datamanager.domain.PaymentTerm;
import vn.com.datamanager.repository.PaymentTermRepository;
import vn.com.datamanager.service.criteria.PaymentTermCriteria;

/**
 * Service for executing complex queries for {@link PaymentTerm} entities in the database.
 * The main input is a {@link PaymentTermCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentTerm} or a {@link Page} of {@link PaymentTerm} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentTermQueryService extends QueryService<PaymentTerm> {

    private final Logger log = LoggerFactory.getLogger(PaymentTermQueryService.class);

    private final PaymentTermRepository paymentTermRepository;

    public PaymentTermQueryService(PaymentTermRepository paymentTermRepository) {
        this.paymentTermRepository = paymentTermRepository;
    }

    /**
     * Return a {@link List} of {@link PaymentTerm} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentTerm> findByCriteria(PaymentTermCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaymentTerm> specification = createSpecification(criteria);
        return paymentTermRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PaymentTerm} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentTerm> findByCriteria(PaymentTermCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaymentTerm> specification = createSpecification(criteria);
        return paymentTermRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentTermCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaymentTerm> specification = createSpecification(criteria);
        return paymentTermRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentTermCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaymentTerm> createSpecification(PaymentTermCriteria criteria) {
        Specification<PaymentTerm> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaymentTerm_.id));
            }
            if (criteria.getPaymentTermId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentTermId(), PaymentTerm_.paymentTermId));
            }
            if (criteria.getPaymentTermCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentTermCode(), PaymentTerm_.paymentTermCode));
            }
            if (criteria.getPaymentTermName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentTermName(), PaymentTerm_.paymentTermName));
            }
        }
        return specification;
    }
}
