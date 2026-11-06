package com.ems.environment_monitor_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(
            16,      // Salt length, bytes
            32,      // Hash length, bytes
            1,       // Parallelism
            19_456,  // Memory, KiB
            2        // Iterations
        );
    }
}