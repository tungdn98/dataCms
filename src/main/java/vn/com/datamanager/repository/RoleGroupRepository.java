package vn.com.datamanager.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.com.datamanager.domain.RoleGroup;

/**
 * Spring Data SQL repository for the RoleGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleGroupRepository extends JpaRepository<RoleGroup, Long>, JpaSpecificationExecutor<RoleGroup> {}
