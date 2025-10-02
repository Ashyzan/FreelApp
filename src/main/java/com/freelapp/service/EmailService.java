package com.freelapp.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	private final JavaMailSender mailSender;
	
	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	
	
	@Async
	public void sendEmail(String to, String subject, String body) {
		try {
			
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setFrom("info@studiocreativo69.it");
			message.setSubject(subject);
			message.setText(body);
			
			mailSender.send(message);
			System.out.println("=== EMAIL INVIATA CON SUCCESSO! ===");
			
		} catch (Exception e) {
			System.err.println("=== ERRORE INVIO EMAIL ===");
			System.err.println("Errore: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Errore invio email: " + e.getMessage(), e);
		}
	}
	

}
