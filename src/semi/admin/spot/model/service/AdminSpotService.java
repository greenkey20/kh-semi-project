package semi.admin.spot.model.service;

import static semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import semi.admin.spot.model.dao.AdminSpotDao;
import semi.admin.spot.model.vo.Attachment;
import semi.common.model.vo.PageInfo;
import semi.keyword.model.vo.Restaurant;
import semi.keyword.model.vo.TravelSpot;

public class AdminSpotService {
	
	// 2022.1.29(토) 22h25
	public int selectTravelSpotListCount(String category) {
		Connection conn = getConnection();
		
		int listCount = new AdminSpotDao().selectTravelSpotListCount(conn, category);
		
		close(conn);
		
		return listCount;
	} // selectTravelSpotListCount() 종료
	
	// 2022.2.2(수) 15h20
	public int selectRestaurantListCount(String category) {
		Connection conn = getConnection();
		
		int listCount = new AdminSpotDao().selectRestaurantListCount(conn, category);
		
		close(conn);
		
		return listCount;
	} // selectRestaurantListCount() 종료
	
	// 2022.1.29(토) 23h45
	public ArrayList<TravelSpot> selectTravelSpotList(String category, PageInfo pi) {
		Connection conn = getConnection();
		
		ArrayList<TravelSpot> tsList = new AdminSpotDao().selectTravelSpotList(conn, category, pi);
		
		close(conn);
		
		return tsList;
	} // selectTravelSpotList() 종료
	
	// 2022.1.30(일) 3h25
//	public ArrayList<TravelSpot> selectTravelSpotList(TravelSpot ts, PageInfo pi) {
//		Connection conn = getConnection();
//	} // 다른 종류의 매개변수 2개 받는 selectTravelSpotList() 종료
	
	// 2022.2.2(수) 15h45
	public ArrayList<Restaurant> selectRestaurantList(String category, PageInfo pi) {
		Connection conn = getConnection();
		
		ArrayList<Restaurant> rList = new AdminSpotDao().selectRestaurantList(conn, category, pi);
		
		close(conn);
		
		return rList;
	} // selectRestaurantList() 종료
	
	// 2022.1.31(월) 15h20
	public int insertTravelSpot(TravelSpot ts, ArrayList<Attachment> fList) {
		Connection conn = getConnection();
		
		int result1 = new AdminSpotDao().insertTravelSpot(conn, ts); // HOTEL 또는 LANDSCAPE 또는 ACTIVITY 테이블에 신규 TravelSpot 추가/INSERT
		
		int result2 = new AdminSpotDao().insertAttachmentList(conn, fList); // ATTACHMENT 테이블에 신규 TravelSpot의 이미지(들) 추가/INSERT
		
		if (result1 * result2 > 0) { // TravelSpot INSERT나 첨부파일(들) INSERT 둘 다 성공한 경우 = result1은 1, result2는 1 반환
			commit(conn);
		} else { // TravelSpot INSERT나 첨부파일(들) INSERT 둘 중 하나라도 실패한 경우 = result1이 0 또는 result2가 0 = result1, result2 둘 중 하나라도 0 -> result1 * result2 = 0
			rollback(conn);
		}
		
		close(conn);
		
		return result1 * result2;
	} // insertTravelSpot() 종료
	
	// 2022.2.2(수) 18h30
	public int insertRestaurant(Restaurant r, ArrayList<Attachment> fList) {
		Connection conn = getConnection();
		
		int result1 = new AdminSpotDao().insertRestaurant(conn, r); // RESTAURANT 테이블에 신규 식당 추가/INSERT
		
		int result2 = new AdminSpotDao().insertAttachmentList(conn, fList); // ATTACHMENT 테이블에 신규 식당의 이미지(들) 추가/INSERT
		
		if (result1 * result2 > 0) { // 식당 INSERT나 첨부파일(들) INSERT 둘 다 성공한 경우 = result1은 1, result2는 1 반환
			commit(conn);
		} else { // 식당 INSERT나 첨부파일(들) INSERT 둘 중 하나라도 실패한 경우 = result1이 0 또는 result2가 0 = result1, result2 둘 중 하나라도 0 -> result1 * result2 = 0
			rollback(conn);
		}
		
		close(conn);
		
		return result1 * result2;
	} // insertRestaurant() 종료
	
	// 2022.1.31(월) 18h5
	public TravelSpot selectTravelSpot(int tsNo, String category) {
		Connection conn = getConnection();
		
		TravelSpot ts = new AdminSpotDao().selectTravelSpot(conn, tsNo, category);
		
		close(conn);
		
		return ts;
	} // selectTravelSpot() 종료
	
	// 2022.2.2(수) 22h30
	public Restaurant selectRestaurant(int rsNo) {
		Connection conn = getConnection();
		
		Restaurant r = new AdminSpotDao().selectRestaurant(conn, rsNo);
		
		close(conn);
		
		return r;
	} // selectRestaurant() 종료
	
	// 2022.1.31(월) 19h30 여행지 상세 조회 + 2022.2.2(수) 23h 식당 상세 조회
	public ArrayList<Attachment> selectAttachmentList(int tsNo, String category) {
		Connection conn = getConnection();
		
		ArrayList<Attachment> fList = new AdminSpotDao().selectAttachmentList(conn, tsNo, category);
		
		close(conn);
		
		return fList;
	} // selectAttachmentList() 종료
	
