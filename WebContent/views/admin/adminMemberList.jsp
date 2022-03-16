<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, semi.member.model.vo.Member, semi.common.model.vo.PageInfo"  %>
<%
	ArrayList<Member> list = (ArrayList<Member>)request.getAttribute("list");

	Member member = (Member)request.getAttribute("m");
%>
<%
	String pageNavi = (String)request.getAttribute("pageNavi");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Google Fonts -->
	<link
		href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
		rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.4.0.js"></script>
<!-- Vendor CSS Files -->
	<link href="../assets/vendor/bootstrap/css/bootstrap.min.css"
		rel="stylesheet">
	<link href="../assets/vendor/bootstrap-icons/bootstrap-icons.css"
		rel="stylesheet">
	<link href="../assets/vendor/boxicons/css/boxicons.min.css"
		rel="stylesheet">
	<link href="../assets/vendor/glightbox/css/glightbox.min.css"
		rel="stylesheet">
	<link href="../assets/vendor/remixicon/remixicon.css" rel="stylesheet">
	<link href="../assets/vendor/swiper/swiper-bundle.min.css"
		rel="stylesheet">

<!-- Template Main CSS File -->
	<link href="../assets/css/style.css" rel="stylesheet">
	<style>
		#main-title {
			
			width: 100%;
			height: 100px;
			
		}
		
		#main-title h1 {
			color: #1d284b;
			/* margin-left: 15%; */
			font-weight: bolder;
			line-height: 50px;
			text-align: center;
		}
		
		h2 {
			padding: 5px 10px;
			border-bottom: 1px solid #848484;
			border-left: 8px solid #848484;
		}
		
		ul, li {
			list-style: none;
			text-align: center;
			padding: 0;
			margin: 0;
		}
		
		#mainWrapper {
			width: 950px;
			margin: auto;
			justify-content: center;
			vertical-align: middle;
			align-items: center;
		}
		
		#mainWrapper>ul>li:first-child {
			text-align: center;
			font-size: 14pt;
			height: 40px;
			vertical-align: middle;
			line-height: 30px;
		}
		
		#ulTable {
			margin-top: 10px;
		}
		
		#ulTable ul {
			width: 100%;
		}
		
		#ulTable>li:first-child>ul>li {
			background: #72b1ca;
			color: #fff;
			font-weight: bold;
			text-align: center;
		}
		
		#ulTable>li>ul {
			clear: both;
			padding: 0px auto;
			position: relative;
			min-width: 50px;
		}
		
		#ulTable>li>ul>li {
			float: left;
			font-size: 10pt;
			border-bottom: 1px solid silver;
			vertical-align: baseline;
		}
		
		#ulTable>li>ul>li:first-child {
			width: 10%;
		} 
		#ulTable>li>ul>li:first-child+li {
			width: 20%;
		} 
		#ulTable>li>ul>li:first-child+li+li {
			width: 20%;
		} 
		#ulTable>li>ul>li:first-child+li+li+li {
			width: 30%;
		}
		#ulTable>li>ul>li:first-child+li+li+li+li {
			width: 20%;
		} 
		
		#divPaging {
			clear: both;
			margin: 0 auto;
			padding: 20px;
			width: 250px;
			height: 50px;
		}
		
		#divPaging>div {
			float: left;
			width: 30px;
			margin: 0 auto;
			text-align: center;
		}
		
		#liSearchOption {
			clear: both;
		}
		
		#liSearchOption>div {
			margin: 0 auto;
			margin-top: 30px;
			width: auto;
			height: 100px;
		}
		
		.left {
			text-align: left;
		}
		
		#button-div1 {
			position: relative;
			left: 450px;
			bottom: 15px;
		}
		
		#divPaging>div {
			float: left;
			width: 30px;
			margin: 0 auto;
			text-align: center;
		}
		
		/* #back-btn {
			margin-left: 25%;
			margin-right: 20%;
			text-align: right;
		} */
		
		#search-btn {
			background-color: #72b1ca;
			color: white;
			border-style: none;
			font-family: "Raleway", sans-serif;
			font-weight: 600;
			font-size: 14px;
			letter-spacing: 1px;
			display: inline-block;
			padding: 9px 20px;
			border-radius: 5px;
			transition: 0.3s;
			line-height: 1;
			-webkit-animation-delay: 0.8s;
			animation-delay: 0.8s;
			margin-top: 6px;
			border: 2px solid #72b1ca;
		}
		
		#search-btn:hover {
			background: #276077;
			color: #fff;
			text-decoration: none;
		}
		
		#search {
			margin-left: 30px;
		}
</style>
<meta charset="UTF-8">
<title>관리자페이지 회원관리 리스트</title>
</head>
<body>
	<%@ include file = "../common/menubar.jsp" %>

	<!--  === Main ===  -->
	<main id="main">
		<section>			<div id="main-title">
				<h1>전체 회원 관리</h1>
			</div>
		</section>

		<div id="mainWrapper">
			<ul>
				<li>
					<ul id="ulTable">
						<li>
							<ul>
								<li>NO</li>
								<li>아이디</li>
								<li>이름</li>
								<li>이메일</li>
								<li>가입일</li>
							</ul>
						</li>
						<li>
						<% if(list != null) { %>
							<% for(Member m : list) { %>
							<ul class="memberList"><!-- 회원 목록 전체 조회 -->
								<li><%= m.getUserNo() %></li>
								<li><%= m.getUserId() %></li>
								<li><%= m.getUserName() %></li>
								<li><%= m.getEmail() %></li>
								<li><%= m.getEnrollDate() %></li>
							</ul>
							<% } %>
							<% } %>
							<% if(member != null) { %>
							<ul class="memberList"><!-- 회원 검색  -->
								<li><%= member.getUserNo() %></li>
								<li><%= member.getUserId() %></li>
								<li><%= member.getUserName() %></li>
								<li><%= member.getEmail() %></li>
								<li><%= member.getEnrollDate() %></li>
							</ul>
							<% } %>
						</li>
					</ul>
				</li>
                <li>
        <!-- === 페이징 === -->
					<div id="divPaging">
					
					</div>
				</li>
				
			</ul>
		</div>
        <!-- === 검색 === -->
        <section>
            <form action="/SEMI-PROJECT/admin.member.search" method="get">
                <ul>
                    <li id='liSearchOption'>
                        <div>
                            <input type="text" id='txtKeyWord' name="searchKeyword" placeholder="아이디 검색" /> <input type="submit" id="search-btn" value='검색' />
                        </div>
                    </li>
                </ul>
            </form>
		</section>
	</main>
	<script>
			$(function(){
				$(".memberList").click(function(){
					// 클릭했을 때 회원 아이디를 넘긴다
					var userNo = $(this).children().eq(0).text();

					// 회원 아이디를 가지고 요청
					location.href = "/SEMI-PROJECT/memberDetail?userNo=" + userNo;
				})
			})
	</script>
	
</body>
</html>