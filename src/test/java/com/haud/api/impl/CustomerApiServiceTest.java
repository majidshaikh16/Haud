package com.haud.api.impl;

import com.haud.TelecomApplication;
import com.haud.entity.Customer;
import com.haud.entity.Sim;
import com.haud.exception.ClientException;
import com.haud.repository.CustomerRepository;
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

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TelecomApplication.class)
@ActiveProfiles("test")
public class CustomerApiServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SimRepository simRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void createCustomerSuccess() {
        Customer customer = Customer.builder()
                .id(1L)
                .name("Majid")
                .build();
        Mockito.when(customerRepository.save(Mockito.any(Customer.class)))
                .thenReturn(customer);

        Customer reponse = customerService.createCustomer(customer);
        assertEquals(Long.valueOf(1), reponse.getId());
    }

    @Test
    public void linkSimSuccess() {
        Mockito.when(customerRepository.existsByIdAndSimsId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(Boolean.FALSE);

        Mockito.when(customerRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(Customer.builder()
                        .id(1L)
                        .name("Majid")
                        .sims(new ArrayList<>())
                        .build()));
        Mockito.when(simRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(Sim.builder()
                        .id(1L)
                        .name("Sim1")
                        .build()));

        Customer reponse = customerService.linkSim(Mockito.anyLong(), Mockito.anyLong());
        assertEquals("Majid", reponse.getName());
        assertEquals("Sim1", reponse.getSims().get(0).getName());


    }

    @Test(expected = ClientException.class)
    public void linkSimThrowErrorSimAlreadyAssigned() {
        Mockito.when(customerRepository.existsByIdAndSimsId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(Boolean.TRUE);

        customerService.linkSim(Mockito.anyLong(), Mockito.anyLong());


    }

    @Test
    public void getCustomerSuccess() {
        Mockito.when(customerRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(Customer.builder()
                        .id(1L)
                        .name("Majid")
                        .sims(new ArrayList<>())
                        .build()));
        Customer response = customerService.getCustomer(Mockito.anyLong());
        assertEquals("Majid", response.getName());
        assertEquals(Long.valueOf(1), response.getId());
    }
}
