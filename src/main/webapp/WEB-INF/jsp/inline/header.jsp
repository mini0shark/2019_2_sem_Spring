<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="/termProject-final/css/header.css">
</head>
<body>
	<section>
		 	<div class="logo pointer-group"><img class="logo" alt="Logo Image" src="/termProject-final/img/logo.png" onclick="location.href='/termProject-final/';"></div>
		 		<div class='head-box'>
				    <div class="login-block">
				    	<font id="accountId"></font>
				    	<button id="blockButton1" class="badge badge-info pointer-group"></button>
						<button id="blockButton2" class="badge badge-info pointer-group"></button>
				    </div>
				    <div class='head-dummy'></div>
				    <div id="mineButton" class='d-button'></div>
				    <div id="dDayButton" class='d-button'></div>
			  	</div>
		</section>
		<script type="text/javascript">

    		var btn1 = document.getElementById("blockButton1");
    		var btn2 = document.getElementById("blockButton2");
    		(function checkSessionExpired(){
	    		xmlhttp = new XMLHttpRequest();
				xmlhttp.open('get', '/termProject-final/checkSession');
				xmlhttp.addEventListener('load', function(){
					if(this.responseText==="true"){
						var userId = getCookie("userId");
				    	var nickname = getCookie("nickname");
			    		var isLogin=(userId != null && nickname != null);
				    	(function () {
					    	if(isLogin){
					    		document.getElementById("accountId").innerHTML=nickname+"님";			    	
						    	loginHeader();

						    	
					    		var btn = document.createElement("button");
						    	btn.setAttribute("class","badge badge-info pointer-group head-buttons");
						    	btn.setAttribute("onclick",'location.href=\"mypage?tab=2\"');
						    	btn.innerHTML="나의 다이어리~";
						    	document.getElementById("dDayButton").appendChild(btn);



					    		
						    	btn = document.createElement("button");
						    	btn.setAttribute("id", "dDayButton");
						    	btn.setAttribute("class","badge badge-info pointer-group head-buttons");
						    	btn.setAttribute("onclick",'location.href=\"mypage?tab=3\"');
						    	btn.innerHTML="D-day";
						    	document.getElementById("dDayButton").appendChild(btn);

						    	data={'account':getCookie('')}
					    		xmlhttp = new XMLHttpRequest();
								xmlhttp.open('get', '/termProject-final/toDaysAlarmCheck');
								xmlhttp.addEventListener('load', function(){
										if(this.responseText==='true'){
											//var btn = document.getElementById("createddDayButton");					
											var dDayClass="background-color: red;";
									    	btn.setAttribute("style",dDayClass);
										}
									});
								xmlhttp.send();

						    	
						    }else{
							    notLoginHeader();
							}
						})();
					}else{
						alert(this.responseText);
					}
				});
				xmlhttp.send();	
		    })();
	    	var loginHeader=function(){
		    		btn1.innerHTML="Logout";
		    		btn2.innerHTML="MyPage";
		    		btn1.setAttribute("onClick","location.href='/termProject-final/logout'");
		    		btn2.setAttribute("onClick","location.href='/termProject-final/mypage?tab=1'");
		    	
		    	}
	    	var notLoginHeader=function(){
	    		btn1.innerHTML="Login";
	    		btn2.innerHTML="SignIn";
	    		btn1.setAttribute("onClick","location.href='/termProject-final/login'");
	    		btn2.setAttribute("onClick","location.href='/termProject-final/signin'");
		    	}
	
		    	function getCookie(cookieName){
		    	    var cookieValue=null;
		    	    if(document.cookie){
		    	        var array=document.cookie.split((escape(cookieName)+'='));
		    	        if(array.length >= 2){
		    	            var arraySub=array[1].split(';');
		    	            cookieValue=unescape(arraySub[0]);
		    	        }
		    	    }
		    	    return cookieValue;
		    	}
		    	
			</script>
</body>
</html>