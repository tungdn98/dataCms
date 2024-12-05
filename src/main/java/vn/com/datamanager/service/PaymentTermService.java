package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.PaymentTerm;
import vn.com.datamanager.repository.PaymentTermRepository;

/**
 * Service Implementation for managing {@link PaymentTerm}.
 */
@Service
@Transactional
public class PaymentTermService {

    private final Logger log = LoggerFactory.getLogger(PaymentTermService.class);

    private final PaymentTermRepository paymentTermRepository;

    public PaymentTermService(PaymentTermRepository paymentTermRepository) {
        this.paymentTermRepository = paymentTermRepository;
    }

    /**
     * Save a paymentTerm.
     *
     * @param paymentTerm the entity to save.
     * @return the persisted entity.
     */
    public PaymentTerm save(PaymentTerm paymentTerm) {
        log.debug("Request to save PaymentTerm : {}", paymentTerm);
        return paymentTermRepository.save(paymentTerm);
    }

    /**
     * Update a paymentTerm.
     *
     * @param paymentTerm the entity to save.
     * @return the persisted entity.
     */
    public PaymentTerm update(PaymentTerm paymentTerm) {
        log.debug("Request to save PaymentTerm : {}", paymentTerm);
        return paymentTermRepository.save(paymentTerm);
    }

    /**
     * Partially update a paymentTerm.
     *
     * @param paymentTerm the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PaymentTerm> partialUpdate(PaymentTerm paymentTerm) {
        log.debug("Request to partially update PaymentTerm : {}", paymentTerm);

        return paymentTermRepository
            .findById(paymentTerm.getId())
            .map(existingPaymentTerm -> {
                if (paymentTerm.getPaymentTermId() != null) {
                    existingPaymentTerm.setPaymentTermId(paymentTerm.getPaymentTermId());
                }
                if (paymentTerm.getPaymentTermCode() != null) {
                    existingPaymentTerm.setPaymentTermCode(paymentTerm.getPaymentTermCode());
                }
                if (paymentTerm.getPaymentTermName() != null) {
                    existingPaymentTerm.setPaymentTermName(paymentTerm.getPaymentTermName());
                }

                return existingPaymentTerm;
            })
            .map(paymentTermRepository::save);
    }

    /**
     * Get all the paymentTerms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentTerm> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentTerms");
        return paymentTermRepository.findAll(pageable);
    }

    /**
     * Get one paymentTerm by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentTerm> findOne(Long id) {
        log.debug("Request to get PaymentTerm : {}", id);
        return paymentTermRepository.findById(id);
    }

    /**
     * Delete the paymentTerm by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaymentTerm : {}", id);
        paymentTermRepository.deleteById(id);
    }
}
