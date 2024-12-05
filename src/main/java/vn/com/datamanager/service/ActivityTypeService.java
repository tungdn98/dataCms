package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.ActivityType;
import vn.com.datamanager.repository.ActivityTypeRepository;

/**
 * Service Implementation for managing {@link ActivityType}.
 */
@Service
@Transactional
public class ActivityTypeService {

    private final Logger log = LoggerFactory.getLogger(ActivityTypeService.class);

    private final ActivityTypeRepository activityTypeRepository;

    public ActivityTypeService(ActivityTypeRepository activityTypeRepository) {
        this.activityTypeRepository = activityTypeRepository;
    }

    /**
     * Save a activityType.
     *
     * @param activityType the entity to save.
     * @return the persisted entity.
     */
    public ActivityType save(ActivityType activityType) {
        log.debug("Request to save ActivityType : {}", activityType);
        return activityTypeRepository.save(activityType);
    }

    /**
     * Update a activityType.
     *
     * @param activityType the entity to save.
     * @return the persisted entity.
     */
    public ActivityType update(ActivityType activityType) {
        log.debug("Request to save ActivityType : {}", activityType);
        return activityTypeRepository.save(activityType);
    }

    /**
     * Partially update a activityType.
     *
     * @param activityType the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ActivityType> partialUpdate(ActivityType activityType) {
        log.debug("Request to partially update ActivityType : {}", activityType);

        return activityTypeRepository
            .findById(activityType.getId())
            .map(existingActivityType -> {
                if (activityType.getActivityTypeId() != null) {
                    existingActivityType.setActivityTypeId(activityType.getActivityTypeId());
                }
                if (activityType.getActivityType() != null) {
                    existingActivityType.setActivityType(activityType.getActivityType());
                }
                if (activityType.getTextStr() != null) {
                    existingActivityType.setTextStr(activityType.getTextStr());
                }

                return existingActivityType;
            })
            .map(activityTypeRepository::save);
    }

    /**
     * Get all the activityTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ActivityType> findAll(Pageable pageable) {
        log.debug("Request to get all ActivityTypes");
        return activityTypeRepository.findAll(pageable);
    }

    /**
     * Get one activityType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ActivityType> findOne(Long id) {
        log.debug("Request to get ActivityType : {}", id);
        return activityTypeRepository.findById(id);
    }

    /**
     * Delete the activityType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ActivityType : {}", id);
        activityTypeRepository.deleteById(id);
    }
}
