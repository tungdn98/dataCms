package vn.com.datamanager.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.com.datamanager.domain.ActivityObject;

/**
 * Spring Data SQL repository for the ActivityObject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityObjectRepository extends JpaRepository<ActivityObject, Long>, JpaSpecificationExecutor<ActivityObject> {}
