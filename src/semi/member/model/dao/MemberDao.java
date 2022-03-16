package semi.member.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import semi.member.model.vo.Member;
import semi.member.model.vo.MemberTravelHistory;

import static semi.common.JDBCTemplate.*;

public class MemberDao {

	// 2022.1.27(목) 14h40
	private Properties prop = new Properties();

	// 기본 생성자; 이 생성자 내부에 파일 호출하는 코드 작성 -> 이 생성자가 호출될 때마다/dao 객체 생성 시 아래 xml 파일 다시 읽어옴
	public MemberDao() {
		String fileName = MemberDao.class.getResource("/sql/member/member-mapper.xml").getPath();

		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

	} // 기본생성자 영역 끝

	// 2022.1.24(월) 16h5 평가자 checklist - service 클래스, JDBC Template 등 없다고 가정
	/*
	 * public Boolean isEmailExist(String email) { // SELECT문 실행 -> ResultSet 조회 결과 반환 -> COUNT 함수 이용해서 숫자 1개에 담음
	 * 
	 * // 필요한 변수 세팅
	 * Boolean result = false;
	 * int count = 0;
	 * Connection conn = null;
	 * PreparedStatement pstmt = null;
	 * ResultSet rset = null;
	 * 
	 * String sql = "SELECT COUNT(*) FROM MEMBER WHERE EMAIL = ?";
	 * // SELECT COUNT(*) FROM MEMBER WHERE EMAIL = '사용자가 입력한 email';
	 * 
	 * try {
	 * // jdbc driver 등록
	 * Class.forName("oracle.jdbc.driver.OracleDriver");
	 * 
	 * // Connection 객체 생성
	 * conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SEMI3", "SEMI3");
	 * 
	 * // PreparedStatement 객체 생성 pstmt = conn.prepareStatement(sql);
	 * pstmt.setString(1, email);
	 * 
	 * rset = pstmt.executeQuery();
	 * 
	 * if (rset.next()) {
	 * 		count = rset.getInt("COUNT(*)"); }
	 * 
	 * } catch (ClassNotFoundException e) { 
	 * e.printStackTrace(); 
	 * } catch
	 * (SQLException e) { 
	 * e.printStackTrace(); 
	 * } finally { 
	 * try { 
	 * rset.close();
	 * pstmt.close();
	 * conn.close(); 
	 * } catch (SQLException e) { 
	 * e.printStackTrace();
	 * }
	 * }
	 * 
	 * if (count > 0) { // db에 일치하는 email이 있는 경우 = 이미 사용 중인 email result = true; }
	 * else { // db에 일치하는 email이 없는 경우 = 사용 가능한 email result = false; }
	 * 
	 * return result; }
	 */

