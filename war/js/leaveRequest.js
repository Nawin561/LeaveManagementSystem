//For saving the new Password
function validateForm() {
	// Old Password validation
	if ((document.getElementById('oldPassword').value).trim() == "") {
		document.getElementById("oldPasswordError").innerHTML = "*Mandatory Field";
		return false;
	} else {
		document.getElementById("oldPasswordError").innerHTML = "";
		document.formPasswordChange.oldPassword.focus();
	}

	// New Password1 validation
	if ((document.getElementById('newPassword1').value).trim() == "") {
		document.getElementById("newPassword1Error").innerHTML = "*Mandatory Field";
		return false;
	} else {
		document.getElementById("newPassword1Error").innerHTML = "";
		document.formPasswordChange.newPassword1.focus();
	}

	// New Password2 validation
	if ((document.getElementById('newPassword2').value).trim() == "") {
		document.getElementById("newPassword2Error").innerHTML = "*Mandatory Field";
		return false;
	} else {
		document.getElementById("newPassword2Error").innerHTML = "";
		document.formPasswordChange.newPassword2.focus();
	}

	if (p1 != p2) {
		document.getElementById("newPassword2Error").innerHTML = " *Password Mismatches";
		return false;
	} else {
		document.getElementById("newPassword2Error").innerHTML = "";
	}

}

function setFieldValue() {
	document.getElementById("startingDateModal").value = document
			.getElementById("startingDate").value;
	document.getElementById("endingDateModal").value = document
			.getElementById("endingDate").value;
	document.getElementById("durationModal").value = document
			.getElementById("daysOfLeaves").value;
	document.getElementById("commentsModal").value = document
			.getElementById("comment").value;
	if (document.getElementById("cleavesradio").checked)
		leaveType = "Casual Leaves";
	else if (document.getElementById("pleavesradio").checked)
		leaveType = "Personal Leaves"
	else if (document.getElementById("sleavesradio").checked)
		leaveType = "Sick Leaves";
	document.getElementById("leaveTypeModal").value = leaveType;
}

