<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, semi.admin.model.vo.*, semi.member.model.vo.*" %>
<%
	ArrayList<MemberTravelHistory> list = (ArrayList<MemberTravelHistory>)request.getAttribute("list");
	ArrayList<Review> list2 = (ArrayList<Review>)request.getAttribute("list2");
	Member m = (Member)request.getAttribute("m");
	System.out.println(list);
%>

<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<meta charset="UTF-8">
<title>Admin_MemberDetail</title>
    <style>
    h3{
    text-align: center;
    margin: 30px; 
    }
    h6{
    text-align: center;
    }
    #memberDetailInformation{
        font-size: small;
    }
    .table {
        font-size: small;
    }
</style>
</head>
<body>
    <h3>회원상세정보</h3>
    <div class="container">
        <div class="row">
            <div class="col-sm-3">
            <h6>회원정보</h6>
            <table id="memberDetailInformation">
                <tr>
                    <td>아이디</td>
                    <td><input type="text" name="userId" id="userId" value="<%= m.getUserId()%>"></td>
                </tr>
                <tr>
                    <td>비밀번호</td>
                    <td><input type="text" name="pswd" id="pswd" value="<%= m.getUserPwd()%>"></td>
                </tr>
                <tr>
                    <td>이름</td>
                    <td><input type="text" name="name" id="name" value="<%= m.getUserName()%>"></td>
                </tr>
                <tr>
                    <td>이메일</td>
                    <td><input type="email" name="email" id="email" value="<%= m.getEmail()%>"></td>
                </tr>
                <tr>
                    <td>휴대전화</td>
                    <td><input type="text" name="phone" id="phone" value="<%= m.getPhone()%>"></td>
                </tr>
                <tr>
                    <td>주소</td>
                    <td><input type="text" name="address" id="address" value="<%= m.getAddress()%>"></td>
                </tr>
                <tr>
                    <td>관심분야</td>
                    <td><input type="text" name="interest" id="interest" value="<%= m.getInterest()%>"></td>
                </tr>
                <tr>
                    <td><input type="submit" value="수정" ></td>
                    <td><input type="submit" value="탈퇴" ></td>
                </tr>
            </table>
        

          </div>
          <div class="col-sm-9">
            <div class="row">
              <div class="col-8 col-sm-6">
                <h6>여행이력</h6>
                <table class="table "> 
                    <thead>
                        <tr class="table-success">
                            <th >여행날짜</th>
                            <th>여행type</th>
                            <th>여행지</th>
                            <th>식당type</th>
                            <th>식당1</th>
                            <th>식당2</th>
                            <th>식당3</th>
                        </tr>
                    </thead>
                    <tbody>
                    	<% if(!list.isEmpty()) {%>
                    		<% for(MemberTravelHistory mth : list){ %>
		                        <tr>
		                            <td><%= mth.getTravelDate() %></td>
		                            <td><%= mth.getTravelType1() %></td>
		                            <td><%= mth.getTravelSpotName() %></td>
		                            <td><%= mth.getFoodType() %></td>
		                            <td><%= mth.getRestaurantName() %></td>
		                            <td><%= mth.getRestaurantName2() %></td>
		                            <td><%= mth.getRestaurantName3() %></td>
		                        </tr>
		                    <%} %>
                        <%} %>
                    </tbody>
                </table>
              </div>
              <div class="col-4 col-sm-6">
                <h6>식당리뷰작성내역</h6>
                <table class="table"> 
                    <thead>
                        <tr class="table-success">
                            <th>type</th>
                            <th>장소</th>
                            <th>방문날짜</th>
                            <th width ='150'>내용</th>
                        </tr>
                    </thead>
                    <tbody>
                    <% if(!list2.isEmpty()) {%>
                    	<% for(Review rs : list2){ %>
	                        <tr>
	                            <td><%=rs.getCategory()%></td>
	                            <td><%=rs.getRsName()%></td>
	                            <td><%=rs.getCreateDate()%></td>
	                            <td><%=rs.getReviewContent()%></td>
	                        </tr>
	                    <%} %>
                     <%} %>
                    </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
</body>
</html>