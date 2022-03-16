package semi.question.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import static semi.common.JDBCTemplate.*;

import semi.common.model.vo.PageInfo;
import semi.question.model.vo.Question;

public class QuestionDao {
	
	private Properties prop = new Properties();
	
	public QuestionDao() {
		
		String fileName = QuestionDao.class.getResource("/sql/question/question-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int insertQuestion(Connection conn, Question q) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertQuestion");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, q.getQuestionTitle());
			pstmt.setString(2, q.getQuestionContent());
			pstmt.setInt(3, q.getUserNo());
			pstmt.setString(4, q.getUserId());
			pstmt.setString(5, q.getUserName());
			
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public ArrayList<Question> selectQuestionList(Connection conn, PageInfo pi) {
		
		
		ArrayList<Question> list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		
		// SELECT문 조회결과 => ResultSet => 여러행 ArrayList<>
		ResultSet rset = null; 
		
		String sql = prop.getProperty("selectQuestionList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() - 1) * pi.getPageLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				Question q = new Question(rset.getInt("Q_NO"),
										  rset.getString("Q_TITLE"),
										  rset.getString("USER_NAME"),
										  rset.getString("USER_ID"),
										  rset.getDate("Q_DATE"),
										  rset.getString("A_STATUS")
						);	
				
				list.add(q);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}


	public Question questionDetail(Connection conn, int questionNo) {
		Question q = null;//비어있는 객체
		PreparedStatement pstmt = null;
		
		ResultSet rset = null;
		
		String sql = prop.getProperty("questionDetail");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, questionNo);//
			
			rset = pstmt.executeQuery();
			//ResultSet.next() -> 해당 로우 다음 줄에 있냐 없냐를 조사해주는 메소드 
			// 결과가 하나 일땐 조건문으로 충분히 가능 하지만 여러개 일 경우 while문을 쓴다 
			
			if(rset.next()) {
				q = new Question(); // 값은 비어있지만 안에 내용물이 있는 객체로 다시 선언
				q.setQuestionNo(rset.getInt("Q_NO"));
				q.setQuestionTitle(rset.getString("Q_TITLE"));
				q.setQuestionContent(rset.getString("Q_CONTENT"));
				q.setUserName(rset.getString("USER_NAME"));
				q.setUserId(rset.getString("USER_ID"));
				q.setCreateDate(rset.getDate("Q_DATE"));
				q.setAnswer(rset.getString("ANSWER"));
				q.setaStatus(rset.getString("A_STATUS"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return q;
	}

	public int answer(Connection conn, int qNo, String answer) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("answer");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, answer);
			pstmt.setInt(2, qNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}

	public int listCount(Connection conn) {

		int listCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("listCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt("COUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}

	public int questionDelete(Connection conn, int qNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("questionDelete");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}

}
