package com.nsglobal.queue.security.jwt;

import com.nsglobal.queue.user.entity.User;

/**
 * Créer un token
Valider un token Extraire le username
 * */
public interface JwtService {
	
	 /**
     * Génère un JWT.
     */
    String generateToken(User user);

    /**
     * Extrait le username.
     */
    String extractUsername(String token);

    /**
     * Vérifie si le token est valide.
     */
    boolean isTokenValid(String token);
    
    
}
