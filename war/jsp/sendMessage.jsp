<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>BootStrap Project</title>
<script src="https://code.jquery.com/jquery.js"></script>

<script src="jquery-1.10.2.js"></script>


<script src="jquery-1.10.2.js"></script>

<script src="js/bootstrap.min.js"></script>
<link href="modal.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript">
	function taskBriefEdit() {

		if (document.getElementById("taskToDo").value == "To-do") {
			document.getElementById("taskBrief").value = "Task Brief";
		} else {
			document.getElementById("taskBrief").value = document
					.getElementById("taskToDo").value
					+ " " + document.getElementById("person").value
		}
	}

	$(document)
			.ready(
					function() {
						$("#newContactDetail").hide();
						$("#newCommentArea").hide();
						$("#openModal").hide();
						$("#row3").hide();

						$("#newPersonButton")
								.click(
										function() {

											$("#newUserpic").hide();
											$("#newContactDetail").show();

											$("#listOfContacts")
													.append(
															"<label for='ui-5' class='checkbox-inline'><input type='checkbox' id='inlineCheckbox1' value='option1'> New Contact </label>");
											$("#listOfContacts")
													.append(
															"<button type='button' class='btn btn-danger btn-xs navbar-right'>Delete</button>");
										});
						$("#newCommentButton").click(function() {

							$("#newUserpic").hide();
							$("#newCommentArea").show();

						});

						$("#showHistoryDiv").click(function() {

							$("#newTaskpic").hide();
							$("#row3dynamicPageTasks").hide();
							$("#row3dynamicPageHistory").show();

						});
						$("#showTasksDiv").click(function() {

							$("#newTaskpic").show();
							$("#row3dynamicPageTasks").show();
							$("#row3dynamicPageHistory").hide();

						});

						$("#newContactName")
								.keyup(
										function() {
											$("label[for='ui-5']")
													.text(
															document
																	.getElementById('newContactName').value);
										});

						$("#newContactName")
								.blur(
										function() {
											if ((document
													.getElementById('newContactName').value)
													.trim() == "") {
											} else {
												$("#row3").show();
												$("#row3dynamicPageHistory")
														.hide();
											}
										});

					});