	public int insertMemberTravelHistory(Connection conn, MemberTravelHistory mth) {
		// INSERT문 실행 -> 처리된 행의 개수(int 자료형) 반환

		// 필요한 변수들 세팅
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertMemberTravelHistory");
//		 INSERT INTO MEMBERTRAVELHISTORY(MTH_NO, USER_NO,
//			TRAVELTYPE1, TRAVELSPOT1, TRAVELTYPE3, TRAVELSPOT3,
//			FOODTYPE, RESTAURANT1, RESTAURANT2, RESTAURANT3)
//		 VALUES(SEQ_MTHNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?);

		// 2022.1.29(토) 21h55 TRAVELTYPE3, TRAVELSPOT3 삭제
		// INSERT INTO MEMBERTRAVELHISTORY(MTH_NO, USER_NO,
//			TRAVELTYPE1, TRAVELSPOT1, 
//			FOODTYPE, RESTAURANT1, RESTAURANT2, RESTAURANT3)
//			VALUES(SEQ_MTHNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)

		try {
			pstmt = conn.prepareStatement(sql);
			// 위치 홀더 9개
			pstmt.setInt(1, mth.getUserNo());
			pstmt.setString(2, mth.getTravelType1());
			pstmt.setInt(3, mth.getTravelSpot1());
			pstmt.setString(4, mth.getFoodType());
			pstmt.setInt(5, mth.getRestaurant1());
			pstmt.setInt(6, mth.getRestaurant2());
			pstmt.setInt(7, mth.getRestaurant3());

			result = pstmt.executeUpdate(); // 1(행 INSERT 성공) 또는 0 반환
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	} // insertMemberTravelHistory() 종료

	// 2022.2.2(수) 유경님 작업
	public Member loginMember(Connection conn, String userId, String userPwd) {
		Member m = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("loginMember");
		// SELECT *
//  	  FROM MEMBER
//		WHERE USER_ID = ?
//  	  AND USER_PWD = ?
//  	  AND STATUS = 'Y'

		try {
			// pstmt객체 생성
			pstmt = conn.prepareStatement(sql);

			// 구멍메꾸기
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);

			// 쿼리문 실행 후 결과 받기
			// 쿼리문 실행 메소드
			// pstmt.executeQuery(); => SELECT
			// pstmt.executeUpdate(); => INSERT/UPDATE/DELETE
			rset = pstmt.executeQuery();

			// rset으로부터 각각의 컬럼값을 뽑아서 Member객체 담는다.
			// rset.next()
			// 조회 결과가 여러행일 때 => while(rest.next())
			// 조회 결과가 한행일 때 => if(rset.next())
			if (rset.next()) {
				// 각 컬럼의 값을 뽑기
				// rset.getInt / getString / getDate(뽑아올 컬럼명 또는 컬럼의 순번)
				m = new Member(rset.getInt("USER_NO"), rset.getString("USER_ID"), rset.getString("USER_PWD"),
						rset.getString("USER_NAME"), rset.getString("PHONE"), rset.getString("EMAIL"),
						rset.getString("ADDRESS"), rset.getString("INTEREST"), rset.getDate("ENROLL_DATE"),
						rset.getDate("MODIFY_DATE"), rset.getString("STATUS"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 자원반납 => 생성된 순서의 역순
			close(rset);
			close(pstmt);
		}
		
		// Service에 결과(Member) 넘기기
		return m;
	}

	public String findId(Connection conn, String name, String email) {
		String userId = "";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("findId");
		// SELECT USER_ID FROM MEMBER
//		   WHERE USER_NAME = ?
//				   AND EMAIL = ?
//				   AND STATUS = 'Y'
//		System.out.println(sql);
		
		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, name);
			pstmt.setString(2, email);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				userId = rset.getString("USER_ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 자원반납 => 생성된 순서의 역순
			close(rset);
			close(pstmt);
		}
		
		return userId;
	}

	public int emailCheck(Connection conn, String email, String userId) {
		int result = 0;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("findpWd");
		// SELECT COUNT(*) COUNT FROM MEMBER
//		   WHERE USER_ID = ?
//				   AND EMAIL = ?
//				   AND STATUS = 'Y'

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setString(2, email);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				result = rset.getInt("COUNT");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return result;
	}
	
	public int temporaryPwd(Connection conn, int authNumber, String email, String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("temporaryPwd");
		// UPDATE MEMBER
//		   SET USER_PWD = ?, MODIFY_DATE = SYSDATE
//		   WHERE USER_ID = ? AND EMAIL = ?   

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, authNumber + "");
			pstmt.setString(2, userId);
			pstmt.setString(3, email);

			result = pstmt.executeUpdate(); // 비밀번호를 찾고자 하는 회원의 비밀번호를 임시비밀번호로 변경 성공하면 1 vs 실패하면 0 반환

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int insertMember(Connection conn, Member m) {

		int result = 0;

		PreparedStatement pstmt = null;

		String sql = prop.getProperty("insertMember");
		// INSERT INTO MEMBER(USER_NO, USER_ID, USER_PWD, USER_NAME, EMAIL, PHONE,
		// ADDRESS, INTEREST)
		// VALUES(SEQ_UNO.NEXTVAL, 아이디, 비밀번호, 이름, 이메일, 전화번호, 주소, 취미)

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(5, m.getEmail());
			pstmt.setString(4, m.getPhone());
			pstmt.setString(6, m.getAddress());
			pstmt.setString(7, m.getInterest());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int idCheck(Connection conn, String checkId) {
		// SELECT => ResultSet => COUNT 함수 이용(숫자 한 개)
		// 변수
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("idCheck");
		// SELECT COUNT(*) FROM MEMBER WHERE USER_ID = ?;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, checkId);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				count = rset.getInt("COUNT(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return count;
	}

}
