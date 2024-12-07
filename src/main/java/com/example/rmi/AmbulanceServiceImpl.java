package com.example.rmi;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AmbulanceServiceImpl extends UnicastRemoteObject implements AmbulanceService {
    private DatabaseConnection dbConnection;

    public AmbulanceServiceImpl() throws RemoteException {
        dbConnection = new DatabaseConnection();
    }

    @Override
    public void updateAmbulanceStatusAndLocation(String id, String status, String location) throws RemoteException {
        try (Connection connection = dbConnection.getConnection()) {
            String query = "REPLACE INTO ambulance_status (id, status, location) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            statement.setString(2, status);
            statement.setString(3, location);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageToEmergency(String message) throws RemoteException {
        try (Connection connection = dbConnection.getConnection()) {
            String query = "INSERT INTO emergency_messages (message) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, message);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void acceptEmergencyRequest(String requestId) throws RemoteException {
        try (Connection connection = dbConnection.getConnection()) {
            String query = "UPDATE emergency_requests SET status = 'Accepted' WHERE request_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, requestId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String receiveRequest() throws RemoteException {
        try (Connection connection = dbConnection.getConnection()) {
            String query = "SELECT request_id FROM emergency_requests WHERE status = 'Pending' LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("request_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}