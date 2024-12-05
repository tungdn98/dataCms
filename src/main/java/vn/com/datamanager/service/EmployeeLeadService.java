package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.EmployeeLead;
import vn.com.datamanager.repository.EmployeeLeadRepository;

/**
 * Service Implementation for managing {@link EmployeeLead}.
 */
@Service
@Transactional
public class EmployeeLeadService {

    private final Logger log = LoggerFactory.getLogger(EmployeeLeadService.class);

    private final EmployeeLeadRepository employeeLeadRepository;

    public EmployeeLeadService(EmployeeLeadRepository employeeLeadRepository) {
        this.employeeLeadRepository = employeeLeadRepository;
    }

    /**
     * Save a employeeLead.
     *
     * @param employeeLead the entity to save.
     * @return the persisted entity.
     */
    public EmployeeLead save(EmployeeLead employeeLead) {
        log.debug("Request to save EmployeeLead : {}", employeeLead);
        return employeeLeadRepository.save(employeeLead);
    }

    /**
     * Update a employeeLead.
     *
     * @param employeeLead the entity to save.
     * @return the persisted entity.
     */
    public EmployeeLead update(EmployeeLead employeeLead) {
        log.debug("Request to save EmployeeLead : {}", employeeLead);
        return employeeLeadRepository.save(employeeLead);
    }

    /**
     * Partially update a employeeLead.
     *
     * @param employeeLead the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeLead> partialUpdate(EmployeeLead employeeLead) {
        log.debug("Request to partially update EmployeeLead : {}", employeeLead);

        return employeeLeadRepository
            .findById(employeeLead.getId())
            .map(existingEmployeeLead -> {
                if (employeeLead.getLeadId() != null) {
                    existingEmployeeLead.setLeadId(employeeLead.getLeadId());
                }
                if (employeeLead.getEmployeeId() != null) {
                    existingEmployeeLead.setEmployeeId(employeeLead.getEmployeeId());
                }
                if (employeeLead.getLeadCode() != null) {
                    existingEmployeeLead.setLeadCode(employeeLead.getLeadCode());
                }
                if (employeeLead.getLeadName() != null) {
                    existingEmployeeLead.setLeadName(employeeLead.getLeadName());
                }
                if (employeeLead.getLeadPotentialLevelId() != null) {
                    existingEmployeeLead.setLeadPotentialLevelId(employeeLead.getLeadPotentialLevelId());
                }
                if (employeeLead.getLeadSourceId() != null) {
                    existingEmployeeLead.setLeadSourceId(employeeLead.getLeadSourceId());
                }
                if (employeeLead.getLeadPotentialLevelName() != null) {
                    existingEmployeeLead.setLeadPotentialLevelName(employeeLead.getLeadPotentialLevelName());
                }
                if (employeeLead.getLeadSourceName() != null) {
                    existingEmployeeLead.setLeadSourceName(employeeLead.getLeadSourceName());
                }

                return existingEmployeeLead;
            })
            .map(employeeLeadRepository::save);
    }

    /**
     * Get all the employeeLeads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeLead> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeLeads");
        return employeeLeadRepository.findAll(pageable);
    }

    /**
     * Get one employeeLead by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeLead> findOne(Long id) {
        log.debug("Request to get EmployeeLead : {}", id);
        return employeeLeadRepository.findById(id);
    }

    /**
     * Delete the employeeLead by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeLead : {}", id);
        employeeLeadRepository.deleteById(id);
    }
}
