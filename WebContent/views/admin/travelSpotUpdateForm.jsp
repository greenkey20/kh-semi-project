<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, semi.keyword.model.vo.TravelSpot, semi.keyword.model.vo.Activity, semi.admin.spot.model.vo.Attachment" %>
<%
	TravelSpot ts = (TravelSpot)request.getAttribute("ts");
	ArrayList<Attachment> fList = (ArrayList<Attachment>)request.getAttribute("fList");
	
	String category = ts.getCategory(); // KOREAN 또는 JEJU 또는 WESTERN
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>여행지 정보 수정</title>
<style>
	.outer {
        border: 1px solid mediumspringgreen;
        width: 1000px;
		height: 850px;
        margin: auto;
        margin-top: 50px;
    }

    #update-form>table {border: 1px solid mediumspringgreen;}
    
    #update-form input, #update-form textarea {
        width: 100%;
        box-sizing: border-box;
    }
</style>
</head>
<body>
	<!--2022.2.1(화) 0h40-->
	<%@ include file = "../common/menubar.jsp" %>

    <div class="outer">
        <h2>[관리자] 여행지 정보 수정</h2>
        <br>

        <form id="update-form" action="<%= contextPath %>/updateTravelSpot.ads" method="post" enctype="multipart/form-data">
        	
        	<input type="hidden" name="tsNo" value="<%= ts.getTsNo() %>">
        	<input type="hidden" name="category" value="<%= category %>"> <!--HOTEL 또는 LANDSCAPE 또는 ACTIVITY-->

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
                    <td colspan="3"><input type="text" name="name" value="<%= ts.getName() %>" required></td>
                </tr>
    
                <tr>
                    <th>주소</th>
                    <td colspan="3"><input type="text" name="address" value="<%= ts.getAddress() %>" required></td>
                </tr>
    
                <tr>
                    <th>지역</th>
                    <td colspan="3">
                        <select name="area" id="area">
                            <option value="제주시">제주시</option>
                            <option value="서귀포시">서귀포시</option>
                            <option value="우도부근">우도 부근</option>
                        </select>
                    </td>
                    
                    <script>
                    	$(function() {
                            $("#area option").each(function() {
                                if ($(this).val() == "<%= ts.getArea() %>") {
                                    ($(this).attr("selected", true));
                                }
                            })
                        })
                    </script>                    
                </tr>
    
                <tr>
                    <th>전화번호</th>
                    <td colspan="3"><input type="text" name="phone" value="<%= ts.getPhone() %>" placeholder="xxx-xxxx-xxxx 형식 입력" required></td>
                </tr>
    
                <tr>
                    <th>소개 문구</th>
                    <td colspan="3">
                        <textarea name="description" rows="5" style="resize: none;" placeholder="1000byte 입력 가능" required><%= ts.getDescription() %></textarea>
                    </td>
                </tr>
    
                <tr>
                    <th>가격</th>
                    <td colspan="3"><input type="number" name="price" value="<%= ts.getPrice() %>"></td>
                </tr>
                
                <% if (category.equals("ACTIVITY")) { %>
					<tr>
	                    <th>준비물</th>
	                    <td colspan="3"><input type="text" name="equipment" value="<%= ((Activity)ts).getEquipment() %>"></td>
	                </tr>
				<% } %>
    
                <tr>
                    <th>대표 이미지</th>
                    <td colspan="3" align="center">
                    	<% if (!fList.isEmpty()) { %>
                    		<%= fList.get(0).getOriginName() %> <!--기존 대표 이미지의 원본 파일명을 보여줌-->
	                    	<input type="hidden" name="originFileNo1" value="<%= fList.get(0).getFileNo() %>">
	                        <input type="hidden" name="originFileName1" value="<%= fList.get(0).getChangeName() %>">
                    	<% } %>
                        <input type="file" name="reUpfile1">
                   	</td>
                </tr>
    
                <tr>
                    <th>상세 이미지</th>
                    <td align="center">
                    <% for (int i = 1; i < fList.size(); i++) { %>
                   		<%= fList.get(i).getOriginName() %><br>
                    	<input type="hidden" name="originFileNo<%= i + 1 %>" value="<%= fList.get(i).getFileNo() %>">
                        <input type="hidden" name="originFileName<%= i + 1 %>" value="<%= fList.get(i).getChangeName() %>">
                    <% } %>
                    </td>
                    <td colspan="2">
                    	<input type="file" name="reUpfile2"><br>
	                    <input type="file" name="reUpfile3"><br>
	                    <input type="file" name="reUpfile4"><br>
                    </td>
                </tr>
            </table>

            <br>
            <div align="center">
                <button class="btn btn-sm btn-info" type="submit">등록</button>
                <button class="btn btn-sm btn-secondary" type="reset">초기화</button>
                <a href="<%= contextPath %>/tsList.ads?category=<%= category %>&currentPage=1&boardLimit=7" class="btn btn-primary btn-sm">목록으로 돌아가기</a>
            </div>
            
        </form>

    </div> <!--div class="outer" 영역 끝-->

</body>
</html>