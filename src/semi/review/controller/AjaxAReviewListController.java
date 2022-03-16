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

// 2022.1.23(일) 22h35 생성 -> 2022.1.26(수) 11h25 수정/작성
/**
 * Servlet implementation class AReviewListController
 */
@WebServlet("/aList.rv")
public class AjaxAReviewListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxAReviewListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get 방식으로 요청 받음 -> encoding 필요 없음
		
		// request 객체로부터 값 뽑기
		int tsNo = Integer.parseInt(request.getParameter("tsNo"));
//		String category = request.getParameter("category"); // activity -> 그런데 여기에서는 이거 필요 없을 수도 있을 것 같당..?! -> 2022.1.26(수) 11h25 필요 없는 듯하여 주석 처리함
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		// paging 처리 시 필요한 변수들 준비
		int listCount = new ReviewService().selectAReviewListCount(tsNo); // activityReview 테이블 + 해당 activity 번호 + status 'Y'인 리뷰 총 개수
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
		ArrayList<Review> rvList = new ReviewService().selectAReviewList(tsNo, pi);
		
//		System.out.println(pi);
//		System.out.println(rvList); // 리뷰 아예 없는 숙소의 경우 []로 찍힘
		
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
