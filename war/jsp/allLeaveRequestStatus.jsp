<html xmlns="http://www.w3.org/1999/xhtml">
<!DOCTYPE HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Employee HomePage</title>
<meta name="keywords" content="" />
<meta name="description" content="" />

<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700"
	rel="stylesheet" type="text/css">
<link href="/css/modal.css" rel="stylesheet" type="text/css" media="all" />
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/loaderGif.css" rel="stylesheet">
<link href="/css/logout.css" rel="stylesheet" type="text/css" >
<link href="/css/loaderGif.css" rel="stylesheet">


<script src="https://code.jquery.com/jquery.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/allLeaveRequestStatus.js"></script>


</head>
<body onload="ajax();">

	<%@ page import="java.util.*"%>
	<%@ page import="com.example.bean.*"%>
	<%
		if (session.getAttribute("sessionEmail") == null)
			response.sendRedirect("/jsp/logOut.jsp");
		if ((session.getAttribute("sessionUserType")).equals("admin"))
			response.sendRedirect("/jsp/logOut.jsp");
	%>
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
				<a href="/jsp/empHomePage.jsp" accesskey="3" title=""
					class="btn btn-primary">HomePage</a> <a href="#" accesskey="3"
					title="" class="btn btn-primary">Inbox</a> <input type="button"
					id="click" name="click" class="btn btn-primary"
					value="Send Message">

				<button class="btn btn-primary " data-toggle="modal"
					data-target="#myModal">Profile</button>

				<form name="leaveApplication1" method="POST" class="btn"
					action="/EntryExit/logout.htm">
					<input type="submit" id="click" name="click"
						class="btn btn-primary" value="LogOut">
				</form>

			</div>
		</nav>
		<div id="page">
			<div id="content"
				style="min-height: 400px; background-color: white; width: 15%; float: left;">
				<ul class="style2">
					<li class="first"><a href="/jsp/leaveRequest.jsp"
						class="btn btn-default ">Apply for Leave</a></li>
					<br>
					<li>
						<form name="registrationForm" method="POST"
							action="/LeaveOperation/CheckStatus.htm">
							<input type="submit" class="btn btn-default "
								value="Outgoing Requests" id="submit">
						</form>
					</li>
					<li>
						<form name="leaveApplication1" method="POST"
							action="/LeaveOperation/incomingLeaveRequest.htm">
							<input type="submit" id="click" name="click"
								class="btn btn-default " value="Incoming Requests">
						</form>
					</li>
				</ul>
			</div>

			<div id="sidebar"
				style="min-height: 350px; background-color: whie; width: 60%; float: left;">
				<%
					if (request.getAttribute("statusFromServer").equals("true")) {
				%>

				<div id="tbox1" style="overflow-y:scroll;height:500px">
					<h3>
						<span class="label label-info ">Outgoing Leave Request
							Status Status</span>
					</h3>
					<br> <br>
					<table class="table">
						<tr>
							<th>Applied Date</th>
							<th>Starting</th>
							<th>Ending</th>
							<th>Leave Type</th>
							<th>No. of Days</th>
							<th>Status</th>
							<th>Approved/Rejected Date</th>
						</tr>

						<%
							List<EmployeeLeave> list = (List<EmployeeLeave>) request
										.getAttribute("passedObject");
								int i = 1;
								for (EmployeeLeave category : list) {
									if ((category.getLeaveType()).equals("Casual Leaves")) {
						%>
						<tr style="background-color: #3385FF">
							<%
								} else if ((category.getLeaveType())
												.equals("Personal Leaves")) {
							%>
						
						<tr style="background-color: #FFD1B2">
							<%
								} else if ((category.getLeaveType()).equals("Sick Leaves")) {
							%>
						
						<tr style="background-color: #CCFFCC">
							<%
								}
							%>


							<td><%=category.getAppliedDate()%></td>
							<td><%=category.getStartDate()%></td>
							<td><%=category.getEndDate()%></td>
							<td><%=category.getLeaveType()%></td>
							<td><%=category.getNoOfDays()%></td>
							<td><%=category.getStatus()%></td>
							<td><%=category.getAppRejOn()%></td>
							<%
								i = i + 1;
									}
							%>
						</tr>

					</table>
				</div>
				<br>
				<br>
				<input type="button" value="Sort by Leave type" class="btn btn-success "
				onclick="" />
			</div>

			<%
				} else {
			%>
			<h3>No Outgoing Leave Requests</h3>

			<%
				}
			%>

		</div>

		<div id="currentLeaveStatus"
			style="min-height: 350px; background-color: white; width: 25%; float: left;">
			<table class="table">
				<tr>
					<td></td>
					<td><span class="label label-default">Current Leave
							Status</span></td>
				</tr>
				<tr>
					<td>Casual Leaves</td>
					<td><input type="text" id="cleaves" class="form-control"
						readonly></td>
				</tr>
				<tr>
					<td>Personal Leaves</td>
					<td><input type="text" id="pleaves" class="form-control"
						readonly></td>
				</tr>
				<tr>
					<td>Sick Leaves</td>
					<td><input type="text" id="sleaves" class="form-control"
						readonly></td>

				</tr>
			</table>

		
			
		</div>

		<input type="hidden" id="emailAddress" name="emailAddress"
			value=<%=session.getAttribute("sessionEmail")%>>

		<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">Change My Password</h4>
					</div>
					<div class="modal-body">
						<form name="formPasswordChange">
							<table>
								<tr>
									<td>Old Password</td>
									<td><input type="password" id="oldPassword"
										name="oldPassword" class="form-control" required></td>
									<td><label id="oldPasswordError" style="color: #FF0000"></td>
								</tr>
								<tr>
									<td>New Password</td>
									<td><input type="password" id="newPassword1"
										name="newPassword1" class="form-control" required></td>
									<td><label id="newPassword1Error" style="color: #FF0000"></td>
								</tr>
								<tr>
									<td>Re enter New Password</td>
									<td><input type="password" id="newPassword2"
										name="newPassword2" class="form-control" required></td>
									<td><label id="newPassword2Error" style="color: #FF0000"></td>
								</tr>
							</table>
							<div id="pswdResultBox">
								<img src="/images/gif-load.gif" class="ajax-loader" />
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary"
							onclick="updatePasswordAjax()">Save changes</button>
						<div id="resultBox" class="alert alert-success"></div>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>
