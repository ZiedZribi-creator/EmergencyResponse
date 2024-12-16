package rmi.Ambulance;

import java.io.Serializable;

public class Ambulance implements Serializable {
    private int id;           // Identifiant unique de l'ambulance
    private String position;     // Position actuelle de l'ambulance (par exemple : coordonnées GPS)
    private boolean available;   // Disponibilité de l'ambulance (true si disponible, false sinon)

    // Constructeur
    public Ambulance( String position, String string, boolean available) {

        this.position = position;
        this.available = available;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Ambulance[id=" + id + ", Position=" + position + ", Available=" + available + "]";
    }
}
