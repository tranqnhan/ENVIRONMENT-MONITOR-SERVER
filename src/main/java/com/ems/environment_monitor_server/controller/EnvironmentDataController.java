package com.ems.environment_monitor_server.controller;

import java.time.Duration;
import java.time.Instant;
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

import com.ems.environment_monitor_server.entity.CurrentEnvironmentData;
import com.ems.environment_monitor_server.entity.EnvironmentDataHistory;
import com.ems.environment_monitor_server.entity.MonitorDevice;
import com.ems.environment_monitor_server.repository.CurrentEnvironmentDataRepository;
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
    CurrentEnvironmentDataRepository currentEnvironmentDataRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public EnvironmentDataController(
        EnvironmentDataRepository environmentDataRepository, 
        MonitorDeviceRepository monitorDeviceRepository, 
        CurrentEnvironmentDataRepository currentEnvironmentDataRepository, 
        PasswordEncoder passwordEncoder
    ) {
        this.environmentDataRepository = environmentDataRepository;
        this.monitorDeviceRepository = monitorDeviceRepository;
        this.currentEnvironmentDataRepository = currentEnvironmentDataRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("{id}")
    public ResponseEntity<EnvironmentDataHistory> getById(@PathVariable("id") Long id) {
        Optional<EnvironmentDataHistory> existingItemOptional = environmentDataRepository.findById(id);

        if (existingItemOptional.isPresent()) {
            return ResponseEntity.ok(existingItemOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("current")
    public ResponseEntity<CurrentEnvironmentData> current (
        @RequestBody EnvironmentDataPost item
    ) {
        try {
            String monitorDeviceUsername = item.getMonitorDeviceUsername();
            Optional<MonitorDevice> monitorDeviceOp = monitorDeviceRepository.findByUsername(monitorDeviceUsername);
            if (!(monitorDeviceOp.isPresent() &&
                passwordEncoder.matches(
                    item.getMonitorDevicePassword(), 
                    monitorDeviceOp.get().getPassword()
                ))
            ) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            MonitorDevice monitorDevice = monitorDeviceOp.get();
            CurrentEnvironmentData currentEnvironmentData = currentEnvironmentDataRepository
                    .findByMonitorDevice_Id(monitorDevice.getId())
                    .orElseGet(() -> {
                        CurrentEnvironmentData nCurrentEnvironmentData = new CurrentEnvironmentData();
                        nCurrentEnvironmentData.setCo2LevelSum(0);
                        nCurrentEnvironmentData.setTemperatureSum(0);
                        nCurrentEnvironmentData.setHumiditySum(0);
                        nCurrentEnvironmentData.setNumAggregator(0);
                        nCurrentEnvironmentData.setMonitorDevice(monitorDevice);
                        nCurrentEnvironmentData.setLastHistoryUpdate(item.getMeasuredAt());
                        return nCurrentEnvironmentData;
                    });

            currentEnvironmentData.setTemperature(item.getTemperature());
            currentEnvironmentData.setHumidity(item.getHumidity());
            currentEnvironmentData.setCo2Level(item.getCo2Level());

            currentEnvironmentData.addTemperatureSum(item.getTemperature());
            currentEnvironmentData.addHumiditySum(item.getHumidity());
            currentEnvironmentData.addCo2LevelSum(item.getCo2Level());

            currentEnvironmentData.incrementNumAggregator();

            Instant lastHistoryUpdate = currentEnvironmentData.getLastHistoryUpdate();

            final int NUM_MINUTES_UNTIL_NEXT_SAVE = 15;

            if (!item.getMeasuredAt().isBefore(lastHistoryUpdate.plus(Duration.ofMinutes(NUM_MINUTES_UNTIL_NEXT_SAVE)))) {
                EnvironmentDataHistory environmentDataHistory = new EnvironmentDataHistory();

                float numberOfMeasurements = currentEnvironmentData.getNumAggregator();

                if (numberOfMeasurements > 0) {
                    environmentDataHistory.setCo2Level(currentEnvironmentData.getCo2LevelSum() / numberOfMeasurements);
                    environmentDataHistory.setHumidity(currentEnvironmentData.getHumiditySum() / numberOfMeasurements);
                    environmentDataHistory.setTemperature(currentEnvironmentData.getTemperatureSum() / numberOfMeasurements);
                    environmentDataHistory.setMeasuredAt(item.getMeasuredAt());
                    environmentDataHistory.setMonitorDevice(monitorDeviceOp.get());
                
                    environmentDataRepository.save(environmentDataHistory);
                }

                currentEnvironmentData.setCo2LevelSum(0);
                currentEnvironmentData.setTemperatureSum(0);
                currentEnvironmentData.setHumiditySum(0);
                currentEnvironmentData.setNumAggregator(0);
                currentEnvironmentData.setLastHistoryUpdate(item.getMeasuredAt());

            }
        
            currentEnvironmentDataRepository.save(currentEnvironmentData);

            return ResponseEntity.status(HttpStatus.CREATED).body(currentEnvironmentData);
            
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