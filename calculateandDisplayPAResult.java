import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*; // Import JDBC classes for database connectivity
import java.util.*;

public class calculateandDisplayPAResult extends HttpServlet {
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
            
            // Fetch data from the database
            String province = request.getParameter("province");
            String sql = "SELECT candidate_name, party_name, SUM(votes) AS total_votes FROM candidate_votes WHERE province = ? GROUP BY candidate_name, party_name";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, province);
            rs = stmt.executeQuery();
            
            // Process the result set and calculate results
            List<Candidate> candidateResults = new ArrayList<>();
            while (rs.next()) {
                String candidateName = rs.getString("candidate_name");
                String partyName = rs.getString("party_name");
                int totalVotes = rs.getInt("total_votes");
                candidateResults.add(new Candidate(candidateName, partyName, totalVotes));
            }
            
            // Set calculated results as attribute in request object
            request.setAttribute("candidateResults", candidateResults);
            
            // Forward the calculated results to the view PA results page
            RequestDispatcher rd = request.getRequestDispatcher("viewPAResults.jsp");
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
