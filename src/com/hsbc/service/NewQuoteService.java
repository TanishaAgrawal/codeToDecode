package com.hsbc.service;


import java.sql.Time;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.hsbc.Security.RSA;
import com.hsbc.dao.OrderProcessingDAO;
import com.hsbc.daoImpl.OrderProcessingDAOImpl;
import com.hsbc.dto.ProductQuoteDto;
import com.hsbc.exceptions.CompanyNotFoundException;
import com.hsbc.exceptions.CustomerNotFoundException;
import com.hsbc.exceptions.EmployeeNotFoundException;
import com.hsbc.exceptions.OrderNotFoundForEmployee;
import com.hsbc.exceptions.ProductNotFoundException;
import com.hsbc.exceptions.SystemSecurityException;
import com.hsbc.models.Customer;
import com.hsbc.models.Employee;
import com.hsbc.models.OrderDetails;
import com.hsbc.models.Product;

public class NewQuoteService {

	private static final Logger log = LogManager.getLogger(NewQuoteService.class); 
	private OrderProcessingDAO orderProcessingDAOImpl;
	private static DecimalFormat decimalFormat = new DecimalFormat("#.##");
	

	public NewQuoteService() 
	{
		super();
		orderProcessingDAOImpl = OrderProcessingDAOImpl.getInstance();

		
	}
	/**
	 * Method return Customer data on the basis of CustomerId
	 * 
	 * @param custId
	 * @return
	 */
	public Customer getCustomerData(String custId) 
	{
		   int customerId=Integer.parseInt(custId);
		   Customer c=null;
	       	try {
				   c=orderProcessingDAOImpl.getCustomerById(customerId);
			}
	       	catch (CustomerNotFoundException e) {
				log.error("Error getting customer data: " + e.getMessage());
				e.printStackTrace();
			}
	       	return c;
	 }
	/**
	 * Method returns the ProductQuoteDto containing shipping 
	 * cost and total order value depending upon the product id of string supplied separated by ","
	 * 
	 * @param prodIds
	 * @return
	 */
	public ProductQuoteDto calcCosts(String prodIds)
	{
		int i=0;
		Product product;
		double totalOrderValue=0;
		double shippingCost=0;
		
		
		String prodId[]=prodIds.split("\\,");
	
		int[] productIds=new int[prodId.length];
		for (String str : prodId)
		{
		    productIds[i++] = Integer.parseInt(str);
		    
		}
		
		System.out.println(productIds[0] + " " + productIds[1]);
		
		for(int j=0;j<productIds.length;j++)
		{
			try {
				product= orderProcessingDAOImpl.productFetcher(productIds[j]);
				totalOrderValue+=product.getProductPrice();
			} catch (CompanyNotFoundException e) {
				log.error("Error calculating costs : " +e.getMessage());
				e.printStackTrace();
			} catch (ProductNotFoundException e) {
				log.error("Error calculation cost : " +e.getMessage());
				e.printStackTrace();
			}
			
			
			
		}
		
		shippingCost=calcShippingPrice(totalOrderValue,productIds);
		shippingCost=Double.parseDouble(decimalFormat.format(shippingCost));
		totalOrderValue=Double.parseDouble(decimalFormat.format(totalOrderValue));
		ProductQuoteDto productQuoteDto=new ProductQuoteDto(shippingCost, totalOrderValue);
		
		return productQuoteDto;
		
	}
	/**
	 * Method computes the shooping cost on the basis of product and total order value
	 * 
	 * @param totalOrderValue
	 * @param productIds
	 * @return
	 */
	public double calcShippingPrice(double totalOrderValue,int[] productIds)
	{
		String categoryId;
		Product product=null;
		double shippingCost=0;
		
		if(totalOrderValue>100000)
		{
			shippingCost=0;
		}
		else
		{
		
			for(int j=0;j<productIds.length;j++)
			{
		
				try {
					product= orderProcessingDAOImpl.productFetcher(productIds[j]);
				} catch (CompanyNotFoundException e) {
					log.error("Error getting product by ID: " + e.getMessage());
					e.printStackTrace();
				} catch (ProductNotFoundException e) 
				{
					log.error("Error getting product by ID: " + e.getMessage());
					e.printStackTrace();
				}
				
				categoryId=product.getProductCategory();
				if(categoryId.equals("LEVEL 1"))
				{
					shippingCost+=0.05*product.getProductPrice();
				}
				else if(categoryId.equals("LEVEL 2"))
				{
					shippingCost+=0.03*product.getProductPrice();
				}
				else if(categoryId.equals("LEVEL 3"))
				{
					shippingCost+=0.02*product.getProductPrice();
				}
			}
		}
	
		return shippingCost;
	
	}
	/**
	 * Receives the order data and if the order data is applicable then
	 * creates OrderDetails object and passes it to DAO to be saved to database
	 * 
	 * 
	 * @param Date
	 * @param customerId
	 * @param employeeId
	 * @param gstNumber
	 * @param address
	 * @param city
	 * @param phone
	 * @param email
	 * @param pincode
	 * @param prodIds
	 * @param tov
	 * @param shippindcst
	 */
	public void saveOrderDetailsToDb(String Date,String customerId,String employeeId,String gstNumber,String address,String city,String phone,String email,String pincode,String prodIds,String tov,String shippindcst)
	{
	
		ArrayList<Product> prodList=new ArrayList<Product>();
		String prodId[]=prodIds.split("\\,");
		int i=0;
		int[] productIds=new int[prodId.length];
		for (String str : prodId)
		{	
			productIds[i++] = Integer.parseInt(str);
			    
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate orderDate = LocalDate.parse(Date, formatter);
		//System.out.println(orderDate.getDayOfWeek());
		if(orderDate.equals(LocalDate.now()) )         //checing if order date is current date
		{
			LocalDate calculatedDate=add(orderDate,3);
			if(calculatedDate.getDayOfWeek()!=DayOfWeek.SATURDAY ||  calculatedDate.getDayOfWeek() != DayOfWeek.SUNDAY)
			{	
				 for(int j=0;j<productIds.length;j++)
				    {
				    	try {
							prodList.add(orderProcessingDAOImpl.productFetcher(productIds[j]));
						} catch (CompanyNotFoundException | ProductNotFoundException e) {
							log.error("Error in saving products to DB: " + e.getMessage());
							e.printStackTrace();
						}
				     	
				    }
				 
				 double totalOrderValue=Double.parseDouble(tov);
				 double shippingCost=Double.parseDouble(shippindcst);
				 int custId=Integer.parseInt(customerId);
				 int empId=Integer.parseInt(employeeId);
				 
				 OrderDetails orderDetails=new OrderDetails(1000,java.sql.Date.valueOf(orderDate),custId,empId,prodList,totalOrderValue,shippingCost,"BLUE DART","PENDING",new Time(System.currentTimeMillis()), new Time(System.currentTimeMillis()));
				 orderProcessingDAOImpl.addOrdertoDB(orderDetails);
			}
		}
		
	}
	/**
	 * Calculated the result date after adding workdays to date
	 * and returns it
	 * 
	 * @param date
	 * @param workdays
	 * @return
	 */
	public LocalDate add(LocalDate date, int workdays) {
	    if (workdays < 1) {
	        return date;
	    }
	    
	    LocalDate result = date;
	    int addedDays = 0;
	    while (addedDays < workdays) {
	        result = result.plusDays(1);
	       /* if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY ||
	              result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
	            ++addedDays;
	        }  */
	        addedDays++;
	    }

	    return result;
	}
	/**
	 * Retrieves the list of products from DAO and
	 * returns it to controller
	 * 
	 * @return
	 */
	public List<Product> getAllProducts(){
		try {
			return orderProcessingDAOImpl.getProducts();
		} catch (ProductNotFoundException | CompanyNotFoundException e) {
			log.error("Error in getting all products: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Performs login logic for Employee login
	 * 
	 * 
	 * @param employeeId
	 * @param password
	 * @return
	 * @throws EmployeeNotFoundException
	 * @throws SystemSecurityException
	 */

	//customer login
	public Employee employeelogin(int employeeId,String password) throws EmployeeNotFoundException, SystemSecurityException {
		Employee e = orderProcessingDAOImpl.getEmployeeById(employeeId);
		if(e!=null) {
			String passFromDB = e.getPassword();
			try {
				System.out.println("password + "+passFromDB);
				//checking the encrypted password of the entity
				passFromDB = RSA.decrypt(e.getPassword());
				
			} catch (SystemSecurityException ex) {
				if(passFromDB.equals(password)) {
					password = RSA.encrypt(password);
					//run an update PASSWORD for e.setpassword();
					e.setPassword(password);
					orderProcessingDAOImpl.updateEmployeePassword(e);
				}else return null;
			}
		}
		return e;
	}

	/**
	 * Perform login Logic for Customer
	 * 
	 * @param customerId
	 * @param password
	 * @return
	 * @throws SystemSecurityException
	 * @throws CustomerNotFoundException
	 */

//	employee login

	public Customer customerLogin(String customerId,String password) throws SystemSecurityException, CustomerNotFoundException {
		Customer c = orderProcessingDAOImpl.getCustomerById(Integer.parseInt(customerId));
		if(c!=null) {
			String passFromDB = c.getPassword();
			try {
				//checking the encrypted password of the entity
				passFromDB = RSA.decrypt(c.getPassword());
			} catch (Exception ex) {
				System.out.println(passFromDB+"  "+password);
				if(passFromDB.equals(password)) {
					password = RSA.encrypt(password);
					//run an update PASSWORD for e.setpassword();
					c.setPassword(password);
					orderProcessingDAOImpl.updateCustomerPassword(c);
				}else return null;
			}
		}
		return c;
		
	}

	/**
	 *  Returns all the orders related to a customer on 
	 *  the basis of customerId
	 * 
	 * @param customerId
	 * @return
	 * @throws OrderNotFoundForEmployee
	 * @throws ProductNotFoundException
	 * @throws CompanyNotFoundException
	 */

	
//	getting the customer order details

	public List<OrderDetails> getCustomerOrderDetailsList(int customerId) throws OrderNotFoundForEmployee, ProductNotFoundException, CompanyNotFoundException{
		List<OrderDetails> list = orderProcessingDAOImpl.getOrdersOfCustomer(customerId);
		return list;
	}

	/**
	 * Approves the order on the basis of orderId 
	 * and returns the OrderDetails object
	 * 
	 * @param orderId
	 * @return
	 * @throws OrderNotFoundForEmployee
	 * @throws ProductNotFoundException
	 */
	//approving the order

	public OrderDetails approveOrder(int orderId) throws OrderNotFoundForEmployee, ProductNotFoundException  {
		return orderProcessingDAOImpl.approveOrder(orderId);
	}
}


