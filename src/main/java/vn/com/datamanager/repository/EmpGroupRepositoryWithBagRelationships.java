package vn.com.datamanager.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import vn.com.datamanager.domain.EmpGroup;

public interface EmpGroupRepositoryWithBagRelationships {
    Optional<EmpGroup> fetchBagRelationships(Optional<EmpGroup> empGroup);

    List<EmpGroup> fetchBagRelationships(List<EmpGroup> empGroups);

    Page<EmpGroup> fetchBagRelationships(Page<EmpGroup> empGroups);
}
