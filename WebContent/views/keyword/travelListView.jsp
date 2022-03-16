<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, semi.keyword.model.vo.*, semi.review.model.vo.Review, semi.common.model.vo.PageInfo" %>
<%
	// 2022.1.21(금) 18h20
	TravelSpot ts = (TravelSpot)request.getAttribute("ts");
	ArrayList<Restaurant> rList = (ArrayList<Restaurant>)request.getAttribute("rList");	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>여행 추천 결과 페이지</title>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"> <!--2022.1.17(월) AJAX로  id 중복 확인 기능 구현 시, 이걸로 jQuery link 변경-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>

<!--2022.1.25(화) 17h20 kakao map 그리기 위한 JavaScript API + services 라이브러리 불러오기-->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b1970f057e838c2389da5b46c1b5d5ec&libraries=services,clusterer,drawing"></script>

<!--2022.1.20(목) 14h-->
<style>
    div {
        /* border: 1px solid green; */
        box-sizing: border-box; /* width, height 속성은 기본적으로 content 영역 지정 vs width, height가 border 영역까지 지정되도록 box-sizing 속성 border-box 지정*/
    }

    /* 전체를 감싸는 div 속성 부여*/
    .wrap {
        width: 1200px; /* box-sizing: border-box;로 인해 border 영역 포함하여 width 1000px, height 800px로 만듦*/
        /* height: 800px; */
        /* margin: auto;*/ /* margin = 바깥쪽(4방)에 여백 주기 -> 상/하는 auto 속성 안 먹음 vs 좌/우 margin auto로 주어(좌/우에서 각각 밂) 화면 가운데에 배치되도록 함*/
        margin-left: auto;
        margin-right: auto;
    }

    /* 큰 2등분 속성 부여 <- wrap 클래스의 바로 아래/자식 div 요소*/
    #left {
        width: 55%;
        height: 100%;
        float: left;  
    }

    #right {
        width: 45%;
        height: 100%;
        float: left;
    }

    /* 왼쪽 div(여행 정보 부분)*/
    #left>div {
        width: 100%;
    }

    #lefttitle {
        height: 10%;
    }

    #leftbody {
        width: 100%;
    }

    #leftbody>div {
        width: 100%;
        height: 100%;
    }

    .resulthead>div {
        width: 50%;
        height: 50px;
        float: left;
    }

    .food>div {
        width: 50%;
        height: 150px;
        float: left;
        background-color: rgb(238, 241, 227);
    }

    /* 오른쪽 div(여행 정보 부분)*/
    #map {
        width: 100%;
        height: 400px;
    }
    
    .modal-body>div {
    	width: 100%
    }
    
    .modal-body1>div {
    	width: 50%;
        height: 150px;
        float: left;
    }
    
    .modal-body2>div {
        float: left;
    }
    
    .modal-body2a {
    	width: 300px;
    }   
    
    .modal-body2b {
    	margin-left: 50px;
    	margin-top: 60px;
    }
    
    .modal-body3>div {
    	width: 230px;
    	height: 300px;
    	float: left;
    }
    
    /*===========================*/
