package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.Activity;
import vn.com.datamanager.repository.ActivityRepository;

/**
 * Service Implementation for managing {@link Activity}.
 */
@Service
@Transactional
public class ActivityService {

    private final Logger log = LoggerFactory.getLogger(ActivityService.class);

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * Save a activity.
     *
     * @param activity the entity to save.
     * @return the persisted entity.
     */
    public Activity save(Activity activity) {
        log.debug("Request to save Activity : {}", activity);
        return activityRepository.save(activity);
    }

    /**
     * Update a activity.
     *
     * @param activity the entity to save.
     * @return the persisted entity.
     */
    public Activity update(Activity activity) {
        log.debug("Request to save Activity : {}", activity);
        return activityRepository.save(activity);
    }

    /**
     * Partially update a activity.
     *
     * @param activity the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Activity> partialUpdate(Activity activity) {
        log.debug("Request to partially update Activity : {}", activity);

        return activityRepository
            .findById(activity.getId())
            .map(existingActivity -> {
                if (activity.getActivityId() != null) {
                    existingActivity.setActivityId(activity.getActivityId());
                }
                if (activity.getCompanyId() != null) {
                    existingActivity.setCompanyId(activity.getCompanyId());
                }
                if (activity.getCreateDate() != null) {
                    existingActivity.setCreateDate(activity.getCreateDate());
                }
                if (activity.getDeadline() != null) {
                    existingActivity.setDeadline(activity.getDeadline());
                }
                if (activity.getName() != null) {
                    existingActivity.setName(activity.getName());
                }
                if (activity.getState() != null) {
                    existingActivity.setState(activity.getState());
                }
                if (activity.getType() != null) {
                    existingActivity.setType(activity.getType());
                }
                if (activity.getAccountId() != null) {
                    existingActivity.setAccountId(activity.getAccountId());
                }
                if (activity.getActivityTypeId() != null) {
                    existingActivity.setActivityTypeId(activity.getActivityTypeId());
                }
                if (activity.getObjectTypeId() != null) {
                    existingActivity.setObjectTypeId(activity.getObjectTypeId());
                }
                if (activity.getPriorityId() != null) {
                    existingActivity.setPriorityId(activity.getPriorityId());
                }
                if (activity.getOpportunityId() != null) {
                    existingActivity.setOpportunityId(activity.getOpportunityId());
                }
                if (activity.getOrderId() != null) {
                    existingActivity.setOrderId(activity.getOrderId());
                }
                if (activity.getContractId() != null) {
                    existingActivity.setContractId(activity.getContractId());
                }
                if (activity.getPriorityName() != null) {
                    existingActivity.setPriorityName(activity.getPriorityName());
                }
                if (activity.getResponsibleId() != null) {
                    existingActivity.setResponsibleId(activity.getResponsibleId());
                }
                if (activity.getStartDate() != null) {
                    existingActivity.setStartDate(activity.getStartDate());
                }
                if (activity.getClosedOn() != null) {
                    existingActivity.setClosedOn(activity.getClosedOn());
                }
                if (activity.getDuration() != null) {
                    existingActivity.setDuration(activity.getDuration());
                }
                if (activity.getDurationUnitId() != null) {
                    existingActivity.setDurationUnitId(activity.getDurationUnitId());
                }
                if (activity.getConversion() != null) {
                    existingActivity.setConversion(activity.getConversion());
                }
                if (activity.getTextStr() != null) {
                    existingActivity.setTextStr(activity.getTextStr());
                }

                return existingActivity;
            })
            .map(activityRepository::save);
    }

    /**
     * Get all the activities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Activity> findAll(Pageable pageable) {
        log.debug("Request to get all Activities");
        return activityRepository.findAll(pageable);
    }

    /**
     * Get one activity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Activity> findOne(Long id) {
        log.debug("Request to get Activity : {}", id);
        return activityRepository.findById(id);
    }

    /**
     * Delete the activity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Activity : {}", id);
        activityRepository.deleteById(id);
    }
}
