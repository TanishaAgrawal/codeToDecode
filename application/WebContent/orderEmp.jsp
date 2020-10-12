<%@page import="java.sql.Date"%>
<%@page import="com.hsbc.dto.CustomerDTO"%>
<%@page import="java.util.Map"%>
<%@page import="com.hsbc.models.OrderDetails"%>
<%@page import="java.util.List"%>
<%@page import="com.hsbc.dto.EmployeeDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <title>Order Management Employee</title>
   
    <link rel="stylesheet" type="text/css" href="bootstrap.css">
    <link rel="stylesheet" type="text/css" href="orderEmp.css">
</head>

<body>

    <nav class="navbar fixed-top navbar-dark bg-dark">
        <a class="navbar-brand" href="#"><img
            src="images/LogoRounded.jpg"    
            width="30" height="30" alt="Blogs Home">HOME</a>
    </nav>
    <%
		EmployeeDTO employeeDTO = (EmployeeDTO)request.getAttribute("employee");
	%>
    
    <div style="margin-top: 7%;" class="container">
        <div class="jumbotron">
            <h1 class="display-4">Welcome,&nbsp;<span id="empName"><%=employeeDTO.getUserName() %></span></h1>
            <p class="lead">Find all your order related stuff in one place!</p>
            <hr style=" border: 2px solid red;"><br>
           
        </div>
    </div>
    <div class="empdetails">

        <p>Employee ID: <span class="blc" id="empId"><%=employeeDTO.getEmployeeId() %></span></p>
        <p>Employee Username: <span class="blc" id="empUsername"><%=employeeDTO.getUserName() %></span></p>
        <p>Last Logged in at: <span class="blc" id="empLastLogin"><%=employeeDTO.getLastLogin() %></span></p>

    </div>
    <hr id="h">
    <div class="table-responsive-sm">
        <table class="table table-hover">
            <thead class="thead-light">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Order ID</th>
                    <th scope="col">Customer Name</th>
                    <th scope="col">Order Date</th>
                    <th scope="col">Order Value</th>
                    <th scope="col">Customer City</th>
                    <th scope="col">Status</th>
                    <th scope="col">Invoice</th>
                </tr>
            </thead>
            <tbody>
           <%
            	List<OrderDetails> orderDetails = employeeDTO.getOrderDetails();
            	Map<Integer,CustomerDTO> customers = employeeDTO.getCustomers(); 
            	for(int i = 0; i < orderDetails.size(); i++){
            		int orderId = orderDetails.get(i).getOrderId();
            		String customerName = customers.get(orderDetails.get(i).getCustomerId()).getName();
            		String city = customers.get(orderDetails.get(i).getCustomerId()).getCity();
            		double orderValue = orderDetails.get(i).getTotalOrderValue();
            		Date orderDate = orderDetails.get(i).getOrderDate();
            		String status = orderDetails.get(i).getStatus();
            		String disabled = "disabled";
            		if(orderDetails.get(i).getStatus().equals("COMPLETED"))
            			disabled = "";
            
            %>
                <tr>
                    <th scope="row"><%=(i+1) %></th>
                    <td class="orderId"><%=orderId%></td>
                    <td class="custName"><%=customerName %></td>
                    <td class="orderDate"><%=orderDate %></td>
                    <td class="orderValue"><%=orderValue %></td>
                    <td class="custCity"><%=city %></td>
                    <td class="status"><%=status %></td>
					<td id="invoice"><button type="button"
		                                    onclick="onLoad('<%=orderId%>')" class="btn btn-danger" <%=disabled%>>View Invoice</button></a></td>
		                                    
		                                    
                </tr>
                <%} %>
            
            </tbody>
        </table>
    </div>
    </div>

    <hr id="h">

    
    <button onclick="addNewQuote(event)" class="btn btn-primary" style="padding-left: 20px;margin-left:14px;">Add New Quote</button>
   
    <button onclick="addProduct(event)" class="btn btn-primary" style="padding-left: 20px;margin-left:14px;">Import Products</button>
    <br><br>

    <div class="navbar navbar-dark bg-dark footer-content-outer-div">
        <footer>
            <div class="footer-text">
               Copyright &#169; 2020 | CodeFury | codeToDecode | All rights reserved | Handcrafted with &#10084;&#65039;
            </div>
        </footer>
    </div>
	 <script>
      
            function onLoad(orderId){
            	document.location.href="./getInvoice?orderId="+orderId;
            }
            function addNewQuote(event){
            	event.preventDefault();
            	window.location.href = './getAddQuote';
            }
            function addProduct(){
            	event.preventDefault();
            	window.open('./product', '_blank').focus();
            }
        	
    </script>
</body>
</html>