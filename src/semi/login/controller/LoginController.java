package semi.login.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import semi.member.model.service.MemberService;
import semi.member.model.vo.Member;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login.me")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");

		Member loginUser = new MemberService().loginMember(userId, userPwd);

		// 로그인 유저가 있을시
		if (loginUser != null) {
			// 로그인유저아이디가 admin 일경우
			if (loginUser.getUserId().equals("admin")) {
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", loginUser);
				session.setAttribute("alertMsg", loginUser.getUserName() + "님 환영합니다!");

				// 경로 수정 해줘야함 꼭(로그인 시 넘어갈 페이지로!!!!)
				request.getRequestDispatcher("views/admin/adminMain.jsp").forward(request, response);
			} else { // 일반 유저
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", loginUser);
				session.setAttribute("alertMsg", loginUser.getUserName() + "님 환영합니다!");
				response.sendRedirect(request.getContextPath() + "/views/common/afterLogin.jsp");
			}
		} else { // 로그인정보가 불일치
			request.setAttribute("errorMsg", "로그인에 실패했습니다.");
			RequestDispatcher view = request.getRequestDispatcher("views/common/login.jsp");
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
