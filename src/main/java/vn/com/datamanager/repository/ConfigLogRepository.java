package vn.com.datamanager.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.com.datamanager.domain.ConfigLog;

/**
 * Spring Data SQL repository for the ConfigLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigLogRepository extends JpaRepository<ConfigLog, Long>, JpaSpecificationExecutor<ConfigLog> {}
