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
import vn.com.datamanager.domain.SaleContract;
import vn.com.datamanager.repository.SaleContractRepository;
import vn.com.datamanager.service.criteria.SaleContractCriteria;

/**
 * Service for executing complex queries for {@link SaleContract} entities in the database.
 * The main input is a {@link SaleContractCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SaleContract} or a {@link Page} of {@link SaleContract} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SaleContractQueryService extends QueryService<SaleContract> {

    private final Logger log = LoggerFactory.getLogger(SaleContractQueryService.class);

    private final SaleContractRepository saleContractRepository;

    public SaleContractQueryService(SaleContractRepository saleContractRepository) {
        this.saleContractRepository = saleContractRepository;
    }

    /**
     * Return a {@link List} of {@link SaleContract} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SaleContract> findByCriteria(SaleContractCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SaleContract> specification = createSpecification(criteria);
        return saleContractRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SaleContract} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SaleContract> findByCriteria(SaleContractCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SaleContract> specification = createSpecification(criteria);
        return saleContractRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SaleContractCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SaleContract> specification = createSpecification(criteria);
        return saleContractRepository.count(specification);
    }

    /**
     * Function to convert {@link SaleContractCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SaleContract> createSpecification(SaleContractCriteria criteria) {
        Specification<SaleContract> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SaleContract_.id));
            }
            if (criteria.getContractId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContractId(), SaleContract_.contractId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyId(), SaleContract_.companyId));
            }
            if (criteria.getAccountId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountId(), SaleContract_.accountId));
            }
            if (criteria.getContactSignedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getContactSignedDate(), SaleContract_.contactSignedDate));
            }
            if (criteria.getContactSignedTitle() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getContactSignedTitle(), SaleContract_.contactSignedTitle));
            }
            if (criteria.getContractEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContractEndDate(), SaleContract_.contractEndDate));
            }
            if (criteria.getContractNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContractNumber(), SaleContract_.contractNumber));
            }
            if (criteria.getContractNumberInput() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getContractNumberInput(), SaleContract_.contractNumberInput));
            }
            if (criteria.getContractStageId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContractStageId(), SaleContract_.contractStageId));
            }
            if (criteria.getContractStartDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getContractStartDate(), SaleContract_.contractStartDate));
            }
            if (criteria.getOwnerEmployeeId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOwnerEmployeeId(), SaleContract_.ownerEmployeeId));
            }
            if (criteria.getPaymentMethodId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentMethodId(), SaleContract_.paymentMethodId));
            }
            if (criteria.getContractName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContractName(), SaleContract_.contractName));
            }
            if (criteria.getContractTypeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContractTypeId(), SaleContract_.contractTypeId));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrencyId(), SaleContract_.currencyId));
            }
            if (criteria.getGrandTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGrandTotal(), SaleContract_.grandTotal));
            }
            if (criteria.getPaymentTermId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentTermId(), SaleContract_.paymentTermId));
            }
            if (criteria.getQuoteId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuoteId(), SaleContract_.quoteId));
            }
            if (criteria.getCurrencyExchangeRateId() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCurrencyExchangeRateId(), SaleContract_.currencyExchangeRateId));
            }
            if (criteria.getContractStageName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getContractStageName(), SaleContract_.contractStageName));
            }
            if (criteria.getPaymentStatusId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentStatusId(), SaleContract_.paymentStatusId));
            }
            if (criteria.getPeriod() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriod(), SaleContract_.period));
            }
            if (criteria.getPayment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPayment(), SaleContract_.payment));
            }
        }
        return specification;
    }
}
