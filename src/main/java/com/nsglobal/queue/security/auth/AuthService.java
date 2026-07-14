package com.nsglobal.queue.security.auth;

import com.nsglobal.queue.security.auth.dto.LoginRequestDto;
import com.nsglobal.queue.security.auth.dto.LoginResponseDto;

public interface AuthService {

	 LoginResponseDto login(LoginRequestDto dto);
	
}
