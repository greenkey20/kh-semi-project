package semi.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import semi.member.model.service.MemberService;
import semi.member.model.vo.MemberTravelHistory;

// 2022.1.27(목) 11h40
/**
 * Servlet implementation class MemberTravelHistoryInsertController
 */
@WebServlet("/insertTravelHistory.me")
public class MemberTravelHistoryInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberTravelHistoryInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get 방식으로 받음 -> 단계0) encoding 필요 없음
		
		// 단계1) request 객체로부터 값 뽑기
		// <%= contextPath %>/insertTravelHistory.me?userno=<%= loginUser.getUserNo() %>&traveltype1=<%= ts.getCategory() %>&travelspot1=<%= ts.getTsNo() %>&traveltype3=<%= a.getCategory() %>&travelspot3=<%= a.getTsNo() %>&foodtype=<%= rList.get(0).getCategory() %>&restaurant1=<%= rList.get(0).getRsNo() %>&restaurant2=<%= rList.get(1).getRsNo() %>&restaurant3=<%= rList.get(2).getRsNo() %>
		int userNo = Integer.parseInt(request.getParameter("userno"));
		
		// 로직상 travelType1/Spot1 또는 travelType3/Spot3 중 하나만 값이 들어있을 것임 -> 2022.1.28(금) 오후에 db 조회 결과를 모두 TravelSpot 자료형으로 받는 것으로 수정한 바, travelType1 = HOTEL 또는 LANDSCAPE 또는 ACTIVITY
		String travelType1 = request.getParameter("traveltype1"); // HOTEL 또는 LANDSCAPE 또는 ACTIVITY 
		int travelSpot1 = Integer.parseInt(request.getParameter("travelspot1"));
		
		/*
		String travelType3 = "";
		int travelSpot3 = 0;
		
		if (request.getParameter("traveltype1") != null) {
			travelType1 = request.getParameter("traveltype1"); // HOTEL 또는 LANDSCAPE; 나의 질문 = 사용자가 애초에 ACTIVITY 키워드를 선택했다면, 이렇게 뽑아온 값은 ""일까, null일까? ts 객체가 애초에 null이었을테니 null이겠지..?! 
			travelSpot1 = Integer.parseInt(request.getParameter("travelspot1"));
		} else {
			travelType3 = request.getParameter("traveltype3");
			travelSpot3 = Integer.parseInt(request.getParameter("travelspot3")); // ACTIVITY
		}
		*/
		
		String foodType = request.getParameter("foodtype");
		int restaurant1 = Integer.parseInt(request.getParameter("restaurant1"));
		int restaurant2 = Integer.parseInt(request.getParameter("restaurant2"));
		int restaurant3 = Integer.parseInt(request.getParameter("restaurant3"));
		
		// 단계2) vo로 가공 <- 매개변수 생성자를 이용해서 MemberTravelHistory 객체에 담기
		// 2022.1.29(토) 21h50 업체 리스트 조회 서블릿 만들다가 selectKeyword2Controller 및 MemberTravelHistoryInsertController 다시 읽어보다가 이 서블릿 수정
		// MemberTravelHistory mth = new MemberTravelHistory(userNo, travelType1, travelSpot1, travelType3, travelSpot3, foodType, restaurant1, restaurant2, restaurant3);
		MemberTravelHistory mth = new MemberTravelHistory(userNo, travelType1, travelSpot1, foodType, restaurant1, restaurant2, restaurant3);
		
		// 단계3) service단에 toss -> 처리 결과 받음
		int result = new MemberService().insertMemberTravelHistory(mth);
		
		// 단계4) 처리 결과에 따라 사용자가 보게 될 응답 화면 지정
		if (result > 0) { // 회원 여행 이력 TABLE에 추천 결과 저장 성공한 경우
			request.getSession().setAttribute("alertMsg", "나의 여행 이력에 저장되었습니다.");
			// 2022.1.28(금) 오후 travelListView.jsp에서 요청 보낼 때 travelType3, travelSpot3은 url에서 뺌 + INSERT sql문에 travelType3, travelSpot3 컬럼은 그대로 놔둠 -> travelType3 컬럼 값은 null, travelSpot3 컬럼 값은 0으로 추가됨
			// vs 2022.1.29(토) 22h INSERT sql문에서 travelType3, travelSpot3 컬럼 삭제하고 테스트해보니 travelType3과 travelSpot3 컬럼 값 모두 null로 추가됨
			response.sendRedirect(request.getContextPath() + "/myPage.me"); // url mapping 값이 "/myPage.me"인 서블릿(session에 loginUser 정보가 담겨있는 경우, myPage.jsp로 forwarding해주는 역할) 호출 -> localhost:8002/SEMI-PROJECT/myPage.me가 보이도록 응답 페이지 넘겨줌
		} else {
			request.setAttribute("errorMsg", "나의 여행 이력에 저장 실패했습니다."); // 실패 msg는 응답 페이지 1곳에서만 사용하면 되므로, request 객체에 담아서 넘김
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
