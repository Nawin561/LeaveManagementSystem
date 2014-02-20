package com.example.controller;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

public class MailController {

	public void mailMethod(String recieverName,String recieverEmail, String subject, String body) throws IOException {

		String from = "leaverequestsystem@gmail.com";
		String host = "localhost";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);
//		response.setContentType("text/html");
		
		
		// For employee
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					recieverEmail));
			message.setSubject(subject);
			message.setText("Hello, " + recieverName + "\n" + body + "\n Thanks & Best Regards," + "\n" + "Admin");
			Transport.send(message);

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
		
	
		
		
		
		
	}

}
