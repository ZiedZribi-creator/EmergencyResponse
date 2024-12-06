package com.example.rmi;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AmbulanceServer {
    public static void main(String[] args) {
        try {
            AmbulanceService service = new AmbulanceServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("AmbulanceService", service);
            System.out.println("Ambulance Service is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}