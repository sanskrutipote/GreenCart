package com.agzone.controller;

import com.agzone.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Post Controller
 * Handles machine/product posts for sale/rent
 */
@WebServlet("/post")
public class PostController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get all posts
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            con = DatabaseConnection.getConnection();
            String query = "SELECT * FROM posts ORDER BY CreatedAt DESC";
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();
            
            StringBuilder json = new StringBuilder("[");
            boolean first = true;
            
            while (rs.next()) {
                if (!first) json.append(",");
                first = false;
                
                json.append("{")
                    .append("\"id\":").append(rs.getInt("Id")).append(",")
                    .append("\"name\":\"").append(escapeJson(rs.getString("Name"))).append("\",")
                    .append("\"email\":\"").append(escapeJson(rs.getString("Email"))).append("\",")
                    .append("\"mobile\":\"").append(escapeJson(rs.getString("Mobile"))).append("\",")
                    .append("\"price\":").append(rs.getBigDecimal("Price")).append(",")
                    .append("\"location\":\"").append(escapeJson(rs.getString("location"))).append("\",")
                    .append("\"image\":\"").append(escapeJson(rs.getString("image"))).append("\",")
                    .append("\"owner\":\"").append(escapeJson(rs.getString("Owner"))).append("\",")
                    .append("\"createdAt\":\"").append(rs.getTimestamp("CreatedAt")).append("\"")
                    .append("}");
            }
            
            json.append("]");
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Database error\"}");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("login") == null) {
            response.getWriter().println("<script>alert('Please login first'); window.location.href='login.html';</script>");
            return;
        }
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String price = request.getParameter("price");
        String location = request.getParameter("location");
        String image = request.getParameter("image");
        String owner = (String) session.getAttribute("name");
        
        // Validation
        if (name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            mobile == null || mobile.trim().isEmpty() ||
            price == null || price.trim().isEmpty() ||
            location == null || location.trim().isEmpty()) {
            response.getWriter().println("<script>alert('All fields are required'); window.history.back();</script>");
            return;
        }
        
        Connection con = null;
        PreparedStatement pstmt = null;
        
        try {
            con = DatabaseConnection.getConnection();
            
            String insertQuery = "INSERT INTO posts (Name, Email, Mobile, Price, location, image, Owner) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, name.trim());
            pstmt.setString(2, email.trim());
            pstmt.setString(3, mobile.trim());
            pstmt.setBigDecimal(4, new java.math.BigDecimal(price));
            pstmt.setString(5, location.trim());
            pstmt.setString(6, image != null ? image.trim() : "");
            pstmt.setString(7, owner);
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                response.getWriter().println("<script>alert('Post created successfully!'); window.location.href='rent_machine.html';</script>");
            } else {
                response.getWriter().println("<script>alert('Failed to create post'); window.history.back();</script>");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("<script>alert('Database error occurred'); window.history.back();</script>");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}
