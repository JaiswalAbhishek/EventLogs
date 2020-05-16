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

<div>
	 <form:form modelAttribute="login" action="event" method="POST">
			<form:input placeholder="Enter your Email ID" path="userName" />
			<form:input placeholder="Enter your password"  path="password" />
			<input type="submit" value="Login"/>
	</form:form>
</div>

</body>
</html>