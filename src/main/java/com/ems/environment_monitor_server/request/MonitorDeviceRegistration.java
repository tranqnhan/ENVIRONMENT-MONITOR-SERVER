package com.ems.environment_monitor_server.request;

public class MonitorDeviceRegistration {
    private String serverAdminPassword;
    private String deviceUsername;
    private String devicePassword;

    public String getServerAdminPassword() {
        return serverAdminPassword;
    }

    public void setServerAdminPassword(String databasePassword) {
        this.serverAdminPassword = databasePassword;
    }

    public String getDeviceUsername() {
        return deviceUsername;
    }

    public void setDeviceUsername(String deviceUsername) {
        this.deviceUsername = deviceUsername;
    }

    public String getDevicePassword() {
        return devicePassword;
    }

    public void setDevicePassword(String devicePassword) {
        this.devicePassword = devicePassword;
    }
}