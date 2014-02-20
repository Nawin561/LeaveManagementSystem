<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>View all Employees</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700"
	rel="stylesheet" type="text/css">
<link href="/css/logout.css" rel="stylesheet" type="text/css"
	media="all" />
<link href="/css/loaderGif.css" rel="stylesheet">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<script src="/js/jquery-1.10.2.js"></script>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/viewAllEmployee.js"></script>
</head>
<body>
	<%
		if (session.getAttribute("sessionEmail") == null)
			response.sendRedirect("/jsp/logOut.jsp");
		if ((session.getAttribute("sessionUserType")).equals("general"))
			response.sendRedirect("/jsp/logOut.jsp");
	%>
	<%@ page import="java.util.*"%>
	<%@ page import="com.example.bean.*"%>
	<div id="loaderGif">
		<img src="/images/ajax-loader.gif" class="ajax-loader" />
	</div>
	<div id="wrapper">
		<div id="header">
			<div id="logo">

				<h1>
					Welcome <small><%=session.getAttribute("sessionEmail")%></small>
				</h1>


			</div>
		</div>
		<nav class="navbar navbar-inverse" role="navigation">
			<div class="container-fluid  navbar-right">
				<!-- Brand and toggle get grouped for better mobile display -->
				<a href="/jsp/adminHomePage.jsp" class="btn btn-primary"
					accesskey="3" title="">HomePage</a> <a href="#"
					class="btn btn-primary" accesskey="3" title="">Inbox</a> <a
					href="#" class="btn btn-primary" accesskey="4" title="">Outbox</a>
				<form name="leaveApplication1" method="GET" class="btn "
					action="/EmployeeOperation/retrieveAllDepartmentName.htm">
					<input type="submit" class="btn btn-primary" id="click"
						name="click" value="Employee">
				</form>

				<a href="/jsp/addDepartment.jsp" class="btn btn-primary">Department</a>
				<form name="leaveApplication1" method="POST" class="btn"
					action="/EntryExit/logout.htm">
					<input type="submit" id="click" name="click"
						class="btn btn-primary" value="LogOut">
				</form>



			</div>
		</nav>

		<div id="search">
			Search Employee by email/Name <input type="email" id="email"
				name="email" required>
		</div>
		<div id="page">
			<div id="content"
				style="min-height: 400px; background-color: white; width: 20%; float: left;">
				<ul class="style2">
					<li class="first">
						<form name="leaveApplication1" method="GET"
							action="/EmployeeOperation/retrieveAllDepartmentName.htm">
							<input type="submit" id="click" name="click" value="Employee"
								class="btn btn-default ">
						</form>
					</li>
					<li>
						<h3>
							<form name="form2" method="POST"
								action="/EmployeeOperation/editEmployee.htm">
								<input type="submit" id="click1" name="click1"
									class="btn btn-default " value="Edit Employee">
							</form>
						</h3>
					</li>
					<li>
						<h3>
							<form name="registrationForm" method="POST"
								action="/EmployeeOperation/viewAllEmployeeDelete.htm">
								<input type="submit" class="btn btn-default "
									value="Delete Employee" id="submit">
							</form>
						</h3>
					</li>
					<li>
						<h3>
							<form name="registrationForm" method="POST"
								action="/EmployeeOperation/viewAllEmployeeOnly.htm">
								<input type="submit" class="btn btn-default "
									value="View All Employees" id="submit">
							</form>
						</h3>
				</ul>
			</div>
			<div id="sidebar"
				style="min-height: 350px; background-color: whie; width: 60%; float: left;">
				<div id="tbox1">

					<h3>
						<span class="label label-default">Employee Detail</span>
					</h3>
					<div class="table" style="width: 600px;">
						<table id="dataTable" class="table">
							<tr>
								<th>Email</th>
								<th>Name</th>
								<th>Team</th>
								<th>PIC</th>
								<th>Contact</th>
								<th>Type</th>
								<th>CL</th>
								<th>PL</th>
								<th>SL</th>
							</tr>
							<%@ page import="java.util.*"%>
							<%@ page import="com.example.bean.*"%>
							<%
								List<EmployeeBeanString> list = (List<EmployeeBeanString>) request
										.getAttribute("passedObject");
								int i = 1;
								for (EmployeeBeanString category : list) {
							%>
							<tr>
								<td><%=category.getEmail()%></td>
								<td><%=category.getName()%></td>
								<td><%=category.getTeam()%></td>
								<td><%=category.getReportingTo()%></td>
								<td><%=category.getContact()%></td>
								<td><%=category.getEmployeeType()%></td>
								<td><%=category.getCleaves()%></td>
								<td><%=category.getPleaves()%></td>
								<td><%=category.getSleaves()%></td>
							</tr>
							<%
								i = i + 1;
								}
							%>
						</table>
					</div>
				</div>

				<div id="resultBox" class="alert alert-success"></div>
			</div>


		</div>

	</div>
</body>
</html>
