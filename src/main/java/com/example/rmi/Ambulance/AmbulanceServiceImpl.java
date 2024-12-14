package com.example.rmi.Ambulance;

import com.example.rmi.Emergency.EmergencyRequest;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class AmbulanceServiceImpl extends UnicastRemoteObject implements AmbulanceService {
    private String ambulanceId;
    private String status;
    private String position;
    private boolean available;
    private List<String> messages;
    private List<EmergencyRequest> emergencyRequests;

    public AmbulanceServiceImpl(String ambulanceId) throws RemoteException {
        this.ambulanceId = ambulanceId;
        this.messages = new ArrayList<>();
        this.emergencyRequests = new ArrayList<>();
    }

    @Override
    public void updateStatus(String ambulanceId, String status, String position, boolean available) throws RemoteException {
        this.status = status;
        this.position = position;
        this.available = available;
        System.out.println("Ambulance " + ambulanceId + " updated: Status=" + status + ", Position=" + position + ", Available=" + available);
    }

    @Override
    public void receiveEmergencyRequest(EmergencyRequest request) throws RemoteException {
        emergencyRequests.add(request);
        System.out.println("Received emergency request: " + request.getDescription());
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        messages.add(message);
        System.out.println("Message sent: " + message);
    }

    @Override
    public String receiveMessage() throws RemoteException {
        if (!messages.isEmpty()) {
            return messages.remove(0); // Return and remove the first message
        }
        return "No new messages.";
    }
}