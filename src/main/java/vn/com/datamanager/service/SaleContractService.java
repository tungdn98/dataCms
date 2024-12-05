package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.SaleContract;
import vn.com.datamanager.repository.SaleContractRepository;

/**
 * Service Implementation for managing {@link SaleContract}.
 */
@Service
@Transactional
public class SaleContractService {

    private final Logger log = LoggerFactory.getLogger(SaleContractService.class);

    private final SaleContractRepository saleContractRepository;

    public SaleContractService(SaleContractRepository saleContractRepository) {
        this.saleContractRepository = saleContractRepository;
    }

    /**
     * Save a saleContract.
     *
     * @param saleContract the entity to save.
     * @return the persisted entity.
     */
    public SaleContract save(SaleContract saleContract) {
        log.debug("Request to save SaleContract : {}", saleContract);
        return saleContractRepository.save(saleContract);
    }

    /**
     * Update a saleContract.
     *
     * @param saleContract the entity to save.
     * @return the persisted entity.
     */
    public SaleContract update(SaleContract saleContract) {
        log.debug("Request to save SaleContract : {}", saleContract);
        return saleContractRepository.save(saleContract);
    }

    /**
     * Partially update a saleContract.
     *
     * @param saleContract the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SaleContract> partialUpdate(SaleContract saleContract) {
        log.debug("Request to partially update SaleContract : {}", saleContract);

        return saleContractRepository
            .findById(saleContract.getId())
            .map(existingSaleContract -> {
                if (saleContract.getContractId() != null) {
                    existingSaleContract.setContractId(saleContract.getContractId());
                }
                if (saleContract.getCompanyId() != null) {
                    existingSaleContract.setCompanyId(saleContract.getCompanyId());
                }
                if (saleContract.getAccountId() != null) {
                    existingSaleContract.setAccountId(saleContract.getAccountId());
                }
                if (saleContract.getContactSignedDate() != null) {
                    existingSaleContract.setContactSignedDate(saleContract.getContactSignedDate());
                }
                if (saleContract.getContactSignedTitle() != null) {
                    existingSaleContract.setContactSignedTitle(saleContract.getContactSignedTitle());
                }
                if (saleContract.getContractEndDate() != null) {
                    existingSaleContract.setContractEndDate(saleContract.getContractEndDate());
                }
                if (saleContract.getContractNumber() != null) {
                    existingSaleContract.setContractNumber(saleContract.getContractNumber());
                }
                if (saleContract.getContractNumberInput() != null) {
                    existingSaleContract.setContractNumberInput(saleContract.getContractNumberInput());
                }
                if (saleContract.getContractStageId() != null) {
                    existingSaleContract.setContractStageId(saleContract.getContractStageId());
                }
                if (saleContract.getContractStartDate() != null) {
                    existingSaleContract.setContractStartDate(saleContract.getContractStartDate());
                }
                if (saleContract.getOwnerEmployeeId() != null) {
                    existingSaleContract.setOwnerEmployeeId(saleContract.getOwnerEmployeeId());
                }
                if (saleContract.getPaymentMethodId() != null) {
                    existingSaleContract.setPaymentMethodId(saleContract.getPaymentMethodId());
                }
                if (saleContract.getContractName() != null) {
                    existingSaleContract.setContractName(saleContract.getContractName());
                }
                if (saleContract.getContractTypeId() != null) {
                    existingSaleContract.setContractTypeId(saleContract.getContractTypeId());
                }
                if (saleContract.getCurrencyId() != null) {
                    existingSaleContract.setCurrencyId(saleContract.getCurrencyId());
                }
                if (saleContract.getGrandTotal() != null) {
                    existingSaleContract.setGrandTotal(saleContract.getGrandTotal());
                }
                if (saleContract.getPaymentTermId() != null) {
                    existingSaleContract.setPaymentTermId(saleContract.getPaymentTermId());
                }
                if (saleContract.getQuoteId() != null) {
                    existingSaleContract.setQuoteId(saleContract.getQuoteId());
                }
                if (saleContract.getCurrencyExchangeRateId() != null) {
                    existingSaleContract.setCurrencyExchangeRateId(saleContract.getCurrencyExchangeRateId());
                }
                if (saleContract.getContractStageName() != null) {
                    existingSaleContract.setContractStageName(saleContract.getContractStageName());
                }
                if (saleContract.getPaymentStatusId() != null) {
                    existingSaleContract.setPaymentStatusId(saleContract.getPaymentStatusId());
                }
                if (saleContract.getPeriod() != null) {
                    existingSaleContract.setPeriod(saleContract.getPeriod());
                }
                if (saleContract.getPayment() != null) {
                    existingSaleContract.setPayment(saleContract.getPayment());
                }

                return existingSaleContract;
            })
            .map(saleContractRepository::save);
    }

    /**
     * Get all the saleContracts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SaleContract> findAll(Pageable pageable) {
        log.debug("Request to get all SaleContracts");
        return saleContractRepository.findAll(pageable);
    }

    /**
     * Get one saleContract by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SaleContract> findOne(Long id) {
        log.debug("Request to get SaleContract : {}", id);
        return saleContractRepository.findById(id);
    }

    /**
     * Delete the saleContract by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SaleContract : {}", id);
        saleContractRepository.deleteById(id);
    }
}
