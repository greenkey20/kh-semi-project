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
import semi.keyword.model.vo.Restaurant;

// 2022.2.2(수) 23h30
/**
 * Servlet implementation class RestaurantUpdateFormController
 */
@WebServlet("/updateRsForm.ads")
public class RestaurantUpdateFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantUpdateFormController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String category = request.getParameter("category"); // KOREAN 또는 JEJU 또는 WESTERN
		int rsNo = Integer.parseInt(request.getParameter("rsNo"));
		
		Restaurant r = new AdminSpotService().selectRestaurant(rsNo);
		ArrayList<Attachment> fList = new AdminSpotService().selectAttachmentList(rsNo, category);
		
		// 응답 페이지로 넘기기 + 응답 페이지 지정
		request.setAttribute("r", r);
		request.setAttribute("fList", fList);
		
		request.getRequestDispatcher("views/admin/restaurantUpdateForm.jsp").forward(request, response);
		// 2022.2.2(수) 23h40 초안 작성 마무리
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
