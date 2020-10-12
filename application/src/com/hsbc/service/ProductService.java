package com.hsbc.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hsbc.controllers.GetInvoice;
import com.hsbc.dao.OrderProcessingDAO;
import com.hsbc.daoImpl.OrderProcessingDAOImpl;
import com.hsbc.dto.ProductFileDTO;
import com.hsbc.exceptions.InputStreamEmptyException;
import com.hsbc.exceptions.InvalidFileFormatException;
import com.hsbc.models.Company;
import com.hsbc.models.Product;


public class ProductService {
	private OrderProcessingDAO productDAO;
	private static final Logger log = LogManager.getLogger(ProductService.class); 
	public ProductService() {
		productDAO = new OrderProcessingDAOImpl();
	
	}
	/**
	 * Methods calls method to add XML or JSON object to 
	 * database using the extension of file upload
	 * 
	 * @param file
	 * @return
	 */
	public ProductFileDTO addProduct(Part file) {
		String name = file.getSubmittedFileName();
		String type = name.split("\\.")[1];
		
		switch(type) {
			case "xml":
				try {
					return this.addProductsFromXML(file.getInputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					log.error("Error parsing xml file: " + e.getMessage());
					e.printStackTrace();
				} catch (SAXException e) {
					log.error("Error parsing xml file: " + e.getMessage());
					e.printStackTrace();
				} catch (InputStreamEmptyException e) {
					log.error("Error parsing xml file: " + e.getMessage());
					e.printStackTrace();
				}
				break;
			case "json":
				try {
					return this.addProductsFromJSON(file.getInputStream());
				} catch (IOException e) {
					log.error("Error parsing json file: " + e.getMessage());
					e.printStackTrace();
				} catch (ParseException e) {
					log.error("Error parsing json file: " + e.getMessage());
					e.printStackTrace();
				} catch (InputStreamEmptyException e) {
					log.error("Error parsing json file: " + e.getMessage());
					e.printStackTrace();
				}
				break;
			default:
				try {
					throw new InvalidFileFormatException("File format invalid");
				} catch (InvalidFileFormatException e) {
					log.error("Error in file: " + e.getMessage());
					e.printStackTrace();
				}
				break;
				
		}
		return new ProductFileDTO();
	}
	/**
	 * 
	 *   Method parses XML file using:
	 * 	i. Create a document of XML inputstream using DocumentBuilder
	 *  ii. if document is null DocumentNotFound exception thrown else further process
	 *  iii. List of nodes of XML is obtained in XML file
	 *  iv. Nodes list is iteratively traversed and when node of type ELEMENT_NODE encountered, information
	 *  	is retrieved and an object of Product is created
	 *  v. ProductDAO's method is called to store the object to DB 
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws InputStreamEmptyException
	 */
	private ProductFileDTO addProductsFromXML(InputStream inputStream) throws IOException, ParserConfigurationException, SAXException, InputStreamEmptyException {
		if(inputStream == null) {
			throw new InputStreamEmptyException("InputStream cannot be empty");
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputStream);
		
		int successCount = 0;
		int failedCount = 0;
		
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		List<Product> products = new ArrayList<Product>();
		for(int i = 0; i < nodeList.getLength(); i++) {
			
			Node node = nodeList.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				
				Product product = processXMLObject(node);
				if(product == null)
					failedCount++;
				else {
					successCount++;
					products.add(product);
				}

				
			}
		}
		Product[] productsArr = new Product[products.size()];
		int i = 0;
		for(Product product: products) {
			productsArr[i] = product;
			i++;
		}
		productDAO.addProductsToDB(productsArr);
		
		log.info("Success Count: " + successCount);
		log.info("Failure Count: " + failedCount);
		ProductFileDTO productFileDTO = new ProductFileDTO(successCount, failedCount);
		
		return productFileDTO;
		
	}
	/**
	 * Method parses individual node of xml element to retrieve the information
	 * 
	 * @param node
	 * @return
	 */
	private Product processXMLObject(Node node) {
		Element element = (Element) node;
		int productId = 0;
		String productName = "";
		double productPrice = 0;
		String productCategory = "";
		String gstNumber = "";
	
		try {
			productId  = Integer.parseInt(element.getElementsByTagName("ProductId")
											.item(0).getChildNodes().item(0).getNodeValue());
			productName = element.getElementsByTagName("ProductName")
											.item(0).getChildNodes().item(0).getNodeValue();
			productPrice = Double.parseDouble(element.getElementsByTagName("ProductPrice")
											.item(0).getChildNodes().item(0).getNodeValue());
			productCategory = element.getElementsByTagName("ProductCategory")
											.item(0).getChildNodes().item(0).getNodeValue();
			gstNumber = element.getElementsByTagName("GstNumber")
										.item(0).getChildNodes().item(0).getNodeValue();
		}catch(NullPointerException e) {
			log.error("Error parsing XML Object: " + e.getMessage());
			return null;
		}
		if(productId <= 0 || productName == null || productName == "" || productCategory==null || productCategory=="" || productPrice <= 0 )
			return null;
		
		System.out.println("[ " + productId + ", " + productName + ", " + productPrice + ", " + productCategory + ", " + gstNumber + " ]");
		Company company = new Company();
		company.setGstNumber(gstNumber);
		Product productObj = new Product((int)productId, productName, productPrice, productCategory, company, new Time(System.currentTimeMillis()), new Time(System.currentTimeMillis()));
		
		return productObj;
		
	}
	/**
	 *  Method parses JSON file using JSONParser by:
	 * 	i. Calling parse() on inputstream
	 * 	ii. Creation of JSONArray of objects on JSON file
	 * 	iii. Individual object of JSON is then used to extract different fields and 
	 * 		Product object is created out it
	 * 	iv. ProductDAO's method is called to store object to DB
	 * 
	 * @param inputstream
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws InputStreamEmptyException
	 */
	private ProductFileDTO addProductsFromJSON(InputStream inputstream) throws IOException, ParseException, InputStreamEmptyException {
		if(inputstream == null) {
			throw new InputStreamEmptyException("InputStream cannot be empty");
		}
		JSONParser parser = new JSONParser();
		
		int successCount = 0;
		int failedCount = 0;
		
		InputStreamReader inputStreamReader = new InputStreamReader(inputstream);
		Object obj = parser.parse(inputStreamReader);
		JSONArray json = (JSONArray)obj;
		List<Product> products = new ArrayList<Product>();
		for(Object product : json) {
			Product productObj = this.processJSONObject((JSONObject)product);
			
			if(productObj == null)
				failedCount++;
			else {
				successCount++;
				products.add(productObj);
			}

		}
		Product[] productsArr = new Product[products.size()];
		int i = 0;
		for(Product product: products) {
			productsArr[i] = product;
			i++;
		}
		productDAO.addProductsToDB(productsArr);
		
		log.info("Success Count: " + successCount);
		log.info("Failure Count: " + failedCount);
		
		ProductFileDTO productFileDTO = new ProductFileDTO(successCount, failedCount);
		
		return productFileDTO;
		
	}
	/**
	 * Method parses JSON object to extract fields and created an object out of it
	 * 
	 * @param jsonObject
	 * @return
	 */
	private Product processJSONObject(JSONObject jsonObject) {
		JSONObject product = (JSONObject) jsonObject;
		long productId = 0;
		String productName = "";
		double productPrice = 0;
		String productCategory = "";
		String gstNumber = "";
		try {
			productId = (Long)product.get("productId");
			productName = (String)product.get("productName");
			productPrice = (Double)product.get("productPrice");
			productCategory = (String)product.get("productCategory");
			gstNumber = (String)product.get("gstNumber");
		}catch(Exception e) {
			log.error("Error parsing JSON Object: " + e.getMessage());
			return null;
		}
		if(productId <= 0 || productName == null || productPrice <= 0 || productCategory == null || productCategory == "" || gstNumber == null || gstNumber == "")
			return null;
		
		System.out.println("[ " + productId + ", " + productName + ", " + productPrice + ", " + productCategory + ", " + gstNumber + " ]");
		Company company = new Company();
		company.setGstNumber(gstNumber);
		Product productObj = new Product((int)productId, productName, productPrice, productCategory, company, new Time(System.currentTimeMillis()), new Time(System.currentTimeMillis()));
		return productObj;
		
	}
}