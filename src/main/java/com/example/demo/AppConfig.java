package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    CustomCommandLineRunner customCommandLineRunner() {
        return new CustomCommandLineRunner();
    }

}
