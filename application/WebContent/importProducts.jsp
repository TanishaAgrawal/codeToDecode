<%@page import="com.hsbc.dto.ProductFileDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<div id="file-upload-information">
	<% 
		ProductFileDTO p = ((ProductFileDTO)request.getAttribute("productFileResponse")); 
		if(p != null){
          	out.println("<p>Status of Order: <span id=" + "status" + ">Completed</span></p>");
           	out.println("<p>Number of products imported:<span id=" + "numProducts" + ">" + p.getSuccessCount() + "</span></p>");
           	out.println("<p>Number of products not imported:<span id=" + "numProdNotImp" + ">" + p.getFailedCount()  + "</span></p>");
		}   
      %>
</div>
