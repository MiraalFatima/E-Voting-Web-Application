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
import jakarta.servlet.http.HttpSession; // Import HttpSession

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cnic = request.getParameter("cnic");
        String pin = request.getParameter("pin");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eVoting", "root", "Pakistan123")) {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM voters WHERE cnic = ?");
                ps.setString(1, cnic);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String actualPin = rs.getString("pin");
                        int loginAttempts = rs.getInt("login_attempts");
                        boolean isBlocked = rs.getBoolean("is_blocked");

                        if (isBlocked) {
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Your account is blocked from accessing the system.");
                            return;
                        }

                        if (actualPin.equals(pin)) {
                            // Reset login attempts
                            PreparedStatement resetStmt = con.prepareStatement("UPDATE voters SET login_attempts = 0 WHERE cnic = ?");
                            resetStmt.setString(1, cnic);
                            resetStmt.executeUpdate();

                            // Store CNIC in session
HttpSession session = request.getSession();
session.setAttribute("voterCNIC", cnic);

// Send success response
response.sendRedirect("demopage_voter.html"); // Adjusted redirection

                        } else {
                            // Incorrect PIN
                            loginAttempts++;
                            if (loginAttempts >= 3) {
                                // Block the user after 3 failed attempts
                                PreparedStatement blockStmt = con.prepareStatement("UPDATE voters SET is_blocked = true, login_attempts = ? WHERE cnic = ?");
                                blockStmt.setInt(1, loginAttempts);
                                blockStmt.setString(2, cnic);
                                blockStmt.executeUpdate();
                                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Your account is blocked from accessing the system.");
                            } else {
                                // Update login attempts
                                PreparedStatement updateStmt = con.prepareStatement("UPDATE voters SET login_attempts = ? WHERE cnic = ?");
                                updateStmt.setInt(1, loginAttempts);
                                updateStmt.setString(2, cnic);
                                updateStmt.executeUpdate();

                                // Send error response with remaining attempts
                                int remainingAttempts = 3 - loginAttempts;
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Incorrect PIN. Remaining attempts: " + remainingAttempts);
                            }
                        }
                    } else {
                        // CNIC does not exist
                        // Send error response for invalid CNIC
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Voter data not found!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Replace with better error handling in production
        }
    }
}
