package com.hsbc.models;

import java.sql.Time;

public class Employee {

	private int employeeId;
	private String userName;
	private String password;
	private Time lastLogin;
	private Time createdAt;
	private Time updatedAt;
	
	/**
	 * @return the createdAt
	 */
	public Time getCreatedAt() {
		return createdAt;
	}
	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Time createdAt) {
		this.createdAt = createdAt;
	}
	/**
	 * @return the updatedAt
	 */
	public Time getUpdatedAt() {
		return updatedAt;
	}
	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(Time updatedAt) {
		this.updatedAt = updatedAt;
	}
	/**
	 * @return the employeeId
	 */
	public int getEmployeeId() {
		return employeeId;
	}
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the lastLogin
	 */
	public Time getLastLogin() {
		return lastLogin;
	}
	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(Time lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	
	
	
	/**
	 * @param employeeId
	 * @param userName
	 * @param password
	 * @param lastLogin
	 * @param createdAt
	 * @param updatedAt
	 */
	public Employee(int employeeId, String userName, String password, Time lastLogin, Time createdAt, Time updatedAt) {
		super();
		this.employeeId = employeeId;
		this.userName = userName;
		this.password = password;
		this.lastLogin = lastLogin;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	/**
	 * @param employeeId
	 * @param userName
	 * @param password
	 * @param lastLogin
	 */
	public Employee(int employeeId, String userName, String password, Time lastLogin) {
		super();
		this.employeeId = employeeId;
		this.userName = userName;
		this.password = password;
		this.lastLogin = lastLogin;
	}
	/**
	 * 
	 */
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
