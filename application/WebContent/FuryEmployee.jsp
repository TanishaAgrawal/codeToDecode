<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Login</title>
    <link rel="stylesheet" href="bootstrap.css" />
    <link rel="stylesheet" href="css/FuryEmployee.css" />

</head>

<body>
<%
		String loginMessage = "";
		loginMessage = (String)request.getAttribute("loginMessage");
	%>
    <nav class="navbar navbar-dark bg-dark">
        <a class="navbar-brand" href="#"><img
          src="images/LogoRounded.jpg"
                width="30" height="30" alt="Blogs Home">HOME</a>
        </nav>
        <div class="row" class="container">
          <div class="column1" id="col1" ></div>
    
    
            <div class="column2" id="col2" >
              <div class="container">
                <div id="customer-employee" >
              <div class="card" style="width: 24rem;">
                <div class="card-body">
            <div class="login-box">
              <h1> Employee Login</h1>
              <form action="./employeeLogin" method="POST" class="employeeLoginForm"> 
                <div class="form-group" class="textbox">
                  <label for="employeeId"></label>
                  <input type="number" class="form-control" id="employeeId" name="employeeId" aria-describedby="emailHelp" placeholder="Employee Id" required>
                </div>
                <div class="form-group" class="textbox">
                  <label for="password"></label>
                  <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
                </div>
                <div class="form-group" >
                <input class="employeebutton" type="submit" value="Log In">
                </div>
    
              </form>
              </div>
              </div>
              </div>
              </div>
              </div>
              
             
    
    
          
          </div>
          <div class="navbar navbar-dark bg-dark footer-content-outer-div">
            <footer>
                <div class="footer-text">Copyright &#169; 2020 | CodeFury |
                    codeToDecode | All rights reserved | Handcrafted with &#10084;&#65039;</div>
            </footer>
        </div>
	<script type="text/javascript">
	var loginMessage = '<%=loginMessage%>';	
	if( loginMessage!='null'){
		alert(loginMessage);
	}
	</script>	
    
</body>
</html>
