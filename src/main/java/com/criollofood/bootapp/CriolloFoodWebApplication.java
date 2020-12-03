package com.criollofood.bootapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"com.criollofood"})
@EntityScan("com.criollofood")
public class CriolloFoodWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CriolloFoodWebApplication.class, args);
    }
}
