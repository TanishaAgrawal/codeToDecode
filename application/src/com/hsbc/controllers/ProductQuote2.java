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
import com.hsbc.dto.ProductQuoteDto;
import com.hsbc.service.NewQuoteService;

/**
 * Servlet implementation class ProductQuote2
 */
@WebServlet("/ProductQuote2")
public class ProductQuote2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(ProductQuote2.class);   
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private NewQuoteService newQuoteService;
	private Gson gson;
	
    public ProductQuote2() {
        
    	super();
        newQuoteService=new NewQuoteService();
        gson = new Gson();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Receives GET request with productIds as parameter, sends back computed costs as JSON 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("/ProductQuote2 GET request received");
		String param=request.getParameter("productIds");
		ProductQuoteDto productQuoteDto=newQuoteService.calcCosts(param);
		String responseStr = gson.toJson(productQuoteDto);
		response.setContentType("application/json");
		response.getWriter().print(responseStr);		
	 }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Receives POST request and calls doGet()
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("/ProductQuote2 POST request received");
		doGet(request, response);
	}

}
