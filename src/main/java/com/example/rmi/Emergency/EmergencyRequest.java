package com.example.rmi.Emergency;

import java.io.Serializable;

public class EmergencyRequest implements Serializable {
    private String id;          // Identifiant unique de la requête
    private String patientName; // Nom du patient
    private String position;    // Position de l'urgence
    private String description; // Description de l'urgence
    private String assignedAmbulance; // Ambulance assignée (optionnelle)
    private String status;      // Statut de la requête (ex: "En attente", "En cours", "Terminé")

    // Constructeur
    public EmergencyRequest(String id, String patientName, String position, String description, String status) {
        this.id = id;
        this.patientName = patientName;
        this.position = position;
        this.description = description;
        this.status = status;
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getAssignedAmbulance() {
        return assignedAmbulance;
    }

    public void setAssignedAmbulance(String assignedAmbulance) {
        this.assignedAmbulance = assignedAmbulance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EmergencyRequest[id=" + id + ", Patient=" + patientName + ", Position=" + position + ", Description=" + description + ", Status=" + status + "]";
    }
}
