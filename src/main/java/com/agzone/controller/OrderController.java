package com.agzone.controller;

import com.agzone.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Order Controller - Handles all order operations
 * Supports both legacy endpoints and REST API
 */
@WebServlet({"/order", "/api/orders/*"})
public class OrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // Get all orders - with optional userId filter
                String userId = request.getParameter("userId");
                if (userId != null && !userId.isEmpty()) {
                    getOrdersByUserId(Integer.parseInt(userId), response, out);
                } else {
                    getAllOrders(response, out);
                }
            } else if (pathInfo.matches("/\\d+")) {
                // Get order by ID
                int orderId = Integer.parseInt(pathInfo.substring(1));
                getOrderById(orderId, response, out);
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
        
        // Check if this is a legacy order endpoint or REST API
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/order")) {
            // Legacy order endpoint
            handleLegacyOrder(request, response, out);
        } else {
            // REST API endpoint
            handleRestOrder(request, response, out);
        }
    }
    
    private void handleLegacyOrder(HttpServletRequest request, HttpServletResponse response, PrintWriter out) 
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("login") == null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.write("{\"success\":false,\"message\":\"Please login first\"}");
            return;
        }
        
        String cartData = request.getParameter("cart");
        String address = request.getParameter("address");
        String totalAmount = request.getParameter("total");
        
        if (cartData == null || cartData.trim().isEmpty()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.write("{\"success\":false,\"message\":\"Cart is empty\"}");
            return;
        }
        
        String userName = (String) session.getAttribute("name");
        
        Connection con = null;
        PreparedStatement pstmt = null;
        
        try {
            con = DatabaseConnection.getConnection();
            
            // Get user ID
            int userId = -1;
            try {
                PreparedStatement userStmt = con.prepareStatement("SELECT id FROM membership WHERE UserName = ?");
                userStmt.setString(1, userName);
                ResultSet rs = userStmt.executeQuery();
                if (rs.next()) {
                    userId = rs.getInt("id");
                }
                rs.close();
                userStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            // Create order
            String orderId = "ORD-" + System.currentTimeMillis();
            String insertQuery = "INSERT INTO orders (orderId, userId, totalAmount, shippingAddress, status) " +
                               "VALUES (?, ?, ?, ?, 'pending')";
            pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, orderId);
            pstmt.setInt(2, userId);
            pstmt.setDouble(3, Double.parseDouble(totalAmount != null ? totalAmount : "0"));
            pstmt.setString(4, address != null ? address : "Not specified");
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.write("{\"success\":true,\"message\":\"Order placed successfully!\",\"orderId\":\"" + orderId + "\"}");
            } else {
                out.write("{\"success\":false,\"message\":\"Failed to create order\"}");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"success\":false,\"message\":\"Error placing order: " + e.getMessage() + "\"}");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void handleRestOrder(HttpServletRequest request, HttpServletResponse response, PrintWriter out) 
            throws IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
            String paymentMethod = request.getParameter("paymentMethod");
            String shippingAddress = request.getParameter("shippingAddress");
            String contactNumber = request.getParameter("contactNumber");
            
            // Generate unique order ID
            String orderId = "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8);
            
            Connection con = DatabaseConnection.getConnection();
            String query = "INSERT INTO orders (orderId, userId, totalAmount, paymentMethod, " +
                          "shippingAddress, contactNumber, status) " +
                          "VALUES (?, ?, ?, ?, ?, ?, 'pending')";
            
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, orderId);
            pstmt.setInt(2, userId);
            pstmt.setDouble(3, totalAmount);
            pstmt.setString(4, paymentMethod);
            pstmt.setString(5, shippingAddress);
            pstmt.setString(6, contactNumber);
            
            int result = pstmt.executeUpdate();
            pstmt.close();
            
            if (result > 0) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.println("{\"message\": \"Order created successfully\", \"orderId\": \"" + orderId + "\", \"success\": true}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"message\": \"Failed to create order\", \"success\": false}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try {
            String pathInfo = request.getPathInfo();
            if (!pathInfo.matches("/\\d+")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\": \"Invalid order ID\"}");
                return;
            }
            
            int orderId = Integer.parseInt(pathInfo.substring(1));
            String status = request.getParameter("status");
            
            Connection con = DatabaseConnection.getConnection();
            String query = "UPDATE orders SET status = ? WHERE id = ?";
            
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, status);
            pstmt.setInt(2, orderId);
            
            int result = pstmt.executeUpdate();
            pstmt.close();
            
            if (result > 0) {
                out.println("{\"message\": \"Order status updated successfully\", \"success\": true}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("{\"message\": \"Order not found\", \"success\": false}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    private void getAllOrders(HttpServletResponse response, PrintWriter out) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        String query = "SELECT * FROM orders ORDER BY createdAt DESC";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        
        StringBuilder json = new StringBuilder("[");
        boolean first = true;
        
        while (rs.next()) {
            if (!first) json.append(",");
            json.append("{");
            json.append("\"id\":").append(rs.getInt("id")).append(",");
            json.append("\"orderId\":\"").append(rs.getString("orderId")).append("\",");
            json.append("\"userId\":").append(rs.getInt("userId")).append(",");
            json.append("\"totalAmount\":").append(rs.getDouble("totalAmount")).append(",");
            json.append("\"status\":\"").append(rs.getString("status")).append("\",");
            json.append("\"paymentMethod\":\"").append(rs.getString("paymentMethod")).append("\",");
            json.append("\"createdAt\":\"").append(rs.getTimestamp("createdAt")).append("\"");
            json.append("}");
            first = false;
        }
        json.append("]");
        
        out.println(json.toString());
        pstmt.close();
    }
    
    private void getOrderById(int orderId, HttpServletResponse response, PrintWriter out) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        String query = "SELECT * FROM orders WHERE id = ?";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, orderId);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            out.println("{");
            out.println("\"id\":" + rs.getInt("id") + ",");
            out.println("\"orderId\":\"" + rs.getString("orderId") + "\",");
            out.println("\"userId\":" + rs.getInt("userId") + ",");
            out.println("\"totalAmount\":" + rs.getDouble("totalAmount") + ",");
            out.println("\"status\":\"" + rs.getString("status") + "\",");
            out.println("\"paymentMethod\":\"" + rs.getString("paymentMethod") + "\",");
            out.println("\"shippingAddress\":\"" + escapeJson(rs.getString("shippingAddress")) + "\",");
            out.println("\"contactNumber\":\"" + rs.getString("contactNumber") + "\",");
            out.println("\"createdAt\":\"" + rs.getTimestamp("createdAt") + "\"");
            out.println("}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.println("{\"error\": \"Order not found\"}");
        }
        pstmt.close();
    }
    
    private void getOrdersByUserId(int userId, HttpServletResponse response, PrintWriter out) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        String query = "SELECT * FROM orders WHERE userId = ? ORDER BY createdAt DESC";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, userId);
        ResultSet rs = pstmt.executeQuery();
        
        StringBuilder json = new StringBuilder("[");
        boolean first = true;
        
        while (rs.next()) {
            if (!first) json.append(",");
            json.append("{");
            json.append("\"id\":").append(rs.getInt("id")).append(",");
            json.append("\"orderId\":\"").append(rs.getString("orderId")).append("\",");
            json.append("\"totalAmount\":").append(rs.getDouble("totalAmount")).append(",");
            json.append("\"status\":\"").append(rs.getString("status")).append("\",");
            json.append("\"paymentMethod\":\"").append(rs.getString("paymentMethod")).append("\",");
            json.append("\"createdAt\":\"").append(rs.getTimestamp("createdAt")).append("\"");
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