function updatePasswordAjax() {
	var x = validateForm();
	if (x == false)
		return;
	$("#loaderGif").show();
	$("#pswdResultBox").show();
	var oldPassword = document.getElementById("oldPassword").value;
	var newPassword1 = document.getElementById("newPassword1").value;
	var emailAddress = document.getElementById("emailAddress").value;
	var obj = new XMLHttpRequest();
	var str = "emailAddress=" + emailAddress + "&newPassword1=" + newPassword1
			+ "&oldPassword=" + oldPassword;
	obj.open("GET", "/EmployeeOperation/UpdatePassword.htm?" + str, true);
	obj.send();
	obj.onreadystatechange = function() {
		if (obj.readyState == 4 && obj.status == 200) {

			$("#pswdResultBox").hide();
			$("#resultBox").slideDown();
			document.getElementById("resultBox").innerHTML = obj.responseText;
			document.getElementById('oldPassword').value = "";
			document.getElementById('newPassword1').value = "";
			document.getElementById('newPassword2').value = "";
			$(document).ready(function() {
				$("#resultBox").slideDown();
				setTimeout(function() {
					$("#resultBox").slideUp();
				}, 3000);
			});

		}
	}

}
$(document)
		.ready(
				function() {

					$("#cleavesBalanceBefore").css("background-color", "white");
					$("#cleavesBalanceBefore").css("color", "black");
					$("#cleavesBalanceAfter").css("background-color", "white");
					$("#cleavesBalanceAfter").css("color", "black");

					$("#sleavesBalanceBefore").css("background-color", "white");
					$("#sleavesBalanceBefore").css("color", "black");
					$("#sleavesBalanceAfter").css("background-color", "white");
					$("#sleavesBalanceAfter").css("color", "black");

					$("#pleavesBalanceBefore").css("background-color", "white");
					$("#pleavesBalanceBefore").css("color", "black");
					$("#pleavesBalanceAfter").css("background-color", "white");
					$("#pleavesBalanceAfter").css("color", "black");

					$("#pswdResultBox").hide();
					$("#resultBox").hide();
					$("#resultBoxLeave").hide();
					$("#loaderGif").hide();
					$("#cleavesradio").attr('disabled', 'disabled');
					$("#pleavesradio").attr('disabled', 'disabled');
					$("#sleavesradio").attr('disabled', 'disabled');
					$("#leaveApplyButton").attr('disabled', 'disabled');
					// $("#leaveApplyButton").hide();
					// $("#leaveSubmitButton").attr('disabled', 'disabled');
					$("img").hide();
					$("#resultForLeaveStatus").hide();
					document.getElementById('startingDate').valueAsDate = new Date();
					document.getElementById('endingDate').valueAsDate = new Date();

					var d = new Date();
					document.getElementById('todayDate').value = [
							d.getMonth() + 1, d.getDate(), d.getFullYear() ]
							.join('/');

					$(".date")
							.change(
									function() {

										var date1 = new Date(
												document
														.getElementById("startingDate").value);
										var date2 = new Date(
												document
														.getElementById("endingDate").value);

										var diffinsec = (date2.getTime() - date1
												.getTime());
										var diffDays = (diffinsec / (1000 * 3600 * 24));

										var absdiffinsec = Math.abs(date2
												.getTime()
												- date1.getTime());
										var absdiffDays = Math.ceil(diffinsec
												/ (1000 * 3600 * 24));
										// alert(" Days diff is :"
										// +diffDays);

										var todayDate = new Date();
										var difffrom = Math.ceil((date1
												.getTime() - todayDate)
												/ (1000 * 3600 * 24));
										var diffto = Math
												.ceil((date2.getTime() - todayDate)
														/ (1000 * 3600 * 24));

										if (diffDays >= 0 && difffrom >= 0
												&& diffto >= 0) {

											document
													.getElementById("dateerror").innerHTML = "";
											// Casual leaves
											if ($('#cleavesBalanceAfter').val() < 0) {
												$("#cleavesrevoked").show();
												$("#cleavesgranted").hide();
												$("#cleavesradio").attr(
														'disabled', 'disabled');
												$("#cleavesradio").prop(
														'checked', false);
												$("#cleavesradio").removeAttr(
														"disabled");
											} else {
												$("#cleavesgranted").show();
												$("#cleavesrevoked").hide();
												$("#cleavesradio").removeAttr(
														"disabled");

											}
											// PersonalLeaves
											if ($('#pleavesBalanceAfter').val() < 0) {
												$("#pleavesrevoked").show();
												$("#pleavesgranted").hide();
												$("#pleavesradio").attr(
														'disabled', 'disabled');
												$("#pleavesradio").prop(
														'checked', false);
											} else {
												$("#pleavesgranted").show();
												$("#pleavesrevoked").hide();
												$("#pleavesradio").removeAttr(
														"disabled");
											}
											// Sick Leaves
											if ($('#sleavesBalanceAfter').val() < 0) {
												$("#sleavesrevoked").show();
												$("#sleavesgranted").hide();
												$("#sleavesradio").attr(
														'disabled', 'disabled');
												$("#sleavesradio").prop(
														'checked', false);
											} else {
												$("#sleavesgranted").show();
												$("#sleavesrevoked").hide();
												$("#sleavesradio").removeAttr(
														"disabled");
											}

											if (($('#cleavesBalanceAfter')
													.val() < 0)
													|| ($(
															'#pleavesBalanceAfter')
															.val() < 0)
													|| ($(
															'#sleavesBalanceAfter')
															.val() < 0)) {

												$("#leaveSubmitButton")
														.removeAttr("disabled");

											}

											if (($('#cleavesBalanceAfter')
													.val() < 0)
													&& ($(
															'#pleavesBalanceAfter')
															.val() < 0)
													&& ($(
															'#sleavesBalanceAfter')
															.val() < 0)) {

												$("#leaveApplyButton").attr(
														'disabled', 'disabled');
												$("#cleavesradio").attr(
														'disabled', 'disabled');
												$("#pleavesradio").attr(
														'disabled', 'disabled');
												$("#sleavesradio").attr(
														'disabled', 'disabled');
											}
										} else {
											document
													.getElementById("daysOfLeaves").value = 0;
											$("#cleavesradio").attr('disabled',
													'disabled');
											$("#pleavesradio").attr('disabled',
													'disabled');
											$("#sleavesradio").attr('disabled',
													'disabled');
											document
													.getElementById("cleavesBalanceAfter").value = "";
											document
													.getElementById("pleavesBalanceAfter").value = "";
											document
													.getElementById("sleavesBalanceAfter").value = "";
											$("#cleavesradio").prop('checked',
													false);
											$("#sleavesradio").prop('checked',
													false);
											$("#pleavesradio").prop('checked',
													false);
											$("#cleavesrevoked").hide();
											$("#cleavesgranted").hide();
											$("#pleavesrevoked").hide();
											$("#pleavesgranted").hide();
											$("#sleavesrevoked").hide();
											$("#sleavesgranted").hide();
											$("#leaveApplyButton").attr(
													'disabled', 'disabled');

										}
									});

				});
