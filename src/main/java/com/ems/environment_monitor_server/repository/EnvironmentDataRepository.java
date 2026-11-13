package com.ems.environment_monitor_server.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ems.environment_monitor_server.entity.EnvironmentData;

@RepositoryRestResource
public interface EnvironmentDataRepository extends CrudRepository<EnvironmentData, Long> {
    List<EnvironmentData> findByMonitorDeviceUsername(String monitorDeviceUsername);
    List<EnvironmentData> deleteByMonitorDeviceUsername(String monitorDeviceUsername);
}
