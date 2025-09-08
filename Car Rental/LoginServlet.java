import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String username = request.getParameter("username");
    String password = request.getParameter("password");

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizdb", "root", "yourpassword");

      PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
      ps.setString(1, username);
      ps.setString(2, password);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        response.sendRedirect("home.html"); // Redirect after successful login
      } else {
        out.println("<h3 style='color:red;'>Invalid Username or Password</h3>");
      }
    } catch (Exception e) {
      out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
    }
  }
}
