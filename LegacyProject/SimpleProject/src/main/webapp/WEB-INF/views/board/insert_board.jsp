<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        .content {
            background-color:rgb(247, 245, 245); 
            width:80%;
            margin:auto;
        }
        .innerOuter {
            border:1px solid lightgray;
            width:80%;
            margin:auto;
            padding:5% 10%;
            background-color:white;
        }

        #enrollForm>table {width:100%;}
        #enrollForm>table * {margin:5px;}
        #img-area{
            width : 100%;
            margin : auto;
            text-align: center;
        }
        #img-area > img{
            width : 80%;
        }
    </style>
</head>
<body>
        
    <jsp:include page="../include/header.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 작성하기</h2>
            <br>

            <form id="enrollForm" method="post" action="boards" enctype="multipart/form-data">
                <table algin="center">
                    <tr>
                        <th><label for="title">제목</label></th>
                        <td><input type="text" id="title" class="form-control" name="boardTitle" required></td>
                    </tr>
                    <tr>
                        <th><label for="writer">작성자</label></th>
                        <td><input type="text" id="writer" class="form-control" value="${ sessionScope.loginMember.memberId }" name="boardWriter" readonly></td>
                    </tr>
                    <tr>
                        <th><label for="content">내용</label></th>
                        <td><textarea id="content" class="form-control" rows="10" style="resize:none;" name="boardContent" required></textarea></td>
                    </tr>
                    <tr>
                        <th><label for="upfile">첨부파일</label></th>
                        <td><input onchange="changeImage(this);" type="file" id="upfile" class="form-control-file border" name="upfile"></td>
                    </tr>
                    <tr>
                        <th colspan="2">
                            <div id="img-area">
                                <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMwAAADACAMAAAB/Pny7AAAAM1BMVEW7u7vz8/O3t7fQ0NDe3t729vbj4+O0tLTDw8Pn5+fu7u7JycnT09PNzc2+vr7Z2dn8/PxPXgjlAAACz0lEQVR4nO3a63KqMBSG4RAJgUAO93+1e0GroILSmexJdN7nXx3FfERWDo1SAAAAAAAAAAAAAAAAAAAAAAAAAIBydG7lkoRLZt6HQnF070yTmXGhUJgoWUzOPPP1YqGu6bImWbI0ZigTRg9zC7o2m265XqEwndzKqGw2OkrPFAzjxozfrUepKB8VRsaSo4JVPsyfPtHHto3+qLnlw/zhu3V0Sy1vjz5TXRh7OCWx8aeUGzPY/XdUF6aVArfbHh1uo0k6+HFWFkZ70yTn9+78MmH45fZbXFcYHdzc1jT0z3Gs2wz2/e4V6wqjpp+7b1JUjz8226w9Yz4iTLq21rjH2fxdz+wPNVWFsZ3ZThn9XefY9Zk5mk3WFEaPZnv3ZUDZxtHqd/ljGnMwOtUURg33SwL5rbWbSrAM8MvLR2uWisLoaWeN4tZRR6uxkyRmuBxdsZ4wsoreXXA5f5sT6NB7Px4vjOsJY+NOljmO67ajzppMPVXvasLIE7EfZjbZ5yb653lCNWFU+2JDIDX+8dMxyTyh1p7xzavdDSMtlzetj8vygJn2fmCtJUwY3uzUSEEO12dEX/sxNdO2HNQSZnq/65TcdJ2SaX/LOPg1TiVhTm1uGtP9Ttj02o/S/Ns8oZIw8dx2oEwJRmn5vOrZvhjnShBqCdOfirL0g2uD1e7xxbhcsYowuju/TyufuTz2o/zp5j2bGsLIbPl0luOMXV/FVpN9MfafzeKkdFtfPoyd0vvWvucmW7xnpDjliNLME7jiYbQ+WZY/IszpsvwBYezrGeaHhbnk+jdtHWHyZCEMYQhDmNLjzFeF+aJxxuc4DGTkGpca1jPtkEUbyq80lQp9FkGXDtNEnfcgUMkwJuMRrdjNpaRkz5ic5lJQ6vBcTLnGyxuXCh1r/KoDp0oHn/8ocKEs6j+c0S53SBsAAAAAAAAAAAAAAAAAAAAAAAAAoP4BZKMySZMqeNYAAAAASUVORK5CYII=" alt="">
                            </div>
                        </th>
                    </tr>
                </table>
                <br>
                <script>
                	function changeImage(file) {
						//console.log(file.files);
						//files : 선택된 파일의 정보가 들어있는 객체
						//file.files.length == 1 파일 선택됨
						//file.files.length == 0 선택 취소됨
						
						//파일이 첨부되었을 경우에는 file.files
						//0번 속성을 보면 파일정보를 확인할수 있음
						//console.log(file.files[0]);
						const imgEl = document.querySelector('#img-area > img');
						if(file.files.length){	//파일 첨부시 이미지 보이기
							const reader = new FileReader();
							reader.readAsDataURL(file.files[0]);
							
							reader.onload = function(e) {
								//console.log(e.target.result);
								const url = e.target.result;
								imgEl.src = url;
							}
						} else {
							imgEl.src = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMwAAADACAMAAAB/Pny7AAAAM1BMVEW7u7vz8/O3t7fQ0NDe3t729vbj4+O0tLTDw8Pn5+fu7u7JycnT09PNzc2+vr7Z2dn8/PxPXgjlAAACz0lEQVR4nO3a63KqMBSG4RAJgUAO93+1e0GroILSmexJdN7nXx3FfERWDo1SAAAAAAAAAAAAAAAAAAAAAAAAAIBydG7lkoRLZt6HQnF070yTmXGhUJgoWUzOPPP1YqGu6bImWbI0ZigTRg9zC7o2m265XqEwndzKqGw2OkrPFAzjxozfrUepKB8VRsaSo4JVPsyfPtHHto3+qLnlw/zhu3V0Sy1vjz5TXRh7OCWx8aeUGzPY/XdUF6aVArfbHh1uo0k6+HFWFkZ70yTn9+78MmH45fZbXFcYHdzc1jT0z3Gs2wz2/e4V6wqjpp+7b1JUjz8226w9Yz4iTLq21rjH2fxdz+wPNVWFsZ3ZThn9XefY9Zk5mk3WFEaPZnv3ZUDZxtHqd/ljGnMwOtUURg33SwL5rbWbSrAM8MvLR2uWisLoaWeN4tZRR6uxkyRmuBxdsZ4wsoreXXA5f5sT6NB7Px4vjOsJY+NOljmO67ajzppMPVXvasLIE7EfZjbZ5yb653lCNWFU+2JDIDX+8dMxyTyh1p7xzavdDSMtlzetj8vygJn2fmCtJUwY3uzUSEEO12dEX/sxNdO2HNQSZnq/65TcdJ2SaX/LOPg1TiVhTm1uGtP9Ttj02o/S/Ns8oZIw8dx2oEwJRmn5vOrZvhjnShBqCdOfirL0g2uD1e7xxbhcsYowuju/TyufuTz2o/zp5j2bGsLIbPl0luOMXV/FVpN9MfafzeKkdFtfPoyd0vvWvucmW7xnpDjliNLME7jiYbQ+WZY/IszpsvwBYezrGeaHhbnk+jdtHWHyZCEMYQhDmNLjzFeF+aJxxuc4DGTkGpca1jPtkEUbyq80lQp9FkGXDtNEnfcgUMkwJuMRrdjNpaRkz5ic5lJQ6vBcTLnGyxuXCh1r/KoDp0oHn/8ocKEs6j+c0S53SBsAAAAAAAAAAAAAAAAAAAAAAAAAoP4BZKMySZMqeNYAAAAASUVORK5CYII=';
						}
						
					}
                </script>

                <div align="center">
                    <button type="submit" class="btn btn-primary">등록하기</button>
                    <button type="reset" class="btn btn-danger">취소하기</button>
                </div>
            </form>
        </div>
        <br><br>

    </div>
    
    <jsp:include page="../include/footer.jsp" />
    
</body>
</html>