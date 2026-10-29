package com.ems.environment_monitor_server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ems.environment_monitor_server.entity.EnvironmentDataEntity;

//Remove @RepositoryRestResource below to disable auto REST api:
@RepositoryRestResource
public interface EnvironmentDataRepository extends CrudRepository<EnvironmentDataEntity, Long> {

}
