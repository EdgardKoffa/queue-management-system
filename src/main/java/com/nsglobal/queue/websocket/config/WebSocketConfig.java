package com.nsglobal.queue.websocket.config;

import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.nsglobal.queue.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	private final UserDetailsService usrDetail;
	private final JwtService jwtUtils;
	
	/*
	 * Voici la classe Java typique pour configurer
	 *  le serveur de WebSocket. Elle doit correspondre exactement 
	 * aux routes définies dans votre code Angular (/ws et /topic).
	 * */
	// Configuration du Broker
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		
		// Active un broker simple en mémoire pour 
		//les messages sortants (Serveur -> Client)
        // Correspond à votre ".subscribe('/topic/branch/...')" côté Angular
		registry.enableSimpleBroker("/topic");
		
		// Préfixe pour les messages entrants (Client -> Serveur)
        // Utile pour la méthode sendMessage() d'Angular
		registry.setApplicationDestinationPrefixes("/app");

	}

	// Déclaration du point de connexion
	/**
	 * Le navigateur se connectera à : ws://localhost:2026/ws
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// Enregistre le point d'entrée qui correspond à votre "new SockJS('/ws')" côté Angular ou autre
		registry
		.addEndpoint("/ws")
		.setAllowedOriginPatterns("*")// Permet les requêtes Cross-Origin (CORS) si nécessaire
        .withSockJS(); // Active le support de secours SockJS

	}
	
	 @Override
	    public void configureClientInboundChannel(ChannelRegistration registration) {
	        registration.interceptors(new ChannelInterceptor() {
	            @Override
	            public Message<?> preSend(Message<?> message, MessageChannel channel) {
	                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
	                
	                // Si l'action en cours est une demande de connexion STOMP
	                if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
	                    // Récupération de l'en-tête "Authorization" envoyé par Angular
	                    String authHeader = accessor.getFirstNativeHeader("Authorization");
	                    
	                    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	                        String jwt = authHeader.substring(7);
	                        
	                        try {
	                            // 1. Extraire le nom d'utilisateur (ou email) du token
	                            String username = jwtUtils.extractUsername(jwt); // Adaptez selon votre méthode réelle
	                            
	                            // 2. Valider le token et injecter l'authentification si valide
	                            if (username != null && jwtUtils.isTokenValid(jwt) && accessor.getUser() == null) {
	                                UserDetails userDetails = usrDetail.loadUserByUsername(username);
	                                
	                                UsernamePasswordAuthenticationToken authentication = 
	                                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                                
	                                // On lie l'utilisateur à la session de ce WebSocket
	                                accessor.setUser(authentication);
	                            }
	                        } catch (Exception e) {
	                            throw new IllegalArgumentException("Échec de l'authentification WebSocket : " + e.getMessage());
	                        }
	                    } else {
	                        throw new IllegalArgumentException("Jeton JWT manquant dans la connexion WebSocket.");
	                    }
	                }
	                return message;
	            }
	        });
	    }
}
