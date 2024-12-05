package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.LoginHistory;
import vn.com.datamanager.repository.LoginHistoryRepository;

/**
 * Service Implementation for managing {@link LoginHistory}.
 */
@Service
@Transactional
public class LoginHistoryService {

    private final Logger log = LoggerFactory.getLogger(LoginHistoryService.class);

    private final LoginHistoryRepository loginHistoryRepository;

    public LoginHistoryService(LoginHistoryRepository loginHistoryRepository) {
        this.loginHistoryRepository = loginHistoryRepository;
    }

    /**
     * Save a loginHistory.
     *
     * @param loginHistory the entity to save.
     * @return the persisted entity.
     */
    public LoginHistory save(LoginHistory loginHistory) {
        log.debug("Request to save LoginHistory : {}", loginHistory);
        return loginHistoryRepository.save(loginHistory);
    }

    /**
     * Update a loginHistory.
     *
     * @param loginHistory the entity to save.
     * @return the persisted entity.
     */
    public LoginHistory update(LoginHistory loginHistory) {
        log.debug("Request to save LoginHistory : {}", loginHistory);
        return loginHistoryRepository.save(loginHistory);
    }

    /**
     * Partially update a loginHistory.
     *
     * @param loginHistory the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LoginHistory> partialUpdate(LoginHistory loginHistory) {
        log.debug("Request to partially update LoginHistory : {}", loginHistory);

        return loginHistoryRepository
            .findById(loginHistory.getId())
            .map(existingLoginHistory -> {
                if (loginHistory.getEmpCode() != null) {
                    existingLoginHistory.setEmpCode(loginHistory.getEmpCode());
                }
                if (loginHistory.getEmpUsername() != null) {
                    existingLoginHistory.setEmpUsername(loginHistory.getEmpUsername());
                }
                if (loginHistory.getEmpFullName() != null) {
                    existingLoginHistory.setEmpFullName(loginHistory.getEmpFullName());
                }
                if (loginHistory.getLoginIp() != null) {
                    existingLoginHistory.setLoginIp(loginHistory.getLoginIp());
                }
                if (loginHistory.getLoginTime() != null) {
                    existingLoginHistory.setLoginTime(loginHistory.getLoginTime());
                }

                return existingLoginHistory;
            })
            .map(loginHistoryRepository::save);
    }

    /**
     * Get all the loginHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LoginHistory> findAll(Pageable pageable) {
        log.debug("Request to get all LoginHistories");
        return loginHistoryRepository.findAll(pageable);
    }

    /**
     * Get one loginHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LoginHistory> findOne(Long id) {
        log.debug("Request to get LoginHistory : {}", id);
        return loginHistoryRepository.findById(id);
    }

    /**
     * Delete the loginHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LoginHistory : {}", id);
        loginHistoryRepository.deleteById(id);
    }
}
