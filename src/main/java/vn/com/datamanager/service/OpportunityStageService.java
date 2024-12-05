package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.OpportunityStage;
import vn.com.datamanager.repository.OpportunityStageRepository;

/**
 * Service Implementation for managing {@link OpportunityStage}.
 */
@Service
@Transactional
public class OpportunityStageService {

    private final Logger log = LoggerFactory.getLogger(OpportunityStageService.class);

    private final OpportunityStageRepository opportunityStageRepository;

    public OpportunityStageService(OpportunityStageRepository opportunityStageRepository) {
        this.opportunityStageRepository = opportunityStageRepository;
    }

    /**
     * Save a opportunityStage.
     *
     * @param opportunityStage the entity to save.
     * @return the persisted entity.
     */
    public OpportunityStage save(OpportunityStage opportunityStage) {
        log.debug("Request to save OpportunityStage : {}", opportunityStage);
        return opportunityStageRepository.save(opportunityStage);
    }

    /**
     * Update a opportunityStage.
     *
     * @param opportunityStage the entity to save.
     * @return the persisted entity.
     */
    public OpportunityStage update(OpportunityStage opportunityStage) {
        log.debug("Request to save OpportunityStage : {}", opportunityStage);
        return opportunityStageRepository.save(opportunityStage);
    }

    /**
     * Partially update a opportunityStage.
     *
     * @param opportunityStage the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OpportunityStage> partialUpdate(OpportunityStage opportunityStage) {
        log.debug("Request to partially update OpportunityStage : {}", opportunityStage);

        return opportunityStageRepository
            .findById(opportunityStage.getId())
            .map(existingOpportunityStage -> {
                if (opportunityStage.getOpportunityStageId() != null) {
                    existingOpportunityStage.setOpportunityStageId(opportunityStage.getOpportunityStageId());
                }
                if (opportunityStage.getOpportunityStageName() != null) {
                    existingOpportunityStage.setOpportunityStageName(opportunityStage.getOpportunityStageName());
                }
                if (opportunityStage.getOpportunityStageCode() != null) {
                    existingOpportunityStage.setOpportunityStageCode(opportunityStage.getOpportunityStageCode());
                }

                return existingOpportunityStage;
            })
            .map(opportunityStageRepository::save);
    }

    /**
     * Get all the opportunityStages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OpportunityStage> findAll(Pageable pageable) {
        log.debug("Request to get all OpportunityStages");
        return opportunityStageRepository.findAll(pageable);
    }

    /**
     * Get one opportunityStage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OpportunityStage> findOne(Long id) {
        log.debug("Request to get OpportunityStage : {}", id);
        return opportunityStageRepository.findById(id);
    }

    /**
     * Delete the opportunityStage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OpportunityStage : {}", id);
        opportunityStageRepository.deleteById(id);
    }
}
