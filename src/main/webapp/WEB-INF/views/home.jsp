<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Tracking System</title>
</head>
<body>

<%
String username=(String)session.getAttribute("userID");  
%>
<div id="welExit">
<h2>Welcome,<% out.println(""+username); %></h2>

<form:form action="logout" method="post">
        <input id="logoutBtn" type="submit" value="Logout" />
</form:form>


<br/><br/><br/><br/><br/><br/><br/>

${status}
</body>
</html>