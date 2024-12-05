package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.SaleOpportunity;
import vn.com.datamanager.repository.SaleOpportunityRepository;

/**
 * Service Implementation for managing {@link SaleOpportunity}.
 */
@Service
@Transactional
public class SaleOpportunityService {

    private final Logger log = LoggerFactory.getLogger(SaleOpportunityService.class);

    private final SaleOpportunityRepository saleOpportunityRepository;

    public SaleOpportunityService(SaleOpportunityRepository saleOpportunityRepository) {
        this.saleOpportunityRepository = saleOpportunityRepository;
    }

    /**
     * Save a saleOpportunity.
     *
     * @param saleOpportunity the entity to save.
     * @return the persisted entity.
     */
    public SaleOpportunity save(SaleOpportunity saleOpportunity) {
        log.debug("Request to save SaleOpportunity : {}", saleOpportunity);
        return saleOpportunityRepository.save(saleOpportunity);
    }

    /**
     * Update a saleOpportunity.
     *
     * @param saleOpportunity the entity to save.
     * @return the persisted entity.
     */
    public SaleOpportunity update(SaleOpportunity saleOpportunity) {
        log.debug("Request to save SaleOpportunity : {}", saleOpportunity);
        return saleOpportunityRepository.save(saleOpportunity);
    }

    /**
     * Partially update a saleOpportunity.
     *
     * @param saleOpportunity the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SaleOpportunity> partialUpdate(SaleOpportunity saleOpportunity) {
        log.debug("Request to partially update SaleOpportunity : {}", saleOpportunity);

        return saleOpportunityRepository
            .findById(saleOpportunity.getId())
            .map(existingSaleOpportunity -> {
                if (saleOpportunity.getOpportunityId() != null) {
                    existingSaleOpportunity.setOpportunityId(saleOpportunity.getOpportunityId());
                }
                if (saleOpportunity.getOpportunityCode() != null) {
                    existingSaleOpportunity.setOpportunityCode(saleOpportunity.getOpportunityCode());
                }
                if (saleOpportunity.getOpportunityName() != null) {
                    existingSaleOpportunity.setOpportunityName(saleOpportunity.getOpportunityName());
                }
                if (saleOpportunity.getOpportunityTypeName() != null) {
                    existingSaleOpportunity.setOpportunityTypeName(saleOpportunity.getOpportunityTypeName());
                }
                if (saleOpportunity.getStartDate() != null) {
                    existingSaleOpportunity.setStartDate(saleOpportunity.getStartDate());
                }
                if (saleOpportunity.getCloseDate() != null) {
                    existingSaleOpportunity.setCloseDate(saleOpportunity.getCloseDate());
                }
                if (saleOpportunity.getStageId() != null) {
                    existingSaleOpportunity.setStageId(saleOpportunity.getStageId());
                }
                if (saleOpportunity.getStageReasonId() != null) {
                    existingSaleOpportunity.setStageReasonId(saleOpportunity.getStageReasonId());
                }
                if (saleOpportunity.getEmployeeId() != null) {
                    existingSaleOpportunity.setEmployeeId(saleOpportunity.getEmployeeId());
                }
                if (saleOpportunity.getLeadId() != null) {
                    existingSaleOpportunity.setLeadId(saleOpportunity.getLeadId());
                }
                if (saleOpportunity.getCurrencyCode() != null) {
                    existingSaleOpportunity.setCurrencyCode(saleOpportunity.getCurrencyCode());
                }
                if (saleOpportunity.getAccountId() != null) {
                    existingSaleOpportunity.setAccountId(saleOpportunity.getAccountId());
                }
                if (saleOpportunity.getProductId() != null) {
                    existingSaleOpportunity.setProductId(saleOpportunity.getProductId());
                }
                if (saleOpportunity.getSalesPricePrd() != null) {
                    existingSaleOpportunity.setSalesPricePrd(saleOpportunity.getSalesPricePrd());
                }
                if (saleOpportunity.getValue() != null) {
                    existingSaleOpportunity.setValue(saleOpportunity.getValue());
                }

                return existingSaleOpportunity;
            })
            .map(saleOpportunityRepository::save);
    }

    /**
     * Get all the saleOpportunities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SaleOpportunity> findAll(Pageable pageable) {
        log.debug("Request to get all SaleOpportunities");
        return saleOpportunityRepository.findAll(pageable);
    }

    /**
     * Get one saleOpportunity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SaleOpportunity> findOne(Long id) {
        log.debug("Request to get SaleOpportunity : {}", id);
        return saleOpportunityRepository.findById(id);
    }

    /**
     * Delete the saleOpportunity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SaleOpportunity : {}", id);
        saleOpportunityRepository.deleteById(id);
    }
}
