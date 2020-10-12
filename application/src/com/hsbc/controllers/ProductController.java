package com.hsbc.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.hsbc.Security.SessionManager;
import com.hsbc.dto.EmployeeDTO;
import com.hsbc.dto.ProductFileDTO;
import com.hsbc.exceptions.SessionExpiredException;
import com.hsbc.models.Employee;
import com.hsbc.models.SessionEntity;
import com.hsbc.service.EmployeeService;
import com.hsbc.service.ProductService;

/**
 * Servlet implementation class ProductController
 * 
 * This receives JSON or XML file containing products information to
 * be stored and calls service layer for further processing
 * 
 */
@WebServlet("/product")
@MultipartConfig
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger log = LogManager.getLogger(ProductController.class); 
	private ProductService productService;
	private EmployeeService employeeService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductController() {
        super();
        // TODO Auto-generated constructor stub
        productService = new ProductService();
        employeeService = new EmployeeService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Receives GET request, sets employeee details as request attributes and 
	 * forwards request to importProductsBK.jsp
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		log.info("/product GET request received");
		SessionEntity session = null;
		try {
			session = SessionManager.getSessionData(request);
		} catch (SessionExpiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int employee = session.getPersonId();
		if( employee > 0) {
			EmployeeDTO employeeData = employeeService.getEmployeeDetails(employee);
			request.setAttribute("employee", employeeData);
			
		}
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		request.getRequestDispatcher("importProductsBK.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Receives POST request with file, sets request attributes and forwards the request to importProducts.jsp
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		log.info("/product POST request received");
		ProductFileDTO productFileDTO = productService.addProduct(request.getPart("file"));
		
		request.setAttribute("productFileResponse", productFileDTO);

		request.getRequestDispatcher("./importProducts.jsp").forward(request, response);
		
	}

}
