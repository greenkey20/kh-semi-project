<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 작성</title>
<style>
	.outer{
        background-color: skyblue;
        color: white;
        width: 1000px;
		height: 500px;
        margin : auto;
        margin-top : 50px;
    }

	#enroll-form>table {border : 1px solid white}
	#enroll-form input, #enroll-form textarea{
		width: 100%;
		box-sizing: border-box;
	}
</style>
</head>
<body>

	<%@ include file = "../Member/login.jsp" %>
	
	<div class="outer">

		<br>

		<h2 align="center">공지사항 작성하기</h2>
		<br>

		<form id="enroll-form" action="<%= contextPath %>/insert.no" method="post">
		
			<input type="hidden" name="userNo" value="<%= loginUser.getUserNo() %>">

			<table align="center">
				<tr>
					<th width="50">제목</th>
					<td width="400"><input type="text" name="title" required></td>
				</tr>
				<tr>
					<th>내용</th>
					<td></td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea name="content" rows="10" style="resize:none" required></textarea>
					</td>
				</tr>
			</table>
			<br><br>

			<div align="center">
				<button type="submit" class="btn btn-sm btn-primary">등록하기</button>
				<button type="button" class="btn btn-sm btn-secondary" onclick="history.back();">뒤로가기</button>
				
			</div>

		</form>
	
	
	</div>

</body>
</html>