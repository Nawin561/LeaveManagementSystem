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
<link href="/css/adminHomepage.css" rel="stylesheet" type="text/css"
	media="all" />
<link href="/css/modal.css" rel="stylesheet" type="text/css" media="all" />

<link rel="stylesheet" type="text/css" href="/css/button1.css">
<link rel="stylesheet" type="text/css" href="/css/button2.css">
<link rel="stylesheet" type="text/css" href="/css/lengthyButton.css">
<link rel="stylesheet" type="text/css" href="/css/logout.css">
<script type="text/javascript">
	function updatePasswordAjax() {
		var oldPassword = document.getElementById("oldPassword").value;
		var newPassword1 = document.getElementById("newPassword1").value;
		var emailAddress = document.getElementById("emailAddress").value;
		var obj = new XMLHttpRequest();
		var str= "emailAddress=" + emailAddress + "&newPassword1=" + newPassword1 + "&oldPassword=" + oldPassword;
		obj.open("GET", "/EmployeeOperation/UpdatePassword.htm?"
				+ str, true);
		obj.send();
		obj.onreadystatechange = function() {
			if (obj.readyState == 4 && obj.status == 200) {
				document.getElementById("resultBox").innerHTML =  obj.responseText;
			}
		}

	}
</script>
</head>
<body>
	<%
		if (session.getAttribute("sessionEmail") == null)
			response.sendRedirect("/jsp/logOut.jsp");
		if ((session.getAttribute("sessionUserType")).equals("admin"))
			response.sendRedirect("/jsp/logOut.jsp");
	%>
	<div id="wrapper">
		<div id="header">
			<div id="logo" style="color: yellow">
				<h1>Welcome</h1>
				<h2><%=session.getAttribute("sessionEmail")%></h2>
			</div>
			<div id="menu">
				<ul>
					<li><a href="/jsp/empHomePage.jsp" accesskey="3" title="">HomePage</a></li>
					<li><a href="#" accesskey="3" title="">Inbox</a></li>
					<li><a href="#" accesskey="4" title="">Send Message</a></li>
					<li><a href="/jsp/empSettings.jsp"> Settings </a></li>
					<li><form name="leaveApplication1" method="POST"
							action="/EntryExit/logout.htm">
							<input type="submit" id="click" name="click" class="logout"
								value="LogOut">
						</form></li>
				</ul>
			</div>
		</div>
		<div id="page">
			<div id="content">
				<ul class="style2">
					<li class="first"><a href="/jsp/leaveRequest.jsp"
						class="button2">Apply for Leave</a></li>
					<li><a href="#openModal" onclick="ajax()" class="button2">Check
							Leave Status</a></li>

					<li>
						<form name="registrationForm" method="POST"
							action="/LeaveOperation/CheckStatus.htm">
							<input type="submit" class="button2" value="Outgoing Requests"
								id="submit">
						</form>
					</li>
					<li>
						<form name="leaveApplication1" method="POST"
							action="/LeaveOperation/incomingLeaveRequest.htm">
							<input type="submit" id="click" name="click" class="button2"
								value="Incoming Requests">
						</form>
					</li>
				</ul>
			</div>
			<div id="sidebar">
				<div id="tbox1">


					<h2> Password Modification</h2>

					<table>
						<tr>
							<td>Old Password</td>
							<td><input type="password" id="oldPassword"
								name="oldPassword" required></td>
						</tr>
						<tr>
							<td>New Password</td>
							<td><input type="password" id="newPassword1" name="newPassword1"
								required></td>
						</tr>
						<tr>
							<td>Re enter New Password</td>
							<td><input type="password" id="newPassword2"
								name="newPassword2" required></td>

						</tr>

					</table>
					<input type="button" name="submit" id="submit" value="Update Password"
						class="button1" onclick="updatePasswordAjax()">
				</div>
				<div id="resultBox"> </div>
			</div>
		</div>
		<input type="hidden" id="emailAddress" name="emailAddress"
			value=<%=session.getAttribute("sessionEmail")%>>
		<div id="openModal" class="modalDialog">
			<div>
				<a href="#close" title="Close" class="close">X</a>
				<table>
					<tr>
						<td>Casual Leaves</td>
						<td><input type="label" id="cleaves"></td>
					</tr>
					<tr>
						<td>Personal Leaves</td>
						<td><input type="label" id="pleaves"></td>
					</tr>
					<tr>
						<td>Sick Leaves</td>
						<td><input type="label" id="sleaves"></td>

					</tr>
						<tr>
						<td></td>
						<td><input type="text" id="emailAddress" value=<%=session.getAttribute("sessionEmail") %>></td>

					</tr>
				</table>
			</div>
			
		</div>

		<div id="footer">
			<p>
				Copyright (c) 2014 leaverequest-ad.appspot.com.<br> All rights
				reserved.<br> Design by Chandaneswar Sengupta
			</p>
		</div>
	</div>
</body>
</html>
