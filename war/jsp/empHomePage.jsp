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
<link href="/css/logout.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/loaderGif.css" rel="stylesheet">


<script src="https://code.jquery.com/jquery.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/empHomePage.js"></script>





</head>
<body onload="ajax();">
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
					title="" class="btn btn-primary">Inbox</a>

				<button class="btn btn-primary " data-toggle="modal"
					data-target="#myModalMessagetoAdmin">Send Message</button>


				<button class="btn btn-primary " data-toggle="modal"
					data-target="#myModalPassword">Profile</button>



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
				style="min-height: 350px; background-color: whie; width: 40%; float: left;">
				<div id="tbox1" class="">

					<table class="table">
						<tr>
							<td></td>
							<td><span class="label label-default">My Profile</span></td>
						</tr>

						<tr>
							<td>Department</td>
							<td><%=session.getAttribute("sessionTeam")%></td>
						</tr>
						<tr>
							<td>Manager's Name</td>
							<td><%=session.getAttribute("sessionManagerName")%></td>
						</tr>
						<tr>
							<td>Manager's Email</td>
							<td><%=session.getAttribute("sessionManagerEmail")%></td>

						</tr>
						<tr>
							<td>Email</td>
							<td><%=session.getAttribute("sessionEmail")%></td>
						</tr>
						<tr>
							<td>Contact No.</td>
							<td><%=session.getAttribute("sessionContactNo")%></td>
						</tr>
						<tr>
							<td>Total Casual Leaves</td>
							<td><%=session.getAttribute("sessionTotalcLeaves")%></td>
						</tr>
						<tr>
							<td>Total Personal Leaves</td>
							<td><%=session.getAttribute("sessionTotalpLeaves")%></td>
						</tr>
						<tr>
							<td>Total Sick Leaves</td>
							<td><%=session.getAttribute("sessionTotalsLeaves")%></td>
						</tr>
						<tr>
							<td>Employee Type</td>
							<td><%=session.getAttribute("sessioneType")%></td>
						</tr>
					</table>
				</div>
			</div>
		</div>


		<div id="currentLeaveStatus"
			style="min-height: 350px; background-color: white; width: 40%; float: left;">
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


	</div>

	<input type="hidden" id="emailAddress" name="emailAddress"
		value=<%=session.getAttribute("sessionEmail")%>>

	<!-- Modal -->
	<div class="modal fade" id="myModalPassword" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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

	<!-- 	Send Message to Admin  -->
	<div class="modal fade" id="myModalMessagetoAdmin" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Compose Message to
						Admin</h4>
				</div>
				<div class="modal-body">
					<form name="formPasswordChange">
						<table class="table">
							<tr>
								<td></td>
								<td><span class="label label-default"></span></td>
							</tr>

							<tr>
								<td>Subject</td>
								<td><input type="text" id="messageSubject"
									class="form-control" required></td>
							</tr>
							<tr>
								<td>Body</td>
								<td><input type="text" id="messageBody"
									class="form-control" required></td>
							</tr>
						</table>
						<div id="messageResultBoxGif">
							<img src="/images/gif-load.gif" class="ajax-loader" />
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="sendMessageToAdmin()">Send Message</button>
					<div id="messageResultBoxMessage" class="alert alert-success"></div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
