package com.ems.environment_monitor_server.pojo;

import java.time.Instant;


public class CurrentEnvironmentUpdateGet {
    private float temperature;
    private float humidity;
    private int co2Level;
    private Instant measuredAt;

    public CurrentEnvironmentUpdateGet(CurrentEnvironmentUpdate update) {
        this.temperature = update.getTemperature();
        this.humidity = update.getHumidity();
        this.co2Level = update.getCo2Level();
        this.measuredAt = update.getMeasuredAt();
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
}
