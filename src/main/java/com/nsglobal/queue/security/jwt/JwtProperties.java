package com.nsglobal.queue.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "com.nsglobal.queue")
public class JwtProperties {
	
	private String jwtSecret;
	
	private Long jwtExpiration;
}
