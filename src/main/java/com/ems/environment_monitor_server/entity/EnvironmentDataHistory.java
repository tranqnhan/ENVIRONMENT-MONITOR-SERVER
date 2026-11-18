package com.ems.environment_monitor_server.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class EnvironmentDataHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private float averageTemperature;

    @Column(nullable = false)
    private float averageHumidity;

    @Column(nullable = false)
    private float averageCo2Level;

    @Column(nullable = false)
    private Instant measuredAt;

    @Column(nullable = false)
    private int numberOfMeasurements;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "monitorDeviceId",
        nullable = false
    )
    private MonitorDevice monitorDevice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(float temperature) {
        this.averageTemperature = temperature;
    }

    public float getAverageHumidity() {
        return averageHumidity;
    }

    public void setAverageHumidity(float humidity) {
        this.averageHumidity = humidity;
    }

    public float getAverageCo2Level() {
        return averageCo2Level;
    }

    public void setAverageCo2Level(float co2Level) {
        this.averageCo2Level = co2Level;
    }

    public Instant getMeasuredAt() {
        return measuredAt;
    }

    public void setMeasuredAt(Instant measuredAt) {
        this.measuredAt = measuredAt;
    }

    public MonitorDevice getMonitorDevice() {
        return monitorDevice;
    }

    public void setMonitorDevice(MonitorDevice monitorDevice) {
        this.monitorDevice = monitorDevice;
    }

    public int getNumberOfMeasurements() {
        return numberOfMeasurements;
    }

    public void setNumberOfMeasurements(int numberOfMeasurements) {
        this.numberOfMeasurements = numberOfMeasurements;
    }
}