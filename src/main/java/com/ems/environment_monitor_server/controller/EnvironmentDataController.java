package com.ems.environment_monitor_server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.environment_monitor_server.entity.EnvironmentData;
import com.ems.environment_monitor_server.repository.EnvironmentDataRepository;
import com.ems.environment_monitor_server.repository.MonitorDeviceRepository;
import com.ems.environment_monitor_server.request.EnvironmentDataPost;

@RestController
@RequestMapping("data")
class EnvironmentDataController {

    @Autowired
    EnvironmentDataRepository environmentDataRepository;

    @Autowired
    MonitorDeviceRepository monitorDeviceRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public EnvironmentDataController(EnvironmentDataRepository environmentDataRepository, MonitorDeviceRepository monitorDeviceRepository, PasswordEncoder passwordEncoder) {
        this.environmentDataRepository = environmentDataRepository;
        this.monitorDeviceRepository = monitorDeviceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<EnvironmentData>> getAll() {
        try {
            List<EnvironmentData> items = new ArrayList<>();

            environmentDataRepository.findAll().forEach(items::add);

            if (items.isEmpty())
                return ResponseEntity.noContent().build();
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<EnvironmentData> getById(@PathVariable("id") Long id) {
        Optional<EnvironmentData> existingItemOptional = environmentDataRepository.findById(id);

        if (existingItemOptional.isPresent()) {
            return ResponseEntity.ok(existingItemOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("add")
    public ResponseEntity<EnvironmentData> create(
        @RequestBody EnvironmentDataPost item
    ) {
        try {
            String monitorDeviceUsername = item.getMonitorDeviceUsername();
            if (
                monitorDeviceRepository.existsByUsername(monitorDeviceUsername) &&
                passwordEncoder.matches(
                    item.getMonitorDevicePassword(), 
                    monitorDeviceRepository.findByUsername(monitorDeviceUsername).getPassword()
                )
            ) {
                EnvironmentData data = new EnvironmentData();
                data.setCo2Level(item.getCo2Level());
                data.setHumidity(item.getHumidity());
                data.setTemperature(item.getTemperature());
                data.setMeasuredAt(item.getMeasuredAt());
                data.setMonitorDeviceUsername(monitorDeviceUsername);
                
                environmentDataRepository.save(data);
                return ResponseEntity.status(HttpStatus.CREATED).body(data);
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            environmentDataRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }
}