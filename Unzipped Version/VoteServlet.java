import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import com.google.gson.Gson;

public class VoteServlet extends HttpServlet {
    private Connection connect() {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/eVoting";
        String user = "root";
        String password = "Pakistan123";
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, name, partyName, symbol FROM Candidates");
             ResultSet rs = stmt.executeQuery()) {

            ArrayList<Candidate> candidates = new ArrayList<>();
            while (rs.next()) {
                Candidate candidate = new Candidate(rs.getInt("id"), rs.getString("name"), rs.getString("partyName"), rs.getString("symbol"));
                candidates.add(candidate);
            }

            String json = new Gson().toJson(candidates);
            response.getWriter().write(json);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("{'error': 'Unable to fetch candidates'}");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cnic = request.getParameter("cnic");
        int candidateId = Integer.parseInt(request.getParameter("candidateId"));

        try (Connection conn = connect()) {
            // Ensure a voter votes only once
            PreparedStatement checkVote = conn.prepareStatement("SELECT status FROM Voters WHERE cnic = ?");
            checkVote.setString(1, cnic);
            ResultSet rs = checkVote.executeQuery();
            if (rs.next() && rs.getString("status").equals("voted")) {
                response.getWriter().print("Error: You have already voted.");
                return;
            }

            // Update the voter's record
            PreparedStatement updateVoter = conn.prepareStatement("UPDATE Voters SET voted_candidate_id = ?, status = 'voted' WHERE cnic = ?");
            updateVoter.setInt(1, candidateId);
            updateVoter.setString(2, cnic);
            updateVoter.executeUpdate();

            // Increment the candidate's vote count
            PreparedStatement updateCandidate = conn.prepareStatement("UPDATE Candidates SET vote_count = vote_count + 1 WHERE id = ?");
            updateCandidate.setInt(1, candidateId);
            updateCandidate.executeUpdate();

            response.getWriter().print("Vote successfully cast!");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().print("Error: Voting failed.");
        }
    }

    // Candidate class for JSON mapping
    static class Candidate {
        int id;
        String name;
        String partyName;
        String symbol;

        Candidate(int id, String name, String partyName, String symbol) {
            this.id = id;
            this.name = name;
            this.partyName = partyName;
            this.symbol = symbol;
        }
    }
}