	// 2022.2.1(화) 12h10
	public int updateTravelSpot(TravelSpot ts, ArrayList<Attachment> fList) {
		Connection conn = getConnection();
		
		// cases1~3 공통적으로 UPDATE TRAVELSPOT 처리
		int result1 = new AdminSpotDao().updateTravelSpot(conn, ts); // UPDATE 성공 시 1 vs 실패 시 0
		
		int result2 = 1;
		
		if (!fList.isEmpty()) { // 새로 업로드하는 파일(들)이 있는 경우
			for (int i = 0; i < fList.size(); i++) {
				if (fList.get(i).getFileNo() != 0) { // 기존 업로드된 파일 번호가 Servlet으로부터 저장되어있는 경우
					result2 = new AdminSpotDao().updateAttachment(conn, fList.get(i)); // UPDATE 성공 시 1 vs 실패 시 0
				} else { // 기존 업로드된 파일이 없는 바, fileNo 필드의 값이 0(=int 자료형의 기본값)인 경우 
					result2 = new AdminSpotDao().insertAttachment(conn, fList.get(i)); // INSERT 성공 시 1 vs 실패 시 0
				}
			} // for문 영역 끝
		} // if문 영역 끝
		
		if (result1 * result2 > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result1 * result2;
	} // updateTravelSpot() 종료
	
	// 2022.2.3(목) 1h15
	public int updateRestaurant(Restaurant r, ArrayList<Attachment> fList) {
		Connection conn = getConnection();
		
		// cases1~3 공통적으로 UPDATE RESTAURANT 처리
		int result1 = new AdminSpotDao().updateRestaurant(conn, r); // UPDATE 성공 시 1 vs 실패 시 0
		
		int result2 = 1;
		
		if (!fList.isEmpty()) { // 새로 업로드하는 파일(들)이 있는 경우
			for (int i = 0; i < fList.size(); i++) {
				if (fList.get(i).getFileNo() != 0) { // 기존 업로드된 파일 번호가 Servlet으로부터 저장되어있는 경우
					result2 = new AdminSpotDao().updateAttachment(conn, fList.get(i)); // UPDATE 성공 시 1 vs 실패 시 0
				} else { // 기존 업로드된 파일이 없는 바, fileNo 필드의 값이 0(=int 자료형의 기본값)인 경우 
					result2 = new AdminSpotDao().insertAttachment(conn, fList.get(i)); // INSERT 성공 시 1 vs 실패 시 0
				}
			} // for문 영역 끝
		} // if문 영역 끝
		
		if (result1 * result2 > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result1 * result2;
	} // updateRestaurant() 종료
	
	// 2022.2.1(화) 15h15
	public int deleteTravelSpot(int tsNo, String category) {
		Connection conn = getConnection();
		
		int result = new AdminSpotDao().deleteTravelSpot(conn, tsNo, category);
		
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	} // deleteTravelSpot() 종료
	
	public int deleteRestaurant(int rsNo) {
		Connection conn = getConnection();
		
		int result = new AdminSpotDao().deleteRestaurant(conn, rsNo);
		
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	} // deleteRestaurant() 종료
	
	// 2022.2.2(수) 10h25
	public int deleteCheckedTs(String[] checkedArr, String category) {
		Connection conn = getConnection();
		
		int result = 0;
		
		for (int i = 0; i < checkedArr.length; i++) {
			result += new AdminSpotDao().deleteTravelSpot(conn, Integer.parseInt(checkedArr[i]), category); // 1개 업체 삭제 성공 시 1 vs 실패 시 0 반환
		}
		
		if (result == checkedArr.length) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	} // deleteCheckedTs() 종료
	
	// 2022.2.3(목) 2h45
	public int deleteCheckedRs(String[] checkedArr) {
		Connection conn = getConnection();
		
		int result = 0;
		
		for (int i = 0; i < checkedArr.length; i++) {
			result += new AdminSpotDao().deleteRestaurant(conn, Integer.parseInt(checkedArr[i])); // 1개 업체 삭제 성공 시 1 vs 실패 시 0 반환
		}
		
		if (result == checkedArr.length) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	} // deleteCheckedRs() 종료
	
	// 2022.2.1(화) 17h
	public int travelSpotSearchKeywordCount(String category, String searchFilter, String keyword) {
		Connection conn = getConnection();
		
		int listCount = new AdminSpotDao().travelSpotSearchKeywordCount(conn, category, searchFilter, keyword);
		
		close(conn);
		
		return listCount;
	} // travelSpotSearchKeywordCount() 종료
	
	// 2022.2.2(수) 17h25
	public int restaurantSearchKeywordCount(String category, String searchFilter, String keyword) {
		Connection conn = getConnection();
		
		int listCount = new AdminSpotDao().restaurantSearchKeywordCount(conn, category, searchFilter, keyword);
		
		close(conn);
		
		return listCount;
	} // restaurantSearchKeywordCount() 종료
	
	// 2022.2.1(화) 17h30
	public ArrayList<TravelSpot> travelSpotSearchKeyword(PageInfo pi, String category, String searchFilter, String keyword) {
		Connection conn = getConnection();
		
		ArrayList<TravelSpot> tsList = new AdminSpotDao().travelSpotSearchKeyword(conn, pi, category, searchFilter, keyword);
		
		close(conn);
		
		return tsList;
	} // travelSpotSearchKeyword() 종료
	
	// 2022.2.2(수) 17h55
	public ArrayList<Restaurant> restaurantSearchKeyword(PageInfo pi, String category, String searchFilter, String keyword) {
		Connection conn = getConnection();
		
		ArrayList<Restaurant> rList = new AdminSpotDao().restaurantSearchKeyword(conn, pi, category, searchFilter, keyword);
		
		close(conn);
		
		return rList;
	} // restaurantSearchKeyword() 종료

}
