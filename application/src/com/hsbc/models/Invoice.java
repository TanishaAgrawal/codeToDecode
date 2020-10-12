package com.hsbc.models;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Invoice  {

	private int invoiceId;
	private Date invoiceDate;
	private OrderDetails orderDetails ;
//	private int orderId; // extended from OrderDetails table
//	private int customerId;
	private String gstType; // 0 -> inter state, 1 -> same state
	private double gstAmount;
	private double totalInvoiceAmount;
	private String invoiceStatus; // 0 -> unpaid, 1 -> paid
	private Time invoiceCreatedAt;
	private Time invoiceUpdatedAt;
	/**
	 * @param invoiceId
	 * @param invoiceDate
	 * @param orderDetails
	 * @param gstAmount
	 * @param totalInvoiceAmount
	 * @param invoiceStatus
	 * @param invoiceCreatedAt
	 * @param invoiceUpdatedAt
	 */
	public Invoice(OrderDetails orderDetails, int invoiceId, Date invoiceDate, String gstType, double gstAmount,
			double totalInvoiceAmount, String invoiceStatus, Time invoiceCreatedAt, Time invoiceUpdatedAt) {
		super();
		this.invoiceId = invoiceId;
		this.invoiceDate = invoiceDate;
		this.orderDetails = orderDetails;
		this.gstType = gstType;
		this.gstAmount = gstAmount;
		this.totalInvoiceAmount = totalInvoiceAmount;
		this.invoiceStatus = invoiceStatus.toUpperCase();
		this.invoiceCreatedAt = invoiceCreatedAt;
		this.invoiceUpdatedAt = invoiceUpdatedAt;
	}
	/**
	 * 
	 */
	public Invoice() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the invoiceId
	 */
	public int getInvoiceId() {
		return invoiceId;
	}
	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	/**
	 * @return the invoiceDate
	 */
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	/**
	 * @param invoiceDate the invoiceDate to set
	 */
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	/**
	 * @return the orderDetails
	 */
	public OrderDetails getOrderDetails() {
		return orderDetails;
	}
	/**
	 * @param orderDetails the orderDetails to set
	 */
	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}
	/**
	 * @return the gstType
	 */
	public String getGstType() {
		return gstType;
	}
	/**
	 * @param gstType the gstType to set
	 */
	public void setGstType(String gstType) {
		this.gstType = gstType;
	}
	/**
	 * @return the gstAmount
	 */
	public double getGstAmount() {
		return gstAmount;
	}
	/**
	 * @param gstAmount the gstAmount to set
	 */
	public void setGstAmount(double gstAmount) {
		this.gstAmount = gstAmount;
	}
	/**
	 * @return the totalInvoiceAmount
	 */
	public double getTotalInvoiceAmount() {
		return totalInvoiceAmount;
	}
	/**
	 * @param totalInvoiceAmount the totalInvoiceAmount to set
	 */
	public void setTotalInvoiceAmount(double totalInvoiceAmount) {
		this.totalInvoiceAmount = totalInvoiceAmount;
	}
	/**
	 * @return the invoiceStatus
	 */
	public String getInvoiceStatus() {
		return invoiceStatus.toUpperCase();
	}
	/**
	 * @param invoiceStatus the invoiceStatus to set
	 */
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus.toUpperCase();
	}
	/**
	 * @return the invoiceCreatedAt
	 */
	public Time getInvoiceCreatedAt() {
		return invoiceCreatedAt;
	}
	/**
	 * @param invoiceCreatedAt the invoiceCreatedAt to set
	 */
	public void setInvoiceCreatedAt(Time invoiceCreatedAt) {
		this.invoiceCreatedAt = invoiceCreatedAt;
	}
	/**
	 * @return the invoiceUpdatedAt
	 */
	public Time getInvoiceUpdatedAt() {
		return invoiceUpdatedAt;
	}
	/**
	 * @param invoiceUpdatedAt the invoiceUpdatedAt to set
	 */
	public void setInvoiceUpdatedAt(Time invoiceUpdatedAt) {
		this.invoiceUpdatedAt = invoiceUpdatedAt;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Invoice [invoiceId=" + invoiceId + ", invoiceDate=" + invoiceDate + ", orderDetails=" + orderDetails
				+ ", gstType=" + gstType + ", gstAmount=" + gstAmount + ", totalInvoiceAmount=" + totalInvoiceAmount
				+ ", invoiceStatus=" + invoiceStatus + ", invoiceCreatedAt=" + invoiceCreatedAt + ", invoiceUpdatedAt="
				+ invoiceUpdatedAt + "]";
	}
	
	
	
}
