import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/TimerServlet")
public class TimerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static int timerDuration = 5 * 60; // 5 minutes in seconds
    private static boolean electionStarted = false;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("start".equals(action)) {
            startTimer(response);
        } else if ("stop".equals(action)) {
            stopTimer(response);
        } else if ("checkStatus".equals(action)) {
            checkElectionStatus(response);
        }
    }

   private void startTimer(HttpServletResponse response) throws IOException {
  electionStarted = true;
  // Start timer logic here (if applicable)
  response.getWriter().print("Timer started.");
}

private void stopTimer(HttpServletResponse response) throws IOException {
  electionStarted = false;
  // Stop timer logic here (if applicable)
  response.getWriter().print("Timer stopped.");
}


    private void checkElectionStatus(HttpServletResponse response) throws IOException {
        response.getWriter().print(electionStarted);
    }
}
