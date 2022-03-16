package semi.keyword.model.vo;

//2022.1.21(금) 15h10
public class Restaurant {
	// 식당 객체 필드들 - 우리 프로그램에서 한식당, 제주별미식당, 양식당 모두 동일한 필드들을 가짐 -> 일단 2022.1.21(금) 15h15에는 음식 스타일별 vo 클래스 안 만듦
	private int rsNo; // RS_NO	NUMBER -> primary key, SEQ_xNO.NEXTVAL
	private String category; // CATEGORY	VARCHAR2(20 BYTE) -> 각 테이블명(음식 스타일)이 default 값으로 들어감
	private String rsName; // RS_NAME	VARCHAR2(30 BYTE) 상호명 not null
	private String description; // DESCRIPTION	VARCHAR2(1000 BYTE) 장소 소개 문구 not null
	private String address; // ADDRESS	VARCHAR2(100 BYTE) 주소 not null
	private String phone; // PHONE	VARCHAR2(13 BYTE) 연락처 not null
	private String menu; // MENU	VARCHAR2(100 BYTE) -> 필수 입력x
	private int price; // PRICE	NUMBER -> 필수 입력x
	private String filePath; // FILE_PATH	VARCHAR2(1000 BYTE) 업체소개 사진 저장 폴더 경로 not null
	private String fileName; // FILE_NAME	VARCHAR2(255 BYTE) 업체소개 사진 파일명 not null	
	private String status; // STATUS	CHAR(1 BYTE)  default 'Y'
	private String titleImg; // 2022.1.21(금) 18h5 필드 추가 -> 업체 소개 이미지의 파일명 = DB 조회 결과상 별칭 TITLEIMG에 해당하는 필드
	private String area; // 2022.1.30(일) 3h5 필드 추가; "제주" 또는 "서귀포" 또는 "우도"
	private int visitCount; // 2022.2.2(수) 16h20 필드 추가
	
	// 생성자
	public Restaurant() {
		super();
	}

	public Restaurant(int rsNo, String category, String rsName, String description, String address, String phone,
			String menu, int price, String filePath, String fileName, String status, String titleImg, String area, int visitCount) {
		super();
		this.rsNo = rsNo;
		this.category = category;
		this.rsName = rsName;
		this.description = description;
		this.address = address;
		this.phone = phone;
		this.menu = menu;
		this.price = price;
		this.filePath = filePath;
		this.fileName = fileName;
		this.status = status;
		this.titleImg = titleImg;
		this.area = area;
		this.visitCount = visitCount;
	}

	// 2022.1.21(금) 20h45 사용자가 선택한 음식 스타일에 따라 DB에서 3군데 조회해 올 때 추가한, 매개변수 9개 받는 생성자-> 2022.2.3(목) 13h15 AREA 추가
	public Restaurant(int rsNo, String category, String rsName, String description, String address, String phone,
			String menu, int price, String titleImg, String area) {
		super();
		this.rsNo = rsNo;
		this.category = category;
		this.rsName = rsName;
		this.description = description;
		this.address = address;
		this.phone = phone;
		this.menu = menu;
		this.price = price;
		this.titleImg = titleImg;
		this.area = area;
	}
	
	// 식당 관리 페이지에 식당 상세 조회하기 위해 db로부터 해당 여행지 1곳 정보를 받아오며, 아래 생성자 추가 vs 2022.2.2(수) 22h50 setter로 객체 r 채워서 controller로 가져옴
	
	// 2022.2.2(수) 16h25 식당 관리 페이지에 식당 목록 띄우기 위해 db로부터 식당 목록을 받아오며, 아래 생성자 추가 
	public Restaurant(int rsNo, String category, String rsName, String address, String phone, String menu, int price,
			String area, int visitCount) {
		super();
		this.rsNo = rsNo;
		this.category = category;
		this.rsName = rsName;
		this.address = address;
		this.phone = phone;
		this.menu = menu;
		this.price = price;
		this.area = area;
		this.visitCount = visitCount;
	}
	
	// 식당 관리 페이지에서 식당 추가/등록 처리를 위해, 아래 생성자 추가
	public Restaurant(String category, String rsName, String description, String address, String phone, String menu,
			int price, String filePath, String fileName, String area) {
		super();
		this.category = category;
		this.rsName = rsName;
		this.description = description;
		this.address = address;
		this.phone = phone;
		this.menu = menu;
		this.price = price;
		this.filePath = filePath;
		this.fileName = fileName;
		this.area = area;
	}
	
	// 2022.2.3(목) 0h45 식당 관리 페이지에서 식당 정보 수정 처리를 위해 Servlet에서 service단으로 넘길 때, 아래 생성자 추가
	public Restaurant(int rsNo, String category, String rsName, String description, String address, String phone,
			String menu, int price, String filePath, String fileName, String area) {
		super();
		this.rsNo = rsNo;
		this.category = category;
		this.rsName = rsName;
		this.description = description;
		this.address = address;
		this.phone = phone;
		this.menu = menu;
		this.price = price;
		this.filePath = filePath;
		this.fileName = fileName;
		this.area = area;
	}

	// getter, setter
	public int getRsNo() {
		return rsNo;
	}

	public void setRsNo(int rsNo) {
		this.rsNo = rsNo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRsName() {
		return rsName;
	}

	public void setRsName(String rsName) {
		this.rsName = rsName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	@Override
	public String toString() {
		return "Restaurant [rsNo=" + rsNo + ", category=" + category + ", rsName=" + rsName + ", description="
				+ description + ", address=" + address + ", phone=" + phone + ", menu=" + menu + ", price=" + price
				+ ", filePath=" + filePath + ", fileName=" + fileName + ", status=" + status + ", titleImg=" + titleImg
				+ ", area=" + area + ", visitCount=" + visitCount + "]";
	}

}
