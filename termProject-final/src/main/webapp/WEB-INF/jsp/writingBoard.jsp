<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script src="/termProject-final/webjars/jquery/3.3.1/jquery.min.js"></script>
	<link rel="stylesheet" href="/termProject-final/webjars/bootstrap/4.1.0/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/termProject-final/css/writingBoard.css">
</head>
<body class="writing-page">

<section class="container w-75">
<div class="logo"> <!-- 글쓰기 이미지로 -->
	<img alt="Logo Image" src="/termProject-final/img/writeDiary.png">
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
	            <input class="diary-title-input" type="text" name="title">
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
	          	<label><input id="dDayCheck" type="checkbox" 
	          	name="isSet" value="off" onclick="onDdayCheckBoxClick()">D-day설정</label>
	            <input id="calendar" type='date' name='dDay' onclick="onCalendarClick()" disabled="disabled"/>
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
				     <option value="3">전체공개</option>    
				     <option value="2">회원 공개</option>    
				     <option value="1">비공개</option>
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
	             <textarea name="content" cols="40" rows="8" ></textarea>
	          </div>
	
	      </tr>
	      <tr>
	          <td class="items">
	            <label for="attachment">첨부파일</label>
	          </td>
	          <td>
	            <div class="attachment input-item">  <!-- 제목 -->
	            <input type="file" name="attachment">
	          </div>
	        </td>
	      </tr>
	    </table>
	  </section>
	</form>
	  <div class="button-box">
	  	<button onclick="submit()">작성하기</button>
	  	<button id="cancel" onclick="cancel();">취소하기</button>
	  </div>
	</div>
	<script type="text/javascript">
	function submit(){
		var formElement = document.querySelector("#formData");
		var formData = new FormData(formElement);
		var xmlhttp= new XMLHttpRequest();
		xmlhttp.addEventListener('load', function(){
			console.log(this.responseText);
			switch(this.responseText){
			case "1":
				alert("등록되었습니다.");
				location.href="/termProject-final";
				break;
			case "2":
				alert("세션이 만료되었습니다.");
				location.href="/termProject-final";	
				break;
			default:	
				alert("등록에 실패했습니다. 잠시 후 다시 시도하세요");
			}
		});
		xmlhttp.open('POST', '/termProject-final/registerDiary');
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