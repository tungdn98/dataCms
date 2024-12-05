package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.PaymentStatus;
import vn.com.datamanager.repository.PaymentStatusRepository;

/**
 * Service Implementation for managing {@link PaymentStatus}.
 */
@Service
@Transactional
public class PaymentStatusService {

    private final Logger log = LoggerFactory.getLogger(PaymentStatusService.class);

    private final PaymentStatusRepository paymentStatusRepository;

    public PaymentStatusService(PaymentStatusRepository paymentStatusRepository) {
        this.paymentStatusRepository = paymentStatusRepository;
    }

    /**
     * Save a paymentStatus.
     *
     * @param paymentStatus the entity to save.
     * @return the persisted entity.
     */
    public PaymentStatus save(PaymentStatus paymentStatus) {
        log.debug("Request to save PaymentStatus : {}", paymentStatus);
        return paymentStatusRepository.save(paymentStatus);
    }

    /**
     * Update a paymentStatus.
     *
     * @param paymentStatus the entity to save.
     * @return the persisted entity.
     */
    public PaymentStatus update(PaymentStatus paymentStatus) {
        log.debug("Request to save PaymentStatus : {}", paymentStatus);
        return paymentStatusRepository.save(paymentStatus);
    }

    /**
     * Partially update a paymentStatus.
     *
     * @param paymentStatus the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PaymentStatus> partialUpdate(PaymentStatus paymentStatus) {
        log.debug("Request to partially update PaymentStatus : {}", paymentStatus);

        return paymentStatusRepository
            .findById(paymentStatus.getId())
            .map(existingPaymentStatus -> {
                if (paymentStatus.getPaymentStatusId() != null) {
                    existingPaymentStatus.setPaymentStatusId(paymentStatus.getPaymentStatusId());
                }
                if (paymentStatus.getPaymentStatusName() != null) {
                    existingPaymentStatus.setPaymentStatusName(paymentStatus.getPaymentStatusName());
                }

                return existingPaymentStatus;
            })
            .map(paymentStatusRepository::save);
    }

    /**
     * Get all the paymentStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentStatus> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentStatuses");
        return paymentStatusRepository.findAll(pageable);
    }

    /**
     * Get one paymentStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentStatus> findOne(Long id) {
        log.debug("Request to get PaymentStatus : {}", id);
        return paymentStatusRepository.findById(id);
    }

    /**
     * Delete the paymentStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaymentStatus : {}", id);
        paymentStatusRepository.deleteById(id);
    }
}
