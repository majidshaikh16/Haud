package com.haud.api.impl;

import com.haud.entity.Customer;

/**
 * @author webwerks
 */
public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer linkSim(long custId,long simId);
    Customer getCustomer(long custId);
    boolean isCustomerExists(long custId);
}
