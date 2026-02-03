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
 * Login controller
 * Java equivalent of login.php
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String userName = request.getParameter("UserName");
        String password = request.getParameter("Password");
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            con = DatabaseConnection.getConnection();
            
            // Check if user exists
            String query = "SELECT * FROM membership WHERE UserName = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String dbPassword = rs.getString("Password");
                
                // Password confirmation check
                if (password.equals(dbPassword)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("login", true);
                    session.setAttribute("name", rs.getString("UserName"));
                    session.setAttribute("Pass", userName);
                    
                    response.sendRedirect("index.html");
                } else {
                    response.getWriter().println("<script>alert('Wrong password'); window.history.back();</script>");
                }
            } else {
                response.getWriter().println("<script>alert('User not registered'); window.history.back();</script>");
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
