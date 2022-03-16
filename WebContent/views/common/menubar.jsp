<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="semi.member.model.vo.Member" %>
<%	
	//2022.1.19(수) 15h
	String contextPath = request.getContextPath();
	
	//추가할 부분(2022.1.23(일) 15h25 메모)
	Member loginUser = (Member)session.getAttribute("loginUser"); // 이 key 값과 세트인 value 'loginUser' 객체는 Member 형태 -> java.lang에 있는(x) 내가 만든(o) 클래스이므로 import 필요; getAttribute()의 반환형 = Object -> 대입을 위해서는 강제 형 변환 필요
	// 이 페이지가 로그인 전 로딩될 때 loginUser는 null vs 로그인 후 로딩될 때 loginUser에는 로그인한 회원의 정보가 담겨있음

	String alertMsg = (String)session.getAttribute("alertMsg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공통 부분 묶어둠</title> <!-- 히든 메뉴바 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<!--2022.1.26(수) 민성님의 1차 취합본에서 가져다 쓰기로 함-->
	<script>
		// script 태그 위치 신경쓰기 싫으면 window.onload() 써두기
		// script 태그 내에서도 scriptlet과 같은 jsp 요소 쓸 수 있음
		var msg = "<%= alertMsg %>"; // 메시지 문구 "성공적으로 로그인하셨습니다" 또는 "null" e.g. LoginController로부터 session.setAttribute("alertMsg", "성공적으로 로그인하셨습니다");
		
		if(msg != "null"){ // msg에 성공 메시지 문구가 담겨있을 경우
			alert(msg);
		
			// 위 줄만 쓰면 menubar.jsp가 로딩될 때마다 매번 alert가 뜸 vs 해결방법 = session에 들어있는 'alertMsg' key 값에  대한 value를 지워줌(객체명.removeAttribute("key 값"))
			<% session.removeAttribute("alertMsg"); %>
		}
	</script>

</body>
</html>