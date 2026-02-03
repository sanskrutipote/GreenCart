package com.agzone.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility class
 * Java equivalent of dbconnection.php
 * 
 * Updated to use separate database: agriculture_website
 */
public class DatabaseConnection {
    // Updated to use separate database
    private static final String DB_URL = "jdbc:mysql://localhost:3306/agriculture_website";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "pass@1234";
    
    private static Connection connection = null;
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        return connection;
    }
    
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
