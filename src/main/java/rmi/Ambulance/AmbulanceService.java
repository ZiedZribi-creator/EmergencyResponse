package rmi.Ambulance;

import com.example.rmi.Emergency.EmergencyRequest;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AmbulanceService extends Remote {

    // Method to update ambulance status (position, availability, etc.)
    void updateStatus(int ambulanceId, String status, String position, boolean available) throws RemoteException;

    // Method to receive an emergency request (this will send a request to the ambulance system)
    void receiveEmergencyRequest(EmergencyRequest request) throws RemoteException;

    // Method to accept an emergency request
    void acceptEmergencyRequest(int ambulanceId) throws RemoteException; // Accepts ambulance ID and request ID

    // Method to send a message (could be for communication with the ambulance)
    void sendMessage(String message) throws RemoteException;

    // New method to reject an emergency request
    void rejectRequest(Integer ambulanceId) throws RemoteException;

    // Method to receive a message (could be for communication with the server or other ambulances)
    String receiveMessage() throws RemoteException;
    EmergencyRequest getNextEmergencyRequest() throws RemoteException;
     List<EmergencyRequest> getAllEmergencyRequests() throws RemoteException ;

}
