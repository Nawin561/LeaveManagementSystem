<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Leave Absence Module</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700"
	rel="stylesheet" type="text/css">

<script src="js/bootstrap.min.js"></script>
<script src="js/jquery-1.10.2.js"></script>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="js/login.js"></script>
<link href="css/loaderGif.css" rel="stylesheet">


<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/modal.css" rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" type="text/css" href="css/login.css">

<style type="text/css">
.slider {
	width: 350px;
	height: 200px;
	overflow: hidden;
	margin: 30px auto;
	background-image: url(loader1.gif);
	background-repeat: no-repeat;
	background-position: center;
	border-style: none;
}

.slider img {
	width: 350px;
	height: 200px;
	display: none;
	border-style: none;
}

</style>

</head>

<body onload="Slider()">
	<div id="loaderGif">
		<img src="images/ajax-loader.gif" class="ajax-loader" />
	</div>



	<div id="wrapper" style="width: 550px; border-radius: 50px;">
		<div id="page" style="width: 600px; border-radius: 50px;">
			<div id="content">

				<h2>
					<span>Sign In</span>
				</h2>
				<br>
				<ul class="style2">

					<li class="first">

						<form name="form1" method="post" class="form-horizontal"
							role="form" action="/EntryExit/login.htm">



							<label for="inputEmail3" class="col-sm-2 control-label">Email</label>

							<div class="form-group">
								<div class="col-sm-4">
									<input type="email" class="form-control " id="email"
										name="email" placeholder="Email">
								</div>
							</div>


							<label for="inputPassword3" class="col-sm-2 control-label">Password</label>



							<div class="form-group">
								<div class="col-sm-4">
									<input type="password" class="form-control " id="password"
										name="password" maxlength="15" required placeholder="Password">
								</div>
							</div>



							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-4">

									<input type="radio" name="loginEmployeeType" value="general"
										checked>General <input type="radio"
										name="loginEmployeeType" value="admin">Admin

								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button type="submit" id="loginButton"
										onclick="$('#loaderGif').show()" ; class="btn btn-success">Sign
										in</button>

								</div>
							</div>
						</form>
						<form name="addAdmin" method="POST"
							action="/EmployeeOperation/addAdminAccount.htm">

							<div id="resultBox" style="color: red"></div>
							<input type="submit" class="btn btn-danger"
								style="margin: 0px 50px 30px 132px"
								value="Create Administrator Account">
						</form>
						<button class="btn btn-primary" data-toggle="modal"
							data-target="#myModalResetPassword">Reset my password</button>
					</li>

				</ul>
			</div>
		</div>
	</div>

	<!-- 	Modal Box for Reset Passowrd -->
	<div class="modal fade bs-modal-sm" tabindex="-1" role="dialog"
		id="myModalResetPassword" aria-labelledby="mySmallModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Reset Password</h4>
				</div>
				<div class="modal-body">
					<div id="row2"
						style="min-height: 100px; background-color: whie; width: 100%; float: left;">

						<div style="margin-top: 30px">
							<div class="input-group">
								<span class="input-group-addon">@</span> <input type="text"
									id="emailForReset" class="form-control" placeholder="Email">
							</div>
						</div>
						<div></div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary btn-sm"
						style="margin-top: 20px" id="ResetPassword"
						onclick="updatePasswordAjax()">Reset my password</button>
					<div id="resultBoxResetPassword"></div>
				</div>
			</div>
		</div>
	</div>

	<div id="footer">
		<p>
			Copyright (c) 2014 leaverequest-ad.appspot.com.<br> All rights
			reserved.<br> Design by Chandaneswar Sengupta
		</p>
	</div>
</body>
</html>
