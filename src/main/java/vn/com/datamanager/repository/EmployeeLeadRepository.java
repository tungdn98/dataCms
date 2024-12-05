package vn.com.datamanager.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.com.datamanager.domain.EmployeeLead;

/**
 * Spring Data SQL repository for the EmployeeLead entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeLeadRepository extends JpaRepository<EmployeeLead, Long>, JpaSpecificationExecutor<EmployeeLead> {}
