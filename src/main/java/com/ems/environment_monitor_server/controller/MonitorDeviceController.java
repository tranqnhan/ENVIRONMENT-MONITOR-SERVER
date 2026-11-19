package com.ems.environment_monitor_server.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.environment_monitor_server.entity.MonitorDevice;
import com.ems.environment_monitor_server.pojo.MonitorDeviceRegistration;
import com.ems.environment_monitor_server.repository.MonitorDeviceRepository;
import com.ems.environment_monitor_server.repository.UserRepository;



@RestController
@RequestMapping("device")
class MonitorDeviceController { 
    @Autowired 
    UserRepository userRepository;

    @Autowired 
    MonitorDeviceRepository monitorDeviceRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public MonitorDeviceController(UserRepository userRepository, MonitorDeviceRepository monitorDeviceRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.monitorDeviceRepository = monitorDeviceRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("register")
    public ResponseEntity<String> registerDevice(@RequestBody MonitorDeviceRegistration registration) {
        String serverAdminPassword = userRepository.findByUsername("admin").getPasswordHash();
        if (passwordEncoder.matches(registration.getServerAdminPassword(), serverAdminPassword)) {
            MonitorDevice device = new MonitorDevice();
            device.setPassword(passwordEncoder.encode(registration.getMonitorDevicePassword()));

            MonitorDevice savedMonitorDevice = monitorDeviceRepository.save(device);

            return ResponseEntity.ok(savedMonitorDevice.getUsername());
        } 

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("usernames")
    public ResponseEntity<List<String>> getAllDevices() {
        List<String> usernames = new ArrayList<>();

        monitorDeviceRepository.findAll().forEach(
            ((monitorDevice) -> {
                usernames.add(monitorDevice.getUsername());
            })
        );

        return ResponseEntity.ok(usernames);
    }
    
    
}