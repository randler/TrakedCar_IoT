package br.edu.ifba.embarcados.clientewebcoisas.email;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import br.edu.ifba.embarcados.clientewebcoisas.bean.UserEmail;
import br.edu.ifba.embarcados.clientewebcoisas.bean.Usuario;

public class SendEmail {

	private static SendEmail email = null;
	
	private SendEmail(){
		
	}
	
	public static  SendEmail getInstancia(){
		if(email == null){
			email = new SendEmail();
		}
		return email;
	}


	
	public static void sendMail(String hora, String data,String local, Image foto, Usuario usuario) {
  
		 String to = usuario.getEmail();

         // Sender's email ID needs to be mentioned
         String from = UserEmail.getUSER_EMAIL_NAME();

         converterImage(foto);
         // Get system properties
         Properties props = System.getProperties();

         props.put("mail.smtp.starttls.enable", "true"); 
         props.put("mail.smtp.host", "smtp.gmail.com");

         props.put("mail.smtp.port", "587");
         props.put("mail.smtp.auth", "true");

     Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication(UserEmail.getUSER_EMAIL_NAME(), UserEmail.getUSER_EMAIL_PASSWORD());
           }
        });

     try {

        // Create a default MimeMessage object.
        Message message = new MimeMessage(session);

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(from));

        // Set To: header field of the header.
        message.setRecipients(Message.RecipientType.TO,
           InternetAddress.parse(to));

        // Set Subject: header field
        message.setSubject("CarTracked");

        // This mail has 2 part, the BODY and the embedded image
        MimeMultipart multipart = new MimeMultipart("related");

        // first part (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<H1>Veículo Identificado</H1>"
        		+ "<strong>Sr. "+usuario.getNome() + "</strong> O seu veículo foi identificado no seguinte"
        		+ " local: " + local
        		+ "- no dia: " + data
        		+ "- na hora: " + hora
        		+ "<br /><img src=\"cid:image\">";
        messageBodyPart.setContent(htmlText, "text/html");
        // add it
        multipart.addBodyPart(messageBodyPart);

        // second part (the image)
        messageBodyPart = new MimeBodyPart();
        
        DataSource fds = new FileDataSource(
           "/home/randler/imagem.png");

        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<image>");

        // add image to the multipart
        multipart.addBodyPart(messageBodyPart);

        // put everything together
        message.setContent(multipart);
        // Send message
        Transport.send(message);

        System.out.println("Sent message successfully....");

     } catch (MessagingException e) {
        throw new RuntimeException(e);
     }
	
	}

	private static void converterImage(Image foto) {
		BufferedImage img = new BufferedImage(foto.getWidth(null), foto.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(foto,0,0, null);
		g2d.dispose();
		
		try {
			ImageIO.write(img, "PNG", new java.io.File("/home/randler/imagem.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
