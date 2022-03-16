package semi.login.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import semi.common.MailSend;
import semi.member.model.service.MemberService;

/**
 * Servlet implementation class AjaxMailAuthController
 */
@WebServlet("/emailAuth.me")
public class AjaxMailAuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AjaxMailAuthController() {
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
		String email = request.getParameter("email");

		int result = new MemberService().emailCheck(email, userId);

		if (result != 1) { // 사용자가 입력한 사용자 이름 및 이메일 정보가 일치하는 회원 1명이 있지 않으면
			/*
			response.setContentType("application/json; charset=UTF-8");
			int check = result;
			JSONObject jObj = new JSONObject();
			jObj.put("check", check);
			response.getWriter().print(jObj);
			*/
			// 2022.2.4(금) 12h55 은영 시도
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().print(result);
		} else { // 사용자가 입력한 사용자 이름 및 이메일 정보가 일치하는 회원 1명이 있으면
//			int check = result;

			MailSend ms = new MailSend();

			// 100000~999998 범위 내 6자리 숫자로 이루어진 임시 비밀번호 생성
			int authNumber = (int)(Math.random() * 899999) + 100000;
			
			// 비밀번호를 찾고자 하는 회원의 비밀번호를 임시비밀번호로 변경
			int result2 = new MemberService().temporaryPwd(authNumber, email, userId);
			
			if (result2 > 0) { // 변경 성공하면 1 vs 실패하면 0 반환
				// 사용자의 이메일 주소, 임시 비밀번호, 정수 num(2022.2.4(금) 14h40 은영 질문 = num의 의미는 무엇인가?)을 매개변수로 넘겨서, 사용자의 이메일로 임시 비밀번호 발송
				ms.welcomeMailSend(email, authNumber, 2);

				/*
				response.setContentType("application/json; charset=UTF-8");
				JSONObject jObj = new JSONObject();
				jObj.put("check", check);
				jObj.put("authNumber", authNumber);

				response.getWriter().print(jObj);
				*/
				// 2022.2.4(금) 14h45 은영 시도
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().print(result);
			}

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}