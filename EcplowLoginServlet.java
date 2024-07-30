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


@WebServlet("/ecplowlogin")
public class EcplowLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eVoting", "root", "Pakistan123")) {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM ecp_low_level WHERE email = ? AND password = ?");
                ps.setString(1, email);
                ps.setString(2, password);

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
        } catch (Exception e) {
            e.printStackTrace(); // Replace with better error handling in production
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
