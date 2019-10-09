package com.haud.api.impl.config;

import com.haud.api.impl.CustomerService;
import com.haud.api.impl.SimService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TelecomConfig {
    @Bean
    @Primary
    public CustomerService customerService() {
        return Mockito.mock(CustomerService.class);
    }

    @Bean
    @Primary
    public SimService simService() {
        return Mockito.mock(SimService.class);
    }
}
