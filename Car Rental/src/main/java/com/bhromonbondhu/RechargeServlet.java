package com.bhromonbondhu;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RechargeServlet")
public class RechargeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // DB Config
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/car_rental";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "yourpassword";  // <-- Update this!

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Read data from form
        String amountStr = request.getParameter("amount");
        String method = request.getParameter("method");
        String upi = request.getParameter("upi");

        // Convert amount
        double amount = 0;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (Exception e) {
            out.println("<h3 style='color:red;'>Invalid amount entered.</h3>");
            return;
        }

        // Save to DB
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            String sql = "INSERT INTO recharges (amount, method, upi_id) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, amount);
            ps.setString(2, method);
            ps.setString(3, upi);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Recharge stored successfully.");
            }

            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
            return;
        }

        // Redirect to confirmation or dashboard
        response.sendRedirect("dashboard.html");
    }
}
