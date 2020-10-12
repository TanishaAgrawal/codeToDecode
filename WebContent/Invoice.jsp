<%@page import="com.hsbc.models.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Import Products</title>
<script>
	function showStatusOfOperations() {
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && xhr.status == 200) {
				document.getElementById("displayinvoice").innerHTML = xhr.responseText;
			}
		}
		xhr.open('GET', './demo', true);
		xhr.send();
	}
</script>
<link rel="stylesheet" href="bootstrap.css">

<link rel="stylesheet" type="text/css" href="Invoice.css">
</head>

<body>

	<nav class="navbar fixed-top navbar-dark bg-dark">
		<a class="navbar-brand" href="#"><img src="LogoRounded.jpg"
			width="30" height="30" alt="Blogs Home"> HOME</a>
	</nav>


	<div style="margin-top: 7%;" class="container">



		<div class="jumbotron">
			<h1 class="display-4">Invoice Details</h1>

			<hr style="border: 2px solid red;">
			<br>

		</div>

		<%
			Invoice inv = (Invoice) request.getAttribute("inv");
		%>



		<div>
			<p>
				<b>Invoice ID: <%=inv.getInvoiceId()%></b>
			</p>
			<p>
				<b>Invoice Date: <%=inv.getInvoiceDate()%></b>
			</p>
			<p>
				<b>Customer ID: <%=inv.getOrderDetails().getCustomerId()%></b>
			</p>
			<p>
				<b>Order ID: <%=inv.getOrderDetails().getOrderId()%></b>
			</p>
			<p>
				<b>GST Type: <%=inv.getGstType()%></b>
			</p>
			<p>
				<b>GST Amount: <%=inv.getGstAmount()%></b>
			</p>
			<p>
				<b>Total Invoice Amount: <%=inv.getTotalInvoiceAmount()%></b>
			</p>
			<p>
				<b>Payment Status: <%=inv.getInvoiceStatus()%></b>
			</p>
			<br>
		</div>


		<div class="table-responsive-sm">
			<table class="table table-hover">
				<thead class="thead-light">
					<tr>

						<th scope="col" style="text-align: center">Sr. No.</th>
						<th scope="col" style="text-align: center">Product ID</th>
						<th scope="col" style="text-align: center">Items</th>
						<th scope="col" style="text-align: center">Cost</th>
					</tr>
				</thead>
				<tbody>

					<%
						int x = 0;
						OrderDetails orderDetails = (OrderDetails) inv.getOrderDetails();
						ArrayList<Product> list = orderDetails.getProducts();
						
						for (Product actor : list) {
							x++;
					%>
					<tr>
						<th scope="row"><%=x%>
						<td id="orderId"><%=actor.getProductId()%></td>
						<td id="orderDate"><%=actor.getProductName()%></td>
						<td id="shippingCost"><%=actor.getProductPrice()%></td>

						</th>
					</tr>
					<%
						}
						;
					%>
				</tbody>
			</table>

		</div>
	</div>
	<div class="navbar navbar-dark bg-dark footer-content-outer-div">
		<footer>
			<div class="footer-text">Copyright &#169; 2020 | CodeFury |
				codeToDecode | All rights reserved | Handcrafted with
				&#10084;&#65039;è</div>
		</footer>
	</div>
</body>
</html>




