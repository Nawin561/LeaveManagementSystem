<html xmlns="http://www.w3.org/1999/xhtml">
<!DOCTYPE HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Leave Application</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700"
	rel="stylesheet" type="text/css">
<link href="/css/modal.css" rel="stylesheet" type="text/css" media="all" />
<link href="/css/logout.css" rel="stylesheet" type="text/css" >
<link href="/css/bootstrap.min.css" rel="stylesheet">

<script src="/js/jquery-1.10.2.js"></script>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/leaveRequest.js"></script>

</head>
<body onload="ajax();">
	<%
		if (session.getAttribute("sessionEmail") == null)
			response.sendRedirect("/jsp/logOut.jsp");
		if ((session.getAttribute("sessionUserType")).equals("admin"))
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




				<a href="/jsp/empHomePage.jsp" accesskey="3" title=""
					class="btn btn-primary">HomePage</a> <a href="#" accesskey="3"
					title="" class="btn btn-primary">Inbox</a> <input type="button"
					id="click" name="click" class="btn btn-primary"
					value="Send Message">



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
				style="min-height: 400px; background-color: white; width: 15%; float: left;">
				<ul class="style2">
					<li class="first"><a href="/jsp/leaveRequest.jsp"
						id="applyButton" class="btn btn-default ">Apply for Leave</a></li>
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
				style="min-height: 200px; background-color: whie; width: 60%; float: left;">
				<div id="tbox1">

					<h3>
						<span class="label label-info ">Leave Application</span>
					</h3>
					<br> <br>

					<table class="table">
						<tr>
							<td>Applied On</td>
							<td><input type="text" id="todayDate" name="todayDate"
								class="form-control" readonly> mm/dd/yyyy</td>
						</tr>
						<tr>
							<td>Leave From</td>
							<td><input type="date" id="startingDate" class="date"
								name="startingDate" onchange="noOfDaysLeave();"
								class="form-control">
								<div id="dateerror" style="color: red"></div></td>
						</tr>
						<tr>
							<td>Until</td>
							<td><input type="date" id="endingDate" class="date"
								name="endingDate" onchange="noOfDaysLeave();"
								class="form-control"></td>

						</tr>
						<tr>
							<td>Days of Leave</td>
							<td><input type="text" id="daysOfLeaves" name="daysOfLeaves"
								value="0" readonly class="form-control"></td>
						</tr>
						<tr>
							<td>Leave Details</td>
							<td>

								<table>
									<th>Type</th>
									<th>Balance till date</th>
									<th>Balance after this leave</th>
									<tr>
										<td><input type="radio" name="leaveType"
											id="cleavesradio" value="Casual Leaves"
											onclick="selectLeaveType();">Casual</td>
										<td><input type="text" id="cleavesBalanceBefore"
											value=<%=session.getAttribute("sessionBalancecLeaves")%>
											readonly class="form-control"></td>
										<td><input type="text" id="cleavesBalanceAfter" readonly
											class="form-control"></td>

										<td><img src="/images/greencorrect.jpg" height="28px"
											id="cleavesgranted"> <img src="/images/redcross.jpg"
											height="40px" id="cleavesrevoked"></td>
									</tr>

									<tr>
										<td><input type="radio" name="leaveType"
											id="pleavesradio" value="Personal Leaves"
											onclick="selectLeaveType();">Personal</td>
										<td><input type="text" id="pleavesBalanceBefore"
											value=<%=session.getAttribute("sessionBalancepLeaves")%>
											readonly class="form-control"></td>
										<td><input type="text" id="pleavesBalanceAfter" readonly
											class="form-control"></td>

										<td><img src="/images/greencorrect.jpg" height="28px"
											id="pleavesgranted"> <img src="/images/redcross.jpg"
											height="40px" id="pleavesrevoked"></td>
									</tr>

									<tr>
										<td><input type="radio" name="leaveType"
											id="sleavesradio" value="Sick Leaves"
											onclick="selectLeaveType();">Sick</td>
										<td><input type="text" id="sleavesBalanceBefore"
											value=<%=session.getAttribute("sessionBalancesLeaves")%>
											readonly class="form-control"></td>
										<td><input type="text" id="sleavesBalanceAfter" readonly
											class="form-control"></td>

										<td><img src="/images/greencorrect.jpg" height="28px"
											id="sleavesgranted"> <img src="/images/redcross.jpg"
											height="40px" id="sleavesrevoked"></td>
									</tr>
								</table>
							</td>
						<tr>
							<td>Comments</td>
							<td><textarea name="comment" id="comment" form="usrform"
									rows="3" class="form-control" cols="47"
									placeholder="Please grant my leave"></textarea></td>
						</tr>
						</tr>
					</table>


					<input type="button" id="leaveApplyButton" name="click"
						data-toggle="modal" data-target="#myModalLeave"
						class="btn btn-primary" onclick="setFieldValue();"
						value="Leave Summary">
				</div>

			</div>
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
		<!-- 			// Password	change modal box -->
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

		<!-- 		// Summary For Leave Modal Box -->
		<div class="modal fade" id="myModalLeave" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">Leave Application
							Summary</h4>
					</div>
					<div class="modal-body">
						<table class="table">
							<tr>
								<td></td>
								<td><span class="label label-default"></span></td>
							</tr>

							<tr>
								<td>Starting Date</td>
								<td><input type="text" id="startingDateModal"
									class="form-control" required readonly></td>
							</tr>
							<tr>
								<td>Ending Date</td>
								<td><input type="text" id="endingDateModal"
									class="form-control" required readonly></td>
							</tr>
							<tr>
								<td>Duration</td>
								<td><input type="text" id="durationModal"
									class="form-control" required readonly></td>

							</tr>
							<tr>
								<td>Leave Type</td>
								<td><input type="text" id="leaveTypeModal"
									class="form-control" required readonly></td>
							</tr>
							<tr>
								<td>Comments</td>
								<td><input type="text" id="commentsModal"
									class="form-control" required readonly></td>
							</tr>
						</table>
						<div id="pswdResultBox">
							<img src="/images/gif-load.gif" class="ajax-loader" />
						</div>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<input type="button" id="leaveApplyButton" name="click"
							class="btn btn-primary" value="Apply For Leave"
							onclick="leaveRequestAjax();">
						<div id="resultBoxLeave" class="alert alert-success"></div>
						<div id="loaderGif">
							<img src="/images/gif-load.gif" class="ajax-loader" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
