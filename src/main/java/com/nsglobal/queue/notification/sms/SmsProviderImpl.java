package com.nsglobal.queue.notification.sms;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SmsProviderImpl implements SmsProvider  {

	@Override
	public void send(String phoneNumber, String message) {
		
		 log.info("================================================");
	        log.info("SMS");
	        log.info("To : {}", phoneNumber);
	        log.info("Message : {}", message);
	        log.info("================================================");
		
	}

}
