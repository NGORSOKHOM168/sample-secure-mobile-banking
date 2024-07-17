package com.learning.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.learning.dto.EmailDetails;
import com.learning.service.SendMailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendMailServiceImpl implements SendMailService {
	
	private final JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String mailSender;

	@Override
	public void sendMail(EmailDetails emailDetails) {
	    SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(mailSender);
		mailMessage.setTo(emailDetails.recipient());
		mailMessage.setText(emailDetails.messageBody());
		mailMessage.setSubject(emailDetails.subject());
		javaMailSender.send(mailMessage);
		log.info("Mail has been sent successfully.");
	}

	@Override
	public void sendMailWithAttachment(EmailDetails emailDetails) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(mailSender);
			mimeMessageHelper.setTo(emailDetails.recipient());
			mimeMessageHelper.setText(emailDetails.messageBody());
			mimeMessageHelper.setSubject(emailDetails.subject());
			FileSystemResource file = new FileSystemResource(new File(emailDetails.attachment()));
			mimeMessageHelper.addAttachment(file.getFilename(), file);
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			log.error("Failed send mail, Error Details : "+ e.getMessage());
		}

		log.info("Mail with attachment has been sent successfully.");
	}

}
