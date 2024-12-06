package com.example.rmi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry .LocateRegistry;
import java.rmi.registry.Registry;

public class AmbulanceClientGUI extends JFrame {
    private AmbulanceService service;
    private JTextField idField, statusField, locationField, messageField, requestIdField;
    private JTextArea outputArea;

    public AmbulanceClientGUI() {
        setTitle("Ambulance Service Client");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        idField = new JTextField(10);
        statusField = new JTextField(10);
        locationField = new JTextField(10);
        messageField = new JTextField(10);
        requestIdField = new JTextField(10);
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        JButton updateButton = new JButton("Update Status");
        JButton sendMessageButton = new JButton("Send Message");
        JButton acceptRequestButton = new JButton("Accept Request");
        JButton receiveRequestButton = new JButton("Receive Request");

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    service.updateAmbulanceStatusAndLocation(idField.getText(), statusField.getText(), locationField.getText());
                    outputArea.append("Status updated for ambulance ID: " + idField.getText() + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    service.sendMessageToEmergency(messageField.getText());
                    outputArea.append("Message sent: " + messageField.getText() + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        acceptRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    service.acceptEmergencyRequest(requestIdField.getText());
                    outputArea.append("Accepted request ID: " + requestIdField.getText() + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        receiveRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String requestId = service.receiveRequest();
                    if (requestId != null) {
                        outputArea.append("Received request ID: " + requestId + "\n");
                    } else {
                        outputArea.append("No pending requests.\n");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(new JLabel("Ambulance ID:"));
        add(idField);
        add(new JLabel("Status:"));
        add(statusField);
        add(new JLabel("Location:"));
        add(locationField);
        add(updateButton);
        add(new JLabel("Message:"));
        add(messageField);
        add(sendMessageButton);
        add(new JLabel("Request ID:"));
        add(requestIdField);
        add(acceptRequestButton);
        add(receiveRequestButton);
        add(new JScrollPane(outputArea));

        setVisible(true);
        connectToService();
    }

    private void connectToService() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            service = (AmbulanceService) registry.lookup("AmbulanceService");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AmbulanceClientGUI();
    }
}