package com.hsbc.daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.hsbc.dao.OrderProcessingDAO;
import com.hsbc.exceptions.CompanyNotFoundException;
import com.hsbc.exceptions.CustomerNotFoundException;
import com.hsbc.exceptions.EmployeeNotFoundException;
import com.hsbc.exceptions.InvoiceNotFoundException;
import com.hsbc.exceptions.OrderNotFoundForEmployee;
import com.hsbc.exceptions.ProductNotFoundException;
import com.hsbc.models.Company;
import com.hsbc.models.Customer;
import com.hsbc.models.Employee;
import com.hsbc.models.Invoice;
import com.hsbc.models.OrderDetails;
import com.hsbc.models.Product;
import com.hsbc.models.SessionEntity;

public class OrderProcessingDAOImpl implements OrderProcessingDAO {

	/* 
	 * @ Param
	 * con of type Connection
	 * */
	private static Connection con;
	
	/* Static type
	 * To get Single instance of this class throughout the life-cycle.
	 * Every time, whenever instance of is called, same instance will pe passed to the caller. 
	 * */
	private static OrderProcessingDAO single_instance = null;

	/*
	 * This function will return the single_instance if it is already created, otherwise 
         * it will call the constructor of the class and initialize the static data member.
         * @param  - None
         * @return   instance of type OrderProcessingDAO
         */
	
	public static OrderProcessingDAO getInstance() {
		if (single_instance == null)
			single_instance = new OrderProcessingDAOImpl();
		return single_instance;
	}

