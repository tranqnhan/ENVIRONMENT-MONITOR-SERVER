package com.ems.environment_monitor_server.component;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ems.environment_monitor_server.repository.UserRepository;

@Component
public class AdminInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminUsername;
    private final String adminPassword;

    public AdminInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.bootstrap.serverAdminUsername}") String adminUsername,
            @Value("${app.bootstrap.serverAdminPassword}") String adminPassword) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (adminUsername.isBlank()) {
            throw new IllegalArgumentException(
                "Admin username must not be blank"
            );
        }

        if (adminPassword.isBlank()) {
            throw new IllegalArgumentException(
                "Admin password must not be blank"
            );
        }

        userRepository.upsertAdmin(
            adminUsername,
            passwordEncoder.encode(adminPassword),
            Instant.now()
        );
    }
}