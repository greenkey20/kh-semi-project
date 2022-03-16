<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%	
	//2022.2.4(금) 11h 그룹 코드 LoginController 읽으며 은영 추가 -> 로그인 실패 시 alert창으로 보여줄 error 메시지
	String errorMsg = (String)request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<style>
header {
	display: flex;
	justify-content: center;
}

form {
	padding: 10px;
}

.input-box {
	position: relative;
	margin: 10px 0;
}

.input-box>input {
	background: transparent;
	border: none;
	border-bottom: solid 1px #ccc;
	padding: 20px 0px 5px 0px;
	font-size: 14pt;
	width: 60%;
}

input::placeholder {
	color: transparent;
}

input:placeholder-shown+label {
	color: #aaa;
	font-size: 14pt;
	top: 15px;
}

input:focus+label, label {
	color: #8aa1a1;
	font-size: 10pt;
	pointer-events: none;
	position: absolute;
	left: 0px;
	top: 0px;
	transition: all 0.2s ease;
	-webkit-transition: all 0.2s ease;
	-moz-transition: all 0.2s ease;
	-o-transition: all 0.2s ease;
}

input:focus, input:not (:placeholder-shown ){
	border-bottom: solid 1px #8aa1a1;
	outline: none;
}

input[type=submit] {
	background-color: #6aa3ac;
	border: none;
	color: white;
	border-radius: 5px;
	width: 60%;
	height: 35px;
	font-size: 14pt;
	margin-top: 8px;
}

.etc {
	font-size: 12pt;
	color: rgb(164, 164, 164);
	margin: 10px 0px;
	padding-left: 12%;
	margin-top: 2%;
}

#bimg {
	width: 660px;
	height: 600px;
}

#logo {
	width: 240px;
	height: 150px;
	margin-left: 14%;
	margin-top: 5%;
}

#fid {
	color: #565f5f;
}

#fpwd {
	color: #565f5f;
}

#enroll {
	color: #565f5f;;
}
</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js" type="text/javascript"></script>
</head>
<body>
	<%@ include file = "../common/menubar.jsp" %>
	
	<script>
		var msg = "<%= errorMsg %>"; // 메시지 문구 "로그인에 실패했습니다" 또는 "null" e.g. LoginController로부터 request.setAttribute("errorMsg", "로그인에 실패했습니다.")
		
		if(msg != "null"){ // msg에 성공 메시지 문구가 담겨있을 경우
			alert(msg);
		
			// 위 줄만 쓰면 이 문서가 로딩될 때마다 매번 alert가 뜸 vs 해결방법 = request에 들어있는 'alertMsg' key 값에  대한 value를 지워줌(객체명.removeAttribute("key 값"))
			<% request.removeAttribute("errorMsg"); %>
		}
	</script>

	<form action="<%=contextPath%>/login.me" method="post">
		<div class="row">
			<div class="col-xs-6">
				<img src="<%=contextPath%>/resources/image/semi.jpg" alt="" id="bimg">
			</div>
			
			<div class="col-xs-6">
				<div class="logo">
					<img src="<%=contextPath%>/resources/image/logo.png" alt="" id="logo">
				</div>
				<div class="input-box">
					<input id="userId" type="text" name="userId" placeholder="아이디">
					<label for="userId">아이디</label>
				</div>
				<div class="input-box">
					<input id="userPwd" type="password" name="userPwd" placeholder="비밀번호"> <label for="userPwd">비밀번호</label>
				</div>
				<div class="etc" id="etc">
					<a href="<%=contextPath%>/find.id" id="fid"><b>아이디 찾기 |</b></a>
					<a href="<%=contextPath%>/find.pwd" id="fpwd"> <b>비밀번호 찾기 |</b></a>
					<a href="<%=contextPath%>/views/member/memberEnrollForm.jsp" id="enroll"><b>회원가입</b></a> <br>
				</div>
				<input type="submit" value="로그인">
			</div>
		</div>
	</form>
</body>
</html>