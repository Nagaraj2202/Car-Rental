import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RentServlet")
public class RentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection parameters
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/car_rental";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "yourpassword"; // <-- Replace with your MySQL password

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve form parameters
        String carId = request.getParameter("carId");
        String rentDate = request.getParameter("rentDate");
        String returnDate = request.getParameter("returnDate");
        String priceStr = request.getParameter("price");

        // Debug output (optional)
        System.out.println("Car ID: " + carId);
        System.out.println("Rent Date: " + rentDate);
        System.out.println("Return Date: " + returnDate);
        System.out.println("Price: " + priceStr);

        // Save to DB
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            String sql = "INSERT INTO rentals (car_id, rent_date, return_date, price) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, carId);
            ps.setDate(2, Date.valueOf(rentDate));
            ps.setDate(3, Date.valueOf(returnDate));
            ps.setDouble(4, Double.parseDouble(priceStr));

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Rental record inserted successfully.");
            }

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
            return;
        }

        // Redirect after success
        response.sendRedirect("recharge.html");
    }
}
