package com.hsbc.dto;

public class ProductQuoteDto {

	private double shippingCost;
	private double totalOrderValue;
	
	public ProductQuoteDto(double shippingCost, double totalOrderValue) {
		super();
		this.shippingCost = shippingCost;
		this.totalOrderValue = totalOrderValue;
	}

	/**
	 * @return the shippingCost
	 */
	public double getShippingCost() {
		return shippingCost;
	}

	/**
	 * @param shippingCost the shippingCost to set
	 */
	public void setShippingCost(double shippingCost) {
		this.shippingCost = shippingCost;
	}

	/**
	 * @return the totalOrderValue
	 */
	public double getTotalOrderValue() {
		return totalOrderValue;
	}

	/**
	 * @param totalOrderValue the totalOrderValue to set
	 */
	public void setTotalOrderValue(double totalOrderValue) {
		this.totalOrderValue = totalOrderValue;
	}

	
	
}
