package com.haud.api.impl;

import com.haud.entity.Sim;

/**
 * @author webwerks
 */
public interface SimService {
    Sim createSim(Sim sim);

    boolean isSimExists(long simId);
}
