package com.hsbc.models;

public class ShippingCompany {

	private int companyId;
	private String companyName;

	/**
	 * @return the companyId
	 */
	public int getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @param companyId
	 * @param companyName
	 */
	public ShippingCompany(int companyId, String companyName) {
		super();
		this.companyId = companyId;
		this.companyName = companyName;
	}

	/**
	 * 
	 */
	public ShippingCompany() {
		super();
		// TODO Auto-generated constructor stub
	}

}
