package com.agzone.controller;

import com.agzone.model.Crop;
import com.agzone.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Crop Controller - Handles all crop-related operations
 */
@WebServlet("/api/crops/*")
public class CropController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // Get all crops
                getAllCrops(response, out);
            } else if (pathInfo.equals("/available")) {
                // Get available crops
                getAvailableCrops(response, out);
            } else if (pathInfo.matches("/\\d+")) {
                // Get crop by ID
                int cropId = Integer.parseInt(pathInfo.substring(1));
                getCropById(cropId, response, out);
            } else if (pathInfo.matches("/category/.*")) {
                // Get crops by category
                String category = pathInfo.substring(10); // Remove "/category/"
                getCropsByCategory(category, response, out);
            } else if (pathInfo.matches("/location/.*")) {
                // Get crops by location
                String location = pathInfo.substring(10); // Remove "/location/"
                getCropsByLocation(location, response, out);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("{\"error\": \"Endpoint not found\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try {
            String cropName = request.getParameter("cropName");
            String category = request.getParameter("category");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String unit = request.getParameter("unit");
            String description = request.getParameter("description");
            String image = request.getParameter("image");
            int seller_id = Integer.parseInt(request.getParameter("seller_id"));
            String location = request.getParameter("location");
            
            Connection con = DatabaseConnection.getConnection();
            String query = "INSERT INTO crops (cropName, category, price, quantity, unit, " +
                          "description, image, seller_id, location, available) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, true)";
            
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, cropName);
            pstmt.setString(2, category);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, quantity);
            pstmt.setString(5, unit);
            pstmt.setString(6, description);
            pstmt.setString(7, image);
            pstmt.setInt(8, seller_id);
            pstmt.setString(9, location);
            
            int result = pstmt.executeUpdate();
            pstmt.close();
            
            if (result > 0) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.println("{\"message\": \"Crop added successfully\", \"success\": true}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"message\": \"Failed to add crop\", \"success\": false}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    private void getAllCrops(HttpServletResponse response, PrintWriter out) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        String query = "SELECT * FROM crops WHERE available = true ORDER BY createdAt DESC";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        
        StringBuilder json = new StringBuilder("[");
        boolean first = true;
        
        while (rs.next()) {
            if (!first) json.append(",");
            json.append("{");
            json.append("\"id\":").append(rs.getInt("id")).append(",");
            json.append("\"cropName\":\"").append(rs.getString("cropName")).append("\",");
            json.append("\"category\":\"").append(rs.getString("category")).append("\",");
            json.append("\"price\":").append(rs.getDouble("price")).append(",");
            json.append("\"quantity\":").append(rs.getInt("quantity")).append(",");
            json.append("\"unit\":\"").append(rs.getString("unit")).append("\",");
            json.append("\"description\":\"").append(escapeJson(rs.getString("description"))).append("\",");
            json.append("\"image\":\"").append(rs.getString("image")).append("\",");
            json.append("\"location\":\"").append(rs.getString("location")).append("\",");
            json.append("\"seller_id\":").append(rs.getInt("seller_id"));
            json.append("}");
            first = false;
        }
        json.append("]");
        
        out.println(json.toString());
        pstmt.close();
    }
    
    private void getAvailableCrops(HttpServletResponse response, PrintWriter out) throws SQLException {
        getAllCrops(response, out);
    }
    
    private void getCropById(int cropId, HttpServletResponse response, PrintWriter out) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        String query = "SELECT * FROM crops WHERE id = ?";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, cropId);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            out.println("{");
            out.println("\"id\":" + rs.getInt("id") + ",");
            out.println("\"cropName\":\"" + rs.getString("cropName") + "\",");
            out.println("\"category\":\"" + rs.getString("category") + "\",");
            out.println("\"price\":" + rs.getDouble("price") + ",");
            out.println("\"quantity\":" + rs.getInt("quantity") + ",");
            out.println("\"unit\":\"" + rs.getString("unit") + "\",");
            out.println("\"description\":\"" + escapeJson(rs.getString("description")) + "\",");
            out.println("\"image\":\"" + rs.getString("image") + "\",");
            out.println("\"location\":\"" + rs.getString("location") + "\",");
            out.println("\"seller_id\":" + rs.getInt("seller_id"));
            out.println("}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.println("{\"error\": \"Crop not found\"}");
        }
        pstmt.close();
    }
    
    private void getCropsByCategory(String category, HttpServletResponse response, PrintWriter out) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        String query = "SELECT * FROM crops WHERE category = ? AND available = true ORDER BY createdAt DESC";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, category);
        ResultSet rs = pstmt.executeQuery();
        
        StringBuilder json = new StringBuilder("[");
        boolean first = true;
        
        while (rs.next()) {
            if (!first) json.append(",");
            json.append("{");
            json.append("\"id\":").append(rs.getInt("id")).append(",");
            json.append("\"cropName\":\"").append(rs.getString("cropName")).append("\",");
            json.append("\"price\":").append(rs.getDouble("price")).append(",");
            json.append("\"quantity\":").append(rs.getInt("quantity")).append(",");
            json.append("\"image\":\"").append(rs.getString("image")).append("\",");
            json.append("\"location\":\"").append(rs.getString("location")).append("\"");
            json.append("}");
            first = false;
        }
        json.append("]");
        
        out.println(json.toString());
        pstmt.close();
    }
    
    private void getCropsByLocation(String location, HttpServletResponse response, PrintWriter out) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        String query = "SELECT * FROM crops WHERE location = ? AND available = true ORDER BY createdAt DESC";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, location);
        ResultSet rs = pstmt.executeQuery();
        
        StringBuilder json = new StringBuilder("[");
        boolean first = true;
        
        while (rs.next()) {
            if (!first) json.append(",");
            json.append("{");
            json.append("\"id\":").append(rs.getInt("id")).append(",");
            json.append("\"cropName\":\"").append(rs.getString("cropName")).append("\",");
            json.append("\"price\":").append(rs.getDouble("price")).append(",");
            json.append("\"quantity\":").append(rs.getInt("quantity")).append(",");
            json.append("\"image\":\"").append(rs.getString("image")).append("\",");
            json.append("\"seller_id\":").append(rs.getInt("seller_id")).append("\"");
            json.append("}");
            first = false;
        }
        json.append("]");
        
        out.println(json.toString());
        pstmt.close();
    }
    
    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}
