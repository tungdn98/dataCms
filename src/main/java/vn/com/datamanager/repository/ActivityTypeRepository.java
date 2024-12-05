package vn.com.datamanager.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.com.datamanager.domain.ActivityType;

/**
 * Spring Data SQL repository for the ActivityType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityTypeRepository extends JpaRepository<ActivityType, Long>, JpaSpecificationExecutor<ActivityType> {}
