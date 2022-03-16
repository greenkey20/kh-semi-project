package semi.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

// 2022.1.31(월) 9h35
public class MyFileRenamePolicy implements FileRenamePolicy {

	// implements FileRenamePolicy -> 내가 만든 클래스명에 뜬 빨간줄에 마우스 올려두었을 때 나오는 options 중 'add unimplemented method' 선택
	@Override
	public File rename(File originFile) {
		// getName() -> 매개변수로 전달받은 원본 파일로부터 원본 파일명 뽑기
		String originName = originFile.getName();
		
		// 규칙을 만들어 최대한 이름이 겹치지 않도록 수정 파일명 만들기 <- 파일이 업로드된 시각(연,월,일,시,분,초) + 10000~99998 5자리 random 값 + 원본 파일의 확장자
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		int ranNum = (int)(Math.random() * 89999) + 10000;
		String ext = originName.substring(originName.lastIndexOf(".")); // 원본 파일명에서 가장 마지막에 있는 . 기준으로 문자열 추출
		
		// 위 규칙 3가지를 조합해서 수정 파일명 변수에 담기
		String changeName = currentTime + ranNum + ext;
		
		// 기존/원본 파일에 수정된 파일명을 적용시켜서 return
		return new File(originFile.getParent(), changeName);
	}

}
