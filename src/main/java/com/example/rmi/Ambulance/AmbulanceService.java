package com.example.rmi.Ambulance;

import com.example.rmi.Emergency.EmergencyRequest;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AmbulanceService extends Remote {
    void updateStatus(String ambulanceId, String status, String position, boolean available) throws RemoteException;
    void receiveEmergencyRequest(EmergencyRequest request) throws RemoteException;
    void sendMessage(String message) throws RemoteException;
    String receiveMessage() throws RemoteException;
}