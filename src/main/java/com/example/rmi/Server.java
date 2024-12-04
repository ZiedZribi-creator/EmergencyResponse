package com.example.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements RemoteInterface {

    protected Server() throws RemoteException {
        super();
    }

    @Override
    public void sendEmergencyNotification(String emergencyDetails) throws RemoteException {
        System.out.println("Emergency Notification: " + emergencyDetails);
    }

    @Override
    public void confirmMission(String missionDetails) throws RemoteException {
        System.out.println("Mission Confirmed: " + missionDetails);
    }

    @Override
    public void sendPosition(String position) throws RemoteException {
        System.out.println("Position Sent: " + position);
    }

    @Override
    public void createEmergency(String emergencyDetails) throws RemoteException {
        System.out.println("Creating Emergency: " + emergencyDetails);
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            java.rmi.Naming.rebind("EmergencyResponse", server);
            System.out.println("Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
