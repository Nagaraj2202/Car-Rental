package com.bhromonbondhu.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/rent")
public class RentServlet extends HttpServlet {

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/car_rental";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root"; // change this

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String carId = request.getParameter("car-id");
        String rentDate = request.getParameter("rent-date");
        String returnDate = request.getParameter("return-date");
        String selectedPrice = request.getParameter("selectedPrice");

        HttpSession session = request.getSession();
        Double currentBalance = (Double) session.getAttribute("balance");

        if (currentBalance == null) {
            currentBalance = 500.0; // default balance
        }

        double price = Double.parseDouble(selectedPrice);
        double newBalance = currentBalance - price;

        session.setAttribute("balance", newBalance);

        // Save rent details in database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                String query = "INSERT INTO rentals (car_id, rent_date, return_date, price) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, carId);
                ps.setString(2, rentDate);
                ps.setString(3, returnDate);
                ps.setDouble(4, price);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("message", "Car rented successfully!");
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
