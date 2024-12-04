package com.example.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements RemoteInterface {

    public Server() {}

    @Override
    public String sayHello(String name) throws RemoteException {
        return "Hello, " + name + "!";
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            RemoteInterface stub = (RemoteInterface) UnicastRemoteObject.exportObject(server, 0);

            // Bind the remote object to the registry
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("HelloService", stub);

            System.out.println("Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
