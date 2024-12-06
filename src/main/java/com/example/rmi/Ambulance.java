package com.example.rmi;

import java.io.Serializable;

public class Ambulance implements Serializable {
    private static final long serialVersionUID = 1L; // For serialization
    private String id;          // Unique identifier for the ambulance
    private String status;      // Status of the ambulance (e.g., "available", "en route", "busy", "broken")
    private String location;    // Current location of the ambulance
    private String driverName;  // Name of the driver (optional)
    private String vehicleType;  // Type of the ambulance (e.g., "Type A", "Type B")

    // Constructor
    public Ambulance(String id, String status, String location, String driverName, String vehicleType) {
        this.id = id;
        this.status = status;
        this.location = location;
        this.driverName = driverName;
        this.vehicleType = vehicleType;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    // Method to display ambulance information
    @Override
    public String toString() {
        return "Ambulance{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", location='" + location + '\'' +
                ", driverName='" + driverName + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                '}';
    }
}