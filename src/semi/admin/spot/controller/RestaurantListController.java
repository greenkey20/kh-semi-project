package semi.admin.spot.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import semi.admin.spot.model.service.AdminSpotService;
import semi.common.model.vo.PageInfo;
import semi.keyword.model.vo.Restaurant;

// 2022.2.2(수) 15h20
/**
 * Servlet implementation class RestaurantListController
 */
@WebServlet("/rList.ads")
public class RestaurantListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get 방식으로 요청 보냄 -> encoding 필요 없음
		
		// request 객체로부터 값 뽑기
		String category = request.getParameter("categoryR"); // KOREAN 또는 JEJU 또는 WESTERN
		int currentPage = Integer.parseInt(request.getParameter("currentPage")); // 1
		int boardLimit = Integer.parseInt(request.getParameter("boardLimit")); // 7
		
//		System.out.println(category);
		
		// + paging 처리 시 필요한 변수들 준비
		int listCount = new AdminSpotService().selectRestaurantListCount(category);
		int pageLimit = 5; // 게시글 list 보여주는 페이지 하단의  paging bar/buttons의 최대 개수
		int maxPage = (int)Math.ceil((double)listCount / boardLimit); // 가장 마지막 페이지가 몇 번 페이지인지 = 페이지의 총 개수
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1; // 페이지 하단에 보여질 첫번째 paging bar
		int endPage = startPage + pageLimit - 1; // 페이지 하단에 보여질 마지막 paging bar
		if (endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);

		// 이상 paging 관련 7개 변수 및 category 변수를 service단으로 전달 -> currentPage(사용자가 조회 요청한 페이지)에 보여줄 + 특정 여행 타입 관련 업체 목록(ArrayList에 담긴 TravelSpot 자료형)을 db로부터 조회해오고자 함
		ArrayList<Restaurant> rList = new AdminSpotService().selectRestaurantList(category, pi);
		// cf. KeywordService/KeywordDao에도 selectRestaurantList() 메소드 있으나, 기능 = 음식 타입별 업체 3개 랜덤으로 추출 -> 여기서 필요한 Restaurant 목록을 조회해올 수 없음
		
//		System.out.println(pi);
//		System.out.println(rList);
		
		// db 조회 결과를 넘기며 응답 view 지정
		request.setAttribute("rList", rList);
		request.setAttribute("pi", pi);		
		
		request.getRequestDispatcher("views/admin/restaurantListView.jsp").forward(request, response);
		// 2022.2.2(수) 17h 테스트 완료
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
