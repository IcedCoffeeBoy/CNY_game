package com.dsta.CNYBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CnyBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CnyBackendApplication.class, args);
    }

}
