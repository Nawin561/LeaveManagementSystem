$(document).ready(function() {
		$("#result").hide();
		$("#resultBox").hide();
		$("#resultBoxPassword").hide();
		$("#loaderGif").hide();
		$("#approveButton").attr('disabled', 'disabled');
		$("#rejectButton").attr('disabled', 'disabled');
		$("#pswdResultBox").hide();
	});

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
	function updatePasswordAjax() {
		var x = validateForm();
		if (x == false)
			return;
		$("#pswdResultBox").show();
		var oldPassword = document.getElementById("oldPassword").value;
		var newPassword1 = document.getElementById("newPassword1").value;
		var emailAddress = document.getElementById("emailAddress").value;
		var obj = new XMLHttpRequest();
		var str = "emailAddress=" + emailAddress + "&newPassword1="
				+ newPassword1 + "&oldPassword=" + oldPassword;
		obj.open("GET", "/EmployeeOperation/UpdatePassword.htm?" + str, true);
		obj.send();
		obj.onreadystatechange = function() {
			if (obj.readyState == 4 && obj.status == 200) {
				$("#pswdResultBox").hide();
				document.getElementById("resultBoxPassword").innerHTML = obj.responseText;
				$("#resultBoxPassword").slideDown();

				$(document).ready(function() {
					$("#resultBoxPassword").slideDown();
					setTimeout(function() {
						$("#resultBoxPassword").slideUp();
					}, 3000);
				});
				document.getElementById('oldPassword').value = "";
				document.getElementById('newPassword1').value = "";
				document.getElementById('newPassword2').value = "";
			}
		}

	}
	function ajax() {
		var emailAddress = document.getElementById("emailAddress").value;
		var obj = new XMLHttpRequest();
		obj.open("GET", "/LeaveOperation/CheckLeaveStatus.htm?emailAddress="
				+ emailAddress, true);
		obj.send();
		obj.onreadystatechange = function() {
			if (obj.readyState == 4 && obj.status == 200) {
				var json = JSON.parse(obj.responseText);
				document.getElementById("cleaves").value = json.cleaves;
				document.getElementById("pleaves").value = json.pleaves;
				document.getElementById("sleaves").value = json.sleaves;
			}
		}

	}
	function approveLeaveAppAjax(tableID) {
		var key;
		var radios = document.getElementsByName("emprecord");
		var index = 0;
		for (var i = 0; i < radios.length; i++) {
			if (radios[i].checked) {
				key = radios[i].value;
				break;
			}
			index++;
		}
		index++;
		var applicantId = document.getElementById("applicantId").value;
		var obj = new XMLHttpRequest();
		obj.open("GET", "/LeaveOperation/ApproveLeaveRequest.htm?key="
				+ applicantId, true);
		obj.send();
		obj.onreadystatechange = function() {
			if (obj.readyState == 4 && obj.status == 200) {
				var d = new Date();
				var todayDate = d.getFullYear() + "-"
						+ parseInt(d.getMonth() + 1) + "-" + d.getDate();
				alert(todayDate);

				document.getElementById("tableData").rows[index].cells[8].innerHTML = "Approved";
				document.getElementById("tableData").rows[index].cells[9].innerHTML = todayDate;

				document.getElementById("resultBox").innerHTML = obj.responseText;
				$("#result").slideUp();
				$("#approveButton").attr('disabled', 'disabled');
				$("#rejectButton").attr('disabled', 'disabled');

			}
		}

	}

	function rejectLeaveAppAjax(tableID) {
		var key;
		var radios = document.getElementsByName("emprecord");
		var index = 0;
		for (var i = 0; i < radios.length; i++) {
			if (radios[i].checked) {
				key = radios[i].value;
				break;
			}
			index++;
		}
		index++;
		var applicantId = document.getElementById("applicantId").value;
		var emailAddress = document.getElementById("employeeEmail").value;
		var noOfDays = document.getElementById("noOfDays").value;
		var leaveType = document.getElementById("leaveType").value;
		var str = "key=" + applicantId + "&emailAddress=" + emailAddress
				+ "&noOfDays=" + noOfDays + "&leaveType=" + leaveType;
		var obj = new XMLHttpRequest();
		obj.open("GET", "/LeaveOperation/RejectLeaveRequest.htm?" + str, true);
		obj.send();
		obj.onreadystatechange = function() {
			if (obj.readyState == 4 && obj.status == 200) {
				var d = new Date();
				var todayDate = d.getFullYear() + "-"
						+ parseInt(d.getMonth() + 1) + "-" + d.getDate();
				console.log(todayDate);

				document.getElementById("tableData").rows[index].cells[8].innerHTML = "Rejected";
				document.getElementById("tableData").rows[index].cells[9].innerHTML = todayDate;
				document.getElementById("resultBox").innerHTML = obj.responseText;
				$("#result").slideUp();
				$("#approveButton").attr('disabled', 'disabled');
				$("#rejectButton").attr('disabled', 'disabled');

			}
		}

	}

	function ajaxFunctionForRetrievingLeaveRecord() {
		var key;
		var radios = document.getElementsByName("emprecord");

		for (var i = 0; i < radios.length; i++) {
			if (radios[i].checked) {
				// 	            alert(radios[i].value);
				key = radios[i].value;
				break;
			}
		}
		// 	alert("key is " + key);
		var str = "key=" + key;
		var obj = new XMLHttpRequest();
		// 	alert("str is " + str);
		obj.open("POST", "/LeaveOperation/retrieveParticularLeaveRecord.htm?"
				+ str, true);
		obj.send();
		obj.onreadystatechange = function() {
			if (obj.readyState == 4 && obj.status == 200) {
				var json1 = JSON.parse(obj.responseText);
				$("#result").slideDown();
				document.getElementById("applicantId").value = json1.applicantId;
				document.getElementById("employeeName").value = json1.employeeName;
				document.getElementById("employeeEmail").value = json1.employeeEmail;
				document.getElementById("appliedDate").value = json1.appliedDate;

				document.getElementById("startDate").value = json1.startDate;
				document.getElementById("endDate").value = json1.endDate;
				document.getElementById("noOfDays").value = json1.noOfDays;

				document.getElementById("leaveType").value = json1.leaveType;
				document.getElementById("status").value = json1.status;
				document.getElementById("appRejDate").value = json1.appRejDate;

				if ((document.getElementById("status").value) == ("NA")) {
					$('#approveButton').removeAttr('disabled');
					$('#rejectButton').removeAttr('disabled');

				} else {
					$("#approveButton").attr('disabled', 'disabled');
					$("#rejectButton").attr('disabled', 'disabled');
				}

			}
		}
	}
	function currentLeaveStatusByAjax() {
		var emailAddress = document.getElementById("emailAddress").value;
		// 		alert(emailAddress);
		//  	alert("for leave status ajax started");
		var obj = new XMLHttpRequest();
		obj.open("GET", "/LeaveOperation/CheckLeaveStatus.htm?emailAddress="
				+ emailAddress, true);
		obj.send();
		obj.onreadystatechange = function() {
			if (obj.readyState == 4 && obj.status == 200) {
				// 				alert("ok");

				var json = JSON.parse(obj.responseText);
				document.getElementById("cleaves").value = json.cleaves;
				document.getElementById("pleaves").value = json.pleaves;
				document.getElementById("sleaves").value = json.sleaves;

			}
		}

	}