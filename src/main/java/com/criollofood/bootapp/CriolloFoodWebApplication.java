package com.criollofood.bootapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.criollofood"})
@EntityScan("com.criollofood")
@EnableJpaRepositories(basePackages = {"com.criollofood.bootapp.repository"})
public class CriolloFoodWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CriolloFoodWebApplication.class, args);
    }

}
