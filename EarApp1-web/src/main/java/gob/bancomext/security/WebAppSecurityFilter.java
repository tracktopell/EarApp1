package gob.bancomext.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Alfredo Estrada
 */
public class WebAppSecurityFilter implements Filter {
    private static Log logger = LogFactory.getLog(WebAppSecurityFilter.class);
    private static final boolean debug = true;

    private FilterConfig filterConfig = null;

    public WebAppSecurityFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpSR = (HttpServletRequest)request;

            String url = httpSR.getRequestURI();
            String queryString = httpSR.getQueryString();
            final HttpSession session = httpSR.getSession();
            StringBuilder remoteInfo = new StringBuilder();
//            remoteInfo.append("{\"RemoteAddr\":").append(httpSR.getRemoteAddr()).append("\",");
//            remoteInfo.append("{\"RemoteHost\":").append(httpSR.getRemoteHost()).append("\",");
//            remoteInfo.append("{\"RemoteHost\":").append(httpSR.getRemotePort()).append("\",");
//            remoteInfo.append("{\"RemoteHost\":").append(httpSR.getRemotePort()).append("\",");
            
            final Object user = session.getAttribute("user");
            logger.info("["+((HttpServletRequest) request).getSession().getId()+"][*filter*] url="+url+", queryString="+queryString+", user="+user+", ContextPath="+httpSR.getContextPath()+"");
            if(user == null){
                session.setAttribute("afterLoginFwd", url);
                //request.getParameterMap().put("afterLoginFwd", new String[]{url});
                logger.info("["+((HttpServletRequest) request).getSession().getId()+"][*filter*] >> FFWD: login.jsp >> then forward >> "+url);
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            }else{
                chain.doFilter(request, response);
            }
       }
        
    }

    @Override
    public void destroy() {
        logger.info("<<****");
    }


}
