<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.*,java.util.*"%>
<%
List<Log> list = (List<Log>) request.getAttribute("list");
String msg = (String)request.getAttribute("msg");
User loginUser =(User)session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>データ入力</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/input.css">
</head>
<body>
	<h1>レコーディングダイエットアプリ</h1>
	<c:if test="${not empty sessionScope.user}">
	<p class="greeting">こんにちは${sessionScope.user.user_name}さん</p>
	</c:if>
	<h3>本日の体重と食事内容を入力してください</h3>
	<form action="InputServlet" method="post">
		体重<input type="number" name="weight" min="0" max="300" step="0.1"
			required oninvalid="this.setCustomValidity('不正な値です')"
			oninput="this.setCustomValidity('')"><br> 朝食<input
			type="text" name="breakfast"><br> 昼食<input type="text"
			name="lunch"><br> 夜食<input type="text" name="dinner"><br>
		間食<input type="text" name="snack"><br> メモ<input
			type="text" name="memo"><br>
		<button type="submit">登録</button>
	</form>
<c:if test="${not empty msg}">
  <p class="message">${msg}</p>
</c:if>
<p><a href="ResultServlet">記録一覧・グラフを見る</a></p>
<p><a href="${pageContext.request.contextPath }/LogoutServlet">ログアウト</a></p>
</body>
</html>