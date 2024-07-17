package com.learning.service;

import com.learning.dto.EmailDetails;

public interface SendMailService {

	public void sendMail(EmailDetails emailDetails);
	public void sendMailWithAttachment(EmailDetails emailDetails);
	
}
