package vn.com.datamanager.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import vn.com.datamanager.domain.EmpGroup;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class EmpGroupRepositoryWithBagRelationshipsImpl implements EmpGroupRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<EmpGroup> fetchBagRelationships(Optional<EmpGroup> empGroup) {
        return empGroup.map(this::fetchRoles);
    }

    @Override
    public Page<EmpGroup> fetchBagRelationships(Page<EmpGroup> empGroups) {
        return new PageImpl<>(fetchBagRelationships(empGroups.getContent()), empGroups.getPageable(), empGroups.getTotalElements());
    }

    @Override
    public List<EmpGroup> fetchBagRelationships(List<EmpGroup> empGroups) {
        return Optional.of(empGroups).map(this::fetchRoles).orElse(Collections.emptyList());
    }

    EmpGroup fetchRoles(EmpGroup result) {
        return entityManager
            .createQuery(
                "select empGroup from EmpGroup empGroup left join fetch empGroup.roles where empGroup is :empGroup",
                EmpGroup.class
            )
            .setParameter("empGroup", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<EmpGroup> fetchRoles(List<EmpGroup> empGroups) {
        return entityManager
            .createQuery(
                "select distinct empGroup from EmpGroup empGroup left join fetch empGroup.roles where empGroup in :empGroups",
                EmpGroup.class
            )
            .setParameter("empGroups", empGroups)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
