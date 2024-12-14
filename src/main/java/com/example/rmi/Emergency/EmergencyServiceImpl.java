package com.example.rmi.Emergency;

import com.example.rmi.Ambulance.Ambulance;
import com.example.rmi.DatabaseConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmergencyServiceImpl extends UnicastRemoteObject implements EmergencyService {

    public EmergencyServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized void addAmbulance(Ambulance ambulance) throws RemoteException {
        String query = "INSERT INTO ambulances (id, status, position, available) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, ambulance.getId());
            statement.setString(2, ambulance.getStatus());
            statement.setString(3, ambulance.getPosition());
            statement.setBoolean(4, ambulance.isAvailable());
            statement.executeUpdate();

            System.out.println("Ambulance ajoutée à la base de données : " + ambulance.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Erreur lors de l'ajout de l'ambulance : " + e.getMessage());
        }
    }

    public synchronized void addEmergencyRequest(EmergencyRequest request) throws RemoteException {
        String query = "INSERT INTO emergency_requests (id, patient_name, patient_position, description, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, request.getId());
            statement.setString(2, request.getPatientName());
            statement.setString(3, request.getPosition());
            statement.setString(4, request.getDescription());
            statement.setString(5, request.getStatus());
            statement.executeUpdate();

            System.out.println("Requête d'urgence ajoutée à la base de données : " + request.getId());

            // Traitement pour assigner une ambulance
            Ambulance assignedAmbulance = assignAmbulance(request.getPosition());
            if (assignedAmbulance != null) {
                request.setAssignedAmbulance(assignedAmbulance.getId());
                String message = "Nouvelle urgence : " + request.getDescription() + " à la position " + request.getPosition();
                sendMessageToAmbulance(assignedAmbulance.getId(), message);
                System.out.println("Urgence assignée à l'ambulance " + assignedAmbulance.getId());
            } else {
                System.out.println("Aucune ambulance disponible pour cette requête.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Erreur lors de l'ajout de la requête d'urgence : " + e.getMessage());
        }
    }

    @Override
    public String getMessageFromAmbulance(String ambulanceId) throws RemoteException {
        String message = null;
        String query = "SELECT * FROM messages WHERE ambulance_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, ambulanceId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                message = resultSet.getString("message");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return message != null ? message : "Aucun message trouvé pour cette ambulance.";
    }

    @Override
    public void sendMessageToAmbulance(String ambulanceId, String message) throws RemoteException {
        String query = "INSERT INTO messages (ambulance_id, message) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, ambulanceId);
            statement.setString(2, message);
            statement.executeUpdate();

            System.out.println("Message envoyé à l'ambulance " + ambulanceId + ": " + message);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Erreur lors de l'envoi du message à l'ambulance : " + e.getMessage());
        }
    }

    // Méthode privée pour attribuer une ambulance disponible
    private Ambulance assignAmbulance(String position) {
        Ambulance assignedAmbulance = null;
        String query = "SELECT * FROM ambulance WHERE position = ? AND available = true LIMIT 1";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, position);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                assignedAmbulance = new Ambulance(
                        resultSet.getString("id"),
                        resultSet.getString("status"),
                        resultSet.getString("position"),
                        resultSet.getBoolean("available")
                );

                updateAmbulanceStatus(assignedAmbulance.getId(), "Occupée");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assignedAmbulance;
    }

    private void updateAmbulanceStatus(String ambulanceId, String status) {
        String query = "UPDATE ambulance SET status = ?, available = false WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, status);
            statement.setString(2, ambulanceId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
