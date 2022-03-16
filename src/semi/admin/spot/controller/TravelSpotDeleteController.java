package semi.admin.spot.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import semi.admin.spot.model.service.AdminSpotService;

// 2022.2.1(화) 15h5
/**
 * Servlet implementation class TravelSpotDeleteController
 */
@WebServlet("/deleteTs.ads")
public class TravelSpotDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TravelSpotDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get 방식으로 요청 받음 -> encoding 지정 필요 없음
		
		// request 객체로부터 값 뽑기
		String category = request.getParameter("category"); // HOTEL 또는 LANDSCAPE 또는 ACTIVITY
		int tsNo = Integer.parseInt(request.getParameter("tsNo"));
		
		// vo 가공 필요 없음 -> service단으로 바로 toss -> 삭제하고자 하는 업체의 STATUS를 'N'으로 변경/UPDATE
		int result = new AdminSpotService().deleteTravelSpot(tsNo,category);
		
		// 처리 결과에 따라 응답 view 지정
		if (result > 0) {
			request.getSession().setAttribute("alertMsg", "여행지 삭제에 성공했습니다");
			response.sendRedirect(request.getContextPath() + "/tsList.ads?category=" + category + "&currentPage=1&boardLimit=7"); // 테스트 시 tsList.ads 앞에 / 누락 -> 404 error 'SEMI-PROJECTtsList.ads는 가용하지 않습니다' 발생
		} else {
			request.setAttribute("errorMsg", "여행지 삭제에 실패했습니다");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
		// 2022.2.1(화) 15h25 초안 작성 마무리 -> 15h45 테스트 완료
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