	/*
	*  Static block, which will be called when class will be loaded.
	*  This block will assign value to the con(of type Connection)
	*  And it will throw exception if passed values are wrong.
	*/
	static {
		try {	
			@SuppressWarnings("unused")
			OrderProcessingDAO emImpl = OrderProcessingDAOImpl.getInstance();
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			con = DriverManager.getConnection("jdbc:derby:C:\\Users\\ujjwa\\testDB;create=true", "admin", "derby");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	* This method will return the Employee object based on the employeeId.
        * @param  employeeId unique number given to each employee.
        * @return   Object of type Employee
        */
	@Override
	public Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException {
		// TODO Auto-generated method stub
		ResultSet rs = null;
		Employee emp = null;
		String query = "SELECT * FROM APP.EMPLOYEE WHERE employeeId=?";
		try {
			PreparedStatement ppstmt = con.prepareStatement(query);
			ppstmt.setInt(1, employeeId);
			rs = ppstmt.executeQuery();
			if (rs.next()) {
				emp = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTime(4), rs.getTime(5),
						rs.getTime(6));
			} else {
				throw new EmployeeNotFoundException(
						"Employee is not added in Employee Database. Please add Employee First.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emp;
	}

	/**
	* This method will return the Customer object based on the customerId.
        * @param  employeeId unique number given to each customer.
        * @return  Object of type Customer.
        */
	@Override
	public Customer getCustomerById(int customerId) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		ResultSet rs = null;
		Customer cust = null;
		String query = "SELECT * FROM APP.CUSTOMER WHERE customerId=?";
		try {
			PreparedStatement ppstmt = con.prepareStatement(query);
			ppstmt.setInt(1, customerId);
			rs = ppstmt.executeQuery();
			if (rs.next()) {
				cust = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getTime(10),
						rs.getTime(11), rs.getTime(12));
			} else {
				throw new CustomerNotFoundException(
						"Customer is not added in Customer Database. Please add Customer First.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cust;
	}

	/**
	* This method returns the details of the product corresponding to the productId.
	* It uses the tables PRODUCT and COMPANY. It checks if the productId is present in the PRODUCT 
	* table, then it retrieves the GST Number.The query for the GST Number is set to retrieve the 
	* company details in the COMPANY table. If company is found corresponding to the GST Number, then
	* it return the details of product.
    * @param    productId
    * @return   Object of type Product
    */
	public Product productFetcher(int productId) throws CompanyNotFoundException, ProductNotFoundException {
		String queryProduct = "SELECT * FROM APP.PRODUCT WHERE productId=?";
		String queryCompany = "SELECT * FROM APP.COMPANY WHERE gstNumber=?";

		ResultSet rsProduct = null;
		ResultSet rsCompany = null;

		PreparedStatement ppstmtProduct;
		PreparedStatement ppstmtCompany;

		try {
			ppstmtProduct = con.prepareStatement(queryProduct);
			ppstmtCompany = con.prepareStatement(queryCompany);

			ppstmtProduct.setInt(1, productId);//1 specifies the first parameter in the query  

			rsProduct = ppstmtProduct.executeQuery();
			if (rsProduct.next()) {
				// Product is found in the product table
				String gstNum = rsProduct.getString(5);

				ppstmtCompany.setString(1, gstNum);
				rsCompany = ppstmtCompany.executeQuery();
				if (rsCompany.next()) {
					//Company found in the Company table respective to gst number
					//Creating and returning the Product object
					return (new Product(rsProduct.getInt(1), rsProduct.getString(2), rsProduct.getDouble(3),
							rsProduct.getString(4),
							new Company(rsCompany.getString(1), rsCompany.getString(2), rsCompany.getString(3),
									rsCompany.getString(4), rsCompany.getTime(5), rsCompany.getTime(6)),
							rsProduct.getTime(6), rsProduct.getTime(7))); 
				} else {
					throw new CompanyNotFoundException(
							"Company is not added in Company Database. Please add Company First.");
					//throwing exception for company not present in database
				}

			} else {
				throw new ProductNotFoundException(
						"Product is not added in Product Database. Please add Product First.");
				//throwing exception for product not present in database
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Product> getProductByProductIds(int[] productIds)
			throws ProductNotFoundException, CompanyNotFoundException {

		List<Product> al = new ArrayList<>();

		for (int pId : productIds) {

			al.add(productFetcher(pId));

		}

		return al;

	}

	/**
	* This method returns the list of order details an employee is having corresponding to the employeeId.
	* It uses the tables ORDERDETAILS. OrderList is created to store the orders an employee has. 
	* It calls the method orderFetcher which in turn calls the method productFetcher to store the
	* order details.
    * @param    employeeId
    * @return   list of OrderDetails
    */
	@Override
	public List<OrderDetails> getOrdersOfEmployee(int employeeId)
			throws OrderNotFoundForEmployee, ProductNotFoundException, CompanyNotFoundException {
		// TODO Auto-generated method stub

		List<OrderDetails> orderList = new ArrayList<OrderDetails>();

		ResultSet rs = null;

		String query = "SELECT * FROM APP.ORDERDETAILS WHERE employeeId=?";

		try {

			PreparedStatement ppstmt = con.prepareStatement(query);

			ppstmt.setInt(1, employeeId);
			rs = ppstmt.executeQuery();

			if (rs.next()) {

				do {

					orderList.add(orderFetcher(rs.getInt(1)));

				} while (rs.next());

			} else {
				throw new OrderNotFoundForEmployee("No order found for this employee ID");
			}

			return orderList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	* This method will return the list of all Orders with same customerId. If no order for customerId exists
	* then it will return NULL. So,to get OrderDetails it will first call orderFetcher method with orderId, 
	* which it will get after executing select query.
	* Each returned value from the orderFetcher will be added into ArrayList of OrderDetails.
        * @param  customerId unique number given to each customerId.
        * @return  List of object object of type OrderDetails.
        */
	@Override
	public List<OrderDetails> getOrdersOfCustomer(int customerId)
			throws OrderNotFoundForEmployee, ProductNotFoundException, CompanyNotFoundException {

		List<OrderDetails> orderList = new ArrayList<OrderDetails>();

		ResultSet rs = null;

		String query = "SELECT * FROM APP.ORDERDETAILS WHERE customerId=?";

		PreparedStatement ppstmt;
		try {
			ppstmt = con.prepareStatement(query);

			ppstmt.setInt(1, customerId);
			rs = ppstmt.executeQuery();

			if (rs.next()) {

				do {
					orderList.add(orderFetcher(rs.getInt(1)));
				} while (rs.next());
			} else {
				throw new OrderNotFoundForEmployee("No order found for this Customer ID");
			}
			return orderList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	* This method will add Invoice object to the database. It will simply get values for column with 
	* getters and execute the preparedStatement.
        * @param  invoice of type Invoice.
        * @return  same invoice of type Invoice.
        */
	@Override
	public Invoice addInvoiceToDB(Invoice invoice) {

		String queryInvoice = "INSERT INTO APP.INVOICE VALUES(?,?,?,?,?,?,?,?,?)";
		String counter = "SELECT COUNT(*) AS TOTALENTRIES FROM APP.INVOICE";

		try {
			con.setAutoCommit(false);
			PreparedStatement ppstmt = con.prepareStatement(queryInvoice);

			PreparedStatement ps = con.prepareStatement(counter);
			ResultSet count = ps.executeQuery();

			if (count.next()) {

				int currentEntries = count.getInt("TOTALENTRIES");

				ppstmt.setInt(1, 101 + currentEntries);
				ppstmt.setDate(2, (Date) invoice.getInvoiceDate());
				ppstmt.setInt(3, invoice.getOrderDetails().getOrderId());
				ppstmt.setString(4, invoice.getGstType());
				ppstmt.setDouble(5, invoice.getGstAmount());
				ppstmt.setDouble(6, invoice.getTotalInvoiceAmount());
				ppstmt.setString(7, invoice.getInvoiceStatus());
				ppstmt.setTime(8, invoice.getInvoiceCreatedAt());
				ppstmt.setTime(9, invoice.getInvoiceUpdatedAt());
				int status = ppstmt.executeUpdate();
				if (status > 0) {
					con.commit();
				} else {

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	* This method will return the object of type Invoice which will behaving same orderId equal to the 
	* given argument. To achieve this, it will first run a search query on Invoice table to get Invoice
	* object parameters and orderFetcher to get object of OrderDetails which is first parameter of Invoice 
	* constructor. If no such orderId exists in the table, then it will return null.
        * @param  orderId - unique number given to each Order.
        * @return  Object of type Invoice.
        */
	@Override
	public Invoice getInvoiceByOrderId(int orderId)
			throws OrderNotFoundForEmployee, ProductNotFoundException, InvoiceNotFoundException {
		// TODO Auto-generated method stub

		ResultSet rs = null;

		String query = "SELECT * FROM APP.INVOICE WHERE orderId=?";

		try {

			PreparedStatement ppstmt = con.prepareStatement(query);
			ppstmt.setInt(1, orderId);
			rs = ppstmt.executeQuery();

			if (rs.next()) {

				OrderDetails od = orderFetcher(orderId);

				return new Invoice(od, rs.getInt(1), rs.getDate(2), rs.getString(4), rs.getFloat(5), rs.getFloat(6),
						rs.getString(7), rs.getTime(8), rs.getTime(9));
			} else {
				throw new InvoiceNotFoundException("Invioce is not present in database.");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	* This method will return all the Products which exists in the table. It stores all the products into
	* an ArrayList and to get each Product, it will call ProductFetcher for each row of ResultSet.
        * @param  None.
        * @return  ArrayList of type Product.
        */
	@Override
	public List<Product> getProducts() throws ProductNotFoundException, CompanyNotFoundException {

		List<Product> al = new ArrayList<>();
		String queryProduct = "SELECT * FROM APP.PRODUCT";

		ResultSet rsProduct = null;

		PreparedStatement ppstmtProduct;

		try {

			ppstmtProduct = con.prepareStatement(queryProduct);

			rsProduct = ppstmtProduct.executeQuery();

			if (rsProduct.next()) {

				do {
					al.add(productFetcher(rsProduct.getInt(1)));
				} while (rsProduct.next());

				return al;

			} else {
				throw new ProductNotFoundException(
						"Product is not added in Product Database. Please add Product First.");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	* This method is called when an employee wants to add product to the database.
	* It basically performs the insert query over Product table.
        * @param  An Array of type Product.
        * @return  None.
        */
	@Override
	public void addProductsToDB(Product[] products) {

		String queryOrderProduct = "INSERT INTO APP.PRODUCT VALUES(?,?,?,?,?,?,?)";
		String counter = "SELECT COUNT(*) AS TOTALENTRIES FROM APP.PRODUCT";

		try {
			con.setAutoCommit(false);
			PreparedStatement ppstmt = con.prepareStatement(queryOrderProduct);
			PreparedStatement ps = con.prepareStatement(counter);
			ResultSet count = ps.executeQuery();

			if (count.next()) {

				int currentEntries = count.getInt("TOTALENTRIES");

				int i = 0;

				for (Product p : products) {

					ppstmt.setInt(1, 11 + currentEntries + i);
					ppstmt.setString(2, p.getProductName());
					ppstmt.setDouble(3, p.getProductPrice());
					ppstmt.setString(4, p.getProductCategory());
					ppstmt.setString(5, p.getCompany().getGstNumber());
					ppstmt.setTime(6, p.getCreatedAt());
					ppstmt.setTime(7, p.getUpdatedAt());
					int status = ppstmt.executeUpdate();
					if (status > 0) {
						con.commit();
					}
					i++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	* This method will add the passed object of OrderDetails to the database and if it succeed, will return
	* the same object. So, in order to achieve this, the function will execute three different queries in
	* sequence to extract required column values from object and insert it into OrderDetails table and 
	* also makes an entry into OrderProducts Table.
		* @param  orderDetails of type OrderDetails.
        * @return  orderDetails of type OrderDetails.
        */
	@Override
	public OrderDetails addOrdertoDB(OrderDetails orderDetails) {
		// TODO Auto-generated method stub
		String query = "INSERT INTO APP.ORDERDETAILS VALUES(?,?,?,?,?,?,?,?,?,?)";
		String queryOrderProduct = "INSERT INTO APP.ORDERPRODUCTS VALUES(?,?)";
		String counter = "SELECT COUNT(*) AS TOTALENTRIES FROM APP.ORDERDETAILS";

		try {
			con.setAutoCommit(false);

			PreparedStatement ppstmt = con.prepareStatement(query);
			PreparedStatement ppstmtOrderProduct = con.prepareStatement(queryOrderProduct);

			PreparedStatement ps = con.prepareStatement(counter);
			ResultSet count = ps.executeQuery();

			if (count.next()) {

				int currentEntries = count.getInt("TOTALENTRIES");

				ppstmt.setInt(1, 1001 + currentEntries);
				ppstmt.setDate(2, (java.sql.Date) orderDetails.getOrderDate());
				ppstmt.setInt(3, orderDetails.getCustomerId());
				ppstmt.setInt(4, orderDetails.getEmployeeId());
				ppstmt.setDouble(5, orderDetails.getTotalOrderValue());
				ppstmt.setDouble(6, orderDetails.getShippingCost());
				ppstmt.setString(7, orderDetails.getShippingCompany());
				ppstmt.setString(8, orderDetails.getStatus());
				ppstmt.setTime(9, orderDetails.getCreatedAt());
				ppstmt.setTime(10, orderDetails.getUpdatedAt());

				ppstmtOrderProduct.setInt(1, 1001 + currentEntries);

				for (Product p : orderDetails.getProducts()) {
					ppstmtOrderProduct.setInt(2, p.getProductId());
					int status = ppstmtOrderProduct.executeUpdate();
					if (status > 0) {
						con.commit();
					}
				}

				int status = ppstmt.executeUpdate();

				if (status > 0) {
					con.commit();
				}
			}
			return orderDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	* This method is.called when the user wants to approve his/her order. So, for this it just updates the
	*  status column of table OrderDetails, by using orderId as searching key.It basically performs the 
	*  update query over OrderDetails table.
        * @param  orderId of type integer.
        * @return  object of type OrderDetails.
        */
	@Override
	public OrderDetails approveOrder(int orderId) throws OrderNotFoundForEmployee, ProductNotFoundException {
		// TODO Auto-generated method stub

		System.out.println("in approve order with id " + orderId);
		OrderDetails od = orderFetcher(orderId);

		od.setStatus("APPROVED");

		try {
			Statement stmt = con.createStatement();
			stmt.execute("UPDATE APP.ORDERDETAILS SET STATUS='APPROVED' WHERE orderId=" + orderId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return od;

	}

	/**
	* This method is used to complete the approved orders of customer. For this, it first run select query 
	* on OrderDetails table and selects the row which have status value equal to APPROVED. And then it 
	* performs update query and set the status value to Completed. 
	* It basically performs the Update query on OrderDetails table. It uses ArrayList to store all the Orders
	* with status completed and at last return the same ArrayList.
        * @param  None.
        * @return  ArrayList of type OrderDetails.
        */
	@Override // CRON
	public List<OrderDetails> completeOrder() throws OrderNotFoundForEmployee, ProductNotFoundException {

		List<OrderDetails> updatedOrders = new ArrayList<OrderDetails>();

		String fetchApprovedQuery = "SELECT * FROM APP.ORDERDETAILS WHERE STATUS = 'APPROVED'";

		try {

			PreparedStatement ppstmtFetch = con.prepareStatement(fetchApprovedQuery);
			ResultSet rs = ppstmtFetch.executeQuery();

			while (rs.next()) {

				int orderId = rs.getInt(1);

				OrderDetails od = orderFetcher(orderId);

				od.setStatus("COMPLETED");

				try {
					Statement stmt = con.createStatement();
					stmt.execute("UPDATE APP.ORDERDETAILS SET STATUS='COMPLETED' WHERE orderId=" + orderId);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				updatedOrders.add(od);

			}

			return updatedOrders;

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	/**
	* This method is called to change status of Orders which are ordered before 30 days from today.
	* It basically performs the update query on OrderDetails table to check whether orderDate is before 
	* 30 days from current_day and changed the status to EXPIRED.
        * @param  None.
        * @return  List of OrderDetails with status Expired.
        */
	@Override // CRON
	public List<OrderDetails> expiryOrder() throws OrderNotFoundForEmployee, ProductNotFoundException {

		List<OrderDetails> updatedOrders = new ArrayList<OrderDetails>();

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DATE, -30);

		@SuppressWarnings("deprecation")
		Date tryDate = new Date(cal.getTime().getYear(), cal.getTime().getMonth(), cal.getTime().getDate());

		String fetchPendingQuery = "UPDATE APP.ORDERDETAILS SET STATUS = 'EXPIRED' where orderDate < ? AND STATUS != 'EXPIRED'";

		try {
			con.setAutoCommit(false);
			PreparedStatement ppstmtFetch = con.prepareStatement(fetchPendingQuery);
			ppstmtFetch.setDate(1, tryDate);
			int status = ppstmtFetch.executeUpdate();

			if (status > 0) {
				System.out.println(status);
				con.commit();
			}

			return updatedOrders;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}
		return null;
	}
	
	
	/**
	* This method returns the order details corresponding to the orderId.
	* It uses the tables ORDERDETAILS and ORDERPRODUCTS. Order id is set in the query to fetch the order
	* details. ArrayList of Product is created to store the products and productFetcher method is called
	* to add the products in the list. Object of orderDetails is returned with the products.
    	* @param    orderId
    	* @return   Object of type OrderDetails
    */
	public OrderDetails orderFetcher(int orderId) throws OrderNotFoundForEmployee, ProductNotFoundException {

		String query = "SELECT * FROM APP.ORDERDETAILS WHERE orderId=?";//Fetching the query of orderId passed from orderdetails table
		String queryOrder = "SELECT * FROM APP.ORDERPRODUCTS WHERE orderId=?";//Fetching the column from orderproducts table 

		ResultSet rs = null;
		ResultSet rsOrder = null;
		
		
		ArrayList<Product> productList = null;
		//productList List to store the product present in each order
		try {
			PreparedStatement ppstmt = con.prepareStatement(query);
			PreparedStatement ppstmtOrder = con.prepareStatement(queryOrder);
			ppstmt.setInt(1, orderId);
			rs = ppstmt.executeQuery();

			while (rs.next()) {
				
				// ORDER FETCHED
				ppstmtOrder.setInt(1, rs.getInt(1));
				rsOrder = ppstmtOrder.executeQuery();
				productList = new ArrayList<Product>();

				while (rsOrder.next()) {

					// WE GOT NEW PRODUCT ID
					productList.add(productFetcher(rsOrder.getInt(2)));

				}

				return (new OrderDetails(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getInt(4), productList,
						rs.getDouble(5), rs.getDouble(6), rs.getString(7), rs.getString(8), rs.getTime(9),
						rs.getTime(10)));

			}
		} catch (SQLException | CompanyNotFoundException e) {
			
			e.printStackTrace();
		}
		return null;
	}

	/**
	* This method is to update the password of the customer in the CUSTOMER table corresponding 
	* to the customerId.
		* @param    customer
		* @return   Object of type Customer
    */
	@Override
	public Customer updateCustomerPassword(Customer customer) {

		String updateQuery = "UPDATE APP.CUSTOMER SET password = ? WHERE customerId=?";

		try {
			con.setAutoCommit(false);

			PreparedStatement ppstmt = con.prepareStatement(updateQuery);
			ppstmt.setString(1, customer.getPassword());
			ppstmt.setInt(2, customer.getCustomerId());

			boolean status = ppstmt.execute();

			if (status) {
				con.commit();
				return customer;
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	* This method is to update the password of the employee in the EMPLOYEE table corresponding 
	* to the employeeId.
		* @param    employee
		* @return   Object of type Employee
    */
	@Override
	public Employee updateEmployeePassword(Employee employee) {

		String updateQuery = "UPDATE APP.EMPLOYEE SET password = ? WHERE employeeId=?";

		try {
			con.setAutoCommit(false);

			PreparedStatement ppstmt = con.prepareStatement(updateQuery);
			ppstmt.setString(1, employee.getPassword());
			ppstmt.setInt(2, employee.getEmployeeId());

			boolean status = ppstmt.execute();

			if (status) {
				con.commit();
				return employee;
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	* This method is used to fetch the object of type SessionEntity based on the passed value of personId. 
	* Main use of this method is only to fetch row where personId matches with the column value of personId
	* of table SessionEntity. 
        * @param  personId of type integer.
        * @return Object of SessionEntity.
        */
	@Override
	public SessionEntity tokenFetcher(int personId) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM APP.SESSIONENTITY WHERE personId=?";
		PreparedStatement ppstmt;
		ResultSet rs;
		try {
			ppstmt = con.prepareStatement(query);
			ppstmt.setInt(1, personId);
			rs = ppstmt.executeQuery();
			if (rs.next()) {
				return new SessionEntity(rs.getInt(1), rs.getString(2));
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	* This method is used to update the sessionToken of type String every time user log into the system. 
	* It is used for authentication. 
	* It will extract values from the sessionEntity argument and run UPDATE query on SessionEntity table. 
	* The value of sessionToken will change every time user logs into the system.
        * @param  sessionEntity of type SessionEntity.
        * @return Object of SessionEntity.
        */
	@Override
	public SessionEntity updateToken(SessionEntity sessionEntity) {

		String query = "UPDATE APP.SESSIONENTITY SET sessionToken = ? WHERE personId=?";
		PreparedStatement ppstmt;
		try {
			con.setAutoCommit(false);
			ppstmt = con.prepareStatement(query);
			ppstmt.setString(1, sessionEntity.getSessionToken());
			ppstmt.setInt(2, sessionEntity.getPersonId());
			boolean status = ppstmt.execute();
			if (status) {
				con.commit();
				return sessionEntity;
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}