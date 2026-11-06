package com.ems.environment_monitor_server.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.environment_monitor_server.repository.UserRepository;

@RestController
@RequestMapping("server")
class ServerController {
    @Autowired 
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;
   
    public ServerController(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("auth")
    public ResponseEntity<String> authenticate(@RequestBody String password) {

        String passwordHash = repository.findPasswordHashByUsername("admin");

        if (passwordEncoder.matches(password, passwordHash)) {
            return ResponseEntity.ok("Success");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
