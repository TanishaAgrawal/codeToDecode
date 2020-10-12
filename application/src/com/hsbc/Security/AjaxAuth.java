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
 * Servlet Filter implementation class AjaxAuth
 */
@WebFilter(dispatcherTypes = {DispatcherType.REQUEST }
					, urlPatterns = { "/ajaxAuth" })
public class AjaxAuth implements Filter {

    /**
     * Default constructor. 
     */
    public AjaxAuth() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
	    HttpSession session = request.getSession(false);
	    SessionEntity sessionObj = (session != null) ? (SessionEntity) session.getAttribute("sessionObject") : null;
	    String loginURL = request.getContextPath() + "/home.html"; 
	    if (sessionObj == null && !request.getRequestURI().equals(loginURL)) {
	    	if(request.getCookies()!=null)
	    		request.setAttribute("errorMessage", "Session Expired Login Again");
	    	 response.getWriter().print("sessionexpired");
//	    	request.getRequestDispatcher("home.html").forward(request, response);
	    } else {
	    	try {
				SessionManager.checkToken(sessionObj);
				request.setAttribute("sessionObject", sessionObj);
		        chain.doFilter(request, response);
			} catch (UnAuthAccessException e) {
				if(request.getCookies()!=null)
		    		request.setAttribute("errorMessage", e.getMessage());
				response.getWriter().print("sessionexpired");
			}
	    }
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
