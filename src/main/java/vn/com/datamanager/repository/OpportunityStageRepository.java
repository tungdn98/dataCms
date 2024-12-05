package vn.com.datamanager.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.com.datamanager.domain.OpportunityStage;

/**
 * Spring Data SQL repository for the OpportunityStage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpportunityStageRepository extends JpaRepository<OpportunityStage, Long>, JpaSpecificationExecutor<OpportunityStage> {}
