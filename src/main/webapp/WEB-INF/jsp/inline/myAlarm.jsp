<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="model.PostDto"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="/termProject-final/css/myAlarm.css">
</head>
<body class="myAlarm h-100 w-100">
	<section class="change-block h-100">
		<div class="container h-100">
			<div class="row justify-content-md-center h-100">
				<div class="card-wrapper w-100 h-100">
					<table class="w-100 h-100">
					<rowgroup>
						<row height="35%"/>
						<row height="*"/>
						<row height="35%"/>
					</rowgroup>
					<tbody class=" h-100">
						<tr>
							<th>D+Nday</th>
							<td>
								<%
								List<PostDto> postPastList = (List<PostDto>) request.getAttribute("past");
								for(PostDto post : postPastList){
									Date dDay = post.getAlarmDate();
									Date now = new Date();
									long timeDiff = (now.getTime() - dDay.getTime())/(60*60*1000*24);
									out.print("<div class='alarm-block'>"+
									"<a href='/termProject-final/openDiary?diaryNo="+(post.getNo()+1)+"'>");
									out.print("<div class='badge badge-past badge-info'>D+"+timeDiff+"</div>");
									out.print(post.getTitle());
									out.print("</a></div>");
								}
								%>
							</td>
						</tr>	
						<tr class="d-day-tr">
							<th>D-day</th>
							<td>
								<%
								List<PostDto> postTodayList = (List<PostDto>) request.getAttribute("today");
								for(PostDto post : postTodayList){
									Date dDay = post.getAlarmDate();
									Date now = new Date();
									long timeDiff = (now.getTime() - dDay.getTime())/(60*60*1000*24);
									out.print("<div class='alarm-block'>"+
									"<a href='/termProject-final/openDiary?diaryNo="+(post.getNo()+1)+"'>");
									out.print("<div class='badge badge-d-day badge-info'>D-day</div>");
									out.print(post.getTitle());
									out.print("</a></div>");
								}
								%>
							</td>
						</tr>		
						<tr>
							<th>D-Nday</th>
							<td >
								<%
								List<PostDto> postFutureList = (List<PostDto>) request.getAttribute("futre");
								for(PostDto post : postFutureList){
									Date dDay = post.getAlarmDate();
									Date now = new Date();
									long timeDiff = (dDay.getTime()-now.getTime())/(60*60*1000*24);
									out.print("<div class='alarm-block'>"+
									"<a href='/termProject-final/openDiary?diaryNo="+(post.getNo()+1)+"'>");
									out.print("<div class='badge badge-future badge-info'>D-"+(timeDiff+1)+"</div>");
									out.print(post.getTitle());
									out.print("</a></div>");
								}
								%>
							</td>
						</tr>
					</tbody>
					</table>
				</div>
			</div>
		</div>
	</section>
</body>
</html>