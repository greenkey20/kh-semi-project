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

// 2022.2.1(화) 1h30
/**
 * Servlet implementation class TravelSpotUpdateController
 */
@WebServlet("/updateTravelSpot.ads")
public class TravelSpotUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TravelSpotUpdateController() {
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
//			System.out.println("업체 정보 수정 잘 되는지 확인해봅니다 ^^"); // 2022.2.1(화) 1h35 확인
			
			int maxSize = 1024 * 1024 * 3;
			String savePath = request.getSession().getServletContext().getRealPath("/resources/travelspot_upfiles");
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			
			// 단계1) multiRequest 객체로부터 값 뽑기
			// HOTEL, LANDSCAPE, ACTIVITY 공통적인 컬럼들
			int tsNo = Integer.parseInt(multiRequest.getParameter("tsNo"));
			String category = multiRequest.getParameter("category"); // HOTEL 또는 LANDSCAPE 또는 ACTIVITY
			String name = multiRequest.getParameter("name");
			String description = multiRequest.getParameter("description");
			String address = multiRequest.getParameter("address");
			String phone = multiRequest.getParameter("phone");
			int price = Integer.parseInt(multiRequest.getParameter("price"));
			String filePath = "/resources/travelspot_upfiles/"; // 첨부파일의 저장 경로
			
			// 2022.2.1(화) 10h25
			String fileName = "";
			if (multiRequest.getOriginalFileName("reUpfile1") != null) { // 대표 이미지를 새로 업로드하는 경우
				fileName = multiRequest.getFilesystemName("reUpfile1"); // 새로 업로드하는 대표 이미지의 수정 파일명
			} else { // 대표 이미지를 새로 업로드하지 않는 경우 = 기존 대표 이미지를 사용하고자 하는 경우
				fileName = multiRequest.getParameter("originFileName1"); // 기존에 업로드되어있던 대표 이미지의 수정 파일명
			}
			
			String area = multiRequest.getParameter("area");
			
			// ACTIVITY 고유의 컬럼
			String equipment = "";
			if (category.equals("ACTIVITY")) {
				equipment = multiRequest.getParameter("equipment");
			}
			
			// 단계2) vo 객체로 가공
			// 단계2a) 여행지(TravelSpot) 객체로 가공 -> HOTEL 또는 LANDSCAPE 또는 ACTIVITY 테이블의 관련 행 UPDATE
			TravelSpot ts = null;
			
			switch (category) {
			case "HOTEL" :
				ts = new Hotel(tsNo, category, name, description, address, phone, price, filePath, fileName, area);
				break;
			case "LANDSCAPE" :
				ts = new Landscape(tsNo, category, name, description, address, phone, price, filePath, fileName, area);
				break;
			case "ACTIVITY" :
				ts = new Activity(tsNo, category, name, description, address, phone, price, filePath, fileName, area, equipment);
			}
			
//			System.out.println(ts);
			
			// 단계2b) Attachment 객체로 가공; 대표 이미지 1장은 required + 최대 3장까지 상세 이미지 추가 가능 -> ArrayList<Attachment>에 담아 service단에 넘기고자 함 -> Attachment 테이블에  UPDATE 또는 INSERT
			ArrayList<Attachment> fList = new ArrayList<>();
			
