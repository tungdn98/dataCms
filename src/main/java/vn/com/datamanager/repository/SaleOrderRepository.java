package vn.com.datamanager.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.com.datamanager.domain.SaleOrder;

/**
 * Spring Data SQL repository for the SaleOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaleOrderRepository extends JpaRepository<SaleOrder, Long>, JpaSpecificationExecutor<SaleOrder> {}
