/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class viewPAOptions extends HttpServlet {
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String province = request.getParameter("province"); // Selected province

        // Forward the request to view PA results page for the selected province
        RequestDispatcher rd = request.getRequestDispatcher("viewPAResult.jsp?province=" + province);
        rd.forward(request, response);
    }
}
