package com.hsbc.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.hsbc.models.Product;
import com.hsbc.service.NewQuoteService;

/**
 * Servlet implementation class GetProducts
 */
@WebServlet("/getProducts")
public class GetProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(GetProducts.class); 
    private NewQuoteService newQuoteService; 
    private Gson gson;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProducts() {
        super();
        newQuoteService = new NewQuoteService();
        gson = new Gson();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Receives GET request and sends all product data as JSON response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("/getProducts GET request receieved");
		List<Product> products = newQuoteService.getAllProducts();
		String responseStr = gson.toJson(products);
		response.setContentType("application/json");
		response.getWriter().print(responseStr);

	}

}
