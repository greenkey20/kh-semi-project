package semi.keyword.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import semi.keyword.model.service.KeywordService;
import semi.keyword.model.vo.Restaurant;
import semi.keyword.model.vo.TravelSpot;

// 2022.1.19(수) 16h
/**
 * Servlet implementation class selectKeywordController
 */
@WebServlet("/selectkeyword2copy.tr")
public class selectKeyword2ControllerCopy extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public selectKeyword2ControllerCopy() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String travelType = request.getParameter("traveltype").toUpperCase();
		String foodType = request.getParameter("foodtype").toUpperCase();
		
		int ranNum = (int)(Math.random() * 3) + 1; // 1~3 중 정수 1개를 무작위로 뽑음
		String area = ""; // "제주" 또는 "서귀포" 또는 "우도부근"
		
		switch (ranNum) {
		case 1 :
			area = "제주";
			break;
		case 2 :
			area = "서귀포";
			break;
		case 3 :
			area = "우도부근";
		}
		
		TravelSpot ts = new KeywordService().selectTravelSpotList(travelType, area);
		request.setAttribute("ts", ts);

		ArrayList<Restaurant> rList = new KeywordService().selectRestaurantList(foodType, area);
		request.setAttribute("rList", rList);
		
		request.getRequestDispatcher("views/keyword/travelListView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
