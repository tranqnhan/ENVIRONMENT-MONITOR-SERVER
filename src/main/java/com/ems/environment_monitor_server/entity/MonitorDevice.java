package com.ems.environment_monitor_server.entity;

import java.util.Locale;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;

@Entity
public class MonitorDevice {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "monitorDeviceIdGenerator"
    )
    @SequenceGenerator(
        name = "monitorDeviceIdGenerator",
        sequenceName = "monitorDeviceIdSeq",
        initialValue = 1,
        allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @PrePersist
    private void assignUsername() {
        if (id == null) {
            throw new IllegalStateException(
                "ID must be generated before assigning the username"
            );
        }

        this.username = String.format(Locale.ROOT, "EMS.%04d", id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
