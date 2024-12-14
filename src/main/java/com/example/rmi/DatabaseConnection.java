package com.example.rmi;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ambulance_service"; // Remplacer par votre nom de base
    private static final String USER = "sdpp";  // Votre utilisateur MySQL
    private static final String PASSWORD = "mimizizi";  // Votre mot de passe

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
