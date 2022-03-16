package semi.keyword.model.dao;

import static semi.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import semi.keyword.model.vo.*;

public class KeywordDao {
	
	// 2022.1.21(금) 15h25
	// sql문들 모아둔 별도의 외부 파일을 만들고 dao 클래스의 메소드들 호출할 때마다 불러읽어들여서 (수정된)sql문 실행
	private Properties prop = new Properties(); // properties 객체 생성 + 이 클래스만 사용할 수 있도록 캡슐화(접근제한자 private)
	
	// 기본 생성자; 이 생성자 내부에 파일 호출하는 코드 작성 -> 이 생성자가 호출될 때마다/dao 객체 생성 시 아래 xml 파일 다시 읽어옴
	public KeywordDao() {
		String fileName = KeywordDao.class.getResource("/sql/keyword/keyword-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName)); // surround with try + multicatch
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // 기본 생성자 영역 끝
	
	// 2022.1.21(금) 15h35 -> 2022.1.28(금) 12h10 다형성 활용해서 db로부터 받은 자료 넘기기 위해 수정
	public TravelSpot selectTravelSpotList(Connection conn, String travelType, String area) {
		// SELECT문 실행 -> 1행인 resultSet 반환 받음 -> TravelSpot 객체에 담음
		
		// 필요한 변수 세팅
		TravelSpot ts = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "";
		
		// 2022.1.28(금) 12h10 travelType = HOTEL 또는 LANDSCAPE 또는 ACTIVITY(대문자 표기) 넘어옴
		if (travelType.equals("HOTEL") || travelType.equals("LANDSCAPE")) {
//			sql = prop.getProperty("selectTravelSpotList1");
			
			// 2022.1.28(금) 12h 수정 = xml 파일에 sql문 넣고 쓰지 않음 = hard coding, 코드 내부에 못박아놓음 = 좋지 않은 방법 -> 나의 질문 = xml 파일에 sql문을 넣고 썼던 이유/목적은?
			// 2021.11.29(월) 9h 'JDBC 4 properties' 수업을 2022.2.6(일) 15h30  다시 들어보니, 실시간으로 수정해도 문제 없도록/굳이 프로그램 종료하고 재실행하지 않도록 + (sql문만 모아놓아서) 유지/보수 용이 vs 이렇게 클래스 내부 코드(sql문 포함) 수정 시, 프로그램을 껐다 켜야 내가 원하는 수정 사항이 프로그램 실행에 반영되는데, 사용자 입장에서 프로그램이 갑자기/뜬금없이 껐다 켜지는 것은 좋지 않은 방식임
			// 2022.2.6(일) 18h 나의 질문 = xml 파일에 sql문 빼두면 프로그램 재구동 없이도 이 메소드 (재)호출 시 수정된 sql문 반영 vs 아래와 같이 hard coding해두고 sql문 수정 시 프로그램 재구동 시 반영되는 게 맞나?
			sql = "SELECT * FROM (SELECT TS_NO, CATEGORY, NAME, DESCRIPTION, ADDRESS, PHONE, PRICE, FILE_PATH||FILE_NAME TITLEIMG, AREA"
							  + " FROM " + travelType + " WHERE AREA LIKE '%'||?||'%' AND STATUS = 'Y' ORDER BY DBMS_RANDOM.VALUE)"
				+ " WHERE ROWNUM = 1";
			// SELECT *
//				FROM (SELECT TS_NO, CATEGORY, NAME, DESCRIPTION, ADDRESS, PHONE, PRICE, FILE_PATH||FILE_NAME "TITLEIMG"
//					  FROM HOTEL 또는 LANDSCAPE
//					  WHERE STATUS = 'Y'
//					  ORDER BY DBMS_RANDOM.VALUE)
//				WHERE ROWNUM = 1
		} else {
			sql = "SELECT * FROM (SELECT TS_NO, CATEGORY, NAME, DESCRIPTION, ADDRESS, PHONE, PRICE, EQUIPMENT, FILE_PATH||FILE_NAME TITLEIMG, AREA"
							  + " FROM " + travelType + " WHERE AREA LIKE '%'||?||'%' AND STATUS = 'Y' ORDER BY DBMS_RANDOM.VALUE)"
				+ " WHERE ROWNUM = 1";
			// SELECT * 
//			FROM (SELECT TS_NO, CATEGORY, NAME, DESCRIPTION, ADDRESS, PHONE, PRICE, EQUIPMENT, FILE_PATH||FILE_NAME "TITLEIMG"
//				  FROM ACTIVITY
//				  WHERE STATUS = 'Y'
//				  ORDER BY DBMS_RANDOM.VALUE)
//			WHERE ROWNUM = 1
		}
						
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, area);

			rset = pstmt.executeQuery();
			
			switch (travelType) {
			case "HOTEL" :
				if (rset.next()) {
					ts = new Hotel(rset.getInt("TS_NO"),
								   rset.getString("CATEGORY"),
								   rset.getString("NAME"),
								   rset.getString("DESCRIPTION"),
								   rset.getString("ADDRESS"),
							   	   rset.getString("PHONE"),
								   rset.getInt("PRICE"),
								   rset.getString("TITLEIMG"),
								   rset.getString("AREA"));
				}
				break;
			case "LANDSCAPE" :
				if (rset.next()) {
					ts = new Landscape(rset.getInt("TS_NO"),
									   rset.getString("CATEGORY"),
									   rset.getString("NAME"),
									   rset.getString("DESCRIPTION"),
									   rset.getString("ADDRESS"),
									   rset.getString("PHONE"),
									   rset.getInt("PRICE"),
									   rset.getString("TITLEIMG"),
									   rset.getString("AREA"));
				}
				break;
			case "ACTIVITY" :
				if (rset.next()) {
					ts = new Activity(rset.getInt("TS_NO"),
									  rset.getString("CATEGORY"),
									  rset.getString("NAME"),
								 	  rset.getString("DESCRIPTION"),
									  rset.getString("ADDRESS"),
									  rset.getString("PHONE"),
									  rset.getInt("PRICE"),
									  rset.getString("TITLEIMG"),
									  rset.getString("AREA"),
									  rset.getString("EQUIPMENT"));
				}
				break;
			} // switch문 영역 끝
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return ts;
	} // selectTravelSpotList() 종료
	
	// 2022.1.21(금) 16h45
	public ArrayList<Restaurant> selectRestaurantList(Connection conn, String foodType, String area) {
		// SELECT문 실행 -> 3행인 ResultSet을 반환 받음 -> ResultSet의 각 행 자료를 Restaurant 객체에 담은 뒤, 그 객체 3개를 ArrayList에 담음
		
		// 필요한 변수  세팅
		ArrayList<Restaurant> rList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 2022.1.21(금) 20h40 수정
		String sql = prop.getProperty("selectRestaurantList");
		// SELECT * FROM (SELECT RS_NO, CATEGORY, RS_NAME, DESCRIPTION, ADDRESS, PHONE, MENU, PRICE, FILE_PATH||FILE_NAME "TITLEIMG", AREA
//					        FROM RESTAURANT
//					        WHERE CATEGORY = ? AND AREA LIKE '%'||?||'%' AND STATUS = 'Y'
//					        ORDER BY DBMS_RANDOM.VALUE)
//			WHERE ROWNUM IN (1, 2, 3)
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, foodType.toUpperCase()); // 2022.1.21(금) 21h15 나의 발견 = Oracle DB에서 대소문자 구분함 -> 그냥 foodType으로 위치홀더 채웠을 때/쿼리문 날렸을 때 아무것도 조회 안 됨 -> rList []로 반환됨 
			pstmt.setString(2, area);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				Restaurant r = new Restaurant(rset.getInt("RS_NO"),
											  rset.getString("CATEGORY"),
											  rset.getString("RS_NAME"),
											  rset.getString("DESCRIPTION"),
											  rset.getString("ADDRESS"),
											  rset.getString("PHONE"),
											  rset.getString("MENU"),
											  rset.getInt("PRICE"),
											  rset.getString("TITLEIMG"),
											  rset.getString("AREA"));

				rList.add(r);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
			
		return rList;
		
		// 2022.1.21(금) 20h30 필요 없는 바, 주석 처리
//		if (foodListCount - rNum2 >= 2) {
//			String sql = prop.getProperty("selectRestaurantList1");
//			// SELECT RS_NAME, DESCRIPTION, ADDRESS, FILE_PATH||FILE_NAME "TITLEIMG"
////			FROM ?
////			WHERE RS_NO IN (?, ? + 1, ? + 2) AND STATUS = 'Y';
//			
//			try {
//				pstmt = conn.prepareStatement(sql);
//				
//				pstmt.setString(1,  foodType);
//				pstmt.setInt(2, rNum2);
//				pstmt.setInt(3, rNum2);
//				pstmt.setInt(4, rNum2);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//						
//		} else if (foodListCount - rNum2 == 1) {
//			String sql = prop.getProperty("selectRestaurantList2");
//			// SELECT RS_NAME, DESCRIPTION, ADDRESS, FILE_PATH||FILE_NAME "TITLEIMG"
////			FROM ?
////			WHERE RS_NO IN (?, ?, 1) AND STATUS = 'Y';
//			
//			try {
//				pstmt = conn.prepareStatement(sql);
//				
//				pstmt.setString(1, foodType);
//				pstmt.setInt(2, rNum2);
//				pstmt.setInt(3, foodListCount);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			
//		} else {
//			String sql = prop.getProperty("selectRestaurantList3");
//			// SELECT RS_NAME, DESCRIPTION, ADDRESS, FILE_PATH||FILE_NAME "TITLEIMG"
////			FROM ?
////			WHERE RS_NO IN (?, 1, 2) AND STATUS = 'Y';
//			
//			try {
//				pstmt = conn.prepareStatement(sql);
//				pstmt.setString(1, foodType);
//				pstmt.setInt(2, rNum2);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			
//		} // else문 영역 끝		
		
	} // selectRestaurantList() 종료
	
