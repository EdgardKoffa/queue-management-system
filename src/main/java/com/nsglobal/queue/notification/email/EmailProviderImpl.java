package com.nsglobal.queue.notification.email;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailProviderImpl implements EmailProvider {

	@Override
	public void send(String email, String subject, String message) {
		 log.info("================================================");
	        log.info("EMAIL");
	        log.info("To : {}", email);
	        log.info("Subject : {}", subject);
	        log.info("Message : {}", message);
	        log.info("================================================");
		
	}

}
