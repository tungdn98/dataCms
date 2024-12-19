package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.Finalcial;
import vn.com.datamanager.repository.FinalcialRepository;

/**
 * Service Implementation for managing {@link Finalcial}.
 */
@Service
@Transactional
public class FinalcialService {

    private final Logger log = LoggerFactory.getLogger(FinalcialService.class);

    private final FinalcialRepository finalcialRepository;

    public FinalcialService(FinalcialRepository finalcialRepository) {
        this.finalcialRepository = finalcialRepository;
    }

    /**
     * Save a finalcial.
     *
     * @param finalcial the entity to save.
     * @return the persisted entity.
     */
    public Finalcial save(Finalcial finalcial) {
        log.debug("Request to save Finalcial : {}", finalcial);
        return finalcialRepository.save(finalcial);
    }

    /**
     * Update a finalcial.
     *
     * @param finalcial the entity to save.
     * @return the persisted entity.
     */
    public Finalcial update(Finalcial finalcial) {
        log.debug("Request to save Finalcial : {}", finalcial);
        return finalcialRepository.save(finalcial);
    }

    /**
     * Partially update a finalcial.
     *
     * @param finalcial the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Finalcial> partialUpdate(Finalcial finalcial) {
        log.debug("Request to partially update Finalcial : {}", finalcial);

        return finalcialRepository
            .findById(finalcial.getId())
            .map(existingFinalcial -> {
                if (finalcial.getCode() != null) {
                    existingFinalcial.setCode(finalcial.getCode());
                }
                if (finalcial.getCustomerName() != null) {
                    existingFinalcial.setCustomerName(finalcial.getCustomerName());
                }
                if (finalcial.getCustomerShortName() != null) {
                    existingFinalcial.setCustomerShortName(finalcial.getCustomerShortName());
                }
                if (finalcial.getCustomerType() != null) {
                    existingFinalcial.setCustomerType(finalcial.getCustomerType());
                }

                return existingFinalcial;
            })
            .map(finalcialRepository::save);
    }

    /**
     * Get all the finalcials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Finalcial> findAll(Pageable pageable) {
        log.debug("Request to get all Finalcials");
        return finalcialRepository.findAll(pageable);
    }

    /**
     * Get one finalcial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Finalcial> findOne(Long id) {
        log.debug("Request to get Finalcial : {}", id);
        return finalcialRepository.findById(id);
    }

    /**
     * Delete the finalcial by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Finalcial : {}", id);
        finalcialRepository.deleteById(id);
    }
}
