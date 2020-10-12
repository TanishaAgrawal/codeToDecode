package com.hsbc.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.sql.Date;
import java.util.stream.Collectors;

import org.apache.derby.impl.sql.execute.CurrentDatetime;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

import com.hsbc.dao.OrderProcessingDAO;
import com.hsbc.daoImpl.OrderProcessingDAOImpl;
import com.hsbc.exceptions.CompanyNotFoundException;
import com.hsbc.exceptions.CustomerNotFoundException;
import com.hsbc.exceptions.OrderNotFoundForEmployee;
import com.hsbc.exceptions.ProductNotFoundException;
import com.hsbc.models.Company;
import com.hsbc.models.Invoice;
import com.hsbc.models.OrderDetails;
import com.hsbc.models.Product;

public class Caller {

	public static void main(String[] args) {
		System.out.println("Entering in the end game now !!!");

		System.out.println("Ankit is here ...............");
		OrderProcessingDAO emImpl = OrderProcessingDAOImpl.getInstance();

//		//		String query = "INSERT INTO APP.ORDERDETAILS VALUES(?,?,?,?,?,?,?,?,?,?)";
////		String queryOrderProduct = "INSERT INTO APP.ORDERPRODUCTS VALUES(?,?)";
////		Time t = new Time(10);
////		ArrayList<Product> alProducts = new ArrayList<Product>();
////		
////		Product p1 = new Product(30111, "One1", 1.01, "LEVEL 1", new Company("1","1","1","nthikevojaa",t,t), t, t);
////		Product p2 = new Product(30212, "Two1", 2.01, "LEVEL 1", new Company("1","1","1","nthikevojaa",t,t), t, t);
////		Product p3 = new Product(30313, "Three1", 3.01, "LEVEL 1", new Company("1","1","1","nthikevojaa",t,t), t, t);
////		
////		alProducts.add(p1);
////		alProducts.add(p2);
////		alProducts.add(p3);
////		
////		try {
////
////			
////			OrderDetails orderDetails = new OrderDetails(55555, new java.sql.Date(2020, 12, 31), 103, 104,
////					alProducts, 106.06d, 107.07d, "String108", "PENDING", new Time(110), new Time(111));
////
////			PreparedStatement ppstmt = emImpl.con.prepareStatement(query);
////			PreparedStatement ppstmtOrderProduct = emImpl.con.prepareStatement(queryOrderProduct);
////
////			ppstmt.setInt(1, orderDetails.getOrderId());
////			ppstmt.setDate(2, (java.sql.Date) orderDetails.getOrderDate());
////			ppstmt.setInt(3, orderDetails.getCustomerId());
////			ppstmt.setInt(4, orderDetails.getEmployeeId());
////			ppstmt.setDouble(5, orderDetails.getTotalOrderValue());
////			ppstmt.setDouble(6, orderDetails.getShippingCost());
////			ppstmt.setString(7, orderDetails.getShippingCompany());
////			ppstmt.setString(8, orderDetails.getStatus());
////			ppstmt.setTime(9, orderDetails.getCreatedAt());
////			ppstmt.setTime(10, orderDetails.getUpdatedAt());
////	
////			ppstmtOrderProduct.setInt(1, orderDetails.getOrderId());
////			for (Product p : orderDetails.getProducts()) {
////				ppstmtOrderProduct.setInt(2, p.getProductId());
////				ppstmtOrderProduct.execute();
////			}
////
////			ppstmt.execute();
////
////			System.out.println("DONE !!!!!!!!!!!!");
////			System.out.println("OVER AND OUT");
////		} catch (SQLException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		
////		
////		System.out.println(emImpl.approveOrder(55556));
////		System.out.println(emImpl.completeOrder(55556));
////		System.out.println(emImpl.expiryOrder(444));
////		
////		Product[] p = {p1,p2,p3};
////		
////		emImpl.addProductsToDB(p);
////		
////		try {
////			System.out.println(emImpl.getOrdersOfEmployee(104));
////		} catch (OrderNotFoundForEmployee | ProductNotFoundException e) {
////			// TODO Auto-generated catch block
////			System.out.println(e.getMessage());
////		}
//
////		System.out.println("Products : " + emImpl.getProductByProductId(new int[]{1, 3}));
////
////		try {
////			System.out.println(emImpl.getCustomerById(103));
////			System.out.println(emImpl.getProducts());
////			
////			System.out.println(emImpl.approveOrder(55557));
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////			System.out.println(e.getMessage());
////		
////		}
//		
////		try {
////			System.out.println(emImpl.getCustomerById(103));
////		} catch (CustomerNotFoundException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
////		try {
//			
////			System.out.println("invoice : "+ emImpl.getOrdersOfEmployee(104));
////			System.out.println("Exp : " + emImpl.completeOrder());
////			System.out.println("products : " + emImpl.getProducts());
////			System.out.println();
//			
//		
//		System.out.println("new try in product add");
//			Product pr = new Product( 0, "Product201", 101.01, "LEVEL 1", new Company("Company1", "Rajkot1", "Gujarat1", "nthikevojaa", new Time(System.currentTimeMillis()), new Time(System.currentTimeMillis()) ) , new Time(System.currentTimeMillis()), new Time(System.currentTimeMillis()) );
//			Product[] p = {pr, pr, pr};
//			
//			emImpl.addProductsToDB( p );
////			
//////			System.out.println("invoice : "+ emImpl.getInvoiceByOrderId(55556));
////			
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			System.out.println(e.getMessage());
////			e.printStackTrace();
////		} 
//		
////		System.out.println(emImpl.addInvoiceToDB(new Invoice(new OrderDetails(55556, new Date(0, 0, 0), 103, 104, 1321.56, 50.01, "EKART", "PENDING", new Time(0, 0, 0), new Time(0, 0, 0)), 101, new Date(0, 0, 0), "INTER STATE", 1052.16, 649464.65, "UNPAID", new Time(0, 0, 0), new Time(0, 0, 0))));
//		
//		try {
//			System.out.println("Exp : " + emImpl.approveOrder(55555));
//		} catch (OrderNotFoundForEmployee e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ProductNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		try {
////			System.out.println( emImpl.getOrdersOfCustomer(103) );
////			System.out.println( emImpl.getOrdersOfEmployee(104) );
//			
//			System.out.println( emImpl.expiryOrder());
//			
//			
//		} catch (OrderNotFoundForEmployee | ProductNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		try {
			System.out.println(emImpl.expiryOrder());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
