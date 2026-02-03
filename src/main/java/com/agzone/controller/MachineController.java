package com.agzone.controller;

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

/**
 * Machine Controller - Handles all machinery rental/sale operations
 */
@WebServlet("/api/machines/*")
public class MachineController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // Get all machinery
                getAllMachines(response, out);
            } else if (pathInfo.matches("/\\d+")) {
                // Get machine by ID
                int machineId = Integer.parseInt(pathInfo.substring(1));
                getMachineById(machineId, response, out);
            } else if (pathInfo.matches("/type/.*")) {
                // Get machines by type (rent/sell)
                String type = pathInfo.substring(6); // Remove "/type/"
                getMachinesByType(type, response, out);
            } else if (pathInfo.matches("/location/.*")) {
                // Get machines by location
                String location = pathInfo.substring(10); // Remove "/location/"
                getMachinesByLocation(location, response, out);
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
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String mobile = request.getParameter("mobile");
            double price = Double.parseDouble(request.getParameter("price"));
            String location = request.getParameter("location");
            String image = request.getParameter("image");
            String owner = request.getParameter("owner");
            String postType = request.getParameter("postType"); // rent, sell, buy
            String description = request.getParameter("description");
            String specifications = request.getParameter("specifications");
            String condition = request.getParameter("condition");
            int userId = Integer.parseInt(request.getParameter("userId"));
            
            Connection con = DatabaseConnection.getConnection();
            String query = "INSERT INTO posts (name, email, mobile, price, location, image, " +
                          "owner, postType, description, specifications, condition, userId) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, mobile);
            pstmt.setDouble(4, price);
            pstmt.setString(5, location);
            pstmt.setString(6, image);
            pstmt.setString(7, owner);
            pstmt.setString(8, postType);
            pstmt.setString(9, description);
            pstmt.setString(10, specifications);
            pstmt.setString(11, condition);
            pstmt.setInt(12, userId);
            
            int result = pstmt.executeUpdate();
            pstmt.close();
            
            if (result > 0) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.println("{\"message\": \"Machinery listing added successfully\", \"success\": true}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"message\": \"Failed to add machinery\", \"success\": false}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    private void getAllMachines(HttpServletResponse response, PrintWriter out) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        String query = "SELECT * FROM posts ORDER BY createdAt DESC";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        
        StringBuilder json = new StringBuilder("[");
        boolean first = true;
        
        while (rs.next()) {
            if (!first) json.append(",");
            json.append("{");
            json.append("\"id\":").append(rs.getInt("id")).append(",");
            json.append("\"name\":\"").append(rs.getString("name")).append("\",");
            json.append("\"price\":").append(rs.getDouble("price")).append(",");
            json.append("\"location\":\"").append(rs.getString("location")).append("\",");
            json.append("\"image\":\"").append(rs.getString("image")).append("\",");
            json.append("\"owner\":\"").append(rs.getString("owner")).append("\",");
            json.append("\"postType\":\"").append(rs.getString("postType")).append("\",");
            json.append("\"condition\":\"").append(rs.getString("condition")).append("\",");
            json.append("\"description\":\"").append(escapeJson(rs.getString("description"))).append("\"");
            json.append("}");
            first = false;
        }
        json.append("]");
        
        out.println(json.toString());
        pstmt.close();
    }
    
    private void getMachineById(int machineId, HttpServletResponse response, PrintWriter out) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        String query = "SELECT * FROM posts WHERE id = ?";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, machineId);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            out.println("{");
            out.println("\"id\":" + rs.getInt("id") + ",");
            out.println("\"name\":\"" + rs.getString("name") + "\",");
            out.println("\"email\":\"" + rs.getString("email") + "\",");
            out.println("\"mobile\":\"" + rs.getString("mobile") + "\",");
            out.println("\"price\":" + rs.getDouble("price") + ",");
            out.println("\"location\":\"" + rs.getString("location") + "\",");
            out.println("\"image\":\"" + rs.getString("image") + "\",");
            out.println("\"owner\":\"" + rs.getString("owner") + "\",");
            out.println("\"postType\":\"" + rs.getString("postType") + "\",");
            out.println("\"description\":\"" + escapeJson(rs.getString("description")) + "\",");
            out.println("\"specifications\":\"" + escapeJson(rs.getString("specifications")) + "\",");
            out.println("\"condition\":\"" + rs.getString("condition") + "\"");
            out.println("}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.println("{\"error\": \"Machine not found\"}");
        }
        pstmt.close();
    }
    
    private void getMachinesByType(String type, HttpServletResponse response, PrintWriter out) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        String query = "SELECT * FROM posts WHERE postType = ? ORDER BY createdAt DESC";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, type);
        ResultSet rs = pstmt.executeQuery();
        
        StringBuilder json = new StringBuilder("[");
        boolean first = true;
        
        while (rs.next()) {
            if (!first) json.append(",");
            json.append("{");
            json.append("\"id\":").append(rs.getInt("id")).append(",");
            json.append("\"name\":\"").append(rs.getString("name")).append("\",");
            json.append("\"price\":").append(rs.getDouble("price")).append(",");
            json.append("\"location\":\"").append(rs.getString("location")).append("\",");
            json.append("\"image\":\"").append(rs.getString("image")).append("\",");
            json.append("\"owner\":\"").append(rs.getString("owner")).append("\"");
            json.append("}");
            first = false;
        }
        json.append("]");
        
        out.println(json.toString());
        pstmt.close();
    }
    
    private void getMachinesByLocation(String location, HttpServletResponse response, PrintWriter out) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        String query = "SELECT * FROM posts WHERE location = ? ORDER BY createdAt DESC";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, location);
        ResultSet rs = pstmt.executeQuery();
        
        StringBuilder json = new StringBuilder("[");
        boolean first = true;
        
        while (rs.next()) {
            if (!first) json.append(",");
            json.append("{");
            json.append("\"id\":").append(rs.getInt("id")).append(",");
            json.append("\"name\":\"").append(rs.getString("name")).append("\",");
            json.append("\"price\":").append(rs.getDouble("price")).append(",");
            json.append("\"image\":\"").append(rs.getString("image")).append("\",");
            json.append("\"owner\":\"").append(rs.getString("owner")).append("\"");
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
