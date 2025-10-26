package rmi.Ambulance;

import com.example.rmi.DatabaseConnection;
import com.example.rmi.Emergency.EmergencyRequest;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AmbulanceServiceImpl extends UnicastRemoteObject implements AmbulanceService {

    private int ambulanceId;
    private boolean available;
    private Connection connection;

    public AmbulanceServiceImpl(int ambulanceId) throws RemoteException {
        super();
        this.ambulanceId = ambulanceId;
        this.available = true;  // Default to available
        connectToDatabase();  // Establish database connection
    }

    // Method to establish a connection to the database
    private void connectToDatabase() {
        try {
            connection = DatabaseConnection.getConnection();  // Use the static method from DatabaseConnection class
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    @Override
    public EmergencyRequest getNextEmergencyRequest() throws RemoteException {
        try {
            // Retrieve the first pending request that hasn't been accepted
            String query = "SELECT * FROM emergency_requests WHERE status = 'PENDING' LIMIT 1";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                // Create and return an EmergencyRequest object from the result set
                EmergencyRequest request = new EmergencyRequest(
                        rs.getInt("id"),  // Add ID field
                        rs.getString("description"),
                        rs.getString("position"),
                        rs.getString("status")
                );
                return request;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving emergency request: " + e.getMessage());
        }

        return null;  // Return null if no request is found
    }


    @Override
    public void updateStatus(int ambulanceId, String status, String position, boolean available) throws RemoteException {
        try {
            // Update the ambulance status in the database
            String query = "UPDATE ambulances SET status = ?, position = ?, available = ? WHERE ambulance_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, status);
            pstmt.setString(2, position);
            pstmt.setBoolean(3, available);
            pstmt.setInt(4, ambulanceId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating ambulance status: " + e.getMessage());
        }
    }

    @Override
    public void receiveEmergencyRequest(EmergencyRequest request) throws RemoteException {
        try {
            // Insert the emergency request into the database
            String query = "INSERT INTO emergency_requests (description, position, status) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, request.getDescription());
            pstmt.setString(2, request.getPosition());
            pstmt.setString(3, "PENDING");  // Set status to PENDING
            pstmt.executeUpdate();
            System.out.println("Emergency request received: " + request.getDescription());
        } catch (SQLException e) {
            System.out.println("Error receiving emergency request: " + e.getMessage());
        }
    }

    @Override
    public void acceptEmergencyRequest(int ambulanceId) throws RemoteException {
        try {
            // Fetch the next pending emergency request for this ambulance
            String query = "SELECT id FROM emergency_requests WHERE status = 'PENDING' AND ambulance_id IS NULL LIMIT 1";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int requestId = rs.getInt("id"); // Get the request ID from the result set

                // Update the request status to 'ACCEPTED' and assign the ambulance ID
                String updateQuery = "UPDATE emergency_requests SET status = 'ACCEPTED', ambulance_id = ? WHERE id = ?";
                PreparedStatement updatePstmt = connection.prepareStatement(updateQuery);
                updatePstmt.setInt(1, ambulanceId); // Set the ambulance ID
                updatePstmt.setInt(2, requestId); // Set the request ID

                // Execute the update for the emergency request
                updatePstmt.executeUpdate();

                // Now mark the ambulance as unavailable
                String updateAmbulanceQuery = "UPDATE ambulances SET available = ? WHERE ambulance_id = ?";
                PreparedStatement updateAmbulancePstmt = connection.prepareStatement(updateAmbulanceQuery);
                updateAmbulancePstmt.setBoolean(1, false); // Set available to false
                updateAmbulancePstmt.setInt(2, ambulanceId); // Set the ambulance ID

                // Execute the update for the ambulance
                updateAmbulancePstmt.executeUpdate();

                System.out.println("Emergency request accepted: ID " + requestId + " with ambulance ID " + ambulanceId);
            } else {
                System.out.println("No pending emergency requests available for this ambulance.");
            }
        } catch (SQLException e) {
            System.out.println("Error accepting emergency request: " + e.getMessage());
        }
    }

    @Override
    public void sendMessage(String message) throws RemoteException {

    }


    @Override
    public void rejectRequest(Integer ambulanceId) throws RemoteException {
        try {
            // Reject the current emergency request and mark it as "REJECTED"
            String query = "UPDATE emergency_requests SET status = 'REJECTED' WHERE status = 'PENDING' LIMIT 1";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.executeUpdate();
            System.out.println("Emergency request rejected for Ambulance: " + ambulanceId);
        } catch (SQLException e) {
            System.out.println("Error rejecting emergency request: " + e.getMessage());
        }
    }

    @Override
    public String receiveMessage() throws RemoteException {
        // Logic for receiving messages, for example:
        return "Message received.";
    }

    public List<EmergencyRequest> getAllEmergencyRequests() throws RemoteException {
        List<EmergencyRequest> requests = new ArrayList<>();
        try {
            // Retrieve all pending requests
            String query = "SELECT * FROM emergency_requests WHERE status = 'PENDING'";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                // Create an EmergencyRequest object for each row
                EmergencyRequest request = new EmergencyRequest(
                        rs.getInt("id"),  // Add ID field
                        rs.getString("description"),
                        rs.getString("position"),
                        rs.getString("status")
                );
                requests.add(request);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving emergency requests: " + e.getMessage());
        }

        return requests;
    }

}
