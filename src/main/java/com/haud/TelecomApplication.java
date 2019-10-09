package com.haud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(basePackages = "com.haud.repository")
@EntityScan(basePackages = "com.haud.entity")
@EnableTransactionManagement
@SpringBootApplication
public class TelecomApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelecomApplication.class, args);
    }

}
