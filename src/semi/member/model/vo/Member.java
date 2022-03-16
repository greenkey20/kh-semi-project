package semi.member.model.vo;

import java.sql.Date;

// 2022.1.19(수) 17h 민성님께서 카톡으로 공유해주심
public class Member {

	private int userNo; // USER_NO	NUMBER -> primary key, seq_mno 
	private String userId; // USER_ID	VARCHAR2(20 BYTE) -> 2022.2.4(금) 14h15 은영 생각 = UNIQUE 제약조건 부여했어야 한다는 생각이 듦 ㅠ.ㅠ -> sql 파일 추가해서 "ALTER TABLE MEMBER ADD CONSTRAINT MEM_USERID_UNQ UNIQUE(USER_ID);" 
	private String userPwd; // USER_PWD	VARCHAR2(20 BYTE)
	private String userName; // USER_NAME	VARCHAR2(20 BYTE)
	private String phone; // PHONE	VARCHAR2(13 BYTE) -> 필수 입력 사항 아님
	private String email; // EMAIL	VARCHAR2(100 BYTE)
	private String address; // ADDRESS	VARCHAR2(100 BYTE) -> 필수 입력 사항 아님
	private String interest; // INTEREST	VARCHAR2(200 BYTE) -> 필수 입력 사항 아님
	private Date enrollDate; // ENROLL_DATE	DATE -> 기본값 sysdate
	private Date modifyDate; // MODIFY_DATE	DATE -> 기본값  sysdate
	private String status;  // STATUS	CHAR(1 BYTE) -> 기본값 'Y'
	
	public Member() {
		super();
	}

	public Member(int userNo, String userId, String userPwd, String userName, String phone, String email,
			String address, String interest, Date enrollDate, Date modifyDate, String status) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.interest = interest;
		this.enrollDate = enrollDate;
		this.modifyDate = modifyDate;
		this.status = status;
	}

	public Member(String userId, String userPwd, String userName, String phone, String email, String address,
			String interest) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.interest = interest;
	}

	public Member(int userNo, String userId, String userName, String email, Date enrollDate) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.enrollDate = enrollDate;
	}

	public Member(int userNo, String userId, String userPwd, String userName, String phone, String email,
			String address, String interest, Date enrollDate) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.interest = interest;
		this.enrollDate = enrollDate;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Member [userNo=" + userNo + ", userId=" + userId + ", userPwd=" + userPwd + ", userName=" + userName
				+ ", phone=" + phone + ", email=" + email + ", address=" + address + ", interest=" + interest
				+ ", enrollDate=" + enrollDate + ", modifyDate=" + modifyDate + ", status=" + status + "]";
	}

}
