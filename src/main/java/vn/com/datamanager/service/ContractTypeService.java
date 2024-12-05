package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.ContractType;
import vn.com.datamanager.repository.ContractTypeRepository;

/**
 * Service Implementation for managing {@link ContractType}.
 */
@Service
@Transactional
public class ContractTypeService {

    private final Logger log = LoggerFactory.getLogger(ContractTypeService.class);

    private final ContractTypeRepository contractTypeRepository;

    public ContractTypeService(ContractTypeRepository contractTypeRepository) {
        this.contractTypeRepository = contractTypeRepository;
    }

    /**
     * Save a contractType.
     *
     * @param contractType the entity to save.
     * @return the persisted entity.
     */
    public ContractType save(ContractType contractType) {
        log.debug("Request to save ContractType : {}", contractType);
        return contractTypeRepository.save(contractType);
    }

    /**
     * Update a contractType.
     *
     * @param contractType the entity to save.
     * @return the persisted entity.
     */
    public ContractType update(ContractType contractType) {
        log.debug("Request to save ContractType : {}", contractType);
        return contractTypeRepository.save(contractType);
    }

    /**
     * Partially update a contractType.
     *
     * @param contractType the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContractType> partialUpdate(ContractType contractType) {
        log.debug("Request to partially update ContractType : {}", contractType);

        return contractTypeRepository
            .findById(contractType.getId())
            .map(existingContractType -> {
                if (contractType.getContractTypeId() != null) {
                    existingContractType.setContractTypeId(contractType.getContractTypeId());
                }
                if (contractType.getContractTypeName() != null) {
                    existingContractType.setContractTypeName(contractType.getContractTypeName());
                }
                if (contractType.getContractTypeCode() != null) {
                    existingContractType.setContractTypeCode(contractType.getContractTypeCode());
                }

                return existingContractType;
            })
            .map(contractTypeRepository::save);
    }

    /**
     * Get all the contractTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContractType> findAll(Pageable pageable) {
        log.debug("Request to get all ContractTypes");
        return contractTypeRepository.findAll(pageable);
    }

    /**
     * Get one contractType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContractType> findOne(Long id) {
        log.debug("Request to get ContractType : {}", id);
        return contractTypeRepository.findById(id);
    }

    /**
     * Delete the contractType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContractType : {}", id);
        contractTypeRepository.deleteById(id);
    }
}
