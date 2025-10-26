package rmi;

import com.example.rmi.Ambulance.AmbulanceService;
import com.example.rmi.Ambulance.AmbulanceServiceImpl;
import com.example.rmi.Emergency.EmergencyService;
import com.example.rmi.Emergency.EmergencyServiceImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {

    public static void main(String[] args) {
        try {
            // Create and start the RMI registry on port 1099
            LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry started on port 1099.");

            // Create an instance of the emergency service implementation
            EmergencyService emergencyService = new EmergencyServiceImpl();

            // Bind the emergency service to the RMI registry with a name
            Naming.rebind("rmi://localhost/EmergencyService", emergencyService);
            System.out.println("Emergency service registered in RMI registry under the name 'EmergencyService'.");

            // Create an instance of the ambulance service implementation
            AmbulanceService ambulanceService = new AmbulanceServiceImpl(1); // Example ambulance ID

            // Bind the ambulance service to the RMI registry with a name
            Naming.rebind("rmi://localhost/AmbulanceService", ambulanceService);
            System.out.println("Ambulance service registered in RMI registry under the name 'AmbulanceService'.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}