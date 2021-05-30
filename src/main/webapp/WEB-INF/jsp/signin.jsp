<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8">
	<title>Diary - sign in</title>
	<script src="/termProject-final/webjars/jquery/3.3.1/jquery.min.js"></script>
	<link rel="stylesheet" href="/termProject-final/webjars/bootstrap/4.1.0/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/termProject-final/css/signin.css">
</head>
<body class="my-signin-page">
	<section class="h-100">
		<div class="container h-100">
			<div class="row justify-content-md-center h-100">
				<div class="card-wrapper">
					<div class="logo">
						<img src="/termProject-final/img/logo.png" onclick="location.href='/termProject-final'">
					</div>
							<h4 class="card-title text-center text-dark">
							Sign in right now~!
							</h4>
							<form id="formData" method="POST" >
							 
								<div class="form-group">
										<label for="id" class="text-secondary">Account Email</label>
										<input id="account" class="form-control" name="account" placeholder="@email" required autofocus>
								</div>

								<div class="form-group">
									
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
										<input id="nickname" class="form-control" name="nickname" placeholder="nickname" required autofocus>
								</div>
									
									
									
								</div>
							</form>

								<div class="form-group no-margin"><button class="btn btn-info btn-block" onclick="submitCheck()">sign in</button></div>
								<script>
									function submitCheck(){
										var account = document.getElementById("account");
										if(!(account.value).includes('@')){
											alert("e-mail형식의 Id를 입력하세요");
											return;
										}
										var passwd = document.getElementById("password");
										var passCheck = document.getElementById("password_check");
										if(passwd.value!=passCheck.value){
											alert("비밀번호가 일치하지 않습니다.");
											return;
										}
										
						
										var formElement = document.querySelector("#formData");
										var formData = new FormData(formElement);
										var xmlhttp= new XMLHttpRequest();
										xmlhttp.addEventListener('load', function(){
											console.log(this.responseText);
											switch(this.responseText){
											case "1":
												alert("축하합니다. 회원가입에 성공했습니다.");
												location.href="/termProject-final/login";
												break;
											case "0":
												alert("동일한 아이디가 존재합니다.");
												break;
											default:
												alert("원인 모를 이유로 실패했습니다.");
												break;
											}
										});
										xmlhttp.open('POST', '/termProject-final/signinCheck');
										xmlhttp.send(formData);
										return false;
									}
									function idCheck(id){
										//id check ajax;
										if(false){
											alert("동일한 ID가 있습니다. 다른 ID를 입력하세요");
											return false;
										}
										return true;
									}
									function idValidCheck(id){

										return true;
									}
								</script>
	<div class="footer w-100"></div>
	<script>$(".footer").load("/termProject-final/footer");</script>
				</div>
			</div>
		</div>
	</section>
	<script src="/termProject-final/webjars/jquery/3.3.1/jquery.min.js"></script>
	<script src="/termProject-final/webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</body>
</html>