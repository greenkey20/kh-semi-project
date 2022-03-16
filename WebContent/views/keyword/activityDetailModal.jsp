<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>추천 여행지(액티비티) 상세 보기 modal</title>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"> <!--2022.1.17(월) AJAX로  id 중복 확인 기능 구현 시, 이걸로 jQuery link 변경-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
	<!--2022.1.26(수) 10h15 별도 jsp 문서로 분리 -> 12h30 hotel/landscape 추천하는 경우와 activity 추천하는 경우를 1개 문서에 작성하려고 했더니, 하나의 db 조회 결과 값(ts 또는 a)이 null인 경우에도 문서를 읽으려고 하는 바, null pointer exception이 발생하는 것 같아서, 각각의 경우 문서 구분함-->

	<!--2022.1.23(일) 15h30-->
	<!--The Modal = 여행지 상세 보기-->
	<div class="modal" id="travelInfoDetail">
		<div class="modal-dialog">
			<div class="modal-content">

				<!--Modal Header-->
				<div class="modal-header">
					<h4 class="modal-title">추천 여행지 상세 보기</h4>
				</div>

				<!--Modal body-->
				<div class="modal-body">
					<div class="modal-body1">
						<div>
							<img src="<%= contextPath %><%= a.getTitleImg() %>"
								alt="추천 여행지 사진 1장" width="100%" height="150px">
						</div>
						<div>
							<h3><%= a.getName() %></h3>
							<h4><%= a.getDescription() %></h4>
						</div>
					</div>

					<div class="modal-body2">
						<div class="modal-body2a">
							주소 :
							<%= a.getAddress() %><br> 전화 :
							<%= a.getPhone() %><br> 가격 :
							<% if (a.getPrice() != 0) { %>
							<%= a.getPrice() %>원<br>
							<% } else { %>
							가격 정보가 없습니다<br>
							<% } %>
							준비물 :
							<% if (a.getEquipment() != null) { %>
							<%= a.getEquipment() %>
							<% } else { %>
							준비물 정보가 없습니다
							<% } %>
						</div>

						<div class="modal-body2b" id="aReviewList">
							<button type="button" class="btn btn-secondary btn-sm">리뷰 보러가기</button>
						</div>
					</div>

					<!--2022.1.23(일) 18h-->
					<div class="modal-body3">

						<script>
		                // selectReplyList() 함수를 누군가가 호출해야 댓글 목록이 보임 + 댓글은 화면이 로딩되었을 때 곧바로 뿌려줘야 함 <- window.onload = $(function() {})
						$(function() {
							$(".modal-body3").hide();
							// setInterval(selectTsReviewList, 1000); // ms(밀리초) 단위는 알아서 붙여주는 바, ms 표기하면 syntax error 발생; 나의 질문 = 함수 호출 시 강사님께서 () 붙이셨는지 확인 필요
						})
                    
						// ACTIVITY 여행지 '리뷰 보러가기' 버튼 관련
				    	$("#aReviewList").click(function() {
				   			$(this).parent().next().show();
				   			$(this).hide();
				   			selectAReviewList();				   			
				   		})
				   		
				   		// 2022.1.26(수) 10h30
				   		function selectAReviewList() {
							$.ajax({
								url : "aListPaging.rv", // url 속성은 반드시 기재해야 함
								data : {
									tsNo : <%= a.getTsNo() %>,
									currentPage : 1
								},
								success : function(jPi) {
									// console.log(jPi);
									
									$.ajax({
										url : "aList.rv",
										data : {
											tsNo : <%= a.getTsNo() %>,
											currentPage : 1
										},
										success : function(jRvList) {
											// 리뷰 개수만큼 반복 -> 문자열로 누적 -> html 속성 값(관련 div의 content 부분 내용물)으로 넣어줌
											var review = "";
											
											if (jRvList.length === 0) {
												review += "<p>작성된 리뷰가 아직 없습니다</p>"
											} else {
												for (var i in jRvList) { // for in문
													review += "<img src='<%= contextPath %>" + jRvList[i].titleImg + "' width='200px' height='150px' style='margin-top: 5px;'>"
														  + "<p>"
														  + "작성자 : " + jRvList[i].reviewWriterId + " (" + jRvList[i].reviewWriterName + ") <br>"
														  + "작성일 : " + jRvList[i].createDate + "<br>"
														  + jRvList[i].reviewContent
														  + "</p>"
												}
											}
												
											$("#a-review-content").html(review);
										},
										error : function(request, error) {
											console.log("db로부터 리뷰 목록 읽어오기 실패");
											console.log("message"+request.responseText);
											console.log(error);
											
										},
										complete : function(jRvList) {
											console.log("성공이든 실패든 출력");
											console.log(jRvList);
										}
									})

										var paging = "";

										if (jPi.currentPage != 1) {
											paging += "<button onclick='location.href='<%= contextPath %>/aList.rv?currentPage=" + (jPi.currentPage - 1) + "''>&lt;</button>";
										}

										for (var i = jPi.startPage; i <= jPi.endPage; i++) {
											if (i != jPi.currentPage) {
												paging += "<button onclick='location.href='<%= contextPath %>/aList.rv?currentPage=" + i + "''>" + i + "</button>";
											} else {
												paging += "<button disabled>" + i + "</button>";											
											}
										}

										if (jPi.currentPage != jPi.maxPage && jPi.maxPage != 0) { // currentPage = 1, maxPage = 0(보여줄 리뷰 없음)인 경우, "&gt;" 1개 표시됨
											paging += "<button onclick='location.href='<%= contextPath %>/aList.rv?currentPage=" + (jPi.currentPage + 1) + "''>&gt;</button>";
										}
										
										$("#a-paging-area").html(paging);			
								},
								error : function(request, error) {
									console.log("db로부터 페이징 정보 읽어오기 실패");
									console.log("message"+request.responseText);
									console.log(error);
									
								},
								complete : function(jPi) {
									console.log("성공이든 실패든 출력");
									console.log(jPi);
								}
							})
							
						} // ACTIVITY 여행지 '리뷰 보러가기' 버튼 
						
				    </script>

					<div>
						<div class="review-area">
							<div id="a-review-content"></div>
						</div>
					</div>
					<div class="paging-area" id="a-paging-area" align="center">
						
				</div> <!--div class="modal-body3" 영역 끝-->

				<!--Modal footer-->
				<div class="modal-footer">
					<button type="button" class="btn btn-primrary" data-dismiss="modal">OK</button>
				</div>
				
			</div>
		</div>
	</div>

</body>
</html>