// /////////////////////////ss

function ajax() {
	var emailAddress = document.getElementById("emailAddress").value;
	var obj = new XMLHttpRequest();
	obj.open("GET", "/LeaveOperation/CheckLeaveStatus.htm?emailAddress="
			+ emailAddress, true);
	obj.send();
	obj.onreadystatechange = function() {
		if (obj.readyState == 4 && obj.status == 200) {
			var json = JSON.parse(obj.responseText);
			if (parseInt(json.cleaves) < 5) {
				$("#cleaves").css("background-color", "red");
				$("#cleaves").css("color", "black");
			} else if (parseInt(json.cleaves) == 5) {
				$("#cleaves").css("background-color", "yellow");
				$("#cleaves").css("color", "black");
			} else if (parseInt(json.cleaves) > 5) {
				$("#cleaves").css("background-color", "green");
				$("#cleaves").css("color", "black");
			}

			if (parseInt(json.pleaves) < 5) {
				$("#pleaves").css("background-color", "red");
				$("#pleaves").css("color", "black");
			} else if (parseInt(json.pleaves) == 5) {
				$("#pleaves").css("background-color", "yellow");
				$("#pleaves").css("color", "black");
			} else if (parseInt(json.pleaves) > 5) {
				$("#pleaves").css("background-color", "green");
				$("#pleaves").css("color", "black");
			}

			if (parseInt(json.sleaves) < 5) {
				$("#sleaves").css("background-color", "red");
				$("#sleaves").css("color", "black");
			} else if (parseInt(json.sleaves) == 5) {
				$("#sleaves").css("background-color", "yellow");
				$("#sleaves").css("color", "black");
			} else if (parseInt(json.sleaves) > 5) {
				$("#sleaves").css("background-color", "green");
				$("#sleaves").css("color", "black");
			}

			document.getElementById("cleaves").value = json.cleaves;
			document.getElementById("cleavesBalanceBefore").value = json.cleaves;

			document.getElementById("pleaves").value = json.pleaves;
			document.getElementById("pleavesBalanceBefore").value = json.pleaves;

			document.getElementById("sleaves").value = json.sleaves;
			document.getElementById("sleavesBalanceBefore").value = json.sleaves;
		}
	}

}
function noOfDaysLeave() {

	var date1 = new Date(document.getElementById("startingDate").value);
	var date2 = new Date(document.getElementById("endingDate").value);

	var diffinsec = (date2.getTime() - date1.getTime());
	var diffDays = (diffinsec / (1000 * 3600 * 24));

	var absdiffinsec = Math.abs(date2.getTime() - date1.getTime());
	var absdiffDays = Math.ceil(diffinsec / (1000 * 3600 * 24));

	var todayDate = new Date();
	var difffrom = Math
			.ceil((date1.getTime() - todayDate) / (1000 * 3600 * 24));
	var diffto = Math.ceil((date2.getTime() - todayDate) / (1000 * 3600 * 24));
	console.log(" From - today " + difffrom);
	console.log(" To - today " + diffto);

	// alert(date1.getUTCDay());

	var day1 = date1.getUTCDay();
	var day2 = date2.getUTCDay();

//	if ((day1 == 0) || (day1 == 6)) {
//		alert("1");
//		document.getElementById("dateerror").innerHTML = "Starting Date cannot be a Weekend";
//		console.log("d");
//		return;
//	}
//
//	if ((day2 == 0) || (day2 == 6)) {
//		alert("2");
//		document.getElementById("dateerror").innerHTML = "Ending Date cannot be a Weekend";
//		console.log("e");
//		return;
//	}

	if (diffDays < 0) {
		document.getElementById("dateerror").innerHTML = "Dates range are improper";
		console.log("a");
		return;
	} else if (difffrom < 0) {
		document.getElementById("dateerror").innerHTML = "Starting Date cannot be less than Current Data";
		console.log("b");
		return;
	} else if (diffto < 0) {
		document.getElementById("dateerror").innerHTML = "Ending Date cannot be less than Current Data";
		console.log("c");
		return;
		// }
		// else if ((date1.getUTCDay() == 0) || ( date1.getUTCDay() == 6)) {
		// alert("1");
		// document.getElementById("dateerror").innerHTML = "Starting Date
		// cannot be a
		// Weekend";
		// console.log("d");
		// return;
		// } else if ((date2.getUTCDay() == 0) || ( date2.getUTCDay() == 6)) {
		// alert("2");
		// document.getElementById("dateerror").innerHTML = "Ending Date cannot
		// be a
		// Weekend";
		// console.log("e");
		// return;
	} else {

		var a = Math.floor(absdiffDays / 7);
		console.log("a=" + a);
		// alert(a);
		var weekends = a * 2;
		var b = Math.floor(absdiffDays % 7);
		console.log("b=" + b);
		// alert(b);

		var startingDateNumber = date1.getUTCDay();
		var temp = startingDateNumber + b;
		if (temp == 6)
			weekends = weekends + 1;
		else if (temp >= 7)
			weekends = weekends + 2;

		console.log(" Weekends : " + weekends);
		alert(diffDays);

		document.getElementById("daysOfLeaves").value = (absdiffDays + 1)
				- weekends;
		clcalculation();
		plcalculation();
		slcalculation();
	}
}
function clcalculation() {
	var balance = document.getElementById("cleavesBalanceBefore").value
	var requested = document.getElementById("daysOfLeaves").value
	var left = balance - requested;
	document.getElementById("cleavesBalanceAfter").value = left;
}
function plcalculation() {
	var balance = document.getElementById("pleavesBalanceBefore").value
	var requested = document.getElementById("daysOfLeaves").value
	var left = balance - requested;
	document.getElementById("pleavesBalanceAfter").value = left;
}
function slcalculation() {
	var balance = document.getElementById("sleavesBalanceBefore").value
	var requested = document.getElementById("daysOfLeaves").value
	var left = balance - requested;
	document.getElementById("sleavesBalanceAfter").value = left;
}

