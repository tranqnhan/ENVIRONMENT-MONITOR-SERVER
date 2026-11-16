package com.ems.environment_monitor_server.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


@Entity
public class CurrentEnvironmentData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private float temperature;

    @Column(nullable = false)
    private float humidity;

    @Column(nullable = false)
    private int co2Level;

    @Column(nullable = false)
    private Instant lastMeasuredAt;

    @Column(nullable = false)
    private Instant lastHistoryUpdate;

    @Column(nullable = false)
    private float temperatureSum;
    
    @Column(nullable = false)
    private float humiditySum;
    
    @Column(nullable = false)
    private int co2LevelSum;

    @Column(nullable = false)
    private int numAggregator;
    

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "monitorDeviceId",
        nullable = false,
        unique = true,
        updatable = false
    )
    private MonitorDevice monitorDevice;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public MonitorDevice getMonitorDevice() {
        return monitorDevice;
    }

    public void setMonitorDevice(MonitorDevice monitorDevice) {
        this.monitorDevice = monitorDevice;
    }

    public Instant getLastHistoryUpdate() {
        return lastHistoryUpdate;
    }

    public void setLastHistoryUpdate(Instant lastHistoryUpdate) {
        this.lastHistoryUpdate = lastHistoryUpdate;
    }

    public float getTemperatureSum() {
        return temperatureSum;
    }

    public void setTemperatureSum(float temperatureSum) {
        this.temperatureSum = temperatureSum;
    }

    public void addTemperatureSum(float temperature) {
        this.temperatureSum += temperature;
    }

    public float getHumiditySum() {
        return humiditySum;
    }

    public void setHumiditySum(float humiditySum) {
        this.humiditySum = humiditySum;
    }

    public void addHumiditySum(float humiditySum) {
        this.humiditySum += humiditySum;
    }

    public int getCo2LevelSum() {
        return co2LevelSum;
    }

    public void setCo2LevelSum(int co2LevelSum) {
        this.co2LevelSum = co2LevelSum;
    }

    public void addCo2LevelSum(int co2LevelSum) {
        this.co2LevelSum += co2LevelSum;
    }

    public int getNumAggregator() {
        return numAggregator;
    }

    public void setNumAggregator(int numAggregator) {
        this.numAggregator = numAggregator;
    }

    public void incrementNumAggregator() {
        numAggregator++;
    }

    public Instant getLastMeasuredAt() {
        return lastMeasuredAt;
    }

    public void setLastMeasuredAt(Instant lastMeasuredAt) {
        this.lastMeasuredAt = lastMeasuredAt;
    }

}