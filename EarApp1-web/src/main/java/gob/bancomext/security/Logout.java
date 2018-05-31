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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Alfredo Estrada
 */
@WebServlet(name = "Logout", urlPatterns = {"/logout","/exit","/quit"})
public class Logout extends HttpServlet {    
    private static Log logger = LogFactory.getLog(Logout.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        final HttpSession session = request.getSession();
        
        logger.info(":::::<< EXIT <<[]");        
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
