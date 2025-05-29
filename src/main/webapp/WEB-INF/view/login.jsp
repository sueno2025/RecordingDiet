<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/login.css">
</head>
<body>
	<h1>ログイン</h1>
	<c:if test="${not empty error }">
		<p style="color:red; text-align:center;">${error}</p>
	</c:if>
	<c:if test="${not empty msg }">
	<p style="color:green; text-align:center;">${msg }</p>
	</c:if> 
	<form action="LoginServlet" method="post">
	ユーザーID:<input type="text" name="user_name" required><br>
	パスワード:<input type="password" name="user_pass" required><br>
	<button type="submit">ログイン</button>
	</form>
	<a href="RegisterServlet">新規登録はこちらから</a>
</body>
</html>