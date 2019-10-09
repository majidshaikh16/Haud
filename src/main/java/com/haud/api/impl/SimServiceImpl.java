package com.haud.api.impl;

import com.haud.entity.Sim;
import com.haud.repository.SimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author webwerks
 */
@Service
public class SimServiceImpl implements SimService {

    @Autowired
    private SimRepository simRepository;

    /**
     * Create a new sim entity
     *
     * @param sim
     * @return sim
     */
    @Override
    public Sim createSim(Sim sim) {
        return simRepository.save(sim);
    }

    /**
     * Check if the sim exists
     * @param simId
     * @return
     */
    @Override
    public boolean isSimExists(long simId) {
        return simRepository.existsById(simId);
    }
}
