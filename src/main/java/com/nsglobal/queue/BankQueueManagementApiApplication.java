package com.nsglobal.queue;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nsglobal.queue.security.jwt.JwtService;

import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class BankQueueManagementApiApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(BankQueueManagementApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
	}

}