	// 2022.1.21(금) 15h20 -> 2022.1.21(금) Oracle에서 랜덤 행 추출 기능 사용할 것인 바, 20h5 필요 없는 듯? >.<
	public int selectListCount(Connection conn, String typeKeyword) {
		// SELECT문 실행 -> ResultSet 결과 반환 vs 지금 내가 필요한 것은 총 게시글의 개수(SELECT문을 쓰지만, 상식적으로 반환되는 값으로 정수가 필요함)

		// 필요한 변수 세팅
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectListCount");
		// SELECT COUNT(*) COUNT FROM ? WHERE STATUS = 'Y'
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, typeKeyword);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				listCount = rset.getInt("COUNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 나의 질문 = 왜 생성된 순서의 역순으로 객체 자원을 반납해야 했던 것이지? >.<
			close(rset);
			close(pstmt);
		}
		
		return listCount;
	} // selectListCount() 종료
	
	// 2022.1.23(일) 16h20 -> 2022.1.28(금) 14h20~ 필요 없는 듯
	public Activity selectActivityList(Connection conn, String travelType) {
		// SELECT문 실행 -> 1행인 resultSet 반환 받음 -> TravelSpot 객체에 담음
		
		// 필요한 변수 세팅
		Activity a = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectActivityList");
		// SELECT * 
//				FROM (SELECT TS_NO, CATEGORY, NAME, DESCRIPTION, ADDRESS, PHONE, PRICE, EQUIPMENT, FILE_PATH||FILE_NAME "TITLEIMG", AREA
//					  FROM ACTIVITY
//					  WHERE STATUS = 'Y'
//					  ORDER BY DBMS_RANDOM.VALUE)
//				WHERE ROWNUM = 1
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				a = new Activity(rset.getInt("TS_NO"),
								 rset.getString("CATEGORY"),
								 rset.getString("NAME"),
								 rset.getString("DESCRIPTION"),
								 rset.getString("ADDRESS"),
								 rset.getString("PHONE"),
								 rset.getInt("PRICE"),
								 rset.getString("TITLEIMG"),
								 rset.getString("AREA"),
								 rset.getString("EQUIPMENT"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return a;
	} // selectActivityList() 종료

}
