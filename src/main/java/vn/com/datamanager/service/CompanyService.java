package vn.com.datamanager.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.Company;
import vn.com.datamanager.repository.CompanyRepository;

/**
 * Service Implementation for managing {@link Company}.
 */
@Service
@Transactional
public class CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * Save a company.
     *
     * @param company the entity to save.
     * @return the persisted entity.
     */
    public Company save(Company company) {
        log.debug("Request to save Company : {}", company);
        return companyRepository.save(company);
    }

    /**
     * Update a company.
     *
     * @param company the entity to save.
     * @return the persisted entity.
     */
    public Company update(Company company) {
        log.debug("Request to save Company : {}", company);
        return companyRepository.save(company);
    }

    /**
     * Partially update a company.
     *
     * @param company the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Company> partialUpdate(Company company) {
        log.debug("Request to partially update Company : {}", company);

        return companyRepository
            .findById(company.getId())
            .map(existingCompany -> {
                if (company.getCompanyCode() != null) {
                    existingCompany.setCompanyCode(company.getCompanyCode());
                }
                if (company.getCompanyName() != null) {
                    existingCompany.setCompanyName(company.getCompanyName());
                }
                if (company.getDescription() != null) {
                    existingCompany.setDescription(company.getDescription());
                }
                if (company.getLocation() != null) {
                    existingCompany.setLocation(company.getLocation());
                }
                if (company.getPhoneNumber() != null) {
                    existingCompany.setPhoneNumber(company.getPhoneNumber());
                }
                if (company.getCreatedDate() != null) {
                    existingCompany.setCreatedDate(company.getCreatedDate());
                }
                if (company.getCreatedBy() != null) {
                    existingCompany.setCreatedBy(company.getCreatedBy());
                }
                if (company.getLastModifiedDate() != null) {
                    existingCompany.setLastModifiedDate(company.getLastModifiedDate());
                }
                if (company.getLastModifiedBy() != null) {
                    existingCompany.setLastModifiedBy(company.getLastModifiedBy());
                }

                return existingCompany;
            })
            .map(companyRepository::save);
    }

    /**
     * Get all the companies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Company> findAll(Pageable pageable) {
        log.debug("Request to get all Companies");
        return companyRepository.findAll(pageable);
    }

    /**
     * Get one company by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Company> findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        return companyRepository.findById(id);
    }

    /**
     * Delete the company by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.deleteById(id);
    }

    @Transactional
    public Integer saveAll(List<Company> companies) {
        log.debug("Request to save companies : {}", companies);
        List<Company> savedCompanies = companyRepository.saveAll(companies);
        return savedCompanies.size();
    }
}
