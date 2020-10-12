package com.hsbc.models;

public class GSTType {

	private int gstTypeId;
	private String type;
	/**
	 * @return the gstTypeId
	 */
	public int getGstTypeId() {
		return gstTypeId;
	}
	/**
	 * @param gstTypeId the gstTypeId to set
	 */
	public void setGstTypeId(int gstTypeId) {
		this.gstTypeId = gstTypeId;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @param gstTypeId
	 * @param type
	 */
	public GSTType(int gstTypeId, String type) {
		super();
		this.gstTypeId = gstTypeId;
		this.type = type;
	}
	/**
	 * 
	 */
	public GSTType() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
