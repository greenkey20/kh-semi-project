<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title> 회원가입</title>
    <link rel="stylesheet" href="new_main.css">
    <script src="https://code.jquery.com/jquery-3.4.0.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <style>
        html {
            height: 100%;
        }
        
        body {
            margin: 0;
            height: 100%;
            background: #ffffff;
            font-family: Dotum,'돋움',Helvetica,sans-serif;
        }
        #logo {
            width: 300px;
            cursor: pointer;
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
            width:100%;
            height:100%;
        }
        
        /*관심분야---*/
        .input_interest{
        	width:auto;
            height:auto;
        }
        .font_interest{
        	font-size: 13px;
        }

        /*에러*/
        .error_next_box {
            margin-top: 9px;
            font-size: 10px;
            color: red;   
        }
        
        /*이메일 인증확인 문구*/
        #EMAIL_verification{
            margin-top: 9px;
            font-size: 10px;
            color: green; 
            display: none; 
        }
        
        /*아이디 중복확인 버튼*/
        #id_btn {
            width: auto;
            display: inline-block;
            position: absolute;
            top: 50%;
            right: 16px;
            margin-top: -10px;
        }
        
        /*이메일 인증번호전송 버튼*/
        #email_btn, #email_btn2 {
            width: auto;
            display: inline-block;
            position: absolute;
            top: 50%;
            right: 16px;
            margin-top: -10px;
        }
        
        /* 회원가입 버튼 */
        .btn_area {
            margin: 30px 0 91px;
        }
        #btnJoin {
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
        
        /*---------------*/
        span{
        	border-radius: 4px;
        }
 
        </style>
