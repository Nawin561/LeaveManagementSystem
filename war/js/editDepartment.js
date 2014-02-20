$(document).ready(function() {
	$("#loaderGif").hide();
	$("#resultTable").hide();
	$("#resultBox").hide();
});
function validateForm() {

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

// Ajax function for retrieving details of the department from database
function retrieveDeptDetails() {
	$("#loaderGif").show();
	var departmentName;
	var radios = document.getElementsByName("deprecord");
	for (var i = 0; i < radios.length; i++) {
		if (radios[i].checked) {
			departmentName = radios[i].value;
			break;
		}
	}
	var obj = new XMLHttpRequest();
	obj.open("POST", "/DepartmentOperation/RetrieveDepartmentDetails.htm?"
			+ "departmentName=" + departmentName, true);
	obj.send();
	obj.onreadystatechange = function() {
		if (obj.readyState == 4 && obj.status == 200) {
			var json = JSON.parse(obj.responseText);
			$("#loaderGif").hide();
			$("#resultTable").slideDown();
			document.getElementById("managerName").value = json.managerName;
			document.getElementById("managerEmail").value = json.managerEmail;
			document.getElementById("departmentName").value = json.departmentName;
		}
	}
}
// Ajax function for getting the status after edition
function updateStatus(tableID) {
	$("#loaderGif").show();
	var x = validateForm();
	if (x == false)
		return;
	var index = 1;
	var radios = document.getElementsByName("deprecord");
	for (var i = 0; i < radios.length; i++) {
		if (radios[i].checked) {
			break;
		}
		index++;
	}
	index++;
	var departmentName = document.getElementById("departmentName").value;
	var managerName = document.getElementById("managerName").value;
	var managerEmail = document.getElementById("managerEmail").value;
	var str = "departmentName=" + departmentName + "&managerName="
			+ managerName + "&managerEmail=" + managerEmail;
	var obj = new XMLHttpRequest();
	obj.open("POST", "/DepartmentOperation/updateDepartment.htm?" + str, true);
	obj.send();
	obj.onreadystatechange = function() {
		if (obj.readyState == 4 && obj.status == 200) {
			$("#loaderGif").hide();
			console.log("index is : " + index);
			try {
				var table = document.getElementById("dataTable");
				var rowCount = table.rows.length;
				var json = JSON.parse(obj.responseText);
				document.getElementById("dataTable").rows[index].cells[1].innerHTML = json.managerName;
				document.getElementById("dataTable").rows[index].cells[2].innerHTML = json.managerEmail;
				document.getElementById("resultBox").innerHTML = "Record Updated";
				$("#resultTable").slideUp();
				document.getElementById("managerName").value = "";
				document.getElementById("managerEmail").value = "";
				document.getElementById("departmentName").value = "";
				$(document).ready(function() {
					$("#resultBox").slideDown();
					setTimeout(function() {
						$("#resultBox").slideUp();

					}, 3000);
				});

			}

			catch (e) {
				alert(e);
			}

		}
	}
}