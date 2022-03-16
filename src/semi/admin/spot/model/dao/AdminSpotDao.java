package semi.admin.spot.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import semi.admin.spot.model.vo.Attachment;
import semi.common.model.vo.PageInfo;
import semi.keyword.model.vo.Activity;
import semi.keyword.model.vo.Hotel;
import semi.keyword.model.vo.Landscape;
import semi.keyword.model.vo.Restaurant;
import semi.keyword.model.vo.TravelSpot;

import static semi.common.JDBCTemplate.*;

public class AdminSpotDao {
	
	// 2022.1.29(토) 22h25
	private Properties prop = new Properties();
	
	public AdminSpotDao() {
		String fileName = AdminSpotDao.class.getResource("/sql/admin/adminspot-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int selectTravelSpotListCount(Connection conn, String category) {
		// SELECT문 실행 -> 1행짜리 ResultSet 결과 반환 vs 지금 내가 필요한 것은 총 업체의 개수(SELECT문을 쓰지만, 상식적으로 반환되는 값으로 정수가 필요함)
		
		// 필요한 변수 세팅
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 테이블명을 위치홀더로 채울 수는 없기 때문에, sql문을 문자열로 준비해두고, 필요한 Java 변수를 문자열 중간에  삽입해서 쿼리문 완성시킴
		String sql = "SELECT COUNT(*) COUNT FROM " + category + " WHERE STATUS = 'Y'";
//		SELECT COUNT(*) COUNT
//		FROM HOTEL 또는 LANDSCAPE 또는 ACTIVITY
//		WHERE STATUS = 'Y';
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				listCount = rset.getInt("COUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return listCount;
	} // selectTravelSpotListCount() 종료
	
	// 2022.2.2(수) 15h25
	public int selectRestaurantListCount(Connection conn, String category) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 테이블명에 Java 변수가 필요하지 않으므로, 위치홀더로 뚫어둔 sql문을 xml 파일에 저장해두고 가져다 씀
		String sql = prop.getProperty("selectRestaurantListCount");
		// SELECT COUNT(*) COUNT
//			FROM RESTAURANT
//			WHERE CATEGORY = ? AND STATUS = 'Y'
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				listCount = rset.getInt("COUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return listCount;
	} // selectRestaurantListCount() 종료
	
	// 2022.1.29(토) 23h50
	public ArrayList<TravelSpot> selectTravelSpotList(Connection conn, String category, PageInfo pi) {
		// SELECT문 실행 -> currentPage에서 boardLimit만큼 보여줄 업체 목록 ResultSet을 반환받음 -> ArrayList<TravelSpot>에 담음
		
		// 필요한 변수 세팅
		TravelSpot ts = null;
		
		ArrayList<TravelSpot> tsList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		int endRow = pi.getCurrentPage() * pi.getBoardLimit(); // 7, 14, 21..
		int startRow = endRow - pi.getBoardLimit() + 1; // 1, 8, 15..
		
		String sql = "";
		
		if (category.equals("HOTEL") || category.equals("LANDSCAPE")) {
			sql = "SELECT *"
			   + " FROM (SELECT ROWNUM RNUM, A.*"
			   		 + " FROM (SELECT TS_NO, CATEGORY, NAME, ADDRESS, PHONE, PRICE, AREA,"
			   		 	   		  + " (SELECT COUNT(*) COUNT FROM MEMBERTRAVELHISTORY WHERE TRAVELTYPE1 = ? AND TRAVELSPOT1 = TS_NO) VISITCOUNT"
			   		 	   + " FROM " + category
			   		 	   + " WHERE STATUS = 'Y'"
			   		 	   + " ORDER BY TS_NO DESC) A)"
			   + " WHERE RNUM BETWEEN ? AND ?";
//			category가 HOTEL 또는 LANDSCAPE인 경우
//			SELECT *
//			FROM (SELECT ROWNUM RNUM, A.*
//			        FROM (SELECT TS_NO, CATEGORY, NAME, ADDRESS, PHONE, PRICE
//			                FROM LANDSCAPE
//			                WHERE STATUS = 'Y'
//			                ORDER BY TS_NO DESC) A)
//			WHERE RNUM BETWEEN 1 AND 7;
		/*
		} else if (ts.getCategory().equals("HOTEL") || ts.getCategory().equals("LANDSCAPE") && !ts.getArea().equals("")) {
			sql = "SELECT *"
					   + " FROM (SELECT ROWNUM RNUM, A.*"
					   		 + " FROM (SELECT TS_NO, CATEGORY, NAME, ADDRESS, PHONE, PRICE, AREA"
					   		 	   + " FROM " + ts.getCategory()
					   		 	   + " WHERE AREA = '" + ts.getArea() + "'"
					   		 	   + " AND STATUS = 'Y'"
					   		 	   + " ORDER BY TS_NO DESC) A)"
					   + " WHERE RNUM BETWEEN ? AND ?";
		*/
		} else {
			sql = "SELECT *"
			   + " FROM (SELECT ROWNUM RNUM, A.*"
			   		 + " FROM (SELECT TS_NO, CATEGORY, NAME, ADDRESS, PHONE, PRICE, AREA,"
			   		 			  + " (SELECT COUNT(*) COUNT FROM MEMBERTRAVELHISTORY WHERE TRAVELTYPE1 = ? AND TRAVELSPOT1 = TS_NO) VISITCOUNT, EQUIPMENT"
			   		 	   + " FROM " + category
			   		 	   + " WHERE STATUS = 'Y'"
			   		 	   + " ORDER BY TS_NO DESC) A)"
			   + " WHERE RNUM BETWEEN ? AND ?";
//			category가 ACTIVITY인 경우
//			SELECT *
//			FROM (SELECT ROWNUM RNUM, A.*
//			        FROM (SELECT TS_NO, CATEGORY, NAME, ADDRESS, PHONE, PRICE, EQUIPMENT
//			                FROM ACTIVITY
//			                WHERE STATUS = 'Y'
//			                ORDER BY TS_NO DESC) A)
//			WHERE RNUM BETWEEN 8 AND 14;
		}
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, category);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			switch (category) {
			case "HOTEL" :
				while (rset.next()) {
					ts = new Hotel(rset.getInt("TS_NO"),
								   rset.getString("CATEGORY"),
								   rset.getString("NAME"),
								   rset.getString("ADDRESS"),
								   rset.getString("PHONE"),
								   rset.getInt("PRICE"),
								   rset.getString("AREA"),
								   rset.getInt("VISITCOUNT"));
					
					tsList.add(ts);
				}
				break;
			case "LANDSCAPE" :
				while (rset.next()) {
					ts = new Landscape(rset.getInt("TS_NO"),
									   rset.getString("CATEGORY"),
									   rset.getString("NAME"),
									   rset.getString("ADDRESS"),
									   rset.getString("PHONE"),
									   rset.getInt("PRICE"),
									   rset.getString("AREA"),
									   rset.getInt("VISITCOUNT"));
					
					tsList.add(ts);
				}
				break;
			case "ACTIVITY" :
				while (rset.next()) {
					ts = new Activity(rset.getInt("TS_NO"),
									  rset.getString("CATEGORY"),
									  rset.getString("NAME"),
									  rset.getString("ADDRESS"),
									  rset.getString("PHONE"),
									  rset.getInt("PRICE"),
									  rset.getString("AREA"),
									  rset.getInt("VISITCOUNT"),
									  rset.getString("EQUIPMENT"));
					
					tsList.add(ts);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return tsList;
	} // selectTravelSpotList() 종료
	
	// 2022.2.2(수) 15h55
	public ArrayList<Restaurant> selectRestaurantList(Connection conn, String category, PageInfo pi) {
		ArrayList<Restaurant> rList = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		int endRow = pi.getCurrentPage() * pi.getBoardLimit(); // 7, 14, 21..
		int startRow = endRow - pi.getBoardLimit() + 1; // 1, 8, 15..
		
		String sql = prop.getProperty("selectRestaurantList");
		// SELECT * FROM (SELECT ROWNUM RNUM, A.*
		//	        		FROM (SELECT RS_NO, CATEGORY, RS_NAME, ADDRESS, PHONE, MENU, PRICE, AREA,
		//	                	 		(SELECT COUNT(*) COUNT FROM MEMBERTRAVELHISTORY
		//	            		 		 WHERE RESTAURANT1 = RS_NO OR RESTAURANT2 = RS_NO OR RESTAURANT3 = RS_NO) VISITCOUNT
		//	   		  	  		  FROM RESTAURANT
		//	   		  	  		  WHERE CATEGORY = ? AND STATUS = 'Y'
		//	   		  	 		  ORDER BY RS_NO DESC) A)
		//	WHERE RNUM BETWEEN ? AND ?
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, category);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				Restaurant r = new Restaurant(rset.getInt("RS_NO"),
											  rset.getString("CATEGORY"),
											  rset.getString("RS_NAME"),
											  rset.getString("ADDRESS"),
											  rset.getString("PHONE"),
											  rset.getString("MENU"),
											  rset.getInt("PRICE"),
											  rset.getString("AREA"),
											  rset.getInt("VISITCOUNT"));
				
				rList.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return rList;
	} // selectRestaurantList() 종료
	
	// 2022.1.31(월) 15h30
	public int insertTravelSpot(Connection conn, TravelSpot ts) {
		// INSERT문 실행 -> 처리된 행의 개수 int 반환
		
		// 필요한 변수 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";
		
		String category = ts.getCategory(); // HOTEL 또는 LANDSCAPE 또는 ACTIVITY
		
		if (category.equals("HOTEL") || category.equals("LANDSCAPE")) {
			sql = "INSERT INTO " + category
			    + "(TS_NO, NAME, DESCRIPTION, ADDRESS, PHONE, PRICE, FILE_PATH, FILE_NAME, AREA)"
				+ "VALUES(SEQ_" + category.substring(0, 1) + "NO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";
		} else {
			sql = "INSERT INTO " + category
			    + "(TS_NO, NAME, DESCRIPTION, ADDRESS, PHONE, PRICE, FILE_PATH, FILE_NAME, AREA, EQUIPMENT)"
				+ "VALUES(SEQ_" + category.substring(0, 1) + "NO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		}
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, ts.getName());
			pstmt.setString(2, ts.getDescription());
			pstmt.setString(3, ts.getAddress());
			pstmt.setString(4, ts.getPhone());
			pstmt.setInt(5, ts.getPrice());
			pstmt.setString(6, ts.getFilePath());
			pstmt.setString(7, ts.getFileName());
			pstmt.setString(8, ts.getArea());
			
			if (category.equals("ACTIVITY")) {
				pstmt.setString(9, ((Activity)ts).getEquipment());
			}
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // insertTravelSpot() 종료
	
	// 2022.2.2(수) 18h35
	public int insertRestaurant(Connection conn, Restaurant r) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertRestaurant");
		// INSERT INTO RESTAURANT(RS_NO, CATEGORY, RS_NAME, DESCRIPTION, ADDRESS, PHONE, MENU, PRICE, FILE_PATH, FILE_NAME, AREA)
//			VALUES(SEQ_RNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, r.getCategory());
			pstmt.setString(2, r.getRsName());
			pstmt.setString(3, r.getDescription());
			pstmt.setString(4, r.getAddress());
			pstmt.setString(5, r.getPhone());
			pstmt.setString(6, r.getMenu());
			pstmt.setInt(7, r.getPrice());
			pstmt.setString(8, r.getFilePath());
			pstmt.setString(9, r.getFileName());
			pstmt.setString(10, r.getArea());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // insertRestaurant() 종료
	
	// 2022.1.31(월) 16h10
	public int insertAttachmentList(Connection conn, ArrayList<Attachment> fList) {
		// INSERT문 실행 -> 처리된 행의 개수 int 반환
		
		// 필요한 변수 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		String category = fList.get(0).getRefCategory(); // HOTEL 또는 LANDSCAPE 또는 ACTIVITY
		
		String sql = "INSERT INTO ATTACHMENT(FILE_NO, REF_TSNO, REF_CATEGORY, ORIGIN_NAME, CHANGE_NAME, FILE_PATH, FILE_LEVEL)"
				  + " VALUES(SEQ_FNO.NEXTVAL, SEQ_";
		
		switch (category) {
		case "HOTEL" : // HOTEL을 추가하고 있는 경우
			sql += category.substring(0, 1) + "NO.CURRVAL, ?, ?, ?, ?, ?)";
			break;
		case "LANDSCAPE" :
			sql += category.substring(0, 1) + "NO.CURRVAL, ?, ?, ?, ?, ?)";
			break;
		case "ACTIVITY" :
			sql += category.substring(0, 1) + "NO.CURRVAL, ?, ?, ?, ?, ?)";
			break;
		// 2022.2.2(수) 18h45 식당 추가할 때도 이 메소드 사용하기 위해 아래 cases 추가
		case "KOREAN" :
		case "JEJU" :
		case "WESTERN" :
			sql += "RNO.CURRVAL, ?, ?, ?, ?, ?)";
		}
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			for (Attachment at : fList) {
				pstmt.setString(1, category);
				pstmt.setString(2, at.getOriginName());
				pstmt.setString(3, at.getChangeName());
				pstmt.setString(4, at.getFilePath());
				pstmt.setInt(5, at.getFileLevel());
				
				result = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // insertAttachmentList() 종료
	
	// 2022.1.31(월) 18h5
	public TravelSpot selectTravelSpot(Connection conn, int tsNo, String category) {
		// SELECT문 실행 -> 해당 tsNo 및 category의 TravelSpot 1행이 담긴 ResultSet 반환 받음 -> TravelSpot에 담음
		
		// 필요한 변수 세팅
		TravelSpot ts = null; // 나의 질문 = 여기서 new TravelSpot()으로 만들어두면 추후에 문제가 생길까? 2022.1.31(월) 18h20 생각으로는 별 이슈 없을 것 같긴 함 -> 2022.2.1(화)에 결국 TravelSpot을 추상클래스로 만들었기 때문에, 객체 생성 불가능하게 되긴 함
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "";
		// category = HOTEL 또는 LANDSCAPE 또는 ACTIVITY
		
		// 2022.2.1(화) 21h10 travelSpotDetailView.jsp에 해당 여행지의 총 방문 횟수도 보여주고 싶어서, 아래와 같이 sql문 수정
		if (category.equals("HOTEL") || category.equals("LANDSCAPE")) {
			sql = "SELECT TS_NO, CATEGORY, NAME, DESCRIPTION, ADDRESS, PHONE, PRICE, FILE_PATH||FILE_NAME TITLEIMG, AREA,"
					  + " (SELECT COUNT(*) COUNT FROM MEMBERTRAVELHISTORY WHERE TRAVELTYPE1 = ? AND TRAVELSPOT1 = ?) VISITCOUNT"
			   + " FROM " + category
			   + " WHERE TS_NO = ? AND STATUS = 'Y'";
//			SELECT TS_NO, CATEGORY, NAME, DESCRIPTION, ADDRESS, PHONE, PRICE, FILE_PATH, FILE_NAME, AREA,
//			(SELECT COUNT(*) COUNT FROM MEMBERTRAVELHISTORY WHERE TRAVELTYPE1 = 'HOTEL' AND TRAVELSPOT1 = 4) VISITCOUNT
//			FROM HOTEL
//			WHERE TS_NO = 11 AND STATUS = 'Y';
		} else {
			sql = "SELECT TS_NO, CATEGORY, NAME, DESCRIPTION, ADDRESS, PHONE, PRICE, FILE_PATH||FILE_NAME TITLEIMG, AREA,"
					  + " (SELECT COUNT(*) COUNT FROM MEMBERTRAVELHISTORY WHERE TRAVELTYPE1 = ? AND TRAVELSPOT1 = ?) VISITCOUNT, EQUIPMENT"
			   + " FROM " + category
			   + " WHERE TS_NO = ? AND STATUS = 'Y'";
		}
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			pstmt.setInt(2, tsNo);
			pstmt.setInt(3, tsNo);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				switch (category) {
				case "HOTEL" :
					ts = new Hotel(rset.getInt("TS_NO"),
								   rset.getString("CATEGORY"),
								   rset.getString("NAME"),
								   rset.getString("DESCRIPTION"),
								   rset.getString("ADDRESS"),
								   rset.getString("PHONE"),
								   rset.getInt("PRICE"),
								   rset.getString("TITLEIMG"),
								   rset.getString("AREA"),
								   rset.getInt("VISITCOUNT"));
					break;
				case "LANDSCAPE" :
					ts = new Landscape(rset.getInt("TS_NO"),
									   rset.getString("CATEGORY"),
									   rset.getString("NAME"),
									   rset.getString("DESCRIPTION"),
									   rset.getString("ADDRESS"),
									   rset.getString("PHONE"),
									   rset.getInt("PRICE"),
									   rset.getString("TITLEIMG"),
									   rset.getString("AREA"),
									   rset.getInt("VISITCOUNT"));
					break;
				case "ACTIVITY" :
					ts = new Activity(rset.getInt("TS_NO"),
									  rset.getString("CATEGORY"),
									  rset.getString("NAME"),
									  rset.getString("DESCRIPTION"),
									  rset.getString("ADDRESS"),
									  rset.getString("PHONE"),
									  rset.getInt("PRICE"),
									  rset.getString("TITLEIMG"),
									  rset.getString("AREA"),
									  rset.getInt("VISITCOUNT"),
									  rset.getString("EQUIPMENT"));
				} // switch문 영역 끝
			} // if문 영역 끝
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return ts;
	} // selectTravelSpot() 종료
	
	// 2022.2.2(수) 22h30
	public Restaurant selectRestaurant(Connection conn, int rsNo) {
		Restaurant r = new Restaurant();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectRestaurant");
		// SELECT RS_NO, CATEGORY, RS_NAME, DESCRIPTION, ADDRESS, PHONE, MENU, PRICE, FILE_PATH||FILE_NAME TITLEIMG, AREA,
//		        (SELECT COUNT(*) COUNT FROM MEMBERTRAVELHISTORY
//		        WHERE RESTAURANT1 = RS_NO OR RESTAURANT2 = RS_NO OR RESTAURANT3 = RS_NO) VISITCOUNT
//		FROM RESTAURANT
//		WHERE RS_NO = ? AND STATUS = 'Y'
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rsNo);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				r.setRsNo(rset.getInt("RS_NO"));
				r.setCategory(rset.getString("CATEGORY"));
				r.setRsName(rset.getString("RS_NAME"));
				r.setDescription(rset.getString("DESCRIPTION"));
				r.setAddress(rset.getString("ADDRESS"));
				r.setPhone(rset.getString("PHONE"));
				r.setMenu(rset.getString("MENU"));
				r.setPrice(rset.getInt("PRICE"));
				r.setTitleImg(rset.getString("TITLEIMG"));
				r.setArea(rset.getString("AREA"));
				r.setVisitCount(rset.getInt("VISITCOUNT"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return r;
	} // selectRestaurant() 종료
	
	// 2022.1.31(월) 19h30 여행지 상세 조회 + 2022.2.2(수) 23h 식당 상세 조회
	public ArrayList<Attachment> selectAttachmentList(Connection conn, int tsNo, String category) {
		// SELECT문 실행 -> 0~3개의 상세 이미지가 담긴, 0~3행의 ResultSet 반환 받음 -> ArrayList<Attachment>에 담음
		
		// 필요한 변수 세팅
		ArrayList<Attachment> fList = new ArrayList<>(); // null로 만들어두면, 있다가 rset 내용물 담을 때 null에 담으려고 해서 null pointer exception 발생할 수 있음
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectAttachmentList");
		// SELECT FILE_NO, ORIGIN_NAME, CHANGE_NAME, FILE_PATH
//			FROM ATTACHMENT
//			WHERE STATUS = 'Y' AND REF_TSNO = ? AND REF_CATEGORY = ?
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tsNo);
			pstmt.setString(2, category);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				Attachment at = new Attachment();
				
				at.setFileNo(rset.getInt("FILE_NO"));
				at.setOriginName(rset.getString("ORIGIN_NAME"));
				at.setChangeName(rset.getString("CHANGE_NAME"));
				at.setFilePath(rset.getString("FILE_PATH"));
				
				fList.add(at);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return fList;
	} // selectAttachmentList() 종료
	
	// 2022.2.1(화) 13h20
	public int updateTravelSpot(Connection conn, TravelSpot ts) {
		// UPDATE문 실행 -> 처리된 행의 수 int 반환
		
		// 필요한 변수 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		String category = ts.getCategory(); // HOTEL 또는 LANDSCAPE 또는 ACTIVITY
		String sql = "UPDATE " + category
				  + " SET NAME = ?, DESCRIPTION = ?, ADDRESS = ?, PHONE = ?, PRICE = ?, FILE_PATH = ?, FILE_NAME = ?, AREA = ?";
		
		if (category.equals("HOTEL") || category.equals("LANDSCAPE")) {
			sql += " WHERE TS_NO = ?";
		} else {
			sql += ", EQUIPMENT = ? WHERE TS_NO = ?";
		}
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, ts.getName());
			pstmt.setString(2, ts.getDescription());
			pstmt.setString(3, ts.getAddress());
			pstmt.setString(4, ts.getPhone());
			pstmt.setInt(5, ts.getPrice());
			pstmt.setString(6, ts.getFilePath());
			pstmt.setString(7, ts.getFileName());
			pstmt.setString(8, ts.getArea());
			
			if (category.equals("HOTEL") || category.equals("LANDSCAPE")) {
				pstmt.setInt(9, ts.getTsNo());
			} else {
				pstmt.setString(9, ((Activity)ts).getEquipment());
				pstmt.setInt(10, ts.getTsNo());
			}
			
			result = pstmt.executeUpdate(); // UPDATE 성공 시 1 vs 실패 시 0
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // updateTravelSpot() 종료
	
	// 2022.2.3(목) 1h20
	public int updateRestaurant(Connection conn, Restaurant r) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateRestaurant");
		// UPDATE RESTAURANT
//			SET CATEGORY = ?, RS_NAME = ?. DESCRIPTION = ?, ADDRESS = ?, PHONE = ?, MENU = ?, PRICE = ?, FILE_PATH = ?, FILE_NAME = ?, AREA = ?
//			WHERE RS_NO = ?
		
			try {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, r.getCategory());
				pstmt.setString(2, r.getRsName());
				pstmt.setString(3, r.getDescription());
				pstmt.setString(4, r.getAddress());
				pstmt.setString(5, r.getPhone());
				pstmt.setString(6, r.getMenu());
				pstmt.setInt(7, r.getPrice());
				pstmt.setString(8, r.getFilePath());
				pstmt.setString(9, r.getFileName());
				pstmt.setString(10, r.getArea());
				pstmt.setInt(11, r.getRsNo());
				
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			}
		
		return result;
	} // updateRestaurant() 종료
	
	// 2022.2.1(화) 13h35 여행지 정보 수정 + 2022.2.3(목) 1h30 식당 정보 수정
	public int updateAttachment(Connection conn, Attachment at) {
		// UPDATE문 실행 -> 처리된 행의 수 int 반환
		
		// 필요한 변수 세팅 
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("updateAttachment");
		// UPDATE ATTACHMENT
	//		SET REF_TSNO = ?, REF_CATEGORY = ?, ORIGIN_NAME = ?, CHANGE_NAME = ?, FILE_PATH = ?, UPLOAD_DATE = SYSDATE, FILE_LEVEL = ?
	//		WHERE FILE_NO = ?
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, at.getRefTsNo());
			pstmt.setString(2, at.getRefCategory());
			pstmt.setString(3, at.getOriginName());
			pstmt.setString(4, at.getChangeName());
			pstmt.setString(5, at.getFilePath());
			pstmt.setInt(6, at.getFileLevel());
			pstmt.setInt(7, at.getFileNo());
			
			result = pstmt.executeUpdate(); // UPDATE 성공 시 1 vs 실패 시 0
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // updateAttachment() 종료
	
	// 2022.2.1(화) 13h50 여행지 정보 수정 + 2022.2.3(목) 1h30 식당 정보 수정
	public int insertAttachment(Connection conn, Attachment at) {
		// INSERT문 실행 -> 처리된 행의 개수 int 반환
		
		// 필요한 변수 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertAttachment");
		// INSERT INTO ATTACHMENT(FILE_NO, REF_TSNO, REF_CATEGORY, ORIGIN_NAME, CHANGE_NAME, FILE_PATH, FILE_LEVEL)
//			VALUES(SEQ_FNO.NEXTVAL, ?, ?, ?, ?, ?, ?)
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, at.getRefTsNo());
			pstmt.setString(2, at.getRefCategory());
			pstmt.setString(3, at.getOriginName());
			pstmt.setString(4, at.getChangeName());
			pstmt.setString(5, at.getFilePath());
			pstmt.setInt(6, at.getFileLevel());
			
			result = pstmt.executeUpdate(); // INSERT 성공 시 1 vs 실패 시 0
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // insertAttachment() 종료
	
	// 2022.2.1(화) 15h15
	public int deleteTravelSpot(Connection conn, int tsNo, String category) {
		// DELETE문 실행 -> 처리된 행의 개수 int 반환
		
		// 필요한 변수 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "UPDATE " + category
				  + " SET STATUS = 'N'"
				  + " WHERE TS_NO = ? ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tsNo);
			
			result = pstmt.executeUpdate(); // 삭제 성공 시 1 vs 실패 시 0 반환
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // deleteTravelSpot() 종료
	
	// 2022.2.3(목) 2h20
	public int deleteRestaurant(Connection conn, int rsNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteRestaurant");
		// UPDATE RESTAURANT
//			SET STATUS = 'N'
//			WHERE RS_NO = ?
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rsNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} // deleteRestaurant() 종료
	
	// 2022.2.1(화) 17h
	public int travelSpotSearchKeywordCount(Connection conn, String category, String searchFilter, String keyword) {
		// SELECT문 실행 -> 1행짜리 ResultSet 결과 반환 vs 지금 내가 필요한 것은 총 업체의 개수(SELECT문을 쓰지만, 상식적으로 반환되는 값으로 정수가 필요함)
		
		// 필요한 변수 세팅
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT COUNT(*) COUNT"
				  + " FROM " + category
				  + " WHERE " + searchFilter + " LIKE '%'||?||'%' AND STATUS = 'Y'";
		// SELECT COUNT(*) COUNT
//			FROM HOTEL
//			WHERE ADDRESS LIKE '%'||'제주'||'%' AND STATUS = 'Y';
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				listCount = rset.getInt("COUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return listCount;
	} // travelSpotSearchKeywordCount() 종료
	
	// 2022.2.2(수) 17h25
	public int restaurantSearchKeywordCount(Connection conn, String category, String searchFilter, String keyword) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 내가 현재 알기로는 컬럼명에도 위치홀더 못 씀
		String sql = "SELECT COUNT(*) COUNT"
				  + " FROM RESTAURANT"
				  + " WHERE CATEGORY = ? AND " + searchFilter + " LIKE '%'||?||'%' AND STATUS = 'Y'";
		// SELECT COUNT(*) COUNT
//			FROM RESTAURANT
//			WHERE CATEGORY = ? AND RS_NAME LIKE '%?%' AND STATUS = 'Y'
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, category);
			pstmt.setString(2, keyword);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				listCount = rset.getInt("COUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return listCount;
	} // restaurantSearchKeywordCount() 종료
	
	// 2022.2.1(화) 17h30
	public ArrayList<TravelSpot> travelSpotSearchKeyword(Connection conn, PageInfo pi, String category, String searchFilter, String keyword) {
		// SELECT문 실행 -> 해당 category 테이블의 searchFilter 컬럼에서 keyword를 검색한 결과를 ResultSet으로 반환 받음 -> ArrayList<TravelSpot>에 담음
		
		// 필요한 변수 세팅
		TravelSpot ts = null;
		
		ArrayList<TravelSpot> tsList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		int endRow = pi.getCurrentPage() * pi.getBoardLimit(); // 7, 14, 21..
		int startRow = endRow - pi.getBoardLimit() + 1; // 1, 8, 15..
		
		String sql = "";
		
		if (category.equals("HOTEL") || category.equals("LANDSCAPE")) {
			sql = "SELECT *"
			   + " FROM (SELECT ROWNUM RNUM, A.*"
			   		 + " FROM (SELECT TS_NO, CATEGORY, NAME, ADDRESS, PHONE, PRICE, AREA,"
			   		 			  + " (SELECT COUNT(*) COUNT FROM MEMBERTRAVELHISTORY WHERE TRAVELTYPE1 = ? AND TRAVELSPOT1 = TS_NO) VISITCOUNT"
			   		 	   + " FROM " + category + " TS"
			   		 	   + " WHERE " + searchFilter + " LIKE '%'||?||'%' AND TS.STATUS = 'Y'" // 2022.2.1(화) 21h50 column ambiguously defined vs 그런데 selectTravelSpotList()에서는 오류 안 나는 듯?! 
			   		 	   + " ORDER BY TS_NO DESC) A)"
			   + " WHERE RNUM BETWEEN ? AND ?";
		} else {
			sql = "SELECT *"
			   + " FROM (SELECT ROWNUM RNUM, A.*"
			   		 + " FROM (SELECT TS_NO, CATEGORY, NAME, ADDRESS, PHONE, PRICE, AREA,"
			   		 			  + " (SELECT COUNT(*) COUNT FROM MEMBERTRAVELHISTORY WHERE TRAVELTYPE1 = ? AND TRAVELSPOT1 = TS_NO) VISITCOUNT, EQUIPMENT"
			   		 	   + " FROM " + category + " TS"
			   		 	   + " WHERE " + searchFilter + " LIKE '%'||?||'%' AND TS.STATUS = 'Y'"
			   		 	   + " ORDER BY TS_NO DESC) A)"
			   + " WHERE RNUM BETWEEN ? AND ?";
		}
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, category);
			pstmt.setString(2, keyword);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			
			rset = pstmt.executeQuery();
			
			switch (category) {
			case "HOTEL" :
				while (rset.next()) {
					ts = new Hotel(rset.getInt("TS_NO"),
								   rset.getString("CATEGORY"),
								   rset.getString("NAME"),
								   rset.getString("ADDRESS"),
								   rset.getString("PHONE"),
								   rset.getInt("PRICE"),
								   rset.getString("AREA"),
								   rset.getInt("VISITCOUNT"));
					
					tsList.add(ts);
				}
				break;
			case "LANDSCAPE" :
				while (rset.next()) {
					ts = new Landscape(rset.getInt("TS_NO"),
									   rset.getString("CATEGORY"),
									   rset.getString("NAME"),
									   rset.getString("ADDRESS"),
									   rset.getString("PHONE"),
									   rset.getInt("PRICE"),
									   rset.getString("AREA"),
									   rset.getInt("VISITCOUNT"));
					
					tsList.add(ts);
				}
				break;
			case "ACTIVITY" :
				while (rset.next()) {
					ts = new Activity(rset.getInt("TS_NO"),
									  rset.getString("CATEGORY"),
									  rset.getString("NAME"),
									  rset.getString("ADDRESS"),
									  rset.getString("PHONE"),
									  rset.getInt("PRICE"),
									  rset.getString("AREA"),
									  rset.getInt("VISITCOUNT"),
									  rset.getString("EQUIPMENT"));
					
					tsList.add(ts);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return tsList;
	} // travelSpotSearchKeyword() 종료
	
	// 2022.2.2(수) 17h55
	public ArrayList<Restaurant> restaurantSearchKeyword(Connection conn, PageInfo pi, String category, String searchFilter, String keyword) {
		ArrayList<Restaurant> rList = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		int endRow = pi.getCurrentPage() * pi.getBoardLimit(); // 7, 14, 21..
		int startRow = endRow - pi.getBoardLimit() + 1; // 1, 8, 15..
		
		String sql = "SELECT *"
				  + " FROM (SELECT ROWNUM RNUM, A.*"
			   			+ " FROM (SELECT RS_NO, CATEGORY, RS_NAME, ADDRESS, PHONE, MENU, PRICE, AREA,"
			   						+ " (SELECT COUNT(*) COUNT FROM MEMBERTRAVELHISTORY"
			   						+ " WHERE RESTAURANT1 = RS_NO OR RESTAURANT2 = RS_NO OR RESTAURANT3 = RS_NO) VISITCOUNT"
			   				  + " FROM RESTAURANT RS"
			   				  + " WHERE CATEGORY = ? AND " + searchFilter + " LIKE '%'||?||'%' AND RS.STATUS = 'Y'"
			   				  + " ORDER BY RS_NO DESC) A)"
			   	  + " WHERE RNUM BETWEEN ? AND ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, category);
			pstmt.setString(2, keyword);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				Restaurant r = new Restaurant(rset.getInt("RS_NO"),
											  rset.getString("CATEGORY"),
											  rset.getString("RS_NAME"),
											  rset.getString("ADDRESS"),
											  rset.getString("PHONE"),
											  rset.getString("MENU"),
											  rset.getInt("PRICE"),
											  rset.getString("AREA"),
											  rset.getInt("VISITCOUNT"));
				
				rList.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return rList;
	} // restaurantSearchKeyword() 종료

}
