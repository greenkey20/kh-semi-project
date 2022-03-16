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
import semi.keyword.model.vo.TravelSpot;

// 2022.2.2(수) 17h20
/**
 * Servlet implementation class RestaurantSearchKeywordController
 */
@WebServlet("/searchRsKeyword.ads")
public class RestaurantSearchKeywordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantSearchKeywordController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request 객체로부터 값 뽑기
		String category = request.getParameter("category"); // KOREAN 또는 JEJU 또는 WESTERN
		String searchFilter = request.getParameter("searchFilter"); // RS_NAME 또는 ADDRESS 또는 DESCRIPTION 또는 MENU 또는 AREA
		String keyword = request.getParameter("keyword");
//		System.out.println(category);
//		System.out.println(searchFilter);
//		System.out.println(keyword);
		
		// + paging 처리 시 필요한 변수들 준비
		int listCount = new AdminSpotService().restaurantSearchKeywordCount(category, searchFilter, keyword);
		int currentPage = 1;
		int pageLimit = 5;
		int boardLimit = 100;
		int maxPage = (int)Math.ceil((double)listCount / boardLimit);
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1; 
		int endPage = startPage + pageLimit - 1;
		if (endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
		
		// 이상의 자료들을 service단으로 toss -> currentPage(사용자가 조회 요청한 페이지)에 보여줄 + 특정 키워드 검색 결과(ArrayList에 담긴 Restaurant 자료형)를 db로부터 조회해오기
		ArrayList<Restaurant> rList = new AdminSpotService().restaurantSearchKeyword(pi, category, searchFilter, keyword);
		
		// db 조회 결과를 넘기며 응답 view 지정
		request.setAttribute("rList", rList);
		request.setAttribute("pi", pi);
		request.setAttribute("searchFilter", searchFilter);
		
		request.getRequestDispatcher("views/admin/restaurantListView.jsp").forward(request, response);
		// 2022.2.2(수) 18h10 초안 작성 마무리 -> 18h15 테스트 완료
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
