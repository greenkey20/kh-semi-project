package semi.question.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import semi.member.model.vo.Member;
import semi.question.model.service.QuestionService;
import semi.question.model.vo.Question;

/**
 * Servlet implementation class QuestionInsertContoller
 */
@WebServlet("/inq.bo")
public class QuestionInsertContoller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionInsertContoller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Member loginUser = (Member)request.getSession().getAttribute("loginUser");
		if(loginUser == null) {
			request.setAttribute("msg", "로그인 후 이용하세요.");
			request.getRequestDispatcher("views/common/login.jsp").forward(request, response);
		}else {
			String qTitle = request.getParameter("title");
			String qContent = request.getParameter("content");
			int userNo = loginUser.getUserNo();
			String userId = loginUser.getUserId();
			String userName = loginUser.getUserName();
			
			Question q = new Question();
			q.setQuestionTitle(qTitle);
			q.setQuestionContent(qContent);
			q.setUserNo(userNo);
			q.setUserId(userId);
			q.setUserName(userName);
			
			int result = new QuestionService().insertQuestion(q);
			
			if(result > 0 ) {
				request.setAttribute("msg", "1:1문의가 등록 되었습니다.");
				//이건 수정해야함 일단 임시로 인덱스로
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}else {
				//실패했을때 에러페이지로 보낼 것인가? 아니면 처리 페이지를 인덱스로 할것인가 의견 나눠보고 정할것
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
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
