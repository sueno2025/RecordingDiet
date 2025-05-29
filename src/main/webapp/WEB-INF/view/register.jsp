<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規ユーザー登録</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/register.css">
</head>
<body>
	<h1>新規ユーザー登録</h1>
<%if(request.getAttribute("error") != null){ %>
	<p style="color:red;"><%=request.getAttribute("error") %></p>
<%} %>
<div class="register-warning">
  <p class="warning-title">※ ご注意ください ※</p>
  <p class="warning-text">
    ご登録いただくパスワードは、安全のためハッシュ化して保存されます。<br>
    しかし、万が一の情報漏洩に備え、<strong>他のサービスと同じパスワードの使い回しは絶対にお控えください。</strong><br>
    また、本システムにはパスワードの再発行機能がございません。<br>
    <strong>パスワードはご自身で控えを取り、大切に管理してください。</strong>
  </p>
</div>

<form action="RegisterServlet" method="post">
	ユーザー名:<input type="text" name="user_name"  value="${user_name }"required><br>
	パスワード:<input type="password" name="user_pass" required><br>
	確認用パスワード:<input type="password" name="confirm" required><br>
	身長(cm):<input type="number" name="height" value="${height}" min="0" step="0.1" required><br>
	性別:<select name="gender" required>
		<option value="M" ${gender=='M' ? 'Selected' : '' }>男性</option>
		<option value="F" ${gender=='F' ? 'Selected' : '' }>女性</option>
		<option value="O" ${gender=='O' ? 'Selected' : '' }>どちらでもない</option>
	</select>
	<button type="submit">登録</button>
</form>
<p><a href="LoginServlet">すでにアカウントをお持ちの方はこちら</a></p>
</body>
</html>