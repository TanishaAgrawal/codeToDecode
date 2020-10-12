package com.hsbc.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.hsbc.models.Customer;
import com.hsbc.service.NewQuoteService;

/**
 * Servlet implementation class ProductQuote1
 */
@WebServlet("/ProductQuote1")
public class ProductQuote1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(ProductQuote1.class); 
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private NewQuoteService newQuoteService;
	private Gson gson;
    public ProductQuote1() {
       
    	super();
        newQuoteService=new NewQuoteService();
        gson = new Gson();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Receives GET request with customerId as parameter, sends back customer data using customerId as JSON 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		log.info("/ProductQuote1 GET request received");
		String custId=request.getParameter("customerId");
		Customer customer=newQuoteService.getCustomerData(custId);    
		String responseStr = this.gson.toJson(customer);
		response.setContentType("application/json");
		response.getWriter().print(responseStr);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Receives POST request and call doGet()
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
