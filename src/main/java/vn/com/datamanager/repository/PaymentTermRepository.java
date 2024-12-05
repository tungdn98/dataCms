package vn.com.datamanager.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.com.datamanager.domain.PaymentTerm;

/**
 * Spring Data SQL repository for the PaymentTerm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentTermRepository extends JpaRepository<PaymentTerm, Long>, JpaSpecificationExecutor<PaymentTerm> {}
