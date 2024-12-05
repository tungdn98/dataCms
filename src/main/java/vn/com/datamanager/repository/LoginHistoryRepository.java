package vn.com.datamanager.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.com.datamanager.domain.LoginHistory;

/**
 * Spring Data SQL repository for the LoginHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long>, JpaSpecificationExecutor<LoginHistory> {}
