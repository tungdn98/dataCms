package vn.com.datamanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.datamanager.domain.Customer;
import vn.com.datamanager.repository.CustomerRepository;

/**
 * Service Implementation for managing {@link Customer}.
 */
@Service
@Transactional
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Save a customer.
     *
     * @param customer the entity to save.
     * @return the persisted entity.
     */
    public Customer save(Customer customer) {
        log.debug("Request to save Customer : {}", customer);
        return customerRepository.save(customer);
    }

    /**
     * Update a customer.
     *
     * @param customer the entity to save.
     * @return the persisted entity.
     */
    public Customer update(Customer customer) {
        log.debug("Request to save Customer : {}", customer);
        return customerRepository.save(customer);
    }

    /**
     * Partially update a customer.
     *
     * @param customer the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Customer> partialUpdate(Customer customer) {
        log.debug("Request to partially update Customer : {}", customer);

        return customerRepository
            .findById(customer.getId())
            .map(existingCustomer -> {
                if (customer.getAccountId() != null) {
                    existingCustomer.setAccountId(customer.getAccountId());
                }
                if (customer.getAccountCode() != null) {
                    existingCustomer.setAccountCode(customer.getAccountCode());
                }
                if (customer.getAccountName() != null) {
                    existingCustomer.setAccountName(customer.getAccountName());
                }
                if (customer.getMappingAccount() != null) {
                    existingCustomer.setMappingAccount(customer.getMappingAccount());
                }
                if (customer.getAccountEmail() != null) {
                    existingCustomer.setAccountEmail(customer.getAccountEmail());
                }
                if (customer.getAccountPhone() != null) {
                    existingCustomer.setAccountPhone(customer.getAccountPhone());
                }
                if (customer.getAccountTypeName() != null) {
                    existingCustomer.setAccountTypeName(customer.getAccountTypeName());
                }
                if (customer.getGenderName() != null) {
                    existingCustomer.setGenderName(customer.getGenderName());
                }
                if (customer.getIndustryName() != null) {
                    existingCustomer.setIndustryName(customer.getIndustryName());
                }
                if (customer.getOwnerEmployeeId() != null) {
                    existingCustomer.setOwnerEmployeeId(customer.getOwnerEmployeeId());
                }

                return existingCustomer;
            })
            .map(customerRepository::save);
    }

    /**
     * Get all the customers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Customer> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        return customerRepository.findAll(pageable);
    }

    /**
     * Get one customer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Customer> findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findById(id);
    }

    /**
     * Delete the customer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.deleteById(id);
    }
}
