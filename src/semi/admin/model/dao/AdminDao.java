package semi.admin.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import semi.common.model.vo.PageInfo;
import semi.member.model.vo.MemberTravelHistory;
import semi.review.model.vo.Review;
import semi.common.JDBCTemplate;
import semi.member.model.dao.MemberDao;
import semi.member.model.vo.Member;

public class AdminDao {
	
private Properties prop = new Properties();
	
	public AdminDao() {
		String fileName = MemberDao.class.getResource("/sql/member/member-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Member> memberList(Connection conn) {
		
		// SELECT문 => ResultSet => 여러행 => ArrayList<Notice>
		
		// 필요한 변수들
		ArrayList<Member> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
				
		String sql = prop.getProperty("selectMemberList");
		/*
		SELECT
	    	  USER_NO
			  ,USER_ID
			  ,USER_NAME
			  ,EMAIL
			  ,ENROLL_DATE
		  FROM 
			  MEMBER 
	   	 ORDER BY USER_NO DESC
		*/
		try {
			pstmt = conn.prepareStatement(sql);
					
			rset = pstmt.executeQuery();
					
			while(rset.next()) {
				Member m = new Member(rset.getInt("USER_NO"),
									  rset.getString("USER_ID"),
									  rset.getString("USER_NAME"),
									  rset.getString("EMAIL"),
									  rset.getDate("ENROLL_DATE"));
						
				list.add(m);
			}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(rset);
				JDBCTemplate.close(pstmt);
			}
			return list;
			}

	public Member SearchMember(int searchKeyword, Connection conn) {

		Member m = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("searchMemberKeyword");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, searchKeyword);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				m = new Member(rset.getInt("USER_NO"),
						  	   rset.getString("USER_ID"),
						  	   rset.getString("USER_PWD"),
						  	   rset.getString("USER_NAME"),
						  	   rset.getString("PHONE"),
						  	   rset.getString("EMAIL"),
						  	   rset.getString("ADDRESS"),
						  	   rset.getString("INTEREST"),
						  	   rset.getDate("ENROLL_DATE"));
				System.out.println(m);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return m;
	}

	public ArrayList<MemberTravelHistory> memberDetail(int userNo, Connection conn) {

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<MemberTravelHistory> list = new ArrayList<>();
		
		String sql = prop.getProperty("memberDetail");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				// TRAVEL_DATE, TRAVELTYPE1, TS_NAME, FOODTYPE, R1.RS_NAME
				MemberTravelHistory mth = new MemberTravelHistory();
				mth.setTravelDate(rset.getDate("TRAVEL_DATE"));			
				mth.setTravelType1(rset.getString("TRAVELTYPE1"));
				mth.setTravelSpotName(rset.getString("TS_NAME"));
				mth.setFoodType(rset.getString("FOODTYPE"));
				mth.setRestaurantName(rset.getString("RS_NAME"));
				mth.setRestaurantName2(rset.getString("RS_NAME2"));
				mth.setRestaurantName3(rset.getString("RS_NAME3"));
				
				list.add(mth);
			}
			System.out.println(list);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return list;
		
		
		
	}

	public ArrayList<Review> memberDetail2(int userNo, Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Review> list2 = new ArrayList<>();
		
		String sql = prop.getProperty("memberDetail2");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, userNo);
			pstmt.setInt(3, userNo);
			pstmt.setInt(4, userNo);
			rset = pstmt.executeQuery();
			
			//CATEGORY, RS_NAME,CREATE_DATE, REVIEW_CONTENT
			while(rset.next()) {
				Review rv = new Review(); 
				rv.setCategory(rset.getString("CATEGORY"));
				rv.setRsName(rset.getString("NAME"));
				rv.setCreateDate(rset.getDate("CREATE_DATE"));
				rv.setReviewContent(rset.getString("REVIEW_CONTENT"));
				
				list2.add(rv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return list2;
		
	}
	
//	public ArrayList<Member> selectList(Connection conn, PageInfo pi) {
//		
//		
//		ArrayList<Member> list = new ArrayList<>();
//		PreparedStatement pstmt = null;
//		ResultSet rset = null;
//		
//		String sql = prop.getProperty("selectList");
//		
//		try {
//			pstmt = conn.prepareStatement(sql);
//			
//			
//			
//			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
//			int endRow = startRow + pi.getBoardLimit() -1;
//			
//			pstmt.setInt(1, startRow);
//			pstmt.setInt(2, endRow);
//			
//			rset = pstmt.executeQuery();
//			
//			while(rset.next()) {		
//				Member m = new Member(rset.getInt("USER_NO"),
//						  rset.getString("USER_ID"),
//						  rset.getString("USER_NAME"),
//						  rset.getString("EMAIL"),
//						  rset.getDate("ENROLL_DATE"));
//			
//				list.add(m);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			JDBCTemplate.close(rset);
//			JDBCTemplate.close(pstmt);
//		}
//		return list;
//	}
//	
	
}