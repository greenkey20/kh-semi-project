package semi.admin.spot.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import semi.admin.spot.model.service.AdminSpotService;

// 2022.2.3(목) 2h10
/**
 * Servlet implementation class RestaurantDeleteController
 */
@WebServlet("/deleteRs.ads")
public class RestaurantDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String category = request.getParameter("category"); // KOREAN 또는 JEJU 또는 WESTERN
		int rsNo = Integer.parseInt(request.getParameter("rsNo"));
		
		int result = new AdminSpotService().deleteRestaurant(rsNo);
		
		if (result > 0) {
			request.getSession().setAttribute("alertMsg", "식당 삭제에 성공했습니다");
			response.sendRedirect(request.getContextPath() + "/rList.ads?categoryR=" + category + "&currentPage=1&boardLimit=7");
		} else {
			request.setAttribute("errorMsg", "식당 삭제에 실패했습니다");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
		// 2022.2.3(목) 2h25 초안 작성 마무리 -> 2h30 테스트 완료
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
