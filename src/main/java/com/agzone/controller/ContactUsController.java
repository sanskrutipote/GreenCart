package com.agzone.controller;

import com.agzone.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * Contact Us controller
 * Handles contact form submissions from various tabs
 */
@WebServlet("/saveContact")
public class ContactUsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[0-9]{10,15}$");
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String type = request.getParameter("subject"); // Type from subject field
        String message = request.getParameter("message");
        
        // Validation
        if (name == null || name.trim().isEmpty()) {
            sendError(response, "Name is required");
            return;
        }
        
        if (email == null || email.trim().isEmpty() || !EMAIL_PATTERN.matcher(email).matches()) {
            sendError(response, "Valid email is required");
            return;
        }
        
        if (phone == null || phone.trim().isEmpty() || !MOBILE_PATTERN.matcher(phone.replaceAll("[^0-9]", "")).matches()) {
            sendError(response, "Valid phone number is required");
            return;
        }
        
        if (type == null || type.trim().isEmpty()) {
            type = "General Inquiry"; // Default type
        }
        
        if (message == null || message.trim().isEmpty()) {
            sendError(response, "Message is required");
            return;
        }
        
        Connection con = null;
        PreparedStatement pstmt = null;
        
        try {
            con = DatabaseConnection.getConnection();
            
            String insertQuery = "INSERT INTO contactus (Name, Email, MobileNo, Type, Message) VALUES (?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, name.trim());
            pstmt.setString(2, email.trim());
            pstmt.setString(3, phone.replaceAll("[^0-9]", ""));
            pstmt.setString(4, type.trim());
            pstmt.setString(5, message.trim());
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                response.getWriter().println("<script>alert('Thank you for contacting us! We will get back to you soon.'); window.location.href='contactUs.html';</script>");
            } else {
                sendError(response, "Failed to submit your message. Please try again.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            sendError(response, "Database error occurred. Please try again later.");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.getWriter().println("<script>alert('" + message + "'); window.history.back();</script>");
    }
}
