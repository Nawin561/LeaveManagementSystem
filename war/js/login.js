$(document).ready(function() {
	$("#loaderGif").hide();

});
function Slider() {

	$(".slider #1").show(500);
	$(".slider #1").delay(5500).hide(500);
	var sc = $(".slider img").size();
	var count = 2;
	setInterval(function() {
		$(".slider #" + count).show(500);
		$(".slider #" + count).delay(5500).hide(500);

		if (count == sc)
			count = 1;
		else
			count = count + 1;

	}, 4500);
}

function updatePasswordAjax() {
	var newPassword = "newPassword";
	var emailAddress = document.getElementById("emailForReset").value;
	var obj = new XMLHttpRequest();
	var str = "emailAddress=" + emailAddress + "&newPassword=" + newPassword;
	obj.open("GET", "/EmployeeOperation/ResetPassword.htm?" + str, true);
	obj.send();
	obj.onreadystatechange = function() {
		if (obj.readyState == 4 && obj.status == 200) {
			document.getElementById("resultBoxResetPassword").innerHTML = obj.responseText;
		}
	}

}

function credentialsVerification() {
	$("#loaderGif").show();
	document.getElementById("resultBox").innerHTML = "";
	var loginEmployeeType;
	var radios = document.getElementsByName("loginEmployeeType");
	for (var i = 0; i < radios.length; i++) {
		if (radios[i].checked) {
			loginEmployeeType = radios[i].value;
			break;
		}
	}

	var email = document.getElementById("email").value;
	var password = document.getElementById("password").value;
	var str = "email=" + email + "&password=" + password
			+ "&loginEmployeeType=" + loginEmployeeType;
	var obj = new XMLHttpRequest();
	obj.open("GET", "/login/credentialsValidate.htm?" + str, true);
	obj.send();

	obj.onreadystatechange = function() {
		if (obj.readyState == 4 && obj.status == 200) {
			if (n === 1) {
				document.getElementById("resultBox").innerHTML = "Click on Login";
			} else {
				document.getElementById("resultBox").innerHTML = "Credentials Mismatched";
			}

		}
	}
}