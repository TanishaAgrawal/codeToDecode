
package com.hsbc.service;

import java.sql.Date;
import java.sql.Time;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.hsbc.controllers.GetInvoice;
import com.hsbc.dao.OrderProcessingDAO;
import com.hsbc.daoImpl.OrderProcessingDAOImpl;
import com.hsbc.exceptions.InvoiceNotFoundException;
import com.hsbc.exceptions.OrderNotFoundForEmployee;
import com.hsbc.exceptions.ProductNotFoundException;
import com.hsbc.models.Invoice;
import com.hsbc.models.OrderDetails;

public class InvoiceService
{
	private OrderProcessingDAO orderProcessingDAO;
	private static final Logger log = LogManager.getLogger(InvoiceService.class); 
	public InvoiceService() {
		this.orderProcessingDAO = new OrderProcessingDAOImpl();
	}
	/**
	 * Method generates an Invoice using the OrderDetails passed.
	 * Also using totalOrderValue, it computes GST amount and totalInvoiceValue and adds it to Invoice object
	 * Then Invoice object is passed to DAO to be added to database
	 */
	public Invoice generateInvoice(OrderDetails obj3)
	{
		//Calling DAO to get order details by orderID
		
		Invoice obj1=new Invoice();
		obj1.setInvoiceId(0);
		obj1.setInvoiceDate(new Date(System.currentTimeMillis()));
		obj1.setGstType("INTER STATE");
		obj1.setOrderDetails(obj3);
		double gstAmount = this.generateGST(obj3.getTotalOrderValue());
		obj1.setGstAmount(gstAmount);
		double totalInvoiceValue=obj3.getTotalOrderValue()+gstAmount+obj3.getShippingCost();
		obj1.setTotalInvoiceAmount(totalInvoiceValue);
		obj1.setInvoiceCreatedAt(new Time(System.currentTimeMillis()));
		obj1.setInvoiceUpdatedAt(new Time(System.currentTimeMillis()));
		obj1.setInvoiceStatus("PAID");
		Invoice invoiceResp = this.orderProcessingDAO.addInvoiceToDB(obj1);
		
		return invoiceResp;
	}
	/*
	 *Helper Methods uses totalOrderValue to compute applicable GST
	 */
	public double generateGST(double totalOrderValue)
	{
		return (double) (0.10*totalOrderValue);
	}
	/*
	 * Methods uses orderId to return the get the 
	 * Invoice from DAO
	 */
	public Invoice getInvoiceByOrderId(int orderId) {
		try {
			return this.orderProcessingDAO.getInvoiceByOrderId(orderId);
		} catch (OrderNotFoundForEmployee | ProductNotFoundException | InvoiceNotFoundException e) {
			// TODO Auto-generated catch block
			log.error("Error getting order by ID: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}































