package rmi.Emergency;

import java.io.Serializable;

/**
 * Represents an emergency request in the system.
 */
public class EmergencyRequest implements Serializable {
    private int id;          // Unique identifier for the request
    private String patientName; // Name of the patient
    private String position;    // Location of the emergency
    private String description; // Description of the emergency
    private Integer assignedAmbulanceId; // Track the ambulance assigned
    private String status;

    public EmergencyRequest(int id, String patientName, String position, String description) {
        this.patientName = patientName;
        this.position = position;
        this.description = description;
    }

    public EmergencyRequest( String patientName, String position, String description) {
        this.patientName = patientName;
        this.position = position;
        this.description = description;
    }

    private String generateId() {
        return "ER" + System.currentTimeMillis(); // A simple example using the current time
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "EmergencyRequest[" +
                "id=" + id +
                ", patientName=" + patientName +
                ", position=" + position +
                ", description=" + description +
                "]";
    }

    public void setAssignedAmbulanceId(Integer assignedAmbulanceId) {
        this.assignedAmbulanceId = assignedAmbulanceId;
    }

    public Integer getAssignedAmbulanceId() {
        return assignedAmbulanceId ;
    }

    public void setStatus(String status) {
        this.status= status;
    }

    public String getStatus() {
        return status ;
    }


}
