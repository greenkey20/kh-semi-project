<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, semi.keyword.model.vo.TravelSpot, semi.keyword.model.vo.Activity, semi.common.model.vo.PageInfo" %>
<% 
	// 2022.1.30(일) 0h55
	ArrayList<TravelSpot> tsList = (ArrayList<TravelSpot>)request.getAttribute("tsList");
	String category = "";
	if (!tsList.isEmpty()) {
		category = tsList.get(0).getCategory(); // HOTEL 또는 LANDSCAPE 또는 ACTIVITY
	}
	PageInfo pi = (PageInfo)request.getAttribute("pi");
	
	// paging bar 만들 때 필요한 변수들 미리 세팅
	int currentPage = pi.getCurrentPage();
	int boardLimit = pi.getBoardLimit();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
	int maxPage = pi.getMaxPage();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>여행지(숙소/풍경명소/액티비티) 관리</title>
<style>
	.outer {
        border: 1px solid mediumspringgreen;
        width: 1000px;
		height: 1000px;
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
	.menu>div {
		float: left;
	}
</style>
</head>
<body>
	<%@ include file = "../common/menubar.jsp" %>

	<div class="outer">
		<div class="common">
			<div align="right" style="width: 800px;">
				<a href="views/admin/spotAdminMain.jsp" class="btn btn-sm btn-primary">업체 관리 메인으로 이동</a>			
			</div>
			<hr>

			<h2>[관리자] 여행지 관리</h2>
			<br>
			
			<select name="category" id="category">
				<option value="default" selected>여행지 분류를 선택해주세요</option>	
				<option value="HOTEL">숙소</option>
				<option value="LANDSCAPE">풍경명소</option>
				<option value="ACTIVITY">액티비티</option>
			</select>

			<script>
				$(function() {
					// 2022.2.1(화) 0h50 추가
					$("#category option").each(function() {
						if ($(this).val() == "<%= category %>") {
							($(this).attr("selected", true));
						}
					})
					
					$("#category").change(function() {
						var choice = $("#category option:selected").val();

						switch (choice) {
							case "HOTEL" :
								// 2022.1.29(토) 21h 구현
								location.href = '<%= contextPath %>/tsList.ads?category=' + choice + '&currentPage=1&boardLimit=7';
							break
							case "LANDSCAPE" :
								location.href = '<%= contextPath %>/tsList.ads?category=' + choice + '&currentPage=1&boardLimit=7';
							break
							case "ACTIVITY" :
								location.href = '<%= contextPath %>/tsList.ads?category=' + choice + '&currentPage=1&boardLimit=7';
						}
					})
				})
			</script>
		</div> <!--div class="common" 영역 끝-->
				
		<!--2022.1.29(토) 17h-->
		<div class="wrap">
			<hr>
			<!--2022.1.31(월) 10h20 이 페이지에서 숙소, 풍경명소, 액티비티 목록을 모두 볼 수 있도록 수정 = TravelSpotListController에서 받아온 ArrayList<TravelSpot> tsList의 카테고리 정보로 조건 걸어둠-->			
			<h3>
				<% switch(category) { 
	        	   case "HOTEL" : %>
	       			숙소 관리
	       		   <% break; 
	       		   case "LANDSCAPE" : %>
	       			풍경명소 관리
	       			<% break;
	    		   case "ACTIVITY" : %>
	    			액티비티 관리
	   			<% } %>
			</h3>
			<br>

			<div class="menu">
				<div class="search">
					<select name="searchFilter" id="searchFilter">
						<option value="default">검색 기준</option>
						<option value="NAME">이름</option>
						<option value="ADDRESS">주소</option>
						<option value="DESCRIPTION">소개문구</option>
					</select>
		
					<input type="search" name="keyword" placeholder="검색어 입력">
	
					<button onclick="searchKeyword();">검색</button>
				</div>
				
				<script>
					// 2022.2.1(화) 16h5
					function searchKeyword() {
						var searchFilter = $("#searchFilter option:selected").val();
						var keyword = $("input[name=keyword]").val();
						
						switch (searchFilter) {
						case "NAME" :
							location.href = '<%= contextPath %>/searchKeyword.ads?category=<%= category %>&searchFilter=' + searchFilter + '&keyword=' + keyword;
							break
						case "ADDRESS" :
							location.href = '<%= contextPath %>/searchKeyword.ads?category=<%= category %>&searchFilter=' + searchFilter + '&keyword=' + keyword;
							break
						case "DESCRIPTION" :
							location.href = '<%= contextPath %>/searchKeyword.ads?category=<%= category %>&searchFilter=' + searchFilter + '&keyword=' + keyword;
						}
					}
					
					$("#searchFilter option").each(function() {
						if ($(this).val() == "<%= (String)request.getAttribute("searchFilter") %>") {
							($(this).attr("selected", true));
						}
					})
				</script>

				<div class="buttons" align="right" style="width: 500px;">
					<a href="<%= contextPath %>/enrollForm.ads?category=<%= category %>" class="btn btn-sm btn-info">추가/등록</a>
					<input type="button" value="선택 삭제" class="btn btn-sm btn-warning" onclick="deleteCheckedTs();">					
				</div>
			</div>			
			<br><br><br>
			
			총 <b><%= pi.getListCount() %>개의 업체가 조회</b>되었습니다.<br>
			해당 업체의 행을 클릭하면 상세 정보 조회가 가능합니다.
			<!--2022.1.30(일) 2h-->
			<select name="boardLimit" id="boardLimit">
				<option value="x">(업체 개수 선택)개씩</option>
				<option value="5">5개씩</option>
				<option value="10">10개씩</option>
				<option value="20">20개씩</option>
				<option value="30">30개씩</option>
			</select> 조회합니다.
			
			<script>
				$(function() {
					$("#boardLimit").change(function() {
						var boardLimit = $("#boardLimit option:selected").val();
	
						switch (boardLimit) {
						case "5" :
							location.href = '<%= contextPath %>/tsList.ads?category=<%= category %>&currentPage=1&boardLimit=' + boardLimit;
							break
						case "10" :
							location.href = '<%= contextPath %>/tsList.ads?category=<%= category %>&currentPage=1&boardLimit=' + boardLimit;
							break
						case "20" :
							location.href = '<%= contextPath %>/tsList.ads?category=<%= category %>&currentPage=1&boardLimit=' + boardLimit;
							break
						case "30" :
							location.href = '<%= contextPath %>/tsList.ads?category=<%= category %>&currentPage=1&boardLimit=' + boardLimit;
						}
					})
				})
			</script>
			
			<br><br>
			<table class="list-area" border="1">
				<thead>
					<tr>
						<th width="50"><input type="checkbox" id="allCheck" name="allCheck"></th>
						<th width="100">번호</th>
						<th width="150">이름</th>
						<th width="300">주소</th>
						<th width="150">연락처</th>
						<th width="100">가격</th>
						<th width="100">
							<select name="area" id="area">
								<option value="default">선택</option>
								<option value="제주">제주시</option>
								<option value="서귀포">서귀포시</option>
								<option value="우도부근">우도 부근</option>
							</select>						
						</th>
						<th width="50">방문횟수</th>
						<% if (category.equals("ACTIVITY")) { %>
							<th width="150">준비물</th>
						<% } %>
					</tr>
				</thead>

				<!--2022.1.30(일) 3h15 이 때 구현 못 함 -> 2022.2.2(수) 10h40-->
				<script>
					$(function() {
						$("#area").change(function() {
							var area = $("#area option:selected").val();
		
							switch (area) {
							case "제주" :
								location.href = '<%= contextPath %>/searchKeyword.ads?category=<%= category %>&searchFilter=AREA&keyword=' + area;
								break
							case "서귀포" :
								location.href = '<%= contextPath %>/searchKeyword.ads?category=<%= category %>&searchFilter=AREA&keyword=' + area;
								break
							case "우도부근" :
								location.href = '<%= contextPath %>/searchKeyword.ads?category=<%= category %>&searchFilter=AREA&keyword=' + area;
							}
						})
					})
				</script>

				<tbody>
					<!--업체 목록 출력-->
					<!--2022.1.30(일) 0h55-->
					<% if (tsList.isEmpty()) { %>
						<tr>
							<td colspan="8">조회된 업체가 없습니다</td>
						</tr>
					<% } else { %>
						<% for (int i = 0; i < tsList.size(); i++) { %>
							<tr>
								<th><input type="checkbox" id="hL<%= i + 1 %>" name="rowCheck" class="tsListing" value="<%= tsList.get(i).getTsNo() %>"></th>
								<td class="tsNo"><%= tsList.get(i).getTsNo() %></td>
								<td><%= tsList.get(i).getName() %></td>
								<td><%= tsList.get(i).getAddress() %> </td>
								<td><%= tsList.get(i).getPhone() %></td>
								<td><%= tsList.get(i).getPrice() %></td>
								<td id="areaData"><%= tsList.get(i).getArea() %></td>
								<td><%= tsList.get(i).getVisitCount() %></td>
								<% if (category.equals("ACTIVITY")) { %>
									<td><%= ((Activity)(tsList.get(i))).getEquipment() %></td>
								<% } %>
							</tr>
						<% } %>
					<% } %>
				</tbody>
			</table>

			<script>
				$(function() {
					// 해당 업체의 행을 클릭하면 상세 정보 조회
					$('.list-area>tbody>tr>td').click(function() {
						var tsNo = $(this).prevAll().siblings('.tsNo').first().text();
						// console.log(tsNo);
						location.href = "<%= contextPath %>/detail.ads?tsNo=" + tsNo + "&category=<%= category %>&currentPage=1";
					})

					// '전체선택' 체크박스를 선택/해제 시, 전체 listings 선택/해제
					$('#allCheck').on('change', function() {
						var $all = $('#allCheck').prop('checked');
						
						console.log($all);

						if ($all) {
							$('.tsListing').prop('checked', true);
						}
						else {
							$('.tsListing').prop('checked', false);
						}
					})

					// listings 중 하나라도 체크되어있지 않으면, '전체선택' 체크박스 해제
					$('.tsListing').on('change', function() {
						if ($(this).prop('checked') == false) {
							$('#allCheck').prop('checked', false);
						}							
					})

					// 2022.2.2(수) 9h10
					// listings 전체가 체크되어 있으면, '전체선택' 체크박스 체크
					var rowCnt = $("input[name='rowCheck']").length;

					$("input[name='rowCheck']").click(function() {
						if($("input[name='rowCheck']:checked").length == rowCnt) {
							$("#allCheck").prop('checked', true);
						}
						else {
							$("#allCheck").prop('checked', false);
						}
					})
					
				})
				
				// 2022.2.2(수) 9h25
				// 체크된 체크박스 관련 업체 선택 삭제
				function deleteCheckedTs() {
					// var checkedTs = new Array(); // Eclipse에서 null로 인식됨 -> 나의 질문 = 왜일까? vs 일단 문자열로 넘기기로 함
					var checkedTs = "";
					var rowList = $("input[name='rowCheck']");
					
					for (var i = 0; i < rowList.length; i++) {
						if (rowList[i].checked) {
							// checkedTs.push(rowList[i].value); // push() 메소드는 원본 배열에 영향을 끼침
							checkedTs += rowList[i].value + ' ';
						}						
					}
					
					console.log(checkedTs); // 2022.2.2(수) 10h5 length 2인 배열 ['9', '8'] 찍힘

					if (checkedTs.length == 0) {
						window.alert("선택된 여행지가 없습니다");
					}
					else {
						var answer = window.confirm("정말로 삭제하시겠습니까?");
						
						if (answer) {
							$.ajax({
								url : "deleteCheckedTs.ads",
								data : {
									checkedTs : checkedTs,
									category : "<%= category %>"								
								},
								success : function(result) {
									console.log(result);
									
									if (result == "YY") {
										window.alert("선택한 여행지 삭제에 성공했습니다");
										location.href = '<%= contextPath %>/tsList.ads?category=<%= category %>&currentPage=1&boardLimit=7';
									}
									else {
										window.alert("선택한 여행지 삭제에 실패했습니다");
									}
								},
								error : function() {
									console.log("AJAX 통신 실패");
								},
								complete : function() {
									console.log("성공이든 실패든 출력");
								}
							})
						}
					} // else문 영역 끝
				} // deleteCheckedTs() 종료
				
				// 'jQuery 3 추가적인 선택자' 수업자료 참고
				// var hArr = document.getElementsByName('hobby'); // name 속성의 값으로 'hobby'를 가진 요소객체들이 배열에 담겨옴 -> 이 문서에서는 3개 요소가 있음
                // var checkedItem = '';
				// for (var i = 0; i < hArr.length; i++) {
				// 	if (hArr[i].checked) {
				// 		checkedItem += hArr[i].value + ' ';
				// 	}
				// }			
			</script>
			
			<!--2022.1.30(일) 1h15-->
			<!--paging bar-->
			<br>
			<div class="paging-area" align="center">
				<% if (currentPage != 1) { %>
					<button onclick="location.href='<%= contextPath %>/tsList.ads?category=<%= category %>&currentPage=<%= currentPage - 1 %>&boardLimit=<%= boardLimit %>'">&lt;</button>
				<% } %>
				
				<% for (int i = startPage; i <= endPage; i++) { %>
					<% if (i != currentPage) { %>
						<button onclick="location.href='<%= contextPath %>/tsList.ads?category=<%= category %>&currentPage=<%= i %>&boardLimit=<%= boardLimit %>'"><%= i %></button>
					<% } else { %>
						<button disabled><%= i %></button>
					<% } %>
				<% } %>
				
				<% if (currentPage != maxPage) { %>
					<button onclick="location.href='<%= contextPath %>/tsList.ads?category=<%= category %>&currentPage=<%= currentPage + 1 %>&boardLimit=<%= boardLimit %>'">&gt;</button>
				<% } %>
			</div>
			
		</div> <!--div class="wrap" 영역 끝-->
	</div> <!--div class="outer" 영역 끝-->

</body>
</html>