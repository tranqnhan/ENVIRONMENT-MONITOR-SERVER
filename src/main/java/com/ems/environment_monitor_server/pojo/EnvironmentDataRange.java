package com.ems.environment_monitor_server.pojo;

import java.time.Instant;

public class EnvironmentDataRange {
    private String monitorDeviceUsername;
    private Instant startTime;
    private Instant endTime;

    public EnvironmentDataRange() {
        
    }

    public String getMonitorDeviceUsername() {
        return monitorDeviceUsername;
    }

    public void setMonitorDeviceUsername(String monitorDeviceUsername) {
        this.monitorDeviceUsername = monitorDeviceUsername;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }
}
