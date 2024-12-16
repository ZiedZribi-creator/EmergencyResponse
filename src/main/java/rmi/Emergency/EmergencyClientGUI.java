package rmi.Emergency;

import com.example.rmi.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.*;

public class EmergencyClientGUI extends JFrame {
    private EmergencyService emergencyService;

    // Components for adding an emergency request
    private JTextField patientNameField;
    private JTextField patientPositionField;
    private JTextArea requestDescriptionArea;

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
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        // Section 1: Ajouter une requête d'urgence
        JPanel requestPanel = new JPanel();
        requestPanel.setBorder(BorderFactory.createTitledBorder("Ajouter une Requête d'Urgence"));
        requestPanel.setLayout(new GridLayout(5, 2)); // Adjusted to fit the new field

        requestPanel.add(new JLabel("Nom du Patient:"));
        patientNameField = new JTextField();
        requestPanel.add(patientNameField);

        requestPanel.add(new JLabel("Position du Patient:"));
        patientPositionField = new JTextField();
        requestPanel.add(patientPositionField);

        requestPanel.add(new JLabel("Description:"));
        requestDescriptionArea = new JTextArea();
        requestPanel.add(new JScrollPane(requestDescriptionArea));

        JButton addRequestButton = new JButton("Ajouter Requête");
        addRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the user input from the text fields
                String patientName = patientNameField.getText();
                String patientPosition = patientPositionField.getText();
                String description = requestDescriptionArea.getText();

                // Validate input fields
                if (patientName.isEmpty() || patientPosition.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(EmergencyClientGUI.this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create an EmergencyRequest object with the input data
                EmergencyRequest request = new EmergencyRequest(patientName, patientPosition, description);

                try {
                    // Call the method to add the emergency request
                    addEmergencyRequest(request);
                    JOptionPane.showMessageDialog(EmergencyClientGUI.this, "Requête d'urgence ajoutée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(EmergencyClientGUI.this, "Erreur lors de l'ajout de la requête d'urgence.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        requestPanel.add(addRequestButton);
        add(requestPanel);

        setVisible(true);
    }

    public void addEmergencyRequest(EmergencyRequest request) throws RemoteException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Step 1: Connect to the database
            conn = DatabaseConnection.getConnection();

            // Step 2: Insert the emergency request into the `emergency_requests` table
            String insertRequestQuery = "INSERT INTO emergency_requests (patient_name, position, description, status) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(insertRequestQuery, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, request.getPatientName());
            stmt.setString(2, request.getPosition());
            stmt.setString(3, request.getDescription());
            stmt.setString(4, "Pending"); // Default status

            // Execute the insertion and retrieve the generated ID
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);  // The generated ID for the new emergency request
                    System.out.println("Emergency request added with ID: " + generatedId);

                    // Step 3: Now, update the ambulance availability
                    String updateAmbulanceQuery = "UPDATE ambulances SET available = false WHERE id = (SELECT ambulance_id FROM emergency_requests WHERE id = ?)";
                    stmt = conn.prepareStatement(updateAmbulanceQuery);
                    stmt.setInt(1, generatedId); // Use the generated request ID to find the associated ambulance
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error while adding emergency request", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Main method to start the GUI
    public static void main(String[] args) {
        // Create and show the GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EmergencyClientGUI();
            }
        });
    }
}
