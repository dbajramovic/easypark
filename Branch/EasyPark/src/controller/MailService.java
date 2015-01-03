package controller;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {
	MailService(){};
	
	public void SendToken(String primalac, String token) throws UnsupportedEncodingException{

		final String username = "easyparkinfo@gmail.com";
		final String password = "!Q\"W#E$R%T&Z/U(I";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("easyparkinfo@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(primalac));
			message.setSubject("Easy Park pristupni token");
			message.setText("Poštovani,"
				+ "\n\n Hvala Vam na zahtjevu za registraciju na našem sajtu! "
				+ "\n U nastavku ove poruke se nalazi token kojeg trebate upisati u potrebno polje za registraciju!"
				+ "\n U našoj bazi Vaš zahtjev je upisan:"
				+ "\n e-mail: " + primalac+""
						+ "\n token: \b "+token+""
								+ "\n\n\n Srdačan pozdrav");
			Transport.send(message); 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	
}
