package com.hsbc.models;

import java.sql.Time;

public class Customer {

	private int customerId ;
	private String name;
	private String gstNumber;
	private String address;
	private String city ;
	private String email ;
	private String phone ;
	private String pincode ;
	private String password;
	private Time lastLogin;
	private Time createdAt;
	private Time updatedAt;
	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the gstNumber
	 */
	public String getGstNumber() {
		return gstNumber;
	}
	/**
	 * @param gstNumber the gstNumber to set
	 */
	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}
	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
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
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param customerId
	 * @param name
	 * @param gstNumber
	 * @param address
	 * @param city
	 * @param email
	 * @param phone
	 * @param pincode
	 * @param password
	 * @param lastLogin
	 */
	public Customer(int customerId, String name, String gstNumber, String address, String city, String email,
			String phone, String pincode, String password, Time lastLogin) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.gstNumber = gstNumber;
		this.address = address;
		this.city = city;
		this.email = email;
		this.phone = phone;
		this.pincode = pincode;
		this.password = password;
		this.lastLogin = lastLogin;
	}
	/**
	 * @param customerId
	 * @param name
	 * @param gstNumber
	 * @param address
	 * @param city
	 * @param email
	 * @param phone
	 * @param pincode
	 * @param password
	 * @param lastLogin
	 * @param createdAt
	 * @param updatedAt
	 */
	public Customer(int customerId, String name, String gstNumber, String address, String city, String email,
			String phone, String pincode, String password, Time lastLogin, Time createdAt, Time updatedAt) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.gstNumber = gstNumber;
		this.address = address;
		this.city = city;
		this.email = email;
		this.phone = phone;
		this.pincode = pincode;
		this.password = password;
		this.lastLogin = lastLogin;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	
	
}
