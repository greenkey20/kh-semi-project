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
<title>임시 비밀번호 발급</title>
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
	/* 임시비밀번호발급 버튼 */
	.btn_area {
		margin: 30px 0 91px;
	}
	#fpwd_btn {
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
	</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	$(function(){
		
		//이메일 인증 이벤트
	      $('#fpwd_btn').on('click', function(){
	         if ($('#userId').val() == "") {
	        	 $('#userId').val('아이디를 입력해 주세요.');
	        	 $('#userId').select();
	         } else if ($('#email').val() == "") {
	        	 $('#email').val('이메일을 입력해 주세요.');
	        	 $('#email').select();
	         } else {
	        	// 중복확인 AJAX 및 인증번호 발급 후 이메일 전송
	            $.ajax({
	               url : "emailAuth.me",
	               data : { 
	            	   userId : $('#userId').val(),
	            	   email : $('#email').val()
	            	   },
	               type : "post",
	               success : function(result){
	                  // if(result.check != 1){
	                  if(result != 1){ // 사용자가 입력한 사용자 이름 및 이메일 정보가 일치하는 회원 1명이 있지 않으면
	                    // $('.email_text').text('사용 할 수 없는 이메일 입니다.');
	                    // $('.email_text').css('color', 'red');
	                    alert('잘못된 정보입니다.다시 시도하세요.');
	                    $('#userId').val('');
	                    $('#email').val('');
	                  }else{ // 사용자가 입력한 사용자 이름 및 이메일 정보가 일치하는 회원 1명이 있으면
	                     alert('임시 비밀번호가 가입 시 입력한 이메일로 전송 되었습니다.')
	                    // $('.email_certification_text').text('인증번호를 입력해주세요.');
	                    // $('.email_certification_text').css('color', 'rgb(13, 71, 161)');
	                  }
	               },
	               error : function(){
	                  console.log('인증 번호 발송 실패');
	               }
	            })
	         }
	      })
	})
</script>
</head>
<body>
	  <!-- header -->
	  <div id="header">
		<!-- *링크 입력하기-->
		<a href="views/common/login.jsp" target="_blank" title="로그인 페이지로 돌아가기"><img src="<%=contextPath %>/resources/image/logo.png" alt="" id="logo"></a>
	</div>

	<!-- wrapper -->
	<div id="wrapper" >
		<form id="findPwd-form" action="" method="post">
		<!-- content-->
		<div id="content">

			<div>
				<h3 class="find_title"><label for="userId">아이디<span class="optional"></span></label></h3>
				<span class="box int_id">
					<input type="text" id="userId" class="int" name="userId" maxlength="100" placeholder="아이디">
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
			<input type="button" value="임시비밀번호발급" id="fpwd_btn">
		</div>
	</div>
	</form>
	</div> 
	<!-- content-->	
</body>
</html>