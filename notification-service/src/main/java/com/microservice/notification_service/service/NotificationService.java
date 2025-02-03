package com.microservice.notification_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.microservice.notification_service.dto.NotificationRequest;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class NotificationService {

	private final JavaMailSender mailSender;

	public NotificationService(JavaMailSender mailSender) {
		// TODO Auto-generated constructor stub
		this.mailSender = mailSender;
	}

	@RabbitListener(queues = "purchase.queue")
	public void sendEmail(NotificationRequest notification) {
		try {
			MimeMessage mail = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mail, true);

			helper.setTo(notification.getEmail());
			helper.setSubject("Confirmação de Compra");
			helper.setText(notification.getMessage(), true);

			mailSender.send(mail);
			System.out.println("E-mail enviado com sucesso!");

		} catch (MessagingException e) {
			// TODO: handle exception
			System.err.println("Erro ao enviar e-mail: " + e.getMessage());

		}

	}
}
