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
import java.sql.SQLException;

/**
 * Sales Controller
 * Handles crop sales submissions
 */
@WebServlet("/sales")
public class SalesController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("login") == null) {
            response.getWriter().println("<script>alert('Please login first'); window.location.href='login.html';</script>");
            return;
        }
        
        String cropId = request.getParameter("cid");
        String item = request.getParameter("item");
        String quantity = request.getParameter("quantity");
        String location = request.getParameter("sell-location");
        
        // Get user info from session
        String userName = (String) session.getAttribute("name");
        String userEmail = (String) session.getAttribute("email");
        String userMobile = (String) session.getAttribute("mobile");
        
        // If user info not in session, try to get from database
        if (userEmail == null || userMobile == null) {
            try {
                Connection con = DatabaseConnection.getConnection();
                String query = "SELECT Email, MobileNo FROM membership WHERE UserName = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, userName);
                var rs = pstmt.executeQuery();
                if (rs.next()) {
                    userEmail = rs.getString("Email");
                    userMobile = rs.getString("MobileNo");
                }
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        // Validation
        if (cropId == null || cropId.trim().isEmpty() ||
            item == null || item.trim().isEmpty() ||
            quantity == null || quantity.trim().isEmpty() ||
            location == null || location.trim().isEmpty()) {
            response.getWriter().println("<script>alert('All fields are required'); window.history.back();</script>");
            return;
        }
        
        if (userName == null) {
            response.getWriter().println("<script>alert('User session expired. Please login again.'); window.location.href='login.html';</script>");
            return;
        }
        
        Connection con = null;
        PreparedStatement pstmt = null;
        
        try {
            con = DatabaseConnection.getConnection();
            
            // Get user's address from membership or use location as address
            String address = location; // Default to location, can be enhanced to get from user profile
            
            String insertQuery = "INSERT INTO cropsales (cropid, Quantity, Name, address, mobileNo) VALUES (?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, cropId.trim());
            pstmt.setString(2, quantity.trim());
            pstmt.setString(3, item.trim());
            pstmt.setString(4, address.trim());
            pstmt.setString(5, userMobile != null ? userMobile : "");
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                response.getWriter().println("<script>alert('Crop sale posted successfully! Buyers will contact you soon.'); window.location.href='f1.html';</script>");
            } else {
                response.getWriter().println("<script>alert('Failed to post crop sale'); window.history.back();</script>");
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
}
