package semi.review.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

import semi.common.model.vo.PageInfo;
import semi.review.model.service.ReviewService;
import semi.review.model.vo.Review;

// 2022.1.23(일) 21h15
/**
 * Servlet implementation class AjaxReviewListController
 */
@WebServlet("/tsListPaging.rv")
public class AjaxTsReviewListPagingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxTsReviewListPagingController() {
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
		String category = request.getParameter("category"); // HOTEL 또는 LANDSCAPE (2022.1.28(금) 15h25 추가)또는 ACTIVITY
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
//		System.out.println(tsNo);
//		System.out.println(category); // HOTEL 또는 LANDSCAPE 또는 ACTIVITY
//		System.out.println(currentPage); // 1
		
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
		
		// 이상 paging 관련 7개 변수들을 service단으로 전달 -> 사용자가 조회 요청한 페이지(currentPage)에  보여줄 리뷰 글들(ArrayList에 담긴 Review 객체들)을 DB로부터 조회해오고자 함
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
		
//		System.out.println(pi); // 리뷰 아예 없는 숙소의 경우 PageInfo [listCount=0, currentPage=1, pageLimit=4, boardLimit=4, maxPage=0, startPage=1, endPage=0]로 찍힘
		
		// 동기식 방법 = list와 pi 넘기며, 응답 view 지정
//		request.setAttribute("rvList", rvList);
//		request.setAttribute("pi", pi);
//		request.getRequestDispatcher("views/keyword/travelListView.jsp").forward(request, response);
		// 2022.1.24(월) 1h 초안 마무리
		
		// 2022.1.24(월) 11h30 비동기식 방법
		// 2022.1.24(월) ArrayList에 rvList와 pi를 담아서 gson으로 넘겼을 때, 17h45 이 servlet에서 request 객체의 tsNo, category, currentPage 및 db 조회 결과 pi, rvList 다 찍히는 것 같은데, AJAX success에서 이 객체들을 못 읽어오겠다 ㅠ.ㅠ
		// 2022.1.24(월) 18h20 JSONArray에 담는 것으로 바꿔봄
		//JSONArray jArr = new JSONArray();
		
		//jArr.add(rvList);
		//jArr.add(pi);
		// 2022.1.25(화) 10h35 브라우저 개발자 도구 > network > response에 아래와 같이 응답 가긴 하는 것 같은데, console에는 'db로부터 리뷰 목록 읽어오기 실패' 뜸
		// [[Review [reviewNo=0, reviewWriter=null, reviewWriterId=user05, reviewWriterName=사*자5, sNo=5, category=HOTEL, reviewContent=좋아요, createDate=2022-01-16, status=null, filePath=null, fileName=null, titleImg=null]],PageInfo [listCount=1, currentPage=1, pageLimit=4, boardLimit=4, maxPage=1, startPage=1, endPage=1]]
		
		/*
		2022.1.25(화) 14h 강사님께 질문 = 리뷰확인 버튼을 누르면 디비에서 리뷰와 페이징바를 비동기식으로 표현할수있는걸 만드려고 했다.
		JSP에서 ajax로 서블릿에 요청을 해서 데이터를 전달했습니다 -> 서블릿은 받아온 데이터를 잘 가지고 service 및 Dao를 통해 디비로부터 값을 잘 받아왔습니다.
		그러나 서블릿에서 다시 jsp 로 응답을 보낼때 계속해서 error로 빠집니다
		그래서 전송할때 문제가 있었는지 콘솔을 찍어서 값들이 담겨있는것을 다 확인했고 datetype이나 contentType 모두 확인했습니다 모두 Error 로 넘어가지고 또 complete 로 함수 매개변수를 찍어봤을때 값이 넘어옵니다 저희가 생각했을때 서블릿이 jsp로 준 값이 Success에서만 받아올수없는 무언가 벽이있는데 뭔지모르겠습니다 참고로 저희는 서블릿에서 jsonArray로 데이터를 가공해서 전달했습니다.
		*/
		
		// 15h 강사님께서 도와주심
		//JSONObject jObj = new JSONObject();
		//jObj.put("rvList", rvList);
		//jObj.put("pi", pi);
		//jObj.put("hi", 123);
		//response.getWriter().print(jObj); // .toJSONString()
		
		/* 15h45 강사님의 진단 = JSON Array(JavaScript 배열)의 요소로 Java 객체를 넣으면 parsing을 못 해옴 + json list 그냥 넘기면 주소 값 보내므로 toString() 써봤는데도 안 됨 vs 해결방법(ideas)
		 1. b(binary)son 사용
		 2. Java 객체를 json object로 변환해서 json array에 넣어보기 -> 16h 아래에 시도해봤으나, 잘 안 되어서 방법4 시도해봄
		 3. 리뷰 페이지 따로 빼서 만들기
		 4. 페이징 결과를 먼저 ajax 응답한 뒤, ajax success 들어오면 리뷰 불러오는 ajax 요청 보냄 -> 16h50 작동하는 듯?
		*/
		
		response.setContentType("application/json; charset=UTF-8");
		
		// 2022.1.25(화) 16h 방법2 시도
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
		*/
		
		JSONObject jPi = new JSONObject();
		jPi.put("listCount", pi.getListCount());
		jPi.put("currentPage", pi.getCurrentPage());
		jPi.put("pageLimit", pi.getPageLimit());
		jPi.put("boardLimit", pi.getBoardLimit());
		jPi.put("maxPage", pi.getMaxPage());
		jPi.put("startPage", pi.getStartPage());
		jPi.put("endPage", pi.getEndPage());
		
//		JSONArray jArr = new JSONArray();
//		jArr.add(jRvList);
//		jArr.add(jPi);
		
		response.getWriter().print(jPi); // .toJSONString()
		
		// 2022.1.25(화) 13h15
//		new Gson().toJson(pi, response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
