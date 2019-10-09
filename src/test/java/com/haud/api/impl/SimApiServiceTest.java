package com.haud.api.impl;

import com.haud.TelecomApplication;
import com.haud.entity.Sim;
import com.haud.repository.SimRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TelecomApplication.class)
@ActiveProfiles("test")
public class SimApiServiceTest {
    @Mock
    private SimRepository simRepository;

    @InjectMocks
    private SimServiceImpl simService;

    @Test
    public void createSimSuccess() {
        Sim sim = Sim.builder()
                .id(1L)
                .name("Sim1")
                .build();
        Mockito.when(simRepository.save(Mockito.any(Sim.class)))
                .thenReturn(sim);
        Sim response = simService.createSim(sim);
        assertEquals("Sim1", response.getName());
        assertEquals(Long.valueOf(1), response.getId());
    }

    @Test
    public void simExits() {
        Mockito.when(simRepository.existsById(Mockito.anyLong()))
                .thenReturn(Boolean.TRUE);
        boolean simExists = simService.isSimExists(Mockito.anyLong());
        assertEquals(Boolean.TRUE, simExists);
    }
}
