package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.ActivityObject;
import vn.com.datamanager.repository.ActivityObjectRepository;

/**
 * Service Implementation for managing {@link ActivityObject}.
 */
@Service
@Transactional
public class ActivityObjectService {

    private final Logger log = LoggerFactory.getLogger(ActivityObjectService.class);

    private final ActivityObjectRepository activityObjectRepository;

    public ActivityObjectService(ActivityObjectRepository activityObjectRepository) {
        this.activityObjectRepository = activityObjectRepository;
    }

    /**
     * Save a activityObject.
     *
     * @param activityObject the entity to save.
     * @return the persisted entity.
     */
    public ActivityObject save(ActivityObject activityObject) {
        log.debug("Request to save ActivityObject : {}", activityObject);
        return activityObjectRepository.save(activityObject);
    }

    /**
     * Update a activityObject.
     *
     * @param activityObject the entity to save.
     * @return the persisted entity.
     */
    public ActivityObject update(ActivityObject activityObject) {
        log.debug("Request to save ActivityObject : {}", activityObject);
        return activityObjectRepository.save(activityObject);
    }

    /**
     * Partially update a activityObject.
     *
     * @param activityObject the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ActivityObject> partialUpdate(ActivityObject activityObject) {
        log.debug("Request to partially update ActivityObject : {}", activityObject);

        return activityObjectRepository
            .findById(activityObject.getId())
            .map(existingActivityObject -> {
                if (activityObject.getUnitCode() != null) {
                    existingActivityObject.setUnitCode(activityObject.getUnitCode());
                }
                if (activityObject.getUnitName() != null) {
                    existingActivityObject.setUnitName(activityObject.getUnitName());
                }

                return existingActivityObject;
            })
            .map(activityObjectRepository::save);
    }

    /**
     * Get all the activityObjects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ActivityObject> findAll(Pageable pageable) {
        log.debug("Request to get all ActivityObjects");
        return activityObjectRepository.findAll(pageable);
    }

    /**
     * Get one activityObject by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ActivityObject> findOne(Long id) {
        log.debug("Request to get ActivityObject : {}", id);
        return activityObjectRepository.findById(id);
    }

    /**
     * Delete the activityObject by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ActivityObject : {}", id);
        activityObjectRepository.deleteById(id);
    }
}
