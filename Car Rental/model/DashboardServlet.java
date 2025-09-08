package com.bhromonbondhu.controller;

import com.bhromonbondhu.model.Car;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

// Mapped to /dashboard-data
@WebServlet("/dashboard-data")
public class DashboardServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        double walletBalance = session.getAttribute("walletBalance") != null
            ? (double) session.getAttribute("walletBalance")
            : 0.0;

        int rentedCars = session.getAttribute("rentedCars") != null
            ? (int) session.getAttribute("rentedCars")
            : 0;

        // Assume CarServlet stores in application attribute
        List<Car> carList = (List<Car>) getServletContext().getAttribute("CAR_LIST");
        int availableCars = carList == null ? 0 : carList.size();

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(new DashboardData(walletBalance, rentedCars, availableCars)));
    }

    // Inner static DTO class
    private static class DashboardData {
        public double balance;
        public int rentedCars;
        public int availableCars;

        public DashboardData(double b, int r, int a) {
            balance = b;
            rentedCars = r;
            availableCars = a;
        }
    }
}
