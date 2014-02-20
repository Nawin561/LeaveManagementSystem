<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<script src="jquery-1.10.2.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring Project</title>

</head>
<body>
	<h1>
		Welcome
		</h1>
	<form name="form1" method="POST" action="/EmployeeOperation/deleteEmployee.htm">
		<br>
		<h2>Employee Details</h2>
		<table bgcolor="gray">
		<tr>
		<th>Email</th>
		<th>Name</th>
		<th>Team</th>
		<th>PIC</th>
		<th>Contact</th>
		<th>Employee Type</th>
		<th>Casual Leaves</th>
		<th>Personal Leaves</th>
		<th>Sick leaves</th>
		</tr><tr>
			<%@ page import="java.util.*"%>
			<%@ page import="com.example.bean.*"%>
			<%
				List<EmployeeBeanString> list = (List<EmployeeBeanString>) request.getAttribute("passedObject");
				int i = 1;
				for (EmployeeBeanString category : list) {%>
			<tr>
				<td><input type="checkbox" id=<%=category.getEmail()%> value=<%=category.getEmail() %>
					name="rowrecord" /><%=category.getEmail()%></td>
				<td><%=category.getName()%></td>
				<td><%=category.getTeam()%></td>
				<td><%=category.getReportingTo()%></td>
				<td><%=category.getContact()%></td>
				<td><%=category.getEmployeeType()%></td>
				<td><%=category.getCleaves()%></td>
				<td><%=category.getPleaves()%></td>
				<td><%=category.getSleaves()%></td>
				
			</tr>
			<%i = i+ 1;}%>
		</table>
		<input type="submit" id="click" name="click" value="Delete Record">
		
		<div id="result"></div>
	</form>
	<br>
	
	
	
	
</body>
</html>