</style>
</head>
<body style="background-color: skyblue">
	<%@ include file = "../common/menubar.jsp" %>
	
    <div class="wrap">
        <div id="left">
            <div id="lefttitle" style="background-color: skyblue;">
                <h2 align="center">이런 여행은 어때요?</h2>
            </div>

            <div id="leftbody">
                <div class="result">
                	<div class="resulthead">
                        <div style="background-color: rgb(238, 241, 227);">추천 여행지 #<%= ts.getCategory() %> #<%= ts.getArea() %></div> <!--호텔, 풍경 중 하나 보여줌-->
                        <div style="background-color: skyblue;"></div>
                    </div>

                    <div id="travelinfo" align="center" style="background-color: rgb(238, 241, 227);">
                        <br>
                        <h3><%= ts.getName() %></h3>
                        <h4><%= ts.getDescription() %></h4>
                        <img src="<%= contextPath %><%= ts.getTitleImg() %>" alt="추천 여행지 사진 1장" width="100%"><br>
				        <%= ts.getAddress() %><br>
                        <button type="button" class="btn btn-warning btn-sm" data-toggle="modal" data-target="#travelInfoDetail">상세 보기</button>
                    </div>
                </div>
                
                <br>
    
                <div class="result">
                    <div class="resulthead">
                        <div style="background-color: skyblue;"></div>
                        <div style="background-color: rgb(238, 241, 227);">추천 3끼 #<%= rList.get(0).getCategory() %> #<%= rList.get(0).getArea() %></div> <!--한식, 제주대표식, 양식 중 하나 보여줌-->                        
                    </div>
                    <div class="food">
                        <div><img src="<%= contextPath %><%= rList.get(0).getTitleImg() %>" alt="추천 식당1 사진" width="100%" height="100%"></div>
                        <div align="center">
                            <h3><%= rList.get(0).getRsName() %></h3>
                            <h4><%= rList.get(0).getDescription() %></h4>
                            <button type="button" class="btn btn-warning btn-sm" data-toggle="modal" data-target="#food1Detail">상세 보기</button>
                        </div>
                    </div>
                    <div class="food">                    
                        <div align="center">
                            <h3><%= rList.get(1).getRsName() %></h3>
                            <h4><%= rList.get(1).getDescription() %></h4>
                            <button type="button" class="btn btn-warning btn-sm" data-toggle="modal" data-target="#food2Detail">상세 보기</button>
                        </div>
                        <div><img src="<%= contextPath %><%= rList.get(1).getTitleImg() %>" alt="추천 식당2 사진" width="100%" height="100%"></div>
                    </div>
                    <div class="food">
                        <div><img src="<%= contextPath %><%= rList.get(2).getTitleImg() %>" alt="추천 식당3 사진" width="100%" height="100%"></div>
                        <div align="center">
                            <h3><%= rList.get(2).getRsName() %></h3>
                            <h4><%= rList.get(2).getDescription() %></h4>
                            <button type="button" class="btn btn-warning btn-sm" data-toggle="modal" data-target="#food3Detail">상세 보기</button>
                        </div>
                    </div>
                </div>

            </div>            

        </div>

        <div id="right">
            <br>
            <div id="map">지도</div> <!--각 추천 업체 정보 위에 마우스 올렸을 때 해당 주소를 지도에 표시?-->
            <div align="center">
                <br><br>
                <!--이 버튼을 클릭하면 사용자의 여행 이력에 위 업체들 정보를 INSERT하는 서블릿에 요청 보냄-->
                <!--2022.1.27(목) 1h50 나의 질문 = 이렇게 긴 query string 대신 Servlet으로 보내는 방법이 무엇이 있지? >.<-->
                <!--2022.1.27(목) 메인 ~ 로그인 소스코드 및 뷰 붙여서 실험 18h-->
                <button class="btn btn-sm btn-secondary" onclick="location.href='<%= contextPath %>/insertTravelHistory.me?userno=<%= loginUser.getUserNo() %>&traveltype1=<%= ts.getCategory() %>&travelspot1=<%= ts.getTsNo() %>&foodtype=<%= rList.get(0).getCategory() %>&restaurant1=<%= rList.get(0).getRsNo() %>&restaurant2=<%= rList.get(1).getRsNo() %>&restaurant3=<%= rList.get(2).getRsNo() %>'">이 여행을 갈래요</button>

                <!--이 버튼을 클릭하면 사용자의 여행 이력에 위 업체들 정보를 INSERT하는 서블릿에 요청 보냄-->
                <button class="btn btn-sm btn-secondary" onclick="location.reload();">다른 장소 추천해주세요</button> <!--reload(새로 고침; 이 페이지/html 문서를 다시 1번 읽음/읽어들임) = "location.href = location.href"(페이지 다시 읽음)-->
                <!--추후 추가 기능 = 이전 여행지 제외 + 금번에 랜덤 선택된 업체 번호들과 카테고리/선택받은 키워드들을 "/selectkeyword2.tr"과 비슷한 servlet으로 보내고, 'tsNo/rsNo != 그 번호들'인 행들 중 랜덤 추출
                	나의 생각 = user가 남긴 리뷰/별점으로 판단했을 때 user가 이전 여행지를 좋아한 경우에는 이전 여행지 포함 vs 안 좋아한 경우에는 이전 여행지 제외-->
                
                <!--이 버튼을 클릭하면 첫번째 키워드 선택 받는 jsp 페이지를 띄워주는 서블릿에 요청 보냄-->
                <button class="btn btn-sm btn-secondary" onclick="location.href='<%= contextPath %>/traveller1.st'">처음부터 다시 할래요</button> <!--"/traveller1.st" 서블릿 = keyword1EnrollFormController = "views/keyword/keyword1EnrollForm.jsp"만 띄워줌-->
            </div>
        </div>
    </div>
    
    <!--2022.1.25(화) 17h?-->
    <script>
    	// sample1 = 주소로 장소 표시
	    var mapContainer = document.getElementById('map'), // 지도를 표시할 div = 지도를 담을 영역의 DOM 레퍼런스 = id가 'map'인, 내가 이 문서에 만든 div
	    	mapOption = { // 지도를 생성할 때 필요한 기본 옵션
		        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표 -> 2022.1.27(목) 2h35 나의 질문 = 이게 뭐지?
		        level: 10 // 지도의 레벨(확대/축소 정도)
		    };

		var map = new kakao.maps.Map(mapContainer, mapOption); //지도 생성 및 객체 리턴
	
		// 주소-좌표 변환 객체를 생성
		var geocoder = new kakao.maps.services.Geocoder();

		// 주소로 좌표를 검색 -> '주소 값' = ts가 null이 아닌 경우 ts.getAddress() 또는 a가 null이 아닌 경우 a.getAddress() + 반복문(for) 돌려서 rList.get(0~2).getAddress() 주소 값 뽑기
		// 2022.1.27(목) 3h15 나의 궁금증 = info window 스타일 변경 방법? + 지도의 중심 바꾸는 방법? <- geocoder 객체의 메소드 실행 마지막에 map.setCenter 다 주석 처리했는데, 왜 결국 '식당3'이 보이지?
		// 2022.2.3(목) 13h45 db에서 받아온 객체로부터 뽑아온 주소를 마커로 표시하기
		geocoder.addressSearch('<%= ts.getAddress() %>', function(result, status) {
	    	if (status === kakao.maps.services.Status.OK) { // 정상적으로 검색이 완료됐으면 
		        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
		
		        // 결과값으로 받은 위치를 마커로 표시
		        var marker = new kakao.maps.Marker({
		            map: map,
		            position: coords
		        });
	
		        // 인포윈도우로 장소에 대한 설명을 표시
		        var infowindow = new kakao.maps.InfoWindow({
		            content: '<div style="width:150px;text-align:center;padding:6px 0;"><%= ts.getName() %></div>'
		        });
		        infowindow.open(map, marker);
		
		        // 지도의 중심을 결과값으로 받은 위치로 이동시킴
		        map.setCenter(coords);
	    	} 
		});
		
		<% for (int i = 0; i < rList.size(); i++) { %>
			geocoder.addressSearch('<%= rList.get(i).getAddress() %>', function(result, status) {
		    	if (status === kakao.maps.services.Status.OK) { // 정상적으로 검색이 완료됐으면 
			        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
			
			        // 결과값으로 받은 위치를 마커로 표시
			        var marker = new kakao.maps.Marker({
			            map: map,
			            position: coords
			        });
		
			        // 인포윈도우로 장소에 대한 설명을 표시
			        var infowindow = new kakao.maps.InfoWindow({
			            content: '<div style="width:150px;text-align:center;padding:6px 0;"><%= rList.get(i).getRsName() %></div>'
			        });
			        infowindow.open(map, marker);
			
			        // 지도의 중심을 결과값으로 받은 위치로 이동시킴
			        map.setCenter(coords);
		    	} 
			});
		<% } %>
    </script>
    
    <!--2022.1.23(일) 15h30-->
    <!--The Modal = 여행지 상세 보기-->
    <!--2022.1.26(수) 10h15 별도 jsp 문서로 분리 -> 2022.2.3(목) 15h15 분리된 문서 상 가독성 문제(Eclipse Java 코드/변수 빨간줄)로 인해 다시 가져옴-->
    <!-- <%@ include file="travelSpotDetailModal.jsp" %> -->
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
							<img src="<%= contextPath %><%= ts.getTitleImg() %>"
								alt="추천 여행지 사진 1장" width="100%" height="150px">
						</div>
						<div>
							<h3><%= ts.getName() %></h3>
							<h4><%= ts.getDescription() %></h4>
						</div>
					</div>

					<!--2022.1.28(금) 14h25 다형성 활용하여 db에서 여행지 추천 받아온 뒤, 내용 수정-->
					<div class="modal-body2">
						<div class="modal-body2a">
							주소 : <%= ts.getAddress() %><br>
							전화 : <%= ts.getPhone() %><br>
							가격 : <% if (ts.getPrice() == 0 && ts.getCategory().equals("HOTEL")) { %>
									가격 정보가 없습니다
								 <% } else if (ts.getPrice() == 0 && ts.getCategory().equals("LANDSCAPE") || ts.getCategory().equals("ACTIVITY")) { %>
									무료
								 <% } else { %>
									<%= ts.getPrice() %>원
								 <% } %>
							<% if (ts.getCategory().equals("ACTIVITY")) { %>
							<br>
							준비물 : <% if (((Activity)ts).getEquipment() != null) { %>
									<%= ((Activity)ts).getEquipment() %>
								  <% } else { %>
									준비물 정보가 없습니다
								  <% } %>
							<% } %>
						</div>
						<div class="modal-body2b" id="tsReviewList">
							<button type="button" class="btn btn-secondary btn-sm">리뷰 보러가기</button>
						</div>
					</div>

					<!--2022.1.23(일) 18h-->
					<div class="modal-body3">

					<script>
						$(function() {
							$(".modal-body3").hide();
							// setInterval(selectTsReviewList, 1000); // ms(밀리초) 단위는 알아서 붙여주는 바, ms 표기하면 syntax error 발생; 나의 질문 = 함수 호출 시 강사님께서 () 붙이셨는지 확인 필요
						})
                    
						// HOTEL, LANDSCAPE 여행지 '리뷰 보러가기' 버튼 관련
				    	$("#tsReviewList").click(function() {
				   			$(this).parent().next().show();
				   			$(this).hide();
				   			selectTsReviewList();				   			
				   		})
				   		
				   		// 2022.1.24(월) 11h
				   		function selectTsReviewList() {
	                		$.ajax({
								url : "tsListPaging.rv", // url 속성은 반드시 기재해야 함
								data : {
									tsNo : <%= ts.getTsNo() %>,
									category : "<%= ts.getCategory() %>",
									currentPage : 1
								},
								//type : "post",
								//dataType : "json",
								//contentType: "application/json; charset=UTF-8",
								success : function(jPi) {
									// console.log(jPi);
									
									$.ajax({
										url : "tsList.rv",
										data : {
											tsNo : <%= ts.getTsNo() %>,
											category : "<%= ts.getCategory() %>",
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
												
											$("#ts-review-content").html(review);
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
											paging += "<button onclick='location.href='<%= contextPath %>/tsList.rv?currentPage=" + (jPi.currentPage - 1) + "''>&lt;</button>";
										}

										for (var i = jPi.startPage; i <= jPi.endPage; i++) {
											if (i != jPi.currentPage) {
												paging += "<button onclick='location.href='<%= contextPath %>/tsList.rv?currentPage=" + i + "''>" + i + "</button>";
											} else {
												paging += "<button disabled>" + i + "</button>";											
											}
										}

										if (jPi.currentPage != jPi.maxPage && jPi.maxPage != 0) { // currentPage = 1, maxPage = 0(보여줄 리뷰 없음)인 경우, "&gt;" 1개 표시됨
											paging += "<button onclick='location.href='<%= contextPath %>/tsList.rv?currentPage=" + (jPi.currentPage + 1) + "''>&gt;</button>";
										}
										
										$("#ts-paging-area").html(paging);			
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
		                } // HOTEL, LANDSCAPE 여행지 '리뷰 보러가기' 버튼
						
				    </script>

						<!--2022.1.23(일) 20h-->
						<div class="review-area">
							<!--2022.1.24(월) 1h5-->
							<div id="ts-review-content"></div>
						</div> <!--class="review-area" 영역 끝-->
						<div class="paging-area" id="ts-paging-area" align="center"></div>
							
					</div> <!--div class="modal-body3" 영역 끝-->

				</div> <!--div class="modal-body" 영역 끝-->

				<!--Modal footer-->
				<div class="modal-footer">
					<button type="button" class="btn btn-primrary" data-dismiss="modal">OK</button>
				</div>
				
			</div>
		</div>
	</div>
    
    <!--2022.1.26(수) 15h15 별도 jsp 문서로 분리 -> 2022.2.3(목) 15h15 분리된 문서 상 가독성 문제(Eclipse Java 코드/변수 빨간줄)로 인해 다시 가져옴-->
    <!--The Modal = 식당1 상세 보기-->
	<!-- <%@ include file="foodDetailModal.jsp" %> -->
    <!--2022.1.26(수) 17h30 이 modal jsp를 아래 if~ else if문 아래에 놓으면, travelSpotDetailModal이 항상 실행/생성되어, food 상세 보기 modal 클릭 시 어두운 배경화면만 뜨는 오류 있었음
    	vs 강사님 조언 = div 잘 닫혔는지 확인해보기 + 다중 modal 검색해보기-->
    
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