<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8">
	<title>Diary - Login</title>
	<script src="/termProject-final/webjars/jquery/3.3.1/jquery.min.js"></script>
	<link rel="stylesheet" href="/termProject-final/webjars/bootstrap/4.1.0/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/termProject-final/css/login.css">
</head>
<body class="my-login-page">
	<section class="h-100">
		<div class="container h-100">
			<div class="row justify-content-md-center h-100">
				<div class="card-wrapper">
					<div class="logo">
						<img src="/termProject-final/img/logo.png" onclick="location.href='/termProject-final'">
					</div>
					<div class="card fat">
						<div class="card-body">
							<h4 class="card-title text-center text-dark">
							Please Login
							</h4>
							<form method="POST" id="loginForm" enctype="multipart/form-data">
							 
								<div class="form-group">
									<label for="id" class="text-secondary">Account</label>
									<input id="account" class="form-control" name="account" placeholder="@email" required autofocus>
								</div>

								<div class="form-group">
									<label for="id" class="text-secondary">Password</label>
									<input id="password" type="password" class="form-control" placeholder="password" name="password" required>
								</div>

								
							</form>
								<div class="form-group no-margin">
									<button onclick="checkIdPassword()" class="btn btn-info btn-block">
										Login
									</button>
								</div>
						</div>
					</div>
	<div class="footer w-100"></div>
	<script>$(".footer").load("/termProject-final/footer");</script>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript">
		function checkIdPassword(){
			var account = document.getElementById('account').value;
			var password = document.getElementById('password').value;
			var xmlhttp= new XMLHttpRequest();
			const data = {'account':account, 'password':password};
			xmlhttp.addEventListener('load', function(){
					if(this.responseText==='true'){
						submitLogin();
					}else{
						alert("비밀번호 혹은 아이디가 틀립니다.");
					}
				});
			xmlhttp.open('POST', '/termProject-final/idPwdCheck');
			xmlhttp.setRequestHeader("Content-Type","application/json;charset=UTF-8");
			xmlhttp.send(JSON.stringify(data));
			//submitLogin();
		}
		function submitLogin(){
			var form = document.getElementById("loginForm");
			form.action = "/termProject-final/loginAction";
			form.submit();
		}
	</script>
	<script src="/termProject-final/webjars/jquery/3.3.1/jquery.min.js"></script>
	<script src="/termProject-final/webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</body>
</html>