package semi.admin.spot.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import semi.admin.spot.model.service.AdminSpotService;
import semi.admin.spot.model.vo.Attachment;
import semi.common.model.vo.PageInfo;
import semi.keyword.model.vo.Restaurant;
import semi.review.model.service.ReviewService;
import semi.review.model.vo.Review;

// 2022.2.2(수) 22h10
/**
 * Servlet implementation class RestaurantDetailController
 */
@WebServlet("/detailRs.ads")
public class RestaurantDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int rsNo = Integer.parseInt(request.getParameter("rsNo"));
		String category = request.getParameter("category"); // KOREAN 또는 JEJU 또는 WESTERN -> db에서 해당 식당 정보 찾는 데에 굳이 필요 없으나/식당 번호만 있으면 되나, 기존에 만들어둔 메소드들 재활용하려면 category 넘겨야 해서 받아옴
		int currentPage = Integer.parseInt(request.getParameter("currentPage")); // 1
		
		Restaurant r = new AdminSpotService().selectRestaurant(rsNo);
		ArrayList<Attachment> fList = new AdminSpotService().selectAttachmentList(rsNo, category);
		
		// 해당 식당 관련 리뷰 목록 가져오기
		int listCount = new ReviewService().selectFoodReviewListCount(rsNo, category);
		int pageLimit = 5;
		int boardLimit = 10;
		int maxPage = (int)Math.ceil((double)listCount / boardLimit);
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit - 1;
		if (endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
		
		ArrayList<Review> rvList = new ReviewService().selectFoodReviewList(rsNo, category, pi);
		
		// 처리 결과를 응답 view로 넘김 + 응답 page 지정
		request.setAttribute("r", r);
		request.setAttribute("fList", fList);
		request.setAttribute("rvList", rvList);
		request.setAttribute("pi", pi);
		
		request.getRequestDispatcher("views/admin/restaurantDetailView.jsp").forward(request, response);
		// 2022.2.2(수) 23h10 초안 작성 마무리 -> 23h30 테스트 완료
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
