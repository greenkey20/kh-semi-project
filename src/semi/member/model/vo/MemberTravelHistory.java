package semi.member.model.vo;

import java.sql.Date;

// 2022.1.27(목) 12h15
public class MemberTravelHistory {
	// 필드
	private int memberTravelHistoryNo; // MTH_NO NUMBER nullable No -> primary key

	private int userNo; // USER_NO NUMBER Yes

	private Date travelDate; // TRAVEL_DATE DATE Yes SYSDATE
	private String travelType1; // TRAVELTYPE1 VARCHAR2(20 BYTE) Yes
	private int travelSpot1; // TRAVELSPOT1 NUMBER Yes
	private String travelType3; // TRAVELTYPE3 VARCHAR2(20 BYTE) Yes
	private int travelSpot3; // TRAVELSPOT3 NUMBER Yes
	private String travelSpotName;

	private String foodType; // FOODTYPE VARCHAR2(20 BYTE) No
	private int restaurant1; // RESTAURANT1 NUMBER Yes
	private int restaurant2; // RESTAURANT2 NUMBER Yes
	private int restaurant3; // RESTAURANT3 NUMBER Yes
	private String restaurantName;
	private String restaurantName2;
	private String restaurantName3;

	private String review; // REVIEW CHAR(1 BYTE) default = 'N' -> review 작성 여부
	private String status; // STATUS CHAR(1 BYTE) default = 'Y'

	public MemberTravelHistory() {
		super();
	}

	public MemberTravelHistory(int memberTravelHistoryNo, int userNo, Date travelDate, String travelType1,
			int travelSpot1, String travelType3, int travelSpot3, String foodType, int restaurant1, int restaurant2,
			int restaurant3, String review, String status) {
		super();
		this.memberTravelHistoryNo = memberTravelHistoryNo;
		this.userNo = userNo;
		this.travelDate = travelDate;
		this.travelType1 = travelType1;
		this.travelSpot1 = travelSpot1;
		this.travelType3 = travelType3;
		this.travelSpot3 = travelSpot3;
		this.foodType = foodType;
		this.restaurant1 = restaurant1;
		this.restaurant2 = restaurant2;
		this.restaurant3 = restaurant3;
		this.review = review;
		this.status = status;
	}

	// 2022.1.27(목) 13h5 여행 이력 ISNERT 서블릿 구현 시, 매개변수 9개 받는 생성자 만듦
	public MemberTravelHistory(int userNo, String travelType1, int travelSpot1, String travelType3, int travelSpot3,
			String foodType, int restaurant1, int restaurant2, int restaurant3) {
		super();
		this.userNo = userNo;
		this.travelType1 = travelType1;
		this.travelSpot1 = travelSpot1;
		this.travelType3 = travelType3;
		this.travelSpot3 = travelSpot3;
		this.foodType = foodType;
		this.restaurant1 = restaurant1;
		this.restaurant2 = restaurant2;
		this.restaurant3 = restaurant3;
	}

	// 2022.1.29(토) 21h50 업체 리스트 조회 서블릿 만들다가 selectKeyword2Controller 및
	// MemberTravelHistoryInsertController 다시 읽어보다가 후자 코드 수정하며, 아래 생성자 추가
	public MemberTravelHistory(int userNo, String travelType1, int travelSpot1, String foodType, int restaurant1,
			int restaurant2, int restaurant3) {
		super();
		this.userNo = userNo;
		this.travelType1 = travelType1;
		this.travelSpot1 = travelSpot1;
		this.foodType = foodType;
		this.restaurant1 = restaurant1;
		this.restaurant2 = restaurant2;
		this.restaurant3 = restaurant3;
	}

	// getter, setter
	public int getMemberTravelHistoryNo() {
		return memberTravelHistoryNo;
	}

	public void setMemberTravelHistoryNo(int memberTravelHistoryNo) {
		this.memberTravelHistoryNo = memberTravelHistoryNo;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public String getTravelType1() {
		return travelType1;
	}

	public void setTravelType1(String travelType1) {
		this.travelType1 = travelType1;
	}

	public int getTravelSpot1() {
		return travelSpot1;
	}

	public void setTravelSpot1(int travelSpot1) {
		this.travelSpot1 = travelSpot1;
	}

	public String getTravelType3() {
		return travelType3;
	}

	public void setTravelType3(String travelType3) {
		this.travelType3 = travelType3;
	}

	public int getTravelSpot3() {
		return travelSpot3;
	}

	public void setTravelSpot3(int travelSpot3) {
		this.travelSpot3 = travelSpot3;
	}

	public String getFoodType() {
		return foodType;
	}

	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}

	public int getRestaurant1() {
		return restaurant1;
	}

	public void setRestaurant1(int restaurant1) {
		this.restaurant1 = restaurant1;
	}

	public int getRestaurant2() {
		return restaurant2;
	}

	public void setRestaurant2(int restaurant2) {
		this.restaurant2 = restaurant2;
	}

	public int getRestaurant3() {
		return restaurant3;
	}

	public void setRestaurant3(int restaurant3) {
		this.restaurant3 = restaurant3;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTravelSpotName() {
		return travelSpotName;
	}

	public void setTravelSpotName(String travelSpotName) {
		this.travelSpotName = travelSpotName;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getRestaurantName2() {
		return restaurantName2;
	}

	public void setRestaurantName2(String restaurantName2) {
		this.restaurantName2 = restaurantName2;
	}

	public String getRestaurantName3() {
		return restaurantName3;
	}

	public void setRestaurantName3(String restaurantName3) {
		this.restaurantName3 = restaurantName3;
	}

	@Override
	public String toString() {
		return "MemberTravelHistory [memberTravelHistoryNo=" + memberTravelHistoryNo + ", userNo=" + userNo
				+ ", travelDate=" + travelDate + ", travelType1=" + travelType1 + ", travelSpot1=" + travelSpot1
				+ ", travelType3=" + travelType3 + ", travelSpot3=" + travelSpot3 + ", foodType=" + foodType
				+ ", restaurant1=" + restaurant1 + ", restaurant2=" + restaurant2 + ", restaurant3=" + restaurant3
				+ ", review=" + review + ", status=" + status + "]";
	}

}
