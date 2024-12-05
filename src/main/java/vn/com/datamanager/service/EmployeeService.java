package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.Employee;
import vn.com.datamanager.repository.EmployeeRepository;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
@Transactional
public class EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Save a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    public Employee save(Employee employee) {
        log.debug("Request to save Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    /**
     * Update a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    public Employee update(Employee employee) {
        log.debug("Request to save Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    /**
     * Partially update a employee.
     *
     * @param employee the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Employee> partialUpdate(Employee employee) {
        log.debug("Request to partially update Employee : {}", employee);

        return employeeRepository
            .findById(employee.getId())
            .map(existingEmployee -> {
                if (employee.getEmployeeCode() != null) {
                    existingEmployee.setEmployeeCode(employee.getEmployeeCode());
                }
                if (employee.getEmployeeName() != null) {
                    existingEmployee.setEmployeeName(employee.getEmployeeName());
                }
                if (employee.getUsername() != null) {
                    existingEmployee.setUsername(employee.getUsername());
                }
                if (employee.getPassword() != null) {
                    existingEmployee.setPassword(employee.getPassword());
                }
                if (employee.getActive() != null) {
                    existingEmployee.setActive(employee.getActive());
                }
                if (employee.getCompanyCode() != null) {
                    existingEmployee.setCompanyCode(employee.getCompanyCode());
                }
                if (employee.getCompanyName() != null) {
                    existingEmployee.setCompanyName(employee.getCompanyName());
                }
                if (employee.getOrganizationId() != null) {
                    existingEmployee.setOrganizationId(employee.getOrganizationId());
                }
                if (employee.getEmployeeLastName() != null) {
                    existingEmployee.setEmployeeLastName(employee.getEmployeeLastName());
                }
                if (employee.getEmployeeMiddleName() != null) {
                    existingEmployee.setEmployeeMiddleName(employee.getEmployeeMiddleName());
                }
                if (employee.getEmployeeTitleId() != null) {
                    existingEmployee.setEmployeeTitleId(employee.getEmployeeTitleId());
                }
                if (employee.getEmployeeTitleName() != null) {
                    existingEmployee.setEmployeeTitleName(employee.getEmployeeTitleName());
                }
                if (employee.getEmployeeFullName() != null) {
                    existingEmployee.setEmployeeFullName(employee.getEmployeeFullName());
                }
                if (employee.getCreatedDate() != null) {
                    existingEmployee.setCreatedDate(employee.getCreatedDate());
                }
                if (employee.getCreatedBy() != null) {
                    existingEmployee.setCreatedBy(employee.getCreatedBy());
                }
                if (employee.getLastModifiedDate() != null) {
                    existingEmployee.setLastModifiedDate(employee.getLastModifiedDate());
                }
                if (employee.getLastModifiedBy() != null) {
                    existingEmployee.setLastModifiedBy(employee.getLastModifiedBy());
                }

                return existingEmployee;
            })
            .map(employeeRepository::save);
    }

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Employee> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable);
    }

    /**
     * Get one employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Employee> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id);
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }
}
