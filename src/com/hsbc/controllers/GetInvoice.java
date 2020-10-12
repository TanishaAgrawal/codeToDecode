package com.hsbc.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.hsbc.models.Invoice;
import com.hsbc.service.InvoiceService;

@WebServlet("/getInvoice")
public class GetInvoice extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(GetInvoice.class); 
	private InvoiceService invoiceService;

	public GetInvoice() {
		super();
		invoiceService = new InvoiceService();
	}
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * Receives GET request with orderId as param, sets invoice object to attribute to request and forwards the request
	 * to Invoice.jsp
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("/getInvoice GET request received");
		int orderId = Integer.parseInt(request.getParameter("orderId"));

		Invoice inv = invoiceService.getInvoiceByOrderId(orderId);
		request.setAttribute("inv", inv);
		
		request.getRequestDispatcher("Invoice.jsp").forward(request, response);

	}
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * Receives POST request and forwards it to doGET
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("/getInvoice POST request received");
		doGet(request, response);
	}

}