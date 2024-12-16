package rmi.Emergency;

import com.example.rmi.Ambulance.Ambulance;
import com.example.rmi.DatabaseConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmergencyServiceImpl extends UnicastRemoteObject implements EmergencyService {

    private Connection connection;

    // Constructor that establishes the database connection
    public EmergencyServiceImpl() throws RemoteException {
        try {
            // Use the DatabaseConnection class to get a connection
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RemoteException("Failed to connect to database.", e);
        }
    }

    @Override
    public void addEmergencyRequest(EmergencyRequest request) throws RemoteException {
        String sql = "INSERT INTO emergency_requests (id, patient_name, position, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, request.getId());
            stmt.setString(2, request.getPatientName());
            stmt.setString(3, request.getPosition());
            stmt.setString(4, request.getDescription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RemoteException("Error adding emergency request to the database.", e);
        }
    }

    @Override
    public EmergencyRequest getEmergencyRequestById(int id) throws RemoteException {
        String sql = "SELECT * FROM emergency_requests WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String patientName = resultSet.getString("patient_name");
                String position = resultSet.getString("position");
                String description = resultSet.getString("description");
                return new EmergencyRequest(id, patientName, position, description);
            } else {
                return null; // No request found with the given ID
            }
        } catch (SQLException e) {
            throw new RemoteException("Error retrieving emergency request from the database.", e);
        }
    }

    @Override
    public void updateEmergencyRequest(EmergencyRequest request) throws RemoteException {
        String sql = "UPDATE emergency_requests SET patient_name = ?, position = ?, description = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, request.getPatientName());
            stmt.setString(2, request.getPosition());
            stmt.setString(3, request.getDescription());
            stmt.setInt(4, request.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RemoteException("Error updating emergency request in the database.", e);
        }
    }

    @Override
    public void deleteEmergencyRequest(int id) throws RemoteException {
        String sql = "DELETE FROM emergency_requests WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RemoteException("Error deleting emergency request from the database.", e);
        }
    }

    // New method to assign an ambulance to an emergency request
    public void assignAmbulance(int requestId, int ambulanceId) throws RemoteException {
        String sql = "UPDATE emergency_requests SET ambulance_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ambulanceId);  // Set the ambulance ID to assign
            stmt.setInt(2, requestId);     // Specify which emergency request to update
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Emergency request ID " + requestId + " has been assigned to ambulance ID " + ambulanceId);
            } else {
                System.out.println("No emergency request found with ID " + requestId);
            }
        } catch (SQLException e) {
            throw new RemoteException("Error assigning ambulance to emergency request.", e);
        }
    }

    @Override
    public void addAmbulance(Ambulance ambulance) throws RemoteException {
        // Implementation for adding an ambulance (if needed)
    }
}
