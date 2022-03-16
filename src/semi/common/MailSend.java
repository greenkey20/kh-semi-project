package semi.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSend {
	
	public void welcomeMailSend(String email, Object temporary, int num) {
	      Properties prop1 = System.getProperties();

	      // 로그인시 TLS를 사용할 것인지 설정 -> 2022.2.4(금) 12h35 은영 질문 = TLS란 무엇인가? -> 나무위키: TSL = 인터넷에서의 정보를 암호화해서 송수신하는 프로토콜; 인터넷을 사용한 통신에서 보안을 확보하려면 두 통신 당사자가 서로가 신뢰할 수 있는 자임을 확인할 수 있어야 하며, 서로간의 통신 내용이 제 3자에 의해 도청되는 것을 방지해야 한다. 따라서 서로 자신을 신뢰할 수 있음을 알리기 위해 전자 서명이 포함된 인증서를 사용하며, 도청을 방지하기 위해 통신 내용을 암호화한다. 이러한 통신 규약을 묶어 정리한 것이 바로 TLS. 주요 웹브라우저 주소창에 자물쇠 아이콘이 뜨는 것으로 TLS의 적용 여부를 확인할 수 있다.
	      prop1.put("mail.smtp.starttls.enable", "true");

	      // 이메일 발송을 처리해줄 SMTP서버
	      // SMTP(Simple Mail Transfer Protocol) = the basic standard that mail servers use to send email to one another across the internet; used by applications such as Apple Mail or Outlook to upload emails to mail servers that then relay them to other mail servers cf. mail apps typically rely on other standards such as IMAP or POP3 to retrieve emails from servers.
	      prop1.put("mail.smtp.host", "smtp.gmail.com");
	      
	      // Could not convert socket to TLS - > 오류가 발생 했을시  버전을 명시해준다.
	      prop1.put("mail.smtp.ssl.protocols", "TLSv1.2");
	      
	      // SMTP 서버의 인증을 사용한다는 의미
	      prop1.put("mail.smtp.auth", "true");

	      // TLS의 포트번호는 587이며 SSL의 포트번호는 465이다.
	      prop1.put("mail.smtp.port", "587");

	      Authenticator auth = new MailAuth(); // MailAuth 클래스의 객체 생성 -> MailAuth 클래스의 부모 자료형인 Authenticator 변수에 대입 -> 2022.2.4(금) 14h50 은영 질문 = MailAuth()의 역할은 무엇인가? 메일 발신자의 이메일 계정에 접근 허용?

	      Session session = Session.getDefaultInstance(prop1, auth);
	      MimeMessage msg = new MimeMessage(session); // Message 클래스의 객체 생성
	      
	      try {
	         Properties prop2 = System.getProperties();
	         
	         // 읽어들이고자하는 파일의 물리적인 경로
	         String fileName = MailSend.class.getResource("/sql/driver/driver.properties").getPath();
	         
	         try {
	            prop2.load(new FileInputStream(fileName));
	            
	            // 발송자를 지정 <- 발송자의 메일, 발송자명
	            msg.setFrom(new InternetAddress(prop2.getProperty("sendemail"), prop2.getProperty("sendtitle")));
	         } catch (FileNotFoundException e) {
	            e.printStackTrace();
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	         
	         // 보내는 날짜 지정
	         msg.setSentDate(new Date());
	         
	         // 수신자의 메일을 생성
	         InternetAddress to = new InternetAddress(email);

	         // Message 클래스의 setRecipient() 메소드를 사용하여 수신자, 참조, 숨은참조 설정 가능
	         // Message.RecipientType.TO : 받는 사람
	         // Message.RecipientType.CC : 참조
	         // Message.RecipientType.BCC : 숨은 참조
	         msg.setRecipient(Message.RecipientType.TO, to);
	         
	         // 메일의 제목 지정
	         msg.setSubject("놀멍쉬멍 임시 비밀번호입니다", "UTF-8");

	         // Transport는 메일을 최종적으로 보내는 클래스 -> 메일을 보내는 부분 -> 2022.2.4(금) 14h40 은영 질문 = 여기서 왜 num에 따라 부여해야 하지? num의 의미는 무엇인가?
	         if (num == 1) {
	            msg.setText("인증 번호는 " + temporary + "입니다.", "UTF-8");
	         } else {
	        	 msg.setText("임시 비밀번호는 " + temporary + " 입니다.", "UTF-8");
	         }

	         Transport.send(msg);

	      } catch (AddressException ae) {
	         System.out.println("AddressException : " + ae.getMessage());
	      } catch (MessagingException me) {
	         System.out.println("MessagingException : " + me.getMessage());
	      }
	      
	   } // welcomeMailSend() 종료

}
