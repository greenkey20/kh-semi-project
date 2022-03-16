package semi.review.model.dao;

import static semi.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import semi.common.model.vo.PageInfo;
import semi.review.model.vo.Review;

public class ReviewDao {
	
	// 2022.1.23(일) 22h15
	private Properties prop = new Properties();

	public ReviewDao() {
		String fileName = ReviewDao.class.getResource("/sql/review/review-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int selectTsReviewListCount(Connection conn, int tsNo, String category) {
		// SELECT문 실행 -> ResultSet 결과 반환 vs 지금 내가 필요한 것은 총 리뷰의 개수(SELECT문을 쓰지만, 상식적으로 반환되는 값으로 정수가 필요함)
		
		// 필요한 변수 세팅
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT COUNT(*) COUNT FROM " + category + "REVIEW WHERE TS_NO = ? AND STATUS = 'Y'";
		// SELECT COUNT(*) COUNT FROM HOTELREVIEW WHERE TS_NO = ? AND STATUS = 'Y'
		// category에 전달된 값에 따라 아래와 같이 조건문 나눠서 썼었다가 -> 2022.1.28(금) 15h25 위와 같이 수정 -> sql문 반복 필요 없게 됨
//		sql = prop.getProperty("selectHotelReviewListCount");
//		sql = prop.getProperty("selectLandscapeReviewListCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tsNo);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				listCount = rset.getInt("COUNT");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return listCount;
	} // selectListCount() 종료
	
	public ArrayList<Review> selectTsReviewList(Connection conn, int tsNo, String category, PageInfo pi) {
		// SELECT문 실행 -> 사용자가 요청한 페이지(currentPage)에 boardLimit만큼 보여줄 리뷰 글들을 해당 category Review 테이블로부터 ResultSet으로 조회 받음 -> ArrayList<Review>에 담음
		
		// 필요한 변수 세팅
		ArrayList<Review> rvList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		int endRow = pi.getCurrentPage() * pi.getBoardLimit();
		int startRow = endRow - pi.getBoardLimit() + 1;
		
		String sql = "SELECT * FROM (SELECT ROWNUM RNUM, A.*"
				   + " FROM (SELECT USER_ID, REPLACE(USER_NAME, SUBSTR(USER_NAME, 2, 1), '*') USER_NAME, TS_NO, CATEGORY, REVIEW_CONTENT, CREATE_DATE, FILE_PATH||FILE_NAME TITLEIMG"
						   + " FROM " + category + "REVIEW TS"
						   + " LEFT JOIN MEMBER ON (REVIEW_WRITER = USER_NO)"
						   + " WHERE TS_NO = ? AND TS.STATUS = 'Y'"
						   + " ORDER BY CREATE_DATE DESC) A)"
				   + " WHERE RNUM BETWEEN ? AND ?";
		// SELECT *
//		FROM (SELECT ROWNUM RNUM, A.*
//		        FROM (SELECT USER_ID, REPLACE(USER_NAME, SUBSTR(USER_NAME, 2, 1), '*') USER_NAME, TS_NO, CATEGORY, REVIEW_CONTENT, CREATE_DATE, FILE_PATH||FILE_NAME "TITLEIMG"
//		                FROM HOTELREVIEW H
//		                LEFT JOIN MEMBER ON (REVIEW_WRITER = USER_NO)
//		                WHERE TS_NO = ? AND H.STATUS = 'Y'
//		                ORDER BY CREATE_DATE DESC) A)
//		WHERE RNUM BETWEEN ? AND ?
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, tsNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				Review rv = new Review(rset.getString("USER_ID"),
									   rset.getString("USER_NAME"),
									   rset.getInt("TS_NO"),
									   rset.getString("CATEGORY"),
									   rset.getString("REVIEW_CONTENT"),
									   rset.getDate("CREATE_DATE"),
									   rset.getString("TITLEIMG"));
				rvList.add(rv);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return rvList;
	} // selectTsReviewList() 종료
	
	// 2022.1.26(수) 16h15 여행 추천 페이지 + 2022.2.2(수) 23h 관리자 페이지에서 식당 상세 조회 시
	public int selectFoodReviewListCount(Connection conn, int rsNo, String category) {
		// SELECT문 실행 -> ResultSet 결과 반환 vs 지금 내가 필요한 것은 총 리뷰의 개수(SELECT문을 쓰지만, 상식적으로 반환되는 값으로 정수가 필요함)
		
		// 필요한 변수 세팅
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectFoodReviewListCount");
//		SELECT COUNT(*) COUNT FROM RESTAURANTREVIEW
//		WHERE RS_NO = ? AND CATEGORY = ? AND STATUS = 'Y'
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rsNo);
			pstmt.setString(2, category);
			
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
	} // selectFoodReviewListCount() 종료
	
