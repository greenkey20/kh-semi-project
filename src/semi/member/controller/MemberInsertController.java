package semi.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import semi.member.model.service.MemberService;
import semi.member.model.vo.Member;

/**
 * Servlet implementation class MemberInsertController
 */
@WebServlet("/member.enroll")
public class MemberInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 1.인코딩
		request.setCharacterEncoding("UTF-8");
		
		// 2.전달값 
		String userId = request.getParameter("userId"); // 필수입력사항 
		String userPwd = request.getParameter("userPwd"); // 필수입력사항
		String userName = request.getParameter("userName"); // 필수입력사항
		String email = request.getParameter("email"); // 필수입력사항
		String phone = request.getParameter("phone"); // 빈 문자열이 들어갈 수 있음
		String address = request.getParameter("address"); // 빈문자열이 들어갈 수 있음
		String[] interestArr = request.getParameterValues("interest"); // ["액티비티", "명소", ....] // null
		
		// 3.배열 하나로 합쳐주기
		String interest = "";
		if(interestArr != null) { // 값이 최소 1개 이상일때 수행 null이면 수행 할 필요가 없다.
			interest = String.join(",", interestArr);
		}
		
		System.out.println(userId);
		
		// 4.Member객체에 담기
		Member m = new Member(userId,userPwd,userName,email,phone,address,interest);

		// 5.서비스단으로 요청 처리 후 결과 받기
		int result = new MemberService().insertMember(m);
		
		// -----
		// 6.처리 결과를 가지고 사용자가 보게될 응답화면 지정
		if(result > 0) { //=> 성공 시 알럿창 띄우고 로그인페이지로 돌려보내기
			request.getSession().setAttribute("alertMsg", "회원가입에 성공했습니다");
			
			response.sendRedirect(request.getContextPath()); // 로그인 페이지로 돌려보내기
		}else { // 실패 시 에러페이지
			request.setAttribute("errorMsg", "회원가입에 실패했습니다.");
			
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}