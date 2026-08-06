package com.nsglobal.queue.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * 
 * Récupérer l'utilisateur connecté
Dans n'importe quel service ou contrôleur
 * */
@Service
@RequiredArgsConstructor
public class UtilsServiceImpl implements UtilsService {
	

	private final HttpServletRequest http;
	
	@Override
	public String getAuthenticatedUser() {
		
		Authentication authentication =SecurityContextHolder
				.getContext()
		        .getAuthentication();

		String username = authentication.getName();
		
		if (authentication == null
	            || !authentication.isAuthenticated()) {

	        return "SYSTEM";

	    }
		
		return username;
	}
	@Override
	public String getConnectedUserName() {
		
		Authentication authentication =SecurityContextHolder
				.getContext()
		        .getAuthentication();
		
		if (authentication == null
	            || !authentication.isAuthenticated()) {

	        return "SYSTEM";

	    }
		String username = authentication.getName();
		return username;
	}
	@Override
	public RemoteHttpDto getRemoteHostInfo() {
		
		//HttpSession session=http.getSession();
		System.out.print("http.getHeader(user-agent) ==>");
		System.out.println(http.getHeader("user-agent"));
		return RemoteHttpDto
				.builder()
				.ipAddress(http.getRemoteAddr())
				.httpMethod(http.getMethod())
				.requestUri(http.getRequestURI())
				.sessionId(http.getRequestedSessionId())
				.userLogged(http.getRemoteUser())
				.userAgent(http.getHeader("user-agent"))
				.build();
	}
	@Override
	public LocalHttpDto getServerHostInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
