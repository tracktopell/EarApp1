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

/**
 *
 * @author Alfredo Estrada
 */
public class WebAppSecurityFilter implements Filter {

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
            final Object user = session.getAttribute("user");
            System.out.println("**["+((HttpServletRequest) request).getSession().getId()+"]**>>url="+url+", queryString="+queryString+", user="+user+", ContextPath="+httpSR.getContextPath()+"");
            if(user == null){
                session.setAttribute("afterLoginFwd", url);
                //request.getParameterMap().put("afterLoginFwd", new String[]{url});
                System.out.println("**["+((HttpServletRequest) request).getSession().getId()+"]**>> login.jsp >> then forward >> "+url);
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            }else{
                chain.doFilter(request, response);
            }
       }
        
    }

    @Override
    public void destroy() {
        System.out.println("<<++++++++++++++++++++++++++++++++++++++++");
    }


}
