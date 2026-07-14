package com.nsglobal.queue.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "com.nsglobal.queue")
@SuppressWarnings("unused")
public class AppConfig {
	
	private String enterpriseName;

	private String vocalMessage;
}
