package semi.keyword.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import semi.keyword.model.dao.KeywordDao;
import semi.keyword.model.vo.Activity;
import semi.keyword.model.vo.Restaurant;
import semi.keyword.model.vo.TravelSpot;

import static semi.common.JDBCTemplate.*;

public class KeywordService {
	
	// 2022.1.21(금) 15h15
	public int selectListCount(String typeKeyword) {
		Connection conn = getConnection();
		
		// SELECT문의 실행 결과는 ResultSet이긴 한데, 게시글의 총 개수를 알아야 하기 때문에 정수형 자료를 반환 받음
		int listCount = new KeywordDao().selectListCount(conn, typeKeyword);
		
		close(conn);
		
		return listCount;
	} // selectListCount() 종료
	
	// 2022.1.21(금) 15h35
	public TravelSpot selectTravelSpotList(String travelType, String area) {
		// travelType = HOTEL 또는 LANDSCAPE 또는 ACTIVITY(대문자 표기) 넘어옴
		Connection conn = getConnection();
		
		TravelSpot ts = new KeywordDao().selectTravelSpotList(conn, travelType, area);
		
		close(conn);
		
		return ts;
	} // selectTravelSpotList() 종료
	
	// 2021.1.21(금) 16h45
	public ArrayList<Restaurant> selectRestaurantList(String foodType, String area) {
		Connection conn = getConnection();
		
		ArrayList<Restaurant> rList = new KeywordDao().selectRestaurantList(conn, foodType, area);
		
		close(conn);
		
		return rList;
	} // selectRestaurantList() 종료
	
	// 2022.1.23(일) 16h20 -> 2022.1.28(금) 14h20~ 필요 없는 듯
	public Activity selectActivityList(String travelType) {
		Connection conn = getConnection();
		
		Activity a = new KeywordDao().selectActivityList(conn, travelType);
		
		close(conn);
		
		return a;		
	} // selectActivityList() 종료

}
