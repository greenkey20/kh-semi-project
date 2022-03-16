package semi.admin.spot.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import semi.admin.spot.model.service.AdminSpotService;
import semi.admin.spot.model.vo.Attachment;
import semi.common.model.vo.PageInfo;
import semi.keyword.model.vo.TravelSpot;
import semi.review.model.service.ReviewService;
import semi.review.model.vo.Review;

// 2022.1.31(월) 18h
/**
 * Servlet implementation class TravelSpotDetailController
 */
@WebServlet("/detail.ads")
public class TravelSpotDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TravelSpotDetailController() {
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
		String category = request.getParameter("category"); // HOTEL 또는 LANDSCAPE 또는 ACTIVITY
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		// service단으로 toss
		// part1) db로부터 상세 보기 대상 TravelSpot 조회
		TravelSpot ts = new AdminSpotService().selectTravelSpot(tsNo, category);
		// part2) db로부터 첨부파일 조회해오기; 대표 이미지 경로 및 파일명은 ts에 가져옴 -> fList에는 상세 이미지(fileLevel 2)(들)만 가져오면 됨  -> 2022.2.1(화) 1h10 여행지 정보 수정 시 대표 이미지의 originName이 필요해서, 해당 여행지 관련 모든 첨부파일을 fList에 가져오는 것으로 수정함 
		ArrayList<Attachment> fList = new AdminSpotService().selectAttachmentList(tsNo, category);
//		System.out.println(fList);
		
		// part3) 2022.2.1(화) 22h 상세 조회 페이지에서 해당 여행지 관련 리뷰 목록도 보여주고 싶어서 추가 -> 23h15 화면 구현 및 테스트 완료
		// ReviewService/Dao에 ArrayList<Review> selectTsReviewList(Connection conn, int tsNo, String category, PageInfo pi) 메소드 만들어 둔 것 있음
		int listCount = new ReviewService().selectTsReviewListCount(tsNo, category);
		int pageLimit = 5;
		int boardLimit = 10;
		int maxPage = (int)Math.ceil((double)listCount / boardLimit);
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit - 1;
		if (endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
		
		ArrayList<Review> rvList = new ReviewService().selectTsReviewList(tsNo, category, pi);
		
		// 처리 결과를 응답 view로 넘김 + 응답 page 지정
		request.setAttribute("ts", ts);
		request.setAttribute("fList", fList);
		request.setAttribute("rvList", rvList);
		request.setAttribute("pi", pi);
		
		request.getRequestDispatcher("views/admin/travelSpotDetailView.jsp").forward(request, response);
		// 2022.1.31(월) 19h55 초안 작성 마무리
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
