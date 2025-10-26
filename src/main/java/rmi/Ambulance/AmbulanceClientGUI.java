package rmi.Ambulance;

import com.example.rmi.Emergency.EmergencyRequest;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

public class AmbulanceClientGUI extends JFrame {
    private JTextField ambulanceIdField; // New field for ambulance ID input
    private JTextField idField;
    private JTextField descriptionField;
    private JTextField positionField;
    private JButton acceptButton;
    private JButton rejectButton;
    private AmbulanceService ambulanceService;
    private EmergencyRequest currentRequest;

    public AmbulanceClientGUI(AmbulanceService ambulanceService) {
        this.ambulanceService = ambulanceService;

        setTitle("Ambulance Emergency Management");
        setLayout(new FlowLayout());
        setSize(400, 300);  // Adjusted size for the fields
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ambulance ID input field
        JLabel ambulanceIdLabel = new JLabel("Enter Ambulance ID:");
        ambulanceIdField = new JTextField(20); // Text field for ambulance ID input

        // Emergency Request ID field
        JLabel idLabel = new JLabel("Request ID:");
        idField = new JTextField(20);
        idField.setEditable(false); // ID should be read-only

        // Description field
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(20);
        descriptionField.setEditable(false);  // Make the description field read-only for ambulance

        // Position field
        JLabel positionLabel = new JLabel("Position:");
        positionField = new JTextField(20);
        positionField.setEditable(false); // Make the position field read-only for ambulance

        // Accept Emergency Button
        acceptButton = new JButton("Accept Emergency");

        // Reject Emergency Button
        rejectButton = new JButton("Reject Emergency");

        // Add components to the frame
        add(ambulanceIdLabel);
        add(ambulanceIdField);
        add(idLabel);
        add(idField);
        add(descriptionLabel);
        add(descriptionField);
        add(positionLabel);
        add(positionField);
        add(acceptButton);
        add(rejectButton);

        // Add action listeners for the buttons
        acceptButton.addActionListener(e -> {
            try {
                acceptEmergency();
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(AmbulanceClientGUI.this, "Error accepting emergency: " + ex.getMessage());
            }
        });

        rejectButton.addActionListener(e -> {
            try {
                rejectEmergency();
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(AmbulanceClientGUI.this, "Error rejecting emergency: " + ex.getMessage());
            }
        });

        // Fetch the current emergency request
        fetchEmergencyRequest();
    }

    private void fetchEmergencyRequest() {
        try {
            // Get the next emergency request from the ambulance service
            currentRequest = ambulanceService.getNextEmergencyRequest();
            if (currentRequest != null) {
                // Set the fields with current request data
                idField.setText(String.valueOf(currentRequest.getId())); // Store ID as string
                descriptionField.setText(currentRequest.getDescription());
                positionField.setText(currentRequest.getPosition());

                // Enable the accept/reject buttons
                acceptButton.setEnabled(true);
                rejectButton.setEnabled(true);
            } else {
                // No requests available
                clearFields(); // Clear fields if no requests
                idField.setText("No requests");

                // Disable buttons if no request
                acceptButton.setEnabled(false);
                rejectButton.setEnabled(false);
            }
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(this, "Error fetching emergency request: " + e.getMessage());
        }

        // Revalidate and repaint to ensure the UI is updated
        revalidate();
        repaint();
    }

    private void acceptEmergency() throws RemoteException {
        String ambulanceIdText = ambulanceIdField.getText(); // Get the ambulance ID from the text field
        if (currentRequest != null && !ambulanceIdText.isEmpty()) {
            int ambulanceId = Integer.parseInt(ambulanceIdText); // Parse the ambulance ID from the text field

            // Call the service to accept the emergency request with the specified ambulance ID
            ambulanceService.acceptEmergencyRequest(ambulanceId); // Only pass the ambulance ID
            JOptionPane.showMessageDialog(this, "Emergency request accepted for ambulance ID: " + ambulanceId);

            // Clear fields and fetch the next emergency request after accepting the current one
            clearFields();
            fetchEmergencyRequest();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a valid ambulance ID.");
        }
    }

    private void rejectEmergency() throws RemoteException
    {
        if (currentRequest != null) {
            // Notify the ambulance service that this request has been rejected
            ambulanceService.rejectRequest(currentRequest.getId()); // Assuming rejectRequest takes the request ID
            JOptionPane.showMessageDialog(this, "Emergency request rejected: " + currentRequest.getDescription());

            // Clear fields and fetch the next emergency request after rejecting the current one
            clearFields();
            fetchEmergencyRequest();
        } else {
            JOptionPane.showMessageDialog(this, "No emergency request available.");
        }
    }

    private void clearFields() {
        ambulanceIdField.setText(""); // Clear the ambulance ID field
        idField.setText("");
        descriptionField.setText("");
        positionField.setText("");
        currentRequest = null; // Reset current request
    }

    public static void main(String[] args) {
        // Example setup: assuming ambulanceService is already created
        try {
            AmbulanceService ambulanceService = new AmbulanceServiceImpl(1); // Example ambulance ID
            AmbulanceClientGUI gui = new AmbulanceClientGUI(ambulanceService);
            gui.setVisible(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}