package semi.keyword.model.vo;

// 2022.1.21(금) 14h55
public class Hotel extends TravelSpot {
	// 부모클래스로부터 추가되는 필드 없음 + 부모로부터 필드부와 메소드 상속 받음-> 생성자도 추가적으로 만들 필요 없음(2022.1.28(금) 12h30 이건 아닌 듯?) -> 나의 질문 = toString도 오버라이딩할 필요 없나?
	// 2022.1.28(금) 12h30 민성님 설명 = 자식 클래스는 부모 클래스의 생성자를 상속 받지 않는 것 같다; 부모 없이 자식이 존재할 수 없는 바, 자식 객체 생성 시 super()로 부모도 생성되는데, 이게 객체화를 못 시켜서 heap 영역에는 안 올라가도(나의 질문 = 부모가 추상일 때만? 아니면 모든 부모가?), stack에 올라가 있음 -> 자식이 new로 heap 영역에 올라갈 때, stack에 올라간 부모의 형태/자료형/정보를 stack에서 가져와 heap으로 올림
	// + Java 정석  p.332~ 조상클래스의 생성자 super(), p.375~ 추상클래스 등 읽어봄
	
	// 2022.1.28(금) 12h15 추천 여행지 1곳 db 랜덤 조회 결과를 CATEGORY/travelType별 객체/자료형으로 담고자 해서, 아래 생성자 추가-> 2022.2.3(목) 13h15 AREA 추가
	public Hotel() {
		super();
	}
	
	public Hotel(int tsNo, String category, String name, String description, String address, String phone,
			int price, String titleImg, String area) {
		super(tsNo, category, name, description, address, phone, price, titleImg, area);
		// super()로만 썼더니, 2022.1.28(금) 14h30 테스트 시 dao에서 rset 내용이  Hotel/Landscape 객체에 안 담겼음 -> Activity vo에서 매개변수 생성자 위와 같이 만들었던 것 참고해서 수정함
	}
	
	// 2022.1.31(월) 18h50 여행지 관리 페이지에 여행지 상세 조회하기 위해 db로부터 해당 여행지 1곳 정보를 받아오며, 아래 생성자 추가 -> 2022.2.1(화) 21h10 travelSpotDetailView.jsp에 해당 여행지의 총 방문 횟수도 보여주고 싶어서 visitCount 매개변수 추가
	// 2022.1.31(월) 18h50 현재 business logic상 위와 같이  '사용자가 선택한 여행 스타일에 따라 DB에서 1군데 random 조회'해 올 때 AREA를 받아오지 않지만, 추후 프로그램 수정 시 이 생성자를 재활용할 수 있을 것으로 예상함
	public Hotel(int tsNo, String category, String name, String description, String address, String phone,
			int price, String titleImg, String area, int visitCount) {
		super(tsNo, category, name, description, address, phone, price, titleImg, area, visitCount);
	}
	
	// 2022.1.30(일) 0h35 여행지 관리 페이지에 여행지 목록 띄우기 위해 db로부터 여행지 목록을 받아오며, 아래 생성자 추가 -> 2022.1.30(일) 3h5 area 필드 추가 -> 2022.2.1(화) 21h30 해당 여행지의 총 방문 횟수도 보여주고 싶어서 visitCount 매개변수 추가
	public Hotel(int tsNo, String category, String name, String address, String phone, int price, String area, int visitCount) {
		super(tsNo, category, name, address, phone, price, area, visitCount);
	}
	
	// 2022.1.31(월) 13h30 여행지 관리 페이지에서 여행지 추가/등록 처리를 위해, 아래 생성자 추가
	public Hotel(String category, String name, String description, String address, String phone, int price,
			String filePath, String fileName, String area) {
		super(category, name, description, address, phone, price, filePath, fileName, area);
	}
	
	// 2022.2.1(화) 10h50 여행지 관리 페이지에서 여행지 정보 수정 처리를 위해 Servlet에서 service 단으로 넘길 때, 아래 생성자 추가
	public Hotel(int tsNo, String category, String name, String description, String address, String phone,
			int price, String filePath, String fileName, String area) {
		super(tsNo, category, name, description, address, phone, price, filePath, fileName, area);
	}

}
