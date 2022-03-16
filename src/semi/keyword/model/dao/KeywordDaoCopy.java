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

public class KeywordDaoCopy {
	
	// 2022.1.21(금) 15h25
	// sql문들 모아둔 별도의 외부 파일을 만들고 dao 클래스의 메소드들 호출할 때마다 불러읽어들여서 (수정된)sql문 실행
	private Properties prop = new Properties(); // properties 객체 생성 + 이 클래스만 사용할 수 있도록 캡슐화(접근제한자 private)
	
	// 기본 생성자; 이 생성자 내부에 파일 호출하는 코드 작성 -> 이 생성자가 호출될 때마다/dao 객체 생성 시 아래 xml 파일 다시 읽어옴
	public KeywordDaoCopy() {
		String fileName = KeywordDaoCopy.class.getResource("/sql/keyword/keyword-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName)); // surround with try + multicatch
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // 기본 생성자 영역 끝
	
	public TravelSpot selectTravelSpotList(Connection conn, String travelType, String area) {

		TravelSpot ts = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "";
		
		if (travelType.equals("HOTEL") || travelType.equals("LANDSCAPE")) {
			sql = "SELECT * FROM (SELECT TS_NO, CATEGORY, NAME, DESCRIPTION, ADDRESS, PHONE, PRICE, FILE_PATH||FILE_NAME TITLEIMG, AREA"
							  + " FROM " + travelType + " WHERE AREA LIKE '%'||?||'%' AND STATUS = 'Y' ORDER BY DBMS_RANDOM.VALUE)"
				+ " WHERE ROWNUM = 1";
		} else {
			sql = "SELECT * FROM (SELECT TS_NO, CATEGORY, NAME, DESCRIPTION, ADDRESS, PHONE, PRICE, EQUIPMENT, FILE_PATH||FILE_NAME TITLEIMG, AREA"
							  + " FROM " + travelType + " WHERE AREA LIKE '%'||?||'%' AND STATUS = 'Y' ORDER BY DBMS_RANDOM.VALUE)"
				+ " WHERE ROWNUM = 1";
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
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return ts;
	}
	
	public ArrayList<Restaurant> selectRestaurantList(Connection conn, String foodType, String area) {

		ArrayList<Restaurant> rList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectRestaurantList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, foodType.toUpperCase());
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
	}
	
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
