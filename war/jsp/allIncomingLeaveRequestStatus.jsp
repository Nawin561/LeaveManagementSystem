<html xmlns="http://www.w3.org/1999/xhtml">
<!DOCTYPE HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Incoming Leave Requests</title>
<meta name="keywords" content="" />
<meta name="description" content="" />

<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700"
	rel="stylesheet" type="text/css">
<link href="/css/table1.css" rel="stylesheet" type="text/css"
	media="all" />
<link rel="stylesheet" type="text/css" href="/css/logout.css">
<link href="/css/modal.css" rel="stylesheet" type="text/css" media="all" />
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/loaderGif.css" rel="stylesheet">

<script src="/js/jquery-1.10.2.js"></script>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/allIncomingLeaveRequestStatus.js"></script>


</head>
<%@ page import="java.util.*"%>
<%@ page import="com.example.bean.*"%>

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
				</ul>
			</div>
			<div id="sidebar"
				style="min-height: 150px; background-color: white; width: 60%; float: left;">
				<div id="tbox1" style="overflow-y: scroll; height: 500px">

					<%
						if (request.getAttribute("statusFromServer").equals("true")) {
					%>
					<h3>
						<span class="label label-info ">Leave Requests For
							Approval/Rejection</span>
					</h3>
					<br> <br>
					<table id="tableData" class="table">
						<tr>
							<td>ID</td>
							<td>By</td>
							<td>Email</td>
							<td>App on</td>
							<td>From</td>
							<td>To</td>
							<td>Dur.</td>
							<td>Type</td>
							<td>Status</td>
							<td>Updated</td>
						</tr>


						<%
							List<EmployeeLeave> list = (List<EmployeeLeave>) request
										.getAttribute("passedObject");
								int i = 1;
								for (EmployeeLeave category : list) {
						%>

						<tr>
							<td><input type="radio" id=<%=category.getKey()%>
								value=<%=category.getKey()%> name="emprecord"
								onchange="ajaxFunctionForRetrievingLeaveRecord()" /><%=category.getKey()%></td>
							<td><%=category.getApplicant()%></td>
							<td><%=category.getEmail()%></td>
							<td><%=category.getAppliedDate()%></td>
							<td><%=category.getStartDate()%></td>
							<td><%=category.getEndDate()%></td>
							<td><%=category.getNoOfDays()%></td>
							<td><%=category.getLeaveType()%></td>
							<td><%=category.getStatus()%></td>
							<td><%=category.getAppRejOn()%></td>

							<%
								i = i + 1;
									}
							%>
						</tr>

					</table>

					<br>
					<div id="result">



						<h3>
							<span class="label label-info ">Detail of the Leave</span>
						</h3>
						<br>

						<table>
							<tr>
								<td>Applicant ID</td>
								<td><input type="text" id="applicantId" name="applicantId"
									class="form-control" readonly></td>
							</tr>
							<tr>
								<td>Applicant's Name</td>
								<td><input type="text" id="employeeName"
									name="employeeName" class="form-control" readonly></td>
							</tr>
							<tr>
								<td>Applicant's Email</td>
								<td><input type="text" id="employeeEmail"
									class="form-control" name="employeeEmail" readonly></td>
							</tr>
							<tr>
								<td>Date Applied</td>
								<td><input type="text" id="appliedDate" name="appliedDate"
									class="form-control" readonly></td>
							</tr>
							<tr>
								<td>Start Date</td>
								<td><input type="text" id="startDate" name="startDate"
									class="form-control" readonly></td>
							</tr>
							<tr>
								<td>End Date</td>
								<td><input type="text" id="endDate" name="endDate"
									class="form-control" readonly></td>
							</tr>
							<tr>
								<td>No. of Days</td>
								<td><input type="text" id="noOfDays" name="noOfDays"
									class="form-control" readonly></td>
							</tr>
							<tr>
								<td>Leave Type</td>
								<td><input type="text" id="leaveType" name="leaveType"
									class="form-control" readonly></td>
							</tr>
							<tr>
								<td>Status</td>
								<td><input type="text" id="status" name="status" readonly
									class="form-control"></td>
							</tr>
							<tr>
								<td>Approved/Rejected on</td>
								<td><input type="text" id="appRejDate" name="appRejDate"
									class="form-control" readonly></td>
							</tr>
						</table>
						<input type="button" id="approveButton" name="click"
							value="Approve" class="btn btn-primary"
							onclick="approveLeaveAppAjax('tableData')"> <input
							type="button" id="rejectButton" name="click" value="Reject"
							class="btn btn-primary" onclick="rejectLeaveAppAjax('tableData')">



					</div>
					<div id="resultBox" class="alert alert-success"></div>
				</div>
				<%
					} else {
				%>
				<h3>No Incoming Leave Requests</h3>
				<%
					}
				%>
			</div>
			<br> <br> <input type="hidden" id="emailAddress"
				name="emailAddress" value=<%=session.getAttribute("sessionEmail")%>>


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
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
							<button type="button" class="btn btn-primary"
								onclick="updatePasswordAjax()">Save changes</button>
							<div id="resultBoxPassword" class="alert alert-success"></div>
						</div>
					</div>
				</div>
			</div>

		</div>
</body>
</html>
