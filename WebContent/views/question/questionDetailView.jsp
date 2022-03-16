<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "semi.question.model.vo.Question, semi.member.model.vo.*" %>    
<%	
	String contextPath = request.getContextPath();
	Question q = (Question)request.getAttribute("q"); // : Object이므로 강제 형변환
	Member loginUser = (Member)request.getSession().getAttribute("loginUser");
	
	int qno = q.getQuestionNo();
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 폰트 -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
<title>문의사항</title>
<style>
	#logo {
	width: 300px;
    height: 190px;
	cursor: pointer;
    margin-top: 10px;
    margin: auto;
  }
  #web-font{
    font-family: 'Jua', sans-serif;
    font-size: 40px;
    margin-top: 10px;
    }
   

  table, td, th {  
  border: 1px solid #ddd;
  text-align: left;
}

table {
  border-collapse: collapse;
  width: 65%;
}

th, td {
  padding: 15px;
}
.btn {
		margin: 30px 0 91px;
	}
	#list_btn {
		width: 90px;
		padding: 5px 7px;
		border: 0;
		cursor: pointer;
		color: #fff;
		border-radius: 7px;
		background-color: #72b1ca;
		font-size: 15px;
		font-weight: 400;
		font-family: "굴림";
		font-weight: 1000;
		margin-left: 650px;
		margin-bottom:50px;
	}
	#re_btn{
		width: 90px;
		padding: 5px 7px;
		border: 0;
		cursor: pointer;
		color: #fff;
		border-radius: 7px;
		background-color: #f5b369;
		font-size: 15px;
		font-weight: 400;
		font-family: "굴림";
		font-weight: 1000;
		margin-left: 0px;
	}
	#delete_btn{
		width: 70px;
		padding: 3px 3px;
		border: 0;
		cursor: pointer;
		color: #b37834;
		border-radius: 7px;
		background-color: #ffffff;
		border: 2px solid #b37834;
		font-size: 15px;
		font-weight: 400;
		font-family: "굴림";
		font-weight: 1000;
		margin-left: 720px;
		margin-bottom: 20px;
	}

</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet"	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<div align="center" class="logo" >
		<img src="<%=contextPath %>/resources/image/logo.png" alt="" id="logo">
	</div>
    <div class="outer">
        <h2 style="text-align: center; margin-top: 10px;"><p id="web-font">문의사항</p></h2>
    
        <table class="qna" align="center" border="1">
            <tr style="border: black 1px;">
                <th width="100px" height="20px;">제목</th>
                <td width="600px" colspan="3">
                	<%= q.getQuestionTitle() %>
                	<input type="hidden" name="questionNo" id="questionNo" value="<%=q.getQuestionNo() %>">
                </td>
          
                <th width="70px">성명</th>
                <td width="150px"><%= q.getUserName() %></td>
                <th width="80px;">작성일</th>
                <td width="120px"><%= q.getCreateDate() %></td>
            </tr>
			<tr style="border: black 1px;">
               <th>내용</th>
               <td colspan="7">
                   <p style="height: 250px;"><%= q.getQuestionContent() %></p>
               </td>
            </tr>
			<tr style="border: black 1px;">
            	<th height="50px">문의답변</th>
            	<td colspan="7"><%if(q.getAnswer() == null){%>
        		<%}else{%>
        		<%=q.getAnswer() %>
        		<%} %></td>
            </tr>
        </table>
        <br><br>
        
    </div>
    <%if(loginUser.getUserId().equals("admin")){ %>
	<div class="container">
		<!-- The Modal -->
		<div class="modal" id="myModal">
			<div class="modal-dialog">
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">댓글 입력</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<!-- Modal body -->
					<form action="<%=contextPath %>/answer.no">
						<div align="center">
							<input type="hidden" name="qNo" id="qNo" value="<%=q.getQuestionNo() %>">
							<%if(q.getAnswer() == null){ %>
								<textarea name="answer" rows="10" cols="64"></textarea>
							<%}else{ %>
									<textarea name="answer" rows="10" cols="64"><%=q.getAnswer() %></textarea>
							<%} %>
								</div>
								<div>
									<%if(q.getAnswer() == null){ %>
										<input type="submit" value="등록">
									<%}else{ %>
										<input type="submit" value="수정" id="upd_btn">
									<%} %>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<div class="btn">
							<input type="button" id="list_btn" value="목록가기" >
							<button type="button" class="re_btn" data-toggle="modal" data-target="#myModal" id="re_btn">답변달기</button>
	<%}else{ %>
							<input type="button" id="delete_btn" value="삭제">
	<%} %>
					</div>
<script type="text/javascript">
	
	$(function(){
		$('#list_btn').click(function(){
			location.href="<%=contextPath%>/list.qt?currentPage=1";
		})
		
		$('#delete_btn').click(function(){
			var result = confirm('삭제 하시겠습니까?'); 
			if(result) { 
				var qNo = $('#questionNo').val();
				
				location.href="<%=contextPath%>/delete.qt?qNo="+qNo;
			}
			
			
		})
	})
	
</script>
</body>
</html>