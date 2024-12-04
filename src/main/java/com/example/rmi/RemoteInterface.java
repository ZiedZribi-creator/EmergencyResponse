package com.example.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote {
    // Method to send emergency notifications
    void sendEmergencyNotification(String emergencyDetails) throws RemoteException;

    // Method to confirm mission reception (ambulance)
    void confirmMission(String missionDetails) throws RemoteException;

    // Method to send current position (ambulance)
    void sendPosition(String position) throws RemoteException;

    // Method for the Emergency Center to create an emergency
    void createEmergency(String emergencyDetails) throws RemoteException;
}
