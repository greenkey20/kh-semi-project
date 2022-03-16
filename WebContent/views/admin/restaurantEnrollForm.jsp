<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String category = (String)request.getAttribute("category"); // KOREAN 또는 JEJU 또는 WESTERN
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식당 추가/등록</title>
<style>
	.outer {
        border: 1px solid mediumspringgreen;
        width: 1000px;
		height: 800px;
        margin: auto;
        margin-top: 50px;
    }

    #enroll-form>table {border: 1px solid mediumspringgreen;}
    
    #enroll-form input, #enroll-form textarea {
        width: 100%;
        box-sizing: border-box;
    }
</style>
</head>
<body>
	<!--2022.2.2(수) 18h15-->
	<%@ include file = "../common/menubar.jsp" %>

    <div class="outer">
        <h2>[관리자] 식당 추가/등록</h2>
        <br>

        <form id="enroll-form" action="<%= contextPath %>/insertRestaurant.ads" method="post" enctype="multipart/form-data">
        	<input type="hidden" name="category" value="<%= category %>"> <!--KOREAN 또는 JEJU 또는 WESTERN-->
        	
            <table align="center" border="1">
                <tr>
                    <th width="100">음식 종류</th>
                    <td width="500" colspan="3">
                        <% switch(category) { 
                        case "KOREAN" : %>
                           	 한식당
                        <% break; 
                        case "JEJU" : %>
                           	 제주별미식당
                        <% break;
                    	case "WESTERN" : %>
                           	 양식당
                        <% } %>
                    </td>
                </tr>
                <tr>
                    <th>이름</th>
                    <td colspan="3"><input type="text" name="name" required></td>
                </tr>
    
                <tr>
                    <th>주소</th>
                    <td colspan="3"><input type="text" name="address" required></td>
                </tr>
    
                <tr>
                    <th>지역</th>
                    <td colspan="3">
                        <select name="area" id="area">
                            <option value="제주시" selected>제주시</option>
                            <option value="서귀포시">서귀포시</option>
                            <option value="우도부근">우도 부근</option>
                        </select>
                    </td>
                </tr>
    
                <tr>
                    <th>전화번호</th>
                    <td colspan="3"><input type="text" name="phone" placeholder="xxx-xxxx-xxxx 형식 입력" required></td>
                </tr>
    
                <tr>
                    <th>소개 문구</th>
                    <td colspan="3">
                        <textarea name="description" rows="5" style="resize: none;" placeholder="1000byte 입력 가능" required></textarea>
                    </td>
                </tr>
                
                <tr>
                    <th>메뉴</th>
                    <td colspan="3"><input type="text" name="menu"></td>
                </tr>
    
                <tr>
                    <th>가격</th>
                    <td colspan="3"><input type="number" name="price"></td>
                </tr>
    
                <tr>
                    <th>대표 이미지</th>
                    <td colspan="3" align="center"><img id="titleImg" alt="titleImg" width="450" height="170"></td>
                </tr>
    
                <tr>
                    <th>상세 이미지</th>
                    <td><img id="contentImg1" alt="contentImg1" width="150" height="120"></td>
                    <td><img id="contentImg2" alt="contentImg2" width="150" height="120"></td>
                    <td><img id="contentImg3" alt="contentImg3" width="150" height="120"></td>
                </tr>
            </table>
  
            <div id="file-area">
                <input type="file" id="file1" onchange="loadImg(this, 1);" name="file1" required><br>
                <input type="file" id="file2" onchange="loadImg(this, 2);" name="file2">
                <input type="file" id="file3" onchange="loadImg(this, 3);" name="file3">
                <input type="file" id="file4" onchange="loadImg(this, 4);" name="file4">
            </div>     
            
            <script>
                $(function() {
                    $('#file-area').hide();
                    
                    $('#titleImg').click(function() {
						$('#file1').click();
					})

					$('#contentImg1').click(function() {
						$('#file2').click();
					})

					$('#contentImg2').click(function() {
						$('#file3').click();
					})

					$('#contentImg3').click(function() {
						$('#file4').click();
					})
                })

                function loadImg(inputFile, num) {
                    if (inputFile.files.length == 1) {
                        var reader = new FileReader();
                        reader.readAsDataURL(inputFile.files[0]);
                        reader.onload = function(e) {
                            switch (num) {
								case 1 : $("#titleImg").attr("src", e.target.result); break;
								case 2 : $("#contentImg1").attr("src", e.target.result); break;
								case 3 : $("#contentImg2").attr("src", e.target.result); break;
								case 4 : $("#contentImg3").attr("src", e.target.result); break;
							}
                        }
                    }
                    else {
                        switch (num) {
                        case 1 : $("#titleImg").attr("src", null); break;
                        case 2 : $("#contentImg1").attr("src", null); break;
                        case 3 : $("#contentImg2").attr("src", null); break;
                        case 4 : $("#contentImg3").attr("src", null); break;
                        }
                    }
                }             
            </script>

            <br>
            <div align="center">
                <button class="btn btn-sm btn-info" type="submit">등록</button>
                <button class="btn btn-sm btn-secondary" type="reset">초기화</button>
                <a href="<%= contextPath %>/rList.ads?categoryR=<%= category %>&currentPage=1&boardLimit=7" class="btn btn-primary btn-sm">목록으로 돌아가기</a>
            </div>
            
        </form>

    </div> <!--div class="outer" 영역 끝-->

</body>
</html>