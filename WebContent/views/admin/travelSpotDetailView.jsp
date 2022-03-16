<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, semi.keyword.model.vo.TravelSpot, semi.keyword.model.vo.Activity, semi.admin.spot.model.vo.Attachment, semi.review.model.vo.Review, semi.common.model.vo.PageInfo" %>
<%
	TravelSpot ts = (TravelSpot)request.getAttribute("ts");
	String category = ts.getCategory(); // HOTEL 또는 LANDSCAPE 또는 ACTIVITY
	
	ArrayList<Attachment> fList = (ArrayList<Attachment>)request.getAttribute("fList");
	
	ArrayList<Review> rvList = (ArrayList<Review>)request.getAttribute("rvList");
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
<title>여행지 상세 조회</title>
<style>
	.outer {
        border: 1px solid mediumspringgreen;
        width: 1000px;
		height: 2000px;
        margin: auto;
        margin-top: 50px;
    }

    table {border: 1px solid mediumspringgreen;}
</style>
</head>
<body>
	<!--2022.1.31(월) 20h jsp 파일 생성 -> 23h5 내용 추가-->
	<%@ include file = "../common/menubar.jsp" %>

    <div class="outer">
        <h2>[관리자] 여행지 상세 조회</h2>
        <br>

        <table align="center" border="1">
            <tr>
                <th width="100">여행지 종류</th>
                <td width="500" colspan="3">
                    <% switch(category) { 
                    case "HOTEL" : %>
                            	숙소
                    <% break; 
                    case "LANDSCAPE" : %>
                          	  풍경명소
                    <% break;
                    case "ACTIVITY" : %>
                            	액티비티
                    <% } %>
                </td>
            </tr>
            <tr>
                <th>이름</th>
                <td colspan="3"><%= ts.getName() %></td>
            </tr>

            <tr>
                <th>주소</th>
                <td colspan="3"><%= ts.getAddress() %></td>
            </tr>

            <tr>
                <th>지역</th>
                <td colspan="3"><%= ts.getArea() %></td>
            </tr>

            <tr>
                <th>전화번호</th>
                <td colspan="3"><%= ts.getPhone() %></td>
            </tr>

            <tr>
                <th>소개 문구</th>
                <td colspan="3">
                    <p style="height: 50px;"><%= ts.getDescription() %></p>
                </td>
            </tr>

            <tr>
                <th>가격</th>
                <td colspan="3"><%= ts.getPrice() %>원</td>
            </tr>
            
            <tr>
                <th>방문횟수</th>
                <td colspan="3"><%= ts.getVisitCount() %></td>
            </tr>
            
            <% if (category.equals("ACTIVITY")) { %>
                <tr>
                    <th>준비물</th>
                    <td colspan="3">
                    	<% if (((Activity)ts).getEquipment() == null) { %>
                    		등록된 준비물 정보가 없습니다
                    	<% } else { %>
                    		<%= ((Activity)ts).getEquipment() %>
                    	<% } %>
                    </td>
                </tr>
            <% } %>

            <tr>
                <th>대표 이미지</th>
                <td colspan="3" align="center"><img src="<%= contextPath %><%= ts.getTitleImg() %>" width="500" height="300"></td>
            </tr>

            <tr>
                <th>상세 이미지</th>
                <% for (int i = 1; i < fList.size(); i++) { %>
                	<td><img src="<%= contextPath %><%= fList.get(i).getFilePath() %><%= fList.get(i).getChangeName() %>" width="160" height="120"></td>
                <% } %>
            </tr>
        </table>

        <br>
        
        <!--2022.2.1(화) 22h 상세 조회 페이지에서 해당 여행지 관련 리뷰 목록도 보여주고 싶어서 추가-->
        <div id="review-area" style="background-color: mintcream;">
            <h3>여행지 관련 리뷰</h3>
            
            <% if (!rvList.isEmpty()) { %>
            	총 <%= pi.getListCount() %>개의 리뷰가 조회되었습니다.
            <% } %>

            <table border="1" align="center">
                <thead>
                    <tr>
                        <th width="100">작성자</th>
                        <th width="300">리뷰 내용</th>
                        <th width="200">썸네일</th>
                        <th width="100">작성일</th>
                    </tr>
                </thead>

                <tbody>
                	<% if (rvList.isEmpty()) { %>
                		<tr>
                			<td colspan="4">조회된 리뷰가 없습니다</td>
                		</tr>
                	<% } else { %>
                		<% for (int i = 0; i < rvList.size(); i++) { %>
                			<tr>
                				<td>
		                            <%= rvList.get(i).getReviewWriterId() %><br>
		                            (<%= rvList.get(i).getReviewWriterName() %>)
		                        </td>
		                        
		                        <td><%= rvList.get(i).getReviewContent() %></td>
		                        
		                        <td>
		                        <% if (rvList.get(i).getTitleImg() == null) { %>
		                        	첨부 이미지 없음
		                        <% } else { %>
		                        	<img src="<%= contextPath %><%= rvList.get(i).getTitleImg() %>" width="190" height="140">
		                        <% } %>
		                        </td>
		                        
		                        <td><%= rvList.get(i).getCreateDate() %></td>
                			</tr>
                		<% } %>
                	<% } %>
                
                    <!-- <tr>
                        <td>
                            userId<br>
                            (userName)
                        </td>
                        <td>좋았어요</td>
                        <td><img src="" width="190" height="140"></td>
                        <td>2022-02-01</td>
                    </tr>                     -->
                </tbody>
            </table>
        </div> <!--'review-area' div 영역 끝-->
        
        <div class="paging-area" align="center">
			<% if (currentPage != 1) { %>
				<button onclick="location.href='<%= contextPath %>/detail.ads?category=<%= category %>&currentPage=<%= currentPage - 1 %>&boardLimit=<%= boardLimit %>'">&lt;</button>
			<% } %>
			
			<% for (int i = startPage; i <= endPage; i++) { %>
				<% if (i != currentPage) { %>
					<button onclick="location.href='<%= contextPath %>/tsList.ads?category=<%= category %>&currentPage=<%= i %>&boardLimit=<%= boardLimit %>'"><%= i %></button>
				<% } else { %>
					<button disabled><%= i %></button>
				<% } %>
			<% } %>
			
			<% if (currentPage != maxPage && maxPage != 0) { %>
				<button onclick="location.href='<%= contextPath %>/tsList.ads?category=<%= category %>&currentPage=<%= currentPage + 1 %>&boardLimit=<%= boardLimit %>'">&gt;</button>
			<% } %>
		</div>

        <br>
        <div align="center">
        	<a href="<%= contextPath %>/tsList.ads?category=<%= category %>&currentPage=1&boardLimit=7" class="btn btn-primary btn-sm">목록으로 돌아가기</a>
        	<a href="<%= contextPath %>/updateForm.ads?category=<%= category %>&tsNo=<%= ts.getTsNo() %>" class="btn btn-info btn-sm">정보 수정</a>
        	<button onclick="deleteTs();" class="btn btn-sm btn-warning">여행지 삭제</button>
        </div>

        <script>
	        function deleteTs() {
	            var answer = window.confirm("정말로 삭제하시겠습니까?");
	            console.log("삭제 의사 확인 : " + answer);
	
	            if (answer) {
	            	location.href = "<%= contextPath %>/deleteTs.ads?category=<%= category %>&tsNo=<%= ts.getTsNo() %>";
	            }
	        }
        </script>

    </div> <!--div class="outer" 영역 끝-->
	
</body>
</html>