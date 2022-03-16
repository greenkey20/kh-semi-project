package semi.member.model.service;

import static semi.common.JDBCTemplate.*;

import java.sql.Connection;

import semi.member.model.dao.MemberDao;
import semi.member.model.vo.Member;
import semi.member.model.vo.MemberTravelHistory;

public class MemberService {

	// 2022.1.27(목) 14h10
	public int insertMemberTravelHistory(MemberTravelHistory mth) {
		Connection conn = getConnection();

		int result = new MemberDao().insertMemberTravelHistory(conn, mth);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		close(conn);

		return result;
	} // insertMemberTravelHistory() 종료

	// 2022.2.2(수) 유경님 작업
	public Member loginMember(String userId, String userPwd) {

		Connection conn = getConnection();

		// 2) Dao에 요청
		Member m = new MemberDao().loginMember(conn, userId, userPwd);

		// 3) Connection 객체 반납
		close(conn);

		// 4) Controller로 결과 넘기기
		return m;

	}

	public String findId(String name, String email) {
		Connection conn = getConnection();

		String userId = new MemberDao().findId(conn, name, email);

		close(conn);

		return userId;
	}

	public int emailCheck(String email, String userId) {
		Connection conn = getConnection();

		int result = new MemberDao().emailCheck(conn, email, userId);

		close(conn);

		return result;
	}

	public int temporaryPwd(int authNumber, String email, String userId) {
		Connection conn = getConnection();

		int result = new MemberDao().temporaryPwd(conn, authNumber, email, userId);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);

		return result;
	}

	public int insertMember(Member m) {

		Connection conn = getConnection();

		int result = new MemberDao().insertMember(conn, m);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		close(conn);

		return result;
	}

	public int idCheck(String checkId) {
		Connection conn = getConnection();
		int count = new MemberDao().idCheck(conn, checkId);
		close(conn);

		return count;
	}

}
