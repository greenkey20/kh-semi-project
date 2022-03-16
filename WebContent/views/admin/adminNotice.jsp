<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, semi.notice.model.vo.Notice" %>
<%
	ArrayList<Notice> list = (ArrayList<Notice>)request.getAttribute("list"); // : Object
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<style>
	    .outer{
        background-color: skyblue;
        color: white;
        width: 1000px;
		height: 500px;
        margin : auto;
        margin-top : 50px;
    }
	.list-area{
		border : 1px solid white;
		text-align: center;
	}
	.list-area>tbody>tr:hover{
		cursor : pointer;
		background : lightblue;
	}
	
	

</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	
	
	<div class="outer">

		<br>
		<h2 align="center">공지사항</h2>
		<br>

		
			<div align="right" style="width:850px;">
				
				<a href="<%= contextPath %>/enrollForm.no" class="btn btn-sm btn-primary">글작성</a>
				
				<br><br>
			</div>


	
		<table align="center" class="list-area">
			<thead>
				<tr>
					<th>글번호</th>
					<th width="400">글제목</th>
					<th width="100">작성자</th>
					<th>조회수</th>
					<th width="100">작성일</th>
				</tr>
			</thead>
			<tbody>
				
				<%if(list.isEmpty()) { %>
					<tr>
						<td colspan="5">공지사항이 없습니다.</td>
					</tr>
				<% }else {%> 
						<% for(Notice n : list) { %>
							<tr>
								<td><%= n.getNoticeNo() %></td>
								<td><%= n.getNoticeTitle() %></td>
								<td><%= n.getNoticeWriter() %></td>
								<td><%= n.getCount() %></td>
								<td><%= n.getCreateDate() %></td>
							</tr>
						<% } %>
				<% } %>
			</tbody>
		</table>

		<script>
			$(function(){
				$(".list-area>tbody>tr").click(function(){


					
					var nno = $(this).children().eq(0).text(); 

				
					location.href = "<%= contextPath %>/admindetail.no?nno=" + nno;
				})
			})
		</script>


	
	</div>
</body>
</html>