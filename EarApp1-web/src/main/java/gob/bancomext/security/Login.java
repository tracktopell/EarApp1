package gob.bancomext.security;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
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
@WebServlet(name = "Login", urlPatterns = {"/__login"})
public class Login extends HttpServlet {    
    private static Log logger = LogFactory.getLog(Login.class);
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        final HttpSession session = request.getSession();
        logger.info("["+session.getId()+"][__login]---------------------------------------");        
        
        final SecurityToken securityTokenAttribute = (SecurityToken)session.getAttribute("tokenId");
        
        if(securityTokenAttribute == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }else{
            session.removeAttribute("tokenId");
        }
        SecurityManager.getInstance().invalidateSecurityToken(securityTokenAttribute.getTokenId());
        
        final String tokenIdParameter    = request.getParameter("tokenId");
        if(tokenIdParameter == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        if(!securityTokenAttribute.getTokenId().equals(tokenIdParameter)){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        logger.info("["+session.getId()+"][__login]P:       tokenIdParameter = "+tokenIdParameter);
        logger.info("["+session.getId()+"][__login]A: securityTokenAttribute = "+securityTokenAttribute.getTokenId());
        logger.info("["+session.getId()+"][__login]               parameters = "+request.getParameterMap());
        
        final String user          = request.getParameter(securityTokenAttribute.getUserFieldId());
        final String pass          = request.getParameter(securityTokenAttribute.getPassFieldId());
        String  afterLoginFwd      = null;
        Integer attemptsLogin      = null;
        
        afterLoginFwd = (String )session.getAttribute("afterLoginFwd");
        attemptsLogin = (Integer)session.getAttribute("attemptsLogin");
        
        if(attemptsLogin == null){
            attemptsLogin = 1;
            session.setAttribute("attemptsLogin", attemptsLogin);
        } else{
            attemptsLogin++;
            session.setAttribute("attemptsLogin", attemptsLogin);
        }
        logger.info("["+session.getId()+"][__login]  session afterLoginFwd = "+afterLoginFwd);
        if(afterLoginFwd == null){
            afterLoginFwd = request.getParameter("afterLoginFwd");
            logger.info("["+session.getId()+"][__login]  request afterLoginFwd = "+afterLoginFwd);
        }        
        if(afterLoginFwd == null){
            afterLoginFwd = request.getContextPath();
        }
                
        logger.info("["+session.getId()+"][__login] ["+attemptsLogin+"] user="+user+", pass="+pass+", tokenId="+tokenIdParameter+", afterLoginFwd="+afterLoginFwd+",RequestURI="+request.getRequestURI()+", RequestURL="+request.getRequestURL()+"session{new?"+session.isNew()+", id="+session.getId()+",CreationTime="+session.getCreationTime()+"}");

        if(attemptsLogin < 3 ){
            if(authenticate(user, pass)){
                logger.info("["+session.getId()+"][__login]--->> :O Autenticate");
                String remoteInfo = request.getRemoteUser()+"@("+request.getRemoteHost()+"|"+request.getRemoteHost()+"):"+request.getRemotePort()+"{"+request.getHeader("User-Agent")+"}";
                final User userLoged = new User(user, pass, remoteInfo , System.currentTimeMillis());
                session.setAttribute("user", userLoged);
                if(user.endsWith("F")){
                    logger.info("["+session.getId()+"][__login]---------->> :D !! Forward >> "+afterLoginFwd);
                    Map<String, String[]> extraParams = new TreeMap<String, String[]>();
                    HttpServletRequest cleannedRequest = new CleannedHttpServletRequest(request, extraParams);
                    request.getRequestDispatcher(afterLoginFwd).forward(cleannedRequest, response);
                } else {
                    logger.info("["+session.getId()+"][__login]---------->> :) !! Redirect >> "+afterLoginFwd);
                    response.sendRedirect(afterLoginFwd);
                }
                return;
            } else {                
                session.setAttribute("loginMsg","USER_OR_PASSWROD_ERROR");
                logger.info("["+session.getId()+"][__login] <<-- ;( -->> again login.jsp !!");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            }
        } else {            
            logger.info("["+session.getId()+"][__login] <<-- MAX ATTEMPTS !!");
            //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            request.getRequestDispatcher(request.getContextPath()+"/quit").forward(request, response);
            return;
        }
    }

    private static boolean authenticate(final String user, final String pass) {
        return user.startsWith("usr") && pass.startsWith("pas");
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
