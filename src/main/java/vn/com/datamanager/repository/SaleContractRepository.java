package vn.com.datamanager.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.com.datamanager.domain.SaleContract;

/**
 * Spring Data SQL repository for the SaleContract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaleContractRepository extends JpaRepository<SaleContract, Long>, JpaSpecificationExecutor<SaleContract> {}
