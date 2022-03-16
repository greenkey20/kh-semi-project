<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>키워드1(여행 스타일) 선택 form</title>
</head>
<body>
    <!--2022.1.19(수) 15h30-->
    <h2>당신의 여행을 들려주세요</h2>
    
    <!--2022.1.19(수) 낮 시도1-->
    <!-- <form action="selectkeyword1.tr">         
        <input type="radio" name="traveltype" id="radioT1" value="hotel"> <label for="radioT1">#호텔에서 모두 즐기는 slack 여행</label><br>
        <input type="radio" name="traveltype" id="radioT2" value="landscape"> <label for="radioT2">#내 추억을 더 멋지게 꾸며줄 풍경 맛집</label><br>
        <input type="radio" name="traveltype" id="radioT3" value="activity"> <label for="radioT3">#1분 1초가 아쉬워, 다음은 어디야? Activity 여행</label><br><br>
     
        <button type="submit">다음 페이지로</button>
        <button type="reset">초기화</button>
    </form> -->

    <!--2022.1.19(수) 낮 시도2-->
    <!-- <div id="travelType">
        <div value="hotel">#호텔에서 모두 즐기는 slack 여행</div>
        <div value="landscape">#내 추억을 더 멋지게 꾸며줄 풍경 맛집</div>
        <div value="activity">#1분 1초가 아쉬워, 다음은 어디야? Activity 여행</div>
    </div>

    <script>
        $(function() {
            if ($("#travelType").children().eq(0)) {

            }            
        })
    </script> -->

	<!--2022.1.19(수) 22h50-->
    <!--"selectkeyword1.tr" 서블릿 = selectKeyword1Controller = 이 페이지에서 사용자가 선택해서 넘기는 travelType(여행 스타일)의 값을 받아 request 객체의 attribute 영역에 set해서  "views/keyword/keyword2EnrollForm.jsp" 페이지 응답--> 
    <button onclick="location.href='<%= contextPath %>/selectkeyword1.tr?traveltype=hotel'">#호텔에서 모두 즐기는 slack 여행</button><br><br>
    <button onclick="location.href='<%= contextPath %>/selectkeyword1.tr?traveltype=landscape'">#내 추억을 더 멋지게 꾸며줄 풍경 맛집</button><br><br>
    <button onclick="location.href='<%= contextPath %>/selectkeyword1.tr?traveltype=activity'">#1분 1초가 아쉬워, 다음은 어디야? Activity 여행</button>   
    
</body>
</html>