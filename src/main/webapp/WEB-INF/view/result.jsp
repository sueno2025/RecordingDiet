<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*,java.text.SimpleDateFormat,java.util.*"%>
    <%
    Log log = (Log)request.getAttribute("latestLog");
    Object bmiObj =request.getAttribute("bmi");
    //double bmi = (bmiObj instanceof Double) ? (Double)bmiObj : null;
    User user = (User)session.getAttribute("user");
    List<Log> logs = (List<Log>)request.getAttribute("logs");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    double bmi = -1;
    boolean bmiAvailable = false;
    if(bmiObj instanceof Double){
    	bmi = (Double)bmiObj;
    	bmiAvailable = true;
    }
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>結果表示</title>
<link rel="stylesheet" href="css/result.css">
<script src="https://cdn.jsdelivr.net/npm/luxon"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-adapter-luxon"></script>
</head>
<body>
<h1><%=user.getUser_name() %>さんの記録</h1>
<%if(bmiAvailable && log != null){%>
<div class="result-container">
<div class="text-box">
<!--  <p><%=user.getUser_name() %>さん</p> -->
<p>身長：<%=user.getHeight() %>cm</p>
<p>最新の体重 : <%= log.getWeight() %>kg</p>
<p>BMI : <%=String.format("%.1f",bmi) %></p>
</div><!--text-box  -->
<div class="image-box">
<%if(bmi < 18.5 ) { %>
<%if(user.getGender()=='M'){ %>
<img src="<%=request.getContextPath() %>/images/yaseM.png" alt="痩せ型男性">
<%}else if(user.getGender()== 'F'){ %>
<img src="<%=request.getContextPath() %>/images/yaseF.png" alt="痩せ型女性">
<%} else{%>
<img src="<%=request.getContextPath() %>/images/yase.png" alt="痩せ型動物">
<%} %>
<p>痩せ型です</p>
<% }else if(bmi < 25){%>
<%if(user.getGender() == 'M'){ %>
<img src="<%=request.getContextPath() %>/images/nomalM.png" alt="普通型男性">
<%}else if(user.getGender()=='F'){ %>
<img src="<%=request.getContextPath() %>/images/nomalF.png" alt="普通型女性">
<%}else{ %>
<img src="<%=request.getContextPath() %>/images/nomal.png" alt="普通型動物">
<%} %>
<p>標準体型です</p>
<%}else{ %>
<%if(user.getGender()=='M'){ %>
<img src="<%=request.getContextPath() %>/images/himanM.png" alt="肥満型男性">
<% }else if(user.getGender()=='F'){%>
<img src="<%=request.getContextPath() %>/images/himanF.png" alt="肥満型女性">
<%} else{%>
<img src="<%=request.getContextPath() %>/images/himan.png" alt="肥満型動物">
<%} %>
<p>肥満体型です</p>
<%} %>
<%}else{ %>
<p class="centered-message">データが見つかりませんでした</p>
<%} %>
</div><!--image-box  -->
</div><!--result-container  -->
<h2>記録一覧</h2>
<c:if test="${not empty msg}">
  <p style="color:green; text-align:center; font-weight:bold;">
    ${msg}
  </p>
</c:if>
<table class="log-table" border="1">
  <tr>
    <th>日付</th>
    <th>体重</th>
    <th>朝食</th>
    <th>昼食</th>
    <th>夕食</th>
    <th>間食</th>
    <th>メモ</th>
    <th>編集</th>
  </tr>
  <% for (Log l : logs) { %>
    <tr>
      <td><%= sdf.format(l.getLogDate()) %></td>
      <td><%= l.getWeight() %>kg</td>
      <td><%= l.getBreakfast() %></td>
      <td><%= l.getLunch() %></td>
      <td><%= l.getDinner() %></td>
      <td><%= l.getSnack() %></td>
      <td><%= l.getMemo() %></td>
      <td>
      	<form action="EditServlet" method="get" style="display:inline;">
      		<input type="hidden" name="log_id" value="<%= l.getLog_id() %>">
      		<button type="submit">編集</button>
      	</form>
      	<form action="DeleteServlet" method="post" style="display:inline;" onsubmit="return confirm('本当に削除しますか？');">
      	<input type="hidden" name="log_id" value="<%=l.getLog_id() %>">
      	<button type="submit">削除</button>
      	</form>
      </td>
    </tr>
  <% } %>
</table>
<hr>
<h2>体重推移グラフ</h2>
<canvas id="weightChart" width="600" height="300"></canvas>
<script>
const ctx = document.getElementById('weightChart').getContext('2d');
const chart = new Chart(ctx, {
    type: 'line',
    data: {
        datasets: [{
            label: '体重（kg）',
            data: [
                <% for (int i = logs.size() - 1; i >= 0; i--) { 
                    Log l = logs.get(i); %>
                    { x: '<%= isoFormat.format(l.getLogDate()) %>', y: <%= l.getWeight() %> },
                <% } %>
            ],
            borderWidth: 2,
            tension: 0.3,
            fill: false
        }]
    },
    options: {
        scales: {
            x: {
                type: 'time',
                time: {
                    unit: 'day',
                    tooltipFormat: 'yyyy-MM-dd',
                    displayFormats: {
                        day: 'MM/dd'
                    }
                },
                title: {
                    display: true,
                    text: '日付'
                }
            },
            y: {
            	//min: 30,  // ここで最小値（例：40kg）を指定
                //max: 150, // 最大値（例：100kg）も指定しておくと見やすい
                title: {
                    display: true,
                    text: '体重（kg）'
                }
            }
        }
    }
});
</script>

<hr>
<a href="<%= request.getContextPath() %>/InputServlet" class="centered-link">入力画面に戻る</a>
</body>
</html>