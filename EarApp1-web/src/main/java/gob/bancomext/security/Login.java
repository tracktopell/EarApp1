package gob.bancomext.security;

import java.io.IOException;
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
@WebServlet(name = "Login", urlPatterns = {"/__login"})
public class Login extends HttpServlet {    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        final HttpSession session = request.getSession();
        System.out.println("["+session.getId()+"][__login]---------------------------------------");        
        final String tokenId    = request.getParameter("tokenId");
        if(tokenId == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        SecurityToken securityToken = SecurityManager.getInstance().getSecurityToken(tokenId);
        
        System.out.println("["+session.getId()+"][__login] securityToken = "+securityToken);
        System.out.println("["+session.getId()+"][__login]    parameters = "+request.getParameterMap());
        
        final String user          = request.getParameter(securityToken.getUserFieldId());
        final String pass          = request.getParameter(securityToken.getPassFieldId());
        String afterLoginFwd       = null;
        afterLoginFwd = (String)session.getAttribute("afterLoginFwd");
        System.out.println("["+session.getId()+"][__login]  session afterLoginFwd = "+afterLoginFwd);
        if(afterLoginFwd == null){
            afterLoginFwd = request.getParameter("afterLoginFwd");
            System.out.println("["+session.getId()+"][__login]  request afterLoginFwd = "+afterLoginFwd);
        }
        if(afterLoginFwd == null){
            afterLoginFwd = "/";
        }
                
        System.out.println("["+session.getId()+"][__login] user="+user+", pass="+pass+", tokenId="+tokenId+", afterLoginFwd="+afterLoginFwd+",RequestURI="+request.getRequestURI()+", RequestURL="+request.getRequestURL()+"session{new?"+session.isNew()+", id="+session.getId()+",CreationTime="+session.getCreationTime()+"}");
                
        if(user.equals("usr") && pass.equals("pas")){
            System.out.println("["+session.getId()+"][__login]--->> :O Autenticate");
            final User userLoged = new User(user, pass, "?", System.currentTimeMillis());
            session.setAttribute("user", userLoged);
            
            System.out.println("["+session.getId()+"][__login]---------->> :D !!  afterLoginFwd="+afterLoginFwd);
            response.sendRedirect(afterLoginFwd);
            return;
        } else {
            System.out.println("["+session.getId()+"][__login] <<-- ;( !!");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //session.invalidate();
            return;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "__login";
    }

}
