package com.hsbc.controllers;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.hsbc.service.NewQuoteService;

/**
 * Servlet implementation class ProductQuoteSubmit
 */
@WebServlet("/ProductQuoteSubmit")
public class ProductQuoteSubmit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(ProductQuoteSubmit.class); 
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private NewQuoteService newQuoteService;
	
    public ProductQuoteSubmit() {
        super();
        newQuoteService=new NewQuoteService();
      
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("/ProductQuoteSubmit GET request received");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Receives POST request with order data as parameters forwards it to service layer for addition to db
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("/ProductQuoteSubmit POST request received");
		String orderDate=request.getParameter("orderDate");
		String customerId=request.getParameter("customerId");
		String employeeId=request.getParameter("employeeId");
	   // String customerName=request.getParameter("customerName");      if username is sent as parameter      
		String gstNumber=request.getParameter("gstNumber");
		String address=request.getParameter("shippingAddress");
		String city=request.getParameter("city");
		String phone=request.getParameter("phoneNumber");
		String email=request.getParameter("email");
		String pincode=request.getParameter("pincode");  
		String productIds=request.getParameter("productIds");
		String totalOrderValue=request.getParameter("totalOrderValue");
		String shippingCost=request.getParameter("shippingCosts");
		
		newQuoteService.saveOrderDetailsToDb(orderDate,customerId,employeeId,gstNumber,address,city,phone,email,pincode,productIds,totalOrderValue,shippingCost);
			
	}

}
