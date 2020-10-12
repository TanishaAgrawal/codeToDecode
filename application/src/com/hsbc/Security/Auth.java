package com.hsbc.Security;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hsbc.exceptions.UnAuthAccessException;
import com.hsbc.models.SessionEntity;

/**
 * Servlet Filter implementation class Auth
 */
@WebFilter(dispatcherTypes = {DispatcherType.REQUEST }
					, urlPatterns = { "/auth" })
public class Auth implements Filter {

    /**
     * Default constructor. 
     */
    public Auth() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
	    HttpSession session = request.getSession(false);
	    SessionEntity sessionObj = (session != null) ? (SessionEntity) session.getAttribute("sessionObject") : null;
	    String loginURL = request.getContextPath() + "/home.html"; 
	  
	    if (sessionObj == null && !request.getRequestURI().equals(loginURL)) {
	    	if(request.getCookies()!=null)
	    		request.setAttribute("errorMessage", "Session Expired Login Again");
	    	response.sendRedirect(loginURL);
	    } else {
	    	try {
				SessionManager.checkToken(sessionObj);
				request.setAttribute("sessionObject", sessionObj);
				
		        chain.doFilter(request, response);
			} catch (UnAuthAccessException e) {
				if(request.getCookies()!=null)
		    		request.setAttribute("errorMessage", e.getMessage());
				
		    	response.sendRedirect(loginURL);
			}
	    }
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
