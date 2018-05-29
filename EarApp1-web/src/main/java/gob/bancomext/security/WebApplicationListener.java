package gob.bancomext.security;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * com.tracktopell.testsecurityfilter.WebApplicationListener
 * Web application lifecycle listener.
 *
 * @author Alfredo Estrada
 */
public class WebApplicationListener implements ServletContextListener,HttpSessionListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("==============================>>");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("<<==============================");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("--["+se.getSession().getId()+"]-->>");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("<<--["+se.getSession().getId()+"]--");
    }
}
