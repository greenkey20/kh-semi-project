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
@WebServlet("/selectkeyword2.tr")
public class selectKeyword2Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public selectKeyword2Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 단계0) get 방식으로 요청 받음 -> encoding 필요 없음
		
		// 단계1) request 객체로부터 값 뽑기
		String travelType = request.getParameter("traveltype").toUpperCase();
		String foodType = request.getParameter("foodtype").toUpperCase();
		// 브라우저 주소창에 http://localhost:8002/SEMI-PROJECT/selectkeyword2.tr?traveltype=hotel&foodtype=korean 이렇게 넘어옴
		System.out.println("여행 타입 : " + travelType); // 여행 타입 : hotel 또는 landscape 또는 activity -> 2022.1.28(금) 12h10 db 조회 시 테이블명으로 사용할 것이므로, toUpperCase()로 바꿔서 db로 넘김
		System.out.println("음식 타입 : " + foodType); // 음식 타입 : korean 또는 jeju 또는 western -> 2022.1.28(금) 12h10 db 조회 시 테이블명으로 사용할 것이므로, toUpperCase()로 바꿔서 db로 넘김
		
		// 단계2) vo로 가공 -> 쿼리문 2개에 대해 문자열 각 1개씩 넘길 것이므로, 필요 없음
		// 단, DB 조회를 위해 필요한 변수들 준비
		// DB에 가서 listCount(총 게시글 개수) 알아오기 -> 아래 랜덤숫자의 상한 값 결정
//		int travelListCount = new KeywordService().selectListCount(travelType); // 사용자가 선택한 여행 타입(HOTEL,LANDSCAPE, ACTIVITY) 테이블의 총 데이터/행 수 <- 그룹 함수 중 하나인 COUNT(*) 활용해서 BOARD 테이블로부터 조회
//		int foodListCount = new KeywordService().selectListCount(foodType); // 사용자가 선택한 음식 타입(KOREAN, JEJU, WESTERN) 테이블의 총 데이터/행 수
//		
//		int rNum1 = (int)(Math.random() * travelListCount) + 1;
//		int rNum2 = (int)(Math.random() * foodListCount) + 1;
		
		// 2022.2.3(목) 13h db에 AREA별 업체들 + 주소 추가한 뒤 기능 추가 -> 13h55 테스트 완료
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
		
		System.out.println("지역 : " + area);
		
		// 단계3) service단으로 toss
		// 사용자가 선택한 여행 타입별 추천 장소 1개 조회
		// TravelSpot ts = new TravelSpot();
		// 2022.2.1(화)? TravelSpot vo 클래스를 abstract로 변경한 뒤, 2022.2.2(화) 15h50 위 코드를 아래와 같이 수정함
		TravelSpot ts = null;
		
		/*
		Activity a = new Activity();
		
		// 2022.1.20(목) 12h10의 고민 = 추상 클래스 TravelSpot + 자식 클래스 Hotel, Landscape, Activity 구조 필요한가? 또는 사용자가 선택한 키워드별 다른 자료형으로 받아올까?
		if (travelType.equals("hotel") || travelType.equals("landscape")) {
			ts = new KeywordService().selectTravelSpotList(travelType);
			request.setAttribute("ts", ts);
		} else {
			a = new KeywordService().selectActivityList(travelType);
			request.setAttribute("a", a);
		}
		*/
		// 2022.1.28(금) 14h20
		ts = new KeywordService().selectTravelSpotList(travelType, area);
		
		// 사용자가 선택한 음식 타입별 추천 식당 3개 조회
		// 2022.1.20(목) 12h10의 고민 = 추상 클래스 Restaurant + 자식 클래스 Korean, Jeju, Western 구조 필요한가?
		ArrayList<Restaurant> rList = new KeywordService().selectRestaurantList(foodType, area);
		
		// e.g. 2022.1.24(월) 1h20 hotel + Jeju 선택 시
		System.out.println(ts); // TravelSpot [tsNo=1, category=HOTEL, name=숙소1, description=포근한 숙소1, address=제주시, phone=012-3456-7890, price=45000, filePath=null, fileName=null, status=null, titleImg=/resources/hotel.jpg]
		// System.out.println(a); // Activity [equipment=null, getTsNo()=0, getCategory()=null, getName()=null, getDescription()=null, getAddress()=null, getPhone()=null, getPrice()=0, getFilePath()=null, getFileName()=null, getStatus()=null, getTitleImg()=null, toString()=TravelSpot [tsNo=0, category=null, name=null, description=null, address=null, phone=null, price=0, filePath=null, fileName=null, status=null, titleImg=null], getClass()=class semi.keyword.model.vo.Activity, hashCode()=1246246671]
		System.out.println(rList); // [Restaurant [rsNo=18, category=JEJU, rsName=제주식당7, description=제주별미7, address=제주시, phone=012-3456-7896, menu=성게비빔밥, price=7500, filePath=null, fileName=null, status=null, titleImg=/resources/jeju.jpg], Restaurant [rsNo=8, category=JEJU, rsName=제주식당3, description=제주별미3, address=제주시, phone=012-3456-7892, menu=성게비빔밥, price=7500, filePath=null, fileName=null, status=null, titleImg=/resources/jeju.jpg], Restaurant [rsNo=16, category=JEJU, rsName=제주식당6, description=제주별미6, address=제주시, phone=012-3456-7895, menu=성게비빔밥, price=7500, filePath=null, fileName=null, status=null, titleImg=/resources/jeju.jpg]]
		
		// 단계4) DB 조회 결과를 넘기며 응답 view 지정
		request.setAttribute("ts", ts);
		request.setAttribute("rList", rList);
		
		// views/keyword/travelListView.jsp로 forwarding -> 사용자가 선택한 키워드에 따른 추천 여행지 및  추천 식당 목록이 보이는 페이지로 응답해줌
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
