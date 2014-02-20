$(document).ready(function() {
		$("#resultBox").hide();
		$("#loaderGif").hide();

	});

	function ajaxDeleteSingleDepartmentFunction(tableID, temp) {
		$("#loaderGif").show();
		var obj = new XMLHttpRequest();
		var str = "departmentName=" + temp;
		obj.open("POST", "/DepartmentOperation/deleteSingleDepartment.htm?"
				+ str, true);
		obj.send();
		obj.onreadystatechange = function() {
			if (obj.readyState == 4 && obj.status == 200) {
				$("#loaderGif").hide();
				document.getElementById("resultBox").innerHTML = obj.responseText;
				try {
					var table = document.getElementById(tableID);
					var rowCount = table.rows.length;

					for (var i = 0; i < rowCount; i++) {
						var row = table.rows[i];
						var chkbox = row.cells[0].childNodes[0];
						if (chkbox.value == temp) {
							document.getElementById("resultBox").innerHTML = obj.responseText;
							$(document).ready(function() {
								$("#resultBox").slideDown();
								setTimeout(function() {
									$("#resultBox").slideUp();
								}, 3000);
							});
							table.deleteRow(i);

						}

					}
				} catch (e) {
					alert(e);
				}

			}
		}
	}

	function ajaxFunction(tableID) {
		$("#loaderGif").show();
		var radios = document.getElementsByName("deprecord");
		var k = 0;
		var arr = new Array();
		for (var i = 0; i < radios.length; i++) {
			if (radios[i].checked) {
				arr[k++] = radios[i].value;
			}
		}
		var obj = new XMLHttpRequest();
		var str = "deprecord=" + arr;
		obj.open("POST", "/DepartmentOperation/deleteDepartment.htm?" + str,
				true);
		obj.send();
		obj.onreadystatechange = function() {
			if (obj.readyState == 4 && obj.status == 200) {
				$("#loaderGif").hide();
				document.getElementById("resultBox").innerHTML = obj.responseText;
				try {
					var table = document.getElementById(tableID);
					var rowCount = table.rows.length;

					for (var i = 0; i < rowCount; i++) {
						var row = table.rows[i];
						var chkbox = row.cells[0].childNodes[0];
						if (null != chkbox && true == chkbox.checked) {
							if (rowCount <= 1) {
								break;
							}
							document.getElementById("resultBox").innerHTML = obj.responseText;
							$(document).ready(function() {
								$("#resultBox").slideDown();
								setTimeout(function() {
									$("#resultBox").slideUp();
								}, 3000);
							});
							table.deleteRow(i);

							rowCount--;
							i--;
						}

					}
				} catch (e) {
					alert(e);
				}

			}
		}
	}