<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="semi.member.model.vo.Member" %>
<%
	String contextPath = request.getContextPath();

   /* Member loginUser = (Member)session.getAttribute("loginUser"); */   // : Object
   // 로그인 전 : menubar.jsp가 로딩될 때 null
   // 로그인 후: manubar.jsp가 로딩될 때 로그인한 회원의 정보가 담겨 있음
   
   String alertMsg = (String)session.getAttribute("alertMsg"); // : Object
   // 서비스 요청 전 : alertMsg = null
   // 서비스 요청 후 성공 시 : alerMsg = 메세지 문구
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <!-- 폰트 -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
    <title>문의하기</title>
    <style>
        #header {
		width: auto;
		height: auto;
		padding-top: 10px;
		padding-bottom: 5px;
		text-align: center;
	    }
        .outer{
            background-color: white;
            color: rgb(68, 66, 66);
            width: 550px;
            height: 800px;
            margin : auto;
            margin-top : 0px;
        }
    
        #enroll-form>table {border : 1px solid rgb(133, 128, 128)}
        #enroll-form input, #enroll-form textarea{
            width: 100%;
            box-sizing: border-box;
            border-radius: 5px;
        }
        #logo {
		    width: 300px;
            height: 170px;
		    cursor: pointer;
            margin-top: 10px;
	    }
        #submit{
            width: 50px;
            height: 30px;
            border: 0;
		    cursor: pointer;
		    color: #fff;
		    border-radius: 7px;
		    background-color: #72b1ca;
        }
        #reset{
            width: 50px;
            height: 30px;
            border: 0;
		    cursor: pointer;
		    color: #fff;
		    border-radius: 7px;
		    background-color: #72b1ca;    
        }
        #logo {
	    width: 300px;
        height: 190px;
	    cursor: pointer;
        margin-top: 10px;
        margin: auto;
        }
        #web-font{
        font-family: 'Jua', sans-serif;
        font-size: 40px;
        margin-top: 10px;
        }   
        #content{
            height: 200px;
        }
        #title{
            height: 30px;
        }
        #sub-btn {
		width: 90px;
		padding: 5px 7px;
		border: 0;
		cursor: pointer;
		color: #fff;
		border-radius: 7px;
		background-color: #72b1ca;
		font-size: 15px;
		font-weight: 400;
		font-family: "굴림";
		font-weight: 1000;
	
	}
	#back-btn{
		width: 90px;
		padding: 5px 7px;
		border: 0;
		cursor: pointer;
		color: #fff;
		border-radius: 7px;
		background-color: #f5b369;
		font-size: 15px;
		font-weight: 400;
		font-family: "굴림";
		font-weight: 1000;
	}
    .btn{
        margin-bottom: 0px;
    }
    </style>
</head>
<body>
        <div align="center" class="logo" >
            <img src="<%=contextPath %>/resources/image/logo.png" alt="" id="logo">
        </div>
        
        <h2 style="text-align: center; margin-top: 10px;"><p id="web-font">문의 작성하기</p></h2>
    
        <div class="outer">
            <form action="<%=contextPath %>/inq.bo" id="enroll-form" method="post">
            <%-- <input type="hidden" name="userNo" value="<%=loginUser.getUserNo()%>"> --%>
            
                <table align="center">
                    <tr>
                        <th width="50">제목</th>
                        <td width="400"><input type="text" name="title" required></td>
                    </tr>
                    <tr>
                        <th>내용</th>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <textarea name="content" rows="18" style="resize: none" required></textarea>
                        </td>
                    </tr>
                </table>
                <br>
                <div align="center" class="btn">
                    <button type="submit" class="btn btn-sm btn-primary" id="sub-btn">등록</button>
                    <button type="button" id="back-btn" class="btn btn-sm btn-secondary" onclick="history.back();">뒤로가기</button>
                    <!-- history.back() : 이전 페이지로 돌아가게 해주는 함수 -->
                </div>
            
            </form>
        </div>
</body>
</html>