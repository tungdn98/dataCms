package vn.com.datamanager.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.com.datamanager.domain.OpportunityStageReason;

/**
 * Spring Data SQL repository for the OpportunityStageReason entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpportunityStageReasonRepository
    extends JpaRepository<OpportunityStageReason, Long>, JpaSpecificationExecutor<OpportunityStageReason> {}
