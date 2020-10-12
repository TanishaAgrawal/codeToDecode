<%@page import="com.hsbc.dto.EmployeeDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<title>Import Products</title>
    <link rel="stylesheet" type="text/css" href="bootstrap.css">
    <link rel="stylesheet" type="text/css" href="importProducts.css">
</head>

<body>

    <nav class="navbar fixed-top navbar-dark bg-dark">
        <a class="navbar-brand" href="#"><img
            src="images/LogoRounded.jpg"    
            width="30" height="30" alt="Blogs Home">HOME</a>
    </nav>


    <div style="margin-top: 7%;" class="container">
	
	<%
		EmployeeDTO employeeDTO = (EmployeeDTO)request.getAttribute("employee");
	%>


        <div class="jumbotron">
            <h1 class="display-4">Welcome,<span id="empName"><%=employeeDTO.getUserName()%></span></h1>
            <p class="lead">Upload Files!</p>
            <hr style=" border: 2px solid red;"><br>

        </div>
        <div class="row">

            <form id="file-form">
                <p style="color: black;">Enter JSON/XML file to upload:</p>

                <div class="input-group mb-3">
                    <div class="custom-file">
                        <input type="file" name="file" class="custom-file-input" id="file-input"
                            accept=".xml,.json">
                        <label id="file-input-label" class="custom-file-label" for="file-input">Choose file</label>
                    </div>
                    <div class="input-group-append">
                        <button id="file-submit" class="input-group-text"
                            class="btn btn-danger">Upload</button>

                    </div>
                </div>
            </form>
        </div>
        <div id="file-upload-information">
		
    	</div>

    </div>

    <br>
    <div class="navbar navbar-dark bg-dark footer-content-outer-div">
        <footer>
            <div class="footer-text">
               Copyright &#169; 2020 | CodeFury | codeToDecode | All rights reserved | Handcrafted with &#10084;&#65039;
            </div>
        </footer>
    </div>
	<script>
		document.getElementById('file-input').onchange = function(){
	        document.getElementById('file-input-label').innerHTML = this.files[0].name;
	    }
        document.getElementById('file-submit').addEventListener('click', function (event){
        	event.preventDefault();
        	this.disable = true;
          	var input = document.getElementById('file-input');
            if(input.files.length == 0){
            	alert('Please select a file');
            	document.getElementById("file-upload-information").innerHTML = "";
            	this.disable = false;
            	return;
            }
            
            var data = new FormData();
            data.append('file', input.files[0]);
          	        	
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                	if(xhr.responseText=="sessionexpired"){
                		alert("Session Expired");
                		document.location.href="home.html";
                	}else{
                		alert('File uploaded successfully')
                        document.getElementById("file-upload-information").innerHTML = xhr.responseText;
                	}
                	
                }
                document.getElementById('file-input-label').innerHTML = 'Choose file';
                document.getElementById('file-form').reset();
            }	
            xhr.open('POST', './product', true);
            xhr.send(data);
            this.disable = false;
        })
 
    </script>
</body>

</html>