function leaveRequestAjax() {
	$("#loaderGif").show();
	var todayDate = document.getElementById("todayDate").value;
	var startingDate = document.getElementById("startingDate").value;
	var endingDate = document.getElementById("endingDate").value;
	var daysOfLeaves = document.getElementById("daysOfLeaves").value;
	var comment = document.getElementById("comment").value;
	var leaveType;
	if (document.getElementById("cleavesradio").checked)
		leaveType = "Casual Leaves";
	else if (document.getElementById("pleavesradio").checked)
		leaveType = "Personal Leaves"
	else if (document.getElementById("sleavesradio").checked)
		leaveType = "Sick Leaves";

	console.log("Before : " + leaveType);

	console.log(todayDate);
	console.log(startingDate);
	console.log(endingDate);
	console.log(daysOfLeaves);
	console.log(leaveType);
	console.log(comment);

	var obj = new XMLHttpRequest();
	var queryString = "/LeaveOperation/RequestForLeave.htm?todayDate="
			+ todayDate + "&startingDate=" + startingDate + "&endingDate="
			+ endingDate + "&daysOfLeaves=" + daysOfLeaves + "&leaveType="
			+ leaveType + "&comment=" + comment;

	obj.open("GET", queryString, true);
	obj.send();
	obj.onreadystatechange = function() {
		if (obj.readyState == 4 && obj.status == 200) {
			$("#loaderGif").hide();
			$("#resultBoxLeave").show();
			var json = JSON.parse(obj.responseText);
			if (json.status == true)
				document.getElementById("resultBoxLeave").innerHTML = "Successfull! Leave Application Id : "
						+ json.leaveApplicationId;
			else
				document.getElementById("resultBoxLeave").innerHTML = "Leave Application unsuccessful";
		}
	}

}

