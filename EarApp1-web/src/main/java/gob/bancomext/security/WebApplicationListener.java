package gob.bancomext.security;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Web application lifecycle listener.
 *
 * @author Alfredo Estrada
 */
public class WebApplicationListener implements ServletContextListener,HttpSessionListener {
    private static Log logger = LogFactory.getLog(WebApplicationListener.class);    
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("=[0]=============================>> context:"+sce.getServletContext().getContextPath()+"@"+sce.getServletContext().getServerInfo());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("<<=[0]============================= context:"+sce.getServletContext().getContextPath()+"@"+sce.getServletContext().getServerInfo());
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("--["+se.getSession().getId()+"]-->>");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("<<--["+se.getSession().getId()+"]--");
    }
}
