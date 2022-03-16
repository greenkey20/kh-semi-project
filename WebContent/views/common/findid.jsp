<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "semi.member.model.vo.Member" %>
<%
	String contextPath = request.getContextPath();

   Member loginUser = (Member)session.getAttribute("loginUser");   // : Object
   
   String alertMsg = (String)session.getAttribute("alertMsg"); // : Object
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디찾기</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style>
	html {
		height: 60%;
	}
	
	body {
		margin: 0;
		height: 100%;
		background: #ffffff;
		font-family: Dotum,'돋움',Helvetica,sans-serif;
	}
	#logo {
		    width: 300px;
            height: 170px;
		    cursor: pointer;
            margin-top: 10px;
	    }

	#title {
		font-family: 'Courier New', Courier, monospace;
		font-weight: 1500;
	}
	
	#header {
		width: auto;
		height: auto;
		padding-top: 10px;
		padding-bottom: 5px;
		text-align: center;
	}
	#wrapper {
		position: relative;
		height: 100%;
	}
	
	#content {
		position: absolute;
		left: 50%;
		transform: translate(-50%);
		width: 360px;
	}
	/* 입력폼 */
	
	
	h3 {
		margin: 19px 0 8px;
		font-size: 14px;
		font-weight: 700;
	}
	
	
	.box {
		display: block;
		width: 100;
		height: 51px;
		border: solid 1px #dadada;
		padding: 10px 14px 10px 14px;
		box-sizing: border-box;
		background: #fff;
		position: relative;
	}
	
	.int {
		display: block;
		position: relative;
		width: 100%;
		height: 29px;
		border: none;
		background: #fff;
		font-size: 13px;
	}
	
	input {
		font-family: '굴림';  
	}
	/* 아이디찾기 버튼 */
	.btn_area {
		margin: 30px 0 91px;
	}
	#fid_btn {
		width: 100%;
		padding: 21px 17px;
		border: 0;
		cursor: pointer;
		color: #fff;
		border-radius: 7px;
		background-color: #72b1ca;
		font-size: 15px;
		font-weight: 400;
		font-family: "굴림";
		font-weight: 1000;
	}

	div[contenteditable=true] {
	margin-top: 10px;
  	border: 1px solid #dadada;
  	color : rgb(87, 84, 84);
  	font-size: 12px;
 	width: 350px;
  	padding: 5px;
	}
 

	</style>

<script>
	$(function(){
		$('#fid_btn').click(function(){
			$.ajax({
	               url : "<%=request.getContextPath()%>/fid.me",
	               data : { 
	            	   email : $('#email').val(),
	            	   name : $('#name').val()
	               },
	               type : "post",
	               success : function(result){
	                 console.log(result);
	                 if(result==""){
	                	 alert("정보가 없습니다");
	                 }else{
	                	 $('.resultId').text(result);
	                 }
	               },
	               error : function(){
	                  console.log('실패');
	               }
	            })
		})
	})
</script>
</head>
<body>
     <!-- header -->
	 <div id="header">
		<!-- *링크 입력하기-->
		<a href="views/common/login.jsp" target="_blank" title="로그인 페이지로 돌아가기"><img src="<%=contextPath %>/resources/image/logo.png" id="logo"></a>
	</div>

	<!-- wrapper -->
	<div id="wrapper" >
		<!-- content-->
		<div id="content">

			<div>
				<h3 class="find_title"><label for="name">이름<span class="optional"></span></label></h3>
				<span class="box int_name">
					<input type="text" id="name" class="int" name="name" maxlength="100" placeholder="이름">
				</span>
			</div>

			<!-- EMAIL-->
			<div>
				<h3 class="find_title"><label for="email">이메일<span class="optional"></span></label></h3>
				<span class="box int_email">
					<input type="text" id="email" class="int" name="email" maxlength="100" placeholder="이메일">
				</span>
			</div>
			
			<!--BTN-->
			<div class="btn_area">
				<input type="button" value="아이디찾기" id="fid_btn">
			<div>
				<div contenteditable="true" class="resultId" >
				</div>
			</div>
		</div>
		</div> 
		<!-- content-->

	</div> 
	<!-- wrapper -->
</body>
</html>