package semi.keyword.model.vo;

//2022.1.21(금) 15h5
public class Activity extends TravelSpot {

	// 부모클래스로부터 추가되는 필드 = equipment
	private String equipment; // EQUIPMENT	VARCHAR2(1000 BYTE)	컬럼을 추가적으로 가지고 있음

	// 매개변수 없는 생성자
	public Activity() {
		super();
	}

	// 모든 필드를 매개변수로 받는 생성자
	public Activity(String equipment) {
		super();
		this.equipment = equipment;
	}
	
//	public Activity(int int1, String string, String string2, String string3, String string4, String string5, int int2,
//			String string6, String string7) {
//		// TODO Auto-generated constructor stub
//	}
	
	// 2022.1.23(일) 16h35 사용자가 선택한 여행 스타일에 따라 DB에서 1군데 조회해 올 때 추가한, 매개변수 9개 받는 생성자-> 2022.2.3(목) 13h15 AREA 추가
	public Activity(int tsNo, String category, String name, String description, String address, String phone, int price,
			String titleImg, String area, String equipment) {
		super(tsNo, category, name, description, address, phone, price, titleImg, area);
		this.equipment = equipment;
	}
	
	// 2022.1.31(월) 18h50 여행지 관리 페이지에 여행지 상세 조회하기 위해 db로부터 해당 여행지 1곳 정보를 받아오며, 아래 생성자 추가 -> 2022.2.1(화) 21h10 travelSpotDetailView.jsp에 해당 여행지의 총 방문 횟수도 보여주고 싶어서 visitCount 매개변수 추가
	// 2022.1.31(월) 18h50 현재 business logic상 위와 같이  '사용자가 선택한 여행 스타일에 따라 DB에서 1군데 random 조회'해 올 때 AREA를 받아오지 않지만, 추후 프로그램 수정 시 이 생성자를 재활용할 수 있을 것으로 예상함
	public Activity(int tsNo, String category, String name, String description, String address, String phone,
			int price, String titleImg, String area, int visitCount, String equipment) {
		super(tsNo, category, name, description, address, phone, price, titleImg, area, visitCount);
		this.equipment = equipment;
	}
	
	// 2022.1.30(일) 0h45 여행지 관리 페이지에 여행지 목록 띄우기 위해 db로부터 여행지 목록을 받아오며, 아래 생성자 추가 -> 2022.2.1(화) 21h30 해당 여행지의 총 방문 횟수도 보여주고 싶어서 visitCount 매개변수 추가
	public Activity(int tsNo, String category, String name, String address, String phone, int price, String area, int visitCount, String equipment) {
		super(tsNo, category, name, address, phone, price, area, visitCount);
		this.equipment = equipment;
	}
	
	// 2022.1.31(월) 13h30 여행지 관리 페이지에서 여행지 추가/등록 처리를 위해, 아래 생성자 추가
	public Activity(String category, String name, String description, String address, String phone, int price,
			String filePath, String fileName, String area, String equipment) {
		super(category, name, description, address, phone, price, filePath, fileName, area);
		this.equipment = equipment;
	}
	
	// 2022.2.1(화) 10h50 여행지 관리 페이지에서 여행지 정보 수정 처리를 위해 Servlet에서 service 단으로 넘길 때, 아래 생성자 추가
	public Activity(int tsNo, String category, String name, String description, String address, String phone,
			int price, String filePath, String fileName, String area, String equipment) {
		super(tsNo, category, name, description, address, phone, price, filePath, fileName, area);
		this.equipment = equipment;
	}

	// 부모클래스에 비해 추가되는 getter, setter
	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	@Override
	public String toString() {
		return "Activity [equipment=" + equipment + ", getTsNo()=" + getTsNo() + ", getCategory()=" + getCategory()
				+ ", getName()=" + getName() + ", getDescription()=" + getDescription() + ", getAddress()="
				+ getAddress() + ", getPhone()=" + getPhone() + ", getPrice()=" + getPrice() + ", getFilePath()="
				+ getFilePath() + ", getFileName()=" + getFileName() + ", getStatus()=" + getStatus()
				+ ", getTitleImg()=" + getTitleImg() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}

}
