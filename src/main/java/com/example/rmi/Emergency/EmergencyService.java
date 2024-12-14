package com.example.rmi.Emergency;

import com.example.rmi.Ambulance.Ambulance;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EmergencyService extends Remote {
    void addAmbulance(Ambulance ambulance) throws RemoteException;

    void addEmergencyRequest(EmergencyRequest request) throws RemoteException;

    String getMessageFromAmbulance(String ambulanceId) throws RemoteException;

    void sendMessageToAmbulance(String ambulanceId, String message) throws RemoteException;
}
