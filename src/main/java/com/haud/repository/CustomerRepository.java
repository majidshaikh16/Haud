package com.haud.repository;

import com.haud.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    boolean existsByIdAndSimsId(long custId, long simId);
}
