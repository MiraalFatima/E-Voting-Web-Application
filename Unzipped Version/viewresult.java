/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

/**
 *
 * @author lenovo
 */
//@WebServlet(urlPatterns = {"/viewresult"})

//VIEWNARESULT BACKEND
public class viewresult extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String option = request.getParameter("option"); // Selected option (NA or PA)

        if (null == option) {
            // Invalid option
            response.sendRedirect("error.jsp");
        } else switch (option) {
            case "NA" ->                 {
                    // Forward the request to view NA results page
                    RequestDispatcher rd = request.getRequestDispatcher("calculateandDisplayNAResult.jsp");
                    rd.forward(request, response);
                }
            case "PA" ->                 {
                    // Forward the request to view PA options page
                    RequestDispatcher rd = request.getRequestDispatcher("viewPAOptions.jsp");
                    rd.forward(request, response);
                }
            default -> // Invalid option
                response.sendRedirect("error.jsp");
        }
    }
}
