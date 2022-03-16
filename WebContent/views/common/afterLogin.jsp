<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 후 보이는 화면</title>
</head>
<body>
	<%@ include file = "../common/menubar.jsp" %>

	<% if (loginUser == null) { %>
		잘못된 접근입니다- 로그인 후 이용 가능한 서비스입니다
	<% } else { %>		
	        <div id="user-info">
	            <b><%= loginUser.getUserName() %> 님</b>, 환영합니다<br>
	            <div>
	                <a href="<%= contextPath %>/myPage.me">my page</a> <!--내 application directory 구조 들키지 않도록 Servlet 이용-->
	                <a href="<%= contextPath %>/logout.me">로그아웃</a> <!--default/기본 = get 방식으로 요청보냄-->
	                <!--2022.1.18(화) 12h20 server 문제해결 scenario 풀이 시 시도-->
	                <!--<button id="logoutBtn" onclick="logout();">로그아웃</button>-->
	            </div>           
	        </div>
	        
	        <br><br>
	        
	        <div>
	        	<a href="<%= contextPath %>/traveller1.st">여행을 가시는 건가요?</a><br> <!--"/traveller1.st" 서블릿 = keyword1EnrollFormController = "views/keyword/keyword1EnrollForm.jsp"만 띄워줌-->
				<a href="<%= contextPath %>/reviewer.st">이번 여행도 즐거우셨나요?</a><br><br><br>
				놀멍쉬멍에서 소중한 추억을 만들어 보세요 :)
	        </div>
	<% } %>
	
</body>
</html>