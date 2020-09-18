<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="/termProject-final/css/changeInfo.css">
</head>
<body class="change-info">
	<section class="change-block h-100">
		<div class="container h-100">
			<div class="row justify-content-md-center h-100">
				<div class="card-wrapper">
					<form method="POST" id='formData'>
					 
						<div class="form-group">
								<label for="id" class="text-secondary">Account Email</label>
								<div class="id-block">-ID<% %>-</div>
						</div>

						<div class="form-group">
								<label for="oldPassword" class="text-secondary">기존 비밀번호</label>
								<div>
									<input id="oldPassword" type="password" class="form-control" placeholder="Original Password" name="oldPassword" required>
								</div>
						<table  class="table-width passwd">
							<tr>
								<td class="label"><label for="password" class="text-secondary">Password</label></td>
								<td class="label"><label for="pwcheck" class="text-secondary">Password Check</label></td>
							</tr>
							<tr>
								<td><input id="password" type="password" class="form-control" placeholder="password" name="password" required></td>
								<td><input id="password_check" type="password" class="form-control" placeholder="re-entry" name="passwordCheck" required></td>
							</tr>
						</table>
							
					 
						<div class="form-group nickname">
								<label for="nickname" class="text-secondary">Nick Name</label>
								<input id="nick-name" class="form-control" name="nickname" placeholder="nickname" required>
						</div>
						</div>
					</form>

						<div class="form-group submit-btn"><button class="btn btn-info btn-block" onclick="submitCheck();">Edit</button></div>
						<script>
						function submitCheck(){
							var passwd = document.getElementById("password");
							var passCheck = document.getElementById("password_check");
							if(!(passwd.value==passCheck.value)){
								alert("새 비밀번호가 일치하지 않습니다..");
								return;
							}

										
							var formElement = document.querySelector("#formData");
							var formData = new FormData(formElement);
							var xmlhttp= new XMLHttpRequest();
							xmlhttp.addEventListener('load', function(){
								console.log(this.responseText);
								switch(this.responseText){
								case "1":
									alert("회원 정보를 바꿨습니다.");
									location.href="/termProject-final";
									break;
								case "-1":
									alert("기존 패스워드가 틀립니다.");
									break;
								default:
									alert("원인 모를 이유로 실패했습니다.");
									break;
								}
							});
							xmlhttp.open('POST', '/termProject-final/editInfo');
							xmlhttp.send(formData);
						}
						</script>
				</div>
			</div>
		</div>
	</section>
</body>
</html>