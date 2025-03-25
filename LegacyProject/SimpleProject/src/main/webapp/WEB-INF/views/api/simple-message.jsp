<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
	#wrap{
		width: 1000px;
		min-height: 400px;
		margin: auto;
		margin-top 30px;
		padding: 20px;
		height: auto;
		border: 1px solid rgba(0, 0, 0, 0.7);
		border-radius: 15px; 
		background-color: gray;
	}
	#content{
		margin: auto;
		padding: auto;
		min-height: 300px;
	}
	butten{
		width: 100%;
		
	}
	.message{
		border: 2px solid white;
		border-radius: 25px;
		margin-bottom: 10px;
	}
	.category{
		color: #FF00FF;
	}
	.content{
		color: #00FFFF;
		background-color: purple;
	}
	.region{
		color: #FFFF00;
	}
	
</style>
<title>문자</title>
</head>
<body>
	<jsp:include page="../include/header.jsp"/>
	<div id="wrap">
		<div id="content">
			
		</div>
		<div>
			<button class="btn btn-outline-success" onclick="getMessage();">더보기</button>
		</div>
	</div>
	<script>
		$(function() {	
			getMessage();
		});
		
		let pageNo = 1;
		
		function getMessage() {
			$.ajax({
				url: `message?pageNo=\${pageNo}`,
				type: 'get',
				success: result =>{
					//console.log(result);
					const message = result.body;
					const outputStr = message.map(e =>
						`<div class="message">
							<h3 class="category">\${e.DST_SE_NM}</h3>
							<p class="content">\${e.MSG_CN}</p>
							<h6 class="region">\${e.RCPTN_RGN_NM}</h6>
						</div>`
					).join('');
					$('#content').append(outputStr);
					pageNo++;
				}
			});
		}
		
	</script>
	<jsp:include page="../include/footer.jsp"/>
</body>
</html>