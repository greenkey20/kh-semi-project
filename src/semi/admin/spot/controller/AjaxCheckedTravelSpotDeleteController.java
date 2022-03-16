package semi.admin.spot.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import semi.admin.spot.model.service.AdminSpotService;

// 2022.2.2(수) 9h55
/**
 * Servlet implementation class AjaxCheckedTravelSpotDeleteController
 */
@WebServlet("/deleteCheckedTs.ads")
public class AjaxCheckedTravelSpotDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxCheckedTravelSpotDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get 방식으로 요청받음 -> encoding 필요 없음
		
		// request 객체로부터 값 뽑기
		String checkedTs = request.getParameter("checkedTs"); // 2022.2.2(수) 브라우저 console에는 length 2인 배열 ['9', '8'] 찍힘 vs Eclipse console에는 null 찍힘 -> 나의 질문 = 어떻게 해야 배열을 넘겨받을 수 있을까?
		String[] checkedArr = checkedTs.split(" ");
		
		String category = request.getParameter("category");
		
//		System.out.println(checkedTs); // 9 8 
//		System.out.println(Arrays.toString(checkedArr)); // [9, 8]
//		System.out.println(category); // ACTIVITY
		
		// service단으로 toss
		int result = new AdminSpotService().deleteCheckedTs(checkedArr, category);
		
		// 2022.2.4(금) 12h55 추가 = 형식 및 encoding 먼저 지정
//		response.setContentType("application/json; charset=UTF-8"); // 2022.2.3(목) 3h5 테스트 시 이 코드 때문에 'AJAX 통신 실패' -> 나의 질문 = 그런데 정확히 왜 이것 때문에 AJAX 통신 실패가 되었는지 모르겠음 ㅠ.ㅠ -> 2022.2.4(금) 12h55 나의 생각 = json(x) 일반 text(o)로 응답을 보내고 있기 때문에? 
		response.setContentType("text/html; charset=UTF-8");
		
		if (result == checkedArr.length) { // 선택한 여행지 삭제 성공한 경우
			response.getWriter().print("YY");
		} else { // 선택한 여행지 삭제 실패한 경우
			response.getWriter().print("NN");
		}
		// 2022.2.2(수) 10h30 초안 작성 마무리 -> 10h35 테스트 완료
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
