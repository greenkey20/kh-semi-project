package semi.review.model.service;

import static semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import semi.common.model.vo.PageInfo;
import semi.review.model.dao.ReviewDao;
import semi.review.model.vo.Review;

public class ReviewService {
	
	// 2022.1.23(일) 22h10
	public int selectTsReviewListCount(int tsNo, String category) {
		// category = HOTEL 또는 LANDSCAPE 또는 ACTIVITY(대문자 표기) 넘어옴
		Connection conn = getConnection();
		
		int listCount = new ReviewDao().selectTsReviewListCount(conn, tsNo, category);
		
		close(conn);
		
		return listCount;
	} // selectListCount() 종료
	
	// 2022.1.23(일) 23h45
	public ArrayList<Review> selectTsReviewList(int tsNo, String category, PageInfo pi) {
		Connection conn = getConnection();
		
		ArrayList<Review> rvList = new ReviewDao().selectTsReviewList(conn, tsNo, category, pi);
		
		close(conn);
		
		return rvList;
	} // selectTsReviewList() 종료
	
	// 2022.1.26(수) 10h45
	public int selectAReviewListCount(int tsNo) {
		Connection conn = getConnection();
		
		int listCount = new ReviewDao().selectAReviewListCount(conn, tsNo);
		
		close(conn);
		
		return listCount;
	} // selectAReviewListCount() 종료
	
	// 2022.1.26(수) 11h35
	public ArrayList<Review> selectAReviewList(int tsNo, PageInfo pi) {
		Connection conn = getConnection();
		
		ArrayList<Review> rvList = new ReviewDao().selectAReviewList(conn, tsNo, pi);
		
		close(conn);
		
		return rvList;
	} // selectAReviewList() 종료
	
	// 2022.1.26(수) 16h15 + 2022.2.2(수) 23h 관리자 페이지에서 식당 상세 조회 시
	public int selectFoodReviewListCount(int rsNo, String category) {
		Connection conn = getConnection();
		
		int listCount = new ReviewDao().selectFoodReviewListCount(conn, rsNo, category);
		
		close(conn);
		
		return listCount;
	} // selectFoodReviewListCount() 종료
	
	// 2022.1.26(수) 16h50 + 2022.2.2(수) 23h 관리자 페이지에서 식당 상세 조회 시
	public ArrayList<Review> selectFoodReviewList(int rsNo, String category, PageInfo pi) {
		Connection conn = getConnection();
		
		ArrayList<Review> rvList = new ReviewDao().selectFoodReviewList(conn, rsNo, category, pi);
		
		close(conn);
		
		return rvList;
	} // selectFoodReviewList() 종료

}
