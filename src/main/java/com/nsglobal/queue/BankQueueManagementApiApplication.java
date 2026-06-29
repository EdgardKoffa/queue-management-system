package com.nsglobal.queue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing
public class BankQueueManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankQueueManagementApiApplication.class, args);
	}

}
