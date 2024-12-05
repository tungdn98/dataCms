package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.Unit;
import vn.com.datamanager.repository.UnitRepository;

/**
 * Service Implementation for managing {@link Unit}.
 */
@Service
@Transactional
public class UnitService {

    private final Logger log = LoggerFactory.getLogger(UnitService.class);

    private final UnitRepository unitRepository;

    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    /**
     * Save a unit.
     *
     * @param unit the entity to save.
     * @return the persisted entity.
     */
    public Unit save(Unit unit) {
        log.debug("Request to save Unit : {}", unit);
        return unitRepository.save(unit);
    }

    /**
     * Update a unit.
     *
     * @param unit the entity to save.
     * @return the persisted entity.
     */
    public Unit update(Unit unit) {
        log.debug("Request to save Unit : {}", unit);
        return unitRepository.save(unit);
    }

    /**
     * Partially update a unit.
     *
     * @param unit the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Unit> partialUpdate(Unit unit) {
        log.debug("Request to partially update Unit : {}", unit);

        return unitRepository
            .findById(unit.getId())
            .map(existingUnit -> {
                if (unit.getUnitCode() != null) {
                    existingUnit.setUnitCode(unit.getUnitCode());
                }
                if (unit.getUnitName() != null) {
                    existingUnit.setUnitName(unit.getUnitName());
                }

                return existingUnit;
            })
            .map(unitRepository::save);
    }

    /**
     * Get all the units.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Unit> findAll(Pageable pageable) {
        log.debug("Request to get all Units");
        return unitRepository.findAll(pageable);
    }

    /**
     * Get one unit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Unit> findOne(Long id) {
        log.debug("Request to get Unit : {}", id);
        return unitRepository.findById(id);
    }

    /**
     * Delete the unit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Unit : {}", id);
        unitRepository.deleteById(id);
    }
}
