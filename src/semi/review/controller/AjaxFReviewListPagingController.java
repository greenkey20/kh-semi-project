package semi.review.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import semi.common.model.vo.PageInfo;
import semi.review.model.service.ReviewService;

// 2022.1.26(수) 16h10
/**
 * Servlet implementation class AjaxF1ReviewListPagingController
 */
@WebServlet("/foodListPaging.rv")
public class AjaxFReviewListPagingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxFReviewListPagingController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int rsNo = Integer.parseInt(request.getParameter("rsNo"));
		String category = request.getParameter("category"); // KOREAN 또는 JEJU 또는 WESTERN
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		// + paging 처리 시 필요한 변수들 준비
		int listCount = new ReviewService().selectFoodReviewListCount(rsNo, category); // RestaurantReview 테이블 + 해당 식당 번호 + category 'KOREAN' 또는 'JEJU' 또는 'WESTERN' + status 'Y'인 리뷰 총 개수
		int pageLimit = 4;
		int boardLimit = 4;
		
		// paging bar/buttons 관련
		int maxPage = (int)Math.ceil((double)listCount / boardLimit);
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit - 1;
		if (endPage > maxPage) {
			endPage = maxPage;
		}
		
		// 이상 paging 관련 7개 변수들을 service단으로 전달 -> 사용자가 조회 요청한 페이지(currentPage)에  보여줄 리뷰 글들(ArrayList에 담긴 Review 객체들)을 DB로부터 조회해오고자 함
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
				
		// 응답
		response.setContentType("application/json; charset=UTF-8");
		
		// Java 객체를 JavaScript 객체로 변환해서 AJAX 응답
		JSONObject jPi = new JSONObject();
		jPi.put("listCount", pi.getListCount());
		jPi.put("currentPage", pi.getCurrentPage());
		jPi.put("pageLimit", pi.getPageLimit());
		jPi.put("boardLimit", pi.getBoardLimit());
		jPi.put("maxPage", pi.getMaxPage());
		jPi.put("startPage", pi.getStartPage());
		jPi.put("endPage", pi.getEndPage());
		
		response.getWriter().print(jPi); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
