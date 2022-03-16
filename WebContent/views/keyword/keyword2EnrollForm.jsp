<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String travelType = (String)request.getAttribute("travelType");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>키워드2(음식 스타일) 선택 form</title>
</head>
<body>
	<h2>어떤 음식을 좋아하세요?</h2>
	
	<!--2022.1.19(수) 16h20-->
    <!-- <form action="selectkeyword2.tr">
        <input type="hidden" name="traveltype" value="<%= (String)request.getAttribute("traveltype") %>">      
                
        <input type="radio" name="foodtype" id="radioF1" value="korean"> <label for="radioF1">#세상의 중심은 밥심, 고로 난 한식</label><br>
        <input type="radio" name="foodtype" id="radioF2" value="jeju"> <label for="radioF2">#여까지 왔는데 이걸 안 먹는다? 제주 대표 음식</label><br>
        <input type="radio" name="foodtype" id="radioF3" value="western"> <label for="radioF3">#그래도 여행인데 우아하게 가야지, 난 양식</label><br><br>

        <button type="submit">추천 여행 보기</button>
        <button type="reset">초기화</button>
    </form> -->

    <!--2022.1.19(수) 23h10-->
    <!--"selectkeyword2.tr" 서블릿 = selectKeyword2Controller = 이 전 jsp 페이지에서 사용자가 선택해서 넘긴 travelType(여행 스타일)의 값 + 이 페이지에서 사용자가 선택해서 넘기는 foodType(음식 스타일)의 값을 받아 DB로부터 추천 여행지들을 조회해옴--> 
    <button onclick="location.href='<%= contextPath %>/selectkeyword2.tr?traveltype=<%= travelType %>&foodtype=korean'">#세상의 중심은 밥심, 고로 난 한식</button><br><br>
    <button onclick="location.href='<%= contextPath %>/selectkeyword2.tr?traveltype=<%= travelType %>&foodtype=jeju'">#여까지 왔는데 이걸 안 먹는다? 제주 대표 음식</button><br><br>
    <button onclick="location.href='<%= contextPath %>/selectkeyword2.tr?traveltype=<%= travelType %>&foodtype=western'">#그래도 여행인데 우아하게 가야지, 난 양식</button>       
    
</body>
</html>