			for (int i = 1; i <= 4; i++) { // name 속성의 값 "reUpfile1~4"를 가진 file input에 대해 아래 내용을 확인/처리하고자 함 
				String key = "reUpfile" + i;
				Attachment at = null; // reUpfile이 없다면 이 객체 생성할 필요 없음
				
				if (multiRequest.getOriginalFileName(key) != null) { // 새로 업로드하는 파일이 있는 경우
					at = new Attachment(); // 2022.2.1(화) 14h20 테스트 시 이 객체 생성 안 했더니, 아래 if문 내 null인 at에서 뭔가 접근하려고 해서 null pointer exception 발생

					if (multiRequest.getParameter("originFileName" + i) != null) { // 기존에 업로드된 파일이 있는 경우 -> 새로 업로드하는 파일의 정보를 기존 업로드된 파일 행에 덮어쓰기(=기존 파일 행을 UPDATE)하고자 함
						at.setFileNo(Integer.parseInt(multiRequest.getParameter("originFileNo" + i))); // 새로 업로드하는 파일의 파일 번호는 기존 파일의 번호로 교체
						new File(savePath + multiRequest.getParameter("originFileName" + i)).delete(); // 기존에 업로드된 파일(수정 파일명으로 서버에 저장되어있음)은 서버로부터 삭제
					} else { // 기존에 업로드된 파일이 없는 경우 -> ATTACHMENT 테이블에 새로운 첨부파일 관련 행 INSERT
						
					}
					
					at.setRefTsNo(tsNo); // 새로 업로드하는 파일이 참조하는 여행지 번호 입력
					at.setRefCategory(category); // 새로 업로드하는 파일이 참조하는 여행 타입 입력
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
			
//			System.out.println(fList);
			
			// 단계3) service단으로 toss
			// case1) 새로 업로드하는 파일이 있음 + 기존에 업로드된 파일이 있음 ->  UPDATE TRAVELSPOT + UPDATE ATTACHMENT
			// case2) 새로 업로드하는 파일이 있음 + 기존에 업로드된 파일은 없음 -> UPDATE TRAVELSPOT + INSERT ATTACHMENT
			// case3) 새로 업로드하는 파일 없음 -> UPDATE TRAVELSPOT
			int result = new AdminSpotService().updateTravelSpot(ts, fList);
			
			if (result > 0) { // 여행지 UPDATE + 첨부파일 UPDATE/INSERT 모두 성공 시
				request.getSession().setAttribute("alertMsg", "여행지 정보 수정에 성공했습니다");
				response.sendRedirect(request.getContextPath() + "/detail.ads?tsNo=" + tsNo + "&category=" + category + "&currentPage=1");
			} else {
				request.setAttribute("errorMsg", "여행지 정보 수정에 실패했습니다");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}
			
		} // enctype이 multipart/form-data로 잘 전송된 경우 if문 영역 끝
		// 2022.2.1(화) 14h20 초안 작성 마무리
		// 테스트 시 이슈1 = 여행지 테이블에 dummy data로 대표 이미지 1장씩 일괄적으로 넣었던 여행지들 정보 수정하려 할 때, ATTACHMENT 테이블에 관련 파일이 없으므로 '대표 이미지'의 원본 파일명을 표시할 수 없어 java.lang.IndexOutOfBoundsException: Index: 0, Size: 0 vs !fList.isEmpty()인 경우에만 파일명 표시되도록 jsp 수정
		// 이슈2 = 여행지 정보 수정 양식 jsp에서 상세 이미지 "reUpfile" input file 태그를 기존 상세 이미지 개수만큼만 추가되도록 해 두었었음 -> 항상 파일 첨부 3개 보이도록 수정
		// 이슈3 = 여행지 테이블에 dummy data로 대표 이미지 1장씩 일괄적으로 넣었던 여행지 등 정보 수정 jsp에서 첨부파일 정보 없는 상태로 '등록' 시 java.sql.SQLException: ORA-01407: cannot update ("SEMI3"."HOTEL"."FILE_NAME") to NULL -> errorPage 응답 vs 여행지 테이블 UPDATE 시 대표 이미지 첨부파일 정보 컬럼들은 필수 입력해야 하는 바(nullable no), 대표 이미지 첨부 필수
		// 이슈4 = 첨부파일 UPDATE 시 UPLOAD_DATE가 '오늘 날짜(첨부파일을 수정해서 업로드한 날)'이었으면 해서, sql문 set 부분에 해당 내용 추가
		// 2022.2.1(화) 15h5 테스트 완료
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
