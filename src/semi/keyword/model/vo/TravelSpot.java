package semi.keyword.model.vo;

public abstract class TravelSpot {
	// 2022.1.21(금) 14h30 abstract 클래스로 만듦
	// vs 2022.1.21(금) 16h20 keywordDao의 selectTravelSpotList()의 조회 결과를 TravelSpot 객체에 담으려고 하니, 추상클래스는 객체 생성이 불가능한 바, 안 됨 -> 일반 클래스로 변경
	// 2022.2.1(화) abstract 클래스로 결국 변경
	
	// 상속받는 자식클래스들이 물려받을 필드들
	private int tsNo; //	TS_NO	NUMBER -> primary key, SEQ_xNO.NEXTVAL
	private String category; //	CATEGORY	VARCHAR2(20 BYTE) -> 각 테이블명(여행 스타일)이 default 값으로 들어감
	private String name; //	NAME	VARCHAR2(30 BYTE) 상호명 not null
	private String description; //	DESCRIPTION	VARCHAR2(1000 BYTE) 장소 소개 문구 not null
	private String address; // ADDRESS	VARCHAR2(100 BYTE) 주소 not null
	private String phone; // PHONE	VARCHAR2(13 BYTE) 연락처 not null
	private int price; // PRICE	NUMBER -> 필수 입력x
	private String filePath; //	FILE_PATH	VARCHAR2(1000 BYTE)	업체소개 사진 저장 폴더 경로 not null
	private String fileName; //	FILE_NAME	VARCHAR2(255 BYTE) 업체소개 사진 파일명 not null
	private String status; //	STATUS	CHAR(1 BYTE) default 'Y' 
	private String titleImg; // 2022.1.21(금) 16h25 필드 추가 -> 업체 소개 이미지의 파일명 = DB 조회 결과상 별칭 TITLEIMG에 해당하는 필드
	private String area; // 2022.1.30(일) 3h5 필드 추가; "제주" 또는 "서귀포" 또는 "우도"
	private int visitCount; // 2022.2.1(화) 21h 필드 추가
	// ACTIVITY 테이블만 EQUIPMENT	VARCHAR2(1000 BYTE)	컬럼을 추가적으로 가지고 있음
		
	// 생성자
	public TravelSpot() {
		super();
	}

	public TravelSpot(int tsNo, String category, String name, String description, String address, String phone,
			int price, String filePath, String fileName, String status, String titleImg, String area, int visitCount) {
		super();
		this.tsNo = tsNo;
		this.category = category;
		this.name = name;
		this.description = description;
		this.address = address;
		this.phone = phone;
		this.price = price;
		this.filePath = filePath;
		this.fileName = fileName;
		this.status = status;
		this.titleImg = titleImg;
		this.area = area;
		this.visitCount = visitCount;
	}
	
	// 2022.1.21(금) 20h30 사용자가 선택한 여행 스타일에 따라 DB에서 1군데 random 조회해 올 때 추가한, 매개변수 8개 받는 생성자 -> 2022.2.3(목) 13h15 AREA 추가
	public TravelSpot(int tsNo, String category, String name, String description, String address, String phone,
			int price, String titleImg, String area) {
		super();
		this.tsNo = tsNo;
		this.category = category;
		this.name = name;
		this.description = description;
		this.address = address;
		this.phone = phone;
		this.price = price;
		this.titleImg = titleImg;
		this.area = area;
	}
	
	// 2022.1.31(월) 18h50 여행지 관리 페이지에 여행지 상세 조회하기 위해 db로부터 해당 여행지 1곳 정보를 받아오며, 아래 생성자 추가 -> 2022.2.1(화) 21h10 travelSpotDetailView.jsp에 해당 여행지의 총 방문 횟수도 보여주고 싶어서 visitCount 매개변수 추가
	// 2022.1.31(월) 18h50 현재 business logic상 위와 같이  '사용자가 선택한 여행 스타일에 따라 DB에서 1군데 random 조회'해 올 때 AREA를 받아오지 않지만, 추후 프로그램 수정 시 이 생성자를 재활용할 수 있을 것으로 예상함
	public TravelSpot(int tsNo, String category, String name, String description, String address, String phone,
			int price, String titleImg, String area, int visitCount) {
		super();
		this.tsNo = tsNo;
		this.category = category;
		this.name = name;
		this.description = description;
		this.address = address;
		this.phone = phone;
		this.price = price;
		this.titleImg = titleImg;
		this.area = area;
		this.visitCount = visitCount;
	}

	// 2022.1.30(일) 0h40 여행지 관리 페이지에 여행지 목록 띄우기 위해 db로부터 여행지 목록을 받아오며, 아래 생성자 추가 -> 2022.2.1(화) 21h30 해당 여행지의 총 방문 횟수도 보여주고 싶어서 visitCount 매개변수 추가
	public TravelSpot(int tsNo, String category, String name, String address, String phone, int price, String area, int visitCount) {
		super();
		this.tsNo = tsNo;
		this.category = category;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.price = price;
		this.area = area;
		this.visitCount = visitCount;
	}
	
	// 2022.1.31(월) 13h30 여행지 관리 페이지에서 여행지 추가/등록 처리를 위해, 아래 생성자 추가
	public TravelSpot(String category, String name, String description, String address, String phone, int price,
			String filePath, String fileName, String area) {
		super();
		this.category = category;
		this.name = name;
		this.description = description;
		this.address = address;
		this.phone = phone;
		this.price = price;
		this.filePath = filePath;
		this.fileName = fileName;
		this.area = area;
	}
	
	// 2022.2.1(화) 10h50 여행지 관리 페이지에서 여행지 정보 수정 처리를 위해 Servlet에서 service단으로 넘길 때, 아래 생성자 추가
	public TravelSpot(int tsNo, String category, String name, String description, String address, String phone,
			int price, String filePath, String fileName, String area) {
		super();
		this.tsNo = tsNo;
		this.category = category;
		this.name = name;
		this.description = description;
		this.address = address;
		this.phone = phone;
		this.price = price;
		this.filePath = filePath;
		this.fileName = fileName;
		this.area = area;
	}

	// getter, setter
	public int getTsNo() {
		return tsNo;
	}

	public void setTsNo(int tsNo) {
		this.tsNo = tsNo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	// toString() 오버라이딩
	@Override
	public String toString() {
		return "TravelSpot [tsNo=" + tsNo + ", category=" + category + ", name=" + name + ", description=" + description
				+ ", address=" + address + ", phone=" + phone + ", price=" + price + ", filePath=" + filePath
				+ ", fileName=" + fileName + ", status=" + status + ", titleImg=" + titleImg + ", area=" + area
				+ ", visitCount=" + visitCount + "]";
	}

}
