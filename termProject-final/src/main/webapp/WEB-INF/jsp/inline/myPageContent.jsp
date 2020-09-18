<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="/termProject-final/css/myPageContent.css">
</head>
<body class="main-page">
<% 
	int tab = (int)request.getAttribute("tab");
%>

<div class="row h-100">
  <div class="col-3">
    <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
      <a class='nav-link <%=tab==1?"active":"" %>' id="v-pills-home-tab" data-toggle="pill" href="#tab1" role="tab" aria-controls="tab1" aria-selected=<%=tab==1?"true":"false" %>>My Info</a>
      <a class='nav-link <%=tab==2?"active":"" %>' id="v-pills-profile-tab" data-toggle="pill" href="#tab2" role="tab" aria-controls="tab2" aria-selected=<%=tab==2?"true":"false" %>>My Diarys</a>
      <a class='nav-link <%=tab==3?"active":"" %>' id="v-pills-messages-tab" data-toggle="pill" href="#tab3" role="tab" aria-controls="tab3" aria-selected=<%=tab==3?"true":"false" %>>Alarms</a>
    </div>
  </div>
  
  <div class="col-9 h-100">
    <div class="tab-content h-100" id="v-pills-tabContent">
      <div class='tab-pane fade h-100 <%=tab==1?"show active":"" %>' id="tab1" role="tabpanel" aria-labelledby="v-pills-home-tab">
		<div class="changeInfo w-100"></div>
		<script>$(".changeInfo").load("/termProject-final/changeInfo");</script>
      </div>
      <div class='tab-pane fade h-100 <%=tab==2?"show active":"" %>' id="tab2" role="tabpanel" aria-labelledby="v-pills-profile-tab">
		<div class="diaryList w-100"></div>
		<script>$(".diaryList").load("/termProject-final/diaryList");</script>
	  </div>
      <div class='tab-pane fade h-100 <%=tab==3?"show active":"" %>' id="tab3" role="tabpanel" aria-labelledby="v-pills-messages-tab">
       <div class="myAlarm w-100"></div>
		<script>$(".myAlarm").load("/termProject-final/myAlarm");</script>
	   </div>
    </div>
  </div>
</div>

</body>
</html>