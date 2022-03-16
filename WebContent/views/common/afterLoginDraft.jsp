<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="semi.member.model.vo.Member" %>
<%
	//2022.1.23(일) 15h45 임시 페이지
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 후 보이는 화면</title>
</head>
<body>
	<div>
      	<a href="<%= contextPath %>/traveller1.st">여행을 가시는 건가요?</a><br> <!--"/traveller1.st" 서블릿 = keyword1EnrollFormController = "views/keyword/keyword1EnrollForm.jsp"만 띄워줌-->
		<a href="<%= contextPath %>/reviewer.st">이번 여행도 즐거우셨나요?</a><br><br><br>
		놀멍쉬멍에서 소중한 추억을 만들어 보세요 :)
	</div>
</body>
</html>