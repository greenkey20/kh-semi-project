<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="semi.member.model.vo.Member" %>
<%
	//2022.1.27(목) 17h20
	String contextPath = request.getContextPath();

	Member loginUser = (Member)session.getAttribute("loginUser"); // 이 key 값과 세트인 value 'loginUser' 객체는 Member 형태 -> java.lang(?)에 있는(x) 내가 만든(o) 클래스이므로 import 필요; getAttribute()의 반환형 = Object -> 대입을 위해서는 강제 형 변환 필요
	// 로그인 전 menubar.jsp가 로딩될 때 loginUser는 null
	// 로그인 후 menubar.jsp가 로딩될 때 loginUser에는 로그인한 회원의 정보가 담겨있음
	
	String alertMsg = (String)session.getAttribute("alertMsg"); // 서비스 요청 전 alertMsg는 null vs 서비스 요청 후 성공 시 alertMsg는 메시지 문구
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이 페이지</title>
</head>
<body>
	<script>
		var msg = "<%= alertMsg %>"; 
		if (msg != "null") {
			alert(msg);
			<% session.removeAttribute("alertMsg"); %>}
	</script>

	<h2><%= loginUser.getUserName() %>(<%= loginUser.getUserId() %>) 님, 환영합니다~</h2>
	<h3>오늘 추천 받은 코스</h3>
	<h3>나의 관심 키워드</h3>
	<h3>나의 정보 수정</h3>
	<h3>회원 탈퇴</h3>
	등등
</body>
</html>