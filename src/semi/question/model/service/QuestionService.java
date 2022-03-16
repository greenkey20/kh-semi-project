package semi.question.model.service;

import semi.common.model.vo.PageInfo;
import semi.question.model.dao.QuestionDao;
import semi.question.model.vo.Question;
import static semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

public class QuestionService {

	public int insertQuestion(Question q) {
		
		Connection conn = getConnection();
		
		int result = new QuestionDao().insertQuestion(conn, q);
		
		if(result > 0) { // 성공
			commit(conn);
		} else { // 실패
			rollback(conn);
		}
		close(conn);
		
		return result;
		
	}


	public ArrayList<Question> selectQuestionList(PageInfo pi) {
		
		Connection conn = getConnection();
		
		ArrayList<Question> list = new QuestionDao().selectQuestionList(conn, pi);
		
		close(conn);
		
		return list;
	}


	public Question questionDetail(int questionNo) {

		Connection conn = getConnection();
		
		Question q = new QuestionDao().questionDetail(conn, questionNo);
		close(conn);
		
		return q;
	}


	public int answer(int qNo, String answer) {
		Connection conn = getConnection();
		int result = new QuestionDao().answer(conn, qNo, answer);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result;
	}


	public int listCount() {
		
		Connection conn = getConnection();
		
		int listCount = new QuestionDao().listCount(conn);
		
		close(conn);
		
		return listCount;
	}


	public int questionDelete(int qNo) {
		Connection conn = getConnection();
		int result = new QuestionDao().questionDelete(conn, qNo);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result;
	}


}
