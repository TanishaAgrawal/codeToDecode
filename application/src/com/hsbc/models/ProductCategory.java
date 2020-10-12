package com.hsbc.models;

public class ProductCategory {

	private int categoryId;
	private String categoryName;
	/**
	 * @return the categoryId
	 */
	public int getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @param categoryId
	 * @param categoryName
	 */
	public ProductCategory(int categoryId, String categoryName) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
	/**
	 * 
	 */
	public ProductCategory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
