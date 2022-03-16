package semi.admin.spot.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import semi.admin.spot.model.service.AdminSpotService;

// 2022.2.3(목) 2h35
/**
 * Servlet implementation class AjaxCheckedRestaurantDeleteController
 */
@WebServlet("/deleteCheckedRs.ads")
public class AjaxCheckedRestaurantDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxCheckedRestaurantDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String checkedRs = request.getParameter("checkedRs");
		String[] checkedArr = checkedRs.split(" ");
				
		String category = request.getParameter("category");
		
		int result = new AdminSpotService().deleteCheckedRs(checkedArr);
		
		// 2022.2.4(금) 12h55 추가 = 형식 및 encoding 먼저 지정
		response.setContentType("text/html; charset=UTF-8");
		
		if (result == checkedArr.length) {
			response.getWriter().print("YY");
		} else {
			response.getWriter().print("NN");
		}
		// 2022.2.3(목) 2h50 초안 작성 마무리 -> 3h5 테스트 완료
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
