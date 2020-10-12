package com.hsbc.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsbc.Security.SessionManager;
import com.hsbc.exceptions.CustomerNotFoundException;
import com.hsbc.exceptions.SystemSecurityException;
import com.hsbc.models.Customer;
import com.hsbc.service.NewQuoteService;

@WebServlet("/customerLogin")
public class CustomerLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NewQuoteService newQuoteService; 
	public CustomerLogin() {
        super();
        newQuoteService = new NewQuoteService();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		get the id and password 
		String customerId = request.getParameter("customerId");
		String password = request.getParameter("password");
		try {
			Customer c = null;
			if(( c = newQuoteService.customerLogin(customerId, password))!=null) {
				//successful login
				SessionManager.createSession(request, response, c.getCustomerId());
				request.getRequestDispatcher("./orderList").forward(request, response);
			}else {
				//wrong password
				request.setAttribute("loginMessage", "CustomerId Or Password is incorrect");
				request.getRequestDispatcher("FuryCustomer.jsp").forward(request, response);
			}
		}catch(SystemSecurityException sse) {
			//Encryption error
			request.setAttribute("errorMessage", "Server Error Try After Sometime");
			request.getRequestDispatcher("Error.jsp").forward(request, response);
		}catch(CustomerNotFoundException e) {
			//customerId Does not exists in the database
			request.setAttribute("loginMessage", "CustomerId Or Password is incorrect");
			request.getRequestDispatcher("FuryCustomer.jsp").forward(request, response);
		}
		
	}

}
