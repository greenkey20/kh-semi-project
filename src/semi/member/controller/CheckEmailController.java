package semi.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import semi.member.model.dao.MemberDao;

// 2022.1.24(월) 16h5 평가자 checklist
/**
 * Servlet implementation class CheckEmailController
 */
@WebServlet("/checkEmail")
public class CheckEmailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckEmailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 전송 방식 별도 기재 안 함 = get 방식으로 요청 받음 -> encoding 필요 없음
		
		// request 객체로부터 값 뽑기 -> db로 전달할 값이  문자열 1개 뿐이므로, vo로 가공 필요 없음
		String email = request.getParameter("email");
//		System.out.println(email);
		
		// MemberDAO 클래스의 isEmailExist 함수에 인자값으로 전송받은 email을 전달 -> db(MEMBER 테이블)에 일치하는 email 있는지 조회 -> 결과를 boolean 형으로 반환받음
		Boolean result = new MemberDao().isEmailExist(email);
//		System.out.println(result);
		
		// db 조회 결과에 따른 응답 view 지정
		// content type(형식) 및 encoding 먼저 지정
		response.setContentType("text/html; charset=UTF-8");
		
		if (result) { // result가 true인 경우 = db에 일치하는 email이 있음 -> "이미 사용 중인 email입니다."
			response.getWriter().print("이미 사용 중인 email입니다.");
		} else { // result가 false = db에 일치하는 email이 없음 -> "사용 가능한 email입니다"
			response.getWriter().print("사용 가능한 email입니다.");
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
