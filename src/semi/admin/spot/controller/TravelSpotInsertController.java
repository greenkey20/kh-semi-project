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
import semi.keyword.model.vo.Activity;
import semi.keyword.model.vo.Hotel;
import semi.keyword.model.vo.Landscape;
import semi.keyword.model.vo.TravelSpot;

// 2022.1.30(일) 4h40
/**
 * Servlet implementation class TravelSpotInsertController
 */
@WebServlet("/insertTravelSpot.ads")
public class TravelSpotInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TravelSpotInsertController() {
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
//			System.out.println("업체 등록 잘 되는지 확인해봅니다 ^^"); // 2022.1.31(월) 13h5 확인
			
			int maxSize = 1024 * 1024 * 3;
			String savePath = request.getSession().getServletContext().getRealPath("/resources/travelspot_upfiles");
//			System.out.println(maxSize); // 3145728
//			System.out.println(savePath); // C:\Semi-workspace\SEMI-PROJECT\WebContent\resources\travelspot_upfiles
			// + 2022.1.31(월) 13h5 위 경로에 2022013113041144187.jpg 파일 업로드되어있음
			
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			
			// 단계1) 2022.1.31(월) 11h15~20 (multipart)request 객체로부터 값 뽑기
			// HOTEL, LANDSCAPE, ACTIVITY 공통적인 컬럼들
			String category = multiRequest.getParameter("category"); // HOTEL 또는 LANDSCAPE 또는 ACTIVITY
//			System.out.println(category);
			String name = multiRequest.getParameter("name");
			// 2022.1.31(월) 13h5
			String description = multiRequest.getParameter("description");
			String address = multiRequest.getParameter("address");
			String phone = multiRequest.getParameter("phone");
			int price = Integer.parseInt(multiRequest.getParameter("price"));
			String filePath = "/resources/travelspot_upfiles/"; // 첨부파일의 저장 경로
			String fileName = multiRequest.getFilesystemName("file1"); // 첨부파일의 수정 파일명
			String area = multiRequest.getParameter("area");
			
			// ACTIVITY 고유의 컬럼
			String equipment = "";
			if (category.equals("ACTIVITY")) {
				equipment = multiRequest.getParameter("equipment");
			}
			
			// 단계2) vo 객체로 가공
			// 단계2a) 여행지(TravelSpot) 객체로 가공 -> HOTEL 또는 LANDSCAPE 또는 ACTIVITY 테이블에 INSERT
			TravelSpot ts = null;
			
			switch (category) {
			case "HOTEL" :
				ts = new Hotel(category, name, description, address, phone, price, filePath, fileName, area);
				break;
			case "LANDSCAPE" :
				ts = new Landscape(category, name, description, address, phone, price, filePath, fileName, area);
				break;
			case "ACTIVITY" :
				ts = new Activity(category, name, description, address, phone, price, filePath, fileName, area, equipment);
			}
			
//			System.out.println(ts); // 처음 테스트 시 null <- TravelSpot의 자식 vo 클래스상 위와 같이 객체 생성하는 생성자 super()에 값을 대입 안 해줬음
//			TravelSpot [tsNo=0, category=HOTEL, name=숙소11, description=럭셔리한 숙소11, address=서귀포시, phone=012-3456-7900, price=75000, filePath=/resources/travelspot_upfiles/, fileName=2022013117453425708.jpg, status=null, titleImg=null]
//			TravelSpot [tsNo=0, category=LANDSCAPE, name=풍경명소8, description=화사한 풍경8, address=서귀포시, phone=012-3456-7897, price=2000, filePath=/resources/travelspot_upfiles/, fileName=2022013117431944644.jpg, status=null, titleImg=null]
//			Activity [equipment=물안경, 개인 세면도구, getTsNo()=0, getCategory()=ACTIVITY, getName()=액티비티12, getDescription()=이색적인 활동12, getAddress()=서귀포시, getPhone()=012-3456-7901, getPrice()=12000, getFilePath()=/resources/travelspot_upfiles/, getFileName()=2022013117482915721.jpg, getStatus()=null, getTitleImg()=null, toString()=TravelSpot [tsNo=0, category=ACTIVITY, name=액티비티12, description=이색적인 활동12, address=서귀포시, phone=012-3456-7901, price=12000, filePath=/resources/travelspot_upfiles/, fileName=2022013117482915721.jpg, status=null, titleImg=null], getClass()=class semi.keyword.model.vo.Activity, hashCode()=202799623]
			
			// 단계2b) Attachment 객체로 가공; 대표 이미지 1장은 required + 최대 3장까지 상세 이미지 추가 가능 -> ArrayList<Attachment>에 담아 service단에 넘기고자 함 -> Attachment 테이블에 INSERT
			ArrayList<Attachment> fList = new ArrayList<>();
			
			for (int i = 1; i <= 4; i++) {
				String key = "file" + i;
				
				if (multiRequest.getOriginalFileName(key) != null) { // name 속성 "keyx"로 넘긴 첨부파일의 원본 파일(명)이 존재한다면 = 해당 첨부파일이 존재할 경우 -> Attachment 객체 생성 + fList의 요소로 추가
					// 나의 궁금증 = multiRequest.getParameter(key)로 해당 name에 해당하는 첨부파일이 있/없는지 확인해서 조건 걸 수 있을까? 이 때 이 parameter의 value는 파일인 것일까? 수업시간 web2 BoardUpdateController에서 이걸로 조건문 쓴 적 있긴 함
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
			int result = new AdminSpotService().insertTravelSpot(ts, fList);
			
			// 단계4) 처리 결과에 따라 응답 페이지 결정 
			if (result > 0) { // db에 TravelSpot 등록 및 첨부파일 추가 성공 시
				request.getSession().setAttribute("alertMsg", "여행지 추가/등록에 성공했습니다");
				response.sendRedirect(request.getContextPath() + "/tsList.ads?category=" + category + "&currentPage=1&boardLimit=7"); // 가장 최신 업체이므로 tsList.ads?/currentPage=1 요청
			} else { // db에 TravelSpot 등록 실패 또는 첨부파일 추가 실패 시
				// new File(savePath + fileName).delete(); // multiRequest 객체 생성하며 서버에 업로드해둔/저장된 첨부 파일을 서버에 보관할 이유가 없는 바, 서버 공간으로부터 제거/삭제함
				// 2022.2.1(화) 10h20 나의 생각 = multiRequest 객체 생성 시 대표 이미지 뿐만 아니라 상세 이미지들도 업로드된 것 아닌가? -> file1~4 모두 삭제/제거하도록 수정
				for (int i = 0; i <= fList.size(); i++) {
					new File(savePath + fList.get(i).getChangeName()).delete();
				}
				
				request.setAttribute("errorMsg", "여행지 추가/등록에 실패했습니다");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}
			
		} // enctype이 multipart/form-data로 잘 전송된 경우 if문 영역 끝
		// 2022.1.31(월) 17h20 초안 작성 마무리 -> 17h50 테스트 완료
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
