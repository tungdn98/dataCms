package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.ConfigLog;
import vn.com.datamanager.repository.ConfigLogRepository;

/**
 * Service Implementation for managing {@link ConfigLog}.
 */
@Service
@Transactional
public class ConfigLogService {

    private final Logger log = LoggerFactory.getLogger(ConfigLogService.class);

    private final ConfigLogRepository configLogRepository;

    public ConfigLogService(ConfigLogRepository configLogRepository) {
        this.configLogRepository = configLogRepository;
    }

    /**
     * Save a configLog.
     *
     * @param configLog the entity to save.
     * @return the persisted entity.
     */
    public ConfigLog save(ConfigLog configLog) {
        log.debug("Request to save ConfigLog : {}", configLog);
        return configLogRepository.save(configLog);
    }

    /**
     * Update a configLog.
     *
     * @param configLog the entity to save.
     * @return the persisted entity.
     */
    public ConfigLog update(ConfigLog configLog) {
        log.debug("Request to save ConfigLog : {}", configLog);
        return configLogRepository.save(configLog);
    }

    /**
     * Partially update a configLog.
     *
     * @param configLog the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ConfigLog> partialUpdate(ConfigLog configLog) {
        log.debug("Request to partially update ConfigLog : {}", configLog);

        return configLogRepository
            .findById(configLog.getId())
            .map(existingConfigLog -> {
                if (configLog.getConfigName() != null) {
                    existingConfigLog.setConfigName(configLog.getConfigName());
                }
                if (configLog.getValueBefore() != null) {
                    existingConfigLog.setValueBefore(configLog.getValueBefore());
                }
                if (configLog.getValueAfter() != null) {
                    existingConfigLog.setValueAfter(configLog.getValueAfter());
                }
                if (configLog.getModifiedDate() != null) {
                    existingConfigLog.setModifiedDate(configLog.getModifiedDate());
                }
                if (configLog.getModifiedUsername() != null) {
                    existingConfigLog.setModifiedUsername(configLog.getModifiedUsername());
                }
                if (configLog.getModifiedFullname() != null) {
                    existingConfigLog.setModifiedFullname(configLog.getModifiedFullname());
                }

                return existingConfigLog;
            })
            .map(configLogRepository::save);
    }

    /**
     * Get all the configLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConfigLog> findAll(Pageable pageable) {
        log.debug("Request to get all ConfigLogs");
        return configLogRepository.findAll(pageable);
    }

    /**
     * Get one configLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConfigLog> findOne(Long id) {
        log.debug("Request to get ConfigLog : {}", id);
        return configLogRepository.findById(id);
    }

    /**
     * Delete the configLog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConfigLog : {}", id);
        configLogRepository.deleteById(id);
    }
}
