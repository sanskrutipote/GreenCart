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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * Registration controller
 * Java equivalent of registration.php
 */
@WebServlet("/registration")
public class RegistrationController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[0-9]{10}$");
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String userName = request.getParameter("UserName");
        String email = request.getParameter("Email");
        String mobileNo = request.getParameter("MobileNo");
        String nPassword = request.getParameter("Npassword");
        String cPassword = request.getParameter("Cpassword");
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            con = DatabaseConnection.getConnection();
            
            // Validation - Mobile number check
            if (!MOBILE_PATTERN.matcher(mobileNo).matches()) {
                response.getWriter().println("<script>alert('MobileNo is invalid'); window.history.back();</script>");
                return;
            }
            
            // Duplication check
            String checkQuery = "SELECT * FROM membership WHERE UserName = ?";
            pstmt = con.prepareStatement(checkQuery);
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                response.getWriter().println("<script>alert('UserName has been already taken'); window.history.back();</script>");
            } else {
                // Password confirmation check
                if (nPassword.equals(cPassword)) {
                    // Insert into database
                    String insertQuery = "INSERT INTO membership(UserName, MobileNo, Email, Password) VALUES (?, ?, ?, ?)";
                    pstmt = con.prepareStatement(insertQuery);
                    pstmt.setString(1, userName);
                    pstmt.setString(2, mobileNo);
                    pstmt.setString(3, email);
                    pstmt.setString(4, cPassword);
                    
                    int result = pstmt.executeUpdate();
                    
                    if (result > 0) {
                        response.getWriter().println("<script>alert('Registration Successful'); window.location.href='login.html';</script>");
                    } else {
                        response.getWriter().println("<script>alert('Registration failed'); window.history.back();</script>");
                    }
                } else {
                    response.getWriter().println("<script>alert('Password doesn't match'); window.history.back();</script>");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("<script>alert('Database error occurred'); window.history.back();</script>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
