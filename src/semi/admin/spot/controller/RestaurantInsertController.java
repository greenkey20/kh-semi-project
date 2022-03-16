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

// 2022.2.2(수) 18h20
/**
 * Servlet implementation class RestaurantInsertController
 */
@WebServlet("/insertRestaurant.ads")
public class RestaurantInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// post 방식으로 요청받음 -> 단계0) encoding 방식 설정
		request.setCharacterEncoding("UTF-8");
		
		if (ServletFileUpload.isMultipartContent(request)) { // enctype이 multipart/form-data로 잘 전송된 경우 = ServletFileUpload.isMultipartContent() 메소드가 true 반환
			
			int maxSize = 1024 * 1024 * 3;
			String savePath = request.getSession().getServletContext().getRealPath("/resources/restaurant_upfiles");
			
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			
			// 단계1) (multipart)request 객체로부터 값 뽑기
			String category = multiRequest.getParameter("category"); // KOREAN 또는 JEJU 또는 WESTERN
			String name = multiRequest.getParameter("name");
			String description = multiRequest.getParameter("description");
			String address = multiRequest.getParameter("address");
			String phone = multiRequest.getParameter("phone");
			String menu = multiRequest.getParameter("menu");
			int price = Integer.parseInt(multiRequest.getParameter("price"));
			String filePath = "/resources/restaurant_upfiles/"; // 첨부파일의 저장 경로
			String fileName = multiRequest.getFilesystemName("file1"); // 첨부파일의 수정 파일명
			String area = multiRequest.getParameter("area");
			
			// 단계2) vo 객체로 가공
			// 단계2a) 식당(Restaurant) 객체로 가공 -> RESTAURANT 테이블에 INSERT
			Restaurant r = new Restaurant(category, name, description, address, phone, menu, price, filePath, fileName, area);

			// 단계2b) Attachment 객체로 가공; 대표 이미지 1장은 required + 최대 3장까지 상세 이미지 추가 가능 -> ArrayList<Attachment>에 담아 service단에 넘기고자 함 -> Attachment 테이블에 INSERT
			ArrayList<Attachment> fList = new ArrayList<>();
			
			for (int i = 1; i <= 4; i++) {
				String key = "file" + i;
				
				if (multiRequest.getOriginalFileName(key) != null) { // name 속성 "keyx"로 넘긴 첨부파일의 원본 파일(명)이 존재한다면 = 해당 첨부파일이 존재할 경우 -> Attachment 객체 생성 + fList의 요소로 추가
					Attachment at = new Attachment();
					
					at.setRefCategory(category);
					at.setOriginName(multiRequest.getOriginalFileName(key)); // 첨부파일의 원본 파일명
					at.setChangeName(multiRequest.getFilesystemName(key)); // 첨부파일의 수정 파일명 -> 처음 작성 시 70행의 'fileName'을 setter의 매개변수로 입력했더니, 모든 첨부파일의 변경 후 파일명이 titleImg와 같게 된 바, 2022.1.31(월) 23h50 '여행지 상세 보기' 테스트 시 titleImg와 상세이미지1가 모두 같은 사진으로 표시되는 오류가 발생했음
					at.setFilePath(filePath); // 첨부파일의 저장 경로
					
					if (i == 1) { // 첨부파일이 대표 이미지인 경우 = name 속성 값이 file1
						at.setFileLevel(1);
					} else { // 첨부파일이 상세 이미지인 경우 = name 속성 값이 file2~4
						at.setFileLevel(2);
					}
					
					fList.add(at);
				} // 첨부파일이 있는 경우 if문 영역 끝
			} // for문 영역 끝
			
			// 단계3) service단으로 toss
			int result = new AdminSpotService().insertRestaurant(r, fList);
			
			// 단계4) 처리 결과에 따라 응답 페이지 결정 
			if (result > 0) { // db에 식당 등록 및 첨부파일 추가 성공 시
				request.getSession().setAttribute("alertMsg", "식당 추가/등록에 성공했습니다");
				response.sendRedirect(request.getContextPath() + "/rList.ads?categoryR=" + category + "&currentPage=1&boardLimit=7"); // 가장 최신 업체이므로 tsList.ads?/currentPage=1 요청
			} else { // db에 식당 등록 실패 또는 첨부파일 추가 실패 시
				// new File(savePath + fileName).delete(); // multiRequest 객체 생성하며 서버에 업로드해둔/저장된 첨부 파일을 서버에 보관할 이유가 없는 바, 서버 공간으로부터 제거/삭제함
				// 2022.2.1(화) 10h20 나의 생각 = multiRequest 객체 생성 시 대표 이미지 뿐만 아니라 상세 이미지들도 업로드된 것 아닌가? -> file1~4 모두 삭제/제거하도록 수정
				for (int i = 0; i <= fList.size(); i++) {
					new File(savePath + fList.get(i).getChangeName()).delete();
				}
				
				request.setAttribute("errorMsg", "식당 추가/등록에 실패했습니다");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}
			
		} // enctype이 multipart/form-data로 잘 전송된 경우 if문 영역 끝
		// 2022.2.2(수) 18h50 초안 작성 마무리 -> 18h55 테스트 완료
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
