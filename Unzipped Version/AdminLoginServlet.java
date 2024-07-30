import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/adminlogin")
public class AdminLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Check if username and password match the admin credentials
            if ("admin".equals(username) && "admin".equals(password)) {
                // Send success response
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                // Send error response for invalid credentials
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Replace with better error handling in production
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

