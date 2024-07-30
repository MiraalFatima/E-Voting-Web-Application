import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*; // Import JDBC classes for database connectivity
import java.util.*;

public class calculateandDisplayNAresult extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            // Establish database connection (replace databaseUrl, username, and password with your actual database credentials)
            String databaseUrl = "jdbc:mysql://localhost:3306/election";
            String username = "root";
            String password = "";
            conn = DriverManager.getConnection(databaseUrl, username, password);
            
            // Fetch data from the database for National Assembly (NA) Results
            String sqlNA = "SELECT candidate_name, party_name, SUM(votes) AS total_votes FROM candidate_votes WHERE election_type = 'NA' GROUP BY candidate_name, party_name";
            stmt = conn.prepareStatement(sqlNA);
            rs = stmt.executeQuery();
            
            // Process the result set and calculate NA results
            List<Candidate> naResults = new ArrayList<>();
            while (rs.next()) {
                String candidateName = rs.getString("candidate_name");
                String partyName = rs.getString("party_name");
                int totalVotes = rs.getInt("total_votes");
                naResults.add(new Candidate(candidateName, partyName, totalVotes));
            }
            
            // Set NA results as attribute in request object
            request.setAttribute("naResults", naResults);
            
            // Fetch data from the database for Provincial Assembly (PA) Results
            String selectedProvince = request.getParameter("province");
            String sqlPA = "SELECT candidate_name, party_name, SUM(votes) AS total_votes FROM candidate_votes WHERE election_type = 'PA' AND province = ? GROUP BY candidate_name, party_name";
            stmt = conn.prepareStatement(sqlPA);
            stmt.setString(1, selectedProvince);
            rs = stmt.executeQuery();
            
            // Process the result set and calculate PA results
            List<Candidate> paResults = new ArrayList<>();
            while (rs.next()) {
                String candidateName = rs.getString("candidate_name");
                String partyName = rs.getString("party_name");
                int totalVotes = rs.getInt("total_votes");
                paResults.add(new Candidate(candidateName, partyName, totalVotes));
            }
            
            // Set PA results as attribute in request object
            request.setAttribute("paResults", paResults);
            
            // Forward the calculated results to the view results page
            RequestDispatcher rd = request.getRequestDispatcher("viewResults.jsp");
            rd.forward(request, response);
        } catch (SQLException e) {
            // Handle database errors

        } finally {
            // Close JDBC resources in finally block
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
            }
        }
    }
}
