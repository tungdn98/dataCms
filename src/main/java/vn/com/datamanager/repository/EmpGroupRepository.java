package vn.com.datamanager.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.datamanager.domain.EmpGroup;

/**
 * Spring Data SQL repository for the EmpGroup entity.
 */
@Repository
public interface EmpGroupRepository
    extends EmpGroupRepositoryWithBagRelationships, JpaRepository<EmpGroup, Long>, JpaSpecificationExecutor<EmpGroup> {
    default Optional<EmpGroup> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<EmpGroup> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<EmpGroup> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
