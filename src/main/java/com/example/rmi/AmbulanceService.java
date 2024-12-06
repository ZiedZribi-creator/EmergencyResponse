package com.example.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AmbulanceService extends Remote {
    void updateAmbulanceStatusAndLocation(String id, String status, String location) throws RemoteException;
    void sendMessageToEmergency(String message) throws RemoteException;
    void acceptEmergencyRequest(String requestId) throws RemoteException;
    String receiveRequest() throws RemoteException; // To receive requests from the server
}