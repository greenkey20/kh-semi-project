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
import semi.keyword.model.vo.TravelSpot;

// 2022.2.1(화) 0h25
/**
 * Servlet implementation class TravelSpotUpdateFormController
 */
@WebServlet("/updateForm.ads")
public class TravelSpotUpdateFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TravelSpotUpdateFormController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// a 태그를 통해 'default = get 방식'으로 요청 받음 -> encoding 필요 없음
		
		// request 객체로부터 값 뽑기
		int tsNo = Integer.parseInt(request.getParameter("tsNo"));
		String category = request.getParameter("category"); // HOTEL 또는 LANDSCAPE 또는 ACTIVITY
		
		// 여행지 상세 조회 기능 구현 시, 해당 여행지 정보 및 관련 첨부파일을 db에서 조회해오기 위해 만들어놓은 메소드/기능 재활용 가능
		TravelSpot ts = new AdminSpotService().selectTravelSpot(tsNo, category);
		ArrayList<Attachment> fList = new AdminSpotService().selectAttachmentList(tsNo, category);
		
		// view 단으로 값 넘기기
		request.setAttribute("ts", ts);
		request.setAttribute("fList", fList);
		
		// RequestDispatcher 객체를 이용(+응답 페이지 지정)해서 forwarding
		request.getRequestDispatcher("views/admin/travelSpotUpdateForm.jsp").forward(request, response);
		// 2022.2.1(화) 0h35 초안 작성 마무리
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
