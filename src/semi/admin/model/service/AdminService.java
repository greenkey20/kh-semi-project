package semi.admin.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import semi.admin.model.dao.AdminDao;
import semi.common.JDBCTemplate;
import semi.member.model.vo.Member;

public class AdminService {

	
	
	public ArrayList<Member> memberList() {

		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Member> list = new AdminDao().memberList(conn);
		
		JDBCTemplate.close(conn);
		
		return list;
		
	}

	public Member SearchMember(int searchKeyword) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		Member m = new AdminDao().SearchMember(searchKeyword, conn);
		
		JDBCTemplate.close(conn);

		return m;
	}

	public ArrayList memberDetail(int userNo) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList list = new AdminDao().memberDetail(userNo, conn);
		
		JDBCTemplate.close(conn);
		return list;
	}

	public ArrayList memberDetail2(int userNo) {

		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList list2 = new AdminDao().memberDetail2(userNo, conn);
		
		JDBCTemplate.close(conn);
		
		return list2;
		
	}
	
}