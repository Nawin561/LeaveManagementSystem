$(document).ready(function() {
	$("#resultBox").hide();
	$("#loaderGif").hide();
});
function validateForm() {
	// Department Name validation
	if ((document.getElementById('departmentName').value).trim() == "") {
		document.getElementById("departmentError").innerHTML = "*Mandatory Field";
		return false;
	} else {
		document.getElementById("departmentError").innerHTML = "";
		document.form1.departmentName.focus();
	}

	// Manager Name validation
	if ((document.getElementById('managerName').value).trim() == "") {
		document.getElementById("managerError").innerHTML = "*Mandatory Field";
		return false;
	} else {
		document.getElementById("managerError").innerHTML = "";
		document.form1.managerName.focus();
	}

	// Manager's emailAddress validation
	if ((document.getElementById('managerEmail').value).trim() == "") {
		document.getElementById("managerEmailError").innerHTML = "*Mandatory Field";
		return false;
	} else {
		var managerEmail = document.getElementById('managerEmail').value;
		if (managerEmail.indexOf("@", 0) < 0) {
			document.getElementById("managerEmailError").innerHTML = "*Invalid Email ID";
			return false;

		} else if (managerEmail.indexOf(".", 0) < 0) {
			document.getElementById("managerEmailError").innerHTML = "*Invalid Email ID";
			return false;
		} else {
			document.getElementById("managerEmailError").innerHTML = "";
			document.form1.managerEmail.focus();
		}
	}

}

function ajaxFunction() {
	var x = validateForm();
	if (x == false)
		return;
	$("#loaderGif").show();
	var departmentName = document.getElementById("departmentName").value;
	var managerName = document.getElementById("managerName").value;
	var managerEmail = document.getElementById("managerEmail").value;
	var str = "departmentName=" + departmentName + "&managerName="
			+ managerName + "&managerEmail=" + managerEmail;
	var obj = new XMLHttpRequest();
	obj.open("POST", "/DepartmentOperation/addDepartment.htm?" + str, true);
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

			document.getElementById("departmentName").value = "";
			document.getElementById("managerName").value = "";
			document.getElementById("managerEmail").value = "";

			document.form1.departmentName.focus();
		}
	}
}