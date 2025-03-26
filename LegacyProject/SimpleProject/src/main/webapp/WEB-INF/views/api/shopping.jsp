<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
	#items{
		width: 800px;
		padding: 40px;
		margin: auto;
		display: flex;
		flex-wrap: wrap;
		gap: 15px;
	}
	.div-view{
		
	}
	#item-img{
		width: 200px;
		height: 200px;
	}
</style>
<title>요건 쇼핑</title>
</head>
<body>
	<jsp:include page="../include/header.jsp"/>
	
	<h1>상품을 검색해주세요!</h1>
	<input type="text" id="query"/>
	<button onclick="search();">검색하기</button>
	<div id="items">
	
	</div>
	<script>
		function search() {
			const query = document.querySelector('#query').value;
			
			$.ajax({
				url: `naver-shopping?query=\${query}`,
				type: 'get',
				success: result =>{
					console.log(result);
					const items = result.items;
					const item = items.map(e =>
						`<div class="div-view">
							<h5>\${e.brand}</h5><br>
							<p>\${e.title}</p>
							<p>\${e.lprice}</p>
							<img src="\${e.image}" id="item-img"/>							
							<a href="\${e.link}" target="_blank">바로가기</a>							
						</div>`
					).join('');
					document.querySelector('#items').innerHTML = item;
				}
			});
		}
	</script>
	<jsp:include page="../include/footer.jsp"/>
</body>
</html>