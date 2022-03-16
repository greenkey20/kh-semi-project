<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>추천 식당 상세 보기 modal</title>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"> <!--2022.1.17(월) AJAX로  id 중복 확인 기능 구현 시, 이걸로 jQuery link 변경-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
	<!--2022.1.26(수) 15h15 별도 jsp 문서로 분리-->
	
	<script>
		console.log("추천 식당 상세 보기 modal 창 문서 들어옴");
	</script>
	
	<!--The Modal = 식당1 상세 보기-->
	<div class="modal" id="food1Detail">
        <div class="modal-dialog">
	        <div class="modal-content">
	    
	            <!--Modal Header-->
	            <div class="modal-header">
	                <h4 class="modal-title">추천 식당1 상세 보기</h4>
	            </div>
	    
	            <!--Modal body-->
	            <div class="modal-body">
	            	<div class="modal-body1">
	            		<div>
	            			<img src="<%= contextPath %><%= rList.get(0).getTitleImg() %>" alt="추천 식당1 사진 1장" width="100%" height="150px">
            			</div>
	            		<div>
	            			<h3><%= rList.get(0).getRsName() %></h3>
	                        <h4><%= rList.get(0).getDescription() %></h4>
	            		</div>
	            	</div>
	            	
	            	<div class="modal-body2">
	            		<div class="modal-body2a">
	            			주소 : <%= rList.get(0).getAddress() %><br>
	            			전화 : <%= rList.get(0).getPhone() %><br>
	            			대표 메뉴 : <%= rList.get(0).getMenu() %><br>
	            			가격 : <% if (rList.get(0).getPrice() == 0) { %>
	            					가격 정보가 없습니다	            					
	            				 <% } else { %>
	            				 	<%= rList.get(0).getPrice() %>원
	            				 <% } %>
	            		</div>
	            		<div class="modal-body2b" id="food1ReviewList">
	            			<button type="button" class="btn btn-secondary btn-sm">리뷰 보러가기</button>
            			</div>
	            	</div> 
	            	
	            	<!--2022.1.26(수) 15h30 추가-->
					<div class="modal-body3">
					
					<script>
						$(function() {
							$(".modal-body3").hide();
							// setInterval(selectTsReviewList, 1000); // ms(밀리초) 단위는 알아서 붙여주는 바, ms 표기하면 syntax error 발생; 나의 질문 = 함수 호출 시 강사님께서 () 붙이셨는지 확인 필요
						})
                    
						// 식당1 '리뷰 보러가기' 버튼 관련
				    	$("#food1ReviewList").click(function() {
				   			$(this).parent().next().show();
				   			$(this).hide();
				   			selectFood1ReviewList();				   			
				   		})
				   		
				   		// 2022.1.24(월) 11h
				   		function selectFood1ReviewList() {
	                		$.ajax({
								url : "foodListPaging.rv", // url 속성은 반드시 기재해야 함
								data : {
									rsNo : <%= rList.get(0).getRsNo() %>,
									category : "<%= rList.get(0).getCategory() %>",
									currentPage : 1
								},
								success : function(jPi) {
									// console.log(jPi);
									
									$.ajax({
										url : "foodList.rv",
										data : {
											rsNo : <%= rList.get(0).getRsNo() %>,
											category : "<%= rList.get(0).getCategory() %>",
											currentPage : 1
										},
										success : function(jRvList) {
											// 리뷰 개수만큼 반복 -> 문자열로 누적 -> html 속성 값(관련 div의 content 부분 내용물)으로 넣어줌
											console.log(jRvList);
											
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
												
											$("#food1-review-content").html(review);
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
											paging += "<button onclick='location.href='<%= contextPath %>/foodList.rv?currentPage=" + (jPi.currentPage - 1) + "''>&lt;</button>";
										}

										for (var i = jPi.startPage; i <= jPi.endPage; i++) {
											if (i != jPi.currentPage) {
												paging += "<button onclick='location.href='<%= contextPath %>/foodList.rv?currentPage=" + i + "''>" + i + "</button>";
											} else {
												paging += "<button disabled>" + i + "</button>";											
											}
										}

										if (jPi.currentPage != jPi.maxPage && jPi.maxPage != 0) { // currentPage = 1, maxPage = 0(보여줄 리뷰 없음)인 경우, "&gt;" 1개 표시됨
											paging += "<button onclick='location.href='<%= contextPath %>/foodList.rv?currentPage=" + (jPi.currentPage + 1) + "''>&gt;</button>";
										}
										
										$("#food1-paging-area").html(paging);			
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
		                } // 식당1 '리뷰 보러가기' 버튼
					</script>
                	
						<div class="review-area">
							<div id="food1-review-content"></div>
						</div> 
						<div class="paging-area" id="food1-paging-area" align="center"></div>
						
					</div> <!--div class="modal-body3" 영역 끝-->
	            	
	            </div> <!--div class="modal-body" 영역 끝-->
	    
	            <!--Modal footer-->
	            <div class="modal-footer">
	                <button type="button" class="btn btn-primrary" data-dismiss="modal">OK</button>
	            </div>
	             
	        </div> <!--div class="modal-content" 영역 끝-->
        </div> <!--div class="modal-dialog" 영역 끝-->
    </div> <!--div class="modal" id="food1Detail" 영역 끝-->
    
    <!--==============================================-->
    <!--The Modal = 식당2 상세 보기-->
    <div class="modal" id="food2Detail">
        <div class="modal-dialog">
	        <div class="modal-content">
	    
	            <!--Modal Header-->
	            <div class="modal-header">
	                <h4 class="modal-title">추천 식당2 상세 보기</h4>
	            </div>
	    
	            <!--Modal body-->
	            <div class="modal-body">
	            	<div class="modal-body1">
	            		<div><img src="<%= contextPath %><%= rList.get(1).getTitleImg() %>" alt="추천 식당2 사진 1장" width="100%" height="150px"></div>
	            		<div>
	            			<h3><%= rList.get(1).getRsName() %></h3>
	                        <h4><%= rList.get(1).getDescription() %></h4>
	            		</div>
	            	</div>
	            	
	            	<div class="modal-body2">
	            		<div class="modal-body2a">
	            			주소 : <%= rList.get(1).getAddress() %><br>
	            			전화 : <%= rList.get(1).getPhone() %><br>
	            			대표 메뉴 : <%= rList.get(1).getMenu() %><br>
	            			가격 : <% if (rList.get(1).getPrice() == 0) { %>
	            					가격 정보가 없습니다	            					
	            				 <% } else { %>
	            				 	<%= rList.get(1).getPrice() %>원
	            				 <% } %>
	            		</div>
	            		<div class="modal-body2b" id="food2ReviewList">
	            			<button type="button" class="btn btn-secondary btn-sm reviewList">리뷰 보러가기</button>
            			</div>
	            	</div> 
	            	
	            	<!--2022.1.26(수) 20h10 추가-->
					<div class="modal-body3">
					
					<script>
						$(function() {
							$(".modal-body3").hide();
							// setInterval(selectTsReviewList, 1000); // ms(밀리초) 단위는 알아서 붙여주는 바, ms 표기하면 syntax error 발생; 나의 질문 = 함수 호출 시 강사님께서 () 붙이셨는지 확인 필요
						})
                    
						// 식당2 '리뷰 보러가기' 버튼 관련
				    	$("#food2ReviewList").click(function() {
				   			$(this).parent().next().show();
				   			$(this).hide();
				   			selectFood2ReviewList();				   			
				   		})
				   		
				   		// 2022.1.24(월) 11h
				   		function selectFood2ReviewList() {
	                		$.ajax({
								url : "foodListPaging.rv", // url 속성은 반드시 기재해야 함
								data : {
									rsNo : <%= rList.get(1).getRsNo() %>,
									category : "<%= rList.get(1).getCategory() %>",
									currentPage : 1
								},
								success : function(jPi) {
									// console.log(jPi);
									
									$.ajax({
										url : "foodList.rv",
										data : {
											rsNo : <%= rList.get(1).getRsNo() %>,
											category : "<%= rList.get(1).getCategory() %>",
											currentPage : 1
										},
										success : function(jRvList) {
											// 리뷰 개수만큼 반복 -> 문자열로 누적 -> html 속성 값(관련 div의 content 부분 내용물)으로 넣어줌
											console.log(jRvList);
											
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
												
											$("#food2-review-content").html(review);
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
											paging += "<button onclick='location.href='<%= contextPath %>/foodList.rv?currentPage=" + (jPi.currentPage - 1) + "''>&lt;</button>";
										}

										for (var i = jPi.startPage; i <= jPi.endPage; i++) {
											if (i != jPi.currentPage) {
												paging += "<button onclick='location.href='<%= contextPath %>/foodList.rv?currentPage=" + i + "''>" + i + "</button>";
											} else {
												paging += "<button disabled>" + i + "</button>";											
											}
										}

										if (jPi.currentPage != jPi.maxPage && jPi.maxPage != 0) { // currentPage = 1, maxPage = 0(보여줄 리뷰 없음)인 경우, "&gt;" 1개 표시됨
											paging += "<button onclick='location.href='<%= contextPath %>/foodList.rv?currentPage=" + (jPi.currentPage + 1) + "''>&gt;</button>";
										}
										
										$("#food2-paging-area").html(paging);			
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
		                } // 식당2 '리뷰 보러가기' 버튼
					</script>
                	
						<div class="review-area">
							<div id="food2-review-content"></div>
						</div> 
						<div class="paging-area" id="food2-paging-area" align="center"></div>
						
					</div> <!--div class="modal-body3" 영역 끝-->
	            	
	            </div>
	    
	            <!--Modal footer-->
	            <div class="modal-footer">
	                <button type="button" class="btn btn-primrary" data-dismiss="modal">OK</button>
	            </div> 
              
        	</div> <!--div class="modal-content" 영역 끝-->
        </div> <!--div class="modal-dialog" 영역 끝-->
    </div> <!--div class="modal" id="food2Detail" 영역 끝-->
    
    <!--=========================================-->
    <!--The Modal = 식당3 상세 보기-->
    <div class="modal" id="food3Detail">
        <div class="modal-dialog">
	        <div class="modal-content">
	    
	            <!--Modal Header-->
	            <div class="modal-header">
	                <h4 class="modal-title">추천 식당3 상세 보기</h4>
	            </div>
	    
	            <!--Modal body-->
	            <div class="modal-body">
	            	<div class="modal-body1">
	            		<div><img src="<%= contextPath %><%= rList.get(2).getTitleImg() %>" alt="추천 식당3 사진 1장" width="100%" height="150px"></div>
	            		<div>
	            			<h3><%= rList.get(2).getRsName() %></h3>
	                        <h4><%= rList.get(2).getDescription() %></h4>
	            		</div>
	            	</div>
	            	
	            	<div class="modal-body2">
	            		<div class="modal-body2a">
	            			주소 : <%= rList.get(2).getAddress() %><br>
	            			전화 : <%= rList.get(2).getPhone() %><br>
	            			대표 메뉴 : <%= rList.get(2).getMenu() %><br>
	            			가격 : <% if (rList.get(2).getPrice() == 0) { %>
	            					가격 정보가 없습니다	
	            				 <% } else { %>
	            				 	<%= rList.get(2).getPrice() %>원
	            				 <% } %>
	            		</div>
	            		<div class="modal-body2b" id="food3ReviewList">
	            			<button type="button" class="btn btn-secondary btn-sm reviewList">리뷰 보러가기</button>
            			</div>
	            	</div> 
	            	
	            	<!--2022.1.26(수) 20h15 추가-->
					<div class="modal-body3">
					
					<script>
						$(function() {
							$(".modal-body3").hide();
							// setInterval(selectTsReviewList, 1000); // ms(밀리초) 단위는 알아서 붙여주는 바, ms 표기하면 syntax error 발생; 나의 질문 = 함수 호출 시 강사님께서 () 붙이셨는지 확인 필요
						})
                    
						// 식당3 '리뷰 보러가기' 버튼 관련
				    	$("#food3ReviewList").click(function() {
				   			$(this).parent().next().show();
				   			$(this).hide();
				   			selectFood3ReviewList();				   			
				   		})
				   		
				   		function selectFood3ReviewList() {
	                		$.ajax({
								url : "foodListPaging.rv", // url 속성은 반드시 기재해야 함
								data : {
									rsNo : <%= rList.get(2).getRsNo() %>,
									category : "<%= rList.get(2).getCategory() %>",
									currentPage : 1
								},
								success : function(jPi) {
									// console.log(jPi);
									
									$.ajax({
										url : "foodList.rv",
										data : {
											rsNo : <%= rList.get(2).getRsNo() %>,
											category : "<%= rList.get(2).getCategory() %>",
											currentPage : 1
										},
										success : function(jRvList) {
											// 리뷰 개수만큼 반복 -> 문자열로 누적 -> html 속성 값(관련 div의 content 부분 내용물)으로 넣어줌
											console.log(jRvList);
											
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
												
											$("#food3-review-content").html(review);
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
											paging += "<button onclick='location.href='<%= contextPath %>/foodList.rv?currentPage=" + (jPi.currentPage - 1) + "''>&lt;</button>";
										}

										for (var i = jPi.startPage; i <= jPi.endPage; i++) {
											if (i != jPi.currentPage) {
												paging += "<button onclick='location.href='<%= contextPath %>/foodList.rv?currentPage=" + i + "''>" + i + "</button>";
											} else {
												paging += "<button disabled>" + i + "</button>";											
											}
										}

										if (jPi.currentPage != jPi.maxPage && jPi.maxPage != 0) { // currentPage = 1, maxPage = 0(보여줄 리뷰 없음)인 경우, "&gt;" 1개 표시됨
											paging += "<button onclick='location.href='<%= contextPath %>/foodList.rv?currentPage=" + (jPi.currentPage + 1) + "''>&gt;</button>";
										}
										
										$("#food3-paging-area").html(paging);			
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
		                } // 식당3 '리뷰 보러가기' 버튼
					</script>
                	
						<div class="review-area">
							<div id="food3-review-content"></div>
						</div> 
						<div class="paging-area" id="food3-paging-area" align="center"></div>
						
					</div> <!--div class="modal-body3" 영역 끝-->
	            	
	            </div>
	    
	            <!--Modal footer-->
	            <div class="modal-footer">
	                <button type="button" class="btn btn-primrary" data-dismiss="modal">OK</button>
	            </div>   
	            
	        </div> <!--div class="modal-content" 영역 끝-->
        </div> <!--div class="modal-dialog" 영역 끝-->
    </div> <!--div class="modal" id="food3Detail" 영역 끝-->

</body>
</html>