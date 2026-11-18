package com.ems.environment_monitor_server.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ems.environment_monitor_server.entity.EnvironmentDataHistory;

@RepositoryRestResource
public interface EnvironmentDataHistoryRepository extends CrudRepository<EnvironmentDataHistory, Long> {
    List<EnvironmentDataHistory> findByMonitorDevice_Username(String monitorDeviceUsername);
    List<EnvironmentDataHistory> findByMonitorDevice_UsernameAndMeasuredAtBetween(String monitorDeviceUsername, Instant startDate, Instant endDate);
    void deleteByMonitorDevice_Username(String monitorDeviceUsername);
}
