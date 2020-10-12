package com.hsbc.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsbc.Security.SessionManager;
import com.hsbc.exceptions.OrderNotFoundForEmployee;
import com.hsbc.exceptions.ProductNotFoundException;
import com.hsbc.models.OrderDetails;
import com.hsbc.models.SessionEntity;
import com.hsbc.service.InvoiceService;
import com.hsbc.service.NewQuoteService;

@WebServlet("/approveOrder")
public class Approve extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NewQuoteService newQuoteService; 
	private InvoiceService invserv;
       
    public Approve() {
        super();
        newQuoteService = new NewQuoteService();
        invserv = new InvoiceService();
    }
 // to be called by ajax
//   	This servlet is called by customer and this change the status of the order from PENDING to APPROVED 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			try {
				
				//calling the Service which internally calls the DAO  to change the order status
				OrderDetails fOrder = newQuoteService.approveOrder(Integer.valueOf(request.getParameter("orderId")));
				response.getWriter().println("Approved");
				invserv.generateInvoice(fOrder);
			} catch (OrderNotFoundForEmployee | ProductNotFoundException e) {
				// TODO Auto-generated catch block
				response.getWriter().println("error");
			}
			
		
	}

}
