package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.EmpGroup;
import vn.com.datamanager.repository.EmpGroupRepository;

/**
 * Service Implementation for managing {@link EmpGroup}.
 */
@Service
@Transactional
public class EmpGroupService {

    private final Logger log = LoggerFactory.getLogger(EmpGroupService.class);

    private final EmpGroupRepository empGroupRepository;

    public EmpGroupService(EmpGroupRepository empGroupRepository) {
        this.empGroupRepository = empGroupRepository;
    }

    /**
     * Save a empGroup.
     *
     * @param empGroup the entity to save.
     * @return the persisted entity.
     */
    public EmpGroup save(EmpGroup empGroup) {
        log.debug("Request to save EmpGroup : {}", empGroup);
        return empGroupRepository.save(empGroup);
    }

    /**
     * Update a empGroup.
     *
     * @param empGroup the entity to save.
     * @return the persisted entity.
     */
    public EmpGroup update(EmpGroup empGroup) {
        log.debug("Request to save EmpGroup : {}", empGroup);
        return empGroupRepository.save(empGroup);
    }

    /**
     * Partially update a empGroup.
     *
     * @param empGroup the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmpGroup> partialUpdate(EmpGroup empGroup) {
        log.debug("Request to partially update EmpGroup : {}", empGroup);

        return empGroupRepository
            .findById(empGroup.getId())
            .map(existingEmpGroup -> {
                if (empGroup.getGroupName() != null) {
                    existingEmpGroup.setGroupName(empGroup.getGroupName());
                }
                if (empGroup.getCreatedDate() != null) {
                    existingEmpGroup.setCreatedDate(empGroup.getCreatedDate());
                }
                if (empGroup.getCreatedBy() != null) {
                    existingEmpGroup.setCreatedBy(empGroup.getCreatedBy());
                }
                if (empGroup.getLastModifiedDate() != null) {
                    existingEmpGroup.setLastModifiedDate(empGroup.getLastModifiedDate());
                }
                if (empGroup.getLastModifiedBy() != null) {
                    existingEmpGroup.setLastModifiedBy(empGroup.getLastModifiedBy());
                }

                return existingEmpGroup;
            })
            .map(empGroupRepository::save);
    }

    /**
     * Get all the empGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmpGroup> findAll(Pageable pageable) {
        log.debug("Request to get all EmpGroups");
        return empGroupRepository.findAll(pageable);
    }

    /**
     * Get all the empGroups with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EmpGroup> findAllWithEagerRelationships(Pageable pageable) {
        return empGroupRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one empGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmpGroup> findOne(Long id) {
        log.debug("Request to get EmpGroup : {}", id);
        return empGroupRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the empGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmpGroup : {}", id);
        empGroupRepository.deleteById(id);
    }
}
