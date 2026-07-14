package com.nsglobal.queue.notification.email;

public interface EmailProvider {
	void send(String email,
            String subject,
            String message);
}
