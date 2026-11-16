package com.ems.environment_monitor_server.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ems.environment_monitor_server.entity.CurrentEnvironmentData;

@RepositoryRestResource
public interface CurrentEnvironmentDataRepository extends CrudRepository<CurrentEnvironmentData, Long> {
    Optional<CurrentEnvironmentData> findByMonitorDevice_Id(Long deviceId);
    Optional<CurrentEnvironmentData> findByMonitorDevice_Username(String username);
}