function selectLeaveType() {
	$('#leaveApplyButton').removeAttr('disabled');

	if (document.getElementById("cleavesradio").checked) {
		$("#cleavesBalanceBefore").css("background-color", "black");
		$("#cleavesBalanceBefore").css("color", "white");
		$("#cleavesBalanceAfter").css("background-color", "black");
		$("#cleavesBalanceAfter").css("color", "white");

		$("#pleavesBalanceBefore").css("background-color", "white");
		$("#pleavesBalanceBefore").css("color", "black");
		$("#pleavesBalanceAfter").css("background-color", "white");
		$("#pleavesBalanceAfter").css("color", "black");

		$("#sleavesBalanceBefore").css("background-color", "white");
		$("#sleavesBalanceBefore").css("color", "black");
		$("#sleavesBalanceAfter").css("background-color", "white");
		$("#sleavesBalanceAfter").css("color", "black");

	} else if (document.getElementById("pleavesradio").checked) {
		$("#pleavesBalanceBefore").css("background-color", "black");
		$("#pleavesBalanceBefore").css("color", "white");
		$("#pleavesBalanceAfter").css("background-color", "black");
		$("#pleavesBalanceAfter").css("color", "white");

		$("#cleavesBalanceBefore").css("background-color", "white");
		$("#cleavesBalanceBefore").css("color", "black");
		$("#cleavesBalanceAfter").css("background-color", "white");
		$("#cleavesBalanceAfter").css("color", "black");

		$("#sleavesBalanceBefore").css("background-color", "white");
		$("#sleavesBalanceBefore").css("color", "black");
		$("#sleavesBalanceAfter").css("background-color", "white");
		$("#sleavesBalanceAfter").css("color", "black");

	} else if (document.getElementById("sleavesradio").checked) {
		$("#sleavesBalanceBefore").css("background-color", "black");
		$("#sleavesBalanceBefore").css("color", "white");
		$("#sleavesBalanceAfter").css("background-color", "black");
		$("#sleavesBalanceAfter").css("color", "white");

		$("#cleavesBalanceBefore").css("background-color", "white");
		$("#cleavesBalanceBefore").css("color", "black");
		$("#cleavesBalanceAfter").css("background-color", "white");
		$("#cleavesBalanceAfter").css("color", "black");

		$("#pleavesBalanceBefore").css("background-color", "white");
		$("#pleavesBalanceBefore").css("color", "black");
		$("#pleavesBalanceAfter").css("background-color", "white");
		$("#pleavesBalanceAfter").css("color", "black");
	}

}
