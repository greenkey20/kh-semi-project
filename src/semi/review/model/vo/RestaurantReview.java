package semi.review.model.vo;

// 2022.1.23(일) 21h40
public class RestaurantReview {
	
	// REVIEW_NO	NUMBER	No -> 공통
	// REVIEW_WRITER	NUMBER	No -> 공통
	// RS_NO	NUMBER	No -> 다름; 여행지 TS_NO vs 식당 RS_NO
	// CATEGORY	VARCHAR2(20 BYTE)	Yes -> 공통
	// REVIEW_CONTENT	VARCHAR2(2000 BYTE)	No -> 공통
	// CREATE_DATE	DATE	Yes	SYSDATE -> 공통
	// STATUS	CHAR(1 BYTE) 'Y' -> 공통
	// FILE_PATH	VARCHAR2(1000 BYTE) -> 공통	
	// FILE_NAME	VARCHAR2(255 BYTE) -> 공통
	// titleImg -> 업체 소개 이미지의 파일명 = DB 조회 결과상 별칭 TITLEIMG에 해당하는 필드
	

}
