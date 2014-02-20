$(document).ready(function() {
		$("#resultBox").hide();
		$("#loaderGif").hide();

	});

	function ajaxFunction(tableID) {
		$("#loaderGif").show();
		
		var radios = document.getElementsByName("rowrecord");
		var k = 0;

		var arr = new Array();
		for (var i = 0; i < radios.length; i++) {
			if (radios[i].checked) {
				arr[k++] = radios[i].value;
			}
		}
		var str = "rowrecord=" + arr;
		var obj = new XMLHttpRequest();
		obj.open("POST", "/EmployeeOperation/deleteEmployee.htm?" + str, true);
		obj.send();
		obj.onreadystatechange = function() {
			if (obj.readyState == 4 && obj.status == 200) {

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
							table.deleteRow(i);
							$("#loaderGif").hide();
							$("#resultBox").show();
							document.getElementById("resultBox").innerHTML = "Deleted Sucessfully";
							$(document).ready(function() {
								$("#resultBox").slideDown();
								setTimeout(function() {
									$("#resultBox").slideUp();
								}, 3000);
							});

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