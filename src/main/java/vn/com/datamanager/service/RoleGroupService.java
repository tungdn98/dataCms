package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.RoleGroup;
import vn.com.datamanager.repository.RoleGroupRepository;

/**
 * Service Implementation for managing {@link RoleGroup}.
 */
@Service
@Transactional
public class RoleGroupService {

    private final Logger log = LoggerFactory.getLogger(RoleGroupService.class);

    private final RoleGroupRepository roleGroupRepository;

    public RoleGroupService(RoleGroupRepository roleGroupRepository) {
        this.roleGroupRepository = roleGroupRepository;
    }

    /**
     * Save a roleGroup.
     *
     * @param roleGroup the entity to save.
     * @return the persisted entity.
     */
    public RoleGroup save(RoleGroup roleGroup) {
        log.debug("Request to save RoleGroup : {}", roleGroup);
        return roleGroupRepository.save(roleGroup);
    }

    /**
     * Update a roleGroup.
     *
     * @param roleGroup the entity to save.
     * @return the persisted entity.
     */
    public RoleGroup update(RoleGroup roleGroup) {
        log.debug("Request to save RoleGroup : {}", roleGroup);
        return roleGroupRepository.save(roleGroup);
    }

    /**
     * Partially update a roleGroup.
     *
     * @param roleGroup the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RoleGroup> partialUpdate(RoleGroup roleGroup) {
        log.debug("Request to partially update RoleGroup : {}", roleGroup);

        return roleGroupRepository
            .findById(roleGroup.getId())
            .map(existingRoleGroup -> {
                if (roleGroup.getGroupName() != null) {
                    existingRoleGroup.setGroupName(roleGroup.getGroupName());
                }
                if (roleGroup.getCreatedDate() != null) {
                    existingRoleGroup.setCreatedDate(roleGroup.getCreatedDate());
                }
                if (roleGroup.getCreatedBy() != null) {
                    existingRoleGroup.setCreatedBy(roleGroup.getCreatedBy());
                }
                if (roleGroup.getLastModifiedDate() != null) {
                    existingRoleGroup.setLastModifiedDate(roleGroup.getLastModifiedDate());
                }
                if (roleGroup.getLastModifiedBy() != null) {
                    existingRoleGroup.setLastModifiedBy(roleGroup.getLastModifiedBy());
                }

                return existingRoleGroup;
            })
            .map(roleGroupRepository::save);
    }

    /**
     * Get all the roleGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RoleGroup> findAll(Pageable pageable) {
        log.debug("Request to get all RoleGroups");
        return roleGroupRepository.findAll(pageable);
    }

    /**
     * Get one roleGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RoleGroup> findOne(Long id) {
        log.debug("Request to get RoleGroup : {}", id);
        return roleGroupRepository.findById(id);
    }

    /**
     * Delete the roleGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RoleGroup : {}", id);
        roleGroupRepository.deleteById(id);
    }
}
