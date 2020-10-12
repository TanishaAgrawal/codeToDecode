package com.hsbc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hsbc.dao.OrderProcessingDAO;
import com.hsbc.daoImpl.OrderProcessingDAOImpl;
import com.hsbc.dto.CustomerDTO;
import com.hsbc.dto.EmployeeDTO;
import com.hsbc.exceptions.CompanyNotFoundException;
import com.hsbc.exceptions.CustomerNotFoundException;
import com.hsbc.exceptions.EmployeeNotFoundException;
import com.hsbc.exceptions.OrderNotFoundForEmployee;
import com.hsbc.exceptions.ProductNotFoundException;
import com.hsbc.models.Customer;
import com.hsbc.models.Employee;
import com.hsbc.models.OrderDetails;

public class EmployeeService {
	
	private OrderProcessingDAO orderProcessingDAO = null;
	
	public EmployeeService() {
		orderProcessingDAO = new OrderProcessingDAOImpl();
	}
	/**
	 * method returns EmployeeDTO object on the basis on employee id
	 * It fetches:
	 * 	i. Employee object using employeeId
	 * 	ii. OrderDetails object using employeeId
	 * 	iii. Customer Data for each other
	 * 
	 * i,ii,iii are then added to EmployeeDTO object and returned
	 */
	public EmployeeDTO getEmployeeDetails(int employeeId) {
		try {
			Employee employee = orderProcessingDAO.getEmployeeById(employeeId);
			List<OrderDetails> orderDetails = null;
			Map<Integer, CustomerDTO> customers = null;
			try {
				orderDetails = orderProcessingDAO.getOrdersOfEmployee(employeeId);
				customers = new HashMap<Integer, CustomerDTO>();
				for(OrderDetails order : orderDetails) {
					Customer customer = orderProcessingDAO.getCustomerById(order.getCustomerId());
					customers.put(customer.getCustomerId(), new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getCity()));
				
				}
			} catch (OrderNotFoundForEmployee | ProductNotFoundException | CompanyNotFoundException | CustomerNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return new EmployeeDTO(employeeId, employee.getUserName(), employee.getLastLogin(), orderDetails, customers);
			
		} catch (EmployeeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
