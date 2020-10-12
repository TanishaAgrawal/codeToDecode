package com.hsbc.models;

public class OrderStatus {

	private int orderStatusId;
	private String status;
	/**
	 * @return the orderStatusId
	 */
	public int getOrderStatusId() {
		return orderStatusId;
	}
	/**
	 * @param orderStatusId the orderStatusId to set
	 */
	public void setOrderStatusId(int orderStatusId) {
		this.orderStatusId = orderStatusId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @param orderStatusId
	 * @param status
	 */
	public OrderStatus(int orderStatusId, String status) {
		super();
		this.orderStatusId = orderStatusId;
		this.status = status;
	}
	/**
	 * 
	 */
	public OrderStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
