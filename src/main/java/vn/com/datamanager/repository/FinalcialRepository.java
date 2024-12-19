package vn.com.datamanager.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.com.datamanager.domain.Finalcial;

/**
 * Spring Data SQL repository for the Finalcial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinalcialRepository extends JpaRepository<Finalcial, Long>, JpaSpecificationExecutor<Finalcial> {}
