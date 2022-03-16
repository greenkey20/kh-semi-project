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
import semi.keyword.model.vo.TravelSpot;

// 2022.1.29(토) 21h40
/**
 * Servlet implementation class HotelListController
 */
@WebServlet("/tsList.ads")
public class TravelSpotlListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TravelSpotlListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get 방식으로 요청 보냄 -> encoding 필요 없음
		
		// request 객체로부터 값 뽑기
		String category = request.getParameter("category"); // HOTEL 또는 LANDSCAPE 또는 ACTIVITY
		int currentPage = Integer.parseInt(request.getParameter("currentPage")); // 1
		/*
		int boardLimit = 7; // 한 페이지에 보여질 게시글의 최대 개수
		
		if (!request.getParameter("boardLimit").equals("")) {
			boardLimit = Integer.parseInt(request.getParameter("boardLimit"));
		}
		*/
		// 2022.1.31(월) 16h45 이 서블릿 호출 시 항상 boardLimit 값 넘겨준다고 가정하고, 그냥 아래와 같이 설정함
		int boardLimit = Integer.parseInt(request.getParameter("boardLimit"));
		
		// + paging 처리 시 필요한 변수들 준비
		int listCount = new AdminSpotService().selectTravelSpotListCount(category);
		int pageLimit = 5; // 게시글 list 보여주는 페이지 하단의  paging bar/buttons의 최대 개수
		int maxPage = (int)Math.ceil((double)listCount / boardLimit); // 가장 마지막 페이지가 몇 번 페이지인지 = 페이지의 총 개수
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1; // 페이지 하단에 보여질 첫번째 paging bar
		int endPage = startPage + pageLimit - 1; // 페이지 하단에 보여질 마지막 paging bar
		if (endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);

		// 2022.1.30(일) 3h20 여행지 테이블에 AREA 컬럼 추가 + 여행지 관리 페이지에서 AREA별 조회 필터 추가 -> 이 서블릿 수정
		/*
		String area = "";
		TravelSpot ts = new TravelSpot();
		ArrayList<TravelSpot> tsList = new ArrayList<>();
		
		if (!request.getParameter("area").equals("")) {
			area = request.getParameter("area");
			ts.setCategory(category);
			ts.setArea(area);
			tsList = new TravelSpotService().selectTravelSpotList(ts, pi);
		} else {
			ts.setCategory(category);
			tsList = new TravelSpotService().selectTravelSpotList(ts, pi);
		}
		*/
		
		// 이상 paging 관련 7개 변수 및 category 변수를 service단으로 전달 -> currentPage(사용자가 조회 요청한 페이지)에 보여줄 + 특정 여행 타입 관련 업체 목록(ArrayList에 담긴 TravelSpot 자료형)을 db로부터 조회해오고자 함
		ArrayList<TravelSpot> tsList = new AdminSpotService().selectTravelSpotList(category, pi);
		// cf. KeywordService/KeywordDao에도 selectTravelSpotList() 메소드 있으나, 기능 = 여행 타입별 업체 1개 랜덤으로 추출 -> 반환 자료형 = TravelSpot
		
		// db 조회 결과를 넘기며 응답 view 지정
		request.setAttribute("tsList", tsList);
		request.setAttribute("pi", pi);		
//		request.getRequestDispatcher("views/admin/" + category.toLowerCase() + "ListView.jsp").forward(request, response);
		// 2022.1.30(일) 0h50 초안 작성 마무리
		
		// 2022.1.31(월) 10h20 주말에 hotelListView.jsp로 만든 페이지를 HOTEL, LANDSCAPE, ACTIVITY 목록 모두에 대해 사용할 수 있도록 수정하고 travelSpotListView.jsp로 이름 변경
		request.getRequestDispatcher("views/admin/travelSpotListView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
