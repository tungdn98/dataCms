package vn.com.datamanager.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.com.datamanager.domain.SaleOpportunity;

/**
 * Spring Data SQL repository for the SaleOpportunity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaleOpportunityRepository extends JpaRepository<SaleOpportunity, Long>, JpaSpecificationExecutor<SaleOpportunity> {}
