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
 * Cart Controller - Handles shopping cart operations
 */
@WebServlet("/api/cart/*")
public class CartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try {
            String userId = request.getParameter("userId");
            if (userId == null || userId.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\": \"userId is required\"}");
                return;
            }
            
            getCartItems(Integer.parseInt(userId), response, out);
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
            int userId = Integer.parseInt(request.getParameter("userId"));
            String itemName = request.getParameter("itemName");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String itemType = request.getParameter("itemType"); // crop or machine
            Integer cropId = request.getParameter("cropId") != null ? Integer.parseInt(request.getParameter("cropId")) : null;
            Integer machineId = request.getParameter("machineId") != null ? Integer.parseInt(request.getParameter("machineId")) : null;
            
            Connection con = DatabaseConnection.getConnection();
            
            // Check if item already exists in cart
            String checkQuery = "SELECT * FROM cart_items WHERE userId = ? AND itemType = ? AND " +
                               "(cropId = ? OR machineId = ?)";
            PreparedStatement checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setInt(1, userId);
            checkStmt.setString(2, itemType);
            checkStmt.setObject(3, cropId);
            checkStmt.setObject(4, machineId);
            
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                // Update quantity if item exists
                int newQuantity = rs.getInt("quantity") + quantity;
                String updateQuery = "UPDATE cart_items SET quantity = ? WHERE id = ?";
                PreparedStatement updateStmt = con.prepareStatement(updateQuery);
                updateStmt.setInt(1, newQuantity);
                updateStmt.setInt(2, rs.getInt("id"));
                updateStmt.executeUpdate();
                updateStmt.close();
                
                out.println("{\"message\": \"Cart item updated\", \"success\": true}");
            } else {
                // Add new item to cart
                String insertQuery = "INSERT INTO cart_items (userId, itemName, price, quantity, itemType, cropId, machineId) " +
                                   "VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = con.prepareStatement(insertQuery);
                insertStmt.setInt(1, userId);
                insertStmt.setString(2, itemName);
                insertStmt.setDouble(3, price);
                insertStmt.setInt(4, quantity);
                insertStmt.setString(5, itemType);
                insertStmt.setObject(6, cropId);
                insertStmt.setObject(7, machineId);
                
                int result = insertStmt.executeUpdate();
                insertStmt.close();
                
                if (result > 0) {
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    out.println("{\"message\": \"Item added to cart\", \"success\": true}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.println("{\"message\": \"Failed to add item to cart\", \"success\": false}");
                }
            }
            
            checkStmt.close();
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
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            Connection con = DatabaseConnection.getConnection();
            String query = "UPDATE cart_items SET quantity = ? WHERE id = ?";
            
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, itemId);
            
            int result = pstmt.executeUpdate();
            pstmt.close();
            
            if (result > 0) {
                out.println("{\"message\": \"Cart item updated\", \"success\": true}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("{\"message\": \"Item not found\", \"success\": false}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            
            Connection con = DatabaseConnection.getConnection();
            String query = "DELETE FROM cart_items WHERE id = ?";
            
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, itemId);
            
            int result = pstmt.executeUpdate();
            pstmt.close();
            
            if (result > 0) {
                out.println("{\"message\": \"Item removed from cart\", \"success\": true}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("{\"message\": \"Item not found\", \"success\": false}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    private void getCartItems(int userId, HttpServletResponse response, PrintWriter out) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        String query = "SELECT * FROM cart_items WHERE userId = ?";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, userId);
        ResultSet rs = pstmt.executeQuery();
        
        StringBuilder json = new StringBuilder("[");
        boolean first = true;
        double total = 0;
        
        while (rs.next()) {
            if (!first) json.append(",");
            double itemTotal = rs.getDouble("price") * rs.getInt("quantity");
            total += itemTotal;
            
            json.append("{");
            json.append("\"id\":").append(rs.getInt("id")).append(",");
            json.append("\"itemName\":\"").append(rs.getString("itemName")).append("\",");
            json.append("\"price\":").append(rs.getDouble("price")).append(",");
            json.append("\"quantity\":").append(rs.getInt("quantity")).append(",");
            json.append("\"itemType\":\"").append(rs.getString("itemType")).append("\",");
            json.append("\"totalPrice\":").append(itemTotal);
            json.append("}");
            first = false;
        }
        json.append("]");
        
        // Return cart with summary
        out.println("{\"items\":" + json.toString() + ",\"totalAmount\":" + total + "}");
        pstmt.close();
    }
}
