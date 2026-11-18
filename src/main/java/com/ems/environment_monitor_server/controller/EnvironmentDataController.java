package com.ems.environment_monitor_server.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import com.ems.environment_monitor_server.pojo.CurrentEnvironmentUpdate;
import com.ems.environment_monitor_server.pojo.EnvironmentDataGet;
import com.ems.environment_monitor_server.pojo.EnvironmentDataPost;
import com.ems.environment_monitor_server.repository.CurrentEnvironmentDataRepository;
import com.ems.environment_monitor_server.repository.EnvironmentDataHistoryRepository;
import com.ems.environment_monitor_server.repository.MonitorDeviceRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("data")
class EnvironmentDataController {

    @Autowired
    EnvironmentDataHistoryRepository environmentDataHistoryRepository;

    @Autowired
    MonitorDeviceRepository monitorDeviceRepository;

    @Autowired 
    CurrentEnvironmentDataRepository currentEnvironmentDataRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired 
    ApplicationEventPublisher events;

    public EnvironmentDataController(
        EnvironmentDataHistoryRepository environmentDataHistoryRepository, 
        MonitorDeviceRepository monitorDeviceRepository, 
        CurrentEnvironmentDataRepository currentEnvironmentDataRepository, 
        PasswordEncoder passwordEncoder,
        ApplicationEventPublisher events
    ) {
        this.environmentDataHistoryRepository = environmentDataHistoryRepository;
        this.monitorDeviceRepository = monitorDeviceRepository;
        this.currentEnvironmentDataRepository = currentEnvironmentDataRepository;
        this.passwordEncoder = passwordEncoder;
        this.events = events;
    }

    @GetMapping("range")
    public ResponseEntity<List<EnvironmentDataHistory>> getById(@RequestBody EnvironmentDataGet item) 
    {
        List<EnvironmentDataHistory> environmentDataHistory = environmentDataHistoryRepository.findByMonitorDevice_UsernameAndMeasuredAtBetween(
            item.getMonitorDeviceUsername(), 
            item.getStartDate(),
            item.getEndDate()
        );

        if (!environmentDataHistory.isEmpty()) {
            return ResponseEntity.ok(environmentDataHistory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    @PostMapping("add")
    public ResponseEntity<CurrentEnvironmentData> current(@RequestBody EnvironmentDataPost item) 
    {
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
                        nCurrentEnvironmentData.setNumberOfMeasurements(0);
                        nCurrentEnvironmentData.setMonitorDevice(monitorDevice);
                        nCurrentEnvironmentData.setLastHistoryUpdate(item.getMeasuredAt());
                        return nCurrentEnvironmentData;
                    });

            currentEnvironmentData.setTemperature(item.getTemperature());
            currentEnvironmentData.setHumidity(item.getHumidity());
            currentEnvironmentData.setCo2Level(item.getCo2Level());
            currentEnvironmentData.setLastMeasuredAt(item.getMeasuredAt());

            currentEnvironmentData.addTemperatureSum(item.getTemperature());
            currentEnvironmentData.addHumiditySum(item.getHumidity());
            currentEnvironmentData.addCo2LevelSum(item.getCo2Level());

            currentEnvironmentData.incrementNumberOfMeasurements();

            Instant lastHistoryUpdate = currentEnvironmentData.getLastHistoryUpdate();

            final int NUM_MINUTES_UNTIL_NEXT_SAVE = 15;

            if (!item.getMeasuredAt().isBefore(lastHistoryUpdate.plus(Duration.ofMinutes(NUM_MINUTES_UNTIL_NEXT_SAVE)))) {
                EnvironmentDataHistory environmentDataHistory = new EnvironmentDataHistory();

                float numberOfMeasurements = currentEnvironmentData.getNumberOfMeasurements();

                if (numberOfMeasurements > 0) {
                    environmentDataHistory.setAverageCo2Level(currentEnvironmentData.getCo2LevelSum() / numberOfMeasurements);
                    environmentDataHistory.setAverageHumidity(currentEnvironmentData.getHumiditySum() / numberOfMeasurements);
                    environmentDataHistory.setAverageTemperature(currentEnvironmentData.getTemperatureSum() / numberOfMeasurements);
                    environmentDataHistory.setMeasuredAt(item.getMeasuredAt());
                    environmentDataHistory.setNumberOfMeasurements((int)numberOfMeasurements);
                    environmentDataHistory.setMonitorDevice(monitorDeviceOp.get());
                
                    environmentDataHistoryRepository.save(environmentDataHistory);
                }

                currentEnvironmentData.setCo2LevelSum(0);
                currentEnvironmentData.setTemperatureSum(0);
                currentEnvironmentData.setHumiditySum(0);
                currentEnvironmentData.setNumberOfMeasurements(0);
                currentEnvironmentData.setLastHistoryUpdate(item.getMeasuredAt());

            }
        
        
            CurrentEnvironmentData saved = currentEnvironmentDataRepository.save(currentEnvironmentData);

            CurrentEnvironmentUpdate currentEnvironmentUpdate = new CurrentEnvironmentUpdate(
                saved.getTemperature(),
                saved.getHumidity(),
                saved.getCo2Level(),
                saved.getLastMeasuredAt(),
                monitorDeviceUsername
            );

            events.publishEvent(currentEnvironmentUpdate);

            return ResponseEntity.status(HttpStatus.CREATED).body(currentEnvironmentData);
            
        } catch (Exception e) {
            System.err.println("Failed to save environment data: " + e.toString());
            
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            environmentDataHistoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }
}