<html xmlns="http://www.w3.org/1999/xhtml">
<!DOCTYPE HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Admin Homepage</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700"
	rel="stylesheet" type="text/css">
<link href="/css/logout.css" rel="stylesheet" type="text/css"
	media="all" />
<link href="/css/bootstrap.min.css" rel="stylesheet">


<script src="https://code.jquery.com/jquery.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/adminHomePage.js"></script>




</head>
<body>
	<%
		if (session.getAttribute("sessionEmail") == null)
			response.sendRedirect("/jsp/logOut.jsp");
		if ((session.getAttribute("sessionUserType")).equals("general"))
			response.sendRedirect("/jsp/logOut.jsp");
	%>
	<div id="wrapper">
		<div id="header">
			<div id="logo">
				<div class="page-header">
					<h1>
						Welcome <small><%=session.getAttribute("sessionEmail")%></small>
					</h1>
				</div>

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
			<!-- /.container-fluid -->
		</nav>
		<div id="page">
			<div id="content"></div>
			<div id="sidebar">
				<div id="tbox1">

					<ul class="style2">
						<!--<li class="first">
						 <img id="1" src="/images/admin.jpg"
							alt="Image 1" border="0" width="600px" height="300px"> 
							
							</li>-->
					</ul>
				</div>
			</div>
		</div>

	</div>
</body>
</html>
