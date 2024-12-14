package com.example.rmi.Emergency;

import com.example.rmi.Ambulance.Ambulance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class EmergencyClientGUI extends JFrame {
    private EmergencyService emergencyService;

    // Components for adding an ambulance
    private JTextField ambulanceIdField;
    private JTextField ambulanceStatusField;
    private JTextField ambulancePositionField;
    private JCheckBox ambulanceAvailableCheckBox;

    // Components for adding an emergency request
    private JTextField requestIdField;
    private JTextField patientNameField;
    private JTextField patientPositionField;
    private JTextArea requestDescriptionArea;
    private JTextField requestStatusField;

    // Components for chat with ambulance
    private JTextField chatAmbulanceIdField;
    private JTextArea chatArea;
    private JTextField chatMessageField;

    public EmergencyClientGUI() {
        try {
            // Connect to the RMI service
            emergencyService = (EmergencyService) Naming.lookup("rmi://localhost/EmergencyService");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur de connexion au service d'urgence.", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        setTitle("Client d'Urgence");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        // Section 1: Ajouter une ambulance
        JPanel ambulancePanel = new JPanel();
        ambulancePanel.setBorder(BorderFactory.createTitledBorder("Ajouter une Ambulance"));
        ambulancePanel.setLayout(new GridLayout(5, 2));

        ambulancePanel.add(new JLabel("ID Ambulance:"));
        ambulanceIdField = new JTextField();
        ambulancePanel.add(ambulanceIdField);

        ambulancePanel.add(new JLabel("Statut:"));
        ambulanceStatusField = new JTextField();
        ambulancePanel.add(ambulanceStatusField);

        ambulancePanel.add(new JLabel("Position:"));
        ambulancePositionField = new JTextField();
        ambulancePanel.add(ambulancePositionField);

        ambulancePanel.add(new JLabel("Disponible:"));
        ambulanceAvailableCheckBox = new JCheckBox();
        ambulancePanel.add(ambulanceAvailableCheckBox);

        JButton addAmbulanceButton = new JButton("Ajouter Ambulance");
        addAmbulanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAmbulance();
            }
        });
        ambulancePanel.add(addAmbulanceButton);
        add(ambulancePanel);

        // Section 2: Ajouter une requête d'urgence
        JPanel requestPanel = new JPanel();
        requestPanel.setBorder(BorderFactory.createTitledBorder("Ajouter une Requête d'Urgence"));
        requestPanel.setLayout(new GridLayout(6, 2));

        requestPanel.add(new JLabel("ID Requête:"));
        requestIdField = new JTextField();
        requestPanel.add(requestIdField);

        requestPanel.add(new JLabel("Nom du Patient:"));
        patientNameField = new JTextField();
        requestPanel.add(patientNameField);

        requestPanel.add(new JLabel("Position du Patient:"));
        patientPositionField = new JTextField();
        requestPanel.add(patientPositionField);

        requestPanel.add(new JLabel("Description:"));
        requestDescriptionArea = new JTextArea();
        requestPanel.add(new JScrollPane(requestDescriptionArea));

        requestPanel.add(new JLabel("Statut:"));
        requestStatusField = new JTextField();
        requestPanel.add(requestStatusField);

        JButton addRequestButton = new JButton("Ajouter Requête");
        addRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmergencyRequest();
            }
        });
        requestPanel.add(addRequestButton);
        add(requestPanel);

        // Section 3: Espace de chat avec l'ambulance
        JPanel chatPanel = new JPanel();
        chatPanel.setBorder(BorderFactory.createTitledBorder("Chat avec Ambulance"));
        chatPanel.setLayout(new BorderLayout());

        chatAmbulanceIdField = new JTextField();
        chatPanel.add(chatAmbulanceIdField, BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        chatMessageField = new JTextField();
        chatPanel.add(chatMessageField, BorderLayout.SOUTH);

        JButton sendMessageButton = new JButton("Envoyer Message");
        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessageToAmbulance();
            }
        });
        chatPanel.add(sendMessageButton, BorderLayout.EAST);
        add(chatPanel);

        setVisible(true);
    }

    private void addAmbulance() {
        String id = ambulanceIdField.getText();
        String status = ambulanceStatusField.getText();
        String position = ambulancePositionField.getText();
        boolean available = ambulanceAvailableCheckBox.isSelected();

        try {
            Ambulance ambulance = new Ambulance(id, status, position, available);
            emergencyService.addAmbulance(ambulance);
            JOptionPane.showMessageDialog(this, "Ambulance ajoutée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (RemoteException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'ambulance.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEmergencyRequest() {
        String id = requestIdField.getText();
        String patientName = patientNameField.getText();
        String patientPosition = patientPositionField.getText();
        String description = requestDescriptionArea.getText();
        String status = requestStatusField.getText();

        try {
            EmergencyRequest request = new EmergencyRequest(id, patientName, patientPosition, description, status);
            emergencyService.addEmergencyRequest(request);
            JOptionPane.showMessageDialog(this, "Requête d'urgence ajoutée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (RemoteException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la requête d'urgence.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendMessageToAmbulance() {
        String ambulanceId = chatAmbulanceIdField.getText();
        String message = chatMessageField.getText();

        try {
            emergencyService.sendMessageToAmbulance(ambulanceId, message);
            chatArea.append("Vous: " + message + "\n");
            chatMessageField.setText("");
        } catch (RemoteException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'envoi du message.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmergencyClientGUI());
    }
}