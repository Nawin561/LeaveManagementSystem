<html xmlns="http://www.w3.org/1999/xhtml">
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Admin Homepage</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700"
	rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/table1.css">
<link href="/css/logout.css" rel="stylesheet" type="text/css"
	media="all" />
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/loaderGif.css" rel="stylesheet">

<script src="/js/jquery-1.10.2.js"></script>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/addEmployee.js"></script>

</head>
<body onload="retrieveManagerNameAjax()">
	<%@ page import="java.util.*"%>
	<%@ page import="com.example.bean.*"%>
	<%
		if (session.getAttribute("sessionEmail") == null)
			response.sendRedirect("/jsp/logOut.jsp");
		if ((session.getAttribute("sessionUserType")).equals("general"))
			response.sendRedirect("/jsp/logOut.jsp");
	%>
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
			<!-- /.container-fluid -->
		</nav>
		<div id="page">
			<div id="content"
				style="min-height: 400px; background-color: white; width: 30%; float: left;">
				<ul class="style2">
					<li class="first">
						<form name="leaveApplication1" method="GET"
							action="/EmployeeOperation/retrieveAllDepartmentName.htm">
							<input type="submit" id="click" name="click" value="Add Employee"
								class="btn btn-default ">
						</form>
					</li>
					<li>
						<h3>
							<form name="form2" method="POST"
								action="/EmployeeOperation/editEmployee.htm">
								<input type="submit" id="click1" name="click1"
									class="btn btn-default" value="Edit Employee">
							</form>
						</h3>
					</li>
					<li>
						<h3>
							<form name="registrationForm" method="POST"
								action="/EmployeeOperation/viewAllEmployeeDelete.htm">
								<input type="submit" value="Delete Employee"
									class="btn btn-default" id="submit">
							</form>
						</h3>
					</li>
					<li>
						<h3>
							<form name="registrationForm" method="POST"
								action="/EmployeeOperation/viewAllEmployeeOnly.htm">
								<input type="submit" value="View All Employees"
									class="btn btn-default" id="submit">
							</form>
						</h3>
				</ul>
			</div>

			<form name="form1">
				<div id="sidebar"
					style="min-height: 350px; background-color: whie; width: 60%; float: left;">
					<div id="tbox1">
						<h3>
							<span class="label label-default">Employee Detail</span>
						</h3>
						<br>
						<table>
							<tr>
								<td>Name</td>
								<td><input type="text" id="employeeName"
									class="form-control" name="employeeName" placeholder="Name"
									required /></td>
								<td><label id="nameError" style="color: #FF0000"></td>
							</tr>



							<tr>
								<td>Team</td>
								<td><select id="team" name="team" class="form-control"
									onfocus="retrieveManagerNameAjax()"
									onchange="retrieveManagerNameAjax()"
									onblur="retrieveManagerNameAjax()">
										<%
											List<DepartmentBeanSpring> list = (List<DepartmentBeanSpring>) request
													.getAttribute("passedObject");
											int i = 1;
											for (DepartmentBeanSpring category : list) {
										%>
										<option value=<%=category.getDepartmentName()%>><%=category.getDepartmentName()%></option>
										<%
											i = i + 1;
											}
										%>
								</select></td>
							</tr>

							<tr>
								<td>Manager/T.Lead</td>
								<td><input class="form-control" type="text"
									id="reportingTo" name="reportingTo" readonly
									placeholder="Manager/TLead"></td>
							</tr>
							<tr>
								<td>Email Address</td>
								<td><input type="email" id="emailAddress"
									name="emailAddress" class="form-control" placeholder="Email"></td>
								<td><label id="emailError" style="color: #FF0000"></td>
							</tr>
							<tr>
								<td>Contact No.</td>
								<td><input class="form-control" type="tel" id="contactNo"
									name="contactNo" maxlength="10" placeholder="Phone No."></td>
								<td><label id="contactError" style="color: #FF0000"></td>
							</tr>
							<tr>
								<td>Casual Leaves</td>
								<td><input type="number" id="cleaves" name="cleaves"
									min="0" max="10" value="5" class="form-control"></td>
								<td><label id="clError" style="color: #FF0000"></td>
							</tr>
							<tr>
								<td>Personal Leaves</td>
								<td><input type="number" id="pleaves" name="pleaves"
									min="0" max="10" value="5" class="form-control"></td>
								<td><label id="pleavesError" style="color: #FF0000"></td>
							</tr>
							<tr>
								<td>Sick Leaves</td>
								<td><input type="number" id="sleaves" name="sleaves"
									min="0" max="10" value="5" class="form-control"></td>
								<td><label id="sicklError" style="color: #FF0000"></td>

							</tr>
							<tr>
								<td>Employee Type</td>
								<td><input type="radio" name="employeeType" value="intern">Intern
									<input type="radio" name="employeeType" value="regular">Regular</td>
								<td><label id="empTypeError" style="color: #FF0000"></td>
							</tr>
						</table>
						<hr>

						<input type="button" class="btn btn-success "
							value="Save Employee" id="submit" onclick="addEmployeeAjax()">
					</div>
					<div id="resultBox" class="alert alert-success">Well done!
						You successfully created the Employee. Check your mailbox!!!</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