</head>
<body>
	<%@ include file = "../common/menubar.jsp" %>
	
    <!-- header -->
    <div id="header">
        <!-- *로그인 페이지 링크 입력하기-->
        <a href="<%= contextPath %>" target="_blank" title="첫 페이지로 돌아가기"><img src="<%=contextPath %>/resources/image/logo.png" id="logo"></a>
    </div>

    <!-- wrapper -->
    <div id="wrapper" >
        <!-- content-->
    <div id="content">
      <form id="enroll-form" action="<%= contextPath %>/member.enroll" method="post">
            <!-- ID -->
            <div>
                <h3 class="join_title"><label for="id">아이디</label></h3>
                <span class="box box_id">
                    <input type="text" id="id" class="int" name="userId" maxlength="20" required>
                    <button type="button" id="id_btn" onclick= "idCheck();">중복확인</button>
                </span>
                <span class="error_next_box" id="error_userId">첫글자를 반드시 영문자로, 영문자, 숫자 총 4~12자로 입력하시오.</span>
            </div>

            <!-- PW1 -->
            <div>
                <h3 class="join_title"><label for="pswd1">비밀번호</label></h3>
                <span class="box box_password">
                    <input type="password" id="password1" class="int" name="userPwd" maxlength="20" placeholder="영문자, 숫자, 특수문자(!@#$%^)로 총 8~15자로 입력하시오." required>
                </span>
                <span class="error_next_box" id="error_password1">영문자, 숫자, 특수문자(!@#$%^)로 총 8~15자로 입력하시오</span>
            </div>

            <!-- PW2 확인 -->
            <div>
                <h3 class="join_title"><label for="pswd2">비밀번호 재확인</label></h3>
                <span class="box box_pass_check">
                    <input type="password" id="password2" class="int" maxlength="20" placeholder="비밀번호 재입력" required>
                </span>
                <span class="error_next_box" id="error_password2">비밀번호가 일치하지 않습니다</span>
            </div>

            <!-- NAME -->
            <div>
                <h3 class="join_title"><label for="name">이름</label></h3>
                <span class="box box_name">
                    <input type="text" id="name" class="int" name="userName" maxlength="20" required>
                </span>
                <span class="error_next_box" id="error_userName">이름 형식이 올바르지 않습니다.</span>
            </div>

            <!-- EMAIL -->
            <!-- 메일인증 기능 넣기 -->
            <div>
                <h3 class="join_title"><label for="email">이메일(+본인 확인)<span class="optional"></span></label></h3>
                <span class="box box_email">
                    <input type="text" id="email" class="int" name="email" maxlength="100" placeholder="이메일 입력" required>
                    <button type="button" onclick = "emailCheck();" id="email_btn">인증번호 받기</button>
                </span>
                <span class="error_next_box" id="error_email">이메일 형식이 올바르지 않습니다.</span>
            </div>

            <!-- EMAIL_verification code -->
            <div>
                <span class="box box_emailCheck">
                    <input type="text" id="email2" class="int" maxlength="10" placeholder="인증번호 입력하세요" required>
                    <button type="button"  id="email_btn2" >인증하기</button>
                </span>
                <span class="" id="EMAIL_verification">인증되었습니다.</span>
            </div>

            <!-- MOBILE -->
            <div>
                <h3 class="join_title"><label for="phoneNo">휴대전화(선택)</label></h3>
                <span class="box box_mobile">
                    <input type="tel" id="phoneNo" class="int" name="phone" maxlength="16" placeholder="xxx-xxxx-xxxx 형식 입력">
                </span>
            </div>

			<!-- ADDRESS -->
            <div>
                <h3 class="join_title"><label for="address">주소(선택)</label></h3>
                <span class="box box_address">
                    <input type="tel" id="address" class="int" name="address" maxlength="100" placeholder="주소 입력">
                </span>
            </div>

            <!-- INTEREST-->
            <div>
                <h3 class="join_title">관심분야</h3>
                <span class="box box_interest ">
                    <input type="checkbox" class="input_interest" name="interest" value="액티비티" id="activity"><label class="font_interest" for="activity">액티비티</label>
                    <input type="checkbox" class="input_interest" name="interest" value="관광지" id="tourist_spot"><label class="font_interest" for="tourist_spot">관광지</label>
                    <input type="checkbox" class="input_interest" name="interest" value="호텔" id="hotel"><label class="font_interest" for="hotel">호텔</label>
                    <input type="checkbox" class="input_interest" name="interest" value="음식" id="food"><label class="font_interest" for="food">음식</label>
                    <input type="checkbox" class="input_interest" name="interest" value="쇼핑" id="shopping"><label class="font_interest" for="shopping">쇼핑</label>

                </span>
            </div>

            <!-- JOIN BTN-->
            <div class="btn_area">
                <button type="submit" id="btnJoin"  >
                    <span>가입하기</span>
                </button>
            </div>
    	</form>
    </div> 
    <!-- content-->
    </div> 
    <!-- wrapper -->
    
    <script>
       $(function(){
    		// 아이디 유효성 검사 -> 첫글자를 반드시 영문자로, 영문자, 숫자 총 4~12자로 입력
    		$("#id").keyup(function(){
    			var $id = $("#id").val();
    			var regExp = /^[a-z][a-z\d]{3,11}$/i;
    			
    			if(regExp.test($id)){
    				$("#error_userId").text("중복확인을 해주세요.").css("color", "green");
    				$(".box_id").css("box-shadow", "1px 1px 4px greenyellow").css("box-shadow", "-1px -1px 4px greenyellow").css("border-color", "greenyellow");
    			}else{
    				$("#error_userId").text("첫글자를 반드시 영문자로, 영문자, 숫자 총 4  ~ 12자로 입력하시오.").css("color", "red");
    				$(".box_id").css("box-shadow", "1px 1px 4px red").css("box-shadow", "-1px -1px 4px red").css("border-color", "orangered");
    				
    			}
    		})
    		
    		// 비밀번호 유효성 검사 -> 영문자, 숫자, 특수문자(!@#$%^)로 총 8~15자로 입력
    		$("#password1").keyup(function(){
    			var $password1 = $("#password1").val();
    			var regExpPw = /^[a-z\d!@#$%^]{8,15}$/i;
    			
    			if(regExpPw.test($password1)){
    				$("#error_password1").text("사용가능합니다.").css("color","green");
    				$(".box_password").css("box-shadow", "1px 1px 4px greenyellow").css("box-shadow", "-1px -1px 4px greenyellow").css("border-color", "greenyellow");
    			}else{
    				$("#error_password1").text("영문자, 숫자, 특수문자로 총 8~15자로 입력하시오.").css("color", "red");
    				$(".box_password").css("box-shadow", "1px 1px 4px red").css("box-shadow", "-1px -1px 4px red").css("border-color", "orangered");
    			}
    		}) 
    		
    		// 비밀번호 확인
  			$("#password2").keyup(function(){
  				var $password1 = $("#password1").val();
  				var $password2 = $("#password2").val();
  			
  				if($password1 == $password2){
  					$("#error_password2").text("일치합니다.").css("color","green");
  					$(".box_pass_check").css("box-shadow", "1px 1px 4px greenyellow").css("box-shadow", "-1px -1px 4px greenyellow").css("border-color", "greenyellow");
  				}else{
  					$("#error_password2").text("일치하지 않습니다.").css("color", "red");
  					$(".box_pass_check").css("box-shadow", "1px 1px 4px red").css("box-shadow", "-1px -1px 4px red").css("border-color", "orangered");
  				}
  			})
  			
  			// 이름 형식 확인
    		$("#name").keyup(function(){
    			var $userName = $("#name").val();
    			var regExpId = /^[가-힣]{2,}$/;
    			
    			if(regExpId.test($userName)){
    				$("#error_userName").text("사용가능합니다.").css("color","green");
  					$(".box_name").css("box-shadow", "1px 1px 4px greenyellow").css("box-shadow", "-1px -1px 4px greenyellow").css("border-color", "greenyellow");
    			}else{
    				$("#error_userName").text("이름 형식이 올바르지 않습니다.").css("color", "red");
    				$(".box_name").css("box-shadow", "1px 1px 4px red").css("box-shadow", "-1px -1px 4px red").css("border-color", "orangered");
    			}
    		}) 
    		
    		// 이메일 형식 확인
		 	$("#email").keyup(function(){
		 		var $email = $("#email").val();
		 		var regExp = /^[A-Za-z0-9._]+[@]+[A-Za-z0-9]+[.]+[A-Za-z.]+$/;
		 		
		 		if(regExp.test($email)){
		 			$("#error_email").text("인증번호를 입력해주세요").css("color","green");
		 			$(".box_email").css("box-shadow", "1px 1px 4px greenyellow").css("box-shadow", "-1px -1px 4px greenyellow").css("border-color", "greenyellow");
		 		}else{
		 			$("#error_email").text("이메일 형식이 올바르지 않습니다.").css("color","red");
		 			$(".box_email").css("box-shadow", "1px 1px 4px red").css("box-shadow", "-1px -1px 4px red").css("border-color", "orangered");
		 		}
		 	})
		 	
       })
	   
		function idCheck(){
	        var $userId = $("input[name=userId]");
	        
	        $.ajax({
	            url : "idCheck.enroll",
	            data : { checkId : $userId.val() },
	            success : function(result){
	                
	                // result 경우의 수 : "NN", "NY"
	                if(result == "NN"){ // 중복된 아이디 == 사용불가
	                    
	                    alert("이미 존재하거나 탈퇴한 회원의 아이디입니다.");
	                    //재입력 유도
	                    $userId.focus();
	                }
	                else{ // 중복되지 않은 아이디 == 사용가능
	                    
	                    alert("사용 가능한 아이디입니다.")
	                }
	            },
	            error : function(){
	                console.log("아이디 중복체크 실패");
	            }
	        })
	    }
	   
	   function emailCheck(){
		   $.ajax({
			   url : "email.check",
			   data : {email : $("#email").val()},
				success : function(result){

					console.log(result);
					$("#email_btn2").click(function(){
						if($("#email2").val() == result){
							$("#EMAIL_verification").css("display", "block");
						}
					})
					
				},
             error : function(){
                 console.log("이메일 인증 실패");
             }
			   
		   })
		   
	   }

    </script>
</body>
</html>