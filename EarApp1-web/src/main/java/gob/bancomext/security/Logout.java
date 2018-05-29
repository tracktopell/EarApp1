package gob.bancomext.security;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alfredo Estrada
 */
@WebServlet(name = "Logout", urlPatterns = {"/logout","/exit","/quit"})
public class Logout extends HttpServlet {    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        final HttpSession session = request.getSession();
        
        System.out.println(":::::<< EXIT <<[]");        
        session.setAttribute("user", null);        
            
        session.invalidate();
        response.sendRedirect(request.getContextPath());
        return;        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
