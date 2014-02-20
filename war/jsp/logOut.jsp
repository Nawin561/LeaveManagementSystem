<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Logged Out</title>

 <script type="text/javascript">
window.history.forward();
function noBack() { window.history.forward(); }

</script>

</head>
<body onload="noBack();  onpageshow="if (event.persisted) noBack();" onunload="">
<% response.sendRedirect("/login.jsp"); %>
<center>
<h1 style="color:red"> Session Destroyed and Logged Out </h1>
<a href="/login.jsp">Login</a>
</center>
</body>
</html>