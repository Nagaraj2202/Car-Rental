import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    // Get username and password from form
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    try {
      // ✅ Load MySQL JDBC driver
      Class.forName("com.mysql.cj.jdbc.Driver");

      // ✅ Connect to MySQL database (update password if needed)
      Connection conn = DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/Car_rental", "root", "your_password");

      // ✅ Prepare SQL to check user credentials
      PreparedStatement ps = conn.prepareStatement(
          "SELECT * FROM users WHERE username = ? AND password = ?");
      ps.setString(1, username);
      ps.setString(2, password);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        // ✅ If user found, redirect to rent page
        response.sendRedirect("rent.html");
      } else {
        // ❌ Invalid login
        out.println("<script>alert('Invalid username or password');window.location.href='login.html';</script>");
      }

      conn.close();
    } catch (Exception e) {
      e.printStackTrace(out);
      out.println("<h3>Error: " + e.getMessage() + "</h3>");
    }
  }
}
