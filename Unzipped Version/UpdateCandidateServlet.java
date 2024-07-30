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

@WebServlet("/updateCandidate")
public class UpdateCandidateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String candidateName = request.getParameter("candidateName");
        String candidateCnic = request.getParameter("candidateCnic");
        String candidateParty = request.getParameter("candidateParty");
        String candidateSymbol = request.getParameter("candidateSymbol");

        // Update database
        try {
         try {
    Class.forName("com.mysql.cj.jdbc.Driver");
} catch (ClassNotFoundException e) {
    e.printStackTrace();
    return;
}

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eVoting", "root", "Pakistan123");

            // Prepare SQL statement
            String sql = "INSERT INTO candidates (candidate_name, candidate_cnic, party_name, party_symbol) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, candidateName);
            statement.setString(2, candidateCnic);
            statement.setString(3, candidateParty);
            statement.setString(4, candidateSymbol);

            // Execute SQL statement
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                response.getWriter().write("Candidate data updated successfully.");
            } else {
                response.getWriter().write("Failed to update candidate data.");
            }
            conn.close();
        } catch (Exception e) {
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
}
