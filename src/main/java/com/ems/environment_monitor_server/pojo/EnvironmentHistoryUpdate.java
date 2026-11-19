package com.ems.environment_monitor_server.pojo;

import java.time.Instant;

public class EnvironmentHistoryUpdate {
    private float temperature;
    private float humidity;
    private int co2Level;
    private Instant measuredAt;
    private String monitorDeviceUsername;

    public EnvironmentHistoryUpdate(
        float temperature,
        float humidity,
        int co2Level,
        Instant measuredAt,
        String monitorDeviceUsername
    ) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.co2Level = co2Level;
        this.measuredAt = measuredAt;
        this.monitorDeviceUsername = monitorDeviceUsername;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public int getCo2Level() {
        return co2Level;
    }

    public void setCo2Level(int co2Level) {
        this.co2Level = co2Level;
    }

    public Instant getMeasuredAt() {
        return measuredAt;
    }

    public void setMeasuredAt(Instant measuredAt) {
        this.measuredAt = measuredAt;
    }

    public String getMonitorDeviceUsername() {
        return monitorDeviceUsername;
    }

    public void setMonitorDeviceUsername(String monitorDeviceUsername) {
        this.monitorDeviceUsername = monitorDeviceUsername;
    }

}
