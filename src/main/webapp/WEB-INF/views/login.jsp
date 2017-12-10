
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<title>Login Page</title>
<style>
.error {
	padding: 15px;
	marginbottom: 20px;
	border: 1px solid transparent;
	borderradius: 4px;
	color: #a94442;
	backgroundcolor: #f2dede;
	bordercolor: #ebccd1;
}

.msg {
	padding: 15px;
	marginbottom: 20px;
	border: 1px solid transparent;
	borderradius: 4px;
	color: #31708f;
	backgroundcolor: #d9edf7;
	bordercolor: #bce8f1;
}

#loginbox {
	width: 300px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	webkitborderradius: 2px;
	mozborderradius: 2px;
	border: 1px solid #000;
}
</style>
</head>
<body onload='document.loginForm.username.focus();'>

	<h1>Spring Security Login Form (Database + Hibernate
		Authentication)</h1>

	<div id="loginbox">

		<h3>Login with Username and Password</h3>

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>

		<form name='loginForm' action="/MiniForum/login" method='POST'>

			<table>
				<tr>
					<td>User:</td>
					<td><input type='text' name='username'></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type='password' name='password' /></td>
				</tr>
				<tr>
					<td colspan='2'><input name="submit" type="submit"
						value="submit" /></td>
				</tr>
			</table>

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />

		</form>
	</div>
</body>
</html>
