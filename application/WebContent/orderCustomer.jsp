<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"  import ="java.util.ArrayList,com.hsbc.models.Customer,java.util.List,com.hsbc.models.OrderDetails"%>
<!DOCTYPE html>
<html lang="en">

	<%
        List<OrderDetails> list = new ArrayList();
        list = (List<OrderDetails>) request.getAttribute("orderList");
        Customer customer = (Customer)request.getAttribute("customerData");
        ArrayList<OrderDetails> completed= new ArrayList();
        ArrayList<OrderDetails> pendingList = new ArrayList();
        ArrayList<OrderDetails> approvedList = new ArrayList();
        for(OrderDetails a:list){
        	if(a.getStatus().toLowerCase().equals("approved")){
        		approvedList.add(a);
        	}else if(a.getStatus().toLowerCase().equals("completed")){
        		completed.add(a);
        	}else{
        		pendingList.add(a);
        	}
        }
	%>
	
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Customer</title>
    <script>
    	
        function toApprove(orderId) {
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                	if(xhr.responseText=="sessionexpired"){
                		alert("Session Expired");
                		document.location.href="home.html";
                	}else if(xhr.responseText=="error"){
                		alert("Order with orderID: "+orderId+" Unable to find the Order or Some Products in the order contact to our Customer Care Service");
                		document.getElementById("displayquote"+orderId).innerHTML = 'Technical Error';
                        document.getElementById("displayquote"+orderId).disabled = true; 
                	}else{//do what so ever you want to do
                		document.getElementById("displayquote"+orderId).innerHTML = xhr.responseText;
                        document.getElementById("displayquote"+orderId).disabled = true; 
                	}
                    
                }
                
            }
            xhr.open('GET', './approveOrder?orderId='+orderId, true);
            xhr.send();
        }
        function onLoad( orderId){
        	document.location.href="./getInvoice?orderId="+orderId;
        }
        
        
    </script>
    <link rel="stylesheet" type="text/css" href="bootstrap.css">
    <link rel="stylesheet" type="text/css" href="orderCustomer.css">
</head>

<body>

    <nav class="navbar fixed-top navbar-dark bg-dark">
        <a class="navbar-brand" href="#"><img
                src="images/LogoRounded.jpg"
                width="30" height="30" alt="Blogs Home">HOME</a>
    </nav>


    <div style="margin-top: 7%;" class="container">

        <div class="jumbotron">
            <h1 class="display-4">Welcome,&nbsp;<span id="custName"><%=customer.getName() %></span></h1>
            <p class="lead">Dashboard</p>
            <hr style=" border: 2px solid red;"><br>
        </div>

        <div class="custdetails">

            <p>Customer Name: <span class="blc" id="custName"><%=customer.getName() %></span></p>
            <p>Customer ID: <span class="blc" id="custId"><%=customer.getCustomerId() %></span></p>
            <p>GST: <span class="blc" id="custGst"><%=customer.getGstNumber() %></span></p>
            <p>Address: <span class="blc" id="custAdd"><%=customer.getAddress() %></span></p>
            <p>City: <span class="blc" id="custCity"><%=customer.getCity() %></span></p>
            <p>Email ID: <span class="blc" id="custEmail"><%=customer.getEmail() %></span></p>
            <p>Phone: <span class="blc" id="custPhone"><%=customer.getPhone()%></span></p>
            <p>Pincode: <span class="blc" id="custPin"><%=customer.getPincode()%></span></p>
            <p>Last Logged in at: <span class="blc" id="custLastLogin"><%=customer.getLastLogin() %></span></p>

        </div>

        <hr id="h">

        <p><b>Your Orders:</p></b>
        

        <div class="table-responsive-sm">
            <table class="table table-hover">
                <thead class="thead-light">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Order ID</th>
                        <th scope="col">Order Date</th>
                        <th scope="col">Shipping Cost</th>
                        <th scope="col">Total Order Value</th>
                        <th scope="col">Status</th>
                        <th scope="col">Invoice</th>
                    </tr>
                </thead>

                <tbody>
                	<%int x = 0; 
                	for(OrderDetails actor:completed) {
                		x++;
            		%>
            		
		            <tr>
		            	<th scope="row"><%=x %></th>
		                        <td id="orderId"><%=actor.getOrderId() %> </td>
		                        <td id="orderDate"><%= actor.getOrderDate()%></td>
		                        <td id="shippingCost"><%=actor.getShippingCost()%></td>
		                        <td id="orderValue"><%=actor.getTotalOrderValue() %></td>
		                        <td id="status"><%=actor.getStatus() %></td>
		                        <td id="invoice"><button type="button"
		                                    onclick="onLoad('<%=actor.getOrderId() %>')" class="btn btn-danger">View Invoice</button></a></td>
	               </tr>
		            <%
		            };
		            %>
                </tbody>
                 <tbody>
                	<% for(OrderDetails actor:approvedList) {
                		x++;
            		%>
            		
		            <tr>
		            	<th scope="row"><%=x %></th>
		                        <td id="orderId"><%=actor.getOrderId() %> </td>
		                        <td id="orderDate"><%= actor.getOrderDate()%></td>
		                        <td id="shippingCost"><%=actor.getShippingCost()%></td>
		                        <td id="orderValue"><%=actor.getTotalOrderValue() %></td>
		                        <td id="status"><%=actor.getStatus() %></td>
		                        
		            </tr>
		            <%
		            };
		            %>
                </tbody>


            </table>
        </div>

        <p><b>A List of Quotes Pending for Approval:</p></b>

        <div class="table-responsive-sm">
            <table class="table table-hover">
                <thead class="thead-light">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Order ID</th>
                        <th scope="col">Order Date</th>
                        <th scope="col">Shipping Cost</th>
                        <th scope="col">Total Order Value</th>
                        <th scope="col">Approval</th>
                    </tr>
                </thead>
				<tbody>
						
                	<%int y = 0; 
                	for(OrderDetails actor:pendingList) {
                		y++;	
            		%>
            		
		            <tr>
		            	<th scope="row"><%=y %></th>
		                        <td id="orderId"><%=actor.getOrderId() %> </td>
		                        <td id="orderDate"><%= actor.getOrderDate()%></td>
		                        <td id="shippingCost"><%=actor.getShippingCost()%></td>
		                        <td id="orderValue"><%=actor.getTotalOrderValue() %></td>
		                        <td id="approve"><button type="button" id="displayquote<%=actor.getOrderId() %>"
                                onclick="toApprove('<%=actor.getOrderId() %>')" class="btn btn-success">Approve</button></a> </td>
	               </tr>
		            <%
		            };
		            %>
                </tbody>
            </table>
        </div>




    </div>

    
		<div class="navbar navbar-dark bg-dark footer-content-outer-div">
			<footer>
				<div class="footer-text">Copyright &#169; 2020 | CodeFury |
					codeToDecode | All rights reserved | Handcrafted with &#10084;&#65039;</div>
			</footer>
		</div>
</body>

</html>