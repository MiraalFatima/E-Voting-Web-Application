import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ecphighlogin")
public class EcphighLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Connect to the database
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eVoting", "root", "Pakistan123")) {
                // Prepare SQL statement
                String sql = "SELECT * FROM ecp_high_level WHERE email = ? AND password = ?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, email);
                    ps.setString(2, password);

                    // Execute query
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            // Send success response
                            response.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            // Send error response for invalid credentials
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Replace with better error handling in production
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
