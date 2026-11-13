package com.ems.environment_monitor_server.request;

public class MonitorDeviceRegistration {
    private String serverAdminPassword;
    private String monitorDeviceUsername;
    private String monitorDevicePassword;

    public String getServerAdminPassword() {
        return serverAdminPassword;
    }

    public void setServerAdminPassword(String databasePassword) {
        this.serverAdminPassword = databasePassword;
    }

    public String getMonitorDeviceUsername() {
        return monitorDeviceUsername;
    }

    public void setMonitorDeviceUsername(String deviceUsername) {
        this.monitorDeviceUsername = deviceUsername;
    }

    public String getMonitorDevicePassword() {
        return monitorDevicePassword;
    }

    public void setMonitorDevicePassword(String devicePassword) {
        this.monitorDevicePassword = devicePassword;
    }
}