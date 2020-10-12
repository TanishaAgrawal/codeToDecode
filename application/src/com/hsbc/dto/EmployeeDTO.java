package com.hsbc.dto;

import java.sql.Time;
import java.util.List;
import java.util.Map;


import com.hsbc.models.OrderDetails;

public class EmployeeDTO {
	private int employeeId;
	private String userName;
	private Time lastLogin;
	private List<OrderDetails> orderDetails;
	private Map<Integer, CustomerDTO> customers;
		
	public EmployeeDTO(int employeeId, String userName, Time lastLogin, List<OrderDetails> orderDetails,Map<Integer, CustomerDTO> customers) {
		super();
		this.employeeId = employeeId;
		this.userName = userName;
		this.lastLogin = lastLogin;
		this.orderDetails = orderDetails;
		this.customers = customers;
	}
	
	public Map<Integer, CustomerDTO> getCustomers() {
		return customers;
	}

	public void setCustomers(Map<Integer, CustomerDTO> customers) {
		this.customers = customers;
	}

	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Time getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Time lastLogin) {
		this.lastLogin = lastLogin;
	}
	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	
	
}
