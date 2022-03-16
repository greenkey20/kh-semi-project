package semi.review.model.vo;

import java.sql.Date;

//2022.1.23(일) 21h40
public class Review {
	// 필드들 
	private int reviewNo; // REVIEW_NO	NUMBER	No -> 공통, 리뷰 번호
	private String reviewWriter; // REVIEW_WRITER	NUMBER	No -> 공통, 리뷰 작성자; userNo나 userId 모두 담을 수 있도록 String 자료형으로 만듦
	private String reviewWriterId; // 2022.1.24(월) 0h40 DB로부터 리뷰 글 목록 받아오며 추가 -> 리뷰 작성자 ID
	private String reviewWriterName; // 2022.1.24(월) 0h40 DB로부터 리뷰 글 목록 받아오며 추가 -> 리뷰 작성자 이름
	private int sNo; // NUMBER	No -> 다름; 여행지 TS_NO vs 식당 RS_NO -> 여기서는 여행지, 식당 공통적으로 spotNumber로 명명해서 사용하고자 함
	private String category; // CATEGORY	VARCHAR2(20 BYTE)	Yes -> 공통
	private String reviewContent; // REVIEW_CONTENT	VARCHAR2(2000 BYTE)	No -> 공통
	private Date createDate; // CREATE_DATE	DATE	Yes	SYSDATE -> 공통
	private String status; // STATUS	CHAR(1 BYTE) 'Y' -> 공통
	private String filePath; // FILE_PATH	VARCHAR2(1000 BYTE) -> 공통, 필수 입력 사항 아님
	private String fileName; // FILE_NAME	VARCHAR2(255 BYTE) -> 공통, 필수 입력 사항 아님
	private String titleImg; // titleImg -> 업체 소개 이미지의 파일명 = DB 조회 결과상 별칭 TITLEIMG에 해당하는 필드
	
	private String rsName;
	
	public Review() {
		super();
	}

	public Review(int reviewNo, String reviewWriter, String reviewWriterId, String reviewWriterName, int sNo, String category, String reviewContent, Date createDate,
			String status, String filePath, String fileName, String titleImg) {
		super();
		this.reviewNo = reviewNo;
		this.reviewWriter = reviewWriter;
		this.reviewWriterId = reviewWriterId;
		this.reviewWriterName = reviewWriterName;
		this.sNo = sNo;
		this.category = category;
		this.reviewContent = reviewContent;
		this.createDate = createDate;
		this.status = status;
		this.filePath = filePath;
		this.fileName = fileName;
		this.titleImg = titleImg;
	}

	// 2022.1.24(월) 0h55 DB로부터 리뷰 글 목록 받아오며 추가
	public Review(String reviewWriterId, String reviewWriterName, int sNo, String category, String reviewContent,
			Date createDate, String titleImg) {
		super();
		this.reviewWriterId = reviewWriterId;
		this.reviewWriterName = reviewWriterName;
		this.sNo = sNo;
		this.category = category;
		this.reviewContent = reviewContent;
		this.createDate = createDate;
		this.titleImg = titleImg;
	}

	public int getReviewNo() {
		return reviewNo;
	}

	public void setReviewNo(int reviewNo) {
		this.reviewNo = reviewNo;
	}

	public String getReviewWriter() {
		return reviewWriter;
	}

	public void setReviewWriter(String reviewWriter) {
		this.reviewWriter = reviewWriter;
	}

	public int getsNo() {
		return sNo;
	}

	public void setsNo(int sNo) {
		this.sNo = sNo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}

	public String getReviewWriterId() {
		return reviewWriterId;
	}

	public void setReviewWriterId(String reviewWriterId) {
		this.reviewWriterId = reviewWriterId;
	}

	public String getReviewWriterName() {
		return reviewWriterName;
	}

	public void setReviewWriterName(String reviewWriterName) {
		this.reviewWriterName = reviewWriterName;
	}

	public String getRsName() {
		return rsName;
	}

	public void setRsName(String rsName) {
		this.rsName = rsName;
	}

	@Override
	public String toString() {
		return "Review [reviewNo=" + reviewNo + ", reviewWriter=" + reviewWriter + ", reviewWriterId=" + reviewWriterId
				+ ", reviewWriterName=" + reviewWriterName + ", sNo=" + sNo + ", category=" + category
				+ ", reviewContent=" + reviewContent + ", createDate=" + createDate + ", status=" + status
				+ ", filePath=" + filePath + ", fileName=" + fileName + ", titleImg=" + titleImg + "]";
	}

}
