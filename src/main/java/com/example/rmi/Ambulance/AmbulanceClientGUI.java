package com.example.rmi.Ambulance;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class AmbulanceClientGUI extends JFrame {
    private AmbulanceService ambulanceService;
    private String ambulanceId;

    // Components for updating status
    private JTextField statusField;
    private JTextField positionField;
    private JCheckBox availableCheckBox;

    // Components for receiving emergency requests
    private JTextArea emergencyRequestArea;
    private JButton acceptRequestButton;
    private JButton rejectRequestButton;

    // Components for chat
    private JTextArea chatArea;
    private JTextField chatMessageField;

    public AmbulanceClientGUI(String ambulanceId) {
        this.ambulanceId = ambulanceId;

        try {
            // Connect to the RMI service
            ambulanceService = (AmbulanceService) Naming.lookup("rmi://localhost/AmbulanceService");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to ambulance service.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        setTitle("Ambulance Client - " + ambulanceId);
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        // Section 1: Update Ambulance Status
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(BorderFactory.createTitledBorder("Update Ambulance Status"));
        statusPanel.setLayout(new GridLayout(4, 2));

        statusPanel.add(new JLabel("Status:"));
        statusField = new JTextField();
        statusPanel.add(statusField);

        statusPanel.add(new JLabel("Position:"));
        positionField = new JTextField();
        statusPanel.add(positionField);

        statusPanel.add(new JLabel("Available:"));
        availableCheckBox = new JCheckBox();
        statusPanel.add(availableCheckBox);

        JButton updateStatusButton = new JButton("Update Status");
        updateStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String status = statusField.getText();
                String position = positionField.getText();
                boolean available = availableCheckBox.isSelected();
                try {
                    ambulanceService.updateStatus(ambulanceId, status, position, available);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        statusPanel.add(updateStatusButton);

        add(statusPanel);

        // Section 2: Emergency Requests
        JPanel emergencyPanel = new JPanel();
        emergencyPanel.setBorder(BorderFactory.createTitledBorder("Emergency Requests"));
        emergencyPanel.setLayout(new BorderLayout());

        emergencyRequestArea = new JTextArea();
        emergencyRequestArea.setEditable(false);
        emergencyPanel.add(new JScrollPane(emergencyRequestArea), BorderLayout.CENTER);

        acceptRequestButton = new JButton("Accept Request");
        rejectRequestButton = new JButton("Reject Request");

        acceptRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to accept the emergency request
                // This would typically involve notifying the service and updating the UI
            }
        });

        rejectRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to reject the emergency request
                // This would typically involve notifying the service and updating the UI
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(acceptRequestButton);
        buttonPanel.add(rejectRequestButton);
        emergencyPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(emergencyPanel);

        // Section 3: Chat
        JPanel chatPanel = new JPanel();
        chatPanel.setBorder(BorderFactory.createTitledBorder("Chat"));
        chatPanel.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        chatMessageField = new JTextField();
        chatPanel.add(chatMessageField, BorderLayout.SOUTH);

        JButton sendMessageButton = new JButton("Send Message");
        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = chatMessageField.getText();
                try {
                    ambulanceService.sendMessage(message);
                    chatArea.append("You: " + message + "\n");
                    chatMessageField.setText("");
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        chatPanel.add(sendMessageButton, BorderLayout.EAST);
        add(chatPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new AmbulanceClientGUI("2");
    }
}