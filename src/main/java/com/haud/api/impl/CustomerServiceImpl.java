package com.haud.api.impl;

import com.haud.entity.Customer;
import com.haud.exception.ClientException;
import com.haud.repository.CustomerRepository;
import com.haud.repository.SimRepository;
import com.haud.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author webwerks
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SimRepository simRepository;

    /**
     * Save a new customer
     *
     * @param customer
     * @return
     */
    @Transactional
    @Override
    public Customer createCustomer(Customer customer) {
        customerRepository.save(customer);
        return customer;
    }

    /**
     * Set the sim mapping to the customer entity
     *
     * @param custId
     * @param simId
     * @return
     */
    @Transactional
    @Override
    public Customer linkSim(long custId, long simId) {
        if (!isSimAlreadyMapped(custId, simId)) {
            Customer customer = customerRepository.findById(custId).get();
            customer.getSims().add(simRepository.findById(simId).get());
            return customer;
        }
        Errors errors = new Errors();
        errors.addError(Errors.Error.builder().title("Sim Already Mapped").detail("Sim already assigned to the customer").build());
        throw new ClientException(HttpStatus.BAD_REQUEST, errors);

    }

    /**
     * Fetch a specific customer
     *
     * @param custId
     * @return
     */
    @Override
    public Customer getCustomer(long custId) {
        return customerRepository.findById(custId).get();
    }

    /**
     * Check is the given customer id exists
     *
     * @param custId
     * @return
     */
    @Override
    public boolean isCustomerExists(long custId) {
        return customerRepository.existsById(custId);
    }

    /**
     * Verify is the sim is already mapped to the customer
     *
     * @param custId
     * @param simId
     * @return
     */
    private boolean isSimAlreadyMapped(long custId, long simId) {
        return customerRepository.existsByIdAndSimsId(custId, simId);
    }


}
