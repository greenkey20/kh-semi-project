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

// 2022.1.25(화) 16h30
/**
 * Servlet implementation class AjaxTsReviewListController
 */
@WebServlet("/tsList.rv")
public class AjaxTsReviewListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxTsReviewListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int tsNo = Integer.parseInt(request.getParameter("tsNo"));
		String category = request.getParameter("category"); // HOTEL 또는 LANDSCAPE (2022.1.28(금) 15h40 추가)또는 ACTIVITY
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		// + paging 처리 시 필요한 변수들 준비
		int listCount = new ReviewService().selectTsReviewListCount(tsNo, category); // hotel/landscape/activityReview 테이블 + 해당 숙소/식당 번호 + status 'Y'인 리뷰 총 개수
		int pageLimit = 4;
		int boardLimit = 4;
		
		// paging bar/buttons 관련
		int maxPage = (int)Math.ceil((double)listCount / boardLimit);
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit - 1;
		if (endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);

		// 이상 paging 관련 7개 변수들을 service단으로 전달 -> 사용자가 조회 요청한 페이지(currentPage)에  보여줄 리뷰 글들(ArrayList에 담긴 Review 객체들)을 DB로부터 조회해오고자 함
		ArrayList<Review> rvList = new ReviewService().selectTsReviewList(tsNo, category, pi);
		
//		System.out.println(pi); // 2022.1.26(수) 10h25 문제점 = LANDSCAPE 리뷰 1개 있는데, listCount 0으로 찍힘 -> 비활성화 '1' 버튼 안 뜸 
//		System.out.println(rvList); // 리뷰 아예 없는 숙소의 경우 []로 찍힘 vs [Review [reviewNo=0, reviewWriter=null, reviewWriterId=user01, reviewWriterName=사*자1, sNo=1, category=LANDSCAPE, reviewContent=웅장해요, createDate=2022-01-12, status=null, filePath=null, fileName=null, titleImg=null]]
		
		response.setContentType("application/json; charset=UTF-8");
		
		/*
		JSONArray jRvList = new JSONArray();
		for (Review rv : rvList) {
			JSONObject jObj = new JSONObject();
			jObj.put("reviewWriterId", rv.getReviewWriterId());
			jObj.put("reviewWriterName", rv.getReviewWriterName());
			jObj.put("sNo", rv.getsNo());
			jObj.put("category", rv.getCategory());
			jObj.put("reviewContent", rv.getReviewContent());
			jObj.put("createDate", rv.getCreateDate());
			jObj.put("titleImg", rv.getTitleImg());
			
			jRvList.add(jObj);
		}
		
		response.getWriter().print(jRvList);
		*/
		
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
