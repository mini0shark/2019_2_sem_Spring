<%@page import="model.PostDto"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<script src="/termProject-final/webjars/jquery/3.3.1/jquery.min.js"></script>
	<link rel="stylesheet" href="/termProject-final/webjars/bootstrap/4.1.0/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/termProject-final/css/openDiary.css">
</head>
<body class="writing-page">
	<header class="w-100 header_bolck"></header>
	<script>$(".header_bolck").load("/termProject-final/header");</script>
	<script type="text/javascript">
		var auth = "${auth}";
		console.log(auth);
		if(auth==="false"){
			alert("권한이 없습니다.");
			location.href="/termProject-final/";
		}		
	</script>
<section class="container w-75">
	<div class="writers-block">
		<%
			boolean isWriter = (boolean)request.getAttribute("isWriter");
			if(isWriter){
				out.println("<button id='editButton' onclick='editPost()' class='writers-button badge-info'>수정하기</button>");
				out.println("<button id='deleteButton' onclick='deletePost()'  class='writers-button badge-info'>삭제하기</button>");
			}
		%>
		<script type="text/javascript">
		function editPost(){
			location.href='/termProject-final/editPost/'+${diary.no};
		}
		function deletePost(){
			if (confirm("정말 삭제하시겠습니까??") == true){    //확인
	    		xmlhttp = new XMLHttpRequest();
				xmlhttp.open('delete', '/termProject-final/deletePost/'+${diary.no});
				xmlhttp.addEventListener('load', function(){
					switch(Number(this.responseText)){
						case 1:
							alert("삭제되었습니다.");
							location.href="/termProject-final";
							break;
						case -1:
							alert("존재하지 않는 글 입니다.");
							location.href="/termProject-final";
							break;
						case -2:
							alert("세션이 만료되었습니다.");
							location.href="/termProject-final";
							break;
						case -3:
							alert("권한이 없습니다.");
							location.href="/termProject-final";
							break;
						default:
							alert("삭제에 실패했습니다.");
							break;
					}
					});
				xmlhttp.send();
			}else{   //취소
			    return;
			}
		}
		</script>
	</div>
	<div class="justify-content-md-center w-100">
	      <table class="w-100 " border="1">
				<colgroup>
					<col width="15%" />
					<col width="45%" />
					<col width="15%" />
					<col width="*" />
				</colgroup>
	        <tr>
	          <td class="items">
	            <label for="diary-title">제목</label>
	          </td>
	          <td>
	            <div class="diary-title diplay-item">  <!-- 제목 -->
	            ${diary.title}
	          </div>
	        </td>
	          <td class="items">
	            <label for="writer">작성자</label>
	          </td>
	          <td>
	            <div class="writer diplay-item">  <!-- 제목 -->
	            ${diary.user.nickname}
	          
	          </div>
	        </td>
	      </tr>
	      <tr>
	        <td  class="items">
	          <label for="alarm">D-Day</label>
	        </td>
	        <td colspan="3">
	        <div class="alarm diplay-item">  <!-- 알람 -->
	          <div class="check">
	            	<%
	            		PostDto post = (PostDto)request.getAttribute("diary");
	        			Date alarm = post.getAlarmDate();
	            	%>
	            	<%=alarm!=null?alarm:"-" %>
	          </div>
	        </div>
	      </td>
	      </tr>
	      
	      <tr>
	        <td class="items">
	          <label for="disclosure">공개범위</label>
	        </td>
	        <td colspan="3">
	          <div class="disclosure input-item">
	          	<%
	          		int disLevel = post.getDisclosureLevel();
	        		String str;
	        		switch(disLevel){
	        		case 1:
	        			str = "비밀글";
	        			break;
	        		case 2:
	        			str = "로그인 회원";
	        			break;
	        		default :
	        			str = "전체공개";
	        			break;
	        		}
	          	%>
	          	<%=str %>
	          	
	          </div>
	        </td>
	        
	      <tr>
	        <td class="items">
	          <label for="content">내용</label>
	        </td>
	        <td colspan="3">
	        	<div class="content">
	            	${diary.content}
	            </div>
	        </td>
	      </tr>
	      <tr>
	          <td class="items">
	            <label for="attachment">첨부파일</label>
	          </td>
	          <td colspan="3">
	            <div class="attachment diplay-item">
	            	<c:forEach var="att" items="${diary.attachmentList}" varStatus="status">
		             	<a href="/termProject-final/fileDownLoad/${att.no}">${att.originalName}</a><br>
	            	</c:forEach>
	          </div>
	        </td>
	      </tr>
	    </table>
	</div>
	  <div class="button-box">
	  	<button onclick="location.href='/termProject-final/'">돌아가기</button>
	  </div>
</section>
	<div class="footer w-100"></div>
	<script>$(".footer").load("/termProject-final/footer");</script>
	<script src="/termProject-final/webjars/jquery/3.3.1/jquery.min.js"></script>
	<script src="/termProject-final/webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
	
</body>
</html>