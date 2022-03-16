package semi.admin.spot.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;

import semi.admin.spot.model.service.AdminSpotService;
import semi.admin.spot.model.vo.Attachment;
import semi.common.MyFileRenamePolicy;
import semi.keyword.model.vo.Restaurant;

// 2022.2.3(목) 0h30
/**
 * Servlet implementation class RestaurantUpdateController
 */
@WebServlet("/updateRestaurant.ads")
public class RestaurantUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// post 방식으로 요청 받음 -> 단계0) encoding 방식 설정 필요
		request.setCharacterEncoding("UTF-8");
		
		if (ServletFileUpload.isMultipartContent(request)) {
			int maxSize = 1024 * 1024 * 3;
			String savePath = request.getSession().getServletContext().getRealPath("/resources/restaurant_upfiles");
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			
			// 단계1) multiRequest 객체로부터 값 뽑기
			int rsNo = Integer.parseInt(multiRequest.getParameter("rsNo"));
			String category = multiRequest.getParameter("category"); // KOREAN 또는 JEJU 또는 WESTERN
			String name = multiRequest.getParameter("name");
			String description = multiRequest.getParameter("description");
			String address = multiRequest.getParameter("address");
			String phone = multiRequest.getParameter("phone");
			String menu = multiRequest.getParameter("menu");
			int price = Integer.parseInt(multiRequest.getParameter("price"));
			String filePath = "/resources/restaurant_upfiles/"; // 첨부파일의 저장 경로
			
			String fileName = "";
			if (multiRequest.getOriginalFileName("reUpfile1") != null) { // 대표 이미지를 새로 업로드하는 경우
				fileName = multiRequest.getFilesystemName("reUpfile1"); // 새로 업로드하는 대표 이미지의 수정 파일명
			} else { // 대표 이미지를 새로 업로드하지 않는 경우 = 기존 대표 이미지를 사용하고자 하는 경우
				fileName = multiRequest.getParameter("originFileName1"); // 기존에 업로드되어있던 대표 이미지의 수정 파일명
			}
			
			String area = multiRequest.getParameter("area");
			
			// 단계2) vo 객체로 가공
			// 단계2a) 식당(Restaurant) 객체로 가공 -> RESTAURANT 테이블의 관련 행 UPDATE
			Restaurant r = new Restaurant(rsNo, category, name, description, address, phone, menu, price, filePath, fileName, area);
			
			// 단계2b) Attachment 객체로 가공; 대표 이미지 1장은 required + 최대 3장까지 상세 이미지 추가 가능 -> ArrayList<Attachment>에 담아 service단에 넘기고자 함 -> Attachment 테이블에  UPDATE 또는 INSERT
			ArrayList<Attachment> fList = new ArrayList<>();
			
			for (int i = 1; i <= 4; i++) { // name 속성의 값 "reUpfile1~4"를 가진 file input에 대해 아래 내용을 확인/처리하고자 함 
				String key = "reUpfile" + i;
				Attachment at = null; // reUpfile이 없다면 이 객체 생성할 필요 없음
				
				if (multiRequest.getOriginalFileName(key) != null) { // 새로 업로드하는 파일이 있는 경우
					at = new Attachment();
					
					if (multiRequest.getParameter("originFileName" + i) != null) { // 기존에 업로드된 파일이 있는 경우 -> 새로 업로드하는 파일의 정보를 기존 업로드된 파일 행에 덮어쓰기(=기존 파일 행을 UPDATE)하고자 함
						at.setFileNo(Integer.parseInt(multiRequest.getParameter("originFileNo" + i))); // 새로 업로드하는 파일의 파일 번호는 기존 파일의 번호로 교체
						new File(savePath + multiRequest.getParameter("originFileName" + i)).delete(); // 기존에 업로드된 파일(수정 파일명으로 서버에 저장되어있음)은 서버로부터 삭제
					} else { // 기존에 업로드된 파일이 없는 경우 -> ATTACHMENT 테이블에 새로운 첨부파일 관련 행 INSERT
						
					}
					
					at.setRefTsNo(rsNo); // 새로 업로드하는 파일이 참조하는(=현재 정보 수정 중인) 식당 번호 입력
					at.setRefCategory(category); // 새로 업로드하는 파일이 참조하는 음식 타입 입력
					at.setOriginName(multiRequest.getOriginalFileName(key)); // 새로 업로드하는 파일의 원본 파일명 입력
					at.setChangeName(multiRequest.getFilesystemName(key)); // 새로 업로드하는 파일의 수정 파일명 입력
					at.setFilePath(filePath); // 새로 업로드하는 파일의 저장 경로 입력
					
					if (i == 1) { // 첨부파일이 대표 이미지인 경우 = name 속성 값이 file1
						at.setFileLevel(1);
					} else { // 첨부파일이 상세 이미지인 경우 = name 속성 값이 file2~4
						at.setFileLevel(2);
					}
					
					fList.add(at);
				} // 새로 업로드하는 파일이 있는 경우 if문 영역 끝 vs 새로 업로드하는 파일이 없는 경우 = 기존 파일을 사용하는 경우, 처리(UPDATE 또는 INSERT)할 일 없음
			} // for문 영역 끝
			
			// 단계3) service단으로 toss
			// case1) 새로 업로드하는 파일이 있음 + 기존에 업로드된 파일이 있음 ->  UPDATE RESTAURANT + UPDATE ATTACHMENT
			// case2) 새로 업로드하는 파일이 있음 + 기존에 업로드된 파일은 없음 -> UPDATE RESTAURANT + INSERT ATTACHMENT
			// case3) 새로 업로드하는 파일 없음 -> UPDATE RESTAURANT
			int result = new AdminSpotService().updateRestaurant(r, fList);
			
			if (result > 0) {
				request.getSession().setAttribute("alertMsg", "식당 정보 수정에 성공했습니다");
				response.sendRedirect(request.getContextPath() + "/detailRs.ads?rsNo=" + rsNo + "&category=" + category + "&currentPage=1");
			} else {
				request.setAttribute("errorMsg", "식당 정보 수정에 실패했습니다");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}
		} // enctype이 multipart/form-data로 잘 전송된 경우 if문 영역 끝
		// 2022.2.3(목) 1h40 초안 작성 마무리 -> 2h10 테스트 완료
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
