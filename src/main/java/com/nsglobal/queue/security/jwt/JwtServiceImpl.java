package com.nsglobal.queue.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.nsglobal.queue.user.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
	private final JwtProperties properties;
	
	 /**
     * Retourne la clé utilisée pour signer le JWT.
     */
    private SecretKey getSigningKey() {
    	
    	byte[] keyBytes = Decoders.BASE64.decode(properties.getJwtSecret());

        return Keys.hmacShaKeyFor(keyBytes);
       // return Keys.hmacShaKeyFor().getBytes(StandardCharsets.UTF_8);

    }
    
   

	
	@Override
	public String generateToken(User user) {
		Date currentDate=new Date();
		Date expiration=new Date();
			expiration.setTime(currentDate.getTime()+properties.getJwtExpiration());
			
		String token=Jwts.builder()
				.subject(user.getUserName())
				.claim("role", user.getRole().getName())
				.claim("branchId",
                        user.getBranch() != null
                                ? user.getBranch().getId()
                                : null)
				.issuedAt(currentDate)
				.expiration(expiration)
				.signWith(getSigningKey())
				.compact();
		
		return token;
	}
	
	 /**
     * Extraction des informations du JWT.
     */
	
	 /**
     * Extraction des informations du JWT.
     */
    private Claims extractClaims(String token) {

        return Jwts.parser()

                .verifyWith(getSigningKey())

                .build()

                .parseSignedClaims(token)
                
                .getPayload();
	}
	

	@Override
	public String extractUsername(String token) {

		return extractClaims(token).getSubject();
	}
	
	
	@Override
	public boolean isTokenValid(String token) {

		  try {

	            extractClaims(token);

	            return true;

	        } catch (Exception e) {
	        	System.out.println(e.getMessage());
	            return false;

	        }
	}

	

}
