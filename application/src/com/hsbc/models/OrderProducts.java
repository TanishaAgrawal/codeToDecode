package com.hsbc.models;

public class OrderProducts {

	private int orderId;
	private int productId;
	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}
	/**
	 * @param orderId
	 * @param productId
	 */
	public OrderProducts(int orderId, int productId) {
		super();
		this.orderId = orderId;
		this.productId = productId;
	}
	/**
	 * 
	 */
	public OrderProducts() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
