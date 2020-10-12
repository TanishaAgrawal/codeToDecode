<%@page import="com.hsbc.Security.SessionManager"%>
<%@page import="com.hsbc.models.Product"%>
<%@page import="java.util.List"%>
<%@page import="com.hsbc.models.Customer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Quote</title>
<link rel="stylesheet" href="bootstrap.css" />
<link rel="stylesheet" href="quote.css" />
</head>

<body>

	<nav class="navbar navbar-dark bg-dark">
		<a class="navbar-brand" href="#"><img
			src="images/LogoRounded.jpg"
			width="30" height="30" alt="Blogs Home">HOME</a>
	</nav>
	<!--This for card layout structure-->

	<div class="card" style="width: 100%;">
		<form>

			<div class="card-body">
				<h5 class="card-title">Get a Quote!</h5>
				<p class="card-text">Please enter details!</p>


				<div class="form-group row">
					<label for="quoteOrderDate" class="col-sm-2 col-form-label">Order
						Date:</label>
					<div class="col-sm-10">
						<input type="date" class="form-control" id="quoteOrderDate"
							aria-describedby="dateHelp" placeholder="Order Date"
						>
					</div>
				</div>

				<div class="form-group row">
					<label for="quoteUserId" class="col-sm-2 col-form-label">User
						Id:</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="quoteUserId"
							onchange="getCustomerData()" placeholder="Enter user id" required>
					</div>
				</div>


				<div class="form-group row">
					<label for="quoteGSTNumber" class="col-sm-2 col-form-label">GST
						Number:</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="quoteGSTNumber"
							placeholder="GST Number">
					</div>
				</div>


				<div class="form-group row">
					<label for="quoteShippingAddress" class="col-sm-2 col-form-label">Shipping
						Address:</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="quoteShippingAddress"
							disabled required>
					</div>
				</div>


				<div class="form-group row">
					<label for="quoteCity" class="col-sm-2 col-form-label">City:</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="quoteCity" disabled
							required>
					</div>
				</div>


				<div class="form-group row">
					<label for="quotePhoneNumber" class="col-sm-2 col-form-label">Phone
						Number:</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="quotePhoneNumber"
							disabled required>
					</div>
				</div>


				<div class="form-group row">
					<label for="quoteEmail" class="col-sm-2 col-form-label">E-mail:</label>
					<div class="col-sm-10">
						<input type="email" class="form-control" id="quoteEmail" disabled
							required>
					</div>

				</div>


				<div class="form-group row">
					<label for="quotePincode" class="col-sm-2 col-form-label">Pincode:</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="quotePincode" disabled
							required>
					</div>
				</div>


				<div class="form-group row">
					<div class="col-sm-10 offset-sm-2">
						<button type="submit" id="getProductsButton"
							class="btn btn-primary">Get Products</button>

					</div>

				</div>
				<div id="addMoreProductsDiv">
					<div class="section-heading">Choose product(s):</div>

					<table>
						<thead>
							<tr>
								<th></th>
								<th>Product</th>
								<th>Product Category</th>
								<th>Product Price (&#x20B9;)</th>
							</tr>
							<tr>
						</thead>
						<tbody id="productsDataTable">

						</tbody>

					</table>



					<div class="form-group row">
						<div class="col-sm-10 offset-sm-2">
							<button id="computeCosts" class="btn btn-primary">Compute
								Costs</button>
						</div>
					</div>


					<div id="calculatedCosts">
						<div class="form-group row">
							<label for="totalOrderValue" class="col-sm-3 col-form-label">Total
								Order Value&nbsp;(&#x20B9;): </label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="totalOrderValue"
									value="" disabled required>
							</div>
						</div>
						<div class="form-group row">
							<label for="shippingCost" class="col-sm-3 col-form-label">Shipping
								Cost&nbsp;(&#x20B9;):</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="shippingCost"
									value="" disabled required>
							</div>
						</div>


					</div>
				</div>
				<button id="generatequoteButton" class="btn btn-success">Generate
					Quote</button>
			</div>
		</form>

	</div>
	<div class="navbar navbar-dark bg-dark footer-content-outer-div">
		<footer>
			<div class="footer-text">Copyright &#169; 2020 | CodeFury |
				codeToDecode | All rights reserved | Handcrafted with
				&#10084;&#65039;</div>
		</footer>
	</div>
	<script>

  // document.getElementById('quoteOrderDate').value = new Date().toISOString().split('T')[0];

  document.getElementById('generatequoteButton').addEventListener('click', function(event){

    event.preventDefault();
    this.disabled = true;

    var orderDate = document.getElementById('quoteOrderDate').value;
    var customerId = document.getElementById('quoteUserId').value;
    var gstNumber = document.getElementById('quoteGSTNumber').value;
    var shippingAddress = document.getElementById('quoteShippingAddress').value;
    var city = document.getElementById('quoteCity').value;
    var phoneNumber = document.getElementById('quotePhoneNumber').value;
    var email = document.getElementById('quoteEmail').value;
    var pincode =  document.getElementById('quotePincode').value;
    var productsIdObj = document.querySelectorAll('input[name=products]:checked');
    var productIds = []
    productsIdObj.forEach(productId =>{
      productIds.push(productId.value);
    })
    var productIds = productIds.join(",");
    var totalOrderValue = document.getElementById('totalOrderValue').value;
    var shippingCost = document.getElementById('shippingCost').value;
    var employeeId = <%=SessionManager.getSessionData(request).getPersonId()%>;
	
    if(orderDate.trim() == ''|| customerId.trim() == '' || shippingAddress.trim() == '' || city.trim() == '' 
      || phoneNumber.trim() == '' || email.trim() == '' || pincode.trim() == '' || productIds == null 
      || productIds.trim() == '' || totalOrderValue.trim(0) == '' || shippingCost.trim() == '' || employeeId.trim == ''){
        alert('Fill the fields correctly');
        this.disabled = false;
        return;
    }
	var date = new Date;
	var orderD = new Date(orderDate);
	if(date.getDate() !== orderD.getDate() || date.getMonth() !== orderD.getMonth() || date.getFullYear() !== orderD.getFullYear()){
		alert('Order date should be today\'s date');
		this.disabled = false;
		return;
	}
    
      var xhr = new XMLHttpRequest();
      xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
        	if(xhr.responseText=="sessionexpired"){
        		alert("Session Expired");
        		document.location.href="home.html";
        		}
        	else{
        		alert('Data submitted successfully')
                history.go(-1)
			}
          
         
        }
        else if(xhr.readyState == 4 && xhr.status !== 200){
          alert('Form cannot be submitted')
        }
        
      }
      // var requestObj = {
      //   'orderDate': orderDate,
      //   'customerId': customerId,
      //   'gstNumber': gstNumber,
      //   'shippingAddress': shippingAddress,
      //   'city': city,
      //   'phoneNumber': phoneNumber,
      //   'email': email,
      //   'pincode': pincode,
      //   'productIds': productIds,
      //   'totalOrderValue': totalOrderValue,
      //   'shippingCosts': shippingCost,
      //   'employeeId': employeeId
      // }

      var obj = '?orderDate=' + orderDate + '&customerId=' + customerId + '&gstNumber=' + gstNumber + '&shippingAddress=' + shippingAddress + 
                '&city=' +  city + '&phoneNumber=' + phoneNumber + '&email=' + email + '&pincode=' + '&productIds=' + productIds + 
                '&totalOrderValue=' + totalOrderValue + '&shippingCosts=' + shippingCost + '&employeeId=' + employeeId;
      

      xhr.open('POST','./ProductQuoteSubmit' + obj, true);
      xhr.send();
      this.disabled = false;
  })
    
	function getCustomerData(){
      var xhr = new XMLHttpRequest();
      xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
        	if(xhr.responseText=="sessionexpired"){
        		alert("Session Expired");
        		document.location.href="home.html";
        		}else{
        			 var response = JSON.parse(xhr.responseText);
        	          renderCustomerData(response)
        		}
         
        } 
        
      }

      customerId = document.getElementById('quoteUserId').value;
      if(parseInt(customerId) > 0){ 
        xhr.open('GET', './ProductQuote1?customerId=' + customerId, true);
        xhr.send();
      }else{
        alert('Invalid user id');
      }
      this.disabled = false;
 }
 
 function renderCustomerData(customer){
    document.getElementById('quoteGSTNumber').value = customer['gstNumber'];
    document.getElementById('quoteShippingAddress').value = customer['address'];
    document.getElementById('quoteCity').value = customer['city'];
    document.getElementById('quotePhoneNumber').value = customer['phone'];
    document.getElementById('quoteEmail').value = customer['email'];
    document.getElementById('quotePincode').value = customer['pincode'];
 }


 document.getElementById('getProductsButton').addEventListener('click', function(event){
     event.preventDefault();
     this.disabled = true;
      var xhr = new XMLHttpRequest();
      xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
        	if(xhr.responseText=="sessionexpired"){
        		alert("Session Expired");
        		document.location.href="home.html";
        		}else{
        			var productResponse = JSON.parse(xhr.responseText);
        	          renderProducts(productResponse);
        		}
          
        }  
      }

      // var productsIds = document.querySelectorAll('input[name=products]:checked');
      xhr.open('GET', './getProducts', true);
      xhr.send();
      this.disabled = false;
 })
	
 function renderProducts(products){
      var productsHtml = '';
    
      for(var indx in products){
          productsHtml += 
                '<tr>' +  
                  '<td>' +  
                    '<input type="checkbox" name="products" value=' + products[indx]['productId'] +'>' +
                  '</td>' + 
                  '<td>' +
                    '<label for ="extraProductsList">' + products[indx]['productName'] + '</label>' + 
                  '</td>' + 
                  '<td>' + products[indx]['productCategory'] + '</td>' + 
                  '<td>' + products[indx]['productPrice'] + '</td>'+
                '</tr>'             
          
      }

      document.getElementById('productsDataTable').innerHTML = productsHtml;
      document.getElementById('addMoreProductsDiv').style.display = "block";
 }
 

 document.getElementById('computeCosts').addEventListener('click', function(event){
       event.preventDefault();
      this.disabled = true;
      var xhr = new XMLHttpRequest();
      xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
        	if(xhr.responseText=="sessionexpired"){
        		alert("Session Expired");
        		document.location.href="home.html";
        		}else{
        			var response = JSON.parse(xhr.responseText)
        	         
        	          document.getElementById('totalOrderValue').value = response['totalOrderValue']
        	          document.getElementById('shippingCost').value = response['shippingCost']
        		}
          
        }  
        
      }
      
      var productsIdObj = document.querySelectorAll('input[name=products]:checked');
      if(productsIdObj.length == 0){
        alert('Please select some product');
        this.disabled = false;
        return;
      }
      var productIds = []
      productsIdObj.forEach(productId =>{
        productIds.push(productId.value);
      })
      var param = productIds.join(",");
      xhr.open('GET', './ProductQuote2?productIds=' + param, true);
      xhr.send();
      this.disabled = false;
 })

</script>
</body>
</html>