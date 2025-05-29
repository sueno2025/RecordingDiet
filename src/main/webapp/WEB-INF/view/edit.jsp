<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Log"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%
    	Log log =(Log)request.getAttribute("log");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/edit.css" %>
<title>データ編集画面</title>
</head>
<body>
<h1>記録の編集</h1>

<%--エラーの表示 --%>
<c:if test="${not empty error }">
<p style="color:red;">${error }</p>
</c:if>
<form action="EditServlet" method="post">
	<input type="hidden" name="log_id" value=<%=log.getLog_id() %>>
	
	体重:<input type="number" name="weight" step="0.1" value="<%=log.getWeight() %>"required><br>
	
	朝食:<input type="text" name="breakfast" value="<%=log.getBreakfast() %>"><br>
	昼食:<input type="text" name="lunch" value="<%=log.getLunch()%>"><br>
	夕食:<input type="text" name="dinner" value="<%=log.getDinner() %>"><br>
	間食:<input type="text" name="snack" value="<%=log.getSnack() %>"><br>
	メモ:<br>
	<textarea name="memo" rows="4" cols="40"><%=log.getMemo() %></textarea>
	
	<button type="submit">更新する</button>
</form>

<p><a href="<%=request.getContextPath() %>/ResultServlet">一覧に戻る</a></p>
</body>
</html>