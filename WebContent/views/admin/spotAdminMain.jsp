<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[관리자] 업체 관리</title>
<style>
	table {border: 1px solid mediumspringgreen;}
	
	table tr {text-align: center;}
	
	.layer {display: none;}
	
	.outer {
        border: 1px solid mediumspringgreen;
        width: 1000px;
		height: 800px;
        margin: auto;
        margin-top: 50px;
    }
	.list-area {
		border: 1px solid seagreen;
		text-align: center;
	}
	.list-area>tbody>tr:hover {
		cursor: pointer;
		background: limegreen;
	}
</style>
</head>
<body>
	<!--2022.1.28(금) 10h15-->
	<%@ include file = "../common/menubar.jsp" %>

	<div class="outer">
		<div class="common">
			<h2>[관리자] 업체 관리</h2>
			<br>

			<!--2022.2.2(수) 14h50 식당 관리 기능 구현 시작-->
			<table border="1">
				<thead>
					<tr>
						<th colspan="2" height="50">업체 관리</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th width="300" height="50">여행지 관리</th>
						<th width="300" height="50">식당 관리</th>
					</tr>
					<tr>
						<td height="50">
							<select name="category" id="category">
								<option value="default" selected>여행지 분류를 선택해주세요</option>
								<option value="HOTEL">숙소</option>
								<option value="LANDSCAPE">풍경명소</option>
								<option value="ACTIVITY">액티비티</option>
							</select>
						</td>

						<td height="50">
							<select name="categoryR" id="categoryR">
								<option value="default" selected>식당 분류를 선택해주세요</option>
								<option value="KOREAN">한식당</option>
								<option value="JEJU">제주별미식당</option>
								<option value="WESTERN">양식당</option>
							</select>
						</td>
					</tr>
				</tbody>
			</table>			

			<script>
				$(function() {
					$("#category").change(function() {
						var choice = $("#category option:selected").val();

						switch (choice) {
						case "HOTEL" :
							// $(".layerHotel").show();
							// $(".layerLandscape, .layerActivity").hide();
							// 2022.1.29(토) 21h 구현
							location.href = '<%= contextPath %>/tsList.ads?category=' + choice + '&currentPage=1&boardLimit=7';
							break
						case "LANDSCAPE" :
							// $(".layerLandscape").show();
							// $(".layerHotel, .layerActivity").hide();
							location.href = '<%= contextPath %>/tsList.ads?category=' + choice + '&currentPage=1&boardLimit=7';
							break
						case "ACTIVITY" :
							// $(".layerActivity").show();
							// $(".layerHotel, .layerLandscape").hide();
							location.href = '<%= contextPath %>/tsList.ads?category=' + choice + '&currentPage=1&boardLimit=7';
						}
					})
					
					// 2022.2.2(수) 15h10
					$("#categoryR").change(function() {
						var choiceR = $("#categoryR option:selected").val(); // KOREAN 또는 JEJU 또는 WESTERN

						switch (choiceR) {
						case "KOREAN" :
							location.href = '<%= contextPath %>/rList.ads?categoryR=' + choiceR + '&currentPage=1&boardLimit=7';
							break
						case "JEJU" :
							location.href = '<%= contextPath %>/rList.ads?categoryR=' + choiceR + '&currentPage=1&boardLimit=7';
							break
						case "WESTERN" :
							location.href = '<%= contextPath %>/rList.ads?categoryR=' + choiceR + '&currentPage=1&boardLimit=7';
						}
					})
				})
			</script>
		</div> <!--div class="common" 영역 끝-->
		
		<!--아래와 같이 이 페이지에 숙소/풍경명소/액티비티 목록 관리하는 구조 2022.1.29(토) 17h에 중단 -> 각 여행 타입별 별도의 페이지에 목록 띄우기로 함-->
		<div class="layer layerHotel">
		</div> <!--div class="layer layerHotel" 영역 끝-->

		<div class="layer layerLandscape">
			<div class="wrap">
				<h3 align="center">풍경명소 관리</h3>
				해당 업체의 행을 클릭하면 상세 정보 조회가 가능합니다.
				<br>
			</div> <!--div class="wrap" 영역 끝-->
		</div> <!--div class="layer layerLandscape" 영역 끝-->

		<div class="layer layerActivity">
			<div class="wrap">
				<h3 align="center">액티비티 관리</h3>
				해당 업체의 행을 클릭하면 상세 정보 조회가 가능합니다.
				<br>
			</div> <!--div class="wrap" 영역 끝-->
		</div> <!--div class="layer layerActivity" 영역 끝-->
				
	</div> <!--div class="outer" 영역 끝-->

</body>
</html>