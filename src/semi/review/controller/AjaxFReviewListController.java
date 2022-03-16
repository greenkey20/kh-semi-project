package semi.review.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import semi.common.model.vo.PageInfo;
import semi.review.model.service.ReviewService;
import semi.review.model.vo.Review;

// 2022.1.26(수) 16h45
/**
 * Servlet implementation class AjaxF1ReviewListController
 */
@WebServlet("/foodList.rv")
public class AjaxFReviewListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxFReviewListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int rsNo = Integer.parseInt(request.getParameter("rsNo"));
		String category = request.getParameter("category");
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		System.out.println(rsNo);
		System.out.println(category);
		
		// + paging 처리 시 필요한 변수들 준비
		int listCount = new ReviewService().selectFoodReviewListCount(rsNo, category); // hotelReview 또는 landscapeReview 테이블 + 해당 숙소/식당 번호 + status 'Y'인 리뷰 총 개수
		
		System.out.println(listCount);
		
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
		ArrayList<Review> rvList = new ReviewService().selectFoodReviewList(rsNo, category, pi);
		
		System.out.println(pi);
		System.out.println(rvList);
		
		response.setContentType("application/json; charset=UTF-8");
		
		new Gson().toJson(rvList, response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
