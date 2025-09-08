package com.bhromonbondhu.controller;

import com.bhromonbondhu.model.Car;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/cars")
public class CarServlet extends HttpServlet {
    private static final List<Car> carList = new ArrayList<>();
    private static final AtomicInteger idCounter = new AtomicInteger(1);
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.getWriter().print(gson.toJson(carList));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        BufferedReader reader = request.getReader();
        Car car = gson.fromJson(reader, Car.class);
        car.setId(idCounter.getAndIncrement());
        carList.add(car);
        response.setContentType("application/json");
        response.getWriter().print("{\"status\":\"success\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        carList.removeIf(car -> car.getId() == id);
        response.setContentType("application/json");
        response.getWriter().print("{\"status\":\"deleted\"}");
    }
}
