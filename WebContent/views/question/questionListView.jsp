<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, semi.question.model.vo.Question, semi.common.model.vo.*" %>
<%
	String contextPath = request.getContextPath();
   
    String alertMsg = (String)session.getAttribute("alertMsg"); // : Object
%>
<%
	ArrayList<Question> list = (ArrayList<Question>)request.getAttribute("list"); // : Object
	PageInfo pi = (PageInfo)request.getAttribute("pi");

	int currentPage = (int)pi.getCurrentPage();
	
	int startPage = (int)pi.getStartPage();
	int endPage = (int)pi.getEndPage();
	int maxPage = (int)pi.getMaxPage();
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 폰트 -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
<title>회원 문의 관리</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js" type="text/javascript"></script>  
  <style>
   #header {
		width: auto;
		height: auto;
		padding-top: 10px;
		padding-bottom: 5px;
		text-align: center;
	}   
   #logo {
	width: 300px;
    height: 190px;
	cursor: pointer;
    margin-top: 10px;
    margin: auto;
  }
  table{
    text-align: center;
  	width : 70%;
  	margin: auto;
  }
  th {
  background-color: #7cb4a0;
  color: white;
  height: 33px;
  font-size: 17px;
}
  tr:hover {background-color: rgb(248, 248, 224);}
  button{
    margin-top: 30px;
    font-size: 14px;
    background: none;
    color: #72b1ca;
    border: 3px solid #67be9e;
    background-color:white;
    padding: 3px 7px;
    border-radius: 7px;
    cursor: pointer;
}
#web-font{
    font-family: 'Jua', sans-serif;
    font-size: 40px;
    margin-top: 10px;
    }

#check{
    font-size: 14px;
    background: none;
    color: white;
    border: 0;
    background-color:#72b1ca;
    border-radius: 10px;
    cursor: pointer;
}

#non{
    font-size: 14px;
    background: none;
    color: white;
    border: 0;
    background-color:#f0ae82;
    border-radius: 10px;
    cursor: pointer;
}

</style>
</head>
<body>
    <div id="header">
    	
		<!-- *링크 입력하기-->
		<a href="" target="_blank" title="첫 페이지로 돌아가기"><img src="<%=contextPath %>/resources/image/logo.png" alt="" id="logo"></a>
	</div>
    <h2 style="text-align: center; margin-top: 10px;"><p id="web-font">문의내역</p></h2>
    
    <div class="container mt-3">
        <table class="list-area" style="margin-top: 40px; align: center;">
        <thead>
            <tr style="border: black 1px;">
                <th width="100">No</th>
                <th width="400">글제목</th>
                <th width="100">성명</th>
                <th width="100">아이디</th>
                <th width="100">작성일</th>
                <th width="100">답변여부</th>
            </tr>
        </thead>
         <tbody>
            <%if (list.isEmpty()){ %>
               <tr>
                  <td colspan="5">문의글이 없습니다</td>
               </tr>
            <%} else{%> <!-- 리스트가 차있을 경우 -->
            <!-- 공지사항 존재 -->
            	<!-- 향상된 for문! -->
               <%for(Question q : list){ %>
               <tr>
                  <td><%=q.getQuestionNo() %></td>   
                  <td><%=q.getQuestionTitle() %></td>   
                  <td><%=q.getUserName() %></td>   
                  <td><%=q.getUserId() %></td>   
                  <td><%=q.getCreateDate() %></td>
                  <td><%if(q.getaStatus().equals("Y")){%>
                  	<input type="button" value="답변완료" id="check" class="button disabled">
                  <%}else{ %>
                  	<input type="button" value="답변대기" id="non" class="button disabled">
                  <%} %>
                  </td>   
               </tr>
            <%   } %>
          <%  } %>
         </tbody>
    </table>
	<div class="page-area" align="center">
					
					<%if (currentPage != 1){ %>
						<button class="page_btn" onclick="location.href='<%=contextPath%>/list.no?currentPage=<%= currentPage - 1 %>'">&lt;</button>
					<%} %>
					<% for(int i = startPage; i <= endPage; i++) {%>
						<%if(i != currentPage) {%>
							<button class="page_btn" onclick="location.href='<%=contextPath%>/list.no?currentPage=<%= i %>'"><%= i %></button>
                     
						<%}else{%>
							<button class="page_btn_disabled" disabled><%= i %></button>
						<%} %>
					<%} %>
					<% if(currentPage != maxPage) { %>
						<button class="page_btn" onclick="location.href='<%=contextPath%>/list.no?currentPage=<%= currentPage + 1 %>'">&gt;</button>
					<%} %>
					
	</div>
    <script>

        $(function(){
		
            $(".list-area>tbody>tr").click(function(){
				console.log('클릭이벤트 되냐??');
                
                var questionNo = $(this).children().eq(0).text(); // 글번호
            
                console.log(questionNo);

                location.href="<%=contextPath%>/detail.qt?questionNo="+questionNo;
            
            })
        
        })

    </script>
</div>

</body>
</html>