package com.nsglobal.queue.notification.sms;

public interface SmsProvider {
	
	void send(String phoneNumber, String message);

}
