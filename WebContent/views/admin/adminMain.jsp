<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<title>Admin Page</title>
  
 <style>
		 #main-contents{
		    width: 100%;
		}
    /*제목*/
	h1{
	    vertical-align:middle;
	    line-height:30px;
	   	color: rgb(43, 43, 43);
	    text-align: center;
	}	
		
    .card {
    	height: 300px;
		width: 300px;
		border-radius: 15px;
		display: inline-block;
		margin-top: 30px;
		margin-left: 30px;
		margin-bottom: 30px;
		position: relative;
		box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
		overflow: hidden;
    }
    #member_information{
    	background-color: #ffcaca;
    }
    #notice{
    	background-color: #ffeaa6;
    }
    #inquiry{
    	background-color: #ffb055;
    }
    #information{
    	background-color: #b1ffb8;
    }
    #review{
    	background-color: #9ee0ff;
    }

    .card-header {
		-webkit-transition: 0.5s; /*사파리 & 크롬*/
	    -moz-transition: 0.5s;  /*파이어폭스*/
	    -ms-transition: 0.5s;	/*인터넷 익스플로러*/
	    -o-transition: 0.5s;  /*오페라*/
	    transition: 0.5s;
		width: 100%;
		height: 1500px;
		border-radius: 15px 15px 0 0;
		text-align : center;
		color: white;
		position: absolute;
		top: 50%;
	}
	.card:hover .card-header  {
		opacity: 0.8;
	}
    
    .card:hover {
    	opacity: 1;
	    -webkit-transition: .5s ease-in-out;
	    -moz-transition: .5s ease-in-out;
	    -ms-transition: .5s ease-in-out;
	    -o-transition: .5s ease-in-out;
	    transition : .5s ease-in-out;
  	}
 </style>
</head>
<body>
	<%@ include file = "../common/menubar.jsp" %>
	
	<script>
		window.onload = function() {
			
		}
	</script>


  <section id="main-contents">
    <br><br> 
  	<h1>관리자 전용 메인</h1>
    <br><br>
  	<div class="container">
	 	<div class="row">
	 		<div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
				<a href="">
					<div id="member_information" class="card">
						<div class="card-header">
							<a href="<%= contextPath %>/views/admin/adminMemberList.jsp">회원정보 관리</a>
						</div>
					</div>
				</a>
			</div>
			<div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
				<a href="">
					<div id="notice" class="card">
						<div class="card-header">
							<a href="<%= contextPath %>/views/admin/adminNotice.jsp">공지사항 관리</a>
						</div>
					</div>
				</a>
			</div>
			<div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
				<a href="">
					<div id="inquiry" class="card">
						<div class="card-header">
							<a href="<%= contextPath %>/views/question/questionMain.jsp">문의 관리</a>
						</div>
					</div>
				</a>
			</div>
			<div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
				<a href="">
					<div id="information" class="card">
						<div class="card-header">
							<a href="<%= contextPath %>/views/admin/spotAdminMain.jsp">업체 관리</a>
						</div>
					</div>
				</a>
			</div>
			<div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
				<a href="">
					<div id="review" class="card">
						<div class="card-header">
							<h3>리뷰 관리</h3>
						</div>
					</div>
				</a>
			</div>
		</div>
	</div>
  </section>
</body>
</html>