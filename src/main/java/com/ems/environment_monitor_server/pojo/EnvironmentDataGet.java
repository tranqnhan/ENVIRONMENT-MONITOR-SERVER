package com.ems.environment_monitor_server.pojo;

import java.time.Instant;

public class EnvironmentDataGet {
    private String monitorDeviceUsername;
    private Instant startDate;
    private Instant endDate;

    public EnvironmentDataGet() {
        
    }

    public String getMonitorDeviceUsername() {
        return monitorDeviceUsername;
    }

    public void setMonitorDeviceUsername(String monitorDeviceUsername) {
        this.monitorDeviceUsername = monitorDeviceUsername;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }
}
