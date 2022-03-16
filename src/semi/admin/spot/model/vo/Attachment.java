package semi.admin.spot.model.vo;

import java.sql.Date;

// 2022.1.31(월) 14h35
public class Attachment {
	
	private int fileNo; // FILE_NO	NUMBER	No		1	파일번호 -> primary key
	private int refTsNo; // REF_TSNO	NUMBER	No		2	참조여행지번호
	private String refCategory; // REF_CATEGORY	VARCHAR2(20 BYTE)	No		3	참조여행지타입
	private String originName; // ORIGIN_NAME	VARCHAR2(255 BYTE)	No		4	파일원본명
	private String changeName; // CHANGE_NAME	VARCHAR2(255 BYTE)	No		5	파일수정명
	private String filePath; // FILE_PATH	VARCHAR2(1000 BYTE)	Yes		6	저장폴더경로
	private Date uploadDate; // UPLOAD_DATE	DATE	No	SYSDATE 	7	업로드일
	private int fileLevel; // FILE_LEVEL	NUMBER	Yes		8	파일레벨(1/2) -> titleImg/대표이미지 1 vs 상세/optional/추가 img 2
	private String status; // STATUS	VARCHAR2(1 BYTE)	Yes	'Y' 	9	
	
	public Attachment() {
		super();
	}

	public Attachment(int fileNo, int refTsNo, String refCategory, String originName, String changeName,
			String filePath, Date uploadDate, int fileLevel, String status) {
		super();
		this.fileNo = fileNo;
		this.refTsNo = refTsNo;
		this.refCategory = refCategory;
		this.originName = originName;
		this.changeName = changeName;
		this.filePath = filePath;
		this.uploadDate = uploadDate;
		this.fileLevel = fileLevel;
		this.status = status;
	}

	public int getFileNo() {
		return fileNo;
	}

	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}

	public int getRefTsNo() {
		return refTsNo;
	}

	public void setRefTsNo(int refTsNo) {
		this.refTsNo = refTsNo;
	}

	public String getRefCategory() {
		return refCategory;
	}

	public void setRefCategory(String refCategory) {
		this.refCategory = refCategory;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getChangeName() {
		return changeName;
	}

	public void setChangeName(String changeName) {
		this.changeName = changeName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public int getFileLevel() {
		return fileLevel;
	}

	public void setFileLevel(int fileLevel) {
		this.fileLevel = fileLevel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Attachment [fileNo=" + fileNo + ", refTsNo=" + refTsNo + ", refCategory=" + refCategory
				+ ", originName=" + originName + ", changeName=" + changeName + ", filePath=" + filePath
				+ ", uploadDate=" + uploadDate + ", fileLevel=" + fileLevel + ", status=" + status + "]";
	}
	
}
