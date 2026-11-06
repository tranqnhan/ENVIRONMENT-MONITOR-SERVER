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
import com.ems.environment_monitor_server.request.AuthRequest;

@RestController
@RequestMapping("server")
class ServerController {
    @Autowired 
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;
   
    public ServerController(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("auth")
    public ResponseEntity<Void> authenticate(@RequestBody AuthRequest auth) {
        String passwordHash = repository.findByUsername("admin").getPasswordHash();
        
        if (passwordEncoder.matches(auth.getPassword(), passwordHash)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
