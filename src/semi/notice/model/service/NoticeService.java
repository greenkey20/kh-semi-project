package semi.notice.model.service;

import static semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import semi.notice.model.dao.NoticeDao;
import semi.notice.model.vo.Notice;

public class NoticeService {

	public ArrayList<Notice> selectNoticeList() {

		Connection conn = getConnection();

		ArrayList<Notice> list = new NoticeDao().selectNoticeList(conn);

		close(conn);

		return list;
	}

	public int insertNotice(Notice n) {

		Connection conn = getConnection();

		int result = new NoticeDao().insertNotice(conn, n);

		if (result > 0) { // 성공
			commit(conn);
		} else { // 실패
			rollback(conn);
		}
		close(conn);

		return result;
	}

	public int increaseCount(int noticeNo) {

		Connection conn = getConnection();

		int result = new NoticeDao().increaseCount(conn, noticeNo);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);

		return result;

	}

	public Notice selectNotice(int noticeNo) {

		Connection conn = getConnection();

		Notice n = new NoticeDao().selectNotice(conn, noticeNo);

		close(conn);

		return n;

	}

	public int updateNotice(Notice n) {

		Connection conn = getConnection();

		int result = new NoticeDao().updateNotice(conn, n);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		close(conn);

		return result;
	}

	public int deleteNotice(int noticeNo) {

		Connection conn = getConnection();

		int result = new NoticeDao().deleteNotice(conn, noticeNo);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);

		return result;
	}

}
