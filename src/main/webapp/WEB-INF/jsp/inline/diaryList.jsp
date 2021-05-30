<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="/termProject-final/css/diaryList.css">
</head>
<body>
	<script type="text/javascript">
		function searchPost(page){
			var searchText = document.getElementById("searchText").value;
			var url='';
			if(link.includes("mypage"))
				url = '/termProject-final/searchPostMine?searchText='+searchText+'&page=';
			else
				url = '/termProject-final/searchPost?searchText='+searchText+'&page=';
			xmlhttp = new XMLHttpRequest();
			xmlhttp.open('get', url+page);
			xmlhttp.addEventListener('load', function(){
				var responseData = JSON.parse(this.responseText);
				console.log(responseData);
				var tbody = document.getElementById("listTbody");
				while(tbody.hasChildNodes()){
					tbody.removeChild(tbody.firstChild);
				}

				postList = responseData['postList'];
				for(var i = 0; i<postList.length ; i++){
					registerToTable(postList[i], tbody);
				}

				pageCount = responseData['count']; 
				pageing(pageCount,basicUrl, page);
				});
			xmlhttp.send();
			return false;
		}
		function enterCheck(){
			if(event.keyCode == 13){
				searchPost(1);
		    }
		}
		
	</script>
	<div class="container h-100">
		<div class="row justify-content-md-center h-100">
				<div class="diary h-75 w-100">
					<div class="list"> <!-- 글 목록 -->
					<div class="search-text-group">
						<input id="searchText" type="text" name="searchText" onkeydown="enterCheck();"
							 style="width: 200px;float: left;" >
						<button class="search-button pointer-group" onclick="searchPost(1)">검색</button>
					</div>
						<button class="writing-button pointer-group" onclick="location.href='writingBoard'">글쓰기</button> <!-- 로그인 되어있으면 -->
						<table class="table">
						
						<colgroup>
							<col width="10%" />
							<col width="*" />
							<col width="20%" />
							<col width="20%" />
							<col width="15%" />
						</colgroup>
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>날짜</th>
								<th>공개여부</th>
							</tr>
						</thead>
						<tbody id="listTbody" class="diary-list">
						</tbody>
						</table>
					</div>
           			<div class="searching"></div>
					<div class="pagination">
						<ul>
							<li><button class="left page-button" disabled="true"><<</button></li>
							<div id="pageNumbers"> 
							</div>
							<li><button class="right page-button" disabled="true">>></button></li>
						</ul>
					</div>	
				</div>
		</div>
	</div>
	<script type="text/javascript">
		var link = document.location.href;
		getDiaryList(1);
		
		function getDiaryList(page){
			basicUrl = ''
				if(link.includes("mypage"))
					basicUrl ='/termProject-final/getMyDiaryList?page=';
				else
					basicUrl= '/termProject-final/getDiaryList?page=';
			xmlhttp = new XMLHttpRequest();
			xmlhttp.open('get', basicUrl+page);
			xmlhttp.addEventListener('load', function(){
				var responseData = JSON.parse(this.responseText);
				console.log(responseData);
				var tbody = document.getElementById("listTbody");
				while(tbody.hasChildNodes()){
					tbody.removeChild(tbody.firstChild);
				}

				postList = responseData['postList'];
				for(var i = 0; i<postList.length ; i++){
					registerToTable(postList[i], tbody);
				}

				pageCount = responseData['count']; 
				pageing(pageCount,basicUrl, page);
				});
			xmlhttp.send();
		}
		function pageing(count, url, page){
			totalPage = (count+9)/10;
			totalPage = parseInt(totalPage);
			pageTag= document.getElementById("pageNumbers");
			while(pageTag.hasChildNodes()){
				pageTag.removeChild(pageTag.firstChild);
			}
			for(var i = 0 ; i<totalPage ; i++){
				var number = i+1;
				console.log("num : "+number);
				liTag = document.createElement("li");
				var btnTag = document.createElement("button");
				btnTag.innerHTML = number;
				btnTag.className="page-button";
				if(number ==page){
					console.log("number : "+number+", page : "+page);
					btnTag.disabled='true';
				}
				btnTag.addEventListener("click", function () {
					getDiaryList(this.innerHTML); }); 
				btnTag.id='pageing_'+(number);
				
				liTag.appendChild(btnTag);
				pageTag.appendChild(liTag);
			}
		}
		function registerToTable(post, tbody){
			var tr = document.createElement("tr");
			var td = document.createElement("td");
			td.innerHTML = post.no+1;
			tr.appendChild(td);
			td = document.createElement("td");
			td.innerHTML = post.title;
			tr.appendChild(td);
			td = document.createElement("td");
			td.innerHTML = post.user.nickname;
			tr.appendChild(td);
			td = document.createElement("td");
			
			date = post.createDate;
			year = date.substring(0,4);
			mon = date.substring(5, 7);
			day = date.substring(8,10);
			hh = date.substring(11, 13);
			mm = date.substring(14, 16);
			td.innerHTML = year+"/"+mon+"/"+day+" "+hh+":"+mm;
			
			tr.appendChild(td);
			td = document.createElement("td");
			var disclosure=""
			switch(post.disclosureLevel){
				case 1:
					disclosure+="나만보기";
					break;
				case 2:
					disclosure+="회원 공개";
					break;
				case 3:
					disclosure+="전체공개";
					break;
				default:
					disclosure+="X";
					break
			}
			td.innerHTML = disclosure;
			tr.appendChild(td);
			tr.setAttribute("onclick","location.href='/termProject-final/openDiary?diaryNo="+(post.no+1)+"'");
			tbody.appendChild(tr)
		}
	</script>
</body>
</html>
