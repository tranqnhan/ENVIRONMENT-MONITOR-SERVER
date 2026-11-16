package com.ems.environment_monitor_server.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ems.environment_monitor_server.entity.MonitorDevice;


@RepositoryRestResource
public interface MonitorDeviceRepository extends CrudRepository<MonitorDevice, Long> {
    Boolean existsByUsername(String username);
    Optional<MonitorDevice> findByUsername(String username);
   
}
    