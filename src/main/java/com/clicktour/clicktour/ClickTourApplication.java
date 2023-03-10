package com.clicktour.clicktour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ClickTourApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClickTourApplication.class, args);
    }

}
