<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>1:1리스트</h1>
	<a href="<%=request.getContextPath()%>/list.qt?currentPage=1">1:1리스트</a>
	
	<h1>1:1작성</h1>
	<a href="<%=request.getContextPath()%>/qenroll.bo">1:1작성</a>
	
	<h1>유저1:1상세페이지</h1>
	<a href="<%=request.getContextPath()%>/detail.qt?questionNo=5">유저1:1상세페이지</a>
	
	<h1>로그인페이지</h1>
	<a href="<%=request.getContextPath()%>/views/common/login.jsp">로그인페이지</a>
</body>
</html>