<%@page import="model.PostDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script src="/termProject-final/webjars/jquery/3.3.1/jquery.min.js"></script>
	<link rel="stylesheet" href="/termProject-final/webjars/bootstrap/4.1.0/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/termProject-final/css/editPost.css">
</head>
<body class="writing-page">
<script type="text/javascript">
	if(!${isWriter}){
		alert("세션이 만료되었습니다.");
		location.href="/termProject-final";
	}
</script>
<%
	boolean isWriter = (boolean)request.getAttribute("isWriter");
%>

<section class="container w-75">
<div class="logo"> <!-- 글쓰기 이미지로 -->
	<img class="logo" alt="Logo Image" src="/termProject-final/img/editPost.png">
</div>
<div class="justify-content-md-center w-100">
	<form  id="formData" class="form-group"  method="post" enctype="multipart/form-data">
	    <section  class="w-100 ">
	      <table class="w-100 " border="1">
				<colgroup>
					<col width="15%" />
					<col width="*" />
				</colgroup>
	        <tr>
	          <td class="items">
	            <label for="diary-title">제목</label>
	          </td>
	          <td>
	            <div class="diary-title input-item">  <!-- 제목 -->
	            <input class="diary-title-input" type="text" name="title" value="${diary.title}">
	          </div>
	        </td>
	      </tr>
	      <tr>
	        <td  class="items">
	          <label for="dDay">D-day설정</label>
	        </td>
	        <td>
	        <div class="d-day input-item">  <!-- 알람 -->
	          <div class="check">
	          <% PostDto post = (PostDto)request.getAttribute("diary"); %>
	          	<label><input id="dDayCheck" type="checkbox" 
	          	name="isSet" value=<%=post.getAlarmDate()==null?"off":"on" %>
	          	<%=post.getAlarmDate()==null?"":"checked='checked'" %>  
	          	onclick="onDdayCheckBoxClick()">D-day설정</label>
	            <input id="calendar" type='date' name='dDay' onclick="onCalendarClick()" <%=post.getAlarmDate()==null?"disabled":""%> value="<%=post.getAlarmDate()==null?0:post.getAlarmDate() %>"/>
	          </div>
	        </div>
	      </td>
	
	      </tr>
	      <tr>
	        <td class="items">
	          <label for="disclosure">공개범위</label>
	        </td>
	        <td>
	          <div class="disclosure input-item">
	            <label for="disclosure">
				<select name="discloureScope" onchange="">           
				     <option value="3" <%=post.getDisclosureLevel()==3?"selected":"" %>>전체공개</option>    
				     <option value="2" <%=post.getDisclosureLevel()==2?"selected":"" %>>회원 공개</option>    
				     <option value="1" <%=post.getDisclosureLevel()==1?"selected":"" %>>비공개</option>
				  </select>
				</label>
	          </div>
	        </td>
	
	      </tr>
	      <tr>
	        <td class="items">
	          <label for="content">내용</label>
	        </td>
	        <td>
	          <div class="content input-item">
	             <textarea name="content" cols="40" rows="8" >${diary.content }</textarea>
	          </div>
	
	      </tr>
	      <tr>
	          <td class="items">
	            <label for="attachment">첨부파일</label>
	          </td>
	          <td>
	            <div class="attachment input-item">
	            <%=post.getAttachmentList().size()>0?"" : "<input id='file' type='file' name='attachment'>" %>	            
	            
            	<c:forEach var="att" items="${diary.attachmentList}" varStatus="status">
            		<div id="attBlock">
	             	<span id="att_${att.no}"class="attachments">${att.originalName}</span>
	             	<button onclick="removeAtt(${att.no })"class="delete-button">x</button><br>
	             	</div>
	            	<input id="file_${att.no }" type="file" name="attachment" disabled>
            	</c:forEach>
	          	</div>
	        </td>
	      </tr>
	    </table>
	  </section>
	</form>
	  <div class="button-box">
	  	<button onclick="edit()">수정하기</button>
	  	<button id="cancel" onclick="cancel();">취소하기</button>
	  </div>
	</div>
	<script type="text/javascript">
	function removeAtt(no){ 
		if (confirm("정말 삭제하시겠습니까??") == true){ 
			fileTag = document.getElementById("file_"+no);
			fileTag.disabled = false;
			block = document.getElementById("att_"+no).parentNode;
			while ( block.hasChildNodes() ) { 
				block.removeChild( block.firstChild ); 
			}
		}else{   //취소
		    return;
		}
		
	}
	function edit(){
		var formElement = document.querySelector("#formData");
		var formData = new FormData(formElement);
		var xmlhttp= new XMLHttpRequest();
		xmlhttp.addEventListener('load', function(){
			console.log(this.responseText);
			switch(this.responseText){
			case "1":
				alert("수정되었습니다.");
				location.href="/termProject-final";
				break;
			case "-3":
				alert("세션이 만료되었습니다.");
				location.href="/termProject-final";	
				break;
			case "-1":
				alert("나의 글이 아닙니다.");
				location.href="/termProject-final";	
				break;
			case "-2":
				alert("이미 삭제된 글입니다.");
				location.href="/termProject-final";	
				break;
			default:	
				alert("수정에 실패했습니다. 잠시 후 다시 시도하세요");
			}
		});
		xmlhttp.open('PUT', '/termProject-final/editDiary/'+${diary.no}+'/'+true);
		xmlhttp.send(formData);
	}
	function cancel(){
		location.href="/termProject-final/";
	}
	function onDdayCheckBoxClick(){
		var checkBox = document.getElementById("dDayCheck");
		var calendar = document.getElementById("calendar");
		if(checkBox.checked){
			calendar.removeAttribute("disabled");
			checkBox.value="on";
		}else{
			calendar.disabled='true';
			calendar.value = 0;
			checkBox.value="off";
		}
	}
	function onCalendarClick(){
		var checkBox = document.getElementById("dDayCheck");
		if(!checkBox.checked){
			alert("먼저 알람켜기를 체크하세요");
		}
	}
	</script>
</section>
    <div class="footer"></div>
	<script>$(".footer").load("/termProject-final/footer");</script>
	<script src="/termProject-final/webjars/jquery/3.3.1/jquery.min.js"></script>
	<script src="/termProject-final/webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</body>
</html>