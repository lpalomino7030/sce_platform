package com.sce.platform;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
public class ScePlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScePlatformApplication.class, args);
    }

}
