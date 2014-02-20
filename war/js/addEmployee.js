$(document).ready(function() {
		$("#resultBox").hide();
		$("#loaderGif").hide();

	});
	function validateForm() {

		// Name validation
		if ((document.getElementById('employeeName').value).trim() == "") {
			document.getElementById("nameError").innerHTML = "*Mandatory Field";
			return false;
		} else {
			document.getElementById("nameError").innerHTML = "";
			document.form1.employeeName.focus();
		}

		var nameOfEmployee = document.getElementById('employeeName').value;
		if ((nameOfEmployee.length < 5)) {
			document.getElementById("nameError").innerHTML = "*Length Should be atleast of 5 characters";
			return false;
		} else {
			document.getElementById("nameError").innerHTML = "";
		}

		//emailAddress validation
		if ((document.getElementById('emailAddress').value).trim() == "") {
			document.getElementById("emailError").innerHTML = "*Mandatory Field";

			return false;
		} else {
			var emailAddress = document.getElementById('emailAddress').value;
			if (emailAddress.indexOf("@", 0) < 0) {
				document.getElementById("emailError").innerHTML = "*Invalid Email ID";
				return false;

			} else if (emailAddress.indexOf(".", 0) < 0) {
				document.getElementById("emailError").innerHTML = "*Invalid Email ID";
				return false;
			} else {
				document.getElementById("emailError").innerHTML = "";
				document.form1.emailAddress.focus();
			}
		}

		//contact no
		var a = document.getElementById('contactNo').value;
		if ((document.getElementById('contactNo').value).trim() == "") {
			document.getElementById("contactError").innerHTML = "*Mandatory Field";
		} else {
			var a = document.form1.contactNo.value;
			if (isNaN(a)) {
				document.getElementById("contactError").innerHTML = "*Enter the valid Mobile Number(Like : 9566137117)";
				return false;
			} else if ((a.length != 10)) {
				document.getElementById("contactError").innerHTML = "*Your Mobile Number must be of 10 digits";
				return false;
				document.form.contactNo.select();
			} else {
				document.getElementById("contactError").innerHTML = "";
				document.form1.contactNo.focus();
			}
		}

		//EmpType validation
		if ((document.form1.employeeType[0].checked == false)
				&& (document.form1.employeeType[1].checked == false)) {
			document.getElementById("empTypeError").innerHTML = "*Mandatory Field";
			return false;
		} else {
			document.getElementById("empTypeError").innerHTML = "";

		}

	}

	function retrieveManagerNameAjax() {
		var team = document.getElementById("team").value;
		var str = "team=" + team;
		var obj = new XMLHttpRequest();
		obj.open("GET", "/EmployeeOperation/retrieveManagerName.htm?" + str,
				true);
		obj.send();
		obj.onreadystatechange = function() {
			if (obj.readyState == 4 && obj.status == 200) {
				document.getElementById("reportingTo").value = obj.responseText;
			}
		}

	}

	function addEmployeeAjax() {
		var x = validateForm();
		if (x == false)
			return;
		$(document).ready(function() {
			$("#loaderGif").show();

		});
		var employeeType;
		var radios = document.getElementsByName("employeeType");
		for (var i = 0; i < radios.length; i++) {
			if (radios[i].checked) {
				employeeType = radios[i].value;
				break;
			}
		}

		var x = document.getElementById("team").selectedIndex;
		var team = document.getElementsByTagName("option")[x].value;

		var employeeName = document.getElementById("employeeName").value;
		var reportingTo = document.getElementById("reportingTo").value;
		var emailAddress = document.getElementById("emailAddress").value;
		var contactNo = document.getElementById("contactNo").value;
		var cleaves = document.getElementById("cleaves").value;
		var pleaves = document.getElementById("pleaves").value;
		var sleaves = document.getElementById("sleaves").value;
		var str = "employeeName=" + employeeName + "&reportingTo="
				+ reportingTo + "&team=" + team + "&emailAddress="
				+ emailAddress + "&contactNo=" + contactNo + "&cleaves="
				+ cleaves + "&pleaves=" + pleaves + "&sleaves=" + sleaves
				+ "&employeeType=" + employeeType;
		var obj = new XMLHttpRequest();
		obj.open("POST", "/EmployeeOperation/addEmployee.htm?" + str, true);
		obj.send();
		obj.onreadystatechange = function() {
			if (obj.readyState == 4 && obj.status == 200) {
				$("#loaderGif").hide();
				document.getElementById("resultBox").innerHTML = obj.responseText;
				$(document).ready(function() {
					$("#resultBox").slideDown();
					setTimeout(function() {
						$("#resultBox").slideUp();
					}, 3000);
				});

				document.getElementById('employeeName').value = "";
				document.getElementById('emailAddress').value = "";
				document.getElementById('contactNo').value = "";
				document.getElementById('cleaves').value = "5";
				document.getElementById('pleaves').value = "5";
				document.getElementById('sleaves').value = "5";
				document.form1.employeeType[0].checked = false;
				document.form1.employeeType[1].checked = false;
				document.form1.employeeName.focus();

			}
		}
	}