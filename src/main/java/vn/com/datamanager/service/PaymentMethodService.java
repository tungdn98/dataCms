package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.PaymentMethod;
import vn.com.datamanager.repository.PaymentMethodRepository;

/**
 * Service Implementation for managing {@link PaymentMethod}.
 */
@Service
@Transactional
public class PaymentMethodService {

    private final Logger log = LoggerFactory.getLogger(PaymentMethodService.class);

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    /**
     * Save a paymentMethod.
     *
     * @param paymentMethod the entity to save.
     * @return the persisted entity.
     */
    public PaymentMethod save(PaymentMethod paymentMethod) {
        log.debug("Request to save PaymentMethod : {}", paymentMethod);
        return paymentMethodRepository.save(paymentMethod);
    }

    /**
     * Update a paymentMethod.
     *
     * @param paymentMethod the entity to save.
     * @return the persisted entity.
     */
    public PaymentMethod update(PaymentMethod paymentMethod) {
        log.debug("Request to save PaymentMethod : {}", paymentMethod);
        return paymentMethodRepository.save(paymentMethod);
    }

    /**
     * Partially update a paymentMethod.
     *
     * @param paymentMethod the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PaymentMethod> partialUpdate(PaymentMethod paymentMethod) {
        log.debug("Request to partially update PaymentMethod : {}", paymentMethod);

        return paymentMethodRepository
            .findById(paymentMethod.getId())
            .map(existingPaymentMethod -> {
                if (paymentMethod.getPaymentStatusId() != null) {
                    existingPaymentMethod.setPaymentStatusId(paymentMethod.getPaymentStatusId());
                }
                if (paymentMethod.getPaymentStatusName() != null) {
                    existingPaymentMethod.setPaymentStatusName(paymentMethod.getPaymentStatusName());
                }

                return existingPaymentMethod;
            })
            .map(paymentMethodRepository::save);
    }

    /**
     * Get all the paymentMethods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentMethod> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentMethods");
        return paymentMethodRepository.findAll(pageable);
    }

    /**
     * Get one paymentMethod by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentMethod> findOne(Long id) {
        log.debug("Request to get PaymentMethod : {}", id);
        return paymentMethodRepository.findById(id);
    }

    /**
     * Delete the paymentMethod by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaymentMethod : {}", id);
        paymentMethodRepository.deleteById(id);
    }
}
