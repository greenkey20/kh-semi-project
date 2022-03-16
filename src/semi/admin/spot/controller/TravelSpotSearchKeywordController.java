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

// 2022.2.1(화) 16h20
/**
 * Servlet implementation class TravelSpotSearchKeywordController
 */
@WebServlet("/searchKeyword.ads")
public class TravelSpotSearchKeywordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TravelSpotSearchKeywordController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("keyword로 업체 검색 servlet 호출~");
		
		// get 방식으로 요청 받음 -> encoding 필요 없음
		
		// request 객체로부터 값 뽑기
		String category = request.getParameter("category"); // HOTEL 또는 LANDSCAPE 또는 ACTIVITY
		String searchFilter = request.getParameter("searchFilter"); // NAME 또는 ADDRESS 또는 DESCRIPTION 또는 AREA
		String keyword = request.getParameter("keyword");
//		System.out.println(category);
//		System.out.println(searchFilter);
//		System.out.println(keyword);
		
		// + paging 처리 시 필요한 변수들 준비
		int listCount = new AdminSpotService().travelSpotSearchKeywordCount(category, searchFilter, keyword);
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
		
		// 이상의 자료들을 service단으로 toss -> currentPage(사용자가 조회 요청한 페이지)에 보여줄 + 특정 키워드 검색 결과(ArrayList에 담긴 TravelSpot 자료형)를 db로부터 조회해오기
		ArrayList<TravelSpot> tsList = new AdminSpotService().travelSpotSearchKeyword(pi, category, searchFilter, keyword);
		
		// db 조회 결과를 넘기며 응답 view 지정
		request.setAttribute("tsList", tsList);
		request.setAttribute("pi", pi);
		request.setAttribute("searchFilter", searchFilter);
		
		request.getRequestDispatcher("views/admin/travelSpotListView.jsp").forward(request, response);
		// 2022.2.1(화) 17h40 초안 작성 마무리
		// 테스트하며 나의 관찰
		// 이슈1) 특정 filter로 검색 -> 상세 조회 -> 아래 버튼으로 '목록으로 돌아가기'하면 filter 검색(x) 전체 조회(o) 결과가 나옴
		// -> travelSpotDetailView.jsp 하단 버튼 중 <a href="<%= contextPath %>/tsList.ads?category=<%= category %>&currentPage=1&boardLimit=7" class="btn btn-primary btn-sm">목록으로 돌아가기</a>를 history.back()으로 교체함
		// -> <button onclick="history.back();" class="btn btn-sm btn-secondary">목록으로 돌아가기</button>으로 교체했더니, 업체 상세 조회 -> 업체 정보 수정 -> 이 '목록으로 돌아가기' 버튼 누르면 '업체 정보 수정' 페이지로 돌아감 = 이상함
		// -> 다시 a 태그 버튼으로 되돌려둠 = 특정 filter로 검색 -> 상세 조회 -> filter 검색 결과를 다시 보려면 '목록으로 돌아가기' 버튼 사용하지 말고, 브라우저 '뒤로 가기' 화살표 눌러야 함
		// 이슈2) 이 서블릿에서 paging 처리해서 넘겼을 때, 특정 filter로 검색한 결과의 2페이지를 보다가 1페이지로 넘어가면 filter 검색(x) 전체 조회(o) 결과가 나옴 -> 일단 filter 검색에는 paging 의미 없도록 이 서블릿에서 boardLimit을 100으로 설정해둠 + 이슈1과 관련 있는 듯?
		// 이슈3) 특정 filter로 검색한 결과 목록(list A)을 보다가 다른 filter로 검색하려고 하면, 전체 목록(x) list A(o) 중에서 검색이 되는 듯 함
		// 이슈4) 특정 filter로 검색한 결과 목록을 보다가 이 category 여행지 전체 목록을 다시 보고 싶을 경우 어떻게 해야 할지 모르겠음 + 지금으로써는 뒤로 가기(단, '뒤로 가기'하면 내가 선택했던 filter 및 검색 keyword가 남아있어 내가 정확히 어떤 목록을 보고 있는 것인지 헷갈림)
		// 2022.2.1(화) 18h30 일단 테스트 마무리
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
