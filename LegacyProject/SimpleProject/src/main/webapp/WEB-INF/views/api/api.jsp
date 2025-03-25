<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>병원데이터 출력해보기</title>
</head>
<body>
	<jsp:include page="../include/header.jsp"/>
	<h1 style="color: green;">병원데이터 Print!</h1>
	
	<button class="btn btn-sm btn-info" onclick="callHospital();">병원 정보 보기</button>
	<div style="width: 800px; height: auto; margin: auto;">
		<table class="table">
			<thead>
				<tr>
					<th width="200">병원명(INST_NM)</th>
					<th width="300">주소지(ADDR)</th>
					<th width="300">오시는길(ESNS_RGHMP)</th>
					<th width="150">전화번호(RPRS_TLHN_1)</th>
				</tr>
			</thead>
			<tbody>
			
			</tbody>
		</table>
	</div>
	<script>
		function callHospital() {
			$.ajax({
				url :'hospitals',
				type :'get',
				success : result =>{
					//console.log(result);
					const hospitals = result.body;
					const resultEl = hospitals.map(e =>
						`<tr>
							<td width="200">\${e.INST_NM}</th>
							<td width="300">\${e.ADDR}</th>
							<td width="300">\${e.ESNS_RGHMP}</th>
							<td width="150">\${e.RPRS_TLHN_1}</th>
						</tr>`
					).join('');
					document.querySelector('tbody').innerHTML = resultEl;
				}
			});
		}
	</script>
	
	<jsp:include page="../include/footer.jsp"/>
</body>
</html>