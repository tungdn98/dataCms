package vn.com.datamanager.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.com.datamanager.domain.ContractType;

/**
 * Spring Data SQL repository for the ContractType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractTypeRepository extends JpaRepository<ContractType, Long>, JpaSpecificationExecutor<ContractType> {}