</script>
</head>
<body>

	<div style="min-height: 20px; background-color: black; color: white;">
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Chandaneswar <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#">Help</a></li>
						<li class="divider"></li>
						<li><a href="#">Settings</a></li>
						<li class="divider"></li>
						<li><a href="#">Signout</a></li>
					</ul></li>
			</ul>
		</div>


	</div>
	<div>


		<nav class="navbar navbar-default" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<h3>
						<span class="label label-primary">Distributed Source</span>
					</h3>
				</div>


				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">


					<ul class="nav navbar-nav navbar-right">
						<li><a href="#"><span class="glyphicon glyphicon-user"></span>
								Contacts</a></li>
						<li><a href="#"><span class="glyphicon glyphicon-tasks"></span>
								Tasks</a></li>
						<li><a href="#"><span
								class="glyphicon glyphicon-folder-open"></span> Deals</a></li>
						<li><a href="#"><span class="glyphicon glyphicon-font"></span>
								Accounts</a></li>
						<li><a href="#"><span class="glyphicon glyphicon-signal"></span>
								Reports</a></li>
					</ul>
				</div>

			</div>

			<div style="background-color: #CCFFFF; min-height: 500px;">
				<div style="min-height: 60px">
					<div style="margin-left: 50px;">
						<button type="button" class="btn btn-default btn-xs"
							style="margin-top: 20px" id="newPersonButton">+ New
							Person</button>
						<a href="#" class="btn  btn-xs" style="margin-top: 20px">Import
							Contacts</a> <a href="#" class="btn  btn-xs" style="margin-top: 20px">Export
							Contacts</a> <a href="#" class="btn  btn-xs" style="margin-top: 20px">Delete
							All</a>
						<form class="navbar-form navbar-right" role="search">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="Search">
							</div>
							<button type="submit" class="btn btn-default"
								style="margin-right: 50px">Submit</button>
						</form>
					</div>
				</div>
				<div
					style="background-color: white; min-height: 400px; margin-bottom: 100px; margin-right: 50px; margin-left: 50px;">
					<div id="row1"
						style="min-height: 400px; background-color: white; width: 20%; float: left;">
						<div id="personType"
							style="background-color: white; min-height: 60px; border: 1px solid;">
							<button type="button" class="btn btn-default btn-xs"
								style="margin-top: 20px; margin-left: 10px">People</button>
							<button type="button" class="btn btn-default btn-xs"
								style="margin-top: 20px; margin-left: 10px">Companies</button>
						</div>
						<div id="deleteSelection"
							style="min-height: 60px; background-color: white; border: 1px solid;">
							<label><input type="checkbox" style="margin-top: 20px;">
								Delete Selected</label>
						</div>
						<div id="listOfContacts"></div>

					</div>


					<div class="modal fade bs-modal-lg" tabindex="-1" role="dialog"
						aria-labelledby="myLargeModalLabel" aria-hidden="true">
						<div class="modal-dialog modal-lg">
							<div class="modal-content">
								<div id="row2"
									style="min-height: 350px; background-color: whie; width: 40%; float: left; border: 1px solid;">
									<div class="form-group">
										<input type="email" class="form-control" id="taskBrief"
											placeholder="Task Brief">
									</div>
									<div style="margin-top: 30px">
										<select id="taskToDo" onchange="taskBriefEdit();">
											<option value="Call" selected="">Call</option>
											<option value="Email">Email</option>
											<option value="To-do">To-do</option>
										</select> <select id="person" onchange="taskBriefEdit()";>
											<option value="Chandaneswar" selected="">Chandaneswar</option>
										</select> <a href="#" class="btn  btn-xs">Due Date</a> | <a href="#"
											class="btn  btn-xs">Due Time</a> | <a href="#"
											class="btn  btn-xs">Chandaneswar</a>
									</div>
									<div style="background-color: #CCFFFF; margin-top: 30px">
										<textarea class="form-control" rows="8"></textarea>
									</div>
									<div>
										<button type="button" class="btn btn-default btn-xs"
											style="margin-top: 20px" id="newPersonButton">Add
											This Task</button>
										<a href="#" class="btn  btn-xs" style="margin-top: 20px">Cancel</a>
									</div>
								</div>
							</div>
						</div>
					</div>
















					<div id="row2"
						style="min-height: 400px; background-color: white; width: 30%; float: left; border: 1px solid;">

						<div id="newUserpic"
							style="margin-top: 30%; margin-left: 45%; margin-bottom: 50%; margin-right: 50%">
							<img src="contact.jpg" height="40px" width="40px"
								style="margin-right: -80px" /> <a href="#"><span
								class="label label-info">Add Contacts</span></a>
						</div>

						<div id="newContactDetail"
							style="overflow-y: scroll; height: 350px" height="50px"
							width="50px">
							<div style="width: 40%; float: left;">
								<img src="newUser.png" alt="..." class="img-rounded">
							</div>
							<div style="width: 60%; float: left; margin-top: 10%;">
								<div>
									<input class="form-control" type="text" placeholder="Name"
										id="newContactName">
								</div>
								<div>
									<input class="form-control" type="text" placeholder="Job Title">
								</div>
							</div>

							<div></div>
							<div>
								<input class="form-control" type="text" placeholder="Company">
							</div>
							<div>Phone</div>
							<div>
								<input class="form-control" type="text" placeholder="Phone">
							</div>
							<div>Email</div>
							<div>
								<input class="form-control" type="text" placeholder="Email">
							</div>
							<button type="button" class="btn btn-success">Add</button>
							<div id="resultAddContact" style="color: red"></div>
						</div>

					</div>

					<div id="row3"
						style="min-height: 400px; background-color: white; width: 50%; float: left; border: 1px solid">
						<div class="btn-group" id="headerButtons"
							style="margin-left: 120px; margin-top: 20px">
							<button type="button" class="btn btn-primary">Notes</button>
							<button type="button" class="btn btn-primary" id="showTasksDiv">Tasks</button>
							<button type="button" class="btn btn-primary" id="showHistoryDiv">History</button>
							<button type="button" class="btn btn-primary">Documents</button>
							<button type="button" class="btn btn-primary">Deals</button>
							<button type="button" class="btn btn-primary">Accounts</button>
						</div>
						<div id="row3dynamicPageTasks">
							<div id="eventsHead"
								style="background-color: white; min-height: 50px; margin-left: 50px;">
								<button class="btn btn-success btn-xs" data-toggle="modal"
									data-target="#myModal"
									style="margin-top: 20px; margin-left: 50px">+ New Task

								
							</div>
							<hr>
							<div id="newTaskpic"
								style="margin-top: 50px; margin-left: 250px; margin-bottom: 50px; margin-right: 50px;">
								<div style="margin-right: -80px; float: left">
									<img src="newTask.jpg" height="40px" width="40px" />
								</div>
								<div>
									<a href="#"><span class="label label-info"
										style="margin-top: 200px">Add Tasks</span></a>
								</div>
							</div>
						</div>


						<div id="row3dynamicPageHistory">
							<div id="commentDiv"
								style="min-height: 250px; background-color: white; width: 100%; float: left">
								<div id="eventsHead"
									style="background-color: white; min-height: 50px; margin-left: 50px;">
									<button type="button" class="btn btn-success btn-xs"
										style="margin-top: 20px" id="newCommentButton">+
										Comment</button>
								</div>
								<hr>
								<div id="newCommentArea"
									style="margin-top: 0px; margin-left: 50px; margin-bottom: 50px; margin-right: 50px;">
									<div style="background-color: #CCFFFF; margin-top: 30px">
										<textarea class="form-control" rows="4"
											placeholder="Add a comment to the history..."></textarea>
									</div>
									<div class="navbar-right">
										<button type="button" class="btn btn-success btn-xs"
											style="margin-top: 20px" id="newPersonButton">Add
											this Comment</button>
										<a href="#" class="btn btn-danger btn-xs"
											style="margin-top: 20px"
											onClick="$('#newCommentArea').hide();">Cancel</a>
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>
		</nav>

		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">New Task</h4>
					</div>
					<div class="modal-body">
						<div id="row2"
							style="min-height: 350px; background-color: whie; width: 100%; float: left; border: 1px solid;">
							<div class="form-group">
								<input type="email" class="form-control" id="taskBrief"
									placeholder="Task Brief">
							</div>
							<div style="margin-top: 30px">
								<select id="taskToDo" onchange="taskBriefEdit();">
									<option value="Call" selected="">Call</option>
									<option value="Email">Email</option>
									<option value="To-do">To-do</option>
								</select> <select id="person" onchange="taskBriefEdit()";>
									<option value="Chandaneswar" selected="">Chandaneswar</option>
								</select> <a href="#" class="btn  btn-xs">Due Date</a> | <a href="#"
									class="btn  btn-xs">Due Time</a> | <a href="#"
									class="btn  btn-xs">Chandaneswar</a>
							</div>
							<div style="background-color: #CCFFFF; margin-top: 30px">
								<textarea class="form-control" rows="8"></textarea>
							</div>
							<div></div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-success btn-xs"
							style="margin-top: 20px" id="newPersonButton">Add This
							Task</button>
						<a href="#" class="btn btn-danger btn-xs" style="margin-top: 20px">Cancel</a>
					</div>
				</div>
			</div>
		</div>
</body>
</html>