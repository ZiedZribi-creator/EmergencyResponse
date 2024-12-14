package com.example.rmi.Ambulance;

import java.io.Serializable;

public class Ambulance implements Serializable {
    private String id;           // Identifiant unique de l'ambulance
    private String status;       // Statut de l'ambulance (par exemple : "disponible", "en service", etc.)
    private String position;     // Position actuelle de l'ambulance (par exemple : coordonnées GPS)
    private boolean available;   // Disponibilité de l'ambulance (true si disponible, false sinon)

    // Constructeur
    public Ambulance(String id, String status, String position, boolean available) {
        this.id = id;
        this.status = status;
        this.position = position;
        this.available = available;
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Ambulance[id=" + id + ", Status=" + status + ", Position=" + position + ", Available=" + available + "]";
    }
}
