<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="semi.notice.model.vo.Notice" %>
<%
		Notice n = (Notice)request.getAttribute("n"); 
	
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

    table{ border: 1px solid white;}
</style>
</head>
<body>

	<%@ include file="../common/menubar.jsp" %>

    <div class="outer">
        <br>
        <h2 align="center">공지사항 상세보기</h2>
        <br>

        <table align="center" border="1">
            <tr>
                <th width="70">제목</th>
                <td width="400" colspan="3"><%= n.getNoticeTitle() %></td>
            </tr>
            <tr>
                <th>작성자</th>
                <td><%= n.getNoticeWriter() %></td>
                <th>작성일</th>
                <td><%= n.getCreateDate() %></td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="3">
                    <p style="height:250px;"><%= n.getNoticeContent() %></p>
                </td>
            </tr>
        </table>
        <br><br>
        
        <div align="center">
        	<a href="<%= contextPath%>/list.no" class="btn btn-sm btn-primary">목록가기</a>
        	
        
        </div>

    </div>

</body>
</html>