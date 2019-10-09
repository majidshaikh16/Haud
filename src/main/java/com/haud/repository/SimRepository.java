package com.haud.repository;

import com.haud.entity.Sim;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimRepository extends CrudRepository<Sim, Long> {
}
