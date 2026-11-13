package com.ems.environment_monitor_server.request;

import java.time.Instant;

public class EnvironmentDataPost {
    private int temperature;
    private float humidity;
    private int co2Level;
    private Instant measuredAt;
    private String monitorDeviceUsername;
    private String monitorDevicePassword;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
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

    public String getMonitorDevicePassword() {
        return monitorDevicePassword;
    }

    public void setMonitorDevicePassword(String monitorDevicePassword) {
        this.monitorDevicePassword = monitorDevicePassword;
    }
}
