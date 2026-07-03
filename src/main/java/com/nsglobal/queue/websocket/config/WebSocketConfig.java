package com.nsglobal.queue.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	// Configuration du Broker
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		
		//afectation du prefix de ws
		registry.enableSimpleBroker("/topic");

		registry.setApplicationDestinationPrefixes("/app");

	}

	// Déclaration du point de connexion
	/**
	 * Le navigateur se connectera à : ws://localhost:2026/ws
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {

		registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();

	}
}
