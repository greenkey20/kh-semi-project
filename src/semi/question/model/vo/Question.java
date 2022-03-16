package semi.question.model.vo;

import java.sql.Date;

public class Question {
	private int questionNo; // NOTICE_NO NUMBER
	private String questionTitle; // NOTICE_TITLE VARCHAR2(100 BYTE)
	private String questionContent; // NOTICE_CONTENT VARCHAR2(4000 BYTE)
	private int userNo; // NOTICE_WRITER NUMBER
	private String status; // STATUS VARCHAR2(1 BYTE)
	private String userName;
	private String userId;
	private Date createDate; // CREATE_DATE DATE
	private String answer;
	private String aStatus;

	public Question() {
		super();
	}

	public Question(int questionNo, String questionTitle, String userName, String userId, Date createDate,
			String aStatus) {
		super();
		this.questionNo = questionNo;
		this.questionTitle = questionTitle;
		this.userName = userName;
		this.userId = userId;
		this.createDate = createDate;
		this.aStatus = aStatus;
	}

	public Question(int questionNo, String questionTitle, String questionContent, int userNo, String status,
			String userName, String userId, Date createDate, String answer, String aStatus) {
		super();
		this.questionNo = questionNo;
		this.questionTitle = questionTitle;
		this.questionContent = questionContent;
		this.userNo = userNo;
		this.status = status;
		this.userName = userName;
		this.userId = userId;
		this.createDate = createDate;
		this.answer = answer;
		this.aStatus = aStatus;
	}

	public int getQuestionNo() {
		return questionNo;
	}

	public void setQuestionNo(int questionNo) {
		this.questionNo = questionNo;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getaStatus() {
		return aStatus;
	}

	public void setaStatus(String aStatus) {
		this.aStatus = aStatus;
	}

	@Override
	public String toString() {
		return "Question [questionNo=" + questionNo + ", questionTitle=" + questionTitle + ", questionContent="
				+ questionContent + ", userNo=" + userNo + ", status=" + status + ", userName=" + userName + ", userId="
				+ userId + ", createDate=" + createDate + ", answer=" + answer + ", aStatus=" + aStatus + "]";
	}

}