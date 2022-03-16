<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>실시간 이메일 사용 검사 프로그램</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <h2>실시간 이메일 사용 검사 프로그램</h2>
    
	<!--2022.1.24(월) 15h30 평가자 checklist-->
    이메일 주소를 입력하세요 : <input type="text" id="email">
    <span id="result"></span>
    <!--나의 궁금증 = 강사님, 문제에서 ‘텍스트 입력창’으로 되어있고 이메일 형식을 따로 확인해야 한다는 것은 input 타입을 email 말고 text로 써야 하는 거죠..?-->

    <script>
        $(function() {

            $("#email").blur(function() {
                var $email = $("#email").val();
                var regExp = /^[A-Za-z0-9._]+[@]+[A-Za-z0-9]+[.]+[A-Za-z.]+$/;

                if (!regExp.test($email)) {
                    $("#result").text("이메일 형식에 맞지 않습니다.");
                    return;
                } else {
                    $.ajax({
                        url : "/SEMI-PROJECT/checkEmail", // 17h 나의 질문 = url을 그냥 "checkEmail"로 쓰면 왜 controller에 못 넘어갈까? "404 요청된 리소스 [/SEMI-PROJECT/views/member/checkEmail]은(는) 가용하지 않습니다" -> 17h30 "/SEMI-PROJECT/checkEmail"로 url 쓰니 controller 넘어감 vs 왜인지는 모르겠음 ㅠ.ㅠ 
                        data : {email : $email},
                        success : function(result) { // result 경우의 수 = true 또는 false
                        	if (result == "이미 사용 중인 email입니다.") {
                        		$("#result").text("이미 사용 중인 email입니다.");
                        	} else {
                        		$("#result").text("사용 가능한 email입니다.");
                        	}
                        },
                        error : function() {
                        	alert("조회하는 도중 오류가 발생했습니다.");
                        }
                        
                    })
                }
            })

        })

    </script>
   

</body>
</html>