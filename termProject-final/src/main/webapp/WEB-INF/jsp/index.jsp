<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8">
	<title>Diary</title>
	<script src="/termProject-final/webjars/jquery/3.3.1/jquery.min.js"></script>
	<link rel="stylesheet" href="/termProject-final/webjars/bootstrap/4.1.0/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/termProject-final/css/index.css">
</head>
<body class="main-page">
	<header class="w-100 header_bolck"></header>
	<script>$(".header_bolck").load("/termProject-final/header");</script>
	
	
	<section class="h-100 w-100 diaryList"></section>
	<script>$(".diaryList").load("/termProject-final/diaryList");</script>
	
	<div class="footer w-100"></div>
	<script>$(".footer").load("/termProject-final/footer");</script>
	<script src="/termProject-final/webjars/jquery/3.3.1/jquery.min.js"></script>
	<script src="/termProject-final/webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</body>
</html>
