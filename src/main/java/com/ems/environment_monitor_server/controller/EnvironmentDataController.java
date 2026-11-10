package com.ems.environment_monitor_server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

/* Move to EnvironmentDeviceController
    @GetMapping("{deviceName}")
    public  ResponseEntity<List<EnvironmentDataEntity>> getAllByDeviceName(@PathVariable("deviceName") String deviceName) {
        try {
            List<EnvironmentDataEntity> items = repository.findByDeviceName(deviceName);
            if (items.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  
        }
    }
*/
    @PostMapping("add")
    public ResponseEntity<EnvironmentData> create(
        Authentication auth, 
        @RequestBody EnvironmentData item
    ) {
        try {
            UsernamePasswordAuthenticationToken upAuth = (UsernamePasswordAuthenticationToken) auth;

            if (
                monitorDeviceRepository.existsByUsername(upAuth.getName()) &&
                passwordEncoder.
            
            ) {

            }
            
            item.setMonitorDeviceUsername(upAuth.getName());
            EnvironmentData savedItem = environmentDataRepository.save(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    // @PutMapping("{id}")
    // public ResponseEntity<EnvironmentDataEntity> update(@PathVariable("id") Long id, @RequestBody EnvironmentDataEntity item) {
    //     Optional<EnvironmentDataEntity> existingItemOptional = repository.findById(id);
    //     if (existingItemOptional.isPresent()) {
    //         EnvironmentDataEntity existingItem = existingItemOptional.get();
    //         System.out.println("TODO for developer - update logic is unique to entity and must be implemented manually.");
    //         //existingItem.setSomeField(item.getSomeField());
    //         return new ResponseEntity<>(repository.save(existingItem), HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            environmentDataRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

/* Move to EnvironmentDeviceController
    @DeleteMapping("delete/device/{deviceName}")
    public ResponseEntity<HttpStatus> deleteDevice(@PathVariable("deviceName") String deviceName) {
        try {
            repository.deleteByDeviceName(deviceName);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }
*/
}