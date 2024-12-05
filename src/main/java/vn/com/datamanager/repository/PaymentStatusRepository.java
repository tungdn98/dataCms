package vn.com.datamanager.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.com.datamanager.domain.PaymentStatus;

/**
 * Spring Data SQL repository for the PaymentStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long>, JpaSpecificationExecutor<PaymentStatus> {}
