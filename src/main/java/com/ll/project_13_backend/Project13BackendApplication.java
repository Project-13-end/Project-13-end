package com.ll.project_13_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class Project13BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(Project13BackendApplication.class, args);
    }

}
