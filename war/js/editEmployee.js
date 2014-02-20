	$(document).ready(
			function() {
				$("#resultBox").hide();
				$("#loaderGif").hide();
				$("#resultTable").hide();

				document.getElementById('employeeName').setAttribute(
						'readonly', 'readonly');
				document.getElementById('emailAddress').setAttribute(
						'readonly', 'readonly');
				document.getElementById('contactNo').setAttribute('readonly',
						'readonly');
				document.getElementById('reportingTo').setAttribute('readonly',
						'readonly');
				document.getElementById('cleaves').setAttribute('readonly',
						'readonly');
				document.getElementById('pleaves').setAttribute('readonly',
						'readonly');
				document.getElementById('sleaves').setAttribute('readonly',
						'readonly');
				document.getElementById('empType').setAttribute('readonly',
						'readonly');

			});

	function retrieveManagerNameAjax() {
		var empTeam = document.getElementById("empTeam").value;
		var str = "team=" + empTeam;
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

	function validateForm() {
		// Name validation
		if ((document.getElementById('employeeName').value).trim() == "") {
			document.getElementById("nameError").innerHTML = "*Mandatory Field";
			return false;
		} else {
			document.getElementById("nameError").innerHTML = "";
			document.form1.employeeName.focus();
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
	}
	function retrieveEmployeeDetail() {
		$("#loaderGif").show();
		var emailAddress;
		var radios = document.getElementsByName("emprecord");
		for (var i = 0; i < radios.length; i++) {
			if (radios[i].checked) {
				emailAddress = radios[i].value;
				break;
			}
		}
		var obj = new XMLHttpRequest();
		obj.open("POST", "/EmployeeOperation/RetrieveEmpDetails.htm?"
				+ "emailAddress=" + emailAddress, true);
		obj.send();
		obj.onreadystatechange = function() {
			if (obj.readyState == 4 && obj.status == 200) {
				var json = JSON.parse(obj.responseText);
				$("#loaderGif").hide();
				$(document).ready(function() {
					$("#resultTable").slideDown();
				});

				document.getElementById("employeeName").value = json.name;
				document.getElementById("empTeam").value = json.team;
				document.getElementById("reportingTo").value = json.reportingTo;
				document.getElementById("emailAddress").value = json.emailAddress;
				document.getElementById("contactNo").value = json.contactNo;
				document.getElementById("empType").value = json.empType;
				document.getElementById("cleaves").value = json.cleaves;
				document.getElementById("pleaves").value = json.pleaves;
				document.getElementById("sleaves").value = json.sleaves;

				document.getElementById('employeeName').removeAttribute(
						'readonly');
				document.getElementById('empTeam').removeAttribute('readonly');
				document.getElementById('contactNo')
						.removeAttribute('readonly');
				document.getElementById('cleaves').removeAttribute('readonly');
				document.getElementById('pleaves').removeAttribute('readonly');
				document.getElementById('sleaves').removeAttribute('readonly');
				document.getElementById('empType').removeAttribute('readonly');

			}
		}
	}

	function updateEmployeeDetailAjax(tableID) {
		var x = validateForm();
		if (x == false)
			return;
		$(document).ready(function() {
			$("#loaderGif").show();

		});
		var emailAddress;
		var index = 0;
		var radios = document.getElementsByName("emprecord");
		for (var i = 0; i < radios.length; i++) {
			if (radios[i].checked) {
				emailAddress = radios[i].value;
				break;
			}
			index++;
		}
		index++;

		var x = document.getElementById("empTeam").selectedIndex;
		var team = document.getElementsByTagName("option")[x].value;

		var employeeName = document.getElementById("employeeName").value;
		var reportingTo = document.getElementById("reportingTo").value;
		var emailAddress = document.getElementById("emailAddress").value;
		var contactNo = document.getElementById("contactNo").value;
		var cleaves = document.getElementById("cleaves").value;
		var pleaves = document.getElementById("pleaves").value;
		var sleaves = document.getElementById("sleaves").value;
		var empType = document.getElementById("empType").value;

		var str = "employeeName=" + employeeName + "&reportingTo="
				+ reportingTo + "&team=" + team + "&emailAddress="
				+ emailAddress + "&contactNo=" + contactNo + "&cleaves="
				+ cleaves + "&pleaves=" + pleaves + "&sleaves=" + sleaves
				+ "&employeeType=" + empType;
		var obj = new XMLHttpRequest();
		obj.open("POST", "/EmployeeOperation/saveEmployee.htm?" + str, true);
		obj.send();
		obj.onreadystatechange = function() {
			if (obj.readyState == 4 && obj.status == 200) {
				// 				document.getElementById("resultBox").innerHTML = obj.responseText;

				try {
					var table = document.getElementById("dataTable");
					var rowCount = table.rows.length;
					var json = JSON.parse(obj.responseText);
					document.getElementById("dataTable").rows[index].cells[1].innerHTML = json.name;
					document.getElementById("dataTable").rows[index].cells[2].innerHTML = json.team;
					document.getElementById("dataTable").rows[index].cells[3].innerHTML = json.reportingTo;
					document.getElementById("dataTable").rows[index].cells[4].innerHTML = json.contactNo;
					document.getElementById("dataTable").rows[index].cells[5].innerHTML = json.empType;
					document.getElementById("dataTable").rows[index].cells[6].innerHTML = json.cleaves;
					document.getElementById("dataTable").rows[index].cells[7].innerHTML = json.pleaves;
					document.getElementById("dataTable").rows[index].cells[8].innerHTML = json.sleaves;
					document.getElementById("resultBox").innerHTML = "Rescord of "
							+ json.name + " updated successfully";

					$(document).ready(function() {
						$("#loaderGif").hide();
						$("#resultBox").slideDown();
						setTimeout(function() {
							$("#resultBox").slideUp();
							$("#resultTable").slideUp();
						}, 3000);
					});

					document.getElementById('employeeName').value = "";
					document.getElementById('emailAddress').value = "";
					document.getElementById('contactNo').value = "";
					document.getElementById('empTeam').value = "";
					document.getElementById('reportingTo').value = "";
					document.getElementById('cleaves').value = "";
					document.getElementById('pleaves').value = "5";
					document.getElementById('sleaves').value = "5";
					document.getElementById('empType').value = "5";

				}

				catch (e) {
					alert(e);
				}

			}
		}
	}