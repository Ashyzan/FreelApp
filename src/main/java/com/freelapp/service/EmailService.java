package com.freelapp.service;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	private final JavaMailSender mailSender;
	
	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	
	// invio email semplice
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
	
	
	// invio email con allegati

	public void sendMessageWithAttachment(
	  String to, String subject, String text, String pathToAttachment) {
	    // ...
	    
	    MimeMessage message = mailSender.createMimeMessage();
	     
	    MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setFrom("info@studiocreativo69.it");
		    helper.setTo(to);
		    helper.setSubject(subject);
		    helper.setText(text);
		    FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
		    
		    helper.addAttachment("Invoice.pdf", file);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	    mailSender.send(message);
	    // ...
	}
	
	// invio email HTML
//	public String sendHtmlEmail() {
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setFrom("tutorial.genuinecoder@gmail.com");
//            helper.setTo("tutorial.genuinecoder@gmail.com");
//            helper.setSubject("Java email with attachment | From GC");
//
//            try (var inputStream = Objects.requireNonNull(EmailController.class.getResourceAsStream("/EmailTemplates/template-email-ticket-creation.html"))) {
//                helper.setText(
//                        new String(inputStream.readAllBytes(), StandardCharsets.UTF_8),
//                        true
//                );
//            }
//            helper.addInline("logo.png", new File("C:\\Users\\Genuine Coder\\Documents\\Attachments\\logo.png"));
//            mailSender.send(message);
//            return "success!";
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }


	

}