	// 2022.1.26(수) 17h + 2022.2.2(수) 23h 관리자 페이지에서 식당 상세 조회 시
	public ArrayList<Review> selectFoodReviewList(Connection conn, int rsNo, String category, PageInfo pi) {
		// SELECT문 실행 -> 사용자가 요청한 페이지(currentPage)에 boardLimit만큼 보여줄 리뷰 글들을 해당 category Review 테이블로부터 ResultSet으로 조회 받음 -> ArrayList<Review>에 담음
		
		// 필요한 변수 세팅
		ArrayList<Review> rvList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		int endRow = pi.getCurrentPage() * pi.getBoardLimit();
		int startRow = endRow - pi.getBoardLimit() + 1;
		
		String sql = prop.getProperty("selectFoodReviewList");
		// SELECT *
	//		FROM (SELECT ROWNUM RNUM, A.*
	//		        FROM (SELECT USER_ID, REPLACE(USER_NAME, SUBSTR(USER_NAME, 2, 1), '*') USER_NAME, RS_NO, CATEGORY, REVIEW_CONTENT, CREATE_DATE, FILE_PATH||FILE_NAME "TITLEIMG"
	//		                FROM RESTAURANTREVIEW R
	//		                LEFT JOIN MEMBER ON (REVIEW_WRITER = USER_NO)
	//		                WHERE RS_NO = ? AND CATEGORY = ? AND R.STATUS = 'Y'
	//		                ORDER BY CREATE_DATE DESC) A)
	//		WHERE RNUM BETWEEN ? AND ?
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rsNo);
			pstmt.setString(2, category);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				Review rv = new Review(rset.getString("USER_ID"),
									   rset.getString("USER_NAME"),
									   rset.getInt("RS_NO"),
									   rset.getString("CATEGORY"),
									   rset.getString("REVIEW_CONTENT"),
									   rset.getDate("CREATE_DATE"),
									   rset.getString("TITLEIMG"));
				rvList.add(rv);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return rvList;
	} // selectFoodReviewList() 종료
	
	// 2022.1.26(수) 10h45 -> 2022.1.28(금) 15h45~ 필요 없음
	public int selectAReviewListCount(Connection conn, int tsNo) {
		// SELECT문 실행 -> ResultSet 결과 반환 vs 지금 내가 필요한 것은 총 리뷰의 개수(SELECT문을 쓰지만, 상식적으로 반환되는 값으로 정수가 필요함)
		
		// 필요한 변수 세팅
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectActivityReviewListCount");
		// SELECT COUNT(*) COUNT FROM ACTIVITYREVIEW WHERE TS_NO = ? AND STATUS = 'Y'
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tsNo);
			
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
	} // selectAReviewListCount() 종료
	
	// 2022.1.26(수) 11h35
	public ArrayList<Review> selectAReviewList(Connection conn, int tsNo, PageInfo pi) {
		// SELECT문 실행 -> 사용자가 요청한 페이지(currentPage)에 boardLimit만큼 보여줄 리뷰 글들을 해당 category Review 테이블로부터 ResultSet으로 조회 받음 -> ArrayList<Review>에 담음
		
		// 필요한 변수 세팅
		ArrayList<Review> rvList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		int endRow = pi.getCurrentPage() * pi.getBoardLimit();
		int startRow = endRow - pi.getBoardLimit() + 1;
		
		String sql = prop.getProperty("selectActivityReviewList");
		// SELECT *
//				FROM (SELECT ROWNUM RNUM, A.*
//				        FROM (SELECT USER_ID, REPLACE(USER_NAME, SUBSTR(USER_NAME, 2, 1), '*') USER_NAME, TS_NO, CATEGORY, REVIEW_CONTENT, CREATE_DATE, FILE_PATH||FILE_NAME "TITLEIMG"
//				                FROM ACTIVITYREVIEW A
//				                LEFT JOIN MEMBER ON (REVIEW_WRITER = USER_NO)
//				                WHERE TS_NO = ? AND A.STATUS = 'Y'
//				                ORDER BY CREATE_DATE DESC) A)
//				WHERE RNUM BETWEEN ? AND ?;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, tsNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				Review rv = new Review(rset.getString("USER_ID"),
									   rset.getString("USER_NAME"),
									   rset.getInt("TS_NO"),
									   rset.getString("CATEGORY"),
									   rset.getString("REVIEW_CONTENT"),
									   rset.getDate("CREATE_DATE"),
									   rset.getString("TITLEIMG"));
				rvList.add(rv);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return rvList;
	} // selectAReviewList() 종료

}
