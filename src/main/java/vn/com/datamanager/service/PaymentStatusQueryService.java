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
import vn.com.datamanager.domain.PaymentStatus;
import vn.com.datamanager.repository.PaymentStatusRepository;
import vn.com.datamanager.service.criteria.PaymentStatusCriteria;

/**
 * Service for executing complex queries for {@link PaymentStatus} entities in the database.
 * The main input is a {@link PaymentStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentStatus} or a {@link Page} of {@link PaymentStatus} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentStatusQueryService extends QueryService<PaymentStatus> {

    private final Logger log = LoggerFactory.getLogger(PaymentStatusQueryService.class);

    private final PaymentStatusRepository paymentStatusRepository;

    public PaymentStatusQueryService(PaymentStatusRepository paymentStatusRepository) {
        this.paymentStatusRepository = paymentStatusRepository;
    }

    /**
     * Return a {@link List} of {@link PaymentStatus} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentStatus> findByCriteria(PaymentStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaymentStatus> specification = createSpecification(criteria);
        return paymentStatusRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PaymentStatus} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentStatus> findByCriteria(PaymentStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaymentStatus> specification = createSpecification(criteria);
        return paymentStatusRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaymentStatus> specification = createSpecification(criteria);
        return paymentStatusRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentStatusCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaymentStatus> createSpecification(PaymentStatusCriteria criteria) {
        Specification<PaymentStatus> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaymentStatus_.id));
            }
            if (criteria.getPaymentStatusId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentStatusId(), PaymentStatus_.paymentStatusId));
            }
            if (criteria.getPaymentStatusName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPaymentStatusName(), PaymentStatus_.paymentStatusName));
            }
        }
        return specification;
    }
}
