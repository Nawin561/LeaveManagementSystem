<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Edit Department Detail</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700"
	rel="stylesheet" type="text/css">
<link href="/css/logout.css" rel="stylesheet" type="text/css"
	media="all" />
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/loaderGif.css" rel="stylesheet">

<script src="/js/jquery-1.10.2.js"></script>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/editDepartment.js"></script>

<title>Edit Department Detail</title>

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

		<div id="page">
			<div id="content"
				style="min-height: 400px; background-color: white; width: 20%; float: left;">
				<ul class="style2">
					<li class="first">
						<h3>
							<a href="/jsp/addDepartment.jsp" class="btn btn-default ">Add
								Departments</a>
						</h3>
					</li>
					<li><h3>
							<form name="form5" method="POST"
								action="/DepartmentOperation/editDepartment.htm">
								<input type="submit" id="click1" name="click1"
									class="btn btn-default " value="Edit Department">
							</form>
						</h3></li>
					<li>
						<h3>
							<form name="form4" method="POST"
								action="/DepartmentOperation/viewAllDepartmentDelete.htm">
								<input type="submit" id="click1" name="click1"
									class="btn btn-default " value="Delete Department">
							</form>
						</h3>
					</li>
					<li>
						<h3>
							<form name="form4" method="POST"
								action="/DepartmentOperation/viewAllDepartmentOnly.htm">
								<input type="submit" id="click1" name="click1"
									class="btn btn-default " value="View All Department">
							</form>
						</h3>
					</li>
				</ul>
			</div>
			<form name="form1">
				<div id="sidebar"
					style="min-height: 350px; background-color: whie; width: 60%; float: left;">
					<div id="tbox1">
						<!-- 					<form name="form1" method="POST" -->
						<!-- 						action="/DepartmentOperation/updateDepartment.htm"> -->
						<h3>
							<span class="label label-default">All Departments</span>
						</h3>
						<table id="dataTable" class="table">
							<tr>
								<th>Department Name</th>
								<th>Manager's Name</th>
								<th>Manager's Email</th>
							</tr>
							<tr>
								<%
									List<DepartmentBeanSpring> list = (List<DepartmentBeanSpring>) request
											.getAttribute("passedObject");
									int i = 1;
									for (DepartmentBeanSpring category : list) {
								%>
							</tr>
							<tr>
								<td><input type="radio"
									id=<%=category.getDepartmentName()%>
									value=<%=category.getDepartmentName()%> name="deprecord"
									onchange="retrieveDeptDetails()" /><%=category.getDepartmentName()%></td>
								<td><%=category.getManagerName()%></td>
								<td><%=category.getManagerEmail()%></td>

							</tr>
							<%
								i = i + 1;
								}
							%>
						</table>
						<!-- 		<input type="button" id="click111" name="click" -->
						<!-- 			value="Edit the selected Record" onclick="ajaxFunction()"> -->
						<div id="resultTable">
							<table>
								<tr>
									<td>Department Name</td>
									<td><input type="text" id="departmentName"
										class="form-control" name="departmentName" readonly></td>
								</tr>
								<tr>
									<td>Manager's Name</td>
									<td><input type="text" id="managerName" name="managerName"
										class="form-control"></td>
									<td><label id="managerError" style="color: #FF0000"></td>
								</tr>
								<tr>
									<td>Manager's Email</td>
									<td><input type="text" id="managerEmail"
										name="managerEmail" class="form-control"></td>
									<td><label id="managerEmailError" style="color: #FF0000"></td>
								</tr>
							</table>
							<input type="button" id="click111" name="click" value="Update Department" class="btn btn-success "
							onclick="updateStatus('dataTable')" class="button1">
						</div>
						<br>
						<div id="resultBox" class="alert alert-success"></div>
					</div>
				</div>
			</form>
		</div>


	</div>
</body>
</html>
