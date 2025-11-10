package com.example.bgbg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BgbgApplication {

    public static void main(String[] args) {
        SpringApplication.run(BgbgApplication.class, args);
    }

}
