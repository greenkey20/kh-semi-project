package semi.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuth extends Authenticator {

	// 2022.2.4(금) 13h 은영 질문 = 이게 뭐지? 필드?
	PasswordAuthentication pa;

	// 생성자
	public MailAuth() {

		Properties prop = new Properties();

		// 읽어들이고자하는 파일의 물리적인 경로
		String fileName = MailAuth.class.getResource("/sql/driver/driver.properties").getPath();

		try {
			prop.load(new FileInputStream(fileName));
			
			// prop으로부터 getProperty() 메소드를 이용해서 각 key에 해당되는 value를 뽑아서 배치
			String mail_id = prop.getProperty("email");
			String mail_pw = prop.getProperty("emailPwd");
			
			pa = new PasswordAuthentication(mail_id, mail_pw);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 2022.2.4(금) 13h5 은영 질문 = 이게 뭐지? 위 필드 pa의 getter인 듯?
	public PasswordAuthentication getPasswordAuthentication() {
		return pa;
	}

}