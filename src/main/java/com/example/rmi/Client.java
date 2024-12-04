package com.example.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class Client {

    public static void main(String[] args) {
        try {
            RemoteInterface server = (RemoteInterface) Naming.lookup("rmi://localhost/EmergencyResponse");
            
            // Simulating sending emergency notification
            server.sendEmergencyNotification("Fire in the building!");

            // Simulating sending position (ambulance)
            server.sendPosition("Ambulance at location X");

            // Simulating confirming the mission
            server.confirmMission("Mission received, proceeding to the location.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
