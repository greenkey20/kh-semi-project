<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, semi.keyword.model.vo.Restaurant, semi.common.model.vo.PageInfo" %>
<% 
	// 2022.2.2(수) 16h30
	ArrayList<Restaurant> rList = (ArrayList<Restaurant>)request.getAttribute("rList");
	String category = "";
	if (!rList.isEmpty()) {
		category = rList.get(0).getCategory(); // KOREAN 또는 JEJU 또는 WESTERN
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
<title>식당(한/제주별미/양식당) 관리</title>
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

			<h2>[관리자] 식당 관리</h2>
			<br>
			
			<select name="categoryR" id="categoryR">
				<option value="default" selected>식당 분류를 선택해주세요</option>
				<option value="KOREAN">한식당</option>
				<option value="JEJU">제주별미식당</option>
				<option value="WESTERN">양식당</option>
			</select>

			<script>
				$(function() {
					$("#categoryR option").each(function() {
						if ($(this).val() == "<%= category %>") {
							($(this).attr("selected", true));
						}
					})
					
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
				
		<div class="wrap">
			<hr>
			<h3>
				<% switch(category) { 
	        	   case "KOREAN" : %>
	       			한식당 관리
	       		   <% break; 
	       		   case "JEJU" : %>
	       			제주별미식당 관리
	       			<% break;
	    		   case "WESTERN" : %>
	    			양식당 관리
	   			<% } %>
			</h3>
			<br>

			<div class="menu">
				<div class="search">
					<select name="searchFilter" id="searchFilter">
						<option value="default">검색 기준</option>
						<option value="RS_NAME">이름</option>
						<option value="ADDRESS">주소</option>
						<option value="DESCRIPTION">소개문구</option>
						<option value="MENU">메뉴</option>
					</select>
		
					<input type="search" name="keyword" placeholder="검색어 입력">
	
					<button onclick="searchKeyword();">검색</button>
				</div>
				
				<script>
					function searchKeyword() {
						var searchFilter = $("#searchFilter option:selected").val();
						var keyword = $("input[name=keyword]").val();
						
						switch (searchFilter) {
						case "RS_NAME" :
							location.href = '<%= contextPath %>/searchRsKeyword.ads?category=<%= category %>&searchFilter=' + searchFilter + '&keyword=' + keyword;
							break
						case "ADDRESS" :
							location.href = '<%= contextPath %>/searchRsKeyword.ads?category=<%= category %>&searchFilter=' + searchFilter + '&keyword=' + keyword;
							break
						case "DESCRIPTION" :
							location.href = '<%= contextPath %>/searchRsKeyword.ads?category=<%= category %>&searchFilter=' + searchFilter + '&keyword=' + keyword;
							break
						case "MENU" :
							location.href = '<%= contextPath %>/searchRsKeyword.ads?category=<%= category %>&searchFilter=' + searchFilter + '&keyword=' + keyword;
						}
					}
					
					$("#searchFilter option").each(function() {
						if ($(this).val() == "<%= (String)request.getAttribute("searchFilter") %>") {
							($(this).attr("selected", true));
						}
					})
				</script>

				<div class="buttons" align="right" style="width: 500px;">
					<a href="<%= contextPath %>/enrollRsForm.ads?category=<%= category %>" class="btn btn-sm btn-info">추가/등록</a>
					<input type="button" value="선택 삭제" class="btn btn-sm btn-warning" onclick="deleteCheckedRs();">					
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
							location.href = '<%= contextPath %>/rList.ads?categoryR=<%= category %>&currentPage=1&boardLimit=' + boardLimit;
							break
						case "10" :
							location.href = '<%= contextPath %>/rList.ads?categoryR=<%= category %>&currentPage=1&boardLimit=' + boardLimit;
							break
						case "20" :
							location.href = '<%= contextPath %>/rList.ads?categoryR=<%= category %>&currentPage=1&boardLimit=' + boardLimit;
							break
						case "30" :
							location.href = '<%= contextPath %>/rList.ads?categoryR=<%= category %>&currentPage=1&boardLimit=' + boardLimit;
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
						<th width="150">메뉴</th>
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
					</tr>
				</thead>

				<script>
					$(function() {
						$("#area").change(function() {
							var area = $("#area option:selected").val();
		
							switch (area) {
							case "제주" :
								location.href = '<%= contextPath %>/searchRsKeyword.ads?category=<%= category %>&searchFilter=AREA&keyword=' + area;
								break
							case "서귀포" :
								location.href = '<%= contextPath %>/searchRsKeyword.ads?category=<%= category %>&searchFilter=AREA&keyword=' + area;
								break
							case "우도부근" :
								location.href = '<%= contextPath %>/searchRsKeyword.ads?category=<%= category %>&searchFilter=AREA&keyword=' + area;
							}
						})
					})
				</script>

				<tbody>
					<!--업체 목록 출력-->
					<% if (rList.isEmpty()) { %>
						<tr>
							<td colspan="9">조회된 업체가 없습니다</td>
						</tr>
					<% } else { %>
						<% for (int i = 0; i < rList.size(); i++) { %>
							<tr>
								<th><input type="checkbox" id="hL<%= i + 1 %>" name="rowCheck" class="tsListing" value="<%= rList.get(i).getRsNo() %>"></th>
								<td class="rsNo"><%= rList.get(i).getRsNo() %></td>
								<td><%= rList.get(i).getRsName() %></td>
								<td><%= rList.get(i).getAddress() %> </td>
								<td><%= rList.get(i).getPhone() %></td>
								<td><%= rList.get(i).getMenu() %></td>
								<td><%= rList.get(i).getPrice() %></td>
								<td id="areaData"><%= rList.get(i).getArea() %></td>
								<td><%= rList.get(i).getVisitCount() %></td>
							</tr>
						<% } %>
					<% } %>
				</tbody>
			</table>

			<script>
				$(function() {
					// 해당 업체의 행을 클릭하면 상세 정보 조회
					$('.list-area>tbody>tr>td').click(function() {
						var rsNo = $(this).prevAll().siblings('.rsNo').first().text();
						location.href = "<%= contextPath %>/detailRs.ads?rsNo=" + rsNo + "&category=<%= category %>&currentPage=1";
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
				
				// 체크된 체크박스 관련 업체 선택 삭제
				function deleteCheckedRs() {
					var checkedRs = "";
					var rowList = $("input[name='rowCheck']");
					
					for (var i = 0; i < rowList.length; i++) {
						if (rowList[i].checked) {
							checkedRs += rowList[i].value + ' ';
						}						
					}

					if (checkedRs.length == 0) {
						window.alert("선택된 여행지가 없습니다");
					}
					else {
						var answer = window.confirm("정말로 삭제하시겠습니까?");
						
						if (answer) {
							$.ajax({
								url : "deleteCheckedRs.ads",
								data : {
									checkedRs : checkedRs,
									category : "<%= category %>"								
								},
								success : function(result) {
									console.log(result);
									
									if (result == "YY") {
										window.alert("선택한 식당 삭제에 성공했습니다");
										location.href = '<%= contextPath %>/rList.ads?categoryR=<%= category %>&currentPage=1&boardLimit=7';
									}
									else {
										window.alert("선택한 식당 삭제에 실패했습니다");
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
				
			</script>
			
			<!--paging bar-->
			<br>
			<div class="paging-area" align="center">
				<% if (currentPage != 1) { %>
					<button onclick="location.href='<%= contextPath %>/rList.ads?categoryR=<%= category %>&currentPage=<%= currentPage - 1 %>&boardLimit=<%= boardLimit %>'">&lt;</button>
				<% } %>
				
				<% for (int i = startPage; i <= endPage; i++) { %>
					<% if (i != currentPage) { %>
						<button onclick="location.href='<%= contextPath %>/rList.ads?categoryR=<%= category %>&currentPage=<%= i %>&boardLimit=<%= boardLimit %>'"><%= i %></button>
					<% } else { %>
						<button disabled><%= i %></button>
					<% } %>
				<% } %>
				
				<% if (currentPage != maxPage) { %>
					<button onclick="location.href='<%= contextPath %>/rList.ads?categoryR=<%= category %>&currentPage=<%= currentPage + 1 %>&boardLimit=<%= boardLimit %>'">&gt;</button>
				<% } %>
			</div>
			
		</div> <!--div class="wrap" 영역 끝-->
	</div> <!--div class="outer" 영역 끝-->

</body>
</html>