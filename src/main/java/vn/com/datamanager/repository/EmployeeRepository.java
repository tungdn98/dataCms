package vn.com.datamanager.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.datamanager.domain.Employee;

/**
 * Spring Data SQL repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    @Query("select employee from Employee employee left join fetch employee.roles where employee.username =:username")
    Optional<Employee> findOneByUsernameIgnoreCase(@Param("username") String username);

    Optional<Employee> findOneByEmployeeCode(String code);

    @Query("select employee from Employee employee left join fetch employee.roles where employee.id =:id")
    Optional<Employee> findOneWithEagerRelationships(@Param("id") Long id);

    Optional<Employee> findFirstByUsername(String username);
}
