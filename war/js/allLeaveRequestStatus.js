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
$(document).ready(function() {
	$("#resultBox").hide();
	$("#loaderGif").hide();
	$("#pswdResultBox").hide();

});
function updatePasswordAjax() {
	var x = validateForm();
	if (x == false)
		return;
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
			document.getElementById("pleaves").value = json.pleaves;
			document.getElementById("sleaves").value = json.sleaves;
		}
	}

}