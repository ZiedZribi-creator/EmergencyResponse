package rmi.Emergency;

import com.example.rmi.Ambulance.Ambulance;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EmergencyService extends Remote {
    void addEmergencyRequest(EmergencyRequest request) throws RemoteException;
    EmergencyRequest getEmergencyRequestById(int id) throws RemoteException;
    void updateEmergencyRequest(EmergencyRequest request) throws RemoteException;
    void deleteEmergencyRequest(int id) throws RemoteException;
    void addAmbulance(Ambulance ambulance) throws RemoteException;
    // The new method to add an ambulance
}
