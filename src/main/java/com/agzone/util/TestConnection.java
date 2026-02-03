package com.agzone.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility to test the database connection.
 * Run this as a Java Application to verify your MySQL setup.
 */
public class TestConnection {
    public static void main(String[] args) {
        System.out.println("Testing GreenCart Database Connection...");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ SUCCESS: Connected to agriculture_website database!");
                
                // Optional: Try a simple query
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT count(*) FROM membership")) {
                    if (rs.next()) {
                        System.out.println("📊 Database Check: Found " + rs.getInt(1) + " registered users.");
                    }
                }
                
            } else {
                System.out.println("❌ FAILURE: Connection is null or closed.");
            }
        } catch (SQLException e) {
            System.err.println("❌ ERROR: Could not connect to the database.");
            System.err.println("Details: " + e.getMessage());
            System.err.println("\nPossible Fixes:");
            System.err.println("1. Ensure MySQL Server is running.");
            System.err.println("2. Check if database 'agriculture_website' exists (Run WORKBENCH_SETUP.sql).");
            System.err.println("3. Verify password in DatabaseConnection.java (currently 'pass@1234').");
        }
    }
}
