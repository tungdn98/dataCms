package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.OpportunityStageReason;
import vn.com.datamanager.repository.OpportunityStageReasonRepository;

/**
 * Service Implementation for managing {@link OpportunityStageReason}.
 */
@Service
@Transactional
public class OpportunityStageReasonService {

    private final Logger log = LoggerFactory.getLogger(OpportunityStageReasonService.class);

    private final OpportunityStageReasonRepository opportunityStageReasonRepository;

    public OpportunityStageReasonService(OpportunityStageReasonRepository opportunityStageReasonRepository) {
        this.opportunityStageReasonRepository = opportunityStageReasonRepository;
    }

    /**
     * Save a opportunityStageReason.
     *
     * @param opportunityStageReason the entity to save.
     * @return the persisted entity.
     */
    public OpportunityStageReason save(OpportunityStageReason opportunityStageReason) {
        log.debug("Request to save OpportunityStageReason : {}", opportunityStageReason);
        return opportunityStageReasonRepository.save(opportunityStageReason);
    }

    /**
     * Update a opportunityStageReason.
     *
     * @param opportunityStageReason the entity to save.
     * @return the persisted entity.
     */
    public OpportunityStageReason update(OpportunityStageReason opportunityStageReason) {
        log.debug("Request to save OpportunityStageReason : {}", opportunityStageReason);
        return opportunityStageReasonRepository.save(opportunityStageReason);
    }

    /**
     * Partially update a opportunityStageReason.
     *
     * @param opportunityStageReason the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OpportunityStageReason> partialUpdate(OpportunityStageReason opportunityStageReason) {
        log.debug("Request to partially update OpportunityStageReason : {}", opportunityStageReason);

        return opportunityStageReasonRepository
            .findById(opportunityStageReason.getId())
            .map(existingOpportunityStageReason -> {
                if (opportunityStageReason.getOpportunityStageReasonId() != null) {
                    existingOpportunityStageReason.setOpportunityStageReasonId(opportunityStageReason.getOpportunityStageReasonId());
                }
                if (opportunityStageReason.getOpportunityStageId() != null) {
                    existingOpportunityStageReason.setOpportunityStageId(opportunityStageReason.getOpportunityStageId());
                }
                if (opportunityStageReason.getOpportunityStageReasonName() != null) {
                    existingOpportunityStageReason.setOpportunityStageReasonName(opportunityStageReason.getOpportunityStageReasonName());
                }

                return existingOpportunityStageReason;
            })
            .map(opportunityStageReasonRepository::save);
    }

    /**
     * Get all the opportunityStageReasons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OpportunityStageReason> findAll(Pageable pageable) {
        log.debug("Request to get all OpportunityStageReasons");
        return opportunityStageReasonRepository.findAll(pageable);
    }

    /**
     * Get one opportunityStageReason by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OpportunityStageReason> findOne(Long id) {
        log.debug("Request to get OpportunityStageReason : {}", id);
        return opportunityStageReasonRepository.findById(id);
    }

    /**
     * Delete the opportunityStageReason by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OpportunityStageReason : {}", id);
        opportunityStageReasonRepository.deleteById(id);
    }
}
