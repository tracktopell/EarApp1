package gob.bancomext.security;

import java.io.IOException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.security.auth.login.LoginException;
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
        logger.info("[" + session.getId() + "][__login]---------------------------------------");

        final SecurityToken securityTokenAttribute = (SecurityToken) session.getAttribute("tokenId");

        if (securityTokenAttribute == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } else {
            session.removeAttribute("tokenId");
        }
        SecurityManager.getInstance().invalidateSecurityToken(securityTokenAttribute.getTokenId());

        final String tokenIdParameter = request.getParameter("tokenId");
        if (tokenIdParameter == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (!securityTokenAttribute.getTokenId().equals(tokenIdParameter)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        logger.info("[" + session.getId() + "][__login]P:       tokenIdParameter = " + tokenIdParameter);
        logger.info("[" + session.getId() + "][__login]A: securityTokenAttribute = " + securityTokenAttribute.getTokenId());
        logger.info("[" + session.getId() + "][__login]               parameters = " + request.getParameterMap());

        final String user = request.getParameter(securityTokenAttribute.getUserFieldId());
        final String pass = request.getParameter(securityTokenAttribute.getPassFieldId());
        String afterLoginFwd = null;
        Integer attemptsLogin = null;

        afterLoginFwd = (String) session.getAttribute("afterLoginFwd");
        attemptsLogin = (Integer) session.getAttribute("attemptsLogin");

        if (attemptsLogin == null) {
            attemptsLogin = 1;
            session.setAttribute("attemptsLogin", attemptsLogin);
        } else {
            attemptsLogin++;
            session.setAttribute("attemptsLogin", attemptsLogin);
        }
        logger.info("[" + session.getId() + "][__login]  session afterLoginFwd = " + afterLoginFwd);
        if (afterLoginFwd == null) {
            afterLoginFwd = request.getParameter("afterLoginFwd");
            logger.info("[" + session.getId() + "][__login]  request afterLoginFwd = " + afterLoginFwd);
        }
        if (afterLoginFwd == null) {
            afterLoginFwd = request.getContextPath();
        }

        logger.info("[" + session.getId() + "][__login] [" + attemptsLogin + "] user=" + user + ", pass=" + pass + ", tokenId=" + tokenIdParameter + ", afterLoginFwd=" + afterLoginFwd + ",RequestURI=" + request.getRequestURI() + ", RequestURL=" + request.getRequestURL() + "session{new?" + session.isNew() + ", id=" + session.getId() + ",CreationTime=" + session.getCreationTime() + "}");

        if (attemptsLogin < 3) {
            User userLoged = null;
            try{
                userLoged = authenticate(user, pass);

                logger.info("[" + session.getId() + "][__login]--->> :O Autenticate");
                String remoteInfo = request.getRemoteUser() + "@(" + request.getRemoteHost() + "|" + request.getRemoteHost() + "):" + request.getRemotePort() + "{" + request.getHeader("User-Agent") + "}";
                
                userLoged.setRemoteHost(remoteInfo);
                
                session.setAttribute("user", userLoged);

                logger.info("[" + session.getId() + "][__login]---------->> :) !! Redirect >> " + afterLoginFwd);
                response.sendRedirect(afterLoginFwd);
                return;
            } catch(Exception ex){
                session.setAttribute("loginMsg", "USER_OR_PASSWROD_ERROR");
                logger.info("[" + session.getId() + "][__login] <<-- ;( -->> again login.jsp !!");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            }
        } else {
            logger.info("[" + session.getId() + "][__login] <<-- MAX ATTEMPTS !!");
            response.sendRedirect(request.getContextPath() + "/quit");
            return;
        }
    }

    private static User authenticate(final String user, final String pass) throws LoginException {
        User userLoged = null;
        try {            
            // Set up the environment for creating the initial context
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://10.9.5.245:389/o=bancomext");

            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, "cn=" + user + ",ou=Empleados,ou=Personas,o=Bancomext");
            env.put(Context.SECURITY_CREDENTIALS, pass);

            // Create the initial context
            DirContext ctx = new InitialDirContext(env);
            userLoged = new User();
            userLoged.setUser(user);
            userLoged.setLoggedInTime(System.currentTimeMillis());            
            
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration results = null;
            results = ctx.search("", "(&(cn=" + user + ")(objectclass=person))", controls);

            while (results.hasMore()) {
                SearchResult searchResult = (SearchResult) results.next();
                Attributes attributes = searchResult.getAttributes();
                final NamingEnumeration<String> iDsEnum = attributes.getIDs();
                StringBuilder sbInfo = new StringBuilder();
                
                while(iDsEnum.hasMore()){
                    String attrName = iDsEnum.next();
                    Attribute attr=(Attribute)(attributes.get(attrName));
                    sbInfo.append("["+attr+"]");
                }
                userLoged.setName(sbInfo.toString());
                
                /*
                if (attributes.get("mail") != null ){
                    userLoged.setEmail(((Attribute)(attributes.get("mail"))).toString());
                }
                if (attributes.get("workforceID") != null ){
                    userLoged.setUserId(((Attribute)(attributes.get("workforceID"))).toString());
                }
                if (attributes.get("fullName") != null ){
                    userLoged.setName(((Attribute)(attributes.get("fullName"))).toString());
                } */
            }
            
            return userLoged;
        } catch (Exception ae) {
            logger.error("-> LDAP :( ", ae);
            throw new LoginException(ae.getMessage());
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
