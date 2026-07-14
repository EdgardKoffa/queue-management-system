package com.nsglobal.queue.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nsglobal.queue.user.dto.UserResponseDto;
import com.nsglobal.queue.user.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * 
 * Récupérer l'utilisateur connecté
Dans n'importe quel service ou contrôleur
 * */
@Service
@RequiredArgsConstructor
public class UtilsServiceImpl implements UtilsService {
	
	private final UserService userService;
	@Override
	public UserResponseDto getAuthenticatedUser() {
		Authentication authentication =SecurityContextHolder .getContext()
		                .getAuthentication();

		String username = authentication.getName();
		
		return userService.findByUserName(username);
	}

}
