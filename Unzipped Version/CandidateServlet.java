import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Base64;

public class CandidateServlet extends HttpServlet {
    private Connection connect() {
        String url = "jdbc:mysql://localhost:3306/eVoting";
        String user = "root";
        String password = "Pakistan123";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Candidates");
            ResultSet rs = stmt.executeQuery();

            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("name", rs.getString("name"));
                obj.put("partyName", rs.getString("party_name"));
                obj.put("partyId", rs.getInt("id"));
                obj.put("symbol", Base64.getEncoder().encodeToString(rs.getBytes("symbol")));
                jsonArray.put(obj);
            }
            response.getWriter().write(jsonArray.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().print("Database error occurred.");
        }
    }
}
